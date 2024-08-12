/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBConvenantBean.java,v 1.9 2006/07/25 09:08:52 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for convenant Information
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.9 $
 * @since $Date: 2006/07/25 09:08:52 $ Tag: $Name: $
 */
public abstract class EBConvenantBean implements EntityBean, IConvenant {
	private static final String[] EXCLUDE_METHOD = new String[] { "getConvenantID", "getConvenantRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBConvenantBean() {
	}

	// ************** Abstract methods ************
	public abstract Long getCMPConvenantID();

	public abstract String getIsVerifiedIndStr();

	public abstract String getIsDeletedIndStr();

	public abstract Long getCMPCheckListID();

	public abstract long getConvenantRef();

	// cr26
	public abstract String getChaseReminderIndStr();

	public abstract String getIsOneOffIndStr();

	public abstract Collection getCMRConvenantSubItems();

	public abstract String getRiskTriggerStr();

	// cr 234
	public abstract String getFeeStr();

	public abstract String getIsParameterizedDescStr();

	public abstract void setCMPConvenantID(Long aCheckListItemID);

	public abstract void setIsVerifiedIndStr(String anIsVerifiedIndStr);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setCMPCheckListID(Long aCheckListID);

	// cr26
	public abstract void setChaseReminderIndStr(String aChaseReminderIndStr);

	public abstract void setIsOneOffIndStr(String isOneOffIndStr);

	public abstract void setCMRConvenantSubItems(Collection aConvenantSubItems);

	public abstract void setRiskTriggerStr(String riskTriggerStr);

	// cr 234
	public abstract void setFeeStr(String feeStr);

	public abstract Date getDateChecked();

	public abstract void setIsParameterizedDescStr(String isParameterizedDescStr);

	public abstract String getDescription();

	public abstract int getEndDateChangedCount();

	public abstract int getFrequency();

	public abstract String getFrequencyUnit();

	public abstract int getGracePeriod();

	public abstract String getGracePeriodUnit();

	public abstract Date getInitialDocEndDate();

	public abstract Date getInitialDueDate();

	public abstract Date getLastDocEntryDate();

	public abstract String getRemarks();

	public abstract void setConvenantRef(long convenantRef);

	public abstract void setDateChecked(Date dateChecked);

	public abstract void setDescription(String description);

	public abstract void setEndDateChangedCount(int count);

	public abstract void setFrequency(int frequency);

	public abstract void setFrequencyUnit(String frequencyUnit);

	public abstract void setGracePeriod(int gracePeriod);

	public abstract void setGracePeriodUnit(String gracePeriodUnit);

	public abstract void setInitialDocEndDate(Date anInitialDocEndDate);

	public abstract void setInitialDueDate(Date initialDueDate);

	public abstract void setLastDocEntryDate(Date lastDocEntryDate);

	public abstract void setRemarks(String remarks);

	// ************* Non-persistent methods ***********
	/**
	 * Helper method to get the checklist item ID
	 * @return long - the long value of the checklist item
	 */
	public long getConvenantID() {
		if (getCMPConvenantID() != null) {
			return getCMPConvenantID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get the verified indicator
	 * @return boolean - true if it is to be deleted and false otherwise
	 */
	public boolean getIsVerifiedInd() {
		if ((getIsVerifiedIndStr() != null) && getIsVerifiedIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Get the convenant status if it is verified
	 * @return String - the convenant status
	 */
	public String getConvenantStatus() {
		if (getIsVerifiedInd()) {
			return ICMSConstant.CONVENANT_VERIFIED;
		}
		return ICMSConstant.CONVENANT_NOT_VERIFIED;
	}

	/**
	 * Not implemented
	 */
	public boolean isProcessingAllowed() {
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

	// cr26
	public boolean getChaseReminderInd() {
		if ((getChaseReminderIndStr() != null) && getChaseReminderIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to get the one-off indicator
	 * @return boolean - true if it is one-off and false otherwise
	 */
	public boolean getIsOneOffInd() {
		if ((getIsOneOffIndStr() != null) && getIsOneOffIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public IConvenantSubItem[] getConvenantSubItemList() {
		return null;
	}

	public IConvenantSubItem[] getSubItemsByCondition(String cond) {
		return null;
	}

	/**
	 * Helper method to get the risk trigger indicator
	 * @return boolean - true if it is risk trigger and false otherwise
	 */
	public boolean getRiskTrigger() {
		if ((getRiskTriggerStr() != null) && getRiskTriggerStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to get the Fee indicator
	 * @return boolean - true if it is Fee and false otherwise
	 */
	public boolean getFee() {
		if ((getFeeStr() != null) && getFeeStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	public boolean getIsParameterizedDesc() {
		if ((getIsParameterizedDescStr() != null) && getIsParameterizedDescStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Helper method to set the checklist item ID
	 * @param aConvenantID - long
	 */
	public void setConvenantID(long aConvenantID) {
		setCMPConvenantID(new Long(aConvenantID));
	}

	/**
	 * Helper method to set verified indicator
	 * @param anIsVerifiedInd - boolean
	 */
	public void setIsVerifiedInd(boolean anIsVerifiedInd) {
		if (anIsVerifiedInd) {
			setIsVerifiedIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsVerifiedIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Not implemented
	 */
	public void setConvenantStatus(String aConvenantStatus) {
		// do nothing
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

	public void setChaseReminderInd(boolean aChaseReminderInd) {
		if (aChaseReminderInd) {
			setChaseReminderIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setChaseReminderIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set one-off indicator
	 * @param isOneOffInd - boolean
	 */
	public void setIsOneOffInd(boolean isOneOffInd) {
		if (isOneOffInd) {
			setIsOneOffIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsOneOffIndStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set risk trigger indicator
	 * @param riskTrigger - boolean
	 */
	public void setRiskTrigger(boolean riskTrigger) {
		if (riskTrigger) {
			setRiskTriggerStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setRiskTriggerStr(ICMSConstant.FALSE_VALUE);
	}

	/**
	 * Helper method to set Fee indicator
	 * @param fee - boolean
	 */
	public void setFee(boolean fee) {
		if (fee) {
			setFeeStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setFeeStr(ICMSConstant.FALSE_VALUE);
	}

	public void setIsParameterizedDesc(boolean isParameterizedDesc) {
		if (isParameterizedDesc) {
			setIsParameterizedDescStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setIsParameterizedDescStr(ICMSConstant.FALSE_VALUE);
	}

	public void setConvenantSubItemList(IConvenantSubItem[] aSubItemList) {
		// do nothing
	}

	public void updateSubItem(int anItemIndex, IConvenantSubItem anISubtem) {
		// do nothing
	}

	public abstract String getSourceId();

	public abstract void setSourceId(String sourceId);

	// *****************************************************
	/**
	 * Create a checklist item Information
	 * 
	 * @param aCheckListID - Long
	 * @param anIConvenant of IConvenant type
	 * @return Long - the checklist item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(Long aCheckListID, IConvenant anIConvenant) throws CreateException {
		if (anIConvenant == null) {
			throw new CreateException("IConvenant is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setCMPConvenantID(new Long(pk));
			DefaultLogger.debug(this, "ConvenantRef: " + anIConvenant.getConvenantRef());
			if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == anIConvenant
					.getConvenantRef()) {
				setConvenantRef(pk);
			}
			else {
				setConvenantRef(anIConvenant.getConvenantRef());
			}
			AccessorUtil.copyValue(anIConvenant, this, EXCLUDE_METHOD);
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception at ejbCreate: " + ex.toString());
		}
	}

	/**
	 * Post-Create a record
	 * @param aCheckListID - the checkList ID (parent of this checkList item)
	 * @param anIConvenant of IConvenant type
	 */
	public void ejbPostCreate(Long aCheckListID, IConvenant anIConvenant) throws CreateException {
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return IConvenant
	 */
	public IConvenant getValue() throws RecurrentException {
		IConvenant value = new OBConvenant();
		AccessorUtil.copyValue(this, value);
		IConvenantSubItem[] itemList = retrieveSubItems();
		if ((itemList != null) && (itemList.length > 1)) {
			Arrays.sort(itemList);
		}
		value.setConvenantSubItemList(itemList);
		return value;
	}

	private IConvenantSubItem[] retrieveSubItems() throws RecurrentException {
		try {
			Collection col = getCMRConvenantSubItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBConvenantSubItemLocal local = (EBConvenantSubItemLocal) iter.next();
					IConvenantSubItem obj = local.getValue();
					if (!obj.getIsDeletedInd()) {
						itemList.add(obj);
					}
				}
				return (IConvenantSubItem[]) itemList.toArray(new IConvenantSubItem[0]);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception at retrieveSubItems: " + ex.toString());
		}
	}

	/**
	 * Persist a checklist item information
	 * 
	 * @param anIConvenant - ICheckListItem
	 */
	public void setValue(IConvenant anIConvenant) throws RecurrentException {
		AccessorUtil.copyValue(anIConvenant, this, EXCLUDE_METHOD);
		updateSubItems(anIConvenant.getConvenantSubItemList());
	}

	public void createDependents(IConvenant anIConvenant) throws RecurrentException {
		updateSubItems(anIConvenant.getConvenantSubItemList());
	}

	//
	private void updateSubItems(IConvenantSubItem[] aSubItemList) throws RecurrentException {
		if (aSubItemList != null) {
			// for (int i = 0; i<aSubItemList.length; i++) {
			// DefaultLogger.debug(this, "~~~~~~~~~~item: " +
			// aSubItemList[i].getSubItemID() + ",ref: " +
			// aSubItemList[i].getSubItemRef());
			// }
			try {
				Collection col = getCMRConvenantSubItems();
				// if (col != null) {
				// for (Iterator iter = col.iterator(); iter.hasNext();) {
				// EBConvenantSubItemLocal local =
				// (EBConvenantSubItemLocal)iter.next();
				// long subItemRef = local.getSubItemRef();
				// DefaultLogger.debug(this, "~~~~~~~~~ cmr: " +
				// local.getSubItemID() + ",ref: " + subItemRef);
				// }
				// }
				if (aSubItemList == null) {
					if ((col == null) || (col.size() == 0)) {
						return; // nothing to do
					}
					else {
						// delete all records
						deleteSubItems(new ArrayList(col));
					}
				}
				else if ((col == null) || (col.size() == 0)) {
					// create new records
					createSubItems(Arrays.asList(aSubItemList));
				}
				else {
					Iterator iter = col.iterator();
					ArrayList createList = new ArrayList();
					ArrayList deleteList = new ArrayList(); // contains list of
					// local interfaces

					// identify identify records for delete or udpate first
					while (iter.hasNext()) {
						EBConvenantSubItemLocal local = (EBConvenantSubItemLocal) iter.next();
						long subItemRef = local.getSubItemRef();
						boolean update = false;

						for (int ii = 0; ii < aSubItemList.length; ii++) {
							IConvenantSubItem newOB = aSubItemList[ii];
							if (newOB.getSubItemRef() == subItemRef) {
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
					for (int ii = 0; ii < aSubItemList.length; ii++) {
						iter = col.iterator();
						IConvenantSubItem newOB = aSubItemList[ii];
						boolean found = false;

						while (iter.hasNext()) {
							EBConvenantSubItemLocal local = (EBConvenantSubItemLocal) iter.next();
							long ref = local.getSubItemRef();

							if (newOB.getSubItemRef() == ref) {
								found = true;
								break;
							}
						}
						if (!found) {
							// add for adding
							createList.add(newOB);
						}
					}
					deleteSubItems(deleteList);
					createSubItems(createList);
				}
			}
			catch (RecurrentException ex) {
				throw ex;
			}
			catch (Exception ex) {
				throw new RecurrentException("Exception in updateSubItems: " + ex.toString());
			}
		}
	}

	private void deleteSubItems(List aDeletionList) throws RecurrentException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRConvenantSubItems();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBConvenantSubItemLocal local = (EBConvenantSubItemLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception in deleteSubItems: " + ex.toString());
		}
	}

	public List createSubItems(List aCreationList) throws RecurrentException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return null; // do nothing
		}
		List createdList = new ArrayList();
		Collection col = getCMRConvenantSubItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBConvenantSubItemLocalHome home = getEBConvenantSubItemLocalHome();
			while (iter.hasNext()) {
				IConvenantSubItem obj = (IConvenantSubItem) iter.next();
				DefaultLogger.debug(this, "Creating CheckList Sub Item ID: " + obj.getSubItemID());
				EBConvenantSubItemLocal local = home.create(new Long(getConvenantID()), obj);
				createdList.add(local.getValue());
				col.add(local);
			}
			return createdList;
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception in createSubItems: " + ex.toString());
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
		return ICMSConstant.SEQUENCE_CONVENANT;
	}

	protected EBConvenantSubItemLocalHome getEBConvenantSubItemLocalHome() throws RecurrentException {
		EBConvenantSubItemLocalHome home = (EBConvenantSubItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CONVENANT_SUB_ITEM_LOCAL_JNDI, EBConvenantSubItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBConvenantSubItemLocalHome is null!");
	}
}