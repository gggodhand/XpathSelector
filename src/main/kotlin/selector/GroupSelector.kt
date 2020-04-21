package selector

import org.apache.commons.beanutils.BeanUtils

open class GroupSelector(
    var selectors: ArrayList<Selector> = ArrayList()): Selector() {

    constructor(sel: Selector?) : this() {
        if(sel != null) {
            BeanUtils.copyProperties(this, sel)
            copyied = true
        }
    }

    fun addChild(selector: Selector) = add(selector, "/")
    fun addDescedant(selector: Selector) = add(selector, "//")

    private fun add(selector: Selector, prefix: String): GroupSelector {
        var res = clone()

        //polymorphism for extension functions require 'hard cast'
        if (selector is GroupSelector) {
            res.selectors.add(selector.prefix(prefix))
        } else {
            res.selectors.add(selector.prefix(prefix))
        }

        return res
    }

    override fun toXpath(addAttr:Boolean): String {
        var res = super.toXpath(false)

        selectors.forEach {
            res += it.toXpath()
        }

        if (attributes.isNotEmpty) {
            return "($res)${attributes.build()}"
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

 /*   override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + selectors.hashCode()
        return result
    } */
}