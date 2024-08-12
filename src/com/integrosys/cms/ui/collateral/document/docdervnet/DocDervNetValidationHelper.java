/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervnet/DocDervNetValidationHelper.java,v 1.2 2003/07/18 11:09:45 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docdervnet;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/18 11:09:45 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class DocDervNetValidationHelper {
	public static ActionErrors validateInput(DocDervNetForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;

		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage("error.date.mandatory", "1", "256"));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.DocumentValidationHelper",
					" aForm.getCollateralMaturityDate()= " + aForm.getCollateralMaturityDate());
		}

		return errors;

	}
}
