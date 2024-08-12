package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.util.SortUtil;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.ProductLimitException;



/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public class ListProductLimitCmd extends ProductLimitCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
        		{"event", "java.lang.String", REQUEST_SCOPE}
        });
    }

     public String[][] getResultDescriptor() {
        return (new String[][]{
        		{"event", "java.lang.String", REQUEST_SCOPE},
                {"limitList", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", REQUEST_SCOPE},      
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        String event = (String) map.get("event");
		
        resultMap.put("event", event);
			
        try {

        	List productProgramLimits = (List)getProductLimitProxy().listProductLimit();
        	if (productProgramLimits != null && productProgramLimits.size()> 0) {
        		SortUtil.sortObject((List)productProgramLimits, new String[] {"ProductProgramDesc"});
        	}
			resultMap.put("limitList", productProgramLimits);
        } catch (ProductLimitException e) {
            DefaultLogger.error(this, "ProductLimitException caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        } 
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;

    }
}
