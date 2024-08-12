package com.integrosys.cms.ui.secmaster;

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
public class SecurityMasterFormValidator {
	public static ActionErrors validateInput(SecurityMasterForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		if (!(errorCode = Validator.checkString(aForm.getDocDesc(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
			errors.add("docDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "200"));
		}
		
		if (!(errorCode = Validator.checkDate(aForm.getExpDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("expDate", new ActionMessage("error.date.format"));
		}
		if (aForm.getExpDate().length() > 0) {
			Date d = DateUtil.getDate();
			int a = d.compareTo(DateUtil.convertDate(locale, aForm.getExpDate()));
			DefaultLogger.debug("vaidation ***********************************", "Eroororr date " + a);
			if (a > 0) {
				errors.add("expDate", new ActionMessage("error.date.compareDate", "Due Date", "Current Date"));
			}
		}
		else if(!(errorCode = Validator.checkString(aForm.getExpDate(), true, 0, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("expDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					3 + ""));
		}
		 if(!(errorCode = Validator.checkString(aForm.getDocCode(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("docCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}
		
		
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
