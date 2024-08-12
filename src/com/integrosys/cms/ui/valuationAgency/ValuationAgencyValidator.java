package com.integrosys.cms.ui.valuationAgency;import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;

/**
 
 *@author rajib.aich 
 *Validator for Valuation Agency
 */

public class ValuationAgencyValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		
		ValuationAgencyForm form = (ValuationAgencyForm) commonform;
		String errorCode = null;
		
		if(form.getEvent().equalsIgnoreCase("maker_submit_valuationAgency")){
		if(form.getValuationAgencyName()==null || form.getValuationAgencyName().trim().equals(""))
		{
			errors.add("valuationAgencyNameError", new ActionMessage("error.string.mandatory"));
			
		}
		else if (!(errorCode = Validator.checkString(form.getValuationAgencyName(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("valuationAgencyNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		else{
			boolean nameFlag = ASSTValidator.isValidAndDotDashRoundBrackets(form.getValuationAgencyName());
			if( nameFlag == true)
				errors.add("specialCharacterNameError", new ActionMessage("error.string.invalidCharacter"));
		}	
				 
		if (!(errorCode = Validator.checkString(form.getAddress(), false, 0, 200)).equals(Validator.ERROR_NONE)) {
			errors.add("addressError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",200 + ""));
		}
		}
		else if(form.getGo().equalsIgnoreCase("y")){
			boolean nameFlag1 = ASSTValidator.isValidAndDotDashRoundBrackets(form.getValuationAgencyNameSearch());
			if( nameFlag1 == true)
				errors.add("valuationAgencyNameError", new ActionMessage("error.string.invalidCharacter"));
			boolean nameFlag2 = ASSTValidator.isValidAndDotDashRoundBrackets(form.getValuationAgencyCodeSearch());
			if( nameFlag2 == true)
				errors.add("valuationAgencyCodeError", new ActionMessage("error.string.invalidCharacter"));
		}
		return errors;		
    }
}
