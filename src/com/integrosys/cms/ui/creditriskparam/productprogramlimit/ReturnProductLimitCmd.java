package com.integrosys.cms.ui.creditriskparam.productprogramlimit;

import java.util.HashMap;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;

/**
 * Author: Priya
 * Date: Oct 5, 2009
 */
public class ReturnProductLimitCmd extends AbstractCommand {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
        	{"productProgramLimitObj", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", SERVICE_SCOPE},
        });

    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
        	{"productProgramLimitForm", "com.integrosys.cms.app.creditriskparam.bus.productlimit.IProductProgramLimitParameter", FORM_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();

        resultMap.put("productProgramLimitForm", map.get("productProgramLimitObj"));

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}
