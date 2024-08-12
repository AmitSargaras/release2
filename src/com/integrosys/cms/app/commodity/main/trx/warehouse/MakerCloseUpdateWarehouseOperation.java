/**
 * Copyright of Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/MakerCloseUpdateWarehouseOperation.java,v 1.2 2005/01/13 08:34:01 whuang Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * @author $Author: whuang $
 * @version $Revision: 1.2 $
 * @since $Date: 2005/01/13 08:34:01 $ Tag: $Name: $
 */
public class MakerCloseUpdateWarehouseOperation extends AbstractWarehouseTrxOperation {
	/**
	 * Default constructor.
	 */
	public MakerCloseUpdateWarehouseOperation() {
	}

	/**
	 * Returns the Operation Name
	 * 
	 * @return String
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_UPDATE_CLOSE_COMMODITY_MAIN;
	}

	/**
	 * The following tasks are performed:
	 * 
	 * 1. create staging warehouse record 2. update Transaction record
	 * 
	 * @param value is of type ITrxValue
	 * @return transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         error updating the transaction
	 */
	public ITrxResult performProcess(ITrxValue value) throws TrxOperationException {
		try {
			IWarehouseTrxValue trxValue = super.getWarehouseTrxValue(value);
			// trxValue.setStagingWarehouse(trxValue.getWarehouse());
			// trxValue = super.createStagingWarehouse(trxValue);
			trxValue = super.updateWarehouseTransaction(trxValue);

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
