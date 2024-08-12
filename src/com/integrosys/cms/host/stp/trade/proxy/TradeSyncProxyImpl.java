package com.integrosys.cms.host.stp.trade.proxy;

import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.core.MessageMarshallerFactory;
import com.integrosys.cms.host.stp.proxy.IStpSyncProxy;
import com.integrosys.cms.host.stp.services.IStpMessageManager;
import com.integrosys.cms.host.stp.support.IMessageHandler;
import com.integrosys.cms.host.stp.IMessage;
import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.mq.IMessageSenderProxy;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.trade.*;
import com.integrosys.cms.host.stp.trade.bus.ITradeTransBusManager;
import com.integrosys.cms.host.stp.trade.common.ITradeConstants;
import com.integrosys.cms.host.stp.trade.common.ITradeTransType;
import com.integrosys.cms.host.stp.trade.common.TradeCommonException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Properties;

/**
 * @author Andy Wong
 * @author Chin Kok Cheong
 *
 */
public class TradeSyncProxyImpl implements IStpSyncProxy, ITradeTransType, ITradeConstants {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ITradeTransBusManager tradeTransBusManager;
   // private IMessageAdapter stpMsgAdapter;
    private IMessageSenderProxy stpMsgSender;
    private IStpMessageManager stpMessageManager;
    private IMessageHandler facilityInfoMessageHandler;


    public IStpMessageManager getStpMessageManager() {
        return stpMessageManager;
    }

    public void setStpMessageManager(IStpMessageManager stpMessageManager) {
        this.stpMessageManager = stpMessageManager;
    }

    public IMessageHandler getFacilityInfoMessageHandler() {
        return facilityInfoMessageHandler;
    }

    public void setFacilityInfoMessageHandler(IMessageHandler facilityInfoMessageHandler) {
        this.facilityInfoMessageHandler = facilityInfoMessageHandler;
    }

    public ITradeTransBusManager getTradeTransBusManager() {
        return tradeTransBusManager;
    }

    public void setTradeTransBusManager(ITradeTransBusManager tradeTransBusManager) {
        this.tradeTransBusManager = tradeTransBusManager;
    }

    /*public IMessageAdapter getStpMsgAdapter() {
        return stpMsgAdapter;
    }

    public void setStpMsgAdapter(IMessageAdapter stpMsgAdapter) {
        this.stpMsgAdapter = stpMsgAdapter;
    }*/

    public IMessageSenderProxy getStpMsgSender() {
        return stpMsgSender;
    }

    public void setStpMsgSender(IMessageSenderProxy stpMsgSender) {
        this.stpMsgSender = stpMsgSender;
    }
    
    /**
     * singleton instance
     */
    private static TradeSyncProxyImpl singleton = new TradeSyncProxyImpl();

    /**
     * Gets singleton
     *
     * @return singleton
     */
    public static TradeSyncProxyImpl getInstance() {
        return singleton;
    }

    //** Modified by KLYong - Trade inquiry and detail listing

    /**
     * Submiting task of transaction firing request to sibs to retrieve message response
     *
     * @param trxType - transaction type
     * @param object  - object (hashmap, arraylist or biz object)
     * @return message response
     * @throws Exception exception
     */
    public Object submitTask(String trxType, Object[] object) throws Exception {
        try {
            if (StringUtils.equals(trxType, "sendTradeObj")) {
                return sendTradeObj(trxType, object);
            }
        } catch (UnknownHostException e) {
            throw new TradeCommonException(ERR_CODE_INVALID_HOST, ERR_DESC_INVALID_HOST, e);
        } catch (IOException e) {
            throw new TradeCommonException(ERR_CODE_TIMEOUT, ERR_DESC_TIMEOUT, e);
        }
        return null;
    }

    private String tradeMoreRecord;

    public void setTradeMoreRecord(String tradeMoreRecord) {
        this.tradeMoreRecord = tradeMoreRecord;
    }

    public String getTradeMoreRecord() {
        return this.tradeMoreRecord;
    }
    private MessageMarshallerFactory messageMarshallerFactory;

    public void setMessageMarshallerFactory(MessageMarshallerFactory messageMarshallerFactory) {
		this.messageMarshallerFactory = messageMarshallerFactory;
	}

    public MessageMarshallerFactory getMessageMarshallerFactory() {
		return messageMarshallerFactory;
	}

    public Object sendTradeObj(String trxType, Object[] object) throws Exception {
        STPMessage message = null;
        String correlationID = String.valueOf(this.facilityInfoMessageHandler.generateSeq(IStpConstants.STP_CORRELATION_ID_SEQ, true));
        try {
            Properties msgContext = this.facilityInfoMessageHandler.processMessage(object[0]);
			message = (STPMessage) msgContext.get("msgobj");
		}catch (StpCommonException ex) {
			logger.error("Failed to prepare message for [" + message + "], skipped " + ex);
			throw ex;
		}

        try {
            //need marshell to xml string to send out
            Validate.notNull(message, "'message' to be sent over must not be null.");

		    String rawMessage = getMessageMarshallerFactory().marshall(message);
            String acknowledge = null;
            STPMessage response = null;

            acknowledge = (String) getStpMsgSender().sendAndReceive(rawMessage, correlationID);
            response = getMessageMarshallerFactory().unmarshall(acknowledge);

            if(acknowledge ==null){
                logger.error("JMSException, unable to get response");
            }else{
                if(response != null && response.getMsgBody() instanceof TradeResponseBody){
                    TradeResponseBody respBody = (TradeResponseBody) response.getMsgBody();
                    String responseCode = respBody.getResponseCode();
                    if (GOOD_RESPONSE_CODE.equals(responseCode)) {
                        logger.error("Response is success");
                    }else {
                        logger.error("Response is fail");
                    }
                }else{
                    logger.error("unknow response");
                }
            }
        }
        catch (StpCommonException ex) {
            logger.error("Failed to send message to host",ex);
        }

        return null;
    }
}