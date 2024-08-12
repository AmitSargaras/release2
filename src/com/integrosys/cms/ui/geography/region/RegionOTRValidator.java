package com.integrosys.cms.ui.geography.region;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.geography.region.RegionForm;
public class RegionOTRValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		RegionOTRForm form = (RegionOTRForm) commonform;
		
		if(form.getRegionCode()== null || form.getRegionCode().trim().equals("") )
			errors.add("regionCodeError", new ActionMessage("error.string.mandatory"));
		else{
			if(form.getRegionCode().trim().length() > 30 )
				errors.add("regionCodeError", new ActionMessage("error.string.length","Region Code","30"));
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getRegionCode());
				if( codeFlag == true)
					errors.add("regionCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		
		if(form.getRegionName()== null || form.getRegionName().trim().equals("") )
			errors.add("regionNameError", new ActionMessage("error.string.mandatory"));
		else{
			if(form.getRegionName().trim().length() > 30 )
				errors.add("regionNameError", new ActionMessage("error.string.length","Region Name","30"));
			else{
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getRegionName());
				if( nameFlag == true)
					errors.add("regionNameError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		
		
		return errors;
    }
}
