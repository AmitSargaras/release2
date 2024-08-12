//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.others.othersa;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.others.OthersValidator;
import com.integrosys.cms.ui.collateral.others.othersa.OthersaForm;
import com.integrosys.cms.ui.collateral.others.othersa.OthersaValidationHelper;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class OthersaValidator {
	public static final double BUILT_MAX_AREA = Double.parseDouble("999999999999999");

	public static ActionErrors validateInput(OthersaForm aForm, Locale locale) {

		ActionErrors errors = OthersValidator.validateInput(aForm, locale);
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			OthersaValidationHelper.validateInput(aForm, locale, errors);
		}
		return errors;
	}
}
