import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("Base Selector tests")
class SelectorTest: BaseSelectorTest() {

    @Test
    fun `Default Selector returns All Match xpath`() {
        checkThat(Selector(), "//*")
    }

    @Nested
    @DisplayName("Selector base properties tests")
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
