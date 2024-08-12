package com.integrosys.cms.ui.feed.gold.list;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.feed.FeedConstants;
import com.integrosys.cms.ui.feed.FeedUIValidator;

public class GoldListFormValidator implements java.io.Serializable {

	private static String LOGOBJ = GoldListFormValidator.class.getName();

	public static ActionErrors validateInput(GoldListForm form, Locale locale) {;

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();

		String errorCode = "";
		if (GoldListAction.EVENT_SAVE.equals(event) || GoldListAction.EVENT_SUBMIT.equals(event)
				|| GoldListAction.EVENT_PAGINATE.equals(event)
				|| GoldListAction.EVENT_ADD.equals(event) || GoldListAction.EVENT_REMOVE.equals(event)) {

			// Check that the updated unit prices fall in range.
			FeedUIValidator.validateGoldUnitPricesArr(form.getUpdatedUnitPrices(), errors);

		}

		// Check that there is at least one selected checkbox.
		if (GoldListAction.EVENT_REMOVE.equals(event)) {
			if ((form.getChkDeletes() == null) || (form.getChkDeletes().length == 0)) {
				errors.add("chkDeletes", new ActionMessage(FeedConstants.ERROR_CHKBOX_MANDATORY));
				DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
			}
		}

		if (GoldListAction.EVENT_APPROVE.equals(event) || GoldListAction.EVENT_REJECT.equals(event)
				|| GoldListAction.EVENT_CLOSE.equals(event)) {
			errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));

			}

		}
		DefaultLogger.debug(LOGOBJ, "errors " + errors.size());
		return errors;
	}
}
