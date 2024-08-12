/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/CommDocValidator.java,v 1.3 2004/06/10 10:19:55 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2004/06/10 10:19:55 $ Tag: $Name: $
 */

public class CommDocValidator {
	public static ActionErrors validateInput(CommDocForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (aForm.getEvent().equals(CommDocAction.EVENT_EDIT)) {
			if (!(errorCode = Validator.checkString(aForm.getDocStatusConfirm(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("docStatusConfirm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 10 + ""));
			}
		}
		else {
			if (!(errorCode = Validator.checkString(aForm.getDocStatusConfirm(), false, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("docStatusConfirm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 10 + ""));
			}
		}

		return errors;
	}
}
