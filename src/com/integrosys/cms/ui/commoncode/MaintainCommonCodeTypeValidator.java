package com.integrosys.cms.ui.commoncode;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: marvin $<br>
 * @version $Id$
 * @since $Id$
 */
public class MaintainCommonCodeTypeValidator {

	public static ActionErrors validateInput(MaintainCommonCodeTypeForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";
		try {
			if (!(errorCode = Validator.checkStringWithNoSpecialCharsAndSpace(form.getCategoryCode(), true, 1, 40))
					.equals(Validator.ERROR_NONE)) {
				errors.add("categoryCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"40"));
			}
			if (!(errorCode = Validator.checkString(form.getCategoryName(), true, 1, 255)).equals(Validator.ERROR_NONE)) {
				errors.add("categoryName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						"255"));
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return errors;

	}

}