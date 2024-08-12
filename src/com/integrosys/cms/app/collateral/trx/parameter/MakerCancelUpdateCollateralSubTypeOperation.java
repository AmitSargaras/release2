/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/MakerCancelUpdateCollateralSubTypeOperation.java,v 1.1 2003/08/14 13:56:58 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx.parameter;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to cancel draft updated by him/her,
 * or close securit subtypes rejected by a checker.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/08/14 13:56:58 $ Tag: $Name: $
 */
public class MakerCancelUpdateCollateralSubTypeOperation extends AbstractCollateralSubTypeTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCancelUpdateCollateralSubTypeOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CANCEL_SUBTYPE;
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
			ICollateralSubTypeTrxValue trxValue = super.getCollateralSubTypeTrxValue(value);

			trxValue.setStagingCollateralSubTypes(trxValue.getCollateralSubTypes());
			trxValue = super.createStagingCollateralSubTypes(trxValue);
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
