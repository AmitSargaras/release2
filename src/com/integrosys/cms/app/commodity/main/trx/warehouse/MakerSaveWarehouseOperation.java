/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/MakerSaveWarehouseOperation.java,v 1.1 2004/08/13 07:31:29 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation class is invoked by maker to save title document type.
 * 
 * @author $Author: wltan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2004/08/13 07:31:29 $ Tag: $Name: $
 */
public class MakerSaveWarehouseOperation extends AbstractWarehouseTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerSaveWarehouseOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_SAVE_COMMODITY_MAIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging warehouse record 2. create/update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws TrxOperationException on error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IWarehouseTrxValue trxValue = super.getWarehouseTrxValue(value);

			trxValue = super.createStagingWarehouse(trxValue);

			if (trxValue.getTransactionID() == null) {
				trxValue = (IWarehouseTrxValue) super.createTransaction(trxValue);
			}
			else {
				trxValue = (IWarehouseTrxValue) super.updateWarehouseTransaction(trxValue);
			}

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
