/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collateralevaluationdue/OBEvaluationDueInfo.java,v 1.3 2006/03/06 12:22:53 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.collateralevaluationdue;

import java.util.Date;
import java.util.HashMap;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBEvaluationDueInfo extends OBEventInfo {
	private String subType;

	private String type;

	private String sciSecurityID;

	private String valFreqUnit;

	private Amount amount;

	private Amount fsvAmount;

	private Date expiryDate;

	private Date maturityDate;

	private Date valuationDate;

	private int daysDue;

	private int valFreq;

	private HashMap sourceIDMap = new HashMap();

	private HashMap facilityMap = new HashMap();

	public int getValFreq() {
		return valFreq;
	}

	public void setValFreq(int valFreq) {
		this.valFreq = valFreq;
	}

	public String getValFreqUnit() {
		return valFreqUnit;
	}

	public void setValFreqUnit(String valFreqUnit) {
		this.valFreqUnit = valFreqUnit;
	}

	public Amount getFsvAmount() {
		return fsvAmount;
	}

	public void setFsvAmount(Amount fsvAmount) {
		this.fsvAmount = fsvAmount;
	}

	public Date getValuationDate() {
		return valuationDate;
	}

	public void setValuationDate(Date valuationDate) {
		this.valuationDate = valuationDate;
	}

	public HashMap getSourceIDMap() {
		return sourceIDMap;
	}

	public void setSourceIDMap(HashMap sourceIDMap) {
		this.sourceIDMap = sourceIDMap;
	}

	public HashMap getFacilityMap() {
		return facilityMap;
	}

	public void setFacilityMap(HashMap facilityMap) {
		this.facilityMap = facilityMap;
	}

	public Amount getAmount() {
		return amount;
	}

	public void setAmount(Amount amount) {
		this.amount = amount;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getSciSecurityID() {
		return sciSecurityID;
	}

	public void setSciSecurityID(String sciSecurityID) {
		this.sciSecurityID = sciSecurityID;
		setSecurityID(sciSecurityID);
	}
}
