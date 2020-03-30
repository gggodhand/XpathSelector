package selector

open class Selector(
    internal var base: Selector? = null,

    internal var name: String = "",
    internal var prefix: String = "//",
    internal var tag: String = "*",
    internal var axe: String = "",
    internal var parent: Int = 0,

     var attributes: SelectorAttributeChain = SelectorAttributeChain()
) {
    constructor(sel: Selector) : this() {
        sel.copyTo(this)
    }

    open fun clone(): Selector {
        return copyTo(Selector())
    }

    open fun toXpath(): String {
        var p = ""
        if (parent > 0) {
            for (i in 1..parent) {
                p += "/.."
            }
        }

        val baseXpath = base?.toXpath() ?: ""
        return "$baseXpath$prefix$axe$tag$p${attributes.build()}"
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

fun <T: Selector>T.setBase(value: Selector): T {
    this.base = value
    return this
}

fun <T: Selector>T.copyTo(sel: T): T {
    sel.prefix = prefix
    sel.tag = tag
    sel.parent = parent
    sel.attributes = attributes.clone()

    return sel
}

fun <T: Selector>T.prefix(value: String): T {
    val res = clone()
    res.prefix = value
    return res as T
}

operator fun <T: Selector>T.unaryMinus(): T {
    val res = clone()
    res.prefix = "-"
    return res as T
}

fun <T: Selector>T.tag(value: String): T {
    val res = clone()
    res.tag = value
    return res as T
}

fun <T: Selector>T.parent(value: Int): T {
    val res = clone()
    res.parent = value
    return res as T
}

fun <T: Selector>T.following(): T {
    val res = clone()
    res.axe = "following::"
    return res as T
}

fun <T: Selector>T.followingSibling(): T {
    val res = clone()
    res.axe = "following-sibling::"
    return res as T
}

fun <T: Selector>T.preceding(): T {
    val res = clone()
    res.axe = "preceding::"
    return res as T
}

fun <T: Selector>T.precedingSibling(): T {
    val res = clone()
    res.axe = "preceding-sibling::"
    return res as T
}