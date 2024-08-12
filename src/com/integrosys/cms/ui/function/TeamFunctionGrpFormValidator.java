package com.integrosys.cms.ui.function;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.feed.FeedConstants;

public class TeamFunctionGrpFormValidator implements java.io.Serializable {
	private static String LOGOBJ = TeamFunctionGrpFormValidator.class.getName();

	public static ActionErrors validateInput(TeamFunctionGrpForm form, Locale locale) {

		String event = form.getEvent();
		ActionErrors errors = new ActionErrors();

		String errorCode = "";
		if (TeamFunctionGrpAction.EVENT_SUBMIT.equals(event)) {
			if (!(form.getIsDisb() || form.getIsPreDisb() || form.getIsPostDisb())) {
				errors.add("function", new ActionMessage(FeedConstants.ERROR_CHKBOX_MANDATORY));
			}
		}
		DefaultLogger.debug(LOGOBJ, "errors " + errors.size());
		return errors;
	}
}
