/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/guarantornotice/OBGuarantorNoticeInfo.java,v 1.4 2003/10/14 02:03:32 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.guarantornotice;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBGuarantorNoticeInfo extends OBEventInfo {
	private String subType;

	private Date guaranteeDate;

	private int daysDue;

	private String type;

	private Amount guaranteeAmount;

	public Amount getGuaranteeAmount() {
		return guaranteeAmount;
	}

	public void setGuaranteeAmount(Amount guaranteeAmount) {
		this.guaranteeAmount = guaranteeAmount;
	}

	public Date getGuaranteeDate() {
		return guaranteeDate;
	}

	public void setGuaranteeDate(Date maturityDate) {
		this.guaranteeDate = maturityDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public int getDaysDue() {
		return daysDue;
	}

	public void setDaysDue(int daysDue) {
		this.daysDue = daysDue;
	}

}
