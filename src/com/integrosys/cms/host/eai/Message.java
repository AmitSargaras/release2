package com.integrosys.cms.host.eai;

import java.io.Serializable;

/**
 * <p>
 * A object represent an EAI contents published from the source.
 * <p>
 * This message contains the header and the body. Header will be the same format
 * for different type of message, contains basic info such as publish type,
 * date, reference number, message type, id, etc.
 * <p>
 * Different message type will have different message body. Message body might
 * consist of various kind of domain objects or just an inquiry message which
 * required response back to source.
 * @author marvin
 * @author Chong Jun Yong
 * @since 1.0
 */
public class Message implements Serializable {

	private static final long serialVersionUID = 4153797472016252171L;

	private String correlationId;

	private EAIHeader msgHeader;

	private EAIBody msgBody;

	public Message() {
		super();
	}

	/**
	 * Constructor to provide message header and body
	 * @param header message header
	 * @param body message body
	 */
	public Message(EAIHeader header, EAIBody body) {
		this.msgHeader = header;
		this.msgBody = body;
	}

	/**
	 * Constructor to provide message correlation id, message header and body
	 * @param correlationId correlation id
	 * @param header message header
	 * @param body message body
	 */
	public Message(String correlationId, EAIHeader header, EAIBody body) {
		this.correlationId = correlationId;
		this.msgHeader = header;
		this.msgBody = body;
	}

	public final String getCorrelationId() {
		return correlationId;
	}

	public EAIBody getMsgBody() {
		return msgBody;
	}

	public EAIHeader getMsgHeader() {
		return msgHeader;
	}

	public final void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public void setMsgBody(EAIBody msgBody) {
		this.msgBody = msgBody;
	}

	public void setMsgHeader(EAIHeader msgHeader) {
		this.msgHeader = msgHeader;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Message [");
		buf.append("Message Header=");
		buf.append(msgHeader);
		buf.append(", Message Body=");
		buf.append(msgBody);
		if (getCorrelationId() != null) {
			buf.append(", Correlation Id=");
			buf.append(correlationId);
		}
		buf.append("]");
		return buf.toString();
	}
}
