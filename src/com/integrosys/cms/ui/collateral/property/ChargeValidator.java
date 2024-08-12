package com.integrosys.cms.ui.collateral.property;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class ChargeValidator {
	public static ActionErrors validateInput(ChargeForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		boolean checkPriorType = true;
		boolean caveatWaivedInd = false;

		if ((aForm.getLimitID() == null) || (aForm.getLimitID().length <= 0)) {
			errors.add("limitID", new ActionMessage(ErrorKeyMapper
					.map(ErrorKeyMapper.STRING, Validator.ERROR_MANDATORY), "0", 256 + ""));
			DefaultLogger.debug("ChargeValidator", "........... error is limitID");
		}

		if (!(errorCode = Validator.checkInteger(aForm.getRank(), true, 0, 99)).equals(Validator.ERROR_NONE)) {
			errors.add("rank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errorCode), "0", 99 + ""));
			checkPriorType = false;
		}

		if (!(errorCode = Validator.checkAmount(aForm.getChargeAmount(), true, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("chargeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
		}

		if (!(errorCode = Validator.checkAmount(aForm.getPriorChargeAmount(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("priorChargeAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"0", maximumAmt));
		}

		if (!(errorCode = Validator.checkString(aForm.getChargeePriorCharge(), false, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("chargeePriorCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 50 + ""));
		}
		try {
			if (!(errorCode = Validator.checkDate(aForm.getDateLegalCharge(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("dateLegalCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
			}
			else if ((aForm.getDateLegalCharge() != null) && (aForm.getDateLegalCharge().length() > 0)) {
				Date currDate = DateUtil.getDate();
				Date date1 = DateUtil.convertDate(locale, aForm.getDateLegalCharge());
				if (date1.after(currDate)) {
					errors.add("dateLegalCharge", new ActionMessage("error.date.compareDate.more",
							"Date Legally Charge", "Current Date"));
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (!(errorCode = Validator.checkString(aForm.getNatureOfCharge(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("natureOfCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					20 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getChargeType(), true, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("chargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					10 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getPriorChargeType(), false, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("priorChargeType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					10 + ""));
			checkPriorType = false;
		}

		if (checkPriorType) {
			if (aForm.getRank().equals("1") && !aForm.getPriorChargeType().equals(ICMSConstant.NOT_AVAILABLE_VALUE)) {
				errors.add("priorChargeType", new ActionMessage("error.collateral.na.allowed", "1", "20"));
			}
			else if (!aForm.getRank().equals("1")
					&& aForm.getPriorChargeType().equals(ICMSConstant.NOT_AVAILABLE_VALUE)) {
				errors.add("priorChargeType", new ActionMessage("error.collateral.na.disallowed", "1", "20"));
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getCaveatWaivedInd(), true, 1, 3)).equals(Validator.ERROR_NONE)) {
			errors.add("caveatWaivedInd", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					3 + ""));
			caveatWaivedInd = true;
		}

		if (aForm.getCaveatWaivedInd() != null) {
			if (aForm.getCaveatWaivedInd().equals(ICMSConstant.FALSE_VALUE)) {
				if (!(errorCode = Validator.checkDate(aForm.getExpiryDate(), true, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("expiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
							256 + ""));
				}
				
				if (!(errorCode = Validator.checkString(aForm.getCaveatReferenceNo(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("caveatReferenceNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"1", 40 + ""));
				}
			}
			else {
				if (!(errorCode = Validator.checkDate(aForm.getExpiryDate(), false, locale))
						.equals(Validator.ERROR_NONE)) {
					errors.add("expiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
							256 + ""));
				}
				
				if (!(errorCode = Validator.checkString(aForm.getCaveatReferenceNo(), false, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("caveatReferenceNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"1", 40 + ""));
				}
			}
		}/*
		 * else{ errors.add("caveatWaivedInd", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "1", 10 + "")); }
		 */

		if (!(errorCode = Validator.checkDate(aForm.getPresentationDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("presentationDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}

		if (!(errorCode = Validator.checkDate(aForm.getLodgedDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("lodgedDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getPartyCharge(), true, 1, 1)).equals(Validator.ERROR_NONE)) {
			errors.add("partyCharge", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
					1 + ""));
		}
		return errors;
	}

}
