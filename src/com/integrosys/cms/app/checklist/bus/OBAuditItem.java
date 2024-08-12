/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBAuditItem.java,v 1.3 2003/10/29 11:29:11 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class that provides the implementation for IAuditItem
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/10/29 11:29:11 $ Tag: $Name: $
 */
public class OBAuditItem implements IAuditItem {
	private long checkListItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String itemCode = null;

	private String description = null;

	private boolean isInVaultInd = false;

	private Date documentDate = null;

	/**
	 * Get the checklist item ID
	 * @return long - the checklist item ID
	 */
	public long getCheckListItemID() {
		return this.checkListItemID;
	}

	/**
	 * Get the item code
	 * @return String - the item code
	 */
	public String getItemCode() {
		return this.itemCode;
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
	 * Get the document date
	 * @return Date - the document date
	 */
	public Date getDocumentDate() {
		return this.documentDate;
	}

	/**
	 * Set the CMS collateral ID
	 * @param aCMSCollateralID of long type
	 */
	public void setCheckListItemID(long aCheckListItemID) {
		this.checkListItemID = aCheckListItemID;
	}

	/**
	 * Set the item code
	 * @param anItemCode of String type
	 */
	public void setItemCode(String anItemCode) {
		this.itemCode = anItemCode;
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

	/**
	 * Set the document date
	 * @param aDocumentDate of Date type
	 */
	public void setDocumentDate(Date aDocumentDate) {
		this.documentDate = aDocumentDate;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

}
