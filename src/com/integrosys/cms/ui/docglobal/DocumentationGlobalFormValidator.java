package com.integrosys.cms.ui.docglobal;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.asst.validator.ASSTValidator;
import com.integrosys.cms.ui.recurrentMaster.RecurrentGlobalForm;

/**
 * Validator for creating a global-level document.
 * @author elango
 * @author Chong Jun Yong
 * @since Jul 4, 2003
 */
public class DocumentationGlobalFormValidator extends ASSTValidator {

	public static ActionErrors validateInput(DocumentationGlobalForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();

		String errorCode = "";
		
		if (!(errorCode = Validator.checkString(aForm.getItemDesc(), true, 1, 200)).equals(Validator.ERROR_NONE)) {
			errors.add("itemDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "200"));
		}		
		if (isValidDocumentName(aForm.getItemDesc())) {
			errors.add("itemDesc", new ActionMessage("error.string.specialcharacter"));
		}
		if(aForm.getTenureCount().equals("")){
			errors.add("tenureError", new ActionMessage("error.integer.mandatory"));
		}else{
		if(!Validator.ERROR_NONE.equals(errorCode =Validator.checkInteger(aForm.getTenureCount().toString().trim(),true,1,999)))
		{
				errors.add("tenureError", new ActionMessage("error.integer.format"));
		}else{
			if(aForm.getTenureType().equals("")){
				errors.add("tenureTypeError", new ActionMessage("error.string.mandatory"));
				}
			}
		}	
		if(aForm instanceof RecurrentGlobalForm && StringUtils.isBlank(aForm.getStatementType())) {
			errors.add("statementError", new ActionMessage("error.string.mandatory"));
		}
		return errors;
	}
}