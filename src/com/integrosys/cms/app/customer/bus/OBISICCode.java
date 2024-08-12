/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBISICCode.java,v 1.3 2005/01/07 08:58:59 pooja Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents an ISIC code of a Legal entity.
 * 
 * @author $Author: pooja $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/01/07 08:58:59 $ Tag: $Name: $
 */
public class OBISICCode implements IISICCode {
	private long _id = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _isicRef = null;

	private String _isicType = null;

	private String _isicCode = null;

	private Date _effectiveDate = null;

	private String _weightage = null;

	private String _isicStatus = null;

	/**
	 * Default Constructor
	 */
	public OBISICCode() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IISICCode
	 */
	public OBISICCode(IISICCode value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get the ISIC ID
	 * 
	 * @return long
	 */
	public long getISICID() {
		return _id;
	}

	/**
	 * Get the ISIC Reference
	 * 
	 * @return String
	 */
	public String getISICReference() {
		return _isicRef;
	}

	/**
	 * Get ISIC type
	 * 
	 * @return String
	 */
	public String getISICType() {
		return _isicType;
	}

	/**
	 * Get ISIC Code
	 * 
	 * @return String
	 */
	public String getISICCode() {
		return _isicCode;
	}

	/**
	 * Get ISIC Effective Date
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate() {
		return _effectiveDate;
	}

	/**
	 * Get ISIC Weightage
	 * 
	 * @return String
	 */
	public String getISICWeightage() {
		return _weightage;
	}

	/**
	 * Get the ISIC Status
	 * 
	 * @return String
	 */
	public String getISICStatus() {
		return _isicStatus;
	}

	// Setters

	/**
	 * Set the ISIC ID
	 * 
	 * @param value is of type long
	 */
	public void setISICID(long value) {
		_id = value;
	}

	/**
	 * Set the ISIC Reference
	 * 
	 * @param value is of type String
	 */
	public void setISICReference(String value) {
		_isicRef = value;
	}

	/**
	 * Set ISIC type
	 * 
	 * @param value is of type String
	 */
	public void setISICType(String value) {
		_isicType = value;
	}

	/**
	 * Set ISIC Code
	 * 
	 * @param value is of type String
	 */
	public void setISICCode(String value) {
		_isicCode = value;
	}

	/**
	 * Set ISIC Effective Date
	 * 
	 * @param value is of type Date
	 */
	public void setEffectiveDate(Date value) {
		_effectiveDate = value;
	}

	/**
	 * Set ISIC Weightage
	 * 
	 * @param value is of type String
	 */
	public void setISICWeightage(String value) {
		_weightage = value;
	}

	/**
	 * Set the ISIC Status
	 * 
	 * @param value is of type String
	 */
	public void setISICStatus(String value) {
		_isicStatus = value;
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