package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.app.transaction.OBTrxContext;
import org.apache.struts.action.ActionMessage;

import java.util.HashMap;

/**
 * @author Erene Wong
 * @since 2 Feb 2010
 */
public class MakerCreateSecEnvelopeCmd extends SecEnvelopeCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
                {"SecurityEnvelope", "com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope", FORM_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE}
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"request.ITrxValue", "com.integrosys.cms.app.transaction.ICMSTrxValue", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE}
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {
            OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
            ISecEnvelopeTrxValue SecEnvelopeTrxObj = (ISecEnvelopeTrxValue) map.get("ISecEnvelopeTrxValue");
            ISecEnvelope envelope = (ISecEnvelope) (map.get("SecurityEnvelope"));
            String fromEvent = (String) map.get("fromEvent");

            //To enable user to submit empty envelope item to checker
            /* if (!(SecEnvelopeTrxObj != null &&
                    SecEnvelopeTrxObj.getStagingSecEnvelope() != null &&
                    SecEnvelopeTrxObj.getStagingSecEnvelope().getSecEnvelopeItemList() != null &&
                    SecEnvelopeTrxObj.getStagingSecEnvelope().getSecEnvelopeItemList().size() > 0)) {
                DefaultLogger.debug(this, "In inside condition.........");
                exceptionMap.put("itemEmptyError", new ActionMessage("error.security.item"));
            } */
            envelope.setSecEnvelopeItemList(SecEnvelopeTrxObj.getStagingSecEnvelope().getSecEnvelopeItemList());
            ISecEnvelopeTrxValue trxValue = getSecEnvelopeProxy().makerCreateSecEnvelope(ctx, envelope);
            result.put("request.ITrxValue", trxValue);
            result.put("fromEvent",fromEvent);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
