package com.integrosys.cms.ui.geography.state;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.asst.validator.ASSTValidator;

public class StateOTRValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		StateOTRForm form = (StateOTRForm) commonform;
				
		if(form.getStateCode()== null || form.getStateCode().trim().equals("") )
			errors.add("stateCodeError", new ActionMessage("error.string.mandatory"));
		else{
			if(form.getStateCode().trim().length() > 30 )
				errors.add("stateCodeError", new ActionMessage("error.string.length","State Code","30"));
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getStateCode());
				if( codeFlag == true)
					errors.add("stateCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		}

		if(form.getStateName()== null || form.getStateName().trim().equals("") )
			errors.add("stateNameError", new ActionMessage("error.string.mandatory"));
		else{
			if(form.getStateName().trim().length() > 30 )
				errors.add("stateNameError", new ActionMessage("error.string.length","State Name","30"));
			else{
				boolean nameFlag = ASSTValidator.isValidANDName(form.getStateName());
				if( nameFlag == true)
					errors.add("stateNameError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		
		/*if(form.getCountryOBId() == null || form.getCountryOBId().trim().equals("") )
			errors.add("countryOBIdError", new ActionMessage("error.string.mandatory"));
		
		if(form.getRegionOBId() == null || form.getRegionOBId().trim().equals("") )
			errors.add("regionOBIdError", new ActionMessage("error.string.mandatory"));*/
		return errors;
    }
}
