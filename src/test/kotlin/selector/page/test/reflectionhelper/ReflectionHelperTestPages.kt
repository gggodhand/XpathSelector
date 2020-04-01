package selector.page.test.reflectionhelper

import selector.Selector
import selector.SelectorFactoryHelper
import selector.SelectorFactoryHelper.Companion.tag
import selector.prefix
import selector.tag

open class PageTestCls: Selector(SelectorFactoryHelper.tag("b").prefix("/")) {
    var s1: Selector = SelectorFactoryHelper.tag("a").prefix("/")
    var pageName = "PageTest"

    class ItemCls: Selector(tag = "Item") {
        var s2: Selector = SelectorFactoryHelper.tag("s2")
    } val Item = ItemCls()

    class MenuCls: Selector(tag = "Menu") {
        var itemMenu2: Selector = SelectorFactoryHelper.tag("mmm")

         inner class MenuItemCls: Selector(tag = "MenuItem") {
            var itemMenu: Selector = SelectorFactoryHelper.tag("mmm5")

            fun dos() {

            }
        } val MenuItem = MenuItemCls()
    } val Menu = MenuCls()

    class Menu2Cls {
        var s3: Selector = SelectorFactoryHelper.tag("aaa")
    } val Menu2 = Menu2Cls()
}

object PageTest: PageTestCls()

object PageTest2 {
    var s1: Selector = SelectorFactoryHelper.tag("s1")
}