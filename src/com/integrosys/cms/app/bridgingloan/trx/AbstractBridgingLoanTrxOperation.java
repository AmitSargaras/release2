package com.integrosys.cms.app.bridgingloan.trx;

import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.ejbsupport.ConcurrentUpdateException;
import com.integrosys.cms.app.bridgingloan.bus.BridgingLoanException;
import com.integrosys.cms.app.bridgingloan.bus.IBridgingLoan;
import com.integrosys.cms.app.bridgingloan.bus.SBBridgingLoanBusManager;
import com.integrosys.cms.app.bridgingloan.bus.SBBridgingLoanBusManagerHome;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
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
public abstract class AbstractBridgingLoanTrxOperation extends CMSTrxOperation implements ITrxRouteOperation {
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
	 * This method set the primary key from the original to the copied bridging
	 * loan object. It is required for the case of updating staging from actual
	 * and vice versa as there is a need to perform a deep clone of the object
	 * and set the required attribute in the object to the original one so that
	 * a proper update can be done.
	 * @param anOriginal - IBridgingLoan type
	 * @param aCopy - IBridgingLoan type
	 * @return IBridgingLoan - the copied object with required attributes from
	 *         the original object
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected IBridgingLoan mergeBridgingLoan(IBridgingLoan anOriginal, IBridgingLoan aCopy)
			throws TrxOperationException {
		aCopy.setProjectID(anOriginal.getProjectID());
		aCopy.setVersionTime(anOriginal.getVersionTime());
		return aCopy;
	}

	// ==================================
	// Create Methods
	// ==================================
	/**
	 * Create the staging bridging loan object
	 * @param bridgingLoanTrxValue - IBridgingLoanTrxValue type
	 * @return IBridgingLoanTrxValue - the trx object containing the created
	 *         staging bridging loan
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         errors
	 */
	protected IBridgingLoanTrxValue createStagingBridgingLoan(IBridgingLoanTrxValue bridgingLoanTrxValue)
			throws TrxOperationException {
		try {
			IBridgingLoan bridgingLoan = getSBStagingBridgingLoanBusManager().create(
					bridgingLoanTrxValue.getStagingBridgingLoan());
			bridgingLoanTrxValue.setStagingBridgingLoan(bridgingLoan);
			bridgingLoanTrxValue.setStagingReferenceID(String.valueOf(bridgingLoan.getProjectID()));
			return bridgingLoanTrxValue;
		}
		catch (BridgingLoanException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex.toString());
		}
	}

	protected IBridgingLoanTrxValue createActualBridgingLoan(IBridgingLoanTrxValue bridgingLoanTrxValue)
			throws TrxOperationException {
		try {
			IBridgingLoan staging = bridgingLoanTrxValue.getStagingBridgingLoan();
			IBridgingLoan actualBridgingLoan = getSBBridgingLoanBusManager().create(staging);

			bridgingLoanTrxValue.setBridgingLoan(actualBridgingLoan);
			bridgingLoanTrxValue.setReferenceID(String.valueOf(actualBridgingLoan.getProjectID()));
			return bridgingLoanTrxValue;
		}
		catch (BridgingLoanException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in createActualBridgingLoan(): " + ex.toString());
		}
	}

	/**
	 * Create a bridging loan transaction
	 * @param anIBridgingLoanTrxValue - IBridgingLoanTrxValue
	 * @return IBridgingLoanTrxValue - the bridging loan specific transaction
	 *         object created
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected IBridgingLoanTrxValue createBridgingLoanTransaction(IBridgingLoanTrxValue anIBridgingLoanTrxValue)
			throws TrxOperationException {
		try {
			anIBridgingLoanTrxValue = prepareTrxValue(anIBridgingLoanTrxValue);
			ICMSTrxValue trxValue = createTransaction(anIBridgingLoanTrxValue);
			OBBridgingLoanTrxValue obPrepareTrxValue = new OBBridgingLoanTrxValue(trxValue);
			obPrepareTrxValue.setStagingBridgingLoan(anIBridgingLoanTrxValue.getStagingBridgingLoan());
			obPrepareTrxValue.setBridgingLoan(anIBridgingLoanTrxValue.getBridgingLoan());
			return obPrepareTrxValue;
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
	 * Update the actual bridging loan object
	 * @param anIBridgingLoanTrxValue - ITrxValue type
	 * @return IBridgingLoanTrxValue - the bridging loan trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	protected IBridgingLoanTrxValue updateActualBridgingLoan(IBridgingLoanTrxValue anIBridgingLoanTrxValue)
			throws TrxOperationException {
		try {
			IBridgingLoan staging = anIBridgingLoanTrxValue.getStagingBridgingLoan();
			IBridgingLoan actual = anIBridgingLoanTrxValue.getBridgingLoan();

			IBridgingLoan updActual = (IBridgingLoan) CommonUtil.deepClone(staging);
			updActual = mergeBridgingLoan(actual, updActual);
			IBridgingLoan actualBridgingLoan = getSBBridgingLoanBusManager().update(updActual);

			anIBridgingLoanTrxValue.setBridgingLoan(actualBridgingLoan);
			anIBridgingLoanTrxValue.setReferenceID(String.valueOf(actualBridgingLoan.getProjectID()));
			return anIBridgingLoanTrxValue;
		}
		catch (ConcurrentUpdateException ex) {
			throw new TrxOperationException(ex);
		}
		catch (BridgingLoanException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("Exception in updateActualBridgingLoan(): " + ex.toString());
		}
	}

	/**
	 * Update a bridging loan transaction
	 * @param anIBridgingLoanTrxValue of IBridgingLoanTrxValue type
	 * @return IBridgingLoanTrxValue - the bridging loan specific transaction
	 *         object updated
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is any processing errors
	 */
	protected IBridgingLoanTrxValue updateBridgingLoanTransaction(IBridgingLoanTrxValue anIBridgingLoanTrxValue)
			throws TrxOperationException {
		try {
			anIBridgingLoanTrxValue = prepareTrxValue(anIBridgingLoanTrxValue);
			ICMSTrxValue trxValue = updateTransaction(anIBridgingLoanTrxValue);
			OBBridgingLoanTrxValue newTrxValue = new OBBridgingLoanTrxValue(trxValue);
			newTrxValue.setStagingBridgingLoan(anIBridgingLoanTrxValue.getStagingBridgingLoan());
			newTrxValue.setBridgingLoan(anIBridgingLoanTrxValue.getBridgingLoan());
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
	protected IBridgingLoanTrxValue prepareTrxValue(IBridgingLoanTrxValue bridgingLoanTrxValue) {
		if (bridgingLoanTrxValue != null) {
			IBridgingLoan actual = bridgingLoanTrxValue.getBridgingLoan();
			IBridgingLoan staging = bridgingLoanTrxValue.getStagingBridgingLoan();
			if ((actual != null) && (actual.getProjectID() != ICMSConstant.LONG_INVALID_VALUE)) {
				bridgingLoanTrxValue.setReferenceID(String.valueOf(actual.getProjectID()));
			}
			else {
				bridgingLoanTrxValue.setReferenceID(null);
			}

			if ((staging != null) && (staging.getProjectID() != ICMSConstant.LONG_INVALID_VALUE)) {
				bridgingLoanTrxValue.setStagingReferenceID(String.valueOf(staging.getProjectID()));
			}
			else {
				bridgingLoanTrxValue.setStagingReferenceID(null);
			}
			return bridgingLoanTrxValue;
		}
		return null;
	}

	/**
	 * Helper method to cast a generic trx value object to a document item
	 * specific trx value object
	 * @param anITrxValue of ITrxValue type
	 * @return IBridgingLoanTrxValue - the bridging loan specific trx value
	 *         object
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         there is a ClassCastException
	 */
	protected IBridgingLoanTrxValue getBridgingLoanTrxValue(ITrxValue anITrxValue) throws TrxOperationException {
		try {
			return (IBridgingLoanTrxValue) anITrxValue;
		}
		catch (ClassCastException ex) {
			throw new TrxOperationException("The ITrxValue is not of type IBridgingLoanTrxValue: " + ex.toString());
		}
	}

	/**
	 * Prepares a result object to be returned
	 * @param value of IBridgingLoanTrxValue type
	 * @return ITrxResult
	 */
	protected ITrxResult prepareResult(IBridgingLoanTrxValue value) {
		OBCMSTrxResult result = new OBCMSTrxResult();
		result.setTrxValue(value);
		return result;
	}

	// ==================================
	// Start of getting SB Interfaces
	// ==================================
	/**
	 * Get the home interface for staging bridging loan session bean
	 * @return SBBridgingLoanBusManager - the home interface for the staging
	 *         bridging loan session bean
	 */
	private SBBridgingLoanBusManager getSBStagingBridgingLoanBusManager() {
		SBBridgingLoanBusManager remote = (SBBridgingLoanBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_STAGING_BRIDGING_LOAN_BUS_JNDI, SBBridgingLoanBusManagerHome.class.getName());
		return remote;
	}

	/**
	 * Get the home interface for bridging loan session bean
	 * @return SBBridgingLoanBusManager - the home interface for the bridging
	 *         loan session bean
	 */
	private SBBridgingLoanBusManager getSBBridgingLoanBusManager() {
		SBBridgingLoanBusManager remote = (SBBridgingLoanBusManager) BeanController.getEJB(
				ICMSJNDIConstant.SB_BRIDGING_LOAN_BUS_JNDI, SBBridgingLoanBusManagerHome.class.getName());
		return remote;
	}
}