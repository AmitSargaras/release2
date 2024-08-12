package com.integrosys.cms.host.stp.core;

import com.integrosys.cms.host.stp.FileSystemAccessException;
import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.XmlMappingException;
import com.integrosys.cms.host.stp.common.StpCommonException;

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
	 * @throws com.integrosys.cms.host.eai.XmlMappingException if there is any error in unmarshalling
	 *         process
	 * @throws com.integrosys.cms.host.eai.FileSystemAccessException if the mapping required to unmarshall
	 *         rawMessage doesn't exist.
	 */
	public STPMessage unmarshall(String rawMessage) throws XmlMappingException, FileSystemAccessException;

	/**
	 * To convert the EAIMessage object to raw XML string
	 *
	 * @param stpMessage the EAIMessage object
	 * @return the raw XML string converted from the EAIMessage supplied
	 * @throws com.integrosys.cms.host.eai.XmlMappingException if there is any error in marshalling process
	 * @throws com.integrosys.cms.host.eai.FileSystemAccessException if the mapping required to unmarshall
	 *         rawMessage doesn't exist.
	 */
	public String marshall(STPMessage stpMessage) throws XmlMappingException, FileSystemAccessException;
}