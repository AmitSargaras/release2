/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/collaborationtask/OBCheckListTaskInfo.java,v 1.1 2005/10/13 03:39:22 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.collaborationtask;

import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * Bean to hold the information that are used for sending notifications
 */
public class OBCheckListTaskInfo extends OBEventInfo {

	private String orgCode;

	private String beforeChangeStatus;

	private String afterChangeStatus;

	private ICheckListItem[] itemList;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getBeforeChangeStatus() {
		return this.beforeChangeStatus;
	}

	public void setBeforeChangeStatus(String beforeChangeStatus) {
		this.beforeChangeStatus = beforeChangeStatus;
	}

	public String getAfterChangeStatus() {
		return this.afterChangeStatus;
	}

	public void setAfterChangeStatus(String afterChangeStatus) {
		this.afterChangeStatus = afterChangeStatus;
	}

	public ICheckListItem[] getItemList() {
		return this.itemList;
	}

	public void setItemList(ICheckListItem[] itemList) {
		this.itemList = itemList;
	}

	public int getNoOfDocumentWaived() {
		int noOfDocumentWaived = 0;
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				String st = itemList[i].getItemStatus();
				if ((st != null) && st.equalsIgnoreCase(ICMSConstant.STATE_ITEM_WAIVED)) {
					noOfDocumentWaived++;
				}
			}
		}
		return noOfDocumentWaived;
	}

	public int getNoOfDocumentDeferred() {
		int noOfDocumentDeferred = 0;
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				String st = itemList[i].getItemStatus();
				if ((st != null) && ((st.equalsIgnoreCase(ICMSConstant.STATE_ITEM_DEFERRED)))) {
					noOfDocumentDeferred++;
				}
			}
		}
		return noOfDocumentDeferred;
	}

	public int getNoOfDocumentDeleted() {
		int noOfDocumentDeleted = 0;
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				String st = itemList[i].getItemStatus();
				if ((st != null) && st.equalsIgnoreCase(ICMSConstant.STATE_ITEM_DELETED)) {
					noOfDocumentDeleted++;
				}
			}
		}
		return noOfDocumentDeleted;
	}
}