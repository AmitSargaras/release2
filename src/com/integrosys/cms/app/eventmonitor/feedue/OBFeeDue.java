/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/OBEvaluationDueInfo.java,v 1.3 2006/03/06 12:22:53 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.feedue;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBFeeDue extends OBEventInfo {
	private String secType;

	private String subType;

	private Date feeDueDate;

	private Amount guaranteeAmout;

	public Date getFeeDueDate() {
		return feeDueDate;
	}

	public void setFeeDueDate(Date feeDueDate) {
		this.feeDueDate = feeDueDate;
	}

	public Amount getGuaranteeAmout() {
		return guaranteeAmout;
	}

	public void setGuaranteeAmout(Amount guaranteeAmout) {
		this.guaranteeAmout = guaranteeAmout;
	}

	public String getSecType() {
		return secType;
	}

	public void setSecType(String secType) {
		this.secType = secType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

}
