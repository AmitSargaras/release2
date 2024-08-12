package com.integrosys.cms.ui.genreqdefer;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Created by IntelliJ IDEA. User: elango Date: Jul 4, 2003 Time: 11:23:06 AM To
 * change this template use Options | File Templates.
 */
public class GenerateReqDeferFormValidator {
	public static ActionErrors validateInput(GenerateReqDeferForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		DefaultLogger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$", "Inside Validation");
		if (!(errorCode = Validator.checkString(aForm.getDeferReason(), true, 1, 640)).equals(Validator.ERROR_NONE)) {
			errors.add("deferReason", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					"640"));
		}
		if (!(errorCode = Validator.checkString(aForm.getPropName(), true, 1, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("propName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "30"));
		}
		if (!(errorCode = Validator.checkString(aForm.getPropDesi(), true, 1, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("propDesi", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "30"));
		}
		if (!(errorCode = Validator.checkString(aForm.getPropSignNo(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors
					.add("propSignNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"10"));
		}
		if (!(errorCode = Validator.checkString(aForm.getPropDate(), true, 1, 12)).equals(Validator.ERROR_NONE)) {
			errors.add("propDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", "12"));
		}

		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
