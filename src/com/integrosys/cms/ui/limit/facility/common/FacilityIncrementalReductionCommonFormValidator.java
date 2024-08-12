package com.integrosys.cms.ui.limit.facility.common;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class FacilityIncrementalReductionCommonFormValidator {
	public static ActionErrors validateInput(FacilityIncrementalReductionCommonForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		
		String event = form.getEvent();
		String errorCode = null;
		
		if (!(errorCode = Validator.checkNumber(form.getAmountApplied(), true, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("amountApplied", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
		}  	
		
		if (!(errorCode = Validator.checkNumber(form.getIncrementalReductionLimit(), true, 0,
				IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2, 3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("incrementalReductionLimit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_2_STR));
		}  		
		
		if (!(errorCode = Validator.checkDate(form.getApplicationDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("applicationDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
		}		

		if (!(errorCode = Validator.checkDate(form.getOfferAcceptanceDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("offerAcceptanceDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
		}		
		
		if (!(errorCode = Validator.checkDate(form.getApprovedDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("approvedDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
		}			
		
		if (StringUtils.isBlank(form.getApprovedBy())) {
			errors.add("approvedBy", new ActionMessage("error.mandatory"));
		}
		
		if (StringUtils.isBlank(form.getFacilityStatus())) {
			errors.add("facilityStatus", new ActionMessage("error.mandatory"));
		} else {
			if (ICMSConstant.FACILITY_STATUS_CANCELLED.equals(form.getFacilityStatus()) ||
					ICMSConstant.FACILITY_STATUS_REJECTED.equals(form.getFacilityStatus())) {
				if (StringUtils.isBlank(form.getCancelRejectCode())) {
					errors.add("cancelRejectCode", new ActionMessage("error.mandatory"));
				}
				if (StringUtils.isBlank(form.getCancelRejectDate())) {
					errors.add("cancelRejectDate", new ActionMessage("error.mandatory"));
				}
			}
		}
		
		if (!(errorCode = Validator.checkString(form.getSolicitorReference(), false, 0,
				40)).equals(Validator.ERROR_NONE)) {
			errors.add("solicitorReference", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					40 + ""));
		}			
				
		if (!(errorCode = Validator.checkString(form.getSolicitorName(), false, 0,
				20)).equals(Validator.ERROR_NONE)) {
			errors.add("solicitorName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					20 + ""));
		}			
		
		if (StringUtils.isBlank(form.getRequestReasonCode())) {
			errors.add("requestReasonCode", new ActionMessage("error.mandatory"));
		}
		
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
