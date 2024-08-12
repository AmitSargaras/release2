package com.integrosys.cms.ui.geography.city;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.asst.validator.ASSTValidator;

public class CityOTRValidator {
	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		CityOTRForm form = (CityOTRForm) commonform;
				
		if(form.getCityCode()== null || form.getCityCode().trim().equals("") )
			errors.add("cityCodeError", new ActionMessage("error.string.mandatory"));
		else{
			if(form.getCityCode().trim().length() > 30 )
				errors.add("cityCodeError", new ActionMessage("error.string.length","City Code","30"));
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCityCode());
				if( codeFlag == true)
					errors.add("cityCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		}			

		if(form.getCityName()== null || form.getCityName().trim().equals("") )
			errors.add("cityNameError", new ActionMessage("error.string.mandatory"));
		else{
			if(form.getCityName().trim().length() > 30 )
				errors.add("cityNameError", new ActionMessage("error.string.length","City Name","30"));
			else{
				boolean nameFlag = ASSTValidator.isValidANDName(form.getCityName());
				if( nameFlag == true)
					errors.add("cityNameError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		
		
		return errors;
    }
}
