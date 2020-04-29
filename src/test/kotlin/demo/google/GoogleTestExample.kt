package demo.google

import demo.google.pages.GoogleSearch
import io.github.bonigarcia.wdm.DriverManagerType
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.nac.xpathselector.selector.get
import org.nac.xpathselector.selenium.DriverFactory
import org.nac.xpathselector.selenium.html.Factory
import org.nac.xpathselector.selenium.html.Global
import org.nac.xpathselector.selenium.html.HtmlExecutor
import org.nac.xpathselector.selenium.html.text
import org.nac.xpathselector.util.ReflectionHelper

class GoogleTestExample {

    companion object {
        @BeforeAll
        @JvmStatic
        fun init() {
            ReflectionHelper.scanObject(GoogleSearch)
            val driver = DriverFactory.create(DriverManagerType.CHROME)
            DriverFactory.driver = driver

            Global.executor = HtmlExecutor(driver)
            driver.get("http://google.com")
        }
    }

    @Test
    fun test() {
        GoogleSearch.searchInput("Google Wiki")

        val s1 = GoogleSearch.Results.lblTitle.text
        val s2 = GoogleSearch.Results[2].lblTitle.text

        assertNotEquals(s1, s2)
    }
}