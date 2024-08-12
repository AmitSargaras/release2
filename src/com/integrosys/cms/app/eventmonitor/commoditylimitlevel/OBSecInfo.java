package com.integrosys.cms.app.eventmonitor.commoditylimitlevel;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jun 7, 2004 Time: 5:39:09 PM To
 * change this template use Options | File Templates.
 */
public class OBSecInfo implements java.io.Serializable {

	private String secId;

	private String secType;

	private String secsubType;

	public String getSecId() {
		return secId;
	}

	public void setSecId(String secId) {
		this.secId = secId;
	}

	public String getSecsubType() {
		return secsubType;
	}

	public void setSecsubType(String secsubType) {
		this.secsubType = secsubType;
	}

	public String getSecType() {
		return secType;
	}

	public void setSecType(String secType) {
		this.secType = secType;
	}

	public String toString() {
		String result = secId + ";" + secType + ";" + secsubType;
		return result;
	}
}
