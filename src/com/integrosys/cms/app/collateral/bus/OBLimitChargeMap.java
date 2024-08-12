/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/OBLimitChargeMap.java,v 1.6 2006/08/25 10:24:04 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents mapping from limit map to charge detail table.
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/25 10:24:04 $ Tag: $Name: $
 */
public class OBLimitChargeMap extends OBCollateralLimitMap implements ILimitChargeMap {
	private long limitChargeMapID = ICMSConstant.LONG_INVALID_VALUE;

	private long chargeDetailID = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.STATE_ACTIVE;

	private long coBorrowerLimitID = ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory;
	
	private String lmtSecurityCoverage;
	
	private String cpsSecurityId;

	/**
	 * Default Constructor.
	 */
	public OBLimitChargeMap() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICollateralLimitMap
	 */
	public OBLimitChargeMap(ILimitChargeMap obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get mapping id of limit and charge.
	 * 
	 * @return long
	 */
	public long getLimitChargeMapID() {
		return limitChargeMapID;
	}

	/**
	 * Set mapping id for limit and charge.
	 * 
	 * @param limitChargeMapID of type long
	 */
	public void setLimitChargeMapID(long limitChargeMapID) {
		this.limitChargeMapID = limitChargeMapID;
	}

	/**
	 * Get charge detail id.
	 * 
	 * @return long
	 */
	public long getChargeDetailID() {
		return chargeDetailID;
	}

	/**
	 * Set charge detail id.
	 * 
	 * @param chargeDetailID of type long
	 */
	public void setChargeDetailID(long chargeDetailID) {
		this.chargeDetailID = chargeDetailID;
	}

	/**
	 * Get status of this limit charge map.
	 * 
	 * @return String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Set status of this limit charge map.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	// start.. Cr035
	public long getCoBorrowerLimitID() {
		return coBorrowerLimitID;
	}

	public void setCoBorrowerLimitID(long coBorrowerLimitID) {
		this.coBorrowerLimitID = coBorrowerLimitID;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	// end.. Cr035

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String getLmtSecurityCoverage() {
		return lmtSecurityCoverage;
	}

	public void setLmtSecurityCoverage(String lmtSecurityCoverage) {
		this.lmtSecurityCoverage = lmtSecurityCoverage;
	}
	
	public String getCpsSecurityId() {
		return cpsSecurityId;
	}

	public void setCpsSecurityId(String cpsSecurityId) {
		this.cpsSecurityId = cpsSecurityId;
	}

	/**
	 * Return the hash code
	 * 
	 * @return int
	 */
	public int hashCode() {
		String hash = String.valueOf(limitChargeMapID);
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
		else if (!(obj instanceof OBLimitChargeMap)) {
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