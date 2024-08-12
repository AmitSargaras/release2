package com.integrosys.cms.ui.creditriskparam.producttypelimit;

import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.OBProductTypeLimitParameter;
import com.integrosys.cms.ui.creditriskparam.productprogramlimit.ProductProgramLimitAction;

public class ReadProductLmtCmd extends AbstractCommand {
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{"indexID", "java.lang.String", REQUEST_SCOPE},
				{"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"remarks","java.lang.String",REQUEST_SCOPE },
		});
	}
	public String[][] getResultDescriptor() {
		return (new String[][]{
				{"productTypeLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductTypeLimitParameter", FORM_SCOPE},
				{"remarks","java.lang.String",SERVICE_SCOPE },
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"indexID", "java.lang.String", REQUEST_SCOPE},
	    });
	}
	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
                
        String event = (String)map.get("event");
        String indexID = (String)map.get("indexID");
        
        if (ProductProgramLimitAction.EVENT_PREPARE.equals(event)) {
        	resultMap.put("productTypeLimitForm", new OBProductTypeLimitParameter());
        } else {    	
        	IProductProgramLimitParameter productLimitObj = (IProductProgramLimitParameter)map.get("productProgramLimitObj");
	        resultMap.put("productTypeLimitForm", ((List) productLimitObj.getProductTypeList()).get(Integer.parseInt(indexID)));	        
        }
        
        String remarks = (String)map.get("remarks");
        
        resultMap.put("event", event);
        resultMap.put("remarks", remarks);
        resultMap.put("indexID", indexID);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;	        
	}
	
}
