package com.integrosys.cms.ui.valuationAmountAndRating;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.valuationAmountAndRating.bus.ValuationAmountAndRatingDaoImpl;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.common.UIUtil;


public class ValuationAmountAndRatingValidator {

	public static ActionErrors validateInput(ActionForm commonform,Locale locale){
		ActionErrors errors = new ActionErrors();
		ValuationAmountAndRatingForm form=(ValuationAmountAndRatingForm)commonform;
		String errorCode = null;
		boolean flag = false;
		if(form.getEvent().equalsIgnoreCase("maker_create_valuation_amount_and_rating")||form.getEvent().equalsIgnoreCase("maker_edit_valuation_amount_and_rating")
				|| form.getEvent().equalsIgnoreCase("maker_save_update")||form.getEvent().equalsIgnoreCase("maker_draft_valuation_amount_and_rating")
				||form.getEvent().equalsIgnoreCase("maker_update_draft_valuation_amount_and_rating")
//				||form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_delete") 
				|| form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_create") || form.getEvent().equalsIgnoreCase("maker_confirm_resubmit_edit")){
			
			
			if(null==form.getCriteria()||form.getCriteria().trim().equals("")){
				errors.add("criteria", new ActionMessage("error.string.mandatory"));
				flag = true;
			}
			
			if(null==form.getValuationAmount()||form.getValuationAmount().trim().equals("")){
				errors.add("valuationAmount", new ActionMessage("error.string.mandatory"));	
				flag = true;
			}else {
				String num = form.getValuationAmount() ;
				num = UIUtil.removeComma(num);
				boolean isNumber=ASSTValidator.isValidDecimalNumberWithComma(num);
				if(!isNumber) {
					errors.add("valuationAmount", new ActionMessage("error.amount.format"));
					flag = true;
				}
//				else {
//					num = num.replace(".00", "");
//					form.setValuationAmount(num);
////					String numtoword = UIUtil.numberToWordsIndia(num);//formatWithCommaAndDecimalNew
////					System.out.println("numtoword ==>"+numtoword);
////					num = UIUtil.formatWithCommaAndDecimalNew(num);
////					System.out.println("numwithdecimalAdded ==>"+num);
//				}
			}
			
			if(null==form.getRamRating()||form.getRamRating().trim().equals("")){
				errors.add("ramRating", new ActionMessage("error.string.mandatory"));
				flag = true;
			}
			
			if(flag == false) {
				String ramRatingVal = form.getRamRating();
				String docAmount = 	form.getValuationAmount();
				docAmount = UIUtil.removeComma(docAmount);
				docAmount = docAmount.replace(".00", "");
				String criteriaVal = form.getCriteria();
				ValuationAmountAndRatingDaoImpl dao = new ValuationAmountAndRatingDaoImpl();
				int count = dao.getCountOfValuationamountMasterRecord(criteriaVal,docAmount,ramRatingVal);
				if(count != 0) {
					errors.add("criteria", new ActionMessage("error.combination.alreadyexist.master"));
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
