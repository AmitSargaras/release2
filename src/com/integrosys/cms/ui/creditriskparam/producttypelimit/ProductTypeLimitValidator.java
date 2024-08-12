package com.integrosys.cms.ui.creditriskparam.producttypelimit;

import java.util.Locale;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionErrors;

import com.integrosys.base.techinfra.validation.Validator;


public class ProductTypeLimitValidator {
    public static ActionErrors validateInput(ProductTypeLimitForm form, Locale locale) {

        ActionErrors errors = new ActionErrors();

       if (!(Validator.checkString(form.getProdTypeDesc(), true, 1, 150)).equals(Validator.ERROR_NONE)) {
           errors.add("prodTypeDesc",  new ActionMessage("error.feeds.no.selection"));
       }
        
       if(form.getProdTypeRefCode() == null || form.getProdTypeRefCode().equals("")){
       	 errors.add("prodTypeRefCode", new ActionMessage("error.string.mandatory"));
       }
       
        return errors;        
    }
}
