package com.integrosys.cms.ui.limit.facility.reduction;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.limit.facility.common.FacilityIncrementalReductionCommonFormValidator;

import java.util.Locale;

public class FacilityReductionFormValidator {
	public static ActionErrors validateInput(FacilityReductionForm form, Locale locale) {
		ActionErrors errors = FacilityIncrementalReductionCommonFormValidator.validateInput(form, locale);
		
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
