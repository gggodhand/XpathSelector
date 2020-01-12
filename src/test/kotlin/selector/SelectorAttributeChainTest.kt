package selector

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import selector.KVSelectorAttribute.Companion.Arg
import selector.SelectorFactoryHelper.Companion.tag

internal class SelectorAttributeChainTest : BaseSelectorTest() {

    @Test
    fun `empty chain should give empty string`()
        = checkAttr("", SelectorAttributeChain())

    @Test
    fun `two args with 'and' should create a chain`()
        = checkAttr("[@label='text' and @desc='desc']",
        Arg("label", "text") and Arg("desc", "desc"))

    @Test
    fun `three args with 'and' should create a chain`()
        = checkAttr("[@label='text' and @desc='desc' and @value='val']",
        Arg("label", "text") and Arg("desc", "desc") and Arg("value", "val"))

    @Test
    fun `two args with 'or' should create a chain`()
        = checkAttr("[@label='text' or @desc='desc']",
        Arg("label", "text") or Arg("desc", "desc"))

    @Test
    fun `three args with 'or' should create a chain`()
        = checkAttr("[@label='text' or @desc='desc' or @value='val']",
        Arg("label", "text") or Arg("desc", "desc") or Arg("value", "val"))

    @Test
    fun `three args with 'or', 'and' should create a chain`()
        = checkAttr("[@label='text' and @desc='desc' or @value='val']",
        Arg("label", "text") and Arg("desc", "desc") or Arg("value", "val"))

    @Test
    fun `two args with tag and selector should create a chain`()
        = checkAttr("[@label='text' and A]",
        Arg("label", "text") and tag("A"))

    @Test
    fun `two args with tag and selector with - should create a chain`()
        = checkAttr("[@label='text' and .//A]",
        Arg("label", "text") and -tag("A"))

    private fun checkAttr(expected: String, actual: SelectorAttributeChain) {
        assertEquals(expected, actual.build())
    }
}
