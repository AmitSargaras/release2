package com.integrosys.cms.ui.seccountry;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class SecurityCountryFormValidator {
	public static ActionErrors validateInput(SecurityCountryForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		if (!(errorCode = Validator.checkString(aForm.getDocDesc(), true, 1, 500)).equals(Validator.ERROR_NONE)) {
			errors.add("docDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "500"));
		}
		if (!(errorCode = Validator.checkDate(aForm.getExpDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("expDate", new ActionMessage("error.date.format"));
		}
		if (!(errorCode = Validator.checkString(aForm.getDocVersion(), true, 1, 7)).equals(Validator.ERROR_NONE)
				|| aForm.getDocVersion().equals("")) {
			errors.add("docVersion", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "7"));
		}
		Date d = DateUtil.getDate();
		if (aForm.getExpDate().length() > 0) {
			int a = d.compareTo(DateUtil.convertDate(locale, aForm.getExpDate()));
			if (a > 0) {
				errors.add("expDate", new ActionMessage("error.date.compareDate", "Due Date", "Current Date"));
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getDocCode(), true, 1, 20)).equals(Validator.ERROR_NONE)
				|| aForm.getDocCode().equals("")) {
			errors.add("docCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "20"));
		}
		if(aForm.getAppendLoanList().equalsIgnoreCase("0")){
			errors.add("forLoanApplicationType", new ActionMessage("error.select.mandatory"));
		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
