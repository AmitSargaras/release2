/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Bridging Loan Validator
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class BridgingLoanValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.BridgingLoanForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in BridgingLoanValidator", "form.getEvent()=" + form.getEvent());

			boolean isSubmit = false;
			if (BridgingLoanAction.EVENT_SUBMIT.equals(form.getEvent())) {
				isSubmit = true;
			}
			DefaultLogger.debug("in BridgingLoanValidator", "isSubmit: " + isSubmit);

			String projectNo = form.getProjectNumber();
			String contractDate = form.getContractDate();
			String contractCurrency = form.getContractCurrency();
			String contractAmount = form.getContractAmount();
			String financePercent = form.getFinancePercent(); // Percentage of
																// finance
			String fullSettlementContractDate = form.getFullSettlementContractDate(); // Full
																						// settlement
																						// contract
																						// date
			String collectionAccount = form.getCollectionAccount();
			String hdaAccountNo = form.getHdaAccount();
			String projectAccount = form.getProjectAccount();
			String currentAccount = form.getCurrentAccount();
			String noOfTypes = form.getNoOfTypes(); // Proposed No. of Types
			String expectedStartDate = form.getExpectedStartDate();
			String expectedCompletionDate = form.getExpectedCompletionDate();
			String actualStartDate = form.getActualStartDate();
			String actualCompletionDate = form.getActualCompletionDate();
			String availabilityExpiryDate = form.getAvailabilityExpiryDate(); // Expiry
																				// of
																				// Availability
																				// Date
			String fullSettlementDate = form.getFullSettlementDate(); // Date of
																		// Full
																		// Settlement
			String remarks = form.getRemarks();

			if (!(errMsg = Validator.checkString(projectNo, false, 0, 50)).equals(Validator.ERROR_NONE)) {
				errors
						.add("projectNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
								"50"));
			}
			if (!(errMsg = Validator.checkDate(contractDate, isSubmit, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("contractDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(contractCurrency, isSubmit, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("contractCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(contractAmount, isSubmit, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("contractAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkNumber(financePercent, false, 0, 100.00)).equals(Validator.ERROR_NONE)) {
				errors.add("financePercent", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errMsg), "0",
						"100.00"));
			}
			if (!(errMsg = Validator.checkDate(fullSettlementContractDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("fullSettlementContractDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
						errMsg)));
			}

			if (!(errMsg = Validator.checkString(collectionAccount, false, 0, 25)).equals(Validator.ERROR_NONE)) {
				errors.add("collectionAccount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"0", "25"));
			}
			if (!(errMsg = Validator.checkString(hdaAccountNo, false, 0, 25)).equals(Validator.ERROR_NONE)) {
				errors.add("hdaAccountNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"25"));
			}
			if (!(errMsg = Validator.checkString(projectAccount, false, 0, 25)).equals(Validator.ERROR_NONE)) {
				errors.add("projectAccount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"25"));
			}
			if (!(errMsg = Validator.checkString(currentAccount, false, 0, 25)).equals(Validator.ERROR_NONE)) {
				errors.add("currentAccount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"25"));
			}
			if (!(errMsg = Validator.checkInteger(noOfTypes, false, 0, 99)).equals(Validator.ERROR_NONE)) {
				errors.add("noOfTypes",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errMsg), "0", "99"));
			}
			if (!(errMsg = Validator.checkDate(expectedStartDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("expectedStartDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkDate(expectedCompletionDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors
						.add("expectedCompletionDate", new ActionMessage(ErrorKeyMapper
								.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkDate(actualStartDate, isSubmit, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("actualStartDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkDate(actualCompletionDate, isSubmit, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("actualCompletionDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkDate(availabilityExpiryDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors
						.add("availabilityExpiryDate", new ActionMessage(ErrorKeyMapper
								.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkDate(fullSettlementDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("fullSettlementDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(remarks, false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "500"));
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}