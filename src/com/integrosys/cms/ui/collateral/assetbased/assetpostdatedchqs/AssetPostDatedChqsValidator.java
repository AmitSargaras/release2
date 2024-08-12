//GENERATED FILE... ANYMODIFICATION WILL BE LOST. ASK SATHISH FOR ANY CLARIFICATION
package com.integrosys.cms.ui.collateral.assetbased.assetpostdatedchqs;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;

import com.integrosys.cms.ui.collateral.assetbased.AssetBasedValidator;

public class AssetPostDatedChqsValidator {
	public static ActionErrors validateInput(AssetPostDatedChqsForm aForm, Locale locale) {
		ActionErrors errors = AssetBasedValidator.validateInput(aForm, locale);
		if (!(aForm.getEvent().equals("approve") || aForm.getEvent().equals("reject"))) {
			AssetPostDatedChqsValidationHelper.validateInput(aForm, locale, errors);
		}
		return errors;

	}
}
