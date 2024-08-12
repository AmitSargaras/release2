/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommodityDealValidator.java,v 1.9 2005/11/12 02:54:29 hshii Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.UIValidator;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/11/12 02:54:29 $ Tag: $Name: $
 */

public class CommodityDealValidator {
	public static ActionErrors validateInput(CommodityDealForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		if (aForm.getEvent().equals(CommodityDealAction.EVENT_REFRESH_CAL_POSITION)
				|| aForm.getEvent().equals(CommodityDealAction.EVENT_CAL_POSITION)) {
			if (!(errorCode = Validator.checkString(aForm.getSecurityID(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("securityID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"20"));
			}
			if (!(errorCode = Validator.checkString(aForm.getProductType(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("productType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
						"20"));
			}
			if (!(errorCode = Validator.checkString(aForm.getProductSubType(), false, 0, 20))
					.equals(Validator.ERROR_NONE)) {
				errors.add("productSubType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
						"0", "20"));
			}
			if (!(errorCode = Validator.checkString(aForm.getUomUnit(), false, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("uomUnit",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "20"));
			}
			if (!(errorCode = UIValidator.checkNumber(aForm.getUomValue(), false, 0, CommodityDealConstant.MAX_QTY, 4,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("uomValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						CommodityDealConstant.MAX_QTY_STR, "3"));
			}

			if (!(errorCode = Validator.checkString(aForm.getLimitID(), true, 0, 20)).equals(Validator.ERROR_NONE)) {
				errors.add("limitID",
						new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "20"));
			}

			if (!(errorCode = Validator.checkNumber(aForm.getProposedFaceValueAmt(), true, 0,
					CommodityDealConstant.MAX_AMOUNT, 0, locale)).equals(Validator.ERROR_NONE)) {
				errors.add("proposedFaceValueAmt", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
						errorCode), "0", CommodityDealConstant.MAX_AMOUNT_STR));
			}

			if (!(errorCode = Validator.checkString(aForm.getProposedFaceValueCcy(), true, 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("proposedFaceValueCcy", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
						errorCode), "0", 3 + ""));
			}

			if (!(errorCode = Validator.checkNumber(aForm.getPercentageFinancing(), true, 0, 100, 0, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("percentageFinancing", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
			}
		}
		else {
			if (!(errorCode = UIValidator.checkNumber(aForm.getUomValue(), false, 0, CommodityDealConstant.MAX_QTY, 4,
					locale)).equals(Validator.ERROR_NONE)) {
				errors.add("uomValue", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
						CommodityDealConstant.MAX_QTY_STR, "3"));
			}
			if (!(errorCode = Validator.checkNumber(aForm.getPercentageFinancing(), false, 0, 100, 0, locale))
					.equals(Validator.ERROR_NONE)) {
				errors.add("percentageFinancing", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
			}
			errors = UIValidator.checkAmount(errors, "proposedFaceValueCcy", "proposedFaceValueAmt", aForm
					.getProposedFaceValueCcy(), aForm.getProposedFaceValueAmt(), false, 0,
					CommodityDealConstant.MAX_AMOUNT, 0, locale, CommodityDealConstant.MAX_AMOUNT_STR);
		}
		return errors;
	}
}
