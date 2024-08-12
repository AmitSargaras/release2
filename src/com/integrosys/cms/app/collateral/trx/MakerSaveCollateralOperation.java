/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/trx/MakerSaveCollateralOperation.java,v 1.1 2003/07/07 11:48:55 lyng Exp $
 */
package com.integrosys.cms.app.collateral.trx;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to save a collateral without
 * submitting it.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2003/07/07 11:48:55 $ Tag: $Name: $
 */
public class MakerSaveCollateralOperation extends AbstractCollateralTrxOperation {

	private static final long serialVersionUID = -5417519941817091234L;

	/**
	 * Default constructor.
	 */
	public MakerSaveCollateralOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_COL;
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

		if (StringUtils.isNotBlank(value.getTransactionID())) {
			trxValue = super.updateTransaction(trxValue);
		}
		else {
			trxValue = super.createTransaction(trxValue);
		}

		return super.prepareResult(trxValue);
	}
}
