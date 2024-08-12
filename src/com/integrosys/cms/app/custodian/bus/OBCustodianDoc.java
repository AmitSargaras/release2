/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/custodian/bus/OBCustodianDoc.java,v 1.27 2005/08/09 04:39:37 whuang Exp $
 */
package com.integrosys.cms.app.custodian.bus;

//java
import java.util.ArrayList;
import java.util.Date;

import com.integrosys.base.techinfra.util.AccessorUtil;

/**
 * Implementation class for the ICustodianDoc interface
 * 
 * @author $Author: whuang $<br>
 * @version $Revision: 1.27 $
 * @since $Date: 2005/08/09 04:39:37 $ Tag: $Name: $
 */
public class OBCustodianDoc implements ICustodianDoc {
	private long custodianDocID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long limitProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long pledgorID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long subProfileID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String docType = null;

	private String docSubType = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date lastUpdateDate = null;

	private CCCustodianInfo ccCustodianInfo = null;

	private CollateralCustodianInfo collateralCustodianInfo = null;

	private ArrayList itemList = new ArrayList();

	private ArrayList updatedCheckListItemRefArrayList = null;

	// CR34
	private String reversalRemarks = null;

	private long revRemarksUpdatedBy = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long reversalID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String reversalRmkUpdatedUserInfo = null;

	/**
	 * Default Constructor
	 */
	public OBCustodianDoc() {
	}

	/**
	 * Getter methods
	 */
	public long getCustodianDocID() {
		return this.custodianDocID;
	}

	public long getLimitProfileID() {
		return this.limitProfileID;
	}

	public long getCheckListID() {
		return this.checkListID;
	}

	public long getPledgorID() {
		return this.pledgorID;
	}

	public long getSubProfileID() {
		return this.subProfileID;
	}

	public String getDocType() {
		return this.docType;
	}

	public String getDocSubType() {
		return this.docSubType;
	}

	public long getVersionTime() {
		return this.versionTime;
	}

	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public CCCustodianInfo getCCCustodianInfo() {
		return this.ccCustodianInfo;
	}

	public CollateralCustodianInfo getCollateralCustodianInfo() {
		return this.collateralCustodianInfo;
	}

	/**
	 * get certain custodian doc item
	 * @param index
	 * @return ICustodianDocItem
	 */
	public ICustodianDocItem getCustodianDocItem(int index) {
		if (this.itemList == null) {
			return null;
		}
		ICustodianDocItem item = (ICustodianDocItem) this.itemList.get(index);
		return item;
	}

	/**
	 * get custodian doc items
	 * @return ArrayList
	 */
	public ArrayList getCustodianDocItems() {
		return this.itemList;
	}

	/**
	 * Setter methods
	 */
	public void setCustodianDocID(long aCustodianDocID) {
		this.custodianDocID = aCustodianDocID;
	}

	public void setLimitProfileID(long aLimitProfileID) {
		this.limitProfileID = aLimitProfileID;
	}

	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	public void setPledgorID(long aPledgorID) {
		this.pledgorID = aPledgorID;
	}

	public void setSubProfileID(long aSubProfileID) {
		this.subProfileID = aSubProfileID;
	}

	public void setDocType(String aDocType) {
		this.docType = aDocType;
	}

	public void setDocSubType(String aDocSubType) {
		this.docSubType = aDocSubType;
	}

	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public void setCCCustodianInfo(CCCustodianInfo aCCCustodianInfo) {
		this.ccCustodianInfo = aCCCustodianInfo;
	}

	public void setCollateralCustodianInfo(CollateralCustodianInfo aCollateralCustodianInfo) {
		this.collateralCustodianInfo = aCollateralCustodianInfo;
	}

	/**
	 * set custodian doc item into arraylist
	 */
	public void addCustodianDocItem(ICustodianDocItem item) {
		if (this.itemList == null) {
			this.itemList = new ArrayList();
		}
		this.itemList.add(item);
	}

	/**
	 * Remove custodian doc item from the arraylist
	 */
	public void removeCustodianDocItem(ICustodianDocItem item) {
		if (this.itemList == null) {
			return;
		}
		this.itemList.remove(item);
	}

	/**
	 * Set list of custodian doc items. This list will replace any existing
	 * list.
	 */
	public void setCustodianDocItems(ArrayList items) {
		this.itemList = items;
	}

	/**
	 * Prints a String representation of this object
	 * 
	 * @return String
	 */
	public String toString() {
		return AccessorUtil.printMethodValue(this);
	}

	// TODO: remove these attributes and getter/setter methods after testing
	private long collateralID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	public long getCollateralID() {
		return this.collateralID;
	}

	public void setCollateralID(long aCollateralID) {
		this.collateralID = aCollateralID;
	}

	public ArrayList getUpdatedCheckListItemRefArrayList() {
		return updatedCheckListItemRefArrayList;
	}

	public void setUpdatedCheckListItemRefArrayList(ArrayList updatedCheckListItemRefArrayList) {
		this.updatedCheckListItemRefArrayList = updatedCheckListItemRefArrayList;
	}

	/**
	 * @return Returns the reversalID.
	 */
	public long getReversalID() {
		return reversalID;
	}

	/**
	 * @param reversalID The reversalID to set.
	 */
	public void setReversalID(long reversalID) {
		this.reversalID = reversalID;
	}

	/**
	 * @return Returns the reversalRemarks.
	 */
	public String getReversalRemarks() {
		return reversalRemarks;
	}

	/**
	 * @param reversalRemarks The reversalRemarks to set.
	 */
	public void setReversalRemarks(String reversalRemarks) {
		this.reversalRemarks = reversalRemarks;
	}

	/**
	 * @return Returns the revRemarksUpdatedBy.
	 */
	public long getRevRemarksUpdatedBy() {
		return revRemarksUpdatedBy;
	}

	/**
	 * @param revRemarksUpdatedBy The revRemarksUpdatedBy to set.
	 */
	public void setRevRemarksUpdatedBy(long revRemarksUpdatedBy) {
		this.revRemarksUpdatedBy = revRemarksUpdatedBy;
	}

	/**
	 * @return Returns the reversalRmkUpdatedUserInfo.
	 */
	public String getReversalRmkUpdatedUserInfo() {
		return reversalRmkUpdatedUserInfo;
	}

	/**
	 * @param reversalRmkUpdatedUserInfo The reversalRmkUpdatedUserInfo to set.
	 */
	public void setReversalRmkUpdatedUserInfo(String reversalRmkUpdatedUserInfo) {
		this.reversalRmkUpdatedUserInfo = reversalRmkUpdatedUserInfo;
	}

}