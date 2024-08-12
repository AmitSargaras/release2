/*
 * Created on Jan 11, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.integrosys.cms.app.recurrent.bus;

//java

import java.util.Calendar;
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * @author user
 */
public class OBConvenantSubItem implements IConvenantSubItem, Comparable {

	private static final long serialVersionUID = 8040225536590917018L;

	private long subItemID = ICMSConstant.LONG_INVALID_VALUE;

	private long subItemRef = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.RECURRENT_ITEM_STATE_PENDING;

	private String remarks = null;

	private String actionParty = null;

	private Date docEndDate = null;

	private Date dueDate = null;

	private Date checkedDate = null;

	private Date deferredDate = null;

	private Date waivedDate = null;

	private long deferredCount = 0; // default to zero for no. of times deferred

	private int freq = Integer.MIN_VALUE;

	private String freqUnit = null;

	private int gracePeriod = Integer.MIN_VALUE;

	private String gracePeriodUnit = null;

	private boolean isPrintReminderInd = false;

	private boolean isDeletedInd = false;

	private boolean isVerifiedInd = false;

	public long getSubItemID() {
		return this.subItemID;
	}

	public long getSubItemRef() {
		return this.subItemRef;
	}

	public String getStatus() {
		return this.status;
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

	public Date getCheckedDate() {
		return this.checkedDate;
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

	public boolean getIsVerifiedInd() {
		return this.isVerifiedInd;
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

	public void setCheckedDate(Date aDate) {
		this.checkedDate = aDate;
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

	public void setIsVerifiedInd(boolean anIsVerifiedInd) {
		this.isVerifiedInd = anIsVerifiedInd;
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
		long otherSubItemID = (other == null) ? Long.MAX_VALUE : ((IConvenantSubItem) other).getSubItemID();

		return (this.subItemID == otherSubItemID) ? 0 : ((this.subItemID > otherSubItemID) ? 1 : -1);
	}
}
