package com.integrosys.cms.ui.tatduration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.tatduration.bus.OBTatParam;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;
import com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue;
import com.integrosys.cms.app.tatduration.trx.OBTatParamTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * Title: CLIMS
 * Description: Checker to view & prepare to approve/reject the create/update/delete request for Property Index
 * Copyright: Integro Technologies Sdn Bhd
 * Author: Andy Wong
 * Date: Jan 30, 2008
 */

public class CheckerProcessTatParamCmd extends TatDurationCommand implements ICommonEventConstant 
{
    /**
     * Default Constructor
     */

    public CheckerProcessTatParamCmd() 
    {
    }

    /**
     * Defines an two dimensional array with the result list to be
     * expected as a result from the doExecute method using a HashMap
     * syntax for the array is (HashMapkey,classname,scope)
     * The scope may be request,form or service
     *
     * @return the two dimensional String array
     */
    public String[][] getParameterDescriptor() 
    {
        return (new String[][]{
                {"trxId", "java.lang.String", REQUEST_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
                {"ITatParamTrxValue", "com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue", SERVICE_SCOPE}
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
        		{"tatParamId", "java.lang.String", REQUEST_SCOPE},
        		{"ITatParamTrxValue", "com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue", SERVICE_SCOPE},
                {"TatParam", "com.integrosys.cms.app.tatduration.bus.OBTatParam", FORM_SCOPE},
                {"stagePreDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"stageDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"stagePostDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"trxId", "java.lang.String", REQUEST_SCOPE},
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
    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException
    {
        HashMap returnMap = new HashMap();
        HashMap resultMap = new HashMap();
        
        try 
        {
            String trxId = (String) map.get("trxId");
//            DefaultLogger.debug(this, "trxId : " + trxId);

            OBTatParam obParam = new OBTatParam();
            ITatParamTrxValue trxValue = new OBTatParamTrxValue();

            if (trxId == null && map.containsKey("ITatParamTrxValue")) 
                trxValue = (ITatParamTrxValue) map.get("ITatParamTrxValue");
            else
                trxValue = (OBTatParamTrxValue) getTatParamProxy().getTatParamByTrxID(Long.parseLong(trxId));

            obParam = (OBTatParam) trxValue.getStagingTatParam();
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            
         // Processing the list
            List paramItemList = new ArrayList(obParam.getTatParamItemList());
			List preDibursment = new ArrayList(), dibursment = new ArrayList(), postDibursment = new ArrayList();
			
			// bubble sort the list
			for(int i=0; i<paramItemList.size()-1; i++) 
			{
				OBTatParamItem item = (OBTatParamItem) paramItemList.get(i);
				OBTatParamItem nextItem = (OBTatParamItem) paramItemList.get(i+1);
				if(item.getSequenceOrder() > nextItem.getSequenceOrder())
				{
					paramItemList.set(i, nextItem);
					paramItemList.set(i+1, item);
					// reset the loop
					i = -1;
				}
			}
			
			for (Iterator iterator = paramItemList.iterator(); iterator.hasNext();) 
			{
				OBTatParamItem item = (OBTatParamItem) iterator.next();
				if("1".equals(item.getStageType()))
					preDibursment.add(item);
				else if("2".equals(item.getStageType()))
					dibursment.add(item);
				else if("3".equals(item.getStageType()))
					postDibursment.add(item);
			}
			
			resultMap.put("stagePreDibursmentList", preDibursment);
			resultMap.put("stageDibursmentList", dibursment);
			resultMap.put("stagePostDibursmentList", postDibursment);

            /*ArrayList propertySubtypeFullList = new ArrayList();
            ArrayList propertySubtypeFullListLabel = new ArrayList();
            ArrayList countryListLabel = new ArrayList();
            ArrayList countryListValue = new ArrayList();
            CommonCodeList commonCode;*/

            // get the countries this user is allowed
//            ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);
//            getCountry(team, countryListLabel, countryListValue);

            /*resultMap.put("countryListLabel", countryListLabel);
            resultMap.put("countryListValue", countryListValue);

            commonCode = CommonCodeList.getInstance(CategoryCodeConstant.BUILDING_TYPE);
            resultMap.put("propTypeLabel", commonCode.getCommonCodeLabels());
            resultMap.put("propTypeValue", commonCode.getCommonCodeValues());*/

//            resultMap.put("valDescrValue", PropertyIdxUIHelper.getValDescList());

            // get list for security sub type
//            SecuritySubTypeList subTypeList = SecuritySubTypeList.getInstance();
           /* Collection fullList = subTypeList.getSecuritySubTypeProperty();
            setSecuritySubTypeToList(fullList, propertySubtypeFullList, propertySubtypeFullListLabel, subTypeList, locale);
*/
           /* List lbValList = new ArrayList();
            if (obParam.getCountryCode() != null && !("").equals(obParam.getCountryCode())) {

                HashMap state = CommonDataSingleton.getCodeCategoryLabelValueMap(CategoryCodeConstant.STATE_CATEGORY_CODE, null, obParam.getCountryCode(), null);
                Object[] keyArr = state.keySet().toArray();
                for (int i = 0; i < keyArr.length; i++) {
                    Object nextKey = keyArr[i];
                    LabelValueBean lvBean = new LabelValueBean(state.get(nextKey).toString(), nextKey.toString());
                    lbValList.add(lvBean);
                }
            }
            resultMap.put("stateList", lbValList);*/

//            resultMap.put("propertySubtypeFullList", propertySubtypeFullList);
//            resultMap.put("propertySubtypeFullListLabel", propertySubtypeFullListLabel);
//            DefaultLogger.debug(this, "process....PropertyIndex :: " + obParam);
			resultMap.put("tatParamId", String.valueOf(obParam.getTatParamId()));
            resultMap.put("TatParam", obParam);
            resultMap.put("ITatParamTrxValue", trxValue);
            resultMap.put("ITatParamTrxValue2", trxValue);
            resultMap.put("trxId", trxId);

        }
        catch (Exception e) 
        {
//            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }
//        DefaultLogger.debug(this, "Going out of doExecute()");
        returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
        return returnMap;
    }
}



