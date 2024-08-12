package com.integrosys.cms.ui.collateral.document.docdoa;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.collateral.ManualValuationValidator;

public class DocDoAValidationHelper {

	public static ActionErrors validateInput(DocDoAForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		
		if (!(errorCode = Validator.checkDate(aForm.getAwardedDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("awardedDate", new ActionMessage("error.date.mandatory", "1", "256"));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.DocumentValidationHelper",
					" aForm.getAwardedDate()= " + aForm.getAwardedDate());
		}

		if (aForm.getContractAmt() != null) {
			if (!(errorCode = Validator.checkAmount(aForm.getContractAmt(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("contractAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						maximumAmt));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.CollateralValidator",
						"============= aForm.getContractAmt() ==========");
			}
		}

        ManualValuationValidator.validateInput(aForm, locale, errors);

        return errors;
	}
}
