package com.integrosys.cms.ui.rbicategory;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;


/**
 *@author $Govind.Sahu$
 *Form Bean for Rbi Category
 */
public class RbiCategoryValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		
		
		RbiCategoryForm form = (RbiCategoryForm) commonform;
		
		String errorCode = null;

		
		if(form.getIndustryNameId()==null || form.getIndustryNameId().trim()==""||form.getIndustryNameId().equals("") )
		{
//			System.out.print("In Validation class");
			errors.add("industryNameId", new ActionMessage("error.string.mandatory"));
			
		}	
		if(form.getRbiCodeCategorys()==null || form.getRbiCodeCategorys().trim()==""||form.getRbiCodeCategorys().equals("") )
		{
			errors.add("rbiCodeCategorys", new ActionMessage("error.string.mandatory"));
			
		}	
		
		return errors;
    }

}
