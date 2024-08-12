package com.integrosys.cms.ui.customer.viewdetails;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: jitendra
 * Date: Nov 9, 2007
 * Time: 3:56:31 PM
 * To change this template use File | Settings | File Templates.
 */


public class CustomerViewAction extends CommonAction implements IPin {

    public static final String EVENT_VIEW_CUSTOMER_DETAILS = "view_customer_details";

    public ICommand[] getCommandChain(String event) {
        ICommand objArray [] = null;

        if (EVENT_VIEW_CUSTOMER_DETAILS.equals(event)){
            objArray = new ICommand[1];
            objArray[0] = new ProcessDetailsCustomerCommand();
        }

        return (objArray);
    }

    /**
     * This method is called only for create and Update command to validate the form
     * and return the ActionErrors object.
     *
     * @param aForm  is of type ActionForm
     * @param locale of type Locale
     * @return ActionErrors
     */
    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        DefaultLogger.debug(this, "Inside validate Input child class");
        return CustomerViewFormValidator.validateInput((CustomerViewForm) aForm, locale);
    }

    protected boolean isValidationRequired(String event) {
        boolean result = false;
        if (EVENT_VIEW_CUSTOMER_DETAILS.equals(event)){
            result = true;
        }
        return result;
    }

    protected String getErrorEvent(String event) {
        String errorEvent = getDefaultEvent();
        if (EVENT_VIEW_CUSTOMER_DETAILS.equals(event)){
            errorEvent = "error_page";
        }
        return errorEvent;
    }


    /**
     * This method is used to determine the next page to be displayed using the event
     * Result hashmap and exception hashmap.It returns the page object .
     *
     * @param event        is of type String
     * @param resultMap    is of type HashMap
     * @param exceptionMap is of type HashMap
     * @return IPage
     */
    public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
        Page aPage = new Page();
        String forward = null;
        forward = getReference(event);
        aPage.setPageReference(forward);
        return aPage;
    }

    /**
     * method which determines the forward name for a particular event
     *
     * @param event as String
     * @return String
     */
    private String getReference(String event) {
        String forwardName = "submit_fail";
        forwardName = event;
        return forwardName;
    }
}
