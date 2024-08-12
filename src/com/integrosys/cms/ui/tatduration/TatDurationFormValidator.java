package com.integrosys.cms.ui.tatduration;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * Created by IntelliJ IDEA. User: pooja Date: Jul 24, 2003 Time: 11:23:06 AM
 * 
 */
public class TatDurationFormValidator {
	private static TatDurationFormValidator LOG_OBJ = new TatDurationFormValidator();

	public static ActionErrors validateInput(TatDurationForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
        String durationType[] = aForm.getDurationType();
        String duration[] = aForm.getDuration();

        if (durationType != null && durationType.length > 0) {
            for (int i = 0; i < durationType.length; i++) {
                if ("".equals(durationType[i])) {
                    errors.add("tatDuration"+i, new ActionMessage("error.tatDuration.durationTypeNotSelected"));
                }
                if ("".equals(duration[i]) || "0".equals(duration[i])) {
                    errors.add("tatDuration"+i, new ActionMessage("error.tatDuration.invalidDuration"));
                }
                try {
                    Integer.parseInt(duration[i]);
                } catch (NumberFormatException nfe) {
                    errors.add("tatDuration"+i, new ActionMessage("error.amount.format"));
                }
            }
        }
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}