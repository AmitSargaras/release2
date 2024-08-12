/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/IHedgingContractInfo.java,v 1.5 2004/07/22 12:07:43 lyng Exp $
 */

package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a commodity hedging contract.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/07/22 12:07:43 $ Tag: $Name: $
 */
public interface IHedgingContractInfo extends java.io.Serializable {
	/**
	 * Get primary key hedging contract info id.
	 * 
	 * @return long
	 */
	public long getHedgingContractInfoID();

	/**
	 * Set primary key hedging contract info id.
	 * 
	 * @param hedgingContractInfoID of type long
	 */
	public void setHedgingContractInfoID(long hedgingContractInfoID);

	/**
	 * Get global treasury reference number.
	 * 
	 * @return String
	 */
	public String getGlobalTreasuryReference();

	/**
	 * Set global treasury reference number.
	 * 
	 * @param globalTreasuryReference of type String
	 */
	public void setGlobalTreasuryReference(String globalTreasuryReference);

	/**
	 * Get date of deal.
	 * 
	 * @return Date
	 */
	public Date getDateOfDeal();

	/**
	 * Set date of deal.
	 * 
	 * @param dateOfDeal of type Date
	 */
	public void setDateOfDeal(Date dateOfDeal);

	/**
	 * Get name of counter party.
	 * 
	 * @return String
	 */
	public String getNameOfTheCounterParty();

	/**
	 * Set name of counter party.
	 * 
	 * @param nameOfTheCounterParty of type String
	 */
	public void setNameOfTheCounterParty(String nameOfTheCounterParty);

	/**
	 * Get deal amount.
	 * 
	 * @return Amount
	 */
	public Amount getDealAmount();

	/**
	 * Set deal amount.
	 * 
	 * @param dealAmount of type Amount
	 */
	public void setDealAmount(Amount dealAmount);

	/**
	 * Get hedging agreement reference.
	 * 
	 * @return String
	 */
	public String getHedgingAgreement();

	/**
	 * Set hedging agreement reference.
	 * 
	 * @param hedgingAgreement of type String
	 */
	public void setHedgingAgreement(String hedgingAgreement);

	/**
	 * Get date of hedging agreement.
	 * 
	 * @return Date
	 */
	public Date getHedgingAgreementDate();

	/**
	 * Set date of hedging agreement.
	 * 
	 * @param hedgingAgreementDate of type Date
	 */
	public void setHedgingAgreementDate(Date hedgingAgreementDate);

	/**
	 * Get hedge margin.
	 * 
	 * @return int
	 */
	public int getMargin();

	/**
	 * Set hedge margin.
	 * 
	 * @param margin of type int
	 */
	public void setMargin(int margin);

	/**
	 * Get common reference id across actual and staging hedging.
	 * 
	 * @return long
	 */
	public long getCommonRef();

	/**
	 * Set common reference id across actual and staging hedging.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef);

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}
