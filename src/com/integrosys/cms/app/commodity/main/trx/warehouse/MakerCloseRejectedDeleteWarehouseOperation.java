/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/MakerCloseRejectedDeleteWarehouseOperation.java,v 1.2 2004/06/04 04:54:31 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a maker to close a rejected commodity transaction
 * 
 * @author $Author: hltan $
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:54:31 $ Tag: $Name: $
 */
public class MakerCloseRejectedDeleteWarehouseOperation extends AbstractWarehouseTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public MakerCloseRejectedDeleteWarehouseOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_MAKER_DELETE_CLOSE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IWarehouseTrxValue trxValue = getWarehouseTrxValue(anITrxValue);
		// trxValue.setStagingWarehouse(trxValue.getWarehouse());
		// trxValue = createStagingWarehouse(trxValue);
		trxValue = updateWarehouseTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

}