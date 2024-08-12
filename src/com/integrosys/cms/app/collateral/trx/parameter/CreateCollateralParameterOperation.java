/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CreateCollateralParameterOperation.java,v 1.3 2003/08/13 14:39:28 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/13 14:39:28 $ Tag: $Name: $
 */
public class CreateCollateralParameterOperation extends AbstractCollateralParameterTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_CREATE_COLPARAM;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. Create Staging Record 2. Create Actual Record 3. Create Transaction
	 * Record
	 * 
	 * @param value is of type ITrxValue
	 * @return ITrxResult
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICollateralParameterTrxValue trxValue = (ICollateralParameterTrxValue) (value);

			// Create both the staging and actual collateral parameters,
			// prepare the reference ids of the transaction and
			// create the transaction.
			trxValue = createStagingCollateralParameters(trxValue);
			trxValue = createActualCollateralParameters(trxValue);
			trxValue = createTransaction(trxValue);

			return prepareResult(trxValue);

		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}
