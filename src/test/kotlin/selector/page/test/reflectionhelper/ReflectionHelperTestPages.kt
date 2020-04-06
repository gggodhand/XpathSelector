package selector.page.test.reflectionhelper

import page.Block
import page.Page
import selector.Selector
import selector.SelectorFactoryHelper
import selector.SelectorFactoryHelper.Companion.s_tag as T
import selector.prefix


open class PageTestCls: Page("PageTest_name", T("b").prefix("/")) {
    var s1: Selector = T("a").prefix("/")

    class ItemCls: Block(Selector(tag = "Item")) {
        var s2: Selector = T("s2")
    } val Item = ItemCls()

    class MenuCls: Block(Selector(tag = "Menu")) {
        var itemMenu2: Selector = T("mmm")

         inner class MenuItemCls: Block(Selector(tag = "MenuItem")) {
            var itemMenu: Selector = T("mmm5")

            fun dos() {

            }
        } val MenuItem = MenuItemCls()
    } val Menu = MenuCls()

    //this class is not 'Block'
    class Menu2Cls {
        var s3: Selector = T("aaa")
    } val Menu2 = Menu2Cls()
}

object PageTest: PageTestCls()

val PageTestFromC = PageTestCls()

object PageTest_WithoutBase_WithoutInnerClasses: Page("PageTest_WithoutBase_WithoutInnerClasses_name") {
    var s1 = T("s1")
    var s2 = T("s2")
}

object PageTest_WithoutBase_WithInnerClasses: Page("PageTest_WithoutBase_WithInnerClasses_name") {
    val s1 = T("s1")
    val s2 = T("s2")

    class ItemCls: Block() {
        val item_s1 = T("item_s1")
    } val Item = ItemCls()

    class ItemCls_WithBase: Block(T("ItemBase")) {
        val item_b1 = T("item_s1")

        class ItemCls_WithBaseCls: Block(T("Base2")) {
            val item_b2 = T("item_s2")
        } val  ItemCls_WithBase = ItemCls_WithBaseCls()
    } val ItemWithBase = ItemCls_WithBase()
}

object PageTest_WithoutBase_WithInnerObjects: Page("PageTest_WithoutBase_WithInnerClasses_name") {
    val s1 = T("s1")
    val s2 = T("s2")

    object Item: Block() {
        val item_s1 = T("item_s1")
    }

    object Item_Inner: Block(T("Item_Inner_T")) {
        val item_s1 = T("item_s1")

        object InnerO: Block(T("Item_Inner_O")) {
            val item_s2 = T("item_s2")
        }
    }
}

/////////


object PageTest_WithBase_WithoutInnerClasses: Page("PageTest_WithoutBase_WithoutInnerClasses_name", T("BaseP")) {
    var s1 = T("s1")
    var s2 = T("s2")
}

object PageTest_WithBase_WithInnerClasses: Page("PageTest_WithoutBase_WithInnerClasses_name", T("BaseP")) {
    val s1 = T("s1")
    val s2 = T("s2")

    class ItemCls: Block() {
        val item_s1 = T("item_s1")
    } val Item = ItemCls()

    class ItemCls_WithBase: Block(T("ItemBase")) {
        val item_b1 = T("item_s1")

        class ItemCls_WithBaseCls: Block(T("Base2")) {
            val item_b2 = T("item_s2")
        } val  ItemCls_WithBase = ItemCls_WithBaseCls()
    } val ItemWithBase = ItemCls()
}

object PageTest_WithBase_WithInnerObjects: Page("PageTest_WithoutBase_WithInnerClasses_name", T("BaseP")) {
    val s1 = T("s1")
    val s2 = T("s2")

    object Item: Block() {
        val item_s1 = T("item_s1")
    }

    object Item_Inner: Block(T("Item_Inner_T")) {
        val item_s1 = T("item_s1")

        object InnerO: Block(T("Item_Inner_O")) {
            val item_s2 = T("item_s2")
        }
    }
}