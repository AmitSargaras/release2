/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/titledoc/TitleDocValidator.java,v 1.9 2006/07/24 09:40:26 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.titledoc;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/07/24 09:40:26 $ Tag: $Name: $
 */

public class TitleDocValidator {
	public static ActionErrors validateInput(TitleDocForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (aForm.getEvent().equals(TitleDocAction.EVENT_CREATE)
				|| aForm.getEvent().equals(TitleDocAction.EVENT_UPDATE)) {
			if (!(errorCode = Validator.checkString(aForm.getProcessStage(), true, 0, 5)).equals(Validator.ERROR_NONE)) {
				errors.add("processStage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						5 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getDocDesc(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("docDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						20 + ""));
			}
			if (!(errorCode = Validator.checkInteger(aForm.getEligibilityAdv(), true, 0, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("eligibilityAdv", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", 100 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getSecured(), true, 0, 5)).equals(Validator.ERROR_NONE)) {
				errors.add("secured", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						5 + ""));
			}
			if (!(errorCode = Validator.checkDate(aForm.getTransactionDate(), true, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("transactionDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
			}
			else {
				errors = validatePreviousDate(errors, aForm.getTransactionDate(), "transactionDate",
						"Date of Transaction", locale);
			}
			if (!(errorCode = Validator.checkString(aForm.getDocType(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("docType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						100 + ""));
			}

		}
		else {
			if (!(errorCode = Validator.checkString(aForm.getProcessStage(), false, 0, 5)).equals(Validator.ERROR_NONE)) {
				errors.add("processStage", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						5 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getDocDesc(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("docDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						20 + ""));
			}
			if (!(errorCode = Validator.checkInteger(aForm.getEligibilityAdv(), false, 0, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("eligibilityAdv", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", 100 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getSecured(), false, 0, 5)).equals(Validator.ERROR_NONE)) {
				errors.add("secured", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						5 + ""));
			}
			if (!(errorCode = Validator.checkDate(aForm.getTransactionDate(), false, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("transactionDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
						"0", 256 + ""));
			}
			else {
				errors = validatePreviousDate(errors, aForm.getTransactionDate(), "transactionDate",
						"Date of Transaction", locale);
			}
			if (!(errorCode = Validator.checkString(aForm.getDocType(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("docType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						100 + ""));
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getShippingCompany(), false, 0, 150))
				.equals(Validator.ERROR_NONE)) {
			errors.add("shippingCompany", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getBillLadingNo(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("billLadingNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getBillLadingDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("billLadingDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validatePreviousDate(errors, aForm.getBillLadingDate(), "billLadingDate", "Bill of Lading Date",
					locale);
		}
		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getBillLadingRemarks(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("billLadingRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
		}
		if (!(errorCode = Validator.checkString(aForm.getTrNo(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("trNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getBillLadingRefNo(), false, 0, 150))
				.equals(Validator.ERROR_NONE)) {
			errors.add("billLadingRefNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		errors = UIValidator.checkAmount(errors, "trAmtCcy", "trAmtVal", aForm.getTrAmtCcy(), aForm.getTrAmtVal(),
				false, 0, CommodityDealConstant.MAX_AMOUNT, 0, locale, CommodityDealConstant.MAX_AMOUNT_STR);
		/*
		 * if (!(errorCode = Validator.checkNumber(aForm.getTrAmtVal(), false,
		 * 0, CommodityDealConstant.MAX_AMOUNT, 0,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("trAmtVal", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
		 * "0", CommodityDealConstant.MAX_AMOUNT_STR)); }
		 */
		if (!(errorCode = Validator.checkDate(aForm.getTrDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("trDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
		}
		else {
			errors = validatePreviousDate(errors, aForm.getTrDate(), "trDate", "TR Date", locale);
		}
		if (!(errorCode = Validator.checkDate(aForm.getTrMaturityDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("trMaturityDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validateFutureDate(errors, aForm.getTrMaturityDate(), "trMaturityDate", "TR Maturity Date", locale);
		}
		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getTrRemarks(), false)).equals(Validator.ERROR_NONE)) {
			errors.add("trRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
		}
		if (!(errorCode = Validator.checkString(aForm.getDocumentNo(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("documentNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getDocumentDesc(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("documentDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getDocumentDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("documentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validatePreviousDate(errors, aForm.getDocumentDate(), "documentDate", "Document Date", locale);
		}
		if (!(errorCode = Validator.checkDate(aForm.getDocumentDueDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("documentDueDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validateFutureDate(errors, aForm.getDocumentDueDate(), "documentDueDate", "Document Due Date",
					locale);
		}
		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getDocumentRemarks(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("documentRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
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
}
