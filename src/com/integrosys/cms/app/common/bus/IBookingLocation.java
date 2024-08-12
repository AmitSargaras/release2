/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/common/bus/IBookingLocation.java,v 1.2 2003/09/24 06:29:07 lyng Exp $
 */
package com.integrosys.cms.app.common.bus;

/**
 * This interface represents a Booking Location.
 * 
 * @author $Author: lyng $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/24 06:29:07 $ Tag: $Name: $
 */
public interface IBookingLocation extends java.io.Serializable {
	// Getters

	/**
	 * Get Country Code
	 * 
	 * @return String
	 */
	public String getCountryCode();

	/**
	 * Get Organisation Code
	 * 
	 * @return String
	 */
	public String getOrganisationCode();

	// Setters
	/**
	 * Set the country code
	 * 
	 * @param value is of type String
	 */
	public void setCountryCode(String value);

	/**
	 * Set the organisation code
	 * 
	 * @param value is of type String
	 */
	public void setOrganisationCode(String value);

	/**
	 * Get booking location description.
	 * 
	 * @return String
	 */
	public String getBookingLocationDesc();

	/**
	 * Set booking location description.
	 * 
	 * @param bookingLocationDesc of type String
	 */
	public void setBookingLocationDesc(String bookingLocationDesc);
}