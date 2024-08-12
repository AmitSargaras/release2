package com.integrosys.cms.ui.geography.city;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.asst.validator.ASSTValidator;

public class CityValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		CityForm form = (CityForm) commonform;
				
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
		
		if(form.getCountryOBId() == null || form.getCountryOBId().trim().equals("") )
			errors.add("countryOBIdError", new ActionMessage("error.string.mandatory"));
		
		if(form.getRegionOBId() == null || form.getRegionOBId().trim().equals("") )
			errors.add("regionOBIdError", new ActionMessage("error.string.mandatory"));
		
		if(form.getStateOBId() == null || form.getStateOBId().trim().equals("") )
			errors.add("stateOBIdError", new ActionMessage("error.string.mandatory"));
		
		if(form.getEcbfCityId() != null && form.getEcbfCityId().length() > 0 && !ASSTValidator.isNumeric(form.getEcbfCityId())) {
			errors.add("ecbfCityId", new ActionMessage("error.city.ecbfCityId.invalid"));
		}
		
		return errors;
    }
}
