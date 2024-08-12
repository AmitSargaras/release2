package com.integrosys.cms.ui.custgrpi;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue;

import java.util.HashMap;

import org.apache.commons.lang.StringUtils;

public class CreateCustGrpIdentifierCommand extends AbstractCommand {


    public String[][] getParameterDescriptor() {
        return new String[][]{
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
        };
    }

    /**
     * Defines a two dimensional array with the result list to be expected as a
     * result from the doExecute method using a HashMap syntax for the array is
     * (HashMapkey,classname,scope) The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"itemType", "java.lang.String", REQUEST_SCOPE},
                {CustGroupUIHelper.service_groupTrxValue, "com.integrosys.cms.app.custgrpi.trx.ICustGrpIdentifierTrxValue", SERVICE_SCOPE},
        }
        );
    }


    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param inputMap is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap inputMap) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
        DefaultLogger.debug(this, "Inside  doExecute()");
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap returnMap = new HashMap();
        String itemType = (String) inputMap.get("itemType");

        ICustGrpIdentifierTrxValue itrxValue = ((ICustGrpIdentifierTrxValue) inputMap.get(CustGroupUIHelper.service_groupTrxValue));

        resultMap.put("itemType", itemType);
        resultMap.put(CustGroupUIHelper.service_groupTrxValue, itrxValue);
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        DefaultLogger.debug(this, "Existing doExecute()");
        return returnMap;
    }


}
