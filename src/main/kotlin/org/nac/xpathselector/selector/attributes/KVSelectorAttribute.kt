package org.nac.xpathselector.selector.attributes

class KVSelectorAttribute(
    var key: String = "",
    var value: String = ""): SelectorAttribute() {

    companion object {
        fun Position(pos: Int): KVSelectorAttribute {
            return KVSelectorAttribute("position()", pos.toString())
        }

        fun Position(pos: String): KVSelectorAttribute {
            return KVSelectorAttribute("position()", pos)
        }

        fun KV(key: String, value: String): KVSelectorAttribute {
            return KVSelectorAttribute(key, value)
        }

        fun Arg(key: String, value: String): KVSelectorAttribute {
            return KVSelectorAttribute(
                "@$key",
                "'${escape(value)}'"
            )
        }
    }

    override fun build(): String {
        return "$key=$value"
    }
}