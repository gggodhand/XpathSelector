package selector

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import selector.page.test.reflectionhelper.PageTestCls
import org.junit.jupiter.api.function.Executable
import selector.page.test.reflectionhelper.PageTest

internal class ReflectionHelperTest: BaseSelectorTest() {

    @BeforeEach
    fun before() {
        ReflectionHelper.scanObject(PageTest)
    }

    @Test
    fun `isObject should return true for objects`() {
        assertTrue(ReflectionHelper.isObject(selector.page.PageTest::class.java))
    }

    @Test
    fun `isObject should return false for classes`() {
        assertFalse(ReflectionHelper.isObject(selector.page.PageTestCls::class.java))
    }

    @Test
    fun `getNewRootObject should return new instance of root object for inner class`() {

        assertTrue(
            ReflectionHelper.getNewRootObject(PageTest.Item) is PageTestCls.ItemCls)
    }

    @Test
    fun `getNewRootObject should return new instance of inner object`() {
        val obj = ReflectionHelper.getNewRootObject(PageTest.Item)

        assertAll (
            Executable {
                assertTrue(obj is PageTestCls.ItemCls)
            },

            Executable {
                assertTrue(obj !== PageTest.Item)
            }
        )
    }

    @Test
    fun `getNewRootObject should return new instance of root objec 2t`() {
        val obj = ReflectionHelper.getNewRootObject(PageTest.Menu[2])

        assertAll (
            Executable {
                assertTrue(obj is PageTestCls.MenuCls)
            },

            Executable {
                assertTrue(obj !== PageTest)
            }
        )
    }

    @Test
    fun `setCloned method should set all selectors to the cloned state`() {
        val cloned = PageTest.Item.clone().base as PageTestCls


        assertAll ("cloned members should be CLONED",
            Executable {
                assertTrue(cloned.state == SelectorState.CLONED, "cloned.state")
            },
            Executable {
                assertTrue(cloned.s1.state == SelectorState.CLONED, "cloned.s1.state")
            },
            Executable {
                assertTrue(cloned.Item.state == SelectorState.CLONED, "cloned.Item.state")
            },
            Executable {
                assertTrue(cloned.Item.s2.state == SelectorState.CLONED, "cloned.Item.s2.state")
            },
            Executable {
                assertTrue(cloned.Menu.MenuItem.state == SelectorState.CLONED, "cloned.Menu.MenuItem.state")
            },
            Executable {
                assertTrue(cloned.Menu.MenuItem.itemMenu.state == SelectorState.CLONED, "cloned.Menu.MenuItem.itemMenu.state")
            }
        )
    }

    @Test
    fun `getObjects should return list of objects`() {
        assertEquals(
            ReflectionHelper.getObjects("selector.page.test.reflectionhelper"),
            arrayListOf(
                PageTest::class.java,
                selector.page.test.reflectionhelper.PageTest2::class.java)
        )
    }

    @Test
    fun `getClassSelectors should return a list of selector members for the base object`() {
        assertEquals(
            ReflectionHelper.getClassSelectors(PageTest),
            arrayListOf(
                PageTest.s1,
                PageTest.Item,
                PageTest.Menu
            )
        )
    }

    @Test
    fun `getInnerClassSelectors should return a list of inner selector members for the base object`() {
        assertEquals(
            ReflectionHelper.getInnerClassSelectors(PageTest),
            arrayListOf(
                PageTest.Item,
                PageTest.Menu
            )
        )
    }

    @Test
    fun `getInnerClassSelectors should return null`() {
        assertTrue(
            ReflectionHelper.getInnerClassSelectors
                (PageTest.Menu.MenuItem).isEmpty())
    }


    @Test
    fun `getInnerClassSelectors should return nul l`() {
        assertTrue(
            ReflectionHelper.getInnerClassSelectors
                (PageTest.Item).isEmpty())
    }

    @Test
    fun `getInnerClassSelectors should return 1`() {
        assertTrue(
            ReflectionHelper.getInnerClassSelectors
                (PageTest.Menu).count() == 1)
    }

    @Test
    fun `getClassSelectors should return a list of selector members for inner object`() {
        assertEquals(
            arrayListOf(
                PageTest.Item.s2
            ),
            ReflectionHelper.getClassSelectors(
                PageTest.Item)
        )
    }

    @Test
    fun `getClassInnerSelectors should return an empty list for the inner class`() {
        assertEquals(
            0,
            ReflectionHelper.getInnerClassSelectors(
                PageTest.Item).size
        )
    }

    @Test
    fun `scanObject should init base of members`() {
        ReflectionHelper.scanObject(PageTest)

        assertEquals(
            PageTest,
            PageTest.s1.base)

        checkThat(PageTest.s1, "/b/a")
    }

    @Test
    fun `scanObject should init base of members and inner classes`() {
        checkThat(PageTest.Item.s2, "/b//Item//s2")
    }

    @Test
    fun `scanObject should init base of members and inner classes 2`() {
        ReflectionHelper.scanObject(PageTest)


        checkThat(PageTest.Menu.MenuItem.itemMenu, "/b//Menu//MenuItem//mmm5")
    }

    @Test
    fun `deepClone should create new instance and return inner class`() {
        ReflectionHelper.scanObject(PageTest)
        val res = ReflectionHelper.deepClone<PageTestCls.MenuCls>(PageTest.Menu)
        assertTrue(res.javaClass == PageTestCls.MenuCls::class.java)
    }

    @Test
    fun `scanObject should init base of members and inner classes 3`() {

      //  val s = PageTest.Menu[2]
        ReflectionHelper.scanObject(PageTest)
        val s2 = PageTest.Menu[2]

        checkThat(PageTest.Menu[2].MenuItem.itemMenu, "/b//Item//s2")
    }



}