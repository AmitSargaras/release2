/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/bus/ITATEntry.java,v 1.2 2004/07/15 09:29:12 hltan Exp $
 */
package com.integrosys.cms.app.limit.bus;

import java.util.Date;

/**
 * This interface represents a TAT (turn-around-time) entry
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/07/15 09:29:12 $ Tag: $Name: $
 */
public interface ITATEntry extends java.io.Serializable {
	// Getters
	/**
	 * Get the TAT Entry ID
	 * 
	 * @return long
	 */
	public long getTATEntryID();

	/**
	 * Get the TAT Time Stamp. This is the date that an entry is created.
	 * 
	 * @return Date
	 */
	public Date getTATStamp();

	/**
	 * Get the TAT Service Code. This code represents a particular service that
	 * is to be monitored which would have a corresponding threshold value for
	 * TAT expiry.
	 * 
	 * @return String
	 */
	public String getTATServiceCode();

	/**
	 * Get the TAT Date to be measured from
	 * 
	 * @return Date
	 */
	public Date getReferenceDate();

	/**
	 * Get the TAT remark
	 * 
	 * @return String
	 */
	public String getRemarks();

	// Setters
	/**
	 * Set the TAT Entry ID
	 * 
	 * @param value is of type long
	 */
	public void setTATEntryID(long value);

	/**
	 * Set the TAT Time Stamp. This is the date that an entry is created.
	 * 
	 * @param value is of type Date
	 */
	public void setTATStamp(Date value);

	/**
	 * Set the TAT Service Code. This code represents a particular service that
	 * is to be monitored which would have a corresponding threshold value for
	 * TAT expiry.
	 * 
	 * @param value is of type String
	 */
	public void setTATServiceCode(String value);

	/**
	 * Set the TAT Date to be measured from
	 * 
	 * @param value is of type Date
	 */
	public void setReferenceDate(Date value);

	/**
	 * Set the remarks for the TAT
	 * 
	 * @param remarks is of type String
	 */
	public void setRemarks(String remarks);
}