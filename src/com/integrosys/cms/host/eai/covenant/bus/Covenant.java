package com.integrosys.cms.host.eai.covenant.bus;

import java.util.Vector;

public class Covenant implements java.io.Serializable {

	private static final long serialVersionUID = 8582067729222527550L;

	private String lOSAANumber;

	private Vector covenantItem;

	public Vector getCovenantItem() {
		return covenantItem;
	}

	public String getLOSAANumber() {
		return lOSAANumber;
	}

	public void setCovenantItem(Vector covenantItem) {
		this.covenantItem = covenantItem;
	}

	public void setLOSAANumber(String number) {
		lOSAANumber = number;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("Covenant[");
		buf.append("covenantItem=");
		buf.append(covenantItem);
		buf.append(", lOSAANumber=");
		buf.append(lOSAANumber);
		buf.append("]");
		return buf.toString();
	}
}
