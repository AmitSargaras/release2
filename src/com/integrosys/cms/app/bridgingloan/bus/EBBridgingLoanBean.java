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
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.ejbsupport.VersionGenerator;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSErrorCodes;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.TypeConverter;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public abstract class EBBridgingLoanBean implements EntityBean, IBridgingLoan {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getProjectID" };

	/**
	 * Default Constructor
	 */
	public EBBridgingLoanBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of IBridgingLoan object.
	 * @return IBridgingLoan business object
	 */
	public IBridgingLoan getValue() throws BridgingLoanException {
		try {
			IBridgingLoan value = new OBBridgingLoan();
			AccessorUtil.copyValue(this, value);
			populateLimitDetails(value);

			IPropertyType[] propertyTypeList = retrievePropertyTypeItems();
			value.setPropertyTypeList(propertyTypeList);

			IProjectSchedule[] projectScheduleList = retrieveProjectScheduleItems();
			value.setProjectScheduleList(projectScheduleList);

			IDisbursement[] disbursementList = retrieveDisbursementItems();
			value.setDisbursementList(disbursementList);

			ISettlement[] settlementList = retrieveSettlementItems();
			value.setSettlementList(settlementList);

			IBuildUp[] buildUpList = retrieveBuildUpItems();
			value.setBuildUpList(buildUpList);

			IFDR[] fdrList = retrieveFDRItems();
			value.setFdrList(fdrList);

			return value;
		}
		catch (SearchDAOException e) {
			throw new BridgingLoanException(e);
		}
	}

	/**
	 * Sets the IBridgingLoan object.
	 * @param value of type IBridgingLoan
	 * @throws BridgingLoanException
	 */
	public void setValue(IBridgingLoan value) throws BridgingLoanException, ConcurrentUpdateException {
		try {
			DefaultLogger.debug(this, "Inside setValue");
			DefaultLogger.debug(this, "value: " + value);
			// TODO: checkVersionMismatch remarked
			// checkVersionMismatch(value);
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			updatePropertyTypeItems(value.getPropertyTypeList());
			updateProjectScheduleItems(value.getProjectScheduleList());
			updateDisbursementItems(value.getDisbursementList());
			updateSettlementItems(value.getSettlementList());
			updateBuildUpItems(value.getBuildUpList());
			updateFDRItems(value.getFdrList());
			this.setVersionTime(VersionGenerator.getVersionNumber());

		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new BridgingLoanException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * Helper method to check the version of this IBridgingLoan object.
	 * 
	 * @param value bridging loan object
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         if the entity version is invalid
	 */
	private void checkVersionMismatch(IBridgingLoan value) throws ConcurrentUpdateException {
		if (getVersionTime() != value.getVersionTime()) {
			ConcurrentUpdateException exp = new ConcurrentUpdateException("Mismatch timestamp");
			exp.setErrorCode(ICMSErrorCodes.CONCURRENT_UPDATE);
			throw exp;
		}
	}

	// ========================
	// Derived Methods
	// ========================
	public Amount getFinancedAmount() {
		return TypeConverter.convertToAmount(TypeConverter.convertToObjectType(getCMPContractValue().doubleValue()
				* getCMPFinancePercent().doubleValue() / 100), getCMPContractCurrency());
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPProjectID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPLimitID();

	public String productDescription;

	public String sourceLimit;

	public abstract Double getCMPContractValue();

	public abstract String getCMPContractCurrency();

	public abstract Integer getCMPNoOfTypes();

	public abstract Integer getCMPNoOfUnits();

	public abstract Float getCMPFinancePercent();

	public abstract String getCMPIsDeleted();

	public abstract long getVersionTime();

	public long getProjectID() {
		if (getCMPProjectID() != null) {
			return getCMPProjectID().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getLimitProfileID() {
		if (getCMPLimitProfileID() != null) {
			return getCMPLimitProfileID().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public long getLimitID() {
		if (getCMPLimitID() != null) {
			return getCMPLimitID().longValue();
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	public String getSourceLimit() {
		return sourceLimit;
	}

	public String getProductDescription() {
		return ICMSConstant.PRODUCT_DESCRIPTION;
	}

	public Amount getContractAmount() {
		if ((getCMPContractValue() != null) && (getCMPContractCurrency() != null)) {
			return TypeConverter.convertToAmount(getCMPContractValue(), getCMPContractCurrency());
		}
		return null;
	}

	public int getNoOfTypes() {
		return TypeConverter.convertToPrimitiveType(getCMPNoOfTypes());
	}

	public int getNoOfUnits() {
		return TypeConverter.convertToPrimitiveType(getCMPNoOfUnits());
	}

	public float getFinancePercent() {
		if (getCMPFinancePercent() != null) {
			return getCMPFinancePercent().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	public boolean getIsDeletedInd() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsDeleted());
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPProjectID(Long id);

	public abstract void setCMPLimitProfileID(Long limitProfileID);

	public abstract void setCMPLimitID(Long limitID);

	public abstract void setCMPContractValue(Double contractValue);

	public abstract void setCMPContractCurrency(String contractCurrency);

	public abstract void setCMPNoOfTypes(Integer types);

	public abstract void setCMPNoOfUnits(Integer units);

	public abstract void setCMPFinancePercent(Float financePercent);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setProjectID(long id) {
		setCMPProjectID(new Long(id));
	}

	public void setLimitProfileID(long limitProfileID) {
		setCMPLimitProfileID(new Long(limitProfileID));
	}

	public void setLimitID(long limitID) {
		setCMPLimitID(new Long(limitID));
	}

	public void setSourceLimit(String sourceLimit) {
		this.sourceLimit = sourceLimit;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public void setContractAmount(Amount contractAmount) {
		if (contractAmount != null) {
			setCMPContractValue(new Double(contractAmount.getAmount()));
			setCMPContractCurrency(contractAmount.getCurrencyCode());
		}
		else {
			setCMPContractValue(null);
			setCMPContractCurrency(null);
		}
	}

	public void setNoOfTypes(int types) {
		setCMPNoOfTypes(new Integer(types));
	}

	public void setNoOfUnits(int units) {
		setCMPNoOfUnits(new Integer(units));
	}

	public void setFinancePercent(float financePercent) {
		setCMPFinancePercent(new Float(financePercent));
	}

	public void setIsDeletedInd(boolean isDeleted) {
		setCMPIsDeleted(TypeConverter.convertBooleanToStringEquivalent(isDeleted));
	}

	// ========================
	// CMR Methods
	// ========================
	public abstract Collection getCMRPropertyTypeItems();

	public abstract Collection getCMRProjectScheduleItems();

	public abstract Collection getCMRDisbursementItems();

	public abstract Collection getCMRSettlementItems();

	public abstract Collection getCMRBuildUpItems();

	public abstract Collection getCMRFDRItems();

	public abstract void setCMRPropertyTypeItems(Collection itemList);

	public abstract void setCMRProjectScheduleItems(Collection itemList);

	public abstract void setCMRDisbursementItems(Collection itemList);

	public abstract void setCMRSettlementItems(Collection itemList);

	public abstract void setCMRBuildUpItems(Collection itemList);

	public abstract void setCMRFDRItems(Collection itemList);

	public IPropertyType[] getPropertyTypeList() {
		return null; // not implemented here
	}

	public void setPropertyTypeList(IPropertyType[] list) {
		// do nothing - not implemented here
	}

	public IProjectSchedule[] getProjectScheduleList() {
		return null; // not implemented here
	}

	public void setProjectScheduleList(IProjectSchedule[] list) {
		// do nothing - not implemented here
	}

	public IDisbursement[] getDisbursementList() {
		return null; // not implemented here
	}

	public void setDisbursementList(IDisbursement[] list) {
		// do nothing - not implemented here
	}

	public ISettlement[] getSettlementList() {
		return null; // not implemented here
	}

	public void setSettlementList(ISettlement[] list) {
		// do nothing - not implemented here
	}

	public IBuildUp[] getBuildUpList() {
		return null; // not implemented here
	}

	public void setBuildUpList(IBuildUp[] list) {
		// do nothing - not implemented here
	}

	public IFDR[] getFdrList() {
		return null; // not implemented here
	}

	public void setFdrList(IFDR[] list) {
		// do nothing - not implemented here
	}

	// ==============================
	// CMR Getter Helper Methods
	// ==============================
	private IPropertyType[] retrievePropertyTypeItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRPropertyTypeItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBPropertyTypeLocal local = (EBPropertyTypeLocal) it.next();
				if (!local.getIsDeletedInd()) {
					IPropertyType obj = local.getValue();
					list.add(obj);
				}
			}
			return (IPropertyType[]) list.toArray(new IPropertyType[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing IPropertyType Items");
			throw new BridgingLoanException("Error in retrieveing IPropertyType Items", e);
		}
	}

	private IProjectSchedule[] retrieveProjectScheduleItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRProjectScheduleItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}
			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBProjectScheduleLocal local = (EBProjectScheduleLocal) it.next();
				if (!local.getIsDeletedInd()) {
					IProjectSchedule obj = local.getValue();
					list.add(obj);
				}
			}
			return (IProjectSchedule[]) list.toArray(new IProjectSchedule[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing IProjectSchedule Items");
			throw new BridgingLoanException("Error in retrieveing IProjectSchedule Items", e);
		}
	}

	private IDisbursement[] retrieveDisbursementItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRDisbursementItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBDisbursementLocal local = (EBDisbursementLocal) it.next();
				if (!local.getIsDeletedInd()) {
					IDisbursement obj = local.getValue();
					list.add(obj);
				}
			}
			return (IDisbursement[]) list.toArray(new IDisbursement[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing IDisbursement Items");
			throw new BridgingLoanException("Error in retrieveing IDisbursement Items", e);
		}
	}

	private ISettlement[] retrieveSettlementItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRSettlementItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBSettlementLocal local = (EBSettlementLocal) it.next();
				if (!local.getIsDeletedInd()) {
					ISettlement obj = local.getValue();
					list.add(obj);
				}
			}
			return (ISettlement[]) list.toArray(new ISettlement[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing ISettlement Items");
			throw new BridgingLoanException("Error in retrieveing ISettlement Items", e);
		}
	}

	private IBuildUp[] retrieveBuildUpItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRBuildUpItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBBuildUpLocal local = (EBBuildUpLocal) it.next();
				if (!local.getIsDeletedInd()) {
					IBuildUp obj = local.getValue();
					list.add(obj);
				}
			}
			return (IBuildUp[]) list.toArray(new IBuildUp[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing IBuildUp Items");
			throw new BridgingLoanException("Error in retrieveing IBuildUp Items", e);
		}
	}

	private IFDR[] retrieveFDRItems() throws BridgingLoanException {
		try {
			Collection collection = getCMRFDRItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBFDRLocal local = (EBFDRLocal) it.next();
				if (!local.getIsDeletedInd()) {
					IFDR obj = local.getValue();
					list.add(obj);
				}
			}
			return (IFDR[]) list.toArray(new IFDR[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing IFDR Items");
			throw new BridgingLoanException("Error in retrieveing IFDR Items", e);
		}
	}

	// ==============================
	// CMR Setter Helper Methods
	// ==============================
	/**
	 * Update the list of property type items under the current Bridging Loan
	 * @param itemList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void updatePropertyTypeItems(IPropertyType[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRPropertyTypeItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deletePropertyTypeItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createPropertyTypeItems(Arrays.asList(itemList));
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
					EBPropertyTypeLocal local = (EBPropertyTypeLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IPropertyType obj = (IPropertyType) itemMap.get(commonRef);
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

				deletePropertyTypeItems(deleteList);
				createPropertyTypeItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			ex.printStackTrace();
			throw ex;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BridgingLoanException("Exception in updatePropertyTypeItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of property type items under the current bridging loan
	 * @param aDeletionList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void deletePropertyTypeItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBPropertyTypeLocal local = (EBPropertyTypeLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deletePropertyTypeItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of property type items under the current bridging loan
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void createPropertyTypeItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRPropertyTypeItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBPropertyTypeLocalHome home = getEBPropertyTypeLocalHome();
			while (iter.hasNext()) {
				IPropertyType obj = (IPropertyType) iter.next();
				EBPropertyTypeLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new BridgingLoanException("Exception in createPropertyTypeItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Update the list of project schedule items under the current Bridging Loan
	 * @param itemList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void updateProjectScheduleItems(IProjectSchedule[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRProjectScheduleItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteProjectScheduleItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createProjectScheduleItems(Arrays.asList(itemList));
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
					EBProjectScheduleLocal local = (EBProjectScheduleLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IProjectSchedule obj = (IProjectSchedule) itemMap.get(commonRef);
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

				deleteProjectScheduleItems(deleteList);
				createProjectScheduleItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in updateProjectScheduleItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of project schedule items under the current bridging loan
	 * @param aDeletionList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void deleteProjectScheduleItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBProjectScheduleLocal local = (EBProjectScheduleLocal) iter.next();
				local.setIsDeletedInd(true);
				local.synchronizeDevelopmentDocItemList(local.getValue());
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deleteProjectScheduleItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of project schedule items under the current bridging loan
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void createProjectScheduleItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRProjectScheduleItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBProjectScheduleLocalHome home = getEBProjectScheduleLocalHome();
			while (iter.hasNext()) {
				IProjectSchedule obj = (IProjectSchedule) iter.next();
				EBProjectScheduleLocal local = home.create(obj);
				local.synchronizeDevelopmentDocItemList(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in createProjectScheduleItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Update the list of disbursement items under the current Bridging Loan
	 * @param itemList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void updateDisbursementItems(IDisbursement[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRDisbursementItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteDisbursementItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createDisbursementItems(Arrays.asList(itemList));
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
					EBDisbursementLocal local = (EBDisbursementLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IDisbursement obj = (IDisbursement) itemMap.get(commonRef);
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

				deleteDisbursementItems(deleteList);
				createDisbursementItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in updateDisbursementItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of disbursement items under the current bridging loan
	 * @param aDeletionList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void deleteDisbursementItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBDisbursementLocal local = (EBDisbursementLocal) iter.next();
				local.setIsDeletedInd(true);
				local.synchronizeDisbursementDetailItemList(local.getValue());
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deleteDisbursementItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of disbursement items under the current bridging loan
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void createDisbursementItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRDisbursementItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBDisbursementLocalHome home = getEBDisbursementLocalHome();
			while (iter.hasNext()) {
				IDisbursement obj = (IDisbursement) iter.next();
				EBDisbursementLocal local = home.create(obj);
				local.synchronizeDisbursementDetailItemList(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in createDisbursementItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Update the list of settlement items under the current Bridging Loan
	 * @param itemList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void updateSettlementItems(ISettlement[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRSettlementItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteSettlementItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createSettlementItems(Arrays.asList(itemList));
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
					EBSettlementLocal local = (EBSettlementLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					ISettlement obj = (ISettlement) itemMap.get(commonRef);
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

				deleteSettlementItems(deleteList);
				createSettlementItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in updateSettlementItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of settlement items under the current bridging loan
	 * @param aDeletionList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void deleteSettlementItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBSettlementLocal local = (EBSettlementLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deleteSettlementItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of settlement items under the current bridging loan
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void createSettlementItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRSettlementItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBSettlementLocalHome home = getEBSettlementLocalHome();
			while (iter.hasNext()) {
				ISettlement obj = (ISettlement) iter.next();
				EBSettlementLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in createSettlementItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Update the list of buildup items under the current Bridging Loan
	 * @param itemList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void updateBuildUpItems(IBuildUp[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRBuildUpItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteBuildUpItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createBuildUpItems(Arrays.asList(itemList));
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
					EBBuildUpLocal local = (EBBuildUpLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IBuildUp obj = (IBuildUp) itemMap.get(commonRef);
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

				deleteBuildUpItems(deleteList);
				createBuildUpItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in updateBuildUpItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of buildup items under the current bridging loan
	 * @param aDeletionList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void deleteBuildUpItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBBuildUpLocal local = (EBBuildUpLocal) iter.next();
				local.setIsDeletedInd(true);
				local.synchronizeSalesProceedsItemList(local.getValue());
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deleteBuildUpItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of buildup items under the current bridging loan
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void createBuildUpItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRBuildUpItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBBuildUpLocalHome home = getEBBuildUpLocalHome();
			while (iter.hasNext()) {
				IBuildUp obj = (IBuildUp) iter.next();
				EBBuildUpLocal local = home.create(obj);
				local.synchronizeSalesProceedsItemList(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in createBuildUpItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Update the list of FDR items under the current Bridging Loan
	 * @param itemList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void updateFDRItems(IFDR[] itemList) throws BridgingLoanException {
		try {
			Collection col = getCMRFDRItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteFDRItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createFDRItems(Arrays.asList(itemList));
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
					EBFDRLocal local = (EBFDRLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IFDR obj = (IFDR) itemMap.get(commonRef);
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

				deleteFDRItems(deleteList);
				createFDRItems(createList);
			}
		}
		catch (BridgingLoanException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in updateFDRItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of FDR items under the current bridging loan
	 * @param aDeletionList - List
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void deleteFDRItems(List aDeletionList) throws BridgingLoanException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBFDRLocal local = (EBFDRLocal) iter.next();
				local.setIsDeletedInd(true);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in deleteFDRItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of FDR items under the current bridging loan
	 * @throws com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException on
	 *         errors
	 */
	private void createFDRItems(List aCreationList) throws BridgingLoanException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRFDRItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBFDRLocalHome home = getEBFDRLocalHome();
			while (iter.hasNext()) {
				IFDR obj = (IFDR) iter.next();
				EBFDRLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new BridgingLoanException("Exception in createFDRItems: " + ex.toString(), ex);
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
	 * @return long primary key
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(IBridgingLoan object) throws CreateException {
		if (object == null) {
			throw new CreateException("IBridgingLoan object to be created is null");
		}

		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_BRIDGING_LOAN, true));
			AccessorUtil.copyValue(object, this, EXCLUDE_METHOD);
			setCMPProjectID(new Long(pk));
			this.setVersionTime(VersionGenerator.getVersionNumber());
			return new Long(pk);
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error on ejbCreate: ", e);
			throw new CreateException(e.toString());
		}
	}

	/**
	 * The container invokes this method after invoking the ejbCreate method
	 * with the same arguments.
	 * 
	 * @param value object of IBridgingLoan
	 * @throws javax.ejb.CreateException on error creating the references
	 */
	public void ejbPostCreate(IBridgingLoan value) throws CreateException {
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

	/**
	 * Get the list of bridging loan summary info.
	 * @param aLimitProfileID of long type
	 * @return IBridgingLoanSummary[] - the list of bridging loan summary
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on errors
	 */
	public IBridgingLoanSummary[] ejbHomeGetBridgingLoanSummaryList(long aLimitProfileID) throws SearchDAOException {
		return BridgingLoanDAOFactory.getBridgingLoanDAO().getBridgingLoanSummaryList(aLimitProfileID);
	}

	public void populateLimitDetails(IBridgingLoan value) throws SearchDAOException {
		HashMap map = BridgingLoanDAOFactory.getBridgingLoanDAO().getLimitDetailsByLimitID(getLimitID());
		value.setProductDescription((String) map.get("productDescription"));
		value.setSourceLimit((String) map.get("sourceLimit"));
	}

	// ==============================
	// Locating EB Methods
	// ==============================
	/**
	 * Method to get EB Local Home for the Property Type EB
	 */
	protected EBPropertyTypeLocalHome getEBPropertyTypeLocalHome() throws BridgingLoanException {
		EBPropertyTypeLocalHome home = (EBPropertyTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BL_PROPERTY_TYPE_LOCAL_JNDI, EBPropertyTypeLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBPropertyTypeLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Project Schedule EB
	 */
	protected EBProjectScheduleLocalHome getEBProjectScheduleLocalHome() throws BridgingLoanException {
		EBProjectScheduleLocalHome home = (EBProjectScheduleLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BL_PROJECT_SCHEDULE_LOCAL_JNDI, EBProjectScheduleLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBProjectScheduleLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Disbursement EB
	 */
	protected EBDisbursementLocalHome getEBDisbursementLocalHome() throws BridgingLoanException {
		EBDisbursementLocalHome home = (EBDisbursementLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BL_DISBURSEMENT_LOCAL_JNDI, EBDisbursementLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBDisbursementLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Settlement EB
	 */
	protected EBSettlementLocalHome getEBSettlementLocalHome() throws BridgingLoanException {
		EBSettlementLocalHome home = (EBSettlementLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BL_SETTLEMENT_LOCAL_JNDI, EBSettlementLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBSettlementLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the BuildUp EB
	 */
	protected EBBuildUpLocalHome getEBBuildUpLocalHome() throws BridgingLoanException {
		EBBuildUpLocalHome home = (EBBuildUpLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_BL_BUILDUP_LOCAL_JNDI, EBBuildUpLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBBuildUpLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the FDR EB
	 */
	protected EBFDRLocalHome getEBFDRLocalHome() throws BridgingLoanException {
		EBFDRLocalHome home = (EBFDRLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_BL_FDR_LOCAL_JNDI,
				EBFDRLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new BridgingLoanException("EBFDRLocalHome is null!");
	}
}
