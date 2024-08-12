//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.property.propcommserviceapt;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.property.PropertyValidator;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class PropCommServiceAptValidator {
	public static final double BUILT_MAX_AREA = Double.parseDouble("999999999999999");

	public static ActionErrors validateInput(PropCommServiceAptForm aForm, Locale locale) {
		ActionErrors errors = PropertyValidator.validateInput(aForm, locale);

		return errors;

	}
}
