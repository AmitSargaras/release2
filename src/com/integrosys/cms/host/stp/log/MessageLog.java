package com.integrosys.cms.host.stp.log;

import java.math.BigDecimal;
import java.util.Date;

/**
 * STP Message log to be persisted for tracking purpose
 *
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 *
 */
public class MessageLog {
	private long id;

	private String messageReferenceNumber;

    private Long masterTrxId;

    private String correlationId;

	private String messageType;

	private String messageId;

	private String source;

	private String requestMessagePath;

	private String responseMessagePath;

	private String responseMessageId;

	private String responseCode;

	private String responseDescription;

	private Date endProcessedDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessageReferenceNumber() {
        return messageReferenceNumber;
    }

    public void setMessageReferenceNumber(String messageReferenceNumber) {
        this.messageReferenceNumber = messageReferenceNumber;
    }

    public Long getMasterTrxId() {
        return masterTrxId;
    }

    public void setMasterTrxId(Long masterTrxId) {
        this.masterTrxId = masterTrxId;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRequestMessagePath() {
        return requestMessagePath;
    }

    public void setRequestMessagePath(String requestMessagePath) {
        this.requestMessagePath = requestMessagePath;
    }

    public String getResponseMessagePath() {
        return responseMessagePath;
    }

    public void setResponseMessagePath(String responseMessagePath) {
        this.responseMessagePath = responseMessagePath;
    }

    public String getResponseMessageId() {
        return responseMessageId;
    }

    public void setResponseMessageId(String responseMessageId) {
        this.responseMessageId = responseMessageId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDescription() {
        return responseDescription;
    }

    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    public Date getEndProcessedDate() {
        return endProcessedDate;
    }

    public void setEndProcessedDate(Date endProcessedDate) {
        this.endProcessedDate = endProcessedDate;
    }
}