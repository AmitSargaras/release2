package com.integrosys.cms.ui.creditriskparam.sectorlimit.econsectorlimit;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.ui.creditriskparam.sectorlimit.SectorLimitAction;

import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.OBEcoSectorLimitParameter;

public class ReadEconSectorLmtCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"ecoIndexId", "java.lang.String", REQUEST_SCOPE},
			{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"remarks","java.lang.String",REQUEST_SCOPE },
		});
	}
	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"econSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter", FORM_SCOPE},
			{"remarks","java.lang.String",SERVICE_SCOPE },
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"ecoIndexId", "java.lang.String", REQUEST_SCOPE},
	    });
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
                
        String event = (String)map.get("event");
        String indexId = (String)map.get("ecoIndexId");
        DefaultLogger.debug(this, "sub event= "+ event );
        DefaultLogger.debug(this, "prepare update index id= "+ indexId );
        
        if (SectorLimitAction.EVENT_PREPARE.equals(event)) {
        	DefaultLogger.debug(this, "Eco Sector Limit (Prepare)!!! " );
        	resultMap.put("econSectorLmtForm", new OBEcoSectorLimitParameter());
        } else {
        	DefaultLogger.debug(this, "Eco Sector Limit (Prepare_update)!!! " );
	        ISubSectorLimitParameter sectorLimitObj = (ISubSectorLimitParameter)map.get("subSectorLimitObj");
	        
	        DefaultLogger.debug(this, "********** B4 sectorLimitObj IS: " + sectorLimitObj);
	        DefaultLogger.debug(this, "********** B4 EcoSectorLmtForm IS: " + sectorLimitObj.getEcoSectorList());
	        resultMap.put("econSectorLmtForm", ((List) sectorLimitObj.getEcoSectorList()).get(Integer.parseInt(indexId)));
	        DefaultLogger.debug(this, "********** EcoSectorLmtForm IS: " + sectorLimitObj.getEcoSectorList());
        }
        String remarks = (String)map.get("remarks");
        
        resultMap.put("event", event);
        resultMap.put("remarks", remarks);
        resultMap.put("ecoIndexId", indexId);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
        
	}
}
