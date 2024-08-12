package com.integrosys.cms.host.stp.trade.proxy;

import com.ibm.mq.jms.JMSC;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.host.mq.EAIExceptionListener;
import com.integrosys.cms.host.mq.IMessageSenderProxy;
import com.integrosys.cms.host.mq.MQJmsSender;
import com.integrosys.cms.host.mq.MQMessageSenderProxy;
import com.integrosys.cms.host.stp.IMessage;
import com.integrosys.cms.host.stp.STPMessage;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.host.stp.core.MessageMarshallerFactory;
import com.integrosys.cms.host.stp.common.StpCommonException;
import com.integrosys.cms.host.stp.proxy.IStpAsyncProxy;
import com.integrosys.cms.host.stp.services.IStpMessageManager;
import com.integrosys.cms.host.stp.support.IMessageHandler;
import com.integrosys.cms.host.stp.trade.TradeResponseBody;
import com.integrosys.cms.host.stp.trade.bus.ITradeMasterTrans;
import com.integrosys.cms.host.stp.trade.bus.ITradeTrans;
import com.integrosys.cms.host.stp.trade.bus.ITradeTransBusManager;
import com.integrosys.cms.host.stp.trade.bus.OBTradeMasterTrans;
import com.integrosys.cms.host.stp.trade.common.ITradeConstants;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.QueueConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Stp Asynchronous Message Sender to host (SIBS) for all MBASE interface in real-time
 * <p/>
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Aug 29, 2008
 * Time: 4:16:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class TradeAsyncProxyImpl extends TimerTask implements IStpAsyncProxy, ITradeConstants {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ITradeTransBusManager tradeTransBusManager;
    private IMessageSenderProxy stpMsgSender;
    private IMessageHandler facilityInfoMessageHandler;
    private IStpMessageManager stpMessageManager;
    private int taskInterval;
    private boolean isRunning = false;
    private final Object runningMonitor = new Object();
    private final List localIpList = new ArrayList();

    private MessageMarshallerFactory messageMarshallerFactory;

    public IStpMessageManager getStpMessageManager() {
        return stpMessageManager;
    }

    public void setStpMessageManager(IStpMessageManager stpMessageManager) {
        this.stpMessageManager = stpMessageManager;
    }


    public void setFacilityInfoMessageHandler(IMessageHandler facilityInfoMessageHandler) {
        this.facilityInfoMessageHandler = facilityInfoMessageHandler;
    }

    public void setMessageMarshallerFactory(MessageMarshallerFactory messageMarshallerFactory) {
		this.messageMarshallerFactory = messageMarshallerFactory;
	}

    public MessageMarshallerFactory getMessageMarshallerFactory() {
		return messageMarshallerFactory;
	}

    public ITradeTransBusManager getTradeTransBusManager() {
        return tradeTransBusManager;
    }

    public void setTradeTransBusManager(ITradeTransBusManager tradeTransBusManager) {
        this.tradeTransBusManager = tradeTransBusManager;
    }

    public int getTaskInterval() {
        return taskInterval;
    }

    public void setTaskInterval(int taskInterval) {
        this.taskInterval = taskInterval;
    }

    public IMessageSenderProxy getStpMsgSender() {
        return stpMsgSender;
    }

    public void setStpMsgSender(IMessageSenderProxy stpMsgSender) {
        this.stpMsgSender = stpMsgSender;
    }

    public TradeAsyncProxyImpl() {
        //Andy Wong, 1 April 2009: retrieve all local IP, 1 machine can have multiple IP when multiple network card is installed
        try {
            Enumeration netInts = NetworkInterface.getNetworkInterfaces();
            while (netInts.hasMoreElements()) {
                NetworkInterface o = (NetworkInterface) netInts.nextElement();
                Enumeration intAddrs = o.getInetAddresses();

                while (intAddrs.hasMoreElements()) {
                    InetAddress add = (InetAddress) intAddrs.nextElement();
                    localIpList.add(add.getHostAddress());
                }
            }
        } catch (SocketException e) {
            logger.error("Socket exception in AsyncPoller.", e);
        }
    }

    /**
     * Method to submit Trade Async task from UI
     *
     * @param transactionId
     * @param referenceId
     * @param transactionType
     */
    public void submitTask(String transactionId, long referenceId, String transactionType) {
        Date todayDate = new Date();
        logger.debug("TradeAsyncProxyImpl.submitTask: trx Id=" + transactionId + " ref Id=" + referenceId + " trx Type=" + transactionType);

        ITradeMasterTrans masterTrans = getTradeTransBusManager().getMasterTransByTransactionId(transactionId);
        if (masterTrans != null) {
            masterTrans.setStatus(MASTER_TRX_QUEUE);
            masterTrans.setLastSubmissionDate(new Timestamp(todayDate.getTime()));
            masterTrans = getTradeTransBusManager().updateMasterTrans(masterTrans);
        } else {
            masterTrans = new OBTradeMasterTrans();
            masterTrans.setReferenceId(new Long(referenceId));
            masterTrans.setTransactionType(transactionType);
            masterTrans.setStatus(MASTER_TRX_QUEUE);
            masterTrans.setTransactionId(transactionId);
            masterTrans.setLastSubmissionDate(new Timestamp(todayDate.getTime()));
            masterTrans = getTradeTransBusManager().createMasterTrans(masterTrans);
        }
    }
    List taskList = new ArrayList();

    public List getTaskList() {
        return taskList;
    }

    public void setTaskList(List taskList) {
        this.taskList = taskList;
    }

    public void submitTask(Object obj ){
        if(getTaskList() == null){
            setTaskList(new ArrayList());
        }
        getTaskList().add(obj);             
    }
    private void genereateReponse(String messageId, String CorrelationId){

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Msg><MsgHeader><messageRefNum>B</messageRefNum><publishDate>D</publishDate><messageId>" + messageId + "</messageId><publishType>E</publishType><messageType>LIMIT</messageType><source>TRADE</source></MsgHeader>" +
                "<MsgBody xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"tradeResponseBody\"><RESPONSE_CD>0</RESPONSE_CD><RESPONSE_MESSAGE>SUCCUESS</RESPONSE_MESSAGE></MsgBody></Msg>";

        MQMessageSenderProxy proxy = new MQMessageSenderProxy();
        proxy.setMessageSender(new MQJmsSender());

        proxy.getMessageSender().setJmsTemplate(new JmsTemplate());

          MQQueueConnectionFactory factory = new MQQueueConnectionFactory();

            try{
                factory.setQueueManager("QM_CLM");
                factory.setHostName("localhost");
                factory.setPort(1414); // default
                factory.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);

                EAIExceptionListener outExListner = null;
                DefaultLogger.debug(this, "Creating Q Connection");
                QueueConnection con = factory.createQueueConnection();

                DefaultLogger.debug(this, "Capture out exception listener");
            }catch(Exception e){}
            DefaultLogger.debug(this, "Start connection");

            proxy.getMessageSender().getJmsTemplate().setConnectionFactory(factory);
            proxy.getMessageSender().getJmsTemplate().setDefaultDestinationName("trade.in");
        
        proxy.getMessageSender().sendMesage(xml, CorrelationId);
    }

    public void run() {
        //only start the poller when configured IP matches one of the local IP

        if (localIpList.contains(PropertyManager.getValue(TRADE_ASYNC_POLLER_ENABLED))) {
            if (this.isRunning) {
                return;
            }

            synchronized (runningMonitor) {
                this.isRunning = true;
            }
            try {
                logger.info("###############################################################################");
                logger.info("##### [TradeAsyncProxyImpl] Ready to start cycle");

                List taskList = getTradeTransBusManager().getMasterTransByStatus(Arrays.asList(new String[]{MASTER_TRX_QUEUE, MASTER_TRX_LOADING}));

                logger.info("##### [TradeAsyncProxyImpl] Items found: " + taskList.size());

                for (Iterator iterator = taskList.iterator(); iterator.hasNext();) {
                    ITradeMasterTrans masterTrans = (ITradeMasterTrans) iterator.next();
                    String correlationID = String.valueOf(this.facilityInfoMessageHandler.generateSeq(IStpConstants.STP_CORRELATION_ID_SEQ, true));
                    logger.info("##### [TradeAsyncProxyImpl] Process transaction: trx Id=" + masterTrans.getTransactionId() + " ref Id=" + masterTrans.getReferenceId() + " trx Type=" + masterTrans.getTransactionType());
                    try {
                        masterTrans.setStatus(MASTER_TRX_LOADING);
                        getTradeTransBusManager().updateMasterTrans(masterTrans);

                        STPMessage message = null;
                        try {
                            Properties msgContext = this.facilityInfoMessageHandler.processMessage(masterTrans);
                            message = (STPMessage) msgContext.get("msgobj");
                        }catch (StpCommonException ex) {
                            logger.error("Failed to prepare message for [" + masterTrans.getTransactionId() + "], skipped " + ex);
                            throw ex;
                        }
                    
                        try {
                            //need marshell to xml string to send out
                            Validate.notNull(message, "'message' to be sent over must not be null.");
                            String rawMessage = getMessageMarshallerFactory().marshall(message);

                            //genereateReponse(message.getMsgHeader().getMessageId(), correlationID);

                            String acknowledge = null;
                            STPMessage response = null;

                            acknowledge = (String) getStpMsgSender().sendAndReceive(rawMessage, correlationID);
                            response = getMessageMarshallerFactory().unmarshall(acknowledge);

                            stpMessageManager.logMessage(rawMessage, acknowledge, message.getMsgHeader(), response, correlationID, masterTrans.getMasterTrxId());

                            if(acknowledge ==null){
                                logger.error("JMSException, unable to get response");
                                throw new StpCommonException();
                            }else{
                                if(response != null && response.getMsgBody() instanceof TradeResponseBody){
                                    TradeResponseBody respBody = (TradeResponseBody) response.getMsgBody();
                                    String responseCode = respBody.getResponseCode();
                                    if (GOOD_RESPONSE_CODE.equals(responseCode)) {
                                        masterTrans.setStatus(TRX_SUCCESS);
                                        logger.error("Response is success");
                                    }else {
                                        logger.error("Response is fail");
                                        masterTrans.setStatus(TRX_FAILED);
                                    }
                                }else{
                                    logger.error("Unknow response : " + acknowledge);
                                    throw new StpCommonException();
                                }
                            }
                        }
                        catch (StpCommonException ex) {
                            logger.error("Failed to send message to host",ex);
                            throw ex;
                        }
                        getTradeTransBusManager().updateMasterTrans(masterTrans);
                        persistTradeTransHistory(masterTrans);
                        Thread.sleep(taskInterval);
                    } catch (Exception e) {
                        logger.error("Exception caught in TradeAsyncProxyImpl run.", e);
                        masterTrans.setStatus(MASTER_TRX_RESET);
                        getTradeTransBusManager().updateMasterTrans(masterTrans);
                    }
                }
            } catch (Exception e) {
                logger.error("Fatal exception in AsyncPoller.", e);
            }

            synchronized (runningMonitor) {
                this.isRunning = false;
            }
        }
    }

    /**
     * Persist trade trans history into table, replicate original trade trans
     * insert when trxUID changed, update otherwise
     *
     * @param masterTrans
     */
    private void persistTradeTransHistory(ITradeMasterTrans masterTrans) {
        getTradeTransBusManager().updateMasterTrans(masterTrans);
    }
}