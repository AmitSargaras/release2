package com.integrosys.cms.ui.manualinput.customer;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Set;

public class LoggingHandler implements SOAPHandler<SOAPMessageContext> {
    private String lastRequest;
    private String lastResponse;

    @Override
    public Set<QName> getHeaders() {
        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);

        if (!isOutbound) { // Incoming message (response)
            SOAPMessage message = context.getMessage();
            // Convert SOAPMessage to string
            lastResponse = messageToString(message);
        } else { // Outgoing message (request)
            SOAPMessage message = context.getMessage();
            // Convert SOAPMessage to string
            lastRequest = messageToString(message);
        }

        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    public String getLastRequest() {
        return lastRequest;
    }

    public String getLastResponse() {
        return lastResponse;
    }

    private String messageToString(SOAPMessage message) {
        try {
            java.io.ByteArrayOutputStream outputStream = new java.io.ByteArrayOutputStream();
            message.writeTo(outputStream);
            return outputStream.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error converting message to string: " + e.getMessage();
        }
    }
}