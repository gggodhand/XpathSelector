package org.nac.xpathselector.core
import org.nac.xpathselector.selector.Selector
import org.nac.xpathselector.selenium.html.Global
import org.nac.xpathselector.selenium.html.isHidden
import org.nac.xpathselector.selenium.html.isVisible
import java.time.Duration

import org.nac.xpathselector.core.Log.action as action

object WaitUtilCache {
    val executor: Executor = Global.executor

    fun waitForVisible(selector: Selector,
                       duration: Duration = Duration.ofSeconds(30)) : Boolean {

        return action("Waiting for visible of $selector, for $duration") {
            waitHelper({selector.isVisible}, duration)
        }
    }

    fun waitForDisappear(selector: Selector,
                         duration: Duration = Duration.ofSeconds(30)) : Boolean {
        return action("Waiting for disappear of $selector, for $duration") {
            waitHelper({selector.isHidden}, duration)
        }
    }

    private fun waitHelper(func:() -> Boolean, duration: Duration) : Boolean {
        val t1 = System.currentTimeMillis()
        fun timeoutNotExpired() = !isTimeoutExpired(t1, duration)

        while (func() && timeoutNotExpired()) {
            executor.sleep(executor.refreshIntervalMs)
            executor.refreshCache(true)
        }

        return timeoutNotExpired()
    }

    private fun isTimeoutExpired(startTime : Long, duration: Duration) : Boolean {
        return System.currentTimeMillis() - startTime > duration.toMillis()
    }
}