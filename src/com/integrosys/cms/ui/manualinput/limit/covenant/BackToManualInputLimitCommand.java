 package com.integrosys.cms.ui.manualinput.limit.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.bus.ILimitCovenant;
import com.integrosys.cms.app.limit.bus.OBLimitCovenant;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.EventConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.component.bizstructure.app.bus.ITeam;

public class BackToManualInputLimitCommand extends AbstractCommand implements ILmtCovenantConstants {

	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();
		
		ILimitTrxValue lmtTrxObj = null;
		
		lmtTrxObj = (ILimitTrxValue)map.get("lmtTrxObj");
		String limitId = (String) map.get("limitId");
		String customerID = (String) (map.get("customerID"));
		String lmtID = (String) (map.get("limitId")); 
		String lmtRef = (String) (map.get("limitRef")); 
		String fromEvent = (String) (map.get("fromEvent")); 
		String event = (String) (map.get("event"));
		String trxID = (String) (map.get("trxID"));
		resultMap.put("lmtTrxObj", lmtTrxObj);
		resultMap.put("fromEvent", fromEvent);
		ILimit curLmt = null;
		if( "track".equals(fromEvent) && "process".equals(fromEvent) && "process_update".equals(fromEvent) && "prepare_close".equals(fromEvent) ){
			curLmt = lmtTrxObj.getStagingLimit();
		}else {
			curLmt = lmtTrxObj.getLimit();
		}
		if("cancel_covenant_detail".equals(event))
		{
			List restCountryList1=(List) map.get("restCountryList");
			List restCountryList = new ArrayList();
			if(null != restCountryList1)
			{
			for (int i = 0; i < restCountryList1.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) restCountryList1.get(i);
				if(covenantDetail.getIsNewEntry() != null)
				{
				if(!covenantDetail.getIsNewEntry().equals("Y"))
				{
					restCountryList.add((OBLimitCovenant) covenantDetail);
				}
				}
				else
				{
					restCountryList.add((OBLimitCovenant) covenantDetail);
				}
				
			}
		}
			
			List restCurrencyList1=(List) map.get("restCurrencyList");
			List restCurrencyList = new ArrayList();
			if(null != restCurrencyList1)
			{
			for (int i = 0; i < restCurrencyList1.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) restCurrencyList1.get(i);
				if(covenantDetail.getIsNewEntry() != null)
				{
				if(!covenantDetail.getIsNewEntry().equals("Y"))
				{
					restCurrencyList.add((OBLimitCovenant) covenantDetail);
				}
				}
				else
				{
					restCurrencyList.add((OBLimitCovenant) covenantDetail);
				}
			
			}
			}
			
			List restBankList1=(List) map.get("restBankList");
			List restBankList = new ArrayList();
			if(null != restBankList1) {
			for (int i = 0; i < restBankList1.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) restBankList1.get(i);
				if(covenantDetail.getIsNewEntry() != null)
				{
				if(!covenantDetail.getIsNewEntry().equals("Y"))
				{
					restBankList.add((OBLimitCovenant) covenantDetail);
				}
				}
				else
				{
					restBankList.add((OBLimitCovenant) covenantDetail);
				}
			}
			}
			List restDrawerList1=(List) map.get("restDrawerList");
			List restDrawerList = new ArrayList();
			if(null != restBankList1)
			{
			for (int i = 0; i < restDrawerList1.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) restDrawerList1.get(i);
				if(covenantDetail.getIsNewEntry() != null)
				{
				if(!covenantDetail.getIsNewEntry().equals("Y"))
				{
					restDrawerList.add((OBLimitCovenant) covenantDetail);
				}
				}
				else
				{
					restDrawerList.add((OBLimitCovenant) covenantDetail);
				}
			}
			}
			List restDraweeList1=(List) map.get("restDraweeList");
			List restDraweeList = new ArrayList();
			if(null != restDraweeList1)
			{
			for (int i = 0; i < restDraweeList1.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) restDraweeList1.get(i);
				if (covenantDetail.getIsNewEntry() != null) {
					if (!covenantDetail.getIsNewEntry().equals("Y")) {
						restDraweeList.add((OBLimitCovenant) covenantDetail);
					}
				} else {
					restDraweeList.add((OBLimitCovenant) covenantDetail);
				}
			}
			}
			List restBeneList1=(List) map.get("restBeneList");
			List restBeneList = new ArrayList();
			if(null != restBeneList1)
			{
			for (int i = 0; i < restBeneList1.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) restBeneList1.get(i);
				if(covenantDetail.getIsNewEntry() != null)
				{
				if(!covenantDetail.getIsNewEntry().equals("Y"))
				{
					restBeneList.add((OBLimitCovenant) covenantDetail);
				}
				}
				else
				{
					restBeneList.add((OBLimitCovenant) covenantDetail);
				}
			}
			}
			List restGoodList1=(List) map.get("restGoodList");
			List restGoodList = new ArrayList();
			if(null != restGoodList1)
			{
			for (int i = 0; i < restGoodList1.size(); i++)
			{
				ILimitCovenant covenantDetail= (ILimitCovenant) restGoodList1.get(i);
				if(covenantDetail.getIsNewEntry() != null)
				{
				if(!covenantDetail.getIsNewEntry().equals("Y"))
				{
					restGoodList.add((OBLimitCovenant) covenantDetail);
				}
				}
				else
				{
					restGoodList.add((OBLimitCovenant) covenantDetail);
				}
			}
			}
			resultMap.put("restCountryList",restCountryList);
			resultMap.put("restCurrencyList",restCurrencyList);
			resultMap.put("restBankList",restBankList);
			resultMap.put("restDrawerList",restDrawerList);
			resultMap.put("restDraweeList",restDraweeList);
			resultMap.put("restBeneList",restBeneList);
			resultMap.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST, restGoodList);
		}
		resultMap.put("lmtDetailForm",curLmt);
		resultMap.put("limitId", limitId);
		resultMap.put("customerID", customerID);
		
		resultMap.put("trxID",trxID);
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		
		return returnMap;
	}
	
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
				{"lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE},
			    { "limitId", "java.lang.String", REQUEST_SCOPE },
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE }, 
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
				{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST, List.class.getName() , SERVICE_SCOPE }
		});
	}

	public String[][] getResultDescriptor() {
		return (new String[][] { { "lmtTrxObj", "com.integrosys.cms.app.limit.trx.ILimitTrxValue", SERVICE_SCOPE },
				{ "lmtDetailForm", "java.lang.Object", FORM_SCOPE },
				{ "wip", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "trxID", "java.lang.String", SERVICE_SCOPE },
				{ "relationShipMgrName", "java.lang.String", REQUEST_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "fromEvent", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitId", "java.lang.String", SERVICE_SCOPE },
				{ "limitRef", "java.lang.String", REQUEST_SCOPE },
				{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
				{ "collateralMap", "java.util.HashMap", SERVICE_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				{ "inrValue", "java.lang.String", SERVICE_SCOPE },
				{ "customerID", "java.lang.String", REQUEST_SCOPE }, 
				{ "limitProfileID", "java.lang.String", REQUEST_SCOPE },
				{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
				{ "lmtId", "java.lang.String", SERVICE_SCOPE },
				{ "subPartyNameList", "java.util.List", SERVICE_SCOPE },
				{ "restCountryList", "java.util.List", SERVICE_SCOPE },
				{ "restCurrencyList", "java.util.List", SERVICE_SCOPE },
				{ "restBankList", "java.util.List", SERVICE_SCOPE },
				{ "restDrawerList", "java.util.List", SERVICE_SCOPE },
				{ "restDraweeList", "java.util.List", SERVICE_SCOPE },
				{ "restBeneList", "java.util.List", SERVICE_SCOPE },
				{ "currencyList", "java.util.List", REQUEST_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST, List.class.getName() , SERVICE_SCOPE },
				{"allowToDeleteFacility","java.lang.Boolean",REQUEST_SCOPE}, // Uma Khot:Don't Delete the facility if facility doc pending in case creation.
				{"facilityChklistDocPending","java.lang.Boolean",REQUEST_SCOPE},
				{"checkFacilityDocumentsIsPendingReceived","java.lang.Boolean",REQUEST_SCOPE},
				{"checkFacilityDocumentsIsReceived","java.lang.Boolean",REQUEST_SCOPE},
				{"collateralSet","java.util.HashSet",SERVICE_SCOPE},
				{"pendingPropertySecCount","java.lang.Boolean",REQUEST_SCOPE},
				{ "checklistIsActive", "java.lang.Boolean", SERVICE_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ "status", "java.lang.String", REQUEST_SCOPE },
				});
				
	}
	
}
