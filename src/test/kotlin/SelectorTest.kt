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
}
