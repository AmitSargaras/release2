package com.integrosys.cms.ui.tatduration;

import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.propertyindex.bus.IPropertyIdx;
import com.integrosys.cms.app.propertyindex.bus.OBPropertyIdx;
import com.integrosys.cms.app.propertyindex.trx.IPropertyIdxTrxValue;
import com.integrosys.cms.app.propertyindex.trx.OBPropertyIdxTrxValue;
import com.integrosys.cms.app.tatduration.bus.ITatParam;
import com.integrosys.cms.app.tatduration.bus.ITatParamConstant;
import com.integrosys.cms.app.tatduration.bus.OBTatParam;
import com.integrosys.cms.app.tatduration.bus.OBTatParamItem;
import com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.SecuritySubTypeList;
import com.integrosys.cms.ui.common.UIUtil;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.tat.TatCommand;
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

public class ReadTatParamCmd extends TatDurationCommand
{
    public String[][] getParameterDescriptor() 
    {
        return (new String[][]{
                {"tatParamId", "java.lang.String", REQUEST_SCOPE},
                {"trxId", "java.lang.String", REQUEST_SCOPE},
                {"event", "java.lang.String", REQUEST_SCOPE},
                {"ITatParamTrxValue", "com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue", SERVICE_SCOPE},
                {com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE},
                {IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE},
        });
    }

    public String[][] getResultDescriptor() 
    {
        return (new String[][]{
        		{"tatParamId", "java.lang.String", REQUEST_SCOPE},
        		{"ITatParamTrxValue", "com.integrosys.cms.app.tatduration.trx.ITatParamTrxValue", SERVICE_SCOPE},
                {"TatParam", "com.integrosys.cms.app.tatduration.bus.OBTatParam", FORM_SCOPE},
//                {"propertySubtypeFullList", "java.util.ArrayList", REQUEST_SCOPE},
//                {"propertySubtypeFullListLabel", "java.util.ArrayList", REQUEST_SCOPE},
                {"wip", "java.lang.String", REQUEST_SCOPE},
                {"closeFlag", "java.lang.String", REQUEST_SCOPE},
                {"fromEvent", "java.lang.String", REQUEST_SCOPE},
                {"valDescrValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
                {"stagePreDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"stageDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"stagePostDibursmentList", "java.util.List", REQUEST_SCOPE},
                {"editFlag", "java.lang.String", REQUEST_SCOPE},
                {"durationTypeOptionList", "java.util.List", REQUEST_SCOPE},
        });
    }

    public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException 
    {
        HashMap result = new HashMap();
        HashMap exceptionMap = new HashMap();
        HashMap temp = new HashMap();

        try 
        {
            String event = (String) map.get("event");
            ITatParam obParam = new OBTatParam();
            Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
            ITatParamTrxValue trxValue = null;

            String trxId = (String) map.get("trxId");
            String tatParamId = (String) (map.get("tatParamId"));

            if ("maker_prepare_close".equals(event) || "to_track".equals(event) ) 
            {
                if (trxId == null)
                    trxValue = (ITatParamTrxValue) map.get("ITatParamTrxValue");
                else
                    trxValue = (ITatParamTrxValue) getTatParamProxy().getTatParamByTrxID(Long.parseLong(trxId.trim()));
                
                result.put("fromEvent", "todo");
                if ("maker_prepare_close".equals(event))
                    result.put("closeFlag", "true");

            } 
            else 
            {
                if (tatParamId == null) 
                {
                    trxValue = (ITatParamTrxValue) map.get("ITatParamTrxValue");
                    obParam = (ITatParam) trxValue.getTatParam();
                } 
                else 
                {
                    trxValue = (ITatParamTrxValue) getTatParamProxy().getTatParamTrxValue(Long.parseLong(tatParamId));
                    obParam = (OBTatParam) trxValue.getTatParam();
                }

            }

            if (UIUtil.checkWip(event, trxValue)) 
            {
                result.put("wip", "wip");
            }
            if (UIUtil.checkDeleteWip(event, trxValue)) 
            {
                result.put("wip", "wip");
            }

            if ("read_stage_list".equals(event) || "edit_stage_list".equals(event))
            {
                ITatParam stgTatParam = new OBTatParam();
                stgTatParam = trxValue.getTatParam();
                trxValue.setStagingTatParam(stgTatParam);
            }

            if ("view_property_index".equals(event) || "checker_view_property_index".equals(event)) 
                obParam = trxValue.getTatParam();
            else
                obParam = trxValue.getStagingTatParam();
            
            
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
			
			if("edit_stage_list".equals(event))
				result.put("editFlag", "Y");
			
			result.put("stagePreDibursmentList", preDibursment);
			result.put("stageDibursmentList", dibursment);
			result.put("stagePostDibursmentList", postDibursment);

			result.put("tatParamId", tatParamId);
            result.put("TatParam", obParam);
            result.put("ITatParamTrxValue", trxValue);

            CommonCodeList commonCode = CommonCodeList.getInstance(ITatParamConstant.DURATION_TYPE_ENTRY_CODE);
            result.put("durationTypeOptionList", commonCode.getOptionList());

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
