/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/tradingbook/deal/gmra/GMRADealValidator.java,v 1 2007/02/08 Jerlin Exp $
 */
package com.integrosys.cms.ui.tradingbook.deal.gmra;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Describe this class. Purpose: Validation for GMRA Deal Description: Validate
 * the value that user key in
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/02/08$ Tag: $Name$
 */

public class GMRADealValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(GMRADealForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String event = aForm.getEvent();
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4).toString();

		try {

			if ("maker_update_dealdetails_confirm".equals(event) || "maker_edit_reject_confirm".equals(event)
					|| "maker_add_deal_confirm".equals(event)) {
				String productType = aForm.getProductType();

				if ((productType == null) || productType.equals("")) {
					errors.add("productType", new ActionMessage("error.string.mandatory"));
				}

				String secDesc = aForm.getSecDesc();

				if ((secDesc == null) || secDesc.equals("")) {
					errors.add("secDesc", new ActionMessage("error.string.mandatory"));
				}

				String IsinCode = aForm.getIsinCode();

				if ((IsinCode == null) || IsinCode.equals("")) {
					errors.add("isinCode", new ActionMessage("error.string.mandatory"));
				}

				String dealCurCode = aForm.getDealCurCode();
				String dealPrice = aForm.getDealPrice();

				if ((dealCurCode == null) || dealCurCode.equals("")) {
					errors.add("dealPrice", new ActionMessage("error.string.mandatory"));
				}
				else {
					if ((dealPrice == null) || dealPrice.equals("")) {
						errors.add("dealPrice", new ActionMessage("error.string.mandatory"));

					}
					else {

						if (!(errorCode = Validator.checkNumber(dealPrice, true, 0,
								IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
							if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
								errors.add("dealPrice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", maximumAmt));

							}
							else if (errorCode.equals("decimalexceeded")) {
								errors.add("dealPrice", new ActionMessage("error.number.moredecimalexceeded", "", "",
										"4"));

							}
							else if (!errorCode.equals("mandatory")) {
								errors.add("dealPrice", new ActionMessage("error.number." + errorCode));
							}
							DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.deal.ISDACSADealValidator",
									"============= dealPrice ==========");
						}
					}

				}

				String notionalCurCode = aForm.getNotionalCurCode();
				String notional = aForm.getNotional();

				if ((notionalCurCode != null) && !notionalCurCode.equals("")) {
					if ((notional == null) || notional.equals("")) {
						errors.add("notional", new ActionMessage("error.tradingbook.notional", "amount"));
					}
				}
				else {
					if ((notional != null) && !notional.equals("")) {
						errors.add("notional", new ActionMessage("error.tradingbook.notional",
								"currency code for notional"));
					}
				}

				if (!(errorCode = Validator.checkNumber(notional, true, 0, IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4,
						5, locale)).equals(Validator.ERROR_NONE)) {
					if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
						errors.add("notional", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
								"heightlessthan"), "0", maximumAmt));

					}
					else if (errorCode.equals("decimalexceeded")) {
						errors.add("notional", new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));

					}
					else if (!errorCode.equals("mandatory")) {
						errors.add("notional", new ActionMessage("error.number." + errorCode));
					}
					DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.deal.ISDACSADealValidator",
							"============= notional ==========");
				}

				String haircut = aForm.getHaircut();

				if ((haircut == null) || haircut.equals("")) {
					errors.add("haircut", new ActionMessage("error.string.mandatory"));

				}
				else {

					if (!(errorCode = Validator.checkNumber(haircut, true, 0, 100, 5, locale))
							.equals(Validator.ERROR_NONE)) {
						if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
							errors.add("haircut", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "0", "100"));

						}
						else if (errorCode.equals("decimalexceeded")) {
							errors.add("haircut", new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));

						}
						else if (!errorCode.equals("mandatory")) {
							errors.add("haircut", new ActionMessage("error.number." + errorCode));
						}
						DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.deal.GMRADealValidator",
								"============= haircut ==========");
					}
				}

				String dealRate = aForm.getDealRate();

				if ((dealRate == null) || dealRate.equals("")) {
					errors.add("dealRate", new ActionMessage("error.string.mandatory"));

				}
				else {

					if (!(errorCode = Validator.checkNumber(dealRate, true, 0, 100, 5, locale))
							.equals(Validator.ERROR_NONE)) {
						if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
							errors.add("dealRate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "0", "100"));

						}
						else if (errorCode.equals("decimalexceeded")) {
							errors.add("dealRate", new ActionMessage("error.number.moredecimalexceeded", "", "", "4"));

						}
						else if (!errorCode.equals("mandatory")) {
							errors.add("dealRate", new ActionMessage("error.number." + errorCode));
						}
						DefaultLogger.debug("com.integrosys.cms.ui.tradingbook.deal.GMRADealValidator",
								"============= dealRate ==========");
					}
				}

				String tradeDate = aForm.getTradeDate();

				if ((tradeDate == null) || tradeDate.equals("")) {
					errors.add("tradeDate", new ActionMessage("error.string.mandatory"));

				}
				else {

					if (!(errorCode = Validator.checkDate(tradeDate, false, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("tradeDate", new ActionMessage("error.date.mandatory", "1", "256"));
					}
				}

				String maturityDate = aForm.getMaturityDate();

				if ((maturityDate == null) || maturityDate.equals("")) {
					errors.add("maturityDate", new ActionMessage("error.string.mandatory"));

				}
				else {

					if (!(errorCode = Validator.checkDate(maturityDate, false, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("maturityDate", new ActionMessage("error.date.mandatory", "1", "256"));
					}
				}

				if ((tradeDate != null) && (maturityDate != null) && !tradeDate.equals("") && !maturityDate.equals("")) {
					Date tradDate = DateUtil.convertDate(locale, aForm.getTradeDate());
					Date matuDate = DateUtil.convertDate(locale, aForm.getMaturityDate());
					int a = matuDate.compareTo(tradDate);

					if (a < 0) {
						errors.add("tradeDate", new ActionMessage("error.date.compareDate.cannotBelater", "Trade Date",
								"Maturity Date"));
					}
				}

				String dealCountry = aForm.getDealCountry();

				if ((dealCountry == null) || dealCountry.equals("")) {
					errors.add("dealCountry", new ActionMessage("error.string.mandatory"));
				}

				String dealBranch = aForm.getDealBranch();

				if ((dealBranch == null) || dealBranch.equals("")) {
					errors.add("dealBranch", new ActionMessage("error.string.mandatory"));
				}

				String repoStartAmt = aForm.getRepoStartAmt();
				if ((repoStartAmt == null) || repoStartAmt.equals("")) {
					errors.add("repoStartAmt", new ActionMessage("error.string.mandatory"));

				}
				else {
					if (!(errorCode = Validator.checkNumber(repoStartAmt, true, 0,
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
						if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
							errors.add("repoStartAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "0", maximumAmt));

						}
						else if (errorCode.equals("decimalexceeded")) {
							errors.add("repoStartAmt", new ActionMessage("error.number.moredecimalexceeded", "", "",
									"4"));

						}
						else if (!errorCode.equals("mandatory")) {
							errors.add("repoStartAmt", new ActionMessage("error.number." + errorCode));
						}
					}
				}

				String repoEndAmt = aForm.getRepoEndAmt();
				if ((repoEndAmt == null) || repoEndAmt.equals("")) {
					errors.add("repoEndAmt", new ActionMessage("error.string.mandatory"));

				}
				else {
					if (!(errorCode = Validator.checkNumber(repoEndAmt, true, 0,
							IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_4, 5, locale)).equals(Validator.ERROR_NONE)) {
						if (errorCode.equals("greaterthan") || errorCode.equals("lessthan")) {
							errors.add("repoEndAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "0", maximumAmt));

						}
						else if (errorCode.equals("decimalexceeded")) {
							errors
									.add("repoEndAmt", new ActionMessage("error.number.moredecimalexceeded", "", "",
											"4"));

						}
						else if (!errorCode.equals("mandatory")) {
							errors.add("repoEndAmt", new ActionMessage("error.number." + errorCode));
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
