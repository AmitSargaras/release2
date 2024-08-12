/**
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.base.techinfra.diff;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exolab.castor.mapping.Mapping;
import org.exolab.castor.mapping.MappingException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;

/**
 * <p>
 * Utility tool to generate the XML String from a Object based on the castor
 * mapping.
 * <p>
 * Could be used for comparing two objects' graph but only interested on the
 * fields interested which setup in the castor mapping.
 * 
 * @author Ravi Vegiraju
 * @author Chong Jun Yong
 * @since 2003/06/13
 */
public class MapBasedXMLGenerator {

	private final Log logger = LogFactory.getLog(getClass());

	/** Singleton for default castor mapping files specified in <tt>LoadMaps</tt> */
	private final static MapBasedXMLGenerator SINGLETON_INSTANCE = new MapBasedXMLGenerator();

	/** Singleton for custom castor mapping files provided */
	private static MapBasedXMLGenerator CUSTOM_SINGLETON_INSTANCE;

	/**
	 * Castor marshaller to marshal the object to come out the XML consist of
	 * fields interested for comparison
	 */
	private final Marshaller objectMarshaller;

	/**
	 * Castor marshaller to marshal the object to come out the XML consist of
	 * Reference id field for comparison, mainly use for the a list of object
	 */
	private final Marshaller objectIdMarshaller;

	/** Monitor for object marshaller */
	private final Object marshallerMonitor = new Object();

	/** Monitor for object id marshaller */
	private final Object idMarshallerMonitor = new Object();

	public static MapBasedXMLGenerator getInstance() {
		return SINGLETON_INSTANCE;
	}

	public synchronized static MapBasedXMLGenerator getInstance(Mapping castorMapping, Mapping castorIdMapping) {
		if (CUSTOM_SINGLETON_INSTANCE == null) {
			CUSTOM_SINGLETON_INSTANCE = new MapBasedXMLGenerator(castorMapping, castorIdMapping);
		}
		return CUSTOM_SINGLETON_INSTANCE;
	}

	private MapBasedXMLGenerator(Mapping castorMapping, Mapping castorIdMapping) {
		objectMarshaller = new Marshaller();
		objectIdMarshaller = new Marshaller();

		try {
			objectMarshaller.setMapping(castorMapping);
		}
		catch (MappingException e) {
			throw new CompareOBException("failed to set mapping [" + LoadMaps.getInstance().getMapping() + "]", e);
		}
		try {
			objectIdMarshaller.setMapping(castorIdMapping);
		}
		catch (MappingException e) {
			throw new CompareOBException("failed to set id mapping [" + LoadMaps.getInstance().getMapping() + "]", e);
		}
	}

	private MapBasedXMLGenerator() {
		objectMarshaller = new Marshaller();
		objectIdMarshaller = new Marshaller();

		try {
			objectMarshaller.setMapping(LoadMaps.getInstance().getMapping());
		}
		catch (MappingException e) {
			throw new CompareOBException("failed to set mapping [" + LoadMaps.getInstance().getMapping() + "]", e);
		}
		try {
			objectIdMarshaller.setMapping(LoadMaps.getInstance().getIdMapping());
		}
		catch (MappingException e) {
			throw new CompareOBException("failed to set id mapping [" + LoadMaps.getInstance().getMapping() + "]", e);
		}
	}

	/**
	 * This Method gets the XML string of the object based on the castor xml
	 * map..
	 * @param o Object, which needs to be processed for xml string
	 * @return xml string of the object
	 * @throws CompareOBException is thrown when any io or castor exceptions are
	 *         caught while processing..
	 */
	protected String getXMLString(Object o) throws CompareOBException {
		synchronized (marshallerMonitor) {
			try {
				StringWriter writer = new StringWriter();
				try {
					objectMarshaller.setWriter(writer);
					objectMarshaller.marshal(o);
				}
				catch (MarshalException e) {
					logger.warn("Caught MarshalException, remarshal after filter special charaters !", e);
					convertToXmlCompliantObj(o);
					objectMarshaller.marshal(o);
				}

				String xml = writer.toString();
				if (xml.indexOf("XMLSchema-instance") > 0) {
					logger.error("XMLSchema-instance WARNING--" + o.getClass().getName());
					throw new CompareOBException("failed to marshall object ["
							+ (o == null ? "null object" : o.getClass().getName())
							+ ", has the castor mapping been setup ?");
				}

				return xml;
			}
			catch (IOException e) {
				throw new CompareOBException("failed to set writer to marshaller.", e);
			}
			catch (MarshalException e) {
				throw new CompareOBException("failed to marshal object [" + o + "]", e);
			}
			catch (ValidationException e) {
				throw new CompareOBException(
						"The class that marshaller suppose to marshal not tally with object supplied ["
								+ (o == null ? "null object" : o.getClass().getName()) + "]", e);
			}
		}
	}

	/**
	 * This Method gets the XML string of the object id based on the castor id
	 * xml map..
	 * @param o Object, which needs to be processed for xml string
	 * @return xml string of the object
	 * @throws CompareOBException is thrown when any io or castor exceptions are
	 *         caught while processing..
	 */
	protected String getIdXMLString(Object o) throws CompareOBException {
		synchronized (idMarshallerMonitor) {
			try {
				StringWriter writer = new StringWriter();
				try {
					objectIdMarshaller.setWriter(writer);
					objectIdMarshaller.marshal(o);
				}
				catch (MarshalException e) {
					logger.warn("Caught MarshalException, remarshal after filter special charaters !", e);
					convertToXmlCompliantObj(o);
					objectIdMarshaller.marshal(o);
				}

				String xml = writer.toString();
				if (xml.indexOf("XMLSchema-instance") > 0) {
					logger.error("XMLSchema-instance WARNING--" + o.getClass().getName());
					throw new CompareOBException("failed to marshall object ["
							+ (o == null ? "null object" : o.getClass().getName())
							+ ", has the castor mapping been setup ?");
				}

				return xml;
			}
			catch (IOException e) {
				throw new CompareOBException("failed to set writer to marshaller.", e);
			}
			catch (MarshalException e) {
				throw new CompareOBException("failed to marshal object [" + o + "]", e);
			}
			catch (ValidationException e) {
				throw new CompareOBException(
						"The class that marshaller suppose to marshal not tally with object supplied ["
								+ (o == null ? "null object" : o.getClass().getName()) + "]", e);
			}
		}
	}

	private void convertToXmlCompliantObj(Object originalObj) {
		Class oClass = originalObj.getClass();
		Method[] oMethodArray = oClass.getMethods();
		for (int i = 0; i < oMethodArray.length; i++) {
			String mName = oMethodArray[i].getName();
			if (mName.substring(0, 3).equals("get")) {
				String setMName = mName.substring(4);
				try {
					Object returnObj = oMethodArray[i].invoke(originalObj, (Object[])null);
					if (returnObj != null) {
						String name = returnObj.toString();
						// String className = returnObj.getClass().getName();
						if ((name.length() > 4) && ("com.".equals(name.substring(0, 4)))) {
							convertToXmlCompliantObj(returnObj);
						}
						else if ((name.length() > 6) && ("[Lcom.".equals(name.substring(0, 6)))) {
							int size = Array.getLength(returnObj);
							Object obj2 = null;
							for (int j = 0; j < size; j++) {
								obj2 = Array.get(returnObj, j);
								convertToXmlCompliantObj(obj2);
							}
						}
						else if ((name.length() > 5) && ("[com.".equals(name.substring(0, 5)))) {
							ArrayList al = (ArrayList) returnObj;
							for (int k = 0; k < al.size(); k++) {
								convertToXmlCompliantObj(al.get(k));
							}
						}
						else if ((name.length() > 7) && ("[Ljava.".equals(name.substring(0, 7)))) {
							int size = Array.getLength(returnObj);
							String[] strArray = new String[size];
							for (int k = 0; k < size; k++) {
								strArray[k] = getXMLCompliantStr((String) Array.get(returnObj, k));
							}
							returnObj = strArray;
						}
						else {// returnObj is java.lang.String
							returnObj = getXMLCompliantStr(name);
						}
						Object[] arguments = new Object[] { returnObj };
						oMethodArray[i].invoke(setMName, arguments);
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String getXMLCompliantStr(String inStr) {
		if (inStr == null) {
			return null;
		}
		return getXMLCompliantChars(inStr.toCharArray()).toString();
	}

	private char[] getXMLCompliantChars(char cbuf[]) {
		int length = (cbuf == null ? 0 : cbuf.length);
		for (int index = 0; index < length; index++) {
			int c = cbuf[index];
			if (((c >= 32) && (c < 127)) || (c == '\t') || (c == '\r') || (c == '\n')) {
				// accept
			}
			else {
				logger.info("Changed one : " + cbuf[index]);
				cbuf[index] = 32;
			}
		}
		return cbuf;
	}
}