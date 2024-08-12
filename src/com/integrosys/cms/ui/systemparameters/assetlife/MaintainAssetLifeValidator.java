/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/systemparameters/assetlife/MaintainAssetLifeValidator.java,v 1 2007/01/30 Jerlin Exp $
 */
package com.integrosys.cms.ui.systemparameters.assetlife;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Describe this class. Purpose: Validation for Asset Life Description: Validate
 * the year that user key in
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision: 1$
 * @since $Date: 2007/01/30$ Tag: $Name$
 */

public class MaintainAssetLifeValidator {

	/**
	 * Description : Check the input value
	 * 
	 * @param form is of type CommonForm
	 * @param locale of type Locale
	 * @return errors with error messages
	 */

	public static ActionErrors validateInput(MaintainAssetLifeForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();

		try {

			String[] yearValues = form.getYearValues();
			if ((yearValues == null) || (yearValues.length == 0)) {
				errors.add("yearValues", new ActionMessage("error.integer.mandatory"));

			}
			else {
				for (int i = 0; i < yearValues.length; i++) {

					String yearValue = yearValues[i];
					String propertyName = "yearValues" + i;
					String errMsg = Validator.checkInteger(yearValue, true, -1, 100);

					if (!errMsg.equals(Validator.ERROR_NONE)) {
						DefaultLogger.debug("errMessage is ", "" + errMsg);

						if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
							errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
									"heightlessthan"), "-1", "100"));

						}
						else {
							errors.add(propertyName, new ActionMessage("error.number." + errMsg));
						}
					}
				}
			}
			DefaultLogger.debug(" Total Errors", "--------->" + errors.size());

		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return errors;

	}

}
