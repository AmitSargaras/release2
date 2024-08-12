/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/AbstractCollateralParameterTrxOperation.java,v 1.11 2003/08/15 10:16:06 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.ICollateralParameter;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of security
 * parameter trx operations.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2003/08/15 10:16:06 $ Tag: $Name: $
 */
public abstract class AbstractCollateralParameterTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	 * parameter specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return collateral parameter specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         ICollateralParameterTrxValue
	 */
	protected ICollateralParameterTrxValue getCollateralParameterTrxValue(ITrxValue trxValue)
			throws TrxOperationException {
		try {
			return (ICollateralParameterTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type ICollateralParameterTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging collateral parameter records.
	 * 
	 * @param value is of type ICollateralParameterTrxValue
	 * @return collateral parameter transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralParameterTrxValue createStagingCollateralParameters(ICollateralParameterTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralParameter[] colParams = value.getStagingCollateralParameters();

			for (int i = 0; i < colParams.length; i++) {
				colParams[i].setStatus(value.getToState());
			}

			colParams = getStagingCollateralBusManager().createCollateralParameters(colParams);
			value.setStagingCollateralParameters(colParams);

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
	 * Create actual collateral parameter records.
	 * 
	 * @param value is of type ICollateralParameterTrxValue
	 * @return collateral parameter transaction value
	 * @throws TrxOperationException on errors creating the collateral parameter
	 */
	protected ICollateralParameterTrxValue createActualCollateralParameters(ICollateralParameterTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralParameter[] colParams = value.getStagingCollateralParameters();

			for (int i = 0; i < colParams.length; i++) {
				colParams[i].setStatus(value.getToState());
			}

			colParams = getActualCollateralBusManager().createCollateralParameters(colParams);
			value.setCollateralParameters(colParams); // set into actual
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
	 * Updates a list of actual collateral parameters using a list of staging
	 * collateral parameters.
	 * 
	 * @param value of type ICollateralParameterTrxValue
	 * @return updated collateral parameter transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 *         parameters
	 */
	protected ICollateralParameterTrxValue updateActualCollateralParameters(ICollateralParameterTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralParameter[] actual = value.getCollateralParameters();
			ICollateralParameter[] staging = value.getStagingCollateralParameters();

			long stageGroupID = ICMSConstant.LONG_MIN_VALUE;

			for (int i = 0; i < actual.length; i++) {
				staging[i].setId(actual[i].getId()); // maintain actual's pk
				stageGroupID = staging[i].getGroupId();
				staging[i].setGroupId(actual[i].getGroupId()); // maintain
				// actual group
				// id
				staging[i].setVersionTime(actual[i].getVersionTime());
				staging[i].setStatus(value.getToState());
			}

			actual = getActualCollateralBusManager().updateCollateralParameters(staging);
			value.setCollateralParameters(actual); // set into actual

			// set back the stage group id for transaction reference
			staging[0].setGroupId(stageGroupID);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("CollateralException caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICollateralParameterTrxValue
	 * @return collateral parameter transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralParameterTrxValue createTransaction(ICollateralParameterTrxValue value)
			throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);

			OBCollateralParameterTrxValue newValue = new OBCollateralParameterTrxValue(tempValue);
			newValue.setCollateralParameters(value.getCollateralParameters());
			newValue.setStagingCollateralParameters(value.getStagingCollateralParameters());
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
	 * @param value is of type ICollateralParameterTrxValue
	 * @return collateral parameter transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICollateralParameterTrxValue updateTransaction(ICollateralParameterTrxValue value)
			throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);

			OBCollateralParameterTrxValue newValue = new OBCollateralParameterTrxValue(tempValue);
			newValue.setCollateralParameters(value.getCollateralParameters());
			newValue.setStagingCollateralParameters(value.getStagingCollateralParameters());
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
	 * @param value is of type ICollateralParameterTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(ICollateralParameterTrxValue value) {
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
	private ICollateralParameterTrxValue prepareTrxValue(ICollateralParameterTrxValue value) {
		if (value != null) {
			ICollateralParameter[] actual = value.getCollateralParameters();
			ICollateralParameter[] staging = value.getStagingCollateralParameters();

			value.setReferenceID((actual != null) && (actual.length != 0) ? String.valueOf(actual[0].getGroupId())
					: null);
			value.setStagingReferenceID((staging != null) && (staging.length != 0) ? String.valueOf(staging[0]
					.getGroupId()) : null);
		}
		return value;
	}
}