/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds a deal valuation.
 * 
 * @author $Author: Pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBDealValuation implements IDealValuation {
	private long dealValuationID;

	private long cmsDealID;

	private Amount marketValue;

	private long groupID;

	private String status;

	private long versionTime;

	/**
	 * Default Constructor.
	 */
	public OBDealValuation() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IDealValuation
	 */
	public OBDealValuation(IDealValuation obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGMRADeal
	 */
	public OBDealValuation(IGMRADeal obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Update specific deal valuation to this object.
	 * 
	 * @param obj is of type IDealValuation
	 */
	public void update(IDealValuation obj) {
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getDealValuationID
	 */
	public long getDealValuationID() {
		return this.dealValuationID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setDealValuationID
	 */
	public void setDealValuationID(long dealID) {
		this.dealValuationID = dealID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getCMSDealID
	 */
	public long getCMSDealID() {
		return this.cmsDealID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setCMSDealID
	 */
	public void setCMSDealID(long cmsDealID) {
		this.cmsDealID = cmsDealID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getMarketValue
	 */
	public Amount getMarketValue() {
		return this.marketValue;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setMarketValue
	 */
	public void setMarketValue(Amount value) {
		this.marketValue = value;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getGroupID
	 */
	public long getGroupID() {
		return this.groupID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setGroupID
	 */
	public void setGroupID(long groupID) {
		this.groupID = groupID;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getStatus
	 */
	public String getStatus() {
		return this.status;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setStatus
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#getVersionTime
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IDealValuation#setVersionTime
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
		else if (!(obj instanceof OBDealValuation)) {
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