/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.propertyparameters.trx.marketfactor;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.propertyparameters.bus.PropertyParametersException;
import com.integrosys.cms.app.propertyparameters.bus.SBPropertyParameters;
import com.integrosys.cms.app.propertyparameters.bus.marketfactor.IMFTemplate;
import com.integrosys.cms.app.propertyparameters.trx.PropertyParametersHelper;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among the set of MF
 * Template trx operations.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public abstract class AbstractMFTemplateTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
	/**
	 * Helper method to cast a generic trx value object to a MF Template
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return MF Template specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         IMFTemplateTrxValue
	 */
	protected IMFTemplateTrxValue getMFTemplateTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (IMFTemplateTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type IMFTemplateTrxValue: " + e.toString());
		}
	}

	/**
	 * Create staging MF Template records.
	 * 
	 * @param value is of type IMFTemplateTrxValue
	 * @return MF Template transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IMFTemplateTrxValue createStagingMFTemplate(IMFTemplateTrxValue value) throws TrxOperationException {
		try {
			IMFTemplate templateValue = value.getStagingMFTemplate();

			SBPropertyParameters mgr = getStagingPropertyParameters();
			templateValue = mgr.createMFTemplate(templateValue);
			value.setStagingMFTemplate(templateValue);
			return value;
		}
		catch (PropertyParametersException e) {
			throw new TrxOperationException("PropertyParametersException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Create actual MF Template records.
	 * 
	 * @param value is of type IMFTemplateTrxValue
	 * @return MF Template transaction value
	 * @throws TrxOperationException on errors creating the MF Template
	 */
	protected IMFTemplateTrxValue createActualMFTemplate(IMFTemplateTrxValue value) throws TrxOperationException {
		try {
			IMFTemplate templateValue = value.getStagingMFTemplate(); // create
																		// get
																		// from
																		// staging

			SBPropertyParameters mgr = getActualPropertyParameters();
			templateValue = mgr.createMFTemplate(templateValue);

			value.setMFTemplate(templateValue); // set into actual
			return value;
		}
		catch (PropertyParametersException e) {
			throw new TrxOperationException("PropertyParametersException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Update actual MF Template.
	 * 
	 * @param value is of type IMFTemplateTrxValue
	 * @return MF Template transaction value
	 * @throws TrxOperationException on errors updating the actual collateral
	 */
	protected IMFTemplateTrxValue updateActualMFTemplate(IMFTemplateTrxValue value) throws TrxOperationException {
		try {
			IMFTemplate actual = value.getMFTemplate();
			IMFTemplate staging = value.getStagingMFTemplate(); // update from
																// staging

			long id = staging.getMFTemplateID();
			long stageVersion = staging.getVersionTime();
			String stageStatus = staging.getStatus();

			staging.setMFTemplateID(actual.getMFTemplateID()); // but maintain
																// actual's pk
			staging.setVersionTime(actual.getVersionTime()); // and actual's
																// version time
			staging.setStatus(value.getToState());

			SBPropertyParameters mgr = getActualPropertyParameters();

			actual = mgr.updateMFTemplate(staging);

			value.setMFTemplate(actual); // set into actual

			value.getStagingMFTemplate().setMFTemplateID(id);
			value.getStagingMFTemplate().setVersionTime(stageVersion);
			value.getStagingMFTemplate().setStatus(stageStatus);

			return value;
		}
		catch (PropertyParametersException e) {
			throw new TrxOperationException("MFTemplateException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to delete a MF Template record in staging.
	 * 
	 * @param value is of type IMFTemplateTrxValue
	 * @return MF Template transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IMFTemplateTrxValue deleteStagingMFTemplate(IMFTemplateTrxValue value) throws TrxOperationException {
		try {
			IMFTemplate templateValue = value.getStagingMFTemplate();

			SBPropertyParameters mgr = getStagingPropertyParameters();
			templateValue = mgr.deleteMFTemplate(templateValue);
			value.setStagingMFTemplate(templateValue);
			return value;
		}
		catch (PropertyParametersException e) {
			throw new TrxOperationException("PropertyParametersException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Method to delete a MF Template record in actual.
	 * 
	 * @param value is of type IMFTemplateTrxValue
	 * @return MF Template transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IMFTemplateTrxValue deleteActualMFTemplate(IMFTemplateTrxValue value) throws TrxOperationException {
		try {

			IMFTemplate templateValue = value.getMFTemplate();

			SBPropertyParameters mgr = getActualPropertyParameters();
			templateValue = mgr.deleteMFTemplate(templateValue);
			value.setMFTemplate(templateValue);
			return value;
		}
		catch (PropertyParametersException e) {
			throw new TrxOperationException("PropertyParametersException caught!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}

	/**
	 * Get the SB for the actual storage of property parameter
	 * 
	 * @return SBPropertyParameters
	 * @throws TrxOperationException on errors
	 */
	protected SBPropertyParameters getActualPropertyParameters() throws TrxOperationException {
		return PropertyParametersHelper.getInstance().getActualPropertyParameters();
	}

	/**
	 * Get the SB for the actual storage of property parameter
	 * 
	 * @return SBPropertyParameters
	 * @throws TrxOperationException on errors
	 */
	protected SBPropertyParameters getStagingPropertyParameters() throws TrxOperationException {
		return PropertyParametersHelper.getInstance().getStagingPropertyParameters();
	}

	/**
	 * Method to create a transaction record
	 * 
	 * @param value is of type IMFTemplateTrxValue
	 * @return MF Template transaction value
	 * @throws TrxOperationException on errors
	 */
	protected IMFTemplateTrxValue createTransaction(IMFTemplateTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBMFTemplateTrxValue newValue = new OBMFTemplateTrxValue(tempValue);
			newValue.setMFTemplate(value.getMFTemplate());
			newValue.setStagingMFTemplate(value.getStagingMFTemplate());
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
	 * @param value is of type IMFTemplateTrxValue
	 * @return MF Template transaction value
	 * @throws TrxOperationException on errors updating the transaction
	 */
	protected IMFTemplateTrxValue updateTransaction(IMFTemplateTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBMFTemplateTrxValue newValue = new OBMFTemplateTrxValue(tempValue);
			newValue.setMFTemplate(value.getMFTemplate());
			newValue.setStagingMFTemplate(value.getStagingMFTemplate());
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
	 * @param value is of type IMFTemplateTrxValue
	 * @return transaction result
	 */
	protected ITrxResult prepareResult(IMFTemplateTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type IMFTemplateTrxValue
	 * @return MF Template transaction value
	 */
	protected IMFTemplateTrxValue prepareTrxValue(IMFTemplateTrxValue value) {
		if (value != null) {
			IMFTemplate actual = value.getMFTemplate();
			IMFTemplate staging = value.getStagingMFTemplate();

			value.setReferenceID(actual != null ? String.valueOf(actual.getMFTemplateID()) : null);
			value.setStagingReferenceID(staging != null ? String.valueOf(staging.getMFTemplateID()) : null);
		}
		return value;
	}

}
