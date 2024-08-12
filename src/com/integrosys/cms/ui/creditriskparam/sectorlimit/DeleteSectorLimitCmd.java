package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IEcoSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.ISubSectorLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitUtils;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * Author: Syukri
 * Author: KC Chin
 * Date: Jun 16, 2008
 */
public class DeleteSectorLimitCmd extends SectorLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"sectorLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue", SERVICE_SCOPE},
            {"mainSectorLimitObj", "com.integrosys.cms.app.creditriskparam.bus.sectorlimit.IMainSectorLimitParameter", SERVICE_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
            {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {
            ITrxContext ctx = (ITrxContext)map.get("theOBTrxContext");
            ISectorLimitParameterTrxValue sectorLimitTrxObj = (ISectorLimitParameterTrxValue) map.get("sectorLimitTrxObj");

            IMainSectorLimitParameter obj = (IMainSectorLimitParameter)map.get("mainSectorLimitObj");
            obj.setStatus(ICMSConstant.STATE_DELETED);
            obj = SectorLimitUtils.setAllSubSectorStatus(obj, ICMSConstant.STATE_DELETED);
            
            sectorLimitTrxObj.setStagingMainSectorLimitParameter(obj);
            
            ISectorLimitParameterTrxValue trxValue = getSectorLimitProxy().makerDelete(ctx, sectorLimitTrxObj);
            resultMap.put("request.ITrxValue", trxValue);

            returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
            returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        return returnMap;
    }
}
