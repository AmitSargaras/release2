/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICollateralCheckListOwner.java,v 1.2 2003/09/15 04:12:08 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

/**
 * This interface defines the list of attributes that is required for a
 * collateral checklist owner
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/15 04:12:08 $ Tag: $Name: $
 */
public interface ICollateralCheckListOwner extends ICheckListOwner {
	/**
	 * Get the collateral ID.
	 * @return long - the collateral ID
	 */
	public long getCollateralID();

	public String getCollateralRef();

	/**
	 * Set the collateral ID.
	 * @param aCollateralID the long representation
	 */
	public void setCollateralID(long aCollateralID);

	public void setCollateralRef(String aCollateralRef);


    public String getApplicationType();

    public void setApplicationType(String applicationType);
}
