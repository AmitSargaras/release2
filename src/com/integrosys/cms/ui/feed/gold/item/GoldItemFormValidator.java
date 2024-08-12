package com.integrosys.cms.ui.feed.gold.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.FeedUIValidator;

public class GoldItemFormValidator implements java.io.Serializable {

	private static String LOGOBJ = GoldItemFormValidator.class.getName();

	public static ActionErrors validateInput(GoldItemForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();

		DefaultLogger.debug(LOGOBJ, "errors" + errors.size());

		if (GoldItemAction.EVENT_SAVE.equals(event)) {

			String currencyCode = form.getCurrencyCode();
			String goldGrade = form.getGoldGradeNum();
			String goldUOM = form.getUnitMeasurementNum();
			String unitPrice = form.getUnitPrice();

			FeedUIValidator.validateGoldUnitPrices(unitPrice, errors);

			if ("".equals(currencyCode)) {
				errors.add("currencyCode", new ActionMessage(FeedConstants.ERROR_NO_SELECTION));
				DefaultLogger.debug(LOGOBJ, "currencyCode" + currencyCode);
			}
			if ("".equals(goldGrade)) {
				errors.add("goldGradeNum", new ActionMessage(FeedConstants.ERROR_NO_SELECTION));
				DefaultLogger.debug(LOGOBJ, "goldGradeNum" + goldGrade);
			}
			if ("".equals(goldUOM)) {
				errors.add("unitMeasurementNum", new ActionMessage(FeedConstants.ERROR_NO_SELECTION));
				DefaultLogger.debug(LOGOBJ, "unitMeasurementNum" + goldUOM);
			}
			if ("".equals(unitPrice)) {
				errors.add("unitPrice", new ActionMessage(FeedConstants.ERROR_MANDATORY));
				DefaultLogger.debug(LOGOBJ, "unitPrice" + unitPrice);
			}
		}
		return errors;
	}
}
