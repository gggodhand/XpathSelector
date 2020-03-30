package selector

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import selector.page.PageTest
import selector.page.test.reflectionhelper.PageTestCls

internal class ReflectionHelperTest: BaseSelectorTest() {

    @Test
    fun `isObject should return true for objects`() {
        assertTrue(ReflectionHelper.isObject(selector.page.PageTest::class.java))
    }

    @Test
    fun `isObject should return false for classes`() {
        assertFalse(ReflectionHelper.isObject(selector.page.PageTestCls::class.java))
    }

    @Test
    fun `getObjects should return list of objects`() {
        assertEquals(
            ReflectionHelper.getObjects("selector.page.test.reflectionhelper"),
            arrayListOf(
                selector.page.test.reflectionhelper.PageTest::class.java,
                selector.page.test.reflectionhelper.PageTest2::class.java)
        )
    }

    @Test
    fun `getClassSelectors should return a list of selector members for the base object`() {
        assertEquals(
            ReflectionHelper.getClassSelectors(selector.page.test.reflectionhelper.PageTest),
            arrayListOf(
                selector.page.test.reflectionhelper.PageTest.s1,
                selector.page.test.reflectionhelper.PageTest.Item,
                selector.page.test.reflectionhelper.PageTest.Menu
            )
        )
    }

    @Test
    fun `getInnerClassSelectors should return a list of inner selector members for the base object`() {
        assertEquals(
            ReflectionHelper.getInnerClassSelectors(selector.page.test.reflectionhelper.PageTest),
            arrayListOf(
                selector.page.test.reflectionhelper.PageTest.Item,
                selector.page.test.reflectionhelper.PageTest.Menu
            )
        )
    }

    @Test
    fun `getClassSelectors should return a list of selector members for inner object`() {
        assertEquals(
            arrayListOf(
                selector.page.test.reflectionhelper.PageTest.Item.s2
            ),
            ReflectionHelper.getClassSelectors(
                selector.page.test.reflectionhelper.PageTest.Item)
        )
    }

    @Test
    fun `getClassInnerSelectors should return an empty list for the inner class`() {
        assertEquals(
            0,
            ReflectionHelper.getInnerClassSelectors(
                selector.page.test.reflectionhelper.PageTest.Item).size
        )
    }

    @Test
    fun `scanObject should init base of members`() {
     //   assertNull(selector.page.test.reflectionhelper.PageTest.s1.base)

        ReflectionHelper.scanObject(selector.page.test.reflectionhelper.PageTest)

        assertEquals(selector.page.test.reflectionhelper.PageTest,
            selector.page.test.reflectionhelper.PageTest.s1.base)

        checkThat(selector.page.test.reflectionhelper.PageTest.s1, "/b/a")
    }

    @Test
    fun `scanObject should init base of members and inner classes`() {

        ReflectionHelper.scanObject(selector.page.test.reflectionhelper.PageTest)


        checkThat(selector.page.test.reflectionhelper.PageTest.Item.s2, "/b//Item//s2")
    }

    @Test
    fun `scanObject should init base of members and inner classes 2`() {

        ReflectionHelper.scanObject(selector.page.test.reflectionhelper.PageTest)


        checkThat(selector.page.test.reflectionhelper.PageTest.Menu.MenuItem.itemMenu, "/b//Item//s2")
    }


    @Test
    fun `scanObject should init base of members and inner classes 3`() {

        ReflectionHelper.scanObject(selector.page.test.reflectionhelper.PageTest)


        selector.page.test.reflectionhelper.PageTest().Menu

        val s: Selector = selector.page.test.reflectionhelper.PageTest.Menu[2]


        checkThat(selector.page.test.reflectionhelper.PageTest.Menu[2].MenuItem.itemMenu, "/b//Item//s2")
    }
}