/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBCollateralAuditItem.java,v 1.2 2003/08/22 11:13:25 sathish Exp $
 */
package com.integrosys.cms.app.checklist.bus;

/**
 * This class that provides the implementation for ICollateralAuditItem
 * 
 * @author $Author: sathish $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:25 $ Tag: $Name: $
 */
public class OBCollateralAuditItem implements ICollateralAuditItem {
	private long cmsCollateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String collateralID = null;

	private String description = null;

	private boolean isInVaultInd = false;

	/**
	 * Get the CMS Collateral ID
	 * @return long - the CMS collateral ID
	 */
	public long getCMSCollateralID() {
		return this.cmsCollateralID;
	}

	/**
	 * Get the Collateral ID (Biz Key)
	 * @return String - the collateral ID
	 */
	public String getCollateralID() {
		return this.collateralID;
	}

	/**
	 * Get the audit item description
	 * @return String - the item description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Get the in vault indicator
	 * @return boolean - true if it is in vault and false otherwise
	 */
	public boolean getIsInVaultInd() {
		return this.isInVaultInd;
	}

	/**
	 * Set the CMS collateral ID
	 * @param aCMSCollateralID of long type
	 */
	public void setCMSCollateralID(long aCMSCollateralID) {
		this.cmsCollateralID = aCMSCollateralID;
	}

	/**
	 * Set the collateral ID
	 * @param aCollateralID of long type
	 */
	public void setCollateralID(String aCollateralID) {
		this.collateralID = aCollateralID;
	}

	/**
	 * Set the checklist item
	 * @param aDescription of String type
	 */
	public void setDescription(String aDescription) {
		this.description = aDescription;
	}

	/**
	 * Set the in vault indicator
	 * @param anIsInVaultInd of boolean type
	 */
	public void setIsInVaultInd(boolean anIsInVaultInd) {
		this.isInVaultInd = anIsInVaultInd;
	}
}
