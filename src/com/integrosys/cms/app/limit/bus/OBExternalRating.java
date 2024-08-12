/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.limit.bus;

import java.io.Serializable;

/**
 * Model to hold external rating.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class OBExternalRating implements IExternalRating, Serializable {

	private String ratingType;

	private String rating;

	public OBExternalRating() {
	}

	public OBExternalRating(String ratingType) {
		this.ratingType = ratingType;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IExternalRating#getRatingType
	 */
	public String getRatingType() {
		return ratingType;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IExternalRating#setRatingType
	 */
	public void setRatingType(String value) {
		this.ratingType = value;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IExternalRating#getRating
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @see com.integrosys.cms.app.limit.bus.IExternalRating#setRating
	 */
	public void setRating(String value) {
		this.rating = value;
	}
}
