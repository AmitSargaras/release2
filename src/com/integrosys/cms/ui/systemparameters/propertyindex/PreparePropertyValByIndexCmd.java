package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import org.apache.struts.util.LabelValueBean;

import java.util.*;

/**
 * Describe this class.
 * Purpose: for Maker to create new Property Valuation By Index
 * Description: command that help the Maker to create new Property Valuation By Index
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 *        Tag: $Name$
 */

public class PreparePropertyValByIndexCmd extends PropertyIdxCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public PreparePropertyValByIndexCmd() {

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
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {"isCreate", "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE}
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
                {"PropertyIndex", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx", FORM_SCOPE},
                {"propertySubtypeFullList", "java.util.ArrayList", REQUEST_SCOPE},
                {"propertySubtypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {"valDescrValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
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
        HashMap exceptionMap = new HashMap();

        try {
            String event = (String) (map.get("event"));
            String isCreate = (String) (map.get("isCreate"));
            IPropertyIdx obParam = new OBPropertyIdx();

            PropertyIdxUIHelper helper = new PropertyIdxUIHelper();
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            IPropertyIdxTrxValue PropertyIdxTrxObj = (IPropertyIdxTrxValue) (map.get("IPropertyIdxTrxValue"));
            obParam = this.getPropertyIdx(PropertyIdxTrxObj, event);
            CommonCodeList commonCode;

            ArrayList propertySubtypeFullList = new ArrayList();
            ArrayList propertySubtypeFullListLabel = new ArrayList();

            commonCode = CommonCodeList.getInstance(CategoryCodeConstant.BUILDING_TYPE);
            resultMap.put("propTypeLabel", commonCode.getCommonCodeLabels());
            resultMap.put("propTypeValue", commonCode.getCommonCodeValues());
            resultMap.put("valDescrValue", PropertyIdxUIHelper.getValDescList());

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

            resultMap.put("propertySubtypeFullList", propertySubtypeFullList);
            resultMap.put("propertySubtypeFullListLabel", propertySubtypeFullListLabel);
            resultMap.put("PropertyIndex", obParam);

        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return returnMap;
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

    // sort the list by label
    private void setValueToList(Map map, ArrayList listValue, ArrayList listLabel) {
        if (map != null) {
            String[] keyLabel = (String[]) map.keySet().toArray(new String[0]);
            Arrays.sort(keyLabel);

            listLabel.addAll(new ArrayList(Arrays.asList(keyLabel)));
            for (int i = 0; i < keyLabel.length; i++) {
                listValue.add(map.get(keyLabel[i]));
            }
        }
    }

    private IPropertyIdx getPropertyIdx(IPropertyIdxTrxValue trxValue, String event) {
        if (trxValue == null) {
            return new OBPropertyIdx();
        }
        if ("read".equals(event)) {
            return trxValue.getPrIdx();
        } else {
            IPropertyIdx curTemplate = trxValue.getStagingPrIdx();
            if (curTemplate == null) {
                curTemplate = new OBPropertyIdx();
            }
            return curTemplate;
        }
    }
}



