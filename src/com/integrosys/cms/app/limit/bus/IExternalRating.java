/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

/**
 * This interface represents a external rating.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public interface IExternalRating extends java.io.Serializable {

	/**
	 * Get Rating Type Code
	 * 
	 * @return String
	 */
	public String getRatingType();

	/**
	 * Set Rating Type Code
	 * 
	 * @param value is of type String
	 */
	public void setRatingType(String value);

	/**
	 * Get Rating Code
	 * 
	 * @return String
	 */
	public String getRating();

	/**
	 * Set Rating Code
	 * 
	 * @param value is of type String
	 */
	public void setRating(String value);

}