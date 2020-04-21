package demo.google

import demo.google.pages.GoogleSearch
import io.github.bonigarcia.wdm.DriverManagerType
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import selector.get
import selenium.DriverFactory
import selenium.html.text
import util.ReflectionHelper

class GoogleTestExample {

    companion object {
        @BeforeAll
        @JvmStatic
        fun init() {
            ReflectionHelper.scanObject(GoogleSearch)
            val driver = DriverFactory.create(DriverManagerType.CHROME)
            DriverFactory.driver = driver
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