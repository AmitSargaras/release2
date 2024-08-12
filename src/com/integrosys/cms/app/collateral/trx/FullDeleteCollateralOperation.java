/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/FullDeleteCollateralOperation.java,v 1.4 2003/10/09 10:24:56 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked to delete a collateral if no document is in
 * vault.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/09 10:24:56 $ Tag: $Name: $
 */
public class FullDeleteCollateralOperation extends AbstractCollateralTrxOperation {
	/**
	 * Default constructor.
	 */
	public FullDeleteCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_FULL_DELETE_COL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging collateral record 1. update actual collateral record 2.
	 * update transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICollateralTrxValue trxValue = super.getCollateralTrxValue(value);

			// trxValue.setStagingCollateral (trxValue.getCollateral());
			// trxValue = super.createStagingCollateral (trxValue);

			// to update status of a collateral in actual table
			// trxValue = super.updateActualCollateral (trxValue);

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
