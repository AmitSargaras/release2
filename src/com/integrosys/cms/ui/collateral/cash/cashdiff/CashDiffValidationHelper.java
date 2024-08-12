/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/cashdiff/CashDiffValidationHelper.java,v 1.8 2006/04/10 07:08:03 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.cash.cashdiff;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2006/04/10 07:08:03 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class CashDiffValidationHelper {
	public static ActionErrors validateInput(CashDiffForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}
		if (aForm.getEvent().equals("submit") || aForm.getEvent().equals("update")) {
			if (!(errorCode = Validator.checkString(aForm.getValCurrency(), isMandatory, 1, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("valCurrency", new ActionMessage("error.string.mandatory", "1", "3"));
				DefaultLogger.debug("com.integrosys.cms.ui.collateral.cash,cashdiff.CashDiffValidationHelper",
						"============= aForm.valBefMargin - aForm.valCurrency() ==========>");
			}
		}

		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		if (!(errorCode = Validator.checkAmount(aForm.getMinimalFSV(), false, 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("minimalFSV", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode), "0",
					maximumAmt));
		}

		return errors;

	}
}
