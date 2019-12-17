import SelectorFactoryHelper.Companion.tag
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Compose Selector")
internal class ComposeSelectorTest : BaseSelectorTest() {

    @Test
    fun `div operator should create compose selector`()
            = checkThat(tag("A") / tag("B"),
        "(//A) | (//B)")

    @Test
    fun `div multiple operator should create compose selector`()
            = checkThat(tag("A") / tag("B") / tag("C"),
        "(//A) | (//B) | (//C)")

    @Test
    fun `div multiple operator should create compose selector for group selectors`()
            = checkThat((tag("A") + tag("AA")) / (tag("B") * tag("BB")) / tag("C"),
        "(//A/AA) | (//B//BB) | (//C)")

    @Test
    fun `different compose selectors should be grouped according to their priority by brackets`()
            = checkThat((tag("A") / tag("B")) / ((tag("C") / tag("D"))),
        "((//A) | (//B)) | ((//C) | (//D))")

    @Test
    fun `different compose selectors should be grouped according to their priority by brackets and without brackets`()
            = checkThat(tag("A") / tag("B") / tag("C") / (tag("D") / tag("E")),
        "((//A) | (//B) | (//C)) | ((//D) | (//E))")
}

