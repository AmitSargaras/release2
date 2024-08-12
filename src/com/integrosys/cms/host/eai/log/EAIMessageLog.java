package com.integrosys.cms.host.eai.log;

import java.util.Date;

/**
 * EAI Message log to be persisted for tracking purpose
 * 
 * @author Chong Jun Yong
 * 
 */
public class EAIMessageLog {
	private long id;

	private String messageReferenceNumber;

	private String messageType;

	private String messageId;

	private String publishType;

	private Date publishDate;

	private String source;

	private String receivedMessagePath;

	private String responseMessagePath;

	private String responseMessageId;

	private String responseCode;

	private String responseDescription;

	private Date endProcessedDate;

	public Date getEndProcessedDate() {
		return endProcessedDate;
	}

	public long getId() {
		return id;
	}

	public String getMessageId() {
		return messageId;
	}

	public String getMessageReferenceNumber() {
		return messageReferenceNumber;
	}

	public String getMessageType() {
		return messageType;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public String getPublishType() {
		return publishType;
	}

	public String getReceivedMessagePath() {
		return receivedMessagePath;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public String getResponseMessageId() {
		return responseMessageId;
	}

	public String getResponseMessagePath() {
		return responseMessagePath;
	}

	public String getSource() {
		return source;
	}

	public void setEndProcessedDate(Date endProcessedDate) {
		this.endProcessedDate = endProcessedDate;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public void setMessageReferenceNumber(String messageReferenceNumber) {
		this.messageReferenceNumber = messageReferenceNumber;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	public void setReceivedMessagePath(String receivedMessagePath) {
		this.receivedMessagePath = receivedMessagePath;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public void setResponseMessageId(String responseMessageId) {
		this.responseMessageId = responseMessageId;
	}

	public void setResponseMessagePath(String responseMessagePath) {
		this.responseMessagePath = responseMessagePath;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
