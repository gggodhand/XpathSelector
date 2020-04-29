package org.nac.xpathselector.selenium.html

import org.nac.xpathselector.core.Timeout
import org.openqa.selenium.WebElement
import org.nac.xpathselector.selector.Selector
import org.nac.xpathselector.selenium.html.Global.executor as executor

val Selector.webElement: WebElement?
    get() {
        return executor.toWebElement(this, Timeout.NORMAL)
    }

// methods

fun Selector.click(): Selector {
    executor.click(this)
    return this
}

fun Selector.submit(): Selector {
    executor.submit(this)
    return this
}

fun Selector.clear(): Selector {
    executor.clear(this)
    return this
}

fun Selector.input(text: String): Selector {
    executor.inputText(this, text)
    return this
}

//getters
val Selector.isVisible: Boolean
    get() {
        return executor.isPresent(this)
    }

val Selector.isHidden: Boolean
    get() {
        return !isVisible
    }

val Selector.text: String
    get() {
        return executor.getAttr(this, "text") ?: ""
    }