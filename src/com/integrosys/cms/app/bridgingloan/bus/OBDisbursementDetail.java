package com.integrosys.cms.app.bridgingloan.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since May 28, 2007 Tag: $Name$
 */
public class OBDisbursementDetail implements IDisbursementDetail {
	private long disburseDetailID = ICMSConstant.LONG_INVALID_VALUE;

	private long disbursementID = ICMSConstant.LONG_INVALID_VALUE;

	private Date disbursementDate;

	private String subpurpose;

	private String invoiceNumber;

	private Amount invoiceAmount;

	private Amount disburseAmount;

	private String disbursementMode;

	private String payee;

	private String referenceNumber;

	private String glReferenceNumber;

	private String remarks;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd;

	/**
	 * Default Constructor
	 */
	public OBDisbursementDetail() {
	}

	public long getDisburseDetailID() {
		return disburseDetailID;
	}

	public void setDisburseDetailID(long disburseDetailID) {
		this.disburseDetailID = disburseDetailID;
	}

	public long getDisbursementID() {
		return disbursementID;
	}

	public void setDisbursementID(long disbursementID) {
		this.disbursementID = disbursementID;
	}

	public Date getDisbursementDate() {
		return disbursementDate;
	}

	public void setDisbursementDate(Date disbursementDate) {
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

	public Amount getInvoiceAmount() {
		return invoiceAmount;
	}

	public void setInvoiceAmount(Amount invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}

	public Amount getDisburseAmount() {
		return disburseAmount;
	}

	public void setDisburseAmount(Amount disburseAmount) {
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

	// ========================
	// Common Methods
	// ========================
	public long getCommonRef() {
		return commonRef;
	}

	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	public boolean getIsDeletedInd() {
		return isDeletedInd;
	}

	public void setIsDeletedInd(boolean isDeletedInd) {
		this.isDeletedInd = isDeletedInd;
	}

}
