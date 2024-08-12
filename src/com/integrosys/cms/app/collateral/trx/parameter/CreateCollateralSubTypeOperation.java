/*
Copyright Integro Technologies Pte Ltd
$Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CreateCollateralSubTypeOperation.java,v 1.3 2003/08/14 13:40:18 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/08/14 13:40:18 $ Tag: $Name: $
 */
public class CreateCollateralSubTypeOperation extends AbstractCollateralSubTypeTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_CREATE_SUBTYPE;
	}

	public ITrxResult performProcess(ITrxValue iTrxValue) throws TrxOperationException {

		try {
			ICollateralSubTypeTrxValue trxValue = (ICollateralSubTypeTrxValue) (iTrxValue);

			// Create both the staging and actual collateral subtypes,
			// prepare the reference ids of the transaction and
			// create the transaction.
			trxValue = createStagingCollateralSubTypes(trxValue);
			// trxValue = createCollateralSubType(trxValue);
			trxValue = createTransaction(trxValue);

			return prepareResult(trxValue);

		}
		catch (Exception e) {
			throw new TrxOperationException("Caught Exception!", e);
		}
	}
}
