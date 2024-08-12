package com.integrosys.cms.ui.collateral.guarantees;

import java.util.Map;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.collateral.bus.ICollateralLimitMap;
import com.integrosys.cms.app.collateral.bus.type.guarantee.IGuaranteeCollateral;
import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;

public abstract class AbstractGuaranteesStpValidator extends AbstractCollateralStpValidator {

	protected boolean validateGuaranteeCollateral(Map context) {
		if (!validateCommonCollateral(context)) {
			return false;
		}
		if (validateAndAccumulateGuarantees(context).size() <= 0) {

		}
		else
			return false;

		IGuaranteeCollateral guaranteeCollateral = (IGuaranteeCollateral) context.get(COL_OB);
		ICollateralLimitMap[] gtCollateralLimitMap = (ICollateralLimitMap[]) guaranteeCollateral.getCollateralLimits();
		double pledgeAmt = 0;
		for (int i = 0; i < gtCollateralLimitMap.length; i++) {
			if (gtCollateralLimitMap[i].getPledgeAmount() != null)
				pledgeAmt = pledgeAmt + gtCollateralLimitMap[i].getPledgeAmount().getAmount();
		}
		if (pledgeAmt > 0) {
			double gtAmt = guaranteeCollateral.getGuaranteeAmount().getAmount();
			if (gtAmt < pledgeAmt) {
				return false;
			}
		}

		if ((guaranteeCollateral.getCollateralMaturityDate() != null && guaranteeCollateral.getGuaranteeDate() == null)
				|| (guaranteeCollateral.getCollateralMaturityDate() == null && guaranteeCollateral.getGuaranteeDate() != null)) {
			return false;
		}

		if (guaranteeCollateral.getCollateralMaturityDate() != null && guaranteeCollateral.getGuaranteeDate() != null) {
			if (guaranteeCollateral.getGuaranteeDate().after(guaranteeCollateral.getCollateralMaturityDate())) {
				return false;
			}
		}
		return true;
	}

	public boolean validate(Map context) {
		// TODO Auto-generated method stub
		return false;
	}

	protected ActionErrors validateAndAccumulateGuarantees(Map context) {
		ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);

		IGuaranteeCollateral guarantee = (IGuaranteeCollateral) context.get(COL_OB);
		
		// Deposit Expiration date DDMMYYYY
		/*if (guarantee.getCollateralMaturityDate() == null) {
			errorMessages.add("collateralMaturityDate", new ActionMessage("error.mandatory"));
		}*/
		
		// Guarantee Amount
		if (guarantee.getGuaranteeAmount() == null) {
			errorMessages.add("amtGuarantee", new ActionMessage("error.mandatory"));
		}

		ICollateralLimitMap[] gtCollateralLimitMap = (ICollateralLimitMap[]) guarantee.getCollateralLimits();

		double pledgeAmt = 0;
		if (gtCollateralLimitMap != null && gtCollateralLimitMap.length > 0) {
			for (int i = 0; i < gtCollateralLimitMap.length; i++) {
				if (gtCollateralLimitMap[i].getPledgeAmount() != null) {
					pledgeAmt = pledgeAmt + gtCollateralLimitMap[i].getPledgeAmount().getAmount();
				}
			}
			double gtAmt = guarantee.getGuaranteeAmount().getAmount();
			if (pledgeAmt > 0) {
				if (gtAmt < pledgeAmt) {
					errorMessages.add("amtGuarantee", new ActionMessage("error.amount.lessthan", String
							.valueOf(pledgeAmt)));
				}
			}
		}

		if (guarantee.getCollateralMaturityDate() != null && guarantee.getGuaranteeDate() == null) {
			errorMessages.add("dateGuarantee", new ActionMessage("error.collateral.guarantee.exDate.conDate.provide"));
		}

		if (guarantee.getCollateralMaturityDate() == null && guarantee.getGuaranteeDate() != null) {
			errorMessages.add("collateralMaturityDate", new ActionMessage(
					"error.collateral.guarantee.security.maturity.date"));
		}

		if (guarantee.getCollateralMaturityDate() != null && guarantee.getGuaranteeDate() != null) {
			if (guarantee.getGuaranteeDate().after(guarantee.getCollateralMaturityDate())) {
				errorMessages.add("dateGuarantee", new ActionMessage("error.date.compareDate.cannotBelater",
						"Guarantee Start Date", "Security Maturity Date"));
			}
		}
		return errorMessages;
	}
}
