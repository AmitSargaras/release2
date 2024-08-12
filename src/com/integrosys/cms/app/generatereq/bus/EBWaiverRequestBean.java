/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBWaiverRequestBean.java,v 1.3 2003/09/22 02:23:23 hltan Exp $
 */
package com.integrosys.cms.app.generatereq.bus;

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
import javax.ejb.FinderException;
import javax.ejb.RemoveException;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.checklist.bus.EBCheckListItem;
import com.integrosys.cms.app.checklist.bus.EBCheckListItemHome;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;

/**
 * Implementation for the waiver request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/22 02:23:23 $ Tag: $Name: $
 */

public abstract class EBWaiverRequestBean implements EntityBean, IWaiverRequest {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getRequestID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getRequestID" };

	/**
	 * Default Constructor
	 */
	public EBWaiverRequestBean() {
	}

	public abstract Long getCMPRequestID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPCustomerID();

	/**
	 * Helper method to get the request ID
	 * @return long - the long value of the request ID
	 */
	public long getRequestID() {
		if (getCMPRequestID() != null) {
			return getCMPRequestID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getLimitProfileID() {
		if (getCMPLimitProfileID() != null) {
			return getCMPLimitProfileID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getCustomerID() {
		if (getCMPCustomerID() != null) {
			return getCMPCustomerID().longValue();
		}
		return com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Not implemented here
	 */
	public IRequestHeader getRequestHeader() {
		return null;
	}

	public IRequestSubject getRequestSubject() {
		return null;
	}

	public IRequestDescription getRequestDescription() {
		return null;
	}

	public IRequestItem[] getRequestItemList() {
		return null;
	}

	public IWaiverRequestItem[] getWaiverRequestItemList() {
		return null;
	}

	// setters
	public abstract void setCMPRequestID(Long aRequestID);

	public abstract void setCMPLimitProfileID(Long aLimitProfileID);

	public abstract void setCMPCustomerID(Long aCustomerID);

	/**
	 * Helper method to set the request ID
	 * @param aRequestID - long
	 */
	public void setRequestID(long aRequestID) {
		setCMPRequestID(new Long(aRequestID));
	}

	public void setLimitProfileID(long aLimitProfileID) {
		setCMPLimitProfileID(new Long(aLimitProfileID));
	}

	public void setCustomerID(long aCustomerID) {
		setCMPCustomerID(new Long(aCustomerID));
	}

	/**
	 * Not implemented here
	 */
	public void setRequestHeader(IRequestHeader anIRequestHeader) {
		// do nothing
	}

	public void setRequestSubject(IRequestSubject anIRequestSubject) {
		// do nothing
	}

	public void setRequestDescription(IRequestDescription anIRequestDescription) {
		// do nothing
	}

	public void setRequestItemList(IRequestItem[] anIRequestItemList) {
		// do nothing
	}

	// ************** CMR methods ***************
	// Getters
	/**
	 * Get all waiver request items
	 * 
	 * @return Collection of EBWaiverRequestItemLocal objects
	 */
	public abstract Collection getCMRWaiverRequestItems();

	// Setters
	/**
	 * Set all request items
	 * @param value is of type Collection of EBWaiverRequestItemLocal objects
	 */
	public abstract void setCMRWaiverRequestItems(Collection aWaiverRequestItemList);

	/**
	 * Return a waiver request object
	 * @return IWaiverRequest - the object containing the waiver request object
	 * @throws GenerateRequestException on errors
	 */
	public IWaiverRequest getValue() throws GenerateRequestException {
		IWaiverRequest value = new OBWaiverRequest();
		AccessorUtil.copyValue(this, value, null);

		IWaiverRequestItem[] itemList = retrieveWaiverRequestItems();
		value.setRequestItemList(itemList);
		return value;
	}

	/**
	 * To retrieve the list of waiver request items
	 * @return IWaiverRequestItem[] - the list of waiver request items
	 * @throws GenerateRequestException on error
	 */
	private IWaiverRequestItem[] retrieveWaiverRequestItems() throws GenerateRequestException {
		try {
			Collection col = getCMRWaiverRequestItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBWaiverRequestItemLocal local = (EBWaiverRequestItemLocal) iter.next();
					if (!local.getIsDeletedInd()) {
						IWaiverRequestItem obj = local.getValue();

						ICheckListItem item = obj.getCheckListItem();
						if (item != null) {
							item = getCheckListItem(item.getCheckListItemID());
							obj.setCheckListItem(item);
						}
						itemList.add(obj);
					}
				}
				return (IWaiverRequestItem[]) itemList.toArray(new IWaiverRequestItem[0]);
			}
		}
		catch (Exception ex) {
			throw new GenerateRequestException("Exception at retrieveWaiverRequestItems: " + ex.toString());
		}
	}

	/**
	 * Set the waiver request object.
	 * @param anIWaiverRequest of IWaiverRequest type
	 * @throws GenerateRequestException on error
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IWaiverRequest anIWaiverRequest) throws GenerateRequestException, ConcurrentUpdateException {
		try {
			if (getVersionTime() != anIWaiverRequest.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anIWaiverRequest, this, EXCLUDE_METHOD_UPDATE);
			updateWaiverRequestItems(anIWaiverRequest.getWaiverRequestItemList());
			setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (ConcurrentUpdateException ex) {
			_context.setRollbackOnly();
			throw ex;
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new GenerateRequestException("Exception in setValue: " + ex.toString());
		}
	}

	private ICheckListItem getCheckListItem(long aCheckListItemID) throws GenerateRequestException {
		try {
			EBCheckListItemHome home = getEBCheckListItemHome();
			EBCheckListItem remote = home.findByPrimaryKey(new Long(aCheckListItemID));
			return remote.getValue();
		}
		catch (FinderException ex) {
			_context.setRollbackOnly();
			throw new GenerateRequestException("FinderException in getCheckListItem: " + ex.toString());
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new GenerateRequestException("RemoteException in getCheckListItem: " + ex.toString());
		}
	}

	/**
	 * Create the child items that are under this waiver request
	 * @param anIWaiverRequest of IWaiverRequest type
	 * @throws GenerateRequestException
	 * @throws RemoteException
	 */
	public void createWaiverRequestItems(IWaiverRequest anIWaiverRequest) throws GenerateRequestException {
		updateWaiverRequestItems(anIWaiverRequest.getWaiverRequestItemList());
	}

	private void updateWaiverRequestItems(IWaiverRequestItem[] anIWaiverRequestItemList)
			throws GenerateRequestException {
		try {
			Collection col = getCMRWaiverRequestItems();
			if (anIWaiverRequestItemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteWaiverRequestItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createWaiverRequestItems(Arrays.asList(anIWaiverRequestItemList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (iter.hasNext()) {
					EBWaiverRequestItemLocal local = (EBWaiverRequestItemLocal) iter.next();
					long reqItemRef = local.getRequestItemRef();
					boolean update = false;

					for (int ii = 0; ii < anIWaiverRequestItemList.length; ii++) {
						IWaiverRequestItem newOB = anIWaiverRequestItemList[ii];

						if (newOB.getRequestItemRef() == reqItemRef) {
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
				for (int ii = 0; ii < anIWaiverRequestItemList.length; ii++) {
					iter = col.iterator();
					IWaiverRequestItem newOB = anIWaiverRequestItemList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBWaiverRequestItemLocal local = (EBWaiverRequestItemLocal) iter.next();
						long ref = local.getRequestItemRef();

						if (newOB.getRequestItemRef() == ref) {
							found = true;
							break;
						}
					}
					if (!found) {
						// add for adding
						createList.add(newOB);
					}
				}
				deleteWaiverRequestItems(deleteList);
				createWaiverRequestItems(createList);
			}
		}
		catch (GenerateRequestException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new GenerateRequestException("Exception in updateWaiverRequestItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of waiver request items under the current waiver request
	 * @param aDeletionList - List
	 * @throws GenerateRequestException on errors
	 */
	private void deleteWaiverRequestItems(List aDeletionList) throws GenerateRequestException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRWaiverRequestItems();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBWaiverRequestItemLocal local = (EBWaiverRequestItemLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new GenerateRequestException("Exception in deleteWaiverRequestItems: " + ex.toString());
		}
	}

	/**
	 * Create the list of waiver request items under the current request
	 * @param aCreateList - List
	 * @throws GenerateRequestException on errors
	 */
	private void createWaiverRequestItems(List aCreationList) throws GenerateRequestException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRWaiverRequestItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBWaiverRequestItemLocalHome home = getEBWaiverRequestItemLocalHome();
			while (iter.hasNext()) {
				IWaiverRequestItem obj = (IWaiverRequestItem) iter.next();
				// preCreationProcess(obj);
				EBWaiverRequestItemLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new GenerateRequestException("Exception in createWaiverRequestItems: " + ex.toString());
		}
	}

	/**
	 * To get the number of waiver request that satisfy the criteria
	 * @param aCriteria of WaiverRequestSearchCriteria type
	 * @return int - the number of waiver request that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetNoOfWaiverRequest(WaiverRequestSearchCriteria aCriteria) throws SearchDAOException {
		return GenerateRequestDAOFactory.getWaiverRequestDAO().getNoOfWaiverRequest(aCriteria);
	}

	/**
	 * Create a waiver request.
	 * @param anIWaiverRequest of IWaiverRequest typef
	 * @return Long - the waiver request ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IWaiverRequest anIWaiverRequest) throws CreateException {
		if (anIWaiverRequest == null) {
			throw new CreateException("anIWaiverRequest is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setRequestID(pk);
			AccessorUtil.copyValue(anIWaiverRequest, this, EXCLUDE_METHOD_CREATE);
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
	 * @param anIWaiverRequest - IWaiverRequest
	 */
	public void ejbPostCreate(IWaiverRequest anIWaiverRequest) throws CreateException {
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
		return ICMSConstant.SEQUENCE_WAIVER_REQUEST;
	}

	/**
	 * Method to get EB Local Home for waiver request item
	 */
	protected EBWaiverRequestItemLocalHome getEBWaiverRequestItemLocalHome() throws GenerateRequestException {
		EBWaiverRequestItemLocalHome home = (EBWaiverRequestItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_WAIVER_REQUEST_ITEM_LOCAL_JNDI, EBWaiverRequestItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new GenerateRequestException("EBWaiverRequestItemLocalHome is null!");
	}

	protected EBCheckListItemHome getEBCheckListItemHome() throws GenerateRequestException {
		EBCheckListItemHome home = (EBCheckListItemHome) BeanController.getEJBHome(
				ICMSJNDIConstant.EB_CHECKLIST_ITEM_JNDI, EBCheckListItemHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new GenerateRequestException("EBCheckListItemHome is null!");
	}
}