/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds common data for a Deal.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBDeal implements IDeal {
	private long cmsDealID;

	private long agreementID;

	private String dealID;

	private String productType;

	private Amount dealAmount;

	private Amount notionalAmount;

	private Date tradeDate;

	private Date maturityDate;

	private String status;

	private long versionTime;

	/**
	 * Default Constructor.
	 */
	public OBDeal() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IDeal
	 */
	public OBDeal(IDeal obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getCMSDealID
	 */
	public long getCMSDealID() {
		return this.cmsDealID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setCMSDealID
	 */
	public void setCMSDealID(long dealID) {
		this.cmsDealID = dealID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getAgreementID
	 */
	public long getAgreementID() {
		return this.agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setAgreementID
	 */
	public void setAgreementID(long agreementID) {
		this.agreementID = agreementID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getDealID
	 */
	public String getDealID() {
		return this.dealID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setDealID
	 */
	public void setDealID(String dealId) {
		this.dealID = dealId;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getProductType
	 */
	public String getProductType() {
		return this.productType;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setProductType
	 */
	public void setProductType(String productType) {
		this.productType = productType;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getDealAmount
	 */
	public Amount getDealAmount() {
		return this.dealAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setDealAmount
	 */
	public void setDealAmount(Amount tradePrice) {
		this.dealAmount = tradePrice;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getNotionalAmount
	 */
	public Amount getNotionalAmount() {
		return this.notionalAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setNotionalAmount
	 */
	public void setNotionalAmount(Amount notionalAmount) {
		this.notionalAmount = notionalAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getTradeDate
	 */
	public Date getTradeDate() {
		return this.tradeDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setTradeDate
	 */
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getMaturityDate
	 */
	public Date getMaturityDate() {
		return this.maturityDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setMaturityDate
	 */
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getStatus
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setStatus
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#getVersionTime
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDeal#setVersionTime
	 */
	public void setVersionTime(long versionTime) {
		this.versionTime = versionTime;
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
		else if (!(obj instanceof OBDeal)) {
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