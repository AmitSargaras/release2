/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/generatereq/bus/EBDeferralRequestBean.java,v 1.5 2003/10/21 09:49:39 hltan Exp $
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
 * Implementation for the deferral request entity bean
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2003/10/21 09:49:39 $ Tag: $Name: $
 */

public abstract class EBDeferralRequestBean implements EntityBean, IDeferralRequest {
	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD_CREATE = new String[] { "getRequestID" };

	private static final String[] EXCLUDE_METHOD_UPDATE = new String[] { "getRequestID" };

	/**
	 * Default Constructor
	 */
	public EBDeferralRequestBean() {
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

	public IDeferralRequestItem[] getDeferralRequestItemList() {
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
	 * Get all deferral request items
	 * 
	 * @return Collection of EBDeferralRequestItemLocal objects
	 */
	public abstract Collection getCMRDeferralRequestItems();

	// Setters
	/**
	 * Set all request items
	 * @param value is of type Collection of EBDeferralRequestItemLocal objects
	 */
	public abstract void setCMRDeferralRequestItems(Collection aDeferralRequestItemList);

	/**
	 * Return a deferral request object
	 * @return IDeferralRequest - the object containing the deferral request
	 *         object
	 * @throws GenerateRequestException on errors
	 */
	public IDeferralRequest getValue() throws GenerateRequestException {
		IDeferralRequest value = new OBDeferralRequest();
		AccessorUtil.copyValue(this, value, null);

		IDeferralRequestItem[] itemList = retrieveDeferralRequestItems();
		value.setRequestItemList(itemList);
		return value;
	}

	/**
	 * To retrieve the list of deferral request items
	 * @return IDeferralRequestItem[] - the list of deferral request items
	 * @throws GenerateRequestException on error
	 */
	private IDeferralRequestItem[] retrieveDeferralRequestItems() throws GenerateRequestException {
		try {
			Collection col = getCMRDeferralRequestItems();
			if ((col == null) || (col.size() == 0)) {
				return null;
			}
			else {
				ArrayList itemList = new ArrayList();
				Iterator iter = col.iterator();
				while (iter.hasNext()) {
					EBDeferralRequestItemLocal local = (EBDeferralRequestItemLocal) iter.next();
					if (!local.getIsDeletedInd()) {
						IDeferralRequestItem obj = local.getValue();

						ICheckListItem item = obj.getCheckListItem();
						if (item != null) {
							item = getCheckListItem(item.getCheckListItemID());
							obj.setCheckListItem(item);
						}

						itemList.add(obj);
					}
				}
				return (IDeferralRequestItem[]) itemList.toArray(new IDeferralRequestItem[0]);
			}
		}
		catch (Exception ex) {
			throw new GenerateRequestException("Exception at retrieveDeferralRequestItems: " + ex.toString());
		}
	}

	/**
	 * Set the deferral request object.
	 * @param anIDeferralRequest of IDeferralRequest type
	 * @throws GenerateRequestException on error
	 * @throws ConcurrentUpdateException if the version number of the object to
	 *         be updated does not match with that in entity
	 */
	public void setValue(IDeferralRequest anIDeferralRequest) throws GenerateRequestException,
			ConcurrentUpdateException {
		try {
			if (getVersionTime() != anIDeferralRequest.getVersionTime()) {
				throw new ConcurrentUpdateException("Mismatch timestamp");
			}
			AccessorUtil.copyValue(anIDeferralRequest, this, EXCLUDE_METHOD_UPDATE);
			updateDeferralRequestItems(anIDeferralRequest.getDeferralRequestItemList());
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
	 * Create the child items that are under this deferral request
	 * @param anIDeferralRequest of IDeferralRequest type
	 * @throws GenerateRequestException
	 * @throws RemoteException
	 */
	public void createDeferralRequestItems(IDeferralRequest anIDeferralRequest) throws GenerateRequestException {
		updateDeferralRequestItems(anIDeferralRequest.getDeferralRequestItemList());
	}

	private void updateDeferralRequestItems(IDeferralRequestItem[] anIDeferralRequestItemList)
			throws GenerateRequestException {
		try {
			Collection col = getCMRDeferralRequestItems();
			if (anIDeferralRequestItemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else {
					// delete all records
					deleteDeferralRequestItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) {
				// create new records
				createDeferralRequestItems(Arrays.asList(anIDeferralRequestItemList));
			}
			else {
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces

				// identify identify records for delete or udpate first
				while (iter.hasNext()) {
					EBDeferralRequestItemLocal local = (EBDeferralRequestItemLocal) iter.next();
					long reqItemRef = local.getRequestItemRef();
					boolean update = false;

					for (int ii = 0; ii < anIDeferralRequestItemList.length; ii++) {
						IDeferralRequestItem newOB = anIDeferralRequestItemList[ii];

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
				for (int ii = 0; ii < anIDeferralRequestItemList.length; ii++) {
					iter = col.iterator();
					IDeferralRequestItem newOB = anIDeferralRequestItemList[ii];
					boolean found = false;

					while (iter.hasNext()) {
						EBDeferralRequestItemLocal local = (EBDeferralRequestItemLocal) iter.next();
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
				deleteDeferralRequestItems(deleteList);
				createDeferralRequestItems(createList);
			}
		}
		catch (GenerateRequestException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new GenerateRequestException("Exception in updateDeferralRequestItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of Deferral request items under the current Deferral
	 * request
	 * @param aDeletionList - List
	 * @throws GenerateRequestException on errors
	 */
	private void deleteDeferralRequestItems(List aDeletionList) throws GenerateRequestException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Collection col = getCMRDeferralRequestItems();
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBDeferralRequestItemLocal local = (EBDeferralRequestItemLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new GenerateRequestException("Exception in deleteDeferralRequestItems: " + ex.toString());
		}
	}

	/**
	 * Create the list of Deferral request items under the current request
	 * @param aCreateList - List
	 * @throws GenerateRequestException on errors
	 */
	private void createDeferralRequestItems(List aCreationList) throws GenerateRequestException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRDeferralRequestItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBDeferralRequestItemLocalHome home = getEBDeferralRequestItemLocalHome();
			while (iter.hasNext()) {
				IDeferralRequestItem obj = (IDeferralRequestItem) iter.next();
				// preCreationProcess(obj);
				EBDeferralRequestItemLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new GenerateRequestException("Exception in createDeferralRequestItems: " + ex.toString());
		}
	}

	/**
	 * To get the number of deferral request that satisfy the criteria
	 * @param aCriteria of DeferralRequestSearchCriteria type
	 * @return int - the number of deferral request that satisfy the criteria
	 * @throws SearchDAOException on errors
	 */
	public int ejbHomeGetNoOfDeferralRequest(DeferralRequestSearchCriteria aCriteria) throws SearchDAOException {
		return GenerateRequestDAOFactory.getDeferralRequestDAO().getNoOfDeferralRequest(aCriteria);
	}

	/**
	 * Create a Deferral request.
	 * @param anIDeferralRequest of IDeferralRequest typef
	 * @return Long - the Deferral request ID
	 * @throws CreateException on error
	 */
	public Long ejbCreate(IDeferralRequest anIDeferralRequest) throws CreateException {
		if (anIDeferralRequest == null) {
			throw new CreateException("anIDeferralRequest is null!");
		}
		try {
			long pk = com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE;
			pk = Long.parseLong((new SequenceManager()).getSeqNum(getSequenceName(), true));
			setRequestID(pk);
			AccessorUtil.copyValue(anIDeferralRequest, this, EXCLUDE_METHOD_CREATE);
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
	 * @param anIDeferralRequest - IDeferralRequest
	 */
	public void ejbPostCreate(IDeferralRequest anIDeferralRequest) throws CreateException {
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
		return ICMSConstant.SEQUENCE_DEFERRAL_REQUEST;
	}

	/**
	 * Method to get EB Local Home for Deferral request item
	 */
	protected EBDeferralRequestItemLocalHome getEBDeferralRequestItemLocalHome() throws GenerateRequestException {
		EBDeferralRequestItemLocalHome home = (EBDeferralRequestItemLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_DEFERRAL_REQUEST_ITEM_LOCAL_JNDI, EBDeferralRequestItemLocalHome.class.getName());
		if (home != null) {
			return home;
		}
		throw new GenerateRequestException("EBDeferralRequestItemLocalHome is null!");
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