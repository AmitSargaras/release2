package com.integrosys.cms.app.collateral.bus.type.guarantee.subtype.gtegovtlink;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: jitendra Date: Mar 1, 2007 Time: 2:54:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBFeeDetails implements IFeeDetails {

	private long feeDetailsID = ICMSConstant.LONG_MIN_VALUE;

	private Date effectiveDate;

	private Date expirationDate;

	private Amount amountCGC;

	private Amount amountFee;

	private int tenor;

	private String tenorFreq;

	private String guaranteeCcyCode;

	private long refID = ICMSConstant.LONG_MIN_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;
	
	private Date feePaymentDateCGC;

	public long getFeeDetailsID() {
		return feeDetailsID;
	}

	public void setFeeDetailsID(long feeDetailsID) {
		this.feeDetailsID = feeDetailsID;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public Amount getAmountCGC() {
		return amountCGC;
	}

	public void setAmountCGC(Amount amountCGC) {
		this.amountCGC = amountCGC;
	}

	public Amount getAmountFee() {
		return amountFee;
	}

	public void setAmountFee(Amount amountFee) {
		this.amountFee = amountFee;
	}

	public int getTenor() {
		return tenor;
	}

	public void setTenor(int tenor1) {
		tenor = tenor1;
	}

	public long getRefID() {
		return refID;
	}

	public void setRefID(long refID) {
		this.refID = refID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGuaranteeCcyCode() {
		return guaranteeCcyCode;
	}

	public void setGuaranteeCcyCode(String guaranteeCcyCode) {
		this.guaranteeCcyCode = guaranteeCcyCode;
	}

	public String getTenorFreq() {
		return tenorFreq;
	}

	public void setTenorFreq(String tenorFreq) {
		this.tenorFreq = tenorFreq;
	}

	public Date getFeePaymentDateCGC() {
		return feePaymentDateCGC;
	}

	public void setFeePaymentDateCGC(Date feePaymentDateCGC) {
		this.feePaymentDateCGC = feePaymentDateCGC;
	}

}
