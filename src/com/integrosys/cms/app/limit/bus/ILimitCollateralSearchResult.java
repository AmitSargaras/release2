/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/ILimitCollateralSearchResult.java,v 1.2 2003/07/11 03:44:13 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

/**
 * This interface represents a search result on collateral information due to
 * limits
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/11 03:44:13 $ Tag: $Name: $
 * @deprecated This class is no longer in use
 */
public interface ILimitCollateralSearchResult extends java.io.Serializable {
	// Getters

	/**
	 * Get the collateral ID
	 * 
	 * @return long
	 */
	public long getCollateralID();

	/**
	 * Get the collateral reference
	 * 
	 * @return String
	 */
	public String getCollateralRef();

	/**
	 * Get the Collateral Type String
	 * 
	 * @return String
	 */
	public String getCollateralTypeStr();

	/**
	 * Get the Collateral Sub-type String
	 * 
	 * @return String
	 */
	public String getCollateralSubTypeStr();

	/**
	 * Get the Collateral booking location
	 * 
	 * @return String
	 */
	public String getCollateralBookingLocation();

	// Setters
	/**
	 * Set the collateral ID
	 * 
	 * @param value is of type long
	 */
	public void setCollateralID(long value);

	/**
	 * Set the collateral reference
	 * 
	 * @param value is of type String
	 */
	public void setCollateralRef(String value);

	/**
	 * Set the Collateral Type String
	 * 
	 * @param value is of type String
	 */
	public void setCollateralTypeStr(String value);

	/**
	 * Set the Collateral Sub-type String
	 * 
	 * @param value is of type String
	 */
	public void setCollateralSubTypeStr(String value);

	/**
	 * Set the Collateral booking location
	 * 
	 * @param value is of type String
	 */
	public void setCollateralBookingLocation(String value);
}