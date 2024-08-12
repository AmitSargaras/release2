/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBRecurrentCheckListItem.java,v 1.21 2006/09/13 10:30:24 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This class provides the implementation for IRecurrentCheckListItem
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.21 $
 * @since $Date: 2006/09/13 10:30:24 $ Tag: $Name: $
 */
public class OBRecurrentCheckListItem implements IRecurrentCheckListItem, Comparable {

	private static final long serialVersionUID = -6098167734887091217L;

	private long checkListItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long checkListItemRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private long  recurrentDocId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private IItem item = null;

	private int freq = Integer.MIN_VALUE;

	private String freqUnit = null;

	private int gracePeriod = Integer.MIN_VALUE;

	private String gracePeriodUnit = null;

	private boolean chaseReminderInd = false;

	private String remarks = null;
	
	private String recurrentItemDesc = null;

	private boolean isDeletedInd = false;

	private Date initialDocEndDate = null;

	private Date initialDueDate = null;

	private boolean isOneOffInd = false;

	private Date lastDocEntryDate = null;

	private int endDateChangedCount = 0;

	private IRecurrentCheckListSubItem[] subItemList = null;

	
	private String docType;
	
	/**
	 * @return the docType
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType the docType to set
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	public long getRecurrentDocId() {
		return recurrentDocId;
	}

	public void setRecurrentDocId(long recurrentDocId) {
		this.recurrentDocId = recurrentDocId;
	}

	public int getFreq() {
		return freq;
	}

	public void setFreq(int freq) {
		this.freq = freq;
	}

	public String getFreqUnit() {
		return freqUnit;
	}

	public void setFreqUnit(String freqUnit) {
		this.freqUnit = freqUnit;
	}

	public void setDeletedInd(boolean isDeletedInd) {
		this.isDeletedInd = isDeletedInd;
	}

	public void setOneOffInd(boolean isOneOffInd) {
		this.isOneOffInd = isOneOffInd;
	}

	public long getCheckListItemID() {
		return this.checkListItemID;
	}

	public long getCheckListItemRef() {
		return this.checkListItemRef;
	}

	public IItem getItem() {
		return this.item;
	}

	public String getItemDesc() {
		if (getItem() != null) {
			return getItem().getItemDesc();
		}
		return null;
	}

	public int getFrequency() {
		return this.freq;
	}

	public String getFrequencyUnit() {
		return this.freqUnit;
	}

	public int getGracePeriod() {
		return this.gracePeriod;
	}

	public String getGracePeriodUnit() {
		return this.gracePeriodUnit;
	}

	public boolean getChaseReminderInd() {
		return this.chaseReminderInd;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	public Date getInitialDocEndDate() {
		return this.initialDocEndDate;
	}

	public Date getInitialDueDate() {
		return this.initialDueDate;
	}

	public boolean getIsOneOffInd() {
		return this.isOneOffInd;
	}

	public Date getLastDocEntryDate() {
		return this.lastDocEntryDate;
	}

	public int getEndDateChangedCount() {
		return this.endDateChangedCount;
	}

	public IRecurrentCheckListSubItem[] getRecurrentCheckListSubItemList() {
		return this.subItemList;
	}

	public void setCheckListItemID(long aCheckListItemID) {
		this.checkListItemID = aCheckListItemID;
	}

	public void setCheckListItemRef(long aCheckListItemRef) {
		this.checkListItemRef = aCheckListItemRef;
	}

	public void setItem(IItem anIItem) {
		this.item = anIItem;
	}

	public void setFrequency(int aFrequency) {
		this.freq = aFrequency;
	}

	public void setFrequencyUnit(String aFrequencyUnit) {
		this.freqUnit = aFrequencyUnit;
	}

	public void setGracePeriod(int aGracePeriod) {
		this.gracePeriod = aGracePeriod;
	}

	public void setGracePeriodUnit(String aGracePeriodUnit) {
		this.gracePeriodUnit = aGracePeriodUnit;
	}

	public void setChaseReminderInd(boolean aChaseReminderInd) {
		this.chaseReminderInd = aChaseReminderInd;
	}

	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	public void setInitialDocEndDate(Date anInitialDocEndDate) {
		this.initialDocEndDate = anInitialDocEndDate;
	}

	public void setInitialDueDate(Date aInitialDueDate) {
		this.initialDueDate = aInitialDueDate;
	}

	public void setIsOneOffInd(boolean isOneOffInd) {
		this.isOneOffInd = isOneOffInd;
	}

	public void setLastDocEntryDate(Date lastDocEntryDate) {
		this.lastDocEntryDate = lastDocEntryDate;
	}

	public void setEndDateChangedCount(int count) {
		this.endDateChangedCount = count;
	}

	public void setRecurrentCheckListSubItemList(IRecurrentCheckListSubItem[] aSubItemList) {
		this.subItemList = aSubItemList;
	}

		public String getRecurrentItemDesc() {
		return recurrentItemDesc;
	}

	public void setRecurrentItemDesc(String recurrentItemDesc) {
		this.recurrentItemDesc = recurrentItemDesc;
	}

	public void updateSubItem(int anItemIndex, IRecurrentCheckListSubItem anISubItem) {
		IRecurrentCheckListSubItem[] subItemList = getRecurrentCheckListSubItemList();
		if (subItemList != null) {
			if (anItemIndex < subItemList.length) {
				// if sub-item status is PENDING
				// if
				// (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(subItemList
				// [anItemIndex].getStatus()))
				// {
				if (anISubItem.getReceivedDate() != null) {
					anISubItem.setStatus(ICMSConstant.STATE_PENDING_RECEIVED); // set
					// status
					// to
					// PENDING_RECEIVED
				}
				else if (anISubItem.getDeferredDate() != null) {
					anISubItem.setStatus(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL); // set
					// status
					// to
					// PENDING_RECEIVED
				}
				else if (anISubItem.getWaivedDate() != null) {
					anISubItem.setStatus(ICMSConstant.RECURRENT_ITEM_STATE_PENDING_WAIVER); // set
					// status
					// to
					// PENDING_WAIVER
				}
				else {
					com.integrosys.base.techinfra.logger.DefaultLogger.debug(this,
							"###### <updateSubItem>, to set sub item status to 'PENDING'. ");
					anISubItem.setStatus(ICMSConstant.RECURRENT_ITEM_STATE_PENDING);
				}
				// }
				subItemList[anItemIndex] = anISubItem;
				setRecurrentCheckListSubItemList(subItemList);
			}
		}
	}

	public IRecurrentCheckListSubItem[] getSubItemsByCondition(String cond) {
		if (!ICMSConstant.RECCOV_SUB_ITEM_COND_HISTORY.equals(cond)
				&& !ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING.equals(cond)
				&& !ICMSConstant.RECCOV_SUB_ITEM_COND_NON_PENDING.equals(cond)) {
			return new OBRecurrentCheckListSubItem[0];
		}

		IRecurrentCheckListSubItem[] subItemList = getRecurrentCheckListSubItemList();
		int subItemListLength = (subItemList != null) ? subItemList.length : 0;

		ArrayList aPendingSubItemList = new ArrayList();
		for (int i = 0; i < subItemListLength; i++) {
			IRecurrentCheckListSubItem aRecurrentSubItem = subItemList[i];

			if (ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING.equals(cond)) {
				if (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(aRecurrentSubItem.getStatus())) {
					aPendingSubItemList.add(aRecurrentSubItem);
				}
			}
			else if (ICMSConstant.RECCOV_SUB_ITEM_COND_HISTORY.equals(cond)) {
				if (ICMSConstant.RECURRENT_ITEM_STATE_ITEM_WAIVED.equals(aRecurrentSubItem.getStatus())
						|| ICMSConstant.RECURRENT_ITEM_STATE_RECEIVED.equals(aRecurrentSubItem.getStatus())) {
					aPendingSubItemList.add(aRecurrentSubItem);
				}
			}
			else {
				if (!ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(aRecurrentSubItem.getStatus())) {
					aPendingSubItemList.add(aRecurrentSubItem);
				}
			}
		}

		IRecurrentCheckListSubItem[] newSubItemList = (IRecurrentCheckListSubItem[]) aPendingSubItemList
				.toArray(new IRecurrentCheckListSubItem[0]);

		if ((newSubItemList != null) && (newSubItemList.length > 1)) {
			Arrays.sort(newSubItemList);
		}

		return (newSubItemList != null) ? newSubItemList : new OBRecurrentCheckListSubItem[0];
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
		String otherItemDesc = (other == null) ? null : ((IRecurrentCheckListItem) other).getItemDesc();

		if (this.getItemDesc() == null) {
			return (otherItemDesc == null) ? 0 : -1;
		}

		return (otherItemDesc == null) ? 1 : this.getItemDesc().compareTo(otherItemDesc);
	}
}
