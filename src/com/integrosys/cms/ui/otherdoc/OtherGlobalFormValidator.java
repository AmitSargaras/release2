package com.integrosys.cms.ui.otherdoc;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalForm;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalFormValidator;
import com.integrosys.cms.ui.recurrentMaster.RecurrentGlobalForm;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class OtherGlobalFormValidator extends DocumentationGlobalFormValidator {
	public static ActionErrors validateInput(DocumentationGlobalForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String go="";
		OtherGlobalForm rForm=(OtherGlobalForm)aForm;
		if(aForm instanceof OtherGlobalForm ){
			go=rForm.getGo();
		}
		if(rForm.getEvent().equalsIgnoreCase("create_doc_item")||"update_doc_item".equalsIgnoreCase(rForm.getEvent())){
		errors = DocumentationGlobalFormValidator.validateInput(aForm, locale);
		
		if (!(errorCode = Validator.checkString(aForm.getDocVersion(), true, 1, 100)).equals(Validator.ERROR_NONE)
				|| aForm.getDocVersion().equals("")) {
			errors.add("docVersion", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "100"));
		}
		DefaultLogger.debug(" Total Errors", "-------111111111111111111111111111111111-->" + errors.size());
		}else{
			if(go.equalsIgnoreCase("y")){
				if (isValidDocumentName(rForm.getOtherDocDesc())) {
					errors.add("itemDescError", new ActionMessage("error.string.specialcharacter"));
				}
				if (isValidDocumentName(rForm.getOtherDocCode())) {
					errors.add("itemCodeError", new ActionMessage("error.string.specialcharacter"));
				}
					if(!rForm.getOtherDocTenureCount().equalsIgnoreCase("")){
						if (!(errorCode = Validator.checkNumber(rForm.getOtherDocTenureCount(), false,0,
								999, 3, locale)).equals(Validator.ERROR_NONE)) {
							errors.add("tenureError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),"0",
									"999"));
						}
						
					
					}
					
			
			
		}
		
		}
		return errors;
		
	}

}
