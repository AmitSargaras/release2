/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/CCTaskSearchResult.java,v 1.2 2005/10/17 11:15:19 hshii Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.util.AccessorUtil;

//app

/**
 * This interface defines the list of attributes that is required for CC Task
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/10/17 11:15:19 $ Tag: $Name: $
 */
public class CCTaskSearchResult implements Serializable {
	private long taskID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory = null;

	private long customerID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String domicileCountry = null;

	private String orgCode = null;

	private String trxID = null;

	private String trxStatus = null;

	private String trxOriginCountry = null;

	/**
	 * Get the task ID
	 * @return long - the task ID
	 */
	public long getTaskID() {
		return this.taskID;
	}

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

	/**
	 * Get the domicile country
	 * @return String - the domicile country
	 */
	public String getDomicileCountry() {
		return this.domicileCountry;
	}

	public String getOrgCode() {
		return this.orgCode;
	}

	/**
	 * Get the trx ID
	 * @return String - the trx ID
	 */
	public String getTrxID() {
		return this.trxID;
	}

	/**
	 * Get the trx status
	 * @return String - the trx status
	 */
	public String getTrxStatus() {
		return this.trxStatus;
	}

	/**
	 * Get the trx origin country
	 * @return String - the trx origin country
	 */
	public String getTrxOriginCountry() {
		return this.trxOriginCountry;
	}

	/**
	 * Set the task ID
	 * @param aTaskID of long type
	 */
	public void setTaskID(long aTaskID) {
		this.taskID = aTaskID;
	}

	public void setCustomerCategory(String aCustomerCategory) {
		this.customerCategory = aCustomerCategory;
	}

	/**
	 * Set the collateral ID
	 * @param aCollateralID of long type
	 */
	public void setCustomerID(long aCustomerID) {
		this.customerID = aCustomerID;
	}

	public void setDomicileCountry(String aDomicileCountry) {
		this.domicileCountry = aDomicileCountry;
	}

	/**
	 * Set the collateral location
	 * @param aCollateralLocation of String type
	 */
	public void setOrgCode(String anOrgCode) {
		this.orgCode = anOrgCode;
	}

	/**
	 * Set the trx ID
	 * @param aTrxID of String type
	 */
	public void setTrxID(String aTrxID) {
		this.trxID = aTrxID;
	}

	/**
	 * Set the trx status
	 * @param aTrxStatus of String type
	 */
	public void setTrxStatus(String aTrxStatus) {
		this.trxStatus = aTrxStatus;
	}

	/**
	 * Set the trx origin country
	 * @param aTrxOriginCountry of String type
	 */
	public void setTrxOriginCountry(String aTrxOriginCountry) {
		this.trxOriginCountry = aTrxOriginCountry;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
