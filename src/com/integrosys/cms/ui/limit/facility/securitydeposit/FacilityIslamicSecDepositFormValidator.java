package com.integrosys.cms.ui.limit.facility.securitydeposit;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.limit.facility.main.FacilityMainAction;

import java.util.Locale;

public class FacilityIslamicSecDepositFormValidator {
	public static ActionErrors validateInput(FacilityIslamicSecDepositForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		
		String event = form.getEvent();
		String errorCode = null;
		boolean isMandatoryValidate = false;
		if (FacilityMainAction.EVENT_SUBMIT.equals(event) || FacilityMainAction.EVENT_SUBMIT_WO_FRAME.equals(event)) {
			isMandatoryValidate = true;
		}
		
		if (isMandatoryValidate) {
			if (StringUtils.isBlank(form.getNumberOfMonth()) &&
					StringUtils.isBlank(form.getSecurityDeposit()) &&
					StringUtils.isBlank(form.getFixedSecDepositAmt())) {
				errors.add("numberOfMonth", new ActionMessage("error.islamic.sec.deposit.at.least.one.mandatory" ));
				errors.add("securityDeposit", new ActionMessage("error.islamic.sec.deposit.at.least.one.mandatory" ));
				errors.add("fixedSecDepositAmt", new ActionMessage("error.islamic.sec.deposit.at.least.one.mandatory" ));
			}
		}
		
		// number of months
		if (!(errorCode = Validator.checkNumber(form.getNumberOfMonth(), false, 0,
				99)).equals(Validator.ERROR_NONE)) {
			errors.add("numberOfMonth", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					99 + ""));
		}
		
		// security deposit
		if (!(errorCode = Validator.checkNumber(form.getSecurityDeposit(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9, 8, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("securityDeposit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR));
		}  			
		
		// fixed security deposit amount
		if (!(errorCode = Validator.checkNumber(form.getFixedSecDepositAmt(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("fixedSecDepositAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
		}  	

		// original secrity deposit amount
		if (!(errorCode = Validator.checkNumber(form.getOriginalSecDepositAmt(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("originalSecDepositAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
		}  	
		
		// mth b4 print renewal rpt
		if (!(errorCode = Validator.checkNumber(form.getMthB4PrintRenewalRpt(), false, 0,
				999)).equals(Validator.ERROR_NONE)) {
			errors.add("mthB4PrintRenewalRpt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					999 + ""));
		}		
		
		if (!(errorCode = Validator.checkString(form.getRemark(), false, 0, 60))
				.equals(Validator.ERROR_NONE)) {
			errors.add("remark", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"60"));
		}		
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;				
	}
}
