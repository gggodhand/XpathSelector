
open class Selector(
    protected var prefix: String = "//",
    protected var tag: String = "*") {

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
        return "$prefix$tag"
    }

    open fun clone(): Selector = copyTo(Selector())

    open fun copyTo(sel: Selector): Selector {
        sel.prefix = prefix
        sel.tag = tag

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