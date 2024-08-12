package com.integrosys.cms.ui.commoditydeal.dealinfo;

import com.integrosys.cms.app.commodity.deal.bus.PriceType;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-3-15
 * @Tag com.integrosys.cms.ui.commoditydeal.dealinfo.DealInfoUtil.java
 */
public class DealInfoUtil {
	public boolean isActualPriceRequired(PriceType contractPriceType) {
		if (PriceType.EOD_PRICE.equals(contractPriceType)) {
			return true;
		}
		if (PriceType.FLOATING_FUTURES_PRICE.equals(contractPriceType)) {
			return true;
		}
		if (PriceType.NON_RIC_PRICE.equals(contractPriceType)) {
			return true;
		}
		return false;
	}

	public boolean isDifferRequired(PriceType contractPriceType) {
		if (PriceType.EOD_PRICE.equals(contractPriceType)) {
			return true;
		}
		if (PriceType.MANUAL_EOD_PRICE.equals(contractPriceType)) {
			return true;
		}
		if (PriceType.NON_RIC_PRICE.equals(contractPriceType)) {
			return true;
		}
		if (PriceType.MANUAL_NON_RIC_PRICE.equals(contractPriceType)) {
			return true;
		}
		return false;
	}
}