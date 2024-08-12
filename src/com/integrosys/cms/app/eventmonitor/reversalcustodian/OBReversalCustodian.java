/* Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/reversalcustodian/OBReversalCustodian.java,v 1.6 2006/03/06 12:35:00 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.reversalcustodian;

import java.util.Date;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * @author $Author: hshii $
 * @version $Revision: 1.6 $
 * @since $Date: 2006/03/06 12:35:00 $ Tag: $Name: $
 */
public class OBReversalCustodian extends OBEventInfo {

	private String transactionID;

	private long trxUserID = ICMSConstant.LONG_INVALID_VALUE;

	private long trxUserTeamID = ICMSConstant.LONG_INVALID_VALUE;

	// private String securityId;
	private String docType;

	private String docCode;

	private String docNo;

	private String docDescription;

	private String docRef;

	private Date docDate;

	private Date docExpiryDate;

	private String narration;

	private String reversalRemarks;

	private Date reversalDate;

	private String lmtprofileID;

	private String subprofileID;

	private long checkListID;

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
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

	/*
	 * public String getSecurityId() { return securityId; }
	 * 
	 * public void setSecurityId(String securityId) { this.securityId =
	 * securityId; }
	 */
	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDocNo() {
		return docNo;
	}

	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}

	public String getDocDescription() {
		return docDescription;
	}

	public void setDocDescription(String docDescription) {
		this.docDescription = docDescription;
	}

	public String getDocRef() {
		return docRef;
	}

	public void setDocRef(String docRef) {
		this.docRef = docRef;
	}

	public Date getDocDate() {
		return docDate;
	}

	public void setDocDate(Date docDate) {
		this.docDate = docDate;
	}

	public Date getDocExpiryDate() {
		return docExpiryDate;
	}

	public void setDocExpiryDate(Date docExpiryDate) {
		this.docExpiryDate = docExpiryDate;
	}

	public String getNarration() {
		return narration;
	}

	public void setNarration(String narration) {
		this.narration = narration;
	}

	public String getReversalRemarks() {
		return reversalRemarks;
	}

	public void setReversalRemarks(String reversalRemarks) {
		this.reversalRemarks = reversalRemarks;
	}

	public Date getReversalDate() {
		return reversalDate;
	}

	public void setReversalDate(Date reversalDate) {
		this.reversalDate = reversalDate;
	}

	public String getLmtprofileID() {
		return lmtprofileID;
	}

	public void setLmtprofileID(String lmtprofileID) {
		this.lmtprofileID = lmtprofileID;
	}

	public String getSubprofileID() {
		return subprofileID;
	}

	public void setSubprofileID(String subprofileID) {
		this.subprofileID = subprofileID;
	}

	public long getCheckListID() {
		return checkListID;
	}

	public void setCheckListID(long checkListID) {
		this.checkListID = checkListID;
	}
}