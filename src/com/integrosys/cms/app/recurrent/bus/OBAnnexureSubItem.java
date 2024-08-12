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
public class OBAnnexureSubItem implements IAnnexureSubItem, Comparable {

	private static final long serialVersionUID = 8040225536590917018L;

	private long subItemID = ICMSConstant.LONG_INVALID_VALUE;

	private long subItemRef = ICMSConstant.LONG_INVALID_VALUE;

	private String status = ICMSConstant.RECURRENT_ITEM_STATE_PENDING;

	private Date docEndDate = null;

	private Date dueDate = null;

	private Date deferredDate = null;

	private Date waivedDate = null;

	public long getSubItemID() {
		return this.subItemID;
	}

	public long getSubItemRef() {
		return this.subItemRef;
	}

	public String getStatus() {
		return this.status;
	}

	public Date getDocEndDate() {
		return this.docEndDate;
	}

	public Date getDueDate() {
		return this.dueDate;
	}


	public Date getDeferredDate() {
		return this.deferredDate;
	}

	public Date getWaivedDate() {
		return this.waivedDate;
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

	public void setSubItemID(long aSubItemID) {
		this.subItemID = aSubItemID;
	}

	public void setSubItemRef(long aSubItemRef) {
		this.subItemRef = aSubItemRef;
	}

	public void setStatus(String aStatus) {
		this.status = aStatus;
	}

	public void setDocEndDate(Date aDocEndDate) {
		this.docEndDate = aDocEndDate;
	}

	public void setDueDate(Date aDueDate) {
		this.dueDate = aDueDate;
	}

	public void setDeferredDate(Date aDeferredDate) {
		this.deferredDate = aDeferredDate;
	}

	public void setWaivedDate(Date waivedDate) {
		this.waivedDate = waivedDate;
	}

	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	public int compareTo(Object other) {
		long otherSubItemID = (other == null) ? Long.MAX_VALUE : ((IAnnexureSubItem) other).getSubItemID();

		return (this.subItemID == otherSubItemID) ? 0 : ((this.subItemID > otherSubItemID) ? 1 : -1);
	}
}
