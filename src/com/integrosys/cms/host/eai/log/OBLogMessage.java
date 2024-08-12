package com.integrosys.cms.host.eai.log;

import java.util.Date;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class OBLogMessage implements ILogMessage {

	private static final long serialVersionUID = 3921560282142783145L;

	private String SCIMsgId;

	private Date publishDate;

	private Date receivedDate;

	private String receivedMessage;

	private Date subscriberAckDate;

	private String subscriberResponseCode;

	private String subscriberResponseMessage;

	private char subscriberResponseType;

	private String subscriberResponseDesc;

	private String errorStackTrace;

	/**
	 * Default Constructor
	 */
	public OBLogMessage() {
	}

	public String getErrorStackTrace() {
		return this.errorStackTrace;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public String getReceivedMessage() {
		return receivedMessage;
	}

	public String getSCIMsgId() {
		return SCIMsgId;
	}

	public Date getSubscriberAckDate() {
		return subscriberAckDate;
	}

	public String getSubscriberResponseCode() {
		return subscriberResponseCode;
	}

	public String getSubscriberResponseDesc() {
		return subscriberResponseDesc;
	}

	public String getSubscriberResponseMessage() {
		return subscriberResponseMessage;
	}

	public char getSubscriberResponseType() {
		return subscriberResponseType;
	}

	public void setErrorStackTrace(String str) {
		this.errorStackTrace = str;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public void setReceivedMessage(String receivedMessage) {
		this.receivedMessage = receivedMessage;
	}

	public void setSCIMsgId(String SCIMsgId) {
		this.SCIMsgId = SCIMsgId;
	}

	public void setSubscriberAckDate(Date subscriberAckDate) {
		this.subscriberAckDate = subscriberAckDate;
	}

	public void setSubscriberResponseCode(String subscriberResponseCode) {
		this.subscriberResponseCode = subscriberResponseCode;
	}

	public void setSubscriberResponseDesc(String str) {
		this.subscriberResponseDesc = str;
	}

	public void setSubscriberResponseMessage(String subscriberResponseMessage) {
		this.subscriberResponseMessage = subscriberResponseMessage;
	}

	public void setSubscriberResponseType(char subscriberResponseType) {
		this.subscriberResponseType = subscriberResponseType;
	}

}
