package selector

import net.sf.corn.cps.CPScanner
import net.sf.corn.cps.PackageNameFilter

import java.lang.reflect.Field
import java.util.*
import kotlin.reflect.full.isSubclassOf


data class ProcessSelectorArgs(
    var clazz: Class<*>,
    var prefix: String,
    var prevBase: Selector? = null,
    var prevSBase: Selector? = null,
    var baseObj: Any?=null) {

    fun getBase() : Selector? {
        if(prevBase != null) {
            return prevBase
        }

        return prevSBase
    }

    fun isSBase() = prevSBase != null
}

open class POM (val base: Selector? = null, val sbase: Selector? = null, val clearBase: Boolean = false) {

    fun getSelectorsList() {

    }

    fun getPomObjectFields(): ArrayList<Field> {
        val res = ArrayList<Field>()
        javaClass.declaredFields.forEach {
            it.isAccessible = true
            if(it.get(this) as? POM != null && it.name != "INSTANCE") {
                res.add(it)
            }
        }
        return res
    }

    companion object {
        var packageNameG = "selector"
        // private var parsedClasses = HashSet<Class>()

        fun parseAnnotations(packageName: String) {
            packageNameG = packageName.removeSuffix(".*")

            val classes = CPScanner.scanClasses(PackageNameFilter(packageName))

            for (clazz in classes) {
                //ignore inner classes
                if(!clazz.name.contains("$")) {
                    //  parsedClasses = HashSet()
                    if (clazz.simpleName != "POM") {
                        processSelectors(ProcessSelectorArgs(clazz, clazz.simpleName + "."))
                    }
                }
            }
        }

        fun debug(msg: String, level: Int = 0, iterLevel: Int = 0, debugLevel: Int = 0) {
            var s = ""
            for(i in 1..level) {
                s += "    "
            }

            for(i in 1..iterLevel) {
                s += "      "
            }

            for(i in 1..debugLevel) {
                s += "    "
            }

            println(s + msg)
        }

        internal fun processSelectors(args: ProcessSelectorArgs, iterLevel: Int = 0, debugLevel: Int = 0) {
            val iterLevel = args.prefix.count{ ".".contains(it) } - 1

            debug("Processing $args", iterLevel, debugLevel)
            var base: Selector? = args.prevBase
            var sbase: Selector? = args.prevSBase

            var baseObj = args.baseObj
            var clazz = args.clazz
            var instanceFound = false

            if (clazz.`package`.name == "selector") {
                debug("Internal class, no need to scan", iterLevel, debugLevel)
                return
            }

            debug("Class has ${args.clazz.declaredFields.size} filds", iterLevel, debugLevel)

            for (f in args.clazz.declaredFields) {
                f.isAccessible = true

                debug("Checking ${f.name}: ${f.type.simpleName}", 1, iterLevel, debugLevel)

                if(f.name == "INSTANCE") {
                    debug("Instance field was found", 2, iterLevel, debugLevel)

                    instanceFound = true
                    try {
                        var o = f.get(null)
                        if(o != null) {
                            baseObj = o
                        }
                        if(o is POM) {
                            debug("Instance is POM class", 3, iterLevel, debugLevel)

                            if(o.clearBase) {
                                debug("clear bases...", 4, iterLevel, debugLevel)
                                base = null
                                sbase = null
                            } else {
                                if(o.base != null) {
                                    debug("base was found", 4, iterLevel, debugLevel)
                                    if(base != null) {
                                        if(o.base != null) {
                                            base *= (o.base!!)
                                        }
                                    } else {
                                        base = o.base
                                    }
                                } else if(o.sbase != null){
                                    debug("sbase was found", 5, iterLevel, debugLevel)
                                    if(sbase != null) {
                                        if(o.sbase != null) {
                                            sbase *= (o.sbase!!)
                                        }
                                    } else {
                                        sbase = o.sbase
                                    }
                                }
                            }


                        } else {
                            debug("Instance is not POM class", 3, iterLevel, debugLevel)

                        }
                    } catch (e: Exception) {
                        debug("fall into exception",2, iterLevel, debugLevel)
                    }

                    break
                }
            }

            if(!instanceFound) {
                debug("Instance was not found", 2, iterLevel, debugLevel)
                if(clazz.kotlin.isSubclassOf(POM::class)) {
                    debug("Class is POM", 3, iterLevel, debugLevel)
                    try {
                        val pom = (clazz.newInstance() as POM)

                        if(pom.base != null) {
                            debug("base was found", 4, iterLevel, debugLevel)
                            base = pom.base
                        }
                        if(pom.sbase != null) {
                            debug("sbase was found", 4, iterLevel, debugLevel)
                            sbase = pom.sbase
                        }
                    } catch (e : Exception) {

                    }
                } else {
                    debug("Class is not POM", 3, iterLevel, debugLevel)
                }
            }

            debug("Class has ${clazz.declaredFields.size} fields", 2, iterLevel, debugLevel)

            for (f in clazz.declaredFields) {
                debug("cheking ${f.name}", 3, iterLevel, debugLevel)

                f.isAccessible = true

                try {
                    if(f.name == "INSTANCE") {
                        debug("continue next field", 4, iterLevel, debugLevel)
                        continue
                    }

                    if(baseObj == null) {
                        debug("get object from null", 4, iterLevel, debugLevel)
                    } else {
                        debug("get object from baseObj ${baseObj.javaClass.simpleName}", 4, iterLevel, debugLevel)
                    }
                    val o = if(baseObj == null) f.get(null) else f.get(baseObj)

                    if (o is Selector) {
                        debug("object is Selector", 4, iterLevel, debugLevel)
                        when {
                            base != null -> {
                                debug("base initialized", 5, iterLevel, debugLevel)
                                o.setBase(base.clone())
                            }
                        }
                        val name = args.prefix + f.name
                        o.name = name

                        debug("name set to $name", 4, iterLevel, debugLevel)
                    }
                    else if (o is ComposeSelector) {

                        when {
                            base != null -> o.setBase(base.clone())
                        }

                        o.name = args.prefix + f.name
                    } else {
                        debug("Field is composed type", 5, iterLevel, debugLevel)
                        val cls = o.javaClass
                        val c1 = !cls.isPrimitive
                        val c2 = cls.name.startsWith(packageNameG) && !cls.name.contains("Assert")
                        if(c1 && c2 && args.clazz !== cls) {
                            debug("calling parse to: " + f.declaringClass.simpleName + "." + f.name, 6, iterLevel, debugLevel)
                            processSelectors(ProcessSelectorArgs(cls, args.prefix + f.name + ".",  base, sbase, o), iterLevel + 1, 6) //o
                        } else {
                            debug("bad class", 6, iterLevel, debugLevel)
                        }
                    }
                } catch (e : Exception) {

                }
            }

            if(!instanceFound) {
                debug("Instance not found, clear base", 2)
                base = null
                sbase = null
            }

            debug("Declared objects: ${clazz.declaredClasses.size}", 2)
            //parse innher objects
            Arrays.stream(clazz.declaredClasses).forEach {
                try {
                    if(it.getDeclaredField("INSTANCE") != null) {
                        debug("calling for innher object: ${it.simpleName}", 3)
                        processSelectors(ProcessSelectorArgs(it, args.prefix + it.simpleName + ".",  base, sbase, null), iterLevel + 1, 3)
                    }
                } catch (e : Exception) {
                    debug("No instance field", 3)
                }
            }

            //parse selectors from base
            val sclass = clazz.superclass
            if(sclass != null && sclass.name.startsWith(packageNameG)) {
                debug("Has superclass: ${sclass.name}", 3)
                //   if(!parsedClasses.contains(sclass)) {
                debug("calling process for superclass", 4)
                processSelectors(ProcessSelectorArgs(sclass, args.prefix + sclass.simpleName + ".", base, sbase, baseObj), iterLevel + 1, 3)
                /*} else {
                    debug("class ${sclass.name} already processed", 4)
                }*/
            }

            /*   debug("adding ${args.clazz.simpleName} to checkset", 2)
               parsedClasses.add(args.clazz)*/
        }
    }
}
