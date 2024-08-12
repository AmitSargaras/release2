package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue;
import com.integrosys.cms.app.transaction.ITrxContext;


/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public class DeleteProductLimitCmd extends ProductLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
            {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
            {"productLimitTrxObj", "com.integrosys.cms.app.creditriskparam.trx.productlimit.IProductLimitParameterTrxValue", SERVICE_SCOPE},
            {"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
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
        	IProductLimitParameterTrxValue productLimitTrxObj = (IProductLimitParameterTrxValue) map.get("productLimitTrxObj");
        	
        	IProductProgramLimitParameter obj = (IProductProgramLimitParameter)map.get("productProgramLimitObj");
            obj.setStatus(ICMSConstant.STATE_DELETED);
            
            productLimitTrxObj.setStagingProductProgramLimitParameter(obj);
        	
            IProductLimitParameterTrxValue trxValue = getProductLimitProxy().makerDelete(ctx, productLimitTrxObj);
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
