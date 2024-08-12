/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/MakerSaveCollateralOperation.java,v 1.1 2003/07/07 11:48:55 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to save a collateral without
 * submitting it.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/07 11:48:55 $ Tag: $Name: $
 */
public class MakerDeleteCollateralOperation extends AbstractCollateralTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerDeleteCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_DELETE_COL;
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
		try {
			ICollateralTrxValue trxValue = super.getCollateralTrxValue(value);

			ICollateral col = trxValue.getStagingCollateral();
			if (col != null) {
				col.setStatus(ICMSConstant.STATE_PENDING_DELETE);
			}
			// col= trxValue.getCollateral();
			// if(col!=null){
			// col.setStatus(ICMSConstant.STATE_PENDING_DELETE);
			// }
			trxValue = super.createStagingCollateral(trxValue);
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
