package com.integrosys.cms.ui.manualinput.customer;

import static com.integrosys.base.techinfra.validation.ValidatorConstant.ERROR_NONE;
import static com.integrosys.base.uiinfra.common.ErrorKeyMapper.STRING;

import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;
import org.springframework.util.CollectionUtils;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIUtil;
import static com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerAction.EVENT_SAVE_CO_BORROWER_DETAILS;

public class CoBorrowerDetailsValidator {

	public static ActionErrors validateInput(CommonForm commonForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		
		validateInput(commonForm, errors, locale);
		
		return errors;
	}
	
	public static void validateInput(CommonForm commonForm, ActionErrors errors, Locale locale) {
		CoBorrowerDetailsForm form = (CoBorrowerDetailsForm) commonForm;
		String errorCode;
		
		if (!ERROR_NONE.equals(
				errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(form.getCoBorrowerLiabId(), true, 1, 16))) {
			errors.add("coBorrowerLiabIdError", new ActionMessage(ErrorKeyMapper.map(STRING, errorCode), "1", "16"));
		}
		
		List<String> coBorrowerLiabIdList = UIUtil.getListFromDelimitedString(
				form.getCoBorrowerLiabIdList(), ",");
		if(!CollectionUtils.isEmpty(coBorrowerLiabIdList) && EVENT_SAVE_CO_BORROWER_DETAILS.equals(form.getEvent())) {
			coBorrowerLiabIdList.remove(form.getCoBorrowerLiabId());
		}
		if (!CollectionUtils.isEmpty(coBorrowerLiabIdList)) {
			if(coBorrowerLiabIdList.contains(form.getCoBorrowerLiabId()))
				errors.add("coBorrowerLiabIdError", new ActionMessage("error.coborrower.duplicate.coBorrowerLiabId",form.getCoBorrowerLiabId()));
		}
		
		if (!ERROR_NONE.equals(errorCode = Validator.checkString(form.getCoBorrowerName(), true, 1, 2000))) {
			errors.add("coBorrowerNameError", new ActionMessage(ErrorKeyMapper.map(STRING, errorCode), "1", "2000"));
		}
	}

}
