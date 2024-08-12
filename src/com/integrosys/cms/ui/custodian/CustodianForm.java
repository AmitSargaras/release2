/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.custodian;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * @author $Author: lini $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/08/09 01:39:02 $ Tag: $Name: $
 */

public class CustodianForm extends TrxContextForm implements Serializable {

	private String docCode = "";

	private String docDesc = "";

	private String expDate = "";

	private String docDate = "";

	private String docExpDate = "";

	private String localCode = "";

	private String docActionDate = "";

	private String docActionBy = "";

	private String docReasons = "";

	private String docRemarks = "";

	private String reversalRemarks = "";

	private String authzName1 = "";

	private String authzName2 = "";

	private String signNum1 = "";

	private String signNum2 = "";

	private String[] custodianId;

	private String[] checkListItemRef;

	private String itemStatus = "";

    private String custDocItemBarcode = "";

    private String secEnvelopeBarcode = "";

    private String custDocItemBarcodeTmp = "";

    private String secEnvelopeBarcodeTmp = "";

    private String limitProfile = "";
    
    private String requestedBy = "";
    
    private String releasedTo = "";
    
    private String reason = "";

	public String getDocCode() {
		return docCode;
	}

	public void setDocCode(String docCode) {
		this.docCode = docCode;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getDocDate() {
		return docDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public String getDocExpDate() {
		return docExpDate;
	}

	public void setDocExpDate(String docExpDate) {
		this.docExpDate = docExpDate;
	}

	public String getLocalCode() {
		return localCode;
	}

	public void setLocalCode(String localCode) {
		this.localCode = localCode;
	}

	public String getDocActionDate() {
		return docActionDate;
	}

	public void setDocActionDate(String docActionDate) {
		this.docActionDate = docActionDate;
	}

	public String getDocActionBy() {
		return docActionBy;
	}

	public void setDocActionBy(String docActionBy) {
		this.docActionBy = docActionBy;
	}

	public String getDocReasons() {
		return docReasons;
	}

	public void setDocReasons(String docReasons) {
		this.docReasons = docReasons;
	}

	public String getDocRemarks() {
		return docRemarks;
	}

	public void setDocRemarks(String docRemarks) {
		this.docRemarks = docRemarks;
	}

	public String getAuthzName1() {
		return authzName1;
	}

	public void setAuthzName1(String authzName1) {
		this.authzName1 = authzName1;
	}

	public String getAuthzName2() {
		return authzName2;
	}

	public void setAuthzName2(String authzName2) {
		this.authzName2 = authzName2;
	}

	public String getSignNum1() {
		return signNum1;
	}

	public void setSignNum1(String signNum1) {
		this.signNum1 = signNum1;
	}

	public String getSignNum2() {
		return signNum2;
	}

	public void setSignNum2(String signNum2) {
		this.signNum2 = signNum2;
	}

	public String[] getCustodianId() {
		return custodianId;
	}

	public void setCustodianId(String[] custodianId) {
		this.custodianId = custodianId;
	}

	public String[] getCheckListItemRef() {
		return checkListItemRef;
	}

	public void setCheckListItemRef(String[] checkListItemRef) {
		this.checkListItemRef = checkListItemRef;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}

	public void reset() {
	}

	public String getReversalRemarks() {
		return reversalRemarks;
	}

	public void setReversalRemarks(String reversalRemarks) {
		this.reversalRemarks = reversalRemarks;
	}

     public String getCustDocItemBarcodeTmp() {
		return custDocItemBarcodeTmp;
	}

	public void setCustDocItemBarcodeTmp(String custDocItemBarcodeTmp) {
		this.custDocItemBarcodeTmp = custDocItemBarcodeTmp;
	}

    public String getSecEnvelopeBarcodeTmp() {
		return secEnvelopeBarcodeTmp;
	}

	public void setSecEnvelopeBarcodeTmp(String secEnvelopeBarcodeTmp) {
		this.secEnvelopeBarcodeTmp = secEnvelopeBarcodeTmp;
	}

    public String getCustDocItemBarcode() {
		return custDocItemBarcode;
	}

	public void setCustDocItemBarcode(String custDocItemBarcode) {
		this.custDocItemBarcode = custDocItemBarcode;
	}

    public String getSecEnvelopeBarcode() {
		return secEnvelopeBarcode;
	}

	public void setSecEnvelopeBarcode(String secEnvelopeBarcode) {
		this.secEnvelopeBarcode = secEnvelopeBarcode;
	}

    public String getLimitProfile() {
		return limitProfile;
	}

	public void setLimitProfile(String limitProfile) {
		this.limitProfile = limitProfile;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getReleasedTo() {
		return releasedTo;
	}

	public void setReleasedTo(String releasedTo) {
		this.releasedTo = releasedTo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String[][] getMapper() {
		String[][] input = { { "aCustodianDoc", "com.integrosys.cms.ui.custodian.CustodianMapper" },
				{ "aCustodianTrxVal", "com.integrosys.cms.ui.custodian.CustodianMapper" },
				{ "theOBTrxContext", "com.integrosys.cms.ui.common.TrxContextMapper" },
				{ "CustodianSearchCriteria", "com.integrosys.cms.ui.custodian.CustodianMapper" },
				{ "theMemo", "com.integrosys.cms.ui.custodian.CustodianMapper" } };
		return input;
	}
}