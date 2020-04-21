package selenium

import io.github.bonigarcia.wdm.DriverManagerType
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver

object DriverFactory {

    lateinit var driver: WebDriver

    fun create(type: DriverManagerType, version: String = "latest"): WebDriver {
        WebDriverManager.getInstance(type).version(version).setup()
        val driverClass = Class.forName(type.browserClass())

        return driverClass.newInstance() as WebDriver
    }

}