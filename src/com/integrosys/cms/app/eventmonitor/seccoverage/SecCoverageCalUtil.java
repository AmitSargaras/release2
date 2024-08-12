/*
 *
 * @author $Author: hmbao $
 * @version $Revision: 1.4 $
 * @since $Date: 2006/10/27 02:57:35 $
 * Tag: $Name:  $
 */
package com.integrosys.cms.app.eventmonitor.seccoverage;

import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.forex.SBForexManager;
import com.integrosys.base.businfra.forex.SBForexManagerHome;
import com.integrosys.base.techinfra.beanloader.BeanController;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSJNDIConstant;
import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class SecCoverageCalUtil {
	public static final String baseCurrency = CommonUtil.getBaseCurrency();

	public static double calculateActivedAmount(OBActivateLimitInfo outerLimitModel) {
		DefaultLogger.debug("SecCoverageCalUtil", "INSIDE CALCULATE ACTIVATED AMOUNT");
		try {
			double outerActivatedLimit = 0;
			double innerActivatedLimit = 0;
			double outerApprovedLimit = 0;
			double innerApprovedLimit = 0;

			boolean hasActivatedOuter = false;
			String outerLimitCurrency = outerLimitModel.getLimitCurrency();
			String outerActivatedStr = outerLimitModel.getActivatedLimitAmount();
			String outerOperationStr = outerLimitModel.getOperationalAmount();
			if (outerLimitModel.getHasCommondity()) {
				if (outerOperationStr != null) {
					outerActivatedLimit = convertToBase(outerOperationStr, outerLimitCurrency);
					hasActivatedOuter = true;
				}
			}
			else {
				if (outerActivatedStr != null) {
					outerActivatedLimit = convertToBase(outerActivatedStr, outerLimitCurrency);
					hasActivatedOuter = true;
				}
			}
			String outerApprovedStr = outerLimitModel.getApprovedLimitAmount();
			if (outerApprovedStr != null) {
				outerApprovedLimit = convertToBase(outerApprovedStr, outerLimitCurrency);
			}

			// DefaultLogger.debug("SecCoverageCalUtil",
			// "Outer activated limit for " + outerLimitModel.getLimitID() +
			// " in USD is: " + outerActivatedLimit);
			// DefaultLogger.debug("SecCoverageCalUtil",
			// "Outer approved limit for " + outerLimitModel.getLimitID() +
			// " in USD is: " + outerApprovedLimit);
			// DefaultLogger.debug("SecCoverageCalUtil", "Has activated outer: "
			// + hasActivatedOuter);

			List innerLimitList = outerLimitModel.getInnerLimitList();
			for (int i = 0; i < innerLimitList.size(); i++) {
				OBActivateLimitInfo innerLimitModel = (OBActivateLimitInfo) (innerLimitList.get(i));
				String innerLimitCurrency = innerLimitModel.getLimitCurrency();
				String innerActivatedStr = innerLimitModel.getActivatedLimitAmount();
				String innerOperationStr = innerLimitModel.getOperationalAmount();
				if (innerLimitModel.getHasCommondity()) {
					if (innerOperationStr != null) {
						double innerActivatedInc = convertToBase(innerOperationStr, innerLimitCurrency);
						innerActivatedLimit = innerActivatedLimit + innerActivatedInc;
					}
				}
				else {
					if (innerActivatedStr != null) {
						double innerActivatedInc = convertToBase(innerActivatedStr, innerLimitCurrency);
						// DefaultLogger.debug("SecCoverageCalUtil",
						// "inner activated limit for " +
						// innerLimitModel.getLimitID() + " in USD is: " +
						// innerActivatedInc);
						innerActivatedLimit = innerActivatedLimit + innerActivatedInc;
					}
				}
				String innerApprovedStr = innerLimitModel.getApprovedLimitAmount();
				if (innerApprovedStr != null) {
					double innerApprovedInc = convertToBase(outerApprovedStr, outerLimitCurrency);
					// DefaultLogger.debug("SecCoverageCalUtil",
					// "inner approved limit for " +
					// innerLimitModel.getLimitID() + " in USD is: " +
					// innerApprovedInc);
					innerApprovedLimit = innerApprovedLimit + innerApprovedInc;
				}
			}

			// DefaultLogger.debug("SecCoverageCalUtil",
			// "total inner activated limit for " + outerLimitModel.getLimitID()
			// + " in USD is: " + innerActivatedLimit);
			// DefaultLogger.debug("SecCoverageCalUtil",
			// "total inner approved limit for " + outerLimitModel.getLimitID()
			// + " in USD is: " + innerApprovedLimit);

			// DefaultLogger.debug("SecCoverageCalUtil",
			// "Check rule 1: outer limit is activated? ");
			double result = 0;
			if (hasActivatedOuter) {
				// DefaultLogger.debug("SecCoverageCalUtil",
				// "Check rule 2: sum of outer activated limit > sum of inner activated limit? "
				// );
				if (outerActivatedLimit > innerActivatedLimit) {
					// DefaultLogger.debug("SecCoverageCalUtil",
					// "Apply sum of the outer limit");
					result = outerActivatedLimit;
				}
				else {
					// DefaultLogger.debug("SecCoverageCalUtil",
					// "Check rule 4: sum of inner activated limit > sum of outer approved limit? "
					// );
					if (innerActivatedLimit > outerApprovedLimit) {
						// DefaultLogger.debug("SecCoverageCalUtil",
						// "Apply sum of the outer approved limit");
						result = outerApprovedLimit;
					}
					else {
						// DefaultLogger.debug("SecCoverageCalUtil",
						// "Apply the larger of the inner activated limit or outer activated limit"
						// );
						result = Math.max(innerActivatedLimit, outerActivatedLimit);
					}
				}
			}
			else {
				// DefaultLogger.debug("SecCoverageCalUtil",
				// "Check rule 3: sum of outer approved limit > sum of inner activated limit? "
				// );
				if (outerApprovedLimit > innerActivatedLimit) {
					// DefaultLogger.debug("SecCoverageCalUtil",
					// "Apply sum of the inner activated limit");
					result = innerActivatedLimit;
				}
				else {
					// DefaultLogger.debug("SecCoverageCalUtil",
					// "Apply sum of the outer approved limit");
					result = outerApprovedLimit;
				}
			}

			DefaultLogger.debug("SecCoverageCalUtil", "final result for limit: " + outerLimitModel.getLimitID()
					+ "SCI_LMT_ID: " + outerLimitModel.getSciLmtID() + " is: " + result);
			return result;

		}
		catch (Exception ex) {
			DefaultLogger.error("SecCoverageCalUtil", "Error in method calculateActivedAmount " + ex.getMessage());
		}
		return 0;
	}

	public static double convertToBase(String amtStr, String currencyCode) throws Exception {
		if ((amtStr == null) || (currencyCode == null) || amtStr.equals("")) {
			throw new Exception("Not a valid amount");
		}
		double amtVal = Double.parseDouble(amtStr);
		if (currencyCode.equals(baseCurrency)) {
			return amtVal;
		}
		else {
			Amount amt = new Amount(amtVal, currencyCode);
			SBForexManager fx = getSBForexManager();
			Amount result = fx.convert(amt, new CurrencyCode(baseCurrency));
			if (null == result) {
				throw new Exception("Forex conversion returns null, for FROM Currency: " + amt.getCurrencyCode()
						+ " and TO Currency: " + baseCurrency);
			}
			else {
				return result.getAmount();
			}
		}
	}

	private static SBForexManager getSBForexManager() throws Exception {
		SBForexManager mgr = (SBForexManager) BeanController.getEJB(ICMSJNDIConstant.SB_FOREX_MANAGER_JNDI,
				SBForexManagerHome.class.getName());
		if (mgr == null) {
			throw new Exception("SBForexManager is null!");
		}
		return mgr;
	}
}
