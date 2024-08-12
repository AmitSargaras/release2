package com.integrosys.cms.ui.docglobal;

import com.integrosys.base.uiinfra.common.CommonAction;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.IPage;
import com.integrosys.base.uiinfra.common.Page;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.app.common.constant.ICMSConstant;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Cynthia
 * Date: Aug 8, 2008
 * Time: 10:23:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class DocumentGlobalRedirectAction  extends CommonAction {

    /**
     * This method return a Array of Commad Objects responsible for a event
     *
     * @param event is of type String
     * @return Icommand Array
     */
    public ICommand[] getCommandChain(String event) {
        DefaultLogger.debug(this, "entering getCommandChain...");
        ICommand objArray[] = null;
        objArray = new ICommand[1];

        objArray[0] = new DocumentGlobalRedirectCommand();

        return (objArray);
    }


    /**
     * This method is used to determine which the page to be displayed next
     * using the event Result hashmap and exception hashmap. It returns the page
     * object.
     *
     * @param event is of type String
     * @param resultMap is of type HashMap
     * @param exceptionMap is of type HashMap
     * @return IPage
     */
    public IPage getNextPage(String event, HashMap resultMap, HashMap exceptionMap) {
        DefaultLogger.debug(this, "entering getNextPage...");
        Page aPage = new Page();

        String trxSubType = (String) resultMap.get("trxSubType");
        DefaultLogger.debug(this, "trxSubType is " + trxSubType);

        aPage.setPageReference(trxSubType);
        return aPage;
    }


    protected boolean isValidationRequired(String event) {
        return false;
    }


}
