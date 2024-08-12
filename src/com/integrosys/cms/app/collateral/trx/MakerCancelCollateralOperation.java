/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/MakerCancelCollateralOperation.java,v 1.3 2005/07/18 09:48:06 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.collateral.bus.type.commodity.ICommodityCollateral;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBCommodityCollateral;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to cancel draft of a collateral.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/07/18 09:48:06 $ Tag: $Name: $
 */
public class MakerCancelCollateralOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = -4642527133277944456L;

	/**
	 * Default constructor.
	 */
	public MakerCancelCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CANCEL_COL;
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

		if (trxValue.getCollateral() instanceof ICommodityCollateral) {
			((OBCommodityCollateral) trxValue.getCollateral()).setUpdatePreConditionOnly(true);
			trxValue = super.updateActualPreCondition(trxValue);
		}

		if (trxValue.getCollateral() != null) {
			trxValue.setStagingCollateral(trxValue.getCollateral());
			trxValue = super.createStagingCollateral(trxValue);
		}

		trxValue = super.updateTransaction(trxValue);

		return super.prepareResult(trxValue);

	}
}
