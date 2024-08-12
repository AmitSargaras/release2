/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/MakerCreateCommodityPriceOperation.java,v 1.3 2004/06/14 08:36:33 lyng Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.price;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to add new commodity price.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/14 08:36:33 $ Tag: $Name: $
 */
public class MakerCreateCommodityPriceOperation extends AbstractCommodityPriceTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCreateCommodityPriceOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_CREATE_COMMODITY_MAIN;
	}

	/**
	 * This method performs the required business logic that may include state
	 * changes, etc. The following tasks are performed:
	 * 
	 * 1. create staging price record 2. create transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return the results of the operation
	 * @throws TrxOperationException on errors executing the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		ICommodityPriceTrxValue trxValue = getCommodityPriceTrxValue(value);
		trxValue = super.createStagingCommodityPrice(trxValue);

		if (value.getTransactionID() == null) {
			trxValue = super.createTransaction(trxValue);
		}
		else {
			trxValue = super.updateTransaction(trxValue);
		}
		return super.prepareResult(trxValue);
	}
}
