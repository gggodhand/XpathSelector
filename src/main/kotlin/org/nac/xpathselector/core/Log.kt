package org.nac.xpathselector.core

typealias VoidLambda = () -> Unit
typealias BoolLambda = () -> Boolean

object Log {
    fun log(msg: String) {
        println(msg)
    }

    fun error(msg: String) {
        println(msg)
    }

    fun actionV(msg: String, lambda: VoidLambda) {
        log(msg)
        lambda()
    }

    fun action(msg: String, lambda: BoolLambda): Boolean {
        log(msg)
        return lambda()
    }
}