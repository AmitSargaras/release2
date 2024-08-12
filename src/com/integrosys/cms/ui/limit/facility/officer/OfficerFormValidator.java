package com.integrosys.cms.ui.limit.facility.officer;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;

public class OfficerFormValidator {
	public static ActionErrors validateInput(OfficerForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		
		if (StringUtils.isBlank(form.getRelationshipCodeEntryCode())) {
			errors.add("relationshipCodeEntryCode", new ActionMessage("error.mandatory"));
		}
		if (StringUtils.isBlank(form.getOfficerTypeEntryCode())) {
			errors.add("officerTypeEntryCode", new ActionMessage("error.mandatory"));
		}
		if (StringUtils.isBlank(form.getOfficerEntryCode())) {
			errors.add("officerEntryCode", new ActionMessage("error.mandatory"));
		}
		
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
