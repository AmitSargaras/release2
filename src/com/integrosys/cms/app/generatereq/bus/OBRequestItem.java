/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/OBRequestItem.java,v 1.2 2003/09/12 17:41:55 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

//ofa
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;

/**
 * This class that provides the implementation for IRequestItem
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/12 17:41:55 $ Tag: $Name: $
 */
public class OBRequestItem implements IRequestItem {
	private long requestItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long requestItemRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private boolean isDeletedInd = false;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private ICheckListItem checkListItem = null;

	public OBRequestItem() {
	}

	public long getRequestItemID() {
		return this.requestItemID;
	}

	public long getRequestItemRef() {
		return this.requestItemRef;
	}

	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	public long getCheckListID() {
		return this.checkListID;
	}

	public ICheckListItem getCheckListItem() {
		return this.checkListItem;
	}

	public void setRequestItemID(long aRequestItemID) {
		this.requestItemID = aRequestItemID;
	}

	public void setRequestItemRef(long aRequestItemRef) {
		this.requestItemRef = aRequestItemRef;
	}

	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	public void setCheckListItem(ICheckListItem aCheckListItem) {
		this.checkListItem = aCheckListItem;
	}

	/**
	 * Prints a String representation of this object
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}
}
