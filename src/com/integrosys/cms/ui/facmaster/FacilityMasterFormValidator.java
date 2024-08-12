package com.integrosys.cms.ui.facmaster;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalFormValidator;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class FacilityMasterFormValidator {
	public static ActionErrors validateInput(FacilityMasterForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String go=aForm.getGo();
		if(go.equalsIgnoreCase("y")&&!(aForm.getEvent().equalsIgnoreCase("view_template_item")||aForm.getEvent().equals("edit_template_item"))){
		if (!(errorCode = Validator.checkString(aForm.getDocDesc(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("docDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "100"));
		}
		else if(!(errorCode = Validator.checkString(aForm.getDocDesc(), true, 0, 200)).equals(Validator.ERROR_NONE)) {
			errors.add("docDescError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					200 + ""));
		}
		
		if (!(errorCode = Validator.checkDate(aForm.getExpDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("expDate", new ActionMessage("error.date.format"));
		}
		if (aForm.getExpDate().length() > 0) {
			Date d = DateUtil.getDate();
			int a = d.compareTo(DateUtil.convertDate(locale, aForm.getExpDate()));
			DefaultLogger.debug("vaidation ***********************************", "Eroororr date " + a);
			if (a > 0) {
				errors.add("expDate", new ActionMessage("error.date.compareDate", "Due Date", "Current Date"));
			}
		}
		else if(!(errorCode = Validator.checkString(aForm.getDocCode(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("docCodeError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}
		if(!(errorCode = Validator.checkString(aForm.getExpDate(), true, 0, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("expDateError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					3 + ""));
		}
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}else{
			if(go.equalsIgnoreCase("y")){
				if (DocumentationGlobalFormValidator.isValidDocumentName(aForm.getDocDesc())) {
					errors.add("itemDescError", new ActionMessage("error.string.specialcharacter"));
				}
				if (DocumentationGlobalFormValidator.isValidDocumentName(aForm.getDocCode())) {
					errors.add("itemCodeError", new ActionMessage("error.string.specialcharacter"));
				}
					if(!aForm.getTenureCount().equalsIgnoreCase("")){
						if (!(errorCode = Validator.checkNumber(aForm.getTenureCount(), false,0,
								999, 3, locale)).equals(Validator.ERROR_NONE)) {
							errors.add("tenureError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),"0",
									"999"));
						}
						
					
					}
					
			
			
		}
		
		}
		
//		if(aForm.getEvent().equalsIgnoreCase("submit_template_item")){
//			
//			if ((aForm.getFacilityCategory() == null) || "".equals(aForm.getFacilityCategory())) {
//				errors.add("facilitycategoryerror", new ActionMessage("error.facilitymaster.facilities.mandatory"));
//			}	
//			if ((aForm.getFacilityType() == null) || "".equals(aForm.getFacilityType())) {
//				errors.add("facilitytypeerror", new ActionMessage("error.facilitymaster.facilities.mandatory"));
//			}	
//			if ((aForm.getSystem() == null) || "".equals(aForm.getSystem())) {
//				errors.add("systemfacerror", new ActionMessage("error.facilitymaster.facilities.mandatory"));
//			}	
//		}
		
		return errors;
	}
}
