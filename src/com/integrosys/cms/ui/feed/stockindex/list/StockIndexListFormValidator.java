package com.integrosys.cms.ui.feed.stockindex.list;

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
public class StockIndexListFormValidator implements java.io.Serializable {

	private static String LOGOBJ = StockIndexListFormValidator.class.getName();

	public static ActionErrors validateInput(StockIndexListForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (StockIndexListAction.EVENT_SAVE.equals(event) || StockIndexListAction.EVENT_SUBMIT.equals(event)
				|| StockIndexListAction.EVENT_PAGINATE.equals(event) || StockIndexListAction.EVENT_ADD.equals(event)
				|| StockIndexListAction.EVENT_REMOVE.equals(event)) {

		}
		// Check that the updated unit prices fall in range.
		FeedUIValidator.validateUnitPricesArr(form.getUpdatedUnitPrices(), errors);

		if (StockIndexListAction.EVENT_REMOVE.equals(event)) {
			// Check that there is at least one selected checkbox.
			if ((form.getChkDeletes() == null) || (form.getChkDeletes().length == 0)) {
				errors.add("chkDeletes", new ActionMessage(FeedConstants.ERROR_CHKBOX_MANDATORY));
			}
		}

		if (StockIndexListAction.EVENT_APPROVE.equals(event) || StockIndexListAction.EVENT_REJECT.equals(event)
				|| StockIndexListAction.EVENT_CLOSE.equals(event)) {
			errorCode = RemarksValidatorUtil.checkRemarks(form.getRemarks(), false);
			if (!Validator.ERROR_NONE.equals(errorCode)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
			}

		}

		DefaultLogger.debug(LOGOBJ, "errors " + errors.size());

		return errors;
	}

}
