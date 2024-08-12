package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;

import java.util.HashMap;
/**
 * Author: Syukri
 * Author: KC Chin
 * Date: Jun 17, 2008
 */
public class ReadSectorLimitCmd extends SectorLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"sectorLimitId", "java.lang.String", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
	    });
	}	        		

	public String[][] getResultDescriptor() {
		return (new String[][]{
            {"SectorLimitForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", FORM_SCOPE},
            {"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
            {"sectorLimitId", "java.lang.String", REQUEST_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
	    });
	}
	    
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        String sectorLimitId = (String)map.get("sectorLimitId");
        String event = (String)(map.get("event"));
		
		DefaultLogger.debug(this, "Prepare SectorLimit sectorLimitId : " + sectorLimitId);

        try {
        	IMainSectorLimitParameter sectorObj = getSectorLimitProxy().getSectorLimitById(Long.parseLong(sectorLimitId));
        	
            resultMap.put("event", event);
        	resultMap.put("sectorLimitId", String.valueOf(sectorObj.getId()));
            resultMap.put("SectorLimitForm", sectorObj);
            resultMap.put("mainSectorLimitObj", sectorObj);
            
        } catch (Exception e) {
        	throw new CommandProcessingException (e.toString());
        }
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
    }	    
}
