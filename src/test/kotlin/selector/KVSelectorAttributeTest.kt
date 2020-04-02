package selector

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import selector.attributes.KVSelectorAttribute.Companion.Arg
import selector.attributes.SelectorAttribute

internal class KVSelectorAttributeTest : BaseSelectorTest() {

    @Test
    fun `simple KV-attribute should be converted without escaping`()
        = checkAttribute(Arg("label", "asd"),"@label='asd'")

    @Test
    fun `KV-attribute with escaping chars should be wrapped into concat func`()
        = checkAttribute(Arg("label", "don't"),"@label='concat('don',\"'\",'t')'")

    private fun checkAttribute(attr: SelectorAttribute, expected: String) {
        assertEquals(expected, attr.build())
    }
}
