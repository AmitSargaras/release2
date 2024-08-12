package com.integrosys.cms.ui.systemparameters.propertyindex;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.proxy.IPropertyIdxProxyManager;
import com.integrosys.cms.ui.common.SecuritySubTypeList;

import java.util.*;

/**
 * Title: CLIMS
 * Description: for Maker to view the list of Property Valuation By Index
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 31, 2008
 */

public class ListPropertyValByIndexCmd extends PropertyIdxCmd implements ICommonEventConstant {
    /**
     * Default Constructor
     */
    public ListPropertyValByIndexCmd() {
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
                {"propertyIdxList", "java.util.ArrayList", REQUEST_SCOPE},
                {"propertySubTypeFullList", "java.util.ArrayList", REQUEST_SCOPE},
                {"propertySubTypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE}
        });
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
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            ArrayList propertyIdxList = new ArrayList();
            SecuritySubTypeList subTypeList = SecuritySubTypeList.getInstance();
            propertyIdxList = (ArrayList) getPropertyIdxProxy().getAllActual();

            // get list for security sub type
            Collection fullList = subTypeList.getSecuritySubTypeProperty();
            ArrayList collateralSubTypeFullList = new ArrayList();
            ArrayList collateralSubTypeFullListLabel = new ArrayList();

            setSecuritySubTypeToList(fullList, collateralSubTypeFullList, collateralSubTypeFullListLabel, subTypeList, locale);

            resultMap.put("propertySubTypeFullList", collateralSubTypeFullList);
            resultMap.put("propertySubTypeFullListLabel", collateralSubTypeFullListLabel);
            resultMap.put("propertyIdxList", propertyIdxList);
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
}



