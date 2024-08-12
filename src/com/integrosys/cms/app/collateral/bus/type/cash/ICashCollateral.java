/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/ICashCollateral.java,v 1.9 2006/04/10 07:03:49 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * This interface represents a Collateral of type Cash.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/04/10 07:03:49 $ Tag: $Name: $
 */
public interface ICashCollateral extends ICollateral {
	/**
	 * Get if it is interest capitalisation.
	 * 
	 * @return boolean
	 */
	public boolean getIsInterestCapitalisation();

	/**
	 * Set if it is interest capitalisation.
	 * 
	 * @param isInterestCapitalisation is of type boolean
	 */
	public void setIsInterestCapitalisation(boolean isInterestCapitalisation);

	/**
	 * Get cash deposit information.
	 * 
	 * @return a list of cash deposit
	 */
	public ICashDeposit[] getDepositInfo();

	/**
	 * Set cash deposit information.
	 * 
	 * @param depositInfo a list of cash deposit
	 */
	public void setDepositInfo(ICashDeposit[] depositInfo);

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
	
	public Date getPriCaveatGuaranteeDate();
	
	public void setPriCaveatGuaranteeDate(Date priCaveatGuaranteeDate);
	
	public String getDescription();
	
	public void setDescription(String description);
	
	public String getCreditCardRefNumber();
	
	public void setCreditCardRefNumber(String creditCardRefNumber);
	
	public String getIssuer();
	
	public void setIssuer(String issuer);
	
}