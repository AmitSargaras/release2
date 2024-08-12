/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/warehouse/item/WarehouseItemValidator.java,v 1.4 2005/04/21 06:31:10 hshii Exp $
 */
package com.integrosys.cms.ui.commodityglobal.warehouse.item;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.common.RemarksValidatorUtil;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.4 $
 * @since $Date: 2005/04/21 06:31:10 $ Tag: $Name: $
 */

public class WarehouseItemValidator {
	public static ActionErrors validateInput(WarehouseItemForm aForm, Locale locale) {
		String errorCode = null;
		ActionErrors errors = new ActionErrors();
		if (!(errorCode = Validator.checkString(aForm.getWarehouseName(), true, 0, 150)).equals(Validator.ERROR_NONE)) {
			errors.add("warehouseName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					150 + ""));
		}

		if (!(errorCode = Validator.checkString(aForm.getAddress1(), true, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("address1", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					100 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getAddress2(), false, 0, 100)).equals(Validator.ERROR_NONE)) {
			errors.add("address2", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					100 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getCity(), true, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("city", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 30 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getState(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("state", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getPostalCode(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("postalCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getContactName(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("contactName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					50 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getFax(), false, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("fax", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 30 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getTelephone(), false, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("telephone", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getExtensionNumber(), false, 0, 30)).equals(Validator.ERROR_NONE)) {
			errors.add("extensionNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0",
					30 + ""));
		}
		if (!(errorCode = Validator.checkString(aForm.getEmail(), false, 0, 50)).equals(Validator.ERROR_NONE)) {
			errors.add("email", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "0", 50 + ""));
		}

		if (!(errorCode = RemarksValidatorUtil.checkRemarks(aForm.getWarehouseRemarks(), false))
				.equals(Validator.ERROR_NONE)) {
			errors.add("warehouseRemarks", RemarksValidatorUtil.getErrorMessage(errorCode));
		}

		return errors;
	}
}
