package org.nac.xpathselector.core

import com.sun.org.apache.xerces.internal.parsers.DOMParser
import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import org.nac.xpathselector.selector.ComposeSelector
import org.nac.xpathselector.selector.Selector
import java.io.StringReader
import java.util.*
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory


abstract class Cache {
    protected var xml: String? = null
    protected var prevXml = ""

    protected val nodesMap = HashMap<String, Node?>()
    protected var doc: Document? = null
    internal var factory = DocumentBuilderFactory.newInstance()
    lateinit var builder: DocumentBuilder

    val isPageChanged: Boolean
        get() = prevXml != xml


    abstract fun setXml(xml: String, clear: Boolean = true)


    fun isVisible(selector: Selector): Boolean {
        if(selector == null) return false
        if(selector is ComposeSelector) {
            if(selector.selectors.isEmpty()) {
                return false
            }
        }
        return isVisible(selector.toXpath())
    }

    fun isVisible(selectorXpath: String): Boolean {
        val xpath = XPathFactory.newInstance().newXPath()

        try {
            val nodes = xpath.evaluate(selectorXpath, doc, XPathConstants.NODESET) as NodeList

            //  Log.detail("Checking visibility (Cache) of: $selectorXpath")
            if (nodes.length > 0) {
                nodesMap[selectorXpath] = nodes.item(0)
                //      Log.detail("visible")
                return true
            } else {
                nodesMap[selectorXpath] = null
                //    Log.detail("hidden")
            }
        } catch (e: Exception) {
            Log.error("Can't parse xpath: $selectorXpath")
        }

        return false
    }

    fun getElementsCount(selectorXpath: String): Int {
        val xpath = XPathFactory.newInstance().newXPath()

        try {
            return (xpath.evaluate(selectorXpath, doc, XPathConstants.NODESET) as NodeList).length
        } catch (e: Exception) {
            Log.error("Can't parse xpath: $selectorXpath")
        }

        return 0
    }

    fun getAttribute(selector: Selector, attr: String, default:String = ""): String? {
        val xpath = XPathFactory.newInstance().newXPath()
        val selectorXpath = selector.toXpath()
        val attr = if(attr.startsWith("@")) attr.removeRange(0,1) else attr

        try {
            val nodes = xpath.evaluate(selectorXpath, doc, XPathConstants.NODESET) as NodeList

            if (nodes.length > 0) {
                nodesMap[selectorXpath] = nodes.item(0)

                val textContent = if(attr.startsWith("text")) nodesMap[selectorXpath]?.textContent else default

                return nodesMap[selectorXpath]?.attributes?.getNamedItem(attr)?.textContent
                    ?: textContent
            } else {
                nodesMap[selectorXpath] = null
            }
        } catch (e: Exception) {
            Log.error("Can't parse xpath: $selectorXpath")
        }

        return default
    }

    fun getAttributes(selectorXpath: String, attr: String): List<String> {
        val xpath = XPathFactory.newInstance().newXPath()
        val attr = if(attr.startsWith("@")) attr.removeRange(0,1) else attr
        val res = ArrayList<String>()

        try {
            val nodes = xpath.evaluate(selectorXpath, doc, XPathConstants.NODESET) as NodeList

            if (nodes.length > 0) {
                for (i in 0 until nodes.length) {
                    nodesMap[selectorXpath] = nodes.item(i)
                    val textContent = if(attr.startsWith("text")) nodesMap[selectorXpath]?.textContent else ""
                    val text = nodesMap[selectorXpath]?.attributes?.getNamedItem(attr)?.textContent ?: textContent
                    if (text!!.isNotEmpty()) {
                        res.add(text)
                    }
                }
            } else {
                nodesMap[selectorXpath] = null
            }
        } catch (e: Exception) {
            Log.error("Can't parse xpath: $selectorXpath")
        }

        return res
    }

    fun clear(clearXml: Boolean = true) {
        if(clearXml) {
            setXml("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                    "<hierarchy rotation=\"0\">\n" +
                    "</hierarchy>", true)
        }

        nodesMap.clear()
    }
}