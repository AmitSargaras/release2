// XPathUtils2.java
package com.integrosys.cms.app.common.util.dataaccess;
 

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import com.sun.org.apache.xpath.internal.XPathAPI;
import com.sun.org.apache.xpath.internal.objects.XObject;

/**
 * This class offers the needed XPath utils in a processor-independent manner.
 * It uses Xalan 2
 */
public class XPathUtils2 extends XMLUtils {
	/**
	 * Evaluates an XPath string and returns the result as an XObject
	 * 
	 * @param doc the document whose root is used as context node
	 * @param str the XPath string
	 * @return the resulted object
	 * @throws SAXException - if an error occurs during the evaluation of the
	 *         XPath string
	 */
	protected XObject eval(Document doc, String str) throws SAXException, TransformerException {
		Node root = doc.getDocumentElement();
		return XPathAPI.eval(root, str);
	}

	/**
	 * Evaluates an XPath string and returns the result as a string. The root
	 * element of the document is used as the context node of the evaluation.
	 * 
	 * @param doc the document whose root is used as context node
	 * @param str the XPath string
	 * @return the resulted string
	 * @throws SAXException - if an error occurs during the evaluation of the
	 *         XPath string
	 */
	public String evalToString(Document doc, String str) throws SAXException {
		try {
			return eval(doc, str).str();
		}
		catch (TransformerException e) {
			throw new SAXException(e);
		}
	}

	/**
	 * Evaluates an XPath string and returns the result as a NodeList The root
	 * element of the document is used as the context node of the evaluation.
	 * 
	 * @param doc the document whose root is used as context node
	 * @param str the XPath string
	 * @return the resulted list of nodes
	 * @throws SAXException - if an error occurs during the evaluation of the
	 *         XPath string
	 */
	public NodeList evalToNodeList(Document doc, String str) throws SAXException {
		try {
			return (NodeList) eval(doc, str).nodeset();
		}
		catch (TransformerException e) {
			throw new SAXException(e);
		}
	}

}
