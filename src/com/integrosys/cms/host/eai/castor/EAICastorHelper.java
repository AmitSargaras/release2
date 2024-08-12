package com.integrosys.cms.host.eai.castor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.Validate;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;
import org.exolab.castor.xml.XMLException;

import com.integrosys.cms.host.eai.FileSystemAccessException;
import com.integrosys.cms.host.eai.MessageReader;
import com.integrosys.cms.host.eai.XmlMappingException;

/**
 * Marshal and Unmarshal helper using castor routine
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.1
 */
public class EAICastorHelper {

	/** Singleton instance */
	private static final EAICastorHelper INSTANCE = new EAICastorHelper();

	private EAICastorHelper() {
	}

	public static EAICastorHelper getInstance() {
		return INSTANCE;
	}

	/**
	 * Parse the XML message in a reader into Java Object based on mapping.
	 * 
	 * @param mapping castor mapping instance
	 * @param reader the reader contain the xml message
	 * @param unmarshalClass class of java object required
	 * @return Java object parsed from xml message
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public Object unmarshal(Mapping mapping, Reader reader, Class unmarshalClass) throws XmlMappingException,
			FileSystemAccessException {
		Validate.notNull(mapping, "'mapping' must not be null");
		Validate.notNull(reader, "'reader' must not be null");
		Validate.notNull(unmarshalClass, "'unmarshalClass' must not be null");

		try {
			Unmarshaller un = new Unmarshaller(unmarshalClass);
			un.setMapping(mapping);

			Object obj = un.unmarshal(reader);
			reader.close();

			return obj;
		}
		catch (XMLException ex) {
			throw new UnmarshallingFailureException("failed to unmarshal for class [" + unmarshalClass + "]", ex);
		}
		catch (MappingException ex) {
			throw new MappingMalformedException("failed to load xml mapping file", ex);
		}
		catch (IOException ex) {
			throw new FileSystemAccessException("failed to close the message reader stream", ex);
		}

	}

	/**
	 * Parse the XML message into Java Object based on mapping xml url.
	 * 
	 * @param xmlMappingUrl mapping xml url, can get from any source, such as
	 *        classpath, jar, file
	 * @param xmlMessageFilePath the xml message file path to be parsed into
	 *        java object
	 * @param unmarshalClass class of java object required
	 * @return Java object parsed from xml message
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public Object unmarshal(URL xmlMappingUrl, URL xmlMessageUrl, Class unmarshalClass) throws XmlMappingException,
			FileSystemAccessException {
		Mapping mapping = loadMapping(xmlMappingUrl);

		try {
			FileReader fileReader = new FileReader(xmlMessageUrl.getPath());

			return unmarshal(mapping, fileReader, unmarshalClass);
		}
		catch (FileNotFoundException e) {
			throw new FileSystemAccessException("[" + xmlMessageUrl + "] cannot be found in the file system.", e);
		}
	}

	/**
	 * Parse the XML message into Java Object based on mapping xml url.
	 * 
	 * @param xmlMappingUrl mapping xml url, can get from any source, such as
	 *        classpath, jar, file
	 * @param xmlMessageFilePath the xml message file path to be parsed into
	 *        java object
	 * @param unmarshalClass class of java object required
	 * @return Java object parsed from xml message
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public Object unmarshal(URL xmlMappingUrl, String xmlMessageFilePath, Class unmarshalClass)
			throws XmlMappingException, FileSystemAccessException {
		try {
			File xmlMessageFile = new File(xmlMessageFilePath);

			return unmarshal(xmlMappingUrl, xmlMessageFile.toURI().toURL(), unmarshalClass);
		}
		catch (MalformedURLException e) {
			throw new FileSystemAccessException("The xml message path [" + xmlMessageFilePath
					+ "] is malformed, failed to retrieve", e);
		}
	}

	/**
	 * Given the user data xml and the mapping XMl, this method will return the
	 * Object with user data.
	 * 
	 * @param xmlMappingFilePath the path of the mapping XML.
	 * @param xmlMessageFilePath the path of the user data XML.
	 * @param unmarshalClass the Class of the object to map to.
	 * @return Java object parsed from xml message
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public Object unmarshal(String xmlMappingFilePath, String xmlMessageFilePath, Class unmarshalClass)
			throws XmlMappingException, FileSystemAccessException {
		File mappingFile = new File(xmlMappingFilePath);

		try {
			URL mappingFileUrl = mappingFile.toURI().toURL();

			return unmarshal(mappingFileUrl, xmlMessageFilePath, unmarshalClass);
		}
		catch (MalformedURLException e) {
			throw new FileSystemAccessException("The xml mapping path [" + xmlMessageFilePath
					+ "] is malformed, failed to retrieve", e);
		}
	}

	/**
	 * Given the user data xml and the mapping XMl, this method will return the
	 * Object with user data.
	 * 
	 * @param xmlMappingFilePath the path of the mapping XML.
	 * @param reader of type MessageReader
	 * @param unmarshalClass the Class of the object to map to.
	 * @return Java object parsed from xml message
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public Object unmarshal(String xmlMappingFilePath, MessageReader reader, Class unmarshalClass)
			throws XmlMappingException, FileSystemAccessException {
		File mappingFile = new File(xmlMappingFilePath);

		try {
			Mapping mapping = loadMapping(mappingFile.toURI().toURL());

			return unmarshal(mapping, reader.getStringReader(), unmarshalClass);
		}
		catch (MalformedURLException e) {
			throw new FileSystemAccessException("The xml mapping path [" + xmlMappingFilePath
					+ "] is malformed, failed to retrieve", e);
		}
	}

	/**
	 * Given the user data xml and the mapping XMl, this method will return the
	 * Object with user data.
	 * 
	 * @param xmlMappingUrl the URL of the mapping XML.
	 * @param reader of type MessageReader
	 * @param unmarshalClass the Class of the object to map to.
	 * @return Java object parsed from xml message
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public Object unmarshal(URL xmlMappingUrl, MessageReader reader, Class unmarshalClass) throws XmlMappingException,
			FileSystemAccessException {
		Mapping mapping = loadMapping(xmlMappingUrl);

		return unmarshal(mapping, reader.getStringReader(), unmarshalClass);
	}

	/**
	 * Given the user data xml and the mapping XMl, this method will return the
	 * Object with user data.
	 * 
	 * @param mapping the castor xml mapping instance.
	 * @param messageObject the path of the user data XML.
	 * @param marshalClass the Class of the object to map to.
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public String marshal(Mapping mapping, Object messageObject, Class marshalClass) throws XmlMappingException,
			FileSystemAccessException {
		Validate.notNull(mapping, "'mapping' must not be null");
		Validate.notNull(messageObject, "'messageObject' must not be null");
		Validate.notNull(marshalClass, "'marshalClass' must not be null");

		String returnString = "";
		try {
			StringWriter writer = new StringWriter();
			Marshaller marshaller = new Marshaller(writer);

			marshaller.setMapping(mapping);
			marshaller.marshal(messageObject);

			returnString = writer.toString();
		}
		catch (XMLException ex) {
			throw new MarshallingFailureException("failed to marshall for object [" + messageObject + "]", ex);
		}
		catch (MappingException ex) {
			throw new MappingMalformedException("failed to marshall for object [" + messageObject + "]", ex);
		}
		catch (IOException ex) {
			throw new FileSystemAccessException("failed to write the object to xml stream", ex);
		}

		return returnString;
	}

	/**
	 * Given the user data xml and the mapping XMl, this method will return the
	 * Object with user data.
	 * 
	 * @param xmlMappingUrl the URL of the mapping XML.
	 * @param messageObject the path of the user data XML.
	 * @param unmarshalClass the Class of the object to map to.
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public String marshal(URL xmlMappingUrl, Object messageObject, Class unmarshalClass) throws XmlMappingException,
			FileSystemAccessException {
		Mapping mapping = loadMapping(xmlMappingUrl);

		return marshal(mapping, messageObject, unmarshalClass);
	}

	/**
	 * Given the user data xml and the mapping XMl, this method will return the
	 * Object with user data.
	 * 
	 * @param xmlMappingFilePath the path of the mapping XML.
	 * @param messageObject the path of the user data XML.
	 * @param unmarshalClass the Class of the object to map to.
	 * @throws FileSystemAccessException if there is any error with io system,
	 *         such as xml message stream, the xml mapping
	 * @throws XmlMappingException if there is any error on the xml mapping,
	 *         unmarshall and marshall
	 */
	public String marshal(String xmlMappingFilePath, Object messageObject, Class unmarshalClass)
			throws XmlMappingException, FileSystemAccessException {
		try {
			File mappingFileUrl = new File(xmlMappingFilePath);

			return marshal(mappingFileUrl.toURI().toURL(), messageObject, unmarshalClass);
		}
		catch (MalformedURLException e) {
			throw new FileSystemAccessException("the xml mapping path [" + xmlMappingFilePath
					+ "] is malformed, failed to retrieve", e);
		}
	}

	protected Mapping loadMapping(URL xmlMappingUrl) throws XmlMappingException, FileSystemAccessException {
		Mapping mapping = new Mapping();

		try {
			mapping.loadMapping(xmlMappingUrl);
		}
		catch (MappingException ex) {
			new MappingMalformedException("failed to load the xml mapping file", ex);
		}
		catch (IOException ex) {
			throw new FileSystemAccessException("failed to write the object to xml stream", ex);
		}

		return mapping;
	}

}
