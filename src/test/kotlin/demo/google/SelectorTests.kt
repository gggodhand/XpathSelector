package demo.google

import demo.google.pages.GoogleSearch
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.nac.xpathselector.selector.BaseSelectorTest
import org.nac.xpathselector.selector.get
import org.nac.xpathselector.util.ReflectionHelper

class SelectorTests: BaseSelectorTest() {
    companion object {
        @BeforeAll
        @JvmStatic
        fun init() {
            ReflectionHelper.scanObject(GoogleSearch)
        }
    }

    @Test
    fun `GoogleSearch - searchInput`() = checkThat(GoogleSearch.searchInput,
        "//input[@title='Search']")

    @Test
    fun `GoogleSearch - searchInput pos 2`() = checkThat(GoogleSearch.searchInput[2],
        "//input[@title='Search' and position()=2]")

    @Test
    fun `GoogleSearch - searchInput  pos 2`() = checkThat(GoogleSearch.Results.desc,
        "//*[@id='search']/div/div/div//div[position()=2]")

    @Test
    fun `GoogleSearch - searchInput  pos 3`() = checkThat(GoogleSearch.Results[2].lblTitle,
        "(//*[@id='search']/div/div/div)[position()=2]//h3")

    fun asd() {
        val s = """
            
            
        """.trimIndent()
    }
}