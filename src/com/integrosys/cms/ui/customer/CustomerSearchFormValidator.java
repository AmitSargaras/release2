package com.integrosys.cms.ui.customer;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;

/**
 * Form validator to validate against customer search form.
 * 
 * @author pooja
 * @author Chong Jun Yong
 * @since Jul 4, 2003
 * 
 */
public class CustomerSearchFormValidator {

	private static String LOGOBJ = CustomerSearchFormValidator.class.getName();

	public static ActionErrors validateInput(CustomerSearchForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (aForm.getGobutton() != null) {
			if (aForm.getGobutton().equals("1")) {
				if (!(errorCode = Validator.checkString(aForm.getCustomerName(), true, 0, 100))
						.equals(Validator.ERROR_NONE)) {
					errors.add("cusName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode),"1","100"));
				}
			}
			else if (aForm.getGobutton().equals("2")) {

				if (!(errorCode = Validator.checkString(aForm.getLegalID(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
					errors.add("legalID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"20"));
				}
			}
			else if (aForm.getGobutton().equals("3")) {
				if (!(errorCode = Validator.checkString(aForm.getFacilitySystem(), true, 2, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("facilitySystem", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode)));
				}
				if (!(errorCode = Validator.checkString(aForm.getFacilitySystemID(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("facilitySystemID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"40"));
				}
			}
			else if (aForm.getGobutton().equals("4")) {
				if (!(errorCode = Validator.checkString(aForm.getAaNumber(), true, 1, 35)).equals(Validator.ERROR_NONE)) {
					errors.add("aaNumber", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"35"));
				}
			}
			else if (aForm.getGobutton().equals("5")) {
				if(!"".equals(aForm.getFacilitySystem())||!"".equals(aForm.getFacilitySystemID())) {
					if (!(errorCode = Validator.checkString(aForm.getFacilitySystem(), true, 2, 40)).equals(Validator.ERROR_NONE)) {
						errors.add("facilitySystem", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode)));
					}
					if (!(errorCode = Validator.checkString(aForm.getFacilitySystemID(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
						errors.add("facilitySystemID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"40"));
					}
				}
			}
		}
		return errors;

	}
}