package com.integrosys.cms.ui.feed.exchangerate.list;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.FeedUIValidator;

/**
 * This class implements validation
 */
public class ExchangeRateListFormValidator implements java.io.Serializable {

	private static String LOGOBJ = ExchangeRateListFormValidator.class.getName();

	public static ActionErrors validateInput(ExchangeRateListForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();

		String errorCode = "";

		if (ExchangeRateListAction.EVENT_SAVE.equals(event) || ExchangeRateListAction.EVENT_SUBMIT.equals(event)
				|| ExchangeRateListAction.EVENT_PAGINATE.equals(event)
				|| ExchangeRateListAction.EVENT_ADD.equals(event) || ExchangeRateListAction.EVENT_REMOVE.equals(event)) {

			// Check that the updated unit prices fall in range.
			FeedUIValidator.validateUnitPricesArr(form.getUpdatedUnitPrices(), errors);
			FeedUIValidator.validateCurrencyDiscription(form.getUpdatedCurrencyDescription(), errors);

		}

		// Check that there is at least one selected checkbox.
		if (ExchangeRateListAction.EVENT_REMOVE.equals(event)) {
			if ((form.getChkDeletes() == null) || (form.getChkDeletes().length == 0)) {
				errors.add("chkDeletes", new ActionMessage(FeedConstants.ERROR_CHKBOX_MANDATORY));
				DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
			}
		}

		if (ExchangeRateListAction.EVENT_REJECT.equals(event)) {
			
			errorCode = Validator.checkTextBox(form.getRemarks(), true, 0, 250, 0, 3);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("remarks", new ActionMessage("error.reject.remark"));
			}
		}

		DefaultLogger.debug(LOGOBJ, "errors " + errors.size());
		return errors;
	}

}