package com.integrosys.cms.ui.collateral.guarantees.feedetails;

import java.io.Serializable;

import com.integrosys.base.uiinfra.common.CommonForm;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: May 7, 2007 Time: 6:08:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeeDetailsForm extends CommonForm implements Serializable {

	private String feeDetailsID;

	private String effectiveDate;

	private String expirationDate;

	private String amountCGC;

	private String amountFee;

	private String tenor;

	private String tenorFreq;

	private String guaranteeCcyCode;

	private String refID;

	private String status;
	
	private String feePaymentDateCGC;

	public String getFeeDetailsID() {
		return feeDetailsID;
	}

	public void setFeeDetailsID(String feeDetailsID) {
		this.feeDetailsID = feeDetailsID;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getAmountCGC() {
		return amountCGC;
	}

	public void setAmountCGC(String amountCGC) {
		this.amountCGC = amountCGC;
	}

	public String getAmountFee() {
		return amountFee;
	}

	public void setAmountFee(String amountFee) {
		this.amountFee = amountFee;
	}

	public String getTenor() {
		return tenor;
	}

	public void setTenor(String tenor) {
		this.tenor = tenor;
	}

	public String getTenorFreq() {
		return tenorFreq;
	}

	public void setTenorFreq(String tenorFreq) {
		this.tenorFreq = tenorFreq;
	}

	public String getGuaranteeCcyCode() {
		return guaranteeCcyCode;
	}

	public void setGuaranteeCcyCode(String guaranteeCcyCode) {
		this.guaranteeCcyCode = guaranteeCcyCode;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeePaymentDateCGC() {
		return feePaymentDateCGC;
	}

	public void setFeePaymentDateCGC(String feePaymentDateCGC) {
		this.feePaymentDateCGC = feePaymentDateCGC;
	}

	public String[][] getMapper() {

		String[][] input = { { "form.feeDetailsObject",
				"com.integrosys.cms.ui.collateral.guarantees.feedetails.FeeDetailsMapper" }, };

		return input;

	}

	public void reset() {

	}

}
