package com.integrosys.cms.ui.limit.facility.incremental;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import java.util.Locale;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.limit.facility.common.FacilityIncrementalReductionCommonFormValidator;


public class FacilityIncrementalFormValidator  {
	public static ActionErrors validateInput(FacilityIncrementalForm form, Locale locale) {
		ActionErrors errors = FacilityIncrementalReductionCommonFormValidator.validateInput(form, locale);
		
		String event = form.getEvent();
		String errorCode = null;
		//boolean isMandatoryValidate = false;	
		
		if (StringUtils.isBlank(form.getPurpose())) {
			errors.add("purpose", new ActionMessage("error.mandatory"));
		}
		
		if (!(errorCode = Validator.checkDate(form.getOfferDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("offerDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "1", "256"));
		}			

		if (!(errorCode = Validator.checkNumber(form.getPrimeReviewTerm(), false, 0,
				99999)).equals(Validator.ERROR_NONE)) {
			errors.add("primeReviewTerm", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					99999 + ""));
		}	
						
		DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		return errors;
	}
}
