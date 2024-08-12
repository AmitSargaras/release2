package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.proxy.ISecEnvelopeProxyManager;
import com.integrosys.cms.app.securityenvelope.proxy.SecEnvelopeProxyManagerFactory;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

import org.apache.struts.action.ActionMessage;

/**
 * @author Erene Wong
 * @since 21 Feb 2010
 */
public class DeleteSecEnvelopeItemCmd extends SecEnvelopeCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"SecurityEnvelope", "com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope", FORM_SCOPE}
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"SecurityEnvelope", "com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope", FORM_SCOPE},
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

            ArrayList itemList = new ArrayList(secEnvelope.getSecEnvelopeItemList());
            OBSecEnvelopeItem[] item = (OBSecEnvelopeItem[]) itemList.toArray(new OBSecEnvelopeItem[itemList.size()]);
            String[] deletedInd = secEnvelope.getDeletedItemList();

            if (item != null && deletedInd != null && deletedInd.length > 0) {
                List fullList = new ArrayList();
                for (int i = 0; i < item.length; i++) {
                    fullList.add(item[i]);
                }

                if (fullList != null && deletedInd != null) {
                    List remList = new ArrayList();
                    boolean hasDocItemInEnvelope = true;
                    for (int i = 0; i < deletedInd.length; i++) {
                        int nextDelInd = Integer.parseInt(deletedInd[i]);
                        ISecEnvelopeItem secenvitem = (ISecEnvelopeItem) fullList.get(nextDelInd);
                        hasDocItemInEnvelope =  checkHasDocItemInEnvelope(secenvitem.getSecEnvelopeItemBarcode());
                        if (!hasDocItemInEnvelope){
                            remList.add(fullList.get(nextDelInd));
                        }else{
                            exceptionMap.put("envDocItemExist", new ActionMessage("error.security.envelope.docItemExist"));
                        }
                    }
                    if(exceptionMap.size() == 0){
                        fullList.removeAll(remList);
                        remList.clear();
                    }
                }
                secEnvelope.setSecEnvelopeItemList(new HashSet(fullList));
            }
            result.put("SecurityEnvelope", secEnvelope);
            result.put("fromEvent",fromEvent);
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    public boolean checkHasDocItemInEnvelope(String envBarcode){
        int totDocItems = 0;
        boolean hasDocItemInEnvelope = false;
        ISecEnvelopeProxyManager proxy = SecEnvelopeProxyManagerFactory.getSecEnvelopeProxyManger();
        totDocItems = proxy.getNumDocItemInEnvelope(envBarcode);
        if (totDocItems > 0){
            hasDocItemInEnvelope = true;
        }
        return hasDocItemInEnvelope;
   }
}
