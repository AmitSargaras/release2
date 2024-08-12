package com.integrosys.cms.ui.insurancecoveragedtls;

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

public class InsuranceCoverageDtlsValidator {
	

	public static ActionErrors validateInsuranceCoverageDtlsForm(InsuranceCoverageDtlsForm aForm, Locale locale) {
		//<validate remarks field
    	ActionErrors errors = new ActionErrors();
		String errorCode = null;

		InsuranceCoverageDtlsForm form = (InsuranceCoverageDtlsForm) aForm;
		
		if(form.getInsuranceType()==null || "".equals(form.getInsuranceType().trim()) || form.getInsuranceType().length()>20)
		{
			errors.add("insuranceTypeError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(InsuranceCoverageDtlsValidator.class, "insuranceTypeError");
		}
		
		if(form.getInsuranceCategoryName()==null || "".equals(form.getInsuranceCategoryName().trim()) || form.getInsuranceCategoryName().length()>20)
		{
			errors.add("insuranceCategoryNameError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(InsuranceCoverageDtlsValidator.class, "insuranceCategoryNameError");
		}
		
		return errors;
    }
}
