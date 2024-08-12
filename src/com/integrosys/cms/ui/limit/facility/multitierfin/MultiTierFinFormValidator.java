package com.integrosys.cms.ui.limit.facility.multitierfin;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class MultiTierFinFormValidator {
	
	public static ActionErrors validateInput(MultiTierFinForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = null;
		if (!(FacilityMainAction.EVENT_ADD.equals(form.getEvent()) || FacilityMainAction.EVENT_ADD_WO_FRAME
				.equals(form.getEvent()))) {
			
			// Tier Term
			if (StringUtils.isBlank(form.getTierTerm()) || ("0").equals(form.getTierTerm())) {
				errors.add("tierTerm", new ActionMessage("error.mandatory"));
			}
            if (!(errorCode = Validator.checkNumber(form.getTierTerm(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
				errors.add("tierTerm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_INTEGER + ""));
			}
			
			// Term Code 
			if (StringUtils.isBlank(form.getTierTermCode())) {
				errors.add("tierTermCode", new ActionMessage("error.mandatory"));
			}
			
			if (StringUtils.isBlank(form.getRate())) {
				errors.add("rate", new ActionMessage("error.mandatory"));
			}
			
			if (!(errorCode = Validator.checkNumber(form.getRate(), false, (double)0,
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE))
					.equals(Validator.ERROR_NONE)) {
				errors.add("rate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE + ""));
			}
		}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
			return errors;
	}
}
