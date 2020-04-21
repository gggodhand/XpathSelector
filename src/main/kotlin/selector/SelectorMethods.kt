package selector

import selector.SelectorFactoryHelper.Companion.s_tag
import selector.attributes.KVSelectorAttribute
import selector.attributes.SelectorAttribute

operator fun Selector.plus(selector: Selector): GroupSelector {
    var s = selector
    if (this is GroupSelector) {
        return addChild(s)
    }

    return GroupSelector(this).addChild(s)
}

operator fun Selector.times(selector: Selector): GroupSelector {
    var s = selector
    if (this is GroupSelector) {
        return addDescedant(selector)
    } else if (selector is GroupSelector) {
        s = selector.prefix("//")
    }

    return GroupSelector(this).addDescedant(s)
}

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

fun Selector.parentTag(tag: String, count: Int): GroupSelector {
    val p = s_tag(tag)
    var res = this

    for (i in 1..count) {
        res += p
    }

    return res as GroupSelector
}