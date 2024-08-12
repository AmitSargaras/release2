/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/base/businfra/customer/OBCustomer.java,v 1.2 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.base.businfra.customer;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a Customer entity. Customer contains a reference to the
 * Legal Entity.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class OBCustomer implements ICustomer {
	private long _customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ILegalEntity _entity = null;

	private String _custName = null;

	private Date _relStartDate = null;
	
	private Date _dateOfIncorporation = null;
	
	private Date _raraocPeriod = null;

	/**
	 * Default Constructor
	 */
	public OBCustomer() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICustomer
	 */
	public OBCustomer(ICustomer value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get the customer ID which is the primary key of this entity
	 * 
	 * @return long
	 */
	public long getCustomerID() {
		return _customerID;
	}

	/**
	 * Get the Legal Entity.
	 * 
	 * @return ILegalEntity
	 */
	public ILegalEntity getLegalEntity() {
		return _entity;
	}

	/**
	 * Get the customer name.
	 * 
	 * @return String
	 */
	public String getCustomerName() {
		return _custName;
	}

	/**
	 * Get the customer relationship start date
	 * 
	 * @return Date
	 */
	public Date getCustomerRelationshipStartDate() {
		return _relStartDate;
	}
	
	/**
	 * Get the date of incorporation
	 * 
	 * @return Date
	 */
	public Date getDateOfIncorporation() {
		return _dateOfIncorporation;
	}

	
	public Date getRaraocPeriod() {
		return _raraocPeriod;
	}
	
	// Setters
	/**
	 * Set the customer ID which is the primary key of this entity
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value) {
		_customerID = value;
	}

	/**
	 * Set the Legal Entity.
	 * 
	 * @param value is of type ILegalEntity
	 */
	public void setLegalEntity(ILegalEntity value) {
		_entity = value;
	}

	/**
	 * Set the customer name.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerName(String value) {
		_custName = value;
	}

	/**
	 * Set the customer relationship start date
	 * 
	 * @param value is of type Date
	 */
	public void setCustomerRelationshipStartDate(Date value) {
		_relStartDate = value;
	}
	
	/**
	 * Set the date of incorporation
	 * 
	 * @param value is of type Date
	 */
	public void setDateOfIncorporation(Date value) {
		 _dateOfIncorporation = value;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
	
	public void setRaraocPeriod(Date value) {
		 _raraocPeriod = value;
	}
}