/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.tradingbook.bus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a cash margin.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface ICashMargin extends Serializable {
	/**
	 * Get cash margin ID.
	 * 
	 * @return long
	 */
	public long getCashMarginID();

	/**
	 * Set cash margin ID.
	 * 
	 * @param cashMarginID of type long
	 */
	public void setCashMarginID(long cashMarginID);

	/**
	 * Get Agreement ID.
	 * 
	 * @return long
	 */
	public long getAgreementID();

	/**
	 * Set Agreement ID.
	 * 
	 * @param agreementID of type long
	 */
	public void setAgreementID(long agreementID);

	/**
	 * Get trx date.
	 * 
	 * @return Date
	 */
	public Date getTrxDate();

	/**
	 * Set trx date.
	 * 
	 * @param trxDate of type Date
	 */
	public void setTrxDate(Date trxDate);

	/**
	 * Get NAP amount.
	 * 
	 * @return Amount
	 */
	public Amount getNAPAmount();

	/**
	 * Set NAP amount.
	 * 
	 * @param nAPAmount of type Amount
	 */
	public void setNAPAmount(Amount nAPAmount);

	/**
	 * Get NAP Sign Add indicator.
	 * 
	 * @return boolean
	 */
	public boolean getNAPSignAddInd();

	/**
	 * Set NAP Sign Add indicator.
	 * 
	 * @param nAPSignAdd of type boolean
	 */
	public void setNAPSignAddInd(boolean nAPSignAdd);

	/**
	 * Get cash interest.
	 * 
	 * @return BigDecimal
	 */
	public BigDecimal getCashInterest();

	/**
	 * Set cash interest.
	 * 
	 * @param value of type BigDecimal
	 */
	public void setCashInterest(BigDecimal value);

	/**
	 * Get group id.
	 * 
	 * @return long
	 */
	public long getGroupID();

	/**
	 * Set group id.
	 * 
	 * @param groupID of type long
	 */
	public void setGroupID(long groupID);

	/**
	 * Get status
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set status
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	/**
	 * Get the version time.
	 * 
	 * @return long
	 */
	public long getVersionTime();

	/**
	 * Set the version time.
	 * 
	 * @param versionTime is of type long
	 */
	public void setVersionTime(long versionTime);

	/**
	 * Get update indicator
	 * 
	 * @return String
	 */
	public String getUpdateIndicator();

	/**
	 * Set update indicator
	 * 
	 * @param value of type String
	 */
	public void setUpdateIndicator(String value);

}
