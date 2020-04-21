package selector

class SelectorFactoryHelper {
    companion object {
        fun s_tag(tag: String): Selector =
            Selector(tag = tag)

        fun xpath(value: String): Selector = XpathSelector(value)
    }
}