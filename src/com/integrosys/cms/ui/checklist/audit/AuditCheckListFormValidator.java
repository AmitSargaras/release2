package com.integrosys.cms.ui.checklist.audit;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class AuditCheckListFormValidator {
	public static ActionErrors validateInput(AuditCheckListForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String event = aForm.getEvent();
		DefaultLogger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "Inside Validation");
		Date d = DateUtil.getDate();
		d = DateUtil.clearTime(d);

		if ("list_all".equals(event)) {

			if (!(errorCode = Validator.checkDate(aForm.getAsOfDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				if (aForm.getAsOfDate().length() > 0) {
					errors.add("asOfDate", new ActionMessage("error.date.format"));
				}
				else {
					errors.add("asOfDate", new ActionMessage("error.date.mandatory"));
				}
			}
			else {
				if (aForm.getAsOfDate().trim().length() > 0) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getAsOfDate())).after(
							DateUtil.clearTime(DateUtil.getDate()))) {
						errors.add("asOfDate", new ActionMessage("error.date.compareDate.more", "As Of Date",
								"Current Date"));
					}
				}
				else {
					errors.add("asOfDate", new ActionMessage("error.date.mandatory"));
				}
			}

		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
