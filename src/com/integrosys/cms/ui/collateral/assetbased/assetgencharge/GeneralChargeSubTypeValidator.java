/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/GeneralChargeSubTypeValidator.java,v 1.5 2005/04/29 02:06:56 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Validator for GeneralChargeSubType
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2005/04/29 02:06:56 $ Tag: $Name: $
 */
public class GeneralChargeSubTypeValidator {

	public static ActionErrors validateInput(GeneralChargeSubTypeForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		boolean isMandatory = (aForm.getEvent().equals(AssetGenChargeAction.EVENT_CREATE) || aForm.getEvent().equals(
				AssetGenChargeAction.EVENT_UPDATE));

		if (!(errorCode = Validator.checkString(aForm.getAddress(), (isMandatory && true), 0, 100))
				.equals(Validator.ERROR_NONE)) {
			errors.add("address", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}

		if (!(errorCode = Validator.checkString(aForm.getValuer(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("valuer", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", "100"));
		}
		if (!(errorCode = Validator.checkDate(aForm.getValuationDate(), false, locale)).equals(Validator.ERROR_NONE)) {
			errors.add("valuationDate", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.DATE, errorCode), "0",
					256 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getValCurrency(), true, 0, 3)).equals(Validator.ERROR_NONE)) {
			errors
					.add("valCurrency", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
							"3"));
		}

		if (!AbstractCommonMapper.isEmptyOrNull(aForm.getNonStdRevalFreqNum())
				|| !AbstractCommonMapper.isEmptyOrNull(aForm.getNonStdRevalFreqUnit())) {
			if (!(errorCode = Validator.checkInteger(aForm.getNonStdRevalFreqNum(), (isMandatory && true), 1, 100))
					.equals(Validator.ERROR_NONE)) {
				errors.add("nonStdRevalFreqNum", new ActionMessage(
						ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1", 100 + ""));
			}
			if (!(errorCode = Validator.checkString(aForm.getNonStdRevalFreqUnit(), (isMandatory && true), 0, 3))
					.equals(Validator.ERROR_NONE)) {
				errors.add("nonStdRevalFreqUnit", new ActionMessage(ErrorKeyMapper
						.map(ErrorKeyMapper.STRING, errorCode), "0", 3 + ""));
			}
		}

		return errors;
	}
}
