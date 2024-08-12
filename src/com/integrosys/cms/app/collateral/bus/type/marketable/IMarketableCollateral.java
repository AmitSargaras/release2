/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/marketable/IMarketableCollateral.java,v 1.6 2003/11/06 11:43:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.marketable;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * This interface represents a Collateral of type Marketable.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2003/11/06 11:43:24 $ Tag: $Name: $
 */
public interface IMarketableCollateral extends ICollateral {
	/**
	 * Get a list of marketable equity.
	 * 
	 * @return IMarketableEquity[]
	 */
	public IMarketableEquity[] getEquityList();

	/**
	 * Set marketable equity list.
	 * 
	 * @param equityList of type IMarketableEquity[]
	 */
	public void setEquityList(IMarketableEquity[] equityList);

	/**
	 * Get minimal FSV.
	 * 
	 * @return Amount
	 */
	public Amount getMinimalFSV();

	/**
	 * Set minimal FSV.
	 * 
	 * @param minimalFSV of type Amount
	 */
	public void setMinimalFSV(Amount minimalFSV);

	/**
	 * Get minimal FSV currency code.
	 * 
	 * @return String
	 */
	public String getMinimalFSVCcyCode();

	/**
	 * Set minimal FSV currency code.
	 * 
	 * @param minimalFSVCcyCode of type String
	 */
	public void setMinimalFSVCcyCode(String minimalFSVCcyCode);
	
	public Amount getCappedPrice();
	
	public void setCappedPrice(Amount cappedPrice);
	
	public String getStockCounterCode();
	
	public void setStockCounterCode(String stockCounterCode);
	
	public double getInterestRate();
	
	public void setInterestRate(double interestRate);
}
