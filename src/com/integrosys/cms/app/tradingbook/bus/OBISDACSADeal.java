/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Data model holds a ISDA CSA deal.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBISDACSADeal extends OBDeal implements IISDACSADeal {
	private Date valueDate;

	private Amount NPVAmount;

	private Amount NPVBaseAmount;

	private Amount nearAmount;

	private Amount farAmount;

	private String counterparty;

	/**
	 * Default Constructor.
	 */
	public OBISDACSADeal() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IISDACSADeal
	 */
	public OBISDACSADeal(IISDACSADeal obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getValueDate
	 */
	public Date getValueDate() {
		return this.valueDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setValueDate
	 */
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getNPVAmount
	 */
	public Amount getNPVAmount() {
		return this.NPVAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setNPVAmount
	 */
	public void setNPVAmount(Amount nPV) {
		this.NPVAmount = nPV;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getNPVBaseAmount
	 */
	public Amount getNPVBaseAmount() {
		return this.NPVBaseAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setNPVBaseAmount
	 */
	public void setNPVBaseAmount(Amount nPVBase) {
		this.NPVBaseAmount = nPVBase;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getNearAmount
	 */
	public Amount getNearAmount() {
		return this.nearAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setNearAmount
	 */
	public void setNearAmount(Amount nearAmount) {
		this.nearAmount = nearAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#getFarAmount
	 */
	public Amount getFarAmount() {
		return this.farAmount;
	}

	/**
	 * @see com.integrosys.cms.app.tradingbook.bus.IISDACSADeal#setFarAmount
	 */
	public void setFarAmount(Amount farAmount) {
		this.farAmount = farAmount;
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
		else if (!(obj instanceof OBISDACSADeal)) {
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