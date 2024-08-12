package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;


public class PrepareReturnSubSectorLimitCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
       		{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
       		{"event", "java.lang.String", REQUEST_SCOPE},
       		{"fromEvent", "java.lang.String", SERVICE_SCOPE}, 
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
			{"fromEvent", "java.lang.String", SERVICE_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String event = (String)map.get("event");
        String fromEvent = (String)map.get("fromEvent");
        if (SubSectorLimitAction.EVENT_PREPARE.equals(event) ||
        		SubSectorLimitAction.EVENT_PREPARE_UPDATE.equals(event))
        	resultMap.put("fromEvent", fromEvent);

        resultMap.put("event", event);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
    
}
