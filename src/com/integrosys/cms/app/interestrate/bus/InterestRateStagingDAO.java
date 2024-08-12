/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.interestrate.bus;

/**
 * DAO for stage interestrate.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class InterestRateStagingDAO extends InterestRateDAO {
	// Table names
	public static final String STAGE_INTEREST_RATE_TABLE = "CMS_STAGE_INTEREST_RATE";

	/**
	 * Default Constructor
	 */
	public InterestRateStagingDAO() {
	}

	/**
	 * Get the table for stage interest rate.
	 * 
	 * @return String
	 */
	protected String getTable() {
		return STAGE_INTEREST_RATE_TABLE;
	}

}
