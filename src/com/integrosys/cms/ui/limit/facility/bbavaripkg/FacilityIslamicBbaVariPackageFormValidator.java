package com.integrosys.cms.ui.limit.facility.bbavaripkg;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

public class FacilityIslamicBbaVariPackageFormValidator {
	public static ActionErrors validateInput(FacilityIslamicBbaVariPackageForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();

		String event = form.getEvent();
		String errorCode = null;
		boolean isMandatoryValidate = false;
		if (FacilityMainAction.EVENT_SUBMIT.equals(event) || FacilityMainAction.EVENT_SUBMIT_WO_FRAME.equals(event)) {
			isMandatoryValidate = true;
		}
		if (isMandatoryValidate) {
			// Cust. Prof. Rate
			if (StringUtils.isBlank(form.getCustProfRate())) {
				errors.add("custProfRate", new ActionMessage("error.mandatory"));
			}
			
			// Rebate Method
			if (StringUtils.isBlank(form.getRebateMethod())) {
				errors.add("rebateMethod", new ActionMessage("error.mandatory"));
			}
			
			// GPP Payment Mode
			if (StringUtils.isBlank(form.getGppPaymentMode())) {
				errors.add("gppPaymentMode", new ActionMessage("error.mandatory"));
			}
			
			// GPP Calculation Method
			if (StringUtils.isBlank(form.getGppCalculationMethod())) {
				errors.add("gppCalculationMethod", new ActionMessage("error.mandatory"));
			}
			
			// GPP Term
			if (StringUtils.isBlank(form.getGppTerm())) {
				errors.add("gppTerm", new ActionMessage("error.mandatory"));
			}
			
			// GPP Term Code
			if (StringUtils.isBlank(form.getGppTermCode())) {
				errors.add("gppTermCode", new ActionMessage("error.mandatory"));
			}
			
			// Fulrel profit calculation method
			if (StringUtils.isBlank(form.getFulrelProfitCalMethod())) {
				errors.add("fulrelProfitCalMethod", new ActionMessage("error.mandatory"));
			}
			
			if(!StringUtils.isBlank(form.getFulrelProfitCalMethod())&&"F".equals(form.getFulrelProfitCalMethod())
                    && StringUtils.isBlank(form.getRefundFullReleaseProfit())){
				errors.add("refundFullReleaseProfit", new ActionMessage("error.mandatory"));
			}
		}
		
		// Installment Amount
		if (!(errorCode = Validator.checkAmount(form.getInstallment(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			if (errorCode.equals("decimalexceeded")) {
				errors.add("installment", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("installment", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}
		// Final payment amount
		if (!(errorCode = Validator.checkAmount(form.getFinalPaymentAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {

			if (errorCode.equals("decimalexceeded")) {
				errors.add("finalPaymentAmount", new ActionMessage("error.number.moredecimalexceeded", "", "", "2"));
			}
			else {
				errors.add("finalPaymentAmount", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_2_STR + ""));
			}
		}

		// Cust. Prof. Rate.
		if (!(errorCode = Validator.checkNumber(form.getCustProfRate(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9, 9, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("custProfRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR));
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
