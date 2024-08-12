/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.marketfactor;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.type.property.marketfactor.IMFChecklist;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of MF
 * Checklist trx operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractMFChecklistTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

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
	 * Helper method to cast a generic trx value object to a MF Checklist
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return MF Checklist specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         IMFChecklistTrxValue
	 */
	protected IMFChecklistTrxValue getMFChecklistTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (IMFChecklistTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type IMFChecklistTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging MF Checklist records.
	 * 
	 * @param value is of type IMFChecklistTrxValue
	 * @return MF Checklist transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IMFChecklistTrxValue createStagingMFChecklist(IMFChecklistTrxValue value) throws TrxOperationException {
		try {
			IMFChecklist checklistValue = value.getStagingMFChecklist();

			checklistValue = getStagingCollateralBusManager().createMFChecklist(checklistValue);
			value.setStagingMFChecklist(checklistValue);
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
	 * Create actual MF Checklist records.
	 * 
	 * @param value is of type IMFChecklistTrxValue
	 * @return MF Checklist transaction value
	 * @throws TrxOperationException on errors creating the MF Checklist
	 */
	protected IMFChecklistTrxValue createActualMFChecklist(IMFChecklistTrxValue value) throws TrxOperationException {
		try {
			IMFChecklist checklistValue = value.getStagingMFChecklist();

			checklistValue = getActualCollateralBusManager().createMFChecklist(checklistValue);

			value.setMFChecklist(checklistValue); // set into actual
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
	 * Update actual MF Checklist.
	 * 
	 * @param value is of type IMFChecklistTrxValue
	 * @return MF Checklist transaction value
	 * @throws TrxOperationException on errors updating the actual MF Checklist
	 */
	protected IMFChecklistTrxValue updateActualMFChecklist(IMFChecklistTrxValue value) throws TrxOperationException {
		try {
			IMFChecklist actual = value.getMFChecklist();
			IMFChecklist staging = value.getStagingMFChecklist(); // update from
			// staging

			long id = staging.getMFChecklistID();
			long stageVersion = staging.getVersionTime();
			String stageStatus = staging.getStatus();

			staging.setMFChecklistID(actual.getMFChecklistID());
			staging.setVersionTime(actual.getVersionTime());
			staging.setStatus(value.getToState());

			actual = getActualCollateralBusManager().updateMFChecklist(staging);

			value.setMFChecklist(actual); // set into actual

			value.getStagingMFChecklist().setMFChecklistID(id);
			value.getStagingMFChecklist().setVersionTime(stageVersion);
			value.getStagingMFChecklist().setStatus(stageStatus);

			return value;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("MFChecklistException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IMFChecklistTrxValue
	 * @return MF Checklist transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IMFChecklistTrxValue createTransaction(IMFChecklistTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBMFChecklistTrxValue newValue = new OBMFChecklistTrxValue(tempValue);
			newValue.setMFChecklist(value.getMFChecklist());
			newValue.setStagingMFChecklist(value.getStagingMFChecklist());
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
	 * @param value is of type IMFChecklistTrxValue
	 * @return MF Checklist transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IMFChecklistTrxValue updateTransaction(IMFChecklistTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBMFChecklistTrxValue newValue = new OBMFChecklistTrxValue(tempValue);
			newValue.setMFChecklist(value.getMFChecklist());
			newValue.setStagingMFChecklist(value.getStagingMFChecklist());
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
	 * @param value is of type IMFChecklistTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(IMFChecklistTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type IMFChecklistTrxValue
	 * @return MF Checklist transaction value
	 */
	protected IMFChecklistTrxValue prepareTrxValue(IMFChecklistTrxValue value) {
		if (value != null) {
			IMFChecklist actual = value.getMFChecklist();
			IMFChecklist staging = value.getStagingMFChecklist();

			value.setReferenceID(actual != null ? String.valueOf(actual.getMFChecklistID()) : null);
			value.setStagingReferenceID(staging != null ? String.valueOf(staging.getMFChecklistID()) : null);
		}
		return value;
	}

}
