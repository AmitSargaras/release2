/* Copyright Integro Technologies Pte Ltd
 * com.integrosys.cms.app.transaction.OBCMSTrxHistoryLog.java Created on Jun 29, 2004 9:18:36 AM
 *
 */

package com.integrosys.cms.app.transaction;

import java.util.Date;

/**
 * @since Jun 29, 2004
 * @author heju
 * @version 1.0.0
 */
public class OBCMSTrxHistoryLog extends OBCMSTrxHistoryValue {
	private String logDate; // String format of TRANSACTION_DATE

	private String logUserName; // User Name/Group Name of the user

	private String logGroupName; //

	/**
           * 
           */
	public OBCMSTrxHistoryLog() {
		super();
	}

	/**
	 * @param trxID
	 * @param trxDate
	 */
	public OBCMSTrxHistoryLog(String trxID, Date trxDate) {
		super(trxID, trxDate);
	}

	/**
	 * @return Returns the logDate.
	 */
	public String getLogDate() {
		return logDate;
	}

	/**
	 * @param logDate The logDate to set.
	 */
	public void setLogDate(String logDate) {
		this.logDate = logDate;
	}

	/**
	 * @return Returns the logUserName.
	 */
	public String getLogUserName() {
		return logUserName;
	}

	/**
	 * @param logUserName The logUserName to set.
	 */
	public void setLogUserName(String logUserName) {
		this.logUserName = logUserName;
	}

	/**
	 * @return Returns the logGroupName.
	 */
	public String getLogGroupName() {
		return logGroupName;
	}

	/**
	 * @param logGroupName The logGroupName to set.
	 */
	public void setLogGroupName(String logGroupName) {
		this.logGroupName = logGroupName;
	}
}
