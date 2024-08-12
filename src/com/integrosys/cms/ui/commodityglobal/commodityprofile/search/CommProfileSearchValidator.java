package com.integrosys.cms.ui.commodityglobal.commodityprofile.search;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.5
 * @since 2006-2-10
 * @Tag com.integrosys.cms.ui.commodityglobal.commodityprofile.search.
 *      CommProfileSearchValidator.java
 */
public class CommProfileSearchValidator {
	public static ActionErrors validateInput(ActionForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		if (!isValidSearchCriteria(aForm)) {
			DefaultLogger.debug(CommProfileSearchValidator.class.getName(), " - Invalid Search Criteria");
			errors.add(ActionErrors.GLOBAL_MESSAGE, new ActionMessage("error.search.criteria"));
		}
		return errors;
	}

	private static boolean isValidSearchCriteria(ActionForm form) {
		CommProfileSearchForm aForm = (CommProfileSearchForm) form;
		if (isEmptyString(aForm.getCommodityCategory()) && isEmptyString(aForm.getPriceType())
				&& isEmptyString(aForm.getNonRICCode()) && isEmptyString(aForm.getCommoditySubType())) {
			return false;
		}
		if (isEmptyString(aForm.getCommodityCategory()) && !isEmptyString(aForm.getPriceType())
				&& isEmptyString(aForm.getNonRICCode()) && isEmptyString(aForm.getCommoditySubType())) {
			return false;
		}
		if (isEmptyString(aForm.getCommodityCategory()) && isEmptyString(aForm.getPriceType())
				&& !isEmptyString(aForm.getNonRICCode()) && isEmptyString(aForm.getCommoditySubType())) {
			return true;
		}
		if (isEmptyString(aForm.getCommodityCategory()) && isEmptyString(aForm.getPriceType())
				&& isEmptyString(aForm.getNonRICCode()) && !isEmptyString(aForm.getCommoditySubType())) {
			return true;
		}
		return true;
	}

	private static boolean isEmptyString(String value) {
		if ((value == null) || "".equals(value.trim())) {
			return true;
		}
		return false;
	}
}
