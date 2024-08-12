/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/trx/ReadCommodityDealByTrxIDOperation.java,v 1.6 2004/07/04 10:58:19 jhe Exp $
 */
package com.integrosys.cms.app.commodity.deal.trx;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.businfra.transaction.TrxOperationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.AccessorUtil;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealBusManagerFactory;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
import com.integrosys.cms.app.commodity.deal.bus.ICommodityDealBusManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.transaction.CMSTransactionDAOFactory;
import com.integrosys.cms.app.transaction.CMSTrxOperation;
import com.integrosys.cms.app.transaction.CMSTrxSearchCriteria;
import com.integrosys.cms.app.transaction.ICMSTrxValue;
import com.integrosys.cms.app.transaction.ITrxReadOperation;
import com.integrosys.cms.app.transaction.OBCMSTrxHistoryLog;
import com.integrosys.cms.app.transaction.OBCMSTrxRouteInfo;

/**
 * The operation is to read commodity deal.
 * 
 * @author $Author: jhe $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2004/07/04 10:58:19 $ Tag: $Name: $
 */
public class ReadCommodityDealByTrxIDOperation extends CMSTrxOperation implements ITrxReadOperation {
	/**
	 * Default Constructor
	 */
	public ReadCommodityDealByTrxIDOperation() {
		super();
	}

	/**
	 * Get the operation name of the current operation.
	 * 
	 * @return the operation name of the current operation
	 */
	public String getOperationName() {
		return ICMSConstant.ACTION_READ_DEAL_BY_TRXID;
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
			ICommodityDealTrxValue dealTrxValue = getCommodityDealTrxValue(val);
			ICMSTrxValue cmsTrxValue = getTrxManager().getTransaction(dealTrxValue.getTransactionID());
			AccessorUtil.copyValue(cmsTrxValue, dealTrxValue);

			String stagingRef = dealTrxValue.getStagingReferenceID();
			String actualRef = dealTrxValue.getReferenceID();

			DefaultLogger.debug(this, "Actual Reference: " + actualRef + " , Staging Reference: " + stagingRef);

			if (stagingRef != null) {
				ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getStagingCommodityDealBusManager();
				ICommodityDeal deal = mgr.getCommodityDeal(Long.parseLong(stagingRef));
				dealTrxValue.setStagingCommodityDeal(deal);
			}

			if (actualRef != null) {
				ICommodityDealBusManager mgr = CommodityDealBusManagerFactory.getActualCommodityDealBusManager();
				ICommodityDeal deal = mgr.getCommodityDeal(Long.parseLong(actualRef));
				dealTrxValue.setCommodityDeal(deal);
			}

			if (dealTrxValue.isIncludeHistory()) {
				OBCMSTrxHistoryLog[] logs = (OBCMSTrxHistoryLog[]) CMSTransactionDAOFactory.getDAO()
						.getTransactionLogs(val.getTransactionID()).toArray(new OBCMSTrxHistoryLog[0]);
				dealTrxValue.setTransactionHistory(logs);

				CMSTrxSearchCriteria criteria = new CMSTrxSearchCriteria();
				criteria.setTransactionID((Long.valueOf(val.getTransactionID())).longValue());
				OBCMSTrxRouteInfo[] routeList = (OBCMSTrxRouteInfo[]) CMSTransactionDAOFactory.getDAO()
						.searchNextRouteList(criteria).toArray(new OBCMSTrxRouteInfo[0]);
				dealTrxValue.setNextRouteList(routeList);

			}

			return dealTrxValue;
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new TrxOperationException(e);
		}
	}

	/**
	 * Helper method to cast a generic trx value object to a commodity deal
	 * specific transaction value object.
	 * 
	 * @param trxValue transaction value
	 * @return commodity deal specific transaction value
	 * @throws TrxOperationException if the trxValue is not of type
	 *         ICommodityDealTrxValue
	 */
	private ICommodityDealTrxValue getCommodityDealTrxValue(ITrxValue trxValue) throws TrxOperationException {
		try {
			return (ICommodityDealTrxValue) trxValue;
		}
		catch (ClassCastException e) {
			throw new TrxOperationException("ITrxValue is not of type ICommodityDealTrxValue: " + e.toString());
		}
	}
}