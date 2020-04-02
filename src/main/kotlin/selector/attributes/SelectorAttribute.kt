package selector.attributes

import selector.Selector
import selector.clone
import selector.prefix

open class SelectorAttribute(
    var strVal: String = "",
    var sel: Selector? = null,
    var oper: String = "and"
) {
    companion object {
        fun ArgC(key: String, value: String): SelectorAttribute {
            return SelectorAttribute(
                "contains(@$key, '${escape(
                    value
                )}')"
            )
        }

        fun escape(value: String): String {
            if(value.contains("'")) {
                return "concat('${value.replace("'", "',\"'\",'")}')"
            }
            return value
        }
    }

    fun toXpath(): String {
        val res = build()

        return if (oper.isNotEmpty())
            "$oper $res" else "$res"
    }

    open fun shouldBeReplacedBy(attr: SelectorAttribute): Boolean {
       return false
    }

    open fun build(): String {
       return when {
            strVal.isNotEmpty() -> strVal
            sel != null -> {
                val sel = sel!!
                if (sel.prefix != "-") {
                    sel.prefix("").toXpath()
                } else {
                    sel.prefix(".//").toXpath()
                }
            }
            else -> ""
        }
    }

    infix fun and(right: SelectorAttribute): SelectorAttributeChain {
        var res = SelectorAttributeChain()
        res.add(this)
        res.add(right)
        return res
    }

    infix fun or(right: SelectorAttribute): SelectorAttributeChain {
        var res = SelectorAttributeChain()
        res.add(this)
        right.oper = "or"
        res.add(right)
        return res
    }

    infix fun and(right: Selector): SelectorAttributeChain {
        var res = SelectorAttributeChain()
        res.add(this)
        res.add(SelectorAttribute(sel = right.clone()))
        return res
    }

    infix fun or(right: Selector): SelectorAttributeChain {
        var res = SelectorAttributeChain()
        res.add(this)
        val attr = SelectorAttribute(sel = right.clone())
        attr.oper = "or"
        res.add(attr)
        return res
    }
}