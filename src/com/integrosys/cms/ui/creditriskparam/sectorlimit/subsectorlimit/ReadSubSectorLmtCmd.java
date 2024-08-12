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
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.OBSubSectorLimitParameter;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitAction;

public class ReadSubSectorLmtCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"subIndexId", "java.lang.String", REQUEST_SCOPE},
			{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"remarks","java.lang.String",REQUEST_SCOPE },
		});
	}
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"subSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", FORM_SCOPE},
			{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"subIndexId", "java.lang.String", REQUEST_SCOPE},
		});
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
                
        String event = (String)map.get("event");
        String indexId = (String)map.get("subIndexId");
        DefaultLogger.debug(this, "sub event= "+ event );
        DefaultLogger.debug(this, "prepare update index id= "+ indexId );
        
        ISubSectorLimitParameter subSectorLimitObj = null;
        
        if (SectorLimitAction.EVENT_PREPARE.equals(event)) {
        	DefaultLogger.debug(this, "Sub Sector Limit (Prepare)!!! " );
        	subSectorLimitObj = new OBSubSectorLimitParameter();
        	
        } else {
        	DefaultLogger.debug(this, "Sub Sector Limit (Prepare_update)!!! " );
	        
	        IMainSectorLimitParameter sectorLimitObj = (IMainSectorLimitParameter)map.get("mainSectorLimitObj");

	        DefaultLogger.debug(this, "********** B4 subSectorLmtForm IS: " + sectorLimitObj.getSubSectorList());
	        
	        subSectorLimitObj = (ISubSectorLimitParameter)((List) sectorLimitObj.getSubSectorList()).get(Integer.parseInt(indexId));
	        
	        
	        DefaultLogger.debug(this, "********** subSectorLmtForm IS: " + sectorLimitObj.getSubSectorList());
	     
        }
        String remarks = (String)map.get("remarks");
        
        resultMap.put("subSectorLmtForm", subSectorLimitObj);
        resultMap.put("subSectorLimitObj", subSectorLimitObj);
        
        resultMap.put("event", event);
        resultMap.put("remarks", remarks);
        resultMap.put("subIndexId", indexId);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
        
	}
}
