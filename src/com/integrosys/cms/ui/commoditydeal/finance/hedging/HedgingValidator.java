/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/finance/hedging/HedgingValidator.java,v 1.10 2005/11/12 02:56:10 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal.finance.hedging;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.mapper.MapperUtil;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2005/11/12 02:56:10 $ Tag: $Name: $
 */

public class HedgingValidator {
	public static ActionErrors validateInput(HedgingForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getGlobalTreasuryRefNo(), false, 0, 50))
				.equals(Validator.ERROR_NONE)) {
			errors.add("globalTreasuryRefNo", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 50 + ""));
		}
		if (aForm.getEvent().equals(HedgingAction.EVENT_UPDATE)) {
			if (!(errorCode = Validator.checkString(aForm.getHedgingPriceCcy(), true, 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("hedgingPriceCcy", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 3 + ""));
			}
			if (!(errorCode = UIValidator.checkNumber(aForm.getHedgingPriceAmt(), true, 0,
					CommodityDealConstant.MAX_PRICE, 7, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("hedgingPriceAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", CommodityDealConstant.MAX_PRICE_STR, "6"));
			}
			if (!(errorCode = UIValidator.checkNumber(aForm.getTotalQtyGoodsHedge(), true, 0,
					CommodityDealConstant.MAX_QTY, 5, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("totalQtyGoodsHedge", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", CommodityDealConstant.MAX_QTY_STR,
						"4"));
			}
			else if ((aForm.getTotalQtyGoodsHedge() != null) && (aForm.getTotalQtyGoodsHedge().length() > 0)) {
				double totalQtyHedge = 0;
				double totalQty = 0;
				if ((aForm.getTotalQtyGoods() != null) && (aForm.getTotalQtyGoods().length() > 0)) {
					try {
						totalQtyHedge = MapperUtil.mapStringToDouble(aForm.getTotalQtyGoodsHedge(), locale);
						totalQty = MapperUtil.mapStringToDouble(aForm.getTotalQtyGoods(), locale);
					}
					catch (Exception e) {
						DefaultLogger.debug("HedgigValidator",
								"Error at validate total quantity hedged and total quantity");
					}
				}
				if (totalQtyHedge > totalQty) {
					errors.add("totalQtyGoodsHedge", new ActionMessage(
							"error.commodity.deal.finance.hedging.qty.morethan"));
				}
			}
			String[] period = aForm.getPeriod();
			if (period != null) {
				for (int i = 0; i < period.length; i++) {
					if (!(errorCode = Validator.checkInteger(period[i], true, 0, 999)).equals(Validator.ERROR_NONE)) {
						errors.add("period" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.NUMBER, errorCode), "0", "999"));
					}
				}
			}
			String[] periodUnit = aForm.getPeriodUnit();
			if (periodUnit != null) {
				for (int i = 0; i < periodUnit.length; i++) {
					if (!(errorCode = Validator.checkString(periodUnit[i], true, 0, 10)).equals(Validator.ERROR_NONE)) {
						errors.add("periodUnit" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.STRING, errorCode), "0", 10 + ""));
					}
				}
			}

			String[] startdate = aForm.getPeriodStartDate();
			if (startdate != null) {
				for (int i = 0; i < startdate.length; i++) {
					if (!(errorCode = Validator.checkDate(startdate[i], true, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("periodStartDate" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
					}
				}
			}
		}
		else {
			if (!(errorCode = Validator.checkString(aForm.getHedgingPriceCcy(), false, 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("hedgingPriceCcy", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 3 + ""));
			}
			if (!(errorCode = Validator.checkNumber(aForm.getHedgingPriceAmt(), false, 0,
					CommodityDealConstant.MAX_AMOUNT, 0, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("hedgingPriceAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"0", CommodityDealConstant.MAX_AMOUNT_STR));
			}
			if (!(errorCode = UIValidator.checkNumber(aForm.getTotalQtyGoodsHedge(), false, 0,
					CommodityDealConstant.MAX_QTY, 5, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("totalQtyGoodsHedge", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", CommodityDealConstant.MAX_QTY_STR,
						"4"));
			}
			else if ((aForm.getTotalQtyGoodsHedge() != null) && (aForm.getTotalQtyGoodsHedge().length() > 0)) {
				double totalQtyHedge = 0;
				double totalQty = 0;
				if ((aForm.getTotalQtyGoods() != null) && (aForm.getTotalQtyGoods().length() > 0)) {
					try {
						totalQtyHedge = MapperUtil.mapStringToDouble(aForm.getTotalQtyGoodsHedge(), locale);
						totalQty = MapperUtil.mapStringToDouble(aForm.getTotalQtyGoods(), locale);
					}
					catch (Exception e) {
						DefaultLogger.debug("HedgigValidator",
								"Error at validate total quantity hedged and total quantity");
					}
				}
				if (totalQtyHedge > totalQty) {
					errors.add("totalQtyGoodsHedge", new ActionMessage(
							"error.commodity.deal.finance.hedging.qty.morethan"));
				}
			}
			String[] period = aForm.getPeriod();
			if (period != null) {
				for (int i = 0; i < period.length; i++) {
					if (!(errorCode = Validator.checkInteger(period[i], false, 0, 999)).equals(Validator.ERROR_NONE)) {
						errors.add("period" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.NUMBER, errorCode), "0", "999"));
					}
				}
			}
			String[] periodUnit = aForm.getPeriodUnit();
			if (periodUnit != null) {
				for (int i = 0; i < periodUnit.length; i++) {
					if (!(errorCode = Validator.checkString(periodUnit[i], false, 0, 10)).equals(Validator.ERROR_NONE)) {
						errors.add("periodUnit" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.STRING, errorCode), "0", 10 + ""));
					}
				}
			}

			String[] startdate = aForm.getPeriodStartDate();
			if (startdate != null) {
				for (int i = 0; i < startdate.length; i++) {
					if (!(errorCode = Validator.checkDate(startdate[i], false, locale)).equals(Validator.ERROR_NONE)) {
						errors.add("periodStartDate" + String.valueOf(i), new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
					}
				}
			}
		}
		return errors;
	}
}
