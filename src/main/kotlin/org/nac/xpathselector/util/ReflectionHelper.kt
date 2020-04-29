package org.nac.xpathselector.util


import com.rits.cloning.Cloner
import net.sf.corn.cps.CPScanner
import net.sf.corn.cps.PackageNameFilter
import org.nac.xpathselector.page.Block
import org.nac.xpathselector.selector.Selector
import org.nac.xpathselector.selector.SelectorState
import org.nac.xpathselector.selector.initWithName
import org.nac.xpathselector.selector.setBase
import java.lang.reflect.Field
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName


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
            var fields = obj.javaClass.declaredFields

            if (isObject(obj.javaClass)) {
                val f = obj.javaClass.declaredFields
                if (f.size == 1 && f.first().name == "INSTANCE") {
                    fields = obj.javaClass.superclass.declaredFields
                }
            }

            return fields.filter { it.name != "this$0" && it.name != "INSTANCE" }
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
                if (obj.name.isBlank()) {
                    obj.name = obj::class.simpleName.toString()
                }
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

        val cloner = Cloner()

        fun getNewRootObject(obj: Any): Any {
            var name = obj::class.jvmName

            if(obj is Selector) {
                val root = getRootObject(obj)
                name = root::class.jvmName
            }

            val cls = Class.forName(name)
            val root = getRootObject(obj as Selector)
            root.name = cls.simpleName



            val clone = cloner.deepClone(root)

            var res = cloner.deepClone(root) /// gson.fromJson(json, cls)
            scanObject(res)
            setCloned(res)

            if(obj is Selector && (obj.base != null)) {
                val f = getFieldFromObject(obj.base!!, obj)!!

                val resObj = if (obj.base!!.base != null ) {
                    val base_f = getFieldFromObject(obj.base!!.base!!, obj.base!!)!!
                    base_f.get(res)
                } else {
                    res
                }

                res = f.get(resObj) as Selector?
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
            var fields = source.javaClass.declaredFields

            if (isObject(source.javaClass)) {
                val f = source.javaClass.declaredFields
                if (f.size == 1 && f.first().name == "INSTANCE") {
                    fields = source.javaClass.superclass.declaredFields
                }
            }

            for (f in fields) {
                f.isAccessible = true
                if (f.get(source) === search) {
                    f.isAccessible = true
                    return f
                }
            }

            return null
        }

        fun getBase(source: Selector, search: Selector): Field? {
            var fields = source.javaClass.declaredFields

            if (isObject(source.javaClass)) {
                val f = source.javaClass.declaredFields
                if (f.size == 1 && f.first().name == "INSTANCE") {
                    fields = source.javaClass.superclass.declaredFields
                }
            }

            for (f in fields) {
                f.isAccessible = true
                if (f.get(source) === search) {
                    f.isAccessible = true
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
            val fields = getFieldsFromObj(obj)
            for (f in fields) {
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
