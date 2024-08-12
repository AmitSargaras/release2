/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/srp/global/SRPGlobalValidator.java,v 1.8 2005/09/08 08:56:22 hshii Exp $
 */
package com.integrosys.cms.ui.srp.global;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2005/09/08 08:56:22 $ Tag: $Name: $
 */
public class SRPGlobalValidator {

	public static ActionErrors validateInput(com.integrosys.cms.ui.srp.global.SRPGlobalForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		try {

			if (SRPGlobalAction.EVENT_VIEW.equals(form.getEvent())
					|| "maker_edit_srpglobal_read".equals(form.getEvent())) {
				if (!(errorCode = Validator.checkString(form.getSecurityTypeCode(), true, 1, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("securityTypeCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING,
							errorCode), "1", "1000"));
				}

			}
			else {
				String[] maxValues = form.getMaxValues();
				if ((maxValues == null) || (maxValues.length == 0)) {
					errors.add("maxValues", new ActionMessage("error.string.mandatory"));
				}
				else {
					for (int i = 0; i < maxValues.length; i++) {

						String maxValue = maxValues[i];
						String propertyName = "maxValues" + i;
						String errMsg = Validator.checkNumber(maxValue, true, 0, 100, 0, locale);
						if (!errMsg.equals(Validator.ERROR_NONE)) {
							DefaultLogger.debug("errMessage is ", "" + errMsg);
							if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
								errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.NUMBER,
										"heightlessthan"), "0", "100"));
							}
							else {
								errors.add(propertyName, new ActionMessage("error.number." + errMsg));
							}
							DefaultLogger.debug(" ERROR occured in threshHold", "--------->" + errors.size());
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