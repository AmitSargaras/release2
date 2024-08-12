package com.integrosys.cms.ui.limit.facility.main;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import org.apache.struts.action.ActionErrors;

import java.util.Set;

public class InsuranceObjectValidator {
    public static ActionErrors validateObject(Set InsuranceSet) {
        ActionErrors InsuranceErrors = new ActionErrors();

        DefaultLogger.debug(" FacilityInsurance Total Errors", "--------->" + InsuranceErrors.size());
        return InsuranceErrors.size() == 0 ? null : InsuranceErrors;
    }
}
