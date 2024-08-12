package com.integrosys.cms.app.eventmonitor.updatecustodian;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

public class OBUpdateCustodian extends OBEventInfo {

	private String category;

	private String msgDetails;

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return msgDetauls
	 */

	public String getMsgDetails() {
		return msgDetails;
	}

	/**
	 * 
	 * @param msgDetails
	 */
	public void setMsgDetails(String msgDetails) {
		this.msgDetails = msgDetails;
	}

}
