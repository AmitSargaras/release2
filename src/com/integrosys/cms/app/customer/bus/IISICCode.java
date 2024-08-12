/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/IISICCode.java,v 1.3 2005/01/07 08:58:37 pooja Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

/**
 * This interface represents an ISIC code of a Legal entity.
 * 
 * @author $Author: pooja $
 * @version $Revision: 1.3 $
 * @since $Date: 2005/01/07 08:58:37 $ Tag: $Name: $
 */
public interface IISICCode extends java.io.Serializable {
	// Getters

	/**
	 * Get the ISIC ID
	 * 
	 * @return long
	 */
	public long getISICID();

	/**
	 * Get the ISIC Reference
	 * 
	 * @return String
	 */
	public String getISICReference();

	/**
	 * Get ISIC type
	 * 
	 * @return String
	 */
	public String getISICType();

	/**
	 * Get ISIC Code
	 * 
	 * @return String
	 */
	public String getISICCode();

	/**
	 * Get ISIC Effective Date
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate();

	/**
	 * Get ISIC Weightage
	 * 
	 * @return String
	 */
	public String getISICWeightage();

	/**
	 * Get the ISIC Status
	 * 
	 * @return String
	 */
	public String getISICStatus();

	// Setters

	/**
	 * Set the ISIC ID
	 * 
	 * @param value is of type long
	 */
	public void setISICID(long value);

	/**
	 * Set the ISIC Reference
	 * 
	 * @param value is of type String
	 */
	public void setISICReference(String value);

	/**
	 * Set ISIC type
	 * 
	 * @param value is of type String
	 */
	public void setISICType(String value);

	/**
	 * Set ISIC Code
	 * 
	 * @param value is of type String
	 */
	public void setISICCode(String value);

	/**
	 * Set ISIC Effective Date
	 * 
	 * @param value is of type Date
	 */
	public void setEffectiveDate(Date value);

	/**
	 * Set ISIC Weightage
	 * 
	 * @param value is of type String
	 */
	public void setISICWeightage(String value);

	/**
	 * Set the ISIC Status
	 * 
	 * @param value is of type String
	 */
	public void setISICStatus(String value);
}