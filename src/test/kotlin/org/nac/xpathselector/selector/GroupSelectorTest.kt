package org.nac.xpathselector.selector

import org.nac.xpathselector.selector.SelectorFactoryHelper.Companion.s_tag
import org.junit.jupiter.api.Assertions.assertAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable

@DisplayName("Group Selector")
internal class GroupSelectorTest : BaseSelectorTest() {

    @Test
    fun `plus operator create group selector`()
            = checkThat(s_tag("A") + s_tag("B"), "//A/B")

    @Test
    fun `times operator create group selector`()
            = checkThat(s_tag("A") * s_tag("B"), "//A//B")

    @Test
    fun `times operator can be used multiple times`()
            = checkThat(s_tag("A") * s_tag("B") * s_tag("C") * s_tag("D"), "//A//B//C//D")

    @Test
    fun `plus operator can be used multiple times`()
            = checkThat(s_tag("A") + s_tag("B") + s_tag("C") + s_tag("D"), "//A/B/C/D")

    @Test
    fun `plus and times operators can be combined with each other`()
            = checkThat(s_tag("A") * s_tag("B") + s_tag("C"), "//A//B/C")

    @Test
    fun `prefix should change first selector`()
            = checkThat((s_tag("A") + s_tag("B")).prefix("/"),
        "/A/B")

    @Test
    fun `two GroupSelectors can be merged`()
            = checkThat((s_tag("A") + s_tag("B")) + (s_tag("C") * s_tag("E")),
        "//A/B/C//E")


    @Test
    fun `clone should copy an object`() {
        var s1 = (s_tag("A") * s_tag("B")).freeze()
        var s2 = s1.clone()
        var s3 = s1.clone()

        s1.selectors.clear()
        s3.selectors[0] = s_tag("C")

        assertAll(
            Executable { assertEquals(1, s2.selectors.size, "1") },
            Executable { assertEquals(s_tag("B"), s2.selectors[0], "2") },
            Executable { assertEquals(0, s1.selectors.size, "3") },
            Executable { assertEquals(s_tag("C"), s3.selectors[0], "4") }
        )
    }

    @Test
    fun `parent tag should build a chain of tags`() = checkThat(s_tag("A").parentTag("div", 2),
        "//A/div/div")

    @Test
    fun `attributes of group selector should be added to the first selector`()
            = checkThat((s_tag("A") + s_tag("B"))[2],
        "(//A/B)[position()=2]")
}

