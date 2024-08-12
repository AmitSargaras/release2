package com.integrosys.cms.ui.securityenvelope;


import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.app.securityenvelope.trx.OBSecEnvelopeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;

/**
 * Title: CLIMS
 * Description: for Maker to update existing Security Envelope
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 06, 2010
 */

public class MakerEditSecEnvelopeCmd extends SecEnvelopeCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public MakerEditSecEnvelopeCmd() {
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
                {"SecurityEnvelope", "com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope", FORM_SCOPE},
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
            OBSecEnvelope secEnvelope = (OBSecEnvelope) map.get("SecurityEnvelope");
            String event = (String) map.get("event");
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            ISecEnvelopeTrxValue trxValueIn = (OBSecEnvelopeTrxValue) map.get("ISecEnvelopeTrxValue");

            ISecEnvelopeTrxValue trxValueOut = new OBSecEnvelopeTrxValue();

            /* to enable user to submit for checker apprval with empty envelope item list
            if (!(trxValueIn != null &&
                    trxValueIn.getStagingSecEnvelope() != null &&
                    trxValueIn.getStagingSecEnvelope().getSecEnvelopeItemList() != null &&
                    trxValueIn.getStagingSecEnvelope().getSecEnvelopeItemList().size() > 0)) {
                exceptionMap.put("itemEmptyError", new ActionMessage("error.security.item"));
            } */
            if (event.equals("maker_confirm_edit")) {
                  // 'maker_confirm_edit'
                  trxValueOut = getSecEnvelopeProxy().makerUpdateSecEnvelope(ctx, trxValueIn, trxValueIn.getStagingSecEnvelope());
            } else {
                  // 'maker_confirm_resubmit_edit'
                  String remarks = (String) map.get("remarks");
                  ctx.setRemarks(remarks);
                  trxValueOut = getSecEnvelopeProxy().makerEditRejectedSecEnvelope(ctx, trxValueIn, trxValueIn.getStagingSecEnvelope());
            }
            resultMap.put("request.ITrxValue", trxValueOut);
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
    }
}



