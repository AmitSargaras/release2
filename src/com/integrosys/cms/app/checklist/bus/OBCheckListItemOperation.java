/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBCheckListItemOperation.java,v 1.3 2004/04/08 12:52:44 hltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * This class that implement the ICheckListItemOperation
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/04/08 12:52:44 $ Tag: $Name: $
 */
public class OBCheckListItemOperation implements ICheckListItemOperation, Comparable {

	private static final long serialVersionUID = -869799911703262475L;

	private String state = null;

	private String operation = null;

	private String operationDesc = null;

	/**
	 * Get the state
	 * @return String - the state
	 */
	public String getState() {
		return this.state;
	}

	/**
	 * Get the operation
	 * @return String - the operation
	 */
	public String getOperation() {
		return this.operation;
	}

	/**
	 * Get the operation description
	 * @return String - the operation desc
	 */
	public String getOperationDesc() {
		return this.operationDesc;
	}

	public int getSortOrder() {
		if (getOperationDesc().startsWith("VIEW")) {
			return 1;
		}
		if (getOperationDesc().equals("REMIND")) {
			return 2;
		}
		if (getOperationDesc().equals("RECEIVE")) {
			return 3;
		}
		if (getOperationDesc().equals("COMPLETE")) {
			return 99;
		}
		if (getOperationDesc().equals("DELETE")) {
			return 100;
		}
		return 50;
	}

	/**
	 * Set the state
	 * @param aState of String type
	 */
	public void setState(String aState) {
		this.state = aState;
	}

	/**
	 * Set the operation
	 * @param anOperation of String type
	 */
	public void setOperation(String anOperation) {
		this.operation = anOperation;
	}

	/**
	 * Set the operation description
	 * @param anOperationDescription of String type
	 */
	public void setOperationDesc(String anOperationDesc) {
		this.operationDesc = anOperationDesc;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public int compareTo(Object other) {
		int otherSortOrder = (other == null) ? Integer.MAX_VALUE : ((OBCheckListItemOperation) other).getSortOrder();

		return (this.getSortOrder() == otherSortOrder) ? 0 : ((this.getSortOrder() > otherSortOrder) ? 1 : -1);
	}
}
