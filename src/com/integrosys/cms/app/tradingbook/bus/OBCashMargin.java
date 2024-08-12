/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds cash margin.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBCashMargin implements ICashMargin {
	public static final String UPDATE_INDICATOR = "U";

	public static final String CREATE_INDICATOR = "C";

	public static final String DELETE_INDICATOR = "D";

	private long cashMarginID;

	private long agreementID;

	private Date trxDate;

	private Amount nAPAmount;

	private boolean nAPSignAddInd;

	private BigDecimal cashInterest;

	private long groupID;

	private String status;

	private long versionTime;

	private String updateIndicator;

	/**
	 * Default Constructor.
	 */
	public OBCashMargin() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICashMargin
	 */
	public OBCashMargin(ICashMargin obj) {
		this();
		AccessorUtil.copyValue(obj, this);

	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getCashMarginID
	 */
	public long getCashMarginID() {
		return this.cashMarginID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setCashMarginID
	 */
	public void setCashMarginID(long cashMarginID) {
		this.cashMarginID = cashMarginID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getAgreementID
	 */
	public long getAgreementID() {
		return this.agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setAgreementID
	 */
	public void setAgreementID(long agreementID) {
		this.agreementID = agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getTrxDate
	 */
	public Date getTrxDate() {
		return this.trxDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setTrxDate
	 */
	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getNAPAmount
	 */
	public Amount getNAPAmount() {
		return this.nAPAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setNAPAmount
	 */
	public void setNAPAmount(Amount nAPAmount) {
		this.nAPAmount = nAPAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getNAPSignAdd
	 */
	public boolean getNAPSignAddInd() {
		return this.nAPSignAddInd;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setNAPSignAdd
	 */
	public void setNAPSignAddInd(boolean nAPSignAddInd) {
		this.nAPSignAddInd = nAPSignAddInd;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getNAIAmount
	 */
	public BigDecimal getCashInterest() {
		return this.cashInterest;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setNAIAmount
	 */
	public void setCashInterest(BigDecimal cashInterest) {
		this.cashInterest = cashInterest;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getGroupID
	 */
	public long getGroupID() {
		return this.groupID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setGroupID
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getStatus
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setStatus
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getVersionTime
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setVersionTime
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#getUpdateIndicator
	 */
	public String getUpdateIndicator() {
		return this.updateIndicator;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.ICashMargin#setUpdateIndicator
	 */
	public void setUpdateIndicator(String status) {
		this.updateIndicator = status;
	}

	/**
	 * Return true for performing update to this cash margin, otherwise false
	 * 
	 * @return boolean
	 */
	public boolean isUpdateInd() {
		return (this.updateIndicator.equals(UPDATE_INDICATOR) ? true : false);
	}

	/**
	 * Return true for performing create to this cash margin, otherwise false
	 * 
	 * @return boolean
	 */
	public boolean isCreateInd() {
		return (this.updateIndicator.equals(CREATE_INDICATOR) ? true : false);
	}

	/**
	 * Return true for performing delete to this cash margin, otherwise false
	 * 
	 * @return boolean
	 */
	public boolean isDeleteInd() {
		return (this.updateIndicator.equals(DELETE_INDICATOR) ? true : false);
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	/**
	 * Test for equality.
	 * 
	 * @param obj is of type Object
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (!(obj instanceof OBCashMargin)) {
			return false;
		}
		else {
			if (obj.hashCode() == this.hashCode()) {
				return true;
			}
			else {
				return false;
			}
		}
	}

}