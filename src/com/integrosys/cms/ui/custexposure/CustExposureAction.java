package com.integrosys.cms.ui.custexposure;

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
public class CustExposureAction extends CommonAction implements IPin {

     public static final String EVENT_VIEW_CUST_EXPOSURE = "view_cust_exposure";
     public static final String EVENT_VIEW_GROUP_EXPOSURE = "view_grp_exposure";
     public static final String EVENT_VIEW_CUST_BACK_SYS_EXPOSURE = "view_cust_back_sys_exposure";
     public static final String EVENT_VIEW_CUST_EXPOSURE_FRM_GRP = "view_cust_exposure_frm_grp";
     public static final String EVENT_VIEW_GROUP_TO_OTR_GROUP_EXPOSURE = "view_grp_to_otr_grp_exposure";
     
    public ICommand[] getCommandChain(String event) {
        ICommand objArray [] = null;
        
        if (EVENT_VIEW_CUST_EXPOSURE.equals(event) || EVENT_VIEW_CUST_EXPOSURE_FRM_GRP.equals(event))  
        {
						objArray = new ICommand[1];
						objArray[0] = new ReadCustExposureDetailsCommand();  
        } else if (EVENT_VIEW_GROUP_EXPOSURE.equals(event) || EVENT_VIEW_GROUP_TO_OTR_GROUP_EXPOSURE.equals(event)) 
        {
						objArray = new ICommand[0];
						
        } else if (EVENT_VIEW_CUST_BACK_SYS_EXPOSURE.equals(event))   
        {
            objArray = new ICommand[1];
            objArray[0] = new ReadCustBackSysExposureDetailsCommand();
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

        if (EVENT_VIEW_CUST_BACK_SYS_EXPOSURE.equals(event)){
            forwardName = EVENT_VIEW_CUST_BACK_SYS_EXPOSURE;
        }else if(EVENT_VIEW_GROUP_EXPOSURE.equals(event)){
	          forwardName = EVENT_VIEW_GROUP_EXPOSURE;
        }else if(EVENT_VIEW_CUST_EXPOSURE.equals(event) ||EVENT_VIEW_CUST_EXPOSURE_FRM_GRP.equals(event) ){
            forwardName = EVENT_VIEW_CUST_EXPOSURE;
        }else if(EVENT_VIEW_GROUP_TO_OTR_GROUP_EXPOSURE.equals(event)){
	          forwardName = EVENT_VIEW_GROUP_TO_OTR_GROUP_EXPOSURE;
        }else{
	        forwardName = event;
        }
        return forwardName;
    }
}
