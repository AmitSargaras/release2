package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.common.constant.ICMSConstant;

public class OBShareDoc implements IShareDoc {

	private long docShareId = 0;

	private long docShareIdRef = 0;

	private long checkListItemID = ICMSConstant.LONG_INVALID_VALUE;

	private long checkListId;

	private String details = "";

	private String inValid;

	// private boolean isDeletedInd = false;
	private String itemStatus;

	private String status = "";

	// Used for C/C Checklist Only
	private String leID;

	private String leName;

	// Used for jsp page
	private String deleteCheckListId;

	private String existingChkListId;

	// Used for Security Receipt Only
	private long profileId;

	private long subProfileId;

	private long pledgorDtlId;

	private long collateralId;

	private String securityDtlId;

	private String securityType;

	private String securitySubType;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLeID() {
		return leID;
	}

	public void setLeID(String leID) {
		this.leID = leID;
	}

	public String getLeName() {
		return leName;
	}

	public void setLeName(String leName) {
		this.leName = leName;
	}

	public long getDocShareIdRef() {
		return docShareIdRef;
	}

	public void setDocShareIdRef(long docShareIdRef) {
		this.docShareIdRef = docShareIdRef;
	}

	public long getDocShareId() {
		return docShareId;
	}

	public void setDocShareId(long docShareId) {
		this.docShareId = docShareId;
	}

	public long getProfileId() {
		return profileId;
	}

	public void setProfileId(long profileId) {
		this.profileId = profileId;
	}

	public long getCheckListId() {
		return checkListId;
	}

	public void setCheckListId(long checkListId) {
		this.checkListId = checkListId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	/*
	 * public boolean getIsDeletedInd() { return this.isDeletedInd; }
	 * 
	 * public void setIsDeletedInd(boolean deletedInd) { isDeletedInd =
	 * deletedInd; }
	 */

	public String getInValid() {
		return inValid;
	}

	public void setInValid(String inValid) {
		this.inValid = inValid;
	}

	public long getCheckListItemID() {
		return checkListItemID;
	}

	public void setCheckListItemID(long checkListItemID) {
		this.checkListItemID = checkListItemID;
	}

	public String getItemStatus() {
		return itemStatus;
	}

	public void setItemStatus(String anItemStatus) {
		this.itemStatus = anItemStatus;
	}

	public String getDeleteCheckListId() {
		return deleteCheckListId;
	}

	public void setDeleteCheckListId(String deleteCheckListId) {
		this.deleteCheckListId = deleteCheckListId;
	}

	public String getExistingChkListId() {
		return existingChkListId;
	}

	public void setExistingChkListId(String existingChkListId) {
		this.existingChkListId = existingChkListId;
	}

	public long getSubProfileId() {
		return subProfileId;
	}

	public void setSubProfileId(long subProfileId) {
		this.subProfileId = subProfileId;
	}

	public long getPledgorDtlId() {
		return pledgorDtlId;
	}

	public void setPledgorDtlId(long pledgorDtlId) {
		this.pledgorDtlId = pledgorDtlId;
	}

	public long getCollateralId() {
		return collateralId;
	}

	public void setCollateralId(long collateralId) {
		this.collateralId = collateralId;
	}

	public String getSecurityDtlId() {
		return securityDtlId;
	}

	public void setSecurityDtlId(String securityDtlId) {
		this.securityDtlId = securityDtlId;
	}

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

	/*
	 * public void setLeNameFromDB() { try { ICheckListProxyManager proxy =
	 * CheckListProxyManagerFactory.getCheckListProxyManager(); OBShareDoc
	 * oBShareDoc = proxy.getLeName(this.profileId);
	 * this.setLeID(oBShareDoc.getLeID());
	 * this.setLeName(oBShareDoc.getLeName()); } catch (Exception e) {
	 * DefaultLogger.debug(this, "got exception in getLeNameFromDB" + e);
	 * e.printStackTrace(); } }
	 */

	/*
	 * public void setSecuritySubTypeDetailsFromDB() { try {
	 * ICheckListProxyManager proxy =
	 * CheckListProxyManagerFactory.getCheckListProxyManager(); OBShareDoc
	 * oBShareDoc = proxy.getSecuritySubDetails(this.profileId,
	 * this.collateralId);
	 * //System.out.println("DETAILS  getSecuritySubDetails  = " +
	 * oBShareDoc.toString());
	 * this.setSecurityDtlId(oBShareDoc.getSecurityDtlId());
	 * this.setSecurityType(oBShareDoc.getSecurityType());
	 * this.setSecuritySubType(oBShareDoc.getSecuritySubType()); } catch
	 * (Exception e) { DefaultLogger.debug(this,
	 * "got exception in getSecuritySubDetailsFromDB" + e); e.printStackTrace();
	 * } }
	 */

	public String toString() {
		return "OBShareDoc{" + "docShareId=" + docShareId + ", docShareIdRef=" + docShareIdRef + ", checkListItemID="
				+ checkListItemID + ", checkListId=" + checkListId + ", itemStatus='" + itemStatus + "'"
				+ ", details='" + details + "'"
				+ ", inValid='"
				+ inValid
				+ "'"
				+
				// ", isDeletedInd=" + isDeletedInd +
				", profileId=" + profileId + ", leID='" + leID + "'" + ", leName='" + leName + "'"
				+ ", deleteCheckListId='" + deleteCheckListId + "'" + ", existingChkListId='" + existingChkListId + "'"
				+ ", subProfileId=" + subProfileId + ", pledgorDtlId=" + pledgorDtlId + ", collateralId="
				+ collateralId + ", securityDtlId='" + securityDtlId + "'" + ", securityType='" + securityType + "'"
				+ ", securitySubType='" + securitySubType + "'" + "}";
	}

}
