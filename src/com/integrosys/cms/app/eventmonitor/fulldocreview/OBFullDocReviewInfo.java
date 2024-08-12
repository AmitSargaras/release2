/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/fulldocreview/OBFullDocReviewInfo.java,v 1.1 2003/08/30 08:20:39 phtan Exp $
 */

package com.integrosys.cms.app.eventmonitor.fulldocreview;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBFullDocReviewInfo extends OBEventInfo {

	private String previousCreditGrade;

	private String currentCreditGrade;

	private Date dateDownGraded;

	private int daysDue;

	private HashMap facilityMap = new HashMap();

	public String getPreviousCreditGrade() {
		return previousCreditGrade;
	}

	public void setPreviousCreditGrade(String previousCreditGrade) {
		this.previousCreditGrade = previousCreditGrade;
	}

	public Date getDateDownGraded() {
		return dateDownGraded;
	}

	public void setDateDownGraded(Date dateDownGraded) {
		this.dateDownGraded = dateDownGraded;
	}

	public String getCurrentCreditGrade() {
		return currentCreditGrade;
	}

	public void setCurrentCreditGrade(String currentCreditGrade) {
		this.currentCreditGrade = currentCreditGrade;
	}

	public int getDaysDue() {
		return daysDue;
	}

	public void setDaysDue(int daysDue) {
		this.daysDue = daysDue;
	}

	public HashMap getFacilityMap() {
		return facilityMap;
	}

	public void setFacilityMap(HashMap facilityMap) {
		this.facilityMap = facilityMap;
	}
}
