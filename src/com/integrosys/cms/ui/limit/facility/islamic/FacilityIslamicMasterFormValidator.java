package com.integrosys.cms.ui.limit.facility.islamic;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class FacilityIslamicMasterFormValidator {
	
	public static final int MAXINUM_SHORT_999 = 999 ;
	public static final String MAXINUM_SHORT_999_STR = String.valueOf(MAXINUM_SHORT_999) ;
	
	public static final int MAXINUM_SHORT_99 = 99 ;
	public static final String MAXINUM_SHORT_99_STR = String.valueOf(MAXINUM_SHORT_99) ;
	
	public static ActionErrors validateInput(FacilityIslamicMasterForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();

		String event = form.getEvent();
		String errorCode = null;
		boolean isMandatoryValidate = false;
		if (FacilityMainAction.EVENT_SUBMIT.equals(event) || FacilityMainAction.EVENT_SUBMIT_WO_FRAME.equals(event)) {
			isMandatoryValidate = true;
		}
		if (isMandatoryValidate) {
			// GPP Payment Mode
			if (StringUtils.isBlank(form.getGppPaymentModeValue())) {
				errors.add("gppPaymentModeValue", new ActionMessage("error.mandatory"));
			}
			
		//	if (StringUtils.isBlank(form.getCustomerInterestRate())) {
		//		errors.add("customerInterestRate", new ActionMessage("error.mandatory"));
		//	}
		}
		
		//Commission Rate
		if (!(errorCode = Validator.checkNumber(form.getCommissionRate(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9, 9, locale)).equals(Validator.ERROR_NONE)) { 
			errors.add("commissionRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR));
		}
		
		//Commission Term
		if (!(errorCode = Validator.checkNumber(form.getCommissionTerm(), false, 0,
				MAXINUM_SHORT_999, 0, locale)).equals(Validator.ERROR_NONE)) { 
			errors.add("commissionTerm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", MAXINUM_SHORT_999_STR));
		}
		
		// GPP Term
		if (!(errorCode = Validator.checkNumber(form.getGppTerm(), false, 0,
				MAXINUM_SHORT_999, 0, locale)).equals(Validator.ERROR_NONE)) { 
			errors.add("gppTerm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", MAXINUM_SHORT_999_STR));
		}
		
		// Security Dep # of Month
		if (!(errorCode = Validator.checkNumber(form.getSecurityDepOfMonth(), false, 0,
				MAXINUM_SHORT_99, 0, locale)).equals(Validator.ERROR_NONE)) { 
			errors.add("securityDepOfMonth", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", MAXINUM_SHORT_99_STR));
		}
		
		//Pricing - Tier 1 Rate
		if (!(errorCode = Validator.checkNumber(form.getCustomerInterestRate(), isMandatoryValidate, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9, 9, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("customerInterestRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR));
		}
		
		// Conditional Mandatory
//		if (!(StringUtils.isBlank(form.getGppPaymentModeValue()))&&!"I".equals(form.getGppPaymentModeValue())) {
//			if (StringUtils.isBlank(form.getGppCalculationMethodValue())) {
//				errors.add("gppCalculationMethodValue", new ActionMessage("error.mandatory"));
//			}
//
//			if (StringUtils.isBlank(form.getRefundGppProfitValue())) {
//				errors.add("refundGppProfitValue", new ActionMessage("error.mandatory"));
//			}
//		}
//        else if (!(StringUtils.isBlank(form.getGppPaymentModeValue()))&& "I".equals(form.getGppPaymentModeValue())) {
//            if (StringUtils.isNotBlank(form.getRefundGppProfitValue())) {
//                errors.add("refundGppProfitValue", new ActionMessage("error.string.empty"));
//            }
//            if (StringUtils.isNotBlank(form.getGppTerm()) && !("0").equals(form.getGppTerm())) {
//                errors.add("gppTerm", new ActionMessage("error.string.empty"));
//            }
//			if (StringUtils.isNotBlank(form.getGppCalculationMethodValue())) {
//				errors.add("gppCalculationMethodValue", new ActionMessage("error.string.empty"));
//			}
//        }
//
//        if (!(StringUtils.isBlank(form.getFulrelProfitCalcMethod())) && form.getFulrelProfitCalcMethod().equals("F")) {
//			if (StringUtils.isBlank(form.getRefundFulrelProfitValue())) {
//				errors.add("refundFulrelProfitValue", new ActionMessage("error.mandatory"));
//			}
//		}
		
		// Security Dep Amount
		if (!(errorCode = Validator.checkAmount(form.getSecurityDepAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("securityDepAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("securityDepAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Fixed Commission Amount
		if (!(errorCode = Validator.checkAmount(form.getFixedCommissionAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {

			if (errorCode.equals("decimalexceeded")) {
				errors.add("fixedCommissionAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("fixedCommissionAmount", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}

		// Security Dep %
		if (!(errorCode = Validator.checkNumber(form.getSecurityDepPercentage(), false, 0,
				IGlobalConstant.MAXIMUM_PERCENTAGE_VALUE)).equals(Validator.ERROR_NONE)) {
			errors.add("securityDepPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR + ""));
		}
		
		// Fixed RefundAmount Amount
		if (form.getFixedRefundAmount() != null) {
			if (!(errorCode = Validator.checkAmount(form.getFixedRefundAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("fixedRefundAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR));
			}
		}

        //Andy Wong: snpTerm data type is short/smallint, have limitation till 32767. constraint user to smallint range
        if (!(errorCode = Validator.checkNumber(form.getSnpTerm(), false, 0, IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER, 0, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("snpTerm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER + ""));
		}
		
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}

	public static String mapModifiedStringToDefaultString(String amount) {
		String result = null;
		BigDecimal temp = UIUtil.mapStringToBigDecimal(amount);
		if (temp != null)
			result = temp.toString();
		return result;
	}
}
