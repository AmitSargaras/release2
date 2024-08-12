//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.collateral.MaintainInsuranceGCForm;
import com.integrosys.cms.ui.collateral.assetbased.AssetBasedValidator;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetGenChargeValidator {

	public static ActionErrors validateInput(ActionForm form, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		
		
		if(form instanceof AssetGenChargeForm){
			AssetGenChargeForm aForm = (AssetGenChargeForm) form;
		if ("true".equals(aForm.getUserAccess())) {
			errors = AssetBasedValidator.validateInput(aForm, locale);
			if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
				AssetGenChargeValidationHelper.validateInput(aForm, locale, errors);
			}
		}
		  if(!aForm.getEvent().equals("reject")){
//		if (aForm.getFundedShare() != null) {
//			if (!(errorCode = Validator.checkAmount(aForm.getFundedShare(), true, 0,
//					100, IGlobalConstant.DEFAULT_CURRENCY, locale))
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("fundedSharePctError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
//						"0", "100"));
//				//DefaultLogger.debug(LOGOBJ, "aForm.getScrapValue() = " + aForm.getScrapValue());
//			}
//		}
			 
			  
			  if (!(errorCode = Validator.checkNumber(aForm.getTermLoanOutstdAmt(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, 3, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("termLoanOutstdAmtErr", new ActionMessage(errorMessage, "0",
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, "2"));
				}
			  
			  if (!(errorCode = Validator.checkNumber(aForm.getMarginAssetCover(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_4_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("marginAssetCoverErr", new ActionMessage(errorMessage, "0",
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_4_2, "2"));
				}
			  
			  
			  if (!(errorCode = Validator.checkNumber(aForm.getRecvGivenByClient(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, 3, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("recvGivenByClientErr", new ActionMessage(errorMessage, "0",
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2new, "2"));
				}
			  
			  
			  if (aForm.getDpShare() != null && !"".equals(aForm.getDpShare())) {
					if (!(errorCode = Validator.checkAmount(aForm.getDpShare(), true, 0,
							100, IGlobalConstant.DEFAULT_CURRENCY, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("dpSharePctError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
								"0", "100"));
						//DefaultLogger.debug(LOGOBJ, "aForm.getScrapValue() = " + aForm.getScrapValue());
					}
				}
			  
			  
			
			  
			  if (!(errorCode = Validator.checkNumber(aForm.getTermLoanOutstdAmt(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("termLoanOutstdAmtErr", new ActionMessage(errorMessage, "0",
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2, "2"));
				}
			  
			  if (!(errorCode = Validator.checkNumber(aForm.getMarginAssetCover(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_4_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("marginAssetCoverErr", new ActionMessage(errorMessage, "0",
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_4_2, "2"));
				}
			  
			  
			  if (!(errorCode = Validator.checkNumber(aForm.getRecvGivenByClient(), false, 0,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2, 3, locale)).equals(Validator.ERROR_NONE)) {
					String errorMessage = ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode);
					if (errorMessage.equals("error.number.decimalexceeded")) {
						errorMessage = "error.number.moredecimalexceeded";
					}
					errors.add("recvGivenByClientErr", new ActionMessage(errorMessage, "0",
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_25_2, "2"));
				}
			  
			  
			  
		if (aForm.getCalculatedDP() != null) {
			if (!(errorCode = Validator.checkAmount(aForm.getCalculatedDP(), false, IGlobalConstant.MINIMUM_ALLOWED_AMOUNT_15_2,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("calculatedDPError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"-999999999999999.99", "999999999999999.99"));
				//DefaultLogger.debug(LOGOBJ, "aForm.getScrapValue() = " + aForm.getScrapValue());
			}
		}
		  }
		
	}else if(form instanceof MaintainInsuranceGCForm){
		MaintainInsuranceGCForm aForm = (MaintainInsuranceGCForm) form;
		
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		
		if("maker_submit_insurance_pending".equals(aForm.getEvent()) || "maker_edit_inspending_list".equals(aForm.getEvent()) || "maker_update_inspending_list".equals(aForm.getEvent())){
			
			if (aForm.getOriginalTargetDate() == null || "".equals(aForm.getOriginalTargetDate().trim())) {
				
				errors.add("originalTargetDateError", new ActionMessage("error.string.mandatory"));
			}
			
			if (StringUtils.isBlank(aForm.getOldPolicyNo())) {
				
				errors.add("oldPolicyNoError", new ActionMessage("error.string.mandatory"));
			}
		}else if("maker_submit_insurance_deferred".equals(aForm.getEvent()) || "maker_edit_insdeferred_list".equals(aForm.getEvent())  || "maker_update_insdeferred_list".equals(aForm.getEvent())){
			if (aForm.getOriginalTargetDate() == null || "".equals(aForm.getOriginalTargetDate().trim())) {
				
				errors.add("originalTargetDateError", new ActionMessage("error.string.mandatory"));
			}
			/*if (StringUtils.isBlank(aForm.getOldPolicyNo())) { 
				
				errors.add("oldPolicyNoError", new ActionMessage("error.string.mandatory"));
			}*/
			if (aForm.getDateDeferred() == null || "".equals(aForm.getDateDeferred().trim())) {
				
				errors.add("dateDeferredError", new ActionMessage("error.string.mandatory"));
			}
			if (aForm.getNextDueDate() == null || "".equals(aForm.getNextDueDate().trim())) {

				errors.add("nextDueDateError", new ActionMessage("error.string.mandatory"));
			} else if (aForm.getDateDeferred() != null && !"".equals(aForm.getDateDeferred().trim())
					&& aForm.getOriginalTargetDate() != null && !"".equals(aForm.getOriginalTargetDate().trim())) {
				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getOriginalTargetDate()))
						.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDateDeferred())))) {
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getNextDueDate()))
							.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getDateDeferred())))) {
						errors.add("nextDueDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Next due date", "Date deferred"));
					}
				} else if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getNextDueDate()))
						.before(DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getOriginalTargetDate())))) {
					errors.add("nextDueDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Next due date", "Original target date"));
				}
			}

			if (aForm.getCreditApprover() == null || "".equals(aForm.getCreditApprover().trim())) {
				
				errors.add("creditApproverError", new ActionMessage("error.string.mandatory"));
			}

		}else if("maker_submit_insurance_received".equals(aForm.getEvent()) 
				|| "maker_edit_insreceived_list".equals(aForm.getEvent()) 
				|| "maker_update_insreceived_list".equals(aForm.getEvent())){
			
			if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getInsuredAgainst(), false)).equals(Validator.ERROR_NONE)) {
				errors.add("insuredAgainst",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0","250"));
			}
			
			if (!(errorCode = Validator.checkString(aForm.getInsuredAddress(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("insuredAddress", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0","250"));
			}
			if (aForm.getInsurancePolicyNo() == null || "".equals(aForm.getInsurancePolicyNo().trim())) {
				errors.add("insurancePolicyNoError", new ActionMessage("error.string.mandatory"));
				
			}
			
			if (aForm.getInsuranceCoverge() == null || "".equals(aForm.getInsuranceCoverge().trim())) {
				errors.add("insuranceCoverageError", new ActionMessage("error.string.mandatory"));
			}
			if (aForm.getCoverNoteNo() != null && !("".equals(aForm.getCoverNoteNo().trim()))) {
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getCoverNoteNo());
				if (nameFlag == true)
					errors.add("coverNoteNoError", new ActionMessage("error.string.invalidCharacter"));
			}
			
			if (aForm.getInsuranceCompany() == null || "".equals(aForm.getInsuranceCompany().trim())) {
				errors.add("insuranceCompanyError", new ActionMessage("error.string.mandatory"));
				
			}
			
			if (aForm.getInsuranceCurrency() == null
					|| "".equals(aForm.getInsuranceCurrency().trim())) {
				errors.add("insuranceCurrencyError", new ActionMessage("error.string.mandatory"));
				
			}
			
			if (aForm.getInsurancePolicyAmt() == null
					|| "".equals(aForm.getInsurancePolicyAmt().trim())) {

				errors.add("insurancePolicyAmtError", new ActionMessage("error.string.mandatory"));
				
			} else if (!(errorCode = Validator.checkNumber(aForm
					.getInsurancePolicyAmt(), true, 1, 99999999999999999999.99,
					3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("insurancePolicyAmtLengthError", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),"1", "99999999999999999999.99"));
			}
			if (aForm.getInsuredAmount() == null || "".equals(aForm.getInsuredAmount().trim())) {

				errors.add("insuredAmtError", new ActionMessage("error.string.mandatory"));
				
			} else if (!(errorCode = Validator.checkNumber(aForm.getInsuredAmount(), true, 1, 99999999999999999999.99,3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("insuredAmtLengthError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),"1", "99999999999999999999.99"));
			}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			if (aForm.getEffectiveDate() == null || "".equals(aForm.getEffectiveDate().trim())) {
				errors.add("effectiveDateError", new ActionMessage("error.string.mandatory"));
				
			}
		else{
			
			if(!(aForm.getEffectiveDate() == null || "".equals(aForm.getEffectiveDate().trim()))
				&& !(aForm.getExpiryDate() == null || "".equals(aForm.getExpiryDate().trim()))){
			
				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getEffectiveDate())).after(
						DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDate())))) {
					errors.add("effectiveDateError", new ActionMessage("error.date.compareDate.cannotBelater", "Effective Date","Expiry Date"));
				}	
				
			
		}
		}
			
			if (aForm.getReceivedDate() == null || "".equals(aForm.getReceivedDate().trim())) {
				errors.add("receivedDateError", new ActionMessage("error.string.mandatory"));
				
			}
		else{
			
			if(!(aForm.getReceivedDate() == null || "".equals(aForm.getReceivedDate().trim()))){
			
				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate())).after(
						DateUtil.clearTime(DateUtil.convertDate(locale, dateFormat.format(new Date()))))) {
					errors.add("receivedDateError", new ActionMessage("error.date.compareDate.cannotBelater", "Received Date","current date"));
				}	
				
			
		}
		}
			if (aForm.getExpiryDate() == null || "".equals(aForm.getExpiryDate().trim())) {
				errors.add("expiryDateError", new ActionMessage("error.string.mandatory"));
				
			}else{
				
				if(!(aForm.getEffectiveDate() == null || "".equals(aForm.getEffectiveDate().trim()))){
				
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, dateFormat.format(new Date()))))) {
						errors.add("expiryDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Expiry Date","Current date"));
					}	
					else if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDate())).before(
						DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getEffectiveDate())))) {
					errors.add("expiryDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Expiry Date","Effective Date"));
				}
			}
			}
			
			if ( null == aForm.getInsurancePremium() || "".equals(aForm.getInsurancePremium().trim())) {

				errors.add("insurancePremiumError", new ActionMessage("error.string.mandatory"));
				
			}
			else if (!(errorCode = Validator.checkNumber(aForm.getInsurancePremium(), false, 1, 99999999999999999999.99,3, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("insurancePremiumLengthError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),"1", "99999999999999999999.99"));
			}
			
			if(StringUtils.isBlank(aForm.getRemark())) {
				errors.add("remarkError", new ActionMessage("error.string.mandatory"));
			}
			
		}else if("maker_submit_insurance_waived".equals(aForm.getEvent()) || "maker_edit_inswaived_list".equals(aForm.getEvent()) || "maker_update_inswaived_list".equals(aForm.getEvent())){
			if (aForm.getWaivedDate() == null || "".equals(aForm.getWaivedDate().trim())) {
				
				errors.add("waivedDateError", new ActionMessage("error.string.mandatory"));
			}
			if (aForm.getCreditApprover() == null || "".equals(aForm.getCreditApprover().trim())) {
				
				errors.add("creditApproverError", new ActionMessage("error.string.mandatory"));
			}
		}else{
		if (aForm.getInsuranceRequired() == null
					|| "".equals(aForm.getInsuranceRequired().trim())) {
				errors.add("insuranceRequiredError", new ActionMessage(
						"error.string.mandatory"));
				
			}
		else if ("Component_wise".equals(aForm.getInsuranceRequired().trim())){
			if (aForm.getSelectComponent() == null
					|| "".equals(aForm.getSelectComponent().trim())) {
				errors.add("selectComponentError", new ActionMessage(
						"error.string.mandatory"));
				
			}
		}
		
		
		if (aForm.getInsurancePolicyNo() == null
				|| "".equals(aForm.getInsurancePolicyNo().trim())) {
			errors.add("insurancePolicyNoError", new ActionMessage(
					"error.string.mandatory"));
			
		}
		
		if (aForm.getInsuranceCoverge() == null
				|| "".equals(aForm.getInsuranceCoverge().trim())) {
			errors.add("insuranceCoverageError", new ActionMessage(
					"error.string.mandatory"));
		}
		if (aForm.getCoverNoteNo() != null && !("".equals(aForm.getCoverNoteNo().trim()))) {
			boolean nameFlag = ASSTValidator
					.isValidAlphaNumStringWithoutSpace(aForm.getCoverNoteNo());
			if (nameFlag == true)
				errors.add("coverNoteNoError", new ActionMessage(
						"error.string.invalidCharacter"));
		}
		
		if (aForm.getInsuranceCompany() == null
				|| "".equals(aForm.getInsuranceCompany().trim())) {
			errors.add("insuranceCompanyError", new ActionMessage(
					"error.string.mandatory"));
			
		}
		
		if (aForm.getInsuranceCurrency() == null
				|| "".equals(aForm.getInsuranceCurrency().trim())) {
			errors.add("insuranceCurrencyError", new ActionMessage(
					"error.string.mandatory"));
			
		}
		
		if (aForm.getInsurancePolicyAmt() == null
				|| "".equals(aForm.getInsurancePolicyAmt().trim())) {

			errors.add("insurancePolicyAmtError", new ActionMessage(
					"error.string.mandatory"));
			
		} else if (!(errorCode = Validator.checkNumber(aForm
				.getInsurancePolicyAmt(), true, 0, 99999999999999999999.99,
				3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("insurancePolicyAmtLengthError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "99999999999999999999.99"));
		}
		if (aForm.getInsuredAmount() == null
				|| "".equals(aForm.getInsuredAmount().trim())) {

			errors.add("insuredAmtError", new ActionMessage(
					"error.string.mandatory"));
			
		} else if (!(errorCode = Validator.checkNumber(aForm
				.getInsuredAmount(), true, 0, 99999999999999999999.99,
				3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("insuredAmtLengthError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "99999999999999999999.99"));
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		if (aForm.getEffectiveDate() == null
				|| "".equals(aForm.getEffectiveDate().trim())) {
			errors.add("effectiveDateError", new ActionMessage(
					"error.string.mandatory"));
			
		}
	else{
		
		if(!(aForm.getEffectiveDate() == null
				|| "".equals(aForm.getEffectiveDate().trim()))){
		
			if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getEffectiveDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, dateFormat.format(new Date()))))) {
				errors.add("effectiveDateError", new ActionMessage("error.date.compareDate.cannotBelater", "Effective Date",
								"current date"));
			}	
			
		
	}
	}
		
		if (aForm.getReceivedDate() == null
				|| "".equals(aForm.getReceivedDate().trim())) {
			errors.add("receivedDateError", new ActionMessage(
					"error.string.mandatory"));
			
		}
	else{
		
		if(!(aForm.getReceivedDate() == null
				|| "".equals(aForm.getReceivedDate().trim()))){
		
			if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate())).after(
					DateUtil.clearTime(DateUtil.convertDate(locale, dateFormat.format(new Date()))))) {
				errors
						.add("receivedDateError", new ActionMessage("error.date.compareDate.cannotBelater", "Received Date",
								"current date"));
			}	
			
		
	}
	}
		if (aForm.getExpiryDate() == null
				|| "".equals(aForm.getExpiryDate().trim())) {
			errors.add("expiryDateError", new ActionMessage(
					"error.string.mandatory"));
			
		}else{
			
			if(!(aForm.getEffectiveDate() == null
					|| "".equals(aForm.getEffectiveDate().trim()))){
			
				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDate())).before(
						DateUtil.clearTime(DateUtil.convertDate(locale, dateFormat.format(new Date()))))) {
					errors
							.add("expiryDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Insurance Expiry Date",
									"current date"));
				}	
				else if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDate())).before(
					DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getEffectiveDate())))) {
				errors
						.add("expiryDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Insurance Expiry Date",
								"Effective Date Of Insurance"));
			}
		}
		}
		if (!(errorCode = Validator.checkNumber(aForm
				.getInsurancePremium(), false, 0, 99999999999999999999.99,
				3, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("insurancePremiumLengthError", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", "99999999999999999999.99"));
		}
		
	}
	}
		return errors;	
	}
	
}
