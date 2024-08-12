/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/OBCollateralTask.java,v 1.10 2006/08/08 05:46:46 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;

/**
 * This interface defines the list of attributes that is required for Collateral
 * Task
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2006/08/08 05:46:46 $ Tag: $Name: $
 */
public class OBCollateralTask implements ICollateralTask {
	private long taskID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ILimit[] limitList = null;

	private long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String collateralRef = null;

	private ICollateralType collateralType = null;

	private ICollateralSubType collateralSubType = null;

	private String collateralLocation = null;

	private String remarks = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd = false;

	private String securityOrganisation = null;

	private long leSubProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String customerCategory = null;

	private ICoBorrowerLimit[] coBorrowerLimitList = null;

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
	 * Get the limit reference
	 * @return String - the limit reference
	 */
	public ILimit[] getLimitList() {
		return this.limitList;
	}

	/**
	 * Helper method to get the list of limit references
	 * @return String[] - the list of limit references
	 */
	public String[] getLimitRefList() {
		ILimit[] list = getLimitList();
		if ((list != null) && (list.length > 0)) {
			String[] strList = new String[list.length];
			for (int ii = 0; ii < list.length; ii++) {
				strList[ii] = list[ii].getLimitRef();
			}
			return strList;
		}
		return null;
	}

	/**
	 * Get the collateral ID
	 * @return long - the collateral ID
	 */
	public long getCollateralID() {
		return this.collateralID;
	}

	/**
	 * Get the collateral reference
	 * @return String - the collateral reference
	 */
	public String getCollateralRef() {
		return this.collateralRef;
	}

	/**
	 * Get the collateral type
	 * @return ICollateralType - the collateral type
	 */
	public ICollateralType getCollateralType() {
		return this.collateralType;
	}

	/**
	 * Get the collateral sub type
	 * @return ICollateralSubType - the collateral sub type
	 */
	public ICollateralSubType getCollateralSubType() {
		return this.collateralSubType;
	}

	/**
	 * Get the collateral location
	 * @return String - the collateral location
	 */
	public String getCollateralLocation() {
		return this.collateralLocation;
	}

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Get the deleted indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
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
	 * Set the limit list
	 * @param anILimitList of ILimit[] type
	 */
	public void setLimitList(ILimit[] anILimitList) {
		this.limitList = anILimitList;
	}

	/**
	 * Set the collateral ID
	 * @param aCollateralID of long type
	 */
	public void setCollateralID(long aCollateralID) {
		this.collateralID = aCollateralID;
	}

	/**
	 * Set the collateral reference
	 * @param aCollateralRef of String type
	 */
	public void setCollateralRef(String aCollateralRef) {
		this.collateralRef = aCollateralRef;
	}

	/**
	 * Set the collateral type
	 * @param anICollateralType of ICollateralType type
	 */
	public void setCollateralType(ICollateralType anICollateralType) {
		this.collateralType = anICollateralType;
	}

	/**
	 * Set the collateral sub type
	 * @param anICollateralSubType of ICollateralSubType type
	 */
	public void setCollateralSubType(ICollateralSubType anICollateralSubType) {
		this.collateralSubType = anICollateralSubType;
	}

	/**
	 * Set the collateral location
	 * @param aCollateralLocation of String type
	 */
	public void setCollateralLocation(String aCollateralLocation) {
		this.collateralLocation = aCollateralLocation;
	}

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	/**
	 * Set the version time
	 * @param aVersionTime of long type
	 */
	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

	/**
	 * Set the deleted indicator
	 * @param anIsDeletedInd - true if it is deleted and false otherwisef
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	public String getSecurityOrganisation() {
		return securityOrganisation;
	}

	public void setSecurityOrganisation(String securityOrganisation) {
		this.securityOrganisation = securityOrganisation;
	}

	public long getLeSubProfileID() {
		return leSubProfileID;
	}

	public void setLeSubProfileID(long leSubProfileID) {
		this.leSubProfileID = leSubProfileID;
	}

	public String getCustomerCategory() {
		if (ICMSConstant.CUSTOMER_CATEGORY_MAIN_BORROWER.equals(customerCategory)) {
			return ICMSConstant.CHECKLIST_MAIN_BORROWER;
		}
		if (ICMSConstant.CUSTOMER_CATEGORY_CO_BORROWER.equals(customerCategory)) {
			return ICMSConstant.CHECKLIST_CO_BORROWER;
		}
		return customerCategory;
	}

	public void setCustomerCategory(String customerCategory) {
		this.customerCategory = customerCategory;
	}

	public ICoBorrowerLimit[] getCoBorrowerLimitList() {
		return coBorrowerLimitList;
	}

	public void setCoBorrowerLimitList(ICoBorrowerLimit[] aCoBorrowerLimitList) {
		this.coBorrowerLimitList = aCoBorrowerLimitList;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
