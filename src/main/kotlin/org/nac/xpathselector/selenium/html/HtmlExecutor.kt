package org.nac.xpathselector.selenium.html

import org.nac.xpathselector.core.Executor
import org.nac.xpathselector.core.HtmlCache
import org.openqa.selenium.WebDriver
import org.nac.xpathselector.selector.Selector
import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor


open class HtmlExecutor(driver: WebDriver) : Executor(driver) {

    init {
        cache = HtmlCache()
        Selector.useUpperCaseForTag = true
    }

    fun click(selector: Selector) {
        action("Clicking at ${selector.name}") {
            toWebElement(selector)?.click()
        }
    }

    fun inputText(selector: Selector, text: String) {
        action("Sending '$text' to ${selector.name}") {
            toWebElement(selector)?.sendKeys(text)
        }
    }

    fun submit(selector: Selector) {
        action("Submit for ${selector.name}") {
            toWebElement(selector)?.submit()
        }
    }

    fun clear(selector: Selector) {
        action("Clear for ${selector.name}") {
            toWebElement(selector)?.clear()
        }
    }

    override val pageSource: String
        get()  {
           // return driver.pageSource

            val javascript = "return arguments[0].innerHTML"
            val pageSource=(driver as JavascriptExecutor)
            .executeScript(javascript, driver.findElement(By.tagName("html")))
             return "<html>$pageSource</html>"

        } //driver.findElement(By.ByTagName("html")).getAttribute("innerHTML")
}