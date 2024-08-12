/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/commoditydoc/financingdoc/FinancingDocValidator.java,v 1.12 2005/11/12 02:54:57 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.commoditydoc.financingdoc;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.12 $
 * @since $Date: 2005/11/12 02:54:57 $ Tag: $Name: $
 */

public class FinancingDocValidator {
	public static ActionErrors validateInput(FinancingDocForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		String[] errObj = null;

		if (!(errorCode = Validator.checkString(aForm.getSalesDocDesc(), true, 0, 5)).equals(Validator.ERROR_NONE)) {
			errors.add("salesDocDesc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					5 + ""));
		}
		if ((aForm.getSalesDocDesc() != null)
				&& aForm.getSalesDocDesc().equals(CommodityDealConstant.SALES_DOCTYPE_OTHER)) {
			if (!(errorCode = Validator.checkString(aForm.getSalesDocDescOth(), true, 0, 150))
					.equals(Validator.ERROR_NONE)) {
				errors.add("salesDocDescOth", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 150 + ""));
			}
		}
		else {
			if (!(errorCode = Validator.checkString(aForm.getSalesDocDescOth(), false, 0, 150))
					.equals(Validator.ERROR_NONE)) {
				errors.add("salesDocDescOth", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 150 + ""));
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getDescGoods(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("descGoods", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		/*
		 * errors = UIValidator.checkAmount(errors, "salesOrderCcy",
		 * "salesOrderAmt", aForm.getSalesOrderCcy(), aForm.getSalesOrderAmt(),
		 * false, 0, CommodityDealConstant.MAX_AMOUNT, 0, locale,
		 * CommodityDealConstant.MAX_AMOUNT_STR);
		 */
		errObj = UIValidator.checkAmount(aForm.getSalesOrderCcy(), aForm.getSalesOrderAmt(), false, 0,
				CommodityDealConstant.MAX_AMOUNT, 0, locale);
		errors = UIValidator.addAmountErrors(errors, "salesOrderCcy", "salesOrderAmt", errObj, String.valueOf(0),
				CommodityDealConstant.MAX_AMOUNT_STR);
		/*
		 * if (!(errorCode = Validator.checkNumber(aForm.getSalesOrderAmt(),
		 * false, 0, CommodityDealConstant.MAX_AMOUNT, 0,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("salesOrderAmt",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), "0", CommodityDealConstant.MAX_AMOUNT_STR)); } else if
		 * (aForm.getSalesOrderAmt() != null &&
		 * aForm.getSalesOrderAmt().length() > 0) { if (!(errorCode =
		 * Validator.checkString(aForm.getSalesOrderCcy(), true, 0,
		 * 3)).equals(Validator.ERROR_NONE)) { errors.add("salesOrderCcy", new
		 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
		 * "0", 3 + "")); } }
		 */

		if (!(errorCode = UIValidator.checkNumber(aForm.getQuantityVal(), false, 0, CommodityDealConstant.MAX_QTY, 5,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("quantityVal", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_QTY_STR, "4"));
		}
		if (!(errorCode = Validator.checkString(aForm.getSalesDocNo(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("salesDocNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getExpiryDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("expiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			// errors = validateFutureDate(errors, aForm.getExpiryDate(),
			// "expiryDate", "Expiry Date", locale);
			if (!UIValidator.isFutureDate(aForm.getExpiryDate(), locale)) {
				errors.add("expiryDate", new ActionMessage("error.collateral.commodity.futuredate", "Expiry Date"));
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getExportLCIssBank(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("exportLCIssBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getLcReference(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("lcReference", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getLcExpiryDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("lcExpiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			// errors = validateFutureDate(errors, aForm.getLcExpiryDate(),
			// "lcExpiryDate", "LC Expiry Date", locale);
			if (!UIValidator.isFutureDate(aForm.getLcExpiryDate(), locale)) {
				errors
						.add("lcExpiryDate", new ActionMessage("error.collateral.commodity.futuredate",
								"LC Expiry Date"));
			}
		}

		if (!(errorCode = Validator.checkString(aForm.getCounterParty(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("counterParty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getFinancingDocRemarks(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("financingDocRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
		}

		return errors;
	}

	/*
	 * private static ActionErrors validateFutureDate(ActionErrors errors,
	 * String dateStr, String fieldName, String desc, Locale locale) { if
	 * (dateStr != null && dateStr.length() > 0) { Date dateObj =
	 * DateUtil.convertDate(locale, dateStr); if
	 * (dateObj.before(DateUtil.getDate())) { errors.add(fieldName, new
	 * ActionMessage("error.collateral.commodity.futuredate", desc)); } } return
	 * errors; }
	 */
}
