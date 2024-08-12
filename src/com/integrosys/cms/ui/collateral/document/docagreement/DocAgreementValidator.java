//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.document.docagreement;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.document.DocumentValidator;
import com.integrosys.cms.ui.collateral.ManualValuationValidator;

/**
 * 
 * @author Thurein
 * @since  2/Sep/2008	
 *
 */
public class DocAgreementValidator {
	public static ActionErrors validateInput(DocAgreementForm aForm, Locale locale) {

		ActionErrors errors = DocumentValidator.validateInput(aForm, locale);

		String errorCode = null;

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			DocAgreementValidationHelper.validateInput(aForm, locale, errors);
		}

        ManualValuationValidator.validateInput(aForm, locale, errors);
        
        return errors;

	}
}
