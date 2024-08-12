package com.integrosys.cms.app.collateral.trx.assetlife;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralAssetLife;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * Super class to be implement by sub classes for reading transaction value of
 * collateral asset life and to retrieve domain objects from actual and staging.
 * 
 * @author Chong Jun Yong
 * @see #doInTrxManager(ICMSTrxValue)
 */
public abstract class AbstractReadCollateralAssetLifeOperation extends CMSTrxOperation implements ITrxReadOperation {

	private ICollateralBusManager actualCollateralBusManager;

	private ICollateralBusManager stagingCollateralBusManager;

	/**
	 * @return the actualCollateralBusManager
	 */
	public ICollateralBusManager getActualCollateralBusManager() {
		return actualCollateralBusManager;
	}

	/**
	 * @return the stagingCollateralBusManager
	 */
	public ICollateralBusManager getStagingCollateralBusManager() {
		return stagingCollateralBusManager;
	}

	/**
	 * @param actualCollateralBusManager the actualCollateralBusManager to set
	 */
	public void setActualCollateralBusManager(ICollateralBusManager actualCollateralBusManager) {
		this.actualCollateralBusManager = actualCollateralBusManager;
	}

	/**
	 * @param stagingCollateralBusManager the stagingCollateralBusManager to set
	 */
	public void setStagingCollateralBusManager(ICollateralBusManager stagingCollateralBusManager) {
		this.stagingCollateralBusManager = stagingCollateralBusManager;
	}

	/**
	 * Sub class to implement this method to do the actual process when
	 * interface with workflow manager, ie. trx manager.
	 * 
	 * @param trxValue collateral asset life trx value object, contain certain
	 *        criteria to be used by subclasses
	 * @return full trx value object will reference id, staging reference id,
	 *         others information retrieved.
	 * @throws TransactionException if there is any error occur when interface
	 *         with trx manager.
	 */
	public abstract ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException;

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val transaction value required for retrieving transaction record
	 * @return transaction value
	 * @throws TransactionException on errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {

		ICMSTrxValue cmsTrxValue = (ICMSTrxValue) super.getCMSTrxValue(val);

		cmsTrxValue = doInTrxManager(cmsTrxValue);

		OBCollateralAssetLifeTrxValue assetLifeTrx = new OBCollateralAssetLifeTrxValue(cmsTrxValue);

		String stagingRef = cmsTrxValue.getStagingReferenceID();
		String actualRef = cmsTrxValue.getReferenceID();

		DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

		if (actualRef != null) {
			try {
				ICollateralAssetLife[] assetLifes = getActualCollateralBusManager().getCollateralAssetLifeByGroupID(
						Long.parseLong(actualRef));
				assetLifeTrx.setCollateralAssetLifes(assetLifes);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException("failed to retrieve collateral asset life, using reference id ["
						+ actualRef + "]", ex);
			}
		}

		if (stagingRef != null) {
			try {
				ICollateralAssetLife[] assetLifes = getStagingCollateralBusManager().getCollateralAssetLifeByGroupID(
						Long.parseLong(stagingRef));
				assetLifeTrx.setStagingCollateralAssetLifes(assetLifes);
			}
			catch (CollateralException ex) {
				throw new TrxOperationException(
						"failed to retrieve collateral asset life, using staging reference id [" + stagingRef + "]", ex);
			}
		}

		return assetLifeTrx;

	}
}
