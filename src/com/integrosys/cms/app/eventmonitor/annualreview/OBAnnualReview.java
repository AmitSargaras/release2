/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/OBEvaluationDueInfo.java,v 1.3 2006/03/06 12:22:53 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.annualreview;

import java.util.Date;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBAnnualReview extends OBEventInfo {
	private String facilityID;

	private String facilityDesc;

	private Date reviewDueDate;

	public String getFacilityDesc() {
		return facilityDesc;
	}

	public void setFacilityDesc(String facilityDesc) {
		this.facilityDesc = facilityDesc;
	}

	public String getFacilityID() {
		return facilityID;
	}

	public void setFacilityID(String facilityID) {
		this.facilityID = facilityID;
	}

	public Date getReviewDueDate() {
		return reviewDueDate;
	}

	public void setReviewDueDate(Date reviewDueDate) {
		this.reviewDueDate = reviewDueDate;
	}

}
