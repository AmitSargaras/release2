package com.integrosys.cms.ui.riskType;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.riskType.bus.RiskTypeDaoImpl;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;


public class RiskTypeValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		RiskTypeForm form=(RiskTypeForm)commonform;
		String errorCode = null;
		boolean flag = false;
		if(form.getEvent().equalsIgnoreCase("maker_create_risk_type")||form.getEvent().equalsIgnoreCase("maker_edit_risk_type")
				|| form.getEvent().equalsIgnoreCase("maker_save_update")||form.getEvent().equalsIgnoreCase("maker_draft_risk_type")
				||form.getEvent().equalsIgnoreCase("maker_update_draft_risk_type")
//				||form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete") 
				|| form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_create") || form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_edit")){
			
			
			if(null==form.getRiskTypeName() ||form.getRiskTypeName().trim().equals("")){
				errors.add("riskTypeName", new ActionMessage("error.string.mandatory"));
				flag = true;
			}	
			/*if(null==form.getRiskTypeCode()||form.getRiskTypeCode().trim().equals("")){
				errors.add("riskTypeCode", new ActionMessage("error.string.mandatory"));	
				flag = true;
			}*/				
			if(flag == false) {
				//String riskTypeName = form.getRiskTypeName();
				//String riskTypeCode = 	form.getRiskTypeCode();
				
				String riskTypeName = 	form.getRiskTypeName().trim();

				RiskTypeDaoImpl dao = new RiskTypeDaoImpl();
				/*int count = dao.getCountOfRiskTypeRecord(riskTypeCode);
				if(count != 0) {
					errors.add("riskTypeCode", new ActionMessage("error.riskTypeCode.alreadyexist.master"));
				}*/
				int count1 = dao.getCountOfRiskTypenNameRecord(riskTypeName);
				if(count1 != 0) {
					errors.add("riskTypeName", new ActionMessage("error.riskTypeName.alreadyexist.master"));
				}
			}
				
//			else if (form.getProductCode().length() > 4){
//				errors.add("productCodeLengthError", new ActionMessage("error.string.length.productCode"));
//			}
//			else {
//				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithoutSpace(form.getProductCode());
//				if (codeFlag == true)
//					errors.add("productCode", new ActionMessage("error.string.invalidCharacter"));
//			}
//		
//			if(null==form.getProductName()||form.getProductName().trim().equals("")){
//				errors.add("productName", new ActionMessage("error.string.mandatory"));			
//			}
//			else if (form.getProductName().length() > 50){
//				errors.add("productNameLengthError", new ActionMessage("error.string.length.productName"));
//			}
//			else {
//				boolean codeFlag = ASSTValidator.isValidAlphaNumStringWithSpace(form.getProductName());
//				if (codeFlag == true)
//					errors.add("productName", new ActionMessage("error.string.invalidCharacter"));
//			}
		}	
		return errors;
	}
}
