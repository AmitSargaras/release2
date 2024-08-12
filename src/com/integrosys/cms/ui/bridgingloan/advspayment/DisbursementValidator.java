/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.advspayment;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Disbursement Validation
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class DisbursementValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.advspayment.DisbursementForm form,
			Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in DisbursementValidator", "form.getEvent()=" + form.getEvent());

			String purpose = form.getPurpose();
			String disRemarks = form.getDisRemarks();

			if (!(errMsg = Validator.checkString(purpose, true, 0, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("purpose", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "10"));
			}
			if (!(errMsg = Validator.checkString(disRemarks, false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("disRemarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"500"));
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}