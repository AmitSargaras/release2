package com.integrosys.cms.host.mq;

import com.integrosys.cms.host.eai.EAIMessage;
import com.integrosys.cms.host.eai.castor.CastorMessageIdBaseMessageMarshallerFactory;
import com.integrosys.cms.host.eai.service.MessageHandler;
import com.integrosys.cms.host.eai.support.EAIHeaderHelper;
import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;
import com.integrosys.cms.host.eai.support.MemoryMessageFolderImpl;
import com.integrosys.cms.host.eai.support.MessageHandlerObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Spring DefaultMessageListener for MQ implementation
 *
 * @author Andy Wong
 * @since 22 April 2010
 */
public class MQMessageListener implements MessageListener, MessageHandlerObserver {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private MemoryMessageFolderImpl messageFolder;
    private MessageHandler messageHandlerSubject;
    private String messageAttributeName;
    private CastorMessageIdBaseMessageMarshallerFactory messageMarshallerFactory;
    private long processWaitingSleepingPeriod;
    private MQJmsSender messageSender;
    private EAIMessage responseMessage;

    /**
     * Default Constructor
     */
    public MQMessageListener() {
    }

    /**
     * This method provides the implementation of the MessageListener interface.
     */
    public void onMessage(javax.jms.Message message) {
        responseMessage = null;
        String msgId = null;
        String correlationID = null;

        // Try to get correlationID
        try {
            correlationID = message.getJMSCorrelationID();
            logger.debug("** [Text Message CorrelationID]: " + correlationID);
        }
        catch (JMSException ex) {
            logger.error("Caught Exception while getJMSCorrelationID", ex);
        }

        if (message instanceof TextMessage) {
            logger.debug("EAIMessageListener Received Message");
            logger.debug("\n*** Begin Processing Message ID: " + msgId + "\n");
            try {
                // display the message contents
                String msg = ((TextMessage) message).getText();
                msgId = this.messageFolder.putMessage(msg);
                this.messageHandlerSubject.registerAndProcess(msgId, this);

                while (this.responseMessage == null) {
                    try {
                        Thread.sleep(this.processWaitingSleepingPeriod);
                    }
                    catch (InterruptedException ex) {
                        logger.warn("The thread was interrupted while waiting for getting notified, possible ?", ex);
                    }
                }

                String responseContent = this.messageMarshallerFactory.marshall(responseMessage);
                this.messageSender.sendMesage(responseContent, message.getJMSCorrelationID());
            } catch (JMSException e) {
                logger.error("JMSException in onMessage! ", e);
                Exception le = e.getLinkedException();

                if (le != null) {
                    logger.error("Linked Exception is: ", le);
                }
            } catch (Exception e) {
                logger.error("Exception in onMessage! ", e);
            } finally {
                try {
                    message.acknowledge();
                    logger.debug("\n*** End Processing Message ID: " + msgId + "\n");
                }
                catch (Exception e) {
                    logger.error("Caught Exception in finally clause of onMessage!", e);
                }
            }
        } else {
            try {
                message.acknowledge();
            } catch (Exception e) {
                logger.error("Caught Exception in finally clause of onMessage!", e);
            } finally {
                logger.debug("<WARNING>:Message received is not TextMessage!!!");
            }
        }
        logger.debug("Listener processing onMessage ended");
    }

    public void update(Object object) {
        this.responseMessage = (EAIMessage) object;
    }

    public void setMessageFolder(MemoryMessageFolderImpl messageFolder) {
        this.messageFolder = messageFolder;
    }

    public MemoryMessageFolderImpl getMessageFolder() {
        return messageFolder;
    }

    public void setMessageHandlerSubject(MessageHandler messageHandlerSubject) {
        this.messageHandlerSubject = messageHandlerSubject;
    }

    public MessageHandler getMessageHandlerSubject() {
        return messageHandlerSubject;
    }

    public void setMessageAttributeName(String messageAttributeName) {
        this.messageAttributeName = messageAttributeName;
    }

    public String getMessageAttributeName() {
        return messageAttributeName;
    }

    public void setMessageMarshallerFactory(CastorMessageIdBaseMessageMarshallerFactory messageMarshallerFactory) {
        this.messageMarshallerFactory = messageMarshallerFactory;
    }

    public CastorMessageIdBaseMessageMarshallerFactory getMessageMarshallerFactory() {
        return messageMarshallerFactory;
    }

    public void setProcessWaitingSleepingPeriod(long processWaitingSleepingPeriod) {
        this.processWaitingSleepingPeriod = processWaitingSleepingPeriod;
    }

    public long getProcessWaitingSleepingPeriod() {
        return processWaitingSleepingPeriod;
    }

    public MQJmsSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(MQJmsSender messageSender) {
        this.messageSender = messageSender;
    }
}
