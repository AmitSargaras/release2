// XMLUtils.java
package com.integrosys.cms.app.common.util.dataaccess;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * This class offers the needed XML utils in a parser-independent manner. It
 * uses the JAXP API and Apache's XML serialization API
 */
public abstract class XMLUtils {

	// * JAXP INITIALIZATION

	protected static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

	protected static DocumentBuilderFactory vfactory = DocumentBuilderFactory.newInstance();
	static {
		factory.setValidating(false);
		vfactory.setValidating(true);
		
		  try {
		  factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		  factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		  factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		  vfactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
		  vfactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
		  vfactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates a document builder.
	 * 
	 * @param validating flag for enabling the XML validation
	 * @return the DocumentBuilder object
	 * @throws InternalError - if a ParserConfigurationException occurs
	 */
	public static DocumentBuilder createBuilder(boolean validating) {
		try {
			DocumentBuilder builder = null;
			if (validating) {
				builder = vfactory.newDocumentBuilder();
			}
			else {
				builder = factory.newDocumentBuilder();
			}
			builder.setErrorHandler(new ErrorHandler() {
				public void warning(SAXParseException e) throws SAXException {
					System.err.println(e.getMessage());
				}

				public void error(SAXParseException e) throws SAXException {
					throw e;
				}

				public void fatalError(SAXParseException e) throws SAXException {
					throw e;
				}
			});
			builder.setEntityResolver(new EntityResolver() {
				public InputSource resolveEntity(String publicID, String systemID) {
					if ((publicID != null) && publicID.endsWith(".dtd")) {
						InputStream in = getClass().getResourceAsStream(publicID);
						if (in != null) {
							return new InputSource(in);
						}
					}
					return null;
				}
			});
			return builder;
		}
		catch (ParserConfigurationException e) {
			throw new InternalError(e.getMessage());
		}
	}

	// * DOCUMENT UTILS

	/**
	 * Creates an empty document
	 * 
	 * @return the created document
	 */
	public static Document createEmptyDocument() {
		return createBuilder(false).newDocument();
	}

	/**
	 * Parses an XML file and returns an org.w3c.dom.Document instance.
	 * 
	 * @param file the XML file
	 * @param validating flag for enabling the XML validation
	 * @return the resulted Document object
	 * @throws IOException - if an I/O error occurs
	 * @throws SAXException - if a parsing error occurs
	 */
	public static Document parse(File file, boolean validating) throws IOException, SAXException {
		return createBuilder(validating).parse(file);
	}

	/**
	 * Parses an XML input stream and returns an org.w3c.dom.Document instance.
	 * 
	 * @param in the XML input stream
	 * @param validating flag for enabling the XML validation
	 * @return the resulted Document object
	 * @throws IOException - if an I/O error occurs
	 * @throws SAXException - if a parsing error occurs
	 */
	public static Document parse(InputStream in, boolean validating) throws IOException, SAXException {
		return createBuilder(validating).parse(in);
	}

	// * SERIALIZATION

	/**
	 * Serializes a document to an output stream
	 * 
	 * @param doc the document that is serialized
	 * @param out the output stream
	 * @param indenting flag for enabling the XML indenting
	 * @param publicID the public identifier
	 * @param systemID the system identifier
	 * @throws IOException - if an I/O error occurs
	 */
	public static void serialize(Document doc, OutputStream out, boolean indenting, String publicID, String systemID)
			throws IOException {
		// Configure the output format
		OutputFormat format = new OutputFormat(doc);
		if ((publicID != null) || (systemID != null)) {
			format.setDoctype(publicID, systemID);
		}
		format.setIndenting(indenting);
		if (!indenting) {
			format.setPreserveSpace(true);
		}
		format.setLineWidth(0);
		format.setLineSeparator("\r\n");

		// Remove the documen's doctype if necessary
		DocumentType doctype = null;
		Node afterDoctype = null;
		if ((publicID != null) || (systemID != null)) {
			NodeList list = doc.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i) instanceof DocumentType) {
					doctype = (DocumentType) list.item(i);
					if (i + 1 < list.getLength()) {
						afterDoctype = list.item(i + 1);
					}
					break;
				}
			}
		}
		if (doctype != null) {
			doc.removeChild(doctype);
		}

		// Serialize the document
		XMLSerializer serializer = new XMLSerializer(out, format);
		serializer.asDOMSerializer();
		serializer.serialize(doc);

		// Restore the document's type if necessary
		if (doctype != null) {
			if (afterDoctype != null) {
				doc.insertBefore(doctype, afterDoctype);
			}
			else {
				doc.appendChild(doctype);
			}
		}
	}

	// * XPATH UTILS

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
	public abstract String evalToString(Document doc, String str) throws SAXException;

	/**
	 * Evaluates an XPath string and returns the result as a NodeList. The root
	 * element of the document is used as the context node of the evaluation.
	 * 
	 * @param doc the document whose root is used as context node
	 * @param str the XPath string
	 * @return the resulted list of nodes
	 * @throws SAXException - if an error occurs during the evaluation of the
	 *         XPath string
	 */
	public abstract NodeList evalToNodeList(Document doc, String str) throws SAXException;

	// * CLASS LOADING

	/*
	 * Load the utils class dynamically
	 */
	protected static Class utilsClass = null;
	static {
		// Test if the XPathAPI is available
		boolean useXPathAPI = true;
		try {
			Class.forName("org.apache.xpath.XPathAPI");
		}
		catch (ClassNotFoundException e) {
			useXPathAPI = false;
		}

		// Load the class
		String className = useXPathAPI ? "XPathUtils2" : "XPathUtils1";
		try {

			utilsClass = Class.forName("com.integrosys.cms.app.common.util.dataaccess." + className);
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// * INSTANCE CREATION

	/**
	 * Returns an instance of the dynamically loaded class
	 * 
	 * @return a new instance of the utils class
	 */
	public static XMLUtils getImpl() {
		if (utilsClass == null) {
			throw new InternalError("Don't have an XMLUtils implementation");
		}
		try {
			// Return a new instance of the utils class
			return (XMLUtils) utilsClass.newInstance();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new InternalError("Couldn't instantiate the XMLUtils implementation");
		}
	}

}
