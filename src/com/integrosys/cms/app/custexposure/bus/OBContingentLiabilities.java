package com.integrosys.cms.app.custexposure.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Created by IntelliJ IDEA. 
 * User: JITENDRA 
 * Date: Jun 4, 2008 Time: 2:40:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class OBContingentLiabilities implements IContingentLiabilities {

	private static final long serialVersionUID = 1L;
	private String borrowerName = "";
	private String guaranteeType = "";
	private Amount guaranteeAmt;
	private String bankEntity;
	private String sourceSecurityId;

	private String currencyCode;

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public Amount getGuaranteeAmt() {
		return guaranteeAmt;
	}

	public void setGuaranteeAmt(Amount guaranteeAmt) {
		this.guaranteeAmt = guaranteeAmt;
	}

    /**
     * Return a String representation of the object
     *
     * @return String
     */
    public String toString() {
        return AccessorUtil.printMethodValue(this);
    }

	/**
	 * @return the bankEntity
	 */
	public String getBankEntity() {
		return bankEntity;
	}

	/**
	 * @param bankEntity the bankEntity to set
	 */
	public void setBankEntity(String bankEntity) {
		this.bankEntity = bankEntity;
	}

	/**
	 * @return the sourceSecurityId
	 */
	public String getSourceSecurityId() {
		return sourceSecurityId;
	}

	/**
	 * @param sourceSecurityId the sourceSecurityId to set
	 */
	public void setSourceSecurityId(String sourceSecurityId) {
		this.sourceSecurityId = sourceSecurityId;
	}
}
