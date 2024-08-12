package com.integrosys.cms.ui.creditApproval;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * This class implements validation
 */
public class CreditApprovalFormValidator implements java.io.Serializable {

	public static ActionErrors validateInput(CreditApprovalForm form,
			Locale locale, String event) {

		// Only for save & edit event.

		ActionErrors errors = new ActionErrors();

		String approvalCode = form.getApprovalCode();
		String approvalName = form.getApprovalName();
		String maximumLimit = form.getMaximumLimit();
		String minimumLimit = form.getMinimumLimit();
		String segmentId = form.getSegmentId();
		String email = form.getEmail();
		String deferralPowers = form.getDeferralPowers();
		String waivingPowers = form.getWaivingPowers();
		// String chkSeg = "0";
		String errorCode = "";
		boolean isCheckMini = false;

		/***************/
		if (event.equals(CreditApprovalAction.MAKER_DRAFT_CREDIT_APPROVAL) || event.equals(CreditApprovalAction.MAKER_UPDATE_DRAFT_CREDIT_APPROVAL)) {
			/*
			 * Commented for Auto-Generated Code if (!(errorCode =
			 * Validator.checkStringWithNoSpecialCharsAndSpace(approvalCode,
			 * true, 1, 20)).equals(Validator.ERROR_NONE)) {
			 * errors.add("approvalCode", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			 * errorCode), new Integer(1), new Integer(20))); }
			 * 
			 * if( form.getApprovalCode() != null &&
			 * !(form.getApprovalCode().equals(""))){ boolean codeFlag =
			 * ASSTValidator
			 * .isValidAlphaNumStringWithoutSpace(form.getApprovalCode()); if(
			 * codeFlag == true) errors.add("spaceError", new
			 * ActionMessage("error.string.noSpace","ApprovalCode")); }
			 */
			
			
				if (!(errorCode = Validator.checkString(approvalName, true, 1,75)).equals(Validator.ERROR_NONE)) {
					errors.add("approvalName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),new Integer(0), new Integer(75)));
				}
				if (form.getApprovalName() != null && !(form.getApprovalName().equals(""))) {
					boolean nameFlag = ASSTValidator.isValidCreditApproverName(form.getApprovalName());
					if (nameFlag == true)
						errors.add("approvalName",new ActionMessage("error.string.invalidCharacter"));
				}
			
			/***********/
				if(minimumLimit==null || minimumLimit.trim().equals("") )
				{
					errors.add("minimumLimit", new ActionMessage("error.string.mandatory"));
					
				}else if (!(errorCode = Validator.checkAmount(minimumLimit, false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("minimumLimit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			}

			if(maximumLimit==null || maximumLimit.trim().equals("") )
			{
				errors.add("maximumLimit", new ActionMessage("error.string.mandatory"));
				
			}else 	if (!StringUtils.isBlank(maximumLimit)) {
				if (!(errorCode = Validator.checkAmount(maximumLimit, false, 0,IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("maximumLimit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
				}
				else if (!StringUtils.isBlank(minimumLimit) && !StringUtils.isBlank(maximumLimit) && isCheckMini!=true) {
					//if (Double.parseDouble(minimumLimit) > Double.parseDouble(maximumLimit.toString())) {
					BigDecimal bigMaximumLimit = new BigDecimal(UIUtil.removeComma(form.getMaximumLimit()));  //Phase 3 CR:comma separated
					BigDecimal bigMinimumLimit = new BigDecimal(UIUtil.removeComma(form.getMinimumLimit()));
					int intComp = bigMinimumLimit.compareTo(bigMaximumLimit);
					if (intComp>0) {
						errors.add("minimumLimit", new ActionMessage("error.feeds.maximum.value"));
					}
				}
			}
			/*
			 * if (!StringUtils.isBlank(minimumLimit)) { if (!(errorCode =
			 * Validator.checkAmount(maximumLimit, false, 0,
			 * IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18,
			 * IGlobalConstant.DEFAULT_CURRENCY, locale))
			 * .equals(Validator.ERROR_NONE)) { errors.add("minimumLimit", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
			 * errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_18_STR
			 * )); } }
			 */
				
			if( email != null && !(email.equals("")))
			{
				if( email.length() > 50 )
					errors.add("email", new ActionMessage("error.email.length"));				
				else if( ASSTValidator.isValidEmail(email) )				
					errors.add("email", new ActionMessage("error.email.format"));				
			}
			
			//non mandatory
//			if (deferralPowers == null) {
//				errors.add("deferralPowers", new ActionMessage(
//						"error.string.deferralPowers.value"));
//			}
//			if (waivingPowers == null) {
//				errors.add("waivingPowers", new ActionMessage(
//						"error.string.waivingPowers.value"));
//			}
			
			
			if (!(errorCode = Validator.checkString(form.getEmployeeId(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("employeeIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"10"));
			}
				else{
					boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getEmployeeId());
					if( codeFlag == true)
						errors.add("employeeIdError", new ActionMessage("error.string.invalidCharacter"));
					}

		} else {
			/*
			 * Commented for Auto-Generated Code if (!(errorCode =
			 * Validator.checkStringWithNoSpecialCharsAndSpace(approvalCode,
			 * true, 1, 20)).equals(Validator.ERROR_NONE)) {
			 * errors.add("approvalCode", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			 * errorCode), new Integer(1), new Integer(20))); }
			 */
			/***********/
			if(minimumLimit==null || minimumLimit.trim().equals("") )
			{
				errors.add("minimumLimit", new ActionMessage("error.string.mandatory"));
				
			}else if (!(errorCode = Validator.checkAmount(minimumLimit, false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				isCheckMini = true;
				errors.add("minimumLimit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));

			}
			/**************/
			if (!(errorCode = Validator.checkString(approvalName, true, 1,75)).equals(Validator.ERROR_NONE)) {
				errors.add("approvalName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),new Integer(0), new Integer(75)));
			}
			if (form.getApprovalName() != null && !(form.getApprovalName().equals(""))) {
				boolean nameFlag = ASSTValidator.isValidCreditApproverName(form.getApprovalName());
				if (nameFlag == true)
					errors.add("approvalName",new ActionMessage("error.string.invalidCharacter"));
			}
			
			if(maximumLimit==null || maximumLimit.trim().equals("") )
			{
				errors.add("maximumLimit", new ActionMessage("error.string.mandatory"));
				
			}else if (!(errorCode = Validator.checkAmount(maximumLimit, false,0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("maximumLimit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			}
			else if (!StringUtils.isBlank(minimumLimit)&& !StringUtils.isBlank(maximumLimit) && isCheckMini!=true) {
				//if (Double.parseDouble(minimumLimit) > Double.parseDouble(maximumLimit)) {
				BigDecimal bigMaximumLimit = new BigDecimal(UIUtil.removeComma(form.getMaximumLimit()));
				BigDecimal bigMinimumLimit = new BigDecimal(UIUtil.removeComma(form.getMinimumLimit()));
				int intComp = bigMinimumLimit.compareTo(bigMaximumLimit);
				if (intComp>0) {
				
					errors.add("minimumLimit", new ActionMessage("error.feeds.maximum.value"));
				}
			}

			if( email != null && !(email.equals("")))
			{
				if( email.length() > 50 )
					errors.add("email", new ActionMessage("error.email.length"));				
				else if( ASSTValidator.isValidEmail(email) )				
					errors.add("email", new ActionMessage("error.email.format"));				
			}
			
			
			if (!(errorCode = Validator.checkString(form.getEmployeeId(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("employeeIdError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"10"));
			}
				else{
					boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getEmployeeId());
					if( codeFlag == true)
						errors.add("employeeIdError", new ActionMessage("error.string.invalidCharacter"));
					}

		}
	/*	if (event!= null && event.equals("EODSyncMasters")) {
		

			if (!(errorCode = Validator.checkString(approvalName, true, 1,75)).equals(Validator.ERROR_NONE)) {
				errors.add("approvalName", new ActionMessage("For USER_NAME valid range is between 0 and 75 character(s)"));
			}
			if (form.getApprovalName() != null && !(form.getApprovalName().equals(""))) {
				boolean nameFlag = ASSTValidator.isValidCreditApproverName(form.getApprovalName());
				if (nameFlag == true)
					errors.add("approvalName",new ActionMessage("USER_NAME has Invalid characters"));
			}
		
		if(maximumLimit==null || maximumLimit.trim().equals("") )
		{
			errors.add("maximumLimit", new ActionMessage("DP_VALUE is mandatory"));
			
		}else 	if (!StringUtils.isBlank(maximumLimit)) {
			if (!(errorCode = Validator.checkAmount(maximumLimit, false, 0,IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("maximumLimit", new ActionMessage("DP_VALUE Length Should be Number(20,2)"));
			}
			else if (!StringUtils.isBlank(minimumLimit) && !StringUtils.isBlank(maximumLimit) && isCheckMini!=true) {
				//if (Double.parseDouble(minimumLimit) > Double.parseDouble(maximumLimit.toString())) {
				BigDecimal bigMaximumLimit = new BigDecimal(form.getMaximumLimit());
				BigDecimal bigMinimumLimit = new BigDecimal("0");
				int intComp = bigMinimumLimit.compareTo(bigMaximumLimit);
				if (intComp>0) {
					errors.add("minimumLimit", new ActionMessage("DP_VALUE value can't be less then zero"));
				}
			}
		}
					
		if( email != null && !(email.equals("")))
		{
			if( email.length() > 50 )
				errors.add("email", new ActionMessage("USER_EMAIL valid range is between 0 and 50 character(s)"));				
			else if( ASSTValidator.isValidEmail(email) )				
				errors.add("email", new ActionMessage("USER_EMAIL format is invalid"));				
		}
	}*/
		
		return errors;
	}
	// end validateInput

}
