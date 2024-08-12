/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan.buildup;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.bridgingloan.BridgingLoanAction;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Build Up Validation
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class BuildUpValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.buildup.BuildUpForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in BuildUpValidator", "form.getEvent()=" + form.getEvent());

			boolean isSave = false;
			if (BridgingLoanAction.EVENT_CREATE.equals(form.getEvent())
					|| BridgingLoanAction.EVENT_UPDATE.equals(form.getEvent())) { // ||
				// BridgingLoanAction.EVENT_MAKER_NAVIGATE_TAB.equals(form.
				// getEvent())) {
				isSave = true;
			}
			DefaultLogger.debug("in BridgingLoanValidator", "isSave: " + isSave);

			String propertyType = form.getPropertyType();
			String unitID = form.getUnitID(); // Unit ID No.
			String blockNo = form.getBlockNo(); // Block/Lot No.
			String titleNo = form.getTitleNo();
			String unitNo = form.getUnitNo(); // Unit No.
			String isUnitDischarged = form.getIsUnitDischarged();
			String approxArea = form.getApproxArea();
			String approxAreaUOM = form.getApproxAreaUOM();
			String approxAreaSecondary = form.getApproxAreaSecondary();
			String approxAreaUOMSecondary = form.getApproxAreaUOMSecondary();
			String redemptionCurrency = form.getRedemptionCurrency();
			String redemptionAmount = form.getRedemptionAmount();
			String salesCurrency = form.getSalesCurrency();
			String salesAmount = form.getSalesAmount();
			String salesDate = form.getSalesDate();
			String purchaserName = form.getPurchaserName();
			String endFinancier = form.getEndFinancier();
			String buRemarks = form.getBuRemarks();

			String tenancyDate = form.getTenancyDate(); // Tenancy Agreement
														// Date
			String tenantName = form.getTenantName();
			String tenancyPeriod = form.getTenancyPeriod();
			String tenancyPeriodUnit = form.getTenancyPeriodUnit();
			String tenancyExpiryDate = form.getTenancyExpiryDate();
			String rentalCurrency = form.getRedemptionCurrency();
			String rentalAmount = form.getRentalAmount();
			String paymentFrequency = form.getPaymentFrequency();

			if (!(errMsg = Validator.checkString(propertyType, isSave, 0, 5)).equals(Validator.ERROR_NONE)) {
				errors.add("propertyType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"5"));
			}
			if (!(errMsg = Validator.checkString(unitID, isSave, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("unitID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "30"));
			}
			if (!(errMsg = Validator.checkString(blockNo, isSave, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("blockNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "30"));
			}
			if (!(errMsg = Validator.checkString(titleNo, false, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("titleNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "30"));
			}
			if (!(errMsg = Validator.checkString(unitNo, isSave, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("unitNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "30"));
			}
			if (!(errMsg = Validator.checkString(isUnitDischarged, isSave, 0, 1)).equals(Validator.ERROR_NONE)) {
				errors.add("isUnitDischarged", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"0", "1"));
			}
			if (!(errMsg = Validator.checkNumber(approxArea, isSave, 0, 99999.99)).equals(Validator.ERROR_NONE)) {
				errors.add("approxArea", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errMsg), "0",
						"99999.99"));
			}
			if (!(errMsg = Validator.checkString(approxAreaUOM, isSave, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("approxAreaUOM", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1",
						"10"));
			}
			if (!(errMsg = Validator.checkNumber(approxAreaSecondary, false, 0, 99999.99)).equals(Validator.ERROR_NONE)) {
				errors.add("approxAreaSecondary", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errMsg),
						"0", "99999.99"));
			}
			if (!(errMsg = Validator.checkString(approxAreaUOMSecondary, false, 1, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("approxAreaUOMSecondary", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.STRING, errMsg), "1", "10"));
			}
			if (!(errMsg = Validator.checkString(redemptionCurrency, isSave, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("redemptionCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"1", "3"));
			}
			if (!(errMsg = Validator.checkAmount(redemptionAmount, isSave, 0,
					IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15, IGlobalConstant.DEFAULT_CURRENCY, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("redemptionAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg),
						"0", IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkString(salesCurrency, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("salesCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1",
						"3"));
			}
			if (!(errMsg = Validator.checkAmount(salesAmount, false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("salesAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkDate(salesDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("salesDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(purchaserName, false, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("purchaserName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"100"));
			}
			if (!(errMsg = Validator.checkString(endFinancier, false, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("endFinancier", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"100"));
			}
			if (!(errMsg = Validator.checkString(buRemarks, false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("buRemarks",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "500"));
			}

			if (!(errMsg = Validator.checkDate(tenancyDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("tenancyDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(tenantName, false, 0, 100)).equals(Validator.ERROR_NONE)) {
				errors.add("tenantName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"100"));
			}
			if (!(errMsg = Validator.checkString(tenancyPeriodUnit, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("tenancyPeriodUnit", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"1", "3"));
			}
			if (!(errMsg = Validator.checkInteger(tenancyPeriod, false, 0, 999)).equals(Validator.ERROR_NONE)) {
				errors.add("tenancyPeriod", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errMsg), "0",
						"999"));
			}
			if (!(errMsg = Validator.checkDate(tenancyExpiryDate, false, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("tenancyExpiryDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errMsg)));
			}
			if (!(errMsg = Validator.checkString(rentalCurrency, false, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("rentalCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "1",
						"3"));
			}
			if (!(errMsg = Validator.checkAmount(rentalAmount, false, 0, IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT_15,
					IGlobalConstant.DEFAULT_CURRENCY, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("rentalAmount", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errMsg), "0",
						IGlobalConstant.MAXIMUM_ALLOWED_AMOUNT_15_STR));
			}
			if (!(errMsg = Validator.checkString(paymentFrequency, false, 0, 5)).equals(Validator.ERROR_NONE)) {
				errors.add("paymentFrequency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg),
						"0", "5"));
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}