/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/bus/ICollateralTask.java,v 1.8 2006/08/07 11:12:15 jzhai Exp $
 */
package com.integrosys.cms.app.collaborationtask.bus;

//java
import java.io.Serializable;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.collateral.bus.ICollateralType;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.ILimit;

/**
 * This interface defines the list of attributes that is required for Collateral
 * Task
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/08/07 11:12:15 $ Tag: $Name: $
 */
public interface ICollateralTask extends IValueObject, Serializable {
	/**
	 * Get the task ID
	 * @return long - the task ID
	 */
	public long getTaskID();

	/**
	 * Get the limit profile ID
	 * @return long - the limit profile ID
	 */
	public long getLimitProfileID();

	/**
	 * Get the limit reference
	 * @return String - the limit reference
	 */
	public ILimit[] getLimitList();

	/**
	 * Helper method to get the list of limit references
	 * @return String[] - the list of limit references
	 */
	public String[] getLimitRefList();

	/**
	 * Get the collateral ID
	 * @return long - the collateral ID
	 */
	public long getCollateralID();

	/**
	 * Get the collateral reference
	 * @return long - the collateral reference
	 */
	public String getCollateralRef();

	/**
	 * Get the collateral type
	 * @return ICollateralType - the collateral type
	 */
	public ICollateralType getCollateralType();

	/**
	 * Get the collateral sub type
	 * @return ICollateralSubType - the collateral sub type
	 */
	public ICollateralSubType getCollateralSubType();

	/**
	 * Get the collateral location
	 * @return String - the collateral location
	 */
	public String getCollateralLocation();

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks();

	/**
	 * Get the deleted indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd();

	/**
	 * Set the task ID
	 * @param aTaskID of long type
	 */
	public void setTaskID(long aTaskID);

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID);

	/**
	 * Set the limit list
	 * @param anILimitList of ILimit[] type
	 */
	public void setLimitList(ILimit[] anILimitList);

	/**
	 * Set the collateral ID
	 * @param aCollateralID of long type
	 */
	public void setCollateralID(long aCollateralID);

	/**
	 * Set the collateral reference
	 * @param aCollateralRef of String type
	 */
	public void setCollateralRef(String aCollateralRef);

	/**
	 * Set the collateral Type
	 * @param aCollateralType of ICollateralType type
	 */
	public void setCollateralType(ICollateralType aCollateralType);

	/**
	 * Set the collateral sub type
	 * @param aCollateralSubType of ICollateralSubType type
	 */
	public void setCollateralSubType(ICollateralSubType aCollateralSubType);

	/**
	 * Set the collateral location
	 * @param aCollateralLocation of String type
	 */
	public void setCollateralLocation(String aCollateralLocation);

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks);

	/**
	 * Set the deleted indicator
	 * @param anIsDeletedInd - true if it is deleted and false otherwisef
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	public void setSecurityOrganisation(String aSecurityOrganisation);

	public String getSecurityOrganisation();

	public long getLeSubProfileID();

	public void setLeSubProfileID(long cmsLeSubProfileID);

	public String getCustomerCategory();

	public void setCustomerCategory(String customerCategory);

	public ICoBorrowerLimit[] getCoBorrowerLimitList();

	public void setCoBorrowerLimitList(ICoBorrowerLimit[] coBorrowerLimitList);
}
