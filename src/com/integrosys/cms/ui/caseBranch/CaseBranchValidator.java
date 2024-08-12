/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.caseBranch;

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
public class CaseBranchValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale) 
    {
		ActionErrors errors = new ActionErrors();
		
		
		MaintainCaseBranchForm form = (MaintainCaseBranchForm) commonform;
		Date systemDate = DateUtil.getDate();
		String errorCode = null;
		int errorrCodeInt;
		//Date startDate = DateUtil.convertDate(locale,form.getStartDate());
	//	Date endDate = DateUtil.convertDate(locale,form.getEndDate());
		if(form.getEvent().equalsIgnoreCase("maker_create_caseBranch")||form.getEvent().equalsIgnoreCase("maker_edit_caseBranch")){
		if(form.getBranchCode()==null||form.getBranchCode().trim().equals(""))
		{	
			
			errors.add("branchCodeError", new ActionMessage("error.string.mandatory"));
			
		}
		
		if(form.getCoordinator1()==null||form.getCoordinator1().trim().equals(""))
		{	
			
			errors.add("coordinator1Error", new ActionMessage("error.string.mandatory"));
			
		}		
		else if(!(errorCode = Validator.checkString(form.getCoordinator1(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("coordinator1Error", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					100 + ""));
		}
		else {
			
			boolean descriptionFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getCoordinator1());
			if( descriptionFlag == true)
				errors.add("coordinator1Error", new ActionMessage("error.string.invalidCharacter"));
			}
		
		
		if(form.getCoordinator2()==null||form.getCoordinator2().trim().equals(""))
		{	
			
			errors.add("coordinator2Error", new ActionMessage("error.string.mandatory"));
			
		}		
		else if(!(errorCode = Validator.checkString(form.getCoordinator2(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("coordinator2Error", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					100 + ""));
		}
		else {
			
			boolean descriptionFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getCoordinator2());
			if( descriptionFlag == true)
				errors.add("coordinator2Error", new ActionMessage("error.string.invalidCharacter"));
			}
		
		
		if((form.getCoordinator1Email()==null||form.getCoordinator1Email().trim().equals("")))
		{
			
			errors.add("coordinator1EmailError", new ActionMessage("error.string.mandatory"));
			
			
		}else if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getCoordinator1Email(), 
																		  false)))
		{
			 errors.add("coordinator1EmailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					 										   "1",
					 										   "50"));
			 
		}else{		
		if((form.getCoordinator1Email()!=null && !form.getCoordinator1Email().equals("")) && (ASSTValidator.isValidEmail(form.getCoordinator1Email())) || form.getCoordinator1Email().length()>50)
		{
				errors.add("coordinator1EmailError", new ActionMessage("error.string.invalidCharacter"));
				
		}
    }
		
		
		if((form.getCoordinator2Email()==null||form.getCoordinator2Email().trim().equals("")))
		{
			errors.add("coordinator2EmailError", new ActionMessage("error.string.mandatory"));
		}
		
		else if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getCoordinator2Email(), 
																		  false)))
		{
			 errors.add("coordinator2EmailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					 										   "1",
					 										   "50"));
			 
		}
		else{
		if((form.getCoordinator2Email()!=null && !form.getCoordinator2Email().equals("")) && (ASSTValidator.isValidEmail(form.getCoordinator2Email())) || form.getCoordinator2Email().length()>50)
		{
				errors.add("coordinator2EmailError", new ActionMessage("error.string.invalidCharacter"));
				
		}
		}
		/*else if(!(errorCode = Validator.checkString(form.getDescription(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("descriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		else {
			
			boolean descriptionFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getDescription());
			if( descriptionFlag == true)
				errors.add("descriptionError", new ActionMessage("error.string.invalidCharacter"));
			}
		*//**
		 * @author sandiip.shinde : For what purpose it the following block of code.
		 * *//*
		{
			if(form.getDescription().trim()==""){
				errors.add("descriptionError", new ActionMessage("error.string.mandatory"));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getDescription());
				if( nameFlag == true)
					errors.add("descriptionError", new ActionMessage("error.string.invalidCharacter"));
			}
		}
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
				
		}*/
//		}
		}
		if(form.getGo().equalsIgnoreCase("y")){
			if(!form.getBranchCodeSearch().equalsIgnoreCase("")){
			if(!(errorCode = Validator.checkNumber(form.getBranchCodeSearch(), true,1, 9999999999999d)).equals(Validator.ERROR_NONE)) {
				//errors.add("branchCodeSearchError",new ActionMessage("error.alphabet.format"));
				errors.add("branchCodeSearchError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"9999999999999 "));
			}
			}
			if(!form.getBranchNameSearch().equalsIgnoreCase("")){
			if(ASSTValidator.isValidDirectorName(form.getBranchNameSearch())){
				errors.add("branchNameSearchError",new ActionMessage("error.string.specialcharacter"));
			}
			}
		}
		return errors;
    }

}
