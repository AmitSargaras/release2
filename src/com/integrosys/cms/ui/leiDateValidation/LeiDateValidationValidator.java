package com.integrosys.cms.ui.leiDateValidation;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.leiDateValidation.LeiDateValidationForm;

public class LeiDateValidationValidator {
	
	
	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		LeiDateValidationForm form=(LeiDateValidationForm)commonform;
		String errorCode = null;
		if(form.getEvent().equalsIgnoreCase("maker_create_lei_date_validation")||form.getEvent().equalsIgnoreCase("maker_edit_lei_date_validation")
				|| form.getEvent().equalsIgnoreCase("maker_save_update")||form.getEvent().equalsIgnoreCase("maker_draft_lei_date_validation")
				||form.getEvent().equalsIgnoreCase("maker_update_draft_lei_date_validation")
				||form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete")){
			
			if(null==form.getPartyID()||form.getPartyID().trim().equals("")){
				errors.add("partyID", new ActionMessage("error.string.mandatory"));			
			}else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getPartyID());
				if (codeFlag == true)
					errors.add("partyID", new ActionMessage("error.string.invalidCharacter"));
			}
		
			if(null==form.getPartyName()||form.getPartyName().trim().equals("")){
				errors.add("partyName", new ActionMessage("error.string.mandatory"));			
			}
			else if (form.getPartyName().length() < 3){
				errors.add("partyNameLengthError", new ActionMessage("error.string.length.partyName"));
			}
			else {
							}
			
			if(null==form.getLeiDateValidationPeriod()||form.getLeiDateValidationPeriod().trim().equals("")){
				errors.add("leiDateValidationPeriod", new ActionMessage("error.string.mandatory"));			
			}else {
				boolean codeflag = ASSTValidator.isNumeric(form.getLeiDateValidationPeriod());
				if(codeflag == false) {
					errors.add("leiDateValidationPeriod", new ActionMessage("error.amount.format"));			

				}else {
				if(0 > Integer.parseInt(form.getLeiDateValidationPeriod())) {
						errors.add("leiDateValidationPeriod", new ActionMessage("error.string.negative"));			
				}}
				}
		}else if(form.getEvent().equalsIgnoreCase("list")) {
//			if (!(errorCode=Validator.checkString(form.getPartyID(), true, 3, 50)).equals(Validator.ERROR_NONE)) {
//				errors.add("customerNameError",  new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),3, 50 + ""));
//			}
		}
		return errors;
	}
}
