package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdxItem;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.YearOfManufactureList;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import org.apache.struts.util.LabelValueBean;

import java.util.*;

/**
 * Modified with IntelliJ IDEA.
 * User: Andy Wong
 * Date: Dec 3, 2008
 * Time: 10:31:05 AM
 *
 * Amendment: show property type as category land use
 */
public class PreparePropertyIdxItemCmd extends PropertyIdxCmd {
    public String[][] getParameterDescriptor() {
        return (new String[][]{
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"PropertyIdxItemForm", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem", FORM_SCOPE},
        });
    }

    public String[][] getResultDescriptor() {
        return (new String[][]{
                //{"curPropertyIdxItem", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem", SERVICE_SCOPE},
                {"PropertyIdxItemForm", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem", FORM_SCOPE},
                {"yearValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"yearLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"propIdxTypeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"propIdxTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"stateList", "java.util.List", REQUEST_SCOPE},
                {"districtFullList", "java.util.ArrayList", REQUEST_SCOPE},
                {"districtFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {"propertyTypeFullList", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"propertyTypeFullListLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"mukimFullList", "java.util.ArrayList", REQUEST_SCOPE},
                {"mukimFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();
        CommonCodeList commonCode;

        try {
            String event = (String) (map.get("event"));
            IPropertyIdxItem curPropertyIdxItem = new OBPropertyIdxItem();

            if (map.containsKey("PropertyIdxItemForm")) {
                curPropertyIdxItem = (OBPropertyIdxItem) map.get("PropertyIdxItemForm");
            }

            YearOfManufactureList yearList = YearOfManufactureList.getInstance();
            result.put("yearValue", yearList.getYearValues());
            result.put("yearLabel", yearList.getYearLabels());


            ArrayList districtFullList = new ArrayList();
            ArrayList districtFullListLabel = new ArrayList();
            ArrayList mukimFullList = new ArrayList();
            ArrayList mukimFullListLabel = new ArrayList();
            ArrayList propertyTypeFullList = new ArrayList();
            ArrayList propertyTypeFullListLabel = new ArrayList();

            HashMap propertyType = new HashMap();

            propertyType.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(
                    CategoryCodeConstant.BUILDING_TYPE, null, null, null));

            setValueToList(propertyType, propertyTypeFullList, propertyTypeFullListLabel);
/*
            commonCode = CommonCodeList.getInstance(ICMSConstant.PROP_IDX_TYPE);
            result.put("propIdxTypeLabel", commonCode.getCommonCodeLabels());
            result.put("propIdxTypeValue", commonCode.getCommonCodeValues());
*/

			commonCode = CommonCodeList.getInstance(ICMSConstant.PROP_IDX_PERIOD);
			result.put("propIdxTypeLabel", commonCode.getCommonCodeLabels());
			result.put("propIdxTypeValue", commonCode.getCommonCodeValues());

            List lbValList = new ArrayList();


            HashMap state = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.STATE_CATEGORY_CODE, null, "MY", null);
            Object[] keyArr = state.keySet().toArray();
            for (int i = 0; i < keyArr.length; i++) {
                Object nextKey = keyArr[i];
                LabelValueBean lvBean = new LabelValueBean(state.get(nextKey).toString(), nextKey.toString());
                lbValList.add(lvBean);
            }
            result.put("stateList", lbValList);
            if (curPropertyIdxItem.getStateCode() != null && curPropertyIdxItem.getStateCode().length() > 0) {
                HashMap district = new HashMap();
                HashMap mukim = new HashMap();

                district.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(
                        CategoryCodeConstant.DISTRICT_CATEGORY_CODE, null, "MY", curPropertyIdxItem.getStateCode()));

                mukim.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(
                        CategoryCodeConstant.MUKIM_CATEGORY_CODE, null, "MY", curPropertyIdxItem.getStateCode()));

                DefaultLogger.debug(this, "obParam.getStateList() :: " + curPropertyIdxItem.getStateCode());
                setValueToList(district, districtFullList, districtFullListLabel);
                setValueToList(mukim, mukimFullList, mukimFullListLabel);
            }

            result.put("propertyTypeFullList", propertyTypeFullList);
            result.put("propertyTypeFullListLabel", propertyTypeFullListLabel);
            result.put("districtFullList", districtFullList);
            result.put("districtFullListLabel", districtFullListLabel);
            result.put("mukimFullList", mukimFullList);
            result.put("mukimFullListLabel", mukimFullListLabel);
        }
        catch (Exception ex) {
            throw (new CommandProcessingException(ex.getMessage()));
        }

        temp.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
        temp.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
        return temp;
    }

    //	 sort the list by label
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

}
