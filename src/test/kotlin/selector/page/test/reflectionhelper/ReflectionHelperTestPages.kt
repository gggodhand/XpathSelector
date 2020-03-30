package selector.page.test.reflectionhelper

import selector.Selector
import selector.SelectorFactoryHelper.Companion.tag
import selector.prefix
import selector.tag

open class PageTestCls: Selector(tag("b").prefix("/")) {
    var s1: Selector = tag("a")
    var pageName = "PageTest"

    class ItemCls: Selector(tag = "Item") {
        var s2: Selector = tag("s2")
    } val Item = ItemCls()

    class MenuCls: Selector(tag = "Menu") {
        var itemMenu2: Selector = tag("mmm")

         inner class MenuItemCls: Selector(tag = "MenuItemCls") {
            var itemMenu: Selector = tag("mmm5")

            fun dos() {

            }
        } val MenuItem = MenuItemCls()
    } val Menu = MenuCls()

    class Menu2Cls {
        var s3: Selector = tag("aaa")
    } val Menu2 = Menu2Cls()
}

fun PageTest(): PageTestCls = PageTestCls()

