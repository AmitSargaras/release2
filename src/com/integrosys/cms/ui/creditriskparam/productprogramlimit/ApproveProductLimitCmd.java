package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import com.integrosys.base.businfra.transaction.ITrxValue;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;
import com.integrosys.cms.app.transaction.ITrxContext;

import java.util.HashMap;


/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public class ApproveProductLimitCmd extends ProductLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{"productLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue", SERVICE_SCOPE},
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"trxId", "java.lang.String", REQUEST_SCOPE}
			
        });
    }

     public String[][] getResultDescriptor() {
        return (new String[][]{
			{"request.ITrxValue", "com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        ITrxContext ctx = (ITrxContext)map.get("theOBTrxContext");
        IProductLimitParameterTrxValue productLimitTrxObj = (IProductLimitParameterTrxValue) map.get("productLimitTrxObj");
        
        ITrxValue iTrxVal = (ITrxValue)map.get("productLimitTrxObj");
        String status = iTrxVal.getStatus();
        DefaultLogger.debug(this, "status in approve cmd" + status);
                     
        ctx.setCustomer(null);
        ctx.setLimitProfile(null);
        if (ctx == null) {
            DefaultLogger.debug(this, "trxContext obtained from map is null.");
        }
        
        try {
        	if((status.equals("PENDING_DELETE")) || (status.equals("PENDING_UPDATE"))) {
        		productLimitTrxObj = getProductLimitProxy().checkerApprove(ctx, productLimitTrxObj);
        	}else if((status.equals("PENDING_CREATE"))) {
        		productLimitTrxObj = getProductLimitProxy().checkerApprove(ctx, productLimitTrxObj);
        	}
        	
            resultMap.put("request.ITrxValue", productLimitTrxObj);
        } catch (ProductLimitException e) {
            DefaultLogger.error(this, "ProductLimitException caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
