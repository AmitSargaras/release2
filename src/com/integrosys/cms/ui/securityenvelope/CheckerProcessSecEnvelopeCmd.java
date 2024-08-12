package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.app.securityenvelope.trx.OBSecEnvelopeTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import java.util.*;

/**
 * Title: CLIMS
 * Description: Checker to view & prepare to approve/reject the create/update/delete request for Security Envelope
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 3, 2010
 */

public class CheckerProcessSecEnvelopeCmd extends SecEnvelopeCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */

    public CheckerProcessSecEnvelopeCmd() {
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
                {"paramTrxId", "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE}
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
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
                {"SecurityEnvelope", "com.integrosys.cms.ui.securityenvelope.SecEnvelopeForm", FORM_SCOPE},
                {"paramTrxId", "java.lang.String", REQUEST_SCOPE},
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
        DefaultLogger.debug(this, "Inside doExecute()");
        try {
            String paramTrxId = (String) map.get("paramTrxId");
            DefaultLogger.debug(this, "paramTrxId : " + paramTrxId);

            OBSecEnvelope obParam = new OBSecEnvelope();
            ISecEnvelopeTrxValue trxValue = new OBSecEnvelopeTrxValue();

            if (paramTrxId == null && map.containsKey("ISecEnvelopeTrxValue")) {
                trxValue = (ISecEnvelopeTrxValue) map.get("ISecEnvelopeTrxValue");
            } else {
                trxValue = (OBSecEnvelopeTrxValue) getSecEnvelopeProxy().getSecEnvelopeByTrxID(paramTrxId);
                DefaultLogger.debug(this, "paramTrxId is not null");
            }
            obParam = (OBSecEnvelope) trxValue.getStagingSecEnvelope();
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            CommonCodeList commonCode;
            DefaultLogger.debug(this, "process....Sec Envelope :: " + obParam);

            resultMap.put("SecurityEnvelope", obParam);
            resultMap.put("ISecEnvelopeTrxValue", trxValue);
            resultMap.put("paramTrxId", paramTrxId);

        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
          
}



