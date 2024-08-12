package com.integrosys.cms.ui.custexposure.group;

import com.integrosys.base.uiinfra.common.*;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import org.apache.commons.lang.Validate;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;

import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Grace Teh
 * Date: July 28, 2008
 *
 * To change this template use File | Settings | File Templates.
 */
public class GrpExposureAction extends CommonAction implements IPin {

     public static final String EVENT_VIEW_GROUP_EXPOSURE = "view_grp_exposure";
     public static final String EVENT_VIEW_SINGLE_GROUP_EXPOSURE = "view_single_grp_exposure";
     public static final String EVENT_VIEW_ALL_GRP_EXPOSURES = "view_all_group_exposures";
     public static final String EVENT_VIEW_GPR_EXPOSURE_RPT = "grp_exposure_rpt";
     public static final String EVENT_CANCEL_VIEW = "view_return";
     public static final String EVENT_VIEW_OTR_GRP_EXPOSURE = "view_otr_grp_exposure";

    private Map nameCommandMap;

	public void setNameCommandMap(Map nameCommandMap) {
		this.nameCommandMap = nameCommandMap;
	}

	public Map getNameCommandMap() {
		return nameCommandMap;
	}

    protected ICommand getCommand(String name) {
		ICommand command = (ICommand) getNameCommandMap().get(name);
		Validate.notNull(command, "not able to get command given name [" + name + "]");

		return command;
	}

    public ICommand[] getCommandChain(String event) {
        DefaultLogger.debug(this, "Entering method getCommandChain in GrpExposureAction.java");
        ICommand objArray [] = null;
        
        if (EVENT_VIEW_GROUP_EXPOSURE.equals(event) || EVENT_VIEW_SINGLE_GROUP_EXPOSURE.equals(event)
             || EVENT_VIEW_OTR_GRP_EXPOSURE.equals(event)) 
        {
						objArray = new ICommand[1];
						objArray[0] = new ReadGroupExposureDetailsCommand();
        } else if (EVENT_CANCEL_VIEW.equals(event)) 
        {
            objArray = new ICommand[0];
        } else if (EVENT_VIEW_ALL_GRP_EXPOSURES.equals(event))  
        {
            objArray = new ICommand[1];
            objArray[0] = new ReadALLGroupExposureCommand();
         } else if (EVENT_VIEW_GPR_EXPOSURE_RPT.equals(event))
         {
            objArray = new ICommand[1];
             // Spring
            objArray[0] = getCommand("ReadGrpExpReportCommand");
         }
         DefaultLogger.debug(this, "*******" + event + "================" + objArray);
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
        if (EVENT_CANCEL_VIEW.equals(event)) {
            forwardName = "after_list";
        }else if (EVENT_VIEW_ALL_GRP_EXPOSURES.equals(event)){
            forwardName = EVENT_VIEW_ALL_GRP_EXPOSURES;        
        }else if (EVENT_VIEW_GPR_EXPOSURE_RPT.equals(event)){
            forwardName = EVENT_VIEW_GPR_EXPOSURE_RPT;
        }else if(EVENT_VIEW_GROUP_EXPOSURE.equals(event)){
	          forwardName = EVENT_VIEW_GROUP_EXPOSURE;
        }else if(EVENT_VIEW_SINGLE_GROUP_EXPOSURE.equals(event)){
	          forwardName = EVENT_VIEW_SINGLE_GROUP_EXPOSURE;
        }else if(EVENT_VIEW_OTR_GRP_EXPOSURE.equals(event)){
	          forwardName = EVENT_VIEW_OTR_GRP_EXPOSURE;
        }else {
            forwardName = event;
        }
        return forwardName;
    }
}
