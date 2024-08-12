/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/dealinfo/DealInfoValidator.java,v 1.22 2006/07/28 13:28:43 hmbao Exp $
 */
package com.integrosys.cms.ui.commoditydeal.dealinfo;

import java.util.Date;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.commodity.deal.bus.PriceType;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.22 $
 * @since $Date: 2006/07/28 13:28:43 $ Tag: $Name: $
 */

public class DealInfoValidator {
	public static ActionErrors validateInput(DealInfoForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = Validator.checkString(aForm.getConCommProductType(), true, 0, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("conCommProductType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 30 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getConCommProductSubType(), true, 0, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("conCommProductSubType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 30 + ""));
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getConQtyVolume(), true, 0, CommodityDealConstant.MAX_QTY, 5,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("conQtyVolume", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_QTY_STR, "4"));
		}
		if (!(errorCode = Validator.checkString(aForm.getConQtyUOM(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
			errors.add("conQtyUOM", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					20 + ""));
		}

		if (!(errorCode = UIValidator.checkNumber(aForm.getConQtyDiffValue(), false, 0,
				CommodityDealConstant.MAX_QTY_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("conQtyDiffValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_QTY_DIFF_STR, "6"));
		}
		else if ((aForm.getConQtyDiffValue() != null) && (aForm.getConQtyDiffValue().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getConQtyDiffPlusmn(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("conQtyDiffPlusmn", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 10 + ""));
			}
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getConBuyerSellerAgreeDiff(), false, 0,
				CommodityDealConstant.MAX_PRICE_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("conBuyerSellerAgreeDiff", new ActionMessage(ErrorKeyMapper
					.map(ErrorKeyMapper.NUMBER, errorCode), "0", CommodityDealConstant.MAX_PRICE_DIFF_STR, "6"));
		}
		else if ((aForm.getConBuyerSellerAgreeDiff() != null) && (aForm.getConBuyerSellerAgreeDiff().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getConPriceDiffPlusmn(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("conPriceDiffPlusmn", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 10 + ""));
			}
		}
		errors = UIValidator.checkAmount(errors, "conContractedPriceCcy", "conContractedPriceAmt", aForm
				.getConContractedPriceCcy(), aForm.getConContractedPriceAmt(), false, 0,
				CommodityDealConstant.MAX_PRICE, 7, locale, CommodityDealConstant.MAX_PRICE_STR);

		if (!(errorCode = UIValidator.checkNumber(aForm.getActQtyVolume(), false, 0, CommodityDealConstant.MAX_QTY, 5,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actQtyVolume", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_QTY_STR, "4"));
		}
		String conCmdtPrice = aForm.getConCommPriceType();
		if (PriceType.EOD_PRICE.getName().equals(conCmdtPrice)) {
			validateEOD(aForm, errors, locale);
		}
		if (PriceType.FLOATING_FUTURES_PRICE.getName().equals(conCmdtPrice)) {
			// nothing to do.
		}
		if (aForm.getConCommPriceType().equals(PriceType.FIXED_FUTURES_PRICE.getName())) {
			errors = UIValidator.checkAmount(errors, "actFixBuySellFixPriceCcy", "actFixBuySellFixPriceAmt", aForm
					.getActFixBuySellFixPriceCcy(), aForm.getActFixBuySellFixPriceAmt(), false, 0,
					CommodityDealConstant.MAX_PRICE, 7, locale, CommodityDealConstant.MAX_PRICE_STR);

		}
		if (PriceType.MANUAL_EOD_PRICE.getName().equals(conCmdtPrice)) {
			validateManualEOD(aForm, errors, locale);
		}
		if (PriceType.MANUAL_FLOATING_FUTURES_PRICE.getName().equals(conCmdtPrice)) {
			validateManualFloatingFutures(aForm, errors, locale);
		}
		if (PriceType.NON_RIC_PRICE.getName().equals(conCmdtPrice)) {
			validateNonRIC(aForm, errors, locale);
		}
		if (PriceType.MANUAL_NON_RIC_PRICE.getName().equals(conCmdtPrice)) {
			validateManualNonRIC(aForm, errors, locale);
		}
		/*
		 * System.out.println("Num of errors : "+errors.size()); for(Iterator
		 * iter=errors.properties();iter.hasNext();){
		 * System.out.println(iter.next()); }
		 */
		return errors;
	}

	private static void validateManualEOD(DealInfoForm aForm, ActionErrors errors, Locale locale) {
		String errorCode = null;
		if (!(errorCode = Validator.checkDate(aForm.getActEODDate(), true, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actEODDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validatePreviousDate(errors, aForm.getActEODDate(), "actEODDate", "Price Type - EOD Date", locale);
		}
		errors = UIValidator.checkAmount(errors, "actEODMarketPriceCcy", "actEODMarketPriceAmt", aForm
				.getActEODMarketPriceCcy(), aForm.getActEODMarketPriceAmt(), true, 0, CommodityDealConstant.MAX_PRICE,
				7, locale, CommodityDealConstant.MAX_PRICE_STR);

		if (!(errorCode = UIValidator.checkNumber(aForm.getActEODCustDiff(), false, 0,
				CommodityDealConstant.MAX_PRICE_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actEODCustDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_PRICE_DIFF_STR, "6"));
		}
		else if ((aForm.getActEODCustDiff() != null) && (aForm.getActEODCustDiff().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getActEODCustDiffSign(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("actEODCustDiffSign", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 10 + ""));
			}
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getActEODCommDiff(), false, 0,
				CommodityDealConstant.MAX_PRICE_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actEODCommDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_PRICE_DIFF_STR, "6"));
		}
		else if ((aForm.getActEODCommDiff() != null) && (aForm.getActEODCommDiff().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getActEODCommDiffSign(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("actEODCommDiffSign", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 10 + ""));
			}
		}
	}

	private static void validateManualFloatingFutures(DealInfoForm aForm, ActionErrors errors, Locale locale) {
		String errorCode = null;
		if (!(errorCode = Validator.checkDate(aForm.getActFloatDate(), true, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actFloatDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validatePreviousDate(errors, aForm.getActFloatDate(), "actFloatDate",
					"Price Type - Floating Futures Contract Date", locale);
		}
		errors = UIValidator.checkAmount(errors, "actFloatMarketPriceCcy", "actFloatMarketPriceAmt", aForm
				.getActFloatMarketPriceCcy(), aForm.getActFloatMarketPriceAmt(), true, 0,
				CommodityDealConstant.MAX_PRICE, 7, locale, CommodityDealConstant.MAX_PRICE_STR);

	}

	private static void validateEOD(DealInfoForm aForm, ActionErrors errors, Locale locale) {
		String errorCode = null;
		if (!(errorCode = UIValidator.checkNumber(aForm.getActEODCustDiff(), false, 0,
				CommodityDealConstant.MAX_PRICE_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actEODCustDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_PRICE_DIFF_STR, "6"));
		}
		else if ((aForm.getActEODCustDiff() != null) && (aForm.getActEODCustDiff().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getActEODCustDiffSign(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("actEODCustDiffSign", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 10 + ""));
			}
		}
		if (!(errorCode = UIValidator.checkNumber(aForm.getActEODCommDiff(), false, 0,
				CommodityDealConstant.MAX_PRICE_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actEODCommDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_PRICE_DIFF_STR, "6"));
		}
		else if ((aForm.getActEODCommDiff() != null) && (aForm.getActEODCommDiff().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getActEODCommDiffSign(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("actEODCommDiffSign", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 10 + ""));
			}
		}
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

	private static void validateNonRIC(DealInfoForm aForm, ActionErrors errors, Locale locale) {
		if ((aForm.getActNonRICMarketPrice() == null) || aForm.getActNonRICMarketPrice().equals("")) {
			errors.add("actNonRICMarketPrice", new ActionMessage("error.commodity.deal.dealinfo.nonric.actualprice"));
		}

		String errorCode = null;
		if (!(errorCode = UIValidator.checkNumber(aForm.getActNonRICCmdtDiff(), false, 0,
				CommodityDealConstant.MAX_PRICE_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actNonRICCmdtDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", CommodityDealConstant.MAX_PRICE_DIFF_STR, "6"));
		}
		else if ((aForm.getActNonRICCmdtDiff() != null) && (aForm.getActNonRICCmdtDiff().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getActNonRICCmdtDiffSign(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("actNonRICCmdtDiffSign", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "0", 10 + ""));
			}
		}
	}

	private static void validateManualNonRIC(DealInfoForm aForm, ActionErrors errors, Locale locale) {
		String errorCode = null;
		if (!(errorCode = Validator.checkDate(aForm.getActNonRICDate(), true, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actNonRICDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validatePreviousDate(errors, aForm.getActNonRICDate(), "actNonRICDate",
					"Price Type - Non-RIC Date", locale);
		}
		errors = UIValidator.checkAmount(errors, "actNonRICMarketPriceCcy", "actNonRICMarketPriceAmt", aForm
				.getActNonRICMarketPriceCcy(), aForm.getActNonRICMarketPriceAmt(), true, 0,
				CommodityDealConstant.MAX_PRICE, 7, locale, CommodityDealConstant.MAX_PRICE_STR);

		if (!(errorCode = UIValidator.checkNumber(aForm.getActNonRICCmdtDiff(), false, 0,
				CommodityDealConstant.MAX_PRICE_DIFF, 7, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("actNonRICCmdtDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
					"0", CommodityDealConstant.MAX_PRICE_DIFF_STR, "6"));
		}
		else if ((aForm.getActNonRICCmdtDiff() != null) && (aForm.getActNonRICCmdtDiff().length() > 0)) {
			if (!(errorCode = Validator.checkString(aForm.getActNonRICCmdtDiffSign(), true, 0, 10))
					.equals(Validator.ERROR_NONE)) {
				errors.add("actNonRICCmdtDiffSign", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "0", 10 + ""));
			}
		}
	}
}
