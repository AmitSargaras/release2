package com.integrosys.cms.host.mq;

import com.integrosys.cms.host.stp.bus.IStpTransBusManager;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: May 4, 2010
 * Time: 4:56:39 PM
 *
 * MBASE message sender
 */
public class MQMessageSenderProxy implements IMessageSenderProxy {

    private MQJmsSender messageSender;
    private MQJmsReceiver messageReceiver;

    public MQJmsSender getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(MQJmsSender messageSender) {
        this.messageSender = messageSender;
    }

    public MQJmsReceiver getMessageReceiver() {
        return messageReceiver;
    }

    public void setMessageReceiver(MQJmsReceiver messageReceiver) {
        this.messageReceiver = messageReceiver;
    }

    public Object sendAndReceive(Object input, String correlationId){
        if(input != null) {
            final String messageSelector = "JMSCorrelationID = '" + correlationId + "'";

            getMessageSender().sendMesage(input, correlationId);
            return getMessageReceiver().processMessage(messageSelector);
        }

        return null;
    }
}
