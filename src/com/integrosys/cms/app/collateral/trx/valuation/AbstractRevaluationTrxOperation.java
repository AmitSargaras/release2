/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/AbstractRevaluationTrxOperation.java,v 1.3 2003/08/06 11:24:09 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.valuation;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.CollateralException;
import com.integrosys.cms.app.collateral.bus.ICollateralBusManager;
import com.integrosys.cms.app.collateral.bus.IValuation;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Abstract class that contain methods that is common among valuation trx
 * operations.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/06 11:24:09 $ Tag: $Name: $
 */
public abstract class AbstractRevaluationTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	private ICollateralBusManager actualCollateralBusManager;

	private ICollateralBusManager stagingCollateralBusManager;

	private ICollateralTrxDAO collateralTrxJdbcDao;

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

	public void setCollateralTrxJdbcDao(ICollateralTrxDAO collateralTrxJdbcDao) {
		this.collateralTrxJdbcDao = collateralTrxJdbcDao;
	}

	public ICollateralTrxDAO getCollateralTrxJdbcDao() {
		return collateralTrxJdbcDao;
	}

	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws TransactionException on error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * Prepares the transaction object for persistance. Implemented here to get
	 * the collateral id to be appended as transaction parent reference id.
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction value
	 * @throws TrxOperationException on error pre process the transaction
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);
		IValuationTrxValue trxValue = getValuationTrxValue(value);

		try {
			if (trxValue.getTrxReferenceID() == null) {
				IValuation actual = trxValue.getValuation();
				IValuation staging = trxValue.getStagingValuation();

				String refID = null;

				if (actual != null) {
					refID = String.valueOf(actual.getCollateralID());
				}
				else if (staging != null) {
					refID = String.valueOf(staging.getCollateralID());
				}

				String parentTrxID = getCollateralTrxJdbcDao()
						.getTransactionID(refID, ICMSConstant.INSTANCE_COLLATERAL);
				if (parentTrxID == null) {
					throw new TrxOperationException("TrxID undefined for collateral: " + refID);
				}
				trxValue.setTrxReferenceID(parentTrxID);
			}
			return trxValue;
		}
		catch (SearchDAOException e) {
			throw new TrxOperationException("SearchDAOException caught!", e);
		}
	}

	/**
	 * Create actual valuation record.
	 * 
	 * @param trxVal is of type IValuationTrxValue
	 * @return IValuationTrxValue
	 * @throws TrxOperationException on errors creating valuation
	 */
	protected IValuationTrxValue createActualValuation(IValuationTrxValue trxVal) throws TrxOperationException {
		try {
			IValuation valuation = trxVal.getValuation();
			valuation = getActualCollateralBusManager().createValuation(valuation);
			trxVal.setValuation(valuation);
			return trxVal;
		}
		catch (CollateralException e) {
			throw new TrxOperationException("Caught CollateralException!", e);
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}

	/**
	 * Create staging valuation record.
	 * 
	 * @param value is of type IValuationTrxValue
	 * @return IValuationTrxValue
	 * @throws TrxOperationException on error creating staging valuation
	 */
	protected IValuationTrxValue createStagingValuation(IValuationTrxValue value) throws TrxOperationException {
		try {
			IValuation valuation = value.getStagingValuation();

			String stageColID = getCollateralTrxJdbcDao().getStagingRefIDByTrxID(value.getTrxReferenceID());

			if ((stageColID == null) || (stageColID.length() == 0)) {
				throw new TrxOperationException("Staging Collateral id is null for staging valuation "
						+ valuation.getValuationID());
			}

			valuation = getStagingCollateralBusManager().createValuation(valuation);
			value.setStagingValuation(valuation);

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
	 * Method to create a transaction record
	 * 
	 * @param value is of type IValuationTrxValue
	 * @return IValuationTrxValue
	 * @throws TrxOperationException on errors creating transaction
	 */
	protected IValuationTrxValue createTransaction(IValuationTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);
			ICMSTrxValue tempValue = super.createTransaction(value);
			OBValuationTrxValue newValue = new OBValuationTrxValue(tempValue);
			newValue.setValuation(value.getValuation());
			newValue.setStagingValuation(value.getStagingValuation());
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
	 * Method to update a transaction record
	 * 
	 * @param value is of type IValuationTrxValue
	 * @return IValuationTrxValue
	 * @throws TrxOperationException on errors updating transaction
	 */
	protected IValuationTrxValue updateTransaction(IValuationTrxValue value) throws TrxOperationException {
		try {
			value = prepareTrxValue(value);

			ICMSTrxValue tempValue = super.updateTransaction(value);
			OBValuationTrxValue newValue = new OBValuationTrxValue(tempValue);
			newValue.setValuation(value.getValuation());
			newValue.setStagingValuation(value.getStagingValuation());
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
	 * Convert a ITrxValue into a IValuationTrxValue object.
	 * 
	 * @param value is of type ITrxValue
	 * @return IValuationTrxValue
	 * @throws TrxOperationException on error casting the transaction value
	 */
	protected IValuationTrxValue getValuationTrxValue(ITrxValue value) throws TrxOperationException {
		try {
			IValuationTrxValue trxValue = (IValuationTrxValue) value;
			return trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not instanceof IValuationTrxValue!", e);
		}
	}

	/**
	 * Prepares a result object to be returned.
	 * 
	 * @param value is of type IValuationTrxValue
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IValuationTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	/**
	 * Helper method to prepare transaction object.
	 * 
	 * @param value of type IValuationTrxValue
	 * @return valuation transaction value
	 */
	private IValuationTrxValue prepareTrxValue(IValuationTrxValue value) {
		if (value != null) {
			IValuation actual = value.getValuation();
			IValuation staging = value.getStagingValuation();

			value.setReferenceID(actual != null ? String.valueOf(actual.getValuationID()) : null);
			value.setStagingReferenceID(staging != null ? String.valueOf(staging.getValuationID()) : null);
		}
		return value;
	}
}