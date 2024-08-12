/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CheckerRejectUpdateCollateralOperation.java,v 1.4 2005/07/15 10:42:03 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to reject the collateral updated
 * by a maker.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/07/15 10:42:03 $ Tag: $Name: $
 */
public class CheckerRejectUpdateCollateralOperation extends AbstractCollateralTrxOperation {
	/**
	 * Default constructor.
	 */
	public CheckerRejectUpdateCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_REJECT_UPDATE_COL;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. update transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICollateralTrxValue trxValue = super.getCollateralTrxValue(value);

		if (ICMSConstant.SECURITY_TYPE_COMMODITY.equals(trxValue.getStagingCollateral().getCollateralType()
				.getTypeCode())) {
			trxValue = super.createStagingCollateral(trxValue);
		}
		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);
	}
}
