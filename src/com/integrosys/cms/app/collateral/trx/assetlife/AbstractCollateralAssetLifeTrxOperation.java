/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.assetlife;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralAssetLife;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of security
 * asset life trx operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractCollateralAssetLifeTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	 * Helper method to cast a generic trx value object to a collateral
	 * assetlife specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return collateral assetlife specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         ICollateralAssetLifeTrxValue
	 */
	protected ICollateralAssetLifeTrxValue getCollateralAssetLifeTrxValue(ITrxValue trxValue)
			throws TrxOperationException {
		try {
			return (ICollateralAssetLifeTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type ICollateralAssetLifeTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging collateral assetlife records.
	 * 
	 * @param value is of type ICollateralAssetLifeTrxValue
	 * @return collateral assetlife transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralAssetLifeTrxValue createStagingCollateralAssetLifes(ICollateralAssetLifeTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralAssetLife[] assetLifes = value.getStagingCollateralAssetLifes();

			for (int i = 0; i < assetLifes.length; i++) {
				assetLifes[i].setStatus(value.getToState());
			}

			assetLifes = getStagingCollateralBusManager().createCollateralAssetLifes(assetLifes);
			value.setStagingCollateralAssetLifes(assetLifes);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("CollateralException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Create actual collateral assetlife records.
	 * 
	 * @param value is of type ICollateralAssetLifeTrxValue
	 * @return collateral assetlife transaction value
	 * @throws TrxOperationException on errors creating the collateral assetlife
	 */
	protected ICollateralAssetLifeTrxValue createActualCollateralAssetLifes(ICollateralAssetLifeTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralAssetLife[] assetLifes = value.getStagingCollateralAssetLifes();

			for (int i = 0; i < assetLifes.length; i++) {
				assetLifes[i].setStatus(value.getToState());
			}

			assetLifes = getActualCollateralBusManager().createCollateralAssetLifes(assetLifes);
			value.setCollateralAssetLifes(assetLifes); // set into actual
			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("CollateralException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Updates a list of actual collateral assetlifes using a list of staging
	 * collateral assetlifes.
	 * 
	 * @param value of type ICollateralAssetLifeTrxValue
	 * @return updated collateral assetlife transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 *         assetlifes
	 */
	protected ICollateralAssetLifeTrxValue updateActualCollateralAssetLifes(ICollateralAssetLifeTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralAssetLife[] actual = value.getCollateralAssetLifes();
			ICollateralAssetLife[] staging = value.getStagingCollateralAssetLifes();

			for (int i = 0; i < actual.length; i++) {
				staging[i].setVersionTime(actual[i].getVersionTime());
				staging[i].setStatus(value.getToState());
			}

			actual = getActualCollateralBusManager().updateCollateralAssetLifes(staging);
			value.setCollateralAssetLifes(actual); // set into actual

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("CollateralException caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICollateralAssetLifeTrxValue
	 * @return collateral assetlife transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralAssetLifeTrxValue createTransaction(ICollateralAssetLifeTrxValue value)
			throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBCollateralAssetLifeTrxValue newValue = new OBCollateralAssetLifeTrxValue(tempValue);
			newValue.setCollateralAssetLifes(value.getCollateralAssetLifes());
			newValue.setStagingCollateralAssetLifes(value.getStagingCollateralAssetLifes());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to update a transaction record.
	 * 
	 * @param value is of type ICollateralAssetLifeTrxValue
	 * @return collateral assetlife transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICollateralAssetLifeTrxValue updateTransaction(ICollateralAssetLifeTrxValue value)
			throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBCollateralAssetLifeTrxValue newValue = new OBCollateralAssetLifeTrxValue(tempValue);
			newValue.setCollateralAssetLifes(value.getCollateralAssetLifes());
			newValue.setStagingCollateralAssetLifes(value.getStagingCollateralAssetLifes());
			return newValue;
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Prepares a result object to be returned.
	 * 
	 * @param value is of type ICollateralAssetLifeTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(ICollateralAssetLifeTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type ICollateralParametersTrxValue
	 * @return collateral parameter transaction value
	 */
	private ICollateralAssetLifeTrxValue prepareTrxValue(ICollateralAssetLifeTrxValue value) {
		if (value != null) {
			ICollateralAssetLife[] actual = value.getCollateralAssetLifes();
			ICollateralAssetLife[] staging = value.getStagingCollateralAssetLifes();

			value.setReferenceID((actual != null) && (actual.length != 0) ? String.valueOf(actual[0].getGroupID())
					: null);
			value.setStagingReferenceID((staging != null) && (staging.length != 0) ? String.valueOf(staging[0]
					.getGroupID()) : null);
		}
		return value;
	}
}
