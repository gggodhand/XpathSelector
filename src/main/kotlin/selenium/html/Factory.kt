package selenium.html

import selector.Selector
import selector.SelectorFactoryHelper.Companion.s_tag
import selector.attributes.KVSelectorAttribute.Companion.Arg
import selector.attributes.KVSelectorAttribute.Companion.KV
import selector.attributes.SelectorAttribute.Companion.ArgC
import selector.get
import selector.parentTag

object Factory {
    fun h3(cls: String = "",
              clsContains: String = "",
              title: String = "",
              titleContains: String = "") = tag("h3", cls, clsContains, title, titleContains)

    fun div(cls: String = "",
           clsContains: String = "",
           title: String = "",
           titleContains: String = "") = tag("div", cls, clsContains, title, titleContains)

    fun input(cls: String = "",
              clsContains: String = "",
              title: String = "",
              titleContains: String = "") = tag("input", cls, clsContains, title, titleContains)

    fun button(cls: String = "",
              clsContains: String = "",
              title: String = "",
              titleContains: String = "") = tag("button", cls, clsContains, title, titleContains)


    fun submit(cls: String = "",
               clsContains: String = "",
               title: String = "",
               titleContains: String = "") = tag("button", cls, clsContains, title, titleContains, type = "submit")

    fun id(value: String): Selector {
        return Selector()[Arg("id", value)]
    }

    fun tag(tag: String = "",
            cls: String = "",
            clsContains: String = "",

            title: String = "",
            titleContains: String = "",

            type: String = ""): Selector {
        var res = s_tag(tag)

        when {
            cls.isNotEmpty() -> {
                res = res[Arg("class", cls)]
            }
            clsContains.isNotEmpty() -> {
                res = res[ArgC("class", clsContains)]
            }

            title.isNotEmpty() -> {
                res = res[Arg("title", title)]
            }
            titleContains.isNotEmpty() -> {
                res = res[ArgC("title", titleContains)]
            }

            type.isNotEmpty() -> {
                res = res[Arg("type", type)]
            }
        }

        return res
    }

    fun Selector.divs(count: Int): Selector {
        return this.parentTag("div", count)
    }

}