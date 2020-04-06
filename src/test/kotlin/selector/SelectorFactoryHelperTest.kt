package selector

import selector.SelectorFactoryHelper.Companion.s_tag
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Selector Factory")
internal class SelectorFactoryHelperTest : BaseSelectorTest() {

    @Test
    fun `Tag Selector should create Selector with tag`() = checkThat(s_tag("A"), "//A")
}
