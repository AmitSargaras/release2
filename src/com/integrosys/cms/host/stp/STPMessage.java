package com.integrosys.cms.host.stp;

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
 * @author Chin Kok Cheong
 * @since 1.0
 */
public class STPMessage implements Serializable, IMessage{

	private static final long serialVersionUID = 4153797472016252171L;

	private String correlationId;

	private STPHeader msgHeader;

	private STPBody msgBody;

	public STPMessage() {
		super();
	}

	/**
	 * Constructor to provide message header and body
	 * @param header message header
	 * @param body message body
	 */
	public STPMessage(STPHeader header, STPBody body) {
		this.msgHeader = header;
		this.msgBody = body;
	}

	/**
	 * Constructor to provide message correlation id, message header and body
	 * @param correlationId correlation id
	 * @param header message header
	 * @param body message body
	 */
	public STPMessage(String correlationId, STPHeader header, STPBody body) {
		this.correlationId = correlationId;
		this.msgHeader = header;
		this.msgBody = body;
	}

	public final String getCorrelationId() {
		return correlationId;
	}

	public STPBody getMsgBody() {
		return msgBody;
	}

	public STPHeader getMsgHeader() {
		return msgHeader;
	}
    
	public final void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public void setMsgBody(STPBody msgBody) {
		this.msgBody = msgBody;
	}

	public void setMsgHeader(STPHeader msgHeader) {
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