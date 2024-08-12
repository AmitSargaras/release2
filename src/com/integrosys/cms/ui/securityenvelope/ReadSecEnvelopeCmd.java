package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.trx.OBSecEnvelopeTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

import java.util.*;

/*
 * Title: CLIMS 
 * Description: Standard read security envelope command
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Erene Wong
 * Date: Feb 06, 2010
 */

public class ReadSecEnvelopeCmd extends SecEnvelopeCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"secEnvelopeId", "java.lang.String", REQUEST_SCOPE},
                {"secEnvelopeItemId", "java.lang.String", REQUEST_SCOPE},
                {"paramTrxId", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
                {"SecurityEnvelope", "com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelope", FORM_SCOPE},
                {"wip", "java.lang.String", REQUEST_SCOPE},
                {"closeFlag", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE}
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try {
            String event = (String) map.get("event");
            ISecEnvelope obParam = new OBSecEnvelope();
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            ISecEnvelopeTrxValue trxValue = null;

            OBSecEnvelopeItem obItem = new OBSecEnvelopeItem();
            OBSecEnvelopeItem tempItem = new OBSecEnvelopeItem();

            String trxId = (String) map.get("paramTrxId");
            String secEnvelopeId = (String) (map.get("secEnvelopeId"));

           if ("maker_prepare_close".equals(event) ||                                    
                    "to_track".equals(event) ||
                    "maker_prepare_resubmit".equals(event)) {
                if (trxId == null) {
                    trxValue = (ISecEnvelopeTrxValue) map.get("ISecEnvelopeTrxValue");
                } else {
                    trxValue = (OBSecEnvelopeTrxValue) getSecEnvelopeProxy().getSecEnvelopeByTrxID(trxId);
                }
                result.put("fromEvent", "todo");

                if ("maker_prepare_close".equals(event))
                    result.put("closeFlag", "true");

            } else {
                if (secEnvelopeId == null) {
                    OBSecEnvelopeTrxValue SecEnvelopeTrxObj = new OBSecEnvelopeTrxValue();
                    trxValue =  SecEnvelopeTrxObj;
                    result.put("fromEvent", "add");
                } else {
                    trxValue = (OBSecEnvelopeTrxValue) getSecEnvelopeProxy().getSecEnvelopeTrxValue(Long.parseLong(secEnvelopeId));
                    obParam = (OBSecEnvelope) trxValue.getSecEnvelope();
                    result.put("fromEvent", "edit");
                }
            }

            if (!"checker_view_sec_envelope".equals(event)){
                if (UIUtil.checkWip(event, trxValue)) {
                    result.put("wip", "wip");
                }
                if (UIUtil.checkDeleteWip(event, trxValue)) {
                    result.put("wip", "wip");
                }
            }

           if ((trxValue != null) && "maker_prepare_edit".equals(event)) {
            ISecEnvelope stgEnvelope = new OBSecEnvelope();
            stgEnvelope = trxValue.getSecEnvelope();
            trxValue.setStagingSecEnvelope(stgEnvelope);
           }
           if ("view_property_index".equals(event) || ("checker_view_sec_envelope".equals(event) && trxValue != null)) {
            obParam = trxValue.getSecEnvelope();
           } else {
            obParam = trxValue.getStagingSecEnvelope();
           }
           
            result.put("SecurityEnvelope", obParam);
            result.put("ISecEnvelopeTrxValue", trxValue);


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
