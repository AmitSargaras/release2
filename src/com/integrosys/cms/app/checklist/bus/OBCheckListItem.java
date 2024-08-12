/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBCheckListItem.java,v 1.25 2006/06/21 10:53:43 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.util.Date;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.ICheckListItemImageDetail;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.ITemplateItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This is the implementation class for the ICheckListItem
 * 
 * @author $Author: Abhijit Rudrakshawar $<br>
 * @version $Revision: 1.25 $
 * @since $Date: 2006/06/21 10:53:43 $ Tag: $Name: $
 * 
 * For Lad Module : 
 * Date Configuration :
 * 
 *  IdentifyDate: Lad Generation Date 
 *  CompletedDate: Lad Due Date
 *  ExpiryDate: Lad Expiry Date
 *  DocDate: Lad Doc Date
 *  ReceivedDate: Lad Receive Date
 *  
 *  ----------
 *  DeferExpiryDate: Lad Defer Date
 *  ExpectedReturnDate: Lad Next Due Date
 * 
 * 
 * 
 */
public class OBCheckListItem implements ICheckListItem, Comparable {

	private static final long serialVersionUID = 4183290349925316088L;

	private static final String ITEM_STATUS_DISPLAY_KEY = "chklist.item.status.";

	private long checkListItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private long checkListItemRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date expiryDate = null;

	private boolean isLockedInd = false;

	private boolean isMandatoryInd = false;
	
	private boolean isMandatoryDisplayInd = false;

	private boolean isInVaultInd = false;

	private boolean isExtCustInd = false;

	private boolean isAuditInd = false;

	private boolean isDeletedInd = false;

	private boolean isApprovedInd = true;

	private long parentItemID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String itemStatus = ICMSConstant.STATE_ITEM_AWAITING;

	private String custodianDocStatus = null;

	private Date custodianDocItemTrxDate;

	private String actionParty = null;
	
	private String creditApprover = null;

	private String remarks = null;
	
	private String docJust = null;

	private String docRef = null;

	private String formNo = null;

	private Date docDate = null;

	private IItem item = null;

	private Date deferExpiryDate = null;

	private Date deferExtendedDate = null;

	private Date identifyDate = null;

	private Date docCompletionDate = null;

	private Date lastUpdateDate = null;

	private String cPCCustodianStatus = null;

	private Date cPCCustodianStatusUpdateDate = null;

	private Date cPCCustodianStatusLastUpdateDate = null;

	private long parentCheckListItemRef = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private Date effectiveDate;

	private boolean inherited = false;

	private boolean viewable = false;

	private boolean shared = false;

	private IShareDoc[] IShareDocList = null;

	private boolean isPreApprove;

	private Date receivedDate = null;

	private Date completedDate = null;

	private Date expectedReturnDate = null;

	private Date waivedDate = null;

	private Date lodgedDate = null;

	private Date tempUpliftedDate = null;

	private Date permUpliftedDate = null;
	
	private int tenureCount=0;
	
	private String tenureType="";
	
	private String deferCount="";
	
	private String deferedDays="";
	
	private String currency="";
	private String statementType="";
	private String docAmt="";
	private String hdfcAmt="";
	private String cpsId;
	/*//Added by Pramod for LAD CR
	private String isDisplay="";	
	
	
	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}
//End by Pramod
*/	/**
	 * Default Constructor
	 */
	
	//Added for image tag untag with facility receipt
		private String facImageTagUntagId = "";
		
		private String facImageTagUntagImgName = "";
		
		private String facImageTagUntagStatus = "";
		
//End:Added for image tag untag with facility receipt
	
	
	
//Added for image tag untag with security receipt
	
	private String secImageTagUntagId = "";
	
	private String secImageTagUntagImgName = "";
			
	private String secImageTagUntagStatus = "";
	
	//End:Added for image tag untag with security receipt	
	
	private ICheckListItemImageDetail[] checkListItemImageDetail;
	
	private String rocCharge = "";
	
	private String referenceType;
	
	private String flagSchedulerCheckItem = "";
	
	public OBCheckListItem() {
	}

	/**
	 * Constructor
	 * @param anITemplateItem - ITemplateItem
	 */
	public OBCheckListItem(ITemplateItem anITemplateItem) {
		setIsMandatoryInd(anITemplateItem.getIsMandatoryInd());
		setIsMandatoryDisplayInd(anITemplateItem.getIsMandatoryDisplayInd());
		setTenureCount(anITemplateItem.getTenureCount());
		setTenureType(anITemplateItem.getTenureType());
		//setDeferCount(anITemplateItem.getDeferCount());
		setIsInVaultInd(anITemplateItem.getIsInVaultInd());
		setIsExtCustInd(anITemplateItem.getIsExtCustInd());
		setIsAuditInd(anITemplateItem.getIsAuditInd());
		setItem(anITemplateItem.getItem());
		setParentItemID(anITemplateItem.getTemplateItemID());
	}

	
	public String getFlagSchedulerCheckItem() {
		return flagSchedulerCheckItem;
	}

	public void setFlagSchedulerCheckItem(String flagSchedulerCheckItem) {
		this.flagSchedulerCheckItem = flagSchedulerCheckItem;
	}
	
	public String getFacImageTagUntagId() {
		return facImageTagUntagId;
	}

	public void setFacImageTagUntagId(String facImageTagUntagId) {
		this.facImageTagUntagId = facImageTagUntagId;
	}

	public String getFacImageTagUntagImgName() {
		return facImageTagUntagImgName;
	}

	public void setFacImageTagUntagImgName(String facImageTagUntagImgName) {
		this.facImageTagUntagImgName = facImageTagUntagImgName;
	}

	public String getFacImageTagUntagStatus() {
		return facImageTagUntagStatus;
	}

	public void setFacImageTagUntagStatus(String facImageTagUntagStatus) {
		this.facImageTagUntagStatus = facImageTagUntagStatus;
	}

	
	
	 public String getSecImageTagUntagId() {
			return secImageTagUntagId;
		}

		public void setSecImageTagUntagId(String secImageTagUntagId) {
			this.secImageTagUntagId = secImageTagUntagId;
		}

		public String getSecImageTagUntagImgName() {
			return secImageTagUntagImgName;
		}

		public void setSecImageTagUntagImgName(String secImageTagUntagImgName) {
			this.secImageTagUntagImgName = secImageTagUntagImgName;
		}

		public String getSecImageTagUntagStatus() {
			return secImageTagUntagStatus;
		}

		public void setSecImageTagUntagStatus(String secImageTagUntagStatus) {
			this.secImageTagUntagStatus = secImageTagUntagStatus;
		}
	
	
	
	
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStatementType() {
		return statementType;
	}

	public void setStatementType(String statementType) {
		this.statementType = statementType;
	}

	public String getDocAmt() {
		return docAmt;
	}

	public void setDocAmt(String docAmt) {
		this.docAmt = docAmt;
	}

	public String getHdfcAmt() {
		return hdfcAmt;
	}

	public void setHdfcAmt(String hdfcAmt) {
		this.hdfcAmt = hdfcAmt;
	}

	/**
	 * Get the action party
	 * @return String - the action party
	 */
	public String getActionParty() {
		return this.actionParty;
	}

	/**
	 * Get the checklist item ID
	 * @return long - the checklist item ID
	 */
	public long getCheckListItemID() {
		return this.checkListItemID;
	}

	public int getTenureCount() {
		return tenureCount;
	}

	public void setTenureCount(int tenureCount) {
		this.tenureCount = tenureCount;
	}

	public String getTenureType() {
		return tenureType;
	}

	public void setTenureType(String tenureType) {
		this.tenureType = tenureType;
	}

	public String getDeferedDays() {
		return deferedDays;
	}

	public void setDeferedDays(String deferedDays) {
		this.deferedDays = deferedDays;
	}

	public String getDeferCount() {
		return deferCount;
	}

	public void setDeferCount(String deferCount) {
		this.deferCount = deferCount;
	}

	/**
	 * Get the checklist item reference
	 * @return long - the checklist item reference
	 */
	public long getCheckListItemRef() {
		return this.checkListItemRef;
	}

	public Date getCompletedDate() {
		return completedDate;
	}

	/**
	 * Get the CPC Custodian Status
	 * @return String - the CPC Custodian Status
	 */
	public String getCPCCustodianStatus() {
		return this.cPCCustodianStatus;
	}

	/**
	 * @return Returns the cPCCustodianStatusLastUpdateDate.
	 */
	public Date getCPCCustodianStatusLastUpdateDate() {
		return cPCCustodianStatusLastUpdateDate;
	}

	/**
	 * Get the date on which CPC Custodian Status was updated
	 * @return String - the CPC Custodian Status
	 */
	public Date getCPCCustodianStatusUpdateDate() {
		return this.cPCCustodianStatusUpdateDate;
	}

	/**
	 * Get the custodian doc item transaction date
	 * @return Date - the custodian doc item trx date
	 */
	public Date getCustodianDocItemTrxDate() {
		return this.custodianDocItemTrxDate;
	}

	/**
	 * Get the custodian doc status
	 * @return String - the custodian doc status
	 */
	public String getCustodianDocStatus() {
		return this.custodianDocStatus;
	}

	/**
	 * Expiry for the defer item
	 * @return Date - the defer item expiry date
	 */
	public Date getDeferExpiryDate() {
		return this.deferExpiryDate;
	}

	// cr 36
	/**
	 * Extended date for the defer item
	 * @return Date - the defer item extended date
	 */
	public Date getDeferExtendedDate() {
		return this.deferExtendedDate;
	}

	/**
	 * Get display date for custodian trx date
	 * @return Date
	 */
	public Date getDisplayCustodianTrxDate() {
		return CheckListCustodianHelper.getCPCCustodianTrxDate(this);
	}

	/**
	 * Get document completion date
	 * @return Date - the document completion date
	 */
	public Date getDocCompletionDate() {
		return this.docCompletionDate;
	}

	/**
	 * Get the document date
	 * @return Date - the document date
	 */
	public Date getDocDate() {
		return this.docDate;
	}

	/**
	 * Get the document ref
	 * 
	 */
	public String getDocRef() {
		return this.docRef;
	}

	/**
	 * Get insurance document effective date.
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public Date getExpectedReturnDate() {
		return expectedReturnDate;
	}

	/**
	 * Get the expiry date
	 * @return Date - the expiry date
	 */
	public Date getExpiryDate() {
		return this.expiryDate;
	}

	/**
	 * Get the form no
	 * 
	 */
	public String getFormNo() {
		return this.formNo;
	}

	/**
	 * Get the identify date
	 * @return Date - the identify date
	 */
	public Date getIdentifyDate() {
		return this.identifyDate;
	}

	/**
	 * Get the approved indicator
	 * @return boolean - true if it is approved and false otherwise
	 */
	public boolean getIsApprovedInd() {
		return this.isApprovedInd;
	}

	/**
	 * Get the audit indicator
	 * @return boolean - true if it is to be audited and false otherwise
	 */
	public boolean getIsAuditInd() {
		return this.isAuditInd;
	}

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		return this.isDeletedInd;
	}

	/**
	 * Get the external custodian indicator
	 * @return boolean - true if it is external custodian and false otherwise
	 */
	public boolean getIsExtCustInd() {
		return this.isExtCustInd;
	}

	/**
	 * Helper method to check if the checklist item is inherited or not
	 * @return boolean - true if inherited and false otherwise
	 */
	public boolean getIsInherited() {
		return inherited;
	}

	/**
	 * Get the in vault indicator
	 * @return boolean - true if it is to be vaulted and false otherwise
	 */
	public boolean getIsInVaultInd() {
		return this.isInVaultInd;
	}

	/**
	 * Get the lock indicator
	 * @return boolean - true if it is locked and false otherwise
	 */
	public boolean getIsLockedInd() {
		return this.isLockedInd;
	}

	/**
	 * Get the mandatory indicator
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryInd() {
		return this.isMandatoryInd;
	}

	// Link with LOS
	public boolean getIsPreApprove() {
		return isPreApprove;
	}

	/**
	 * Get the item related to this checklist
	 * @return IItem - a item related to the checklist
	 */
	public IItem getItem() {
		return this.item;
	}

	/**
	 * Helper method to get the item code
	 * @return String - the item code
	 */
	public String getItemCode() {
		if (getItem() != null) {
			return getItem().getItemCode();
		}
		return null;
	}
	
	

	/**
	 * Get the checklist item description
	 * @return String - the checklist item description
	 */
	public String getItemDesc() {
		if (getItem() != null) {
			return getItem().getItemDesc();
		}
		return null;
	}

	public String getItemDisplayStatus() {
		String displayStatus = PropertyManager.getValue(ITEM_STATUS_DISPLAY_KEY + getItemStatus());
		return (displayStatus == null) ? getItemStatus() : displayStatus;
	}

	/**
	 * Get the item status
	 * @return String - the item status
	 */
	public String getItemStatus() {
		return this.itemStatus;
	}

	/**
	 * Get the last update date
	 * @return Date - the last update date
	 */
	public Date getLastUpdateDate() {
		return this.lastUpdateDate;
	}

	public Date getLodgedDate() {
		return lodgedDate;
	}

	/**
	 * Get the Parent checklist item reference
	 * 
	 */
	public long getParentCheckListItemRef() {
		return this.parentCheckListItemRef;
	}

	/**
	 * Get the parent item ID
	 * @return long - the parent item ID
	 */
	public long getParentItemID() {
		return this.parentItemID;
	}

	public Date getPermUpliftedDate() {
		return permUpliftedDate;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	/**
	 * Get the checklist item remarks
	 * @return String - the checklist item remarks
	 */
	public String getRemarks() {
		return this.remarks;
	}

	// R1.5 CR17
	public IShareDoc[] getShareCheckList() {
		return this.IShareDocList;
	}

	public boolean getShared() {
		return shared;
	}

	public Date getTempUpliftedDate() {
		return tempUpliftedDate;
	}

	public boolean getViewable() {
		return viewable;
	}

	public Date getWaivedDate() {
		return waivedDate;
	}

	/**
	 * Helper method to check if the checklist item can be deleted or not
	 * @return boolean - true if it can be deleted and false otherwise
	 */
	public boolean isDeletable() {
		if ((!getIsMandatoryInd()) && (!isInCustodian()) && (ICMSConstant.STATE_ITEM_AWAITING.equals(getItemStatus()))) {
			return true;
		}
		return false;
	}

	public boolean isEditable() {
		if (ICMSConstant.STATE_ITEM_COMPLETED.equals(getItemStatus())
				|| ICMSConstant.STATE_ITEM_EXPIRED.equals(getItemStatus())
				|| ICMSConstant.STATE_ITEM_RENEWED.equals(getItemStatus())
				|| (getIsInVaultInd() && (getCustodianDocStatus() != null))) {
			return false;
		}
		return true;
	}

	/**
	 * Helper method to check if hard delete is allowed
	 * @return boolean - true if hard deleted is allowed and false otherwise
	 */
	public boolean isHardDeleteAllowed() {
		String status = getItemStatus();
		if ((status == null) || (status.trim().length() == 0) || (status.equals(ICMSConstant.STATE_ITEM_AWAITING))) {
			return true;
		}
		return false;
	}

	public boolean isInCustodian() {
		if (getIsInVaultInd() && (getCustodianDocStatus() != null)
				&& !ICMSConstant.STATE_PERM_UPLIFTED.equals(getCustodianDocStatus())) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to check if soft delete is allowed
	 * @return boolean - true if soft deleted is allowed and false otherwise
	 */
	public boolean isSoftDeleteAllowed() {
		String status = getItemStatus();
		if ((status == null) || (status.trim().length() == 0)) {
			return false;
		}
		if ((status.equals(ICMSConstant.STATE_ITEM_COMPLETED)) || (status.equals(ICMSConstant.STATE_ITEM_EXPIRED))) {
			return true;
		}
		return false;
	}

	/**
	 * Set the action party
	 * @param anActionParty of type String
	 */
	public void setActionParty(String anActionParty) {
		this.actionParty = anActionParty;
	}

	/**
	 * Set the checklist item description
	 * @param aCheckListItemDesc of String type
	 */
	public void setCheckListItemDesc(String aCheckListItemDesc) {
		if (getItem() != null) {
			IItem item = getItem();
			item.setItemDesc(aCheckListItemDesc);
			setItem(item);
			return;
		}

		IItem item = new OBItem();
		item.setItemDesc(aCheckListItemDesc);
		setItem(item);
	}

	/**
	 * Set the checklist item ID
	 * @param aCheckListItemID of long type
	 */
	public void setCheckListItemID(long aCheckListItemID) {
		this.checkListItemID = aCheckListItemID;
	}

	/**
	 * Set the checklist item reference
	 * @param aCheckListItemRef of long type
	 */
	public void setCheckListItemRef(long aCheckListItemRef) {
		this.checkListItemRef = aCheckListItemRef;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	/**
	 * Set the CPC Custodian Status
	 * @param aCPCCustodianStatus of String type
	 */
	public void setCPCCustodianStatus(String aCPCCustodianStatus) {
		this.cPCCustodianStatus = aCPCCustodianStatus;
	}

	/**
	 * @param custodianStatusLastUpdateDate The cPCCustodianStatusLastUpdateDate
	 *        to set.
	 */
	public void setCPCCustodianStatusLastUpdateDate(Date custodianStatusLastUpdateDate) {
		cPCCustodianStatusLastUpdateDate = custodianStatusLastUpdateDate;
	}

	/**
	 * Set the date on which CPC Custodian Status was updated
	 * @param anUpdateDate
	 */
	public void setCPCCustodianStatusUpdateDate(Date anUpdateDate) {
		this.cPCCustodianStatusUpdateDate = anUpdateDate;
	}

	/**
	 * Set the custodian doc item transaction date
	 * @param aCustodianDocItemTrxDate of Date type
	 */
	public void setCustodianDocItemTrxDate(Date aCustodianDocItemTrxDate) {
		this.custodianDocItemTrxDate = aCustodianDocItemTrxDate;
	}

	/**
	 * Set the custodian document status
	 * @param aCustodianDocStatus of String type
	 */
	public void setCustodianDocStatus(String aCustodianDocStatus) {
		this.custodianDocStatus = aCustodianDocStatus;
	}

	/**
	 * Set the defer expiry date
	 * @param aDeferExpiryDate of Date type
	 */
	public void setDeferExpiryDate(Date aDeferExpiryDate) {
		this.deferExpiryDate = aDeferExpiryDate;
	}

	// cr 36
	/**
	 * Set the defer extended date
	 * @param aDeferExtendedDate of Date type
	 */
	public void setDeferExtendedDate(Date aDeferExtendedDate) {
		this.deferExtendedDate = aDeferExtendedDate;
	}

	/**
	 * Set the document completion date
	 * @param aDocCompletionDate of Date type
	 */
	public void setDocCompletionDate(Date aDocCompletionDate) {
		this.docCompletionDate = aDocCompletionDate;
	}

	/**
	 * Set the document date
	 * @param aDocDate of Date type
	 */
	public void setDocDate(Date aDocDate) {
		this.docDate = aDocDate;
	}

	/**
	 * Set the document reference
	 * @param aDocRef of String type
	 */
	public void setDocRef(String aDocRef) {
		this.docRef = aDocRef;
	}

	/**
	 * Set insurance document effective date.
	 * 
	 * @param effectiveDate of type Date
	 */
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public void setExpectedReturnDate(Date expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	/**
	 * Set the expiry date
	 * @param anExpiryDate of Date type
	 */
	public void setExpiryDate(Date anExpiryDate) {
		this.expiryDate = anExpiryDate;
	}

	/**
	 * Set the form number
	 * @param aFormNo of String type
	 */
	public void setFormNo(String aFormNo) {
		this.formNo = aFormNo;
	}

	/**
	 * Set the identify date
	 * @param anIdentifyDate of Date type
	 */
	public void setIdentifyDate(Date anIdentifyDate) {
		this.identifyDate = anIdentifyDate;
	}

	/**
	 * Set the approved indicator
	 * @param anIsApprovedInd of boolean type
	 */
	public void setIsApprovedInd(boolean anIsApprovedInd) {
		this.isApprovedInd = anIsApprovedInd;
	}

	/**
	 * Set the audit indicator
	 * @param anIsAuditInd of boolean type
	 */
	public void setIsAuditInd(boolean anIsAuditInd) {
		this.isAuditInd = anIsAuditInd;
	}

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		this.isDeletedInd = anIsDeletedInd;
	}

	/**
	 * Set the external custodian indicator
	 * @param anIsExtCustInd of boolean type
	 */
	public void setIsExtCustInd(boolean anIsExtCustInd) {
		this.isExtCustInd = anIsExtCustInd;
	}

	// cz: newly added - when inherited from template, always set to true
	public void setIsInherited(boolean inherited) {
		this.inherited = inherited;
	}

	/**
	 * Set the in vault indicator
	 * @param anIsInVaultInd of boolean type
	 */
	public void setIsInVaultInd(boolean anIsInVaultInd) {
		this.isInVaultInd = anIsInVaultInd;
	}

	/**
	 * Set the lock indicator param anIsLockedInd of boolean type
	 */
	public void setIsLockedInd(boolean anIsLockedInd) {
		this.isLockedInd = anIsLockedInd;
	}

	/**
	 * Set the mandatory indicator
	 * 
	 */
	public void setIsMandatoryInd(boolean anIsMandatoryInd) {
		this.isMandatoryInd = anIsMandatoryInd;
	}

	public void setIsPreApprove(boolean flag) {
		isPreApprove = flag;
	}

	/**
	 * Set the checklist item. The checklist item must be one of the items
	 * created through the global template
	 * @param anIItem of IItem type
	 */
	public void setItem(IItem anIItem) {
		this.item = anIItem;
	}

	/**
	 * Set the item code
	 * @param itemCode of String type
	 */
	public void setItemCode(String itemCode) {
		if (getItem() == null)
			item = new OBItem();
		getItem().setItemCode(itemCode);
	}

	/**
	 * Set the item code
	 * @param itemDesc of String type
	 */
	public void setItemDesc(String itemDesc) {
		if (getItem() == null)
			item = new OBItem();
		getItem().setItemDesc(itemDesc);
	}

	/**
	 * Set the item status
	 * @param anItemStatus of String type
	 */
	public void setItemStatus(String anItemStatus) {
		this.itemStatus = anItemStatus;
	}

	/**
	 * Set the last update date
	 * @param aLastUpdateDate of Date type
	 */
	public void setLastUpdateDate(Date aLastUpdateDate) {
		this.lastUpdateDate = aLastUpdateDate;
	}

	public void setLodgedDate(Date lodgedDate) {
		this.lodgedDate = lodgedDate;
	}

	/**
	 * Set the parent checklist item reference
	 * @param aParentCheckListItemRef of String type
	 */
	public void setParentCheckListItemRef(long aParentCheckListItemRef) {
		this.parentCheckListItemRef = aParentCheckListItemRef;
	}

	/**
	 * Set the parent item ID
	 * @param aParentItemID of long type
	 */
	public void setParentItemID(long aParentItemID) {
		this.parentItemID = aParentItemID;
	}

	public void setPermUpliftedDate(Date permUpliftedDate) {
		this.permUpliftedDate = permUpliftedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * Set the checklist remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	public void setShareCheckList(IShareDoc[] list) {
		this.IShareDocList = list;
	}

	public void setShared(boolean shareStatus) {
		this.shared = shareStatus;
	}

	public void setTempUpliftedDate(Date tempUpliftedDate) {
		this.tempUpliftedDate = tempUpliftedDate;
	}

	public void setViewable(boolean view) {
		this.viewable = view;
	}

	public void setWaivedDate(Date waivedDate) {
		this.waivedDate = waivedDate;
	}

	public String getCreditApprover() {
		return creditApprover;
	}

	public void setCreditApprover(String creditApprover) {
		this.creditApprover = creditApprover;
	}
	public boolean getIsMandatoryDisplayInd() {
		return isMandatoryDisplayInd;
	}

	public void setIsMandatoryDisplayInd(boolean isMandatoryDisplayInd) {
		this.isMandatoryDisplayInd = isMandatoryDisplayInd;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBCheckListItem [");
		buf.append("checkListItemID=");
		buf.append(checkListItemID);
		buf.append(", checkListItemRef=");
		buf.append(checkListItemRef);
		buf.append(", docDate=");
		buf.append(docDate);
		buf.append(", completedDate=");
		buf.append(completedDate);
		buf.append(", deferExpiryDate=");
		buf.append(deferExpiryDate);
		buf.append(", deferExtendedDate=");
		buf.append(deferExtendedDate);
		buf.append(", docCompletionDate=");
		buf.append(docCompletionDate);
		buf.append(", receivedDate=");
		buf.append(receivedDate);
		buf.append(", waivedDate=");
		buf.append(waivedDate);
		buf.append(", expiryDate=");
		buf.append(expiryDate);
		buf.append(", docRef=");
		buf.append(docRef);
		buf.append(", formNo=");
		buf.append(formNo);
		buf.append(", isInVaultInd=");
		buf.append(isInVaultInd);
		buf.append(", isLockedInd=");
		buf.append(isLockedInd);
		buf.append(", isMandatoryInd=");
		buf.append(isMandatoryInd);
		buf.append(", isPreApprove=");
		buf.append(isPreApprove);
		buf.append(", item=");
		buf.append(item);
		buf.append(", itemStatus=");
		buf.append(itemStatus);
		buf.append(", parentItemID=");
		buf.append(parentItemID);
		buf.append(", remarks=");
		buf.append(remarks);
		buf.append("]");
		return buf.toString();
	}

	/**
	 * Default comparison by item code.
	 */
	public int compareTo(Object other) {
		OBCheckListItem otherCheckListItem = (OBCheckListItem) other;
		if (this.getItem() != null && otherCheckListItem.getItem() == null) {
			return -1;
		}
		if (this.getItem() == null && otherCheckListItem.getItem() != null) {
			return 1;
		}
		return ((OBItem) this.getItem()).compareTo((OBItem) otherCheckListItem.getItem());
	}
/*
	public int getTenureCount() {
		if (getItem() != null) {
			return getItem().getTenureCount();
		}
		return 0;
	}

	public String getTenureType() {
		if (getItem() != null) {
			return getItem().getTenureType();
		}
		return null;
	}

	
	public void setTenureCount(int tenureCount) {
		if (getItem() == null)
			item = new OBItem();
		getItem().setTenureCount(tenureCount);
		
	}

	
	public void setTenureType(String tenureType) {
		if (getItem() == null)
			item = new OBItem();
		getItem().setTenureType(tenureType);
		
	}*/
	
	
	

	/*
	 * New fields added for CR-Document Tracking dated : 16 April 2013 by Abhijit R
	 * 
	 */
	
	private Date updatedDate = null;
	private Date approvedDate = null;
	private Date originalTargetDate = null;
	private String documentVersion="0";
	private String documentStatus="ACTIVE";
	private String updatedBy="";
	private String approvedBy="";
	

	
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public Date getOriginalTargetDate() {
		return originalTargetDate;
	}

	public void setOriginalTargetDate(Date originalTargetDate) {
		this.originalTargetDate = originalTargetDate;
	}

	public String getDocumentVersion() {
		return documentVersion;
	}

	public void setDocumentVersion(String documentVersion) {
		this.documentVersion = documentVersion;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getCpsId() {
		return cpsId;
	}

	public void setCpsId(String cpsId) {
		this.cpsId = cpsId;
	}

	 //Uma Khot::Insurance Deferral maintainance
	
	private String insuranceId;
	
	public void setInsuranceId(String insuranceId) {
		this.insuranceId=insuranceId;
		
	}
	
	public String getInsuranceId() {
		return insuranceId;
	}

	public ICheckListItemImageDetail[] getCheckListItemImageDetail() {
		return checkListItemImageDetail;
	}

	public void setCheckListItemImageDetail(ICheckListItemImageDetail[] checkListItemImageDetail) {
		this.checkListItemImageDetail = checkListItemImageDetail;
	}

	public String getRocCharge() {
		return rocCharge;
	}

	public void setRocCharge(String rocCharge) {
		this.rocCharge = rocCharge;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getDocJust() {
		return docJust;
	}

	public void setDocJust(String docJust) {
		this.docJust = docJust;
	}
	
}
