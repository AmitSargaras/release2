/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/OBLimitSearchResult.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.limit.bus;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a search result on limit that includes security
 * information
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBLimitSearchResult implements ILimitSearchResult {
	private long _limitID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _limitRef = null;

	private long _outerLimitID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _bookingLoc = null;

	private String _productDesc = null;

	private Amount _approvedLimitAmt = null;

	private Amount _activatedLimitAmt = null;

	private ILimitCollateralSearchResult[] _collateralResults = null;

	/**
	 * Default Constructor
	 */
	public OBLimitSearchResult() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ILimitSearchResult
	 */
	public OBLimitSearchResult(ILimitSearchResult value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get Limit ID
	 * 
	 * @return long
	 */
	public long getLimitID() {
		return _limitID;
	}

	/**
	 * Get Limit Reference
	 * 
	 * @return String
	 */
	public String getLimitRef() {
		return _limitRef;
	}

	/**
	 * Get Outer Limit ID
	 * 
	 * @return long
	 */
	public long getOuterLimitID() {
		return _outerLimitID;
	}

	/**
	 * Get Booking Location
	 * 
	 * @return String
	 */
	public String getBookingLocation() {
		return _bookingLoc;
	}

	/**
	 * Get Product Description
	 * 
	 * @return String
	 */
	public String getProductDesc() {
		return _productDesc;
	}

	/**
	 * Get Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getApprovedLimitAmount() {
		return _approvedLimitAmt;
	}

	/**
	 * Get Activated Limit Amount
	 * 
	 * @return Amount
	 */
	public Amount getActivatedLimitAmount() {
		return _activatedLimitAmt;
	}

	/**
	 * Get the security information linked to the limit
	 * 
	 * @return ILimitCollateralSearchResult[]
	 */
	public ILimitCollateralSearchResult[] getCollateralSearchResults() {
		return _collateralResults;
	}

	// Setters

	/**
	 * Set Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setLimitID(long value) {
		_limitID = value;
	}

	/**
	 * Set Limit Reference
	 * 
	 * @param value is of type String
	 */
	public void setLimitRef(String value) {
		_limitRef = value;
	}

	/**
	 * Set Outer Limit ID
	 * 
	 * @param value is of type long
	 */
	public void setOuterLimitID(long value) {
		_outerLimitID = value;
	}

	/**
	 * Set Booking Location
	 * 
	 * @param value is of type String
	 */
	public void setBookingLocation(String value) {
		_bookingLoc = value;
	}

	/**
	 * Set Product Description
	 * 
	 * @param value is of type String
	 */
	public void setProductDesc(String value) {
		_productDesc = value;
	}

	/**
	 * Set Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setApprovedLimitAmount(Amount value) {
		_approvedLimitAmt = value;
	}

	/**
	 * Set Activated Limit Amount
	 * 
	 * @param value is of type Amount
	 */
	public void setActivatedLimitAmount(Amount value) {
		_activatedLimitAmt = value;
	}

	/**
	 * Set the security information linked to the limit
	 * 
	 * @param value is of type ILimitCollateralSearchResult[]
	 */
	public void setCollateralSearchResults(ILimitCollateralSearchResult[] value) {
		_collateralResults = value;
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