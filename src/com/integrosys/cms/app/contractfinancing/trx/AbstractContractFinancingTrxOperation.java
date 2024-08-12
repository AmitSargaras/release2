package com.integrosys.cms.app.contractfinancing.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.contractfinancing.bus.ContractFinancingException;
import com.integrosys.cms.app.contractfinancing.bus.IContractFinancing;
import com.integrosys.cms.app.contractfinancing.bus.SBContractFinancingBusManager;
import com.integrosys.cms.app.contractfinancing.bus.SBContractFinancingBusManagerHome;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxRouteOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxResult;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 21, 2007 Tag: $Name$
 */
public abstract class AbstractContractFinancingTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {

	/**
	 * Sets the next route requirements into the ITrxValue.
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue containing the required routing information for next
	 *         user
	 * @throws com.integrosys.base.businfra.transaction.TransactionException on
	 *         error
	 */
	public ITrxValue getNextRoute(ITrxValue value) throws TransactionException {
		return value;
	}

	/**
	 * This method set the primary key from the original to the copied contract
	 * finance object. It is required for the case of updating staging from
	 * actual and vice versa as there is a need to perform a deep clone of the
	 * object and set the required attribute in the object to the original one
	 * so that a proper update can be done.
	 * @param anOriginal - IContractFinancing type
	 * @param aCopy - IContractFinancing type
	 * @return IContractFinancing - the copied object with required attributes
	 *         from the original object
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected IContractFinancing mergeContractFinancing(IContractFinancing anOriginal, IContractFinancing aCopy)
			throws TrxOperationException {
		aCopy.setContractID(anOriginal.getContractID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	// ==================================
	// Create Methods
	// ==================================
	/**
	 * Create the staging contract finance object
	 * @param contractFinancingTrxValue - IContractFinancingTrxValue type
	 * @return IContractFinancingTrxValue - the trx object containing the
	 *         created staging contract finance
	 * @throws TrxOperationException if errors
	 */
	protected IContractFinancingTrxValue createStagingContractFinancing(
			IContractFinancingTrxValue contractFinancingTrxValue) throws TrxOperationException {
		try {
			IContractFinancing contractFinancing = getSBStagingContractFinancingBusManager().create(
					contractFinancingTrxValue.getStagingContractFinancing());
			contractFinancingTrxValue.setStagingContractFinancing(contractFinancing);
			contractFinancingTrxValue.setStagingReferenceID(String.valueOf(contractFinancing.getContractID()));
			return contractFinancingTrxValue;
		}
		catch (ContractFinancingException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException(ex.toString());
		}
	}

	protected IContractFinancingTrxValue createActualContractFinancing(
			IContractFinancingTrxValue contractFinancingTrxValue) throws TrxOperationException {
		try {
			IContractFinancing staging = contractFinancingTrxValue.getStagingContractFinancing();

			IContractFinancing actualContractFinancing = getSBContractFinancingBusManager().create(staging);

			contractFinancingTrxValue.setContractFinancing(actualContractFinancing);
			DefaultLogger.debug(this, "after setContractFinancing");
			contractFinancingTrxValue.setReferenceID(String.valueOf(actualContractFinancing.getContractID()));
			DefaultLogger.debug(this, "after setReferenceID");
			return contractFinancingTrxValue;
		}
		catch (ContractFinancingException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in createActualContractFinancing(): " + ex.toString());
		}
	}

	/**
	 * Create a contract finance transaction
	 * @param anIContractFinancingTrxValue - IContractFinancingTrxValue
	 * @return IContractFinancingTrxValue - the contract finance specific
	 *         transaction object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected IContractFinancingTrxValue createContractFinancingTransaction(
			IContractFinancingTrxValue anIContractFinancingTrxValue) throws TrxOperationException {
		try {
			anIContractFinancingTrxValue = prepareTrxValue(anIContractFinancingTrxValue);
			ICMSTrxValue trxValue = createTransaction(anIContractFinancingTrxValue);
			OBContractFinancingTrxValue contractFinancingTrxValue = new OBContractFinancingTrxValue(trxValue);
			contractFinancingTrxValue.setStagingContractFinancing(anIContractFinancingTrxValue
					.getStagingContractFinancing());
			contractFinancingTrxValue.setContractFinancing(anIContractFinancingTrxValue.getContractFinancing());
			return contractFinancingTrxValue;
		}
		catch (TransactionException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	// ==================================
	// Update Methods
	// ==================================
	/**
	 * Update the actual contract finance object
	 * @param anIContractFinancingTrxValue - ITrxValue type
	 * @return IContractFinancingTrxValue - the contract finance trx value
	 * @throws TrxOperationException on errors
	 */
	protected IContractFinancingTrxValue updateActualContractFinancing(
			IContractFinancingTrxValue anIContractFinancingTrxValue) throws TrxOperationException {
		try {
			IContractFinancing staging = anIContractFinancingTrxValue.getStagingContractFinancing();
			IContractFinancing actual = anIContractFinancingTrxValue.getContractFinancing();

			IContractFinancing updActual = (IContractFinancing) CommonUtil.deepClone(staging);
			updActual = mergeContractFinancing(actual, updActual);
			IContractFinancing actualContractFinancing = getSBContractFinancingBusManager().update(updActual);

			DefaultLogger.debug(this, "actualContractFinancing=" + actualContractFinancing);
			anIContractFinancingTrxValue.setContractFinancing(actualContractFinancing);
			DefaultLogger.debug(this, "after setContractFinancing");
			anIContractFinancingTrxValue.setReferenceID(String.valueOf(actualContractFinancing.getContractID()));
			DefaultLogger.debug(this, "after setReferenceID");
			return anIContractFinancingTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (ContractFinancingException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualContractFinancing(): " + ex.toString());
		}
	}

	/**
	 * Update a contract finance transaction
	 * @param anIContractFinancingTrxValue of IContractFinancingTrxValue type
	 * @return IContractFinancingTrxValue - the contract finance specific
	 *         transaction object updated
	 * @throws TrxOperationException if there is any processing errors
	 */
	protected IContractFinancingTrxValue updateContractFinancingTransaction(
			IContractFinancingTrxValue anIContractFinancingTrxValue) throws TrxOperationException {
		try {
			anIContractFinancingTrxValue = prepareTrxValue(anIContractFinancingTrxValue);
			ICMSTrxValue trxValue = updateTransaction(anIContractFinancingTrxValue);
			OBContractFinancingTrxValue newTrxValue = new OBContractFinancingTrxValue(trxValue);
			newTrxValue.setStagingContractFinancing(anIContractFinancingTrxValue.getStagingContractFinancing());
			newTrxValue.setContractFinancing(anIContractFinancingTrxValue.getContractFinancing());
			return newTrxValue;
		}
		catch (TransactionException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
	}

	// ========================================================
	// Start of helper methods - to be used by child classes
	// ========================================================
	/**
	 * Prepares a trx object
	 */
	protected IContractFinancingTrxValue prepareTrxValue(IContractFinancingTrxValue contractFinancingTrxValue) {
		if (contractFinancingTrxValue != null) {
			IContractFinancing actual = contractFinancingTrxValue.getContractFinancing();
			IContractFinancing staging = contractFinancingTrxValue.getStagingContractFinancing();
			if ((actual != null) && (actual.getContractID() != ICMSConstant.LONG_INVALID_VALUE)) {
				contractFinancingTrxValue.setReferenceID(String.valueOf(actual.getContractID()));
			}
			else {
				contractFinancingTrxValue.setReferenceID(null);
			}

			if ((staging != null) && (staging.getContractID() != ICMSConstant.LONG_INVALID_VALUE)) {
				contractFinancingTrxValue.setStagingReferenceID(String.valueOf(staging.getContractID()));
			}
			else {
				contractFinancingTrxValue.setStagingReferenceID(null);
			}
			return contractFinancingTrxValue;
		}
		return null;
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue of ITrxValue type
	 * @return IContractFinancingTrxValue - the contract finance specific trx
	 *         value object
	 * @throws TrxOperationException if there is a ClassCastException
	 */
	protected IContractFinancingTrxValue getContractFinancingTrxValue(ITrxValue anITrxValue)
			throws TrxOperationException {
		try {
			return (IContractFinancingTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type IContractFinancingTrxValue: " + ex.toString());
		}
	}

	/**
	 * Prepares a result object to be returned
	 * @param value of IContractFinancingTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IContractFinancingTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	// ==================================
	// Start of getting SB Interfaces
	// ==================================
	/**
	 * Get the home interface for staging contract finance session bean
	 * @return SBContractFinancingBusManager - the home interface for the
	 *         staging contract finance session bean
	 */
	private SBContractFinancingBusManager getSBStagingContractFinancingBusManager() {
		SBContractFinancingBusManager remote = (SBContractFinancingBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_CONTRACT_FINANCING_BUS_JNDI, SBContractFinancingBusManagerHome.class
						.getName());
		return remote;
	}

	/**
	 * Get the home interface for contract finance session bean
	 * @return SBContractFinancingBusManager - the home interface for the
	 *         contract finance session bean
	 */
	private SBContractFinancingBusManager getSBContractFinancingBusManager() {
		SBContractFinancingBusManager remote = (SBContractFinancingBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_CONTRACT_FINANCING_BUS_JNDI, SBContractFinancingBusManagerHome.class.getName());
		return remote;
	}
}
