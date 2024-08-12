/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/AbstractCollateralSubTypeTrxOperation.java,v 1.10 2003/08/15 06:01:15 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.ICollateralSubType;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of security
 * sub type trx operations.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2003/08/15 06:01:15 $ Tag: $Name: $
 */
public abstract class AbstractCollateralSubTypeTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	 * Helper method to cast a generic trx value object to a collateral subtype
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return collateral subtype specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         ICollateralSubTypeTrxValue
	 */
	protected ICollateralSubTypeTrxValue getCollateralSubTypeTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (ICollateralSubTypeTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type ICollateralSubTypeTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging collateral subtype records.
	 * 
	 * @param value is of type ICollateralSubTypeTrxValue
	 * @return collateral subtype transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralSubTypeTrxValue createStagingCollateralSubTypes(ICollateralSubTypeTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralSubType[] subTypes = value.getStagingCollateralSubTypes();

			for (int i = 0; i < subTypes.length; i++) {
				subTypes[i].setStatus(value.getToState());
			}

			subTypes = getStagingCollateralBusManager().createCollateralSubTypes(subTypes);
			value.setStagingCollateralSubTypes(subTypes);

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
	 * Create actual collateral subtype records.
	 * 
	 * @param value is of type ICollateralSubTypeTrxValue
	 * @return collateral subtype transaction value
	 * @throws TrxOperationException on errors creating the collateral subtype
	 */
	protected ICollateralSubTypeTrxValue createActualCollateralSubTypes(ICollateralSubTypeTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralSubType[] subTypes = value.getStagingCollateralSubTypes();

			for (int i = 0; i < subTypes.length; i++) {
				subTypes[i].setStatus(value.getToState());
			}

			subTypes = getActualCollateralBusManager().createCollateralSubTypes(subTypes);
			value.setCollateralSubTypes(subTypes); // set into actual
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
	 * Updates a list of actual collateral subtypes using a list of staging
	 * collateral subtypes.
	 * 
	 * @param value of type ICollateralSubTypeTrxValue
	 * @return updated collateral subtype transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 *         subtypes
	 */
	protected ICollateralSubTypeTrxValue updateActualCollateralSubTypes(ICollateralSubTypeTrxValue value)
			throws TrxOperationException {
		try {
			ICollateralSubType[] actual = value.getCollateralSubTypes();
			ICollateralSubType[] staging = value.getStagingCollateralSubTypes();

			for (int i = 0; i < actual.length; i++) {
				staging[i].setVersionTime(actual[i].getVersionTime());
				staging[i].setStatus(value.getToState());
			}

			actual = getActualCollateralBusManager().updateCollateralSubTypes(staging);
			value.setCollateralSubTypes(actual);
			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("CollateralException caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type ICollateralSubTypeTrxValue
	 * @return collateral subtype transaction value
	 * @throws TrxOperationException on errors
	 */
	protected ICollateralSubTypeTrxValue createTransaction(ICollateralSubTypeTrxValue value)
			throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBCollateralSubTypeTrxValue newValue = new OBCollateralSubTypeTrxValue(tempValue);
			newValue.setCollateralSubTypes(value.getCollateralSubTypes());
			newValue.setStagingCollateralSubTypes(value.getStagingCollateralSubTypes());
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
	 * @param value is of type ICollateralSubTypeTrxValue
	 * @return collateral subtype transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected ICollateralSubTypeTrxValue updateTransaction(ICollateralSubTypeTrxValue value)
			throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBCollateralSubTypeTrxValue newValue = new OBCollateralSubTypeTrxValue(tempValue);
			newValue.setCollateralSubTypes(value.getCollateralSubTypes());
			newValue.setStagingCollateralSubTypes(value.getStagingCollateralSubTypes());
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
	 * @param value is of type ICollateralSubTypeTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(ICollateralSubTypeTrxValue value) {
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
	private ICollateralSubTypeTrxValue prepareTrxValue(ICollateralSubTypeTrxValue value) {
		if (value != null) {
			ICollateralSubType[] actual = value.getCollateralSubTypes();
			ICollateralSubType[] staging = value.getStagingCollateralSubTypes();

			value.setReferenceID((actual != null) && (actual.length != 0) ? String.valueOf(actual[0].getGroupID())
					: null);
			value.setStagingReferenceID((staging != null) && (staging.length != 0) ? String.valueOf(staging[0]
					.getGroupID()) : null);
		}
		return value;
	}
}
