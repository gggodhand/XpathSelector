package demo.google.pages

import org.nac.xpathselector.page.Block
import org.nac.xpathselector.page.Page
import org.nac.xpathselector.selector.get
import org.nac.xpathselector.selenium.html.Factory.div
import org.nac.xpathselector.selenium.html.Factory.id
import org.nac.xpathselector.selenium.html.Factory.input
import org.nac.xpathselector.selenium.html.Factory
import org.nac.xpathselector.selenium.html.input
import org.nac.xpathselector.selenium.html.Factory.divs
import org.nac.xpathselector.selenium.html.Factory.h3
import org.nac.xpathselector.selenium.html.clear
import org.nac.xpathselector.selenium.html.submit

open class GoogleSearchCls: Page("Google") {
    val searchInput = input(title = "Search")
    val submitBtn =  Factory.submit()

    fun searchInput(value: String) {
        searchInput.clear().input(value).submit()
    }

    open inner class ResultsCls: Block(id("search").divs(3)) {
        val lblTitle = h3()
        val desc = div()[2]
    } val Results = ResultsCls()
}

object GoogleSearch: GoogleSearchCls()

object Aa {

}