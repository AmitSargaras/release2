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

package com.integrosys.cms.ui.commoncodeentry.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

public class CCEntryFormValidator {

	public static ActionErrors validateInput(ActionForm commonform, Locale locale) {
		return validate((MaintainCCEntryForm) commonform);
	}

	public static ActionErrors validate(MaintainCCEntryForm aForm) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		try {
			if(aForm.getEvent()== null || !(aForm.getEvent().equalsIgnoreCase("MASTER_SYNC"))){

				if (!(errorCode = Validator.checkString(aForm.getEntryName().trim(), true, 1, 255)).equals(Validator.ERROR_NONE)) {
					errors.add("entryName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"255"));
				}
			
				if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(aForm.getEntryCode(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("entryCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"40"));
				}
		}
		/*
		 * if (isEmptyString(aForm.getCountry())) { errors.add("country",new
		 * ActionMessage("error.string.mandatory", "1", "7")); } if
		 * (isEmptyString(aForm.getRefCategoryCode())) {
		 * errors.add("refCategoryCode",new ActionMessage("error.string.mandatory", "1",
		 * "9")); }
		 */
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
		}
		return errors;
	}
}
