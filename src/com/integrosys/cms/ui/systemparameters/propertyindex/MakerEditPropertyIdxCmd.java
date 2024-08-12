package com.integrosys.cms.ui.systemparameters.propertyindex;


import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.app.propertyindex.trx.OBPropertyIdxTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;

/**
 * Title: CLIMS
 * Description: for Maker to update existing Property Index
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Feb 12, 2008
 */

public class MakerEditPropertyIdxCmd extends PropertyIdxCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public MakerEditPropertyIdxCmd() {
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
                {"PropertyIndex", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx", FORM_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"remarks", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
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
        try {
            OBPropertyIdx propertyIdx = (OBPropertyIdx) map.get("PropertyIndex");
            String event = (String) map.get("event");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            IPropertyIdxTrxValue trxValueIn = (OBPropertyIdxTrxValue) map.get("IPropertyIdxTrxValue");

            IPropertyIdxTrxValue trxValueOut = new OBPropertyIdxTrxValue();
            if (!(trxValueIn != null &&
                    trxValueIn.getStagingPrIdx() != null &&
                    trxValueIn.getStagingPrIdx().getPropertyIdxItemList() != null &&
                    trxValueIn.getStagingPrIdx().getPropertyIdxItemList().size() > 0)) {
                exceptionMap.put("itemEmptyError", new ActionMessage("error.property.item"));
            }

            if (exceptionMap.size() == 0) {
                if (event.equals("maker_confirm_edit")) {
                    // 'maker_confirm_edit'
                    trxValueOut = getPropertyIdxProxy().makerUpdatePropertyIdx(ctx, trxValueIn, trxValueIn.getStagingPrIdx());
                } else {
                    // 'maker_confirm_resubmit_edit'
                    String remarks = (String) map.get("remarks");
                    ctx.setRemarks(remarks);
                    trxValueOut = getPropertyIdxProxy().makerEditRejectedPropertyIdx(ctx, trxValueIn, trxValueIn.getStagingPrIdx());
                }
                resultMap.put("request.ITrxValue", trxValueOut);
            }
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }
}



