package selenium.html

import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import selector.Selector
import selenium.DriverFactory

val Selector.webElement: WebElement?
    get() {
        try{
            return DriverFactory.driver.findElement(By.xpath(this.toXpath()))
        } catch (e: Exception) {

        }

        return null
    }

// methods

fun Selector.click(): Selector {
    this.webElement?.click()
    return this
}

fun Selector.submit(): Selector {
    this.webElement?.submit()
    return this
}

fun Selector.clear(): Selector {
    this.webElement?.clear()

    return this
}

fun Selector.input(text: String): Selector {
    this.webElement?.sendKeys(text)

    return this
}

//getters
val Selector.isVisible: Boolean
    get() {
        return webElement != null
    }

val Selector.text: String
    get() {
        return webElement?.text ?: ""
    }