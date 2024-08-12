/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/eventmonitor/waiverdeferral/OBWaiverDeferralInfo.java,v 1.11 2006/03/06 12:39:45 hshii Exp $
 */

package com.integrosys.cms.app.eventmonitor.waiverdeferral;

import java.util.Date;

import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.eventmonitor.OBEventInfo;

/**
 * Bean to hold the information that are used for sending notifications
 */
public class OBWaiverDeferralInfo extends OBEventInfo {
	private String transactionID; // ROUTE

	private String checkListType;

	private Date approvalDate;

	private int noOfDocumentDeferred;

	private int noOfDocumentWaived;

	private int noOfDocumentPendingWaiver;

	private int noOfDocumentPendingDeferral;

	private String lastComment;

	private ICheckListItem[] itemList;

	private long trxUserID = ICMSConstant.LONG_INVALID_VALUE;

	private long trxUserTeamID = ICMSConstant.LONG_INVALID_VALUE;

	// private String securityID;
	private String securityType;

	private String securitySubType;

	private String securitySubTypeID;

	private Date[] securityMaturityDate;

	public String getTransactionID() {
		return transactionID;
	}

	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}

	public String getLabelOfCheckListType() {
		if (checkListType != null) {
			if (checkListType.equalsIgnoreCase("CC")) {
				return "C/C Checklist";
			}
			if (checkListType.equalsIgnoreCase("S")) {
				return "Security Checklist";
			}
		}
		return checkListType;
	}

	public String getCheckListType() {
		return checkListType;
	}

	public void setCheckListType(String checkListType) {
		this.checkListType = checkListType;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	/*
	 * public String getSecurityID() { return securityID; }
	 * 
	 * public void setSecurityID(String securityID) { this.securityID =
	 * securityID; }
	 */
	public String getSecurityType() {
		return securityType;
	}

	public void setSecurityType(String securityType) {
		this.securityType = securityType;
	}

	public String getSecuritySubType() {
		return securitySubType;
	}

	public void setSecuritySubType(String securitySubType) {
		this.securitySubType = securitySubType;
	}

	public String getSecuritySubTypeID() {
		return securitySubTypeID;
	}

	public void setSecuritySubTypeID(String securitySubTypeID) {
		this.securitySubTypeID = securitySubTypeID;
	}

	public Date[] getSecurityMaturityDate() {
		return securityMaturityDate;
	}

	public void setSecurityMaturityDate(Date[] securityMaturityDate) {
		this.securityMaturityDate = securityMaturityDate;
	}

	public int getNoOfDocumentDeferred() {
		noOfDocumentDeferred = 0;
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

	public int getNoOfDocumentPendingDeferral() {
		noOfDocumentPendingDeferral = 0;
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				String st = itemList[i].getItemStatus();
				if ((st != null) && ((st.equalsIgnoreCase(ICMSConstant.STATE_ITEM_PENDING_DEFERRAL)))) {
					noOfDocumentPendingDeferral++;
				}
			}
		}
		return noOfDocumentPendingDeferral;
	}

	public void setNoOfDocumentDeferred(int noOfDocumentDeferred) {
		this.noOfDocumentDeferred = noOfDocumentDeferred;
	}

	public int getNoOfDocumentWaived() {
		noOfDocumentWaived = 0;
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				String st = itemList[i].getItemStatus();
				if ((st != null) && ((st.equalsIgnoreCase(ICMSConstant.STATE_ITEM_WAIVED)))) {
					noOfDocumentWaived++;
				}
			}
		}
		return noOfDocumentWaived;
	}

	public int getNoOfDocumentPendingWaiver() {
		noOfDocumentPendingWaiver = 0;
		if (itemList != null) {
			for (int i = 0; i < itemList.length; i++) {
				String st = itemList[i].getItemStatus();
				if ((st != null) && ((st.equalsIgnoreCase(ICMSConstant.STATE_ITEM_PENDING_WAIVER)))) {
					noOfDocumentPendingWaiver++;
				}
			}
		}
		return noOfDocumentPendingWaiver;
	}

	public void setNoOfDocumentWaived(int noOfDocumentWaived) {
		this.noOfDocumentWaived = noOfDocumentWaived;
	}

	public String getLastComment() {
		return lastComment;
	}

	public void setLastComment(String lastComment) {
		this.lastComment = lastComment;
	}

	public ICheckListItem[] getItemList() {
		return itemList;
	}

	public void setItemList(ICheckListItem[] itemList) {
		this.itemList = itemList;
	}

	public long getTrxUserID() {
		return trxUserID;
	}

	public void setTrxUserID(long trxUserID) {
		this.trxUserID = trxUserID;
	}

	public long getTrxUserTeamID() {
		return trxUserTeamID;
	}

	public void setTrxUserTeamID(long trxUserTeamID) {
		this.trxUserTeamID = trxUserTeamID;
	}
}
