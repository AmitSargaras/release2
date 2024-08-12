//STUB GENERATED....CHANGE THIS FILE AS YOU FEEL
package com.integrosys.cms.ui.collateral.assetbased;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.ui.collateral.CollateralValidator;
import com.integrosys.cms.ui.collateral.ManualValuationValidator;

/**
 * Created by IntelliJ IDEA. User: ssathish Date: Jun 20, 2003 Time: 6:42:25 PM
 * To change this template use Options | File Templates.
 */
public class AssetBasedValidationHelper extends CollateralValidator {

	private static String LOGOBJ = AssetBasedValidationHelper.class.getName();

	public static ActionErrors validateInput(AssetBasedForm aForm, Locale locale, ActionErrors errors) {
		ManualValuationValidator.validateInput(aForm, locale, errors);
		DefaultLogger.debug(LOGOBJ, "AssetBasedValidationHelper , No of Errors..." + errors.size());
		return errors;

	}
}
