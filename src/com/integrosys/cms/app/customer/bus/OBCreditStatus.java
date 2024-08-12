/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBCreditStatus.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a customer credit status.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBCreditStatus implements ICreditStatus {
	private long _csID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _csRef = null;

	private String _csType = null;

	private String _csValue = null;

	private Date _effectiveDate = null;

	private String _comments = null;

	/**
	 * Default Constructor
	 */
	public OBCreditStatus() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICreditStatus
	 */
	public OBCreditStatus(ICreditStatus value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get credit status ID
	 * 
	 * @return long
	 */
	public long getCSID() {
		return _csID;
	}

	/**
	 * Get the Credit Status reference
	 * 
	 * @return String
	 */
	public String getCSReference() {
		return _csRef;
	}

	/**
	 * Get credit status type
	 * 
	 * @return String
	 */
	public String getCSType() {
		return _csType;
	}

	/**
	 * Get credit status value
	 * 
	 * @return String
	 */
	public String getCSValue() {
		return _csValue;
	}

	/**
	 * Get effective date
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate() {
		return _effectiveDate;
	}

	/**
	 * Get comments
	 * 
	 * @return String
	 */
	public String getComments() {
		return _comments;
	}

	// Setters

	/**
	 * Set credit status ID
	 * 
	 * @param value is of type long
	 */
	public void setCSID(long value) {
		_csID = value;
	}

	/**
	 * Set the Credit Status reference
	 * 
	 * @param value is of type String
	 */
	public void setCSReference(String value) {
		_csRef = value;
	}

	/**
	 * Set credit status type
	 * 
	 * @param value is of type String
	 */
	public void setCSType(String value) {
		_csType = value;
	}

	/**
	 * Set credit status value
	 * 
	 * @param value is of type String
	 */
	public void setCSValue(String value) {
		_csValue = value;
	}

	/**
	 * Set effective date
	 * 
	 * @param value is of type Date
	 */
	public void setEffectiveDate(Date value) {
		_effectiveDate = value;
	}

	/**
	 * Set comments
	 * 
	 * @param value is of type String
	 */
	public void setComments(String value) {
		_comments = value;
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