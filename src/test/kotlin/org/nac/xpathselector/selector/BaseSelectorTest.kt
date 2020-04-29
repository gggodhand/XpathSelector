package org.nac.xpathselector.selector

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll

open class BaseSelectorTest {
    companion object {
        @BeforeAll
        @JvmStatic
        fun init_BaseSelectorTest() {
            Selector.useUpperCaseForTag = false
        }
    }

    fun checkThat(selector: Selector, xpath: String) {
        assertEquals(xpath, selector.toXpath(), "Xpath doesn't match")
    }
}