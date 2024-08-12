package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.app.securityenvelope.trx.OBSecEnvelopeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;

import java.util.HashMap;

/**
 * Title: CLIMS
 * Description: for Checker to approve the new created Security Envelope
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 3, 2010
 */

public class CheckerApproveCreateSecEnvelopeCmd extends SecEnvelopeCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public CheckerApproveCreateSecEnvelopeCmd() {
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
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
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
            ISecEnvelopeTrxValue trxValueIn = (OBSecEnvelopeTrxValue) map.get("ISecEnvelopeTrxValue");
            String remarks = (String) map.get("remarks");
            ctx.setRemarks(remarks);

            ISecEnvelope secEnvelope = trxValueIn.getStagingSecEnvelope();
                DefaultLogger.debug(this, "Current version time " + trxValueIn.getVersionTime());
                ISecEnvelopeTrxValue trxValueOut = getSecEnvelopeProxy().checkerApproveSecEnvelope(ctx, trxValueIn);
                resultMap.put("request.ITrxValue", trxValueOut);
                DefaultLogger.debug(this, "ISecEnvelopeTrxValue after approve " + trxValueOut);

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



