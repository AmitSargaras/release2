package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.CountryList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import org.apache.struts.util.LabelValueBean;

import java.util.*;

/**
 * @author Andy Wong
 * @since 16 Sep 2008
 */
public class PrepareDeletePropertyIdxCmd extends PropertyIdxCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public PrepareDeletePropertyIdxCmd() {
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
                {"paramTrxId", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
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
                {"IPropertyIdxTrxValue", "com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue", SERVICE_SCOPE},
                {"PropertyIndex", "com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx", FORM_SCOPE},
                {"countryListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {"countryListValue", "java.util.ArrayList", REQUEST_SCOPE},
                {"propertySubtypeFullList", "java.util.ArrayList", REQUEST_SCOPE},
                {"propertySubtypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {"stateList", "java.util.List", REQUEST_SCOPE},
                {"districtFullList", "java.util.ArrayList", REQUEST_SCOPE},
                {"districtFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {"propTypeLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"propTypeValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"mukimFullList", "java.util.ArrayList", REQUEST_SCOPE},
                {"mukimFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {"wip", "java.lang.String", REQUEST_SCOPE},
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

        try {
            String paramTrxId = (String) map.get("paramTrxId");
            String event = (String) map.get("event");
            DefaultLogger.debug(this, "paramTrxId : " + paramTrxId);
            OBPropertyIdx obParam = new OBPropertyIdx();
            IPropertyIdxTrxValue trxValue = getPropertyIdxProxy().getPropertyIdxTrxValue(Long.parseLong(paramTrxId));

            if (UIUtil.checkDeleteWip(event, trxValue)) {
                resultMap.put("wip", "wip");
            } else {
                obParam = (OBPropertyIdx) trxValue.getPrIdx();
                Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
                CommonCodeList commonCode;
                ArrayList propertySubtypeFullList = new ArrayList();
                ArrayList propertySubtypeFullListLabel = new ArrayList();
                ArrayList districtFullList = new ArrayList();
                ArrayList districtFullListLabel = new ArrayList();
                ArrayList mukimFullList = new ArrayList();
                ArrayList mukimFullListLabel = new ArrayList();
                ArrayList countryListLabel = new ArrayList();
                ArrayList countryListValue = new ArrayList();
                ArrayList propTypeLabel = new ArrayList();
                ArrayList propTypeValue = new ArrayList();

                // get the countries this user is allowed
                ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
                getCountry(team, countryListLabel, countryListValue);

                resultMap.put("countryListLabel", countryListLabel);
                resultMap.put("countryListValue", countryListValue);

                commonCode = CommonCodeList.getInstance(CategoryCodeConstant.BUILDING_TYPE);
                resultMap.put("propTypeLabel", commonCode.getCommonCodeLabels());
                resultMap.put("propTypeValue", commonCode.getCommonCodeValues());

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
                }
                resultMap.put("stateList", lbValList);
                resultMap.put("propertySubtypeFullList", propertySubtypeFullList);
                resultMap.put("propertySubtypeFullListLabel", propertySubtypeFullListLabel);
                resultMap.put("districtFullList", districtFullList);
                resultMap.put("districtFullListLabel", districtFullListLabel);
                resultMap.put("mukimFullList", mukimFullList);
                resultMap.put("mukimFullListLabel", mukimFullListLabel);
                resultMap.put("PropertyIndex", obParam);
                resultMap.put("IPropertyIdxTrxValue", trxValue);
                DefaultLogger.debug(this, "trxValue after read" + trxValue);
            }
        } catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
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

    private void getCountry(ITeam team, ArrayList countryListLabel, ArrayList countryListValue) throws CommandProcessingException {
        CountryList countries = CountryList.getInstance();

        countryListLabel.addAll(countries.getCountryLabels());
        countryListValue.addAll(countries.getCountryValues());

        if (countryListLabel.size() == 0 || countryListValue.size() == 0) {
            throw new CommandProcessingException("Failed to retrieve country names and labels");
        }
    }

}



