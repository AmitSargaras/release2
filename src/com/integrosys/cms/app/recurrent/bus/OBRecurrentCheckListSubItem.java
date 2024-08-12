/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBRecurrentCheckListSubItem.java,v 1.9 2006/08/18 10:52:58 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import java.util.Calendar;
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This class provides the implementation for IRecurrentCheckListSubItem
 * 
 * @author $Author: jychong $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/08/18 10:52:58 $ Tag: $Name: $
 */
public class OBRecurrentCheckListSubItem implements IRecurrentCheckListSubItem, Comparable {

	private static final long serialVersionUID = -1355038807546385871L;
	private long  recurrentItemId = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	private long subItemID = ICMSConstant.LONG_INVALID_VALUE;

	private long subItemRef = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.RECURRENT_ITEM_STATE_PENDING;

	private String remarks = null;

	private String actionParty = null;

	private int freq = Integer.MIN_VALUE;

	private String freqUnit = null;

	private int gracePeriod = Integer.MIN_VALUE;

	private String gracePeriodUnit = null;

	private Date docEndDate = null;

	private Date dueDate = null;

	private Date receivedDate = null;

	private Date deferredDate = null;

	private Date waivedDate = null;

	private long deferredCount = 0; // default to zero for no. of times deferred

	private boolean isPrintReminderInd = false;

	private boolean isDeletedInd = false;

	public long getSubItemID() {
		return this.subItemID;
	}

	public long getSubItemRef() {
		return this.subItemRef;
	}

	public long getRecurrentItemId() {
		return recurrentItemId;
	}

	public void setRecurrentItemId(long recurrentItemId) {
		this.recurrentItemId = recurrentItemId;
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

	public String getStatus() {
		return this.status;
		/*
		 * if (getReceivedDate() != null) { return
		 * ICMSConstant.RECURRENT_ITEM_STATE_RECEIVED; } return
		 * ICMSConstant.RECURRENT_ITEM_STATE_PENDING;
		 */
	}

	public String getRemarks() {
		return this.remarks;
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

	public Date getDocEndDate() {
		return this.docEndDate;
	}

	public Date getDueDate() {
		return this.dueDate;
	}

	public Date getReceivedDate() {
		return this.receivedDate;
	}

	public Date getDeferredDate() {
		return this.deferredDate;
	}

	public Date getWaivedDate() {
		return this.waivedDate;
	}

	public long getDeferredCount() {
		return this.deferredCount;
	}

	public long getDaysOverDue() {
		Date compDueDate = getDueDate();
		if (getDeferredDate() != null) {
			compDueDate = getDeferredDate();
		}
		if (compDueDate == null) {
			return 0;
		}
		if (DateUtil.getDate().getTime() <= compDueDate.getTime()) {
			return 0;
		}
		return CommonUtil.dateDiff(DateUtil.getDate(), compDueDate, Calendar.DATE);
	}

	public boolean getIsPrintReminderInd() {
		return this.isPrintReminderInd;
	}

	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	public void setSubItemID(long aSubItemID) {
		this.subItemID = aSubItemID;
	}

	public void setSubItemRef(long aSubItemRef) {
		this.subItemRef = aSubItemRef;
	}

	public void setStatus(String aStatus) {
		this.status = aStatus;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public void setDocEndDate(Date aDocEndDate) {
		this.docEndDate = aDocEndDate;
	}

	public void setDueDate(Date aDueDate) {
		this.dueDate = aDueDate;
	}

	public void setReceivedDate(Date aReceivedDate) {
		this.receivedDate = aReceivedDate;
	}

	public void setDeferredDate(Date aDeferredDate) {
		this.deferredDate = aDeferredDate;
	}

	public void setWaivedDate(Date waivedDate) {
		this.waivedDate = waivedDate;
	}

	public void setDeferredCount(long deferredCount) {
		this.deferredCount = deferredCount;
	}

	public void setIsPrintReminderInd(boolean anIsPrintReminderInd) {
		this.isPrintReminderInd = anIsPrintReminderInd;
	}

	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public String getActionParty() {
		return actionParty;
	}

	public void setActionParty(String actionParty) {
		this.actionParty = actionParty;
	}

	public int compareTo(Object other) {
		long otherSubItemID = (other == null) ? Long.MAX_VALUE : ((IRecurrentCheckListSubItem) other).getSubItemID();

		return (this.subItemID == otherSubItemID) ? 0 : ((this.subItemID > otherSubItemID) ? 1 : -1);
	}

}
