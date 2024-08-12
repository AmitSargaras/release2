package com.integrosys.cms.ui.srp.country;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2005/09/08 08:58:35 $ Tag: $Name: $
 */
public class SRPCountryValidator {

	public static ActionErrors validateInput(com.integrosys.cms.ui.srp.country.SRPCountryForm form, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		try {

			if (form.getEvent() != null) {
				if (form.getEvent().equals("maker_edit_srpcountry_read")
						|| SRPCountryAction.EVENT_VIEW.equals(form.getEvent())) {
					if (form.getCountryIsoCodes() != null) {
						DefaultLogger.debug(" country code not null", "");
						if (form.getCountryIsoCodes()[0].equals("")) {
							DefaultLogger.debug(" ERRORin country code", "");
							errors.add("countryIsoCode", new ActionMessage("error.string.mandatory"));
						}
					}
					if (form.getSecuritySubTypeIds() != null) {
						DefaultLogger.debug(" securitySubTypeId code not null", "");
						if (form.getSecuritySubTypeIds()[0].equals("")) {
							DefaultLogger.debug(" ERRORin securitySubTypeId code", "");
							errors.add("securitySubTypeId", new ActionMessage("error.string.mandatory"));
						}
					}
				}
				else {
					String[] threshHoldPercents = form.getThresholdPercents();
					if ((threshHoldPercents == null) || (threshHoldPercents.length == 0)) {
						errors.add("thresholdPercents", new ActionMessage("error.string.mandatory"));
					}
					else {
						for (int i = 0; i < threshHoldPercents.length; i++) {
							String threshHoldPercent = threshHoldPercents[i];
							String propertyName = "maxValues" + i;
							String errMsg = Validator.checkNumber(threshHoldPercent, true, 0, 100, 0, locale);
							if (!errMsg.equals(Validator.ERROR_NONE)) {
								DefaultLogger.debug("errMessage is ", "" + errMsg);
								if (errMsg.equals("greaterthan") || errMsg.equals("lessthan")) {
									errors.add(propertyName, new ActionMessage(ErrorKeyMapper.map(
											ErrorKeyMapper.NUMBER, "heightlessthan"), "0", "100"));
								}
								else {
									errors.add(propertyName, new ActionMessage("error.number." + errMsg));
								}
								DefaultLogger.debug(" ERROR occured in threshHold", "--------->" + errors.size());
							}

						}
					}
					String[] valuationFrequencyUnits = form.getValuationFrequencyUnits();
					if ((valuationFrequencyUnits == null) || (valuationFrequencyUnits.length == 0)) {
						errors.add("valuationFrequencyUnits", new ActionMessage("error.string.mandatory"));
					}
					else {
						for (int j = 0; j < valuationFrequencyUnits.length; j++) {
							String valuationFrequencyUnit = valuationFrequencyUnits[j];
							String propertyName = "maxFreqUnit" + j;
							if (!Validator.validateNumber(valuationFrequencyUnit, true, 1, 4)) {
								errors.add(propertyName, new ActionMessage("error.string.mandatory"));
								DefaultLogger.debug(" ERROR occured in valuationFrequencyUnit", "--------->"
										+ errors.size());
							}
						}
					}
					String[] valuationFrequencies = form.getValuationFrequencies();
					if ((valuationFrequencies == null) || (valuationFrequencies.length == 0)) {
						errors.add("valuationFrequencies", new ActionMessage("error.string.mandatory"));
					}
					else {
						for (int j = 0; j < valuationFrequencies.length; j++) {
							String valuationFrequency = valuationFrequencies[j];
							String propertyName = "maxFreq" + j;
							String errMsg = Validator.checkNumber(valuationFrequency, true, 0, 999, 0, locale);
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
							/*
							 * if (!Validator.validateNumber(valuationFrequency,
							 * true, 0, 999)) { errors.add(propertyName, new
							 * ActionMessage("error.number.format"));
							 * errors.add(propertyName, new
							 * ActionMessage(ErrorKeyMapper
							 * .map(ErrorKeyMapper.NUMBER, "heightlessthan"),
							 * "0", "999"));DefaultLogger.debug(
							 * " ERROR occured in valuationFrequency",
							 * "--------->" + errors.size()); }
							 */

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