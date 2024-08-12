/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/OBTATEntry.java,v 1.3 2004/07/15 09:29:12 hltan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This classrepresents a TAT (turn-around-time) entry
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/15 09:29:12 $ Tag: $Name: $
 */
public class OBTATEntry implements ITATEntry {
	private long tATEntryID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date tATStamp = null;

	private String tATServiceCode = null;

	private Date referenceDate = null;

	private String remarks = null;

	/**
	 * Default Constructor
	 */
	public OBTATEntry() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ITATEntry
	 */
	public OBTATEntry(ITATEntry value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters
	/**
	 * Get the TAT Entry ID
	 * 
	 * @return long
	 */
	public long getTATEntryID() {
		return tATEntryID;
	}

	/**
	 * Get the TAT Time Stamp. This is the date that an entry is created.
	 * 
	 * @return Date
	 */
	public Date getTATStamp() {
		return tATStamp;
	}

	/**
	 * Get the TAT Service Code. This code represents a particular service that
	 * is to be monitored which would have a corresponding threshold value for
	 * TAT expiry.
	 * 
	 * @return String
	 */
	public String getTATServiceCode() {
		return tATServiceCode;
	}

	/**
	 * Get the TAT Date to be measured from
	 * 
	 * @return Date
	 */
	public Date getReferenceDate() {
		return referenceDate;
	}

	/**
	 * Get the TAT remark
	 * 
	 * @return String
	 */
	public String getRemarks() {
		return remarks;
	}

	// Setters
	/**
	 * Set the TAT Entry ID
	 * 
	 * @param value is of type long
	 */
	public void setTATEntryID(long value) {
		tATEntryID = value;
	}

	/**
	 * Set the TAT Time Stamp. This is the date that an entry is created.
	 * 
	 * @param value is of type Date
	 */
	public void setTATStamp(Date value) {
		tATStamp = value;
	}

	/**
	 * Set the TAT Service Code. This code represents a particular service that
	 * is to be monitored which would have a corresponding threshold value for
	 * TAT expiry.
	 * 
	 * @param value is of type String
	 */
	public void setTATServiceCode(String value) {
		tATServiceCode = value;
	}

	/**
	 * Set the TAT Date to be measured from
	 * 
	 * @param value is of type Date
	 */
	public void setReferenceDate(Date value) {
		referenceDate = value;
	}

	/**
	 * Set the remarks for the TAT
	 * 
	 * @param remarks is of type String
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * Return a String representation of the object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}