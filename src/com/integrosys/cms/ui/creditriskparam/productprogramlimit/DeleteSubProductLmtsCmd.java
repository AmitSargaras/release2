package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter;

/**
 * Author: Priya
 * Date: Oct 9, 2009
 */
public class DeleteSubProductLmtsCmd extends AbstractCommand {
	 public String[][] getParameterDescriptor() {
	        return (new String[][]{
	            {"productProgramLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", FORM_SCOPE},
	        });

	    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
        	{"productProgramLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        try {
        	IProductProgramLimitParameter obj = (IProductProgramLimitParameter)map.get("productProgramLimitForm"); 
            
            DefaultLogger.debug(this, "productProgramLimitObj = " + obj.getProductTypeList());
            resultMap.put("productProgramLimitObj", obj);
            resultMap.put("productProgramLimitForm", obj);

            returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
            returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        } catch (Exception e) {
            DefaultLogger.error(this, "Exception caught in doExecute()", e);
            exceptionMap.put("application.exception", e);
        }

        return returnMap;
    }
}
