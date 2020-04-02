package util


import com.google.gson.GsonBuilder
import net.sf.corn.cps.CPScanner
import net.sf.corn.cps.PackageNameFilter
import page.Block
import selector.Selector
import selector.SelectorState
import selector.initWithName
import selector.setBase
import java.lang.reflect.Field
import kotlin.reflect.KClass


class ReflectionHelper {
    companion object {

        fun isObject(clazz: Class<*>): Boolean {
            for (f in clazz.declaredFields) {
                if (f.name == "INSTANCE") {
                    return true
                }
            }

            return false
        }

        fun getObject(clazz: Class<*>): Any {
            return clazz.getField("INSTANCE").get(null)
        }

        fun getObjects(packageName: String): ArrayList<Class<*>> {
            var res = ArrayList<Class<*>>()
            val classes = CPScanner.scanClasses(PackageNameFilter(packageName))

            for (clazz in classes) {
                if (isObject(clazz)) {
                    res.add(clazz)
                }
            }

            return res
        }

        fun parseAnnotations(packageName: String) {
            val objectClasses = getObjects(packageName)

            for (clazz in objectClasses) {
                scanObject(
                    getObject(
                        clazz
                    )
                )
            }
        }

        fun getClassSelectors(obj: Any): ArrayList<Selector> {
            var res = ArrayList<Selector>()

            if (obj is Selector) {
                val fields = getFieldsFromObj(obj)

                for (f in fields) {
                    f.isAccessible = true
                    val member = f.get(obj)
                    if (member is Selector) {
                        member.initWithName(f.name)
                        res.add(member)
                    }
                }
            }

            return res
        }

        fun getFieldsFromObj(obj: Any): List<out Field> {
            val fields = if (isObject(obj.javaClass))
                obj.javaClass.superclass.declaredFields
            else
                obj.javaClass.declaredFields

            return fields.filter { it.name != "this$0" }
        }

        fun getInnerClassSelectors(obj: Any, root: Any = obj): ArrayList<Selector> {
            var res = ArrayList<Selector>()

            val fields = getFieldsFromObj(obj)

            for (f in fields) {
                f.isAccessible = true
                val member = f.get(root)
                if (member is Selector) {
                    if (f.type.simpleName != "Selector") {
                        println(f.type.simpleName)

                        res.add(member)
                       // res.addAll(getInnerClassSelectors(member))
                    }
                }
            }

            return res
        }

        fun scanObject(obj: Any, init: Boolean = true) {

            if (obj is Selector) {

                for (s in getClassSelectors(obj)) {
                    if (obj is Block) {
                        s.setBase(obj)
                        if (init) {
                            s.initWithName("${obj.name}.${s.name}")
                        }
                    }
                }

                for (s in getInnerClassSelectors(obj)) {
                    scanObject(s)
                }
            }
        }

        private val gson = GsonBuilder().setPrettyPrinting().create()

        fun getNewRootObject(obj: Any): Any {
            val c = obj::class
            var innerClass = true
            var name = c.java.name.split("$").first()

            if (name.isBlank()) {
                name = c.simpleName!!
                innerClass = false
            }

            val cls = Class.forName(name)
            val root =
                getRootObject(obj as Selector)
            root.name = cls.simpleName
            val json = gson.toJson(root)
            var res = gson.fromJson(json, cls)
            scanObject(res)
            setCloned(res)

            if(obj is Selector && (obj.base != null && innerClass)) {
                val f = getFieldFromObject(obj.base!!, obj)!!
                res = f.get(res)
            }

            return res
        }

        fun getRootObject(sel: Selector): Selector {
            var res = sel

            do {
                if (res.base != null) {
                    res = res.base!!
                }
            }
            while(res.base != null)

            return res
        }

        fun getFieldFromObject(source: Selector, search: Selector): Field? {
            var cls = if(isObject(source.javaClass)) {
                source.javaClass.superclass
            } else {
                source.javaClass
            }
            for (f in cls.declaredFields) {
                f.isAccessible = true
                if (f.get(source) === search) {
                    return f
                }
            }

            return null
        }

        fun isNestedClassOf(sourceClass: KClass<*>, innerClass: KClass<*>): Boolean {
            val sname = sourceClass.qualifiedName!!
            val iname = innerClass.qualifiedName!!

            if (sname.endsWith(".Selector")) {
                return false
            }

            return iname.startsWith(sname) && sname.length < iname.length
        }

        fun setCloned(obj: Any): Any {
            for (f in obj.javaClass.declaredFields) {
                println("checking $f")
                f.isAccessible = true
                val o = f.get(obj)

                if (o !== obj && o != null) {
                    if (o is Selector) {
                        println("Cloned for: $f" )
                        o.cloned()
                    }

                    if (isNestedClassOf(obj::class, o::class)) {
                        println("!!!!call setCLoned for: $o")
                        setCloned(o)
                    }
                } else {
                    println("ignored")
                }

            }

            if(obj is Selector) {
                println("Cloned for: $obj" )
                obj.cloned()
            }

            return obj
        }

        fun<T> deepClone(obj: Any): T {
            return getNewRootObject(obj) as T
        }
    }
}

private fun Selector.cloned() {
    state = SelectorState.CLONED
}
