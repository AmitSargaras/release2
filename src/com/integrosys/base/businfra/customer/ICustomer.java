/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/base/businfra/customer/ICustomer.java,v 1.2 2003/06/17 02:25:09 kllee Exp $
 */
package com.integrosys.base.businfra.customer;

import java.util.Date;

/**
 * This interface represents a Customer entity. Customer contains a reference to
 * the Legal Entity.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/06/17 02:25:09 $ Tag: $Name: $
 */
public interface ICustomer extends java.io.Serializable {
	// Getters

	/**
	 * Get the customer ID which is the primary key of this entity
	 * 
	 * @return long
	 */
	public long getCustomerID();

	/**
	 * Get the Legal Entity.
	 * 
	 * @return ILegalEntity
	 */
	public ILegalEntity getLegalEntity();

	/**
	 * Get the customer name.
	 * 
	 * @return String
	 */
	public String getCustomerName();

	/**
	 * Get the customer relationship start date
	 * 
	 * @return Date
	 */
	public Date getCustomerRelationshipStartDate();
	
	/**
	 * Get the date of incorporation
	 * 
	 * @return Date
	 */
	public Date getDateOfIncorporation();
	
	public Date getRaraocPeriod();

	// Setters
	/**
	 * Set the customer ID which is the primary key of this entity
	 * 
	 * @param value is of type long
	 */
	public void setCustomerID(long value);

	/**
	 * Set the Legal Entity.
	 * 
	 * @param value is of type ILegalEntity
	 */
	public void setLegalEntity(ILegalEntity value);

	/**
	 * Set the customer name.
	 * 
	 * @param value is of type String
	 */
	public void setCustomerName(String value);

	/**
	 * Set the customer relationship start date
	 * 
	 * @param value is of type Date
	 */
	public void setCustomerRelationshipStartDate(Date value);
	
	/**
	 * Set the  date of incorporation
	 * 
	 * @param value is of type Date
	 */
	public void setDateOfIncorporation(Date value);
	
	public void setRaraocPeriod(Date value);
}