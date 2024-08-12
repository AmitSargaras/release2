/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collaborationtask/trx/MakerCreateCollateralTaskOperation.java,v 1.2 2003/08/22 11:13:26 sathish Exp $
 */
package com.integrosys.cms.app.collaborationtask.trx;

//java
import java.rmi.RemoteException;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collaborationtask.bus.ICollateralTask;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation creates a pending collateral task
 * 
 * @author $Author: sathish $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/08/22 11:13:26 $ Tag: $Name: $
 */
public class MakerCreateCollateralTaskOperation extends AbstractCollateralTaskTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCreateCollateralTaskOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_COLLATERAL_TASK;
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * parent transaction ID to be appended as trx parent ref
	 * @param anITrxValue is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue anITrxValue) throws TrxOperationException {
		anITrxValue = super.preProcess(anITrxValue);
		ICollateralTaskTrxValue trxValue = getCollateralTaskTrxValue(anITrxValue);
		ICollateralTask staging = trxValue.getStagingCollateralTask();
		try {
			if (staging != null) {
				if (staging.getLimitProfileID() != com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE) {
					ICMSTrxValue parentTrx = getTrxManager().getTrxByRefIDAndTrxType(
							String.valueOf(staging.getLimitProfileID()), ICMSConstant.INSTANCE_LIMIT_PROFILE);
					trxValue.setTrxReferenceID(parentTrx.getTransactionID());
				}
				return trxValue;
			}
			return trxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (RemoteException ex) {
			throw new TrxOperationException("Exception in preProcess: " + ex.toString());
		}
	}

	/**
	 * Process the transaction 1. Create the staging data 2. Create the
	 * transaction record
	 * @param anITrxValue of ITrxValue type
	 * @return ITrxResult - the transaction result
	 * @throws TrxOperationException if encounters any error during the
	 *         processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		ICollateralTaskTrxValue trxValue = super.getCollateralTaskTrxValue(anITrxValue);
		trxValue = createStagingCollateralTask(trxValue);
		trxValue = createCollateralTaskTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create a Collateral task transaction
	 * @param anICollateralTaskTrxValue of ICollateralTaskTrxValue type
	 * @return ICollateralTaskTrxValue - the Collateral Task specific
	 *         transaction object created
	 * @throws TrxOperationException if there is any processing errors
	 */
	private ICollateralTaskTrxValue createCollateralTaskTransaction(ICollateralTaskTrxValue anICollateralTaskTrxValue)
			throws TrxOperationException {
		try {
			anICollateralTaskTrxValue = prepareTrxValue(anICollateralTaskTrxValue);
			ICMSTrxValue trxValue = createTransaction(anICollateralTaskTrxValue);
			OBCollateralTaskTrxValue colTaskTrxValue = new OBCollateralTaskTrxValue(trxValue);
			colTaskTrxValue.setStagingCollateralTask(anICollateralTaskTrxValue.getStagingCollateralTask());
			colTaskTrxValue.setCollateralTask(anICollateralTaskTrxValue.getCollateralTask());
			return colTaskTrxValue;
		}
		catch (TransactionException ex) {
			throw new TrxOperationException(ex);
		}
		catch (Exception ex) {
			throw new TrxOperationException("General Exception: " + ex.toString());
		}
	}
}