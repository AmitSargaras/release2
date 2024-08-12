package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;

import java.util.HashMap;

/**
 * @author Erene Wong
 * @since 1 Feb 2010
 */
public class SaveCurWorkingSecEnvelopeCmd extends SecEnvelopeCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
                {"SecurityEnvelope", "com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope", FORM_SCOPE}
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"fromEvent", "java.lang.String", REQUEST_SCOPE}
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {

            ISecEnvelope secEnvelope = (ISecEnvelope) (map.get("SecurityEnvelope"));
            String fromEvent = (String) (map.get("fromEvent"));
            ISecEnvelopeTrxValue SecEnvelopeTrxObj = (ISecEnvelopeTrxValue) (map.get("ISecEnvelopeTrxValue"));
            DefaultLogger.debug("this", "Save cur...getStagingPrIdx : " + SecEnvelopeTrxObj.getStagingSecEnvelope());

            if (SecEnvelopeTrxObj.getStagingSecEnvelope() != null) {
                secEnvelope.setSecEnvelopeItemList(SecEnvelopeTrxObj.getStagingSecEnvelope().getSecEnvelopeItemList());
            }

            SecEnvelopeTrxObj.setStagingSecEnvelope(secEnvelope);
            DefaultLogger.debug("this", "--------->IN SAVE CUR WORKING");
            result.put("ISecEnvelopeTrxValue", SecEnvelopeTrxObj);
        }
        catch (Exception ex) {
            DefaultLogger.error(this, ex);
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
