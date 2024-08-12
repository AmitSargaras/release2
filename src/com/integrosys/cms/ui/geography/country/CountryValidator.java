package com.integrosys.cms.ui.geography.country;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.asst.validator.ASSTValidator;

public class CountryValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		CountryForm form = (CountryForm) commonform;
		
		if(form.getCountryCode()== null || form.getCountryCode().trim().equals("") )
			errors.add("countryCodeError", new ActionMessage("error.string.mandatory"));
		else{
			if(form.getCountryCode().trim().length() > 30 )
				errors.add("countryCodeError", new ActionMessage("error.string.length","Country Code","30"));
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getCountryCode());
				if( codeFlag == true)
					errors.add("countryCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		
		if(form.getCountryName()== null || form.getCountryName().trim().equals("") )
			errors.add("countryNameError", new ActionMessage("error.string.mandatory"));
		else{
			if(form.getCountryName().trim().length() > 50 )
				errors.add("countryNameError", new ActionMessage("error.string.length","Country Name","50"));
			else{				
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getCountryName());
				if( nameFlag == true)
					errors.add("countryNameError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		return errors;
    }
}
