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
public abstract class EBDisbursementBean implements EntityBean, IDisbursement {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getDisbursementID", "getProjectID", "getCommonRef" };

	/**
	 * Default Constructor
	 */
	public EBDisbursementBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of IDisbursement object.
	 * @return IDisbursement business object
	 */
	public IDisbursement getValue() throws BridgingLoanException {
		IDisbursement value = new OBDisbursement();
		AccessorUtil.copyValue(this, value);

		IDisbursementDetail[] docList = retrieveDisbursementDetailItems();
		value.setDisbursementDetailList(docList);
		return value;
	}

	/**
	 * Sets the IDisbursement object.
	 * @param value of type IDisbursement
	 * @throws BridgingLoanException
	 */
	public void setValue(IDisbursement value) throws BridgingLoanException {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		updateDisbursementDetailItems(value.getDisbursementDetailList());
	}

	// ========================================
	// Methods that are not container managed
	// ========================================
	public void synchronizeDisbursementDetailItemList(IDisbursement value) throws BridgingLoanException {
		updateDisbursementDetailItems(value.getDisbursementDetailList());
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPDisbursementID();

	public abstract Long getCMPProjectID();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getDisbursementID() {
		return TypeConverter.convertToPrimitiveType(getCMPDisbursementID());
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
	public abstract void setCMPDisbursementID(Long id);

	public abstract void setCMPProjectID(Long id);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setDisbursementID(long id) {
		setCMPDisbursementID(new Long(id));
	}

	public void setProjectID(long id) {
		setCMPProjectID(new Long(id));
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
	public abstract Collection getCMRDisbursementDetailItems();

	public abstract void setCMRDisbursementDetailItems(Collection itemList);

	public IDisbursementDetail[] getDisbursementDetailList() {
		return null; // not implemented here
	}

	public void setDisbursementDetailList(IDisbursementDetail[] list) {
		// do nothing - not implemented here
	}

	// ==============================
	// CMR Getter Helper Methods
	// ==============================
	private IDisbursementDetail[] retrieveDisbursementDetailItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRDisbursementDetailItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBDisbursementDetailLocal local = (EBDisbursementDetailLocal) it.next();
				if (!local.getIsDeletedInd()) {
					IDisbursementDetail obj = local.getValue();
					list.add(obj);
				}
			}
			return (IDisbursementDetail[]) list.toArray(new IDisbursementDetail[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing IDisbursementDetail Items");
			throw new BridgingLoanException("Error in retrieveing IDisbursementDetail Items", e);
		}
	}

	/**
	 * Update the list of disbursement detail items under the current Bridging
	 * Loan
	 * @param itemList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void updateDisbursementDetailItems(IDisbursementDetail[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRDisbursementDetailItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteDisbursementDetailItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createDisbursementDetailItems(Arrays.asList(itemList));
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
					EBDisbursementDetailLocal local = (EBDisbursementDetailLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IDisbursementDetail obj = (IDisbursementDetail) itemMap.get(commonRef);
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

				deleteDisbursementDetailItems(deleteList);
				createDisbursementDetailItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in updateDisbursementDetailItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of disbursement detail items under the current bridging
	 * loan
	 * @param aDeletionList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void deleteDisbursementDetailItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBDisbursementDetailLocal local = (EBDisbursementDetailLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deleteDisbursementDetailItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of disbursement detail items under the current bridging
	 * loan
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void createDisbursementDetailItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRDisbursementDetailItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBDisbursementDetailLocalHome home = getEBDisbursementDetailLocalHome();
			while (iter.hasNext()) {
				IDisbursementDetail obj = (IDisbursementDetail) iter.next();
				EBDisbursementDetailLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in createDisbursementDetailItems: " + ex.toString(), ex);
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
	public Long ejbCreate(IDisbursement object) throws CreateException {
		if (object == null) {
			throw new CreateException("IDisbursement object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_BL_DISBURSEMENT, true));
			setDisbursementID(pk);
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
	 * @param value object of IDisbursement
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IDisbursement value) throws CreateException {
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
	 * Method to get EB Local Home for the Disbursement Detail EB
	 */
	protected EBDisbursementDetailLocalHome getEBDisbursementDetailLocalHome() throws BridgingLoanException {
		EBDisbursementDetailLocalHome home = (EBDisbursementDetailLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BL_DISBURSE_DETAIL_LOCAL_JNDI, EBDisbursementDetailLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBDisbursementDetailLocalHome is null!");
	}
}