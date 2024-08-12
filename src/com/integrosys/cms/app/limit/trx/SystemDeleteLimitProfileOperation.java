/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/SystemDeleteLimitProfileOperation.java,v 1.2 2006/04/21 09:46:43 jychong Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * This operation allows a system to delete a limit transaction
 * 
 * @author $Author: jychong $
 * @version $Revision: 1.2 $
 * @since $Date: 2006/04/21 09:46:43 $ Tag: $Name: $
 */
public class SystemDeleteLimitProfileOperation extends AbstractLimitProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public SystemDeleteLimitProfileOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SYSTEM_DELETE_LIMIT_PROFILE;
	}

	/**
	 * This method defines the process that should initialised values that would
	 * be required in the <code>performProcess</code> method
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue object that has been initialised with required values
	 * @throws TrxOperationException on errors
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		// this preProcess is to maintain fromState so that it will not be
		// overwritten by the
		// current status which is rejected. i.e. we don't want to have a
		// scenario where we have
		// from statte: rejected, to state: rejected too.
		try {
			String status = value.getStatus();
			if (status.equals(ICMSConstant.STATE_REJECTED)) {
				String fromState = value.getFromState();
				DefaultLogger.debug(this, "From State: " + fromState);
				// then do super.preProcess
				value = super.preProcess(value);
				// now set back the from state to overwrite what was created in
				// preProcess
				ICMSTrxValue trxValue = getCMSTrxValue(value);
				trxValue.setFromState(fromState);
				return trxValue;
			}
			else {
				return super.preProcess(value);
			}
		}
		catch (TransactionException e) {
			throw new TrxOperationException("Caught TransactionException!", e);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TrxOperationException("Caught Unknown Exception!", e);
		}
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Update Transaction
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILimitProfileTrxValue trxValue = super.getLimitProfileTrxValue(value);

			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}