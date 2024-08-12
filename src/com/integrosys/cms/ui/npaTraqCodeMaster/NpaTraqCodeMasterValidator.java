package com.integrosys.cms.ui.npaTraqCodeMaster;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.productMaster.ProductMasterForm;


public class NpaTraqCodeMasterValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		NpaTraqCodeMasterForm form=(NpaTraqCodeMasterForm)commonform;
		String errorCode = null;
		if(form.getEvent().equalsIgnoreCase("maker_create_npa_traq_code_master")||form.getEvent().equalsIgnoreCase("maker_edit_npa_traq_code_master")
				|| form.getEvent().equalsIgnoreCase("maker_save_update")||form.getEvent().equalsIgnoreCase("maker_draft_npa_traq_code_master")
				||form.getEvent().equalsIgnoreCase("maker_update_draft_npa_traq_code_master")
				||form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete")){
			
			if(null==form.getNpaTraqCode()||form.getNpaTraqCode().trim().equals("")){
				errors.add("npaTraqCode", new ActionMessage("error.string.mandatory"));			
			}
			else if (form.getNpaTraqCode().length() > 60){
				errors.add("npaTraqCodeLengthError", new ActionMessage("error.string.length.productCode"));
			}
			else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getNpaTraqCode());
				if (codeFlag == true)
					errors.add("npaTraqCode", new ActionMessage("error.string.invalidCharacter"));
			}
		
			if(null==form.getSecurityType()||form.getSecurityType().trim().equals("")){
				errors.add("securityType", new ActionMessage("error.string.mandatory"));			
			}
			
			if(null==form.getSecuritySubType()||form.getSecuritySubType().trim().equals("")){
				errors.add("securitySubType", new ActionMessage("error.string.mandatory"));			
			}
			
			if("PT".equals(form.getSecurityType()) && (null==form.getPropertyTypeCodeDesc()||form.getPropertyTypeCodeDesc().trim().equals(""))){
				errors.add("propertyTypeCodeDesc", new ActionMessage("error.string.mandatory"));			
			}
			
		}	
		return errors;
	}
}
