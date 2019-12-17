import java.lang.reflect.Field

fun Selector.setTag(tag: String) = getField("tag").set(this, tag)
fun Selector.getTag() = getField("tag").get(this) as String

fun Selector.getField(fieldName: String): Field {
    var f = this.javaClass.getDeclaredField(fieldName)
    f.isAccessible = true
    return f
}
