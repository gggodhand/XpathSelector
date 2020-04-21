package demo.google.pages

import page.Block
import page.Page
import selector.get
import selenium.html.Factory.div
import selenium.html.Factory.id
import selenium.html.Factory.input
import selenium.html.Factory.submit
import selenium.html.click
import selenium.html.input
import selenium.html.Factory.divs
import selenium.html.Factory.h3
import selenium.html.clear
import selenium.html.submit

open class GoogleSearchCls: Page("Google") {
    val searchInput = input(title = "Search")
    val submitBtn = submit()

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