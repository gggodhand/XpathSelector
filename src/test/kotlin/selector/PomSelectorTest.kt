package selector

import org.junit.jupiter.api.Test
import selector.page.PageTest

class PomSelectorTest : BaseSelectorTest() {



    @Test
    fun `POM class should add it`() {
        print(PageTest.Item.s2.tag("asd"))


        val b1 = PageTest.s1.base
        POM.parseAnnotations("selector.page")
        val b2 = PageTest.s1.base
        checkThat(PageTest.s1, "//b//a")

        PageTest.tag
    }

}
