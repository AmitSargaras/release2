/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/loanagency/LoanAgencyCmdValidator.java,v 1.2 2004/07/28 02:05:42 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.loanagency;

import java.math.BigDecimal;
import java.util.HashMap;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.collateral.bus.type.commodity.ILoanAgency;
import com.integrosys.cms.app.collateral.bus.type.commodity.OBLoanAgency;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2004/07/28 02:05:42 $ Tag: $Name: $
 */
public class LoanAgencyCmdValidator {
	public static HashMap validateInput(ILoanAgency loanAgencyObj, HashMap exceptionMap) throws Exception {
		// HashMap exceptionMap = new HashMap();

		if ((loanAgencyObj.getMaxNumberOfDrawdownsAllowed() != ICMSConstant.INT_INVALID_VALUE)
				&& (loanAgencyObj.getMinNumberOfDrawdownsAllowed() != ICMSConstant.INT_INVALID_VALUE)) {
			if (loanAgencyObj.getMaxNumberOfDrawdownsAllowed() < loanAgencyObj.getMinNumberOfDrawdownsAllowed()) {
				exceptionMap.put("minNumDrawdownAllow", new ActionMessage("error.amount.not.greaterthan",
						"No. Drawdown(s) Allowed (Minimum)", "No. Drawdown(s) Allowed (Maximum)"));
			}
		}

		if ((loanAgencyObj.getMinDrawdownAmountAllowed() != null)
				&& (loanAgencyObj.getMaxDrawdownAmountAllowed() != null)) {
			try {
				Amount minAmount = AmountConversion.getConversionAmount(loanAgencyObj.getMinDrawdownAmountAllowed(),
						loanAgencyObj.getMaxDrawdownAmountAllowed().getCurrencyCode());
				if (minAmount.getAmountAsBigDecimal().compareTo(
						loanAgencyObj.getMaxDrawdownAmountAllowed().getAmountAsBigDecimal()) > 0) {
					exceptionMap.put("minDrawdownAllowAmt", new ActionMessage("error.amount.not.greaterthan",
							"Drawdown Amount Allowed (Minimum)", "Drawdown Amount Allowed (Maximum)"));
				}
			}
			catch (Exception e) {
				DefaultLogger.info("LoanAgencyCmdValidator", "Forex Error at validate drawdown amount allowed... ");
				// throw new Exception (e.getMessage());
			}
		}

		if ((loanAgencyObj.getMinAssignmentFees() != null) && (loanAgencyObj.getMaxAssignmentFees() != null)) {
			try {
				Amount minAmount = AmountConversion.getConversionAmount(loanAgencyObj.getMinAssignmentFees(),
						loanAgencyObj.getMaxAssignmentFees().getCurrencyCode());
				if (minAmount.getAmountAsBigDecimal().compareTo(
						loanAgencyObj.getMaxAssignmentFees().getAmountAsBigDecimal()) > 0) {
					exceptionMap.put("minAssignmentFeeAmt", new ActionMessage("error.amount.not.greaterthan",
							"Assignment Fees (Minimum)", "Assignment Fees (Maximum)"));
				}
			}
			catch (Exception e) {
				DefaultLogger.info("LoanAgencyCmdValidator", "Forex Error at validate assignment fees... ");
				// throw new Exception (e.getMessage());
			}
		}

		return exceptionMap;
	}

	public static HashMap validateGlobalAmount(ILoanAgency loanAgencyObj, HashMap exceptionMap) {
		if ((loanAgencyObj.getParticipants() != null) && (loanAgencyObj.getParticipants().length > 0)
				&& (loanAgencyObj.getGlobalAmount() == null)) {
			exceptionMap.put("globalAmountAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					Validator.ERROR_MANDATORY)));
		}
		else {
			exceptionMap = validateParticipantAmt(loanAgencyObj, exceptionMap, false);
		}

		return exceptionMap;
	}

	public static HashMap validateParticipant(ILoanAgency loanAgencyObj, HashMap exceptionMap) {
		if (loanAgencyObj.getGlobalAmount() == null) {
			exceptionMap.put("particpantErr", new ActionMessage("error.collateral.commodity.loan.participant"));
		}
		return exceptionMap;
	}

	public static HashMap validateParticipantAmt(ILoanAgency loanAgencyObj, HashMap exceptionMap, boolean isAdd) {
		if ((loanAgencyObj.getGlobalAmount() != null) && (loanAgencyObj.getParticipants() != null)
				&& (loanAgencyObj.getParticipants().length > 0)) {
			BigDecimal totalAllocatedAmount = null;
			Amount amt = ((OBLoanAgency) loanAgencyObj).getTotalAllocatedAmount();
			if (amt != null) {
				totalAllocatedAmount = amt.getAmountAsBigDecimal();
			}
			if (totalAllocatedAmount != null) {
				if (isAdd) {
					if (totalAllocatedAmount.compareTo(loanAgencyObj.getGlobalAmount().getAmountAsBigDecimal()) >= 0) {
						exceptionMap.put("particpantErr", new ActionMessage("error.amount.not.greaterthan",
								"Total allocated amount", "global amount"));
					}
				}
				else {
					if (totalAllocatedAmount.compareTo(loanAgencyObj.getGlobalAmount().getAmountAsBigDecimal()) > 0) {
						exceptionMap.put("particpantErr", new ActionMessage("error.amount.not.greaterthan",
								"Total allocated amount", "global amount"));
					}
				}
			}
		}
		return exceptionMap;
	}
}
