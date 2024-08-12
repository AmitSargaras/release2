package com.integrosys.cms.ui.systemparameters;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.3 $
 * @since $Date: 2003/09/29 06:16:21 $ Tag: $Name: $
 */
public class MaintainSystemParametersValidator {

	public static ActionErrors validateInput(MaintainSystemParametersForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		try {
			String[] parameterCodes = form.getParameterCodes();
			String[] parameterValues = form.getParameterValues();
			if (parameterCodes.length != parameterValues.length) {
				DefaultLogger.debug("MaintainSystemParametersValidator",
						">>>Debug:::1001 ValidationFailed : ( parameterCodes.length != parameterValues.length )");
				errors.add("parametersError", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode)));
			}
			for (int i = 0; i < parameterCodes.length; i++) {
				String parameterCode = parameterCodes[i];
				String parameterValue = parameterValues[i];
				String propertyName = "parametersError" + i;
				if (!(errorCode = Validator.checkString(parameterCode, true, 1, 40)).equals(Validator.ERROR_NONE)) {
					DefaultLogger.debug("MaintainSystemParametersValidator",
							">>>Debug:::1002 ValidationFailed : when parameterCode='" + parameterCode + "'");
					errors.add("parametersError", new ActionMessage(ErrorKeyMapper
							.map(ErrorKeyMapper.STRING, errorCode), "1", "1000"));
				}

				if ((parameterValue == null) || (parameterValue.trim().length() == 0)) {
					errors.add(propertyName, new ActionMessage("error.SystemParameters.parameterValue.mandatory"));
				}
				else if (parameterValue != null) {
					String errMsg = null;
					try {
						if (i < 4) {
							errMsg = Validator.checkNumber(parameterValue.trim(), true, 1, 99, 0, locale);
						}
						else {
							errMsg = Validator.checkNumber(parameterValue.trim(), true, 1, 100, 0, locale);
						}

						if (!errMsg.equals(Validator.ERROR_NONE)) {
							DefaultLogger.debug("errMessage is ", "" + errMsg);

							if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
								if (i < 4) {
									errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(
											ErrorKeyMapper.NUMBER, "heightlessthan"), "1", "99"));
								}
								else {
									errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(
											ErrorKeyMapper.NUMBER, "heightlessthan"), "1", "100"));
								}

							}
							else {
								errors.add(propertyName, new ActionMessage("error.number." + errMsg));
							}

						}

					}
					catch (Exception ex) {
						if (i < 4) {
							errors.add(propertyName, new ActionMessage(
									"error.SystemParameters.parameterValue.numericRange", "1", "99"));
						}
						else {
							errors.add(propertyName, new ActionMessage(
									"error.SystemParameters.parameterValue.numericRange", "1", "100"));
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