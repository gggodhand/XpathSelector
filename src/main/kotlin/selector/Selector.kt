package selector

import org.apache.commons.beanutils.BeanUtils
import page.Block
import selector.attributes.SelectorAttributeChain
import util.ReflectionHelper

enum class SelectorState {
    INIT, FREEZE, CLONED
}

open class Selector(
    var state: SelectorState = SelectorState.INIT,

    internal var base: Block? = null,

    internal var name: String = "",
    internal var prefix: String = "//",
    internal var tag: String = "*",
    internal var axe: String = "",
    internal var parent: Int = 0,

    var attributes: SelectorAttributeChain = SelectorAttributeChain()
) {
    protected var copyied: Boolean = false
    constructor(sel: Selector?) : this() {
        if(sel != null) {
            BeanUtils.copyProperties(this, sel)
            copyied = true
        }
    }

    open fun toXpath(addAttr:Boolean = true): String {
        var p = ""
        if (parent > 0) {
            for (i in 1..parent) {
                p += "/.."
            }
        }

        var baseXpath = ""
        if (base != null && base!!.copyied) {
            baseXpath = base!!.toXpath()
        }

        var res = "$baseXpath$prefix$axe$tag$p"
        if (addAttr) {
            res += attributes.build()
        }
        return res
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Selector) return false

        if (prefix != other.prefix) return false
        if (tag != other.tag) return false

        return true
    }

    override fun hashCode(): Int {
        var result = prefix.hashCode()
        result = 31 * result + tag.hashCode()
        return result
    }

}

fun <T: Selector>T.initWithName(name: String): T {
    this.name = name
    this.state = SelectorState.FREEZE
    return this
}

fun <T: Selector>T.setBase(value: Block): T {
    if (value !== this) {
        this.base = value
    }

    return this
}

fun <T: Selector>T.freeze(): T {
    this.state = SelectorState.FREEZE
    return this
}

fun <T: Selector>T.clone(): T {
    return if (state == SelectorState.FREEZE ) {
        ReflectionHelper.deepClone(this)
    } else {
        this
    }
}

fun <T: Selector>T.prefix(value: String): T {
    val res = clone()
    res.prefix = value
    return res
}

operator fun <T: Selector>T.unaryMinus(): T {
    val res = clone()
    res.prefix = "-"
    return res
}

fun <T: Selector>T.tag(value: String): T {
    val res = clone()
    res.tag = value
    return res
}

fun <T: Selector>T.parent(value: Int): T {
    val res = clone()
    res.parent = value
    return res
}

fun <T: Selector>T.following(): T {
    val res = clone()
    res.axe = "following::"
    return res
}

fun <T: Selector>T.followingSibling(): T {
    val res = clone()
    res.axe = "following-sibling::"
    return res
}

fun <T: Selector>T.preceding(): T {
    val res = clone()
    res.axe = "preceding::"
    return res
}

fun <T: Selector>T.precedingSibling(): T {
    val res = clone()
    res.axe = "preceding-sibling::"
    return res
}