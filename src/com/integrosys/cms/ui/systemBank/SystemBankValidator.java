/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.systemBank;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.otherbankbranch.OtherBankValidator;
import com.integrosys.cms.ui.user.MaintainUserForm;


/**
 *@author abhijit.rudrakshawar$
 *Validator for System Bank Master
 */
public class SystemBankValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		MaintainSystemBankForm form = (MaintainSystemBankForm) commonform;
		String event= form.getEvent();
		String remarks= form.getRemarks();
		String errorCode = null;
//		if ("maker_search_list_systemBankBranch".equals(form.getEvent())||"checker_search_list_systemBankBranch".equals(form.getEvent())) {
//			return validateSearchCondition(form, errors);
//		}
		if(event.equalsIgnoreCase("maker_edit_systemBank")
				||event.equalsIgnoreCase("maker_save_update")
				||event.equalsIgnoreCase("maker_confirm_resubmit_delete")
				||event.equalsIgnoreCase("maker_confirm_resubmit_edit")){

		if(form.getCityTown()==null || form.getCityTown().trim().equals("") )
		{
			errors.add("systemBankCityError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getAddress()==null || form.getAddress().trim().equals("") )
		{
			errors.add("systemBankAddressError", new ActionMessage("error.string.mandatory"));
			
		}
		if(!(form.getContactMail()==null||form.getContactMail().trim().equals("")))
		{
		if( ASSTValidator.isValidEmail(form.getContactMail()) )
		{
			//CLMJ-14
			 errors.add("systemBankContactMailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.EMAIL, errorCode),
					 										   "1",
					 										   "50"));
			// errors.add("systemBankContactMailError", new ActionMessage("error.email.format"));
		}
		}
		if((form.getContactMail()!=null && !form.getContactMail().equals("")) && (ASSTValidator.isValidEmail(form.getContactMail())))
		{
				errors.add("systemBankContactMailError", new ActionMessage("error.email.format"));
				
		}
		if(!(form.getContactNumber()==null||form.getContactNumber().trim().equals("")))
		{
		if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getContactNumber().toString().trim(),false,15,999999999999999.D)))
		{
				errors.add("systemBankContactNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					   "1",
					   "10"));
				DefaultLogger.debug(OtherBankValidator.class, "contactNoError");
		}
		}
		if(!(form.getFaxNumber()==null||form.getFaxNumber().trim().equals("")))
		{
		if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getFaxNumber().toString().trim(),false,15,999999999999999.D)))
		{
				errors.add("systemBankFaxNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					   "1",
					   "10"));
		}
		}
		}else if(event.equalsIgnoreCase("maker_update_draft_systemBank")){
			if(form.getAddress()==null || form.getAddress().trim().equals("") )
			{
				errors.add("systemBankAddressError", new ActionMessage("error.string.mandatory"));
				
			}
			
//			if(form.getCityTown()==null && form.getCityTown().trim().equals("") )
//			{
//				errors.add("systemBankCityError", new ActionMessage("error.string.mandatory"));
//				
//			}
//			if(form.getAddress()==null && form.getAddress().trim().equals("") )
//			{
//				errors.add("systemBankAddressError", new ActionMessage("error.string.mandatory"));
//				
//			}
			if(!(form.getContactMail()==null||form.getContactMail().trim().equals("")))
			{
			if(ASSTValidator.isValidEmail(form.getContactMail()) )
			{
				 errors.add("systemBankContactMailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.EMAIL, errorCode),
						 										   "1",
						 										   "50"));
				 
				// errors.add("systemBankContactMailError", new ActionMessage("error.email.format")); 
				 
			}
			}
			if((form.getContactMail()!=null && !form.getContactMail().equals("")) && (ASSTValidator.isValidEmail(form.getContactMail())))
			{
					errors.add("systemBankContactMailError", new ActionMessage("error.email.format"));
					
			}
			if(!(form.getContactNumber()==null||form.getContactNumber().trim().equals("")))
			{
			if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getContactNumber().toString().trim(),true,15,999999999999999.D)))
			{
					errors.add("systemBankContactNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						   "1",
						   "10"));
					DefaultLogger.debug(OtherBankValidator.class, "contactNoError");
			}
			}
			if(!(form.getFaxNumber()==null||form.getFaxNumber().trim().equals("")))
			{
			if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getFaxNumber().toString().trim(),true,15,999999999999999.D)))
			{
					errors.add("systemBankFaxNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						   "1",
						   "10"));
			}
		}
		}else if(event.equalsIgnoreCase("checker_reject_edit")){
			if(remarks==null||remarks.equals("")){
				errors.add("systemBankRemarksError", new ActionMessage("error.string.mandatory"));
			}
		}
		return errors;
    }
	/*private static ActionErrors validateSearchCondition(MaintainSystemBankForm form, ActionErrors errors) {
		String errorCode = "";

		DefaultLogger.debug("SearchUser", " - SearchBy: " + form.getSearchBy());
		if ("loginId".equals(form.getSearchBy())) {
			if (!(errorCode = Validator.checkString(form.getLoginId(), true, 3, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("loginId", new ActionMessage("error.user.search.login.id"));
			}
		}
		else if ("name".equals(form.getSearchBy())) {
			if (!(errorCode = Validator.checkString(form.getName(), true, 3, 50)).equals(Validator.ERROR_NONE)) {
				errors.add("name", new ActionMessage("error.user.search.name"));
			}
		}else if ("branch".equals(form.getSearchBy())) { 
			if(form.getBranchCode()==null || form.getBranchCode().trim().equals("") )
		
		{
			errors.add("branchCode", new ActionMessage("error.string.mandatory"));
			
		}
		}	
		else if ("status".equals(form.getSearchBy())) {
			if(form.getStatus()==null || form.getStatus().trim().equals("") )
		
		{
			errors.add("status", new ActionMessage("error.string.mandatory"));
			
		}
		}
		return errors;
	}*/

}
