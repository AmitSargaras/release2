package com.integrosys.cms.ui.insurancecoverage;

/**
 * Purpose : Used for Validating Insurance Coverage 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-04-06 15:13:16 +0800 
 * Tag : $Name$
 */

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;

public class InsuranceCoverageValidator {	

	public static ActionErrors validateInsuranceCoverageForm(InsuranceCoverageForm aForm, Locale locale) {

		ActionErrors errors = new ActionErrors();
		String errorCode = null;

		InsuranceCoverageForm form = (InsuranceCoverageForm) aForm;
	
		if(form.getCompanyName()==null || "".equals(form.getCompanyName().trim()) )
		{
			errors.add("companyNameError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(InsuranceCoverageValidator.class, "companyNameError");
		}
		else if(!(errorCode = Validator.checkString(form.getCompanyName(), true, 0, 75)).equals(Validator.ERROR_NONE)) {
			errors.add("companyNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",75 + ""));
		}
		
		else{
			boolean nameFlag = ASSTValidator.isValidAndDotDashRoundBrackets(form.getCompanyName());
			if( nameFlag == true)
				errors.add("companyNameError", new ActionMessage("error.string.invalidCharacter"));
		}
		
		if ((form.getAddress() != null && !form.getAddress().equals(""))
				&& !(errorCode = Validator.checkString(form.getAddress(),true, 0, 200)).equals(Validator.ERROR_NONE)) {
			errors.add("addressError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 200 + ""));
		}
		
		if((form.getContactNumber()!=null && !form.getContactNumber().trim().equals("")) && 
			!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getContactNumber().toString().trim(),true,0,999999999999999.D)))
		{
			errors.add("contactNumberError", new ActionMessage("error.phone.invalid"));
		}		
		
		return errors;
    }
}
