package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.*;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 4:53:34 PM
 * Desc: Bank entity branch param action class
 */
public class BankEntityBranchParamAction extends CommonAction implements IPin {

    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }

    /**
     * This method return a Array of Commad Objects responsible for a event
     *
     * @param event is of type String
     * @return Icommand Array
     */
    public ICommand[] getCommandChain(String event) {

        ICommand objArray[] = null;

        if ("checker_prepare".equals(event)
                || "maker_prepare_update".equals(event)
                || "maker_prepare_resubmit".equals(event)
                || "maker_prepare_close".equals(event)
                || "to_track".equals(event)) {
//            return new ICommand[]{new ListBankEntityBranchParamCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ListBankEntityBranchParamCmd");
        } else if ("maker_return_item".equals(event)
                || "checker_error_return".equals(event)
                || "maker_error_return".equals(event)){
//            return new ICommand[]{new ReturnBankEntityBranchParamCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("ReturnBankEntityBranchParamCmd");
        } else if ("checker_approve".equals(event)) {
//            return new ICommand[]{new CheckerApproveBankEntityBranchParamCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerApproveBankEntityBranchParamCmd");
        } else if ("checker_reject".equals(event)) {
//            return new ICommand[]{new CheckerRejectBankEntityBranchParamCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("CheckerRejectBankEntityBranchParamCmd");
        } else if ("maker_delete_item".equals(event)) {
//            return new ICommand[]{new DeleteBankEntityBranchItemCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("DeleteBankEntityBranchItemCmd");
        } else if ("maker_prepare_item".equals(event)) {
//            return new ICommand[0];
            objArray = new ICommand[0];
        } else if ("maker_close".equals(event)) {
//            return new ICommand[]{new MakerCloseBankEntityBranchParamCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerCloseBankEntityBranchParamCmd");
        } else if ("maker_submit".equals(event)) {
//            return new ICommand[]{new MakerUpdateBankEntityBranchParamCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("MakerUpdateBankEntityBranchParamCmd");
        }

        // Unrecognized event.
//        return null;
        return (objArray);

    }

    protected boolean isValidationRequired(String event) {
        return true;
    }

	protected String getErrorEvent(String event) {
        if("checker_approve".equals(event)
                || "checker_reject".equals(event))
            return "checker_error_return";
        else
            return "maker_error_return";
    }

    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        return BankEntityBranchParamValidator.validateRemarks(aForm, locale);
    }

    /**
     * This method is used to determine which the page to be displayed next using the event
     * Result hashmap and exception hashmap.It returns the page object .
     *
     * @param event        is of type String
     * @param resultMap    is of type HashMap
     * @param exceptionMap is of type HashMap
     * @return IPage
     */
    public IPage getNextPage(String event, HashMap resultMap,
                             HashMap exceptionMap) {

        String forward = event;

        if ("maker_return_item".equals(event)
                || "maker_delete_item".equals(event)
                || "maker_prepare_resubmit".equals(event)
                || "maker_error_return".equals(event))
            forward = "maker_prepare_update";
        else if("to_track".equals(event))
            forward = "maker_prepare_close";
        else if("checker_error_return".equals(event))
            forward = "checker_prepare";

        if(resultMap.get("wip")!=null && ((String)resultMap.get("wip")).equals("wip"))
            forward = "wip";

        DefaultLogger.debug(this, "the forward is " + forward);

        Page page = new Page();
        page.setPageReference(forward);
        return page;
    }
}
