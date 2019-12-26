class KVSelectorAttribute(
    var key: String = "",
    var value: String = ""): SelectorAttribute() {

    override fun toXpath(): String {
        return "$key = $value"
    }
}