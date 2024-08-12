/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/CheckerApproveUpdateCollateralOperation.java,v 1.6 2006/08/11 03:06:31 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by a checker to verify the collateral updated
 * by a maker.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/08/11 03:06:31 $ Tag: $Name: $
 */
public class CheckerApproveDeleteCollateralOperation extends AbstractCollateralTrxOperation {

	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_APPROVE_DELETE_COL;
	}

	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			ICollateralTrxValue trxValue = super.getCollateralTrxValue(value);
			ICollateral col = trxValue.getStagingCollateral();
			if (col != null) {
				col.setStatus(ICMSConstant.STATE_DELETED);
			}

			col = trxValue.getCollateral();
			if (col != null) {
				col.setStatus(ICMSConstant.STATE_DELETED);
			}
			trxValue = super.createStagingCollateral(trxValue);
			trxValue = super.updateActualCollateral(trxValue);
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
