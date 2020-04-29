package org.nac.xpathselector.selector

import org.nac.xpathselector.selector.SelectorFactoryHelper.Companion.s_tag
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Compose Selector")
internal class ComposeSelectorTest : BaseSelectorTest() {

    @Test
    fun `div operator should create compose selector`()
            = checkThat(s_tag("A") / s_tag("B"),
        "(//A) | (//B)")

    @Test
    fun `div multiple operator should create compose selector`()
            = checkThat(s_tag("A") / s_tag("B") / s_tag("C"),
        "(//A) | (//B) | (//C)")

    @Test
    fun `div multiple operator should create compose selector for group selectors`()
            = checkThat((s_tag("A") + s_tag("AA")) / (s_tag("B") * s_tag("BB")) / s_tag("C"),
        "(//A/AA) | (//B//BB) | (//C)")

    @Test
    fun `different compose selectors should be grouped according to their priority by brackets`()
            = checkThat((s_tag("A") / s_tag("B")) / ((s_tag("C") / s_tag("D"))),
        "((//A) | (//B)) | ((//C) | (//D))")

    @Test
    fun `different compose selectors should be grouped according to their priority by brackets and without brackets`()
            = checkThat(s_tag("A") / s_tag("B") / s_tag("C") / (s_tag("D") / s_tag("E")),
        "((//A) | (//B) | (//C)) | ((//D) | (//E))")
}

