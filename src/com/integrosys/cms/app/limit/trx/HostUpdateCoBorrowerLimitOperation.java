/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/HostUpdateCoBorrowerLimitOperation.java,v 1.3 2003/09/28 13:55:54 slong Exp $
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
 * This operation allows a host to update limit data
 * 
 * @author $Author: slong $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/28 13:55:54 $ Tag: $Name: $
 */
public class HostUpdateCoBorrowerLimitOperation extends AbstractCoBorrowerLimitTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public HostUpdateCoBorrowerLimitOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_HOST_UPDATE_CO_LIMIT;
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
	 * 1. Update Staging Record 2. Update Actual Record 3. Update Transaction
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICoBorrowerLimitTrxValue trxValue = super.getCoBorrowerLimitTrxValue(value);

			// trxValue = super.updateStagingLimit(trxValue);
			// trxValue = super.updateActualLimit(trxValue);
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