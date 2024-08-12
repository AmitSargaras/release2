/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/CreateLimitOperation.java,v 1.7 2003/10/07 02:02:11 slong Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ILimit;

/**
 * This operation creates a limit
 * 
 * @author $Author: slong $
 * @version $Revision: 1.7 $
 * @since $Date: 2003/10/07 02:02:11 $ Tag: $Name: $
 */
public class CreateLimitOperation extends AbstractLimitTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CreateLimitOperation() {
		super();
	}

	/**
	 * Pre process. Prepares the transaction object for persistance Get the
	 * limit profile transaction ID to be appended as trx parent ref
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxValue
	 * @throws TrxOperationException on error
	 */
	public ITrxValue preProcess(ITrxValue value) throws TrxOperationException {
		value = super.preProcess(value);

		// get the limit profile ID trx to be appended to this trx as a parent
		// trx
		ILimitTrxValue trxValue = getLimitTrxValue(value);

		ILimit limit = trxValue.getLimit(); // get from staging
		long profileID = limit.getLimitProfileID();
		if (ICMSConstant.LONG_INVALID_VALUE == profileID) {
			throw new TrxOperationException("LimitProfile ID is undefined in ILimit: " + profileID);
		}

		// set to -999999999l instead
		trxValue.setTrxReferenceID(Long.toString(ICMSConstant.LONG_INVALID_VALUE));

		return trxValue;

		// try {
		// // ILimitProxy proxy = LimitProxyFactory.getProxy();
		// // ILimitProfileTrxValue profileTrx =
		// proxy.getTrxLimitProfile(profileID);
		//
		// // don't set the trx reference id else , the SI message will create a
		// transaction with this
		// //trxValue.setTrxReferenceID(profileTrx.getTransactionID()); //set
		// customer transaction as ref trx
		//
		//
		//
		// return trxValue;
		// }
		// catch(LimitException e) {
		// throw new TrxOperationException("Caught LimitException!", e);
		// }
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CREATE_LIMIT;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Create Staging Record 2. Create Actual Record 3. Create Transaction
	 * Record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILimitTrxValue trxValue = super.getLimitTrxValue(value);

			// trxValue = super.createStagingLimit(trxValue);
			// trxValue = super.createActualLimit(trxValue);
			trxValue = super.createTransaction(trxValue);

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