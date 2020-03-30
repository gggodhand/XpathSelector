package selector


class GroupSelector(
    var selectors: ArrayList<Selector> = ArrayList()): Selector() {

    constructor(selector: Selector) : this() {
        selectors.add(selector)
    }

    override fun clone(): GroupSelector {
        return copyTo(GroupSelector())
    }

    fun copyTo(sel: GroupSelector): GroupSelector {
        var res = (sel as Selector).copyTo(sel) as GroupSelector

        var newSelectors = ArrayList<Selector>()
        newSelectors.addAll(selectors)
        res.selectors = newSelectors

        return res
    }

    fun addChild(selector: Selector) = add(selector, "/")
    fun addDescedant(selector: Selector) = add(selector, "//")

    private fun add(selector: Selector, prefix: String): GroupSelector {
        var res = clone()

        res.selectors.add(selector.prefix(prefix))

        return res
    }

    override fun toXpath(): String {
        var res = ""

        selectors.forEach {
            res += it.toXpath()
        }

        return res
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is GroupSelector) return false
        if (!super.equals(other)) return false

        if (selectors != other.selectors) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + selectors.hashCode()
        return result
    }
}

fun <T: GroupSelector>T.prefix(value: String): T {
    var res = clone()

    if (res.selectors.size > 0) {
        res.selectors[0] = res.selectors[0].prefix(value)
    }

    return res as T
}