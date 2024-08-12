package com.integrosys.cms.ui.genli;

import com.integrosys.base.uiinfra.common.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.Locale;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: JITENDRA
 * Date: May 30, 2008
 * Time: 10:13:13 AM
 * To change this template use File | Settings | File Templates.
 */
public class GenerateLIAction extends CommonAction implements IPin {

	public final String EVENT_GENERATE_LI = "generate_li";
     
    public ICommand[] getCommandChain(String event) {
        ICommand objArray [] = null;
        
        if (EVENT_LIST.equals(event))
        {
          objArray = new ICommand[1];
          objArray[0] = new ListGenerateLICommand();
        }
        else if (EVENT_GENERATE_LI.equals(event))
        {
            objArray = new ICommand[1];
            objArray[0] = new GenerateLICommand();
          }
         
         return (objArray);
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
