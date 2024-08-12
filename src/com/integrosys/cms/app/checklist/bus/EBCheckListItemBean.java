/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBCheckListItemBean.java,v 1.38 2006/11/20 03:10:33 czhou Exp $
 */
package com.integrosys.cms.app.checklist.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.EBCheckListItemImageDetailLocal;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.EBCheckListItemImageDetailLocalHome;
import com.integrosys.cms.app.checklist.bus.checklistitemimagedetail.ICheckListItemImageDetail;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.TypeConverter;

/**
 * This entity bean represents the persistence for checklist item Information
 * 
 * @author $Author: Abhijit Rudrakshawar $
 * @version $Revision: 1.38 $
 * @since $Date: 2006/11/20 03:10:33 $ Tag: $Name: $
 */
public abstract class EBCheckListItemBean implements EntityBean, ICheckListItem {
	private static final String[] EXCLUDE_METHOD = new String[] { "getCheckListItemID", "getCheckListItemRef",
			"getShareCheckList" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBCheckListItemBean() {
	}

	// *************Override Inteface Methods (Java 1.4 requirement)
	// **************
	public abstract long getParentItemID();

	public abstract String getItemStatus();

	public abstract String getActionParty();
	
	public abstract String getCreditApprover();

	public abstract String getRemarks();

	public abstract String getDocJust();
	
	public abstract void setDocJust(String docJust);
	
	public abstract String getDocRef();

	public abstract String getFormNo();

	public abstract Date getDocDate();

	public abstract Date getDeferExpiryDate();

	public abstract Date getDeferExtendedDate();

	public abstract Date getIdentifyDate();

	public abstract Date getDocCompletionDate();

	public abstract Date getLastUpdateDate();

	public abstract String getCPCCustodianStatus();

	public abstract Date getCPCCustodianStatusUpdateDate();

	public abstract long getParentCheckListItemRef();

	public abstract void setCheckListItemRef(long aCheckListItemRef);

	public abstract void setParentItemID(long aParentItemID);

	public abstract void setExpiryDate(Date anExpiryDate);

	public abstract void setActionParty(String anActionParty);
	
	public abstract void setCreditApprover(String creditApprover);

	public abstract void setRemarks(String aRemarks);

	public abstract void setDocRef(String aDocRef);

	public abstract void setFormNo(String aFormNo);

	public abstract void setDocDate(Date aDocDate);

	public abstract void setDeferExpiryDate(Date aDeferExpiryDate);

	public abstract void setDeferExtendedDate(Date aDeferExtendedDate);

	public abstract void setIdentifyDate(Date anIdentifyDate);

	public abstract void setDocCompletionDate(Date aDocCompletionDate);

	public abstract void setLastUpdateDate(Date aLastUpdateDate);

	public abstract void setCPCCustodianStatus(String aCPCCustodianStatus);

	public abstract void setCPCCustodianStatusUpdateDate(Date anUpdateDate);

	public abstract void setParentCheckListItemRef(long aParentCheckListItemRef);

	public abstract Date getEffectiveDate();

	public abstract void setEffectiveDate(Date effectiveDate);

	public abstract Date getCPCCustodianStatusLastUpdateDate();

	public abstract void setCPCCustodianStatusLastUpdateDate(Date date);

	public abstract Date getReceivedDate();

	public abstract void setReceivedDate(Date receivedDate);

	public abstract Date getCompletedDate();

	public abstract void setCompletedDate(Date completedDate);

	public abstract Date getExpectedReturnDate();

	public abstract void setExpectedReturnDate(Date expectedReturnDate);

	public abstract Date getWaivedDate();

	public abstract void setWaivedDate(Date waviedDate);

	public abstract Date getLodgedDate();

	public abstract void setLodgedDate(Date lodgedDate);

	public abstract Date getTempUpliftedDate();

	public abstract void setTempUpliftedDate(Date tempUpliftedDate);

	public abstract Date getPermUpliftedDate();

	public abstract void setPermUpliftedDate(Date permUpliftedDate);

	// ************** Abstract methods ************
	public abstract Long getCMPCheckListItemID();

	public abstract String getIsLockedIndStr();

	public abstract String getIsMandatoryIndStr();
	
	public abstract String getIsMandatoryDisplayIndStr();

	public abstract String getIsInVaultIndStr();

	public abstract String getIsExtCustIndStr();

	public abstract String getIsAuditIndStr();

	public abstract String getIsDeletedIndStr();

	public abstract String getIsInheritedIndStr();

	public abstract Long getCMPCheckListID();

	public abstract long getCheckListItemRef();

	// Getter methods for the checklist item attributes
	public abstract String getCMPItemCode();

	public abstract String getCMPItemDesc();

	public abstract Date getExpiryDate();

	public abstract String getCMPMonitorType();

	public abstract void setCMPCheckListItemID(Long aCheckListItemID);

	public abstract void setIsLockedIndStr(String anIsLockedIndStr);

	public abstract void setIsMandatoryIndStr(String anIsMandatoryIndStr);

	public abstract void setIsMandatoryDisplayIndStr(String anIsMandatoryDisplayIndStr);

	public abstract void setIsInVaultIndStr(String anIsInVaultIndStr);

	public abstract void setIsExtCustIndStr(String anIsExtCustIndStr);

	public abstract void setIsAuditIndStr(String anIsAuditIndStr);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setIsInheritedIndStr(String anIsLockedIndStr);

	public abstract void setCMPCheckListID(Long aCheckListID);

	public abstract void setItemStatus(String anItemStatus);

	// Setter methods for the checklist item attributes
	public abstract void setCMPItemCode(String anItemCode);

	public abstract void setCMPItemDesc(String anItemDesc);

	public abstract void setCMPMonitorType(String aMonitorType);

	// for cr-17
	public abstract String getSharedStr();

	public abstract void setSharedStr(String status);

	public abstract Collection getCMRShareCheckList();

	public abstract void setCMRShareCheckList(Collection aCheckListItems);

	// Link with LOS
	public abstract String getIsPreApproveStr();
	
	public abstract int getTenureCount();
	
	public abstract String getTenureType();
	
    public abstract void setTenureCount(int tenureCount);
    
    public abstract void setTenureType(String tenureType);
    
    public abstract String getDeferCount();
	
    
    public abstract void setDeferCount(String deferCount);
    
    public abstract String getDeferedDays();
	
    
    public abstract void setDeferedDays(String deferedDays);
    
    public abstract String getCurrency();


    public abstract void setCurrency(String currency);

    public abstract String getDocAmt();


    public abstract void setDocAmt(String docAmt);

    public abstract String getHdfcAmt();

    public abstract void setHdfcAmt(String hdfcAmt);
    
    //Added for image tag untag with facility receipt
    
    public abstract String getFacImageTagUntagId();
    public abstract void setFacImageTagUntagId(String facImageTagUntagId);
    
    public abstract String getFacImageTagUntagImgName();
    public abstract void setFacImageTagUntagImgName(String facImageTagUntagImgName);
    
    public abstract String getFacImageTagUntagStatus();
    public abstract void setFacImageTagUntagStatus(String facImageTagUntagStatus);

  //End:Added for image tag untag with facility receipt
    
    
    
//Added for image tag untag with security receipt
    
  public abstract String getSecImageTagUntagId();
  public abstract void setSecImageTagUntagId(String secImageTagUntagId);
  
  public abstract String getSecImageTagUntagImgName();
  public abstract void setSecImageTagUntagImgName(String secImageTagUntagImgName);
  
  public abstract String getSecImageTagUntagStatus();
  public abstract void setSecImageTagUntagStatus(String secImageTagUntagStatus);

//End:Added for image tag untag with security receipt
    

    
   /* public abstract String getIsDisplay();
    
	public abstract void setIsDisplay(String isDisplay);*/
  
    public abstract String getStatementType();


    public abstract void setStatementType(String statementType);

	public abstract void setIsPreApproveStr(String flag);

	// ************** Override methods ************
	public boolean isHardDeleteAllowed() {
		return false;
	}

	public boolean isSoftDeleteAllowed() {
		return false;
	}

	public boolean isDeletable() {
		return false;
	}

	public boolean isEditable() {
		return false;
	}

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the checklist item ID
	 * @return long - the long value of the checklist item
	 */
	public long getCheckListItemID() {
		if (getCMPCheckListItemID() != null) {
			return getCMPCheckListItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get the lock indicator
	 * @return boolean - true if it is locked and false otherwise
	 */
	public boolean getIsLockedInd() {
		if ((getIsLockedIndStr() != null) && getIsLockedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to get mandatory indicator
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryInd() {
		if ((getIsMandatoryIndStr() != null) && getIsMandatoryIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public boolean getIsMandatoryDisplayInd() {
		if ((getIsMandatoryDisplayIndStr() != null) && getIsMandatoryDisplayIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}
	/**
	 * Helper method to get the invault indicator
	 * @return boolean - true if it is to be invault and false otherwise
	 */
	public boolean getIsInVaultInd() {
		if ((getIsInVaultIndStr() != null) && getIsInVaultIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to get the external custodian indicator
	 * @return boolean - true if it is an external custodian and false otherwise
	 */
	public boolean getIsExtCustInd() {
		if ((getIsExtCustIndStr() != null) && getIsExtCustIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to get the audit indicator
	 * @return boolean - true if it is to be audited and false otherwise
	 */
	public boolean getIsAuditInd() {
		if ((getIsAuditIndStr() != null) && getIsAuditIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to get the delete indicator
	 * @return boolean - true if it is to be deleted and false otherwise
	 */
	public boolean getIsDeletedInd() {
		if ((getIsDeletedIndStr() != null) && getIsDeletedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public boolean getIsInherited() {
		if ((getIsInheritedIndStr() != null) && getIsInheritedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Not implemented here
	 */
	public Date getDisplayCustodianTrxDate() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public boolean getIsApprovedInd() {
		return true;
	}

	/**
	 * Not implemented here
	 */
	public String getCustodianDocStatus() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public Date getCustodianDocItemTrxDate() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public IItem getItem() {
		return null;
	}

	/**
	 * Not implemented yet
	 */
	public String getItemCode() {
		return null;
	}

	/**
	 * Not implemented yet
	 */
	public void setItemCode(String itemCode) {
		// Do Nothing
	}

	/**
	 * Not implemented yet
	 */
	public String getItemDesc() {
		return null;
	}

	/**
	 * Not implemented yet
	 */
	public void setItemDesc(String itemDesc) {
		// Do Nothing
	}

	/**
	 * Not implemented yet
	 */
	public boolean getIsInheritedInd() {
		return false;
	}

	/**
	 * Helper method to set the checklist item ID
	 * @param aCheckListItemID - long
	 */
	public void setCheckListItemID(long aCheckListItemID) {
		setCMPCheckListItemID(new Long(aCheckListItemID));
	}

	/**
	 * Helper method to set the lock indicator
	 * @param anIsLockedInd - boolean
	 */
	public void setIsLockedInd(boolean anIsLockedInd) {
		if (anIsLockedInd) {
			setIsLockedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsLockedIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set mandatory indicator
	 * @param anIsMandatoryInd - boolean
	 */
	public void setIsMandatoryInd(boolean anIsMandatoryInd) {
		if (anIsMandatoryInd) {
			setIsMandatoryIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsMandatoryIndStr(ICMSConstant.FALSE_VALUE);
	}

	public void setIsMandatoryDisplayInd(boolean anIsMandatoryDisplayIndStr) {
		if (anIsMandatoryDisplayIndStr) {
			setIsMandatoryDisplayIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsMandatoryDisplayIndStr(ICMSConstant.FALSE_VALUE);
	}
	/**
	 * Helper method to set invault indicator
	 * @param anIsInVaultInd - boolean
	 */
	public void setIsInVaultInd(boolean anIsInVaultInd) {
		if (anIsInVaultInd) {
			setIsInVaultIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsInVaultIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set invault indicator
	 */
	public void setIsExtCustInd(boolean anIsExtCustInd) {
		if (anIsExtCustInd) {
			setIsExtCustIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsExtCustIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set audit indicator
	 * @param anIsAuditInd - boolean
	 */
	public void setIsAuditInd(boolean anIsAuditInd) {
		if (anIsAuditInd) {
			setIsAuditIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsAuditIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set delete indicator
	 * @param anIsDeletedInd - boolean
	 */
	public void setIsDeletedInd(boolean anIsDeletedInd) {
		if (anIsDeletedInd) {
			setIsDeletedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsDeletedIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set inherited indicator
	 * @param isInherited - boolean
	 */
	public void setIsInherited(boolean isInherited) {
		if (isInherited) {
			setIsInheritedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsInheritedIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Not implemented here
	 */
	public void setIsApprovedInd(boolean anIsApprovedInd) {
		// do nothing
	}

	/**
	 * Not implemented here
	 */
	public void setCustodianDocStatus(String aCustodianDocStatus) {
		// do nothing
	}

	/**
	 * Not implemented here
	 */
	public void setCustodianDocItemTrxDate(Date aCustodianDocItemTrxDate) {
		// do nothing
	}

	/**
	 * Not implemented here
	 */
	public void setItem(IItem anIItem) {
		// do nothing
	}

	/**
	 * Not implemented here
	 */
	public void setIsInheritedInd(boolean anIsInheritedInd) {
		// do nothing
	}

	/**
	 * Set the checklist item description
	 * @param aCheckListItemDesc of String type
	 */
	public void setCheckListItemDesc(String aCheckListItemDesc) {
		// do nothing
	}

	/**
	 * Helper method to get pre-approve indicator
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsPreApprove() {
		return TypeConverter.convertStringToBooleanEquivalent(getIsPreApproveStr());
	}

	/**
	 * Helper method to set pre-approve indicator
	 * @param flag - boolean
	 */
	public void setIsPreApprove(boolean flag) {
		setIsPreApproveStr(flag ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
	}

	
	// *****************************************************
	/**
	 * Create a checklist item Information
	 * 
	 * @param aCheckListID - Long
	 * @param anICheckListItem - the checklist item
	 * @return Long - the checklist item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(Long aCheckListID, ICheckListItem anICheckListItem) throws CheckListException,
			CreateException {
		if (anICheckListItem == null) {
			throw new CreateException("ICheckListItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setCheckListItemID(pk);
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == anICheckListItem
					.getCheckListItemRef()) {
				setCheckListItemRef(pk);
			}
			else {
				setCheckListItemRef(anICheckListItem.getCheckListItemRef());
			}
			AccessorUtil.copyValue(anICheckListItem, this, EXCLUDE_METHOD);
			IItem item = anICheckListItem.getItem();
			if (item != null) {
				setCMPItemCode(item.getItemCode());
				setCMPItemDesc(item.getItemDesc());
				item.setTenureCount(anICheckListItem.getTenureCount());
				item.setTenureType(anICheckListItem.getTenureType());
				//setTenureCount(item.getTenureCount());
				//setTenureType(item.getTenureType());
				setCMPMonitorType(item.getMonitorType());
				setIsPreApprove(item.getIsPreApprove());
			}
			setTenureCount(anICheckListItem.getTenureCount());
			setTenureType(anICheckListItem.getTenureType());
					
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("failed to create checklist item when generating sequence ["
					+ getSequenceName() + "]");
			cex.initCause(ex);
			throw cex;
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param aCheckListID - the checkList ID (parent of this checkList item)
	 * @param anICheckListItem - the checkList item
	 */
	public void ejbPostCreate(Long aCheckListID, ICheckListItem anICheckListItem) throws CheckListException,
			CreateException {
		// createDocumentshareList(anICheckListItem);
		try {
			//if(!"Y".equals(anICheckListItem.getFlagSchedulerCheckItem())) {
			updateCheckListItemImageDetails(anICheckListItem.getCheckListItemImageDetail(), anICheckListItem.getCheckListItemID());
			//}
		} catch (Exception e) {
			DefaultLogger.error(this, "Exception caught: ", e);
			throw new CreateException(e.toString());
		}
	}
	
	public ICheckListItemImageDetail[] getCheckListItemImageDetail() {
		return null;
	}
	
	public void setCheckListItemImageDetail(ICheckListItemImageDetail[] lCheckListItemImageDetails) {
		
	}
	
	public static Comparator<ICheckListItemImageDetail> orderByCheckListItemImageDetailId = new Comparator<ICheckListItemImageDetail>() {
		public int compare(ICheckListItemImageDetail o1, ICheckListItemImageDetail o2) {
			return Long.valueOf(o1.getCheckListItemImageDetailId()).compareTo(o2.getCheckListItemImageDetailId());
		}
	}; 
	
	public abstract Collection getCMRCheckListItemImageDetails();
	
	public abstract void setCMRCheckListItemImageDetails(Collection value);
	
	protected EBCheckListItemImageDetailLocalHome getEBLocalCheckListItemImageDetail()  {
		EBCheckListItemImageDetailLocalHome home = (EBCheckListItemImageDetailLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_ITEM_IMAGE_DETAIL_LOCAL_JNDI, EBCheckListItemImageDetailLocalHome.class.getName());

		if (null != home) {
			return home;
		}
		else {
			throw new RuntimeException("EBCheckListItemImageDetailLocalHome is null!");
		}
	}
	
	private ICheckListItemImageDetail[] retrieveCheckListItemImageDetails(){

		try {
			Collection c = getCMRCheckListItemImageDetails();
			if ((null == c) || (c.size() == 0)) {
				return null;
			}
			else {
				ArrayList<ICheckListItemImageDetail> aList = new ArrayList<ICheckListItemImageDetail>();
				Iterator i = c.iterator();
				while (i.hasNext()) {
					EBCheckListItemImageDetailLocal local = (EBCheckListItemImageDetailLocal) i.next();
					ICheckListItemImageDetail ob = local.getValue();
					aList.add(ob);
				}
				ICheckListItemImageDetail[] checkListItemImageDetails = aList.toArray(new ICheckListItemImageDetail[0]);
				Arrays.sort(checkListItemImageDetails, orderByCheckListItemImageDetailId);
				return checkListItemImageDetails;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
				throw new RuntimeException("Caught Exception: " + e.toString());
		}
	
	}
	
	private void deleteCheckListItemImageDetails(List deleteList) {
		if ((null == deleteList) || (deleteList.size() == 0)) {
			
		}
		
		try {
			Collection c = getCMRCheckListItemImageDetails();
			Iterator i = deleteList.iterator();
			while (i.hasNext()) {
				EBCheckListItemImageDetailLocal local = (EBCheckListItemImageDetailLocal) i.next();
				c.remove(local);
				local.remove();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Caught Exception: " + e.toString());
		}
		
	}
	
	private void createCheckListItemImageDetails(List createList,long checkListItemId) {
		if ((null == createList) || (createList.size() == 0)) {
			return; // do nothing
		}
		Collection c = getCMRCheckListItemImageDetails();
		Iterator i = createList.iterator();
		try {
			EBCheckListItemImageDetailLocalHome home = getEBLocalCheckListItemImageDetail();
			while (i.hasNext()) {
				ICheckListItemImageDetail ob = (ICheckListItemImageDetail) i.next();
				if(ob!=null){
					ob.setCheckListItemId(checkListItemId);
					EBCheckListItemImageDetailLocal local = home.create(ob);
					c.add(local);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Caught Exception: " + e.toString());
		}
	}
	
	private void updateCheckListItemImageDetails(ICheckListItemImageDetail[] checkListItemImageDetails,long checkListItemId){
		try {
			System.out.println("<<<>>> Inside updateCheckListItemImageDetails inside async method call...999999=>815 line no.");
			DefaultLogger.debug(this, "<<<>>> Inside updateCheckListItemImageDetails(ICheckListItemImageDetail[] checkListItemImageDetails,long checkListItemId) .Line 815.");
			Collection c = getCMRCheckListItemImageDetails();

			if (null == checkListItemImageDetails) {//c=null	checkListItemImageDetails=null
				if ((null == c) || (c.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteCheckListItemImageDetails(new ArrayList(c));
				}
			}
			else if ((null == c) || (c.size() == 0)) {
				List checkListItemImageDetailList = Arrays.asList(checkListItemImageDetails);
				createCheckListItemImageDetails(checkListItemImageDetailList, checkListItemId);
			}
			else {
				Iterator i = c.iterator();
				ArrayList createList = new ArrayList(); // contains list of OBs
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				DefaultLogger.debug(this, "<<<>>> updateCheckListItemImageDetails(ICheckListItemImageDetail[] checkListItemImageDetails,long checkListItemId) .Line 839.");
				while (i.hasNext()) {
					EBCheckListItemImageDetailLocal local = (EBCheckListItemImageDetailLocal) i.next();

					long checkListItemImageDetailId = local.getCheckListItemImageDetailId();
					boolean update = false;
					DefaultLogger.debug(this, "<<<>>> updateCheckListItemImageDetails(ICheckListItemImageDetail[] checkListItemImageDetails,long checkListItemId) .Line 845.=>checkListItemImageDetails.length=>"+checkListItemImageDetails.length);
					for (int j = 0; j < checkListItemImageDetails.length; j++) {
						ICheckListItemImageDetail newOB = checkListItemImageDetails[j];

						if (newOB.getCheckListItemImageDetailId() == checkListItemImageDetailId) {
							// perform update
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						// add for delete
						deleteList.add(local);
					}
				}
				// next identify records for add
				DefaultLogger.debug(this, "<<<>>> updateCheckListItemImageDetails(ICheckListItemImageDetail[] checkListItemImageDetails,long checkListItemId) .Line 862.=>checkListItemImageDetails.length=>"+checkListItemImageDetails.length);
				for (int j = 0; j < checkListItemImageDetails.length; j++) {
					i = c.iterator();
					ICheckListItemImageDetail newOB = checkListItemImageDetails[j];
					boolean found = false;

					while (i.hasNext()) {
						EBCheckListItemImageDetailLocal local = (EBCheckListItemImageDetailLocal) i.next();
						long id = local.getCheckListItemImageDetailId();

						if (newOB.getCheckListItemImageDetailId() == id) {
							DefaultLogger.debug(this, "<<<>>> updateCheckListItemImageDetails(ICheckListItemImageDetail[] checkListItemImageDetails,long checkListItemId) .Line 873=>ID=>"+id);
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				DefaultLogger.debug(this, "<<<>>> updateCheckListItemImageDetails before deleteCheckListItemImageDetails(deleteList) .Line 879.");
				deleteCheckListItemImageDetails(deleteList);
				createCheckListItemImageDetails(createList, checkListItemId);
				DefaultLogger.debug(this, "<<<>>>  updateCheckListItemImageDetails After createCheckListItemImageDetails(createList, checkListItemId) .Line 882.=>checkListItemId=>"+checkListItemId);
			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "<<<>>> Exception: 896 In the updateCheckListItemImageDetails() of EBCheckListItemBean.java");
			e.printStackTrace();
			throw new RuntimeException("Caught Exception: " + e.toString());
		}
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return ICheckListItem
	 */
	public ICheckListItem getValue() throws CheckListException {
		return getValue(false, ICMSConstant.LONG_INVALID_VALUE);
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return ICheckListItem
	 */
	public ICheckListItem getValue(boolean getParentSharedDocOnly, long childCheckListID) throws CheckListException {
		OBCheckListItem value = new OBCheckListItem();
		AccessorUtil.copyValue(this, value);
		IItem item = new OBItem();
		item.setItemCode(getCMPItemCode());
		item.setItemDesc(getCMPItemDesc());
		item.setTenureCount(getTenureCount());
		item.setTenureType(getTenureType());
		item.setMonitorType(getCMPMonitorType());
		item.setIsPreApprove(getIsPreApprove());
		value.setItem(item);
		IShareDoc[] iShareDoc;

		if (getParentSharedDocOnly) {
			DefaultLogger.debug(this, ">>>>>>>>> Item status: " + value.getItemStatus());
			iShareDoc = retrieveDocumentshareParentList(childCheckListID);
		}
		else {
			iShareDoc = retrieveDocumentshareList();
		}

		value.setShareCheckList(iShareDoc);
		
		try {
			value.setCheckListItemImageDetail(retrieveCheckListItemImageDetails());
		} catch (RuntimeException e) {
			throw new EJBException("failed to retrieve Image detail for checklist id ["
					+ value.getCheckListItemID() + "] ", e);
		}
		return value;

	}

	/**
	 * Persist a checklist item information
	 * 
	 * @param anICheckListItem - ICheckListItem
	 */

	// FOR cr-17
	public void setValue(ICheckListItem anICheckListItem) throws CheckListException {
		try {
			DefaultLogger.debug(this, "<<<>>> 945 setValue(ICheckListItem anICheckListItem) of EBCheckListItemBean.java..");
			AccessorUtil.copyValue(anICheckListItem, this, EXCLUDE_METHOD);
			IItem item = anICheckListItem.getItem();
			if (item != null) {
				setCMPItemCode(item.getItemCode());
				setCMPItemDesc(item.getItemDesc());
				setTenureCount(item.getTenureCount());
				setTenureType(item.getTenureType());
				setCMPMonitorType(item.getMonitorType());
				// setCMPItemExpiryDate(item.getExpiryDate());
				setIsPreApprove(item.getIsPreApprove());
			}
			updateDocumentshareList(anICheckListItem.getShareCheckList(), anICheckListItem.getIsDeletedInd());
			
			System.out.println("setValue() => anICheckListItem.getCheckListItemID()=>"+anICheckListItem.getCheckListItemID()+" .... anICheckListItem.getFlagSchedulerCheckItem() =>"+anICheckListItem.getFlagSchedulerCheckItem());
			DefaultLogger.debug(this, "<<<>>> 945 setValue(ICheckListItem anICheckListItem) of EBCheckListItemBean.java..");
			if(!"Y".equals(anICheckListItem.getFlagSchedulerCheckItem())) {
				System.out.println("RECEIVED Status so going for updateCheckListItemImageDetails =>anICheckListItem.getItemStatus()=>"+anICheckListItem.getItemStatus());
//				updateCheckListItemImageDetails(anICheckListItem.getCheckListItemImageDetail() , anICheckListItem.getCheckListItemID());
				System.out.println("updateCheckListItemImageDetails after async method call...88888888=>968 line no.");
			}
			DefaultLogger.debug(this, "<<<>>> 966 EBCheckListItemBean.java => After If condition..");
//			updateCheckListItemImageDetails(anICheckListItem.getCheckListItemImageDetail() , anICheckListItem.getCheckListItemID());
		}
		catch (CheckListException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
	}

	// ************************************************************************
	/**
	 * EJB callback method
	 */
	public void ejbActivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbPassivate() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbLoad() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbStore() {
	}

	/**
	 * EJB callback method
	 */
	public void ejbRemove() {
	}

	/**
	 * EJB Callback Method
	 */
	public void setEntityContext(EntityContext ctx) {
		_context = ctx;
	}

	/**
	 * EJB Callback Method
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	/**
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_CHECKLIST_ITEM;
	}

	// start for CR-17

	public IShareDoc[] getShareCheckList() {
		return null;
	}

	public void setShareCheckList(IShareDoc[] aIShareDocList) {
		// nothing to do
	};

	public boolean getShared() {
		if ((getSharedStr() != null) && getSharedStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public void setShared(boolean shareStatus) {
		if (shareStatus) {
			setSharedStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setSharedStr(ICMSConstant.FALSE_VALUE);
	}

	public boolean getViewable() {
		return false;
	}

	public void setViewable(boolean view) {
		//
	}

	/**
	 * Used to create share docuemt
	 * @param anICheckListItem
	 * @throws CheckListException
	 */
	public void createDocumentshareList(ICheckListItem anICheckListItem) throws CheckListException {
		updateDocumentshareList(anICheckListItem.getShareCheckList(), anICheckListItem.getIsDeletedInd());
	}

	private void updateDocumentshareList(IShareDoc[] anIShareDocList, boolean isItemDeleted) throws CheckListException {
		try {
			Collection coll = getCMRShareCheckList();
			long checkListItemID = getCMPCheckListItemID().longValue();
			if (anIShareDocList == null) {
				if ((coll == null) || (coll.size() == 0)) {
					// (" nothing to do
					return;
				}
				else {
					// (" delete all records Only ");
					deleteDocumentshareList(new ArrayList(coll));
				}
			}
			else if (isItemDeleted) {
				// (" delete all records Only  since item is deleted ");
				deleteDocumentshareList(new ArrayList(coll));
			}
			else if ((coll == null) || (coll.size() == 0)) {
				// (" create all new  records Only  ");
				createDocumentshareList(Arrays.asList(anIShareDocList));
			}
			else {
				// identify identify records for delete or update List
				Iterator iter = coll.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList();
				while (iter.hasNext()) {
					EBDocumentshareLocal local = (EBDocumentshareLocal) iter.next();
					long docShareIdRef = local.getDocShareIdRef();
					boolean update = false;
					for (int ii = 0; ii < anIShareDocList.length; ii++) {
						IShareDoc newOB = anIShareDocList[ii];
						if (newOB.getDocShareIdRef() == docShareIdRef) {
							local.setValue(newOB);
							update = true;
							break;
						}
					}
					if (!update) {
						deleteList.add(local);
					}

				}// next identify records for add

				for (int ii = 0; ii < anIShareDocList.length; ii++) {
					iter = coll.iterator();
					IShareDoc newOB = anIShareDocList[ii];
					boolean found = false;
					while (iter.hasNext()) {
						EBDocumentshareLocal local = (EBDocumentshareLocal) iter.next();
						long docShareIdRef = local.getDocShareIdRef();
						if (newOB.getDocShareIdRef() == docShareIdRef) {
							found = true;
							break;
						}
					}
					if (!found) {
						createList.add(newOB);
					}
				}
				deleteDocumentshareList(deleteList);
				createDocumentshareList(createList);
			}
		}
		catch (CheckListException ex) {
			throw ex;
		}
	}

	private void createDocumentshareList(List aCreationList) throws CheckListException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		long checkListItemID = getCMPCheckListItemID().longValue();
		Collection coll = getCMRShareCheckList();
		Iterator iter = aCreationList.iterator();

		EBDocumentshareLocalHome home = getEBDocumentshareLocalHome();
		while (iter.hasNext()) {
			IShareDoc obj = (OBShareDoc) iter.next();
			// obj.setIsDeletedInd(false);
			try {
				EBDocumentshareLocal local = home.create(new Long(checkListItemID), obj);
				coll.add(local);
			}
			catch (CreateException ex) {
				throw new CheckListException("Failed to create shared document [" + obj + "]", ex);
			}
		}

	}

	private void deleteDocumentshareList(List aDeletionList) throws CheckListException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		Collection coll = getCMRShareCheckList();
		Iterator iter = aDeletionList.iterator();
		while (iter.hasNext()) {
			EBDocumentshareLocal local = (EBDocumentshareLocal) iter.next();
			// local.setIsDeletedInd(true);
			local.setStatus(ICMSConstant.STATE_DELETED);
		}
	}

	protected IShareDoc[] retrieveDocumentshareParentList(long orgChildCheckListID) throws CheckListException {
		long checkListID = getCMPCheckListID().longValue(); // this will
		// give the
		// staging
		// checklist id
		String[] checkListIDs = { String.valueOf(checkListID) };
		ArrayList resultList = populateShareDocDetails(checkListIDs);
		ArrayList childList = retrieveDocumentshareListRaw();
		if (childList != null) {
			IShareDoc childDoc = filterChildItself(orgChildCheckListID, childList);
			populateChildRemarksToParent(resultList, childDoc);
			resultList.addAll(childList);
		}
		return (IShareDoc[]) resultList.toArray(new IShareDoc[0]);
	}

	private IShareDoc filterChildItself(long orgChildCheckListID, ArrayList shareChildList) {
		for (int i = 0; i < shareChildList.size(); i++) {
			IShareDoc shareDoc = (IShareDoc) shareChildList.get(i);
			if (shareDoc.getCheckListId() == orgChildCheckListID) {
				shareChildList.remove(i);
				return shareDoc; // this can be done 'cos biz rule is that the
				// same checklist can only be shared once
			}
		}
		return null;
	}

	protected IShareDoc[] retrieveDocumentshareList() throws CheckListException {
		ArrayList shareDocList = retrieveDocumentshareListRaw();
		return (shareDocList == null) ? null : (IShareDoc[]) shareDocList.toArray(new IShareDoc[0]);
	}

	private ArrayList retrieveDocumentshareListRaw() throws CheckListException {
		Collection coll = getCMRShareCheckList();
		if ((coll == null) || (coll.size() == 0)) {
			return null;
		}
		else {
			ArrayList shareCheckList = new ArrayList();
			ArrayList checkListIDs = new ArrayList();
			Iterator iter = coll.iterator();
			while (iter.hasNext()) {
				EBDocumentshareLocal local = (EBDocumentshareLocal) iter.next();
				IShareDoc obj = local.getValue();
				if (!("DELETED".equals(obj.getStatus()))) {
					shareCheckList.add(obj);
					checkListIDs.add(String.valueOf(obj.getCheckListId()));
				}
				// if (obj.getIsDeletedInd() &&
				// "APPROVED".equals(obj.getStatus())) {
				// }else{
				// shareCheckList.add(obj);
				// }
			}

			// IShareDoc[] shareDocList = (IShareDoc[])
			// shareCheckList.toArray(new IShareDoc[0]);

			// only retrieve the le id, name, security details. The other
			// share doc details are retrieved using the EBDocumentshare
			// prev.
			ArrayList resultList = populateShareDocDetails((String[]) checkListIDs.toArray(new String[0]));
			if (resultList != null) {
				Iterator it = resultList.iterator();
				while (it.hasNext()) {
					IShareDoc resultShareDoc = (IShareDoc) it.next();
					for (int i = 0; i < shareCheckList.size(); i++) {
						IShareDoc orgShareDoc = (IShareDoc) shareCheckList.get(i);
						if (orgShareDoc != null) {
							if (orgShareDoc.getCheckListId() == resultShareDoc.getCheckListId()) {
								orgShareDoc.setLeID(resultShareDoc.getLeID());
								orgShareDoc.setLeName(resultShareDoc.getLeName());
								orgShareDoc.setSecurityDtlId(resultShareDoc.getSecurityDtlId());
								orgShareDoc.setSecurityType(resultShareDoc.getSecurityType());
								orgShareDoc.setSecuritySubType(resultShareDoc.getSecuritySubType());
								break;
							}
						}
					}
				}
			}
			return shareCheckList;
		}
	}

	private void populateChildRemarksToParent(ArrayList parentList, IShareDoc childDoc) {
		if ((parentList == null) || (parentList.size() == 0) || (childDoc == null)) {
			return;
		}

		for (int i = 0; i < parentList.size(); i++) {
			IShareDoc parentDoc = (IShareDoc) parentList.get(i);
			parentDoc.setDetails(childDoc.getDetails());
		}
	}

	protected ArrayList populateShareDocDetails(String[] checkListIDs) throws SearchDAOException {
		return CheckListDAOFactory.getCheckListDAO().getCheckListDetailsByCheckListId(checkListIDs);
	}

	protected EBDocumentshareLocalHome getEBDocumentshareLocalHome() throws CheckListException {
		EBDocumentshareLocalHome home = (EBDocumentshareLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CHECKLIST_DOCUMENT_SHARE_LOCAL_JNDI, EBDocumentshareLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListException("EBDocumentshareLocalHome is null!");
	}
	
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
    
	public abstract String getCpsId();
	public abstract void setCpsId(String cpsId);
    
    /*
	 * End 
	 * 
	 */
	
	 /* Uma Khot:Insurance Deferral maintainance */
	 
	public abstract String getInsuranceId();
	public abstract void setInsuranceId(String insuranceId);

	public abstract String getRocCharge();
	public abstract void setRocCharge(String rocCharge);
	
	public String getFlagSchedulerCheckItem() {
		return null;
	}
	public void setFlagSchedulerCheckItem(String flagSchedulerCheckItem) {};
	public String getReferenceType() {
		return null;
	}
	public void setReferenceType(String referenceType) {
		
	}
}