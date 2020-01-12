package selector

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import selector.SelectorFactoryHelper.Companion.tag

internal class SelectorAttributeTest : BaseSelectorTest() {

    @Test
    fun `build with selector`() {
        val selectorAttribute = SelectorAttribute(sel = tag("A"))
        assertEquals("A", selectorAttribute.build())
    }

    @Test
    fun `build with selector minus`() {
        val selectorAttribute = SelectorAttribute(sel = -tag("A"))
        assertEquals(".//A", selectorAttribute.build())
    }
}
