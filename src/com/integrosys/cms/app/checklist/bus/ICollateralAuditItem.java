/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICollateralAuditItem.java,v 1.1 2003/07/30 12:14:48 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.io.Serializable;

/**
 * This interface defines the list of attributes that is required for collateral
 * audit
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/30 12:14:48 $ Tag: $Name: $
 */
public interface ICollateralAuditItem extends Serializable {
	/**
	 * Get the CMS Collateral ID
	 * @return long - the CMS collateral ID
	 */
	public long getCMSCollateralID();

	/**
	 * Get the Collateral ID (Biz Key)
	 * @return String - the collateral ID
	 */
	public String getCollateralID();

	/**
	 * Get the audit item description
	 * @return String - the item description
	 */
	public String getDescription();

	/**
	 * Get the in vault indicator
	 * @return boolean - true if it is in vault and false otherwise
	 */
	public boolean getIsInVaultInd();

	/**
	 * Set the CMS collateral ID
	 * @param aCMSCollateralID of long type
	 */
	public void setCMSCollateralID(long aCMSCollateralID);

	/**
	 * Set the collateral ID
	 * @param aCollateralID of long type
	 */
	public void setCollateralID(String aCollateralID);

	/**
	 * Set the checklist item description
	 * @param aDescription of String type
	 */
	public void setDescription(String aDescription);

	/**
	 * Set the in vault indicator
	 * @param anIsInVaultInd of boolean type
	 */
	public void setIsInVaultInd(boolean anIsInVaultInd);
}
