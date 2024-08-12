package com.integrosys.cms.ui.excludedfacility;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

public class ExcludedFacilityValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		ExcludedFacilityForm form=(ExcludedFacilityForm)commonform;
		//boolean a=validInput(form.getStartTime());
		
		if(null==form.getExcludedfacilityDescription()||form.getExcludedfacilityDescription().trim().equals("")){
			errors.add("excludedfacilityDescriptionError", new ActionMessage("error.string.mandatory"));			
		}
		
		return errors;
		
	}
}
