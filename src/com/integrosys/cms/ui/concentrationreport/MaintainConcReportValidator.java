package com.integrosys.cms.ui.concentrationreport;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: pooja $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2003/09/25 05:47:41 $ Tag: $Name: $
 */
public class MaintainConcReportValidator {

	public static ActionErrors validateInput(MaintainConcReportForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		try {
			String[] parameterValues = form.getParameterValues();
			String[] parameterCodes = form.getParameterCodes();
			for (int i = 0; i < parameterValues.length; i++) {
				String parameterValue = parameterValues[i];
				String propertyName = "parametersError" + i;
				if (!parameterCodes[i].endsWith("maxrows")) {
					if ((parameterValue == null) || (parameterValue.trim().length() == 0)) {
						errors.add(propertyName, new ActionMessage("error.ConcReport.frequencofgeneration.mandatory"));
					}
					else if (parameterValue != null) {
						try {

							if (parameterValue.equals("")) {
								errors.add(propertyName, new ActionMessage(
										"error.ConcReport.frequencofgeneration.mandatory"));
							}
						}
						catch (Exception ex) {
							ex.printStackTrace();
							errors.add(propertyName, new ActionMessage(
									"error.ConcReport.frequencofgeneration.mandatory"));
						}
					}
				}
				else {
					if ((parameterValue == null) || (parameterValue.trim().length() == 0)) {
						errors.add(propertyName, new ActionMessage("error.ConcReport.maxrows.mandatory"));
					}
					else if (parameterValue != null) {
						try {
							DefaultLogger.debug("parameterValue", parameterValue);
							String errMsg = Validator.checkNumber(parameterValue.trim(), true, 0, 999, 0, locale);
							if (!errMsg.equals(Validator.ERROR_NONE)) {
								DefaultLogger.debug("errMessage is ", "" + errMsg);
								if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
									errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(
											ErrorKeyMapper.NUMBER, "heightlessthan"), "0", "999"));
								}
								else {
									errors.add(propertyName, new ActionMessage("error.number." + errMsg));
								}
							}
						}
						catch (Exception ex) {
							ex.printStackTrace();
							DefaultLogger.debug("errMessage is ", "" + "in catch except");
							errors.add(propertyName, new ActionMessage("error.ConcReport.maxrows.numericRange", "0",
									"999"));
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