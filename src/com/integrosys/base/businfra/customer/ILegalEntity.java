/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/base/businfra/customer/ILegalEntity.java,v 1.4 2003/07/03 07:47:03 kllee Exp $
 */
package com.integrosys.base.businfra.customer;

import java.util.Date;

/**
 * This interface represents a Legal Entity in the customer package. A Legal
 * Entity refers to portion of a customer's attributes that are constant across
 * different subdiaries. However this interface itself is an "incomplete"
 * picture of a customer's attributes, and therefore should be extended to
 * complete the definition of a customer.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.4 $
 * @since $Date: 2003/07/03 07:47:03 $ Tag: $Name: $
 */
public interface ILegalEntity extends java.io.Serializable {
	// Getters

	/**
	 * Get the Legal Entity ID, the primary key of this object.
	 * 
	 * @return long
	 */
	public long getLEID();

	/**
	 * Get the legal name.
	 * 
	 * @return String
	 */
	public String getLegalName();

	/**
	 * Get the short name of this entity.
	 * 
	 * @return String
	 */
	public String getShortName();

	/**
	 * Get the legal constitution, such as if the Legal entity is an MNC, or a
	 * Sole-Proprietor, etc.
	 * 
	 * 
	 * @return String
	 */
	public String getLegalConstitution();

	/**
	 * Get the legal registration country. For consumer customers, this field
	 * can represent the birth country.
	 * 
	 * @return String
	 */
	public String getLegalRegCountry();

	/**
	 * Get the legal registration number. For consumer customers, this field can
	 * represent the NRIC number or order legal identifier.
	 * 
	 * @return String
	 */
	public String getLegalRegNumber();

	/*
	 * Get the customer type indicator
	 * 
	 * @return String
	 * 
	 * public String getCustomerType();
	 */
	/**
	 * Get the date when the customer started the relationship with the bank.
	 * 
	 * @return Date
	 */
	public Date getRelationshipStartDate();
	
	/**
	 * Get the date of incorporation
	 * 
	 * @return Date
	 */
	public Date getDateOfIncorporation();

	// Setters

	/**
	 * Set the Legal Entity ID, the primary key of this object.
	 * 
	 * @param value is of type long
	 */
	public void setLEID(long value);

	/**
	 * Set the legal name.
	 * 
	 * @param value is of type String
	 */
	public void setLegalName(String value);

	/**
	 * Set the short name of this entity.
	 * 
	 * @param value is of type String
	 */
	public void setShortName(String value);

	/**
	 * Set the legal constitution, such as if the Legal entity is an MNC, or a
	 * Sole-Proprietor, etc.
	 * 
	 * 
	 * @param value is of type String
	 */
	public void setLegalConstitution(String value);

	/**
	 * Set the legal registration country. For consumer customers, this field
	 * can represent the birth country.
	 * 
	 * @param value is of type String
	 */
	public void setLegalRegCountry(String value);

	/**
	 * Set the legal registration number. For consumer customers, this field can
	 * represent the NRIC number or order legal identifier.
	 * 
	 * @param value is of type String
	 */
	public void setLegalRegNumber(String value);

	/*
	 * Set the customer type indicator
	 * 
	 * @param value is of type String
	 * 
	 * public void setCustomerType(String value);
	 */
	/**
	 * Set the date when the customer started the relationship with the bank.
	 * 
	 * @param value is of type Date
	 */
	public void setRelationshipStartDate(Date value);
	
	/**
	 * Set the  date of incorporation
	 * 
	 * @param value is of type Date
	 */
	public void setDateOfIncorporation(Date value);
}