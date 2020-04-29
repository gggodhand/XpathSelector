package org.nac.xpathselector.selector

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.nac.xpathselector.selector.SelectorFactoryHelper.Companion.s_tag
import org.nac.xpathselector.selector.attributes.SelectorAttribute

internal class SelectorAttributeTest : BaseSelectorTest() {

    @Test
    fun `build with selector`() {
        val selectorAttribute = SelectorAttribute(sel = s_tag("A"))
        assertEquals("A", selectorAttribute.build())
    }

    @Test
    fun `build with selector minus`() {
        val selectorAttribute = SelectorAttribute(sel = -s_tag("A"))
        assertEquals(".//A", selectorAttribute.build())
    }
}
