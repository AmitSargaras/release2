/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/OBLimitCollateralSearchResult.java,v 1.3 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a search result on collateral information due to limits
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 * @deprecated This class is not longer in use
 */
public class OBLimitCollateralSearchResult implements ILimitCollateralSearchResult {
	private long _collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _collateralRef = null;

	private String _collateralTypeStr = null;

	private String _collateralSubTypeStr = null;

	private String _collateralBookingLocation = null;

	/**
	 * Default Constructor
	 */
	public OBLimitCollateralSearchResult() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ILimitCollateralSearchResult
	 */
	public OBLimitCollateralSearchResult(ILimitCollateralSearchResult value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get the collateral ID
	 * 
	 * @return long
	 */
	public long getCollateralID() {
		return _collateralID;
	}

	/**
	 * Get the collateral reference
	 * 
	 * @return String
	 */
	public String getCollateralRef() {
		return _collateralRef;
	}

	/**
	 * Get the Collateral Type String
	 * 
	 * @return String
	 */
	public String getCollateralTypeStr() {
		return _collateralTypeStr;
	}

	/**
	 * Get the Collateral Sub-type String
	 * 
	 * @return String
	 */
	public String getCollateralSubTypeStr() {
		return _collateralSubTypeStr;
	}

	/**
	 * Get the Collateral booking location
	 * 
	 * @return String
	 */
	public String getCollateralBookingLocation() {
		return _collateralBookingLocation;
	}

	// Setters
	/**
	 * Set the collateral ID
	 * 
	 * @param value is of type long
	 */
	public void setCollateralID(long value) {
		_collateralID = value;
	}

	/**
	 * Set the collateral reference
	 * 
	 * @param value is of type String
	 */
	public void setCollateralRef(String value) {
		_collateralRef = value;
	}

	/**
	 * Set the Collateral Type String
	 * 
	 * @param value is of type String
	 */
	public void setCollateralTypeStr(String value) {
		_collateralTypeStr = value;
	}

	/**
	 * Set the Collateral Sub-type String
	 * 
	 * @param value is of type String
	 */
	public void setCollateralSubTypeStr(String value) {
		_collateralSubTypeStr = value;
	}

	/**
	 * Set the Collateral booking location
	 * 
	 * @param value is of type String
	 */
	public void setCollateralBookingLocation(String value) {
		_collateralBookingLocation = value;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}