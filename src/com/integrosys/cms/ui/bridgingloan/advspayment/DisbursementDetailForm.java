package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.io.Serializable;

import com.integrosys.cms.ui.common.TrxContextForm;

/**
 * Created by IntelliJ IDEA. User: KLYong Date: May 29, 2007 Time: 3:51:23 PM To
 * change this template use File | Settings | File Templates.
 */
public class DisbursementDetailForm extends TrxContextForm implements Serializable {

	private String disbursementDate = "";

	private String subpurpose = "";

	private String invoiceNumber = "";

	private String invoiceCurrency = "";

	private String invoiceAmount = "";

	private String disburseCurrency = "";

	private String disburseAmount = "";

	private String disbursementMode = "";

	private String payee = "";

	private String referenceNumber = "";

	private String glReferenceNumber = "";

	private String remarks = "";

	private String isSubpurpose = "";

	public String getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(String disbursementDate) {
		this.disbursementDate = disbursementDate;
	}

	public String getSubpurpose() {
		return subpurpose;
	}

	public void setSubpurpose(String subpurpose) {
		this.subpurpose = subpurpose;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getInvoiceCurrency() {
		return invoiceCurrency;
	}

	public void setInvoiceCurrency(String invoiceCurrency) {
		this.invoiceCurrency = invoiceCurrency;
	}

	public String getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public String getDisburseCurrency() {
		return disburseCurrency;
	}

	public void setDisburseCurrency(String disburseCurrency) {
		this.disburseCurrency = disburseCurrency;
	}

	public String getDisburseAmount() {
		return disburseAmount;
	}

	public void setDisburseAmount(String disburseAmount) {
		this.disburseAmount = disburseAmount;
	}

	public String getDisbursementMode() {
		return disbursementMode;
	}

	public void setDisbursementMode(String disbursementMode) {
		this.disbursementMode = disbursementMode;
	}

	public String getPayee() {
		return payee;
	}

	public void setPayee(String payee) {
		this.payee = payee;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getGlReferenceNumber() {
		return glReferenceNumber;
	}

	public void setGlReferenceNumber(String glReferenceNumber) {
		this.glReferenceNumber = glReferenceNumber;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getIsSubpurpose() {
		return isSubpurpose;
	}

	public void setIsSubpurpose(String isSubpurpose) {
		this.isSubpurpose = isSubpurpose;
	}

	public String[][] getMapper() {
		String[][] input = {
				{ "bridgingLoanTrxValue", "com.integrosys.cms.ui.bridgingloan.advspayment.DisbursementDetailMapper" },
				{ "objBridgingLoan", "com.integrosys.cms.ui.bridgingloan.advspayment.DisbursementDetailMapper" }, };
		return input;
	}
}