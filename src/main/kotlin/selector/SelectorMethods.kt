package selector

import selector.SelectorFactoryHelper.Companion.s_tag
import selector.attributes.KVSelectorAttribute
import selector.attributes.SelectorAttribute



object SelectorMethodsHelper {
    var prevComposeSelector: ComposeSelector? = null
}

operator fun Selector.div(selector: Selector): ComposeSelector {
    if (this is ComposeSelector) {
        if (this === SelectorMethodsHelper.prevComposeSelector) {
            SelectorMethodsHelper.prevComposeSelector = this

            return this.add(selector)
        }
    }

    SelectorMethodsHelper.prevComposeSelector = ComposeSelector(this).add(selector)
    return SelectorMethodsHelper.prevComposeSelector!!
}

operator fun <T: Selector>T.get(position: Int): T {
    var res = clone()
    res.attributes.add(KVSelectorAttribute.Position(position))
    return res
}

operator fun <T: Selector>T.get(position: String): T {
    val res = clone()
    res.attributes.add(KVSelectorAttribute.Position(position))
    return res
}

operator fun <T: Selector>T.get(arg: SelectorAttribute): T {
    val res = clone()
    res.attributes.add(arg)
    return res
}

operator fun <T: Selector>T.get(selector: Selector): T {
    val res = clone()
    res.attributes.add(SelectorAttribute(sel = selector))
    return res
}

