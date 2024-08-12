package com.integrosys.cms.host.stp.services;

import com.integrosys.cms.host.eai.support.IEAIResponseConstant;
import com.integrosys.cms.host.eai.support.IMessageFolder;
import com.integrosys.cms.host.eai.support.MessageDate;
import com.integrosys.cms.host.eai.support.MessageHolder;
import com.integrosys.cms.host.stp.*;
import com.integrosys.cms.host.stp.log.ILogMessage;
import com.integrosys.cms.host.stp.log.ISTPLogMessageJdbc;
import com.integrosys.cms.host.stp.log.MessageLog;
import com.integrosys.cms.host.stp.services.IStpMessageManager;
import com.integrosys.cms.host.stp.trade.TradeResponseBody;
import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * <p>
 * Central processing unit to unmarshall XML to Message Object and route to
 * Respective Message Handler to do the business logics.
 *
 * <p>
 * XML message will be routed to {@link com.integrosys.cms.host.eai.core.IMessageHandler} for futher process,
 * which all the message handlers are stored in Map and backed by message type
 * string.
 *
 * <p>
 * The flow is to call {@link com.integrosys.cms.host.eai.core.IMessageHandler#processMessage(com.integrosys.cms.host.eai.EAIMessage)} then
 * follow by the {@link com.integrosys.cms.host.eai.core.IMessageHandler#postprocess(com.integrosys.cms.host.eai.Message, com.integrosys.cms.host.eai.Message, java.util.Map)}.
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
public class StpMessageManagerImpl implements IStpMessageManager {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ISTPLogMessageJdbc logMessageJdbc;

	private IMessageFolder messageFolder;

	private String[] messageTypesRequiredLogToFileSystem;

	private boolean logMessageForNonErrorAcknowledgment = false;

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String[] getMessageTypesRequiredLogToFileSystem() {
        return messageTypesRequiredLogToFileSystem;
    }

    public boolean isLogMessageForNonErrorAcknowledgment() {
        return logMessageForNonErrorAcknowledgment;
    }

    public IMessageFolder getMessageFolder() {
        return messageFolder;
    }

    public ISTPLogMessageJdbc getLogMessageJdbc() {
        return logMessageJdbc;
    }

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
	public void setLogMessageJdbc(ISTPLogMessageJdbc logMessageJdbc) {
		this.logMessageJdbc = logMessageJdbc;
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

    public void logMessage(ILogMessage logMsg)throws STPMessageException {
		this.logMessageJdbc.persistLogMessage(logMsg);
	}

    public void logMessage(String requestXML, String responseXML, STPHeader requestHeader, IMessage responseMsg, String correlationId, Long masterTrxId){

        MessageLog messageLog = new MessageLog();

        messageLog.setMessageReferenceNumber(requestHeader.getMessageRefNum());
        messageLog.setMessageType(requestHeader.getMessageType());
        messageLog.setMessageId(requestHeader.getMessageId());
        //messageLog.setPublishType(requestHeader.getPublishType());
        //messageLog.setPublishDate(MessageDate.getInstance().getDate(requestHeader.getPublishDate()));
        messageLog.setSource(requestHeader.getSource());
        messageLog.setCorrelationId(correlationId);
        messageLog.setMasterTrxId(masterTrxId);

        messageLog.setEndProcessedDate(new Date());

        if (ArrayUtils.contains(this.messageTypesRequiredLogToFileSystem, requestHeader.getMessageType())) {
			String requestMessageId = this.messageFolder.putMessage(requestXML);
			MessageHolder requestMessageHolder = (MessageHolder) this.messageFolder.popMessageByMsgId(requestMessageId);
			messageLog.setRequestMessagePath(requestMessageHolder.getMessageDescription());
		}

        if (responseMsg != null && responseMsg.getMsgBody() != null && responseMsg.getMsgBody() instanceof TradeResponseBody) {
			TradeResponseBody responseBody = (TradeResponseBody) responseMsg.getMsgBody();

            messageLog.setResponseMessageId(responseMsg.getMsgHeader().getMessageId());
            messageLog.setResponseCode(responseBody.getResponseCode());
            messageLog.setResponseDescription(responseBody.getResponseMessage());
                                       String a = responseBody.getResponseCode();
            if (IEAIResponseConstant.ACK_GOOD.equals(responseBody.getResponseCode())
              //      && this.logMessageForNonErrorAcknowledgment
                    && ArrayUtils.contains(this.messageTypesRequiredLogToFileSystem, requestHeader.getMessageType())) {

                // for the purpose of retrieve file name, and also
                // persist the raw processed message into file system
                String responseMessageId = this.messageFolder.putMessage(responseXML);
                MessageHolder messageHolder = (MessageHolder) this.messageFolder.popMessageByMsgId(responseMessageId);

                messageLog.setResponseMessagePath(messageHolder.getMessageDescription());
            }
		}

        try {
			this.logMessageJdbc.persistSTPLogMessage(messageLog);
		}
		catch (Throwable t) {
			logger.warn("failed to persist stp log message, please verify", t);
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
	private class InvalidMessageTypeException extends STPMessageValidationException {

		private static final long serialVersionUID = 2276324595541488706L;

		private static final String ERROR_CODE = "INV_MSG_TYPE";

		public InvalidMessageTypeException(STPHeader header) {
			super("Message type [" + header.getMessageType()
					+ "] not recognized in the system, for message reference [" + header.getMessageRefNum() + "]");
		}

		public String getErrorCode() {
			return ERROR_CODE;
		}
	}
}