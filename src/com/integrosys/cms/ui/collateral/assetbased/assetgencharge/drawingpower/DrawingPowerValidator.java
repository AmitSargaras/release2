/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/drawingpower/DrawingPowerValidator.java,v 1.9 2005/11/12 02:53:03 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.drawingpower;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.mapper.MapperUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.AssetGenChargeUtil;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.UIValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/11/12 02:53:03 $ Tag: $Name: $
 */

public class DrawingPowerValidator {
	static String errorAmtWBracketFormat = "formatwithbracket";

	public static ActionErrors validateInput(DrawingPowerForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		boolean isMandatory = aForm.getEvent().equals(DrawingPowerAction.EVENT_EDIT);

		if (!(errorCode = UIValidator.checkNumber(aForm.getBankParticipatingShare(), isMandatory, 0, 100, 3, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("bankParticipatingShare", new ActionMessage(
					ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100", "2"));
		}

		if (!(errorCode = Validator.checkInteger(aForm.getCrpMargin(), (isMandatory && true), 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors
					.add("crpMargin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
							"100"));
		}

		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getOtherRemarks(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("otherRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
		}

		if (!(errorCode = Validator.checkString(aForm.getValuationCurrency(), false, 1, 3))
				.equals(Validator.ERROR_NONE)) {
			errors.add("valuationCurrency", new ActionMessage("error.string.mandatory", "1", "3"));
		}

		String[] approvedLimitList = aForm.getApprovedLimit();
		if (approvedLimitList != null) {
			for (int i = 0; i < approvedLimitList.length; i++) {
				try {
					double approvedLimit = MapperUtil.mapStringToDouble(approvedLimitList[i], locale);
					double appliedLimit = 0;
					double releasedLimit = 0;
					boolean isAppliedInput = false;

					if (!(errorCode = checkAmountWithBracket(aForm.getAppliedActivatedLimitBase()[i], true, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("appliedActivatedLimitBase" + String.valueOf(i), new ActionMessage(ErrorKeyMapper
								.map(ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
					}
					else {
						if ((aForm.getAppliedActivatedLimitBase()[i] != null)
								&& !aForm.getAppliedActivatedLimitBase()[i].equals("")) {
							isAppliedInput = true;
							appliedLimit = MapperUtil.mapStringToDouble(AssetGenChargeUtil.removeBracket(aForm
									.getAppliedActivatedLimitBase()[i]), locale);
						}
					}
					if (!(errorCode = checkAmountWithBracket(aForm.getReleasedLimitBase()[i], true, locale))
							.equals(Validator.ERROR_NONE)) {
						errors.add("releasedLimitBase" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.AMOUNT, errorCode), "0", maximumAmt));
					}
					else {
						if ((aForm.getReleasedLimitBase()[i] != null) && !aForm.getReleasedLimitBase()[i].equals("")) {
							releasedLimit = MapperUtil.mapStringToDouble(AssetGenChargeUtil.removeBracket(aForm
									.getReleasedLimitBase()[i]), locale);
						}
					}
					if (appliedLimit > approvedLimit) {
						errors.add("appliedActivatedLimitBase" + String.valueOf(i), new ActionMessage(
								"error.collateral.appliedlimit.more"));
					}
					if (isAppliedInput && (releasedLimit > appliedLimit)) {
						errors.add("releasedLimitBase" + String.valueOf(i), new ActionMessage(
								"error.collateral.releasedlimit.more"));
					}
				}
				catch (Exception e) {
					DefaultLogger.error("drawing power validator", "error at convert to double");
				}
			}
		}
		return errors;
	}

	private static String checkAmountWithBracket(String strAmt, boolean isMandatory, Locale locale) {
		if (strAmt != null) {
			strAmt = strAmt.trim();
			int openBracket = strAmt.indexOf("(");
			int closeBracket = strAmt.indexOf(")");
			if ((openBracket < 0) && (closeBracket >= 0)) {
				return errorAmtWBracketFormat;
			}
			if ((closeBracket < 0) && (openBracket >= 0)) {
				return errorAmtWBracketFormat;
			}
			if (openBracket > 0) {
				return errorAmtWBracketFormat;
			}
			if ((closeBracket >= 0) && (strAmt.length() - 1 > closeBracket)) {
				return errorAmtWBracketFormat;
			}
		}
		strAmt = AssetGenChargeUtil.removeBracket(strAmt);
		return Validator.checkAmount(strAmt, isMandatory, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT,
				IGlobalConstant.DEFAULT_CURRENCY, locale);
	}
}
