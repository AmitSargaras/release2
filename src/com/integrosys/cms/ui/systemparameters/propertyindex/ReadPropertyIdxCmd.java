package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.app.propertyindex.trx.OBPropertyIdxTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import org.apache.struts.util.LabelValueBean;

import java.util.*;

/*
 * Title: CLIMS 
 * Description: Standard read property index command
 * Copyright: Integro Technologies Sdn Bhd 
 * Author: Andy Wong 
 * Date: Feb 1, 2008
 */

public class ReadPropertyIdxCmd extends PropertyIdxCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"propertyIdxId", "java.lang.String", REQUEST_SCOPE},
                {"paramTrxId", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {"PropertyIndex", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx", FORM_SCOPE},
                {"propertySubtypeFullList", "java.util.ArrayList", REQUEST_SCOPE},
                {"propertySubtypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {"wip", "java.lang.String", REQUEST_SCOPE},
                {"closeFlag", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"valDescrValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try {
            String event = (String) map.get("event");
            IPropertyIdx obParam = new OBPropertyIdx();
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            IPropertyIdxTrxValue trxValue = null;

            String trxId = (String) map.get("paramTrxId");
            String propertyIdxId = (String) (map.get("propertyIdxId"));

            if ("maker_prepare_close".equals(event) ||
                    "to_track".equals(event) ||
                    "maker_prepare_resubmit".equals(event)) {
                if (trxId == null) {
                    trxValue = (IPropertyIdxTrxValue) map.get("IPropertyIdxTrxValue");
                } else {
                    trxValue = (OBPropertyIdxTrxValue) getPropertyIdxProxy().getPropertyIdxByTrxID(trxId);
                }
                result.put("fromEvent", "todo");

                if ("maker_prepare_close".equals(event))
                    result.put("closeFlag", "true");

            } else {
                if (propertyIdxId == null) {
                    trxValue = (IPropertyIdxTrxValue) map.get("IPropertyIdxTrxValue");
                    obParam = (IPropertyIdx) trxValue.getPrIdx();
                } else {
                    trxValue = (OBPropertyIdxTrxValue) getPropertyIdxProxy().getPropertyIdxTrxValue(Long.parseLong(propertyIdxId));
                    obParam = (OBPropertyIdx) trxValue.getPrIdx();
                }

            }

            if (UIUtil.checkWip(event, trxValue)) {
                result.put("wip", "wip");
            }
            if (UIUtil.checkDeleteWip(event, trxValue)) {
                result.put("wip", "wip");
            }

            if ("maker_prepare_edit".equals(event)) {
                IPropertyIdx stgProperty = new OBPropertyIdx();
                stgProperty = trxValue.getPrIdx();
                trxValue.setStagingPrIdx(stgProperty);
            }

            if ("view_property_index".equals(event) || "checker_view_property_index".equals(event)) {
                obParam = trxValue.getPrIdx();
            } else {
                obParam = trxValue.getStagingPrIdx();
            }

            ArrayList propertySubtypeFullList = new ArrayList();
            ArrayList propertySubtypeFullListLabel = new ArrayList();
            CommonCodeList commonCode;

            result.put("valDescrValue", PropertyIdxUIHelper.getValDescList());

            // get list for security sub type
            SecuritySubTypeList subTypeList = SecuritySubTypeList.getInstance();
            Collection fullList = subTypeList.getSecuritySubTypeProperty();
            setSecuritySubTypeToList(fullList, propertySubtypeFullList, propertySubtypeFullListLabel, subTypeList, locale);

            List lbValList = new ArrayList();
            if (obParam.getCountryCode() != null && !("").equals(obParam.getCountryCode())) {
                HashMap state = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.STATE_CATEGORY_CODE, null, obParam.getCountryCode(), null);
                //setValueToList(state, stateFullList, stateFullListLabel);
                Object[] keyArr = state.keySet().toArray();
                for (int i = 0; i < keyArr.length; i++) {
                    Object nextKey = keyArr[i];
                    LabelValueBean lvBean = new LabelValueBean(state.get(nextKey).toString(), nextKey.toString());
                    lbValList.add(lvBean);
                }
                //return CommonUtil.sortDropdown(lbValList);
            }
            result.put("stateList", lbValList);


            result.put("propertySubtypeFullList", propertySubtypeFullList);
            result.put("propertySubtypeFullListLabel", propertySubtypeFullListLabel);
            // DefaultLogger.debug(this, "view....PropertyIndex :: " + obParam);
            result.put("PropertyIndex", obParam);
            result.put("IPropertyIdxTrxValue", trxValue);


        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw (new CommandProcessingException(ex.getMessage()));
        }
        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    private void setSecuritySubTypeToList(Collection fullList, ArrayList listValue, ArrayList listLabel, SecuritySubTypeList subTypeList, Locale locale) {
        // filter out sub type for property type only
        Iterator iter = fullList.iterator();
        HashMap subTypeLabelValMap = new HashMap();
        while (iter.hasNext()) {
            String subType = (String) iter.next();
            if (subType != null && (ICMSConstant.SECURITY_TYPE_PROPERTY).equalsIgnoreCase(subType.substring(0, 2))) {
                subTypeLabelValMap.put(subTypeList.getSecuritySubTypeValue(subType, locale), subType);
            }
        }

        // sort the list by label
        String[] subTypeLabel = (String[]) subTypeLabelValMap.keySet().toArray(new String[0]);
        Arrays.sort(subTypeLabel);

        listLabel.addAll(new ArrayList(Arrays.asList(subTypeLabel)));
        for (int i = 0; i < subTypeLabel.length; i++) {
            listValue.add(subTypeLabelValMap.get(subTypeLabel[i]));
        }
    }

}
