package com.integrosys.cms.ui.limitsOfAuthorityMaster;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.NumberValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class LimitsOfAuthorityMasterValidator {

	public static ActionErrors validateInput(LimitsOfAuthorityMasterForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();

		LimitsOfAuthorityMasterForm form = (LimitsOfAuthorityMasterForm) aForm; 
		
		String errorCode = null;
		
		if(StringUtils.isBlank(form.getEmployeeGrade()))
			errors.add("employeeGradeError", new ActionMessage("error.string.mandatory"));
		
		if(StringUtils.isBlank(form.getRankingOfSequence()))
			errors.add("rankingOfSequenceError", new ActionMessage("error.string.mandatory"));

		if(StringUtils.isBlank(form.getSegment()))
			errors.add("segmentError", new ActionMessage("error.string.mandatory"));
		
		if (!(errorCode = NumberValidator.checkNumber(form.getTotalSanctionedLimit(), false, BigDecimal.ZERO,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_30_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("totalSanctionedLimitError",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_30_2_STR));
		}
		
		if (!(errorCode = Validator.checkNumber(form.getLimitReleaseAmt(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_22_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("limitReleaseAmtError",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_22_2_STR));
		}
		
		if (!(errorCode = Validator.checkNumber(form.getPropertyValuation(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("propertyValuationError",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
		}
		
		if (!(errorCode = Validator.checkNumber(form.getFdAmount(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("fdAmountError",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
		}
		
		if (!(errorCode = Validator.checkNumber(form.getDrawingPower(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("drawingPowerError",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
		}
		
		if (!(errorCode = Validator.checkNumber(form.getSblcSecurityOmv(), false, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("sblcSecurityOmvError",new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
		}
		
		return errors;
	}
	
}