/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListItemBean.java,v 1.16 2006/07/25 09:08:52 jychong Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

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
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.chktemplate.bus.IItem;
import com.integrosys.cms.app.chktemplate.bus.OBItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * This entity bean represents the persistence for checklist item Information
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.16 $
 * @since $Date: 2006/07/25 09:08:52 $ Tag: $Name: $
 */
public abstract class EBRecurrentCheckListItemBean implements EntityBean, IRecurrentCheckListItem {
	private static final String[] EXCLUDE_METHOD = new String[] { "getCheckListItemID", "getCheckListItemRef" };

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	/**
	 * Default Constructor
	 */
	public EBRecurrentCheckListItemBean() {
	}

	// ************** Abstract methods ************
	public abstract Long getCMPCheckListItemID();

	public abstract String getChaseReminderIndStr();

	public abstract String getIsDeletedIndStr();

	public abstract String getIsOneOffIndStr();

	public abstract Long getCMPCheckListID();

	public abstract String getCMPItemDesc();

	public abstract Collection getCMRCheckListSubItems();

	public abstract long getCheckListItemRef();

	public abstract void setCMPCheckListItemID(Long aCheckListItemID);

	public abstract void setChaseReminderIndStr(String aChaseReminderIndStr);

	public abstract void setIsDeletedIndStr(String anIsDeletedIndStr);

	public abstract void setIsOneOffIndStr(String isOneOffIndStr);

	public abstract void setCMPCheckListID(Long aCheckListID);

	public abstract void setCMPItemDesc(String anItemDesc);

	public abstract void setCMRCheckListSubItems(Collection aCheckListSubItems);

	public abstract int getEndDateChangedCount();

	public abstract int getFrequency();

	public abstract String getFrequencyUnit();

	public abstract int getGracePeriod();

	public abstract String getGracePeriodUnit();

	public abstract Date getInitialDocEndDate();

	public abstract Date getInitialDueDate();

	public abstract Date getLastDocEntryDate();

	public abstract String getRemarks();

	public abstract void setCheckListItemRef(long checkListItemRef);

	public abstract void setEndDateChangedCount(int count);

	public abstract void setFrequency(int frequency);

	public abstract void setFrequencyUnit(String frequencyUnit);

	public abstract void setGracePeriod(int gracePeriod);

	public abstract void setGracePeriodUnit(String gracePeriodUnit);

	public abstract void setInitialDocEndDate(Date anInitialDocEndDate);

	public abstract void setInitialDueDate(Date initialDueDate);

	public abstract void setLastDocEntryDate(Date lastDocEntryDate);

	public abstract void setRemarks(String remarks);
	
	//********* Added for Annexure & DP ************
	
	public abstract String getDocType();
	
	public abstract void setDocType(String docType);

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

	public boolean getChaseReminderInd() {
		if ((getChaseReminderIndStr() != null) && getChaseReminderIndStr().equals(ICMSConstant.TRUE_VALUE)) {
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
	 * Helper method to get the one-off indicator
	 * @return boolean - true if it is one-off and false otherwise
	 */
	public boolean getIsOneOffInd() {
		if ((getIsOneOffIndStr() != null) && getIsOneOffIndStr().equals(ICMSConstant.TRUE_VALUE)) {
			return true;
		}
		return false;
	}

	/**
	 * Not implemented here
	 */
	public IItem getItem() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public String getItemDesc() {
		return null;
	}

	public IRecurrentCheckListSubItem[] getRecurrentCheckListSubItemList() {
		return null;
	}

	public IRecurrentCheckListSubItem[] getSubItemsByCondition(String cond) {
		return null;
	}

	/**
	 * public IRecurrentCheckListSubItem getNextPendingSubItemAfter(int
	 * offSetIdx) { return null; }
	 **/

	/**
	 * Helper method to set the checklist item ID
	 * @param aCheckListItemID - long
	 */
	public void setCheckListItemID(long aCheckListItemID) {
		setCMPCheckListItemID(new Long(aCheckListItemID));
	}

	public void setChaseReminderInd(boolean aChaseReminderInd) {
		if (aChaseReminderInd) {
			setChaseReminderIndStr(ICMSConstant.TRUE_VALUE);
			return;
		}
		setChaseReminderIndStr(ICMSConstant.FALSE_VALUE);
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
	 * Not implemented here
	 */
	public void setItem(IItem anIItem) {
		// do nothing
	}

	/**
	 * Not implemented here
	 */
	public void setItemStatus(String anItemStatus) {
		// do nothing
	}

	public void setRecurrentCheckListSubItemList(IRecurrentCheckListSubItem[] aSubItemList) {
		// do nothing
	}

	public void updateSubItem(int anItemIndex, IRecurrentCheckListSubItem anISubtem) {
		// do nothing
	}

	// *****************************************************
	/**
	 * Create a checklist item Information
	 * 
	 * @param aCheckListID - Long
	 * @param anICheckListItem of IRecurrentCheckListItem type
	 * @return Long - the checklist item ID (primary key)
	 * @throws CreateException on error
	 */
	public Long ejbCreate(Long aCheckListID, IRecurrentCheckListItem anICheckListItem) throws CreateException {
		if (anICheckListItem == null) {
			throw new CreateException("ICheckListItem is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setCheckListItemID(pk);
			// DefaultLogger.debug(this, "PK: " + pk);
			// DefaultLogger.debug(this, "CheckListRef: " +
			// anICheckListItem.getCheckListItemRef());
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
				setCMPItemDesc(item.getItemDesc());
			}
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
	 * @param anICheckListItem of IRecurrentCheckListItem type
	 */
	public void ejbPostCreate(Long aCheckListID, IRecurrentCheckListItem anICheckListItem) throws CreateException {
	}

	/**
	 * Return the Interface representation of this object
	 * 
	 * @return IRecurrentCheckListItem
	 */
	public IRecurrentCheckListItem getValue() throws RecurrentException {
		OBRecurrentCheckListItem value = new OBRecurrentCheckListItem();
		AccessorUtil.copyValue(this, value);
		IItem item = new OBItem();
		item.setItemDesc(getCMPItemDesc());
		value.setItem(item);
		IRecurrentCheckListSubItem[] itemList = retrieveSubItems();
		if ((itemList != null) && (itemList.length > 1)) {
			Arrays.sort(itemList);
		}
		value.setRecurrentCheckListSubItemList(itemList);
		return value;

	}

	private IRecurrentCheckListSubItem[] retrieveSubItems() throws RecurrentException {
		try {
			Collection col = getCMRCheckListSubItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBRecurrentCheckListSubItemLocal local = (EBRecurrentCheckListSubItemLocal) iter.next();
					IRecurrentCheckListSubItem obj = local.getValue();
					if (!obj.getIsDeletedInd()) {
						itemList.add(obj);
					}
				}
				return (IRecurrentCheckListSubItem[]) itemList.toArray(new IRecurrentCheckListSubItem[0]);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception at retrieveSubItems: " + ex.toString());
		}
	}

	/**
	 * Persist a checklist item information
	 * 
	 * @param anICheckListItem - IRecurrentCheckListItem
	 */
	public void setValue(IRecurrentCheckListItem anICheckListItem) throws RecurrentException {
		AccessorUtil.copyValue(anICheckListItem, this, EXCLUDE_METHOD);
		IItem item = anICheckListItem.getItem();
		if (item != null) {
			setCMPItemDesc(item.getItemDesc());
		}
		updateSubItems(anICheckListItem.getRecurrentCheckListSubItemList());
	}

	public void createDependents(IRecurrentCheckListItem anIRecurrentCheckListItem) throws RecurrentException {
		updateSubItems(anIRecurrentCheckListItem.getRecurrentCheckListSubItemList());
	}

	private void updateSubItems(IRecurrentCheckListSubItem[] aSubItemList) throws RecurrentException {
		try {
			Collection col = getCMRCheckListSubItems();
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
					EBRecurrentCheckListSubItemLocal local = (EBRecurrentCheckListSubItemLocal) iter.next();
					long subItemRef = local.getSubItemRef();
					boolean update = false;

					for (int ii = 0; ii < aSubItemList.length; ii++) {
						IRecurrentCheckListSubItem newOB = aSubItemList[ii];
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
					IRecurrentCheckListSubItem newOB = aSubItemList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBRecurrentCheckListSubItemLocal local = (EBRecurrentCheckListSubItemLocal) iter.next();
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

	private void deleteSubItems(List aDeletionList) throws RecurrentException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRCheckListSubItems();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBRecurrentCheckListSubItemLocal local = (EBRecurrentCheckListSubItemLocal) iter.next();
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
		List createdItems = new ArrayList();
		Collection col = getCMRCheckListSubItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBRecurrentCheckListSubItemLocalHome home = getEBRecurrentCheckListSubItemLocalHome();
			while (iter.hasNext()) {
				IRecurrentCheckListSubItem obj = (IRecurrentCheckListSubItem) iter.next();
				// DefaultLogger.debug(this, "Creating CheckList Sub Item ID: "
				// + obj.getSubItemID());
				EBRecurrentCheckListSubItemLocal local = home.create(new Long(getCheckListItemID()), obj);
				createdItems.add(local.getValue());
				col.add(local);
			}
			return createdItems;
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
		return ICMSConstant.SEQUENCE_RECURRENT_CHECKLIST_ITEM;
	}

	protected EBRecurrentCheckListSubItemLocalHome getEBRecurrentCheckListSubItemLocalHome() throws RecurrentException {
		EBRecurrentCheckListSubItemLocalHome home = (EBRecurrentCheckListSubItemLocalHome) BeanController
				.getEJBLocalHome(ICMSJNDIConstant.EB_RECURRENT_CHECKLIST_SUB_ITEM_LOCAL_JNDI,
						EBRecurrentCheckListSubItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBRecurrentCheckListSubItemLocalHome is null!");
	}
}