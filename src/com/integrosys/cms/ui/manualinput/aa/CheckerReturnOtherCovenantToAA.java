package com.integrosys.cms.ui.manualinput.aa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class CheckerReturnOtherCovenantToAA implements ICommonEventConstant, ICommand {

	@Override
	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event;
		event = (String) map.get("event");
		String mainEventIdentifier = (String) map.get("mainEventIdentifier");
		resultMap.put("mainEventIdentifier", mainEventIdentifier);
		
		List otherCovenantDetailsList;
		otherCovenantDetailsList = ((List) map.get("otherCovenantDetailsList") != null)
				? (List) map.get("otherCovenantDetailsList")
				: new ArrayList();
		resultMap.put("otherCovenantDetailsList", otherCovenantDetailsList);
		resultMap.put("event",event);
		
		String trxId = (String) map.get("TrxId");
		if(trxId==null||"".equals(trxId)){
			trxId=(String) map.get("session.TrxId");
		}
		resultMap.put("session.TrxId", trxId);
		
		String relationShipMgrName = (String) map.get("relationShipMgrName");
		//System.out.println("--------------------------------------------->relationShipMgrName:----"+relationShipMgrName);
		resultMap.put("relationShipMgrName", relationShipMgrName);
		
		String regionName = (String) map.get("regionName");
		//System.out.println("--------------------------------------------->regionName:----"+regionName);
		resultMap.put("regionName", regionName);
		
		String branchName = (String) map.get("branchName");
		//System.out.println("--------------------------------------------->branchName:----"+branchName);
		resultMap.put("branchName", branchName);
		
		HashMap timefrequenciesmap;
		timefrequenciesmap=  (HashMap) map.get("timefrequencies.map");
		////System.out.println("--------------------------------------------->timefrequenciesmap:----"+timefrequenciesmap);
		resultMap.put("timefrequencies.map", timefrequenciesmap);
		
		List creditAprrovalList;
		creditAprrovalList = (List) map.get("creditAprrovalList");
		////System.out.println("--------------------------------------------->creditAprrovalList:----"+creditAprrovalList);
		resultMap.put("creditAprrovalList", creditAprrovalList);
		
		List riskGradeList;
		riskGradeList = (List) map.get("riskGradeList");
		////System.out.println("--------------------------------------------->riskGradeList:----"+riskGradeList);
		resultMap.put("riskGradeList", riskGradeList);
		
		ILimitProfile obLimitProfile = (ILimitProfile) map.get("InitialLimitProfile");
		resultMap.put("InitialLimitProfile", obLimitProfile);
//		//System.out.println("--------------------------------------------->InitialLimitProfile:----"+obLimitProfile);
		ICMSCustomer customerOB =(ICMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		////System.out.println("--------------------------------------------->customerOB:----"+customerOB);
		resultMap.put("IGlobalConstant.GLOBAL_CUSTOMER_OBJ",customerOB);
		/*resultMap.put("otherCovenantDetailsList",otherCovenantDetailsList);*/
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
				
		return returnMap;
	}

	@Override
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			
			{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
			{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
			{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE },
			{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
			{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
			{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
			{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
			{ "session.TrxId", "java.lang.String", SERVICE_SCOPE },
			{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
			{ "regionName", "java.lang.String", SERVICE_SCOPE },
			{ "branchName", "java.lang.String", SERVICE_SCOPE },
			{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE }
			
	});

	}

	@Override
	public String[][] getResultDescriptor() {
		return (new String[][] { 
			{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
			{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
			{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE },
			{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
			{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
			{ "session.TrxId", "java.lang.String", SERVICE_SCOPE },
			{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
			{ "regionName", "java.lang.String", SERVICE_SCOPE },
			{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
			{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
			{ "branchName", "java.lang.String", SERVICE_SCOPE },
			{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE }
			
			
		});
	}

}
