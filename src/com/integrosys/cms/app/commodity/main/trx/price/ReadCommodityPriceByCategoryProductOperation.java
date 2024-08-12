/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/main/trx/price/ReadCommodityPriceByCategoryProductOperation.java,v 1.4 2006/10/12 03:15:00 hmbao Exp $
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
 * Operation to read commodity prices by category and product type code.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2006/10/12 03:15:00 $ Tag: $Name: $
 */
public class ReadCommodityPriceByCategoryProductOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCommodityPriceByCategoryProductOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_COMMODITY_MAIN_CAT_PROD;
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
			ICMSTrxValue cmsTrxValue = super.getCMSTrxValue(val);

			ICommodityPriceTrxValue trxVal = (ICommodityPriceTrxValue) val;

			String categoryCode = trxVal.getCommodityCategoryCode();
			String prodTypeCode = trxVal.getCommodityProdTypeCode();
			String ricType = trxVal.getCommodityRICType();

			DefaultLogger.debug(this, "Category Code: " + categoryCode + ", Product Type Code: " + prodTypeCode);
			DefaultLogger.debug(this, "TrxType : " + trxVal.getTransactionType());
			// get actual commodity prices
			ICommodityMainInfoManager mgr = CommodityMainInfoManagerFactory.getManager();
			ICommodityPrice[] actualPrices = mgr.getCommodityPrice(categoryCode, prodTypeCode, ricType);

			ICommodityPrice[] stagePrices = null;
			ICommodityMainInfoManager stageMgr = CommodityMainInfoManagerFactory.getStagingManager();

			long actualRefID = getGroupID(actualPrices);
			// long stagingRefID = ICMSConstant.LONG_INVALID_VALUE;

			if (actualRefID != ICMSConstant.LONG_INVALID_VALUE) {
				cmsTrxValue = getTrxManager().getTrxByRefIDAndTrxType(String.valueOf(actualRefID),
						val.getTransactionType());
				stagePrices = (ICommodityPrice[]) stageMgr.getCommodityMainInfosByGroupID(cmsTrxValue
						.getStagingReferenceID(), ICommodityMainInfo.INFO_TYPE_PRICE);
			}
			else {
				cmsTrxValue = new CommodityPriceTrxDAO().getCommodityPriceTrxValue(actualPrices, trxVal
						.getTransactionType(), true);
				// stagingRefID = stageMgr.getCommodityPriceGroupID
				// (actualPrices);
				// if (actualPrices != null && actualPrices.length != 0 &&
				// stagingRefID != ICMSConstant.LONG_INVALID_VALUE)
				// cmsTrxValue.setStatus (ICMSConstant.STATE_PENDING_UPDATE);
			}

			OBCommodityPriceTrxValue priceTrx = new OBCommodityPriceTrxValue(cmsTrxValue);
			priceTrx.setCommodityCategoryCode(categoryCode);
			priceTrx.setCommodityProdTypeCode(prodTypeCode);
			priceTrx.setCommodityRICType(ricType);

			priceTrx.setCommodityPrice(actualPrices);
			if ((stagePrices == null) || (stagePrices.length == 0)) {
				stagePrices = actualPrices;
			}

			priceTrx.setStagingCommodityPrice(stagePrices);

			return priceTrx;
		}
		catch (Exception e) {
			throw new TrxOperationException(e);
		}
	}

	/**
	 * Helper method to get groupID of commodity price list.
	 * 
	 * @param prices a list of commodity prices
	 * @return group id
	 */
	private long getGroupID(ICommodityPrice[] prices) {
		if (prices != null) {
			for (int i = 0; i < prices.length; i++) {
				if (prices[i].getGroupID() != ICMSConstant.LONG_INVALID_VALUE) {
					return prices[i].getGroupID();
				}
			}
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}
}
