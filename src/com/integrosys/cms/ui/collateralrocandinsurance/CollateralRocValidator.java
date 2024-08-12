package com.integrosys.cms.ui.collateralrocandinsurance;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

public class CollateralRocValidator {
	
	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		CollateralRocForm form=(CollateralRocForm)commonform;
		//boolean a=validInput(form.getStartTime());
		
		if(null==form.getCollateralCategory()||form.getCollateralCategory().trim().equals("")){
			errors.add("collateralCategoryError", new ActionMessage("error.string.mandatory"));			
		}
		
		if(null==form.getCollateralRocCode()||form.getCollateralRocCode().trim().equals("")){
			errors.add("collateralRocCodeError", new ActionMessage("error.string.mandatory"));			
		}
		
		if(null==form.getCollateralRocDescription()||form.getCollateralRocDescription().trim().equals("")){
			errors.add("collateralRocDescriptionError", new ActionMessage("error.string.mandatory"));			
		}
		
		if(null==form.getIrbCategory()||form.getIrbCategory().trim().equals("")){
			errors.add("irbCategoryError", new ActionMessage("error.string.mandatory"));			
		}
		
		/*if(null==form.getInsuranceApplicable()||form.getInsuranceApplicable().trim().equals("")){
			errors.add("insuranceApplicableError", new ActionMessage("error.string.mandatory"));			
		}*/
		
		return errors;
		
	}

}
