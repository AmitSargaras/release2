/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.liquidation.bus;

/**
 * DAO for stage liquidation.
 * 
 * @author $Author: Lini $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class LiquidationStagingDAO extends LiquidationDAO {
	// Table names
	public static final String STAGE_EXPENSES_TABLE = "CMS_STAGE_EXPENSES";

	/**
	 * Default Constructor
	 */
	public LiquidationStagingDAO() {
	}

	/**
	 * Get the table for stage liquidation.
	 * 
	 * @return String
	 */
	protected String getTable() {
		return STAGE_EXPENSES_TABLE;
	}

}
