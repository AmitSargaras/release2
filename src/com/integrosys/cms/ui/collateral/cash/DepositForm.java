package com.integrosys.cms.ui.collateral.cash;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class DepositForm extends CommonForm implements Serializable {

	public static final String DEPOSITMAPPER = "com.integrosys.cms.ui.collateral.cash.DepositMapper";

	private String cashDepositID = "";

	// private String depRecpNo = "";
	private String depCurr = IGlobalConstant.DEFAULT_CURRENCY;

	private String depAmt = "";

	private String depMatDate = "";

	private String depNo = "";

	// private String depositRefNo ="";
	private String depositReceiptNo;

	private String issueDate = "";
	
	public String getDepositReceiptNo() {
		return depositReceiptNo;
	}

	public void setDepositReceiptNo(String depositReceiptNo) {
		this.depositReceiptNo = depositReceiptNo;
	}

	public String getCashDepositID() {
		return cashDepositID;
	}

	public void setCashDepositID(String cashDepositID) {
		this.cashDepositID = cashDepositID;
	}

	public String getDepCurr() {
		return depCurr;
	}

	public void setDepCurr(String depCurr) {
		this.depCurr = depCurr;
	}

	public String getDepAmt() {
		return depAmt;
	}

	public void setDepAmt(String depAmt) {
		this.depAmt = depAmt;
	}

	/*
	 * public String getDepRecpNo() { return this.depRecpNo; }
	 * 
	 * 
	 * public void setDepRecpNo(String depRecpNo) { this.depRecpNo = depRecpNo;
	 * }
	 */

	public String getDepMatDate() {
		return this.depMatDate;
	}

	public void setDepMatDate(String depMatDate) {
		this.depMatDate = depMatDate;
	}

	public String getDepNo() {
		return depNo;
	}

	public void setDepNo(String depNo) {
		this.depNo = depNo;
	}

	/*
	 * public String getDepositRefNo() { return depositRefNo; }
	 * 
	 * public void setDepositRefNo(String depositRefNo) { this.depositRefNo =
	 * depositRefNo; }
	 */

	public String getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String[][] getMapper() {
		String[][] input = { { "form.depositObject", DEPOSITMAPPER }, };
		return input;

	}

	public void reset() {
		depCurr = IGlobalConstant.DEFAULT_CURRENCY;
	}

}
