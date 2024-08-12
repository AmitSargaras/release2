package com.integrosys.cms.ui.manualinput.line.covenant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.OBLineCovenant;
import com.integrosys.cms.app.limit.bus.ILimit;
import com.integrosys.cms.app.limit.trx.ILimitTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.covenant.ILmtCovenantConstants;

public class BackToManualInputLimitCommand extends AbstractCommand implements ILmtCovenantConstants,ILineCovenantConstants {

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
		HashMap<String,String> covenantMap = (HashMap) map.get("covenantMap");
		
		if(covenantMap!=null) {
			resultMap.put("facCat", (String)covenantMap.get("facCat"));
			resultMap.put("fundedAmount", (String)covenantMap.get("fundedAmount"));
			resultMap.put("nonFundedAmount", (String)covenantMap.get("nonFundedAmount"));
			resultMap.put("memoExposer", (String)covenantMap.get("memoExposer"));
			resultMap.put("sanctionedLimit", (String)covenantMap.get("sanctionedLimit"));
		}

		ILimit curLmt = null;
		if( !("track".equals(fromEvent) && "process".equals(fromEvent) && "process_update".equals(fromEvent) && "prepare_close".equals(fromEvent) ) ){
			curLmt = lmtTrxObj.getStagingLimit();
		}else {
			curLmt = lmtTrxObj.getLimit();

		}
       
		
		if("ok_covenant_detail_update_status".equals(event)
				|| "ok_covenant_detail_close".equals(event)
				|| "cancel".equals(event) 
				) {
			if(!(lmtTrxObj.getLimit()==null))
			{
			curLmt = lmtTrxObj.getLimit();
			lmtTrxObj.setStagingLimit(curLmt);
			}
		}
		//List restCountryList1=(List) map.get(SESSION_COUNTRY_LIST_LINE);
		
		if("cancel_covenant_detail".equals(event))
		{
			List<OBLineCovenant> restCountryListForLine= new ArrayList();
			List<OBLineCovenant> restCurrencyListForLine = new ArrayList();
			List<OBLineCovenant> restBankListForLine = new ArrayList();
			List<OBLineCovenant> restDrawerListForLine = new ArrayList();
			List<OBLineCovenant> restDraweeListForLine = new ArrayList();
			List<OBLineCovenant> restBeneListForLine = new ArrayList();
			List<OBLineCovenant> restGoodsRestrictionListForLine = new ArrayList();
			resultMap.put(SESSION_COUNTRY_LIST_LINE, restCountryListForLine);
			resultMap.put(SESSION_CURRENCY_LIST_LINE, restCurrencyListForLine);
			resultMap.put(SESSION_BANK_RESTRICTION_LIST_LINE, restBankListForLine);
			resultMap.put(SESSION_DRAWER_LIST_LINE, restDrawerListForLine);
			resultMap.put(SESSION_DRAWEE_LIST_LINE, restDraweeListForLine);
			resultMap.put(SESSION_BENE_LIST_LINE, restBeneListForLine);
			resultMap.put(SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, restGoodsRestrictionListForLine);
		}
		
		resultMap.put("lmtTrxObj", lmtTrxObj);
		resultMap.put("lmtDetailForm",curLmt);
		resultMap.put("limitId", limitId);
		resultMap.put("customerID", customerID);
		
		
		
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
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ "trxID", "java.lang.String", REQUEST_SCOPE }, { "event", "java.lang.String", REQUEST_SCOPE },
				{ "sessionCriteria", "java.lang.String", REQUEST_SCOPE },
				{ SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BANK_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_DROPDOWN_COUNTRY_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_CURRENCY_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_BANK_RESTRICTION_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_GOODS_PARENT_LIST, List.class.getName(), SERVICE_SCOPE },
                { SESSION_COVENANT_GOODS_RESTRICTION_LIST, List.class.getName() , SERVICE_SCOPE },
				{ "covenantMap", Map.class.getName(), SERVICE_SCOPE },
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
				{ "currencyList", "java.util.List", REQUEST_SCOPE },
				{ SESSION_COUNTRY_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_CURRENCY_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BANK_RESTRICTION_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWER_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DRAWEE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_BENE_LIST_LINE, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_COVENANT_GOODS_RESTRICTION_LIST_LINE, List.class.getName() , SERVICE_SCOPE },
				{ SESSION_DROPDOWN_COUNTRY_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_CURRENCY_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_BANK_RESTRICTION_LIST, List.class.getName(), SERVICE_SCOPE },
				{ SESSION_DROPDOWN_GOODS_PARENT_LIST, List.class.getName(), SERVICE_SCOPE },
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
				{ "facCat", "java.lang.String", REQUEST_SCOPE },
				{ "fundedAmount", "java.lang.String", REQUEST_SCOPE },
				{ "nonFundedAmount", "java.lang.String", REQUEST_SCOPE  },
				{ "memoExposer", "java.lang.String", REQUEST_SCOPE },
				{ "sanctionedLimit", "java.lang.String", REQUEST_SCOPE },
				});
				
	}
	
}
