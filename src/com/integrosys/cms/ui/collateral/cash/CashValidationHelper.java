/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/cash/CashValidationHelper.java,v 1.10 2003/09/17 10:37:39 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.cash;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.ui.collateral.CollateralValidator;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.10 $
 * @since $Date: 2003/09/17 10:37:39 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class CashValidationHelper extends CollateralValidator {

	public static ActionErrors validateInput(CashForm aForm, Locale locale, ActionErrors errors) {
		String errorCode = null;

		boolean isMandatory = false;
		if ("submit".equals(aForm.getEvent())) {
			isMandatory = true;
		}

		/*if (!(errorCode = Validator.checkString(aForm.getInterest(), isMandatory, 1, 10)).equals(Validator.ERROR_NONE)) {
			errors.add("interest", new ActionMessage("error.string.mandatory", "1", "10"));
		}

		if (!(errorCode = Validator.checkString(aForm.getChargeType(), isMandatory, 1, 10))
				.equals(Validator.ERROR_NONE)) {
			errors.add("chargeType", new ActionMessage("error.string.mandatory", "1", "10"));
		}*/

		return errors;

	}
}
