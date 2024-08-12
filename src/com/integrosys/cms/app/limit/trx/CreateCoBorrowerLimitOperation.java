/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/CreateCoBorrowerLimitOperation.java,v 1.3 2003/09/28 13:55:17 slong Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.limit.bus.ICoBorrowerLimit;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.proxy.ILimitProxy;
import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;

/**
 * This operation creates a limit
 * 
 * @author $Author: slong $
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/28 13:55:17 $ Tag: $Name: $
 */
public class CreateCoBorrowerLimitOperation extends AbstractCoBorrowerLimitTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CreateCoBorrowerLimitOperation() {
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

		// get the out limit ID trx to be appended to this trx as a parent trx
		ICoBorrowerLimitTrxValue trxValue = getCoBorrowerLimitTrxValue(value);

		ICoBorrowerLimit limit = trxValue.getLimit(); // get from staging
		long outerLimitID = limit.getOuterLimitID();
		if (com.integrosys.cms.app.common.constant.ICMSConstant.LONG_INVALID_VALUE == outerLimitID) {
			throw new TrxOperationException("OuterLimitID ID is undefined in ICoBorrowerLimit: " + outerLimitID);
		}
		try {
			ILimitProxy proxy = LimitProxyFactory.getProxy();
			ILimitTrxValue outerTrx = proxy.getTrxLimit(outerLimitID);
			trxValue.setTrxReferenceID(outerTrx.getTransactionID()); // set
																		// outer
																		// limit
																		// transaction
																		// as
																		// ref
																		// trx

			return trxValue;
		}
		catch (LimitException e) {
			throw new TrxOperationException("Caught LimitException!", e);
		}
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CREATE_CO_LIMIT;
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
			ICoBorrowerLimitTrxValue trxValue = super.getCoBorrowerLimitTrxValue(value);

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