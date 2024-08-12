/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/MakerResubmitCreateTATOperation.java,v 1.2 2003/07/23 07:49:22 kllee Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows maker to resubmit a rejected update
 * 
 * @author $Author: kllee $
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/23 07:49:22 $ Tag: $Name: $
 */
public class MakerResubmitCreateTATOperation extends AbstractLimitProfileTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerResubmitCreateTATOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_RESUBMIT_CREATE_TAT;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Create New Staging Record 2. Update Transaction Record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILimitProfileTrxValue trxValue = super.getLimitProfileTrxValue(value);

			trxValue = super.createStagingLimitProfile(trxValue);
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