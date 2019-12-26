
open class Selector(
    protected var prefix: String = "//",
    protected var tag: String = "*",
    protected var axe: String = "",
    protected var attributes: SelectorAttributeChain = SelectorAttributeChain()) {

    open fun prefix(value: String): Selector {
        val res = clone()
        res.prefix = value
        return res
    }

    open fun tag(value: String): Selector {
        val res = clone()
        res.tag = value
        return res
    }

    open fun toXpath(): String {
        return "$prefix$axe$tag"
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

    fun addAttr(attr: SelectorAttribute): Selector {
        val res = clone()
        res.attributes.add(attr)
        return res
    }

    fun following(): Selector {
        val res = clone()
        res.axe = "following::"
        return res
    }

    fun followingSibling(): Selector {
        val res = clone()
        res.axe = "following-sibling::"
        return res
    }

    fun preceding(): Selector {
        val res = clone()
        res.axe = "preceding::"
        return res
    }

    fun precedingSibling(): Selector {
        val res = clone()
        res.axe = "preceding-sibling::"
        return res
    }
}