package com.integrosys.cms.ui.collateral.guarantees.gteindem;

import com.integrosys.cms.ui.collateral.guarantees.AbstractGuaranteesStpValidator;
import org.apache.struts.action.ActionErrors;

import java.util.Map;

public class GteIndemStpValidator extends AbstractGuaranteesStpValidator {
    public boolean validate(Map context) {
        if (!validateGuaranteeCollateral(context))
        {
            return false;
        }
//		IGuaranteeCollateral guarantee = (IGuaranteeCollateral) collateral;
        if (validateAndAccumulate(context).size() <= 0
            /*collateral.getCollateralMaturityDate() != null
                   && StringUtils.isNotBlank(guarantee.getIssuingBank())*/
                ) {
            // do nothing
        } else return false;
        return true;
    }

    public ActionErrors validateAndAccumulate(Map context) {
        ActionErrors errorMessages = validateAndAccumulateGuarantees(context);

        return errorMessages;
    }
}
