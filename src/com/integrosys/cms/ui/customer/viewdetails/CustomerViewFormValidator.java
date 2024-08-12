package com.integrosys.cms.ui.customer.viewdetails;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.validation.Validator;
import org.apache.struts.action.ActionErrors;

import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Mar 9, 2008
 * Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */


public class CustomerViewFormValidator {

    private static String LOGOBJ = CustomerViewFormValidator.class.getName();


    public static ActionErrors validateInput(CustomerViewForm aForm, Locale locale) {
        ActionErrors errors = new ActionErrors();

        try {
            String errMsg = Validator.checkNumber(aForm.getSubProfileID(), false, 0, 999, 0, locale);
            if (!errMsg.equals(Validator.ERROR_NONE)) {
//                System.out.println("CustomerViewFormValidator, SubProfileID is null");
            }
        } catch (Exception e) {
        }

        DefaultLogger.debug(LOGOBJ, ", No of Errors..." + errors.size());

        return errors;
    }

  }
