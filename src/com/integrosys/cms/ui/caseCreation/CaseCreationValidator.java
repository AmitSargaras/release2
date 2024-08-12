/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.caseCreation;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;


/**
 *@author $Author: Abhijit R$
 *Validator for System Bank Branch
 */
public class CaseCreationValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale) 
    {
		ActionErrors errors = new ActionErrors();
		
		
		CaseCreationForm form = (CaseCreationForm) commonform;
		Date systemDate = DateUtil.getDate();
		String errorCode = null;
		int errorrCodeInt;
	
		
		return errors;
    }

}
