package com.integrosys.cms.ui.otherbankbranch;

/**
 * Purpose : Used for Validating other bank branch 
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 15:13:16 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;

public class OtherBranchValidator {
	

	public static ActionErrors validateOtherBranchForm(OtherBranchForm aForm, Locale locale) {
		//<validate remarks field
    	ActionErrors errors = new ActionErrors();
		String errorCode = null;

		OtherBranchForm form = (OtherBranchForm) aForm;
		
		if(form.getOtherBankCode()==null || "".equals(form.getOtherBankCode().trim()) )
		{
			errors.add("otherBankCodeError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(OtherBranchValidator.class, "otherBankCodeError");
		}
			
		if(form.getOtherBranchName()==null || "".equals(form.getOtherBranchName().trim()) )
		{
			errors.add("otherBranchNameError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(OtherBranchValidator.class, "otherBranchNameError");
		}
		else if(!(errorCode = Validator.checkString(form.getOtherBranchName(), true, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("otherBranchNameError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		else{
			boolean nameFlag = ASSTValidator.isValidOtherBankBranchName(form.getOtherBranchName());
			if( nameFlag == true)
				errors.add("otherBranchNameError", new ActionMessage("error.string.invalidCharacter"));
		}
		
		if((form.getBranchContactMailId()!= null && !form.getBranchContactMailId().equals("")) 
				&& form.getBranchContactMailId().length()>50)
		{
				errors.add("branchContactMailIdError", new ActionMessage("error.email.length"));
				DefaultLogger.debug(OtherBankValidator.class, "branchContactMailIdError");
		}
		else if((form.getBranchContactMailId()!= null && !form.getBranchContactMailId().equals("")) 
				&& ASSTValidator.isValidEmail(form.getBranchContactMailId()))
		{
				errors.add("branchContactMailIdError", new ActionMessage("error.email.format"));
				DefaultLogger.debug(OtherBankValidator.class, "branchContactMailIdError");
		}
		
		
		
		
		if(form.getBranchContactNo()!=null && !form.getBranchContactNo().equals("") && form.getBranchContactNo().trim().length()> 15){
			errors.add("branchContactNoError", new ActionMessage("error.phone.length"));
			DefaultLogger.debug(OtherBankValidator.class, "contactNoError");
		}
		else if((form.getBranchContactNo()!= null && !form.getBranchContactNo().equals("")) && !Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getBranchContactNo().toString().trim(),true,15,999999999999999.D)))
		{
				errors.add("branchContactNoError", new ActionMessage("error.phone.invalid"));
				DefaultLogger.debug(OtherBranchValidator.class, "branchContactNoError");
		}
		else if((form.getBranchContactNo()!= null && !form.getBranchContactNo().equals("")) && !Validator.ERROR_NONE.equals(errorCode =Validator.checkPhoneNumber(form.getBranchContactNo().toString().trim(),true,locale)))
		{
				errors.add("branchContactNoError", new ActionMessage("error.phone.invalid"));
				DefaultLogger.debug(OtherBranchValidator.class, "branchContactNoError");
		}
		
		if((form.getBranchAddress() !=null && !form.getBranchAddress().equals("")) && !Validator.ERROR_NONE.equals(errorCode = Validator.checkString(form.getBranchAddress(), 
				  true,
				  1,
				  200)))
		{
				errors.add("branchAddressError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					   "1",
					   "200"));
				DefaultLogger.debug(OtherBranchValidator.class, "branchAddressError");
		}
		
		if((form.getBranchFaxNo()!=null && !form.getBranchFaxNo().trim().equals("")) && (form.getBranchFaxNo().trim().length()> 15)){
			errors.add("branchFaxNoError", new ActionMessage("error.fax.length"));
			DefaultLogger.debug(OtherBankValidator.class, "branchFaxNoError");
		}
		else if((form.getBranchFaxNo()!=null && !form.getBranchFaxNo().trim().equals("")) && !Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getBranchFaxNo().toString().trim(),true,15,999999999999999.D)))
		{
				errors.add("branchFaxNoError", new ActionMessage("error.fax.invalid"));
				DefaultLogger.debug(OtherBankValidator.class, "branchFaxNoError");
		}
		/*if((form.getBranchRbiCode()==null && form.getBranchRbiCode().equals(""))) {
			errors.add("branchRbiCodeError", new ActionMessage("error.string.mandatory"));
		}
		else*/ if((form.getBranchRbiCode()!=null && !form.getBranchRbiCode().equals("")) && form.getBranchRbiCode().length() > 9 ) {
			errors.add("branchRbiCodeError", new ActionMessage("error.rbicode.length"));
		}
		else if((form.getBranchRbiCode()!=null && !form.getBranchRbiCode().equals("")) 
				 && !Validator.ERROR_NONE.equals(errorCode = Validator.checkInteger(form.getBranchRbiCode(), false, 0,
							999999999))){
			 errors.add("branchRbiCodeError", new ActionMessage("error.rbicode.invalid"));
				DefaultLogger.debug(OtherBranchValidator.class, "branchRbiCodeError");
		 } 
		 else if(ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getBranchRbiCode())){
				//boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getOtherBranchCode());
				//if( codeFlag == true)
					errors.add("branchRbiCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
			else{
				boolean nameFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getBranchRbiCode());
				if( nameFlag == true)
					errors.add("branchRbiCodeError", new ActionMessage("error.string.invalidCharacter"));
			}
		
		
		return errors;
    }
}
