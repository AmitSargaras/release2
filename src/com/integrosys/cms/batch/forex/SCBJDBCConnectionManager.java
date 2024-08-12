/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/forex/SCBJDBCConnectionManager.java,v 1.1 2003/06/17 08:51:13 jtan Exp $
 */
package com.integrosys.cms.batch.forex;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.RMConnectionManager;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * Purpose: This provides the connection facility and resources to connect to
 * the database. Acts as a retrieval for database connection information.
 * Description:
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public class SCBJDBCConnectionManager extends RMConnectionManager {
	// constants map to key values in the ofa.properties
	public static final String USER_ID = "dbconfig.batch.userId";

	public static final String PASSWORD = "dbconfig.batch.password";

	public SCBJDBCConnectionManager(Object o) throws DBConnectionException, java.sql.SQLException {
		super(o);

	}

	/**
	 * @return the userid for connecting to database.
	 */
	protected String getUserId() {
		return PropertyManager.getValue(USER_ID);
	}

	/**
	 * 
	 * @param s - key for password setting in property file
	 * @return the password for connecting to database.
	 */
	protected String getPassword(String s) {
		return PropertyManager.getValue(PASSWORD);
	}
}
