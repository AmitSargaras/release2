/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/CheckerApproveUpdateWarehouseOperation.java,v 1.3 2004/07/15 04:02:54 cchen Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve the checklist updating
 * 
 * @author $Author: cchen $
 * @version $Revision: 1.3 $
 * @since $Date: 2004/07/15 04:02:54 $ Tag: $Name: $
 */
public class CheckerApproveUpdateWarehouseOperation extends AbstractWarehouseTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerApproveUpdateWarehouseOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_UPDATE_APPROVE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Update the actual data 2. Update the
	 * transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "$$$Debug::: trxValue");
		IWarehouseTrxValue trxValue = getWarehouseTrxValue(anITrxValue);
		trxValue = updateActualWarehouse(trxValue);
		trxValue = super.updateWarehouseTransaction(trxValue);
		return super.prepareResult(trxValue);
	}
}