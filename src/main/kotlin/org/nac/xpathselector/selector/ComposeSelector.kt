package org.nac.xpathselector.selector

class ComposeSelector(
    var selectors: ArrayList<Selector> = ArrayList()): Selector() {

    constructor(selector: Selector) : this() {
        selectors.add(selector)
    }

    fun add(selector: Selector): ComposeSelector {
        var res = clone()

        res.selectors.add(selector)

        return res
    }

    override fun toXpath(addAttr:Boolean): String {
        var res = ""

        selectors.forEach {
            res += "(${it.toXpath()}) | "
        }

        return res.removeSuffix(" | ")
    }
}

