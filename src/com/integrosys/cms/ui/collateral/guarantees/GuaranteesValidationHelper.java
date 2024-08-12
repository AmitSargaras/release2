/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/guarantees/GuaranteesValidationHelper.java,v 1.10 2005/07/29 10:03:48 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.guarantees;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.collateral.CollateralValidator;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class GuaranteesValidationHelper extends CollateralValidator {

	public static ActionErrors validateInput(GuaranteesForm aForm, Locale locale, ActionErrors errors) {
		String errorCode;
//		String maximumAmt = new BigDecimal(IGlobalConstant.MAXIMIUM_ALLOWED_AMOUNT).toString();
		/*if (aForm.getEvent().equals("update") || aForm.getEvent().equals("submit")) {
			boolean isMandatory = false;
			if ("submit".equals(aForm.getEvent())) {
				isMandatory = true;
			}

			if (!(errorCode = Validator.checkString(aForm.getLe() + "", isMandatory, 0, 3)).equals(Validator.ERROR_NONE)) {
				errors.add("le", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "3"));
			}

			 * if ("Y".equals(aForm.getLe())){ if (!(errorCode =
			 * Validator.checkDate(aForm.getLeDate(), true,
			 * locale)).equals(Validator.ERROR_NONE)) { errors.add("leDate", new
			 * ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode),
			 * "0", 256 + "")); } }
			 
		}*/

		return errors;
	}
}
