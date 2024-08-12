/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/document/docdervisd/DocDervISDValidationHelper.java,v 1.2 2003/07/18 11:08:18 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.document.docdeedsub;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.document.DocumentValidator;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2003/10/14 11:16:57 $ Tag: $Name: $
 */
public class DocDeedSubValidator {
	public static ActionErrors validateInput(DocDeedSubForm aForm, Locale locale) {

		ActionErrors errors = DocumentValidator.validateInput(aForm, locale);

		String errorCode = null;
		// if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals(
		// "reject"))) {
		// if (!(errorCode = Validator.checkDate(aForm.getDateIFREMAAgmt(),
		// false, locale)).equals(Validator.ERROR_NONE)) {
		// errors.add("dateIFREMAAgmt", new ActionMessage(
		// "error.date.mandatory", "1", "256"));
		// }
		//
		// if (!(errorCode = Validator.checkDate(aForm.getDateICOMDocument(),
		// false, locale)).equals(Validator.ERROR_NONE)) {
		// errors.add("dateICOMDocument", new ActionMessage(
		// "error.date.mandatory", "1", "256"));
		// }
		//
		// if (!(errorCode = Validator.checkString(aForm.getDescProdICOM(),
		// false, 0, 250)).equals(Validator.ERROR_NONE)) {
		// errors.add("descProdICOM", new ActionMessage(ErrorKeyMapper.map(
		// ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		// }
		//
		// if (!(errorCode = Validator.checkString(aForm.getDescProdIFEMA(),
		// false, 0, 250)).equals(Validator.ERROR_NONE)) {
		// errors.add("descProdIFEMA", new ActionMessage(ErrorKeyMapper.map(
		// ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		// }
		//
		// if (!(errorCode = Validator.checkString(aForm.getDescProducISDA(),
		// false, 0, 250)).equals(Validator.ERROR_NONE)) {
		// errors.add("descProducISDA", new ActionMessage(ErrorKeyMapper
		// .map(ErrorKeyMapper.STRING, errorCode), "0", 250 + ""));
		// }
		//
		// DocDeedSubValidationHelper.validateInput(aForm, locale, errors);
		// }
		return errors;

	}
}
