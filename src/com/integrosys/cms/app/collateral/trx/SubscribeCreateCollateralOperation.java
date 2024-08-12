/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/SubscribeCreateCollateralOperation.java,v 1.4 2003/10/09 08:40:32 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked to subcribe a collteral created in SCI.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2003/10/09 08:40:32 $ Tag: $Name: $
 */
public class SubscribeCreateCollateralOperation extends AbstractCollateralTrxOperation {
	/**
	 * Default constructor.
	 */
	public SubscribeCreateCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_SUBSCRIBE_CREATE_COL;
	}

	/**
	 * This method performs the required business logic that may include state
	 * changes, etc.
	 * 
	 * @param value is of type ITrxValue
	 * @return the results of the operation
	 * @throws TrxOperationException on errors executing the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICollateralTrxValue trxValue = getCollateralTrxValue(value);
		// trxValue = super.createActualCollateral (trxValue);
		// trxValue = super.createStagingCollateral (trxValue);
		trxValue = super.createTransaction(trxValue);

		return super.prepareResult(trxValue);
	}
}
