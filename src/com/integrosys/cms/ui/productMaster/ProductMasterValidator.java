package com.integrosys.cms.ui.productMaster;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;


public class ProductMasterValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		ProductMasterForm form=(ProductMasterForm)commonform;
		String errorCode = null;
		if(form.getEvent().equalsIgnoreCase("maker_create_product_master")||form.getEvent().equalsIgnoreCase("maker_edit_product_master")
				|| form.getEvent().equalsIgnoreCase("maker_save_update")||form.getEvent().equalsIgnoreCase("maker_draft_product_master")
				||form.getEvent().equalsIgnoreCase("maker_update_draft_product_master")
				||form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete")){
			
			if(null==form.getProductCode()||form.getProductCode().trim().equals("")){
				errors.add("productCode", new ActionMessage("error.string.mandatory"));			
			}
			else if (form.getProductCode().length() > 4){
				errors.add("productCodeLengthError", new ActionMessage("error.string.length.productCode"));
			}
			else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getProductCode());
				if (codeFlag == true)
					errors.add("productCode", new ActionMessage("error.string.invalidCharacter"));
			}
		
			if(null==form.getProductName()||form.getProductName().trim().equals("")){
				errors.add("productName", new ActionMessage("error.string.mandatory"));			
			}
			else if (form.getProductName().length() > 50){
				errors.add("productNameLengthError", new ActionMessage("error.string.length.productName"));
			}
			else {
				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getProductName());
				if (codeFlag == true)
					errors.add("productName", new ActionMessage("error.string.invalidCharacter"));
			}
		}	
		return errors;
	}
}
