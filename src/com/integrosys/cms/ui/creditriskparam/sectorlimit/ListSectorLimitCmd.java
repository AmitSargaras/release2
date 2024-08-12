package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.SortUtil;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;

import java.util.HashMap;
import java.util.List;

/**
 * Author: Syukri
 * Author: KC Chin
 * Date: Jun 5, 2008
 */
public class ListSectorLimitCmd extends SectorLimitCommand {

    public String[][] getParameterDescriptor() {
        return (new String[][]{
        		{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
        		{"event", "java.lang.String", REQUEST_SCOPE},
        });
    }

     public String[][] getResultDescriptor() {
        return (new String[][]{
        		{"event", "java.lang.String", REQUEST_SCOPE},
                {"limitList", "[Lcom.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

		String event = (String) map.get("event");
		
        resultMap.put("event", event);

        try {

        	List mainSectorLimits = (List) getSectorLimitProxy().listSectorLimit();
        	if (mainSectorLimits != null && mainSectorLimits.size()> 0) {
        		SortUtil.sortObject((List)mainSectorLimits, new String[] {"LoanPurposeCode"});
        	}

			resultMap.put("limitList", mainSectorLimits);
        } catch (SectorLimitException e) {
            DefaultLogger.error(this, "SectorLimitException caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        } 
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;

    }
}
