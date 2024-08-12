/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/MakerUpdateCollateralParameterOperation.java,v 1.11 2003/08/14 04:32:23 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to update a collateral parameter.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2003/08/14 04:32:23 $ Tag: $Name: $
 */
public class MakerUpdateCollateralParameterOperation extends AbstractCollateralParameterTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerUpdateCollateralParameterOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_COLPARAM;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging collateral parameter record 2. create staging
	 * transaction record if the status is ND, otherwise update transaction
	 * record.
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICollateralParameterTrxValue trxValue = super.getCollateralParameterTrxValue(value);

			trxValue = super.createStagingCollateralParameters(trxValue);

			if (trxValue.getStatus().equals(ICMSConstant.STATE_ND)) {
				trxValue = super.createTransaction(trxValue);
			}
			else {
				trxValue = super.updateTransaction(trxValue);
			}

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
