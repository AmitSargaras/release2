/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/ILimitSearchResult.java,v 1.1 2003/07/03 02:04:51 kllee Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.businfra.currency.Amount;

/**
 * This interface represents a search result on limit that includes security
 * information
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/03 02:04:51 $ Tag: $Name: $
 */
public interface ILimitSearchResult extends java.io.Serializable {
	// Getters

	/**
	 * Get Limit ID
	 * 
	 * @return long
	 */
	public long getLimitID();

	/**
	 * Get Limit Reference
	 * 
	 * @return String
	 */
	public String getLimitRef();

	/**
	 * Get Outer Limit ID
	 * 
	 * @return String
	 */
	public long getOuterLimitID();

	/**
	 * Get Booking Location
	 * 
	 * @return String
	 */
	public String getBookingLocation();

	/**
	 * Get Product Description
	 * 
	 * @return String
	 */
	public String getProductDesc();

	/**
	 * Get Approved Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getApprovedLimitAmount();

	/**
	 * Get Activated Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getActivatedLimitAmount();

	/**
	 * Get the security information linked to the limit
	 * 
	 * @return ILimitCollateralSearchResult[]
	 */
	public ILimitCollateralSearchResult[] getCollateralSearchResults();

	// Setters

	/**
	 * Set Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitID(long value);

	/**
	 * Set Limit Reference
	 * 
	 * @param value is of type String
	 */
	public void setLimitRef(String value);

	/**
	 * Set Outer Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setOuterLimitID(long value);

	/**
	 * Set Booking Location
	 * 
	 * @param value is of type String
	 */
	public void setBookingLocation(String value);

	/**
	 * Set Product Description
	 * 
	 * @param value is of type String
	 */
	public void setProductDesc(String value);

	/**
	 * Set Approved Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setApprovedLimitAmount(Amount value);

	/**
	 * Set Activated Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setActivatedLimitAmount(Amount value);

	/**
	 * Set the security information linked to the limit
	 * 
	 * @param value is of type ILimitCollateralSearchResult[]
	 */
	public void setCollateralSearchResults(ILimitCollateralSearchResult[] value);
}