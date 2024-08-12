package com.integrosys.cms.app.limit.trx;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.limit.bus.IFacilityBusManager;
import com.integrosys.cms.app.limit.bus.IFacilityMaster;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * <p>
 * Abstract operation to be implemented by Sub class to interface with workflow
 * process
 * 
 * <p>
 * Basically provided access methods such as get bus manager to deal with domain
 * objects.
 * 
 * @author Chong Jun Yong
 * @since 03.09.2008
 * @see #createActualFacilityMaster(IFacilityTrxValue)
 * @see #createStagingFacilityMaster(IFacilityTrxValue)
 * @see #createTransaction(IFacilityTrxValue)
 * @see #updateActualFacilityMasterFromStaging(IFacilityTrxValue)
 * @see #updateTransaction(IFacilityTrxValue)
 */
public abstract class AbstractFacilityTrxOperation extends CMSTrxOperation {

	private static final long serialVersionUID = 4839622134828569650L;

	/** logger available for sub classes */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	private IFacilityBusManager actualFacilityBusManager;

	private IFacilityBusManager stagingFacilityBusManager;

	/**
	 * @return the actualFacilityBusManager
	 */
	public IFacilityBusManager getActualFacilityBusManager() {
		return actualFacilityBusManager;
	}

	/**
	 * @return the stagingFacilityBusManager
	 */
	public IFacilityBusManager getStagingFacilityBusManager() {
		return stagingFacilityBusManager;
	}

	/**
	 * @param actualFacilityBusManager the actualFacilityBusManager to set
	 */
	public void setActualFacilityBusManager(IFacilityBusManager actualFacilityBusManager) {
		this.actualFacilityBusManager = actualFacilityBusManager;
	}

	/**
	 * @param stagingFacilityBusManager the stagingFacilityBusManager to set
	 */
	public void setStagingFacilityBusManager(IFacilityBusManager stagingFacilityBusManager) {
		this.stagingFacilityBusManager = stagingFacilityBusManager;
	}

	public abstract ITrxResult performProcess(ITrxValue trxValue) throws TrxOperationException;

	/**
	 * First time create actual facility master using the trx value's facility
	 * master provided.
	 * 
	 * @param trxValue the transaction value having facility master to be
	 *        created
	 * @return trx value having set the reference id and created facility
	 *         master.
	 */
	protected IFacilityTrxValue createActualFacilityMaster(IFacilityTrxValue trxValue) {
		Validate.notNull(trxValue, "'trxValue' must not be null.");
		Validate.notNull(trxValue.getFacilityMaster(),
				"trxValue actual facility master to be created must not be null.");

		IFacilityMaster createdFacilityMaster = getActualFacilityBusManager().createFacilityMaster(
				trxValue.getFacilityMaster());
		trxValue.setFacilityMaster(createdFacilityMaster);
		trxValue.setReferenceID(String.valueOf(createdFacilityMaster.getId()));

		return trxValue;
	}

	/**
	 * Create staging facility master using the trx value's staging facility
	 * master provided.
	 * 
	 * @param trxValue the transaction value having staging facility master to
	 *        be created
	 * @return trx value having set the staging reference id and created staging
	 *         facility master.
	 */
	protected IFacilityTrxValue createStagingFacilityMaster(IFacilityTrxValue trxValue) {
		Validate.notNull(trxValue, "'trxValue' must not be null.");
		Validate.notNull(trxValue.getStagingFacilityMaster(),
				"trxValue staging facility master to be created must not be null.");

		IFacilityMaster createdFacilityMaster = getStagingFacilityBusManager().createFacilityMaster(
				trxValue.getStagingFacilityMaster());
		trxValue.setStagingFacilityMaster(createdFacilityMaster);
		trxValue.setStagingReferenceID(String.valueOf(createdFacilityMaster.getId()));

		return trxValue;
	}

	/**
	 * Update facility master using staging facility master provided in the trx
	 * value supplied
	 * 
	 * @param trxValue transaction value having both actual facility master and
	 *        staging facility master
	 * @return trx value having the updated facility master
	 */
	protected IFacilityTrxValue updateActualFacilityMasterFromStaging(IFacilityTrxValue trxValue) {
		Validate.notNull(trxValue, "'trxValue' must not be null.");
		Validate.notNull(trxValue.getStagingFacilityMaster(),
				"trxValue staging facility master to be used to update actual must not be null.");

		IFacilityMaster updatedFacilityMaster = getActualFacilityBusManager().updateToWorkingCopy(
				trxValue.getFacilityMaster(), trxValue.getStagingFacilityMaster());
		trxValue.setFacilityMaster(updatedFacilityMaster);

		if (trxValue.getReferenceID() == null) {
			trxValue.setReferenceID(String.valueOf(updatedFacilityMaster.getId()));
		}

		return trxValue;
	}

	/**
	 * First time create transaction value using the trx value supplied
	 * 
	 * @param trxValue trx value object that is ready to be created.
	 * @return created trx value object having both facility master and staging
	 *         facility master set.F
	 * @throws TrxOperationException if there is any error when interface with
	 *         trx manager
	 */
	protected IFacilityTrxValue createTransaction(IFacilityTrxValue trxValue) throws TrxOperationException {
		Validate.notNull(trxValue, "'trxValue' must not be null.");

		ICMSTrxValue createdTrxValue = super.createTransaction(trxValue);

		OBFacilityTrxValue facTrxValue = new OBFacilityTrxValue();
		AccessorUtil.copyValue(createdTrxValue, facTrxValue);

		facTrxValue.setFacilityMaster(trxValue.getFacilityMaster());
		facTrxValue.setStagingFacilityMaster(trxValue.getStagingFacilityMaster());

		return facTrxValue;

	}

	/**
	 * Update existing transaction using transaction value supplied
	 * 
	 * @param trxValue trx value ready to be updated
	 * @return updated transaction value object having set both the facility
	 *         master and staging facility master
	 * @throws TrxOperationException if there is any error when interface with
	 *         trx manager
	 */
	protected IFacilityTrxValue updateTransaction(IFacilityTrxValue trxValue) throws TrxOperationException {
		Validate.notNull(trxValue, "'trxValue' must not be null.");
		Validate.notNull(trxValue.getTransactionID(), "trxValue transaction Id must not be null");

		ICMSTrxValue updatedTrxValue = super.updateTransaction(trxValue);

		OBFacilityTrxValue facTrxValue = new OBFacilityTrxValue();
		AccessorUtil.copyValue(updatedTrxValue, facTrxValue);

		facTrxValue.setFacilityMaster(trxValue.getFacilityMaster());
		facTrxValue.setStagingFacilityMaster(trxValue.getStagingFacilityMaster());

		return facTrxValue;
	}
}
