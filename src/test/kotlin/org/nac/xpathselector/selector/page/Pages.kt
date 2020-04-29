package org.nac.xpathselector.selector.page

import org.nac.xpathselector.selector.Selector
import org.nac.xpathselector.selector.SelectorFactoryHelper.Companion.s_tag
import org.nac.xpathselector.selector.prefix
import org.nac.xpathselector.selector.tag

open class PageTestCls: Selector(s_tag("b").prefix("/")) {
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
    var s1: Selector = s_tag("a")

    object Item: Selector(tag = "aI") {
        val ss = tag("ss")
    }
}