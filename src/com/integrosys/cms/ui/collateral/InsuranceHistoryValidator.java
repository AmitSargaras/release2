package com.integrosys.cms.ui.collateral;

import static com.integrosys.base.techinfra.validation.ValidatorConstant.ERROR_NONE;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIValidator;

public class InsuranceHistoryValidator {

	public static ActionErrors validateInput(CommonForm commonForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		InsuranceHistoryForm form = (InsuranceHistoryForm) commonForm;
		String errorCode;

		if (!ERROR_NONE.equals(errorCode = Validator.checkString(form.getInsuranceCompanyName(), false, 5, 100))) {
			errors.add("insuranceCompanyNameError",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), 5, 100));
		}

		if (!ERROR_NONE.equals(errorCode = Validator.checkDate(form.getReceivedDateFrom(), false, locale))) {
			errors.add("receivedDateFromError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
		}
		
		boolean receivedDateToMandatoryFlag = false;
		if(StringUtils.isNotEmpty(form.getReceivedDateFrom())) {
			receivedDateToMandatoryFlag = true;
		}
		
		if (!ERROR_NONE.equals(errorCode = Validator.checkDate(form.getReceivedDateTo(), receivedDateToMandatoryFlag, locale))) {
			errors.add("receivedDateToError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode)));
		}

		if (receivedDateToMandatoryFlag && !ERROR_NONE.equals(errorCode = UIValidator.compareDateEarlier(form.getReceivedDateFrom(), form.getReceivedDateTo(), locale))) {
			errors.add("receivedDateFromError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),"Received Date From", "Received Date To"));
		}
		
		return errors;
	}

}
