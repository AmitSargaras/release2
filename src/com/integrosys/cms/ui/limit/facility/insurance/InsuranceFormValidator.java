package com.integrosys.cms.ui.limit.facility.insurance;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class InsuranceFormValidator {
	public static ActionErrors validateInput(InsuranceForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();

		String errorCode = null;
		for (int i = 0; i < 2; i++) {
			if (!(errorCode = Validator.checkAmount(form.getInsuredAmount()[i], false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insuredAmount[" + i + "]", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR + ""));
			}
			if (!(errorCode = Validator.checkAmount(form.getInsurancePremiumAmount()[i], false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insurancePremiumAmount[" + i + "]", new ActionMessage(ErrorKeyMapper.map(
						ErrorKeyMapper.NUMBER, errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR + ""));
			}

            if (StringUtils.isBlank(form.getCoverageTypeEntryCode()[i])
                    && StringUtils.isNotBlank(form.getInsuranceCompanyEntryCode()[i])) {
                errors.add("coverageType[" + i + "]", new ActionMessage("error.string.mandatory"));
            }
            else if (StringUtils.isNotBlank(form.getCoverageTypeEntryCode()[i])
                    && StringUtils.isBlank(form.getInsuranceCompanyEntryCode()[i])) {
                errors.add("coverageType[" + i + "]", new ActionMessage("error.string.empty"));   
            }
        }
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}