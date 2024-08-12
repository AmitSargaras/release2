/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.countrylimit;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Administrator
 *         <p/>
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class CountryLimitItemAction extends CountryLimitAction {

    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }

    protected IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
        Page aPage = new Page();

        if (resultMap.get("wip") != null && ((String) resultMap.get("wip")).equals("wip")) {
            aPage.setPageReference("wip");
            return aPage;
        }

        aPage.setPageReference(getReference(event, resultMap, exceptionMap));
        return aPage;
    }

    private String getReference(String event, HashMap resultMap, HashMap exceptionMap) {
        String fromEvent = (String) (resultMap.get("fromEvent"));
        if (EventConstant.EVENT_READ.equals(event)) {
            return "read_item";
        } else if (EventConstant.EVENT_PREPARE_CREATE.equals(event) ||
                EventConstant.EVENT_PREPARE_UPDATE.equals(event)) {
            return "update_item";
        } else if (EventConstant.EVENT_CREATE.equals(event) ||
                EventConstant.EVENT_SUBMIT.equals(event) ||
                EventConstant.EVENT_CANCEL.equals(event)) {
            return "update_return";
        } else if (EventConstant.EVENT_READ_RETURN.equals(event)) {
            return fromEvent + "_return";
        }
        return event;
    }

    protected String getErrorEvent(String event) {
        String errorEvent = event;
        if (EventConstant.EVENT_CREATE.equals(event)) {
            return EventConstant.EVENT_PREPARE_CREATE;
        } else if (EventConstant.EVENT_SUBMIT.equals(event)) {
            return EventConstant.EVENT_PREPARE_UPDATE;
        }
        return errorEvent;
    }

    protected boolean isValidationRequired(String event) {
        if (EventConstant.EVENT_CREATE.equals(event) ||
                EventConstant.EVENT_SUBMIT.equals(event)) {
            return true;
        }
        return false;
    }

    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        return CountryLimitValidator.validateCountryLimitItem(aForm, locale);
    }

    protected ICommand[] getCommandChain(String event) {
        ICommand[] objArray = null;
        // TODO Auto-generated method stub
        if (EventConstant.EVENT_PREPARE_CREATE.equals(event)) {
            objArray = new ICommand[1];
//            objArray[0] = new PrepareCountryLimitItemCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("PrepareCountryLimitItemCmd");
        } else if (EventConstant.EVENT_READ.equals(event) ||
                EventConstant.EVENT_PREPARE_UPDATE.equals(event)) {
            objArray = new ICommand[1];
//            objArray[0] = new PrepareCountryLimitItemCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("PrepareCountryLimitItemCmd");
        } else if (EventConstant.EVENT_CREATE.equals(event) ||
                EventConstant.EVENT_SUBMIT.equals(event)) {
            objArray = new ICommand[1];
//            objArray[0] = new SaveCountryLimitItemCmd();
            objArray[0] = (ICommand) getNameCommandMap().get("SaveCountryLimitItemCmd");
        }

        return objArray;
    }
}
