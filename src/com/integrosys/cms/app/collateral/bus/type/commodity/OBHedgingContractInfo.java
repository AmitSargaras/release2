/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/commodity/OBHedgingContractInfo.java,v 1.5 2004/07/22 12:07:43 lyng Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.commodity;

import java.util.Date;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a Commodity Hedging Contract Info.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2004/07/22 12:07:43 $ Tag: $Name: $
 */
public class OBHedgingContractInfo implements IHedgingContractInfo {
	private long hedgingContractInfoID;

	private String globalTreasuryReference;

	private Date dateOfDeal;

	private String nameOfTheCounterParty;

	private Amount dealAmount;

	private String hedgingAgreement;

	private Date hedgingAgreementDate;

	private int margin;

	private long commonRef = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	/**
	 * Default Constructor.
	 */
	public OBHedgingContractInfo() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IHedgingContractInfo
	 */
	public OBHedgingContractInfo(IHedgingContractInfo obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get primary key hedging contract info id.
	 * 
	 * @return long
	 */
	public long getHedgingContractInfoID() {
		return hedgingContractInfoID;
	}

	/**
	 * Set primary key hedging contract info id.
	 * 
	 * @param hedgingContractInfoID of type long
	 */
	public void setHedgingContractInfoID(long hedgingContractInfoID) {
		this.hedgingContractInfoID = hedgingContractInfoID;
	}

	/**
	 * Get global treasury reference number.
	 * 
	 * @return String
	 */
	public String getGlobalTreasuryReference() {
		return globalTreasuryReference;
	}

	/**
	 * Set global treasury reference number.
	 * 
	 * @param globalTreasuryReference of type String
	 */
	public void setGlobalTreasuryReference(String globalTreasuryReference) {
		this.globalTreasuryReference = globalTreasuryReference;
	}

	/**
	 * Get date of deal.
	 * 
	 * @return Date
	 */
	public Date getDateOfDeal() {
		return dateOfDeal;
	}

	/**
	 * Set date of deal.
	 * 
	 * @param dateOfDeal of type Date
	 */
	public void setDateOfDeal(Date dateOfDeal) {
		this.dateOfDeal = dateOfDeal;
	}

	/**
	 * Get name of counter party.
	 * 
	 * @return String
	 */
	public String getNameOfTheCounterParty() {
		return nameOfTheCounterParty;
	}

	/**
	 * Set name of counter party.
	 * 
	 * @param nameOfTheCounterParty of type String
	 */
	public void setNameOfTheCounterParty(String nameOfTheCounterParty) {
		this.nameOfTheCounterParty = nameOfTheCounterParty;
	}

	/**
	 * Get deal amount.
	 * 
	 * @return Amount
	 */
	public Amount getDealAmount() {
		return dealAmount;
	}

	/**
	 * Set deal amount.
	 * 
	 * @param dealAmount of type Amount
	 */
	public void setDealAmount(Amount dealAmount) {
		this.dealAmount = dealAmount;
	}

	/**
	 * Get hedging agreement reference.
	 * 
	 * @return String
	 */
	public String getHedgingAgreement() {
		return hedgingAgreement;
	}

	/**
	 * Set hedging agreement reference.
	 * 
	 * @param hedgingAgreement of type String
	 */
	public void setHedgingAgreement(String hedgingAgreement) {
		this.hedgingAgreement = hedgingAgreement;
	}

	/**
	 * Get date of hedging agreement.
	 * 
	 * @return Date
	 */
	public Date getHedgingAgreementDate() {
		return hedgingAgreementDate;
	}

	/**
	 * Set date of hedging agreement.
	 * 
	 * @param hedgingAgreementDate of type Date
	 */
	public void setHedgingAgreementDate(Date hedgingAgreementDate) {
		this.hedgingAgreementDate = hedgingAgreementDate;
	}

	/**
	 * Get hedge margin.
	 * 
	 * @return int
	 */
	public int getMargin() {
		return margin;
	}

	/**
	 * Set hedge margin.
	 * 
	 * @param margin of type int
	 */
	public void setMargin(int margin) {
		this.margin = margin;
	}

	/**
	 * Get common reference id across actual and staging hedging.
	 * 
	 * @return long
	 */
	public long getCommonRef() {
		return commonRef;
	}

	/**
	 * Set common reference id across actual and staging hedging.
	 * 
	 * @param commonRef of type long
	 */
	public void setCommonRef(long commonRef) {
		this.commonRef = commonRef;
	}

	/**
	 * Get cms business status.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set cms business status.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(hedgingContractInfoID);
		return hash.hashCode();
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
		else if (!(obj instanceof IHedgingContractInfo)) {
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
