
class Selector(
    private var prefix: String = "//",
    private var tag: String = "*") {

    fun prefix(value: String): Selector {
        val res = clone()
        res.prefix = value
        return res
    }

    fun tag(value: String): Selector {
        val res = clone()
        res.tag = value
        return res
    }

    fun toXpath(): String {
        return "$prefix$tag"
    }

    fun clone(): Selector {
        return Selector(prefix, tag)
    }
}