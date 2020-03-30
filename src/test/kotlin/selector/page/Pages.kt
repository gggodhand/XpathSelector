package selector.page

import selector.Selector
import selector.SelectorFactoryHelper.Companion.tag
import selector.prefix
import selector.tag

open class PageTestCls: Selector(tag("b").prefix("/")) {
    var s1: Selector = tag("a")

    inner class ItemCls: Selector(tag = "Item") {
        var s2 = tag("s2")
    } val Item = ItemCls()

    inner class MenuCls: Selector(tag = "Menu") {
        var itemMenu = tag("mmm")
    } val Menu = MenuCls()
}

object PageTest: PageTestCls()


object PageTest2 {
    var s1: Selector = tag("a")

    object Item: Selector(tag = "aI") {
        val ss = tag("ss")
    }
}