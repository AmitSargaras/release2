/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/OBCollateralAllocation.java,v 1.9 2006/08/01 12:50:25 czhou Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.ILimitCharge;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class represents a limit to collateral allocation relationship.
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.9 $
 * @since $Date: 2006/08/01 12:50:25 $ Tag: $Name: $
 */
public class OBCollateralAllocation implements ICollateralAllocation, Comparable {

	private static final long serialVersionUID = 7338442538239541897L;

	private long chargeID = ICMSConstant.LONG_MIN_VALUE;

	private ICollateral collateral = null;

	private long limitID = ICMSConstant.LONG_MIN_VALUE;

	// private long chargeDetailID = ICMSConstant.LONG_MIN_VALUE;
	private String hostStatus = null;

	private long sCILimitSecMapID;

	private long coborrowerLimitID;

	private String sourceLmtId;

	private String customerCategory = ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER;

	private long limitProfileID = ICMSConstant.LONG_INVALID_VALUE;

	private String sourceID = null;
	
	private String lmtSecurityCoverage;
	
	private String cpsSecurityId;

	/**
	 * Default Constructor.
	 */
	public OBCollateralAllocation() {
	}

	/**
	 * Construct the object from its interface.
	 * 
	 * @param obj is of type ICollateralLimitMap
	 */
	public OBCollateralAllocation(ICollateralAllocation obj) {
		this();
		AccessorUtil.copyValue(obj, this);
	}

	/**
	 * Get charge id.
	 * 
	 * @return long
	 */
	public long getChargeID() {
		return chargeID;
	}

	/**
	 * Get the coborrower limit id.
	 * @return coborrower limit id
	 */
	public long getCoborrowerLimitID() {
		return coborrowerLimitID;
	}

	/**
	 * Get collateral
	 * 
	 * @return ICollateral
	 */
	public ICollateral getCollateral() {
		return collateral;
	}

	public String getCustomerCategory() {
		return customerCategory;
	}

	/**
	 * Get the host record change status
	 * 
	 * @return String
	 */
	public String getHostStatus() {
		return hostStatus;
	}

	/**
	 * Get limit id.
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return limitID;
	}

	/**
	 * Get Limit Profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * Get limit-security linkage reference id.
	 * 
	 * @return long
	 */
	public long getSCILimitSecMapID() {
		return sCILimitSecMapID;
	}

	public String getSourceID() {
		return this.sourceID;
	}

	public String getSourceLmtId() {
		return sourceLmtId;
	}

	/**
	 * Set charge id.
	 * 
	 * @param chargeID of type long
	 */
	public void setChargeID(long chargeID) {
		this.chargeID = chargeID;
	}

	/**
	 * Sets the coborrower limit id
	 * @param coborrowerLimitID - Co-Borrower Limit ID
	 */
	public void setCoborrowerLimitID(long coborrowerLimitID) {
		this.coborrowerLimitID = coborrowerLimitID;
	}

	/**
	 * Set collateral
	 * 
	 * @param collateral of type ICollateral
	 */
	public void setCollateral(ICollateral collateral) {
		this.collateral = collateral;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	/**
	 * Set the host record change status
	 * 
	 * @param value is of type String
	 */
	public void setHostStatus(String value) {
		hostStatus = value;
	}

	/**
	 * Set limit id.
	 * 
	 * @param limitID of type long
	 */
	public void setLimitID(long limitID) {
		this.limitID = limitID;
	}

	/**
	 * Set Limit Profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value) {
		this.limitProfileID = value;
	}

	/**
	 * Set limit-security linkage reference id.
	 * 
	 * @param sciLimitSecMapID long
	 */
	public void setSCILimitSecMapID(long sciLimitSecMapID) {
		sCILimitSecMapID = sciLimitSecMapID;
	}

	public void setSourceID(String value) {
		this.sourceID = value;
	}

	public void setSourceLmtId(String sourceLmtId) {
		this.sourceLmtId = sourceLmtId;
	}

	/**
	 * Return a String representation of this object.
	 * 
	 * @return String
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer("CollateralAllocation: ");
		buf.append("CMS Limit Profile key [").append(limitProfileID).append("], ");
		buf.append("CMS Limit key [").append(limitID).append("], ");
		buf.append("CMS Collateral key [").append(
				(collateral == null) ? "null?" : Long.toString(collateral.getCollateralID())).append("], ");
		buf.append("Source Limit Id [").append(sourceLmtId).append("], ");
		buf.append("Source [").append(sourceID).append("], ");
		buf.append("Status [").append(hostStatus).append("]");

		return buf.toString();
	}

	public int compareTo(Object other) {
		long otherChargeID = (other == null) ? Long.MAX_VALUE : ((ICollateralAllocation) other).getChargeID();

		return (this.chargeID == otherChargeID) ? 0 : ((this.chargeID > otherChargeID) ? 1 : -1);
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
	
	
}