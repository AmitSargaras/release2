package com.integrosys.cms.ui.collateral.guarantees.gteslcsame;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.ui.collateral.guarantees.AbstractGuaranteesStpValidator;

import java.util.Map;

public class GteSLCSameStpValidator extends AbstractGuaranteesStpValidator {
	public boolean validate(Map context) {
		if (!validateGuaranteeCollateral(context)) {
			return false;
		}
//		IGuaranteeCollateral guarantee = (IGuaranteeCollateral) collateral;
		if (validateAndAccumulate(context).size() <= 0
				/*StringUtils.isNotBlank(guarantee.getReferenceNo())
				&& collateral.getCollateralMaturityDate() != null
				&& StringUtils.isNotBlank(guarantee.getIssuingBank())
				&& guarantee.getIssuingDate() != null*/
				) {
			// do nothing
		}
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateGuarantees(context);
		
		IGuaranteeCollateral guarantee = (IGuaranteeCollateral) context.get(COL_OB);

		// Bank/Branch code
	/*	if (StringUtils.isBlank(guarantee.getIssuingBank())) {
			errorMessages.add("secIssueBank", new ActionMessage("error.mandatory"));
		}*/
		// Issue Date DDMMYYYY
		/*if (guarantee.getIssuingDate() == null) {
			errorMessages.add("issuingDate", new ActionMessage("error.mandatory"));
		}*/
        if (guarantee.getIssuingDate() != null && guarantee.getCollateralMaturityDate() != null) {
            if(guarantee.getIssuingDate().after(guarantee.getCollateralMaturityDate())){
                errorMessages.add("issuingDate", new ActionMessage("error.date.compareDate.cannotBelater", "Issue Date",
					"Security Maturity Date"));
            }
        }
		return errorMessages;
	}
}
