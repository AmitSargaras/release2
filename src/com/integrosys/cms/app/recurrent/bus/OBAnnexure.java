/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBAnnexure.java,v 1.12 2006/09/13 10:30:24 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class provides the implementation for IRecurrentCheckListItem
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/09/13 10:30:24 $ Tag: $Name: $
 */
public class OBAnnexure implements IAnnexure, Comparable {

	private static final long serialVersionUID = -4460731612348758539L;

	private long annexureID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long annexureRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String description = null;

	private String annexureStatus = ICMSConstant.CONVENANT_NOT_VERIFIED;

	private Date initialDocEndDate = null;

	private Date initialDueDate = null;

	private String sourceId;
	
	private IAnnexureSubItem[] subItemList = null;


	/**
	 * Get the annexure ID
	 * @return long - the annexure ID
	 */
	public long getAnnexureID() {
		return this.annexureID;
	}

	/**
	 * Get the annexure reference
	 * @return long - the annexure reference
	 */
	public long getAnnexureRef() {
		return this.annexureRef;
	}

	/**
	 * Get the annexure status
	 * @param String - the annexure status
	 */
	public String getAnnexureStatus() {
		return this.annexureStatus;
	}

	public IAnnexureSubItem[] getAnnexureSubItemList() {
		return this.subItemList;
	}
	
	public void setAnnexureSubItemList(IAnnexureSubItem[] aSubItemList) {
		this.subItemList = aSubItemList;
	}


	/**
	 * Get the description
	 * @return String - the description
	 */
	public String getDescription() {
		return this.description;
	}


	public Date getInitialDocEndDate() {
		return this.initialDocEndDate;
	}

	public Date getInitialDueDate() {
		return this.initialDueDate;
	}

	public String getSourceId() {
		return sourceId;
	}

	public IAnnexureSubItem[] getSubItemsByCondition(String cond) {
		if (!ICMSConstant.RECCOV_SUB_ITEM_COND_HISTORY.equals(cond)
				&& !ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING.equals(cond)
				&& !ICMSConstant.RECCOV_SUB_ITEM_COND_NON_PENDING.equals(cond)) {
			return new OBAnnexureSubItem[0];
		}

		IAnnexureSubItem[] subItemList = getAnnexureSubItemList();
		int subItemListLength = (subItemList != null) ? subItemList.length : 0;

		ArrayList aPendingSubItemList = new ArrayList();
		for (int i = 0; i < subItemListLength; i++) {
			IAnnexureSubItem aCovenantSubItem = subItemList[i];

			if (ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING.equals(cond)) {
				if (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(aCovenantSubItem.getStatus())) {
					aPendingSubItemList.add(aCovenantSubItem);
				}
			}
			else if (ICMSConstant.RECCOV_SUB_ITEM_COND_HISTORY.equals(cond)) {
				if (ICMSConstant.CONVENANT_STATE_CHECKED.equals(aCovenantSubItem.getStatus())
						|| ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED.equals(aCovenantSubItem.getStatus())) {
					aPendingSubItemList.add(aCovenantSubItem);
				}
			}
			else {
				if (!ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(aCovenantSubItem.getStatus())) {
					aPendingSubItemList.add(aCovenantSubItem);
				}
			}
		}

		IAnnexureSubItem[] newSubItemList = (IAnnexureSubItem[]) aPendingSubItemList
				.toArray(new IAnnexureSubItem[0]);

		if ((newSubItemList != null) && (newSubItemList.length > 1)) {
			Arrays.sort(newSubItemList);
		}

		return (newSubItemList != null) ? newSubItemList : new OBAnnexureSubItem[0];
	}

	/**
	 * Check if processing is allowed or not
	 * @return boolean - true if processing is allowed and false otherwise
	 */
	public boolean isProcessingAllowed() {
		if (ICMSConstant.CONVENANT_VERIFIED.equals(getAnnexureStatus())) {
			return false;
		}
		return true;
	}

	/**
	 * Get the annexure ID
	 * @param aAnnexureID of long type
	 */
	public void setAnnexureID(long aAnnexureID) {
		this.annexureID = aAnnexureID;
	}

	/**
	 * Set the annexure reference
	 * @param aAnnexureRef of long type
	 */
	public void setAnnexureRef(long aAnnexureRef) {
		this.annexureRef = aAnnexureRef;
	}

	/**
	 * Set the annexure status
	 * @param aAnnexureStatus of String type
	 */
	public void setAnnexureStatus(String aAnnexureStatus) {
		this.annexureStatus = aAnnexureStatus;
	}


	/**
	 * Set the description
	 * @param aDescription of String type
	 */
	public void setDescription(String aDescription) {
		this.description = aDescription;
	}


	public void setInitialDocEndDate(Date anInitialDocEndDate) {
		this.initialDocEndDate = anInitialDocEndDate;
	}

	public void setInitialDueDate(Date aInitialDueDate) {
		this.initialDueDate = aInitialDueDate;
	}


	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public void updateSubItem(int anItemIndex, IAnnexureSubItem anISubItem) {
		IAnnexureSubItem[] subItemList = getAnnexureSubItemList();
		if (subItemList != null) {
			if (anItemIndex < subItemList.length) {
				// if sub-item status is PENDING
				// if
				// (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(subItemList
				// [anItemIndex].getStatus()))
				// {
				if (anISubItem.getWaivedDate() != null) {
					anISubItem.setStatus(ICMSConstant.RECURRENT_ITEM_STATE_PENDING_WAIVER); // set
					// status
					// to
					// PENDING_WAIVER
				}
				else {
					anISubItem.setStatus(ICMSConstant.RECURRENT_ITEM_STATE_PENDING);
				}
				// }
				subItemList[anItemIndex] = anISubItem;
				setAnnexureSubItemList(subItemList);
			}
		}
	}

	public int compareTo(Object other) {
		String otherDescription = (other == null) ? null : ((IAnnexure) other).getDescription();

		if (this.description == null) {
			return (otherDescription == null) ? 0 : -1;
		}

		return (otherDescription == null) ? 1 : this.description.compareTo(otherDescription);
	}
}
