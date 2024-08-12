/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/fixedassetsoth/faoitem/FAOItemValidator.java,v 1.4 2005/04/14 06:53:44 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.fixedassetsoth.faoitem;

import java.math.BigDecimal;
import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.assetbased.assetgencharge.GeneralChargeSubTypeValidator;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/04/14 06:53:44 $ Tag: $Name: $
 */

public class FAOItemValidator {
	public static ActionErrors validateInput(FAOItemForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = GeneralChargeSubTypeValidator.validateInput(aForm, locale);
		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();

		boolean isMandatory = (aForm.getEvent().equals(FAOItemAction.EVENT_CREATE) || aForm.getEvent().equals(
				FAOItemAction.EVENT_UPDATE));

		if (!(errorCode = Validator.checkString(aForm.getDescription(), (isMandatory && true), 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("description", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					"100"));
		}
		if (!(errorCode = Validator.checkAmount(aForm.getGrossValueValCurr(), (isMandatory && true), 0,
				IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT, IGlobalConstant.DEFAULT_CURRENCY, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("grossValueValCurr", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.AMOUNT, errorCode),
					"0", maximumAmt));
		}
		if (!(errorCode = Validator.checkInteger(aForm.getMargin(), (isMandatory && true), 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("margin", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0", "100"));
		}

		return errors;
	}
}
