/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervisd/DocDervISDValidationHelper.java,v 1.2 2003/07/18 11:08:18 hshii Exp $
 */

//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.document.docdervisd;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/07/18 11:08:18 $
 * Tag: $Name:  $
 */

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class DocDervISDValidationHelper {
	public static ActionErrors validateInput(DocDervISDForm aForm, Locale locale, ActionErrors errors) {

		String errorCode = null;

		if (!(errorCode = Validator.checkDate(aForm.getCollateralMaturityDate(), false, locale))
				.equals(Validator.ERROR_NONE)) {
			errors.add("collateralMaturityDate", new ActionMessage("error.date.mandatory", "1", "256"));
			DefaultLogger.debug("com.integrosys.cms.ui.collateral.DocumentValidationHelper",
					" aForm.getCollateralMaturityDate()= " + aForm.getCollateralMaturityDate());
		}

		/*
		 * Currently already check in validator if (aForm.getDocumentDate() !=
		 * null) { if (!(errorCode =
		 * Validator.checkDate(aForm.getDocumentDate(), true,
		 * locale)).equals(Validator.ERROR_NONE)) { errors.add("documentDate",
		 * new ActionMessage("error.date.mandatory", "1", "256"));
		 * DefaultLogger.
		 * debug("com.integrosys.cms.ui.collateral.document.docdervisd",
		 * "============= aForm.getDepMatDate() ==========>"+
		 * aForm.getDocumentDate()); } } if (aForm.getDateIFREMAAgmt() != null)
		 * { if (!(errorCode = Validator.checkDate(aForm.getDateIFREMAAgmt(),
		 * true, locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("dateIFREMAAgmt", new
		 * ActionMessage("error.date.mandatory", "1", "256"));
		 * DefaultLogger.debug
		 * ("com.integrosys.cms.ui.collateral.document.docdervisd",
		 * "============= aForm.getDepMatDate() ==========>"+
		 * aForm.getDateIFREMAAgmt()); } } if (aForm.getDateICOMDocument() !=
		 * null) { if (!(errorCode =
		 * Validator.checkDate(aForm.getDateICOMDocument(), true,
		 * locale)).equals(Validator.ERROR_NONE)) {
		 * errors.add("dateICOMDocument", new
		 * ActionMessage("error.date.mandatory", "1", "256"));
		 * DefaultLogger.debug
		 * ("com.integrosys.cms.ui.collateral.document.docdervisd",
		 * "============= aForm.getDepMatDate() ==========>"+
		 * aForm.getDateICOMDocument()); } }
		 */
		return errors;
	}
}
