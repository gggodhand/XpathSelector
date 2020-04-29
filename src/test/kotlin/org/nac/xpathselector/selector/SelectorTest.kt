package org.nac.xpathselector.selector

import org.nac.xpathselector.selector.SelectorFactoryHelper.Companion.s_tag
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import org.nac.xpathselector.selector.attributes.KVSelectorAttribute
import org.nac.xpathselector.selector.attributes.KVSelectorAttribute.Companion.Arg
import org.nac.xpathselector.selector.attributes.KVSelectorAttribute.Companion.KV

@DisplayName("Selector")
class SelectorTest: BaseSelectorTest() {

    @Test
    fun `Default Selector returns All Match xpath`() {
        checkThat(Selector(), "//*")
    }

    @Test
    fun `clone should copy an object`() {
        var s = s_tag("A").freeze()
        var s2 = s.clone()

        s.setTag("B")

        assertAll (
            Executable { assertEquals("B", s.getTag()) },
            Executable { assertEquals("A", s2.getTag()) }
        )
    }

    @Test
    fun `clone should copy attributes`() {
        val s1 = s_tag("a")[Arg("name", "asd")].freeze()
        val s2 = s1.clone()

        assertEquals(s1.toXpath(), s2.toXpath())
    }


    @Test
    fun `clone should copy group selectors`() {
        val s1 = s_tag("a") + s_tag("B")
        val s2 = s1.freeze().clone()

        assertEquals(s1.toXpath(), s2.toXpath())
    }

    @Test
    fun `parent(0) should not add parents to xpath`() =
        checkThat(Selector().parent(0), "//*")

    @Test
    fun `parent(1) should add parents to xpath`() =
        checkThat(Selector().parent(1), "//*/..")

    @Test
    fun `parent(2) should add parents to xpath`() =
        checkThat(Selector().parent(2), "//*/../..")

    @Nested
    @DisplayName("Selector base properties")
    inner class SelectorSettersTest {

        @Test
        fun `Selector with Tag will change tag in Xpath`() {
            checkThat(
                Selector()
                    .tag("A"),
                "//A")
        }

        @Test
        fun `Selector with Prefix will change prefix in Xpath`() {
            checkThat(
                Selector()
                    .prefix("/"),
                "/*")
        }

        @Test
        fun `Selector with Prefix and tag will change prefix and tag in Xpath`() {
            checkThat(
                Selector()
                    .prefix("/")
                    .tag("A"),
                "/A")
        }
    }

    @Nested
    @DisplayName("Axis")
    inner class SelectorAxisTests {

        @Test
        fun `following method should add following axis`() {
            checkThat(
                s_tag("A").following(),
                "//following::A")
        }

        @Test
        fun `followingSibling method should add following axis`() {
            checkThat(
                s_tag("A").followingSibling(),
                "//following-sibling::A")
        }

        @Test
        fun `preceding method should add following axis`() {
            checkThat(
                s_tag("A").preceding(),
                "//preceding::A")
        }

        @Test
        fun `precedingSibling method should add following axis`() {
            checkThat(
                s_tag("A").precedingSibling(),
                "//preceding-sibling::A")
        }
    }

    @Nested
    @DisplayName("Attributes")
    inner class SelectorAttributeTests {

        @Test
        fun `array index operator with Int arg should set selector's position`() {
            checkThat(
                s_tag("A")[2],
                "//A[position()=2]")
        }

        @Test
        fun `array index operator with String arg should set selector's position`() {
            checkThat(
                s_tag("A")["last()"],
                "//A[position()=last()]")
        }

        @Test
        fun `array index operator with Int and KV args should set selector's position and add additional parameter`() {
            checkThat(
                s_tag("A")[2][KVSelectorAttribute("@label", "'asd'")],
                "//A[position()=2 and @label='asd']")
        }

        @Test
        fun `array index operator with Int and two KV args should set selector's position and add additional parameters`() {
            checkThat(
                s_tag("A")[2][KV("@label", "'asd'")][KV("text()", "'text'")],
                "//A[position()=2 and @label='asd' and text()='text']")
        }

        @Test
        fun `array index operator with selector`() {
            checkThat(
                s_tag("A")[s_tag("B")],
                "//A[B]")

            //A[B or C]
        }
    }
}
