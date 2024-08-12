package com.integrosys.cms.ui.collateral.cash;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.ui.collateral.AbstractCollateralStpValidator;

public abstract class AbstractCashStpValidator extends AbstractCollateralStpValidator {

	protected boolean validateCashCollateral(Map context) {
		if (!validateCommonCollateral(context)) {
			return false;
		}

		return true;
	}

	public abstract boolean validate(Map context);

	protected ActionErrors validateAndAccumulateDeposit(Map context) {
		ActionErrors errorMessages = validateAndAccumulateCommonCollateral(context);
		boolean isDepositNotPerfercted = false;

		ICashCollateral cashCollateral = (ICashCollateral) context.get(COL_OB);
		ICashDeposit[] cashDeposits = cashCollateral.getDepositInfo();
		if (cashDeposits != null) {
			for (int i = 0; i < cashDeposits.length; i++) {
				if (cashDeposits[i].getDepositAmount() == null
						|| StringUtils.isBlank(cashDeposits[i].getDepositAmount().getCurrencyCode())) {
					errorMessages.add("depAmt", new ActionMessage("error.mandatory"));
					isDepositNotPerfercted = true;
				}
			}
		}

		if (cashCollateral.getDepositInfo() == null || cashCollateral.getDepositInfo().length == 0) {
			errorMessages.add("valBefMargin", new ActionMessage("error.collateral.deposit.info.missing"));
		}
		else if (isDepositNotPerfercted) {
			errorMessages.add("valBefMargin", new ActionMessage("error.collateral.deposit.info.not.perfected"));
		}

		return errorMessages;
	}
}
