/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/ReadCommodityPriceByTrxIDOperation.java,v 1.2 2004/06/04 04:53:53 hltan Exp $
 */
package com.integrosys.cms.app.commodity.main.trx.price;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.commodity.main.bus.CommodityMainInfoManagerFactory;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfo;
import com.integrosys.cms.app.commodity.main.bus.ICommodityMainInfoManager;
import com.integrosys.cms.app.commodity.main.bus.price.ICommodityPrice;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;

/**
 * The operation is to read commodity prices by its transaction id.
 * 
 * @author $Author: hltan $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/06/04 04:53:53 $ Tag: $Name: $
 */
public class ReadCommodityPriceByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCommodityPriceByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_TRXID;
	}

	/**
	 * This method is used to read a transaction object.
	 * 
	 * @param val transaction value required for retrieving transaction record
	 * @return transaction value
	 * @throws TransactionException on errors retrieving the transaction value
	 */
	public ITrxValue getTransaction(ITrxValue val) throws TransactionException {
		try {
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(val.getTransactionID());
			OBCommodityPriceTrxValue priceTrxValue = new OBCommodityPriceTrxValue(cmsTrxValue);

			String stagingRef = cmsTrxValue.getStagingReferenceID();
			String actualRef = cmsTrxValue.getReferenceID();
			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getStagingManager();
				ICommodityPrice[] prices = (ICommodityPrice[]) mgr.getCommodityMainInfosByGroupID(stagingRef,
						ICommodityMainInfo.INFO_TYPE_PRICE);
				priceTrxValue.setStagingCommodityPrice(prices);

				if ((prices != null) && (prices.length != 0) && (prices[0] != null)) {
					priceTrxValue.setCommodityCategoryCode(prices[0].getCommodityProfile().getCategory());
					priceTrxValue.setCommodityProdTypeCode(prices[0].getCommodityProfile().getProductType());
				}
			}

			if (actualRef != null) {
				ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
				ICommodityPrice[] prices = (ICommodityPrice[]) mgr.getCommodityMainInfosByGroupID(actualRef,
						ICommodityMainInfo.INFO_TYPE_PRICE);
				priceTrxValue.setCommodityPrice(prices);
			}

			return priceTrxValue;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}
}