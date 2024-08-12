package com.integrosys.cms.host.stp.trade.services;

import com.integrosys.cms.host.eai.support.IEAIResponseConstant;
import com.integrosys.cms.host.eai.support.IMessageFolder;
import com.integrosys.cms.host.eai.support.MessageDate;
import com.integrosys.cms.host.eai.support.MessageHolder;
import com.integrosys.cms.host.stp.IMessage;
import com.integrosys.cms.host.stp.STPHeader;
import com.integrosys.cms.host.stp.STPMessageException;
import com.integrosys.cms.host.stp.STPMessageValidationException;
import com.integrosys.cms.host.stp.log.ILogMessage;
import com.integrosys.cms.host.stp.log.ISTPLogMessageJdbc;
import com.integrosys.cms.host.stp.log.MessageLog;
import com.integrosys.cms.host.stp.services.IStpMessageManager;
import com.integrosys.cms.host.stp.services.StpMessageManagerImpl;
import com.integrosys.cms.host.stp.trade.TradeResponseBody;
import com.integrosys.cms.host.stp.trade.log.TradeMessageLog;
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
public class TradeMessageManagerImpl extends StpMessageManagerImpl {

    public void logMessage(String requestXML, String responseXML, STPHeader requestHeader, IMessage responseMsg, String correlationID, Long masterTrxId){

        TradeMessageLog messageLog = new TradeMessageLog();

        messageLog.setMessageReferenceNumber(requestHeader.getMessageRefNum());
        messageLog.setMessageType(requestHeader.getMessageType());
        messageLog.setMessageId(requestHeader.getMessageId());
        messageLog.setPublishType(requestHeader.getPublishType());
        messageLog.setPublishDate(MessageDate.getInstance().getDate(requestHeader.getPublishDate()));
        messageLog.setSource(requestHeader.getSource());
        messageLog.setCorrelationId(correlationID);
        messageLog.setMasterTrxId(masterTrxId);

        messageLog.setEndProcessedDate(new Date());

        if (ArrayUtils.contains(getMessageTypesRequiredLogToFileSystem(), requestHeader.getMessageType())) {
			String requestMessageId = getMessageFolder().putMessage(requestXML);
			MessageHolder requestMessageHolder = (MessageHolder) getMessageFolder().popMessageByMsgId(requestMessageId);
			messageLog.setRequestMessagePath(requestMessageHolder.getMessageDescription());
		}

        if (responseMsg != null && responseMsg.getMsgBody() != null && responseMsg.getMsgBody() instanceof TradeResponseBody) {
			TradeResponseBody responseBody = (TradeResponseBody) responseMsg.getMsgBody();

            messageLog.setResponseMessageId(responseMsg.getMsgHeader().getMessageId());
            messageLog.setResponseCode(responseBody.getResponseCode());
            messageLog.setResponseDescription(responseBody.getResponseMessage());

            if (IEAIResponseConstant.ACK_GOOD.equals(responseBody.getResponseCode())
              //      && this.logMessageForNonErrorAcknowledgment
                    && ArrayUtils.contains(getMessageTypesRequiredLogToFileSystem(), requestHeader.getMessageType())) {

                // for the purpose of retrieve file name, and also
                // persist the raw processed message into file system
                String responseMessageId = getMessageFolder().putMessage(responseXML);
                MessageHolder messageHolder = (MessageHolder) getMessageFolder().popMessageByMsgId(responseMessageId);

                messageLog.setResponseMessagePath(messageHolder.getMessageDescription());
            }
		}

        try {
			getLogMessageJdbc().persistSTPLogMessage(messageLog);
		}
		catch (Throwable t) {
			getLogger().warn("failed to persist stp log message, please verify", t);
		}
    }
}