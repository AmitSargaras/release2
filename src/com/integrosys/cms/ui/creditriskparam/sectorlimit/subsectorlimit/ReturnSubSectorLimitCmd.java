package com.integrosys.cms.ui.creditriskparam.sectorlimit.subsectorlimit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;

public class ReturnSubSectorLimitCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
        	{"subSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", SERVICE_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"subSectorLmtForm", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        ISubSectorLimitParameter sectorLimitObj = (ISubSectorLimitParameter)map.get("subSectorLimitObj");
        DefaultLogger.debug(this, "SectorLimit return frm eco = " + sectorLimitObj.getEcoSectorList());
        resultMap.put("subSectorLmtForm", sectorLimitObj);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
