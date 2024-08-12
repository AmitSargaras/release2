package com.integrosys.cms.ui.collateral.insprotection.insswap;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.insprotection.InsProtectionValidator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class InsSwapValidator {
	public static ActionErrors validateInput(InsSwapForm aForm, Locale locale) {

		ActionErrors errors = InsProtectionValidator.validateInput(aForm, locale);

		String errorCode = null;
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {

			boolean mandatory = "submit".equals(aForm.getEvent());

			if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getDescription(), mandatory))
					.equals(Validator.ERROR_NONE)) {
				errors.add("description", RemarksValidatorUtil.getErrorMessage(errorCode));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.insprotection.insswap.insswapvalidator",
						"============= aForm.getDescription() ==========>");
			}
			if (!(errorCode = Validator.checkDate(aForm.getDateISDAMasterAgmt(), mandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateISDAMasterAgmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
				// DefaultLogger.debug(LOGOBJ,
				// "aForm.getCollateralMaturityDate(): " +
				// aForm.getCollateralMaturityDate());
			}
			if (!(errorCode = Validator.checkDate(aForm.getDateTreasury(), mandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateTreasury", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
						256 + ""));
				// DefaultLogger.debug(LOGOBJ,
				// "aForm.getCollateralMaturityDate(): " +
				// aForm.getCollateralMaturityDate());
			}

			if (!(errorCode = Validator.checkString(aForm.getChargeType(), mandatory, 1, 40))
					.equals(Validator.ERROR_NONE)) {
				errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
			}

			InsSwapValidationHelper.validateInput(aForm, locale, errors);
		}
		return errors;

	}
}
