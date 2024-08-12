package com.integrosys.cms.ui.collateral;

import java.util.Locale;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.common.UIUtil;

public class CollateralLoadingUiHelper {
	public static String convertAmountToString(Amount amount, Locale locale) {
		int decPlaces = 2;
		String amt = null;
		try {
		amt = UIUtil.formatAmount(amount,	decPlaces, locale);
		}
		catch (Exception e) {
			DefaultLogger.error("CollateralLoadingUiHelper.java", "Exception Caught", e);
		}
		return amt;
	}
}
