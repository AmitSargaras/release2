/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/CheckerApproveCreateWarehouseOperation.java,v 1.4 2004/08/17 06:52:48 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

//ofa
import com.integrosys.base.businfra.transaction.ITrxResult;
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * This operation allows a checker to approve a checklist create
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.4 $
 * @since $Date: 2004/08/17 06:52:48 $ Tag: $Name: $
 */
public class CheckerApproveCreateWarehouseOperation extends AbstractWarehouseTrxOperation {
	/**
	 * Default Constructor
	 */
	public CheckerApproveCreateWarehouseOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * 
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_CHECKER_CREATE_APPROVE_COMMODITY_MAIN;
	}

	/**
	 * Process the transaction 1. Create the actual data 2. Update the staging
	 * data with the actual data 3. Update the transaction record
	 * @param anITrxValue - ITrxValue
	 * @return ITrxResult - the transaction result
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException if
	 *         encounters any error during the processing of the transaction
	 */
	public ITrxResult performProcess(ITrxValue anITrxValue) throws TrxOperationException {
		IWarehouseTrxValue trxValue = getWarehouseTrxValue(anITrxValue);
		trxValue = createActualWarehouse(trxValue);
		trxValue = updateWarehouseTransaction(trxValue);
		return super.prepareResult(trxValue);
	}

	/**
	 * Create the actual document item
	 * @param anIWarehouseTrxValue - ITrxValue
	 * @return IWarehouseTrxValue - the document item trx value
	 * @throws com.integrosys.base.businfra.transaction.TrxOperationException on
	 *         errors
	 */
	private IWarehouseTrxValue createActualWarehouse(IWarehouseTrxValue anIWarehouseTrxValue)
			throws TrxOperationException {
		try {
			// DefaultLogger.debug(this,
			// "$$$Debug: x1  staging.getRefID="+anIWarehouseTrxValue
			// .getStagingWarehouse()[0].getGroupID());
			IWarehouse[] staging = anIWarehouseTrxValue.getStagingWarehouse();

			IWarehouse[] actualWarehouse = (IWarehouse[]) getBusManager().createInfo(staging);

			anIWarehouseTrxValue.setWarehouse(actualWarehouse);
			anIWarehouseTrxValue.setReferenceID(String.valueOf(actualWarehouse[0].getGroupID()));

			anIWarehouseTrxValue.setStagingReferenceID(String.valueOf(actualWarehouse[0].getGroupID()));

			return anIWarehouseTrxValue;

		}
		catch (CommodityException cex) {
			throw new TrxOperationException(cex);
		}
	}
}