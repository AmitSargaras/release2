package com.integrosys.cms.host.eai.support;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAIProcessFailedException;
import com.integrosys.cms.host.eai.FileSystemAccessException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.core.MessageMarshallerFactory;
import com.integrosys.cms.host.eai.service.MessageHandler;

/**
 * <p>
 * Message Handler invoker to be used to invoke the handler which is prepared at
 * the first place by the application context. This mean, all the service bean
 * must be configured at the first place in order to invoke the handler.
 * 
 * <p>
 * Processed message by the handler will be marshall to xml stream in string,
 * which is ready to be output at the caller side.
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 */
public class MessageHandlerInvoker implements IEAIHeaderConstant {

	/**
	 * Providing to run the eai tester directly from the console, provide 1
	 * argument which is the full path of the xml file to be loaded for the
	 * processing.
	 * 
	 * @param args array of the argument, must has only 1 argument which is the
	 *        full path name of the xml file.
	 */
	public static void main(String[] args) {
		String xmlFileName = args[0];

		String response = processFile(xmlFileName);
	}

	/**
	 * Process the file object and output the response (which is marshalled from
	 * the response message object after processed).
	 * 
	 * @param file the xml file object
	 * @return the response of the processing on the xml file
	 * @throws EAIMessageException if there is any fatal error occurred.
	 */
	public static String processFileObject(File file) throws EAIMessageException {
		try {
			RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
			byte[] fileBytes = new byte[(int) randomAccessFile.length()];
			randomAccessFile.read(fileBytes);
			String xmlStr = new String(fileBytes);

			randomAccessFile.close();
			return processXmlMsg(xmlStr);
		}
		catch (FileNotFoundException fe) {
			throw new FileSystemAccessException("unable to locate the file [" + file + "].", fe);
		}
		catch (IOException ioe) {
			throw new FileSystemAccessException("failed to access xml file [" + file + "].", ioe);
		}
	}

	/**
	 * Process the file, given the full path of the file, and output the
	 * response (which is marshalled from the response message object after
	 * processed).
	 * 
	 * @param xmlFileFullPath full file path the xml file
	 * @return the response of the processing on the xml file
	 * @throws EAIMessageException if there is any fatal error occurred.
	 */
	public static String processFile(String xmlFileFullPath) throws EAIMessageException {
		File xmlFile = new File(xmlFileFullPath);

		return processFileObject(xmlFile);
	}

	/**
	 * Process the xml stream and output the response (which is marshalled from
	 * the response message object after processed).
	 * 
	 * @param msg the xml stream in string representation
	 * @return the response of the processing on the xml file
	 * @throws EAIMessageException if there is any fatal error occurred.
	 */
	public static String processXmlMsg(String msg) throws EAIMessageException {
		Message responseMessage = null;
		String msgId = EAIHeaderHelper.getHeaderValue(msg, MESSAGE_ID);
		String msgRefNum = EAIHeaderHelper.getHeaderValue(msg, IEAIHeaderConstant.MESSAGE_REF_NUM);

		try {
			responseMessage = processMessage(msg);
		}
		catch (Throwable t) {
			throw new EAIProcessFailedException("unexpected error occured for message id [" + msgId
					+ "] message ref num [" + msgRefNum + "]", t);
		}

		return prepareResponseMessage(responseMessage);
	}

	/**
	 * Based on the response message object, marshall the object into xml stream
	 * based on the xml mapping resource
	 * 
	 * @param responseMessage the response message object to be marhsalled
	 * @return response message object in xml stream format
	 * @throws EAIMessageException if there is any fatal error occurred
	 */
	protected static String prepareResponseMessage(Message responseMessage) throws EAIMessageException {
		MessageMarshallerFactory marshallerFactory = (MessageMarshallerFactory) BeanHouse
				.get("messageMarshallerFactory");

		return marshallerFactory.marshall((EAIMessage) responseMessage);
	}

	/**
	 * Process the xml stream using message handler, getting from the context
	 * 
	 * @param msg the xml stream msg
	 * @return processed message object
	 */
	protected static Message processMessage(String msg) {
		MessageHandler messageHandler = (MessageHandler) BeanHouse.get("messageHandler");

		return messageHandler.process(msg);

	}
}
