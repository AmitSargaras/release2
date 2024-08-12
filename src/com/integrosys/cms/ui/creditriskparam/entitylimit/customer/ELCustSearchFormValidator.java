/*
 * Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.creditriskparam.entitylimit.customer;

import java.io.Serializable;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;

public class ELCustSearchFormValidator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static String LOGOBJ = ELCustSearchFormValidator.class.getName();

    public static ActionErrors validateInput(ELCustSearchForm form, Locale locale) {

        String event = form.getEvent();
        ActionErrors errors = new ActionErrors();
        String errorCode = "";

        if (ELCustomerSearchAction.EVENT_CUST_ADD_SELECT.equals(event)) {
            
            if (form.getCustomerID() == null || form.getCustomerID().length == 0) {
                errors.add("chkDeletes", new ActionMessage("error.checkbox.mandatory"));
                DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
            }
        }
        
        if (form.getGobutton() != null && ELCustomerSearchAction.EVENT_CUST_ADD_SEARCH.equals(event)) {
	        if (form.getGobutton().equals("1"))  {
	            if (!(errorCode = Validator.checkString(form.getCustomerName(), true, 5, 40)).equals(Validator.ERROR_NONE)) {
	                errors.add("cusName", new ActionMessage("error.string.cciCustomername"));
	               // DefaultLogger.debug(LOGOBJ, " aForm.getCustomerName() = " + form.getCustomerName());
	            }
	        }
	        
	        if (form.getGobutton().equals("2")) {
                if (!(errorCode = Validator.checkString(form.getLeIDType(), true, 1, 10)) .equals(Validator.ERROR_NONE)) {
                    errors.add("leIDType", new ActionMessage("error.mandatory"));
                    DefaultLogger.debug(LOGOBJ, " aForm.getLeIDType() = " + form.getLeIDType());
                }
                if (!(errorCode = Validator.checkString(form.getLegalID(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                    errors.add("legalID", new ActionMessage("error.string.legalid"));
                    DefaultLogger.debug(LOGOBJ, " aForm.getLegalID() = " + form.getLegalID());
                }
            }
            if (form.getGobutton().equals("3")) {
                if (!(errorCode = Validator.checkString(form.getIdNO(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
                    errors.add("idNO", new ActionMessage("error.string.idno"));
                    DefaultLogger.debug(LOGOBJ, " aForm.getIdNO() = " + form.getIdNO());
                }
            }
        }
       
        
        DefaultLogger.debug(LOGOBJ, "CustomerSearchFormValidator , No of Errors..." + errors.size());
        return errors;
    }
}
