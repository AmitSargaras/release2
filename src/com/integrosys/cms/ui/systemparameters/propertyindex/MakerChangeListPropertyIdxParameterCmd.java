package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.YearOfManufactureList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import org.apache.struts.util.LabelValueBean;

import java.util.*;

/**
 * @author Andy Wong
 * @since 18 Sep 2008
 */
public class MakerChangeListPropertyIdxParameterCmd extends PropertyIdxCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public MakerChangeListPropertyIdxParameterCmd() {
    }

    final String SEARCH_STATE = "search_state";
    final String SEARCH_DISTRICT = "search_district";
    final String SEARCH_MUKIM = "search_mukim";

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
                {"searchItem", "java.lang.String", REQUEST_SCOPE},
                {"PropertyIdxItemForm", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem", FORM_SCOPE},
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
                {"PropertyIdxItemForm", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdxItem", FORM_SCOPE},
                {"OBPropertyIdx", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx", SERVICE_SCOPE},
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
        CommonCodeList commonCode;

        DefaultLogger.debug(this, "Inside doExecute()");
        try {
            OBPropertyIdxItem obParam = (OBPropertyIdxItem) map.get("PropertyIdxItemForm");
            String searchItem = (String) map.get("searchItem");
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);

            if ((SEARCH_STATE).equals(searchItem)) {
                //clearStateCode(obParam);
                clearDistrictList(obParam);
                clearMukimList(obParam);
            }

            YearOfManufactureList yearList = YearOfManufactureList.getInstance();
            resultMap.put("yearValue", yearList.getYearValues());
            resultMap.put("yearLabel", yearList.getYearLabels());

            commonCode = CommonCodeList.getInstance(ICMSConstant.PROP_IDX_PERIOD);
            resultMap.put("propIdxTypeLabel", commonCode.getCommonCodeLabels());
            resultMap.put("propIdxTypeValue", commonCode.getCommonCodeValues());

            ArrayList districtFullList = new ArrayList();
            ArrayList districtFullListLabel = new ArrayList();
            ArrayList mukimFullList = new ArrayList();
            ArrayList mukimFullListLabel = new ArrayList();
            ArrayList propertyTypeFullList = new ArrayList();
            ArrayList propertyTypeFullListLabel = new ArrayList();

            HashMap propertyType = new HashMap();

            propertyType.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.BUILDING_TYPE, null, null, null));
            setValueToList(propertyType, propertyTypeFullList, propertyTypeFullListLabel);

            List lbValList = new ArrayList();

            HashMap state = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.STATE_CATEGORY_CODE, null, "MY", null);
            //setValueToList(state, stateFullList, stateFullListLabel);
            Object[] keyArr = state.keySet().toArray();
            for (int i = 0; i < keyArr.length; i++) {
                Object nextKey = keyArr[i];
                LabelValueBean lvBean = new LabelValueBean(state.get(nextKey).toString(), nextKey.toString());
                lbValList.add(lvBean);
            }
            //return CommonUtil.sortDropdown(lbValList);

            resultMap.put("stateList", lbValList);


            if (obParam.getStateCode() != null && obParam.getStateCode().length() > 0) {
                HashMap district = new HashMap();
                HashMap mukim = new HashMap();

                district.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(
                        CategoryCodeConstant.DISTRICT_CATEGORY_CODE, null, "MY", obParam.getStateCode()));

                mukim.putAll(CommonDataSingleton.getCodeCategoryLabelValueMap(
                        CategoryCodeConstant.MUKIM_CATEGORY_CODE, null, "MY", obParam.getStateCode()));

                DefaultLogger.debug(this, "obParam.getStateList() :: " + obParam.getStateCode());
                setValueToList(district, districtFullList, districtFullListLabel);
                setValueToList(mukim, mukimFullList, mukimFullListLabel);
            }

            resultMap.put("propertyTypeFullList", propertyTypeFullList);
            resultMap.put("propertyTypeFullListLabel", propertyTypeFullListLabel);
            resultMap.put("districtFullList", districtFullList);
            resultMap.put("districtFullListLabel", districtFullListLabel);
            resultMap.put("mukimFullList", mukimFullList);
            resultMap.put("mukimFullListLabel", mukimFullListLabel);
            resultMap.put("PropertyIndex", obParam);
            resultMap.put("OBPropertyIdx", obParam);

        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }

    private void clearStateCode(OBPropertyIdxItem obPropertyIdx) {
        obPropertyIdx.setStateCode(new String());
    }

    private void clearDistrictList(OBPropertyIdxItem obPropertyIdx) {
        obPropertyIdx.setDistrictList(new HashSet());
    }

    private void clearMukimList(OBPropertyIdxItem obPropertyIdx) {
        obPropertyIdx.setMukimList(new HashSet());
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

    private void getCountry(ITeam team, ArrayList countryListLabel, ArrayList countryListValue) throws CommandProcessingException {
        CountryList countries = CountryList.getInstance();

        countryListLabel.addAll(countries.getCountryLabels());
        countryListValue.addAll(countries.getCountryValues());

        if (countryListLabel.size() == 0 || countryListValue.size() == 0) {
            throw new CommandProcessingException("Failed to retrieve country names and labels");
        }
    }

}



