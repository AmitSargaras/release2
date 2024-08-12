package com.integrosys.cms.ui.customer;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class ProcessDetailsCustomerFormValidator {

	public static ActionErrors validateInput(ProcessDetailsCustomerForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();

		try {
			String errMsg = Validator.checkNumber(aForm.getRequiredSecurityCoverage(), false, 0, 999, 0, locale);
			if (!errMsg.equals(Validator.ERROR_NONE)) {
				if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
					errors.add("secCov", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, "heightlessthan"),
							"0", "999"));
				}
				else {
					errors.add("secCov", new ActionMessage("error.number." + errMsg));
				}
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
		}
		return errors;

	}

}