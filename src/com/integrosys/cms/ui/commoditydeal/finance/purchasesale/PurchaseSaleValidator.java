/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/purchasesale/PurchaseSaleValidator.java,v 1.15 2005/11/12 02:56:28 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.purchasesale;

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
 * @author $Author: hshii $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2005/11/12 02:56:28 $ Tag: $Name: $
 */

public class PurchaseSaleValidator {
	public static ActionErrors validateInput(PurchaseSaleForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		boolean isValidPurQty = false;

		if (aForm.getEvent().equals(PurchaseSaleAction.EVENT_UPDATE)) {
			if ((aForm.getPurSupplier() != null) && aForm.getPurSupplier().equals(CommodityDealConstant.OPTION_OTHER)) {
				if (!(errorCode = Validator.checkString(aForm.getPurSupplierOth(), true, 0, 150))
						.equals(Validator.ERROR_NONE)) {
					errors.add("purSupplierOth", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 150 + ""));
				}
			}
			else {
				if (!(errorCode = Validator.checkString(aForm.getPurSupplierOth(), false, 0, 150))
						.equals(Validator.ERROR_NONE)) {
					errors.add("purSupplierOth", new ActionMessage(
							ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 150 + ""));
				}
			}
			if ((aForm.getPurIsTTClaim() != null) && aForm.getPurIsTTClaim().equals(CommodityDealConstant.OPTION_YES)) {
				if (!(errorCode = Validator.checkInteger(aForm.getPurTTClaimDay(), true, 0, 999))
						.equals(Validator.ERROR_NONE)) {
					errors.add("purTTClaimDay", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", "999"));
				}
			}
			else {
				if (!(errorCode = Validator.checkInteger(aForm.getPurTTClaimDay(), false, 0, 999))
						.equals(Validator.ERROR_NONE)) {
					errors.add("purTTClaimDay", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
							"0", "999"));
				}
			}
			if ((aForm.getSalesBuyer() != null) && aForm.getSalesBuyer().equals(CommodityDealConstant.OPTION_OTHER)) {
				if (!(errorCode = Validator.checkString(aForm.getSalesBuyerOth(), true, 0, 150))
						.equals(Validator.ERROR_NONE)) {
					errors.add("salesBuyerOth", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 150 + ""));
				}
			}
			else {
				if (!(errorCode = Validator.checkString(aForm.getSalesBuyerOth(), false, 0, 150))
						.equals(Validator.ERROR_NONE)) {
					errors.add("salesBuyerOth", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
							"0", 150 + ""));
				}
			}
			if (!(errorCode = UIValidator.checkNumber(aForm.getPurQty(), true, 0, CommodityDealConstant.MAX_QTY, 5,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("purQty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						CommodityDealConstant.MAX_QTY_STR, "4"));
			}
			else {
				isValidPurQty = true;
			}
			errors = UIValidator.checkAmount(errors, "purUnitPriceCcy", "purUnitPriceAmt", aForm.getPurUnitPriceCcy(),
					aForm.getPurUnitPriceAmt(), true, 0, CommodityDealConstant.MAX_PRICE, 7, locale,
					CommodityDealConstant.MAX_PRICE_STR);
		}
		else {
			if (!(errorCode = UIValidator.checkNumber(aForm.getPurQty(), false, 0, CommodityDealConstant.MAX_QTY, 5,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("purQty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						CommodityDealConstant.MAX_QTY_STR, "4"));
			}
			else {
				isValidPurQty = true;
			}
			errors = UIValidator.checkAmount(errors, "purUnitPriceCcy", "purUnitPriceAmt", aForm.getPurUnitPriceCcy(),
					aForm.getPurUnitPriceAmt(), false, 0, CommodityDealConstant.MAX_PRICE, 7, locale,
					CommodityDealConstant.MAX_PRICE_STR);
		}
		if (!(errorCode = Validator.checkDate(aForm.getPurShipmentDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("purShipmentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getPurExpiryDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("purExpiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validateFutureDate(errors, aForm.getPurExpiryDate(), "purExpiryDate", "Purchase Expiry Date",
					locale);
		}
		if (!(errorCode = Validator.checkString(aForm.getPurShipmentFrom(), false, 0, 150))
				.equals(Validator.ERROR_NONE)) {
			errors.add("purShipmentFrom", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getPurShipmentTo(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("purShipmentTo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getPurTransportDoc(), false, 0, 150))
				.equals(Validator.ERROR_NONE)) {
			errors.add("purTransportDoc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getPurPayment(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("purPayment", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getPurCorrBank(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("purCorrBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getPurRefNo(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("purRefNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getPurRemarks(), false)).equals(Validator.ERROR_NONE)) {
			errors.add("purRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
		}

		if (!(errorCode = UIValidator.checkNumber(aForm.getSalesQty(), false, 0, CommodityDealConstant.MAX_QTY, 5,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add("salesQty", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					CommodityDealConstant.MAX_QTY_STR, "4"));
			/*
			 * } else if (aForm.getSalesQty() != null &&
			 * aForm.getSalesQty().length() > 0) { double purQty = 0; double
			 * salesQty = 0; if (isValidPurQty && aForm.getPurQty() != null &&
			 * aForm.getPurQty().length() > 0) { try { purQty =
			 * MapperUtil.mapStringToDouble(aForm.getPurQty(), locale); salesQty
			 * = MapperUtil.mapStringToDouble(aForm.getSalesQty(), locale); }
			 * catch (Exception e) {
			 * DefaultLogger.debug("PurchaseSaleValidator",
			 * "Error at comparing purchase and sales quantity"); } } if
			 * (salesQty > purQty) { errors.add("salesQty", newActionMessage(
			 * "error.commodity.deal.finance.purchasesale.qty.morethan")); }
			 */
		}
		errors = UIValidator.checkAmount(errors, "salesUnitPriceCcy", "salesUnitPriceAmt",
				aForm.getSalesUnitPriceCcy(), aForm.getSalesUnitPriceAmt(), false, 0, CommodityDealConstant.MAX_PRICE,
				7, locale, CommodityDealConstant.MAX_PRICE_STR);
		if (!(errorCode = Validator.checkDate(aForm.getSalesShipmentDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("salesShipmentDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		if (!(errorCode = Validator.checkDate(aForm.getSalesExpiryDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("salesExpiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}
		else {
			errors = validateFutureDate(errors, aForm.getSalesExpiryDate(), "salesExpiryDate", "Sales Expiry Date",
					locale);
		}
		if (!(errorCode = Validator.checkString(aForm.getSalesShipmentFrom(), false, 0, 150))
				.equals(Validator.ERROR_NONE)) {
			errors.add("salesShipmentFrom", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getSalesShipmentTo(), false, 0, 150))
				.equals(Validator.ERROR_NONE)) {
			errors.add("SalesShipmentTo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getSalesTransportDoc(), false, 0, 150))
				.equals(Validator.ERROR_NONE)) {
			errors.add("SalesTransportDoc", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getSalesPayment(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("SalesPayment", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getSalesCorrBank(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("SalesCorrBank", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if ((aForm.getSalesIsTTClaim() != null) && aForm.getSalesIsTTClaim().equals(CommodityDealConstant.OPTION_YES)) {
			if (!(errorCode = Validator.checkInteger(aForm.getSalesTTClaimDay(), true, 0, 999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("salesTTClaimDay", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", "999"));
			}
		}
		else {
			if (!(errorCode = Validator.checkInteger(aForm.getSalesTTClaimDay(), false, 0, 999))
					.equals(Validator.ERROR_NONE)) {
				errors.add("salesTTClaimDay", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", "999"));
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getSalesRefNo(), false, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("salesRefNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}
		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getSalesRemarks(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("salesRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
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
