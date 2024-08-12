package com.integrosys.cms.ui.collateral.insprotection;

import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;
import org.apache.struts.action.ActionErrors;

import java.util.Map;

public abstract class AbstractInsProtectionStpValidator extends AbstractCollateralStpValidator {

    protected boolean validateInsuranceCollateral(Map context) {
        if (!validateCommonCollateral(context)) {
            return false;
        }
        return true;
    }

    public boolean validate(Map context) {
        // TODO Auto-generated method stub
        return false;
    }

    protected ActionErrors validateAndAccumulateInsurance(Map context) {
        ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);

        return errorMessages;
    }

}
