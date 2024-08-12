package com.integrosys.cms.ui.manualinput.aa;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class PrepareViewListOtherCovenant extends AbstractCommand implements ICommonEventConstant {

	public String[][] getParameterDescriptor() {
		return (new String[][] 
			{
			{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE },
			
			
			{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
			{ "theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE },
			{ "InitialLimitProfile", "com.integrosys.cms.app.limit.bus.OBLimitProfile", FORM_SCOPE },
			{ "aaID", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "session.aaID", "java.lang.String", SERVICE_SCOPE },
			{ "session.InitialLimitProfile", "java.lang.Object", SERVICE_SCOPE },
			{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
			{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
			{ "session.obLimitProfile", "com.integrosys.cms.app.limit.bus.ILimitProfile", SERVICE_SCOPE },
			{ "totalSanctionedAmtFac", "java.lang.String", SERVICE_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
			{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
			{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
			{ "preEvent", "java.lang.String", REQUEST_SCOPE }, { "countryList", "java.util.List", REQUEST_SCOPE },
			{ "orgList", "java.util.List", REQUEST_SCOPE },
			{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
			{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
			{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
			{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
			{ "regionName", "java.lang.String", SERVICE_SCOPE },
			{ "branchName", "java.lang.String", SERVICE_SCOPE },
			{ "customerID", "java.lang.String", SERVICE_SCOPE },
			
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][] {
			{ "limitProfileTrxVal", "com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue", SERVICE_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
			{ "timefrequency.labels", "java.util.Collection", REQUEST_SCOPE },
			{ "timefrequency.values", "java.util.Collection", REQUEST_SCOPE },
			{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
			{ "session.InitialLimitProfile", "java.lang.Object", SERVICE_SCOPE },
			{ "transactionHistoryList", "java.util.List", SERVICE_SCOPE },
			{ "migratedFlag", "java.lang.String", SERVICE_SCOPE },
			{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
			{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
			{ "totalSanctionedAmtFac", "java.lang.String", SERVICE_SCOPE },
			{ "regionName", "java.lang.String", SERVICE_SCOPE },
			{ "branchName", "java.lang.String", SERVICE_SCOPE },
			{ "customerId", "java.lang.String", SERVICE_SCOPE },
			{ "session.obLimitProfile", "com.integrosys.cms.app.limit.bus.ILimitProfile", SERVICE_SCOPE },
			{ "session.aaID", "java.lang.String", SERVICE_SCOPE }
		});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		////System.out.println("--------------------------------------------->In doExecute of OtherCovenantDetailsCommand");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event;
		String migratedFlag;
		migratedFlag = (String) map.get("migratedFlag");
		event = (String) map.get("event");
		resultMap.put("event",event);
		List transactionHistoryList;
		transactionHistoryList=(List) map.get("transactionHistoryList");
		resultMap.put("transactionHistoryList", transactionHistoryList);
		
		resultMap.put("migratedFlag", migratedFlag);
		
		String totalSanctionedAmtFac = (String) map.get("totalSanctionedAmtFac");
		//System.out.println("--------------------------------------------->branchName:----"+branchName);
		resultMap.put("totalSanctionedAmtFac", totalSanctionedAmtFac);
		
		//System.out.println("--------------------------------------------->event:----"+event);
		String preEvent = (String) map.get("preEvent");
		//System.out.println("--------------------------------------------->preEvent:----"+preEvent);
		resultMap.put("preEvent", preEvent);
		
		String relationShipMgrName = (String) map.get("relationShipMgrName");
		//System.out.println("--------------------------------------------->relationShipMgrName:----"+relationShipMgrName);
		resultMap.put("relationShipMgrName", relationShipMgrName);
		
		String regionName = (String) map.get("regionName");
		//System.out.println("--------------------------------------------->regionName:----"+regionName);
		resultMap.put("regionName", regionName);
		
		String branchName = (String) map.get("branchName");
		//System.out.println("--------------------------------------------->branchName:----"+branchName);
		resultMap.put("branchName", branchName);
		
		
		String aaID = (String) map.get("aaID");
		resultMap.put("aaID", aaID);
		
		String aaID2=(String) map.get("aaID");
		resultMap.put("branchName", aaID2);
		
		List creditAprrovalList;
		creditAprrovalList = (List) map.get("creditAprrovalList");
		//System.out.println("--------------------------------------------->creditAprrovalList:----"+creditAprrovalList);
		resultMap.put("creditAprrovalList", creditAprrovalList);
		
		List riskGradeList;
		riskGradeList = (List) map.get("riskGradeList");
		////System.out.println("--------------------------------------------->riskGradeList:----"+riskGradeList);
		resultMap.put("riskGradeList", riskGradeList);
		
		
		ILimitProfileTrxValue limitProfileTrxValNew = (ILimitProfileTrxValue) map.get("limitProfileTrxVal");
		////System.out.println("--------------------------------------------->limitProfileTrxValNew:----"+limitProfileTrxValNew);
		ICMSCustomer customerOB =(ICMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		String customerID = Long.toString((customerOB.getCustomerID()));
		////System.out.println("--------------------------------------------->customerID:----"+customerID);
		resultMap.put("customerID", customerID);
		Collection timefrequencylabels=(Collection) map.get("timefrequency.labels");
		Collection timefrequencyvalues=(Collection) map.get("timefrequency.values");
		ILimitProfile obLimitProfile = (ILimitProfile) map.get("InitialLimitProfile");
		////System.out.println("--------------------------------------------->obLimitProfile:----"+obLimitProfile);
		
		
		resultMap.put("session.InitialLimitProfile", obLimitProfile);
		resultMap.put("InitialLimitProfile", limitProfileTrxValNew.getLimitProfile());
		resultMap.put("timefrequency.labels",timefrequencylabels);
		resultMap.put("timefrequency.values",timefrequencyvalues);
		resultMap.put("IGlobalConstant.GLOBAL_CUSTOMER_OBJ",customerOB);
		resultMap.put("limitProfileTrxVal", limitProfileTrxValNew);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		//returnMap.put(key, value);
		return returnMap;
	}

	

}
