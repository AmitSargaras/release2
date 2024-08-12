/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: $
 */
package com.integrosys.cms.app.collateral.trx.assetlife;

import org.apache.commons.lang.Validate;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.ICMSTrxValue;

/**
 * The operation is to read collateral assetlife.
 * 
 * @author $Author: pctan $<br>
 * @version $Revision: $
 * @since $Date: $ Tag: $Name: $
 */
public class ReadCollateralAssetLifeByTrxIDOperation extends AbstractReadCollateralAssetLifeOperation {
	/**
	 * Default Constructor
	 */
	public ReadCollateralAssetLifeByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_ASSETLIFE_BY_TRXID;
	}

	public ICMSTrxValue doInTrxManager(ICMSTrxValue trxValue) throws TransactionException {
		Validate.notNull(trxValue, "'trxValue' must not be null");

		try {
			return getTrxManager().getTransaction(trxValue.getTransactionID());
		}
		catch (Throwable t) {
			throw new TransactionException("not able to retrieve transaction using transaction id ["
					+ trxValue.getTransactionID() + "]", t);
		}
	}
}