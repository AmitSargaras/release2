package com.integrosys.cms.ui.collateral.cash.cashfd;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.cms.app.collateral.bus.type.cash.ICashCollateral;
import com.integrosys.cms.app.collateral.bus.type.cash.ICashDeposit;
import com.integrosys.cms.ui.collateral.cash.AbstractCashStpValidator;

public class CashFdStpValidator extends AbstractCashStpValidator {

	public boolean validate(Map context) {
		if (!validateCashCollateral(context)) {
			return false;
		}
//		ICashDeposit[] cashDeposits = cashCollateral.getDepositInfo();
		/*if (cashDeposits != null) {
			for (int i = 0; i < cashDeposits.length; i++) {
				// not checked because boolean primitive type:
				// cashDeposits[i].getOwnBank()
				if (StringUtils.isNotBlank(cashDeposits[i].getDepositRefNo())
						&& StringUtils.isNotBlank(cashDeposits[i].getDepositReceiptNo())
						&& cashDeposits[i].getDepositAmount() != null
						&& cashDeposits[i].getDepositMaturityDate() != null
						&& StringUtils.isNotBlank(cashDeposits[i].getTenureUOM())
						) {
					// do nothing
				}
				else return false;
			}
			// do nothing
		}
		else return false;*/
		
		if (validateAndAccumulate(context).size() <= 0
//				StringUtils.isNotBlank(cashCollateral.getIssuer())
				) {
			
		}
		else return false;
		return true;
	}

	public ActionErrors validateAndAccumulate(Map context) {
		ActionErrors errorMessages = validateAndAccumulateDeposit(context);
        boolean isDepositNotPerfercted = false;

        ICashCollateral cashCollateral = (ICashCollateral) context.get(COL_OB);
		ICashDeposit[] cashDeposits = cashCollateral.getDepositInfo();
        if (cashDeposits != null) {
            for (int i = 0; i < cashDeposits.length; i++) {
                if(cashDeposits[i].getOwnBank()){
                    if (StringUtils.isBlank(cashDeposits[i].getDepositRefNo())) {
                        errorMessages.add("depositRefNo", new ActionMessage("error.mandatory"));
                        isDepositNotPerfercted = true;
                    }

                    //Andy Wong, 16 Jan 2009: apply SIBS validation MLS1034
                    /*if (!StringUtils.equals(cashCollateral.getCurrencyCode(), cashDeposits[i].getDepositCcyCode())){
                        errorMessages.add("collateralCurrency", new ActionMessage("error.security.fd.currency.not.match"));
                        isDepositNotPerfercted = true;
                    }*/
                } else {
                    if (cashDeposits[i].getIssueDate() == null) {
                        errorMessages.add("issueDate", new ActionMessage("error.mandatory"));
                        isDepositNotPerfercted = true;
                    }
                }
                if (cashDeposits[i].getDepositMaturityDate() == null) {
					errorMessages.add("depMatDate", new ActionMessage("error.mandatory"));
                    isDepositNotPerfercted = true;
                }
				// not checked because boolean primitive type:
				// cashDeposits[i].getOwnBank()
			}
		}

        if (cashCollateral.getDepositInfo() != null && cashCollateral.getDepositInfo().length > 0 && isDepositNotPerfercted) {
            errorMessages.add("valBefMargin", new ActionMessage("error.collateral.deposit.info.not.perfected"));
        }
        
        /*if (StringUtils.isBlank(cashCollateral.getIssuer())) {
			errorMessages.add("issuer", new ActionMessage("error.mandatory"));
		}*/
		return errorMessages;
	}
}
