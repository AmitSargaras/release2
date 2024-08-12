/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckListItemOperation.java,v 1.3 2004/04/08 12:52:44 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.io.Serializable;

/**
 * This interface defines the list of attributes that will be available to a
 * checklist item operation
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/08 12:52:44 $ Tag: $Name: $
 */
public interface ICheckListItemOperation extends Serializable {
	/**
	 * Get the state
	 * @return String - the state
	 */
	public String getState();

	/**
	 * Get the operation
	 * @return String - the operation
	 */
	public String getOperation();

	/**
	 * Get the operation description
	 * @return String - the operation desc
	 */
	public String getOperationDesc();

	/**
	 * Get the sort order
	 * @return int - the sort order
	 */
	public int getSortOrder();

	/**
	 * Set the state
	 * @param aState of String type
	 */
	public void setState(String aState);

	/**
	 * Set the operation
	 * @param anOperation of String type
	 */
	public void setOperation(String anOperation);

	/**
	 * Set the operation description
	 * @param anOperationDescription of String type
	 */
	public void setOperationDesc(String anOperationDesc);
}
