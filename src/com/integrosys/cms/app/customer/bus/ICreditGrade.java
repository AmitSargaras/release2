/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/ICreditGrade.java,v 1.4 2005/11/30 09:30:47 hshii Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

/**
 * This interface represents a customer credit grade.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/11/30 09:30:47 $ Tag: $Name: $
 */
public interface ICreditGrade extends java.io.Serializable {
	// Getters

	/**
	 * Get Credit Grade ID
	 * 
	 * @return long
	 */
	public long getCGID();

	/**
	 * Get Credit Grade Reference
	 * 
	 * @return String
	 */
	public String getCGReference();

	/**
	 * Get Credit Grade Type
	 * 
	 * @return String
	 */
	public String getCGType();

	/**
	 * Get Credit Grade Code
	 * 
	 * @return String
	 */
	public String getCGCode();

	/**
	 * Get Credit Grade Effective Date
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate();

	/**
	 * Get reason for change
	 * 
	 * @return String
	 */
	public String getReasonForChange();

	/**
	 * Get Approver Booking Location
	 * 
	 * @return String
	 */
	public String getApproverBookingLoc();

	/**
	 * Get Approver Employee Code
	 * 
	 * @return String
	 */
	public String getApproverEmployeeCode();

	/**
	 * Get Approver Employee Type
	 * 
	 * @return String
	 */
	public String getApproverEmployeeType();

	/**
	 * Get Update Status Indicator
	 * 
	 * @return String
	 */
	public String getUpdateStatusInd();

	/**
	 * Get Last Update Date
	 * 
	 * @return Date
	 */
	public Date getLastUpdateDate();

	// Setters
	/**
	 * Set Credit Grade ID
	 * 
	 * @param value is of type long
	 */
	public void setCGID(long value);

	/**
	 * Set Credit Grade Reference
	 * 
	 * @param value is of type String
	 */
	public void setCGReference(String value);

	/**
	 * Set Credit Grade Type
	 * 
	 * @param value is of type String
	 */
	public void setCGType(String value);

	/**
	 * Set Credit Grade Code
	 * 
	 * @param value is of type String
	 */
	public void setCGCode(String value);

	/**
	 * Set Credit Grade Effective Date
	 * 
	 * @param value is of type Date
	 */
	public void setEffectiveDate(Date value);

	/**
	 * Set reason for change
	 * 
	 * @param value is of type String
	 */
	public void setReasonForChange(String value);

	/**
	 * Set Approver Booking Location
	 * 
	 * @param value is of type String
	 */
	public void setApproverBookingLoc(String value);

	/**
	 * Set Approver Employee Code
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeCode(String value);

	/**
	 * Set Approver Employee Type
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeType(String value);

	/**
	 * Set Credit Grade status
	 * 
	 * @param value is of type String
	 */
	public void setUpdateStatusInd(String value);

	/**
	 * Set Last Update Date
	 * 
	 * @param value is type Date
	 */
	public void setLastUpdateDate(Date value);
}