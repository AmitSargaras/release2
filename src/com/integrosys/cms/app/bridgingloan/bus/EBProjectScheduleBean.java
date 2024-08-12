package com.integrosys.cms.app.bridgingloan.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
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
import com.integrosys.cms.app.common.util.TypeConverter;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBProjectScheduleBean implements EntityBean, IProjectSchedule {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getScheduleID", "getProjectID", "getCommonRef" };

	/**
	 * Default Constructor
	 */
	public EBProjectScheduleBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of IProjectSchedule object.
	 * @return IProjectSchedule business object
	 */
	public IProjectSchedule getValue() throws BridgingLoanException {
		IProjectSchedule value = new OBProjectSchedule();
		AccessorUtil.copyValue(this, value);

		IDevelopmentDoc[] docList = retrieveDevDocItems();
		value.setDevelopmentDocList(docList);
		return value;
	}

	/**
	 * Sets the IProjectSchedule object.
	 * @param value of type IProjectSchedule
	 * @throws BridgingLoanException
	 */
	public void setValue(IProjectSchedule value) throws BridgingLoanException {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		updateDevDocItems(value.getDevelopmentDocList());
	}

	// ========================================
	// Methods that are not container managed
	// ========================================
	public void synchronizeDevelopmentDocItemList(IProjectSchedule value) throws BridgingLoanException {
		updateDevDocItems(value.getDevelopmentDocList());
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPScheduleID();

	public abstract Long getCMPProjectID();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getScheduleID() {
		return TypeConverter.convertToPrimitiveType(getCMPScheduleID());
	}

	public long getProjectID() {
		return TypeConverter.convertToPrimitiveType(getCMPProjectID());
	}

	public long getCommonRef() {
		return TypeConverter.convertToPrimitiveType(getCMPCommonRef());
	}

	public boolean getIsDeletedInd() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsDeleted());
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPScheduleID(Long id);

	public abstract void setCMPProjectID(Long projectID);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setScheduleID(long id) {
		setCMPScheduleID(new Long(id));
	}

	public void setProjectID(long projectID) {
		setCMPProjectID(new Long(projectID));
	}

	public void setCommonRef(long commonRef) {
		setCMPCommonRef(new Long(commonRef));
	}

	public void setIsDeletedInd(boolean isDeleted) {
		setCMPIsDeleted(TypeConverter.convertBooleanToStringEquivalent(isDeleted));
	}

	// ========================
	// CMR Methods
	// ========================
	public abstract Collection getCMRDevDocItems();

	public abstract void setCMRDevDocItems(Collection itemList);

	public IDevelopmentDoc[] getDevelopmentDocList() {
		return null; // not implemented here
	}

	public void setDevelopmentDocList(IDevelopmentDoc[] list) {
		// do nothing - not implemented here
	}

	// ==============================
	// CMR Getter Helper Methods
	// ==============================
	private IDevelopmentDoc[] retrieveDevDocItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRDevDocItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBDevDocLocal local = (EBDevDocLocal) it.next();
				if (!local.getIsDeletedInd()) {
					IDevelopmentDoc obj = local.getValue();
					list.add(obj);
				}
			}
			return (IDevelopmentDoc[]) list.toArray(new IDevelopmentDoc[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing IDevelopmentDoc Items");
			throw new BridgingLoanException("Error in retrieveing IDevelopmentDoc Items", e);
		}
	}

	// ==============================
	// CMR Setter Helper Methods
	// ==============================
	/**
	 * Update the list of development document items under the current Project
	 * Schedule
	 * @param itemList - List
	 * @throws BridgingLoanException on errors
	 */
	private void updateDevDocItems(IDevelopmentDoc[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRDevDocItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteDevDocItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createDevDocItems(Arrays.asList(itemList));
			}
			else { // need to determine whether to add, update or delete
				Iterator iter = col.iterator();
				ArrayList createList = new ArrayList();
				ArrayList deleteList = new ArrayList(); // contains list of
														// local interfaces
				HashMap cmrMap = new HashMap();

				HashMap itemMap = new HashMap();
				for (int i = 0; i < itemList.length; i++) {
					long commonRef = itemList[i].getCommonRef();
					itemMap.put(new Long(commonRef), itemList[i]);
				}

				// identify identify records for delete or udpate first
				while (iter.hasNext()) {
					EBDevDocLocal local = (EBDevDocLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IDevelopmentDoc obj = (IDevelopmentDoc) itemMap.get(commonRef);
					if (obj == null) {
						deleteList.add(local);
					}
					else {
						local.setValue(obj);
					}
				}

				// next identify records for add
				for (int i = 0; i < itemList.length; i++) {
					long commonRef = itemList[i].getCommonRef();
					if (cmrMap.get(new Long(commonRef)) == null) {
						createList.add(itemList[i]);
					}
				}

				deleteDevDocItems(deleteList);
				createDevDocItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			ex.printStackTrace();
			throw ex;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BridgingLoanException("Exception in updateDevDocItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of development document items under the current project
	 * schedule
	 * @param aDeletionList - List
	 * @throws BridgingLoanException on errors
	 */
	private void deleteDevDocItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBDevDocLocal local = (EBDevDocLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deleteDevDocItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of development document items under the current project
	 * schedule
	 * @throws BridgingLoanException on errors
	 */
	private void createDevDocItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRDevDocItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBDevDocLocalHome home = getEBDevDocLocalHome();
			while (iter.hasNext()) {
				IDevelopmentDoc obj = (IDevelopmentDoc) iter.next();
				EBDevDocLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BridgingLoanException("Exception in createDevDocItems: " + ex.toString(), ex);
		}
	}

	// ==============================
	// Standard Methods
	// ==============================
	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param object IFDR object to be created
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(IProjectSchedule object) throws CreateException {
		if (object == null) {
			throw new CreateException("IProjectSchedule object to be created is null");
		}

		try {
			long pk = Long
					.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_BL_PROJECT_SCHEDULE, true));
			setScheduleID(pk);
			if (object.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonRef(pk);
			}
			else {
				setCommonRef(object.getCommonRef());
			}

			AccessorUtil.copyValue(object, this, EXCLUDE_METHOD);
			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of IProjectSchedule
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IProjectSchedule value) throws CreateException {
	}

	/**
	 * EJB callback method to set the context of the bean.
	 * 
	 * @param context the entity context.
	 */
	public void setEntityContext(EntityContext context) {
		_context = context;
	}

	/**
	 * EJB callback method to clears the context of the bean.
	 */
	public void unsetEntityContext() {
		_context = null;
	}

	/**
	 * This method is called when the container picks this entity object and
	 * assigns it to a specific entity object. No implementation currently
	 * acquires any additional resources that it needs when it is in the ready
	 * state.
	 */
	public void ejbActivate() {
	}

	/**
	 * This method is called when the container diassociates the bean from the
	 * entity object identity and puts the instance back into the pool of
	 * available instances. No implementation is currently provided to release
	 * resources that should not be held while the instance is in the pool.
	 */
	public void ejbPassivate() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the bean's state with the state in the database.
	 * This method is called after the container has loaded the bean's state
	 * from the database.
	 */
	public void ejbLoad() {
	}

	/**
	 * The container invokes this method on the bean whenever it becomes
	 * necessary to synchronize the state in the database with the state of the
	 * bean. This method is called before the container extracts the fields and
	 * writes them into the database.
	 */
	public void ejbStore() {
	}

	/**
	 * The container invokes this method in response to a client-invoked remove
	 * request. No implementation is currently provided for taking actions
	 * before the bean is removed from the database.
	 */
	public void ejbRemove() {
	}

	// ==============================
	// DAO Methods
	// ==============================

	// ==============================
	// Locating EB Methods
	// ==============================
	/**
	 * Method to get EB Local Home for the Development Document EB
	 */
	protected EBDevDocLocalHome getEBDevDocLocalHome() throws BridgingLoanException {
		EBDevDocLocalHome home = (EBDevDocLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BL_DEV_DOC_LOCAL_JNDI, EBDevDocLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBDevDocLocalHome is null!");
	}
}