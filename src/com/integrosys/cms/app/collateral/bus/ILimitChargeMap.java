/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ILimitChargeMap.java,v 1.6 2006/08/25 10:27:06 nkumar Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * This interface represents mapping of limit and charge table.
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/25 10:27:06 $ Tag: $Name: $
 */
public interface ILimitChargeMap extends ICollateralLimitMap {
	/**
	 * Get mapping id of limit and charge.
	 * 
	 * @return long
	 */
	public long getLimitChargeMapID();

	/**
	 * Set mapping id for limit and charge.
	 * 
	 * @param limitChargeMapID of type long
	 */
	public void setLimitChargeMapID(long limitChargeMapID);

	/**
	 * Get charge detail id.
	 * 
	 * @return long
	 */
	public long getChargeDetailID();

	/**
	 * Set charge detail id.
	 * 
	 * @param chargeDetailID of type long
	 */
	public void setChargeDetailID(long chargeDetailID);

	/**
	 * Get status of this limit charge map.
	 * 
	 * @return String
	 */
	public String getStatus();

	/**
	 * Set status of this limit charge map.
	 * 
	 * @param status of type String
	 */
	public void setStatus(String status);

	// start.. Cr035
	/**
	 * get co borrower limit id
	 * 
	 * @return coBorrowerLimitID of type long
	 */
	public long getCoBorrowerLimitID();

	/**
	 * Set co borrower id
	 * 
	 * @param coBorrowerLimitID of type long
	 */
	public void setCoBorrowerLimitID(long coBorrowerLimitID);

	public String getCustomerCategory();

	public void setCustomerCategory(String customerCategory);
	

	public abstract String getLmtSecurityCoverage();

	public abstract void setLmtSecurityCoverage(String lmtSecurityCoverage);
	
	public abstract String getCpsSecurityId();

	public abstract void setCpsSecurityId(String cpsSecurityId);

	// end.. Cr035
}