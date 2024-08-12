/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprofile/item/CommProfileItemValidator.java,v 1.9 2006/03/03 02:11:02 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprofile.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.commodity.main.bus.profile.IProfile;
import com.integrosys.cms.ui.commodityglobal.CommodityGlobalConstants;
import com.integrosys.cms.ui.commodityglobal.commodityprofile.CMDTProfConstants;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/03/03 02:11:02 $ Tag: $Name: $
 */

public class CommProfileItemValidator {
	public static ActionErrors validateInput(CommProfileItemForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (!(errorCode = Validator.checkString(aForm.getCommodityCategory(), true, 0, 30))
				.equals(Validator.ERROR_NONE)) {
			errors.add("commodityCategory", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 30 + ""));
		}

		if (aForm.getEvent().equals(CommProfileItemAction.EVENT_ADD)
				|| aForm.getEvent().equals(CommProfileItemAction.EVENT_UPDATE)) {
			if (!(errorCode = Validator.checkString(aForm.getProductType(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
				errors.add("productType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						30 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getProductSubType(), true, 0, 30))
					.equals(Validator.ERROR_NONE)) {
				errors.add("productSubType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 30 + ""));
			}
			/*
			 * if (!(errorCode = Validator.checkString(aForm.getMarketUOM(),
			 * true, 0, 1)).equals(Validator.ERROR_NONE)) {
			 * errors.add("marketUOM", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
			 * errorCode), "0", 1 + "")); }
			 */
			if (!(errorCode = UIValidator.checkNumber(aForm.getCommPriceDiff(), false, 0,
					CommodityGlobalConstants.MAX_PRICE, 7, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("commPriceDiff", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode),
						"1", CommodityGlobalConstants.MAX_PRICE_STR, "6"));
			}
			else {
				if ((aForm.getCommPriceDiff() != null) && (aForm.getCommPriceDiff().length() > 0)) {
					if (!(errorCode = Validator.checkString(aForm.getPlusmn(), true, 0, 5))
							.equals(Validator.ERROR_NONE)) {
						errors.add("plusmn", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
								"0", 5 + ""));
					}
				}
			}
			if (!(errorCode = Validator.checkString(aForm.getPriceType(), true, 0, 10)).equals(Validator.ERROR_NONE)) {
				errors.add("priceType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						10 + ""));
			}
			else {
				if (IProfile.PRICE_TYPE_FUTURES.equals(aForm.getPriceType())) {
					if (!(errorCode = Validator.checkString(aForm.getMarketName(), true, 0, 50))
							.equals(Validator.ERROR_NONE)) {
						errors.add("marketName", new ActionMessage(
								ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
					}
					if (!(errorCode = Validator.checkString(aForm.getRicFuturesChoice(), true, 0, 10))
							.equals(Validator.ERROR_NONE)) {
						errors.add("ricFuturesChoice", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
								errorCode), "0", 10 + ""));
					}
					else {
						if (aForm.getRicFuturesChoice().equals(IProfile.RIC_TYPE_FUTURES)) {
							if (!(errorCode = Validator.checkString(aForm.getRicFutures(), true, 0, 20))
									.equals(Validator.ERROR_NONE)) {
								errors.add("ricFutures", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
										errorCode), "0", 20 + ""));
							}
						}
						else {
							if (!(errorCode = Validator.checkString(aForm.getRicFuturesOptions(), true, 0, 20))
									.equals(Validator.ERROR_NONE)) {
								errors.add("ricFuturesOptions", new ActionMessage(ErrorKeyMapper.map(
										ErrorKeyMapper.STRING, errorCode), "0", 20 + ""));
							}
						}
					}
				}
				else if (IProfile.PRICE_TYPE_CASH.equals(aForm.getPriceType())) {
					if (!(errorCode = Validator.checkString(aForm.getCountryArea(), true, 0, 50))
							.equals(Validator.ERROR_NONE)) {
						errors.add("countryArea", new ActionMessage(ErrorKeyMapper
								.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
					}
					if (!(errorCode = Validator.checkString(aForm.getOutrights(), true, 0, 50))
							.equals(Validator.ERROR_NONE)) {
						errors.add("outrights", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
								"0", 50 + ""));
					}
					if (!(errorCode = Validator.checkString(aForm.getChains(), true, 0, 50))
							.equals(Validator.ERROR_NONE)) {
						errors.add("chains", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
								"0", 50 + ""));
					}
					if (!(errorCode = Validator.checkString(aForm.getRicCash(), true, 0, 20))
							.equals(Validator.ERROR_NONE)) {
						errors.add("ricCash", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
								"0", 20 + ""));
					}
				}
				else {
					if (!(errorCode = Validator.checkString(aForm.getCountry(), true, 0, 50))
							.equals(Validator.ERROR_NONE)) {
						errors.add(CMDTProfConstants.FN_COUNTRY, new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.STRING, errorCode), "0", 30 + ""));
					}
					if (!(errorCode = Validator.checkString(aForm.getNonRICDesc(), true, 0, 100))
							.equals(Validator.ERROR_NONE)) {
						errors.add(CMDTProfConstants.FN_NON_RIC_DESC, new ActionMessage(ErrorKeyMapper.map(
								ErrorKeyMapper.STRING, errorCode), "0", 100 + ""));
					}
				}
			}

			if (aForm.getSupplierList() != null) {
				String[] tmpList = aForm.getSupplierList();
				boolean hasError = false;
				for (int i = 0; (i < tmpList.length) && !hasError; i++) {
					if (!(errorCode = Validator.checkString(tmpList[i], false, 0, 150)).equals(Validator.ERROR_NONE)) {
						hasError = true;
					}
				}
				if (hasError) {
					errors.add("supplier", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							150 + ""));
				}
			}

			if (aForm.getBuyerList() != null) {
				String[] tmpList = aForm.getBuyerList();
				boolean hasError = false;
				for (int i = 0; (i < tmpList.length) && !hasError; i++) {
					if (!(errorCode = Validator.checkString(tmpList[i], false, 0, 150)).equals(Validator.ERROR_NONE)) {
						hasError = true;
					}
				}
				if (hasError) {
					errors.add("buyer", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							150 + ""));
				}
			}
		}
		return errors;
	}
}
