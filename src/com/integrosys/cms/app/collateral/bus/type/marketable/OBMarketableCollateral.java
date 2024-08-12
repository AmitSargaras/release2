/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/OBMarketableCollateral.java,v 1.6 2003/11/06 11:43:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;

/**
 * This class represents a Collateral of type Marketable.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/11/06 11:43:24 $ Tag: $Name: $
 */
public class OBMarketableCollateral extends OBCollateral implements IMarketableCollateral {
	private IMarketableEquity[] equityList;

	private Amount minimalFSV;

	private String minimalFSVCcyCode;

	/**
	 * Default Constructor.
	 */
	public OBMarketableCollateral() {
		super();
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IMarketableCollateral
	 */
	public OBMarketableCollateral(IMarketableCollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get a list of marketable equity.
	 * 
	 * @return IMarketableEquity[]
	 */
	public IMarketableEquity[] getEquityList() {
		return equityList;
	}

	/**
	 * Set marketable equity list.
	 * 
	 * @param equityList of type IMarketableEquity[]
	 */
	public void setEquityList(IMarketableEquity[] equityList) {
		this.equityList = equityList;
	}

	/**
	 * Get minimal FSV.
	 * 
	 * @return Amount
	 */
	public Amount getMinimalFSV() {
		return minimalFSV;
	}

	/**
	 * Set minimal FSV.
	 * 
	 * @param minimalFSV of type Amount
	 */
	public void setMinimalFSV(Amount minimalFSV) {
		this.minimalFSV = minimalFSV;
	}

	/**
	 * Get minimal FSV currency code.
	 * 
	 * @return String
	 */
	public String getMinimalFSVCcyCode() {
		return minimalFSVCcyCode;
	}

	/**
	 * Set minimal FSV currency code.
	 * 
	 * @param minimalFSVCcyCode of type String
	 */
	public void setMinimalFSVCcyCode(String minimalFSVCcyCode) {
		this.minimalFSVCcyCode = minimalFSVCcyCode;
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
		else if (!(obj instanceof OBMarketableCollateral)) {
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
	
	Amount cappedPrice;
	
	String stockCounterCode;
	
	double interestRate;

	public Amount getCappedPrice() {
		return cappedPrice;
	}

	public void setCappedPrice(Amount cappedPrice) {
		this.cappedPrice = cappedPrice;
	}

	public String getStockCounterCode() {
		return stockCounterCode;
	}

	public void setStockCounterCode(String stockCounterCode) {
		this.stockCounterCode = stockCounterCode;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
}