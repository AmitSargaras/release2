
package com.integrosys.cms.ui.manualinput.aa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;



public class CheckerListOtherCovenantDisplayCommand extends AbstractCommand implements ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][] {
			{ "event", "java.lang.String", SERVICE_SCOPE },
			{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE },
			{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
			{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
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
			{ "branchName", "java.lang.String", SERVICE_SCOPE },
			
			
			{ "aaID", "java.lang.String", REQUEST_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE },
			{ "session.aaID", "java.lang.String", SERVICE_SCOPE },
			
			{ "camChecklistArray", "java.util.ArrayList", SERVICE_SCOPE }
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
			{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
			{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
			{ "timefrequencies.map", "java.util.HashMap", SERVICE_SCOPE },
			{ "InitialLimitProfile", "java.lang.Object", FORM_SCOPE },
			{ "event", "java.lang.String", REQUEST_SCOPE }, { "TrxId", "java.lang.String", REQUEST_SCOPE },
			{ "creditAprrovalList", "java.util.List", SERVICE_SCOPE },
			{ "riskGradeList", "java.util.List", SERVICE_SCOPE },
			{ "session.TrxId", "java.lang.String", SERVICE_SCOPE },
			{ "relationShipMgrName", "java.lang.String", SERVICE_SCOPE },
			{ "regionName", "java.lang.String", SERVICE_SCOPE },
			{ "branchName", "java.lang.String", SERVICE_SCOPE },
			{ "otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE },
			
			{ "aaID", "java.lang.String", REQUEST_SCOPE },
			{ "camChecklistArray", "java.util.ArrayList", SERVICE_SCOPE }
			});
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event;
		
		String mainEventIdentifier = (String) map.get("mainEventIdentifier");
		resultMap.put("mainEventIdentifier", mainEventIdentifier);
		OBOtherCovenant obothercovenant;
		/*List otherCovenantDetailsList = (List)map.get("otherCovenantDetailsList");*/
		event = (String) map.get("event");
		
		List otherCovenantDetailsList;
		otherCovenantDetailsList = ((List) map.get("otherCovenantDetailsList") != null)
				? (List) map.get("otherCovenantDetailsList")
				: new ArrayList();
				if (otherCovenantDetailsList != null) {
					if(!otherCovenantDetailsList.isEmpty())
					{
						for (int i = 0; i < otherCovenantDetailsList.size(); i++)
						{
							obothercovenant = (OBOtherCovenant) otherCovenantDetailsList.get(i);
							String othercovenantid = obothercovenant.getOtherCovenantId()+"";
							IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO) BeanHouse.get("otherCoveantDeatilsDAO");
							String finalmonitoringresp =  othercovenantdetailsdaoimpl.getOtherCovenantDetailsValuesStagingInString(obothercovenant.getOtherCovenantId()+"");
							obothercovenant.setMonitoringResponsibilityList1(finalmonitoringresp);
							String finalFacilityName = othercovenantdetailsdaoimpl
									.getOtherCovenantDetailsFacilityValuesStaging(
											othercovenantid);
							obothercovenant.setFinalfaciltyName(finalFacilityName);
						}
					}
					}		
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
		//System.out.println("--------------------------------------------->timefrequenciesmap:----"+timefrequenciesmap);
		resultMap.put("timefrequencies.map", timefrequenciesmap);
		
		List creditAprrovalList;
		creditAprrovalList = (List) map.get("creditAprrovalList");
	//	System.out.println("--------------------------------------------->creditAprrovalList:----"+creditAprrovalList);
		resultMap.put("creditAprrovalList", creditAprrovalList);
		
		List riskGradeList;
		riskGradeList = (List) map.get("riskGradeList");
		//System.out.println("--------------------------------------------->riskGradeList:----"+riskGradeList);
		resultMap.put("riskGradeList", riskGradeList);
		
		String aaID = (String) map.get("aaID");
		//System.out.println("--------------------------------------------->aaID:----"+aaID);
		 resultMap.put("aaID", aaID);
		
		ILimitProfile obLimitProfile = (ILimitProfile) map.get("InitialLimitProfile");
		resultMap.put("InitialLimitProfile", obLimitProfile);
		//System.out.println("--------------------------------------------->InitialLimitProfile:----"+obLimitProfile);
		ICMSCustomer customerOB =(ICMSCustomer)map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		//System.out.println("--------------------------------------------->customerOB:----"+customerOB);
		resultMap.put("IGlobalConstant.GLOBAL_CUSTOMER_OBJ",customerOB);
		/*resultMap.put("otherCovenantDetailsList",otherCovenantDetailsList);*/
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		//returnMap.put(key, value);
		return returnMap;
	}

}
