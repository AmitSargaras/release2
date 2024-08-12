/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBContact.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.customer.bus;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a contact information which includes address, email and
 * phone numbers.
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class OBContact implements IContact {
	private long _contactID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _contactRef = null;

	private String _contactType = null;

	private String _attentionParty = null;

	private String _address1 = null;

	private String _address2 = null;
	
	private String addressLine3 = null;
	
	private String addressLine4 = null;
	
	private String addressLine5 = null;

	private String _city = null;
	
	private String _region = null;

	private String _state = null;

	private String _postalCode = null;

	private String _countryCode = null;

	private String _fax = null;

	private String _email = null;

	private String _telephone = null;

	private String _telex = null;
	
	
	private String stdCodeTelNo = null;

	private String stdCodeTelex = null;
	
	
	
	private long LEID;

	/**
	 * Default Constructor
	 */
	public OBContact() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type IContact
	 */
	public OBContact(IContact value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getContactID() {
		return _contactID;
	}

	/**
	 * Get the contact reference
	 * 
	 * @return String
	 */
	public String getContactReference() {
		return _contactRef;
	}

	/**
	 * Get contact type
	 * 
	 * @return String
	 */
	public String getContactType() {
		return _contactType;
	}

	/**
	 * Get attention party
	 * 
	 * @return String
	 */
	public String getAttentionParty() {
		return _attentionParty;
	}

	/**
	 * Get address line 1
	 * 
	 * @return String
	 */
	public String getAddressLine1() {
		return _address1;
	}

	/**
	 * Get address line 2
	 * 
	 * @return String
	 */
	public String getAddressLine2() {
		return _address2;
	}

	/**
	 * Get City
	 * 
	 * @return String
	 */
	public String getCity() {
		return _city;
	}
	public String getRegion() {
		return _region;
	}
	/**
	 * Get State
	 * 
	 * @return String
	 */
	public String getState() {
		return _state;
	}

	/**
	 * Get postal code
	 * 
	 * @return String
	 */
	public String getPostalCode() {
		return _postalCode;
	}

	/**
	 * Get Country Code
	 * 
	 * @return String
	 */
	public String getCountryCode() {
		return _countryCode;
	}

	/**
	 * Get Fax Number
	 * 
	 * @return String
	 */
	public String getFaxNumber() {
		return _fax;
	}

	/**
	 * Get Email
	 * 
	 * @return String
	 */
	public String getEmailAddress() {
		return _email;
	}

	/**
	 * Get Telephone number
	 * 
	 * @return String
	 */
	public String getTelephoneNumer() {
		return _telephone;
	}

	/**
	 * Get Telex
	 * 
	 * @return String
	 */
	public String getTelex() {
		return _telex;
	}

	// Setters

	/**
	 * Set the contact ID
	 * 
	 * @param value is of type long
	 */
	public void setContactID(long value) {
		_contactID = value;
	}

	/**
	 * Set the contact reference
	 * 
	 * @param value is of type String
	 */
	public void setContactReference(String value) {
		_contactRef = value;
	}

	/**
	 * Set contact type
	 * 
	 * @param value is of type String
	 */
	public void setContactType(String value) {
		_contactType = value;
	}

	/**
	 * Set attention party
	 * 
	 * @param value is of type String
	 */
	public void setAttentionParty(String value) {
		_attentionParty = value;
	}

	/**
	 * Set address line 1
	 * 
	 * @param value is of type String
	 */
	public void setAddressLine1(String value) {
		_address1 = value;
	}

	/**
	 * Set address line 2
	 * 
	 * @param value is of type String
	 */
	public void setAddressLine2(String value) {
		_address2 = value;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getAddressLine4() {
		return addressLine4;
	}

	public void setAddressLine4(String addressLine4) {
		this.addressLine4 = addressLine4;
	}

	public String getAddressLine5() {
		return addressLine5;
	}

	public void setAddressLine5(String addressLine5) {
		this.addressLine5 = addressLine5;
	}

	/**
	 * Set City
	 * 
	 * @param value is of type String
	 */
	public void setCity(String value) {
		_city = value;
	}
	public void setRegion(String value) {
		_region = value;
	}
	/**
	 * Set State
	 * 
	 * @param value is of type String
	 */
	public void setState(String value) {
		_state = value;
	}

	/**
	 * Set postal code
	 * 
	 * @param value is of type String
	 */
	public void setPostalCode(String value) {
		_postalCode = value;
	}

	/**
	 * Set Country Code
	 * 
	 * @param value is of type String
	 */
	public void setCountryCode(String value) {
		_countryCode = value;
	}

	/**
	 * Set Fax Number
	 * 
	 * @param value is of type String
	 */
	public void setFaxNumber(String value) {
		_fax = value;
	}

	/**
	 * Set Email
	 * 
	 * @param value is of type String
	 */
	public void setEmailAddress(String value) {
		_email = value;
	}

	/**
	 * Set Telephone number
	 * 
	 * @param value is of type String
	 */
	public void setTelephoneNumer(String value) {
		_telephone = value;
	}

	/**
	 * Set Telex
	 * 
	 * @param value is of type String
	 */
	public void setTelex(String value) {
		_telex = value;
	}


	public long getLEID() {
		return LEID;
	}

	
	public String getStdCodeTelNo() {
		return stdCodeTelNo;
	}

	public void setStdCodeTelNo(String stdCodeTelNo) {
		this.stdCodeTelNo = stdCodeTelNo;
	}

	public String getStdCodeTelex() {
		return stdCodeTelex;
	}

	public void setStdCodeTelex(String stdCodeTelex) {
		this.stdCodeTelex = stdCodeTelex;
	}

	public void setLEID(long LEID) {
		this.LEID = LEID;
		
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