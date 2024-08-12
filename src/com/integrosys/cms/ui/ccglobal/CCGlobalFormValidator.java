package com.integrosys.cms.ui.ccglobal;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalForm;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalFormValidator;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class CCGlobalFormValidator extends DocumentationGlobalFormValidator {
	public static ActionErrors validateInput(CCGlobalForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		//DocumentationGlobalForm aForm = (CCGlobalForm)cForm;
		errors = DocumentationGlobalFormValidator.validateInput(aForm, locale);

		if (!(aForm.getIsForBorrower().equals("isBorrower")) && !(aForm.getIsForPledgor().equals("isPledgor"))) {
//			System.out.print("getIsForBorrower=" + aForm.getIsForBorrower() + "====================getIsForPledgor="+ aForm.getIsForPledgor());
			errors.add("forBorrowerOrForPledgor", new ActionMessage("error.checkbox.mandatory"));
		}
		return errors;
	}
}
