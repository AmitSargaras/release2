package com.integrosys.cms.ui.bfl;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class BflFormValidator {
	public static ActionErrors validateInput(BflForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(BflFormValidator.class, "locale from common action ..,.,.,..>>>>>>>>>>>>>>>" + locale);
		String errorCode = "";
		DefaultLogger.debug("REMARKS", "form remark: " + aForm.getTatRemarks());
		if (!(errorCode = Validator.checkString(aForm.getTatRemarks(), false, 1, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("tatRemarks",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "250"));
		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
