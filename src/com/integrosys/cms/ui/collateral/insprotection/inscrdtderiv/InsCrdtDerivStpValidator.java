package com.integrosys.cms.ui.collateral.insprotection.inscrdtderiv;

import com.integrosys.cms.ui.collateral.insprotection.AbstractInsProtectionStpValidator;
import org.apache.struts.action.ActionErrors;

import java.util.Map;

public class InsCrdtDerivStpValidator extends AbstractInsProtectionStpValidator {
    public boolean validate(Map context) {
        if (!validateInsuranceCollateral(context)) {
            return false;
        }
        return true;
    }

    public ActionErrors validateAndAccumulate(Map context) {
        ActionErrors errorMessages = validateAndAccumulateInsurance(context);

        return errorMessages;
    }
}
