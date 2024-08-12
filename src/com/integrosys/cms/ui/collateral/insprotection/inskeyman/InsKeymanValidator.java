package com.integrosys.cms.ui.collateral.insprotection.inskeyman;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.insprotection.InsProtectionValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class InsKeymanValidator {
	public static ActionErrors validateInput(InsKeymanForm aForm, Locale locale) {

		ActionErrors errors = InsProtectionValidator.validateInput(aForm, locale);

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		String errorCode = null;
		boolean isMandatory = false;
		if (aForm.getEvent().equals("submit")) {
			isMandatory = true;
		}

		// this validator is double, try to check also at
		// InsKeymanValidationHelper.java below
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {

			boolean mandatory = "submit".equals(aForm.getEvent());

			if (!(errorCode = Validator.checkString(aForm.getInsureName(), false, 1, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("insureName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						50 + ""));
			}
			/*
			 * if (aForm.getInsurType() != null) { if (!(errorCode =
			 * Validator.checkString(aForm.getInsurType(), false, 1,
			 * 20)).equals(Validator.ERROR_NONE)) { errors.add("insurType", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			 * errorCode), "1", 20 + "")); } }
			 */
			/*
			 * Already checked in InsKeymanValidationHelper
			 * 
			 * if (!(errorCode = Validator.checkAmount(aForm.getInsuredAmt(),
			 * false, 1, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
			 * IGlobalConstant.DEFAULT_CURRENCY, locale))
			 * .equals(Validator.ERROR_NONE)) { errors.add("insuredAmt", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
			 * errorCode), "1", maximumAmt)); }
			 */

			// this validator is double, try to check also at
			// InsKeymanValidationHelper.java below
			if (!(errorCode = Validator.checkDate(aForm.getEffDateInsurance(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("effDateInsurance", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
			}

            //Andy Wong, 11 Feb 2009: remove insurance policy no mandatory check for Life Insurance
			if (!(errorCode = Validator.checkString(aForm.getPolicyNo(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("policyNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						30 + ""));
			}

			if (!(errorCode = Validator.checkNumericString(UIUtil.removeComma(aForm.getInsuredAmt()), false, 0, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insuredAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0","99999999999999999999"));

			}
			
			if (!(errorCode = Validator.checkDate(aForm.getExpiryDateInsure(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("expiryDateInsure", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"1", 256 + ""));
			}
			if (aForm.getEffDateInsurance().trim().length() > 0) {
				if (aForm.getExpiryDateInsure().length() > 0){			
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getEffDateInsurance())).after(
						DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDateInsure())))) {
						errors.add("expiryDateInsure", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Insurance Maturity Date",
							"Effective Date of Insurance"));
					}
				}
			}
			
			

			//--Govind S: This field is use for HDFC (Security Priority), Change it from (Bank's Interest Duly Noted) 07/07/2011--//
			if (!(errorCode = Validator.checkString(aForm.getBankInterestNoted(), false, 1, 5))
					.equals(Validator.ERROR_NONE)) {
				errors.add("bankInterestNoted", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", 1 + ""));
			}

			// in CollateralValidator.java will check amount, here only check
			// mandatory
			/*
			if (mandatory && !Validator.validateMandatoryField(aForm.getAmtCharge())) {
				errors.add("amtCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, "mandatory")));
			}
			 */
			
			/*Govind S: Commented Insurence(Life Insurence) security 06/07/2011
			/*
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
*/
			InsKeymanValidationHelper.validateInput(aForm, locale, errors);
		}
		return errors;

	}
}
