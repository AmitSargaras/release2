/*
 * Copyright Integro Technologies Pte Ltd
 * $HeadURL: https://svn.integrosys.com/projects/uoblos/tags/build00.06.06.006/src/com/integrosys/sml/ui/los/parameters/pricing/PricingValidator.java $
 */

package com.integrosys.cms.ui.systemBankBranch;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.otherbankbranch.OtherBankValidator;


/**
 *@author $Author: Abhijit R$
 *Validator for System Bank Branch
 */
public class SystemBranchValidator {
	

	public static ActionErrors validateInput(ActionForm commonform,Locale locale)
    {
		ActionErrors errors = new ActionErrors();
		
		
		MaintainSystemBankBranchForm form = (MaintainSystemBankBranchForm) commonform;
		String event=form.getEvent();
		String errorCode = null;
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String branchCodeLength = bundle.getString("branch.code.length");
		System.out.println("SystemBranchValidator=>branch.code.length=>branchCodeLength=>"+branchCodeLength);
		if(event.equalsIgnoreCase("maker_create_systemBankBranch")||event.equalsIgnoreCase("maker_edit_systemBankBranch")||event.equalsIgnoreCase("maker_save_update")){
		if(null == form.getSystemBankBranchCode() || "".equals(form.getSystemBankBranchCode().trim()) )
		{
			errors.add("systemBankBranchCodeError", new ActionMessage("error.string.mandatory"));
			
		}
		/*Commented by Sandeep Shinde*/
		/*else if(!(errorCode = Validator.checkString(form.getSystemBankBranchCode(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("systemBankBranchCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}*/
		//else if(!(errorCode = Validator.checkNumber(form.getSystemBankBranchCode(), true, 0, Integer.parseInt(branchCodeLength))).equals(Validator.ERROR_NONE)) {
		else if(!ASSTValidator.isNumeric(form.getSystemBankBranchCode().trim())) {
			errors.add("systemBankBranchCodeError", new ActionMessage("error.number.format"));
		}
		else if(form.getSystemBankBranchCode().trim().length() > Integer.parseInt(branchCodeLength)){
				errors.add("systemBankBranchCodeError",new ActionMessage("error.number.greaterthan","1",branchCodeLength));
			}
		else{
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSystemBankBranchCode());
			if( codeFlag == true)
				errors.add("systemBankBranchCodeError", new ActionMessage("error.number.format"));
			}
		if(form.getSystemBankBranchName()==null || form.getSystemBankBranchName().trim().equals(""))
		{
			errors.add("systemBankBranchNameError", new ActionMessage("error.string.mandatory"));
			
		}
		else if(!(errorCode = Validator.checkString(form.getSystemBankBranchName(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("systemBankBranchNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		
		else{
			boolean nameFlag = ASSTValidator.isValidOtherBankBranchName(form.getSystemBankBranchName());
			if( nameFlag == true)
				errors.add("systemBankBranchNameError", new ActionMessage("error.string.invalidCharacter"));
			}	
		
		if(form.getCityTown()==null || form.getCityTown().trim().equals("") )
		{
			errors.add("systemBankBranchCityError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getCountry()==null || form.getCountry().trim().equals("") )
		{
			errors.add("countryOBIdError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getState()==null || form.getState().trim().equals("") )
		{
			errors.add("stateOBIdError", new ActionMessage("error.string.mandatory"));
			
		}
		if(form.getRegion()==null || form.getRegion().trim().equals("") )
		{
			errors.add("regionOBIdError", new ActionMessage("error.string.mandatory"));
			
		}
		/*if(form.getRbiCode()==null || form.getRbiCode().trim().equals(""))
		{
			errors.add("systemBankRbiError", new ActionMessage("error.string.mandatory"));
			
		}*/
		/*if(!(form.getIsHub()==null || form.getIsHub().trim().equals("") ))
		{
			if(form.getIsHub().trim().equalsIgnoreCase("N")){
				if(form.getLinkedHub()==null || form.getLinkedHub().trim().equals("") ){
					errors.add("systemBankBranchIsHubError", new ActionMessage("error.string.mandatory"));
				}
			}
			
			
		}*/
		if(form.getIsVault()!=null &&form.getIsVault().trim().equals("on"))
		{
			if(form.getCustodian1().trim().equals("")&&form.getCustodian2().trim().equals(""))
			{
				errors.add("systemBankBranchCustodianError", new ActionMessage("error.string.custodian"));
			}else
			{				
				if( ASSTValidator.isValidAlphaNumStringWithSpace(form.getCustodian1()) )
					errors.add("custodian1Error", new ActionMessage("error.string.invalidCharacter"));
				
				if( ASSTValidator.isValidAlphaNumStringWithSpace(form.getCustodian2()) )
					errors.add("custodian2Error", new ActionMessage("error.string.invalidCharacter"));
			}
		}
		/*if(form.getAddress()==null || form.getAddress().trim().equals("") )
		{
			errors.add("systemBankBranchAddressError", new ActionMessage("error.string.mandatory"));
			
		}
		else */if(!(errorCode = Validator.checkString(form.getAddress(), false, 0, 200)).equals(Validator.ERROR_NONE)) {
			errors.add("systemBankBranchAddressError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					200 + ""));
		}

		if(!(form.getContactMail()==null||form.getContactMail().trim().equals("")))
		{
		if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getContactMail(), 
																		  false)))
		{
			//CLMJ-14
			 errors.add("systemBankBranchContactMailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.EMAIL, errorCode),
					 										   "1",
					 										   "50"));
			 errors.add("systemBankBranchContactMailError", new ActionMessage("error.email.format"));
			 
		}
		}
		if((form.getContactMail()!=null && !form.getContactMail().equals("")) && (ASSTValidator.isValidEmail(form.getContactMail())) || form.getContactMail().length()>50)
		{
			//CLMJ-14
			/* errors.add("systemBankBranchContactMailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					   "1",
					   "50"));*/
			 errors.add("systemBankBranchContactMailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.EMAIL, errorCode),
					   "1",
					   "50"));
			 
			 errors.add("systemBankBranchContactMailError", new ActionMessage("error.email.format"));
				
		}
		if(!(form.getContactNumber()==null||form.getContactNumber().trim().equals("")))
		{
		if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getContactNumber().toString().trim(),false,15,999999999999999.D)))
		{
				errors.add("systemBankBranchContactNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					   "1",
					   "15"));
				DefaultLogger.debug(SystemBranchValidator.class, "contactNoError");
		}
		}
		if(!(form.getFaxNumber()==null||form.getFaxNumber().trim().equals("")))
		{
		if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getFaxNumber().toString().trim(),false,15,999999999999999.D)))
		{
				errors.add("systemBankBranchFaxNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					   "1",
					   "15"));
		}
		}
		
		if(form.getRbiCode()!= null && !form.getRbiCode().equals("") && form.getRbiCode().length() > 9) {
			errors.add("rbiCodeError", new ActionMessage("error.rbicode.length"));
		}
		if(!(form.getRbiCode()==null || form.getRbiCode().trim().equals("")) )
		{
			boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getRbiCode());
			if( nameFlag == true)
				errors.add("rbiCodeError", new ActionMessage("error.string.invalidCharacter"));
			
		}
		
		 if( form.getCustodian1()!= null && !form.getCustodian1().equals("") && form.getCustodian1().length() > 50) {
				errors.add("custodian1Error", new ActionMessage("error.string.length"));
			}
		 if( form.getCustodian2()!= null && !form.getCustodian2().equals("") && form.getCustodian2().length() > 50) {
				errors.add("custodian2Error", new ActionMessage("error.string.length"));
			}
		 
		 if(StringUtils.isNotBlank(form.getPincode())) {
			 if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getPincode().toString().trim(),true,10,999999999999999.D))){
				errors.add("systemBankBranchPincodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),"1","6"));
						DefaultLogger.debug(SystemBranchValidator.class, "systemBankBranchPincodeError");
			 }
			 else if(StringUtils.isNotBlank(form.getStatePincodeString()) && StringUtils.isNotBlank(form.getState())) {
				 Map<String, String> statePincodeMap = UIUtil.getMapFromDelimitedString(form.getStatePincodeString(), ",", "=");
				 if(null != statePincodeMap && !statePincodeMap.isEmpty()) {
					 String selectedStatePincode = statePincodeMap.get(form.getState());
					 if(null != selectedStatePincode && !form.getPincode().trim().startsWith(selectedStatePincode)) {
						 errors.add("systemBankBranchPincodeError", new ActionMessage("error.pincode.incorrect"));
					 }
				 }
			 }
		 }
		 else {
			 errors.add("systemBankBranchPincodeError", new ActionMessage("error.string.mandatory"));
		 }
		
}else if(event.equalsIgnoreCase("maker_draft_systemBankBranch")||event.equalsIgnoreCase("maker_update_draft_systemBankBranch")||event.equalsIgnoreCase("maker_confirm_resubmit_delete")){
	if(form.getSystemBankBranchCode()==null || form.getSystemBankBranchCode().trim().equals("") )
	{
		errors.add("systemBankBranchCodeError", new ActionMessage("error.string.mandatory"));
		
	}else if( ASSTValidator.isValidAlphaNumStringWithSpace(form.getSystemBankBranchCode())){
		errors.add("systemBankBranchCodeError", new ActionMessage("error.string.invalidCharacter"));
	}
	if(form.getSystemBankBranchName()==null || form.getSystemBankBranchName().trim().equals(""))
	{
		errors.add("systemBankBranchNameError", new ActionMessage("error.string.mandatory"));
		
	}else if( ASSTValidator.isValidOtherBankBranchName(form.getSystemBankBranchName())){
		errors.add("systemBankBranchNameError", new ActionMessage("error.string.invalidCharacter"));
	}
	
	if(!(form.getContactNumber()==null||form.getContactNumber().trim().equals("")))
	{
	if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getContactNumber().toString().trim(),false,15,999999999999999.D)))
	{
			errors.add("systemBankBranchContactNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
				   "1",
				   "15"));
			DefaultLogger.debug(OtherBankValidator.class, "contactNoError");
	}
	}
	if(form.getIsVault()!=null &&form.getIsVault().trim().equals("on"))
	{
		if(form.getCustodian1().trim().equals("")&&form.getCustodian2().trim().equals(""))
		{
			errors.add("systemBankBranchCustodianError", new ActionMessage("error.string.custodian"));
		}
		
	}
	if(!(form.getFaxNumber()==null||form.getFaxNumber().trim().equals("")))
	{
	if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getFaxNumber().toString().trim(),false,15,999999999999999.D)))
	{
			errors.add("systemBankBranchFaxNumberError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
				   "1",
				   "15"));
	}
	}
	if(!(form.getContactMail()==null||form.getContactMail().trim().equals("")))
	{
	if(!Validator.ERROR_NONE.equals(errorCode = Validator.checkEmail(form.getContactMail(), 
																	  false)))
	{
		 errors.add("systemBankBranchContactMailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.EMAIL, errorCode),
				 										   "1",
				 										   "50"));
		 errors.add("systemBankBranchContactMailError", new ActionMessage("error.email.format"));
		 
	}
	}
	if((form.getContactMail()!=null && !form.getContactMail().equals("")) && (ASSTValidator.isValidEmail(form.getContactMail())) || form.getContactMail().length()>50)
	{
		//CLMJ-14
		 errors.add("systemBankBranchContactMailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.EMAIL, errorCode),
				   "1",
				   "50"));
		 errors.add("systemBankBranchContactMailError", new ActionMessage("error.email.format"));
			
	}
	if(!(form.getRbiCode()==null || form.getRbiCode().trim().equals("")) )
	{
		boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getRbiCode());
		if( nameFlag == true)
			errors.add("rbiCodeError", new ActionMessage("error.string.invalidCharacter"));
		
	}
	if((form.getContactMail()!=null && !form.getContactMail().equals("")) && (ASSTValidator.isValidEmail(form.getContactMail())) || form.getContactMail().length()>50)
	{
		 errors.add("systemBankBranchContactMailError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.EMAIL, errorCode),
				   "1",
				   "50"));
			errors.add("systemBankBranchContactMailError", new ActionMessage("error.email.format"));
			
	}
	if(StringUtils.isNotBlank(form.getPincode())) {
		 if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getPincode().toString().trim(),true,10,999999999999999.D))){
			errors.add("systemBankBranchPincodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),"1","6"));
					DefaultLogger.debug(SystemBranchValidator.class, "systemBankBranchPincodeError");
		 }
		 else if(StringUtils.isNotBlank(form.getStatePincodeString()) && StringUtils.isNotBlank(form.getState())) {
			 Map<String, String> statePincodeMap = UIUtil.getMapFromDelimitedString(form.getStatePincodeString(), ",", "=");
			 if(null != statePincodeMap && !statePincodeMap.isEmpty()) {
				 String selectedStatePincode = statePincodeMap.get(form.getState());
				 if(null != selectedStatePincode && !form.getPincode().trim().startsWith(selectedStatePincode)) {
					 errors.add("systemBankBranchPincodeError", new ActionMessage("error.pincode.incorrect"));
				 }
			 }
		 }
	}
	else {
		 errors.add("systemBankBranchPincodeError", new ActionMessage("error.string.mandatory"));
	}
	
	if(StringUtils.isBlank(form.getCityTown())){
		errors.add("systemBankBranchCityError", new ActionMessage("error.string.mandatory"));
	}
	if(StringUtils.isBlank(form.getCountry())){
		errors.add("countryOBIdError", new ActionMessage("error.string.mandatory"));
	}
	if(StringUtils.isBlank(form.getState())){
		errors.add("stateOBIdError", new ActionMessage("error.string.mandatory"));
	}
	if(StringUtils.isBlank(form.getRegion())){
		errors.add("regionOBIdError", new ActionMessage("error.string.mandatory"));
	}
	

}else if(event.equalsIgnoreCase("EODSyncMasters")) {
			// perform EOD Sync specific validation Here
			if (form.getSystemBankBranchCode() == null || form.getSystemBankBranchCode().trim().equals("")) {
				errors.add("systemBankBranchCodeError", new ActionMessage("System Bank Branch Code is mandatory"));

			} else if (!(errorCode = Validator.checkNumber(form.getSystemBankBranchCode(), true, 0, 9999)).equals(Validator.ERROR_NONE)) {
				errors.add("systemBankBranchCodeError", new ActionMessage("For System Bank Branch Code Valid range is between 0 and 9999."));

			}

			else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getSystemBankBranchCode());
				if (codeFlag == true)
					errors.add("systemBankBranchCodeError", new ActionMessage("Special Character(s) Not Allowed in System Bank Branch Code."));
			}
			if (form.getSystemBankBranchName() == null || form.getSystemBankBranchName().trim().equals("")) {
				errors.add("systemBankBranchNameError", new ActionMessage("System Bank Branch Name is mandatory"));

			} else if (!(errorCode = Validator.checkString(form.getSystemBankBranchName(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
				errors
						.add("systemBankBranchNameError", new ActionMessage(
								"For System Bank Branch Name valid range is between 0 and 50 character(s)"));
			}

			else {
				boolean nameFlag = ASSTValidator.isValidOtherBankBranchName(form.getSystemBankBranchName());
				if (nameFlag == true)
					errors.add("systemBankBranchNameError", new ActionMessage("Special Character(s) Not Allowed in System Bank Branch Name."));
			}

		}
		return errors;
    }

}
