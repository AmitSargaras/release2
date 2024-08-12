/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/MakerUpdateCollateralOperation.java,v 1.1 2003/07/04 03:43:24 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to update a collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/04 03:43:24 $ Tag: $Name: $
 */
public class MakerUpdateCollateralOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = -7575841217978439755L;

	/**
	 * Default constructor.
	 */
	public MakerUpdateCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_COL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging collateral record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICollateralTrxValue trxValue = super.getCollateralTrxValue(value);

		trxValue = super.createStagingCollateral(trxValue);
		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);

	}
}
