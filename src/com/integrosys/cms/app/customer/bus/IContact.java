/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IContact.java,v 1.2 2003/06/17 05:08:10 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

/**
 * This interface represents a contact information which includes address, email
 * and phone numbers.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 05:08:10 $ Tag: $Name: $
 */
public interface IContact extends java.io.Serializable {
	// Getters

	/**
	 * Get the contact ID
	 * 
	 * @return long
	 */
	public long getContactID();

	/**
	 * Get the contact reference
	 * 
	 * @return String
	 */
	public String getContactReference();

	/**
	 * Get contact type
	 * 
	 * @return String
	 */
	public String getContactType();

	/**
	 * Get attention party
	 * 
	 * @return String
	 */
	public String getAttentionParty();

	/**
	 * Get address line 1
	 * 
	 * @return String
	 */
	public String getAddressLine1();

	/**
	 * Get address line 2
	 * 
	 * @return String
	 */
	public String getAddressLine2();

	/**
	 * Get City
	 * 
	 * @return String
	 */
	public String getCity();

	/**
	 * Get State
	 * 
	 * @return String
	 */
	public String getState();
	
	public String getRegion();

	/**
	 * Get postal code
	 * 
	 * @return String
	 */
	public String getPostalCode();

	/**
	 * Get Country Code
	 * 
	 * @return String
	 */
	public String getCountryCode();

	/**
	 * Get Fax Number
	 * 
	 * @return String
	 */
	public String getFaxNumber();

	/**
	 * Get Email
	 * 
	 * @return String
	 */
	public String getEmailAddress();

	/**
	 * Get Telephone number
	 * 
	 * @return String
	 */
	public String getTelephoneNumer();

	/**
	 * Get Telex
	 * 
	 * @return String
	 */
	public String getTelex();

	// Setters

	/**
	 * Set the contact ID
	 * 
	 * @param value is of type long
	 */
	public void setContactID(long value);

	/**
	 * Set the contact reference
	 * 
	 * @param value is of type String
	 */
	public void setContactReference(String value);

	/**
	 * Set contact type
	 * 
	 * @param value is of type String
	 */
	public void setContactType(String value);

	/**
	 * Set attention party
	 * 
	 * @param value is of type String
	 */
	public void setAttentionParty(String value);

	/**
	 * Set address line 1
	 * 
	 * @param value is of type String
	 */
	public void setAddressLine1(String value);

	/**
	 * Set address line 2
	 * 
	 * @param value is of type String
	 */
	public void setAddressLine2(String value);

	/**
	 * Set City
	 * 
	 * @param value is of type String
	 */
	public void setCity(String value);

	/**
	 * Set State
	 * 
	 * @param value is of type String
	 */
	public void setState(String value);
	
	public void setRegion(String value);

	/**
	 * Set postal code
	 * 
	 * @param value is of type String
	 */
	public void setPostalCode(String value);

	/**
	 * Set Country Code
	 * 
	 * @param value is of type String
	 */
	public void setCountryCode(String value);

	/**
	 * Set Fax Number
	 * 
	 * @param value is of type String
	 */
	public void setFaxNumber(String value);

	/**
	 * Set Email
	 * 
	 * @param value is of type String
	 */
	public void setEmailAddress(String value);

	/**
	 * Set Telephone number
	 * 
	 * @param value is of type String
	 */
	public void setTelephoneNumer(String value);

	/**
	 * Set Telex
	 * 
	 * @param value is of type String
	 */
	public void setTelex(String value);
	
	
	public String getAddressLine3();

	public void setAddressLine3(String addressLine3);

	public String getAddressLine4();

	public void setAddressLine4(String addressLine4);

	public String getStdCodeTelNo();

	public void setStdCodeTelNo(String stdCodeTelNo);

	public String getStdCodeTelex() ;

	public void setStdCodeTelex(String stdCodeTelex);
	
	public String getAddressLine5();

	public void setAddressLine5(String addressLine5);
	
	  public long getLEID();
		
		public void setLEID(long value);
}