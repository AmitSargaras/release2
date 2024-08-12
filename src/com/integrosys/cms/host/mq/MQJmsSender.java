package com.integrosys.cms.host.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Apr 24, 2010
 * Time: 7:10:36 PM
 * <p/>
 * Generic MQ JMS message sender with jmsTemplate as Spring injectable property
 */
public class MQJmsSender {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private JmsTemplate jmsTemplate;

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMesage(final Object obj, final String correlationId) {
        getJmsTemplate().send(
                new MessageCreator() {
                    public Message createMessage(Session session)
                            throws JMSException {
                        Message message = null;
                        if (obj instanceof String) {
                            TextMessage textMessage = session.createTextMessage(obj.toString());
                            message = textMessage;
                        } else if (obj instanceof byte[]) {
                            byte[] byteTemp = (byte[]) obj;
                            BytesMessage bytesMessage = session.createBytesMessage();
                            bytesMessage.writeBytes(byteTemp);
                            message = bytesMessage;
                        } else {
                            logger.error("Unknown object type in MQJmsSender.sendMesage(). Type:  " + obj.getClass());
                        }
                        message.setJMSCorrelationID(correlationId);
                        return message;
                    }
                });
    }
}
