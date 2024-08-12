/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/CheckerRejectWarehouseOperation.java,v 1.4 2004/08/17 06:52:48 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * This operation allows a checker to reject a checklist transaction
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/17 06:52:48 $ Tag: $Name: $
 */
public abstract class CheckerRejectWarehouseOperation extends AbstractWarehouseTrxOperation {
	/**
	 * Defaulc Constructor
	 */
	public CheckerRejectWarehouseOperation() {
		super();
	}

	/**
	 * Process the transaction 1. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		DefaultLogger.debug(this, "$$$Operation : 1 trxValue=" + anITrxValue);

		IWarehouseTrxValue trxValue = super.getWarehouseTrxValue(anITrxValue);

		trxValue = super.updateWarehouseTransaction(trxValue);

		return super.prepareResult(trxValue);
	}

}