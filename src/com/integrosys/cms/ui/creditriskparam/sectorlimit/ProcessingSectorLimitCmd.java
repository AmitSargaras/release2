package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;

/**
 * Author: Syukri
 * Date: Jun 16, 2008
 */
public class ProcessingSectorLimitCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"SectorLimitForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", FORM_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {           
            IMainSectorLimitParameter obj = (IMainSectorLimitParameter)map.get("SectorLimitForm");            
            
            resultMap.put("mainSectorLimitObj", obj);

            returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
            returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        return returnMap;
    }
}
