package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import java.util.*;

/**
 * Title: CLIMS
 * Description: for Maker to view the list of Security Envelope By Loc
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Erene Wong
 * Date: Feb 5, 2010
 */

public class ListSecEnvelopeByLocCmd extends SecEnvelopeCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public ListSecEnvelopeByLocCmd() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"lspLmtProfileId", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE}
        });
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
                {"secEnvelopeId", "java.lang.String", REQUEST_SCOPE},
                {"SecurityEnvelope", "com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {"secEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE}
        });
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
        try {
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            String event = (String) map.get("event");
            String lspLmtProfileIdStr = (String) map.get("lspLmtProfileId");
            long lspLmtProfileId = Long.parseLong(lspLmtProfileIdStr);
            ArrayList secEnvelopeList = new ArrayList();
            String secEnvId = null;
            ISecEnvelopeTrxValue secEnvelopeTrxObj;
            ISecEnvelopeTrxValue trxValue = null;

            secEnvelopeList = (ArrayList) getSecEnvelopeProxy().getAllActual();
            secEnvelopeTrxObj = (ISecEnvelopeTrxValue) getSecEnvelopeProxy().getActualMasterSecEnvelope(lspLmtProfileId);


            if(secEnvelopeTrxObj.getSecEnvelope() != null){
                secEnvId = String.valueOf(secEnvelopeTrxObj.getSecEnvelope().getSecEnvelopeId());
                if(!"checker_view_sec_envelope".equals(event)){
                    resultMap.put("event", "maker_prepare_edit");
                }    
            }else{
                resultMap.put("event", "maker_prepare_create");
            }
            
            resultMap.put("secEnvelopeId", secEnvId);
            resultMap.put("secEnvelopeTrxValue", secEnvelopeTrxObj);
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
    
     private ISecEnvelope getSecEnvelope(ISecEnvelopeTrxValue trxValue, String event) {
        if (trxValue == null) {
            return new OBSecEnvelope();
        }
        if ("read".equals(event)) {
            return trxValue.getSecEnvelope();
        } else {
            ISecEnvelope curTemplate = trxValue.getStagingSecEnvelope();
            if (curTemplate == null) {
                curTemplate = new OBSecEnvelope();
            }
            return curTemplate;
        }
    }
}



