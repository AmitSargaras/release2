/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.fccBranch;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;


/****
 * 
 * @author komal.agicha
 *
 */
public class FCCBranchValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale) 
    {
		ActionErrors errors = new ActionErrors();
		FCCBranchForm form = (FCCBranchForm) commonform;
		String errorCode = null;
		if(form.getEvent().equalsIgnoreCase("maker_create_fccBranch")||form.getEvent().equalsIgnoreCase("maker_edit_fccBranch") || form.getEvent().equalsIgnoreCase("maker_save_update")||form.getEvent().equalsIgnoreCase("maker_draft_fccBranch") || form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete")){
		if(form.getBranchCode()==null||form.getBranchCode().trim().equals(""))
		{	
			
			errors.add("branchCodeError", new ActionMessage("error.string.mandatory"));
			
		}
		
		if(form.getAliasBranchCode()==null||form.getAliasBranchCode().trim().equals(""))
		{	
			
			errors.add("aliasBranchCodeError", new ActionMessage("error.string.mandatory"));
			
		}
		
		if(form.getBranchName()==null||form.getBranchName().trim().equals(""))
		{	
			
			errors.add("branchNameError", new ActionMessage("error.string.mandatory"));
			
		}		
		else if(!(errorCode = Validator.checkString(form.getBranchName(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("branchNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					100 + ""));
		}
		else {
			
			boolean descriptionFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getBranchName());
			if( descriptionFlag == true)
				errors.add("branchNameError", new ActionMessage("error.string.invalidCharacter"));
			}
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String branchCodeLength = bundle.getString("branch.code.length");
			System.out.println("FCCBranchValidator=>branch.code.length=>branchCodeLength=>"+branchCodeLength);
			
			if(null != form.getBranchCode() && !form.getBranchCode().trim().isEmpty()){
			if(form.getBranchCode().trim().length()> Integer.parseInt(branchCodeLength)){
				errors.add("branchCodeError",new ActionMessage("error.fccBranchCode.length.exceeds","1",branchCodeLength));
			}
			
			
			boolean descriptionFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getBranchCode());
			if( descriptionFlag == true)
				errors.add("branchCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		
		
		
		if(form.getAliasBranchCode()!=null && !form.getAliasBranchCode().trim().isEmpty()){
				if(form.getAliasBranchCode().trim().length()> Integer.parseInt(branchCodeLength)){
				errors.add("aliasBranchCodeError",new ActionMessage("error.number.greaterthan","1",branchCodeLength));
			}
			
			
//			if (!(errorCode = Validator.checkInteger(form.getAliasBranchCode(), true, 1, 9999)).equals(Validator.ERROR_NONE)) {
//				errors.add("aliasBranchCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "1","9999"));
//			}
			if(!ASSTValidator.isNumeric(form.getAliasBranchCode().trim())) {
				errors.add("aliasBranchCodeError", new ActionMessage("error.number.format"));
			}
		}
		
		}
		
		

		return errors;
}
	
}
