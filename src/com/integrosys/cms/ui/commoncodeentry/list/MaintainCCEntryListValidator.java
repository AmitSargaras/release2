/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * CommonCodeFormValidator.java
 *
 * Created on February 5, 2007, 5:42 PM
 *
 * Purpose:
 * Description:
 *
 * @Author: BaoHongMan
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.ui.commoncodeentry.list;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.docglobal.DocumentationGlobalFormValidator;

public class MaintainCCEntryListValidator {
	public static ActionErrors validateForm(MaintainCCEntryListForm aForm) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		// boolean isMaintainRef = "Y".equals(aForm.getMaintainRef());
		if(!aForm.getGo().equals("")&&!aForm.getEvent().equalsIgnoreCase("maker_edit_common_code_params_edit")&&
				!aForm.getEvent().equalsIgnoreCase("read")){
		String entryName[] = aForm.getEntryName();
		String refCategoryCode[] = aForm.getRefCategoryCode();
		if ((entryName == null) && (refCategoryCode == null)) {
			errors.add("NoEntryError", new ActionMessage("error.no.entries"));
		}
		
		if (entryName != null&&entryName.length>0){
			for(int index=0;index<entryName.length;index++){
				if (!(errorCode = Validator.checkString(entryName[index], true, 1, 255)).equals(Validator.ERROR_NONE)) {
					errors.add("entryName_"+index, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"255"));
				}
			}
		}
		String[] entryCode = aForm.getEntryCode();
		/*if (entryCode != null&&entryCode.length>0){
			for(int index=0;index<entryCode.length;index++){		
				if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(entryCode[index], true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("entryCode_"+index, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"40"));
				}
			}
		}*/
		}
		else{
			if(aForm.getGo().equalsIgnoreCase("y")){
			/*if (!(errorCode = Validator.checkString(aForm.getCodeValue(), true, 1, 40))
					.equals(Validator.ERROR_NONE)) {
				if(!errorCode.equalsIgnoreCase("mandatory"))
				errors.add("codeValueError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"40"));
			}*/
			/*if (!(errorCode = Validator.checkString(aForm.getCodeDescription(), true, 1, 40))
					.equals(Validator.ERROR_NONE)) {
				if(!errorCode.equalsIgnoreCase("mandatory"))
				errors.add("codeDescriptionError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"40"));
			}*/
			
				if (DocumentationGlobalFormValidator.isValidDocumentName(aForm.getCodeValue())) {
					errors.add("codeValueError", new ActionMessage("error.string.specialcharacter"));
				}
				if (DocumentationGlobalFormValidator.isValidDocumentName(aForm.getCodeDescription())) {
					errors.add("codeDescriptionError", new ActionMessage("error.string.specialcharacter"));
				}
			
			
		}
		}
		
		return errors;
	}
}
