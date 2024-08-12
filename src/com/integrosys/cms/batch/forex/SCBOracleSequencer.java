/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/forex/SCBOracleSequencer.java,v 1.3 2006/05/18 08:08:23 jitendra Exp $
 */
package com.integrosys.cms.batch.forex;

import java.sql.SQLException;

import com.integrosys.base.techinfra.dbsupport.AbstractDBUtil;
import com.integrosys.base.techinfra.dbsupport.AbstractOracleSequencer;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;

/**
 * Purpose: This is an object that generates unique ids for inserting records
 * into the Forex table. Description:
 * 
 * @author $jtan$<br>
 * @version $revision$
 * @since $date$ Tag: $Name: $
 * 
 */
public class SCBOracleSequencer extends AbstractOracleSequencer {

	protected AbstractDBUtil getDBUtil() throws DBConnectionException, SQLException {
		return new DBUtil();
	}
}
