/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/IGenChargeMapEntry.java,v 1.3 2005/05/19 12:15:50 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import java.io.Serializable;

/**
 * This class represents Asset of type General Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/05/19 12:15:50 $ Tag: $Name: $
 */
public interface IGenChargeMapEntry extends Serializable {
	/**
	 * Get mapping entry id.
	 * 
	 * @return long
	 */
	public long getMapEntryID();

	/**
	 * Set mapping entry.
	 * 
	 * @param mapEntryID of type long
	 */
	public void setMapEntryID(long mapEntryID);

	/**
	 * Get ref ID.
	 * 
	 * @return long
	 */
	public long getRefID();

	/**
	 * Set ref ID.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID);

	/**
	 * Get insurance ID.
	 * 
	 * @return String
	 */
	public String getInsuranceID();

	/**
	 * Set insurance ID.
	 * 
	 * @param insuranceID of type String
	 */
	public void setInsuranceID(String insuranceID);

	/**
	 * Get entry Value ID.
	 * 
	 * @return String
	 */
	public String getEntryValueID();

	/**
	 * Set entry Value ID.
	 * 
	 * @param entryValueID of type String
	 */
	public void setEntryValueID(String entryValueID);

	// weiling : to be phased out. replaced by recoverable amt in stock.
	/**
	 * Get coverage amount of this insurance against the general charge subtype
	 * in CMS Security Currency.
	 * 
	 * @return Amount - Coverage Amount
	 */
	// public Amount getInsrCoverageAmount();
	// weiling : to be phased out. replaced by recoverable amt in stock.
	/**
	 * Set coverage amount of this insurance against the general charge subtype
	 * in CMS Security Currency.
	 * 
	 * @param insrCoverageAmt - Amount
	 */
	// public void setInsrCoverageAmount(Amount insrCoverageAmt);
	/**
	 * Get the status of the insurance stock map, deleted or active.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set the status of the insurance stock map, deleted or active.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);
}