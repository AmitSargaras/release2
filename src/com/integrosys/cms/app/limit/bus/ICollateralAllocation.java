/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/ICollateralAllocation.java,v 1.10 2006/08/01 12:50:25 czhou Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.cms.app.collateral.bus.ICollateral;

/**
 * This interface represents a limit to collateral allocation relationship.
 * 
 * ********************************************************************* NOTE:
 * The ICollateral object contained in ICollateralAllocation does not contain
 * all collateral details. It only contain the common fields.
 * **********************************************************************
 * 
 * @author $Author: czhou $
 * @version $Revision: 1.10 $
 * @since $Date: 2006/08/01 12:50:25 $ Tag: $Name: $
 */
public interface ICollateralAllocation extends java.io.Serializable {
	/**
	 * Get charge id.
	 * 
	 * @return long
	 */
	public long getChargeID();

	/**
	 * Set charge id.
	 * 
	 * @param chargeID of type long
	 */
	public void setChargeID(long chargeID);

	/**
	 * Get collateral
	 * 
	 * @return ICollateral
	 */
	public ICollateral getCollateral();

	/**
	 * Set collateral
	 * 
	 * @param collateral of type ICollateral
	 */
	public void setCollateral(ICollateral collateral);

	/**
	 * Set the host record change status
	 * 
	 * @param value is of type String
	 */
	public void setHostStatus(String value);

	/**
	 * Get the host record change status
	 * 
	 * @return String
	 */
	public String getHostStatus();

	/**
	 * Get limit-security linkage reference id.
	 * 
	 * @return long
	 */
	public long getSCILimitSecMapID();

	/**
	 * Set limit-security linkage reference id.
	 * 
	 * @param sciLimitMapID long
	 */
	public void setSCILimitSecMapID(long sciLimitMapID);

	/**
	 * Get the coborrower limit id.
	 * @return coborrower limit id
	 */
	public long getCoborrowerLimitID();

	/**
	 * Sets the coborrower limit id
	 * @param coborrowerLimitID - Co-Borrower Limit ID
	 */
	public void setCoborrowerLimitID(long coborrowerLimitID);

	public long getLimitID();

	public void setLimitID(long limitID);

	public String getSourceLmtId();

	public void setSourceLmtId(String sourceLmtId);

	public String getCustomerCategory();

	public void setCustomerCategory(String customerCategory);

	/**
	 * Get Limit Profile ID
	 * 
	 * @return long
	 */
	public long getLimitProfileID();

	/**
	 * Set Limit Profile ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitProfileID(long value);

	public String getSourceID();

	public void setSourceID(String value);

	public String getLmtSecurityCoverage();

	public void setLmtSecurityCoverage(String lmtSecurityCoverage);
	
	public String getCpsSecurityId();

	public void setCpsSecurityId(String cpsSecurityId);
}