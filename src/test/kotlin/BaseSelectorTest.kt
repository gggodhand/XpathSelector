import org.junit.jupiter.api.Assertions.*

open class BaseSelectorTest {
    fun checkThat(selector: Selector, xpath: String) {
        assertEquals(xpath, selector.toXpath(), "Xpath doesn't match")
    }
}