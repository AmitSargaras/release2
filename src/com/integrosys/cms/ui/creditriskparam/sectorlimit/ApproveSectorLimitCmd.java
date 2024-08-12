package com.integrosys.cms.ui.creditriskparam.sectorlimit;

import com.integrosys.base.businfra.transaction.ITrxValue;
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
 * Date: Jun 16, 2008
 */
public class ApproveSectorLimitCmd extends SectorLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{"sectorLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.sectorlimit.ISectorLimitParameterTrxValue", SERVICE_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"trxId", "java.lang.String", REQUEST_SCOPE}
			
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
        
        ITrxValue iTrxVal = (ITrxValue)map.get("sectorLimitTrxObj");
        String status = iTrxVal.getStatus();
        DefaultLogger.debug(this, "status in arrove cmd" + status);
                     
        ctx.setCustomer(null);
        ctx.setLimitProfile(null);
        if (ctx == null) {
            DefaultLogger.debug(this, "trxContext obtained from map is null.");
        }
        
        try {
        	if((status.equals("PENDING_DELETE")) || (status.equals("PENDING_UPDATE"))) {
        		sectorLimitTrxObj = getSectorLimitProxy().checkerApprove(ctx, sectorLimitTrxObj);
        	}else if((status.equals("PENDING_CREATE"))) {
            sectorLimitTrxObj = getSectorLimitProxy().checkerApprove(ctx, sectorLimitTrxObj);
        	}
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
