package selector

class ComposeSelector(
    var selectors: ArrayList<Selector> = ArrayList()): Selector() {

    constructor(selector: Selector) : this() {
        selectors.add(selector)
    }

    override fun clone(): ComposeSelector {
        return copyTo(ComposeSelector())
    }

    fun copyTo(sel: ComposeSelector): ComposeSelector {
        var res = super.copyTo(sel) as ComposeSelector

        var newSelectors = ArrayList<Selector>()
        newSelectors.addAll(selectors)
        res.selectors = newSelectors

        return res
    }

    fun add(selector: Selector): ComposeSelector {
        var res = clone()

        res.selectors.add(selector)

        return res
    }

    override fun toXpath(): String {
        var res = ""

        selectors.forEach {
            res += "(${it.toXpath()}) | "
        }

        return res.removeSuffix(" | ")
    }
}

