/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CheckerApproveUpdateCollateralParameterOperation.java,v 1.1 2003/08/14 04:34:12 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to approve security parameter
 * updated by a maker.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 04:34:12 $ Tag: $Name: $
 */
public class CheckerApproveUpdateCollateralParameterOperation extends AbstractCollateralParameterTrxOperation {
	/**
	 * Default constructor.
	 */
	public CheckerApproveUpdateCollateralParameterOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_COLPARAM;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update actual collateral record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICollateralParameterTrxValue trxValue = super.getCollateralParameterTrxValue(value);

			trxValue = super.updateActualCollateralParameters(trxValue);
			trxValue = super.updateTransaction(trxValue);

			return super.prepareResult(trxValue);
		}
		catch (TrxOperationException e) {
			throw e;
		}
		catch (Exception e) {
			throw new TrxOperationException("Exception caught!", e);
		}
	}
}
