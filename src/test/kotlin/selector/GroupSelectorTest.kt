package selector

import selector.SelectorFactoryHelper.Companion.tag
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

@DisplayName("Group Selector")
internal class GroupSelectorTest : BaseSelectorTest() {

    @Test
    fun `plus operator create group selector`()
            = checkThat(tag("A") + tag("B"), "//A/B")

    @Test
    fun `times operator create group selector`()
            = checkThat(tag("A") * tag("B"), "//A//B")

    @Test
    fun `times operator can be used multiple times`()
            = checkThat(tag("A") * tag("B") * tag("C") * tag("D"), "//A//B//C//D")

    @Test
    fun `plus operator can be used multiple times`()
            = checkThat(tag("A") + tag("B") + tag("C") + tag("D"), "//A/B/C/D")
    @Test
    fun `plus and times operators can be combined with each other`()
            = checkThat(tag("A") * tag("B") + tag("C"), "//A//B/C")

    @Test
    fun `prefix should change first selector`()
            = checkThat((tag("A") + tag("B")).prefix("/"),
        "/A/B")

    @Test
    fun `two GroupSelectors can be merged`()
            = checkThat((tag("A") + tag("B")) + (tag("C") * tag("E")),
        "//A/B/C//E")

    @Test
    fun `clone should copy an object`() {
        var s1 = tag("A") * tag("B")
        var s2 = s1.clone()
        var s3 = s1.clone()

        s1.selectors.clear()
        s3.selectors[0] = tag("C")

        assertAll(
            Executable { assertEquals(2, s2.selectors.size) },
            Executable { assertEquals(tag("A"), s2.selectors[0]) },
            Executable { assertEquals(tag("B"), s2.selectors[1]) },

            Executable { assertEquals(0, s1.selectors.size) },
            Executable { assertEquals(tag("C"), s3.selectors[0]) }
        )
    }
}

