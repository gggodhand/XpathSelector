package selector

import org.apache.commons.beanutils.BeanUtils
import selector.SelectorFactoryHelper.Companion.s_tag
import selector.attributes.SelectorAttributeChain

open class GroupSelector(
    var selectors: ArrayList<Selector> = ArrayList()): Selector() {

    private var firstAttributes = SelectorAttributeChain()

    constructor(sel: Selector?) : this() {
        if(sel != null) {
            BeanUtils.copyProperties(this, sel)
            copyied = true

            firstAttributes = if(sel is GroupSelector) {
                sel.firstAttributes
            } else {
                sel.attributes.clone()
            }

            attributes = SelectorAttributeChain()
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
        var res = super.toXpath(false) + firstAttributes.build()

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

operator fun Selector.plus(selector: Selector): GroupSelector {
    var s = selector
    if (this is GroupSelector) {
        return addChild(s)
    }

    return GroupSelector(this).addChild(s)
}

operator fun Selector.times(selector: Selector): GroupSelector {
    var s = selector
    if (this is GroupSelector) {
        return addDescedant(selector)
    } else if (selector is GroupSelector) {
        s = selector.prefix("//")
    }

    return GroupSelector(this).addDescedant(s)
}

fun Selector.parentTag(tag: String, count: Int): GroupSelector {
    val p = s_tag(tag)
    var res = this

    for (i in 1..count) {
        res += p
    }

    return res as GroupSelector
}