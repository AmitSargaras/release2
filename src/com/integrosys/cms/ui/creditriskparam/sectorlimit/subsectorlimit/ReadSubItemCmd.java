/*
* Copyright Integro Technologies Pte Ltd
* $Header: $
*/
package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;


public class ReadSubItemCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return new String[][]{
			{"sectorLimitId", "java.lang.String", REQUEST_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"indexId", "java.lang.String", REQUEST_SCOPE},
			{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
		};
	}
	
	public String[][] getResultDescriptor() {
		return new String[][]{	
				{"subSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", FORM_SCOPE},
				{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"sectorLimitId", "java.lang.String", REQUEST_SCOPE},
				{"indexId", "java.lang.String", REQUEST_SCOPE},
		};
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
		try
		{
			String event = (String)(map.get("event"));
			
			DefaultLogger.debug(this, "Read sub item event == "+event);
			
			String indexId = (String)(map.get("indexId"));
			
			String sectorLimitId = (String)(map.get("sectorLimitId"));
			
			DefaultLogger.debug(this, "Read sub item indexId == " + indexId);

            IMainSectorLimitParameter sectorLimitObj = (IMainSectorLimitParameter)map.get("mainSectorLimitObj");
            
            ISubSectorLimitParameter subSectorLimitObj  =(ISubSectorLimitParameter)((List)(sectorLimitObj.getSubSectorList())).get(Integer.parseInt(indexId));
            
	        resultMap.put("subSectorLmtForm", subSectorLimitObj);
	        resultMap.put("subSectorLimitObj", subSectorLimitObj);
	        DefaultLogger.debug(this, "********** read sub item is::: " + sectorLimitObj.getSubSectorList());
	     
	        resultMap.put("event", event);
	        
	        resultMap.put("indexId", indexId);
	        
	        resultMap.put("sectorLimitId", sectorLimitId);
		}
		catch(Exception ex)
		{
            throw (new CommandProcessingException(ex.getMessage()));
		}
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
	    return returnMap;
	}	
}
