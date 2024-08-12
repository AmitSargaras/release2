/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docgencredit/DocGenCreditValidationHelper.java,v 1.2 2003/07/18 11:11:16 hshii Exp $
 */
//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docgencredit;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/18 11:11:16 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class DocGenCreditValidationHelper {
	public static ActionErrors validateInput(DocGenCreditForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;

		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage("error.date.mandatory", "1", "256"));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.DocumentValidationHelper",
					" aForm.getCollateralMaturityDate()= " + aForm.getCollateralMaturityDate());
		}

		if (!(errorCode = Validator.checkDate(aForm.getPerfectionDate(), true, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("perfectionDate", new ActionMessage("error.date.mandatory", "1", "256"));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.document.DocGenCreditValidationHelper",
					" aForm.getPerfectionDate()= " + aForm.getPerfectionDate());
		}

		return errors;

	}
}
