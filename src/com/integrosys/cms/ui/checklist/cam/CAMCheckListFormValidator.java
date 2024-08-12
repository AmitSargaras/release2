package com.integrosys.cms.ui.checklist.cam;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class CAMCheckListFormValidator {
	public static ActionErrors validateInput(CAMCheckListForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		String event = aForm.getEvent();

		if ("replace".equals(event)) {
			if (!(errorCode = Validator.checkString(aForm.getReplaceCheckListItemRef(), true, 1, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("replaceCheckListItemRef", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "1", "100"));
			}
		}
		else {
			if (!(errorCode = Validator.checkString(aForm.getDocDesc(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("docDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"100"));
			}
		}
		return errors;
	}
}
