package com.integrosys.cms.host.stp;

/**
 * Object represent a message header, contain basic meta information about the
 * message contents.
 *
 * @author marvin
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 */
public class STPHeader implements java.io.Serializable {

	private static final long serialVersionUID = -4054502239272426731L;

	private String messageId;

	private String messageType;

	private String messageRefNum;

	private String publishDate;

	private String publishType;

	private String source;

	public STPHeader() {
	}

	public STPHeader(STPHeader another) {
		this.messageId = another.messageId;
		this.messageType = another.messageType;
		this.messageRefNum = another.messageRefNum;
		this.publishDate = another.publishDate;
		this.publishType = another.publishType;
		this.source = another.source;
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
		this.messageType = messageType;
	}

	public String getMessageRefNum() {
		return messageRefNum;
	}

	public void setMessageRefNum(String messageRefNum) {
		this.messageRefNum = messageRefNum;
	}

	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Message Header [");
		buf.append("Reference Number=");
		buf.append(messageRefNum);
		buf.append(", Type=");
		buf.append(messageType);
		buf.append(", Id=");
		buf.append(messageId);
		buf.append(", Publish Date=");
		buf.append(publishDate);
		buf.append(", Publish Type=");
		buf.append(publishType);
		buf.append(", Source=");
		buf.append(source);
		buf.append("]");
		return buf.toString();
	}

}