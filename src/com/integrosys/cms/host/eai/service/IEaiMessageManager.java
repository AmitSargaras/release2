package com.integrosys.cms.host.eai.service;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.MessageReader;
import com.integrosys.cms.host.eai.log.ILogMessage;

/**
 * Message Manager to process the message and return the message which is
 * processed, which mean for those inquiry message, processed message will be
 * well prepared, and ready to be marshalled.
 * 
 * @author Chong Jun Yong
 * 
 */
public interface IEaiMessageManager {

	/**
	 * To process the message and return the message at the same time by
	 * respective message handler
	 * 
	 * @param rawXmlStream the raw xml stream
	 * @return processed message from the respective message handler
	 * @throws EAIMessageException if there is any error occur, such as
	 *         validation
	 */
	public Message process(String rawXmlStream) throws EAIMessageException;

	/**
	 * To process the message and return the message at the same time by
	 * respective message handler
	 * 
	 * @param reader the reader contain the xml stream
	 * @return processed message from the respective message handler
	 * @throws EAIMessageException if there is any error occur, such as
	 *         validation
	 */
	public Message process(MessageReader reader) throws EAIMessageException;

	/**
	 * To process the message and return the message at the same time by
	 * respective message handler
	 * 
	 * @param eaiMessage already unmarshalled message from the xml stream
	 * @return processed message from the respective message handler
	 * @throws EAIMessageException if there is any error occur, such as
	 *         validation
	 */
	public Message process(EAIMessage eaiMessage) throws EAIMessageException;

	/**
	 * To log the message info into persistent storage
	 * 
	 * @param logMsg the message to be log into persistent storage
	 * @throws EAIMessageException if there is any error occur
	 */
	public void logMessage(ILogMessage logMsg) throws EAIMessageException;

	/**
	 * To log message info prepared by using the processed message and input
	 * supplied
	 * 
	 * @param processedMessage a processed message after accept the input
	 * @param inputRawMessage input message in raw format
	 * @param inputRawMessagePath the file path of the input raw message
	 * @throws EAIMessageException if there is any error occur
	 */
	public void logMessage(EAIMessage processedMessage, String inputRawMessage, String inputRawMessagePath)
			throws EAIMessageException;

}
