/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/dealapproval/OBDealApproval.java,v 1.7 2006/03/06 12:30:19 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.dealapproval;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * JavaBean object to store the attributes required to prepare notification
 * message This message is for BFL Due notification
 */
public class OBDealApproval extends OBEventInfo {

	private String transactionID;

	private String securityLoc;

	// private String securityId;
	private String comProductType;

	private String comProductSubType;

	private String dealNo;

	private Amount dealAmount;

	private long trxUserID = ICMSConstant.LONG_INVALID_VALUE;

	private long trxUserTeamID = ICMSConstant.LONG_INVALID_VALUE;

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getComProductSubType() {
		return comProductSubType;
	}

	public void setComProductSubType(String comProductSubType) {
		this.comProductSubType = comProductSubType;
	}

	public String getComProductType() {
		return comProductType;
	}

	public void setComProductType(String comProductType) {
		this.comProductType = comProductType;
	}

	public Amount getDealAmount() {
		return dealAmount;
	}

	public void setDealAmount(Amount dealAmount) {
		this.dealAmount = dealAmount;
	}

	public String getDealNo() {
		return dealNo;
	}

	public void setDealNo(String dealNo) {
		this.dealNo = dealNo;
	}

	/*
	 * public String getSecurityId() { return securityId; }
	 * 
	 * public void setSecurityId(String securityId) { this.securityId =
	 * securityId; }
	 */
	public String getSecurityLoc() {
		return securityLoc;
	}

	public void setSecurityLoc(String securityLoc) {
		this.securityLoc = securityLoc;
	}

	public long getTrxUserID() {
		return trxUserID;
	}

	public void setTrxUserID(long trxUserID) {
		this.trxUserID = trxUserID;
	}

	public long getTrxUserTeamID() {
		return trxUserTeamID;
	}

	public void setTrxUserTeamID(long trxUserTeamID) {
		this.trxUserTeamID = trxUserTeamID;
	}
}
