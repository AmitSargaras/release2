package com.integrosys.cms.ui.collateral.insprotection.inscrdt;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.insprotection.InsProtectionValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class InsCrdtValidator {
	public static ActionErrors validateInput(InsCrdtForm aForm, Locale locale) {

		ActionErrors errors = InsProtectionValidator.validateInput(aForm, locale);
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		String errorCode = null;
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {

			boolean mandatory = "submit".equals(aForm.getEvent());

			/*
			 * Already Checked in the Collateral Validator if
			 * (!Validator.validateMandatoryField(aForm.getLe())) {
			 * errors.add("le", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			 * "mandatory"))); }
			 */

			// ledate will check in CollateralValidator.java
			if (!(errorCode = Validator.checkString(aForm.getDescription(), false, 1, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("description", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						250 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getInsureName(), mandatory, 1, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insureName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getInsurType(), mandatory, 0, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insurType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						30 + ""));
			}

			/*
			 * if (!(errorCode = Validator.checkAmount(aForm.getInsuredAmt(),
			 * true, 1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
			 * IGlobalConstant.DEFAULT_CURRENCY, locale))
			 * .equals(Validator.ERROR_NONE)) { errors.add("insuredAmt", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
			 * errorCode), "1", maximumAmt)); }
			 */

			/*
			 * if (!(errorCode = Validator.checkAmount(aForm.getInsuredAmt(),
			 * true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_11_2,
			 * IGlobalConstant.DEFAULT_CURRENCY, locale))
			 * .equals(Validator.ERROR_NONE)) { errors.add("insuredAmt", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
			 * errorCode), "0",
			 * IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_11_2_STR)); }else{ if
			 * (!(errorCode = Validator.checkNumber(aForm.getInsuredAmt(),
			 * false, 1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_11_2, 3,
			 * locale)) .equals(Validator.ERROR_NONE)) {
			 * errors.add("insuredAmt", new
			 * ActionMessage("error.number.moredecimalexceeded", "1",
			 * IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_11_2_STR, "2")); } }
			 */

			String errorMessageDecimalExceeded = "error.number.decimalexceeded";
			if (!(errorCode = Validator.checkNumber(aForm.getInsuredAmt(), mandatory, 1,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
				String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
				if (errorMessage.equals(errorMessageDecimalExceeded)) {
					errorMessage = "error.number.moredecimalexceeded";
				}
				errors.add("insuredAmt", new ActionMessage(errorMessage, "1",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR, "2"));
			}

			if (!(errorCode = Validator.checkDate(aForm.getEffDateInsurance(), mandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("effDateInsurance", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
			}

			if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("collateralMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
						errorCode), "1", 256 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getPolicyNo(), false, 0, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("policyNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						30 + ""));
			}

			/*if (!(errorCode = Validator.checkDate(aForm.getExpiryDateInsure(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("expiryDateInsure", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
			}*/

			if (!(errorCode = Validator.checkString(aForm.getExtLegalCounsel(), false, 1, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("extLegalCounsel", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", 250 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getLocalCurrCM(), false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("localCurrCM", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						3 + ""));
			}

			// in CollateralValidator.java will check amount, here only check
			// mandatory
			if (mandatory && !Validator.validateMandatoryField(aForm.getAmtCharge())) {
				errors.add("amtCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, "mandatory")));
			}

			if (!(errorCode = Validator.checkDate(aForm.getLegalChargeDate(), mandatory, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("legalChargeDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getChargeType(), mandatory, 1, 40))
					.equals(Validator.ERROR_NONE)) {
				errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
			}

			InsCrdtValidationHelper.validateInput(aForm, locale, errors);
		}
		return errors;

	}
}
