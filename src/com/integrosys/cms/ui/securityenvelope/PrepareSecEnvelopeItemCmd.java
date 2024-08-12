package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import com.integrosys.cms.app.securityenvelope.trx.OBSecEnvelopeTrxValue;
import com.integrosys.cms.ui.common.CommonCodeList;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * Modified with IntelliJ IDEA.
 * User: Erene Wong
 * Date: Feb 1, 2010
 * Time: 11:59:05 AM
 *
 */
public class PrepareSecEnvelopeItemCmd extends SecEnvelopeCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"SecEnvelopeItemForm", "com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem", FORM_SCOPE},
                {"secEnvelopeItemId", "java.lang.String", REQUEST_SCOPE},
                {"indId","java.lang.String", REQUEST_SCOPE},
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE}
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"SecEnvelopeItemForm", "com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem", FORM_SCOPE},
                {"secEnvelopeItemAddrLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"secEnvelopeItemAddrValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"secEnvelopeItemCabLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"secEnvelopeItemCabValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"secEnvelopeItemDrwLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"secEnvelopeItemDrwValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        CommonCodeList commonCode;
        OBSecEnvelopeItem obSecEnvItem = new OBSecEnvelopeItem();
        OBSecEnvelopeItem obTmpSecEnvItem = new OBSecEnvelopeItem();

        try {
            String event = (String) (map.get("event"));
            String fromEvent = (String) (map.get("fromEvent"));
            String secEnvelopeItemId = (String) map.get("secEnvelopeItemId");
            String indId = (String) map.get("indId");

            ISecEnvelopeTrxValue trxValue = (ISecEnvelopeTrxValue) map.get("ISecEnvelopeTrxValue");
            ISecEnvelopeTrxValue secEnvelopeTrxValue = new OBSecEnvelopeTrxValue();
            Set editItmList = null;
            ISecEnvelopeItem curSecEnvelopeItem = new OBSecEnvelopeItem();

            if(StringUtils.isNotEmpty(indId) || StringUtils.isNotEmpty(secEnvelopeItemId)){
                if (trxValue == null){
                    editItmList = (Set) secEnvelopeTrxValue.getStagingSecEnvelope().getSecEnvelopeItemList();
                }else{
                    editItmList = (Set) trxValue.getStagingSecEnvelope().getSecEnvelopeItemList();
                }
            }

            if(StringUtils.isNotEmpty(secEnvelopeItemId)){
                Iterator itemIter = editItmList.iterator();
                while(itemIter.hasNext()){
                     obTmpSecEnvItem = (OBSecEnvelopeItem)itemIter.next();
                     if(new Long(obTmpSecEnvItem.getSecEnvelopeItemId()).compareTo(new Long(secEnvelopeItemId)) == 0){
                        obSecEnvItem = obTmpSecEnvItem;
                     }
                }
            }

            if (StringUtils.isNotEmpty(indId)){
                int indIdint = Integer.parseInt(indId);
                Iterator itemIter = editItmList.iterator();
                for (int i=0; i<=indIdint; i++){
                    obTmpSecEnvItem = (OBSecEnvelopeItem)itemIter.next();
                    obSecEnvItem = obTmpSecEnvItem;
                }
            }

            if (map.containsKey("SecEnvelopeItemForm") && (StringUtils.isNotEmpty(secEnvelopeItemId) || StringUtils.isNotEmpty(indId))) {
                result.put("SecEnvelopeItemForm", obSecEnvItem);
            }

			commonCode = CommonCodeList.getInstance(ICMSConstant.SEC_ENVELOPE_ADDRESS);
			result.put("secEnvelopeItemAddrLabel", commonCode.getCommonCodeLabels());
			result.put("secEnvelopeItemAddrValue", commonCode.getCommonCodeValues());
            commonCode = CommonCodeList.getInstance(ICMSConstant.SEC_ENVELOPE_CABINET);
			result.put("secEnvelopeItemCabLabel", commonCode.getCommonCodeLabels());
			result.put("secEnvelopeItemCabValue", commonCode.getCommonCodeValues());
            commonCode = CommonCodeList.getInstance(ICMSConstant.SEC_ENVELOPE_DRAWER);
			result.put("secEnvelopeItemDrwLabel", commonCode.getCommonCodeLabels());
			result.put("secEnvelopeItemDrwValue", commonCode.getCommonCodeValues());
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

}
