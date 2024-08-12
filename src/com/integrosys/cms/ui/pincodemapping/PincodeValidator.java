package com.integrosys.cms.ui.pincodemapping;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class PincodeValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();

		PincodeMappingForm form = (PincodeMappingForm) commonform;
			
		if(form.getPincode()==null || "".equals(form.getPincode().trim()))
		{
			errors.add("pincodeError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(PincodeValidator.class, "pincodeError");
		}
		if(form.getStateId()== null || form.getStateId().trim().equals("") )
			errors.add("stateIdError", new ActionMessage("error.string.mandatory"));
		/*else{
			if(form.getStateCode().trim().length() > 50 )
				errors.add("stateCodeError", new ActionMessage("error.string.length","State Code","50"));
			else{
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getStateCode());
				if( codeFlag == true)
					errors.add("stateCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		}*/
		
		return errors;
		
    }

}
