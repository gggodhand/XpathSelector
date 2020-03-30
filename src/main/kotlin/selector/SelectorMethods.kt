package selector

import kotlin.reflect.KClass

operator fun Selector.plus(selector: Selector): GroupSelector {
    if (this is GroupSelector) {
        addChild(selector)
    }

    return GroupSelector(this).addChild(selector)
}

operator fun Selector.times(selector: Selector): GroupSelector {
    if (this is GroupSelector) {
        addDescedant(selector)
    }

    return GroupSelector(this).addDescedant(selector)
}

object SelectorMethodsHelper {
    var prevComposeSelector: ComposeSelector? = null
}

operator fun Selector.div(selector: Selector): ComposeSelector {
    if (this is ComposeSelector) {
        if (this === SelectorMethodsHelper.prevComposeSelector) {
            SelectorMethodsHelper.prevComposeSelector = this

            return this.add(selector)
        }
    }

    SelectorMethodsHelper.prevComposeSelector = ComposeSelector(this).add(selector)
    return SelectorMethodsHelper.prevComposeSelector!!
}


/* Convenience wrapper that allows you to call getValue<Type>() instead of of getValue(Type::class) */
inline fun <reified T: Any> getValue() : T? = getValue(T::class)

/* We have no way to guarantee that an empty constructor exists, so must return T? instead of T */
fun <T: Any> getValue(clazz: KClass<T>) : T? {
    clazz.constructors.forEach { con ->
        if (con.parameters.isEmpty()) {
            return con.call()
        }
    }
    return null
}

inline operator fun <reified T: Selector>T.get(position: Int): T {
//    val res: T = getValue()!!
//    copyTo(res)
    var res: T = this.javaClass.newInstance()
  //  val res = this.clone()
    res.attributes.add(KVSelectorAttribute.Position(position))
    return res
}

operator fun <T: Selector>T.get(position: String): T {
    val res = clone()
    res.attributes.add(KVSelectorAttribute.Position(position))
    return res as T
}

operator fun <T: Selector>T.get(arg: SelectorAttribute): T {
    val res = clone()
    res.attributes.add(arg)
    return res as T
}

operator fun <T: Selector>T.get(selector: Selector): T {
    val res = clone()
    res.attributes.add(SelectorAttribute(sel = selector))
    return res as T
}