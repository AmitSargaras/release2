package com.integrosys.cms.ui.custrelationship.customer;

import java.io.Serializable;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.cms.ui.custrelationship.CustRelConstants;

public class CRCustSearchFormValidator implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static String LOGOBJ = CRCustSearchFormValidator.class.getName();

    public static ActionErrors validateInput(CRCustSearchForm form, Locale locale) {

        String event = form.getEvent();
        ActionErrors errors = new ActionErrors();
        String errorCode = "";

        if (CRCustomerSearchAction.EVENT_CUST_ADD_SELECT.equals(event) || 
        		CRCustomerSearchAction.EVENT_CUST_ADD_SH_SELECT.equals(event)) {
            
            if (form.getSelectCustomerID() == null || form.getSelectCustomerID().length == 0) {
                errors.add("chkDeletes", new ActionMessage(CustRelConstants.ERROR_CHKBOX_MANDATORY));
                DefaultLogger.debug(LOGOBJ, "Check that there is at least one selected checkbox.");
            }
        }
        
        if (    CRCustomerSearchAction.EVENT_CUST_SEARCH.equals(event) || 
        		CRCustomerSearchAction.EVENT_CUST_ADD_SEARCH.equals(event) ||
        		CRCustomerSearchAction.EVENT_CUST_ADD_SH_SEARCH.equals(event) ||
				CRCustomerSearchAction.EVENT_CUST_ADD_SEARCH_FRAME.equals(event) ||
        		CRCustomerSearchAction.EVENT_CUST_ADD_SH_SEARCH_FRAME.equals(event)							
				)  {         					
			
			if (form.getGobutton() != null) {
                if (form.getGobutton().equals("1")) {
                    if (!(errorCode = Validator.checkString(form.getCustomerName(), true, 5, 40)).equals(Validator.ERROR_NONE)) {
		                errors.add("cusName", new ActionMessage("error.string.cciCustomername"));
//		                DefaultLogger.debug(LOGOBJ, " form.getCustomerName() = " + form.getCustomerName());
		            }
                }
                if (form.getGobutton().equals("2")) {
                    if (!(errorCode = Validator.checkString(form.getLeIDType(), true, 1, 10)) .equals(Validator.ERROR_NONE)) {
                        errors.add("leIDType", new ActionMessage("error.mandatory"));
                        DefaultLogger.debug(LOGOBJ, " form.getLeIDType() = " + form.getLeIDType());
                    }
                    if (!(errorCode = Validator.checkString(form.getLegalID(), true, 1, 20)).equals(Validator.ERROR_NONE)) {
                        errors.add("legalID", new ActionMessage("error.string.legalid"));
                        DefaultLogger.debug(LOGOBJ, " form.getLegalID() = " + form.getLegalID());
                    }
                }
                if (form.getGobutton().equals("3")) {
                    if (!(errorCode = Validator.checkString(form.getIdNO(), true, 1, 100)).equals(Validator.ERROR_NONE)) {
                        errors.add("idNO", new ActionMessage("error.string.idno"));
                        DefaultLogger.debug(LOGOBJ, " form.getIdNO() = " + form.getIdNO());
                    }                    
                }
            }						
        }       
        
        DefaultLogger.debug(LOGOBJ, "errors " + errors.size());
        return errors;
    }
}
