/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.fdr;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * ActionForm for FDRAction.
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class FDRForm extends TrxContextForm implements Serializable {
	private String fdrID = "";

	private String projectID = "";

	private String fdrDate = "";

	private String accountNo = "";

	private String fdrCurrency = "";

	private String fdrAmount = "";

	private String referenceNo = "";

	private String remarks = "";

	public String getFdrID() {
		return fdrID;
	}

	public void setFdrID(String fdrID) {
		this.fdrID = fdrID;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public String getFdrDate() {
		return fdrDate;
	}

	public void setFdrDate(String fdrDate) {
		this.fdrDate = fdrDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getFdrCurrency() {
		return fdrCurrency;
	}

	public void setFdrCurrency(String fdrCurrency) {
		this.fdrCurrency = fdrCurrency;
	}

	public String getFdrAmount() {
		return fdrAmount;
	}

	public void setFdrAmount(String fdrAmount) {
		this.fdrAmount = fdrAmount;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String[][] getMapper() {
		String[][] input = {
		// {"bridgingLoanTrxValue",
		// "com.integrosys.cms.app.bridgingloan.trx.IBridgingLoanTrxValue"},
		{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.fdr.FDRMapper" }, };
		return input;
	}
}