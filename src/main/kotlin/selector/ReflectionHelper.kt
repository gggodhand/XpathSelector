package selector

import com.sun.org.apache.xpath.internal.operations.Bool
import net.sf.corn.cps.CPScanner
import net.sf.corn.cps.PackageNameFilter
import java.lang.reflect.Field

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
                scanObject(clazz.getField("INSTANCE").get(null))
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

        fun getInnerClassSelectors(obj: Any): ArrayList<Selector> {
            var res = ArrayList<Selector>()

            val fields = getFieldsFromObj(obj)

            for (f in fields) {
                f.isAccessible = true
                val member = f.get(obj)
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

        fun scanObject(obj: Any) {

            if (obj is Selector) {
                for (s in getClassSelectors(obj)) {
                    s.setBase(obj)
                }

                for (s in getInnerClassSelectors(obj)) {
                    scanObject(s)
                }
            }

        }
    }
}