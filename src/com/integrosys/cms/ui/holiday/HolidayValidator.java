/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.holiday;

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
public class HolidayValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale) 
    {
		ActionErrors errors = new ActionErrors();
		
		
		MaintainHolidayForm form = (MaintainHolidayForm) commonform;
		Date systemDate = DateUtil.getDate();
		String errorCode = null;
		int errorrCodeInt;
		Date startDate = DateUtil.convertDate(locale,form.getStartDate());
		Date endDate = DateUtil.convertDate(locale,form.getEndDate());
		if(form.getDescription()==null||form.getDescription().trim().equals(""))
		{	
			
			errors.add("descriptionError", new ActionMessage("error.string.mandatory"));
			
		}
		
		else if(!(errorCode = Validator.checkString(form.getDescription(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("descriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		else {
			
			boolean descriptionFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getDescription());
			if( descriptionFlag == true)
				errors.add("descriptionError", new ActionMessage("error.string.invalidCharacter"));
			}
		/**
		 * @author sandiip.shinde : For what purpose it the following block of code.
		 * */
		/*{
			if(form.getDescription().trim()==""){
				errors.add("descriptionError", new ActionMessage("error.string.mandatory"));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getDescription());
				if( nameFlag == true)
					errors.add("descriptionError", new ActionMessage("error.string.invalidCharacter"));
			}
		}*/
		if(form.getStartDate()==null || form.getStartDate().trim().equals("") )
		{
			errors.add("startDateError", new ActionMessage("error.date.mandatory"));
		}else if(startDate.before(systemDate))
		{
			errors.add("startDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier","Start Date","Current Date"));
			
		}
//		if(!(form.getStartDate()==null || form.getStartDate().trim().equals("")) )
//		{
		if(form.getEndDate()==null || form.getEndDate().trim().equals("") )
		{
			errors.add("endDateError", new ActionMessage("error.date.mandatory"));
		} else{
			
				if(endDate.before(startDate))
				{
					errors.add("endDateError", new ActionMessage("error.date.compareDate.cannotBeEarlier","End Date","Start Date"));
					
				}
				
		}
//		}
		
		return errors;
    }

}
