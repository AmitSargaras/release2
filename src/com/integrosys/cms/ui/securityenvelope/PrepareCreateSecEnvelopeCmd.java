package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.trx.OBSecEnvelopeTrxValue;

import java.util.HashMap;

/**
 * @author Erene Wong
 * @since 30 Jan 2010
 */
public class PrepareCreateSecEnvelopeCmd extends SecEnvelopeCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.OBSecEnvelopeTrxValue", SERVICE_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        try {
            OBSecEnvelopeTrxValue SecEnvelopeTrxObj = new OBSecEnvelopeTrxValue();
            result.put("ISecEnvelopeTrxValue", SecEnvelopeTrxObj);
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }
}
