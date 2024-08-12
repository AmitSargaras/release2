package com.integrosys.cms.host.eai.core;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.FileSystemAccessException;
import com.integrosys.cms.host.eai.XmlMappingException;

/**
 * Message marshaller factory to unmarshal (XML to Object) and marshal (Object
 * to XML)
 * 
 * @author Chong Jun Yong
 * 
 */
public interface MessageMarshallerFactory {
	/**
	 * To convert the raw XML string to EAIMessage
	 * 
	 * @param rawMessage the raw XML string
	 * @return the EAIMessage object ready to be processed
	 * @throws XmlMappingException if there is any error in unmarshalling
	 *         process
	 * @throws FileSystemAccessException if the mapping required to unmarshall
	 *         rawMessage doesn't exist.
	 */
	public EAIMessage unmarshall(String rawMessage) throws XmlMappingException, FileSystemAccessException;

	/**
	 * To convert the EAIMessage object to raw XML string
	 * 
	 * @param eaiMessage the EAIMessage object
	 * @return the raw XML string converted from the EAIMessage supplied
	 * @throws XmlMappingException if there is any error in marshalling process
	 * @throws FileSystemAccessException if the mapping required to unmarshall
	 *         rawMessage doesn't exist.
	 */
	public String marshall(EAIMessage eaiMessage) throws XmlMappingException, FileSystemAccessException;
}
