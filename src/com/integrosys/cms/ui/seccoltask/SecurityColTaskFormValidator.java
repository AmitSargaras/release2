package com.integrosys.cms.ui.seccoltask;

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
public class SecurityColTaskFormValidator {
	public static ActionErrors validateInput(SecurityColTaskForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		DefaultLogger.debug(SecurityColTaskFormValidator.class, "locale from common action ..,.,.,..>>>>>>>>>>>>>>>"
				+ locale);
		String errorCode = "";
		DefaultLogger.debug("REMARKS", "form remark: " + aForm.getColRemarks());
		if (!(errorCode = Validator.checkString(aForm.getColRemarks(), false, 1, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("colRemarks",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "250"));
		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
