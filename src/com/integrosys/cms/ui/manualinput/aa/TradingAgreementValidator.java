/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Validation for Trading Agreement Description:
 * Validate the value that user key in
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class TradingAgreementValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(TradingAgreementForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String event = aForm.getEvent();

		try {

			if ("maker_add_agreement_confirm".equals(event) || "maker_add_editreject_confirm".equals(event)
					|| "maker_update_agreement_confirm".equals(event)
					|| "maker_update_editreject_confirm".equals(event)) {

				String agreementType = aForm.getAgreementType();
				String minTransCurCode = aForm.getMinTransCurCode();
				String minTransfer = aForm.getMinTransfer();
				String countRatingType = aForm.getCountRatingType();
				String counterpartyRating = aForm.getCounterpartyRating();
				String mbbRatingType = aForm.getMaybankRatingType();
				String mbbRating = aForm.getMaybankRating();
				String agreeIntRateType = aForm.getAgreeIntRateType();
				String baseCurrency = aForm.getBaseCurrency();
				String counterPartyThresholdAmt = aForm.getCounterPartyThresholdAmt();
				String mbbThresholdAmt = aForm.getMbbThresholdAmt();
				String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4).toString();
				String agentBankName = aForm.getAgentBankName();
				String agentBankAddress = aForm.getAgentBankAddress();
				String bankClearanceId = aForm.getBankClearanceId();
				String bankAccountId = aForm.getBankAccountId();
				String clearingDesc = aForm.getClearingDesc();
				String notificationTime = aForm.getBaseCurrency();
				String valuationTime = aForm.getBaseCurrency();

				if ((agreementType == null) || agreementType.equals("")) {
					errors.add("agreementType", new ActionMessage("error.string.mandatory"));
				}

				if ((minTransCurCode == null) || minTransCurCode.equals("")) {
					errors.add("minTransfer", new ActionMessage("error.string.mandatory"));
				}
				else {
					if ((minTransfer == null) || minTransfer.equals("")) {
						errors.add("minTransfer", new ActionMessage("error.string.mandatory"));
					}
					else {
						if (!(errorCode = Validator.checkNumber(minTransfer, true, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
							if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
								errors.add("minTransfer", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", maximumAmt));

							}
							else if (errorCode.equals("decimalexceeded")) {
								errors.add("minTransfer", new ActionMessage("error.number.moredecimalexceeded", "", "",
										"4"));

							}
							else {
								errors.add("minTransfer", new ActionMessage("error.number." + errorCode));
							}
							DefaultLogger.debug(
									"com.integrosys.cms.ui.manualinput.agreement.TradingAgreementValidator",
									"============= minTransfer ==========");
						}
					}

				}

				if ((countRatingType == null) || countRatingType.equals("")) {
					errors.add("countRatingType", new ActionMessage("error.string.mandatory"));
				}

				if ((counterpartyRating == null) || counterpartyRating.equals("")) {
					errors.add("counterpartyRating", new ActionMessage("error.string.mandatory"));
				}

				if ((mbbRatingType == null) || mbbRatingType.equals("")) {
					errors.add("maybankRatingType", new ActionMessage("error.string.mandatory"));
				}

				if ((mbbRating == null) || mbbRating.equals("")) {
					errors.add("maybankRating", new ActionMessage("error.string.mandatory"));
				}

				if ((agreeIntRateType == null) || agreeIntRateType.equals("")) {
					errors.add("agreeIntRateType", new ActionMessage("error.string.mandatory"));
				}

				if ((baseCurrency == null) || baseCurrency.equals("")) {
					errors.add("baseCurrency", new ActionMessage("error.string.mandatory"));
				}

				if ((counterPartyThresholdAmt == null) || counterPartyThresholdAmt.equals("")) {
					errors.add("counterPartyThresholdAmt", new ActionMessage("error.counterparty.threshold.rating"));
				}

				if ((mbbThresholdAmt == null) || mbbThresholdAmt.equals("")) {
					errors.add("mbbThresholdAmt", new ActionMessage("error.bank.threshold.rating"));
				}

				if ((agentBankName != null) && !agentBankName.equals("")) {
					if (!(errorCode = Validator.checkString(agentBankName, true, 0, 100)).equals(Validator.ERROR_NONE)) {
						if (!errorCode.equals("mandatory")) {
							errors.add("agentBankName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "1", "100"));
						}
					}
				}

				if ((agentBankAddress != null) || agentBankAddress.equals("")) {
					if (!(errorCode = Validator.checkString(agentBankAddress, true, 0, 150))
							.equals(Validator.ERROR_NONE)) {
						if (!errorCode.equals("mandatory")) {
							errors.add("agentBankAddress", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "1", "150"));
						}
					}
				}

				if ((bankClearanceId != null) || bankClearanceId.equals("")) {
					if (!(errorCode = Validator.checkString(bankClearanceId, true, 0, 10)).equals(Validator.ERROR_NONE)) {
						if (!errorCode.equals("mandatory")) {
							errors.add("bankClearanceId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "1", "10"));
						}
					}
				}

				if ((bankAccountId != null) || bankAccountId.equals("")) {
					if (!(errorCode = Validator.checkString(bankAccountId, true, 0, 10)).equals(Validator.ERROR_NONE)) {
						if (!errorCode.equals("mandatory")) {
							errors.add("bankAccountId", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "1", "10"));
						}
					}
				}

				if ((clearingDesc != null) || clearingDesc.equals("")) {
					if (!(errorCode = Validator.checkString(clearingDesc, true, 0, 10)).equals(Validator.ERROR_NONE)) {
						if (!errorCode.equals("mandatory")) {
							errors.add("clearingDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "1", "10"));
						}
					}
				}

				if ((notificationTime != null) || notificationTime.equals("")) {
					if (!(errorCode = Validator.checkString(notificationTime, true, 0, 12))
							.equals(Validator.ERROR_NONE)) {
						if (!errorCode.equals("mandatory")) {
							errors.add("notificationTime", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "1", "12"));
						}
					}
				}

				if ((valuationTime != null) || valuationTime.equals("")) {
					if (!(errorCode = Validator.checkString(valuationTime, true, 0, 12)).equals(Validator.ERROR_NONE)) {
						if (!errorCode.equals("mandatory")) {
							errors.add("valuationTime", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "1", "12"));
						}
					}
				}

			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return errors;

	}

}
