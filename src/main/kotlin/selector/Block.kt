package selector

class Block: Selector() {
    lateinit var selector: Selector

    override fun toXpath(): String {
        return selector.toXpath()
    }

    override fun clone(): Block {
        return copyTo(Block())
    }
}

inline operator fun <reified T: Block>T.get(position: Int): T {
//    val res: T = getValue()!!
//    copyTo(res)
    val s = selector.clone()
    s.attributes.add(KVSelectorAttribute.Position(position))
    return this
}