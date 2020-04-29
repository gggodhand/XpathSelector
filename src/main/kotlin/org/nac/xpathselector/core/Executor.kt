package org.nac.xpathselector.core

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.nac.xpathselector.selector.Selector
import java.time.Duration

enum class Timeout {
    NONE, SHORT, NORMAL, LONG, MAX;
}

abstract class Executor(
    val driver: WebDriver,
    private val useCache: Boolean = true)
{
    lateinit var cache: Cache
    var actionPerformed = true

    open fun toWebElement(selector: Selector, timeout: Timeout = Timeout.NONE): WebElement? {
        refreshCache()

        if (waitForPresent(selector, timeout)) {
            return driver.findElement(By.xpath(selector.toXpath()))
        }

        return null
    }

    open fun isPresent(selector: Selector): Boolean {
        refreshCache()
        return cache.isVisible(selector)
    }

    open fun waitForPresent(selector: Selector, timeout: Timeout): Boolean {
        refreshCache()

        if (timeout != Timeout.NONE) {
            return WaitUtilCache.waitForVisible(selector, toDuration(timeout))
        }

        return cache.isVisible(selector)
    }

    open fun waitForDissapear(selector: Selector, timeout: Timeout): Boolean {
        refreshCache()

        if (timeout != Timeout.NONE) {
            return WaitUtilCache.waitForDisappear(selector, toDuration(timeout))
        }

        return !cache.isVisible(selector)
    }

    fun sleep(duration: Duration) = sleep(duration.toMillis())
    fun sleep(timeout: Timeout) = sleep(toMs(timeout))
    fun sleep(ms: Long) {
        Thread.sleep(ms)
    }

    fun refreshCache(force: Boolean = false) {
        if (useCache && (force || actionPerformed)) {
            cache.setXml(pageSource)
        }
        actionPerformed = false
    }

    open abstract val pageSource: String

    open fun toMs(timeout: Timeout): Long {
       return when (timeout) {
           Timeout.SHORT  -> 100
           Timeout.NORMAL -> 500
           Timeout.LONG   -> 2000
           Timeout.MAX    -> 10000
           else -> 0
       }
    }

    open fun toDuration(timeout: Timeout) = Duration.ofMillis(toMs(timeout))

    //constants
    open val refreshIntervalMs: Long = 500

    //getters

    open fun getAttr(selector: Selector, attrName: String, timeout: Timeout = Timeout.NONE): String? {
        refreshCache()

        if (waitForPresent(selector, timeout)) {
            return cache.getAttribute(selector, attrName)
        }

        return null
    }

    fun action(msg: String, lambda: VoidLambda) {
        Log.actionV(msg, lambda)
        actionPerformed = true
    }
}