package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * Author: Syukri
 * Date: Jun 15, 2008
 */
public class ReturnSectorLimitCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
        	{"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"SectorLimitForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        resultMap.put("SectorLimitForm", map.get("mainSectorLimitObj"));

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
