/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBConvenant.java,v 1.12 2006/09/13 10:30:24 jychong Exp $
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
public class OBConvenant implements IConvenant, Comparable {

	private static final long serialVersionUID = -4460731612348758539L;

	private long convenantID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long convenantRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String description = null;

	private String remarks = null;

	private boolean isVerifiedInd = false;

	private String convenantStatus = ICMSConstant.CONVENANT_NOT_VERIFIED;

	private boolean isDeletedInd = false;

	private Date dateChecked = null;

	// cr 26
	private int freq = Integer.MIN_VALUE;

	private String freqUnit = null;

	private int gracePeriod = Integer.MIN_VALUE;

	private String gracePeriodUnit = null;

	private boolean chaseReminderInd = false;

	private Date initialDocEndDate = null;

	private Date initialDueDate = null;

	private boolean isOneOffInd = false;

	private Date lastDocEntryDate = null;

	private int endDateChangedCount = 0;

	private IConvenantSubItem[] subItemList = null;

	private boolean riskTrigger = false;

	// cr 234
	private boolean fee = false;

	private boolean isParameterizedDesc = false;

	private String sourceId;

	public boolean getChaseReminderInd() {
		return this.chaseReminderInd;
	}

	/**
	 * Get the convenant ID
	 * @return long - the convenant ID
	 */
	public long getConvenantID() {
		return this.convenantID;
	}

	/**
	 * Get the convenant reference
	 * @return long - the convenant reference
	 */
	public long getConvenantRef() {
		return this.convenantRef;
	}

	/**
	 * Get the convenant status
	 * @param String - the convenant status
	 */
	public String getConvenantStatus() {
		return this.convenantStatus;
	}

	public IConvenantSubItem[] getConvenantSubItemList() {
		return this.subItemList;
	}

	/**
	 * Get the date checked
	 * @return Date - the date checked
	 */
	public Date getDateChecked() {
		return this.dateChecked;
	}

	/**
	 * Get the description
	 * @return String - the description
	 */
	public String getDescription() {
		return this.description;
	}

	public int getEndDateChangedCount() {
		return this.endDateChangedCount;
	}

	public boolean getFee() {
		return this.fee;
	}

	// cr26
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

	public Date getInitialDocEndDate() {
		return this.initialDocEndDate;
	}

	public Date getInitialDueDate() {
		return this.initialDueDate;
	}

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	public boolean getIsOneOffInd() {
		return this.isOneOffInd;
	}

	public boolean getIsParameterizedDesc() {
		return isParameterizedDesc;
	}

	/**
	 * Get the verified indicator
	 * @return boolean - true if it is verified and false otherwise
	 */
	public boolean getIsVerifiedInd() {
		return this.isVerifiedInd;
	}

	public Date getLastDocEntryDate() {
		return this.lastDocEntryDate;
	}

	/**
	 * Get the remarks
	 * @return String - the remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	public boolean getRiskTrigger() {
		return this.riskTrigger;
	}

	public String getSourceId() {
		return sourceId;
	}

	public IConvenantSubItem[] getSubItemsByCondition(String cond) {
		if (!ICMSConstant.RECCOV_SUB_ITEM_COND_HISTORY.equals(cond)
				&& !ICMSConstant.RECCOV_SUB_ITEM_COND_PENDING.equals(cond)
				&& !ICMSConstant.RECCOV_SUB_ITEM_COND_NON_PENDING.equals(cond)) {
			return new OBConvenantSubItem[0];
		}

		IConvenantSubItem[] subItemList = getConvenantSubItemList();
		int subItemListLength = (subItemList != null) ? subItemList.length : 0;

		ArrayList aPendingSubItemList = new ArrayList();
		for (int i = 0; i < subItemListLength; i++) {
			IConvenantSubItem aCovenantSubItem = subItemList[i];

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

		IConvenantSubItem[] newSubItemList = (IConvenantSubItem[]) aPendingSubItemList
				.toArray(new IConvenantSubItem[0]);

		if ((newSubItemList != null) && (newSubItemList.length > 1)) {
			Arrays.sort(newSubItemList);
		}

		return (newSubItemList != null) ? newSubItemList : new OBConvenantSubItem[0];
	}

	/**
	 * Check if processing is allowed or not
	 * @return boolean - true if processing is allowed and false otherwise
	 */
	public boolean isProcessingAllowed() {
		if (ICMSConstant.CONVENANT_VERIFIED.equals(getConvenantStatus())) {
			return false;
		}
		return true;
	}

	public void setChaseReminderInd(boolean aChaseReminderInd) {
		this.chaseReminderInd = aChaseReminderInd;
	}

	/**
	 * Get the convenant ID
	 * @param aConvenantID of long type
	 */
	public void setConvenantID(long aConvenantID) {
		this.convenantID = aConvenantID;
	}

	/**
	 * Set the convenant reference
	 * @param aConvenantRef of long type
	 */
	public void setConvenantRef(long aConvenantRef) {
		this.convenantRef = aConvenantRef;
	}

	/**
	 * Set the convenant status
	 * @param aConvenantStatus of String type
	 */
	public void setConvenantStatus(String aConvenantStatus) {
		this.convenantStatus = aConvenantStatus;
	}

	public void setConvenantSubItemList(IConvenantSubItem[] aSubItemList) {
		this.subItemList = aSubItemList;
	}

	/**
	 * Set the date checked
	 * @param aDateChecked of Date type
	 */
	public void setDateChecked(Date aDateChecked) {
		this.dateChecked = aDateChecked;
	}

	/**
	 * Set the description
	 * @param aDescription of String type
	 */
	public void setDescription(String aDescription) {
		this.description = aDescription;
	}

	public void setEndDateChangedCount(int count) {
		this.endDateChangedCount = count;
	}

	public void setFee(boolean fee) {
		this.fee = fee;
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

	public void setInitialDocEndDate(Date anInitialDocEndDate) {
		this.initialDocEndDate = anInitialDocEndDate;
	}

	public void setInitialDueDate(Date aInitialDueDate) {
		this.initialDueDate = aInitialDueDate;
	}

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	public void setIsOneOffInd(boolean isOneOffInd) {
		this.isOneOffInd = isOneOffInd;
	}

	public void setIsParameterizedDesc(boolean isParameterizedDesc) {
		this.isParameterizedDesc = isParameterizedDesc;
	}

	/**
	 * Set the verified indicator
	 * @param anIsVerifiedInd of boolean type
	 */
	public void setIsVerifiedInd(boolean anIsVerifiedInd) {
		this.isVerifiedInd = anIsVerifiedInd;
	}

	public void setLastDocEntryDate(Date lastDocEntryDate) {
		this.lastDocEntryDate = lastDocEntryDate;
	}

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	public void setRiskTrigger(boolean riskTrigger) {
		this.riskTrigger = riskTrigger;
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

	public void updateSubItem(int anItemIndex, IConvenantSubItem anISubItem) {
		IConvenantSubItem[] subItemList = getConvenantSubItemList();
		if (subItemList != null) {
			if (anItemIndex < subItemList.length) {
				// if sub-item status is PENDING
				// if
				// (ICMSConstant.RECURRENT_ITEM_STATE_PENDING.equals(subItemList
				// [anItemIndex].getStatus()))
				// {
				if (anISubItem.getCheckedDate() != null) {
					anISubItem.setStatus(ICMSConstant.CONVENANT_STATE_PENDING_CHECKED); // set
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
					anISubItem.setStatus(ICMSConstant.RECURRENT_ITEM_STATE_PENDING);
				}
				// }
				subItemList[anItemIndex] = anISubItem;
				setConvenantSubItemList(subItemList);
			}
		}
	}

	public int compareTo(Object other) {
		String otherDescription = (other == null) ? null : ((IConvenant) other).getDescription();

		if (this.description == null) {
			return (otherDescription == null) ? 0 : -1;
		}

		return (otherDescription == null) ? 1 : this.description.compareTo(otherDescription);
	}
}
