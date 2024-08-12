package com.integrosys.cms.host.eai.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.FileSystemAccessException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.MessageReader;
import com.integrosys.cms.host.eai.core.IEaiConstant;
import com.integrosys.cms.host.eai.core.IMessageHandler;
import com.integrosys.cms.host.eai.core.MessageMarshallerFactory;
import com.integrosys.cms.host.eai.log.EAIMessageLog;
import com.integrosys.cms.host.eai.log.ILogMessage;
import com.integrosys.cms.host.eai.log.ILogMessageJdbc;
import com.integrosys.cms.host.eai.response.ResponseMessageBody;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.cms.host.eai.support.EAIMessageSynchronizationManager;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.IEAIResponseConstant;
import com.integrosys.cms.host.eai.support.IMessageFolder;
import com.integrosys.cms.host.eai.support.MessageDate;
import com.integrosys.cms.host.eai.support.MessageHolder;

/**
 * <p>
 * Central processing unit to unmarshall XML to Message Object and route to
 * Respective Message Handler to do the business logics.
 * 
 * <p>
 * XML message will be routed to {@link IMessageHandler} for futher process,
 * which all the message handlers are stored in Map and backed by message type
 * string.
 * 
 * <p>
 * The flow is to call {@link IMessageHandler#processMessage(EAIMessage)} then
 * follow by the {@link IMessageHandler#postprocess(Message, Message, Map)}.
 * Post process normally do the workflow update. For the inquiry message, the
 * MessageHandler need not to take action on the message object, just straight
 * away return it enough.
 * 
 * <p>
 * Message type available
 * <ul>
 * <li>CUSTOMER
 * <li>CUSTOMER.UPDATE
 * <li>CUSTOMER.FUSION
 * <li>CUSTOMER.SEARCH
 * <li>CUSTOMER.RELATIONSHIP
 * <li>LIMIT
 * <li>CA.LISTING.INQUIRY
 * <li>CA.INQUIRY
 * <li>SHARED.SECURITY
 * <li>SECURITY
 * <li>SECURITY.INQUIRY
 * <li>DOCUMENT
 * <li>COVENANT
 * <li>DOCUMENT.INQUIRY
 * <li>TEMPLATE.INQUIRY
 * <li>COVENANT.INQUIRY
 * </ul>
 * 
 * @author Chong Jun Yong
 * @since 12.08.2008
 * 
 */
public class EaiMessageManagerImpl implements IEaiMessageManager {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private Map messageHandlerMap;

	private ILogMessageJdbc logMessageJdbc;

	private MessageMarshallerFactory messageMarshallerFactory;

	private IMessageFolder messageFolder;

	private String[] messageTypesRequiredLogToFileSystem;

	private boolean logMessageForNonErrorAcknowledgment = false;

	/**
	 * <p>
	 * Message folder to be used by this message manager to store the incoming
	 * and outgoing message into the folder, mainly would be a file system based
	 * message folder.
	 * <p>
	 * After store, then a path of the message will be retrieved for logging
	 * purpose.
	 * @param messageFolder message folder object to be used for store the
	 *        incoming or outgoing message.
	 */
	public void setMessageFolder(IMessageFolder messageFolder) {
		this.messageFolder = messageFolder;
	}

	/**
	 * To set the Jdbc Dao to persist the message log
	 * @param logMessageJdbc jdbc dao for message log persistence
	 */
	public void setLogMessageJdbc(ILogMessageJdbc logMessageJdbc) {
		this.logMessageJdbc = logMessageJdbc;
	}

	/**
	 * <p>
	 * The map contain the handler bean to process the message sent from message
	 * dispatcher. So that we can process the message correctly based on the
	 * message type in the message.
	 * <p>
	 * key will be the message type, value is the service bean, the handler to
	 * process the message.
	 * @param messageHandlerMap key is the message, value is the
	 *        <tt>IMessageHandler</tt> service bean
	 */
	public void setMessageHandlerMap(Map messageHandlerMap) {
		this.messageHandlerMap = messageHandlerMap;
	}

	/**
	 * The message marshaller or unmarshaller to translate the EAI Message from
	 * object to raw string.
	 * @param messageMarshallerFactory message marshaller factory to be used to
	 *        unmarshal or marshal
	 */
	public void setMessageMarshallerFactory(MessageMarshallerFactory messageMarshallerFactory) {
		this.messageMarshallerFactory = messageMarshallerFactory;
	}

	/**
	 * <p>
	 * Based on the message type provided, to check whether the incoming and
	 * outgoing message should be persisted into file system.
	 * 
	 * @param messageTypesRequiredLogToFileSystem values of the message type
	 *        required log to file system.
	 */
	public void setMessageTypesRequiredLogToFileSystem(String[] messageTypesRequiredLogToFileSystem) {
		this.messageTypesRequiredLogToFileSystem = messageTypesRequiredLogToFileSystem;
	}

	/**
	 * <p>
	 * To set whether non error acknowledgment (outgoing) message should be log
	 * into the file system.
	 * <p>
	 * ie, for those outgoing message having <tt>ResponseMessageBody</tt>
	 * message body, check whether the response code is 0 or not, if is 0, and
	 * this property is true, then we log the message into file system.
	 * <p>
	 * Default is <b>false</b> as not to have so many similiar outgoing message
	 * appear in the file system.
	 * @param logMessageForNonErrorAcknowledgment whether non error
	 *        acknowledgment (outgoing) message should be log into the file
	 *        system.
	 */
	public void setLogMessageForNonErrorAcknowledgment(boolean logMessageForNonErrorAcknowledgment) {
		this.logMessageForNonErrorAcknowledgment = logMessageForNonErrorAcknowledgment;
	}

	protected IMessageHandler getMessageHandlerByMessageType(String messageType) {
		return (IMessageHandler) this.messageHandlerMap.get(messageType);
	}

	public Message process(String rawXmlStream) throws EAIMessageException {
		EAIMessage eaiMessage = this.messageMarshallerFactory.unmarshall(rawXmlStream);
		return process(eaiMessage);
	}

	public Message process(MessageReader reader) throws EAIMessageException {
		BufferedReader bufReader = new BufferedReader(reader.getStringReader());
		StringBuffer msgBuf = new StringBuffer();
		try {
			String line = bufReader.readLine();
			while (line != null) {
				msgBuf.append(line);
				line = bufReader.readLine();
			}
		}
		catch (IOException ex) {
			throw new FileSystemAccessException("failed to read the raw xml stream", ex);
		}
		finally {
			try {
				bufReader.close();
			}
			catch (IOException ex) {
				logger.warn("failed to close buffer reader, ignored", ex);
			}
		}

		EAIMessage eaiMessage = this.messageMarshallerFactory.unmarshall(msgBuf.toString());
		return process(eaiMessage);
	}

	/**
	 * The central place to process the message.
	 * 
	 * <ol>
	 * <li>To consume the message object, deal with domain object, and at the
	 * same time retrieve workflow/transaction value
	 * <li>to work on the workflow/transaction value, to create/update/delete
	 * the transaction value
	 * </ol>
	 * 
	 * @param eaiMessage the eai message object unmarshalled from the raw XML
	 *        stream
	 * @return process eai message, whether the response for the inquiry or the
	 *         processed message with all the keys input
	 * @throws EAIMessageException if there is any error occur in processing
	 */
	public Message process(EAIMessage eaiMessage) throws EAIMessageException {
		String messageSource = eaiMessage.getMsgHeader().getSource();
		EAIMessageSynchronizationManager.setMessageSource(messageSource);

		String msgType = eaiMessage.getMsgHeader().getMessageType();
		msgType = msgType.toUpperCase();

		IMessageHandler messageHandler = getMessageHandlerByMessageType(msgType);
		if (messageHandler == null) {
			throw new InvalidMessageTypeException(eaiMessage.getMsgHeader());
		}

		try {
			// process message
			Properties messageContext = messageHandler.processMessage(eaiMessage);

			// post process message from the context
			Message msg = (Message) messageContext.get(IEaiConstant.MSG_OBJ);
			Message stagmsg = (Message) messageContext.get(IEaiConstant.STAG_MSG_OBJ);
			HashMap flatMessage = (HashMap) messageContext.get(IEaiConstant.FLATMSG_OBJ);

			if (msg != null) {
				messageHandler.postprocess(msg, stagmsg, flatMessage);
				return msg;
			}
		}
		finally {
			EAIMessageSynchronizationManager.clear();
		}

		return null;
	}

	public void logMessage(ILogMessage logMsg) {
		this.logMessageJdbc.persistLogMessage(logMsg);
	}

	public void logMessage(EAIMessage processedMessage, String inputRawMessage, String inputRawMessagePath) {
		Date publishDate = MessageDate.getInstance().getDate(
				EAIHeaderHelper.getHeaderValue(inputRawMessage, IEAIHeaderConstant.PUBLISH_DATE));
		String messageId = EAIHeaderHelper.getHeaderValue(inputRawMessage, IEAIHeaderConstant.MESSAGE_ID);
		String publishType = EAIHeaderHelper.getHeaderValue(inputRawMessage, IEAIHeaderConstant.PUBLISH_TYPE);
		String source = EAIHeaderHelper.getHeaderValue(inputRawMessage, IEAIHeaderConstant.EAIHDR_SOURCE);
		String messageType = EAIHeaderHelper.getHeaderValue(inputRawMessage, IEAIHeaderConstant.MESSAGE_TYPE);

		EAIMessageLog messageLog = new EAIMessageLog();
		messageLog.setMessageReferenceNumber(processedMessage.getMsgHeader().getMessageRefNum());
		messageLog.setMessageType(messageType);
		messageLog.setMessageId(messageId);
		messageLog.setPublishType(publishType);
		messageLog.setPublishDate(publishDate);
		messageLog.setSource(source);
		messageLog.setResponseMessageId(processedMessage.getMsgHeader().getMessageId());
		messageLog.setEndProcessedDate(new Date());

		if (ArrayUtils.contains(this.messageTypesRequiredLogToFileSystem, messageType)) {
			String inputMessageId = this.messageFolder.putMessage(inputRawMessage);
			MessageHolder inputMessageHolder = (MessageHolder) this.messageFolder.popMessageByMsgId(inputMessageId);
			messageLog.setReceivedMessagePath(inputMessageHolder.getMessageDescription());
		}

		if (processedMessage.getMsgBody() instanceof ResponseMessageBody) {
			ResponseMessageBody responseBody = (ResponseMessageBody) processedMessage.getMsgBody();
			if (responseBody.getResponse() != null) {
				messageLog.setResponseCode(responseBody.getResponse().getResponseCode());
				messageLog.setResponseDescription(responseBody.getResponse().getResponseMessage());

				if (IEAIResponseConstant.ACK_GOOD.equals(responseBody.getResponse().getResponseCode())
						&& this.logMessageForNonErrorAcknowledgment
						&& ArrayUtils.contains(this.messageTypesRequiredLogToFileSystem, messageType)) {

					// for the purpose of retrieve file name, and also
					// persist the raw processed message into file system
					String rawProcessedMessage = this.messageMarshallerFactory.marshall((EAIMessage) processedMessage);
					String processedMessageId = this.messageFolder.putMessage(rawProcessedMessage);
					MessageHolder messageHolder = (MessageHolder) this.messageFolder
							.popMessageByMsgId(processedMessageId);

					messageLog.setResponseMessagePath(messageHolder.getMessageDescription());
				}
			}
		}

		try {
			this.logMessageJdbc.persistEAILogMessage(messageLog);
		}
		catch (Throwable t) {
			logger.warn("failed to persist eai log message, please verify", t);
		}
	}

	/**
	 * <p>
	 * Inner class exception to be raised where there is invalid message type
	 * provided.
	 * <p>
	 * Basically, for the message type, if there is no message handler
	 * registered, will throw this excecption, instead of throwing null pointer
	 * exception.
	 */
	private class InvalidMessageTypeException extends EAIMessageValidationException {

		private static final long serialVersionUID = 2276324595541488706L;

		private static final String ERROR_CODE = "INV_MSG_TYPE";

		public InvalidMessageTypeException(EAIHeader header) {
			super("Message type [" + header.getMessageType()
					+ "] not recognized in the system, for message reference [" + header.getMessageRefNum() + "]");
		}

		public String getErrorCode() {
			return ERROR_CODE;
		}
	}
}
