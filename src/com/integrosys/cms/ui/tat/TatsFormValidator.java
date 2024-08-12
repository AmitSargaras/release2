package com.integrosys.cms.ui.tat;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jul 24, 2003 Time: 11:23:06 AM
 * 
 */
public class TatsFormValidator {
	private static TatsFormValidator LOG_OBJ = new TatsFormValidator();

	public static ActionErrors validateInput(TatsForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		int datecomparison = 0;
		if ((aForm.getTatCreateDate() != null) && (!aForm.getTatCreateDate().equals(""))
				&& (aForm.getApprovalDate() != null) && (!aForm.getApprovalDate().equals(""))) {
			datecomparison = ((DateUtil.convertDate(aForm.getTatCreateDate())).compareTo(DateUtil.convertDate(aForm
					.getApprovalDate())));
		}
		DefaultLogger.debug(LOG_OBJ, "Output from validator on date: "
				+ Validator.checkDate(aForm.getTatCreateDate(), true, locale));
		if (!(errorCode = Validator.checkDate(aForm.getTatCreateDate(), true, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("expDate", new ActionMessage("error.date.format"));
		}
		else if ((aForm.getTatCreateDate().length() > 0)
				&& DateUtil.convertDate(locale, aForm.getTatCreateDate()).after(DateUtil.getDate())) {
			errors.add("expDate", new ActionMessage("error.tat.tatCreateDate.DateAfter", "1", "256"));
			DefaultLogger.debug("com.integrosys.cms.ui.tat.TatFormValidator",
					"============= aForm.getTatCreateDate ============> ");
		}
		else if (datecomparison < 0) {
			errors.add("expDate", new ActionMessage("error.tatCreateDate.ApprovedDate"));
			DefaultLogger.debug("", "greater than or not"
					+ ((DateUtil.convertDate(aForm.getTatCreateDate())).compareTo(DateUtil.convertDate(aForm
							.getApprovalDate()))));
		}

		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}