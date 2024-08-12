/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/bflacceptanceexpiry/OBFLAcceptanceExpiryInfo.java,v 1.2 2003/08/30 08:20:39 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.bflacceptanceexpiry;

import java.util.Date;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBFLAcceptanceExpiryInfo extends OBEventInfo {

	private Date receiptDate;

	private Date dueDate;

	private int daysDue;

	public Date getReceiptDate() {
		return receiptDate;
	}

	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public int getDaysDue() {
		return daysDue;
	}

	public void setDaysDue(int daysDue) {
		this.daysDue = daysDue;
	}

}
