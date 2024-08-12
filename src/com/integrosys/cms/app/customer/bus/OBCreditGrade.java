/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/customer/bus/OBCreditGrade.java,v 1.4 2005/11/30 09:30:47 hshii Exp $
 */
package com.integrosys.cms.app.customer.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class represents a customer credit grade.
 * 
 * @author $Author: hshii $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/11/30 09:30:47 $ Tag: $Name: $
 */
public class OBCreditGrade implements ICreditGrade {
	private long _cgID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String _cgRef = null;

	private String _cgType = null;

	private String _cgCode = null;

	private Date _effectiveDate = null;

	private String _reasonForChange = null;

	private String _bookingLoc = null;

	private String _employeeCode = null;

	private String _employeeType = null;

	private String _updateStatusInd = null;

	private Date _lastUpdateDate = null;

	/**
	 * Default Constructor
	 */
	public OBCreditGrade() {
	}

	/**
	 * Construct OB from interface
	 * 
	 * @param value is of type ICreditGrade
	 */
	public OBCreditGrade(ICreditGrade value) {
		this();
		AccessorUtil.copyValue(value, this);
	}

	// Getters

	/**
	 * Get Credit Grade ID
	 * 
	 * @return long
	 */
	public long getCGID() {
		return _cgID;
	}

	/**
	 * Get Credit Grade Reference
	 * 
	 * @return String
	 */
	public String getCGReference() {
		return _cgRef;
	}

	/**
	 * Get Credit Grade Type
	 * 
	 * @return String
	 */
	public String getCGType() {
		return _cgType;
	}

	/**
	 * Get Credit Grade Code
	 * 
	 * @return String
	 */
	public String getCGCode() {
		return _cgCode;
	}

	/**
	 * Get Credit Grade Effective Date
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate() {
		return _effectiveDate;
	}

	/**
	 * Get reason for change
	 * 
	 * @return String
	 */
	public String getReasonForChange() {
		return _reasonForChange;
	}

	/**
	 * Get Approver Booking Location
	 * 
	 * @return String
	 */
	public String getApproverBookingLoc() {
		return _bookingLoc;
	}

	/**
	 * Get Approver Employee Code
	 * 
	 * @return String
	 */
	public String getApproverEmployeeCode() {
		return _employeeCode;
	}

	/**
	 * Get Approver Employee Type
	 * 
	 * @return String
	 */
	public String getApproverEmployeeType() {
		return _employeeType;
	}

	/**
	 * Get Update Status Indicator
	 * 
	 * @return String
	 */
	public String getUpdateStatusInd() {
		return _updateStatusInd;
	}

	/**
	 * Get Last Update Date
	 * 
	 * @return Date
	 */
	public Date getLastUpdateDate() {
		return _lastUpdateDate;
	}

	// Setters
	/**
	 * Set Credit Grade ID
	 * 
	 * @param value is of type long
	 */
	public void setCGID(long value) {
		_cgID = value;
	}

	/**
	 * Set Credit Grade Reference
	 * 
	 * @param value is of type String
	 */
	public void setCGReference(String value) {
		_cgRef = value;
	}

	/**
	 * Set Credit Grade Type
	 * 
	 * @param value is of type String
	 */
	public void setCGType(String value) {
		_cgType = value;
	}

	/**
	 * Set Credit Grade Code
	 * 
	 * @param value is of type String
	 */
	public void setCGCode(String value) {
		_cgCode = value;
	}

	/**
	 * Set Credit Grade Effective Date
	 * 
	 * @param value is of type Date
	 */
	public void setEffectiveDate(Date value) {
		_effectiveDate = value;
	}

	/**
	 * Set reason for change
	 * 
	 * @param value is of type String
	 */
	public void setReasonForChange(String value) {
		_reasonForChange = value;
	}

	/**
	 * Set Approver Booking Location
	 * 
	 * @param value is of type String
	 */
	public void setApproverBookingLoc(String value) {
		_bookingLoc = value;
	}

	/**
	 * Set Approver Employee Code
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeCode(String value) {
		_employeeCode = value;
	}

	/**
	 * Set Approver Employee Type
	 * 
	 * @param value is of type String
	 */
	public void setApproverEmployeeType(String value) {
		_employeeType = value;
	}

	/**
	 * Set Update status indicator
	 * 
	 * @param value is of type String
	 */
	public void setUpdateStatusInd(String value) {
		_updateStatusInd = value;
	}

	/**
	 * Set Last Update Date
	 * 
	 * @param value is type Date
	 */
	public void setLastUpdateDate(Date value) {
		_lastUpdateDate = value;
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