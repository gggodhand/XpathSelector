package selector

open class XpathSelector(var xpath: String = ""): Selector() {

    override fun toXpath(addAttr: Boolean): String {
        return "$xpath${attributes.build()}"
    }
}