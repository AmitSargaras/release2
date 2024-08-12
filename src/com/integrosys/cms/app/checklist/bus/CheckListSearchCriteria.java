/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/CheckListSearchCriteria.java,v 1.2 2004/10/07 11:13:46 wltan Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.base.businfra.search.SearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ITrxContext;

/**
 * This is a helper class that will contains all the search criteria required by
 * the checklist.
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/10/07 11:13:46 $ Tag: $Name: $
 */
public class CheckListSearchCriteria extends SearchCriteria {
	private long checklistID = ICMSConstant.LONG_INVALID_VALUE;

	private boolean isPendingOfficerApproval;

	private String trxID;

	private ITrxContext trxContext;

	private String checklistType;

	/**
	 * Default Constructor
	 */
	public CheckListSearchCriteria() {
	}

	/**
	 * Get transaction context.
	 * 
	 * @return String
	 */
	public ITrxContext getTrxContext() {
		return trxContext;
	}

	/**
	 * Set transaction context.
	 * 
	 * @param trxContext of type ITrxContext
	 */
	public void setTrxContext(ITrxContext trxContext) {
		this.trxContext = trxContext;
	}

	/**
	 * Get commodity deal number.
	 * 
	 * @return String
	 */
	public long getCheckListID() {
		return checklistID;
	}

	/**
	 * Set checklist ID.
	 * 
	 * @param checklistID of type long
	 */
	public void setCheckListID(long checklistID) {
		this.checklistID = checklistID;
	}

	/**
	 * Get transaction id.
	 * 
	 * @return String
	 */
	public String getTrxID() {
		return trxID;
	}

	/**
	 * Set transaction id.
	 * 
	 * @param trxID of type String
	 */
	public void setTrxID(String trxID) {
		this.trxID = trxID;
	}

	/**
	 * Get checklist type.
	 * 
	 * @return String
	 */
	public String getChecklistType() {
		return checklistType;
	}

	/**
	 * Set checklist type. Either ICMSConstant.DOC_TYPE_CC or
	 * ICMSConstant.DOC_TYPE_SECURITY.
	 * 
	 * @param type of type String
	 */
	public void setCheckListType(String type) {
		this.checklistType = type;
	}

	/**
	 * A flag to indicate that the search is from FAM Task Re-assignment.
	 * 
	 * @return boolean
	 */
	public boolean isPendingOfficerApproval() {
		return isPendingOfficerApproval;
	}

	/**
	 * Set the flag that the search is from FAM Task Re-assignment.
	 * 
	 * @param pendingOfficerApproval of type boolean
	 */
	public void setPendingOfficerApproval(boolean pendingOfficerApproval) {
		isPendingOfficerApproval = pendingOfficerApproval;
	}
}
