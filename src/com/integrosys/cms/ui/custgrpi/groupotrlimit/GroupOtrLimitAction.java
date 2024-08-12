package com.integrosys.cms.ui.custgrpi.groupotrlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.custgrpi.CustGrpIdentifierAction;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: July 1, 2008
 * Time: 6:01:28 PM
 * To change this template use File | Settings | File Templates.
 */

public class GroupOtrLimitAction extends CustGrpIdentifierAction {

    public ICommand[] getCommandChain(String event) {

        ICommand objArray[] = null;

        if (EVENT_PREPARE_CREATE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareGroupOtrLimitCommand();
        } else if (EVENT_CREATE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new AddGroupOtrLimitCommand();
        } else if (EVENT_READ.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ReadGroupOtrLimitCommand();
        } else if (EVENT_PREPARE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new PrepareGroupOtrLimitCommand();
        } else if (EVENT_READ_RETURN.equals(event) || EVENT_CANCEL.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new ReturnGroupOtrLimitCommand();
        } else if (EVENT_PREPARE_UPDATE.equals(event)) {
            objArray = new ICommand[2];
            objArray[0] = new ReadGroupOtrLimitCommand();
            objArray[1] = new PrepareGroupOtrLimitCommand();
        } else if (EVENT_UPDATE.equals(event)) {
            objArray = new ICommand[1];
            objArray[0] = new UpdateGroupOtrLimitCommand();
        } else
            objArray = super.getCommandChain(event);

        return objArray;
    }


    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        DefaultLogger.debug(this, "VALIDATION REQUIRED...");
        return GroupOtrLimitValidator.validateInput((GroupOtrLimitForm) aForm, locale);
    }


    protected boolean isValidationRequired(String event) {
        boolean result = false;
        if (EVENT_CREATE.equals(event) || EVENT_UPDATE.equals(event)) {
            result = true;
        }
        return result;
    }

    protected String getErrorEvent(String event) {
        String errorEvent = event;
        if (EVENT_CREATE.equals(event)) {
            errorEvent = EVENT_PREPARE;
        } else if (EVENT_UPDATE.equals(event)) {
            errorEvent = EVENT_PREPARE;
        }

        return errorEvent;
    }

    public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
        Page aPage = new Page();
        DefaultLogger.debug(this, "GroupOtrLimitAction event = " + event);

        if (EVENT_CREATE.equals(event)
                || EVENT_UPDATE.equals(event)
                || EVENT_READ_RETURN.equals(event)
                || EVENT_CANCEL.equals(event)) {

            DefaultLogger.debug(this, "ResultMap is :" + resultMap);

            String itemType = (String) resultMap.get("itemType");

            DefaultLogger.debug(this, "GroupOtrLimitAction itemType = " + itemType);

            if (itemType == null) {
                throw new RuntimeException("URL passed is wrong");
            } else if (EVENT_READ_RETURN.equals(event)) {
                aPage.setPageReference(itemType + "_" + (String) resultMap.get("from_event"));
            } else {
                aPage.setPageReference("groupotrlimit_update");
            }

        } else if (event.equals(EVENT_EDIT) || event.equals(EVENT_FORWARD)) {
            DefaultLogger.debug(this, "forwardPage is: " + resultMap.get("forwardPage"));
            aPage.setPageReference((String) resultMap.get("forwardPage"));
            return aPage;
        } else {
            aPage.setPageReference(event);
        }
        return aPage;
    }

}
