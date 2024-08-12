package com.integrosys.cms.ui.collateral.addtionalDocumentFacilityDetails;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.host.stp.common.IStpConstants;
import com.integrosys.cms.ui.collateral.CollateralAction;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2005/08/24 08:42:19 $ Tag: $Name: $
 */

public class AddtionalDocumentFacilityDetailsValidator {
	public static ActionErrors validateInput(AddtionalDocumentFacilityDetailsForm aForm, Locale locale) {
		String errorCode = null; 
		ActionErrors errors = new ActionErrors();
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_STR).toString();
		
		boolean isMandatory = (aForm.getEvent().equals(CollateralAction.EVENT_CREATE) || aForm.getEvent().equals(
				CollateralAction.EVENT_UPDATE));
		
		 //Uma Khot::Insurance Deferral maintainance
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
		
		
		if("maker_create_add_fac_doc_det_submit".equals(aForm.getEvent()) || "maker_edit_add_fac_doc_det_list".equals(aForm.getEvent())) {
			if (aForm.getDocFacilityCategory() == null || "".equals(aForm.getDocFacilityCategory().trim())) {
				
				errors.add("docFacilityCategoryError", new ActionMessage("error.string.mandatory"));
			}
			if (aForm.getDocFacilityType() == null || "".equals(aForm.getDocFacilityType().trim())) {
				
				errors.add("docFacilityTypeError", new ActionMessage("error.string.mandatory"));
			}
			if (!(errorCode = Validator.checkAmount(aForm.getDocFacilityAmount(), true, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("docFacilityAmountError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
			}
			
		} 
		
		
		
		
		
		
		/*if("maker_submit_addtionalDocumentFacilityDetails_pending".equals(aForm.getEvent()) || "maker_edit_inspending_list".equals(aForm.getEvent()) || "maker_update_inspending_list".equals(aForm.getEvent())){
			
			if (aForm.getOriginalTargetDate() == null || "".equals(aForm.getOriginalTargetDate().trim())) {
				
				errors.add("originalTargetDateError", new ActionMessage("error.string.mandatory"));
			}else if(!(aForm.getOriginalTargetDate() == null || "".equals(aForm.getOriginalTargetDate().trim()))){
				
				if(!(aForm.getOriginalTargetDate() == null || "".equals(aForm.getOriginalTargetDate().trim()))){
			
//				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getOriginalTargetDate())).before(
//						DateUtil.clearTime(DateUtil.convertDate(locale,dateFormat2.format(new Date()))))) {
//					errors.add("originalTargetDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier",  "Original Target Date",
//					"current date"));
//					}	
				}
			}
		
		}else if("maker_submit_addtionalDocumentFacilityDetails_deferred".equals(aForm.getEvent()) || "maker_edit_insdeferred_list".equals(aForm.getEvent())  || "maker_update_insdeferred_list".equals(aForm.getEvent())){
			if (aForm.getOriginalTargetDate() == null || "".equals(aForm.getOriginalTargetDate().trim())) {
				
				errors.add("originalTargetDateError", new ActionMessage("error.string.mandatory"));
			}
			
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

		}else if("maker_submit_addtionalDocumentFacilityDetails_received".equals(aForm.getEvent()) || "maker_edit_insreceived_list".equals(aForm.getEvent()) || "maker_update_insreceived_list".equals(aForm.getEvent())){
			
			if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getInsuredAgainst(), false)).equals(Validator.ERROR_NONE)) {
				errors.add("insuredAgainst",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0","250"));
			}
			
			if (!(errorCode = Validator.checkString(aForm.getInsuredAddress(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("insuredAddress", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0","250"));
			}
			if (aForm.getInsPolicyNum() == null || "".equals(aForm.getInsPolicyNum().trim())) {
				errors.add("insPolicyNum", new ActionMessage("error.string.mandatory"));
				
			}
			
			if (aForm.getInsurerName() == null || "".equals(aForm.getInsurerName().trim())) {
				errors.add("insurerName", new ActionMessage("error.string.mandatory"));
			}
//			if (aForm.getCoverNoteNo() != null && !("".equals(aForm.getCoverNoteNo().trim()))) {
//				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getCoverNoteNo());
//				if (nameFlag == true)
//					errors.add("coverNoteNoError", new ActionMessage("error.string.invalidCharacter"));
//			}
			
//			if (StringUtils.isBlank(aForm.getInsPolicyNum())) {
//				if (!(errorCode = Validator.checkString(aForm.getInsPolicyNum(), (isMandatory && true), 1, 30))
//						.equals(Validator.ERROR_NONE)) {
//					errors.add("insPolicyNum", new ActionMessage("error.string.mandatory", "1", "30"));
//				}
//			}
//			else if(aForm.getInsPolicyNum().length() > 30)
//			{
//					errors.add("insPolicyNum", new ActionMessage("error.string.length30"));
//			}
//			
//			if (StringUtils.isBlank(aForm.getInsurerName())) {
//				if (!(errorCode = Validator.checkString(aForm.getInsurerName(), (isMandatory && true), 1, 50))
//						.equals(Validator.ERROR_NONE)) {
//					errors.add("insurerName", new ActionMessage("error.string.mandatory", "1", "50"));
//				}
//			}
//			else if(aForm.getInsurerName().length() > 50)
//			{
//					errors.add("insurerName", new ActionMessage("error.string.length50"));
//			}
//			else
//			{	
//				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getInsurerName());
//				if( codeFlag == true)
//					errors.add("insurerName", new ActionMessage("error.string.invalidCharacter"));
//			}
			
			if (aForm.getInsPolicyCurrency() == null
							|| "".equals(aForm.getInsPolicyCurrency().trim())) {
						errors.add("insPolicyCurrency", new ActionMessage("error.string.mandatory"));
						
					}
			
//			if (aForm.getTypeOfPerils1() == null || "".equals(aForm.getTypeOfPerils1().trim())) {
//				errors.add("typeOfPerils1", new ActionMessage("error.string.mandatory"));
//			}
			
//			if (!(errorCode = Validator.checkString(aForm.getInsPolicyCurrency(), (isMandatory && true), 1, 30))
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("insPolicyCurrency", new ActionMessage("error.string.mandatory", "1", "25"));
//			}
//			
			if (!(errorCode = Validator.checkString(aForm.getTypeOfPerils1(), true, 1, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("typeOfPerils1", new ActionMessage("error.string.mandatory", "1", "50"));
			}
			
			try {
				if (!(errorCode = Validator.checkAmount(aForm.getInsurableAmt(), true, 1,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("insurableAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
							maximumAmt));
				}
				// Andy Wong, 16 Feb 2009: check insured amt > 0
				
				if (aForm.getInsuredAmt() == null || "".equals(aForm.getInsuredAmt().trim())) {
				
					errors.add("insuredAmt", new ActionMessage("error.string.mandatory"));
								
				}else if (!(errorCode = Validator.checkAmount(aForm.getInsuredAmt(), (isMandatory && true), 1,
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("insuredAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
							maximumAmt));
				}
				else if (StringUtils.isNotBlank(aForm.getInsuredAmt())
						&& StringUtils.isNotBlank(aForm.getInsurancePremium())) {
					if (Double.valueOf(aForm.getInsurancePremium()).doubleValue() > Double.valueOf(aForm.getInsuredAmt())
							.doubleValue()) {
						errors.add("insuredAmt", new ActionMessage("error.amount.not.greaterthan", "Insurance Premium",
								"Insured Amount"));
					}
				}
			}
			catch (Exception e) {
				DefaultLogger.error("AddtionalDocumentFacilityDetailsValidator", "Exception throw at mapperUtil convert String to double");
			}
			
//			if (aForm.getInsurableAmt() == null
//					|| "".equals(aForm.getInsurableAmt().trim())) {
//
//				errors.add("insurableAmt", new ActionMessage("error.string.mandatory"));
//				
//			} else if (!(errorCode = Validator.checkAmount(aForm.getInsurableAmt(), true, 0,
//							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20, IGlobalConstant.DEFAULT_CURRENCY, locale))
//							.equals(Validator.ERROR_NONE)) {
//						errors.add("insurableAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
//								maximumAmt));
//			}
//			if (aForm.getInsuredAmt() == null || "".equals(aForm.getInsuredAmt().trim())) {
//
//				errors.add("insuredAmt", new ActionMessage("error.string.mandatory"));
//				
//			}else if (!(errorCode = Validator.checkAmount(aForm.getInsuredAmt(), (isMandatory && true), 1,
//					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20, IGlobalConstant.DEFAULT_CURRENCY, locale))
//					.equals(Validator.ERROR_NONE)) {
//				errors.add("insuredAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
//						maximumAmt));
//			}else{
//				
//				try{
//				if (StringUtils.isNotBlank(aForm.getInsuredAmt()) && StringUtils.isNotBlank(aForm.getInsurancePremium())) {
//					if (Double.valueOf(aForm.getInsurancePremium()).doubleValue() > Double.valueOf(aForm.getInsuredAmt())
//							.doubleValue()) {
//						errors.add("insuredAmt", new ActionMessage("error.amount.not.greaterthan", "Insurance Premium",
//								"Insured Amount"));
//					}
//				}
//				}catch(Exception e){
//						DefaultLogger.error("AddtionalDocumentFacilityDetailsValidator", "Exception throw at mapperUtil convert String to double");
//					}
//				}
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			
			if (aForm.getEffectiveDateIns() == null || "".equals(aForm.getEffectiveDateIns().trim())) {
				errors.add("effectiveDateIns", new ActionMessage("error.string.mandatory"));
				
			}
		else{
			
			if(!(aForm.getEffectiveDateIns() == null || "".equals(aForm.getEffectiveDateIns().trim()))  &&  !(aForm.getExpiryDateIns() == null || "".equals(aForm.getExpiryDateIns().trim()))  ){
			
				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getEffectiveDateIns())).after(
						DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDateIns())))) {
					errors.add("effectiveDateIns", new ActionMessage("error.date.compareDate.cannotBelater", "Effective Date","Expiry Date"));
				}	
				
			
		}
		}
			
			if (aForm.getReceivedDate() == null || "".equals(aForm.getReceivedDate().trim())) {
				errors.add("receivedDate", new ActionMessage("error.string.mandatory"));
				
			}
		else{
			
			if(!(aForm.getReceivedDate() == null || "".equals(aForm.getReceivedDate().trim()))){
			
				if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getReceivedDate())).after(
						DateUtil.clearTime(DateUtil.convertDate(locale, dateFormat.format(new Date()))))) {
					errors.add("receivedDate", new ActionMessage("error.date.compareDate.cannotBelater", "Received Date","current date"));
				}	
				
			
		}
		}
			if (aForm.getExpiryDateIns() == null || "".equals(aForm.getExpiryDateIns().trim())) {
				errors.add("expiryDateIns", new ActionMessage("error.string.mandatory"));
				
			}else{
				
				if(!(aForm.getEffectiveDateIns() == null || "".equals(aForm.getEffectiveDateIns().trim()))){
				
					if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDate())).before(
							DateUtil.clearTime(DateUtil.convertDate(locale, dateFormat.format(new Date()))))) {
						errors.add("expiryDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Expiry Date","current date"));
					}	
					else  if (DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getExpiryDateIns())).before(
						DateUtil.clearTime(DateUtil.convertDate(locale, aForm.getEffectiveDateIns())))) {
					errors.add("expiryDateIns", new ActionMessage("error.date.compareDate.cannotBeEarlier", "Expiry Date","Effective Date"));
				}
			}
			}
			
			if ( null == aForm.getInsurancePremium() || "".equals(aForm.getInsurancePremium().trim())) {

				errors.add("addtionalDocumentFacilityDetailsPremium", new ActionMessage("error.string.mandatory"));
				
			}
			if (StringUtils.isNotBlank(aForm.getInsurancePremium())) {
				if (!(errorCode = Validator.checkAmount(aForm.getInsurancePremium(), false, 1,IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("addtionalDocumentFacilityDetailsPremium", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
							"1", maximumAmt));
				}
			}
			
			
		}else if("maker_submit_addtionalDocumentFacilityDetails_waived".equals(aForm.getEvent()) || "maker_edit_inswaived_list".equals(aForm.getEvent()) || "maker_update_inswaived_list".equals(aForm.getEvent())){
			if (aForm.getWaivedDate() == null || "".equals(aForm.getWaivedDate().trim())) {
				
				errors.add("waivedDateError", new ActionMessage("error.string.mandatory"));
			}
			if (aForm.getCreditApprover() == null || "".equals(aForm.getCreditApprover().trim())) {
				
				errors.add("creditApproverError", new ActionMessage("error.string.mandatory"));
			}
		}else{
		

		// if (!(errorCode = Validator.checkString(aForm.getInsPolicyNum(),
		// (isMandatory && true), 1, 30))
		// .equals(Validator.ERROR_NONE)) {
		// errors.add("insPolicyNum", new
		// ActionMessage("error.string.mandatory", "1", "25"));
		// }
		
		
		if (StringUtils.isBlank(aForm.getInsPolicyNum())) {
			if (!(errorCode = Validator.checkString(aForm.getInsPolicyNum(), (isMandatory && true), 1, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insPolicyNum", new ActionMessage("error.string.mandatory", "1", "30"));
			}
		}
		else if(aForm.getInsPolicyNum().length() > 30)
		{
				errors.add("insPolicyNum", new ActionMessage("error.string.length30"));
		}
		else
		{	
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getInsPolicyNum());
			if( codeFlag == true)
				errors.add("insPolicyNum", new ActionMessage("error.string.invalidCharacter"));
		}

		if (StringUtils.isBlank(aForm.getInsurerName())) {
			if (!(errorCode = Validator.checkString(aForm.getInsurerName(), (isMandatory && true), 1, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insurerName", new ActionMessage("error.string.mandatory", "1", "50"));
			}
		}
		else if(aForm.getInsurerName().length() > 50)
		{
				errors.add("insurerName", new ActionMessage("error.string.length50"));
		}
		else
		{	
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(aForm.getInsurerName());
			if( codeFlag == true)
				errors.add("insurerName", new ActionMessage("error.string.invalidCharacter"));
		}

		if (!(errorCode = Validator.checkString(aForm.getInsuranceType(), (isMandatory && true), 1, 25))
				.equals(Validator.ERROR_NONE)) {
			errors.add("addtionalDocumentFacilityDetailsType", new ActionMessage("error.string.mandatory", "1", "25"));
		}
		Date expiryDate = null;
		Date issueDate = null;
		Date effectiveDate = null;
		if (!(errorCode = Validator.checkDate(aForm.getEffectiveDateIns(), (isMandatory && true), locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("effectiveDateIns", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			effectiveDate = DateUtil.convertDate(locale, aForm.getEffectiveDateIns());
		}
		// validation added by sachin Patil
		if (!(errorCode = Validator.checkDate(aForm.getReceivedDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("receivedDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		
			if (!(errorCode = Validator.checkString(aForm.getTypeOfPerils1(), true, 1, 50))
					.equals(Validator.ERROR_NONE)) {
				errors.add("typeOfPerils1", new ActionMessage("error.string.mandatory", "1", "50"));
			}
		
		if (!(errorCode = Validator.checkDate(aForm.getExpiryDateIns(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("expiryDateIns", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else if (StringUtils.isNotBlank(aForm.getExpiryDateIns())) {
			expiryDate = DateUtil.convertDate(locale, aForm.getExpiryDateIns());
		}
		if (isMandatory) {
			if ((effectiveDate != null) && (expiryDate != null) && effectiveDate.after(expiryDate)) {
				errors.add("effectiveDateIns", new ActionMessage("error.date.compareDate.greater", "Effective Date",
						"Expiry Date"));
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getInsPolicyCurrency(), (isMandatory && true), 1, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insPolicyCurrency", new ActionMessage("error.string.mandatory", "1", "25"));
		}

		if (!(errorCode = Validator.checkString(aForm.getBankCustomerArrange(), (isMandatory && true), 1, 40))
				.equals(Validator.ERROR_NONE)) {
			errors.add("bankCustomerArrange", new ActionMessage("error.string.mandatory", "1", "25"));
		}

		if (!(errorCode = Validator.checkDate(aForm.getInsIssueDate(), (isMandatory && true), locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insIssueDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			issueDate = DateUtil.convertDate(locale, aForm.getInsIssueDate());
		}

		// Andy Wong, 13 Feb 2009: compare insr issue date must not be equal or
		// greater than expiry date
		if (issueDate != null && expiryDate != null && (issueDate.equals(expiryDate) || issueDate.after(expiryDate))) {
			errors.add("insIssueDate", new ActionMessage("error.date.compareDate.cannotBeEqualLater", "Issue Date",
					"Expiry Date"));
		}
		if (issueDate != null && effectiveDate != null && issueDate.after(effectiveDate)) {
			errors.add("insIssueDate", new ActionMessage("error.date.compareDate.greater", "Issue Date",
					"Effective Date"));
		}
		// compare ins issue date cannot greater than today date
		if ((issueDate != null) && issueDate.after(new Date())) {
			errors.add("insIssueDate", new ActionMessage("error.date.compareDate.greater", "Issue Date", "Today Date"));
		}

		// Andy Wong, 2 Mac 2009: compare ins claim date cannot greater than
		// today date
		Date insClaimDate = DateUtil.convertDate(locale, aForm.getInsuranceClaimDate());
		if (insClaimDate != null && insClaimDate.after(new Date())) {
			errors.add("addtionalDocumentFacilityDetailsClaimDate", new ActionMessage("error.date.compareDate.greater",
					"Insurance Claim Date", "Today Date"));
		}

		try {
			if (!(errorCode = Validator.checkAmount(aForm.getInsurableAmt(), true, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insurableAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						maximumAmt));
			}
			// Andy Wong, 16 Feb 2009: check insured amt > 0
			if (!(errorCode = Validator.checkAmount(aForm.getInsuredAmt(), (isMandatory && true), 1,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("insuredAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						maximumAmt));
			}
			else if (StringUtils.isNotBlank(aForm.getInsuredAmt())
					&& StringUtils.isNotBlank(aForm.getInsurancePremium())) {
				if (Double.valueOf(aForm.getInsurancePremium()).doubleValue() > Double.valueOf(aForm.getInsuredAmt())
						.doubleValue()) {
					errors.add("insuredAmt", new ActionMessage("error.amount.not.greaterthan", "Insurance Premium",
							"Insured Amount"));
				}
			}
		}
		catch (Exception e) {
			DefaultLogger.error("AddtionalDocumentFacilityDetailsValidator", "Exception throw at mapperUtil convert String to double");
		}

		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getInsuredAgainst(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insuredAgainst", RemarksValidatorUtil.getErrorMessage(errorCode));
		}

		// Andy Wong, 15 Jan 2009: remove validation for exchange rate, SIBS to
		// derive
		// if (!(errorCode =
		// Validator.checkNumber(aForm.getInsuranceExchangeRate(), (isMandatory
		// && true), 0, Double.parseDouble("99.999999999"), 9,
		// locale)).equals(Validator.ERROR_NONE)) {
		// errors.add("addtionalDocumentFacilityDetailsExchangeRate", new
		// ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		// "0", "99.999999999"));
		// }

		// Andy Wong, 16 Jan 2009: validate debiting AC No when auto debit flag
		// true
		if (StringUtils.equals(aForm.getAutoDebit(), "Yes") && StringUtils.isBlank(aForm.getDebitingACNo())) {
			errors.add("debitingACNo", new ActionMessage("error.mandatory"));
		}

		if (StringUtils.isNotBlank(aForm.getDebitingACNo())) {
			if (aForm.getDebitingACNo().length() > 19) {
				errors.add("debitingACNo", new ActionMessage("error.integer.greaterthan", "0", StringUtils.repeat("9",
						19)));
			}
			else {
				try {
					Long.parseLong(aForm.getDebitingACNo());
				}
				catch (NumberFormatException ex) {
					errors.add("debitingACNo", new ActionMessage("error.integer.format"));
				}
			}
		}

		if (StringUtils.isNotBlank(aForm.getNettPermByBorrower())) {
			if (!(errorCode = Validator.checkAmount(aForm.getNettPermByBorrower(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("nettPermByBorrower", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
			}
		}

		if (StringUtils.isNotBlank(aForm.getNettPermToInsCo())) {
			if (!(errorCode = Validator.checkAmount(aForm.getNettPermToInsCo(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("nettPermToInsCo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", maximumAmt));
			}
		}

		if (StringUtils.isNotBlank(aForm.getGrossPremium())) {
			if (!(errorCode = Validator.checkAmount(aForm.getGrossPremium(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("grossPremium", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						maximumAmt));
			}
		}

		if (StringUtils.isNotBlank(aForm.getCommissionEarned())) {
			if (!(errorCode = Validator.checkAmount(aForm.getCommissionEarned(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("commissionEarned", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", maximumAmt));
			}
		}

		if (StringUtils.isNotBlank(aForm.getStampDuty())) {
			if (!(errorCode = Validator.checkAmount(aForm.getStampDuty(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("stampDuty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						maximumAmt));
			}
		}

		if (StringUtils.isNotBlank(aForm.getNumberOfStorey())) {
			if (!(errorCode = Validator.checkAmount(aForm.getNumberOfStorey(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("numberOfStorey", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", maximumAmt));
			}
		}

		if (StringUtils.isNotBlank(aForm.getTakafulCommission())) {
			if (!(errorCode = Validator.checkAmount(aForm.getTakafulCommission(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("takafulCommission", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR));
			}
		}

		if (StringUtils.isNotBlank(aForm.getNewAmountInsured())) {
			if (!(errorCode = Validator.checkAmount(aForm.getNewAmountInsured(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("newAmountInsured", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", maximumAmt));
			}
		}

		if (StringUtils.isNotBlank(aForm.getInsurancePremium())) {
			if (!(errorCode = Validator.checkAmount(aForm.getInsurancePremium(), false, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("addtionalDocumentFacilityDetailsPremium", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", maximumAmt));
			}
		}

		if (StringUtils.isNotBlank(aForm.getRebateAmount())) {
			if (!(errorCode = Validator.checkAmount(aForm.getRebateAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("rebateAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR));
			}
		}

		if (StringUtils.isNotBlank(aForm.getServiceTaxAmount())) {
			if (!(errorCode = Validator.checkAmount(aForm.getServiceTaxAmount(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("serviceTaxAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_13_2_STR));
			}
		}

		if (StringUtils.isNotBlank(aForm.getServiceTaxPercentage())) {
			if (!(errorCode = Validator.checkAmount(aForm.getServiceTaxPercentage(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("serviceTaxPercentage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT,
						errorCode), "0", IGlobalConstant.MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR));
			}
		}

		if (StringUtils.isNotBlank(aForm.getNumberOfStorey())) {
			if (!(errorCode = Validator.checkInteger(aForm.getNumberOfStorey(), false, 0,
					IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)).equals(Validator.ERROR_NONE)) {
				errors.add("numberOfStorey", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode),
						"0", new Integer(IGlobalConstant.MAXIMUM_ALLOWED_SMALL_INTEGER)));
			}
		}

		// validate the length of insured address
		if (!(errorCode = Validator.checkString(aForm.getInsuredAddress(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("insuredAddress", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"250"));
		}
	}*/
		return errors;
	}

	public static ActionErrors validateAccSearch(AddtionalDocumentFacilityDetailsForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		if (StringUtils.isNotBlank(aForm.getDebitingACNo())) {
			if (aForm.getDebitingACNo().length() > 19) {
				errors.add("debitingACNo", new ActionMessage("error.integer.greaterthan", "0", StringUtils.repeat("9",
						19)));
			}
			else {
				try {
					Long.parseLong(aForm.getDebitingACNo());
				}
				catch (NumberFormatException ex) {
					errors.add("debitingACNo", new ActionMessage(IStpConstants.ERR_STP_PACKVALUE));
				}
			}
		}
		else {
			errors.add("debitingACNo", new ActionMessage(IStpConstants.ERR_STP_REQUIRED_FIELD, "account number"));
		}
		return errors;
	}
}
