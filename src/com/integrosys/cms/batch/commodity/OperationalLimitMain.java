///*
// * Copyright Integro Technologies Pte Ltd
// * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/commodity/OperationalLimitMain.java,v 1.6 2006/05/16 07:59:02 hmbao Exp $
// */
//package com.integrosys.cms.batch.commodity;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//
//import javax.ejb.SessionContext;
//
//import com.integrosys.base.businfra.currency.Amount;
//import com.integrosys.base.techinfra.logger.DefaultLogger;
//import com.integrosys.cms.app.commodity.common.AmountConversion;
//import com.integrosys.cms.app.commodity.deal.bus.ICommodityDeal;
//import com.integrosys.cms.app.eventmonitor.AbstractMonitorAdapter;
//import com.integrosys.cms.app.eventmonitor.EventMonitorException;
//import com.integrosys.cms.app.limit.proxy.ILimitProxy;
//import com.integrosys.cms.app.limit.proxy.LimitProxyFactory;
//import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
//import com.integrosys.cms.batch.common.StartupInit;
//
///**
// * A batch program to perform manual update of operational limit.
// *
// * @author $Author: hmbao $<br>
// * @version $Revision: 1.6 $
// * @since $Date: 2006/05/16 07:59:02 $ Tag: $Name:  $
// */
//public class OperationalLimitMain extends AbstractMonitorAdapter {
//	// public static void main(String[] args) throws Exception {
//	// OperationalLimitMain main = new OperationalLimitMain();
//	// main.doWork();
//	// }
//
//	public void start(String[] params, SessionContext context)
//			throws EventMonitorException {
//		DefaultLogger.debug(this, "- Start Job -");
//		try {
//			doWork(context);
//		} catch (Exception e) {
//			DefaultLogger.debug(this, e);
//			throw new EventMonitorException(e);
//		}
//	}
//
//	/**
//	 * Default Constructor
//	 */
//	public OperationalLimitMain() {
//		StartupInit.init();
//	}
//
//	public void doWork(SessionContext context) throws Exception {
//		try {
//			OperationalLimitDAO dao = new OperationalLimitDAO();
//			HashMap lmtDealMap = dao.getLimitDealMap();
//
//			int size = 0;
//			if ((size = lmtDealMap.size()) == 0) {
//				DefaultLogger.info(this,
//						"No limits tied to deals. Program ends!");
//				return;
//			}
//
//			DefaultLogger.info(this, "Total record to be processed:" + size);
//
//			ILimitProxy proxy = LimitProxyFactory.getProxy();
//
//			Iterator keys = lmtDealMap.keySet().iterator();
//			while (keys.hasNext()) {
//				Long limitID = (Long) keys.next();
//				DefaultLogger.info(this, "LimitID: " + limitID);
//				try {
//					ILimitTrxValue lmtTrx = proxy.getTrxLimit(limitID
//							.longValue());
//					ArrayList dealList = (ArrayList) lmtDealMap.get(limitID);
//					ICommodityDeal[] deals = (ICommodityDeal[]) dealList
//							.toArray(new ICommodityDeal[0]);
//					Amount newOP = getTotalDealBalanceAmt(deals, lmtTrx
//							.getLimit().getApprovedLimitAmount()
//							.getCurrencyCode());
//					newOP = roundAmount(newOP);
//					boolean isChanged = isAmtChanged(lmtTrx.getLimit()
//							.getOperationalLimit(), newOP);
//					if (!isChanged) {
//						DefaultLogger.info(this, "No update for the limit!!");
//						continue;
//					}
//					lmtTrx.getLimit().setOperationalLimit(newOP);
//					if (lmtTrx.getStagingLimit() != null) {
//						lmtTrx.getStagingLimit().setOperationalLimit(newOP);
//					}
//					proxy.systemUpdateLimit(lmtTrx);
//				} catch (Exception e) {
//					DefaultLogger.error(this, "limitID:" + limitID
//							+ " exception:" + e.toString());
//				}
//			}
//
//		} catch (Exception e) {
//			DefaultLogger.error(this, "Caught Exception!", e);
//			throw e;
//		}
//	}
//
//	/**
//	 * Helper method to calculate total balance amount.
//	 *
//	 * @param deals
//	 *            of type ICommodityDeal[]
//	 * @return Amount
//	 * @throws Exception
//	 */
//	private Amount getTotalDealBalanceAmt(ICommodityDeal[] deals, String lmtCcy)
//			throws Exception {
//		Amount totalAmt = null;
//		for (int i = 0; i < deals.length; i++) {
//
//			Amount bal = AmountConversion.getConversionAmount(deals[i]
//					.getBalanceDealAmt(), lmtCcy);
//			if (totalAmt == null) {
//				totalAmt = bal;
//			} else {
//				if (bal != null) {
//					totalAmt.addToThis(bal);
//				}
//			}
//		}
//		return totalAmt;
//	}
//
//	/**
//	 * Helper method to check if amount has been changed.
//	 *
//	 * @param oldAmt
//	 *            old amount value
//	 * @param newAmt
//	 *            new amount value
//	 * @return boolean
//	 */
//	private boolean isAmtChanged(Amount oldAmt, Amount newAmt) {
//		DefaultLogger.info(this, " old Amount:" + oldAmt + " new Amount:"
//				+ newAmt);
//		if (oldAmt == null && newAmt == null)
//			return false;
//
//		if (oldAmt != null && newAmt != null && oldAmt.equals(newAmt))
//			return false;
//
//		return true;
//	}
//
//	/**
//	 * Helper method to round the amount value.
//	 *
//	 * @param amt
//	 *            of type Amount
//	 * @return rounded amount
//	 */
//	private Amount roundAmount(Amount amt) {
//		if (amt == null)
//			return amt;
//
//		BigDecimal bd = amt.getAmountAsBigDecimal();
//		bd = bd.setScale(0, BigDecimal.ROUND_HALF_UP);
//		return new Amount(bd, amt.getCurrencyCodeAsObject());
//	}
//}