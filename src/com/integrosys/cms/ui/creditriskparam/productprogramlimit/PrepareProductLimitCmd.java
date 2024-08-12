package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * Author: Syukri
 * Date: Jun 5, 2008
 */
public class PrepareProductLimitCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
       		{"mainProductLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
       		{"remarks","java.lang.String",REQUEST_SCOPE },
       		{"event", "java.lang.String", REQUEST_SCOPE},
       		{"fromEvent", "java.lang.String", REQUEST_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"remarks","java.lang.String",REQUEST_SCOPE },
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"fromEvent", "java.lang.String", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String remarks = (String)map.get("remarks");
        resultMap.put("remarks", remarks);
        
        String event = (String)map.get("event");     
        resultMap.put("event", event);
        
        String fromEvent = (String)map.get("fromEvent");
        resultMap.put("fromEvent", fromEvent);
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
    
}
