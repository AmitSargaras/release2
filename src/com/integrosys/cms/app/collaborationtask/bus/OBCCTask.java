/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/OBCCTask.java,v 1.1 2003/08/31 13:56:24 hltan Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This interface defines the list of attributes that is required for CC Task
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/31 13:56:24 $ Tag: $Name: $
 */
public class OBCCTask implements ICCTask {
	private long taskID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory = null;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String legalRef = null;

	private String legalName = null;

	private String customerType = null;

	private String domicileCountry = null;

	private String orgCode = null;

	private String remarks = null;

	private boolean isDeletedInd = false;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	/**
	 * Get the task ID
	 * @return long - the task ID
	 */
	public long getTaskID() {
		return this.taskID;
	}

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	/**
	 * Get the customer category
	 * @return String - the customer category
	 */
	public String getCustomerCategory() {
		return this.customerCategory;
	}

	/**
	 * Get the customer ID
	 * @return long - the customer ID
	 */
	public long getCustomerID() {
		return this.customerID;
	}

	/*
	 * Get the legal Ref
	 * 
	 * @return String - the legal Ref
	 */
	public String getLegalRef() {
		return this.legalRef;
	}

	/**
	 * Get the legal Name
	 * @return String - the legal name
	 */
	public String getLegalName() {
		return this.legalName;
	}

	/**
	 * Get the domicile country
	 * @return String - the domicile country
	 */
	public String getDomicileCountry() {
		return this.domicileCountry;
	}

	/**
	 * Get the customer type
	 * @return String - the customer type
	 */
	public String getCustomerType() {
		return this.customerType;
	}

	/**
	 * Get the organisation code
	 */
	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * Get the deleted indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Set the task ID
	 * @param aTaskID of long type
	 */
	public void setTaskID(long aTaskID) {
		this.taskID = aTaskID;
	}

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	/**
	 * Set the customer category
	 * @param aCustomerCategory of String type
	 */
	public void setCustomerCategory(String aCustomerCategory) {
		this.customerCategory = aCustomerCategory;
	}

	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	/**
	 * Set the legal Ref
	 * @param aLegalRef of String type
	 */
	public void setLegalRef(String aLegalRef) {
		this.legalRef = aLegalRef;
	}

	/**
	 * Set the legal name
	 * @param aLegalName of String type
	 */
	public void setLegalName(String aLegalName) {
		this.legalName = aLegalName;
	}

	/**
	 * Set the domicile country
	 * @param aDomicileCountry of String type
	 */
	public void setDomicileCountry(String aDomicileCountry) {
		this.domicileCountry = aDomicileCountry;
	}

	/**
	 * Set the customer type
	 * @param aCustomerType of String type
	 */
	public void setCustomerType(String aCustomerType) {
		this.customerType = aCustomerType;
	}

	/**
	 * Set the organisation code
	 * @param anOrgCode of String type
	 */
	public void setOrgCode(String anOrgCode) {
		this.orgCode = anOrgCode;
	}

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	/**
	 * Set the deleted indicator
	 * @param anIsDeletedInd - true if it is deleted and false otherwisef
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
