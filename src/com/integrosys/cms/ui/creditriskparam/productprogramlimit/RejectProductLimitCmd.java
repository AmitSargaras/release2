package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;

import java.util.HashMap;

/**
 * Author: Priya
 * Date: Oct 5, 2009
 */
public class RejectProductLimitCmd extends ProductLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
			{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
			{"productLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue", SERVICE_SCOPE},
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

        ctx.setCustomer(null);
        ctx.setLimitProfile(null);
        if (ctx == null) {
            DefaultLogger.debug(this, "trxContext obtained from map is null.");
        }

        try {
            productLimitTrxObj = getProductLimitProxy().checkerReject(ctx, productLimitTrxObj);
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
