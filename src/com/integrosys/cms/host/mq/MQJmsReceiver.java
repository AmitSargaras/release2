package com.integrosys.cms.host.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Apr 24, 2010
 * Time: 7:11:08 PM
 * <p/>
 * Generic MQ JMS message sender with jmsTemplate as Spring injectable property
 */
public class MQJmsReceiver {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public Object processMessage(String messageSelector) {
        Message msg = getJmsTemplate().receiveSelected(messageSelector);

        try {
            if (msg instanceof TextMessage) {
                return ((TextMessage) msg).getText();
            } else if (msg instanceof BytesMessage) {
                BytesMessage bytesMessage = (BytesMessage) msg;
                byte[] responseByte = new byte[4096];
                bytesMessage.readBytes(responseByte); // assume readBytes will always return full bytes with MQ
                return responseByte;
            }
        } catch (JMSException e) {
            logger.error("JMSException caught in MQJmsReceiver", e);
        }

        return null;
    }
}
