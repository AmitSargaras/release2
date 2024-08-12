package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdxItem;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import org.apache.struts.action.ActionMessage;

import java.util.*;

/**
 * @author Andy Wong
 * @since 18 Sep 2008
 */
public class SavePropertyIdxItemCmd extends PropertyIdxCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"PropertyIdxItemForm", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem", FORM_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try {
            String event = (String) (map.get("event"));
            IPropertyIdxTrxValue PropertyIdxTrxObj = (IPropertyIdxTrxValue) map.get("IPropertyIdxTrxValue");
            IPropertyIdx curPropertyIdx = PropertyIdxTrxObj.getStagingPrIdx();
            IPropertyIdxItem item = (IPropertyIdxItem) (map.get("PropertyIdxItemForm"));

            DefaultLogger.debug(this, "Save Property item Event : " + event);

            boolean isCreate = "confirm_create_items".equals(event);
            boolean isEditCreate = "edit_items".equals(event);
            boolean isResumitEditCreate = "resubmit_edit_items".equals(event);
            ArrayList list;

            if (curPropertyIdx.getPropertyIdxItemList() == null) {
                list = new ArrayList();
                DefaultLogger.debug(this, "SavePropertyIdxItemCmd,list : " + list);
            } else {
                list = new ArrayList(curPropertyIdx.getPropertyIdxItemList());
                DefaultLogger.debug(this, "SavePropertyIdxItemCmd,list : " + list);
            }

            PropertyIdxUIHelper helper = new PropertyIdxUIHelper();
            boolean isExist = helper.isItemExist(curPropertyIdx.getPropertyIdxItemList(), item);
            if (isExist) {
                exceptionMap.put("itemExistError", new ActionMessage("error.property.item.exist"));
            }
            if (exceptionMap.size() == 0) {


                OBPropertyIdxItem[] propertyItem = new OBPropertyIdxItem[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    DefaultLogger.debug("this", "save property item...itemList : " + list.get(i));
                    OBPropertyIdxItem propList = (OBPropertyIdxItem) list.get(i);
                    propertyItem[i] = propList;
                    DefaultLogger.debug(this, "save property item...property list: " + propList);
                }
                DefaultLogger.debug(this, "save property item...IPropertyIdxItem 1: " + propertyItem);
                IPropertyIdxItem[] itemList = propertyItem;
                DefaultLogger.debug(this, "B4 save property item...itemList: " + itemList);
                if (isCreate) {

                    int arrayLength = (itemList == null ? 0 : itemList.length);
                    IPropertyIdxItem[] newArray = new IPropertyIdxItem[arrayLength + 1];
                    if (arrayLength != 0) {
                        System.arraycopy(itemList, 0, newArray, 0, arrayLength);
                    }

                    newArray[arrayLength] = item;
                    itemList = newArray;

                    curPropertyIdx.setPropertyIdxItemList(new HashSet(Arrays.asList(itemList)));
                }

                if (isEditCreate) {

                    int arrayLength = (itemList == null ? 0 : itemList.length);
                    IPropertyIdxItem[] newArray = new IPropertyIdxItem[arrayLength + 1];
                    if (arrayLength != 0) {
                        System.arraycopy(itemList, 0, newArray, 0, arrayLength);
                    }

                    newArray[arrayLength] = item;
                    itemList = newArray;

                    curPropertyIdx.setPropertyIdxItemList(new HashSet(Arrays.asList(itemList)));

                }

                if (isResumitEditCreate) {

                    int arrayLength = (itemList == null ? 0 : itemList.length);
                    IPropertyIdxItem[] newArray = new IPropertyIdxItem[arrayLength + 1];
                    if (arrayLength != 0) {
                        System.arraycopy(itemList, 0, newArray, 0, arrayLength);
                    }

                    newArray[arrayLength] = item;
                    itemList = newArray;

                    curPropertyIdx.setPropertyIdxItemList(new HashSet(Arrays.asList(itemList)));
                }
                DefaultLogger.debug(this, "save property item...itemList: " + itemList);
                DefaultLogger.debug(this, "SavePropertyIdxItemCmd,curPropertyIdx : " + curPropertyIdx);
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
