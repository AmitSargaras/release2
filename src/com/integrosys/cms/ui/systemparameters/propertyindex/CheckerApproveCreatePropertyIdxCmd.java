package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.proxy.IPropertyIdxProxyManager;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.app.propertyindex.trx.OBPropertyIdxTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;
import java.util.List;

/**
 * Title: CLIMS
 * Description: for Checker to approve the new created Property Index
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 30, 2008
 */

public class CheckerApproveCreatePropertyIdxCmd extends PropertyIdxCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public CheckerApproveCreatePropertyIdxCmd() {
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE}
        }
        );
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE}
        }
        );
    }

    /**
     * This method does the Business operations  with the HashMap and put the results back into
     * the HashMap.
     *
     * @param map is of type HashMap
     * @return HashMap with the Result
     */
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        HashMap exceptionMap = new HashMap();
        DefaultLogger.debug(this, "Inside doExecute()");
        try {

            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            IPropertyIdxTrxValue trxValueIn = (OBPropertyIdxTrxValue) map.get("IPropertyIdxTrxValue");
            String remarks = (String) map.get("remarks");
            ctx.setRemarks(remarks);

            IPropertyIdx propertyIdx = trxValueIn.getStagingPrIdx();
            boolean isExist = getPropertyIdxProxy().isSecSubTypeValTypeExist(propertyIdx.getPropertyIdxId(), propertyIdx.getPropertyIdxSecSubTypeList(), propertyIdx.getValDescr());
            if (isExist) {
                exceptionMap.put("secsubtypeValdescExistError", new ActionMessage("error.property.secsubtype.valdesc.exist"));
            }
            if (exceptionMap.size() == 0) {
                IPropertyIdxTrxValue trxValueOut = getPropertyIdxProxy().checkerApprovePropertyIdx(ctx, trxValueIn);
                resultMap.put("request.ITrxValue", trxValueOut);
                DefaultLogger.debug(this, "IPropertyIdxTrxValue after approve " + trxValueOut);
            }

        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

        return returnMap;
    }
}



