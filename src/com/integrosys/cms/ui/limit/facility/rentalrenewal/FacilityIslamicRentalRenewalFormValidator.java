package com.integrosys.cms.ui.limit.facility.rentalrenewal;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class FacilityIslamicRentalRenewalFormValidator {
	public static ActionErrors validateInput(FacilityIslamicRentalRenewalForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		
		String event = form.getEvent();
		String errorCode = null;
		boolean isMandatoryValidate = false;
		if (FacilityMainAction.EVENT_SUBMIT.equals(event) || FacilityMainAction.EVENT_SUBMIT_WO_FRAME.equals(event)) {
			isMandatoryValidate = true;
		}
		
		if (isMandatoryValidate) {
			if (StringUtils.isBlank(form.getRenewalRate())) {
				errors.add("renewalRate", new ActionMessage("error.mandatory"));
			}
			if (StringUtils.isBlank(form.getPrimeRateNumber())) {
				errors.add("primeRateNumber", new ActionMessage("error.mandatory"));
			}
		}
		
		// renewal term
		if (!(errorCode = Validator.checkNumber(form.getRenewalTerm(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
			errors.add("renewalTerm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER + ""));
		}
		// renewal rate
		if (!(errorCode = Validator.checkNumber(form.getRenewalRate(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9, 9, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("renewalRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR));
		}  		
		
		// prime variance
		if (!(errorCode = Validator.checkNumber(form.getPrimeVariance(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9, 9, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("primeVariance", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR));
		}  
		
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;		
	}

}
