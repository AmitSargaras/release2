package com.integrosys.cms.ui.otherbankbranch;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;


/**
 * Purpose : Used for Validating other bank  
 *
 * @author $Author: Dattatray Thorat $
 * @version $Revision: 1.0 $
 * @since $Date: 2011-02-18 15:13:16 +0800 (Fri, 18 Feb 2011) $
 * Tag : $Name$
 */

public class OtherBankValidator {
	

	public static ActionErrors validateOtherBankForm(OtherBankForm aForm, Locale locale) {
		//<validate remarks field
    	ActionErrors errors = new ActionErrors();
		String errorCode = null;

		DefaultLogger.debug(OtherBankValidator.class," in validateOtherBankForm");
		
		OtherBankForm form = (OtherBankForm) aForm;
		
		if(form.getOtherBankCode()==null || "".equals(form.getOtherBankCode().trim()) )
		{
			errors.add("otherBankCodeError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(OtherBranchValidator.class, "otherBankCodeError");
		}
		else if((form.getOtherBankCode()!=null && !form.getOtherBankCode().trim().equals("")) 
				&& form.getOtherBankCode().length()>30)
		{
				errors.add("otherBankCodeError", new ActionMessage("error.string.length"));
				DefaultLogger.debug(OtherBankValidator.class, "otherBankCodeError");
		}
		else{
			boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getOtherBankCode());
			if( codeFlag == true)
				errors.add("otherBankCodeError", new ActionMessage("error.string.invalidCharacter","OtherBank Code"));
		}
		
		StringBuffer sb = new StringBuffer("HDFC");
		
		if(form.getOtherBankName()==null || "".equals(form.getOtherBankName().trim()) )
		{
			errors.add("otherBankNameError", new ActionMessage("error.string.mandatory"));
			DefaultLogger.debug(OtherBranchValidator.class, "otherBankNameError");
		}
		else if((form.getOtherBankName()!=null && !form.getOtherBankName().trim().equals("")) 
				&& form.getOtherBankName().length()>50)
		{
				errors.add("otherBankNameError", new ActionMessage("error.string.length"));
				DefaultLogger.debug(OtherBankValidator.class, "otherBankNameError");
		}
		
		else if(form.getOtherBankName().toUpperCase().startsWith("HDFC"))
		{
			errors.add("otherBankNameError", new ActionMessage("error.systembank"));
			DefaultLogger.debug(OtherBankValidator.class, "otherBankNameError");
		}
		else{
			boolean nameFlag = ASSTValidator.isValidOtherBankName(form.getOtherBankName());
			if( nameFlag == true)
				errors.add("otherBankNameError", new ActionMessage("error.string.invalidCharacter"));
		}
		
		if( form.getContactMailId()!= null && !form.getContactMailId().equals("") ){ 
			if( form.getContactMailId().length() > 50 )
			{
				errors.add("contactMailIdError", new ActionMessage("error.email.length"));
				DefaultLogger.debug(OtherBankValidator.class, "contactMailIdError");
			}
			else if( ASSTValidator.isValidEmail(form.getContactMailId()) )
			{
					errors.add("contactMailIdError", new ActionMessage("error.email.format"));
					DefaultLogger.debug(OtherBankValidator.class, "contactMailIdError");
			}
		}
		
		if((form.getContactNo()!=null && !form.getContactNo().trim().equals("")) && (form.getContactNo().trim().length()> 15)){
			errors.add("contactNoError", new ActionMessage("error.phone.length"));
			DefaultLogger.debug(OtherBankValidator.class, "contactNoError");
		}
		else if((form.getContactNo()!=null && !form.getContactNo().trim().equals("")) && !Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getContactNo().toString().trim(),true,15,999999999999999.D)))
		{
				errors.add("contactNoError", new ActionMessage("error.phone.invalid"));
				DefaultLogger.debug(OtherBankValidator.class, "contactNoError");
		}
		
		else if((form.getContactNo()!=null && !form.getContactNo().trim().equals("")) && !Validator.ERROR_NONE.equals(errorCode =Validator.checkPhoneNumber(form.getContactNo().toString().trim(),true,locale)))
		{
				errors.add("contactNoError", new ActionMessage("error.phone.invalid"));
				DefaultLogger.debug(OtherBankValidator.class, "contactNoError");
		}
		
		if((form.getAddress()!=null && !form.getAddress().trim().equals("")) 
				&& form.getAddress().length()>200)
		{
				errors.add("addressError", new ActionMessage("error.address.length"));
				DefaultLogger.debug(OtherBankValidator.class, "addressError");
		}
		
		
		if((form.getFaxNo()!=null && !form.getFaxNo().trim().equals("")) && (form.getFaxNo().trim().length()> 15)){
			errors.add("faxNoError", new ActionMessage("error.fax.length"));
			DefaultLogger.debug(OtherBankValidator.class, "contactNoError");
		}
		else if((form.getFaxNo()!=null && !form.getFaxNo().trim().equals("")) && !Validator.ERROR_NONE.equals(errorCode =Validator.checkNumber(form.getFaxNo().toString().trim(),true,15,999999999999999.D)))
		{
				errors.add("faxNoError", new ActionMessage("error.fax.invalid"));
				DefaultLogger.debug(OtherBankValidator.class, "faxNoError");
		}
		 
		
		return errors;
    }
}
