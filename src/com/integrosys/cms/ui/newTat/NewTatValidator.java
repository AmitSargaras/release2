/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.newTat;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerValidator;


/**
 *@author $Author: Abhijit R$
 *Validator for System Bank Branch
 */
public class NewTatValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale) 
    {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		
		
		NewTatForm form = (NewTatForm) commonform;
		
		if(form.getEvent().equals("confirm_limit_release") ){
			if(form.getFacilitySection()!= null && form.getFacilitySection().equals("SYSTEM") ){
			if (form.getFacilitySystem() == null
					|| "".equals(form.getFacilitySystem().trim())) {
				errors.add("facilitySystemError", new ActionMessage("error.string.mandatory"));
			} 
			
			
			if(form.getFacilitySection().equals("SYSTEM")){
			if (form.getSerialNumberSystem() == null
					|| "".equals(form.getSerialNumberSystem().trim())) {
				errors.add("serialNumberError", new ActionMessage("error.string.mandatory"));
			} 
			else if (form.getSerialNumberSystem() != null
					|| !"".equals(form.getSerialNumberSystem().trim())) {
				 if (!(errorCode = Validator.checkNumber(form.getSerialNumberSystem(), false, 0, 99, 0, locale)).equals(Validator.ERROR_NONE)) { 
					 errors.add("serialNumberError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99")); 
				 }
			}
			}
			}
			if(form.getFacilitySection()!= null && form.getFacilitySection().equals("MANUAL") ){
				if (form.getFacilityManual() == null
						|| "".equals(form.getFacilityManual().trim())) {
					errors.add("facilityManualError", new ActionMessage("error.string.mandatory"));
				} 
				if (form.getSerialNumberManual() == null
						|| "".equals(form.getSerialNumberManual().trim())) {
					errors.add("serialNumberError", new ActionMessage("error.string.mandatory"));
				} 
				else if (form.getSerialNumberManual() != null
						|| !"".equals(form.getSerialNumberManual().trim())) {
					 if (!(errorCode = Validator.checkNumber(form.getSerialNumberManual(), false, 0, 99, 0, locale)).equals(Validator.ERROR_NONE)) { 
						 errors.add("serialNumberError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "99")); 
					 }
					
					
					//errors.add("facilityManualError", new ActionMessage("error.string.mandatory"));
				} 
				
				
				
				}
			if(form.getAmount() == null	|| "".equals(form.getAmount().trim())){
				errors.add("errorAmount", new ActionMessage("error.string.mandatory"));
			}else{
			if (!(errorCode =Validator.checkAmount(form.getAmount(), true,1, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2,IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("errorAmount", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.NUMBER, errorCode), "1",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_20_2_STR));
				}			
			}
			
			if (form.getIsTatBurst() != null
					&& "Y".equals(form.getIsTatBurst().trim())) {
				
				if (form.getDelayReason() == null
						|| "".equals(form.getDelayReason().trim())) {
					errors.add("delayReasonError", new ActionMessage("error.string.mandatory"));
				} 
			} 
			
			if (form.getRemarks()!= null
					&& !"".equals(form.getRemarks().trim())) {

			 if (form.getRemarks().length() > 500) {
				errors.add("remarksLengthError", new ActionMessage(
						"error.remarks.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class,
						"error.remarks.length.exceeded");
			}
			}
			
		}else if
			( "deferral_approve".equals(form.getEvent()) 
					|| "deferral_clearance".equals(form.getEvent())
					|| "confirm_document_receive".equals(form.getEvent())
					|| "confirm_document_scan".equals(form.getEvent())
					|| "confirm_clims_updated".equals(form.getEvent())
					//|| "close_case".equals(form.getEvent())
					|| "confirm_deferral_raised".equals(form.getEvent())){
		
			if (form.getRemarks()!= null
					&& !"".equals(form.getRemarks().trim())) {

			 if (form.getRemarks().length() > 500) {
				errors.add("remarksLengthError", new ActionMessage(
						"error.remarks.length.exceeded"));
				DefaultLogger.debug(ManualInputCustomerValidator.class,
						"error.remarks.length.exceeded");
			}
			}
		
		}else{
		if (form.getLspShortName() == null
				|| "".equals(form.getLspShortName().trim())) {
			errors.add("lspShortNameError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(NewTatValidator.class,
					"lspShortNameError");
		} 
		if (form.getLspLeId() == null
				|| "".equals(form.getLspLeId().trim())) {
			errors.add("lspLeIdError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(NewTatValidator.class,
					"lspLeIdError");
		} 
		/*if (form.getSegment() == null
				|| "".equals(form.getSegment().trim())) {
			errors.add("segmentError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(NewTatValidator.class,
					"segmentError");
		} 
		if (form.getRmRegion() == null
				|| "".equals(form.getRegion().trim())) {
			errors.add("regionError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(NewTatValidator.class,
					"regionError");
		} */
		if (form.getRelationshipManager() == null
				|| "".equals(form.getRelationshipManager().trim())) {
			errors.add("relationshipManagerError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(NewTatValidator.class,
					"relationshipManagerError");
		} 
		if (form.getCaseInitiator() == null
				|| "".equals(form.getCaseInitiator().trim())) {
			errors.add("caseInitiatorError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(NewTatValidator.class,
					"caseInitiatorError");
		} 
		if (form.getModule() == null
				|| "".equals(form.getModule().trim())) {
			errors.add("moduleError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(NewTatValidator.class,
					"moduleError");
		} else if(form.getModule().equals("LIMIT")){
			
			if (form.getFacilityCategory() == null
					|| "".equals(form.getFacilityCategory().trim())) {
				errors.add("facilityCategoryError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(NewTatValidator.class,
						"facilityCategoryError");
			} 
			if (form.getFacilityName() == null
					|| "".equals(form.getFacilityName().trim())) {
				errors.add("facilityNameError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(NewTatValidator.class,
						"facilityNameError");
			} 
			
		}else if(form.getModule().equals("CAM")){
			
			if (form.getCamType() == null
					|| "".equals(form.getCamType().trim())) {
				errors.add("camTypeError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(NewTatValidator.class,
						"camTypeError");
			} 
			
		}else if(form.getModule().equals("PDDC")){
			
			if (form.getDeferralType() == null
					|| "".equals(form.getDeferralType().trim())) {
				errors.add("deferralTypeError", new ActionMessage(
						"error.string.mandatory"));
				DefaultLogger.debug(NewTatValidator.class,
						"deferralTypeError");
			} 
			
		}
		if (form.getLssCoordinatorBranch() == null
				|| "".equals(form.getLssCoordinatorBranch().trim())) {
			errors.add("lssCoordinatorBranchError", new ActionMessage(
					"error.string.mandatory"));
			DefaultLogger.debug(NewTatValidator.class,
					"lssCoordinatorBranchError");
		} 
		
		if (form.getIsTatBurst() != null
				&& "Y".equals(form.getIsTatBurst().trim())) {
			
			if (form.getDelayReason() == null
					|| "".equals(form.getDelayReason().trim())) {
				errors.add("delayReasonError", new ActionMessage("error.string.mandatory"));
			} 
		} 
		
		
		
		
		
		if (form.getRemarks()!= null
				&& !"".equals(form.getRemarks().trim())) {

		 if (form.getRemarks().length() > 500) {
			errors.add("remarksLengthError", new ActionMessage(
					"error.remarks.length.exceeded"));
			DefaultLogger.debug(ManualInputCustomerValidator.class,
					"error.remarks.length.exceeded");
		}
		}
		
		}
		return errors;
    }

}
