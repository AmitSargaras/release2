/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/SubLimitUtils.java,v 1.7 2005/10/25 05:51:46 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.cms.app.collateral.bus.LimitDetailsComparator;
import com.integrosys.cms.app.commodity.deal.bus.CommodityDealException;
import com.integrosys.cms.app.commodity.deal.proxy.CommodityDealProxyFactory;
import com.integrosys.cms.app.commodity.main.CommodityException;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType;
import com.integrosys.cms.app.commodity.main.bus.sublimittype.SubLimitTypeComparator;
import com.integrosys.cms.app.commodity.main.proxy.CommodityMaintenanceProxyFactory;
import com.integrosys.cms.app.commodity.main.proxy.ICommodityMaintenanceProxy;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.ui.common.ForexHelper;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-29
 * @Tag 
 *      com.integrosys.cms.ui.collateral.commodity.sublimit.SubLimitPageUtils.java
 */
public class SubLimitUtils {
	public static HashMap getSLTMap() {
		HashMap sltMap = new HashMap();
		ISubLimitType[] typeArray = getAllSLT();
		if (typeArray != null) {
			for (int index = 0; index < typeArray.length; index++) {
				sltMap.put(String.valueOf(typeArray[index].getSubLimitTypeID()), typeArray[index]);
			}
		}
		return sltMap;
	}

	public static ISubLimitType[] getAllSLT() {
		ICommodityMaintenanceProxy proxy = CommodityMaintenanceProxyFactory.getProxy();
		try {
			return proxy.getAllSubLimitTypes();
		}
		catch (CommodityException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List getALLSLTList() {
		List sltList = null;
		ISubLimitType[] sltArray = getAllSLT();
		if (sltArray != null) {
			SubLimitTypeComparator c = new SubLimitTypeComparator(SubLimitTypeComparator.BY_SUB_LIMIT_TYPE);
			Arrays.sort(sltArray, c);
			sltList = Arrays.asList(sltArray);
		}
		return sltList;
	}

	public static boolean isInnerLimit(ILimit limit) {
		if (limit == null) {
			return false;
		}
		return ((limit.getOuterLimitRef() != null) && (limit.getOuterLimitRef().length() > 0) && !limit
				.getOuterLimitRef().equals("0"));
	}

	public static boolean hasSLRelatedDeal(long subLimitId) throws CommodityDealException {
		return CommodityDealProxyFactory.getProxy().hasSLRelatedDeal(subLimitId);
	}

	public static Collection getSortedLimitDetails(HashMap limitMap) {
		if ((limitMap == null) || limitMap.isEmpty()) {
			return new ArrayList();
		}
		Collection valueSet = limitMap.values();
		Object[] tempArr = valueSet.toArray();
		Arrays.sort(tempArr, new LimitDetailsComparator());
		valueSet = Arrays.asList(tempArr);
		return valueSet;
	}

	public static String getConvertedSLAmt(String amt, String amtCCy, CurrencyCode ccy) {
		try {
			double slAmt = Double.parseDouble(amt);
			Amount slAmount = new Amount(slAmt, amtCCy);
			slAmt = ForexHelper.getInstance().convertAmount(slAmount, ccy);
			return formateSLAmt(slAmt);
		}
		catch (Exception e) {
			return SLUIConstants.ERR_FOREX;
		}
	}

	public static String formateSLAmt(double amt) {
		String formatedStr = "";
		try {
			DecimalFormat df = new DecimalFormat("##################");
			formatedStr = df.format(amt);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return formatedStr;
	}
}
