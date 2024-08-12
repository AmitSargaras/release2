// Descriptor.java
package com.integrosys.cms.app.common.util.dataaccess;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

/**
 * Utility class that can be used to manage an XML descriptor.
 */
public class Descriptor {
	private XMLUtils utils;

	private Document doc;

	// * DESCRIPTOR CREATION

	/**
	 * Creates a org.w3c.dom.Document instance with a root element.
	 * 
	 * @param rootName the name of the root element
	 * @return the created Document object
	 */
	public static Document createDocument(String rootName) {
		// Create the document
		Document doc = XMLUtils.createEmptyDocument();

		// Create the root element
		Node root = doc.createElement(rootName);

		// Add the root element to the document
		doc.appendChild(root);

		// Return the new document
		return doc;
	}

	/**
	 * Constructs a descriptor object based on an existent org.w3c.dom.Document
	 * instance. The document must have a root element.
	 * 
	 * @param doc the existent Document instance
	 * @throws IllegalArgumentException - if doc doesn't have a root element
	 */
	public Descriptor(Document doc) {
		this.utils = XMLUtils.getImpl();
		this.doc = doc;

		// Check the existence of the root element
		Node root = doc.getDocumentElement();
		if (root == null) {
			throw new IllegalArgumentException("Document without root element");
		}
	}

	// * DESCRIPTOR STRUCTURE

	/**
	 * Uses XPath to get an element of the descriptor.
	 * 
	 * @param path the absolute location path of the element, e.g.
	 *        /book/chapter[3]/section[5] (1 is the index of the first child of
	 *        a node.)
	 * @return the Element object
	 * @throws SAXException - if an error occurs during the use of XPath
	 * @throws IllegalArgumentException - if the node isn't an element or it
	 *         doesn't exist
	 */
	public synchronized Element getElement(String elemPath) throws SAXException {
		// Get the list of nodes
		NodeList list = utils.evalToNodeList(doc, elemPath);

		// Get the first node of the list
		Node node = (list.getLength() > 0) ? list.item(0) : null;

		// Cast the Node object to Element
		if (node instanceof Element) {
			return (Element) node;
		}
		else {
			throw new IllegalArgumentException(elemPath + " isn't the path of an element");
		}
	}

	/**
	 * Uses XPath to count the elements have the same name and parent. The path
	 * of the parent element and the common name of the children are used to
	 * build an XPath string: parentPath/elemName This XPath string is evaluated
	 * to a list of nodes used to find out the number of elements.
	 * 
	 * @param parentPath the absolute location path of the parent element, e.g.
	 *        /book/chapter[3] (1 is the index of the first child of a node.)
	 * @param elemName the name of the elements, e.g. section
	 * @return the number of elements
	 * @throws SAXException - if an error occurs during the evaluation of the
	 *         XPath string
	 */
	public synchronized int getElementCount(String parentPath, String elemName) throws SAXException {
		// Build the XPath string
		String path = parentPath + '/' + elemName;

		// Get the list of elements
		NodeList list = utils.evalToNodeList(doc, path);

		// Return the element count
		return list.getLength();
	}

	// * DESCRIPTOR BUILDING

	/**
	 * Creates an element node and adds it to a parent element.
	 * 
	 * @param parentPath the absolute location path of the parent element, e.g.
	 *        /book/chapter[3] (1 is the index of the first child of a node.)
	 * @param elemName the name of the element that must be created, e.g.
	 *        section
	 * @throws SAXException - if an error occurs during the use of XPath
	 */
	public synchronized void addElement(String parentPath, String elemName) throws SAXException {
		// Get the parent element
		Element parent = getElement(parentPath);

		// Create the element
		Element elem = doc.createElement(elemName);

		// Append the created element to its parent
		parent.appendChild(elem);
	}

	/**
	 * Removes an element node.
	 * 
	 * @param elemPath the absolute location path of the element, e.g.
	 *        /book/chapter[3]/section[5] (1 is the index of the first child of
	 *        a node.)
	 * @throws SAXException - if an error occurs during the use of XPath
	 */
	public synchronized void removeElement(String elemPath) throws SAXException {
		// Get the element
		Element elem = getElement(elemPath);

		// Get the element's parent
		Node parent = elem.getParentNode();

		// Remove the element
		if (parent != null) {
			parent.removeChild(elem);
		}
	}

	/**
	 * Adds a Text node containing some character data to an element.
	 * 
	 * @param elemPath the absolute location path of the element, e.g.
	 *        /book/chapter[3]/section[5]/title (1 is the index of the first
	 *        child of a node.)
	 * @param data the character data that is added
	 * @throws SAXException - if an error occurs during the use of XPath
	 */
	public synchronized void addData(String elemPath, String data) throws SAXException {
		// Get the element
		Element elem = getElement(elemPath);

		// Create the text node
		Text text = doc.createTextNode(data);

		// Append the text node to the element
		elem.appendChild(text);
	}

	/**
	 * Removes the character data contained by an element.
	 * 
	 * @param elemPath the absolute location path of the element, e.g.
	 *        /book/chapter[3]/section[5]/title (1 is the index of the first
	 *        child of a node.)
	 * @throws SAXException - if an error occurs during the use of XPath
	 */
	public synchronized void removeData(String elemPath) throws SAXException {
		// Get the element
		Element elem = getElement(elemPath);

		// Get the character data nodes
		NodeList list = elem.getChildNodes();
		Vector v = new Vector();
		for (int i = 0; i < list.getLength(); i++) {
			Node node = list.item(i);
			if (node instanceof CharacterData) {
				v.add(node);
			}
		}

		// Remove the character data nodes
		for (int i = 0; i < v.size(); i++) {
			elem.removeChild((Node) v.get(i));
		}
	}

	/**
	 * Adds an attribute to an element.
	 * 
	 * @param elemPath the absolute location path of the element, e.g.
	 *        /book/chapter[3]/section[5]/title (1 is the index of the first
	 *        child of a node.)
	 * @param attrName the name of the attribute, e.g. style
	 * @param attrValue the value of the attribute, e.g. H2
	 * @throws SAXException - if an error occurs during the use of XPath
	 */
	public synchronized void addAttribute(String elemPath, String attrName, String attrValue) throws SAXException {
		// Get the element
		Element elem = getElement(elemPath);

		// Add the attribute
		elem.setAttribute(attrName, attrValue);
	}

	/**
	 * Removes an attribute of an element.
	 * 
	 * @param elemPath the absolute location path of the element, e.g.
	 *        /book/chapter[3]/section[5]/title (1 is the index of the first
	 *        child of a node.)
	 * @param attrName the name of the attribute, e.g. style
	 * @throws SAXException - if an error occurs during the use of XPath
	 */
	public synchronized void removeAttribute(String elemPath, String attrName) throws SAXException {
		// Get the element
		Element elem = getElement(elemPath);

		// Add the attribute
		elem.removeAttribute(attrName);
	}

	// * DESCRIPTOR SAVING

	/**
	 * Saves the descriptor to an XML file
	 * 
	 * @param file the XML file
	 * @param indenting flag for enabling the XML indenting
	 * @param publicID the public identifier
	 * @param systemID the system identifier
	 * @throws IOException - if an I/O error occurs
	 */
	public synchronized void save(File file, boolean indenting, String publicID, String systemID) throws IOException {
		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		try {
			// Serialize the document
			XMLUtils.serialize(doc, out, indenting, publicID, systemID);
		}
		finally {
			out.close();
		}
	}

	/**
	 * Saves the descriptor to an XML file
	 * 
	 * @param file the XML file
	 * @param indenting flag for enabling the XML indenting
	 * @throws IOException - if an I/O error occurs
	 */
	public synchronized void save(File file, boolean indenting) throws IOException {
		save(file, indenting, null, null);
	}

	// * DESCRIPTOR PARSING

	/**
	 * Parses an XML file and constructs a descriptor object.
	 * 
	 * @param file the XML file
	 * @param validating flag for enabling the XML validation
	 * @throws IOException - if an I/O error occurs
	 * @throws SAXException - if a parsing error occurs
	 */
	public Descriptor(File file, boolean validating) throws IOException, SAXException {
		this.utils = XMLUtils.getImpl();

		// Parse the file
		doc = XMLUtils.parse(file, validating);
	}

	/**
	 * Parses an XML resource and constructs a descriptor object. The resource
	 * must be in CLASSPATH.
	 * 
	 * @param name the name of the XML resource
	 * @param validating flag for enabling the XML validation
	 * @throws IOException - if an I/O error occurs
	 * @throws SAXException - if a parsing error occurs
	 */
	public Descriptor(String name, boolean validating) throws DataAccessException {
		this.utils = XMLUtils.getImpl();
		InputStream in = getClass().getResourceAsStream(name);
		try {
			if (in == null) {
				throw new FileNotFoundException(name);
			}

			// Parse the resource
			doc = XMLUtils.parse(in, validating);
		}
		catch (FileNotFoundException se) {
			throw new DataAccessException(se.getMessage(), se.fillInStackTrace());
		}
		catch (IOException se) {
			throw new DataAccessException(se.getMessage(), se.fillInStackTrace());
		}
		catch (SAXException se) {
			throw new DataAccessException(se.getMessage(), se.fillInStackTrace());
		}
		finally {
			try {
				if (in != null) {
					in.close();
				}
			}
			catch (IOException se) {
			}
		}
	}

	/**
	 * Parses an XML input stream and constructs a descriptor object.
	 * 
	 * @param in the XML input stream
	 * @param validating flag for enabling the XML validation
	 * @throws IOException - if an I/O error occurs
	 * @throws SAXException - if a parsing error occurs
	 */
	public Descriptor(InputStream in, boolean validating) throws DataAccessException {
		try {
			this.utils = XMLUtils.getImpl();

			// Parse the input stream
			doc = XMLUtils.parse(in, validating);
		}
		catch (IOException se) {
			throw new DataAccessException(se.getMessage(), se.fillInStackTrace());
		}
		catch (SAXException se) {
			throw new DataAccessException(se.getMessage(), se.fillInStackTrace());
		}
	}

	// * DESCRIPTOR QUERING

	/**
	 * Uses XPath to get the character data contained by an element. The path of
	 * the element passed as parameter is used to build the XPath string:
	 * string(elemPath/text())
	 * 
	 * @param elemPath the absolute location path of the element, e.g.
	 *        /book/chapter[3]/section[5]/title (1 is the index of the first
	 *        child of a node.)
	 * @return the character data of the element
	 * @throws SAXException - if an error occurs during the evaluation of the
	 *         XPath string
	 */
	public synchronized String getData(String elemPath) throws DataAccessException {
		try {
			return utils.evalToString(doc, "string(" + elemPath + "/text())");
		}
		catch (SAXException se) {
			throw new DataAccessException(se.getMessage(), se.fillInStackTrace());
		}
	}

	/**
	 * Uses XPath to get the value of an attribute of an element. The path of
	 * the element and the name of the attribute passed as parameters are used
	 * to build the XPath string: string(elemPath/@attrName/text())
	 * 
	 * @param elemPath the absolute location path of the element, e.g.
	 *        /book/chapter[3]/section[5]/title (1 is the index of the first
	 *        child of a node.)
	 * @param attrName the name of the attribute, e.g. style
	 * @return the value of the attribute, e.g. H2
	 * @throws SAXException - if an error occurs during the evaluation of the
	 *         XPath string
	 */
	public synchronized String getAttribute(String elemPath, String attrName) throws DataAccessException {
		// Build the path of the attribute
		String path = elemPath + '/' + '@' + attrName;

		// Return the value of the attribute
		return getData(path);
	}

	/**
	 * Uses XPath to get the character data contained by a group of elements
	 * that have the same name and parent. The character data contained by each
	 * child element is obtained with string(arrayPath/elemName[index]/text())
	 * 
	 * @param arrayPath the absolute location path of the parent element, e.g.
	 *        /book/chapter[3] (1 is the index of the first child of a node.)
	 * @param elemName the name of the elements, e.g. section
	 * @return the character data of the elements
	 * @throws SAXException - if an error occurs during the evaluation of the
	 *         XPath string
	 */
	public synchronized String[] getArray(String arrayPath, String elemName) throws DataAccessException {
		// Get the number of elements
		try {
			int length = getElementCount(arrayPath, elemName);
			// Create the string array
			String array[] = new String[length];
			for (int i = 0, j = 1; i < length; i++, j++) {
				// Build the path of the current element
				String path = arrayPath + '/' + elemName + '[' + j + ']';

				// Get the character data of the current element
				array[i] = getData(path);
			}

			// Return the string array containing the character data
			return array;
		}
		catch (SAXException se) {
			throw new DataAccessException(se.getMessage(), se.fillInStackTrace());
		}

	}

}
