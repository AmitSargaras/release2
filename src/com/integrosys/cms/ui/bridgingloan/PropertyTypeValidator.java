/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.bridgingloan;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Property Type Validation
 * @author $Author: KLYong $<br>
 * @version $Revision: $
 * @since $String: $ Tag: $Name: $
 */
public class PropertyTypeValidator {
	public static ActionErrors validateInput(com.integrosys.cms.ui.bridgingloan.PropertyTypeForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errMsg = null;

		try {
			DefaultLogger.debug("in PropertyTypeValidator", "form.getEvent()=" + form.getEvent());

			String propertyType = form.getPropertyType();
			String propertyTypeOthers = form.getPropertyTypeOthers();
			String noOfUnits = form.getNoOfUnits(); // Proposed Number of Unit
			String remarks = form.getRemarks();

			if (!(errMsg = Validator.checkString(propertyType, true, 0, 5)).equals(Validator.ERROR_NONE)) {
				errors.add("propertyType", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0",
						"5"));

				if ((propertyType != null) && propertyType.equals("99")) {
					if (!(errMsg = Validator.checkString(propertyTypeOthers, true, 0, 50)).equals(Validator.ERROR_NONE)) {
						errors.add("propertyTypeOthers", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
								errMsg), "0", "50"));
					}
				}
			}
			if (!(errMsg = Validator.checkInteger(noOfUnits, true, 0, 99999)).equals(Validator.ERROR_NONE)) {
				errors.add("noOfUnits", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.INTEGER, errMsg), "0",
						"99999"));
			}
			if (!(errMsg = Validator.checkString(remarks, false, 0, 500)).equals(Validator.ERROR_NONE)) {
				errors.add("remarks", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errMsg), "0", "500"));
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return errors;
	}
}