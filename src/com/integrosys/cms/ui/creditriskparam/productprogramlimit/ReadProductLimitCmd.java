package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;

public class ReadProductLimitCmd extends ProductLimitCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
			{"event", "java.lang.String", REQUEST_SCOPE},
			{"productLimitId", "java.lang.String", REQUEST_SCOPE},
	    });
	}	        		

	public String[][] getResultDescriptor() {
		return (new String[][]{
			{"productProgramLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", FORM_SCOPE},
            {"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
            {"productLimitId", "java.lang.String", REQUEST_SCOPE},
            {"event", "java.lang.String", REQUEST_SCOPE},
	    });
	}
	    
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        
        String productLimitId = (String)map.get("productLimitId");
		String event = (String)(map.get("event"));
		
        try {
        	IProductProgramLimitParameter productObj = (IProductProgramLimitParameter)getProductLimitProxy().getProductLimitById(Long.parseLong(productLimitId));
        	
        	resultMap.put("productLimitId", String.valueOf(productObj.getId()));
            resultMap.put("productProgramLimitForm", productObj);
            resultMap.put("productProgramLimitObj", productObj);
            resultMap.put("event", event);
            
        } catch (Exception e) {
        	throw new CommandProcessingException (e.toString());
        }
        
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
    }	    
}
