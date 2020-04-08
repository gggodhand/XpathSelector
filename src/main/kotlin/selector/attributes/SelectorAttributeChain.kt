package selector.attributes

import selector.Selector

class SelectorAttributeChain(
    private var attributes: ArrayList<SelectorAttribute> = ArrayList()
) {

    fun clone(): SelectorAttributeChain {
        var newAttributes = ArrayList<SelectorAttribute>()
        newAttributes.addAll(attributes)
        return SelectorAttributeChain(newAttributes)
    }

    fun add(attr: SelectorAttribute) {
        attributes.add(attr)
    }

    fun build(): String {
        if (attributes.isNotEmpty()) {
            attributes.first().oper = ""

            var res = ""
            attributes.forEach {
                res += it.toXpath()
                if (it !== attributes.last()) {
                    res += " "
                }
            }

            return "[$res]"
        }

        return ""
    }

    infix fun and(right: SelectorAttribute): SelectorAttributeChain {
        this.add(right)
        return this
    }

    infix fun or(right: SelectorAttribute): SelectorAttributeChain {
        right.oper = "or"
        this.add(right)
        return this
    }

    infix fun and(right: Selector): SelectorAttributeChain {
        var attr = SelectorAttribute(sel = right)
        this.add(attr)
        return this
    }

    infix fun or(right: Selector): SelectorAttributeChain {
        var attr = SelectorAttribute(sel = right)
        attr.oper = "or"
        this.add(attr)
        return this
    }
}
