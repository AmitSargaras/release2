/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/generalinfo/GeneralInfoValidator.java,v 1.20 2006/01/13 03:41:56 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal.generalinfo;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2006/01/13 03:41:56 $ Tag: $Name: $
 */

public class GeneralInfoValidator {
	public static ActionErrors validateInput(GeneralInfoForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(aForm.getEvent().equals(GeneralInfoAction.EVENT_APPROVE) || aForm.getEvent().equals(
				GeneralInfoAction.EVENT_REJECT))) {
			if (aForm.getEvent().equals(GeneralInfoAction.EVENT_SUBMIT)
					|| aForm.getEvent().equals(GeneralInfoAction.EVENT_UPDATE)
					|| aForm.getEvent().equals(GeneralInfoAction.EVENT_EDIT)) {
				if (!(errorCode = Validator.checkString(aForm.getSecurityID(), true, 0, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("securityID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 10 + ""));
				}
				if (!(errorCode = Validator.checkString(aForm.getLimitID(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
					errors.add("limitID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							10 + ""));
				}
				if (!(errorCode = Validator.checkString(aForm.getTpDealRef(), true, 0, 20))
						.equals(Validator.ERROR_NONE)) {
					errors.add("tpDealRef", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 20 + ""));
				}
				if (!(errorCode = Validator.checkDate(aForm.getDealMaturityDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dealMaturityDate", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
				} /*
				 * else { if (aForm.getIsNewDeal() != null &&
				 * aForm.getIsNewDeal().equals(ICMSConstant.TRUE_VALUE)) {
				 * errors = validateFutureDate(errors,
				 * aForm.getDealMaturityDate(), "dealMaturityDate",
				 * "Deal Maturity Date", locale); } }
				 */
				if ((aForm.getDealMaturityDate() != null) && errorCode.equals(Validator.ERROR_NONE)) { // errorCode
																										// refers
																										// to
																										// the
																										// check
																										// on
																										// Deal
																										// Maturity
																										// Date
					if (!(errorCode = Validator.checkDate(aForm.getExtendedDealMaturityDate(), false, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("extendedDealMaturityDate", new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.DATE, errorCode)));
					}
					else {
						errors = UIValidator.compareIsLaterDate(errors, aForm.getDealMaturityDate(), aForm
								.getExtendedDealMaturityDate(), "extendedDealMaturityDate",
								"error.date.compareDate.cannotBeEarlier", "Extended Deal Maturity Date",
								"Deal Maturity Date", locale, locale);
					}
				}
				errors = UIValidator.checkAmount(errors, "originalFaceCcy", "originalFaceAmt", aForm
						.getOriginalFaceCcy(), aForm.getOriginalFaceAmt(), true, 1,
						CommodityDealConstant.MAX_AMOUNT_15_2, 3, locale, CommodityDealConstant.MAX_AMOUNT_STR_15_2);
				if (!(errorCode = Validator.checkInteger(aForm.getPercentageFinancing(), true, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("percentageFinancing", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
							errorCode), "0", 100 + ""));
				}
				if (!(errorCode = Validator.checkInteger(aForm.getCashRequirement(), false, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("cashRequirement", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.NUMBER, errorCode), "0", 100 + ""));
				}
				if (!(errorCode = Validator.checkInteger(aForm.getMarginCash(), false, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("marginCash", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", 100 + ""));
				}
				if (!(errorCode = Validator.checkString(aForm.getPreSold(), true, 0, 10)).equals(Validator.ERROR_NONE)) {
					errors.add("preSold", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							10 + ""));
				}
				else {
					if ((aForm.getPreSold() != null) && aForm.getPreSold().equals("true")) {
						if (!(errorCode = Validator.checkString(aForm.getBuyerName(), true, 0, 20))
								.equals(Validator.ERROR_NONE)) {
							errors.add("buyerName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "0", 20 + ""));
						}
					}
					else {
						if (!(errorCode = Validator.checkString(aForm.getBuyerName(), false, 0, 20))
								.equals(Validator.ERROR_NONE)) {
							errors.add("buyerName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
									errorCode), "0", 20 + ""));
						}
					}
				}
				if (!(errorCode = Validator.checkString(aForm.getEnforcibilityAtt(), true, 0, 10))
						.equals(Validator.ERROR_NONE)) {
					errors.add("enforcibilityAtt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
							errorCode), "0", 10 + ""));
				}
				else if (aForm.getEnforcibilityAtt().equals(ICMSConstant.TRUE_VALUE)) {
					if (!(errorCode = Validator.checkDate(aForm.getEnforcibilityAttDate(), true, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("enforcibilityAttDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
								errorCode), "0", 256 + ""));
					}
					else {
						errors = validatePreviousDate(errors, aForm.getEnforcibilityAttDate(), "enforcibilityAttDate",
								"Date of Enforceability Attained", locale);
					}
				}
				else {
					if (!(errorCode = Validator.checkDate(aForm.getEnforcibilityAttDate(), false, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("enforcibilityAttDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
								errorCode), "0", 256 + ""));
					}
					else {
						errors = validatePreviousDate(errors, aForm.getEnforcibilityAttDate(), "enforcibilityAttDate",
								"Date of Enforceability Attained", locale);
					}
				}

			}
			else {
				if (!(errorCode = Validator.checkDate(aForm.getDealMaturityDate(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("dealMaturityDate", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
				} /*
				 * else if
				 * (!GeneralInfoAction.EVENT_REFRESH.equals(aForm.getEvent())) {
				 * if (aForm.getIsNewDeal() != null &&
				 * aForm.getIsNewDeal().equals(ICMSConstant.TRUE_VALUE)) {
				 * errors = validateFutureDate(errors,
				 * aForm.getDealMaturityDate(), "dealMaturityDate",
				 * "Deal Maturity Date", locale); } }
				 */
				if ((aForm.getDealMaturityDate() != null) && errorCode.equals(Validator.ERROR_NONE)) { // errorCode
																										// refers
																										// to
																										// the
																										// check
																										// on
																										// Deal
																										// Maturity
																										// Date
					if (!(errorCode = Validator.checkDate(aForm.getExtendedDealMaturityDate(), false, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("extendedDealMaturityDate", new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.DATE, errorCode)));
					}
					else if (!GeneralInfoAction.EVENT_REFRESH.equals(aForm.getEvent())) {
						errors = UIValidator.compareIsLaterDate(errors, aForm.getDealMaturityDate(), aForm
								.getExtendedDealMaturityDate(), "extendedDealMaturityDate",
								"error.date.compareDate.cannotBeEarlier", "Extended Deal Maturity Date",
								"Deal Maturity Date", locale, locale);
					}
				}
				/*
				 * if condition added to prevent not to check if mandatory value
				 * are entered while page refreshing done when server side
				 * validation is done for some drop downs
				 */
				if (!(aForm.getEvent().equals(GeneralInfoAction.EVENT_REFRESH))) {
					errors = UIValidator
							.checkAmount(errors, "originalFaceCcy", "originalFaceAmt", aForm.getOriginalFaceCcy(),
									aForm.getOriginalFaceAmt(), false, 1, CommodityDealConstant.MAX_AMOUNT_15_2, 3,
									locale, CommodityDealConstant.MAX_AMOUNT_STR_15_2);
				}
				if (!(errorCode = Validator.checkInteger(aForm.getPercentageFinancing(), false, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("percentageFinancing", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
							errorCode), "0", 100 + ""));
				}
				if (!(errorCode = Validator.checkInteger(aForm.getCashRequirement(), false, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("cashRequirement", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.NUMBER, errorCode), "0", 100 + ""));
				}
				if (!(errorCode = Validator.checkInteger(aForm.getMarginCash(), false, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("marginCash", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", 100 + ""));
				}
				if (!(errorCode = Validator.checkDate(aForm.getEnforcibilityAttDate(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("enforcibilityAttDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
							errorCode), "0", 256 + ""));
				}
			}
			if (!(errorCode = Validator.checkString(aForm.getShippingMarks(), false, 0, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add("shippingMarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 20 + ""));
			}
			if (!(errorCode = Validator.checkDate(aForm.getLatestShipmentDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("latestShipmentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
			}
			if (!(errorCode = Validator.checkDate(aForm.getDealDate(), false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("dealDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
						256 + ""));
			}

			if (!(errorCode = Validator.checkString(aForm.getContainerNo(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("containerNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						20 + ""));
			}
			if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getCommDealRemarks(), false))
					.equals(Validator.ERROR_NONE)) {
				errors.add("commDealRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
			}
		}
		if ((aForm.getEvent().equals(GeneralInfoAction.EVENT_APPROVE)
				|| aForm.getEvent().equals(GeneralInfoAction.EVENT_REJECT) || aForm.getEvent().equals(
				GeneralInfoAction.EVENT_FORWARD_DEAL))
				&& (aForm.getIsNewDeal() != null) && aForm.getIsNewDeal().equals(ICMSConstant.TRUE_VALUE)) {
			if (!(errorCode = Validator.checkString(aForm.getRemarks(), false, 0, 1000)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"1000"));
			}
		}
		else {
			if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getRemarks(), false))
					.equals(Validator.ERROR_NONE)) {
				errors.add("remarks", RemarksValidatorUtil.getErrorMessage(errorCode));
			}
		}

		return errors;
	}

	private static ActionErrors validateFutureDate(ActionErrors errors, String dateStr, String fieldName, String desc,
			Locale locale) {
		if ((dateStr != null) && (dateStr.length() > 0)) {
			Date dateObj = DateUtil.convertDate(locale, dateStr);
			if (dateObj.before(DateUtil.getDate())) {
				errors.add(fieldName, new ActionMessage("error.collateral.commodity.futuredate", desc));
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

	/**
	 * Compare if the 2nd date is later than the 1st date.
	 * 
	 * @param errors ActionErrors
	 * @param dateStr1 1st Date String
	 * @param dateStr2 2nd Date String (to be validated to be later than 1st
	 *        date)
	 * @param fieldName Name of the field that is requesting for this validation
	 * @param desc Error message
	 * @param locale1 Locale of 1st Date
	 * @param locale2 Locale of 2nd Date
	 * @return ActionErrors, with error added to it if 2nd date is not later
	 *         than the 1st date
	 */
	/*
	 * private static ActionErrors compareIsLaterDate(ActionErrors errors,
	 * String dateStr1, String dateStr2, String fieldName, String desc, Locale
	 * locale1, Locale locale2) { if(dateStr1 != null && dateStr1.length() > 0
	 * && dateStr2 != null && dateStr2.length() > 0) { Date dateObj1 =
	 * DateUtil.convertDate(locale1, dateStr1); Date dateObj2 =
	 * DateUtil.convertDate(locale2, dateStr2);
	 * 
	 * if(!(dateObj2.after(dateObj1))) { //do not use
	 * "if(dateObj2.before(dateObj1))" 'cos if its the same date, it will return
	 * false. It checks for strictly before. errors.add(fieldName, new
	 * ActionMessage
	 * ("error.commodity.deal.genearlinfo.extendedDealMaturityDate", desc)); } }
	 * return errors; }
	 */

}
