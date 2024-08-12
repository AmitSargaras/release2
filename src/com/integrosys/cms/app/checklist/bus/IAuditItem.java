/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/IAuditItem.java,v 1.2 2003/10/29 11:29:11 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that is required for audit
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/10/29 11:29:11 $ Tag: $Name: $
 */
public interface IAuditItem extends Serializable {
	/**
	 * Get the checklist item ID
	 * @return long - the checklist item ID
	 */
	public long getCheckListItemID();

	/**
	 * Get the item code
	 * @return String - the item code
	 */
	public String getItemCode();

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
	 * Get the document date
	 * @return Date - the document date
	 */
	public Date getDocumentDate();

	/**
	 * Set the checklist item ID
	 * @param aCheckListItemID of long type
	 */
	public void setCheckListItemID(long aCheckListItemID);

	/**
	 * Set the item code
	 * @param anItemCode of long type
	 */
	public void setItemCode(String anItemCode);

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

	/**
	 * Set the document date
	 * @param aDocumentDate of Date type
	 */
	public void setDocumentDate(Date aDocumentDate);
}
