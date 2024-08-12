package com.integrosys.cms.host.eai;

/**
 * An value object to represent a message parsed from XML or other form of data.
 * @author marvin
 * @author Chong Jun Yong
 * @version 1.1
 */
public class EAIMessage extends Message implements java.io.Serializable {

	private static final long serialVersionUID = 684838560005065457L;

	public EAIMessage() {
	}

	/**
	 * Constructor to provide message header and body
	 * @param header message header
	 * @param body message body
	 */
	public EAIMessage(EAIHeader header, EAIBody body) {
		super(header, body);
	}

	/**
	 * Constructor to provide message correlation id, message header and body
	 * @param correlationId correlation id
	 * @param header message header
	 * @param body message body
	 */
	public EAIMessage(String correlationId, EAIHeader header, EAIBody body) {
		super(correlationId, header, body);
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("EAIMessage [");
		buf.append("Message Header=");
		buf.append(getMsgHeader());
		buf.append(", Message Body=");
		buf.append(getMsgBody());
		if (getCorrelationId() != null) {
			buf.append(", Correlation Id=");
			buf.append(getCorrelationId());
		}
		buf.append("]");
		return buf.toString();
	}
}
