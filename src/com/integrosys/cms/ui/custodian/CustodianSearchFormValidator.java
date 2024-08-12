package com.integrosys.cms.ui.custodian;

import java.util.Locale;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.cms.ui.customer.CustomerSearchFormValidator;

public class CustodianSearchFormValidator extends CustomerSearchFormValidator{
	
	private static String LOGOBJ = CustomerSearchFormValidator.class.getName();
	
	public static ActionErrors validateInput(CustodianSearchForm aForm, Locale locale) {
		ActionErrors errors = new ActionErrors();
		String errorCode = "";

		if (aForm.getGobutton() != null) {
			if (aForm.getGobutton().equals("1")) {
				if (!(errorCode = Validator.checkString(aForm.getCustomerName(), true, 3, 40))
						.equals(Validator.ERROR_NONE)) {
					errors.add("cusName", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "3",
							"40"));
//					DefaultLogger.debug(LOGOBJ, " aForm.getCustomerName() = " + aForm.getCustomerName());
				}
			}
			else if (aForm.getGobutton().equals("2")) {

                if (!(errorCode = Validator.checkString(aForm.getLeIDType(), true, 1, 10)) .equals(Validator.ERROR_NONE)) {
                        errors.add("leIDType", new ActionMessage("error.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " aForm.getLeIDType() = " + aForm.getLeIDType());
                } 

				if (!(errorCode = Validator.checkString(aForm.getLegalID(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
					errors.add("legalID", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"20"));
					DefaultLogger.debug(LOGOBJ, " aForm.getLegalID() = " + aForm.getLegalID());
				}
			}
			else if (aForm.getGobutton().equals("3")) {
				if (!(errorCode = Validator.checkString(aForm.getIdNO(), true, 1, 40)).equals(Validator.ERROR_NONE)) {
					errors.add("idNO", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"40"));
					DefaultLogger.debug(LOGOBJ, " aForm.getIdNO() = " + aForm.getIdNO());
				}
			}
			else if (aForm.getGobutton().equals("4")) {
				if (!(errorCode = Validator.checkString(aForm.getDocBarCode(), true, 1, 35)).equals(Validator.ERROR_NONE)) {
					errors.add("docBarCode", new ActionMessage(ErrorKeyMapper.map(ErrorKeyMapper.STRING, errorCode), "1",
							"100"));
				}
			}
		}

		DefaultLogger.debug(LOGOBJ, "CustodianSearchFormValidator , No of Errors..." + errors.size());

		return errors;

	}

}
