/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/ICheckListItem.java,v 1.23 2006/04/17 04:22:35 jitendra Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.ICheckListItemImageDetail;
//java
import com.integrosys.cms.app.chktemplate.bus.IItem;

import java.io.Serializable;
import java.util.Date;

/**
 * This interface defines the list of attributes that will be available to a
 * checklist item
 * 
 * @author $Author: jitendra $<br>
 * @version $Revision: 1.23 $
 * @since $Date: 2006/04/17 04:22:35 $ Tag: $Name: $
 */
public interface ICheckListItem extends Serializable {
	/**
	 * Get the checklist item ID
	 * @return long - the checklist item ID
	 */
	public long getCheckListItemID();

	/**
	 * Get the checklist item reference
	 * @return long - the checklist item reference
	 */
	public long getCheckListItemRef();

	/**
	 * Get display date for custodian trx date
	 * @return Date
	 */
	public Date getDisplayCustodianTrxDate();

	/**
	 * Get the lock indicator
	 * @return boolean - true if it is locked and false otherwise
	 */
	public boolean getIsLockedInd();

	/**
	 * Get the mandatory indicator
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryInd();
	
	
	public boolean getIsMandatoryDisplayInd();

	/**
	 * Get the in vault indicator
	 * @return boolean - true if it is to be vaulted and false otherwise
	 */
	public boolean getIsInVaultInd();

	/**
	 * Get the external custodian indicator
	 * @return boolean - true if it is external custodian and false otherwise
	 */
	public boolean getIsExtCustInd();

	/**
	 * Get the audit indicator
	 * @return boolean - true if it is to be audited and false otherwise
	 */
	public boolean getIsAuditInd();

	/**
	 * Get the delete indicator
	 * @return boolean - true if it is deleted and false otherwise
	 */
	public boolean getIsDeletedInd();

	/**
	 * Get the approved indicator
	 * @return boolean - true if it is approved and false otherwise
	 */
	public boolean getIsApprovedInd();

	/**
	 * Get the parent item ID
	 * @return long - the parent item ID
	 */
	public long getParentItemID();

	/**
	 * Get the expiry date of the checklist item
	 * @return Date - the checklist item expiry date
	 */
	public Date getExpiryDate();

	/*
	 * Get the item status
	 * 
	 * @return String - the item status
	 */
	public String getItemStatus();

	/**
	 * Get the custodian doc item status
	 * @return String - the custodian doc item status
	 */
	public String getCustodianDocStatus();

	/**
	 * Get the custodian doc item transaction date
	 * @return Date - the custodian doc item trx date
	 */
	public Date getCustodianDocItemTrxDate();

	/**
	 * Get the action party
	 * @return String - the action party
	 */
	public String getActionParty();

	/**
	 * Get the checklist item remarks
	 * @return String - the checklist item remarks
	 */
	public String getRemarks();

	/**
	 * Get the document reference
	 * @return String - the document reference
	 */
	public String getDocRef();

	/**
	 * Get the form number
	 * @return String - the form number
	 */
	public String getFormNo();

	/**
	 * Get the document date
	 * @return Date - the document date
	 */
	public Date getDocDate();

	/**
	 * Get the checklist item. The checklist item must be one of the items
	 * created through the global template
	 * @return IItem - a checklist item
	 */
	public IItem getItem();

	/**
	 * Expiry for the defer item
	 * @return Date - the defer item expiry date
	 */
	public Date getDeferExpiryDate();

	public Date getDeferExtendedDate(); // cr 36

	/**
	 * Get the identify date
	 * @return Date - the identify date
	 */
	public Date getIdentifyDate();

	/**
	 * Get document completion date
	 * @return Date - the document completion date
	 */
	public Date getDocCompletionDate();

	/**
	 * Get the last update date
	 * @return Date - the last update date
	 */
	public Date getLastUpdateDate();

	/**
	 * Get the CPC Custodian Status
	 * @return String - the CPC Custodian Status
	 */
	public String getCPCCustodianStatus();

	/**
	 * Get the date on which is CPC Custodian Status updated
	 * @return String - the CPC Custodian Status
	 */
	public Date getCPCCustodianStatusUpdateDate();

	/**
	 * Get the Parent checklist item reference
	 * 
	 * @return long
	 */
	public long getParentCheckListItemRef();

	/**
	 * Helper method to get the Item code.
	 * @return String - the item code
	 */
	public String getItemCode();

	/**
	 * Helper method to get the Item code.
	 * @param itemCode  - the item code
	 */
	public void setItemCode(String itemCode);
	
//	public int getTenureCount();
	
//	public void setTenureCount(int tenureCount);
	
//	public String getTenureType();
	
//	public void setTenureType(String tenureType);

	/**
	 * Get the checklist item description
	 * @return String - the checklist item description
	 */
	public String getItemDesc();

	/**
	 * Set the checklist item description
	 * @param itemDesc - the checklist item description
	 */
	public void setItemDesc(String itemDesc);

	/**
	 * Set the checklist item ID
	 * @param aCheckListItemID of long type
	 */
	public void setCheckListItemID(long aCheckListItemID);

	/**
	 * Set the checklist item reference
	 * @param aCheckListItemRef of long type
	 */
	public void setCheckListItemRef(long aCheckListItemRef);

	/**
	 * Set the checklist item description
	 * @param aCheckListItemDesc of String type
	 */
	public void setCheckListItemDesc(String aCheckListItemDesc);

	/**
	 * Set the lock indicator param anIsLockedInd of boolean type
	 */
	public void setIsLockedInd(boolean anIsLockedInd);

	/**
	 * Set the mandatory indicator
	 * @param anIsMandatoryInd of boolean type
	 */
	public void setIsMandatoryInd(boolean anIsMandatoryInd);

	
	public void setIsMandatoryDisplayInd(boolean anIsMandatoryDisplayInd);
	
	
	public int getTenureCount();
	
	public String getTenureType();
	
    public void setTenureCount(int tenureCount);
    
    public void setTenureType(String tenureType);
    
    public String getDeferCount();
	
    
    public void setDeferCount(String deferCount);
    
    public abstract String getDeferedDays();
	
    
    public abstract void setDeferedDays(String deferedDays);
    
    public abstract String getCurrency();


    public abstract void setCurrency(String currency);

    public abstract String getDocAmt();


    public abstract void setDocAmt(String docAmt);

    public abstract String getHdfcAmt();


    public abstract void setHdfcAmt(String hdfcAmt);
    
    //Added for Facility receipt Image tagUntag details
    public String getFacImageTagUntagId();
    public void setFacImageTagUntagId(String facImageTagUntagId);
    
    public String getFacImageTagUntagImgName();
    public void setFacImageTagUntagImgName(String facImageTagUntagImgName);

    public String getFacImageTagUntagStatus();
    public void setFacImageTagUntagStatus(String facImageTagUntagStatus);

    //End:Added for Facility receipt Image tagUntag details
    
    
//Added for image tag untag with security receipt
    
  public abstract String getSecImageTagUntagId();
  public abstract void setSecImageTagUntagId(String secImageTagUntagId);
  
  public abstract String getSecImageTagUntagImgName();
  public abstract void setSecImageTagUntagImgName(String secImageTagUntagImgName);
  
  public abstract String getSecImageTagUntagStatus();
  public abstract void setSecImageTagUntagStatus(String secImageTagUntagStatus);

//End:Added for image tag untag with security receipt

    public abstract String getStatementType();
    

   
    public abstract void setStatementType(String statementType);
	/**
	 * Set the in vault indicator
	 * @param anIsInVaultInd of boolean type
	 */
	public void setIsInVaultInd(boolean anIsInVaultInd);

	/**
	 * Set the external custodian indicator
	 * @param anIsExtCustInd of boolean type
	 */
	public void setIsExtCustInd(boolean anIsExtCustInd);

	/**
	 * Set the audit indicator
	 * @param anIsAuditInd of boolean type
	 */
	public void setIsAuditInd(boolean anIsAuditInd);

	/**
	 * Set the delete indicator
	 * @param anIsDeletedInd of boolean type
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd);

	/**
	 * Set the approved indicator
	 * @param anIsApprovedInd of boolean type
	 */
	public void setIsApprovedInd(boolean anIsApprovedInd);

	/**
	 * Set the template item ID inherited
	 * @param aParentItemID of long type
	 */
	public void setParentItemID(long aParentItemID);

	/**
	 * Set the expiry date
	 * @param anExpiryDate of Date type
	 */
	public void setExpiryDate(Date anExpiryDate);

	/**
	 * Set the item status
	 * @param anItemStatus of String type
	 */
	public void setItemStatus(String anItemStatus);

	/**
	 * Set the custodian doc item status
	 * @param aCustodianDocStatus of String type
	 */
	public void setCustodianDocStatus(String aCustodianDocStatus);

	/**
	 * Set the custodian doc item transaction date
	 * @param aCustodianDocItemTrxDate of Date type
	 */
	public void setCustodianDocItemTrxDate(Date aCustodianDocItemTrxDate);

	/**
	 * Set the action party
	 * @param anActionParty of String type
	 */
	public void setActionParty(String anActionParty);

	/**
	 * Set the remarks
	 * @param aRemarks of String type
	 */
	public void setRemarks(String aRemarks);

	/**
	 * Set the document reference
	 * @param aDocRef of String type
	 */
	public void setDocRef(String aDocRef);

	/**
	 * Set the form number
	 * @param aFormNo of String type
	 */
	public void setFormNo(String aFormNo);

	/**
	 * Set the document date
	 * @param aDocDate of Date type
	 */
	public void setDocDate(Date aDocDate);

	/**
	 * Set the checklist item
	 * @param anIItem of IItem type
	 */
	public void setItem(IItem anIItem);

	/**
	 * Set the defer expiry date
	 * @param aDeferExpiryDate of Date type
	 */
	public void setDeferExpiryDate(Date aDeferExpiryDate);

	public void setDeferExtendedDate(Date aDeferExtendedDate); // cr 36

	/**
	 * Set the identify date
	 * @param anIdentifyDate of Date type
	 */
	public void setIdentifyDate(Date anIdentifyDate);

	/**
	 * Set the document completion date
	 * @param aDocCompletionDate of Date type
	 */
	public void setDocCompletionDate(Date aDocCompletionDate);

	/**
	 * Set the last update date
	 * @param aLastUpdateDate of Date type
	 */
	public void setLastUpdateDate(Date aLastUpdateDate);

	/**
	 * Set the CPC Custodian Status
	 * @param aCPCCustodianStatus of String type
	 */
	public void setCPCCustodianStatus(String aCPCCustodianStatus);

	/**
	 * Set the date on which is CPC Custodian Status updated
	 * @param anUpdateDate
	 */
	public void setCPCCustodianStatusUpdateDate(Date anUpdateDate);

	/**
	 * Set the parent checklist item reference
	 * @param aParentCheckListItemRef of String type
	 */
	public void setParentCheckListItemRef(long aParentCheckListItemRef);

	/**
	 * Get insurance document effective date.
	 * 
	 * @return Date
	 */
	public Date getEffectiveDate();

	/**
	 * Set insurance document effective date.
	 * 
	 * @param effectiveDate of type Date
	 */
	public void setEffectiveDate(Date effectiveDate);

	/**
	 * Helper method to check if hard delete is allowed
	 * @return boolean - true if hard deleted is allowed and false otherwise
	 */
	public boolean isHardDeleteAllowed();

	/**
	 * Helper method to check if soft delete is allowed
	 * @return boolean - true if soft deleted is allowed and false otherwise
	 */
	public boolean isSoftDeleteAllowed();

	/**
	 * Helper method to check if the checklist item is inherited or not
	 * @return boolean - true if inherited and false otherwise
	 */
	public boolean getIsInherited();

    //cz: newly added - when inherited from template, always set to true
    public void setIsInherited(boolean inherited);
    /**
	 * Helper method to check if the checklist item can be deleted or not
	 * @return boolean - true if it can be deleted and false otherwise
	 */
	public boolean isDeletable();

	public boolean isEditable();

	public Date getCPCCustodianStatusLastUpdateDate();

	public void setCPCCustodianStatusLastUpdateDate(Date date);

	// Start For CR17
	public boolean getShared();

	public void setShared(boolean shareStatus);

	public boolean getViewable();

	public void setViewable(boolean view);

	public IShareDoc[] getShareCheckList();

	public void setShareCheckList(IShareDoc[] aIShareDocList);
	// End For CR17


    //Link with LOS
    public boolean getIsPreApprove();

    public void setIsPreApprove(boolean flag);

    public Date getReceivedDate();

    public void setReceivedDate(Date receivedDate);

    public Date getCompletedDate();

    public void setCompletedDate(Date completedDate);

    public Date getExpectedReturnDate();

    public void setExpectedReturnDate(Date expectedReturnDate);

    public Date getWaivedDate();

    public void setWaivedDate(Date waviedDate);

    public Date getLodgedDate();

    public void setLodgedDate(Date lodgedDate);

    public Date getTempUpliftedDate();

    public void setTempUpliftedDate(Date tempUpliftedDate);

    public Date getPermUpliftedDate();

    public void setPermUpliftedDate(Date permUpliftedDate);
    
    
    public String getCreditApprover();
    
    public void setCreditApprover(String creditApprover);
    /*
	 * New fields added for CR-Document Tracking dated : 16 April 2013 by Abhijit R
	 * 
	 */
    
    public abstract Date getUpdatedDate();
	public abstract void setUpdatedDate(Date updatedDate);

	public abstract Date getApprovedDate();
	public abstract void setApprovedDate(Date approvedDate) ;

	public abstract Date getOriginalTargetDate() ;
	public abstract void setOriginalTargetDate(Date originalTargetDate) ;
	
	public abstract String getDocumentVersion() ;
	public abstract void setDocumentVersion(String documentVersion) ;

	public abstract String getDocumentStatus() ;
	public abstract void setDocumentStatus(String documentStatus) ;
	
	public abstract String getUpdatedBy() ;
	public abstract void setUpdatedBy(String updatedBy) ;

	public abstract String getApprovedBy();
	public abstract void setApprovedBy(String approvedBy) ;
    
	/*public String getIsDisplay();
	public void setIsDisplay(String isDisplay);*/
	
	public abstract String getCpsId();
	public abstract void setCpsId(String cpsId);

	
	//Uma Khot::Insurance Deferral maintainance
	public  String getInsuranceId();
	public  void setInsuranceId(String insuranceId);
	
	public ICheckListItemImageDetail[] getCheckListItemImageDetail();
	public void setCheckListItemImageDetail(ICheckListItemImageDetail[] checkListItemImageDetail);
    
    /*
	 * End 
	 * 
	 */
	
	public String getRocCharge();
	public void setRocCharge(String rocCharge);

	public String getFlagSchedulerCheckItem();
	public void setFlagSchedulerCheckItem(String flagSchedulerCheckItem);
	public String getReferenceType() ;
	public void setReferenceType(String referenceType);
	
	public String getDocJust();
	public void setDocJust(String docJust);
}
