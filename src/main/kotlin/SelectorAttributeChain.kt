class SelectorAttributeChain(
    var attributes: ArrayList<SelectorAttribute> = ArrayList()
) {

    fun clone(): SelectorAttributeChain {
        var newAttributes = ArrayList<SelectorAttribute>()
        newAttributes.addAll(attributes)
        return SelectorAttributeChain(newAttributes)
    }

    fun add(attr: SelectorAttribute) {
        attributes.add(attr)
    }
}