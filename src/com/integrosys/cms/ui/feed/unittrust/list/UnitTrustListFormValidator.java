package com.integrosys.cms.ui.feed.unittrust.list;

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
public class UnitTrustListFormValidator implements java.io.Serializable {

	private static String LOGOBJ = UnitTrustListFormValidator.class.getName();

	public static ActionErrors validateInput(UnitTrustListForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (UnitTrustListAction.EVENT_SAVE.equals(event) || UnitTrustListAction.EVENT_SUBMIT.equals(event)
				|| UnitTrustListAction.EVENT_PAGINATE.equals(event) || UnitTrustListAction.EVENT_ADD.equals(event)
				|| UnitTrustListAction.EVENT_REMOVE.equals(event)) {

			// Check that the updated unit prices fall in range.
			FeedUIValidator.validateUnitPricesArr(form.getUpdatedUnitPrices(), errors);

		}

		// Check that there is at least one selected checkbox.
		if (UnitTrustListAction.EVENT_REMOVE.equals(event)) {
			if ((form.getChkDeletes() == null) || (form.getChkDeletes().length == 0)) {
				errors.add("chkDeletes", new ActionMessage(FeedConstants.ERROR_CHKBOX_MANDATORY));
				DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
			}
		}

		if (UnitTrustListAction.EVENT_APPROVE.equals(event) || UnitTrustListAction.EVENT_REJECT.equals(event)
				|| UnitTrustListAction.EVENT_CLOSE.equals(event)) {
			errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
			}

		}

		DefaultLogger.debug(LOGOBJ, "errors " + errors.size());

		return errors;
	}

}
