package com.integrosys.cms.ui.collateral.assetbased.assetvessel;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.assetbased.AssetBasedValidator;

/**
 * Created by IntelliJ IDEA. User: Naveen Date: Feb 02, 2007 Time: 6:42:25 PM To
 * change this template use Options | File Templates.
 */
public class AssetVesselValidator {

	public static ActionErrors validateInput(AssetVesselForm aForm, Locale locale) {

		ActionErrors errors = AssetBasedValidator.validateInput(aForm, locale);

		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			AssetVesselValidationHelper.validateInput(aForm, locale, errors);
		}

		return errors;

	}
}
