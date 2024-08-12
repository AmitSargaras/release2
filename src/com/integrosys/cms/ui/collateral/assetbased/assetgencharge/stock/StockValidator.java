/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/assetbased/assetgencharge/stock/StockValidator.java,v 1.6 2005/11/09 07:02:34 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.assetbased.assetgencharge.stock;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2005/11/09 07:02:34 $ Tag: $Name: $
 */

public class StockValidator {
	public static ActionErrors validateInput(StockForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();

		boolean hasInsurance = false;

		if ((aForm.getHasInsurance() != null) && aForm.getHasInsurance().equals(ICMSConstant.TRUE_VALUE)) {
			hasInsurance = true;
		}

		boolean isMandatory = aForm.getEvent().equals(StockAction.EVENT_EDIT);

		if (!(errorCode = Validator.checkInteger(aForm.getInsCoverNum(), (hasInsurance && isMandatory && true), 0, 999))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insCoverNum", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER, errorCode), "0",
					"999"));
		}
		if (!(errorCode = Validator.checkString(aForm.getInsCoverUnit(), (hasInsurance && isMandatory && true), 1, 5))
				.equals(Validator.ERROR_NONE)) {
			errors.add("insCoverUnit", new ActionMessage("error.string.mandatory", "1", "5"));
		}

		/*
		 * if (hasInsurance) { if (!(errorCode =
		 * Validator.checkInteger(aForm.getInsCoverNum(), (isMandatory && true),
		 * 0, 999)).equals(Validator.ERROR_NONE)) { errors.add("insCoverNum",
		 * new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
		 * errorCode), "0", "999")); } if (!(errorCode =
		 * Validator.checkString(aForm.getInsCoverUnit(), (isMandatory && true),
		 * 1, 5)).equals(Validator.ERROR_NONE)) { errors.add("insCoverUnit", new
		 * ActionMessage("error.string.mandatory", "1", "5")); } } else { if
		 * (aForm.getInsCoverNum() != null &&
		 * !aForm.getInsCoverNum().trim().equals("")) {
		 * errors.add("insCoverNum", new ActionMessage("error.string.empty")); }
		 * else if (aForm.getInsCoverUnit() != null &&
		 * !aForm.getInsCoverUnit().trim().equals("")) {
		 * errors.add("insCoverUnit", new ActionMessage("error.string.empty"));
		 * } }
		 */
		return errors;
	}
}
