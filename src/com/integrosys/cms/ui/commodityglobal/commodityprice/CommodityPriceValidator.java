/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/commodityprice/CommodityPriceValidator.java,v 1.8 2005/08/17 06:57:29 pooja Exp $
 */
package com.integrosys.cms.ui.commodityglobal.commodityprice;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.commodityglobal.CommodityGlobalConstants;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: pooja $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/08/17 06:57:29 $ Tag: $Name: $
 */

public class CommodityPriceValidator {
	public static ActionErrors validateInput(CommodityPriceForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (aForm.getEvent().equals(CommodityPriceAction.EVENT_PREPARE)
				|| aForm.getEvent().equals(CommodityPriceAction.EVENT_READ)) {
			if (!(errorCode = Validator.checkString(aForm.getCategory(), true, 0, 250)).equals(Validator.ERROR_NONE)) {
				errors.add("category", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						250 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getCommodityType(), true, 0, 250))
					.equals(Validator.ERROR_NONE)) {
				errors.add("commodityType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", 250 + ""));
			}
		}
		else if (aForm.getEvent().equals(CommodityPriceAction.EVENT_SUBMIT)
				|| aForm.getEvent().equals(CommodityPriceAction.EVENT_UPDATE)) {
			if ((aForm.getUpdateCheck() == null) || (aForm.getUpdateCheck().length == 0)) {
				errors.add("updateCheck", new ActionMessage("error.commodityprice.uncheck", "0", 250 + ""));
			}
			else {
				String[] updateCheck = aForm.getUpdateCheck();
				String[] tmpUOM = aForm.getPriceUOM();
				String[] tmpClosePriceAmt = aForm.getClosePriceAmt();
				String[] tmpClosePriceCcy = aForm.getClosePriceCcy();
				String[] tmpCurrentPriceAmt = aForm.getCurrentPriceAmt();
				String[] tmpCurrentPriceCcy = aForm.getCurrentPriceCcy();
				String[] tmpCloseDate = aForm.getCloseUpdateDate();
				String[] tmpCurrentDate = aForm.getCurrentUpdateDate();
				for (int i = 0; i < updateCheck.length; i++) {
					int index = Integer.parseInt(updateCheck[i]);
					if (tmpUOM != null) {
						if (!(errorCode = Validator.checkString(tmpUOM[index], false, 0, 40))
								.equals(Validator.ERROR_NONE)) {
							errors.add("priceUOM" + updateCheck[i], new ActionMessage(ErrorKeyMapper.map(
									ErrorKeyMapper.STRING, errorCode), "0", 40 + ""));
						}
					}
					if (tmpClosePriceAmt != null) {
						errors = UIValidator.checkAmount(errors, "closePriceAmt" + updateCheck[i], "closePriceAmt"
								+ updateCheck[i], tmpClosePriceCcy[index], tmpClosePriceAmt[index], false, 0.0,
								CommodityGlobalConstants.MAX_PRICE, 7, locale, CommodityGlobalConstants.MAX_PRICE_STR);
					}
					/*
					 * if (tmpCurrentPriceAmt != null) { errors =
					 * UIValidator.checkAmount(errors,
					 * "currentPriceAmt"+updateCheck[i],
					 * "currentPriceAmt"+updateCheck[i],
					 * tmpCurrentPriceCcy[index], tmpCurrentPriceAmt[index],
					 * false, 0.0, CommodityGlobalConstants.MAX_PRICE, 7,
					 * locale, CommodityGlobalConstants.MAX_PRICE_STR); }
					 */
					if (tmpCloseDate != null) {
						if (!(errorCode = Validator.checkDate(tmpCloseDate[index], false, locale))
								.equals(Validator.ERROR_NONE)) {
							errors.add("closeUpdateDate" + updateCheck[i], new ActionMessage(ErrorKeyMapper.map(
									ErrorKeyMapper.DATE, errorCode), "0", 256 + ""));
						}
					}
					/*
					 * if (tmpCurrentDate != null) { if (!(errorCode =
					 * Validator.checkDate(tmpCurrentDate[index], false,
					 * locale)).equals(Validator.ERROR_NONE)) {
					 * errors.add("currentUpdateDate"+updateCheck[i], new
					 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE,
					 * errorCode), "0", 256 + "")); } }
					 */
				}
			}
		}
		if (!(errorCode = Validator.checkString(aForm.getRemarks(), false, 0, 250)).equals(Validator.ERROR_NONE)) {
			errors.add("remarks",
					new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		}
		return errors;
	}
}