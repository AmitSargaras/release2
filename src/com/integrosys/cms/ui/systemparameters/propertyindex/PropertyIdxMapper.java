/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.diff.CompareOBUtil;
import com.integrosys.base.techinfra.diff.CompareResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.base.uiinfra.common.CommonForm;
import com.integrosys.base.uiinfra.exception.MapperException;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.*;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;

import java.util.*;

/**
 * Title: CLIMS
 * Description: Map the form to OB or OB to form for property valuation by index
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 31, 2008
 */

public class PropertyIdxMapper extends AbstractCommonMapper {
    /**
     * Default Construtor
     */
    public PropertyIdxMapper() {
    }

    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
        });
    }

    /**
     * This method is used to map the Form values into Corresponding OB Values and returns the same.
     *
     * @param cForm is of type CommonForm
     * @return Object
     */
    public Object mapFormToOB(CommonForm cForm, HashMap map) throws MapperException {
        String event = (String) (map.get("event"));
        try {
            IPropertyIdx obParam = null;
            if ("maker_confirm_create".equals(event)) {
                obParam = new OBPropertyIdx();
            } else {
                IPropertyIdxTrxValue trxValue = (IPropertyIdxTrxValue) (map.get("IPropertyIdxTrxValue"));
                DefaultLogger.debug(this, "property index,IPropertyIdxTrxValue:: " + trxValue);
                obParam = trxValue.getStagingPrIdx();
            }
            if (obParam == null) {
                obParam = new OBPropertyIdx();
            }

            PropertyIdxForm aForm = (PropertyIdxForm) cForm;
            obParam.setValDescr(aForm.getValuationDescription());
            obParam.setCountryCode("MY");
            obParam.setStatus(ICMSConstant.STATE_ACTIVE);

            if (aForm.getSelectedPropertySubtype() != null && aForm.getSelectedPropertySubtype().length > 0) {
                ArrayList list = new ArrayList();
                for (int i = 0; i < aForm.getSelectedPropertySubtype().length; i++) {
                    IPropertyIdxSecSubType idxSecSubType = new OBPropertyIdxSecSubType();
                    idxSecSubType.setSecuritySubTypeId(aForm.getSelectedPropertySubtype()[i]);
                    idxSecSubType.setStatus(ICMSConstant.STATE_ACTIVE);
                    list.add(idxSecSubType);
                }
                obParam.setPropertyIdxSecSubTypeList(new HashSet(list));
            }

            if (aForm.getPropertyIdxItemList() != null) {
                DefaultLogger.debug(this, "FormToOB B4 Property Index Item::" + aForm.getPropertyIdxItemList());
                obParam.setPropertyIdxItemList(new HashSet(aForm.getPropertyIdxItemList()));
            }

            if ("delete_items".equals(event) || "delete_edit_items".equals(event) || "delete_resubmit_edit_items".equals(event)) {
                deleteItem(obParam, aForm);
            }
            return obParam;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }

    }

    /**
     * This method is used to map data from OB to the form and to return the form.
     *
     * @param cForm is of type CommonForm
     * @param obj   is of type Object
     * @return Object
     */
    public CommonForm mapOBToForm(CommonForm cForm, Object obj, HashMap map) throws MapperException {
        DefaultLogger.debug(this, "inside mapOb to form ");
        try {
            String event = (String) (map.get("event"));
            DefaultLogger.debug(this, "OB to Form,event here:::" + event);
            IPropertyIdxTrxValue PropertyIdxTrxObj = (IPropertyIdxTrxValue) (map.get("IPropertyIdxTrxValue"));
            PropertyIdxForm aForm = (PropertyIdxForm) cForm;

            if (obj != null) {
                OBPropertyIdx tempOb = (OBPropertyIdx) obj;

                if (!"checker_process_create".equals(event) && !"checker_process_edit".equals(event) && !"checker_process_delete".equals(event)) {
                    DefaultLogger.debug(this, "OB to Form,Inside property index item::");

                    if (tempOb.getPropertyIdxItemList() != null) {
                        for (int i = 0; i < tempOb.getPropertyIdxItemList().size(); i++) {
                            aForm.setPropertyIdxItemList(new ArrayList(tempOb.getPropertyIdxItemList()));
                        }
                    } else {
                        aForm.setPropertyIdxItemList(new ArrayList());
                    }

                    if (tempOb.getPropertyIdxSecSubTypeList() != null) {
                        String[] tmp = new String[tempOb.getPropertyIdxSecSubTypeList().size()];
                        int i = 0;
                        for (Iterator iter = tempOb.getPropertyIdxSecSubTypeList().iterator(); iter.hasNext();) {
                            IPropertyIdxSecSubType element = (IPropertyIdxSecSubType) iter.next();
                            tmp[i] = element.getSecuritySubTypeId();
                            i++;
                        }

                        aForm.setSelectedPropertySubtype(tmp);
                    }
                    List compareList = aForm.getPropertyIdxItemList();
                    if (compareList != null) {
                        Collections.sort(compareList, new SortByYearQuarter(false));

                        aForm.setPropertyIdxItemList(compareList);
                    }
                } else {
                    renderCompareItem(PropertyIdxTrxObj.getPrIdx(), PropertyIdxTrxObj.getStagingPrIdx(), aForm);
                }


                if (tempOb.getValDescr() != null && !("").equals(tempOb.getValDescr())) {
                    aForm.setValuationDescription(tempOb.getValDescr());
                }
                aForm.setCountry(tempOb.getCountryCode());
                aForm.setDeletedItemList(new String[0]);
            }
            DefaultLogger.debug(this, "Going out of mapOb to form ");
            DefaultLogger.debug(this, "Document object in Mapper" + aForm);
            return aForm;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new MapperException();
        }
    }


    private static void renderCompareItem(IPropertyIdx origProperty, IPropertyIdx stagingProperty, PropertyIdxForm aForm) throws Exception {
        IPropertyIdxItem[] propIndexItemOldLmtRef = null;
        IPropertyIdxItem[] propIndexItemNewLmtRef = null;

        IPropertyIdxSecSubType[] propIndexSubTypeOldLmtRef = null;
        IPropertyIdxSecSubType[] propIndexSubTypeNewLmtRef = null;

        if (origProperty != null) {
            ArrayList propIndexItemCompareItemList = new ArrayList(origProperty.getPropertyIdxItemList());
            propIndexItemOldLmtRef = (IPropertyIdxItem[]) propIndexItemCompareItemList.toArray(new IPropertyIdxItem[propIndexItemCompareItemList.size()]);

            ArrayList propIndexSubTypeCompareItemList = new ArrayList(origProperty.getPropertyIdxSecSubTypeList());
            propIndexSubTypeOldLmtRef = (IPropertyIdxSecSubType[]) propIndexSubTypeCompareItemList.toArray(new IPropertyIdxSecSubType[propIndexSubTypeCompareItemList.size()]);

        }
        if (stagingProperty != null) {
            ArrayList propIndexItemCompareStagingItemList = new ArrayList(stagingProperty.getPropertyIdxItemList());
            propIndexItemNewLmtRef = (IPropertyIdxItem[]) propIndexItemCompareStagingItemList.toArray(new IPropertyIdxItem[propIndexItemCompareStagingItemList.size()]);

            ArrayList propIndexSubTypeCompareStagingItemList = new ArrayList(stagingProperty.getPropertyIdxSecSubTypeList());
            propIndexSubTypeNewLmtRef = (IPropertyIdxSecSubType[]) propIndexSubTypeCompareStagingItemList.toArray(new IPropertyIdxSecSubType[propIndexSubTypeCompareStagingItemList.size()]);
        }

        if (propIndexItemOldLmtRef == null) {
            propIndexItemOldLmtRef = new IPropertyIdxItem[0];
        }
        if (propIndexItemNewLmtRef == null) {
            propIndexItemNewLmtRef = new IPropertyIdxItem[0];
        }

        if (propIndexSubTypeOldLmtRef == null) {
            propIndexSubTypeOldLmtRef = new IPropertyIdxSecSubType[0];
        }
        if (propIndexSubTypeNewLmtRef == null) {
            propIndexSubTypeNewLmtRef = new IPropertyIdxSecSubType[0];
        }

        List newList = CompareOBUtil.compOBArray(propIndexSubTypeNewLmtRef, propIndexSubTypeOldLmtRef);

        String[] tmp = new String[newList.size()];
        int i = 0;
        for (Iterator iter = newList.iterator(); iter.hasNext();) {
            CompareResult element = (CompareResult) iter.next();
            IPropertyIdxSecSubType object = (IPropertyIdxSecSubType) element.getObj();
            tmp[i] = object.getSecuritySubTypeId();
            i++;
        }

        aForm.setSelectedPropertySubtype(tmp);

        //sort by latest year, quarter first
        List compareList = CompareOBUtil.compOBArray(propIndexItemNewLmtRef, propIndexItemOldLmtRef);
        if (compareList != null) {
            Collections.sort(compareList, new SortByYearQuarter(true));

            aForm.setPropertyIdxItemList(compareList);
        }

    }

    private void deleteItem(IPropertyIdx obParam, PropertyIdxForm aForm) {

        PropertyIdxUIHelper helper = new PropertyIdxUIHelper();

        ArrayList itemList = new ArrayList(obParam.getPropertyIdxItemList());
        if (itemList != null) {
            //sort by latest year, quarter first
            Collections.sort(itemList, new SortByYearQuarter(false));
        }
        OBPropertyIdxItem[] item = (OBPropertyIdxItem[]) itemList.toArray(new OBPropertyIdxItem[itemList.size()]);

        String[] deletedInd = aForm.getDeletedItemList();

        if (item != null && deletedInd != null && deletedInd.length > 0) {
            List tempList = new ArrayList();
            DefaultLogger.debug(this, "...delete item length:: " + item.length);
            for (int i = 0; i < item.length; i++) {
                tempList.add(item[i]);
            }

            List res = helper.deleteItem(tempList, deletedInd);
            obParam.setPropertyIdxItemList(new HashSet(res));
        }

    }

    public static class SortByYearQuarter implements Comparator {

        boolean isCompareItem = false;

        SortByYearQuarter(boolean isCompareItem) {
            this.isCompareItem = isCompareItem;
        }

        public int compare(Object a, Object b) {
            int retValue = 0;

            if (a != null && b != null) {

                IPropertyIdxItem obj1 = null;
                IPropertyIdxItem obj2 = null;

                if (isCompareItem) {
                    obj1 = (IPropertyIdxItem) ((CompareResult) a).getObj();
                    obj2 = (IPropertyIdxItem) ((CompareResult) b).getObj();
                } else {
                    obj1 = (IPropertyIdxItem) a;
                    obj2 = (IPropertyIdxItem) b;
                }

                //latest year first
                retValue = obj2.getIdxYear().compareTo(obj1.getIdxYear());
                if (retValue != 0)
                    return retValue;

                // followed by quarter
                int quarterCodeValue1 = CommonUtil.getQuarterCodeValue(obj1.getIdxType());
                int quarterCodeValue2 = CommonUtil.getQuarterCodeValue(obj2.getIdxType());

                retValue = new Integer(quarterCodeValue1).compareTo(new Integer(quarterCodeValue2));
                if (retValue != 0)
                    return retValue;

                // followed by latest created
                long itemId1 = obj1.getPropertyIdxItemId();
                long itemId2 = obj2.getPropertyIdxItemId();

                if (itemId2 > itemId1)
                    return 1;
                else if (itemId1 == itemId2)
                    return 0;
                else
                    return -1;
            }
            return retValue;
        }
    }
}

