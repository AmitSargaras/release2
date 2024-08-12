package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;

public class ProcessingSubSectorLimitCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"subSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", FORM_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
        	{"event", "java.lang.String", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String event = (String)map.get("event");
        try {           
        	ISubSectorLimitParameter obj = (ISubSectorLimitParameter)map.get("subSectorLmtForm");            
        	DefaultLogger.debug(this, "ProcessingISubSectorLimitParameter= " + obj.getLoanPurposeCode());
        	DefaultLogger.debug(this, "ProcessingISubSectorLimitParameter,getEcoSectorList= " + obj.getEcoSectorList());
        	
            resultMap.put("subSectorLimitObj", obj);
            resultMap.put("event", event);
            returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
            returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        return returnMap;
    }
}
