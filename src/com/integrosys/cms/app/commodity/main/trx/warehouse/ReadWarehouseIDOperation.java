/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/warehouse/ReadWarehouseIDOperation.java,v 1.3 2004/08/17 06:52:48 wltan Exp $
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
 * @version $Revision: 1.3 $
 * @since $Date: 2004/08/17 06:52:48 $ Tag: $Name: $
 */
public class ReadWarehouseIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadWarehouseIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation
	 * @return String - the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID;
	}

	/**
	 * This method is used to read a transaction object
	 * 
	 * @param val is the ITrxValue object containing the parameters required for
	 *        retrieving a record, such as the transaction ID.
	 * @return ITrxValue containing the requested data.
	 * @throws com.integrosys.base.businfra.transaction.TransactionException if
	 *         any other errors occur.
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue trxValue = super.getCMSTrxValue(val);
			DefaultLogger.debug(this, " &&&Debug:: getTrxManager().getTransaction() - trxValue.getReferenceID()='"
					+ trxValue.getReferenceID() + " , trxValue.getTransactionType()=" + trxValue.getTransactionType());
			trxValue = (ICMSTrxValue) getTrxManager().getTrxByRefIDAndTrxType(trxValue.getReferenceID(),
					trxValue.getTransactionType());
			OBWarehouseTrxValue newValue = new OBWarehouseTrxValue(trxValue);

			String stagingRef = trxValue.getStagingReferenceID();
			String actualRef = trxValue.getReferenceID();

			IWarehouse[] actualWarehouse = null;
			IWarehouse[] stagingWarehouse = null;
			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (null != actualRef) {
				actualWarehouse = (IWarehouse[]) getCommodityMainInfoManager().getCommodityMainInfosByGroupID(
						(actualRef), ICommodityMainInfo.INFO_TYPE_WAREHOUSE);
			}
			if (null != stagingRef) {
				stagingWarehouse = (IWarehouse[]) getStagingCommodityMainInfoManager().getCommodityMainInfosByGroupID(
						(stagingRef), ICommodityMainInfo.INFO_TYPE_WAREHOUSE);
			}

			newValue.setStagingWarehouse(stagingWarehouse);
			newValue.setWarehouse(actualWarehouse);
			return newValue;
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw new TrxOperationException(ex);
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