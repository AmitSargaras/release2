package com.integrosys.cms.ui.collateral.property.propspother;

import com.integrosys.cms.ui.collateral.property.AbstractPropertyStpValidator;
import org.apache.struts.action.ActionErrors;

import java.util.Map;

public class PropSpOtherStpValidator extends AbstractPropertyStpValidator {
    public boolean validate(Map context) {
        if (!validatePropertyCollateral(context)) {
            return false;
        }
        if (validateAndAccumulate(context).size() <= 0) {

        } else return false;
        return true;
    }

    public ActionErrors validateAndAccumulate(Map context) {
        ActionErrors errorMessages = validateAndAccumulateProperty(context);

        return errorMessages;
    }
}
