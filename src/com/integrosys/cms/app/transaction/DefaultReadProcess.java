/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/DefaultReadProcess.java,v 1.3 2006/07/20 04:28:02 wltan Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxProcess;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.IllegalOperationException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.StopWatch;

/**
 * The DefaultReadProcess class is the default class to perform read operations
 * to retrieve business and transaction data
 * @author $Author: wltan $
 * @version $Revision: 1.3 $
 * @since $Date: 2006/07/20 04:28:02 $ Tag: $Name: $
 */

public class DefaultReadProcess implements ITrxProcess {
	public DefaultReadProcess() {
	}

	/**
	 * This method begins the work to be done within a transaction operation,
	 * which in this case is to retrieve a transaction record given an ITrxValue
	 * object that contains the required parameters, such as transactionID. Only
	 * ITrxOperation classes that also implements <code>ITrxReadOperation</code>
	 * will be used in this operation. Otherwise, the input ITrxValue will be
	 * returned as the result.
	 * 
	 * @param op is the ITrxOperation object that will perform the required
	 *        business logic
	 * @param value is the ITrxValue containing the transaction date
	 * @return ITrxResult containing the results of the transaction operation
	 * @throws TrxParameterException if the input parameters are incorrect or
	 *         contain errors
	 * @throws TrxOperationException if error occurs during transaction
	 *         operation.
	 * @throws IllegalOperationException if error occurs during state
	 *         transistion
	 * @throws TransactionException if any other error occurs
	 */
	public ITrxResult operateTransaction(ITrxOperation op, ITrxValue value) throws TrxParameterException,
			TrxOperationException, IllegalOperationException, TransactionException {
		if (null == op) {
			throw new TrxParameterException("ITrxOperation is null!");
		}
		if (null == value) {
			throw new TrxParameterException("ITrxValue is null!");
		}

		if (op instanceof ITrxReadOperation) {
			DefaultLogger.debug(this, "Is instanceof ITrxReadOperation.");
			ITrxReadOperation rOp = (ITrxReadOperation) op;
			ICMSTrxValue trxValue = null;

			try {
				trxValue = (ICMSTrxValue) value;
			}
			catch (ClassCastException ce) {
				throw new TrxParameterException("Caught Exception: " + ce.toString());
			}

			String trxInfo = getTrxInfo(value, rOp);
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			DefaultLogger.debug("ReadProcess", "--OPERATION:" + trxInfo + " START --> (0 ms)");

			ITrxValue returnValue = rOp.getTransaction(trxValue);

			OBCMSTrxResult result = new OBCMSTrxResult();
			result.setTrxValue(returnValue);

			DefaultLogger.debug("ReadProcess", "--OPERATION:" + trxInfo + " END -->>>> total time taken ("
					+ stopWatch.timeElapsed() + " ms)");

			return result;
		}
		else {
			DefaultLogger.debug(this, "Not instanceof ITrxReadOperation.");
			OBCMSTrxResult result = new OBCMSTrxResult();
			result.setTrxValue(value);
			return result;
		}
	}

	/**
	 * Helper method to formulate transaction info.
	 * 
	 * @param trxValue
	 * @param readOperation
	 * @return String
	 */
	private String getTrxInfo(ITrxValue trxValue, ITrxReadOperation readOperation) {
		StringBuffer buf = new StringBuffer();
		String trxType = (trxValue.getTransactionType() == null) ? "-" : trxValue.getTransactionType();
		buf.append("[").append(trxType).append("] [");
		buf.append(readOperation.getClass().getName()).append("] [");
		buf.append(trxValue.getTransactionID()).append("]");
		return buf.toString();
	}

	/**
	 * This method will always return null.
	 * 
	 * @return null
	 */
	public String getNextState(String fromState, String operationName, String instanceName)
	// throws StateTransactionException, TransactionException
	{
		// next state will always return the fromState for the read operation
		return null;

	}

}
