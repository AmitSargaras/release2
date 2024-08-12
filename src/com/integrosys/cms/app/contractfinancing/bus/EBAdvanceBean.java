package com.integrosys.cms.app.contractfinancing.bus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.CreateException;
import javax.ejb.EntityBean;
import javax.ejb.EntityContext;

import com.integrosys.base.businfra.currency.Amount;
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
public abstract class EBAdvanceBean implements EntityBean, IAdvance {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getAdvanceID", "getContractID", "getCommonRef" };

	/**
	 * Default Constructor
	 */
	public EBAdvanceBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of advance object.
	 * @return contract facility type business object
	 * @throws ContractFinancingException on errors
	 */
	public IAdvance getValue() throws ContractFinancingException {
		IAdvance value = new OBAdvance();
		AccessorUtil.copyValue(this, value);

		IPayment[] paymentList = retrievePaymentItems();
		value.setPaymentList(paymentList);

		return value;
	}

	/**
	 * Set the advance object.
	 * @param value of type IAdvance
	 * @throws ContractFinancingException on errors
	 */
	public void setValue(IAdvance value) throws ContractFinancingException {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		updatePaymentItems(value.getPaymentList());
	}

	// ========================================
	// Methods that are not container managed
	// ========================================
	public void synchronizePaymentItemList(IAdvance value) throws ContractFinancingException {
		updatePaymentItems(value.getPaymentList());
	}

	// ========================================
	// Methods that are not container managed
	// ========================================
	public float getFacilityTypeMOA() {
		return ICMSConstant.FLOAT_INVALID_VALUE; // not implemented here; see
													// EBContractFinanceBean
													// .retrieveAdvanceItems()
	}

	public void setFacilityTypeMOA(float facilityTypeMOA) {
		// do nothing
	}

	public String getFacilityType() {
		// do nothing
		return null;
	}

	public void setFacilityType(String facilityType) {
		// do nothing
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPAdvanceID();

	public abstract Long getCMPContractID();

	public abstract Long getCMPFacilityTypeID();

	public abstract Double getCMPClaimValue();

	public abstract String getCMPClaimCurrency();

	public abstract Double getCMPActualAdvanceValue();

	public abstract String getCMPActualAdvanceCurrency();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getAdvanceID() {
		return TypeConverter.convertToPrimitiveType(getCMPAdvanceID());
	}

	public long getContractID() {
		return TypeConverter.convertToPrimitiveType(getCMPContractID());
	}

	public long getFacilityTypeID() {
		return TypeConverter.convertToPrimitiveType(getCMPFacilityTypeID());
	}

	public Amount getAmount() {
		return TypeConverter.convertToAmount(getCMPClaimValue(), getCMPClaimCurrency());
	}

	public Amount getActualAdvanceAmount() {
		return TypeConverter.convertToAmount(getCMPActualAdvanceValue(), getCMPActualAdvanceCurrency());
	}

	public long getCommonRef() {
		return TypeConverter.convertToPrimitiveType(getCMPCommonRef());
	}

	public boolean getIsDeleted() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsDeleted());
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPAdvanceID(Long id);

	public abstract void setCMPContractID(Long contractID);

	public abstract void setCMPFacilityTypeID(Long id);

	public abstract void setCMPClaimValue(Double value);

	public abstract void setCMPClaimCurrency(String currency);

	public abstract void setCMPActualAdvanceValue(Double value);

	public abstract void setCMPActualAdvanceCurrency(String currency);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setAdvanceID(long id) {
		setCMPAdvanceID(new Long(id));
	}

	public void setContractID(long contractID) {
		setCMPContractID(new Long(contractID));
	}

	public void setFacilityTypeID(long id) {
		setCMPFacilityTypeID(new Long(id));
	}

	public void setAmount(Amount amt) {
		if (amt != null) {
			setCMPClaimValue(new Double(amt.getAmount()));
			setCMPClaimCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPClaimValue(null);
			setCMPClaimCurrency(null);
		}
	}

	public void setActualAdvanceAmount(Amount amt) {
		if (amt != null) {
			setCMPActualAdvanceValue(new Double(amt.getAmount()));
			setCMPActualAdvanceCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPActualAdvanceValue(null);
			setCMPActualAdvanceCurrency(null);
		}
	}

	public void setCommonRef(long commonRef) {
		setCMPCommonRef(new Long(commonRef));
	}

	public void setIsDeleted(boolean isDeleted) {
		setCMPIsDeleted(TypeConverter.convertBooleanToStringEquivalent(isDeleted));
	}

	// ========================
	// CMR Methods
	// ========================
	public abstract Collection getCMRPaymentItems();

	public abstract void setCMRPaymentItems(Collection itemList);

	public IPayment[] getPaymentList() {
		return null; // not implemented here
	}

	public void setPaymentList(IPayment[] list) {
		// do nothing - not implemented here
	}

	// ==============================
	// CMR Helper Methods
	// ==============================
	private IPayment[] retrievePaymentItems() throws ContractFinancingException {
		try {
			Collection collection = getCMRPaymentItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBPaymentLocal local = (EBPaymentLocal) it.next();
				if (!local.getIsDeleted()) {
					IPayment obj = local.getValue();
					list.add(obj);
				}
			}
			return (IPayment[]) list.toArray(new IPayment[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing Payment Items");
			throw new ContractFinancingException("Error in retrieveing Payment Items", e);
		}
	}

	private void updatePaymentItems(IPayment[] itemList) throws ContractFinancingException {
		try {
			Collection col = getCMRPaymentItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					// nothing to do
				}
				else { // delete all records
					deletePaymentItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createPaymentItems(Arrays.asList(itemList));
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
					EBPaymentLocal local = (EBPaymentLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IPayment paymentObj = (IPayment) itemMap.get(commonRef);
					if (paymentObj == null) {
						deleteList.add(local);
					}
					else {
						DefaultLogger.debug(this, ">>>>>>>>> 2. Setting value for paymentObj");
						local.setValue(paymentObj);
					}
				}

				// next identify records for add
				for (int i = 0; i < itemList.length; i++) {
					long commonRef = itemList[i].getCommonRef();
					if (cmrMap.get(new Long(commonRef)) == null) {
						createList.add(itemList[i]);
					}
				}

				deletePaymentItems(deleteList);
				createPaymentItems(createList);
			}
		}
		catch (ContractFinancingException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in updatePaymentItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of Payment items under the current Advance
	 * @param aDeletionList - List
	 * @throws ContractFinancingException on errors
	 */
	private void deletePaymentItems(List aDeletionList) throws ContractFinancingException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBPaymentLocal local = (EBPaymentLocal) iter.next();
				local.setIsDeleted(true);
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in deletePaymentItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of payments under the current advance
	 * @throws ContractFinancingException on errors
	 * @param aCreationList payment list
	 */
	private void createPaymentItems(List aCreationList) throws ContractFinancingException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRPaymentItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBPaymentLocalHome home = getEBPaymentLocalHome();
			while (iter.hasNext()) {
				IPayment obj = (IPayment) iter.next();
				DefaultLogger.debug(this, ">>>>>>>> 3. Calling Payment Home to create");
				EBPaymentLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in createPaymentItems: " + ex.toString(), ex);
		}
	}

	// ==============================
	// Standard Methods
	// ==============================
	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param object IAdvance object to be created
	 * @throws javax.ejb.CreateException on error creating the entity object
	 * @return pk
	 */
	public Long ejbCreate(IAdvance object) throws CreateException {
		if (object == null) {
			throw new CreateException("IAdvance object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CF_ADVANCE, true));
			setAdvanceID(pk);
			if (object.getCommonRef() == ICMSConstant.LONG_INVALID_VALUE) {
				setCommonRef(pk);
			}
			else {
				setCommonRef(object.getCommonRef());
			}

			AccessorUtil.copyValue(object, this, EXCLUDE_METHOD);
			// need to copy the CMP Methods?
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
	 * @param value object of IContractFinancing
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IAdvance value) throws CreateException {
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
	 * Method to get EB Local Home for the Payment EB
	 * @throws ContractFinancingException on error
	 * @return home
	 */
	protected EBPaymentLocalHome getEBPaymentLocalHome() throws ContractFinancingException {
		EBPaymentLocalHome home = (EBPaymentLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CF_PAYMENT_LOCAL_JNDI, EBPaymentLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBPaymentLocalHome is null!");
	}

}
