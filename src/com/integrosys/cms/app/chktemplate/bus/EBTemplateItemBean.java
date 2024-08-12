/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBTemplateItemBean.java,v 1.16 2003/10/28 08:56:24 hltan Exp $
 */
package com.integrosys.cms.app.chktemplate.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.FinderException;

import org.springframework.beans.BeanUtils;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.TypeConverter;
import com.integrosys.cms.app.documentlocation.bus.IDocumentAppTypeItem;
import com.integrosys.cms.app.documentlocation.bus.OBDocumentAppTypeItem;

/**
 * This entity bean represents the persistence for template item Information
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.16 $
 * @since $Date: 2003/10/28 08:56:24 $ Tag: $Name: $
 */
public abstract class EBTemplateItemBean implements EntityBean, ITemplateItem {

	private static final long serialVersionUID = 5801771695459817870L;

	private static final String[] EXCLUDE_METHOD = new String[] { "getTemplateItemID" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBTemplateItemBean() {
	}

	// ************** Abstract methods ************
	public abstract Long getCMPTemplateItemID();

	public abstract String getIsMandatoryIndStr();
	
	public abstract String getIsMandatoryDisplayIndStr();

	public abstract String getIsMandatoryForBorrowerIndStr();

	public abstract String getIsMandatoryForPledgorIndStr();

	public abstract String getIsInVaultIndStr();

	public abstract String getIsExtCustIndStr();

	public abstract String getIsAuditIndStr();

	public abstract String getIsDeletedIndStr();

	public abstract EBItemLocal getCMRItem();

	public abstract Long getCMPTemplateID();

	public abstract Long getCMPItemID();

	public abstract Long getCMPParentItemID();

	// Getter methods for the template item attributes
	public abstract String getCMPItemCode();

	public abstract String getCMPItemDesc();

	public abstract Date getCMPItemExpiryDate();

	public abstract String getCMPMonitorType();

	public abstract String getCMPDocumentVersion();

	public abstract String getCMPIsForBorrower();

	public abstract String getCMPIsForPledgor();

	public abstract String getCMPIsPreApprove();

	public abstract String getCMPLoanApplicationType();

	public abstract void setCMPTemplateItemID(Long aTemplateItemID);

	public abstract void setIsMandatoryIndStr(String anIsMandatoryIndStr);
	
	public abstract void setIsMandatoryDisplayIndStr(String anIsMandatoryDisplayIndStr);

	public abstract void setIsMandatoryForBorrowerIndStr(String flag);

	public abstract void setIsMandatoryForPledgorIndStr(String flag);

	public abstract void setIsInVaultIndStr(String anIsInVaultIndStr);

	public abstract void setIsExtCustIndStr(String anIsExtCustIndStr);

	public abstract void setIsAuditIndStr(String anIsAuditIndStr);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setCMRItem(EBItemLocal anEBItemLocal);

	public abstract void setCMPTemplateID(Long aTemplateID);

	public abstract void setCMPItemID(Long anItemID);

	public abstract void setCMPParentItemID(Long aParentItemID);

	// Setter methods for the template item attributes
	public abstract void setCMPItemCode(String anItemCode);

	public abstract void setCMPItemDesc(String anItemDesc);

	public abstract void setCMPItemExpiryDate(Date anItemExpiryDate);

	public abstract void setCMPMonitorType(String aMonitorType);

	public abstract void setCMPDocumentVersion(String docVersion);

	public abstract void setCMPIsForBorrower(String flag);

	public abstract void setCMPIsForPledgor(String flag);

	public abstract void setCMPIsPreApprove(String flag);

	public abstract void setCMPLoanApplicationType(String type);
	
	public abstract void setWithTitle(boolean withTitle);
	
	public abstract boolean getWithTitle();
	
	public abstract boolean getWithoutTitle();

	public abstract void setWithoutTitle(boolean withoutTitle);

	public abstract boolean getUnderConstruction();

	public abstract void setUnderConstruction(boolean underConstruction);
	public abstract boolean getPropertyCompleted();

	public abstract void setPropertyCompleted(boolean propertyCompleted);

	public abstract boolean getNewWithFBR();

	public abstract void setNewWithFBR(boolean newWithFBR);

	public abstract boolean getNewWithoutFBR();

	public abstract void setNewWithoutFBR(boolean newWithoutFBR);
	public abstract boolean getUsedWithoutFBR();

	public abstract void setUsedWithoutFBR(boolean usedWithoutFBR);

	public abstract boolean getUsedWithFBR();

	public abstract void setUsedWithFBR(boolean usedWithFBR);
	
	public abstract int getTenureCount();
	
	public abstract String getTenureType();
	
    public abstract void setTenureCount(int tenureCount);
    
    public abstract void setTenureType(String tenureType);
   
    
   public abstract String getStatementType();
	
	public abstract void setStatementType(String anStatementType);
	
	public abstract String getIsRecurrent();

	public abstract void setIsRecurrent(String isRecurrent);
	
	public abstract String getRating();
	
	public abstract void setRating(String rating) ;

	public abstract String getSegment();

	public abstract void setSegment(String segment);

	public abstract String getTotalSancAmt();

	public abstract void setTotalSancAmt(String totalSancAmt);

	public abstract String getClassification() ;

	public abstract void setClassification(String classification);

	public abstract String getGuarantor() ;

	public abstract void setGuarantor(String guarantor) ;

	public abstract Date getCreationDate();
	public abstract void setCreationDate(Date creationDate);
	public abstract Date getLastUpdateDate() ;
	public abstract void setLastUpdateDate(Date lastUpdateDate) ;
	
	// ************** CMR methods ***************
	public abstract Collection getCMRPropertyList();

	public abstract void setCMRPropertyList(Collection propertyList);
	
	public abstract String getFacilityCategory();

	public abstract void setFacilityCategory(String facilityCategory);

	public abstract String getFacilityType();

	public abstract void setFacilityType(String facilityType);
	public abstract String getSystem();

	public abstract void setSystem(String system) ;

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the template item ID
	 * @return long - the long value of the template item
	 */
	public long getTemplateItemID() {
		if (getCMPTemplateItemID() != null) {
			return getCMPTemplateItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get mandatory indicator This has been changed to be used
	 * for collateral items only. CC items will be using IsMandatoryForBorrower
	 * & IsMandatoryForPledgor
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
	 * Get the borrower mandatory indicator CC items will be using either
	 * IsMandatoryForBorrower or IsMandatoryForPledgor
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryForBorrowerInd() {
		if ((getIsMandatoryForBorrowerIndStr() != null)
				&& getIsMandatoryForBorrowerIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Get the pledgor/guarantor/chargor mandatory indicator CC items will be
	 * using either IsMandatoryForBorrower or IsMandatoryForPledgor
	 * @return boolean - true if it is mandatory and false otherwise
	 */
	public boolean getIsMandatoryForPledgorInd() {
		if ((getIsMandatoryForPledgorIndStr() != null)
				&& getIsMandatoryForPledgorIndStr().equals(ICMSConstant.TRUE_VALUE)) {
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

	/**
	 * Helper method that determine if a template item is inherited or not
	 * @return boolean - true if it is inherited and false otherwise
	 */
	public boolean isInherited() {
		if (getParentItemID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to check if an item is selected from the global list of
	 * items
	 * @return boolean - true if it is from global list and false otherwise
	 */
	public boolean isFromGlobal() {
		if (getItem() != null) {
			if (getItem().getItemID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Helper method to get the long value of the parent item ID
	 * @return long - the long value of the parent item ID
	 */
	public long getParentItemID() {
		if (getCMPParentItemID() != null) {
			return getCMPParentItemID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
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
	public String getItemDesc() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public IItem getItem() {
		return null;
	}

	/**
	 * Helper method to set the template item ID
	 * @param aTemplateItemID - long
	 */
	public void setTemplateItemID(long aTemplateItemID) {
		setCMPTemplateItemID(new Long(aTemplateItemID));
	}

	/**
	 * Helper method to set mandatory indicator This has been changed to be used
	 * for collateral items only. CC items will be using IsMandatoryForBorrower
	 * & IsMandatoryForPledgor
	 * @param anIsMandatoryInd - boolean
	 */
	public void setIsMandatoryInd(boolean anIsMandatoryInd) {
		if (anIsMandatoryInd) {
			setIsMandatoryIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsMandatoryIndStr(ICMSConstant.FALSE_VALUE);
	}

	public void setIsMandatoryDisplayInd(boolean anIsMandatoryDisplayInd) {
		if (anIsMandatoryDisplayInd) {
			setIsMandatoryDisplayIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsMandatoryDisplayIndStr(ICMSConstant.FALSE_VALUE);
	}
	/**
	 * Set the borrower mandatory indicator CC items will be using either
	 * IsMandatoryForBorrower or IsMandatoryForPledgor
	 * @param flag - boolean
	 */
	public void setIsMandatoryForBorrowerInd(boolean flag) {
		if (flag) {
			setIsMandatoryForBorrowerIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsMandatoryForBorrowerIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Set the pledgor/guarantor/chargor mandatory indicator CC items will be
	 * using either IsMandatoryForBorrower or IsMandatoryForPledgor
	 * @param flag - boolean
	 */
	public void setIsMandatoryForPledgorInd(boolean flag) {
		if (flag) {
			setIsMandatoryForPledgorIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsMandatoryForPledgorIndStr(ICMSConstant.FALSE_VALUE);
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
	 * @param anIsExtCustInd - boolean
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
	 * Helper method to set deleted indicator
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
	 * Helper method to set the parent item ID
	 * @param aParentItemID - long
	 */
	public void setParentItemID(long aParentItemID) {
        if (aParentItemID != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
			setCMPParentItemID(new Long(aParentItemID));
		} else {
            setCMPParentItemID(null);  
        }
	}

	/**
	 * Not implemented here
	 */
	public void setItem(IItem anIItem) {
		// do nothing
	}

	/**
	 * Create a template item Information
	 * 
	 * @param aTemplateID - Long
	 * @param anITemplateItem - the template item
	 * @return Long - the template item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(Long aTemplateID, ITemplateItem anITemplateItem) throws CreateException {
		if (anITemplateItem == null) {
			throw new CreateException("ITemplateItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			AccessorUtil.copyValue(anITemplateItem, this, EXCLUDE_METHOD);
			IItem item = anITemplateItem.getItem();

			if (item != null) {
				setCMPItemCode(item.getItemCode());
				setCMPItemDesc(item.getItemDesc());
				setCMPItemExpiryDate(item.getExpiryDate());
				setTenureCount(item.getTenureCount());
				setTenureType(item.getTenureType());
				setCMPMonitorType(item.getMonitorType());
				setCMPDocumentVersion(item.getDocumentVersion());
				setCMPIsForBorrower(item.getIsForBorrower() ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
				setCMPIsForPledgor(item.getIsForPledgor() ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
				setCMPIsPreApprove(item.getIsPreApprove() ? ICMSConstant.TRUE_VALUE : ICMSConstant.FALSE_VALUE);
				//setCMPLoanApplicationType(item.getLoanApplicationType());
			}
			setTemplateItemID(pk);
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("failed to create checklist template");
			cex.initCause(ex);
			throw cex;
		}
	}

	/**
	 * Post-Create a record
	 * 
	 * @param aTemplateID - the template ID (parent of this template item)
	 * @param anITemplateItem - the template item
	 */
	public void ejbPostCreate(Long aTemplateID, ITemplateItem anITemplateItem) throws CreateException {
		try {
			IItem item = anITemplateItem.getItem();
			if (item != null) {
				if (item.getItemID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					EBItemLocalHome home = getEBItemLocalHome();
					EBItemLocal local = home.findByPrimaryKey(new Long(item.getItemID()));
					local.updateDocumentAppItem(item);
					setCMRItem(local);
					return;
				}
			}
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("Unable to set item since it does not exist!");
			cex.initCause(ex);
			throw cex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			CreateException cex = new CreateException("failed to post create checklist template");
			cex.initCause(ex);
			throw cex;
		}
	}

	/**
	 * Return the interface representation of this object
	 * @return ITemplateItem
	 * @throws com.integrosys.cms.app.chktemplate.bus.CheckListTemplateException
	 *         on errors
	 */
	public ITemplateItem getValue() throws CheckListTemplateException {
		OBTemplateItem value = new OBTemplateItem();
		//AccessorUtil.copyValue(this, value);
		BeanUtils.copyProperties(this, value);
		IItem item = retrieveItem();
		item.setPropertyList(retrievePropertyList());
		value.setItem(item);
		return value;
	}

	/**
	 * To retrieve the item related to this template item
	 * @return IItem - the item that is related to this template item
	 * @throws CheckListTemplateException on errors
	 */
	private IItem retrieveItem() throws CheckListTemplateException {
		try {
			EBItemLocal local = getCMRItem();
			if (local != null) {
				return local.getValue();
			}

			IItem item = new OBItem();
			item.setItemCode(getCMPItemCode());
			item.setItemDesc(getCMPItemDesc());
			item.setExpiryDate(getCMPItemExpiryDate());
			item.setMonitorType(getCMPMonitorType());
			item.setDocumentVersion(getCMPDocumentVersion());
			item.setIsForBorrower(TypeConverter.convertStringToBooleanEquivalent(getCMPIsForBorrower()));
			item.setIsForPledgor(TypeConverter.convertStringToBooleanEquivalent(getCMPIsForPledgor()));
			item.setIsPreApprove(TypeConverter.convertStringToBooleanEquivalent(getCMPIsPreApprove()));
			//item.setLoanApplicationType(getCMPLoanApplicationType());
			
			//String[] appList = item.getLoanApplicationType().split("-");
			List docAppListing = new ArrayList();
			
//			for(int i = 0; i < appList.length; i++)
//			{
//				IDocumentAppTypeItem newObj = new OBDocumentAppTypeItem();
//				newObj.setAppType(appList[i]);
//				docAppListing.add(newObj);
//			}
//			
//			item.setCMRDocAppItemList(docAppListing);
	
			return item;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CheckListTemplateException("failed to retrieve global item to be populated into template ", ex);
		}
	}
	


	/**
	 * Persist a template item information
	 * 
	 * @param anITemplateItem - ITemplateItem
	 */
	public void setValue(ITemplateItem anITemplateItem) throws CheckListTemplateException {
		AccessorUtil.copyValue(anITemplateItem, this, EXCLUDE_METHOD);
		IItem item = anITemplateItem.getItem();
		if (item != null) {
			setCMPItemCode(item.getItemCode());
			setCMPItemDesc(item.getItemDesc());
			setCMPItemExpiryDate(item.getExpiryDate());
			setCMPMonitorType(item.getMonitorType());
			setCMPDocumentVersion(item.getDocumentVersion());
			setCMPIsForBorrower(TypeConverter.convertBooleanToStringEquivalent(item.getIsForBorrower()));
			setCMPIsForPledgor(TypeConverter.convertBooleanToStringEquivalent(item.getIsForPledgor()));
			setCMPIsPreApprove(TypeConverter.convertBooleanToStringEquivalent(item.getIsPreApprove()));
			//setCMPLoanApplicationType(item.getLoanApplicationType());
			updatePropertyList(item.getPropertyList());
		}
	}

	private IDynamicProperty[] retrievePropertyList() throws CheckListTemplateException {
		try {
			Collection col = getCMRPropertyList();

			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBDynamicPropertyLocal local = (EBDynamicPropertyLocal) iter.next();
					IDynamicProperty obj = local.getValue();
					if (obj.getStatus().equals(ICMSConstant.STATE_ACTIVE)) {
						itemList.add(obj);
					}
				}
				return (IDynamicProperty[]) itemList.toArray(new IDynamicProperty[0]);
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("failed to retrieve checklist dynamic property list ", ex);
		}

	}

	/**
	 * Create the dynamic properties for this item
	 * @param templateItem - ITemplateItem that the dynamic properties is
	 *        applicable to
	 * @throws CheckListTemplateException
	 */
	public void createPropertyList(ITemplateItem templateItem) throws CheckListTemplateException {
		IItem item = templateItem.getItem();
		updatePropertyList(item.getPropertyList());
	}

	public void updatePropertyList(IDynamicProperty[] propertyList) throws CheckListTemplateException {
		try {
			Collection col = getCMRPropertyList(); // original list in the db

			if (propertyList == null) { // No dynamic properties or all dynamic
				// properties deleted
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					deletePropertyList(new ArrayList(col)); // delete all
					// records
				}

			}
			else if ((col == null) || (col.size() == 0)) {
				createPropertyList(Arrays.asList(propertyList)); // create new
				// records

			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
				// local interfaces

				// identify records for delete or udpate first
				while (iter.hasNext()) {
					EBDynamicPropertyLocal local = (EBDynamicPropertyLocal) iter.next();
					long referenceID = local.getReferenceID();
					boolean update = false;

					for (int i = 0; i < propertyList.length; i++) {
						IDynamicProperty newOB = propertyList[i];
						if (newOB.getReferenceID() == referenceID) {
							local.setValue(newOB); // perform update
							update = true;
							break;
						}
					}

					if (!update) {
						deleteList.add(local); // add for delete
					}
				}

				// next identify records for add
				for (int k = 0; k < propertyList.length; k++) {
					iter = col.iterator();
					IDynamicProperty newOB = propertyList[k];
					boolean found = false;

					while (iter.hasNext()) {
						EBDynamicPropertyLocal local = (EBDynamicPropertyLocal) iter.next();
						long referenceID = local.getReferenceID();

						if (newOB.getReferenceID() == referenceID) {
							found = true;
							break;
						}
					}

					if (!found) {
						createList.add(newOB); // add for adding
					}
				}
				deletePropertyList(deleteList);
				createPropertyList(createList);
			}

		}
		catch (Exception ex) {
			throw new CheckListTemplateException("failed to update dynamic property list ", ex);
		}

	}

	/**
	 * Create the list of checklist items under the current checklist
	 * @param aCreationList - List
	 * @throws CheckListTemplateException on errors
	 */
	private void createPropertyList(List aCreationList) throws CheckListTemplateException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRPropertyList();
		Iterator iter = aCreationList.iterator();
		try {
			EBDynamicPropertyLocalHome home = getEBDynamicPropertyLocalHome();
			while (iter.hasNext()) {
				IDynamicProperty obj = (IDynamicProperty) iter.next();
				EBDynamicPropertyLocal local = home.create(new Long(getTemplateItemID()), obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("failed to create dynamic property list", ex);
		}
	}

	/**
	 * Delete the list of checklist items under the current checklist
	 * @param aDeletionList - List
	 * @throws CheckListTemplateException on errors
	 */
	private void deletePropertyList(List aDeletionList) throws CheckListTemplateException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBDynamicPropertyLocal local = (EBDynamicPropertyLocal) iter.next();
				// col.remove(local);
				// local.remove();
				local.setStatus(ICMSConstant.STATE_DELETED);
			}
		}
		catch (Exception ex) {
			throw new CheckListTemplateException("failed to remove dynamic property list", ex);
		}
	}

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
	 * Method to get EB Local Home for item
	 */
	protected EBItemLocalHome getEBItemLocalHome() throws CheckListTemplateException 
	{
		EBItemLocalHome home = (EBItemLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_ITEM_LOCAL_JNDI,
				EBItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListTemplateException("EBItemLocal is null!");
	}

	/**
	 * Method to get EB Local Home for the item dynamic properties
	 */
	protected EBDynamicPropertyLocalHome getEBDynamicPropertyLocalHome() throws CheckListTemplateException {
		EBDynamicPropertyLocalHome home = (EBDynamicPropertyLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DYNAMIC_PROPERTY_LOCAL_JNDI, EBDynamicPropertyLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new CheckListTemplateException("EBDynamicPropertyLocalHome is null!");
	}

	/**
	 * Get the name of the sequence to be used
	 * @return String - the name of the sequence
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_TEMPLATE_ITEM;
	}
}