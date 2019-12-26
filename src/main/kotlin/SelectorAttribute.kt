open class SelectorAttribute(
    var strVal: String = "",
    var sel: Selector? = null,
    var oper: String = "and"
) {

    open fun toXpath(): String {
        return if (sel != null) sel!!.toXpath() else strVal
    }

    open fun shouldBeReplacedBy(attr: SelectorAttribute): Boolean {
       return false
    }
}