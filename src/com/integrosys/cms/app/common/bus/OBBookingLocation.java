/*
 * Copyright Integro Technologies Pte Ltd
 * 
 */
package com.integrosys.cms.app.common.bus;

/**
 * A component represents a Booking Location, having country and organisation
 * (branch).
 * 
 * @author lyng
 * @author Chong Jun Yong
 * @since 2003/09/24
 */
public class OBBookingLocation implements IBookingLocation {

	private static final long serialVersionUID = -5452435596216366295L;

	private String country = null;

	private String organisation = null;

	private String bookingLocationDesc;

	/**
	 * Default Constructor without any arguments, caller to provide country,
	 * organisation using the setter contracts.
	 */
	public OBBookingLocation() {
	}

	/**
	 * Constructor to supply another instance of booking location, will copy the
	 * state to this instance.
	 * 
	 * @param value another instance of booking location
	 */
	public OBBookingLocation(IBookingLocation value) {
		this(value.getCountryCode(), value.getOrganisationCode(), value.getBookingLocationDesc());
	}

	/**
	 * Constructor to provide country and organisation
	 * 
	 * @param country country
	 * @param organisation organisation (or branch)
	 */
	public OBBookingLocation(String country, String organisation) {
		this(country, organisation, null);
	}

	/**
	 * Constructor to provide country, organisation and organisation description
	 * 
	 * @param country country
	 * @param organisation organisation (or branch)
	 * @param bookingLocationDesc organisation description (the full branch
	 *        name)
	 */
	public OBBookingLocation(String country, String organisation, String bookingLocationDesc) {
		this.country = country;
		this.organisation = organisation;
		this.bookingLocationDesc = bookingLocationDesc;
	}

	/**
	 * Get booking location description.
	 * 
	 * @return String
	 */
	public String getBookingLocationDesc() {
		return bookingLocationDesc;
	}

	/**
	 * Get Country Code
	 * 
	 * @return String
	 */
	public String getCountryCode() {
		return country;
	}

	/**
	 * Get Organisation Code
	 * 
	 * @return String
	 */
	public String getOrganisationCode() {
		return organisation;
	}

	/**
	 * Set booking location description.
	 * 
	 * @param bookingLocationDesc of type String
	 */
	public void setBookingLocationDesc(String bookingLocationDesc) {
		this.bookingLocationDesc = bookingLocationDesc;
	}

	/**
	 * Set the country code
	 * 
	 * @param value is of type String
	 */
	public void setCountryCode(String value) {
		this.country = value;
	}

	/**
	 * Set the organisation code
	 * 
	 * @param value is of type String
	 */
	public void setOrganisationCode(String value) {
		this.organisation = value;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBBookingLocation [");
		buf.append("country=");
		buf.append(country);
		buf.append(", organisation=");
		buf.append(organisation);
		if (bookingLocationDesc != null) {
			buf.append(", organisation description=");
			buf.append(bookingLocationDesc);
		}
		buf.append("]");
		return buf.toString();
	}
}