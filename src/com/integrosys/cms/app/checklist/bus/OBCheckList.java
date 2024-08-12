/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/OBCheckList.java,v 1.15 2006/06/09 09:43:30 lini Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.util.Arrays;
import java.util.Date;

import com.integrosys.cms.app.common.bus.IBookingLocation;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.chktemplate.bus.IItem;

/**
 * This class implements the ICheckList
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/06/09 09:43:30 $ Tag: $Name: $
 */
public class OBCheckList implements ICheckList {

	private static final long serialVersionUID = -7446681057412037896L;

	private long checkListID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String checkListType = null;

	private ICheckListOwner checkListOwner = null;

	private long templateID = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String checkListStatus = ICMSConstant.STATE_CHECKLIST_NEW;

	private Date lastDocReceivedDate = null;

	private String remarks = null;

	private IBookingLocation checkListLocation = null;

	private boolean allowDeleteInd = false;

	private ICheckListItem[] checkListItemList = null;

	private long versionTime = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;

	private String reversalRemarks;

	private String reversalRmkUpdatedUserInfo;

	private Date deficiencyDate;

	private Date nextActionDate;

	private String isObsolete = ICMSConstant.FALSE_VALUE;

	private String applicationType = null;

	private String legalFirm = null;

	private String lawFirmPanelFlag = null;

	private String lawyerReferenceNumber = null;

	private String lawyerInCharge = null;

	private String lawFirmAddress = null;

	private String lawFirmContactNumber = null;

	private String lawyerEmail = null;
	
	private String valuerFirm = null;

	private String valuerFirmPanelFlag = null;

	private String valuerReferenceNumber = null;

	private String valuerInCharge = null;

	private String valuerFirmAddress = null;

	private String valuerFirmContactNumber = null;

	private String valuerEmail = null;
	
	private String insurerFirm = null;

	private String insurerFirmPanelFlag = null;

	private String insurerReferenceNumber = null;

	private String insurerInCharge = null;

	private String insurerFirmAddress = null;

	private String insurerFirmContactNumber = null;

	private String insurerEmail = null;
	
	private String isDisplay = null;
	
	private Date updatedDate = null;
	private Date approvedDate = null;
	private String updatedBy="";
	private String approvedBy="";
	
	private String flagSchedulersCheck="";
	
	public String getFlagSchedulersCheck() {
		return flagSchedulersCheck;
	}

	public void setFlagSchedulersCheck(String flagSchedulersCheck) {
		this.flagSchedulersCheck = flagSchedulersCheck;
	}

	public String getIsDisplay() {
		return isDisplay;
	}

	public void setIsDisplay(String isDisplay) {
		this.isDisplay = isDisplay;
	}

	/**
	 * Default Constructor
	 */
	public OBCheckList() {
	}

	/**
	 * Constructor
	 * @param anICheckListOwner of type ICheckListOwner
	 */
	public OBCheckList(ICheckListOwner anICheckListOwner) {
		setCheckListOwner(anICheckListOwner);
	}

	/**
	 * Get the check list ID
	 * @return long - the checklist ID
	 */
	public long getCheckListID() {
		return this.checkListID;
	}

	/**
	 * Get the check list type
	 * @return String - the checklist type
	 */
	public String getCheckListType() {
		return this.checkListType;
	}

	/**
	 * Get the check list owner
	 * @return ICheckListOwner - the checklist owner
	 */
	public ICheckListOwner getCheckListOwner() {
		return this.checkListOwner;
	}

	/**
	 * Get the template ID
	 * @return long - the template ID
	 */
	public long getTemplateID() {
		return this.templateID;
	}

	/**
	 * Get the status of the checklistf
	 * @return String - the checklist status
	 */
	public String getCheckListStatus() {
		return this.checkListStatus;
	}

	/**
	 * Get the last document received date
	 * @return Date - the last document received date
	 */
	public Date getLastDocReceivedDate() {
		return this.lastDocReceivedDate;
	}

	/**
	 * Get the legal firm
	 * @return String - the legal firm handling the checklist
	 */
	public String getRemarks() {
		return this.remarks;
	}

	/**
	 * Get the checklist location
	 * @return IBookingLocation - the checklist originating location
	 */
	public IBookingLocation getCheckListLocation() {
		return this.checkListLocation;
	}

	/**
	 * Get the allow delete indicator
	 * @return boolean - true of deletion is allowed on the checklist
	 */
	public boolean getAllowDeleteInd() {
		return this.allowDeleteInd;
	}

	/**
	 * Get the list of checklist items
	 * @return ICheckListItem[] - the list of checklist items
	 */
	public ICheckListItem[] getCheckListItemList() {
		return this.checkListItemList;
	}

	/**
	 * Get the version time
	 * @return long - the version time
	 */
	public long getVersionTime() {
		return this.versionTime;
	}

	/**
	 * Get deficiency identified date
	 * @return Date
	 */
	public Date getDeficiencyDate() {
		return this.deficiencyDate;
	}

	/**
	 * Get next action date
	 * @return Date
	 */
	public Date getNextActionDate() {
		return this.nextActionDate;
	}

	/**
	 * Set the checklist ID
	 * @param aCheckListID of long type
	 */
	public void setCheckListID(long aCheckListID) {
		this.checkListID = aCheckListID;
	}

	/**
	 * Set the checklist type
	 * @param aCheckListType of String type
	 */
	public void setCheckListType(String aCheckListType) {
		this.checkListType = aCheckListType;
	}

	/**
	 * Set the checklist owner
	 * @param aCheckListOwner of ICheckListOwner type
	 */
	public void setCheckListOwner(ICheckListOwner aCheckListOwner) {
		this.checkListOwner = aCheckListOwner;
	}

	/**
	 * Set the ID of the template that this checklist inherit from
	 * @param aTemplateID of long type
	 */
	public void setTemplateID(long aTemplateID) {
		this.templateID = aTemplateID;
	}

	/**
	 * Set the checklist status
	 * @param aCheckListStatus of String type
	 */
	public void setCheckListStatus(String aCheckListStatus) {
		this.checkListStatus = aCheckListStatus;
	}

	/**
	 * Set the last document received date
	 * @param aLastDocReceivedDate of Date type
	 */
	public void setLastDocReceivedDate(Date aLastDocReceivedDate) {
		this.lastDocReceivedDate = aLastDocReceivedDate;
	}

	/**
	 * Set the legal form
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks) {
		this.remarks = aRemarks;
	}

	private String disableCollaborationInd = null;

	public String getDisableCollaborationInd() {
		return this.disableCollaborationInd;
	}

	public void setDisableCollaborationInd(String adisableCollaborationInd) {
		this.disableCollaborationInd = adisableCollaborationInd;
	}

	/**
	 * Set the checklist originating location
	 * @param anIBookingLocation if IBookingLocation type
	 */
	public void setCheckListLocation(IBookingLocation anIBookingLocation) {
		this.checkListLocation = anIBookingLocation;
	}

	/**
	 * Set the allow deletion indicator
	 * @param anAllowDeleteInd of boolean type
	 */
	public void setAllowDeleteInd(boolean anAllowDeleteInd) {
		this.allowDeleteInd = anAllowDeleteInd;
	}

	/**
	 * Set the list of checklist items
	 * @param aCheckListItemList of ICheckListItem[] type
	 */
	public void setCheckListItemList(ICheckListItem[] aCheckListItemList) {
		this.checkListItemList = aCheckListItemList;
	}

	/**
	 * Set the version time
	 * @param aVersionTime of long type
	 */
	public void setVersionTime(long aVersionTime) {
		this.versionTime = aVersionTime;
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

	/**
	 * Set the deficiency identified date
	 * @param deficiencyDate of Date type
	 */
	public void setDeficiencyDate(Date deficiencyDate) {
		this.deficiencyDate = deficiencyDate;
	}

	/**
	 * Set the next action date
	 * @param nextActionDate of Date type
	 */
	public void setNextActionDate(Date nextActionDate) {
		this.nextActionDate = nextActionDate;
	}

	public String getObsolete() {
		return isObsolete;
	}

	public void setObsolete(String obsolete) {
		isObsolete = obsolete;
	}

	/**
	 * Add an list of items into the checklist
	 * @param anItemList - IItem[]
	 */
	public void addItems(IItem[] anItemList) {
		int numOfItems = 0;
		ICheckListItem[] itemList = getCheckListItemList();
		if (itemList != null) {
			numOfItems = itemList.length;
		}

		ICheckListItem[] newList = new OBCheckListItem[numOfItems + anItemList.length];
		ICheckListItem item = null;
		if (itemList != null) {
			for (int ii = 0; ii < itemList.length; ii++) {
				newList[ii] = itemList[ii];
			}
		}
		for (int ii = 0; ii < anItemList.length; ii++) {
			item = new OBCheckListItem();
			item.setItem(anItemList[ii]);
			newList[ii + numOfItems] = item;
		}
		setCheckListItemList(newList);
	}

	/**
	 * Remove a list of items into the checklist
	 * @param anItemIndexList int[] - the list of item index to be removed
	 */
	public void removeItems(int[] anItemIndexList) {
		ICheckListItem[] itemList = getCheckListItemList();
		ICheckListItem[] newList = new OBCheckListItem[itemList.length - anItemIndexList.length];
		int ctr = 0;
		boolean removeFlag = false;
		for (int ii = 0; ii < itemList.length; ii++) {
			for (int jj = 0; jj < anItemIndexList.length; jj++) {
				if (ii == anItemIndexList[jj]) {
					removeFlag = true;
					break;
				}
			}
			if (!removeFlag) {
				newList[ctr] = itemList[ii];
				ctr++;
			}
			removeFlag = false;
		}
		setCheckListItemList(newList);
	}

	/**
	 * Update an item in the template
	 * @param anItemIndex - int
	 * @param anItem - IItem
	 */
	public void updateItem(int anItemIndex, IItem anItem) {
		ICheckListItem[] itemList = getCheckListItemList();
		if (itemList != null) {
			if (anItemIndex < itemList.length) {
				itemList[anItemIndex].setItem(anItem);
				setCheckListItemList(itemList);
			}
		}
	}

	/**
	 * Gets the application type of the AA. E.g. CC - Credit Card, HP Hire
	 * Purchase.
	 * @return application type of AA
	 */
	public String getApplicationType() {
		return applicationType;
	}

	/**
	 * Sets the application type of the AA. E.g. CC - Credit Card, HP Hire
	 * Purchase.
	 * @param applicationType - Application Type
	 */
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	/**
	 * Get the legal firm
	 * @return String - the legal firm handling the checklist
	 */
	public String getLegalFirm() {
		return this.legalFirm;
	}

	/**
	 * Set the legal form
	 * @param aLegalFirm of String type
	 */
	public void setLegalFirm(String aLegalFirm) {
		this.legalFirm = aLegalFirm;
	}

	/**
	 * Get the flag indicating if the legal firm is a panel firm or non-panel
	 * firm.
	 * @see ICMSConstant (ICMSConstant.CHECKLIST_PANEL_LAWYER,
	 *      ICMSConstant.CHECKLIST_NON_PANEL_LAWYER)
	 * @return if the law firm is a panel firm or non-panel firm of String type
	 */
	public String getLawFirmPanelFlag() {
		return lawFirmPanelFlag;
	}

	/**
	 * Sets the flag indicating if the legal firm is a panel firm or non-panel
	 * firm
	 * @see ICMSConstant (ICMSConstant.CHECKLIST_PANEL_LAWYER,
	 *      ICMSConstant.CHECKLIST_NON_PANEL_LAWYER)
	 * @param lawFirmPanelFlag - flag indicating if the legal firm is a panel
	 *        firm or non panel firm
	 */
	public void setLawFirmPanelFlag(String lawFirmPanelFlag) {
		this.lawFirmPanelFlag = lawFirmPanelFlag;
	}

	/**
	 * Get the lawyer reference number
	 * @return lawyer reference number of String type
	 */
	public String getLawyerReferenceNumber() {
		return lawyerReferenceNumber;
	}

	/**
	 * Sets the lawyer reference number
	 * @param lawyerReferenceNumber - Lawyer reference number of String type
	 */
	public void setLawyerReferenceNumber(String lawyerReferenceNumber) {
		this.lawyerReferenceNumber = lawyerReferenceNumber;
	}

	/**
	 * Gets the lawyer in charge
	 * @return lawyer in charge of String type
	 */
	public String getLawyerInCharge() {
		return lawyerInCharge;
	}

	/**
	 * Sets the lawyer in charge
	 * @param lawyerInCharge - Lawyer in charge of String type
	 */
	public void setLawyerInCharge(String lawyerInCharge) {
		this.lawyerInCharge = lawyerInCharge;
	}

	/**
	 * Gets the legal firm address
	 * @return Legal firm address of String type
	 */
	public String getLawFirmAddress() {
		return lawFirmAddress;
	}

	/**
	 * Sets the law firm address
	 * @param lawFirmAddress - Legal firm address of String type
	 */
	public void setLawFirmAddress(String lawFirmAddress) {
		this.lawFirmAddress = lawFirmAddress;
	}

	/**
	 * Gets the law firm contact number
	 * @return Law firm contact number of String type
	 */
	public String getLawFirmContactNumber() {
		return lawFirmContactNumber;
	}

	/**
	 * Sets the law firm contact number
	 * @param lawFirmContactNumber - Law Firm Contact Number of String type
	 */
	public void setLawFirmContactNumber(String lawFirmContactNumber) {
		this.lawFirmContactNumber = lawFirmContactNumber;
	}

	/**
	 * Get the email address of the lawyer in charge
	 * @return Email Address of String type
	 */
	public String getLawyerEmail() {
		return lawyerEmail;
	}

	/**
	 * Sets the email address of the lawyer in charge
	 * @param lawyerEmail - Email Address of String type
	 */
	public void setLawyerEmail(String lawyerEmail) {
		this.lawyerEmail = lawyerEmail;
	}
	
	public String getValuerFirm() {
		return valuerFirm;
	}

	public void setValuerFirm(String valuerFirm) {
		this.valuerFirm = valuerFirm;
	}

	public String getValuerFirmPanelFlag() {
		return valuerFirmPanelFlag;
	}

	public void setValuerFirmPanelFlag(String valuerFirmPanelFlag) {
		this.valuerFirmPanelFlag = valuerFirmPanelFlag;
	}

	public String getValuerReferenceNumber() {
		return valuerReferenceNumber;
	}

	public void setValuerReferenceNumber(String valuerReferenceNumber) {
		this.valuerReferenceNumber = valuerReferenceNumber;
	}

	public String getValuerInCharge() {
		return valuerInCharge;
	}

	public void setValuerInCharge(String valuerInCharge) {
		this.valuerInCharge = valuerInCharge;
	}

	public String getValuerFirmAddress() {
		return valuerFirmAddress;
	}

	public void setValuerFirmAddress(String valuerFirmAddress) {
		this.valuerFirmAddress = valuerFirmAddress;
	}

	public String getValuerFirmContactNumber() {
		return valuerFirmContactNumber;
	}

	public void setValuerFirmContactNumber(String valuerFirmContactNumber) {
		this.valuerFirmContactNumber = valuerFirmContactNumber;
	}

	public String getValuerEmail() {
		return valuerEmail;
	}

	public void setValuerEmail(String valuerEmail) {
		this.valuerEmail = valuerEmail;
	}

	public String getInsurerFirm() {
		return insurerFirm;
	}

	public void setInsurerFirm(String insurerFirm) {
		this.insurerFirm = insurerFirm;
	}

	public String getInsurerFirmPanelFlag() {
		return insurerFirmPanelFlag;
	}

	public void setInsurerFirmPanelFlag(String insurerFirmPanelFlag) {
		this.insurerFirmPanelFlag = insurerFirmPanelFlag;
	}

	public String getInsurerReferenceNumber() {
		return insurerReferenceNumber;
	}

	public void setInsurerReferenceNumber(String insurerReferenceNumber) {
		this.insurerReferenceNumber = insurerReferenceNumber;
	}

	public String getInsurerInCharge() {
		return insurerInCharge;
	}

	public void setInsurerInCharge(String insurerInCharge) {
		this.insurerInCharge = insurerInCharge;
	}

	public String getInsurerFirmAddress() {
		return insurerFirmAddress;
	}

	public void setInsurerFirmAddress(String insurerFirmAddress) {
		this.insurerFirmAddress = insurerFirmAddress;
	}

	public String getInsurerFirmContactNumber() {
		return insurerFirmContactNumber;
	}

	public void setInsurerFirmContactNumber(String insurerFirmContactNumber) {
		this.insurerFirmContactNumber = insurerFirmContactNumber;
	}

	public String getInsurerEmail() {
		return insurerEmail;
	}

	public void setInsurerEmail(String insurerEmail) {
		this.insurerEmail = insurerEmail;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer("OBCheckList [");
		buf.append("checkListStatus=");
		buf.append(checkListStatus);
		buf.append(", applicationType=");
		buf.append(applicationType);
		buf.append(", checkListOwner=");
		buf.append(checkListOwner);
		buf.append(", checkListID=");
		buf.append(checkListID);
		buf.append(", checkListLocation=");
		buf.append(checkListLocation);
		buf.append(", checkListType=");
		buf.append(checkListType);
		buf.append(", checkListItemList=");
		buf.append(checkListItemList != null ? Arrays.asList(checkListItemList) : null);
		buf.append(", templateID=");
		buf.append(templateID);
		buf.append(", isObsolete=");
		buf.append(isObsolete);
		buf.append(", lastDocReceivedDate=");
		buf.append(lastDocReceivedDate);
		buf.append(", lawFirmAddress=");
		buf.append(lawFirmAddress);
		buf.append(", lawFirmContactNumber=");
		buf.append(lawFirmContactNumber);
		buf.append(", lawFirmPanelFlag=");
		buf.append(lawFirmPanelFlag);
		buf.append(", lawyerEmail=");
		buf.append(lawyerEmail);
		buf.append(", lawyerInCharge=");
		buf.append(lawyerInCharge);
		buf.append(", lawyerReferenceNumber=");
		buf.append(lawyerReferenceNumber);
		buf.append(", legalFirm=");
		buf.append(legalFirm);
		buf.append(", remarks=");
		buf.append(remarks);
		buf.append("]");
		return buf.toString();
	}

	public String getIsObsolete() {
		return isObsolete;
	}

	public void setIsObsolete(String isObsolete) {
		this.isObsolete = isObsolete;
	}
/*
 * Fields added for HDFC CR CAM document tracking by Abhijit R Dated 24-Apr-2013
 * 
 */
	private Date camDate ;
	private String camNumber="";
	private String camType="";
	private String isLatest="";

	public String getIsLatest() {
		return isLatest;
	}
	public void setIsLatest(String isLatest) {
		this.isLatest = isLatest;
	}



	public Date getCamDate() {
		return camDate;
	}

	public void setCamDate(Date camDate) {
		this.camDate = camDate;
	}

	public String getCamNumber() {
		return camNumber;
	}

	public void setCamNumber(String camNumber) {
		this.camNumber = camNumber;
	}

	public String getCamType() {
		return camType;
	}

	public void setCamType(String camType) {
		this.camType = camType;
	}

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
	
}
