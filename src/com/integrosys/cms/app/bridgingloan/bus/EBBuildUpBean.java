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

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.bus.IArea;
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
public abstract class EBBuildUpBean implements EntityBean, IBuildUp {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getBuildUpID", "getProjectID", "getCommonRef" };

	/**
	 * Default Constructor
	 */
	public EBBuildUpBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of IBuildUp object.
	 * @return IBuildUp business object
	 * @throws BridgingLoanException
	 */
	public IBuildUp getValue() throws BridgingLoanException {
		IBuildUp value = new OBBuildUp();
		AccessorUtil.copyValue(this, value);

		ISalesProceeds[] itemList = retrieveSalesProceedsItems();
		value.setSalesProceedsList(itemList);
		return value;
	}

	/**
	 * Sets the IBuildUp object.
	 * @param value of type IBuildUp
	 * @throws BridgingLoanException
	 */
	public void setValue(IBuildUp value) throws BridgingLoanException {
		AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
		updateSalesProceedsItems(value.getSalesProceedsList());
	}

	// ========================================
	// Methods that are not container managed
	// ========================================
	public void synchronizeSalesProceedsItemList(IBuildUp value) throws BridgingLoanException {
		updateSalesProceedsItems(value.getSalesProceedsList());
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPBuildUpID();

	public abstract Long getCMPProjectID();

	public abstract String getCMPIsUnitDischarged();

	public abstract Double getCMPApproxAreaSize();

	public abstract String getCMPApproxAreaUOM();

	public abstract Double getCMPApproxAreaSizeSecondary();

	public abstract String getCMPApproxAreaUOMSecondary();

	public abstract Double getCMPRedemptionValue();

	public abstract String getCMPRedemptionCurrency();

	public abstract Double getCMPSalesValue();

	public abstract String getCMPSalesCurrency();

	public abstract Integer getCMPTenancyPeriod();

	public abstract Double getCMPRentalValue();

	public abstract String getCMPRentalCurrency();

	public abstract Long getCMPCommonRef();

	public abstract String getCMPIsDeleted();

	public long getBuildUpID() {
		return TypeConverter.convertToPrimitiveType(getCMPBuildUpID());
	}

	public long getProjectID() {
		return TypeConverter.convertToPrimitiveType(getCMPProjectID());
	}

	public boolean getIsUnitDischarged() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsUnitDischarged());
	}

	public IArea getApproxArea() {
		return TypeConverter.convertToArea(getCMPApproxAreaSize(), getCMPApproxAreaUOM());
	}

	public IArea getApproxAreaSecondary() {
		return TypeConverter.convertToArea(getCMPApproxAreaSizeSecondary(), getCMPApproxAreaUOMSecondary());
	}

	public Amount getRedemptionAmount() {
		if ((getCMPRedemptionValue() != null) && (getCMPRedemptionCurrency() != null)) {
			return TypeConverter.convertToAmount(getCMPRedemptionValue(), getCMPRedemptionCurrency());
		}
		return null;
	}

	public Amount getSalesAmount() {
		if ((getCMPRedemptionValue() != null) && (getCMPRedemptionCurrency() != null)) {
			return TypeConverter.convertToAmount(getCMPSalesValue(), getCMPSalesCurrency());
		}
		return null;
	}

	public int getTenancyPeriod() {
		return TypeConverter.convertToPrimitiveType(getCMPTenancyPeriod());
	}

	public long getCommonRef() {
		return TypeConverter.convertToPrimitiveType(getCMPCommonRef());
	}

	public Amount getRentalAmount() {
		return TypeConverter.convertToAmount(getCMPRentalValue(), getCMPRentalCurrency());
	}

	public boolean getIsDeletedInd() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsDeleted());
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPBuildUpID(Long id);

	public abstract void setCMPProjectID(Long id);

	public abstract void setCMPIsUnitDischarged(String flag);

	public abstract void setCMPApproxAreaSize(Double areaSize);

	public abstract void setCMPApproxAreaUOM(String uom);

	public abstract void setCMPApproxAreaSizeSecondary(Double areaSize);

	public abstract void setCMPApproxAreaUOMSecondary(String uom);

	public abstract void setCMPRedemptionValue(Double value);

	public abstract void setCMPRedemptionCurrency(String currency);

	public abstract void setCMPSalesValue(Double value);

	public abstract void setCMPSalesCurrency(String currency);

	public abstract void setCMPTenancyPeriod(Integer period);

	public abstract void setCMPRentalValue(Double value);

	public abstract void setCMPRentalCurrency(String currency);

	public abstract void setCMPCommonRef(Long commonRef);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setBuildUpID(long id) {
		setCMPBuildUpID(new Long(id));
	}

	public void setProjectID(long id) {
		setCMPProjectID(new Long(id));
	}

	public void setIsUnitDischarged(boolean isUnitDischarged) {
		setCMPIsUnitDischarged(TypeConverter.convertBooleanToStringEquivalent(isUnitDischarged));
	}

	public void setApproxArea(IArea area) {
		if (area != null) {
			setCMPApproxAreaSize(new Double(area.getAreaSize()));
			setCMPApproxAreaUOM(area.getUnitOfMeasurement());
		}
		else {
			setCMPApproxAreaSize(null);
			setCMPApproxAreaUOM(null);
		}
	}

	public void setApproxAreaSecondary(IArea area) {
		if (area != null) {
			setCMPApproxAreaSizeSecondary(new Double(area.getAreaSize()));
			setCMPApproxAreaUOMSecondary(area.getUnitOfMeasurement());
		}
		else {
			setCMPApproxAreaSizeSecondary(null);
			setCMPApproxAreaUOMSecondary(null);
		}
	}

	public void setRedemptionAmount(Amount amt) {
		if (amt != null) {
			setCMPRedemptionValue(new Double(amt.getAmount()));
			setCMPRedemptionCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPRedemptionValue(null);
			setCMPRedemptionCurrency(null);
		}
	}

	public void setSalesAmount(Amount amt) {
		if (amt != null) {
			setCMPSalesValue(new Double(amt.getAmount()));
			setCMPSalesCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPSalesValue(null);
			setCMPSalesCurrency(null);
		}
	}

	public void setTenancyPeriod(int period) {
		setCMPTenancyPeriod(new Integer(period));
	}

	public void setRentalAmount(Amount amt) {
		if (amt != null) {
			setCMPRentalValue(new Double(amt.getAmount()));
			setCMPRentalCurrency(amt.getCurrencyCode());
		}
		else {
			setCMPRentalValue(null);
			setCMPRentalCurrency(null);
		}
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
	public abstract Collection getCMRSalesProceedsItems();

	public abstract void setCMRSalesProceedsItems(Collection itemList);

	public ISalesProceeds[] getSalesProceedsList() {
		return null; // not implemented here
	}

	public void setSalesProceedsList(ISalesProceeds[] list) {
		// do nothing - not implemented here
	}

	// ==============================
	// CMR Getter Helper Methods
	// ==============================
	private ISalesProceeds[] retrieveSalesProceedsItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRSalesProceedsItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBSalesProceedsLocal local = (EBSalesProceedsLocal) it.next();
				if (!local.getIsDeletedInd()) {
					ISalesProceeds obj = local.getValue();
					list.add(obj);
				}
			}
			return (ISalesProceeds[]) list.toArray(new ISalesProceeds[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing ISalesProceeds Items");
			throw new BridgingLoanException("Error in retrieveing ISalesProceeds Items", e);
		}
	}

	// ==============================
	// CMR Setter Helper Methods
	// ==============================
	/**
	 * Update the list of sales proceeds items under the current Project
	 * Schedule
	 * @param itemList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void updateSalesProceedsItems(ISalesProceeds[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRSalesProceedsItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteSalesProceedsItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createSalesProceedsItems(Arrays.asList(itemList));
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
					EBSalesProceedsLocal local = (EBSalesProceedsLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					ISalesProceeds obj = (ISalesProceeds) itemMap.get(commonRef);
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

				deleteSalesProceedsItems(deleteList);
				createSalesProceedsItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in updateSalesProceedsItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of sales proceeds items under the current project
	 * schedule
	 * @param aDeletionList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void deleteSalesProceedsItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBSalesProceedsLocal local = (EBSalesProceedsLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deleteSalesProceedsItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of sales proceeds items under the current build up
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void createSalesProceedsItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRSalesProceedsItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBSalesProceedsLocalHome home = getEBSalesProceedsLocalHome();
			while (iter.hasNext()) {
				ISalesProceeds obj = (ISalesProceeds) iter.next();
				EBSalesProceedsLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in createSalesProceedsItems: " + ex.toString(), ex);
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
	public Long ejbCreate(IBuildUp object) throws CreateException {
		if (object == null) {
			throw new CreateException("IBuildUp object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_BL_BUILDUP, true));
			setBuildUpID(pk);
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
	 * @param value object of IBuildUp
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IBuildUp value) throws CreateException {
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
	 * Method to get EB Local Home for the Sales Proceeds EB
	 */
	protected EBSalesProceedsLocalHome getEBSalesProceedsLocalHome() throws BridgingLoanException {
		EBSalesProceedsLocalHome home = (EBSalesProceedsLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BL_SALES_PROCEEDS_LOCAL_JDNI, EBSalesProceedsLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBSalesProceedsLocalHome is null!");
	}
}