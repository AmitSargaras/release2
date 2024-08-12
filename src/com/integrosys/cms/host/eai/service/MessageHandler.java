package com.integrosys.cms.host.eai.service;

import java.util.Collections;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.host.eai.EAIHeader;
import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.EAIMessageException;
import com.integrosys.cms.host.eai.EAIMessageValidationException;
import com.integrosys.cms.host.eai.FileSystemAccessException;
import com.integrosys.cms.host.eai.Message;
import com.integrosys.cms.host.eai.RecordAlreadyExistsException;
import com.integrosys.cms.host.eai.XmlMappingException;
import com.integrosys.cms.host.eai.core.AbstractPostProcessMessageHandler;
import com.integrosys.cms.host.eai.response.Response;
import com.integrosys.cms.host.eai.response.ResponseMessageBody;
import com.integrosys.cms.host.eai.support.DataAccessExceptionUtils;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.IEAIResponseConstant;
import com.integrosys.cms.host.eai.support.IMessageFolder;
import com.integrosys.cms.host.eai.support.MessageHandlerObserver;
import com.integrosys.cms.host.eai.support.MessageHandlerSubject;
import com.integrosys.cms.host.eai.support.MessageHolder;

/**
 * Message Handler to prepare the xml mapping resource and passed into the
 * message manager to process, in return a message object will be retrieved, and
 * possible to be marshall if it's a inquiry message.
 * 
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 */
public class MessageHandler implements IEAIHeaderConstant, MessageHandlerSubject {

	private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	private Map msgIdObserverMap = Collections.synchronizedMap(new Hashtable());

	private Map msgIdPostProcessMsgHandlerMap;

	private IMessageFolder messageFolder;

	private IEaiMessageManager eaiMessageManager;

	private Set requiredLoggedExceptionClasses;

	private List responseRequiredMessageIdList;

	/**
	 * <p>
	 * To set the list of message id which required to directly return the
	 * processed message as response to the caller.
	 * 
	 * <p>
	 * Normally for those inquiry message, the message id must be in this list.
	 * 
	 * @param responseRequiredMessageIdList the list of message id required
	 *        response back to the caller.
	 */
	public void setResponseRequiredMessageIdList(List responseRequiredMessageIdList) {
		this.responseRequiredMessageIdList = responseRequiredMessageIdList;
	}

	/**
	 * <p>
	 * To set a message folder to be used to hold the message, backed by id. Can
	 * be a polling message folder, which return the last message to be
	 * processed.
	 * <p>
	 * Message will get cleared after the processing.
	 * @param messageFolder message folder holding all the messages to be
	 *        processed.
	 */
	public void setMessageFolder(IMessageFolder messageFolder) {
		this.messageFolder = messageFolder;
	}

	/**
	 * To set the message manager with do the real logic on processing the
	 * message that this class handle.
	 * @param eaiMessageManager message manager
	 */
	public void setEaiMessageManager(IEaiMessageManager eaiMessageManager) {
		this.eaiMessageManager = eaiMessageManager;
	}

	/**
	 * <p>
	 * key is the message id, value is the post process message handler, which
	 * must be the subclass of {@link AbstractPostProcessMessageHandler}
	 * <p>
	 * The post process message handler will be invoked after the
	 * {@link IEaiMessageManager#process(EAIMessage)} has finished process, ie
	 * just after all the domain, workflow data has been persisted into storage
	 * system.
	 * 
	 * @param msgIdPostProcessMsgHandlerMap the key is the message id, value is
	 *        the post process message handler
	 */
	public void setMsgIdPostProcessMsgHandlerMap(Map msgIdPostProcessMsgHandlerMap) {
		this.msgIdPostProcessMsgHandlerMap = msgIdPostProcessMsgHandlerMap;
	}

	/**
	 * To set the subclass of <tt>java.lang.Exception</tt> which required to be
	 * logged when we catch a exception when process the message.
	 * @param requiredLoggedExceptionClasses a set of subclass of
	 *        <tt>java.lang.Exception</tt>
	 */
	public void setRequiredLoggedExceptionClasses(Set requiredLoggedExceptionClasses) {
		this.requiredLoggedExceptionClasses = requiredLoggedExceptionClasses;
	}

	/**
	 * <p>
	 * Processed the raw XML stream, and return the message object if required.
	 * 
	 * <p>
	 * This include to prepare which mapping resource to be used to unmarshall
	 * the XML stream to javabeans for further processing. And also the
	 * preparation of the response message.
	 * 
	 * @param message the raw XML stream
	 * @return processed messsage of the raw XML stream, or just merely the
	 *         acknowledgment reply.
	 */
	public Message process(String message) {

		String messageId = EAIHeaderHelper.getHeaderValue(message, IEAIHeaderConstant.MESSAGE_ID);
		String messageReferenceNumber = EAIHeaderHelper.getHeaderValue(message, IEAIHeaderConstant.MESSAGE_REF_NUM);

		EAIMessage processedMessage = new EAIMessage();
		try {
			processedMessage = (EAIMessage) this.eaiMessageManager.process(message);
		}
		catch (EAIMessageValidationException ex) {
			String errorCode = ex.getErrorCode();

			return formulateResponseMessage(MESSAGE_TYPE_ACKNOWLEDGMENT, RESPONSE_AC001, messageReferenceNumber,
					((errorCode == null) ? String.valueOf(IEAIResponseConstant.ERROR_VALIDATION) : errorCode), ex
							.getMessage());
		}
		catch (FileSystemAccessException ex) {
			return formulateResponseMessage(MESSAGE_TYPE_ACKNOWLEDGMENT, RESPONSE_AC001, messageReferenceNumber, ex
					.getErrorCode(), ex.getMessage());
		}
		catch (XmlMappingException ex) {
			String errorCode = ex.getErrorCode();

			return formulateResponseMessage(MESSAGE_TYPE_ACKNOWLEDGMENT, RESPONSE_AC001, messageReferenceNumber,
					((errorCode == null) ? String.valueOf(IEAIResponseConstant.TECHNICAL_ERROR_CODE) : errorCode), ex
							.getMessage());
		}
		catch (RecordAlreadyExistsException ex) {
			return formulateResponseMessage(MESSAGE_TYPE_ACKNOWLEDGMENT, RESPONSE_AC001, messageReferenceNumber, ex
					.getErrorCode(), ex.getMessage());
		}
		catch (EAIMessageException ex) {
			logger.error("failed to process eai message, message id [" + messageId + "] message reference ["
					+ messageReferenceNumber + "], full info: " + ExceptionUtils.getFullStackTrace(ex));
			Throwable[] throwables = ExceptionUtils.getThrowables(ex);
			for (int i = 0; i < throwables.length; i++) {
				for (Iterator itr = this.requiredLoggedExceptionClasses.iterator(); itr.hasNext();) {
					Class exceptionClass = (Class) itr.next();
					if (exceptionClass.isAssignableFrom(throwables[i].getClass())) {
						logger.warn("failed process for : message id [" + messageId + "] message reference ["
								+ messageReferenceNumber + "], interested cause: " + throwables[i]);
					}
				}
			}

			String errorCode = ex.getErrorCode();

			return formulateResponseMessage(MESSAGE_TYPE_ACKNOWLEDGMENT, RESPONSE_AC001, messageReferenceNumber,
					((errorCode == null) ? String.valueOf(IEAIResponseConstant.TECHNICAL_ERROR_CODE) : errorCode), ex
							.getMessage());
		}
		catch (Throwable t) {
			logger.error("unexpected error occur, message id [" + messageId + "] message reference ["
					+ messageReferenceNumber + "], full info: " + ExceptionUtils.getFullStackTrace(t));
			Throwable[] throwables = ExceptionUtils.getThrowables(t);
			for (int i = 0; i < throwables.length; i++) {
				for (Iterator itr = this.requiredLoggedExceptionClasses.iterator(); itr.hasNext();) {
					Class exceptionClass = (Class) itr.next();
					if (exceptionClass.isAssignableFrom(throwables[i].getClass())) {
						logger.warn("failed process for : message id [" + messageId + "] message reference ["
								+ messageReferenceNumber + "], interested cause: " + throwables[i]);
					}
				}
			}

			if (t instanceof DataAccessException) {
				// might be the case that transaction is rollbacked when
				// committing hit jdbc error.
				EAIMessageException ex = DataAccessExceptionUtils.handleDataAccessException((DataAccessException) t);

				String errorCode = ex.getErrorCode();

				return formulateResponseMessage(MESSAGE_TYPE_ACKNOWLEDGMENT, RESPONSE_AC001, messageReferenceNumber,
						((errorCode == null) ? String.valueOf(IEAIResponseConstant.TECHNICAL_ERROR_CODE) : errorCode),
						ex.getMessage());
			}

			return formulateResponseMessage(MESSAGE_TYPE_ACKNOWLEDGMENT, RESPONSE_AC001, messageReferenceNumber, String
					.valueOf(IEAIResponseConstant.TECHNICAL_ERROR_CODE), t.getMessage());
		}

		if (this.msgIdPostProcessMsgHandlerMap.get(processedMessage.getMsgHeader().getMessageId()) != null) {
			logger.info("There is post process handler for message id ["
					+ processedMessage.getMsgHeader().getMessageId() + "], message reference ["
					+ processedMessage.getMsgHeader().getMessageRefNum() + "], processing.");

			AbstractPostProcessMessageHandler postProcessMessageHandler = (AbstractPostProcessMessageHandler) this.msgIdPostProcessMsgHandlerMap
					.get(processedMessage.getMsgHeader().getMessageId());
			postProcessMessageHandler.processMessage(processedMessage);
		}

		if (this.responseRequiredMessageIdList.contains(messageId)) {
			return processedMessage;
		}

		return formulateResponseMessage(MESSAGE_TYPE_ACKNOWLEDGMENT, RESPONSE_AC001, messageReferenceNumber,
				IEAIResponseConstant.ACK_GOOD, "");
	}

	/**
	 * Prepare the response message to be given back to the caller, then caller
	 * can based on the response message, to prepare whatever response format to
	 * the client.
	 * 
	 * @param messageType message type of the XML message
	 * @param messageId message id of the XML message
	 * @param messageReferenceNumber message reference number of the XML
	 *        message, which is the key between client and the server.
	 * @param errorCode the error code to be put into the response message
	 * @param errorMessage the error message corresponding to the error code
	 * @return prepared response message ready to be passed back to caller.
	 */
	protected EAIMessage formulateResponseMessage(String messageType, String messageId, String messageReferenceNumber,
			String errorCode, String errorMessage) {

		EAIMessage responseMessage = new EAIMessage();

		EAIHeader msgHeader = new EAIHeader();
		msgHeader.setMessageId(messageId);
		msgHeader.setMessageRefNum(messageReferenceNumber);
		msgHeader.setMessageType(messageType);
		msgHeader.setPublishDate(DateFormatUtils.SMTP_DATETIME_FORMAT.format(new Date()));
		msgHeader.setPublishType(IEAIHeaderConstant.PUB_TYPE_NORMAL.toUpperCase());
		msgHeader.setSource(ICMSConstant.INSTANCE_CMS);

		responseMessage.setMsgHeader(msgHeader);

		Response msgResponse = new Response();
		msgResponse.setResponseCode(errorCode);
		msgResponse.setResponseMessage(errorMessage);

		ResponseMessageBody msgBody = new ResponseMessageBody();
		msgBody.setResponse(msgResponse);

		responseMessage.setMsgBody(msgBody);

		return responseMessage;
	}

	public void register(String msgId, MessageHandlerObserver observer) {
		throw new UnsupportedOperationException("Currently not support, please use #registerAndProcess instead");
	}

	public void registerAndProcess(String msgId, MessageHandlerObserver observer) {
		msgIdObserverMap.put(msgId, observer);
		logger.debug("subcribing msg observer [" + observer + "], msgId [" + msgId + "]");

		run(msgId);
	}

	protected void run(String msgId) {
		Validate.notNull(msgId, "'msgId' supplied must not be null.");

		MessageHolder messageHolder = (MessageHolder) this.messageFolder.popMessageByMsgId(msgId);
		if (messageHolder == null) {
			logger.warn("there is no message holder this round but run() was initiated.");
			throw new IllegalStateException("no message holder for msgId [" + msgId + "].");
		}

		if (!msgId.equals(messageHolder.getMsgId())) {
			throw new IllegalStateException("msg id supplied [" + msgId
					+ "] is not tally with the msg id in the message holder [" + messageHolder.getMsgId() + "]");
		}

		String xmlMessage = (String) messageHolder.getMessage();
		MessageHandlerObserver observer = (MessageHandlerObserver) msgIdObserverMap.get(msgId);
		if (observer == null) {
			throw new IllegalStateException("There is no observer subscribe to this subject, for msgId [" + msgId
					+ "], will discontinue the process.");
		}

		logger.debug("Processing msg internally, msg Id [" + msgId + "]");
		String currentThreadName = Thread.currentThread().getName();

		String messageReference = EAIHeaderHelper.getHeaderValue(xmlMessage, IEAIHeaderConstant.MESSAGE_REF_NUM);
		Thread.currentThread().setName("MessageProcess_" + messageReference);
		Message returnedMsg = process(xmlMessage);
		Thread.currentThread().setName(currentThreadName);

		this.eaiMessageManager.logMessage((EAIMessage) returnedMsg, xmlMessage, messageHolder.getMessageDescription());

		observer.update(returnedMsg);
		msgIdObserverMap.remove(msgId);
		logger.info("removing msg observer [" + observer + "], msgId [" + msgId + "]");
	}
}
