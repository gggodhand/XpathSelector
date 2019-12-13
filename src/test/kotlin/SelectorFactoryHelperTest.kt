import SelectorFactoryHelper.Companion.tagSelector
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SelectorFactoryHelperTest : BaseSelectorTest() {

    @Test
    fun `Tag Selector should create Selector with tag`() = checkThat(tagSelector("A"), "//A")
}
