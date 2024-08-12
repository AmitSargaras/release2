/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/ReadWarehouseTrxIDOperation.java,v 1.5 2005/04/27 08:17:05 wltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.warehouse;

//ofa
import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.warehouse.IWarehouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * This operation is responsible for reading a titledocument trx based on a ID
 * 
 * @author $Author: wltan $
 * @version $Revision: 1.5 $
 * @since $Date: 2005/04/27 08:17:05 $ Tag: $Name: $
 */
public class ReadWarehouseTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadWarehouseTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID;
	}

	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());
			OBWarehouseTrxValue paramTrxValue = new OBWarehouseTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			String transactionID = cmsTrxValue.getTransactionID();

			DefaultLogger.debug(this, "Transaction id : " + transactionID + ", Actual Reference: " + actualRef
					+ " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getStagingManager();
				IWarehouse[] whs = (IWarehouse[]) mgr.getCommodityMainInfosByGroupID(stagingRef,
						ICommodityMainInfo.INFO_TYPE_WAREHOUSE);
				paramTrxValue.setStagingWarehouse(whs);
			}
			if (actualRef != null) {
				ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
				IWarehouse[] whs = (IWarehouse[]) mgr.getCommodityMainInfosByGroupID(actualRef,
						ICommodityMainInfo.INFO_TYPE_WAREHOUSE);
				paramTrxValue.setWarehouse(whs);
			}
			return paramTrxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the staging
	 * customer data
	 * @return SBCommodityMainInfoManager - the home interface for the staging
	 *         titledocument session bean
	 */
	private ICommodityMainInfoManager getStagingCommodityMainInfoManager() throws TransactionException {
		try {
			return CommodityMainInfoManagerFactory.getStagingManager();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException(e);
		}
	}

	/**
	 * Get the home interface for the Document Item Session Bean of the actual
	 * customer data
	 * @return SBCommodityMainInfoManager - the home interface for the
	 *         titledocument session bean
	 */
	private ICommodityMainInfoManager getCommodityMainInfoManager() throws TransactionException {
		try {
			return CommodityMainInfoManagerFactory.getManager();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TransactionException(e);
		}
	}
}