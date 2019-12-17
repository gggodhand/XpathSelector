operator fun Selector.plus(selector: Selector): GroupSelector {
    if (this is GroupSelector) {
        addChild(selector)
    }

    return GroupSelector(this).addChild(selector)
}

operator fun Selector.times(selector: Selector): GroupSelector {
    if (this is GroupSelector) {
        addDescedant(selector)
    }

    return GroupSelector(this).addDescedant(selector)
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
