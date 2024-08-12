package com.integrosys.cms.host.stp.trade;

import com.integrosys.cms.host.stp.STPHeader;
import com.integrosys.cms.host.stp.STPMessage;

/**
 * An value object to represent a message parsed from XML or other form of data.
 * @author marvin
 * @author Chong Jun Yong
 * @author Chin Kok Cheong
 * @version 1.1
 */
public class TradeMessage extends STPMessage implements java.io.Serializable {

    private static final long serialVersionUID = 684838560005065457L;

	public TradeMessage() {
	}

	/**
	 * Constructor to provide message header and body
	 * @param header message header
	 * @param body message body
	 */
	public TradeMessage(STPHeader header, TradeBody body) {
		super(header, body);
	}

	/**
	 * Constructor to provide message correlation id, message header and body
	 * @param correlationId correlation id
	 * @param header message header
	 * @param body message body
	 */
	/*public TradeMessage(String correlationId, STPHeader header, TradeBody body) {
		super(header, body);
	}*/

	public String toString() {
		StringBuffer buf = new StringBuffer("TradeMessage [");
		buf.append("Message Header=");
		buf.append(getMsgHeader());
		buf.append(", Message Body=");
		buf.append(getMsgBody());
		/*if (getCorrelationId() != null) {
			buf.append(", Correlation Id=");
			buf.append(getCorrelationId());
		}*/
		buf.append("]");
		return buf.toString();
	}
}