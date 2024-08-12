/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/limit/trx/MakerCancelUpdateLimitOperation.java,v 1.4 2005/09/23 05:27:12 whuang Exp $
 */
package com.integrosys.cms.app.limit.trx;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows maker to cancel an update make
 * 
 * @author $Author: whuang $
 * @version $Revision: 1.4 $
 * @since $Date: 2005/09/23 05:27:12 $ Tag: $Name: $
 */
public class MakerCancelUpdateLimitOperation extends AbstractLimitTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCancelUpdateLimitOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CANCEL_UPDATE_LIMIT;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Create New Staging From Actual 2. Update Transaction
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws TrxOperationException on error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ILimitTrxValue trxValue = (ILimitTrxValue) value;
			// swap
			ILimitTrxValue[] trxValues = trxValue.getLimitTrxValues();
			if (trxValues != null) {
				for (int i = 0; i < trxValues.length; i++) {
					trxValues[i].setStagingLimit(trxValues[i].getLimit());
				}
			}
			trxValue = super.createStagingLimit(trxValue);
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