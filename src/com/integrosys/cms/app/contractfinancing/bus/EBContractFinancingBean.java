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
public abstract class EBContractFinancingBean implements EntityBean, IContractFinancing {

	/**
	 * The Entity Context
	 */
	protected EntityContext _context = null;

	private static final String[] EXCLUDE_METHOD = new String[] { "getContractID" };

	/**
	 * Default Constructor
	 */
	public EBContractFinancingBean() {
	}

	// ====================================
	// Get and Set Value
	// ====================================
	/**
	 * Retrieve an instance of contract financing object.
	 * 
	 * @return contract finance business object
	 */
	public IContractFinancing getValue() throws ContractFinancingException {
		try {
			IContractFinancing value = new OBContractFinancing();
			AccessorUtil.copyValue(this, value);
			populateLimitDetails(value);

			IContractFacilityType[] facilityTypeList = retrieveContractFacilityTypeItems();
			value.setFacilityTypeList(facilityTypeList);

			// need to set the MOA from facilityTypeList to the advanceList
			IAdvance[] advanceList = retrieveAdvanceItems(facilityTypeList);
			value.setAdvanceList(advanceList);

			ITNC[] tncList = retrieveTNCItems();
			value.setTncList(tncList);

			IFDR[] fdrList = retrieveFDRItems();
			value.setFdrList(fdrList);

			return value;
		}
		catch (SearchDAOException e) {
			throw new ContractFinancingException(e);
		}
	}

	/**
	 * Set the contract financing object.
	 * 
	 * @param value of type IContractFinancing
	 * @throws com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException
	 *         if more than one client accessing this contract financing object
	 *         at the same time
	 */
	public void setValue(IContractFinancing value) throws ConcurrentUpdateException, ContractFinancingException {
		try {
			// checkVersionMismatch (value);
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			updateFacilityTypeItems(value.getFacilityTypeList());

			// set facility type to list
			IAdvance[] advanceList = value.getAdvanceList();
			if ((advanceList != null) && (advanceList.length > 0)) {
				if (advanceList[0].getFacilityType() == null) { // when update
					advanceList = updateFacilityType(advanceList, value.getFacilityTypeList());
				}
			}

			IContractFacilityType[] facilityTypeList = retrieveContractFacilityTypeItems();
			value.setFacilityTypeList(facilityTypeList);

			if ((advanceList != null) && (advanceList.length > 0)) { // when
																		// create
																		// new
				advanceList = updateFacilityTypeID(advanceList, value.getFacilityTypeList());
			}

			updateAdvanceItems(advanceList);

			DefaultLogger.debug(this, "after updateAdvanceItems");
			updateTNCItems(value.getTncList());
			updateFDRItems(value.getFdrList());
			this.setVersionTime(VersionGenerator.getVersionNumber());
		}
		catch (Exception ex) {
			_context.setRollbackOnly();
			throw new ContractFinancingException("Exception in setValue: " + ex.toString());
		}
	}

	/**
	 * Helper method to check the version of this IContractFinancing object.
	 * 
	 * @param value contract financing object
	 * @throws ConcurrentUpdateException if the entity version is invalid
	 */
	private void checkVersionMismatch(IContractFinancing value) throws ConcurrentUpdateException {
		if (getVersionTime() != value.getVersionTime()) {
			ConcurrentUpdateException exp = new ConcurrentUpdateException("Mismatch timestamp");
			exp.setErrorCode(ICMSErrorCodes.CONCURRENT_UPDATE);
			throw exp;
		}
	}

	// ========================
	// Getters
	// ========================
	public abstract Long getCMPContractID();

	public abstract Long getCMPLimitProfileID();

	public abstract Long getCMPLimitID();

	public String sourceLimit;

	public String productDescription;

	public abstract Float getCMPFinancePercent();

	public abstract Double getCMPContractValue();

	public abstract String getCMPContractCurrency();

	public abstract Double getCMPActualFinanceValue();

	public abstract String getCMPActualFinanceCurrency();

	public abstract Double getCMPProjectedProfitValue();

	public abstract String getCMPProjectedProfitCurrency();

	public abstract Float getCMPBuildUpFDR();

	public abstract String getCMPIsDeleted();

	public abstract long getVersionTime();

	private IContractFacilityType facilityTypeList[];

	private IAdvance advanceList[];

	private ITNC tncList[];

	private IFDR fdrList[];

	public long getContractID() {
		if (getCMPContractID() != null) {
			return getCMPContractID().longValue();
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

	public void setSourceLimit(String sourceLimit) {
		this.sourceLimit = sourceLimit;
	}

	public String getProductDescription() {
		return ICMSConstant.PRODUCT_DESCRIPTION;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public float getFinancePercent() {
		if (getCMPFinancePercent() != null) {
			return getCMPFinancePercent().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	public Amount getContractAmount() {
		if ((getCMPContractValue() != null) && (getCMPContractCurrency() != null)) {
			return new Amount(getCMPContractValue().doubleValue(), getCMPContractCurrency());
		}
		return null;
	}

	public Amount getActualFinanceAmount() {
		if ((getCMPActualFinanceValue() != null) && (getCMPActualFinanceCurrency() != null)) {
			return new Amount(getCMPActualFinanceValue().doubleValue(), getCMPActualFinanceCurrency());
		}
		return null;
	}

	public Amount getProjectedProfit() {
		if ((getCMPProjectedProfitValue() != null) && (getCMPProjectedProfitCurrency() != null)) {
			return new Amount(getCMPProjectedProfitValue().doubleValue(), getCMPProjectedProfitCurrency());
		}
		return null;
	}

	public Amount getFinancedAmount() {
		return new Amount(getCMPContractValue().doubleValue() * getCMPFinancePercent().doubleValue() / 100,
				getCMPContractCurrency());
	}

	public IContractFacilityType[] getFacilityTypeList() {
		return facilityTypeList;
	}

	public void setFacilityTypeList(IContractFacilityType[] facilityTypeList) {
		this.facilityTypeList = facilityTypeList;
	}

	public IAdvance[] getAdvanceList() {
		return advanceList;
	}

	public void setAdvanceList(IAdvance[] advanceList) {
		this.advanceList = advanceList;
	}

	public ITNC[] getTncList() {
		return tncList;
	}

	public void setTncList(ITNC[] tncList) {
		this.tncList = tncList;
	}

	public IFDR[] getFdrList() {
		return fdrList;
	}

	public void setFdrList(IFDR[] fdrList) {
		this.fdrList = fdrList;
	}

	public float getBuildUpFDR() {
		if (getCMPBuildUpFDR() != null) {
			return getCMPBuildUpFDR().floatValue();
		}
		return ICMSConstant.FLOAT_INVALID_VALUE;
	}

	public boolean getIsDeleted() {
		return TypeConverter.convertStringToBooleanEquivalent(getCMPIsDeleted());
	}

	// ========================
	// Setters
	// ========================
	public abstract void setCMPContractID(Long contractID);

	public abstract void setCMPLimitProfileID(Long limitProfileID);

	public abstract void setCMPLimitID(Long limitID);

	public abstract void setCMPFinancePercent(Float financePercent);

	public abstract void setCMPContractValue(Double contractValue);

	public abstract void setCMPContractCurrency(String contractCurrency);

	public abstract void setCMPActualFinanceValue(Double actualFinanceValue);

	public abstract void setCMPActualFinanceCurrency(String actualFinanceCurrency);

	public abstract void setCMPProjectedProfitValue(Double projectedProfitValue);

	public abstract void setCMPProjectedProfitCurrency(String projectProfitCurrency);

	public abstract void setCMPBuildUpFDR(Float buildUpFDR);

	public abstract void setCMPIsDeleted(String isDeleted);

	public void setContractID(long contractID) {
		setCMPContractID(new Long(contractID));
	}

	public void setLimitProfileID(long limitProfileID) {
		setCMPLimitProfileID(new Long(limitProfileID));
	}

	public void setLimitID(long limitID) {
		setCMPLimitID(new Long(limitID));
	}

	public void setFinancePercent(float financePercent) {
		setCMPFinancePercent(new Float(financePercent));
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

	public void setActualFinanceAmount(Amount actualFinanceAmount) {
		if (actualFinanceAmount != null) {
			setCMPActualFinanceValue(new Double(actualFinanceAmount.getAmount()));
			setCMPActualFinanceCurrency(actualFinanceAmount.getCurrencyCode());
		}
		else {
			setCMPActualFinanceValue(null);
			setCMPActualFinanceCurrency(null);
		}
	}

	public void setProjectedProfit(Amount projectedProfit) {
		if (projectedProfit != null) {
			setCMPProjectedProfitValue(new Double(projectedProfit.getAmount()));
			setCMPProjectedProfitCurrency(projectedProfit.getCurrencyCode());
		}
		else {
			setCMPProjectedProfitValue(null);
			setCMPProjectedProfitCurrency(null);
		}
	}

	public void setBuildUpFDR(float buildUpFDR) {
		setCMPBuildUpFDR(new Float(buildUpFDR));
	}

	public void setIsDeleted(boolean isDeleted) {
		setCMPIsDeleted(TypeConverter.convertBooleanToStringEquivalent(isDeleted));
	}

	// ========================
	// CMR Methods
	// ========================
	public abstract Collection getCMRFacilityTypeItems();

	public abstract Collection getCMRAdvanceItems();

	public abstract Collection getCMRTNCItems();

	public abstract Collection getCMRFDRItems();

	public abstract void setCMRFacilityTypeItems(Collection itemList);

	public abstract void setCMRAdvanceItems(Collection itemList);

	public abstract void setCMRTNCItems(Collection itemList);

	public abstract void setCMRFDRItems(Collection itemList);

	// ==============================
	// CMR Getter Helper Methods
	// ==============================
	private IContractFacilityType[] retrieveContractFacilityTypeItems() throws ContractFinancingException {
		try {
			Collection collection = getCMRFacilityTypeItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBContractFacilityTypeLocal local = (EBContractFacilityTypeLocal) it.next();
				if (!local.getIsDeleted()) {
					IContractFacilityType obj = local.getValue();
					list.add(obj);
				}
			}
			return (IContractFacilityType[]) list.toArray(new IContractFacilityType[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing Contract Facility Type Items");
			throw new ContractFinancingException("Error in retrieveing Contract Facility Type Items", e);
		}
	}

	private IAdvance[] retrieveAdvanceItems(IContractFacilityType[] facilityTypeList) throws ContractFinancingException {
		try {
			Collection collection = getCMRAdvanceItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			// Used for setting the Facility Type MOA into the Advance object.
			HashMap facilityTypeMap = new HashMap();
			if ((facilityTypeList != null) && (facilityTypeList.length > 0)) {
				for (int i = 0; i < facilityTypeList.length; i++) {
					facilityTypeMap.put(new Long(facilityTypeList[i].getFacilityTypeID()), facilityTypeList[i]);
				}
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBAdvanceLocal local = (EBAdvanceLocal) it.next();
				if (!local.getIsDeleted()) {
					IAdvance obj = local.getValue();
					IContractFacilityType matchingFacilityType = (IContractFacilityType) facilityTypeMap.get(new Long(
							obj.getFacilityTypeID()));
					if (matchingFacilityType != null) {
						obj.setFacilityTypeMOA(matchingFacilityType.getMoa());
					}
					list.add(obj);
				}
			}
			return (IAdvance[]) list.toArray(new IAdvance[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing Advance Items");
			throw new ContractFinancingException("Error in retrieveing Advance Items", e);
		}
	}

	private ITNC[] retrieveTNCItems() throws ContractFinancingException {
		try {
			Collection collection = getCMRTNCItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBTNCLocal local = (EBTNCLocal) it.next();
				if (!local.getIsDeleted()) {
					ITNC obj = local.getValue();
					list.add(obj);
				}
			}
			return (ITNC[]) list.toArray(new ITNC[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing TNC Items");
			throw new ContractFinancingException("Error in retrieveing TNC Items", e);
		}
	}

	private IFDR[] retrieveFDRItems() throws ContractFinancingException {
		try {
			Collection collection = getCMRFDRItems();
			if ((collection == null) || (collection.size() == 0)) {
				return null;
			}

			ArrayList list = new ArrayList();
			Iterator it = collection.iterator();
			while (it.hasNext()) {
				EBFDRLocal local = (EBFDRLocal) it.next();
				if (!local.getIsDeleted()) {
					IFDR obj = local.getValue();
					list.add(obj);
				}
			}
			return (IFDR[]) list.toArray(new IFDR[0]);

		}
		catch (Exception e) {
			DefaultLogger.error(this, "Error in retrieveing FDR Items");
			throw new ContractFinancingException("Error in retrieveing FDR Items", e);
		}
	}

	// ==============================
	// CMR Setter Helper Methods
	// ==============================
	/**
	 * Update the list of facility type items under the current contract finance
	 * 
	 * @param itemList - List
	 * @throws ContractFinancingException on errors
	 */
	private void updateFacilityTypeItems(IContractFacilityType[] itemList) throws ContractFinancingException {
		try {
			Collection col = getCMRFacilityTypeItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteFacilityTypeItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createFacilityTypeItems(Arrays.asList(itemList));
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
					EBContractFacilityTypeLocal local = (EBContractFacilityTypeLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IContractFacilityType obj = (IContractFacilityType) itemMap.get(commonRef);
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

				deleteFacilityTypeItems(deleteList);
				createFacilityTypeItems(createList);
			}
		}
		catch (ContractFinancingException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in updateFacilityTypeItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of facility type items under the current contract finance
	 * 
	 * @param aDeletionList - List
	 * @throws ContractFinancingException on errors
	 */
	private void deleteFacilityTypeItems(List aDeletionList) throws ContractFinancingException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBContractFacilityTypeLocal local = (EBContractFacilityTypeLocal) iter.next();
				local.setIsDeleted(true);
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in deleteFacilityTypeItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of facility type items under the current contract finance
	 * 
	 * @throws ContractFinancingException on errors
	 */
	private void createFacilityTypeItems(List aCreationList) throws ContractFinancingException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRFacilityTypeItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBContractFacilityTypeLocalHome home = getEBContractFacilityTypeLocalHome();
			while (iter.hasNext()) {
				IContractFacilityType obj = (IContractFacilityType) iter.next();
				EBContractFacilityTypeLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in createFacilityTypeItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Update facilityTypeID before update advanceItems
	 * 
	 * @param advanceList; facilityTypeList
	 * @throws ContractFinancingException on errors
	 */
	private IAdvance[] updateFacilityType(IAdvance[] advanceList, IContractFacilityType[] facilityTypeList)
			throws ContractFinancingException {
		try {
			DefaultLogger.debug(this, "in updateFacilityType");
			for (int i = 0; i < advanceList.length; i++) {
				for (int j = 0; j < facilityTypeList.length; j++) {

					if (advanceList[i].getFacilityTypeID() == facilityTypeList[j].getFacilityTypeID()) {
						advanceList[i].setFacilityType(facilityTypeList[j].getFacilityType());
					}
				}
			}
			DefaultLogger.debug(this, "finish updateFacilityType");
			return advanceList;

		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in updateAdvanceItems: " + ex.toString());
		}
	}

	/**
	 * Update facilityTypeID before update advanceItems
	 * 
	 * @param advanceList; facilityTypeList
	 * @throws ContractFinancingException on errors
	 */
	private IAdvance[] updateFacilityTypeID(IAdvance[] advanceList, IContractFacilityType[] facilityTypeList)
			throws ContractFinancingException {
		try {
			DefaultLogger.debug(this, "in updateFacilityTypeID");

			for (int i = 0; i < advanceList.length; i++) {
				for (int j = 0; j < facilityTypeList.length; j++) {

					if (advanceList[i].getFacilityType().equals(facilityTypeList[j].getFacilityType())) {
						advanceList[i].setFacilityTypeID(facilityTypeList[j].getFacilityTypeID());
					}
				}
			}
			DefaultLogger.debug(this, "finish updateFacilityTypeID");
			return advanceList;

		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in updateAdvanceItems: " + ex.toString());
		}
	}

	/**
	 * Update the list of advance items under the current contract finance
	 * 
	 * @param itemList - List
	 * @throws ContractFinancingException on errors
	 */
	private void updateAdvanceItems(IAdvance[] itemList) throws ContractFinancingException {
		try {
			Collection col = getCMRAdvanceItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteAdvanceItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createAdvanceItems(Arrays.asList(itemList));
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
					EBAdvanceLocal local = (EBAdvanceLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					IAdvance obj = (IAdvance) itemMap.get(commonRef);
					if (obj == null) {
						deleteList.add(local);
					}
					else {
						DefaultLogger
								.debug(this,
										">>>>>>>>####### In EBContractFinancingBean.updateAdvanceItems() -> calling setValue for Advance Bean");
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

				deleteAdvanceItems(deleteList);
				createAdvanceItems(createList);
			}
		}
		catch (ContractFinancingException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in updateAdvanceItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of advance items under the current contract finance
	 * 
	 * @param aDeletionList - List
	 * @throws ContractFinancingException on errors
	 */
	private void deleteAdvanceItems(List aDeletionList) throws ContractFinancingException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBAdvanceLocal local = (EBAdvanceLocal) iter.next();
				local.setIsDeleted(true);
				local.synchronizePaymentItemList(local.getValue());
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in deleteAdvanceItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of advance items under the current contract finance
	 * 
	 * @throws ContractFinancingException on errors
	 */
	private void createAdvanceItems(List aCreationList) throws ContractFinancingException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRAdvanceItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBAdvanceLocalHome home = getEBAdvanceLocalHome();
			while (iter.hasNext()) {
				IAdvance obj = (IAdvance) iter.next();

				EBAdvanceLocal local = home.create(obj);
				local.synchronizePaymentItemList(obj);

				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in createAdvanceItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Update the list of terms and conditions items under the current contract
	 * finance
	 * 
	 * @param itemList - List
	 * @throws ContractFinancingException on errors
	 */
	private void updateTNCItems(ITNC[] itemList) throws ContractFinancingException {
		try {
			Collection col = getCMRTNCItems();
			if (itemList == null) {
				if ((col == null) || (col.size() == 0)) {
					return; // nothing to do
				}
				else { // delete all records
					deleteTNCItems(new ArrayList(col));
				}
			}
			else if ((col == null) || (col.size() == 0)) { // create new records
				createTNCItems(Arrays.asList(itemList));
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
					EBTNCLocal local = (EBTNCLocal) iter.next();
					Long commonRef = new Long(local.getCommonRef());
					cmrMap.put(commonRef, local);

					ITNC obj = (ITNC) itemMap.get(commonRef);
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

				deleteTNCItems(deleteList);
				createTNCItems(createList);
			}
		}
		catch (ContractFinancingException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in updateTNCItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of terms and conditions items under the current contract
	 * finance
	 * 
	 * @param aDeletionList - List
	 * @throws ContractFinancingException on errors
	 */
	private void deleteTNCItems(List aDeletionList) throws ContractFinancingException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBTNCLocal local = (EBTNCLocal) iter.next();
				local.setIsDeleted(true);
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in deleteTNCItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of terms and conditions items under the current contract
	 * finance
	 * 
	 * @throws ContractFinancingException on errors
	 */
	private void createTNCItems(List aCreationList) throws ContractFinancingException {
		if ((aCreationList == null) || (aCreationList.size() == 0)) {
			return; // do nothing
		}
		Collection col = getCMRTNCItems();
		Iterator iter = aCreationList.iterator();
		try {
			EBTNCLocalHome home = getEBTNCLocalHome();
			while (iter.hasNext()) {
				ITNC obj = (ITNC) iter.next();
				EBTNCLocal local = home.create(obj);
				col.add(local);
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in createTNCItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Update the list of FDR items under the current contract finance
	 * 
	 * @param itemList - List
	 * @throws ContractFinancingException on errors
	 */
	private void updateFDRItems(IFDR[] itemList) throws ContractFinancingException {
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
		catch (ContractFinancingException ex) {
			throw ex;
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in updateFDRItems: " + ex.toString());
		}
	}

	/**
	 * Delete the list of FDR items under the current contract finance
	 * 
	 * @param aDeletionList - List
	 * @throws ContractFinancingException on errors
	 */
	private void deleteFDRItems(List aDeletionList) throws ContractFinancingException {
		if ((aDeletionList == null) || (aDeletionList.size() == 0)) {
			return; // do nothing
		}
		try {
			Iterator iter = aDeletionList.iterator();
			while (iter.hasNext()) {
				EBFDRLocal local = (EBFDRLocal) iter.next();
				local.setIsDeleted(true);
			}
		}
		catch (Exception ex) {
			throw new ContractFinancingException("Exception in deleteFDRItems: " + ex.toString(), ex);
		}
	}

	/**
	 * Create the list of FDR items under the current contract finance
	 * 
	 * @throws ContractFinancingException on errors
	 */
	private void createFDRItems(List aCreationList) throws ContractFinancingException {
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
			throw new ContractFinancingException("Exception in createFDRItems: " + ex.toString(), ex);
		}
	}

	// ==============================
	// Standard Methods
	// ==============================
	/**
	 * Matching method of the create(...) method of the bean's home interface.
	 * The container invokes an ejbCreate method to create entity bean instance.
	 * 
	 * @param value IContractFinancing object
	 * @throws javax.ejb.CreateException on error creating the entity object
	 */
	public Long ejbCreate(IContractFinancing value) throws CreateException {
		try {
			long pk = Long.parseLong((new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_CONTRACT_FINANCE, true));
			AccessorUtil.copyValue(value, this, EXCLUDE_METHOD);
			setCMPContractID(new Long(pk));
			this.setVersionTime(VersionGenerator.getVersionNumber());

			return null;
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
	public void ejbPostCreate(IContractFinancing value) throws CreateException {
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
	 * Get the list of contract finance summary info.
	 * 
	 * @param aLimitProfileID of long type
	 * @return IContractFinancingSummary[] - the list of contract finance
	 *         summary
	 * @throws com.integrosys.base.businfra.search.SearchDAOException on errors
	 */
	public IContractFinancingSummary[] ejbHomeGetContractFinancingSummaryList(long aLimitProfileID)
			throws SearchDAOException {
		DefaultLogger.debug(this, "in ejbHomeGetContractFinancingSummaryList");
		return ContractFinancingDAOFactory.getContractFinancingDAO().getContractFinancingSummaryList(aLimitProfileID);
	}

	public void populateLimitDetails(IContractFinancing value) throws SearchDAOException {
		HashMap map = ContractFinancingDAOFactory.getContractFinancingDAO().getLimitDetailsByLimitID(getLimitID());
		value.setProductDescription((String) map.get("productDescription"));
		value.setSourceLimit((String) map.get("sourceLimit"));
	}

	// ==============================
	// Locating EB Methods
	// ==============================
	/**
	 * Method to get EB Local Home for the Facility Type EB
	 */
	protected EBContractFacilityTypeLocalHome getEBContractFacilityTypeLocalHome() throws ContractFinancingException {
		EBContractFacilityTypeLocalHome home = (EBContractFacilityTypeLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CF_FACILITY_TYPE_LOCAL_JNDI, EBContractFacilityTypeLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBContractFacilityTypeLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the Advance EB
	 */
	protected EBAdvanceLocalHome getEBAdvanceLocalHome() throws ContractFinancingException {
		EBAdvanceLocalHome home = (EBAdvanceLocalHome) BeanController.getEJBLocalHome(
				ICMSJNDIConstant.EB_CF_ADVANCE_LOCAL_JNDI, EBAdvanceLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBAdvanceLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the TNC EB
	 */
	protected EBTNCLocalHome getEBTNCLocalHome() throws ContractFinancingException {
		EBTNCLocalHome home = (EBTNCLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_CF_TNC_LOCAL_JNDI,
				EBTNCLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBTNCLocalHome is null!");
	}

	/**
	 * Method to get EB Local Home for the FDR EB
	 */
	protected EBFDRLocalHome getEBFDRLocalHome() throws ContractFinancingException {
		EBFDRLocalHome home = (EBFDRLocalHome) BeanController.getEJBLocalHome(ICMSJNDIConstant.EB_CF_FDR_LOCAL_JNDI,
				EBFDRLocalHome.class.getName());

		if (home != null) {
			return home;
		}

		throw new ContractFinancingException("EBFDRLocalHome is null!");
	}

}
