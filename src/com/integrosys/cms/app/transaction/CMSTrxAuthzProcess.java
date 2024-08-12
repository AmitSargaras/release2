/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/transaction/CMSTrxAuthzProcess.java,v 1.2 2003/06/12 11:58:56 hltan Exp $
 */
package com.integrosys.cms.app.transaction;

import com.integrosys.base.businfra.transaction.ITrxOperation;
import com.integrosys.base.businfra.transaction.ITrxProcess;
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.IllegalOperationException;
import com.integrosys.base.businfra.transaction.StateTransactionException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.businfra.transaction.TrxParameterException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This process handles the authorisation aspect of the transaction engine. For
 * a given ITrxOperation object, it identifies of it is an instance of
 * ITrxAuthzProcess, and if so, invokes the <code>authorize</code> method.
 * 
 * @author Alfred Lee
 */
public class CMSTrxAuthzProcess implements ITrxProcess {
	/**
	 * This method begins the work to be done within a transaction operation,
	 * which in this case is to authorize the transaction process.
	 * 
	 * @param op is the ITrxOperation object that will perform the required
	 *        business logic
	 * @param value is the ITrxValue containing the transaction date
	 * @return ITrxResult containing the results of the transaction operation
	 * @throws TrxParameterException if the input parameters are incorrect or
	 *         contain errors
	 * @throws TrxOperationException if error occurs during transaction
	 *         operation
	 * @throws IllegalOperationException if error occurs during state
	 *         transistion
	 * @throws TransactionException if any other error occurs
	 */
	public ITrxResult operateTransaction(ITrxOperation op, ITrxValue value) throws TrxParameterException,
			TrxOperationException, TransactionException {
		if (op instanceof ITrxAuthzOperation) {
			DefaultLogger.debug(this, "Is instanceof ITrxAuthzOperation.");
			ITrxAuthzOperation aOp = (ITrxAuthzOperation) op;
			try {
				if (true == aOp.authorize(value)) {
					OBCMSTrxResult result = new OBCMSTrxResult();
					result.setTrxValue(value);
					return result;
				}
				else {
					throw new TrxAuthzOperationException("Failed to authorise!");
				}
			}
			catch (TrxAuthzOperationException te) {
				throw te;
			}
		}
		else {
			DefaultLogger.debug(this, "Not instanceof ITrxAuthzOperation.");
			OBCMSTrxResult result = new OBCMSTrxResult();
			result.setTrxValue(value);
			return result;
		}
	}

	/**
	 * Method to return the next state of the transaction. The next state is
	 * defined in the state transistion table. However this method is not
	 * implemented in this class.
	 * 
	 * @param fromState is the String value of the from State
	 * @param operationName is the String value of the operation name to cause
	 *        state change
	 * @param instanceName is the String value of the instance of state
	 *        transistion table due to this transaction
	 * @return null
	 * @throws StateTransactionException if an error occur during the retrieval
	 *         of the next state
	 * @throws TransactionException if any other errors occur
	 */
	public String getNextState(String fromState, String operationName, String instanceName)
			throws StateTransactionException, TransactionException {
		return null;
	}
}
