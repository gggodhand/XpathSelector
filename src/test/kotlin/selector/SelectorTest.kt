package selector

import selector.SelectorFactoryHelper.Companion.tag
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import selector.attributes.KVSelectorAttribute
import selector.attributes.KVSelectorAttribute.Companion.KV

@DisplayName("Selector")
class SelectorTest: BaseSelectorTest() {

    @Test
    fun `Default Selector returns All Match xpath`() {
        checkThat(Selector(), "//*")
    }

    @Test
    fun `clone should copy an object`() {
        var s = tag("A").freeze()
        var s2 = s.clone()

        s.setTag("B")

        assertAll (
            Executable { assertEquals("B", s.getTag()) },
            Executable { assertEquals("A", s2.getTag()) }
        )
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
                tag("A").following(),
                "//following::A")
        }

        @Test
        fun `followingSibling method should add following axis`() {
            checkThat(
                tag("A").followingSibling(),
                "//following-sibling::A")
        }

        @Test
        fun `preceding method should add following axis`() {
            checkThat(
                tag("A").preceding(),
                "//preceding::A")
        }

        @Test
        fun `precedingSibling method should add following axis`() {
            checkThat(
                tag("A").precedingSibling(),
                "//preceding-sibling::A")
        }
    }

    @Nested
    @DisplayName("Attributes")
    inner class SelectorAttributeTests {

        @Test
        fun `array index operator with Int arg should set selector's position`() {
            checkThat(
                tag("A")[2],
                "//A[position()=2]")
        }

        @Test
        fun `array index operator with String arg should set selector's position`() {
            checkThat(
                tag("A")["last()"],
                "//A[position()=last()]")
        }

        @Test
        fun `array index operator with Int and KV args should set selector's position and add additional parameter`() {
            checkThat(
                tag("A")[2][KVSelectorAttribute("@label", "'asd'")],
                "//A[position()=2 and @label='asd']")
        }

        @Test
        fun `array index operator with Int and two KV args should set selector's position and add additional parameters`() {
            checkThat(
                tag("A")[2][KV("@label", "'asd'")][KV("text()", "'text'")],
                "//A[position()=2 and @label='asd' and text()='text']")
        }

        @Test
        fun `array index operator with selector`() {
            checkThat(
                tag("A")[tag("B")],
                "//A[B]")
        }
    }
}
