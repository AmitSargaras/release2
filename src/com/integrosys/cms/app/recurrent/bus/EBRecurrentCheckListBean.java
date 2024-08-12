/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/checklist/bus/EBRecurrentCheckListBean.java,v 1.15 2005/09/09 07:56:44 wltan Exp $
 */
package com.integrosys.cms.app.recurrent.bus;

//java

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Implementation for the checklist entity bean
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/09/09 07:56:44 $ Tag: $Name: $
 */

public abstract class EBRecurrentCheckListBean implements EntityBean, IRecurrentCheckList {

	private static final long serialVersionUID = 4781003366029265880L;

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getCheckListID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getCheckListID" };

	/**
	 * Default Constructor
	 */
	public EBRecurrentCheckListBean() {
	}

	public abstract Long getCMPCheckListID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPSubProfileID();

	/**
	 * Helper method to get the checklist ID
	 * @return long - the long value of the checklist ID
	 */
	public long getCheckListID() {
		if (getCMPCheckListID() != null) {
			return getCMPCheckListID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Helper method to get the limit profile ID
	 * @return long - the long value of the limit profile ID
	 */
	public long getLimitProfileID() {
		if (getCMPLimitProfileID() != null) {
			return getCMPLimitProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Get the sub profile ID
	 * @return long - the sub profile ID
	 */
	public long getSubProfileID() {
		if (getCMPSubProfileID() != null) {
			return getCMPSubProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Not implemented here
	 */
	public IRecurrentCheckListItem[] getCheckListItemList() {
		return null;
	}

	/**
	 * Not implemented here
	 */
	public IConvenant[] getConvenantList() {
		return null;
	}
	
	
	public IAnnexure[] getAnnexureList() {
		return null;
	}

	// setters
	public abstract void setCMPCheckListID(Long aCheckListID);

	public abstract void setCMPLimitProfileID(Long aLimitProfileID);

	public abstract void setCMPSubProfileID(Long aSubProfileID);

	/**
	 * Helper method to set the checklist ID
	 * @param aCheckListID - long
	 */
	public void setCheckListID(long aCheckListID) {
		setCMPCheckListID(new Long(aCheckListID));
	}

	/**
	 * Set the limit profile ID
	 * @param aLimitProfileID of long type
	 */
	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	public void setSubProfileID(long aSubProfileID) {
		setCMPSubProfileID(new Long(aSubProfileID));
	}

	public abstract String getCheckListStatus();

	public abstract void setCheckListStatus(String checkListStatus);

	public abstract long getVersionTime();

	public abstract  void setVersionTime(long version);
	
	/**
	 * Not implemented here
	 */
	public void setConvenantList(IConvenant[] anIConvenantList) {
		// do nothing
	}
	
	public void setAnnexureList(IAnnexure[] anIAnnexureList) {
		// do nothing
	}

	/**
	 * Not implemented here
	 */
	public void setCheckListItemList(IRecurrentCheckListItem[] aCheckListItemList) {
		// do nothing
	}

	/**
	 * Add a convenant into the checklist
	 * @param anIConvenant of IConvenant type
	 */
	public void addConvenant(IConvenant anIConvenant) {
		// do nothing
	}

	/**
	 * Add an list of convenant into the checklist
	 * @param anIConvenantList of IConvenant[] type
	 */
	public void addConvenants(IConvenant[] anIConvenantList) {
		// do nothing
	}

	/**
	 * Add an item into the checklist
	 * @param anItem of IRecurrentCheckListItem type
	 */
	public void addItem(IRecurrentCheckListItem anItem) {
		// do nothing
	}

	/**
	 * Add an list of items into the checklist
	 * @param anItemList of IRecurrentCheckListItem[] type
	 */
	public void addItems(IRecurrentCheckListItem[] anItemList) {
		// do nothing
	}

	/**
	 * Remove a list of convenant into the checklist
	 * @param anIndexList of int[] type
	 */
	public void removeConvenants(int[] anIndexList) {
		// do nothing
	}

	/**
	 * Remove a list of items into the checklist
	 * @param anItemIndexList - the list of item index to be removed
	 */
	public void removeItems(int[] anItemIndexList) {
		// do nothing
	}

	/**
	 * Update an item in the template
	 * @param anIndex of int type
	 * @param anIConvenant of IConvenant type
	 */
	public void updateConvenant(int anIndex, IConvenant anIConvenant) {
		// do nothing
	}

	/**
	 * Update an item in the template
	 * @param anItemIndex - int
	 * @param anItem - IItem
	 */
	public void updateItem(int anItemIndex, IRecurrentCheckListItem anItem) {
		// do nothing
	}
	
	
	/**
	 * Add a convenant into the checklist
	 * @param anIConvenant of IConvenant type
	 */
	public void addAnnexure(IAnnexure anIAnnexure) {
		// do nothing
	}

	/**
	 * Add an list of convenant into the checklist
	 * @param anIAnnexureList of IAnnexure[] type
	 */
	public void addAnnexures(IAnnexure[] anIAnnexureList) {
		// do nothing
	}

	/**
	 * Remove a list of convenant into the checklist
	 * @param anIndexList of int[] type
	 */
	public void removeAnnexures(int[] anIndexList) {
		// do nothing
	}
	
	/**
	 * Update an item in the template
	 * @param anIndex of int type
	 * @param anIConvenant of IConvenant type
	 */
	public void updateAnnexure(int anIndex, IAnnexure anIAnnexure) {
		// do nothing
	}

	// ************** CMR methods ***************
	// Getters
	/**
	 * Get all checklist items
	 * @return Collection - the list of EBCheckListItemLocal objects
	 */
	public abstract Collection getCMRCheckListItems();

	/**
	 * Get all the convenants
	 * @return Collection - the list of EBConvenantLocal objects
	 */
	public abstract Collection getCMRConvenants();

	// Setters
	/**
	 * Set all checklist items
	 * @param aCheckListItems of Collection type
	 */
	public abstract void setCMRCheckListItems(Collection aCheckListItems);

	/**
	 * Set all convenants
	 * @param aCheckListItems of Collection type
	 */
	public abstract void setCMRConvenants(Collection aCheckListItems);
	
	/**
	 * Get all the convenants
	 * @return Collection - the list of EBConvenantLocal objects
	 */
	public abstract Collection getCMRAnnexures();

	/**
	 * Set all convenants
	 * @param aCheckListItems of Collection type
	 */
	public abstract void setCMRAnnexures(Collection aCheckListItems);

	/**
	 * Return a recurrent checklist object
	 * @return IRecurentCheckList - the object containing the recurrent
	 *         checklist object
	 * @throws com.integrosys.cms.app.recurrent.bus.RecurrentException on errors
	 */
	public IRecurrentCheckList getValue() throws RecurrentException {
		OBRecurrentCheckList value = new OBRecurrentCheckList();
		AccessorUtil.copyValue(this, value, null);
		IConvenant[] convList = retrieveConvenants();
		if ((convList != null) && (convList.length > 0)) {
			Arrays.sort(convList);
		}
		value.setConvenantList(convList);
		IRecurrentCheckListItem[] itemList = retrieveCheckListItems();
		if ((itemList != null) && (itemList.length > 0)) {
			Arrays.sort(itemList);
		}
		value.setCheckListItemList(itemList);
		return value;
	}

	/**
	 * To retrieve the list of convenants
	 * @return IConvenant[] - the list of convenants
	 * @throws RecurrentException on error
	 */
	private IConvenant[] retrieveConvenants() throws RecurrentException {
		try {
			Collection col = getCMRConvenants();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBConvenantLocal local = (EBConvenantLocal) iter.next();
					IConvenant obj = local.getValue();
					if (!obj.getIsDeletedInd()) {
						itemList.add(obj);
					}
				}
				return (IConvenant[]) itemList.toArray(new IConvenant[0]);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception at retrieveConvenants: " + ex.toString());
		}
	}

	/**
	 * To retrieve the list of recurrent checklist items
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throws RecurrentException on error
	 */
	private IRecurrentCheckListItem[] retrieveCheckListItems() throws RecurrentException {
		try {
			Collection col = getCMRCheckListItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBRecurrentCheckListItemLocal local = (EBRecurrentCheckListItemLocal) iter.next();
					IRecurrentCheckListItem obj = local.getValue();
					if (!obj.getIsDeletedInd()) {
						itemList.add(obj);
					}
				}
				return (IRecurrentCheckListItem[]) itemList.toArray(new IRecurrentCheckListItem[0]);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception at retrieveCheckListItems: " + ex.toString());
		}
	}

	/**
	 * Set the recurrent checklist object.
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 * @throws RecurrentException on error
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException,
			ConcurrentUpdateException {
		try {
			if (getVersionTime() != anIRecurrentCheckList.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anIRecurrentCheckList, this, EXCLUDE_METHOD_UPDATE);
			updateConvenants(anIRecurrentCheckList.getConvenantList());
			updateCheckListItems(anIRecurrentCheckList.getCheckListItemList());
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new RecurrentException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * Create the child recurrent items and convenants that are under this
	 * checklist
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 * @throws RecurrentException
	 * @throws RemoteException
	 */
	public void createDependents(IRecurrentCheckList anIRecurrentCheckList) throws RecurrentException {
		updateConvenants(anIRecurrentCheckList.getConvenantList());
		updateCheckListItems(anIRecurrentCheckList.getCheckListItemList());
	}

	private void updateConvenants(IConvenant[] anIConvenantList) throws RecurrentException {
		try {
			Collection col = getCMRConvenants();
			if (anIConvenantList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteConvenants(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createConvenants(Arrays.asList(anIConvenantList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or update first
				while (iter.hasNext()) {
					EBConvenantLocal local = (EBConvenantLocal) iter.next();
					long convenantRef = local.getConvenantRef();
					boolean update = false;

					for (int ii = 0; ii < anIConvenantList.length; ii++) {
						IConvenant newOB = anIConvenantList[ii];

						if (newOB.getConvenantRef() == convenantRef) {
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
				for (int ii = 0; ii < anIConvenantList.length; ii++) {
					/*
					 * if (anIConvenantList[ii].getConvenantRef() ==
					 * com.integrosys
					 * .cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE)
					 * { createList.add(anIConvenantList[ii]); }
					 */
					iter = col.iterator();
					IConvenant newOB = anIConvenantList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBConvenantLocal local = (EBConvenantLocal) iter.next();
						long ref = local.getConvenantRef();
						if (newOB.getConvenantRef() == ref) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteConvenants(deleteList);
				createConvenants(createList);
			}
		}
		catch (RecurrentException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception in updateConvenants: " + ex.toString());
		}
	}

	private void updateCheckListItems(IRecurrentCheckListItem[] anIRecurrentCheckList) throws RecurrentException {
		try {
			Collection col = getCMRCheckListItems();
			if (anIRecurrentCheckList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteCheckListItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createCheckListItems(Arrays.asList(anIRecurrentCheckList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (iter.hasNext()) {
					EBRecurrentCheckListItemLocal local = (EBRecurrentCheckListItemLocal) iter.next();
					long checkListItemRef = local.getCheckListItemRef();
					boolean update = false;

					for (int ii = 0; ii < anIRecurrentCheckList.length; ii++) {
						IRecurrentCheckListItem newOB = anIRecurrentCheckList[ii];
						if (newOB.getCheckListItemRef() == checkListItemRef) {
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
				for (int ii = 0; ii < anIRecurrentCheckList.length; ii++) {
					/*
					 * if (anIRecurrentCheckList[ii].getCheckListItemRef() ==
					 * com.integrosys.cms.app.common.constant.ICMSConstant.
					 * LONG_INVALID_VALUE) {
					 * createList.add(anIRecurrentCheckList[ii]); }
					 */
					iter = col.iterator();
					IRecurrentCheckListItem newOB = anIRecurrentCheckList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBRecurrentCheckListItemLocal local = (EBRecurrentCheckListItemLocal) iter.next();
						long ref = local.getCheckListItemRef();

						if (newOB.getCheckListItemRef() == ref) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteCheckListItems(deleteList);
				createCheckListItems(createList);
			}
		}
		catch (RecurrentException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception in updateCheckListItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of cpnvenants
	 * @param aDeletionList - List
	 * @throws RecurrentException on errors
	 */
	private void deleteConvenants(List aDeletionList) throws RecurrentException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRCheckListItems();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBConvenantLocal local = (EBConvenantLocal) iter.next();
				// col.remove(local);
				// local.remove();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception in deleteConvenants: " + ex.toString());
		}
	}

	/**
	 * Delete the list of checklist items under the recurrent checklist
	 * @param aDeletionList - List
	 * @throws RecurrentException on errors
	 */
	private void deleteCheckListItems(List aDeletionList) throws RecurrentException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRCheckListItems();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBRecurrentCheckListItemLocal local = (EBRecurrentCheckListItemLocal) iter.next();
				// col.remove(local);
				// local.remove();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception in deleteCheckListItems: " + ex.toString());
		}
	}

	/**
	 * Create the list of convenants
	 * @param aCreationList - List
	 * @throws RecurrentException on errors
	 */
	private void createConvenants(List aCreationList) throws RecurrentException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRConvenants();
		Iterator iter = aCreationList.iterator();
		try {
			EBConvenantLocalHome home = getEBConvenantLocalHome();
			while (iter.hasNext()) {
				IConvenant obj = (IConvenant) iter.next();
				// DefaultLogger.debug(this, "Creating CheckList Item ID: " +
				// obj.getConvenantRef());
				EBConvenantLocal local = home.create(new Long(getCheckListID()), obj);
				local.createDependents(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception in createConvenants: " + ex.toString());
		}
	}

	/**
	 * Create the list of checklist items under the current checklist
	 * @param aCreationList - List
	 * @throws RecurrentException on errors
	 */
	private void createCheckListItems(List aCreationList) throws RecurrentException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRCheckListItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBRecurrentCheckListItemLocalHome home = getEBRecurrentCheckListItemLocalHome();
			while (iter.hasNext()) {
				IRecurrentCheckListItem obj = (IRecurrentCheckListItem) iter.next();
				// DefaultLogger.debug(this, "Creating CheckList Item ID: " +
				// obj.getCheckListItemID());
				EBRecurrentCheckListItemLocal local = home.create(new Long(getCheckListID()), obj);
				// DefaultLogger.debug(this, "SUBITEM: " + obj);
				local.createDependents(obj);

				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new RecurrentException("Exception in createCheckListItems: " + ex.toString());
		}
	}

	/**
	 * Create a checklist.
	 * @param anIRecurrentCheckList of IRecurrentCheckList type
	 * @return Long - the checklist ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IRecurrentCheckList anIRecurrentCheckList) throws CreateException {
		if (anIRecurrentCheckList == null) {
			throw new CreateException("ICMSCustomer is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setCheckListID(pk);
			AccessorUtil.copyValue(anIRecurrentCheckList, this, EXCLUDE_METHOD_CREATE);
			setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new CreateException("Exception in ejbCreate : " + ex.toString());
		}
	}

	/**
	 * EJB Post Create Method
	 * @param anIRecurrentCheckList - IRecurrentCheckList
	 */
	public void ejbPostCreate(IRecurrentCheckList anIRecurrentCheckList) throws CreateException {
	}

	/**
	 * To get the list of recurrent checklist item history based on the item
	 * reference
	 * @param anItemReference of long type
	 * @return IRecurrentCheckListItem[] - the list of recurrent checklist items
	 * @throw SearchDAOException on errors
	 */
	public IRecurrentCheckListItem[] ejbHomeGetRecurrentItemHistory(long anItemReference) throws SearchDAOException {
		return RecurrentDAOFactory.getRecurrentCheckListDAO().getRecurrentItemHistory(anItemReference);
	}

	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public RecurrentSearchResult[] ejbHomeGetCheckList(long aLimitProfileID, long aSubProfileID, String[] aStatusList)
			throws SearchDAOException {
		return RecurrentDAOFactory.getRecurrentCheckListDAO().getCheckList(aLimitProfileID, aSubProfileID, aStatusList);
	}
	
	/**
	 * Get the number of checklist based on the attribute specified in the owner
	 * and the status of the trx
	 * @param aLimitProfileID of long type
	 * @param aSubProfileID of long type
	 * @param aStatusList of String[] type
	 * @return RecurrentSearchResult[] - the list of checklist result
	 * @throws SearchDAOException
	 */
	public RecurrentSearchResult[] ejbHomeGetCheckListByAnnexureId(long aLimitProfileID, long aSubProfileID, String[] aStatusList, String annexureId)
			throws SearchDAOException {
		return RecurrentDAOFactory.getRecurrentCheckListDAO().getCheckListByAnnexureId(aLimitProfileID, aSubProfileID, aStatusList,annexureId);
	}

	public IRecurrentCheckListItem ejbHomeGetRecurrentCheckListItem(long anItemReference) throws SearchDAOException {
		return RecurrentDAOFactory.getRecurrentCheckListDAO().getRecurrentCheckListItem(anItemReference);
	}

	// cr 26
	public IConvenant[] ejbHomeGetConvenantHistory(long anItemReference) throws SearchDAOException {
		return RecurrentDAOFactory.getRecurrentCheckListDAO().getConvenantHistory(anItemReference);
	}

	public IConvenant ejbHomeGetConvenant(long anItemReference) throws SearchDAOException {
		return RecurrentDAOFactory.getRecurrentCheckListDAO().getConvenant(anItemReference);
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
	 * A container invokes this method when the instance is taken out of the
	 * pool of available instances to become associated with a specific EJB
	 * object. This method transitions the instance to the ready state. This
	 * method executes in an unspecified transaction context.
	 */
	public void ejbActivate() {
	}

	/**
	 * A container invokes this method on an instance before the instance
	 * becomes disassociated with a specific EJB object. After this method
	 * completes, the container will place the instance into the pool of
	 * available instances. This method executes in an unspecified transaction
	 * context.
	 */
	public void ejbPassivate() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by loading it from the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbLoad() {
	}

	/**
	 * A container invokes this method to instruct the instance to synchronize
	 * its state by storing it to the underlying database. This method always
	 * executes in the transaction context determined by the value of the
	 * transaction attribute in the deployment descriptor.
	 */
	public void ejbStore() {
	}

	/**
	 * A container invokes this method before it removes the EJB object that is
	 * currently associated with the instance. It is invoked when a client
	 * invokes a remove operation on the enterprise Bean's home or remote
	 * interface. It transitions the instance from the ready state to the pool
	 * of available instances. It is called in the transaction context of the
	 * remove operation.
	 */
	public void ejbRemove() throws RemoveException {
	}

	/**
	 * Get the name of the sequence to be used for key generation
	 * @return String - the sequence to be used
	 */
	protected String getSequenceName() {
		return ICMSConstant.SEQUENCE_RECURRENT_CHECKLIST;
	}

	/**
	 * Method to get EB Local Home for checkList item
	 */
	protected EBRecurrentCheckListItemLocalHome getEBRecurrentCheckListItemLocalHome() throws RecurrentException {
		EBRecurrentCheckListItemLocalHome home = (EBRecurrentCheckListItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_RECURRENT_CHECKLIST_ITEM_LOCAL_JNDI, EBRecurrentCheckListItemLocalHome.class
						.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBRecurrentCheckListItemLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for convenant
	 */
	protected EBConvenantLocalHome getEBConvenantLocalHome() throws RecurrentException {
		EBConvenantLocalHome home = (EBConvenantLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CONVENANT_LOCAL_JNDI, EBConvenantLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new RecurrentException("EBConvenantLocalHome is null!");
	}
}