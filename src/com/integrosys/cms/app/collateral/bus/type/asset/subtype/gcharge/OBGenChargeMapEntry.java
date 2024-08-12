/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/type/asset/subtype/gcharge/OBGenChargeMapEntry.java,v 1.3 2005/05/19 12:15:50 wltan Exp $
 */
package com.integrosys.cms.app.collateral.bus.type.asset.subtype.gcharge;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents Asset of type General Charge.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/05/19 12:15:50 $ Tag: $Name: $
 */
public class OBGenChargeMapEntry implements IGenChargeMapEntry {
	private long mapEntryID = ICMSConstant.LONG_MIN_VALUE;

	private long refID = ICMSConstant.LONG_MIN_VALUE;

	private String insuranceID;

	private String entryValueID;

	private String status = ICMSConstant.STATE_ACTIVE;

	private Amount insrCoverageAmt;

	/**
	 * Default Constructor.
	 */
	public OBGenChargeMapEntry() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type IGenChargeMapEntry
	 */
	public OBGenChargeMapEntry(IGenChargeMapEntry obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get mapping entry id.
	 * 
	 * @return long
	 */
	public long getMapEntryID() {
		return mapEntryID;
	}

	/**
	 * Set mapping entry.
	 * 
	 * @param mapEntryID of type long
	 */
	public void setMapEntryID(long mapEntryID) {
		this.mapEntryID = mapEntryID;
	}

	/**
	 * Get ref ID.
	 * 
	 * @return long
	 */
	public long getRefID() {
		return refID;
	}

	/**
	 * Set ref ID.
	 * 
	 * @param refID of type long
	 */
	public void setRefID(long refID) {
		this.refID = refID;
	}

	/**
	 * Get insurance ID.
	 * 
	 * @return String
	 */
	public String getInsuranceID() {
		return insuranceID;
	}

	/**
	 * Set insurance ID.
	 * 
	 * @param insuranceID of type String
	 */
	public void setInsuranceID(String insuranceID) {
		this.insuranceID = insuranceID;
	}

	/**
	 * Get entry Value ID.
	 * 
	 * @return String
	 */
	public String getEntryValueID() {
		return entryValueID;
	}

	/**
	 * Set entry Value ID.
	 * 
	 * @param entryValueID of type String
	 */
	public void setEntryValueID(String entryValueID) {
		this.entryValueID = entryValueID;
	}

	// weiling : to be phased out. replaced by recoverable amt in stock.
	/**
	 * Get coverage amount of this insurance against the general charge subtype
	 * in CMS Security Currency.
	 * 
	 * @return Amount - Coverage Amount
	 */
	/*
	 * public Amount getInsrCoverageAmount() { return insrCoverageAmt; }
	 */

	// weiling : to be phased out. replaced by recoverable amt in stock.
	/**
	 * Set coverage amount of this insurance against the general charge subtype
	 * in CMS Security Currency.
	 * 
	 * @param insrCoverageAmt - Amount
	 */
	/*
	 * public void setInsrCoverageAmount(Amount insrCoverageAmt) {
	 * this.insrCoverageAmt = insrCoverageAmt; }
	 */

	/**
	 * Get the status of the insurance stock map, deleted or active.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set the status of the insurance stock map, deleted or active.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}