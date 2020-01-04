package selector

open class Selector(
    internal var prefix: String = "//",
    internal var tag: String = "*",
    internal var axe: String = "",

    internal var attributes: SelectorAttributeChain = SelectorAttributeChain()
) {

    open fun toXpath(): String {
        return "$prefix$axe$tag${attributes.build()}"
    }

    open fun clone(): Selector = copyTo(Selector())

    open fun copyTo(sel: Selector): Selector {
        sel.prefix = prefix
        sel.tag = tag
        sel.attributes = attributes.clone()

        return sel
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

fun <T: Selector>T.prefix(value: String): T {
    val res = clone()
    res.prefix = value
    return res as T
}

fun <T: Selector>T.tag(value: String): T {
    val res = clone()
    res.tag = value
    return res as T
}

fun <T: Selector>T.addAttr(attr: SelectorAttribute): T {
    val res = clone()
    res.attributes.add(attr)
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