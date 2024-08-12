/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICreditStatus.java,v 1.3 2003/06/18 11:39:38 kllee Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

/**
 * This interface represents a customer credit status.
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/06/18 11:39:38 $ Tag: $Name: $
 */
public interface ICreditStatus extends java.io.Serializable {
	// Getters

	/**
	 * Get credit status ID
	 * 
	 * @return long
	 */
	public long getCSID();

	/**
	 * Get the Credit Status reference
	 * 
	 * @return String
	 */
	public String getCSReference();

	/**
	 * Get credit status type
	 * 
	 * @return String
	 */
	public String getCSType();

	/**
	 * Get credit status value
	 * 
	 * @return String
	 */
	public String getCSValue();

	/**
	 * Get effective date
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate();

	/**
	 * Get comments
	 * 
	 * @return String
	 */
	public String getComments();

	// Setters

	/**
	 * Set credit status ID
	 * 
	 * @param value is of type long
	 */
	public void setCSID(long value);

	/**
	 * Set the Credit Status reference
	 * 
	 * @param value is of type String
	 */
	public void setCSReference(String value);

	/**
	 * Set credit status type
	 * 
	 * @param value is of type String
	 */
	public void setCSType(String value);

	/**
	 * Set credit status value
	 * 
	 * @param value is of type String
	 */
	public void setCSValue(String value);

	/**
	 * Set effective date
	 * 
	 * @param value is of type Date
	 */
	public void setEffectiveDate(Date value);

	/**
	 * Set comments
	 * 
	 * @param value is of type String
	 */
	public void setComments(String value);
}