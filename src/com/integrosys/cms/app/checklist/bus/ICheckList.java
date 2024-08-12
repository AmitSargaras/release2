/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckList.java,v 1.12 2006/04/13 03:14:00 jzhai Exp $
 */
package com.integrosys.cms.app.checklist.bus;

//java
import java.io.Serializable;
import java.util.Date;

import com.integrosys.base.techinfra.ejbsupport.IValueObject;
import com.integrosys.cms.app.chktemplate.bus.IDocumentItem;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.common.bus.IBookingLocation;

/**
 * This interface defines the list of attributes that will be available to a
 * checklist
 * 
 * @author $Author: jzhai $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2006/04/13 03:14:00 $ Tag: $Name: $
 */
public interface ICheckList extends IValueObject, Serializable {
	/**
	 * Get the check list ID
	 * @return long - the checklist ID
	 */
	public long getCheckListID();

	/**
	 * Get the check list type
	 * @return String - the checklist type
	 */
	public String getCheckListType();

	/**
	 * Get the check list owner
	 * @return String - the checklist owner
	 */
	public ICheckListOwner getCheckListOwner();

	/**
	 * Get the template ID
	 * @return long - the list of template ID
	 */
	public long getTemplateID();

	/**
	 * Get the checklist status
	 * @return String - the checklist status
	 */
	public String getCheckListStatus();

	/**
	 * Get the last document received date
	 * @return Date - the last document received date
	 */
	public Date getLastDocReceivedDate();

	/**
	 * Get the legal firm
	 * @return String - the legal firm handling the checklist
	 */
	public String getLegalFirm();

	/**
	 * Get the legal firm
	 * @return String - the legal firm handling the checklist
	 */
	public String getRemarks();

	/**
	 * Get the checklist location
	 * @return IBookingLocation - the checklist originating location
	 */
	public IBookingLocation getCheckListLocation();

	/**
	 * Get the allow delete indicator
	 * @return boolean - true of deletion is allowed on the checklist
	 */
	public boolean getAllowDeleteInd();

	/**
	 * Get the list of checklist items
	 * @return ICheckListItem[] - the list of checklist items
	 */
	public ICheckListItem[] getCheckListItemList();

	/**
	 * Get deficiency identified date
	 * @return Date
	 */
	public Date getDeficiencyDate();

	/**
	 * Get next action date
	 * @return Date
	 */
	public Date getNextActionDate();

	/**
	 * Set the checklist ID
	 * @param aCheckListID of long type
	 */
	public void setCheckListID(long aCheckListID);

	/**
	 * Set the checklist type
	 * @param aCheckListType of String type
	 */
	public void setCheckListType(String aCheckListType);

	/**
	 * Set the checklist attributes
	 * @param aCheckListOwner of type ICheckListOwner
	 */
	public void setCheckListOwner(ICheckListOwner aCheckListOwner);

	/**
	 * Set the list of template ID that this checklist inherited from
	 * @param aTemplateID of long type
	 */
	public void setTemplateID(long aTemplateID);

	/**
	 * Set the checklist status
	 * @param aCheckListStatus of String type
	 */
	public void setCheckListStatus(String aCheckListStatus);

	/**
	 * Set the last document received date
	 * @param aLastDocReceivedDate of Date type
	 */
	public void setLastDocReceivedDate(Date aLastDocReceivedDate);

	/**
	 * Set the legal form
	 * @param aLegalFirm of String type
	 */
	public void setLegalFirm(String aLegalFirm);

	/**
	 * Set the legal form
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks);

	/**
	 * Set the checklist originating location
	 * @param anIBookingLocation if IBookingLocation type
	 */
	public void setCheckListLocation(IBookingLocation anIBookingLocation);

	/**
	 * Set the allow deletion indicator
	 * @param anAllowDeleteInd of boolean type
	 */
	public void setAllowDeleteInd(boolean anAllowDeleteInd);

	public void setDisableCollaborationInd(String adisableCollaborationInd);

	public String getDisableCollaborationInd();

	/**
	 * Set the list of checklist items
	 * @param aCheckListItemList of ICheckListItem[] type
	 */
	public void setCheckListItemList(ICheckListItem[] aCheckListItemList);

	/**
	 * Set the deficiency identified date
	 * @param deficiencyDate of Date type
	 */
	public void setDeficiencyDate(Date deficiencyDate);

	/**
	 * Set the next action date
	 * @param nextActionDate of Date type
	 */
	public void setNextActionDate(Date nextActionDate);

	/**
	 * Add a list of items into the template
	 * @param anItemList - IItem[]
	 */
	public void addItems(IItem[] anItemList);

	/**
	 * Remove a list of items into the template
	 * @param anItemIndexList - the list of item index to be removed
	 */
	public void removeItems(int[] anItemIndexList);

	/**
	 * Update an item in the template
	 * @param anItemIndex - int
	 * @param anItem - IItem
	 */
	public void updateItem(int anItemIndex, IItem anItem);

	public void setReversalRemarks(String remarks);

	public String getReversalRemarks();

	public void setReversalRmkUpdatedUserInfo(String userInfo);

	public String getReversalRmkUpdatedUserInfo();

    /**
     * Gets the application type of the AA.
     * E.g. CC - Credit Card, HP Hire Purchase.
     * @return application type of AA
     */
    public String getApplicationType();

    /**
     * Sets the application type of the AA.
     * E.g. CC - Credit Card, HP Hire Purchase.
     * @param applicationType - Application Type
     */
    public void setApplicationType(String applicationType);

    /**
     * Get the flag indicating if the legal firm is a panel firm or non-panel firm.
     * @see com.integrosys.cms.app.common.constant.ICMSConstant (ICMSConstant.CHECKLIST_PANEL_LAWYER, ICMSConstant.CHECKLIST_NON_PANEL_LAWYER)
     * @return if the law firm is a panel firm or non-panel firm of String type
     */
    String getLawFirmPanelFlag();

    /**
     * Sets the flag indicating if the legal firm is a panel firm or non-panel firm
     * @see com.integrosys.cms.app.common.constant.ICMSConstant (ICMSConstant.CHECKLIST_PANEL_LAWYER, ICMSConstant.CHECKLIST_NON_PANEL_LAWYER)
     * @param lawFirmPanelFlag - flag indicating if the legal firm is a panel firm or non panel firm
     */
    void setLawFirmPanelFlag(String lawFirmPanelFlag);

    /**
     * Get the lawyer reference number
     * @return lawyer reference number of String type
     */
    String getLawyerReferenceNumber();

    /**
     * Sets the lawyer reference number
     * @param lawyerReferenceNumber - Lawyer reference number of String type
     */
    void setLawyerReferenceNumber(String lawyerReferenceNumber);

    /**
     * Gets the lawyer in charge
     * @return lawyer in charge of String type
     */
    String getLawyerInCharge();

    /**
     * Sets the lawyer in charge
     * @param lawyerInCharge - Lawyer in charge of String type
     */
    void setLawyerInCharge(String lawyerInCharge);

    /**
     * Gets the legal firm address
     * @return Legal firm address of String type
     */
    String getLawFirmAddress();

    /**
     * Sets the law firm address
     * @param lawFirmAddress - Legal firm address of String type
     */
    void setLawFirmAddress(String lawFirmAddress);

    /**
     * Gets the law firm contact number
     * @return Law firm contact number of String type
     */
    String getLawFirmContactNumber();

    /**
     * Sets the law firm contact number
     * @param lawFirmContactNumber - Law Firm Contact Number of String type
     */
    void setLawFirmContactNumber(String lawFirmContactNumber);

    /**
     * Get the email address of the lawyer in charge
     * @return Email Address of String type
     */
    String getLawyerEmail();

    /**
     * Sets the email address of the lawyer in charge
     * @param lawyerEmail - Email Address of String type
     */
    void setLawyerEmail(String lawyerEmail);
    
	public  String getValuerFirm();

	public  void setValuerFirm(String aValuerFirm);

	public  String getValuerReferenceNumber();

	public  void setValuerReferenceNumber(String valuerReferenceNumber);

	public  String getValuerInCharge();

	public  void setValuerInCharge(String valuerInCharge);

	public  String getValuerFirmAddress();

	public  void setValuerFirmAddress(String valuerFirmAddress);

	public  String getValuerFirmContactNumber();

	public  void setValuerFirmContactNumber(String valuerFirmContactNumber);

	public  String getValuerEmail();

	public  void setValuerEmail(String valuerEmail);

	public  String getValuerFirmPanelFlag();

	public  void setValuerFirmPanelFlag(String valuerFirmPanelFlag);
	
	public  String getInsurerFirm();

	public  void setInsurerFirm(String aInsurerFirm);

	public  String getInsurerReferenceNumber();

	public  void setInsurerReferenceNumber(String insurerReferenceNumber);

	public  String getInsurerInCharge();

	public  void setInsurerInCharge(String insurerInCharge);

	public  String getInsurerFirmAddress();

	public  void setInsurerFirmAddress(String insurerFirmAddress);

	public  String getInsurerFirmContactNumber();

	public  void setInsurerFirmContactNumber(String insurerFirmContactNumber);

	public  String getInsurerEmail();

	public  void setInsurerEmail(String insurerEmail);

	public  String getInsurerFirmPanelFlag();

	public  void setInsurerFirmPanelFlag(String insurerFirmPanelFlag);
	
	public abstract Date getCamDate() ;
	public abstract void setCamDate(Date camDate) ;

	public abstract String getCamNumber() ;
	public abstract void setCamNumber(String camNumber) ;

	public abstract String getCamType() ;
	public abstract void setCamType(String camType) ;
	
	public abstract String getIsLatest();
	public abstract void setIsLatest(String isLatest);
	
	public String getIsDisplay() ;

	public void setIsDisplay(String isDisplay);
	
	//To store maker checker information while maintaining checklist
	public abstract Date getUpdatedDate();
	public abstract void setUpdatedDate(Date updatedDate);

	public abstract Date getApprovedDate();
	public abstract void setApprovedDate(Date approvedDate);

	public abstract String getUpdatedBy();
	public abstract void setUpdatedBy(String updatedBy);

	public abstract String getApprovedBy();
	public abstract void setApprovedBy(String approvedBy);
	
	public String getFlagSchedulersCheck();
	public void setFlagSchedulersCheck(String flagSchedulersCheck);
}
