import SelectorFactoryHelper.Companion.tag
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable


@DisplayName("Selector")
class SelectorTest: BaseSelectorTest() {

    @Test
    fun `Default Selector returns All Match xpath`() {
        checkThat(Selector(), "//*")
    }

    @Test
    fun `clone should copy an object`() {
        var s = tag("A")
        var s2 = s.clone()

        s.setTag("B")

        assertAll (
            Executable { assertEquals("B", s.getTag()) },
            Executable { assertEquals("A", s2.getTag()) }
        )
    }

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
}
