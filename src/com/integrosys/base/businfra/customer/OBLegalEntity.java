/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/base/businfra/customer/OBLegalEntity.java,v 1.3 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.base.businfra.customer;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a Legal Entity in the customer package. A Legal Entity
 * refers to portion of a customer's attributes that are constant across
 * different subdiaries. However this interface itself is an "incomplete"
 * picture of a customer's attributes, and therefore should be extended to
 * complete the definition of a customer.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class OBLegalEntity implements ILegalEntity {
	private long _id = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _legalName = null;

	private String _shortName = null;

	private String _legalConst = null;

	private String _legalRegCountry = null;

	private String _legalRegNumber = null;

	// private String _customerType = null;
	private Date _relStartDate = null;
	
	private Date _dateOfIncorporation = null;

	/**
	 * Default Constructor
	 */
	public OBLegalEntity() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ILegalEntity
	 */
	public OBLegalEntity(ILegalEntity value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get the Legal Entity ID, the primary key of this object.
	 * 
	 * @return long
	 */
	public long getLEID() {
		return _id;
	}

	/**
	 * Get the legal name.
	 * 
	 * @return String
	 */
	public String getLegalName() {
		return _legalName;
	}

	/**
	 * Get the short name of this entity.
	 * 
	 * @return String
	 */
	public String getShortName() {
		return _shortName;
	}

	/**
	 * Get the legal constitution, such as if the Legal entity is an MNC, or a
	 * Sole-Proprietor, etc.
	 * 
	 * 
	 * @return String
	 */
	public String getLegalConstitution() {
		return _legalConst;
	}

	/**
	 * Get the legal registration country. For consumer customers, this field
	 * can represent the birth country.
	 * 
	 * @return String
	 */
	public String getLegalRegCountry() {
		return _legalRegCountry;
	}

	/**
	 * Get the legal registration number. For consumer customers, this field can
	 * represent the NRIC number or order legal identifier.
	 * 
	 * @return String
	 */
	public String getLegalRegNumber() {
		return _legalRegNumber;
	}

	/*
	 * Get the customer type indicator
	 * 
	 * @return String
	 * 
	 * public String getCustomerType() { return _customerType; }
	 */
	/**
	 * Get the date when the customer started the relationship with the bank.
	 * 
	 * @return Date
	 */
	public Date getRelationshipStartDate() {
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

	// Setters

	/**
	 * Set the Legal Entity ID, the primary key of this object.
	 * 
	 * @param value is of type String
	 */
	public void setLEID(long value) {
		_id = value;
	}

	/**
	 * Set the legal name.
	 * 
	 * @param value is of type String
	 */
	public void setLegalName(String value) {
		_legalName = value;
	}

	/**
	 * Set the short name of this entity.
	 * 
	 * @param value is of type String
	 */
	public void setShortName(String value) {
		_shortName = value;
	}

	/**
	 * Set the legal constitution, such as if the Legal entity is an MNC, or a
	 * Sole-Proprietor, etc.
	 * 
	 * 
	 * @param value is of type String
	 */
	public void setLegalConstitution(String value) {
		_legalConst = value;
	}

	/**
	 * Set the legal registration country. For consumer customers, this field
	 * can represent the birth country.
	 * 
	 * @param value is of type String
	 */
	public void setLegalRegCountry(String value) {
		_legalRegCountry = value;
	}

	/**
	 * Set the legal registration number. For consumer customers, this field can
	 * represent the NRIC number or order legal identifier.
	 * 
	 * @param value is of type String
	 */
	public void setLegalRegNumber(String value) {
		_legalRegNumber = value;
	}

	/*
	 * Set the customer type indicator
	 * 
	 * @param value is of type String
	 * 
	 * public void setCustomerType(String value) { _customerType = value; }
	 */
	/**
	 * Set the date when the customer started the relationship with the bank.
	 * 
	 * @param value is of type Date
	 */
	public void setRelationshipStartDate(Date value) {
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
}