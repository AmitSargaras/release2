package com.integrosys.cms.host.stp.services;


import com.integrosys.cms.host.stp.IMessage;
import com.integrosys.cms.host.stp.STPHeader;
import com.integrosys.cms.host.stp.STPMessageException;
import com.integrosys.cms.host.stp.log.ILogMessage;

/**
 * Message Manager to process the message and return the message which is
 * processed, which mean for those inquiry message, processed message will be
 * well prepared, and ready to be marshalled.
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public interface IStpMessageManager {

	/**
	 * To process the message and return the message at the same time by
	 * respective message handler
	 *
	 * @param rawXmlStream the raw xml stream
	 * @return processed message from the respective message handler
	 * @throws com.integrosys.cms.host.eai.EAIMessageException if there is any error occur, such as
	 *         validation
	 */
	//public STPMessage process(String rawXmlStream) throws STPMessageException;

	/**
	 * To process the message and return the message at the same time by
	 * respective message handler
	 *
	 * @param reader the reader contain the xml stream
	 * @return processed message from the respective message handler
	 * @throws com.integrosys.cms.host.eai.EAIMessageException if there is any error occur, such as
	 *         validation
	 */
	//public STPMessage process(MessageReader reader) throws STPMessageException;

	/**
	 * To process the message and return the message at the same time by
	 * respective message handler
	 *
	 * @param message already unmarshalled message from the xml stream
	 * @return processed message from the respective message handler
	 * @throws com.integrosys.cms.host.eai.EAIMessageException if there is any error occur, such as
	 *         validation
	 */
	//public STPMessage process(STPMessage message) throws STPMessageException;

	/**
	 * To log the message info into persistent storage
	 *
	 * @param logMsg the message to be log into persistent storage
	 * @throws com.integrosys.cms.host.eai.EAIMessageException if there is any error occur
	 */
	public void logMessage(ILogMessage logMsg) throws STPMessageException;

	/**
	 * To log message info prepared by using the processed message and input
	 * supplied
	 *
	 * @param requestXML a processed message after accept the input
	 * @param responseXML input message in raw format
	 * @param requestHeader the file path of the input raw message
	 * @throws com.integrosys.cms.host.eai.EAIMessageException if there is any error occur
	 */
	//public void logMessage(STPMessage processedMessage, String inputRawMessage, String inputRawMessagePath) throws STPMessageException;

   // public void logMessage(String intputXML, String outputXML) throws STPMessageException;
    public void logMessage(String requestXML, String responseXML, STPHeader requestHeader, IMessage responseMsg, String correlationID, Long masterTrxId);
}