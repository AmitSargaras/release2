package com.integrosys.cms.host.eai;

import com.integrosys.cms.host.eai.support.IEAIHeaderConstant;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 */
public class CMSHeader implements java.io.Serializable {

	private String messageId;

	private String messageType;

	private String publishType = "";

	private String publishDate = "";

	private String source;

	private String msgRefNo;

	public final String getMsgRefNo() {
		return msgRefNo;
	}

	public final void setMsgRefNo(String msgRefNo) {
		this.msgRefNo = msgRefNo;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {

		// Convert
		if (messageType.equalsIgnoreCase("RELATIONSHIP")) {
			messageType = IEAIHeaderConstant.CUSTOMER_TYPE;
		}

		this.messageType = messageType;
	}

	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
