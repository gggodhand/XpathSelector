package org.nac.xpathselector.selector


import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.nac.xpathselector.selector.page.test.reflectionhelper.*
import org.nac.xpathselector.util.ReflectionHelper

class ReflectionHelperObjectTests {

    @Test
    @Disabled
    fun check_getClassSelectorsForObject() {
        assertEquals(
            arrayListOf(
                PageTest_WithoutBase_WithoutInnerClasses.s1,
                PageTest_WithoutBase_WithoutInnerClasses.s2),
            ReflectionHelper.getClassSelectors(PageTest_WithoutBase_WithoutInnerClasses))
    }

    @Test
    @Disabled
    fun check_PageTest_WithoutBase_WithoutInnerClasses() {

        assertAll(
            "Check Default state",
            Executable {
                assertEquals("", PageTest_WithoutBase_WithoutInnerClasses.s1.name, "s1.name")
            },
            Executable {
                assertEquals("", PageTest_WithoutBase_WithoutInnerClasses.s2.name, "s2.name")
            },
            Executable {
                assertEquals(SelectorState.INIT, PageTest_WithoutBase_WithoutInnerClasses.s1.state, "s1.state")
            },
            Executable {
                assertEquals(SelectorState.INIT, PageTest_WithoutBase_WithoutInnerClasses.s2.state, "s2.state")
            }
        )

        ReflectionHelper.scanObject(PageTest_WithoutBase_WithoutInnerClasses)

        assertAll(
            "State after scan",
            Executable {
                assertEquals("PageTest_WithoutBase_WithoutInnerClasses.s1", PageTest_WithoutBase_WithoutInnerClasses.s1.name, "s1.name")
            },
            Executable {
                assertEquals("PageTest_WithoutBase_WithoutInnerClasses.s2", PageTest_WithoutBase_WithoutInnerClasses.s2.name, "s2.name")
            },

            Executable {
                assertEquals(SelectorState.FREEZE, PageTest_WithoutBase_WithoutInnerClasses.s1.state, "s1.state")
            },
            Executable {
                assertEquals(SelectorState.FREEZE, PageTest_WithoutBase_WithoutInnerClasses.s2.state, "s2.state")
            },

            Executable {
                assertEquals("//s1", PageTest_WithoutBase_WithoutInnerClasses.s1.toXpath(), "s1.xpath")
            },
            Executable {
                assertEquals("//s2", PageTest_WithoutBase_WithoutInnerClasses.s2.toXpath(), "s2.xpath")
            }
        )
    }

    @Test
    @Disabled
    fun check_PositionForSelectorWithoutBase() {
        ReflectionHelper.scanObject(PageTest_WithoutBase_WithInnerClasses)
        assertEquals("//s1[position()=2]", PageTest_WithoutBase_WithInnerClasses.s1[2].toXpath(), "after s1[2].xpath  1")
        assertEquals("//s1[position()=2]", PageTest_WithoutBase_WithInnerClasses.s1[2].toXpath(), "after s1[2].xpath  2")
    }

    @Test
    fun check_PositionForSelectorWithoutBase1() {
        ReflectionHelper.scanObject(PageTestFromC)
        val s1 = PageTestFromC.Item.s2[1]
        val s2 = PageTestFromC.Item.s2[2]

        assertFalse(s1 === s2)
    }

    @Test
    fun check_PositionForSelectorWithoutBase2() {
        ReflectionHelper.scanObject(PageTest)
        val s1 = PageTest.Item.s2[1]
        val s2 = PageTest.Item.s2[2]

        assertFalse(s1 === s2)
    }

    @Disabled
    @Test
    fun check_PositionForSelectorWithoutBase3() {
        ReflectionHelper.scanObject(PageTest_WithoutBase_WithoutInnerClasses)
        val s1 = PageTest_WithoutBase_WithoutInnerClasses.s2[1]
        val s2 = PageTest_WithoutBase_WithoutInnerClasses.s2[2]

        assertFalse(s1 === s2)
    }

    @Test
    fun check_PositionForSelectorWithoutBase4() {
        val o = PageTestCls()
        ReflectionHelper.scanObject(o)
        val s1 = o.s1[1]
        val s2 = o.s1[2]

        assertFalse(s1 === s2)
    }

    @Test
    @Disabled
    fun check_PageTest_WithoutBase_WithInnerClasses() {

        assertAll(
            "Check Default state",
            Executable {
                assertEquals("", PageTest_WithoutBase_WithInnerClasses.s1.name, "s1.name")
            },
            Executable {
                assertEquals("", PageTest_WithoutBase_WithInnerClasses.s2.name, "s2.name")
            },
            Executable {
                assertEquals(SelectorState.INIT, PageTest_WithoutBase_WithInnerClasses.s1.state, "s1.state")
            },
            Executable {
                assertEquals(SelectorState.INIT, PageTest_WithoutBase_WithInnerClasses.s2.state, "s2.state")
            }
        )

        ReflectionHelper.scanObject(PageTest_WithoutBase_WithInnerClasses)

        assertAll(
            "State after scan",
            Executable {
                assertEquals("PageTest_WithoutBase_WithInnerClasses.s1", PageTest_WithoutBase_WithInnerClasses.s1.name, "after s1.name")
            },
            Executable {
                assertEquals("PageTest_WithoutBase_WithInnerClasses.s2", PageTest_WithoutBase_WithInnerClasses.s2.name, "after s2.name")
            },

            Executable {
                assertEquals(SelectorState.FREEZE, PageTest_WithoutBase_WithInnerClasses.s1.state, "after s1.state")
            },
            Executable {
                assertEquals(SelectorState.FREEZE, PageTest_WithoutBase_WithInnerClasses.s2.state, "after s2.state")
            },

            Executable {
                assertEquals(SelectorState.FREEZE, PageTest_WithoutBase_WithInnerClasses.Item.item_s1.state, "after Item.item_s")
            },
            Executable {
                assertEquals(SelectorState.FREEZE, PageTest_WithoutBase_WithInnerClasses.ItemWithBase.item_b1.state, "afte ItemWithBase.item_b1")
            },
            Executable {
                assertEquals(SelectorState.FREEZE, PageTest_WithoutBase_WithInnerClasses.ItemWithBase.ItemCls_WithBase.item_b2.state, "after ItemWithBase.ItemCls_WithBase.item_b2")
            },

            Executable {
                assertEquals("//s1", PageTest_WithoutBase_WithInnerClasses.s1.toXpath(), "s1.xpath")
            },
            Executable {
                assertEquals("//s2", PageTest_WithoutBase_WithInnerClasses.s2.toXpath(), "s2.xpath")
            },
            Executable {
                assertEquals("//item_s1", PageTest_WithoutBase_WithInnerClasses.Item.item_s1.toXpath(), "Item.item_s1.state.xpath")
            },
            Executable {
                assertEquals("//ItemBase//item_s1", PageTest_WithoutBase_WithInnerClasses.ItemWithBase.item_b1.toXpath(), "ItemWithBase.item_b1.xpath")
            },
            Executable {
                assertEquals("//ItemBase//Base2//item_s2", PageTest_WithoutBase_WithInnerClasses.ItemWithBase.ItemCls_WithBase.item_b2.toXpath(), "ItemWithBase.ItemCls_WithBase.item_b2.xpath")
            },

            Executable {
                assertEquals("//s1[position()=2]", PageTest_WithoutBase_WithInnerClasses.s1[2].toXpath(), "s1[2].xpath")
            },
            Executable {
                assertEquals("//s2[position()=2]", PageTest_WithoutBase_WithInnerClasses.s2[2].toXpath(), "s2[2].xpath")
            },
            Executable {
                assertEquals("]", PageTest_WithoutBase_WithInnerClasses.Item.item_s1[2].toXpath(), "Item.item_s1.state[2].xpath")
            },
            Executable {
                assertEquals("//ItemBase[position()=2]//item_s1[position()=2]", PageTest_WithoutBase_WithInnerClasses.ItemWithBase[2].item_b1[2].toXpath(), "ItemWithBase[2].item_b1.xpath")
            },
            Executable {
                assertEquals("//ItemBase[position()=2]//Base2[position()=2]//item_s2[position()=2]", PageTest_WithoutBase_WithInnerClasses.ItemWithBase[2].ItemCls_WithBase[3].item_b2[4].toXpath(), "ItemWithBase[2].ItemCls_WithBase.item_b2.xpath")
            }
        )
    }
}