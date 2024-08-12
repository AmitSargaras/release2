/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/cash/OBCashCollateral.java,v 1.9 2006/04/10 07:03:49 hshii Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.cash;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.OBCollateral;

/**
 * This class represents a Collateral of type cash entity.
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/04/10 07:03:49 $ Tag: $Name: $
 */
public class OBCashCollateral extends OBCollateral implements ICashCollateral {
	private boolean isInterestCapitalisation;

	private Amount minimalFSV;

	private String minimalFSVCcyCode;

	private ICashDeposit[] depositInfo;
	
	private Date priCaveatGuaranteeDate;
	
	private String description;
	
	private String creditCardRefNumber;
	
	private String issuer;
	
	public Date getPriCaveatGuaranteeDate() {
		return priCaveatGuaranteeDate;
	}

	public void setPriCaveatGuaranteeDate(Date priCaveatGuaranteeDate) {
		this.priCaveatGuaranteeDate = priCaveatGuaranteeDate;
	}

	public String getCreditCardRefNumber() {
		return creditCardRefNumber;
	}

	public void setCreditCardRefNumber(String creditCardRefNumber) {
		this.creditCardRefNumber = creditCardRefNumber;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	/**
	 * Default Constructor.
	 */
	public OBCashCollateral() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICashCollateral
	 */
	public OBCashCollateral(ICashCollateral obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get if it is interest capitalisation.
	 * 
	 * @return boolean
	 */
	public boolean getIsInterestCapitalisation() {
		return isInterestCapitalisation;
	}

	/**
	 * Set if it is interest capitalisation.
	 * 
	 * @param isInterestCapitalisation is of type boolean
	 */
	public void setIsInterestCapitalisation(boolean isInterestCapitalisation) {
		this.isInterestCapitalisation = isInterestCapitalisation;
	}

	/**
	 * Get cash deposit information.
	 * 
	 * @return a list of cash deposit
	 */
	public ICashDeposit[] getDepositInfo() {
		return depositInfo;
	}

	/**
	 * Set cash deposit information.
	 * 
	 * @param depositInfo a list of cash deposit
	 */
	public void setDepositInfo(ICashDeposit[] depositInfo) {
		this.depositInfo = depositInfo;
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
		else if (!(obj instanceof OBCashCollateral)) {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}