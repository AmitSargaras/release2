package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.sectorlimit.SectorLimitException;
import com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.HashMap;

/**
 * Author: Syukri
 * Author: KC Chin
 * Date: Jun 17, 2008
 */
public class RejectSectorLimitCmd extends SectorLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{"sectorLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue", SERVICE_SCOPE},
        });
    }

     public String[][] getResultDescriptor() {
        return (new String[][]{
			{"request.ITrxValue", "com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        ITrxContext ctx = (ITrxContext)map.get("theOBTrxContext");
        ISectorLimitParameterTrxValue sectorLimitTrxObj = (ISectorLimitParameterTrxValue) map.get("sectorLimitTrxObj");

        ctx.setCustomer(null);
        ctx.setLimitProfile(null);
        if (ctx == null) {
            DefaultLogger.debug(this, "trxContext obtained from map is null.");
        }

        try {
            sectorLimitTrxObj = getSectorLimitProxy().checkerReject(ctx, sectorLimitTrxObj);
            
            DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>sectorLimitTrxObj.getTransactionID: " + sectorLimitTrxObj.getTransactionID());
            DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>sectorLimitTrxObj.getTransactionHistoryID: " + sectorLimitTrxObj.getTransactionHistoryID());
            DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>sectorLimitTrxObj.getRemarks: " + sectorLimitTrxObj.getRemarks());
            DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>sectorLimitTrxObj.getReferenceID: " + sectorLimitTrxObj.getReferenceID());
            DefaultLogger.debug(this, ">>>>>>>>>>>>>>>>>>>>>>>>>>>sectorLimitTrxObj.getStagingReferenceID: " + sectorLimitTrxObj.getStagingReferenceID());

            resultMap.put("request.ITrxValue", sectorLimitTrxObj);
        } catch (SectorLimitException e) {
            DefaultLogger.error(this, "SectorLimitException caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
