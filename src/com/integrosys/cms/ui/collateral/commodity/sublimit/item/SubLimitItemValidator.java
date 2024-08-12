/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/item/SubLimitItemValidator.java,v 1.4 2006/09/25 05:53:20 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants;
import com.integrosys.cms.ui.commoditydeal.CommodityDealConstant;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-15 Tag :
 *        com.integrosys.cms.ui.collateral.commodity.sublimit.AddSubLimitValidator
 *        .java
 */
public class SubLimitItemValidator {
	public static ActionErrors validateInput(SubLimitItemForm aForm, Locale locale) {
		// System.out.println("\tin AddSubLimitValidator's validateInput -
		// Begin.");
		ActionErrors errors = new ActionErrors();
		String errorCode = Validator.checkString(aForm.getSubLimitType(), true, 0, 19);
		if (!(errorCode).equals(Validator.ERROR_NONE)) {
			errors.add(SLUIConstants.FN_SLT, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", "19"));
		}

		if (!(errorCode = Validator.checkString(aForm.getSubLimitCCY(), true, 0, 3)).equals(Validator.ERROR_NONE)) {
			errors.add(SLUIConstants.FN_SL_CCY, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),
					"0", 3 + ""));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getSubLimitAmount(), true, 1, CommodityDealConstant.MAX_AMOUNT,
				0, locale)).equals(Validator.ERROR_NONE)) {
			errors.add(SLUIConstants.FN_SL_AMOUNT, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "1", String.valueOf(CommodityDealConstant.MAX_AMOUNT), "-1"));
		}

		if (!(errorCode = Validator.checkNumber(aForm.getActiveAmount(), true, 1, CommodityDealConstant.MAX_AMOUNT, 0,
				locale)).equals(Validator.ERROR_NONE)) {
			errors.add(SLUIConstants.FN_ACTIVE_AMOUNT, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
					errorCode), "1", String.valueOf(CommodityDealConstant.MAX_AMOUNT), "-1"));
		}

		if (errors.isEmpty()) {
			double activeAmount = Double.parseDouble(aForm.getActiveAmount());
			double subLimitAmount = Double.parseDouble(aForm.getSubLimitAmount());
			if (activeAmount > subLimitAmount) {
				errors.add(SLUIConstants.FN_ACTIVE_AMOUNT, new ActionMessage(SLUIConstants.ERR_ACTIVE_AMOUNT));
			}
		}
		// System.out.println("Num of errors : " + errors.size());
		// System.out.println("\tin AddSubLimitValidator's validateInput -
		// End.");
		return errors;
	}
}
