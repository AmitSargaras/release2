/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/security/SecurityValidator.java,v 1.14 2006/03/03 07:05:57 pratheepa Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.security;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.collateral.commodity.CommodityMainConstant;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.14 $
 * @since $Date: 2006/03/03 07:05:57 $ Tag: $Name: $
 */

public class SecurityValidator {
	public static ActionErrors validateInput(SecurityForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if ((aForm.getIsCMTmaker() != null) && aForm.getIsCMTmaker().equals(ICMSConstant.FALSE_VALUE)) {
			/*
			 * if (aForm.getLeCharge() != null && aForm.getLeCharge().length() >
			 * 0 && aForm.getLeCharge().equals(ICMSConstant.TRUE_VALUE)) { if
			 * (!(errorCode = Validator.checkDate(aForm.getLeDateCharge(), true,
			 * locale)).equals(Validator.ERROR_NONE)) {
			 * errors.add("leDateCharge", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
			 * "0", 256 + "")); } else { errors = validatePreviousDate(errors,
			 * aForm.getLeDateCharge(), "leDateCharge",
			 * "Legal Enforceability Date - By Charge Ranking", locale); } }
			 * else { if (!(errorCode =
			 * Validator.checkDate(aForm.getLeDateCharge(), false,
			 * locale)).equals(Validator.ERROR_NONE)) {
			 * errors.add("leDateCharge", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
			 * "0", 256 + "")); } else { errors = validatePreviousDate(errors,
			 * aForm.getLeDateCharge(), "leDateCharge",
			 * "Legal Enforceability Date - By Charge Ranking", locale); } }
			 * 
			 * if (aForm.getLeGoverningLaw() != null &&
			 * aForm.getLeGoverningLaw().length() > 0 &&
			 * aForm.getLeGoverningLaw().equals(ICMSConstant.TRUE_VALUE)) { if
			 * (!(errorCode = Validator.checkDate(aForm.getLeDateGovernginLaw(),
			 * true, locale)).equals(Validator.ERROR_NONE)) {
			 * errors.add("leDateGovernginLaw", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
			 * "0", 256 + "")); } else { errors = validatePreviousDate(errors,
			 * aForm.getLeDateGovernginLaw(), "leDateGovernginLaw",
			 * "Legal Enforceability Date - By Governing Laws", locale); } }
			 * else { if (!(errorCode =
			 * Validator.checkDate(aForm.getLeDateGovernginLaw(), false,
			 * locale)).equals(Validator.ERROR_NONE)) {
			 * errors.add("leDateGovernginLaw", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
			 * "0", 256 + "")); } else { errors = validatePreviousDate(errors,
			 * aForm.getLeDateGovernginLaw(), "leDateGovernginLaw",
			 * "Legal Enforceability Date - By Governing Laws", locale); } }
			 * 
			 * if (aForm.getLeJurisdiction() != null &&
			 * aForm.getLeJurisdiction().length() > 0 &&
			 * aForm.getLeJurisdiction().equals(ICMSConstant.TRUE_VALUE)) { if
			 * (!(errorCode = Validator.checkDate(aForm.getLeDateJurisdiction(),
			 * true, locale)).equals(Validator.ERROR_NONE)) {
			 * errors.add("leDateJurisdiction", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
			 * "0", 256 + "")); } else { errors = validatePreviousDate(errors,
			 * aForm.getLeDateJurisdiction(), "leDateJurisdiction",
			 * "Legal Enforceability Date - By Jurisdiction", locale); } } else
			 * { if (!(errorCode =
			 * Validator.checkDate(aForm.getLeDateJurisdiction(), false,
			 * locale)).equals(Validator.ERROR_NONE)) {
			 * errors.add("leDateJurisdiction", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
			 * "0", 256 + "")); } else { errors = validatePreviousDate(errors,
			 * aForm.getLeDateJurisdiction(), "leDateJurisdiction",
			 * "Legal Enforceability Date - By Jurisdiction", locale); } }
			 */

			if ((aForm.getLe() != null) && (aForm.getLe().length() > 0)
					&& aForm.getLe().equals(ICMSConstant.TRUE_VALUE)) {
				if (!(errorCode = Validator.checkDate(aForm.getLeDate(), true, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("leDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
							256 + ""));
				}
				else {
					errors = validatePreviousDate(errors, aForm.getLeDate(), "leDate", "Legal Enforceability Date",
							locale);
				}
			}
			else {
				if (!(errorCode = Validator.checkDate(aForm.getLeDate(), false, locale)).equals(Validator.ERROR_NONE)) {
					errors.add("leDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
							256 + ""));
				}
				else {
					errors = validatePreviousDate(errors, aForm.getLeDate(), "leDate", "Legal Enforceability Date ",
							locale);
				}
			}

			if (!(errorCode = Validator.checkDate(aForm.getLegalChargeDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("legalChargeDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
			}
			else {
				errors = validatePreviousDate(errors, aForm.getLegalChargeDate(), "legalChargeDate",
						"Date legally charged", locale);
			}

			if ((aForm.getSecurityEnvRisk() != null) && (aForm.getSecurityEnvRisk().length() > 0)
					&& (!aForm.getSecurityEnvRisk().equals(CommodityMainConstant.ENV_RISK_NO_APPLICALBE))
					&& (!aForm.getSecurityEnvRisk().equals(CommodityMainConstant.ENV_RISK_NO_RISK))) {
				if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnvRisk(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dateSecurityEnvRisk", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
							errorCode), "0", 256 + ""));
				}
				else if ((aForm.getDateSecurityEnvRisk() != null) && (aForm.getDateSecurityEnvRisk().length() > 0)) {
					Date secEnvDate = DateUtil.convertDate(locale, aForm.getDateSecurityEnvRisk());
					if (secEnvDate.after(DateUtil.getDate())) {
						errors.add("dateSecurityEnvRisk", new ActionMessage("error.collateral.commodity.envrisk.date"));
					}
				}
			}
			else {
				if (!(errorCode = Validator.checkDate(aForm.getDateSecurityEnvRisk(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dateSecurityEnvRisk", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
							errorCode), "0", 256 + ""));
				}
				else if ((aForm.getDateSecurityEnvRisk() != null) && (aForm.getDateSecurityEnvRisk().length() > 0)) {
					Date secEnvDate = DateUtil.convertDate(locale, aForm.getDateSecurityEnvRisk());
					if (secEnvDate.after(DateUtil.getDate())) {
						errors.add("dateSecurityEnvRisk", new ActionMessage("error.collateral.commodity.envrisk.date"));
					}
				}
			}

			if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarkSecurityEnvRisk(), false))
					.equals(Validator.ERROR_NONE)) {
				errors.add("remarkSecurityEnvRisk", RemarksValidatorUtil.getErrorMessage(errorCode));
			}

			if (!(errorCode = Validator.checkString(aForm.getChargeType(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
						10 + ""));
			}

			if (!(errorCode = Validator.checkAmount(aForm.getChargeAmount(), false, 0,
					CommodityMainConstant.MAX_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("chargeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "1",
						CommodityMainConstant.MAX_AMOUNT_STR));
			}

			if (!(errorCode = Validator.checkString(aForm.getCmsSecurityCurrency(), true, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("cmsSecurityCurrency", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.STRING, errorCode), "1", 3 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getValuationCurrency(), false, 1, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("valuationCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"1", 3 + ""));
			}
		}
		else {
			if (!(errorCode = Validator.checkNumber(aForm.getCustomerDiff(), false, 0,
					CommodityMainConstant.MAX_PRICE_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("customerDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						CommodityMainConstant.MAX_PRICE_DIFF_STR));
			}
			else {
				if ((aForm.getCustomerDiff() != null) && (aForm.getCustomerDiff().length() > 0)) {
					if (!(errorCode = Validator.checkString(aForm.getPlusmnSign(), true, 1, 3))
							.equals(Validator.ERROR_NONE)) {
						errors.add("plusmnSign", new ActionMessage(
								ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", 3 + ""));
					}
				}
			}
		}
		return errors;
	}

	private static ActionErrors validatePreviousDate(ActionErrors errors, String dateStr, String fieldName,
			String desc, Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.after(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.not.futuredate", desc));
			}
		}
		return errors;
	}
}
