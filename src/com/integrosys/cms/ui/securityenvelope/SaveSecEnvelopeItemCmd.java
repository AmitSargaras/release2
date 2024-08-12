package com.integrosys.cms.ui.securityenvelope;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelope;
import com.integrosys.cms.app.securityenvelope.bus.ISecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem;
import com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue;
import org.apache.struts.action.ActionMessage;

import java.util.*;

/**
 * @author Erene Wong
 * @since 31 Jan 2010
 */
public class SaveSecEnvelopeItemCmd extends SecEnvelopeCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"SecEnvelopeItemForm", "com.integrosys.cms.app.securityenvelope.bus.OBSecEnvelopeItem", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"secEnvelopeItemId", "java.lang.String", REQUEST_SCOPE},
                {"indId","java.lang.String", REQUEST_SCOPE},
                {"ISecEnvelopeTrxValue", "com.integrosys.cms.app.securityenvelope.trx.ISecEnvelopeTrxValue", SERVICE_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try {
            String event = (String) (map.get("event"));
            ISecEnvelopeTrxValue SecEnvelopeTrxObj = (ISecEnvelopeTrxValue) map.get("ISecEnvelopeTrxValue");
            ISecEnvelope curSecEnvelope = SecEnvelopeTrxObj.getStagingSecEnvelope();
            ISecEnvelopeItem item = (ISecEnvelopeItem) (map.get("SecEnvelopeItemForm"));
            String secEnvelopeItemId = (String) (map.get("secEnvelopeItemId"));
            String indId = (String) (map.get("indId"));

            DefaultLogger.debug(this, "Save envelope item -> Event : " + event);

            boolean isCreate = "confirm_create_items".equals(event);
            boolean isCreateItem = "create_itemdetail".equals(event);
            boolean isEditCreate = "edit_items".equals(event);
            boolean isEditItemCreate = "edit_itemdetail".equals(event);
            boolean isResumitEditCreate = "resubmit_edit_items".equals(event);
            boolean isResubmitEditItemCreate = "resubmit_edit_itemdetail".equals(event);
            ArrayList list;

            if (curSecEnvelope.getSecEnvelopeItemList() == null) {
                list = new ArrayList();
                DefaultLogger.debug(this, "SaveSecEnvelopeItemCmd,list : " + list);
            } else {
                list = new ArrayList(curSecEnvelope.getSecEnvelopeItemList());
                DefaultLogger.debug(this, "SaveSecEnvelopeItemCmd,list : " + list);
            }

            if (!event.equals("edit_itemdetail") && !event.equals("resubmit_edit_itemdetail")){
                boolean isBarcodeExist = false;
                ArrayList arAllEnvItemStagingList = new ArrayList();
                arAllEnvItemStagingList = (ArrayList) getSecEnvelopeProxy().getAllEnvItemStaging();
                Iterator iterEnvItemStg = arAllEnvItemStagingList.iterator();
                while(iterEnvItemStg.hasNext()){
                    OBSecEnvelopeItem obItem = (OBSecEnvelopeItem)iterEnvItemStg.next();
                    if(obItem.getSecEnvelopeItemBarcode().equals(item.getSecEnvelopeItemBarcode())){
                        isBarcodeExist = true;
                    }
                }
                        
                if(isBarcodeExist){
                    exceptionMap.put("barCodeError", new ActionMessage("error.security.envelope.barcodeExist"));
                }
            }

            if (exceptionMap.size() == 0) {

                OBSecEnvelopeItem[] envelopeItem = new OBSecEnvelopeItem[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    OBSecEnvelopeItem envList = (OBSecEnvelopeItem) list.get(i);
                    envelopeItem[i] = envList;
                    DefaultLogger.debug(this, "save envelope item... list: " + envList);
                }
                ISecEnvelopeItem[] itemList = envelopeItem;
                if (isCreate) {

                    int arrayLength = (itemList == null ? 0 : itemList.length);
                    ISecEnvelopeItem[] newArray = new ISecEnvelopeItem[arrayLength + 1];
                    if (arrayLength != 0) {
                        System.arraycopy(itemList, 0, newArray, 0, arrayLength);
                    }

                    newArray[arrayLength] = item;
                    itemList = newArray;                             

                    curSecEnvelope.setSecEnvelopeItemList(new HashSet(Arrays.asList(itemList)));
                }

                if (isCreateItem) {
                    int indIdint = Integer.parseInt(indId);

                    itemList[indIdint].setSecEnvelopeItemAddr(item.getSecEnvelopeItemAddr());
                    itemList[indIdint].setSecEnvelopeItemCab(item.getSecEnvelopeItemCab());
                    itemList[indIdint].setSecEnvelopeItemDrw(item.getSecEnvelopeItemDrw());
                    itemList[indIdint].setSecEnvelopeItemBarcode(item.getSecEnvelopeItemBarcode());

                    curSecEnvelope.setSecEnvelopeItemList(new HashSet(Arrays.asList(itemList)));
                }

                if (isEditCreate || isResumitEditCreate) {

                    int arrayLength = (itemList == null ? 0 : itemList.length);
                    ISecEnvelopeItem[] newArray = new ISecEnvelopeItem[arrayLength + 1];
                    if (arrayLength != 0) {
                        System.arraycopy(itemList, 0, newArray, 0, arrayLength);
                    }

                    newArray[arrayLength] = item;
                    itemList = newArray;

                    curSecEnvelope.setSecEnvelopeItemList(new HashSet(Arrays.asList(itemList)));

                }

                if (isEditItemCreate || isResubmitEditItemCreate) {
                    DefaultLogger.debug(this, "Modified Item " + item.getSecEnvelopeItemId());

                    for (int i=0; i<itemList.length; i++){
                        ISecEnvelopeItem secEnvelopeItem = itemList[i];
                        DefaultLogger.debug(this, "getSecEnvelopeItemId " + secEnvelopeItem.getSecEnvelopeItemId());
                        if (new Long(secEnvelopeItem.getSecEnvelopeItemId()).compareTo(new Long(secEnvelopeItemId)) == 0){
                            itemList[i].setSecEnvelopeItemAddr(item.getSecEnvelopeItemAddr());
                            itemList[i].setSecEnvelopeItemCab(item.getSecEnvelopeItemCab());
                            itemList[i].setSecEnvelopeItemDrw(item.getSecEnvelopeItemDrw());
                        }
                    }
                    curSecEnvelope.setSecEnvelopeItemList(new HashSet(Arrays.asList(itemList)));
                }
            }
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
