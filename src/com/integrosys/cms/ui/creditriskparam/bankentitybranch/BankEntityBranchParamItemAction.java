package com.integrosys.cms.ui.creditriskparam.bankentitybranch;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.cms.ui.creditriskparam.countrylimit.EventConstant;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Andy Wong
 * Date: Jun 3, 2008
 * Time: 5:36:09 PM
 * Desc: Bank entity branch param item action class
 */
public class BankEntityBranchParamItemAction extends BankEntityBranchParamAction {

    private Map nameCommandMap;

    public Map getNameCommandMap() {
        return nameCommandMap;
    }

    public void setNameCommandMap(Map nameCommandMap) {
        this.nameCommandMap = nameCommandMap;
    }

    public IPage getNextPage(String event, HashMap hashMap, HashMap hashMap1) {
        String forward = "";

        if(event.equals("maker_prepare_item"))
            forward = event;
        else
            forward = "maker_return_item";
        
        DefaultLogger.debug(this, "the forward is " + forward);

        Page page = new Page();
        page.setPageReference(forward);
        return page;
    }

    protected boolean isValidationRequired(String event) {
        if("maker_prepare_item".equals(event)
                || "maker_return_item".equals(event))
            return false;
        return true;
    }

	protected String getErrorEvent(String event) {			
    	return "maker_prepare_item";
	}
    
    public ActionErrors validateInput(ActionForm aForm, Locale locale) {
        return BankEntityBranchParamValidator.validateItemInput(aForm, locale);
    }

    public ICommand[] getCommandChain(String event) {

        ICommand objArray[] = null;

        if ("maker_add_item".equals(event) || "maker_edit_item".equals(event)) {
//            return new ICommand[]{new SaveBankEntityBranchParamItemCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("SaveBankEntityBranchParamItemCmd");
        } else if ("maker_prepare_item".equals(event)) {
//            return new ICommand[]{new PrepareBankEntityParamItemCmd()};
            objArray = new ICommand[1];
            objArray[0] = (ICommand) getNameCommandMap().get("PrepareBankEntityParamItemCmd");
        } else if ("maker_return_item".equals(event)) {
//            return new ICommand[0];
            objArray = new ICommand[0];
        }

//        return null;  //To change body of implemented methods use File | Settings | File Templates.
        return (objArray);
    }
}
