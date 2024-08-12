
package com.integrosys.cms.ui.manualinput.aa;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.base.techinfra.validation.Validator;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ErrorKeyMapper;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.customer.bus.OBBankingMethod;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.limit.bus.LimitException;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;

public class SaveOtherCovenantCommand extends AbstractCommand
		implements
			ICommonEventConstant {

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getParameterDescriptor() {
		return (new String[][]{
				/*
				 * { "covenantCondition", "java.lang.String", SERVICE_SCOPE },
				 */
				{"covenantType", "java.lang.String", REQUEST_SCOPE},
				/*
				 * { "covenantCondition", "java.lang.String", REQUEST_SCOPE },
				 */
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
				{"covenantType", "java.lang.String", SERVICE_SCOPE},
				{"targetDate", "java.lang.String", REQUEST_SCOPE},
				{"otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"monitoringResponsibilityList1", "java.util.List",
						SERVICE_SCOPE},
				{"monitoringResponsibilityList2", "java.util.List",
						SERVICE_SCOPE},
				{"covenant_Condition", "java.lang.String", REQUEST_SCOPE},
				{"Compiled", "java.lang.String", REQUEST_SCOPE},
				{"Advised", "java.lang.String", REQUEST_SCOPE},
				{"CovenantCategory", "java.lang.String", REQUEST_SCOPE},
				{"monitoringResponsibility", "java.lang.String", REQUEST_SCOPE},
				{"covenantremarks", "java.lang.String", REQUEST_SCOPE},
				{"covenantDescription", "java.lang.String", REQUEST_SCOPE},
				{"finalmonitoringResponsibility", "java.lang.String", REQUEST_SCOPE},
				{"finalFaciltyName", "java.lang.String", REQUEST_SCOPE},
				{"customerID", "java.lang.String", SERVICE_SCOPE},
				{"limitProfileTrxVal",
						"com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue",
						SERVICE_SCOPE},
				{IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE},
				{"timefrequency.labels", "java.util.Collection", REQUEST_SCOPE},
				{"timefrequency.values", "java.util.Collection", REQUEST_SCOPE},
				{"preEvent", "java.lang.String", REQUEST_SCOPE},
				{"countryList", "java.util.List", REQUEST_SCOPE},
				{"orgList", "java.util.List", REQUEST_SCOPE},
				{"sourceSystemList", "java.util.List", REQUEST_SCOPE},
				{"sourceSystemListID", "java.util.Collection", REQUEST_SCOPE},
				{"sourceSystemListValue", "java.util.Collection",
						REQUEST_SCOPE},
				{"InitialLimitProfile", "java.lang.Object", FORM_SCOPE},
				{"creditAprrovalList", "java.util.List", SERVICE_SCOPE},
				{"riskGradeList", "java.util.List", SERVICE_SCOPE},
				{"relationShipMgrName", "java.lang.String", SERVICE_SCOPE},
				{"regionName", "java.lang.String", SERVICE_SCOPE},
				{"branchName", "java.lang.String", SERVICE_SCOPE},
				{"displayId", "java.lang.String", REQUEST_SCOPE},
				{"displayId", "java.lang.String", SERVICE_SCOPE},

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

		return (new String[][]{
				{"covenant_Condition", "java.lang.String", SERVICE_SCOPE},
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
				{"targetDate", "java.lang.String", REQUEST_SCOPE},
				{"covenantType", "java.lang.String", REQUEST_SCOPE},
				{"covenant_Condition", "java.lang.String", REQUEST_SCOPE},
				{"Compiled", "java.lang.String", REQUEST_SCOPE},
				{"Advised", "java.lang.String", REQUEST_SCOPE},
				{"CovenantCategory", "java.lang.String", REQUEST_SCOPE},
				{"monitoringResponsibility", "java.lang.String", REQUEST_SCOPE},
				{"covenantremarks", "java.lang.String", REQUEST_SCOPE},
				{"covenantDescription", "java.lang.String", REQUEST_SCOPE},
				{"finalmonitoringResponsibility", "java.lang.String", REQUEST_SCOPE},
				{"finalFaciltyName", "java.lang.String", REQUEST_SCOPE},
				{"otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE},
				{"otherCovenantDetailsList", "java.util.List", REQUEST_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"limitProfileTrxVal",
						"com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue",
						SERVICE_SCOPE},
				{IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE},
				{"timefrequency.labels", "java.util.Collection", REQUEST_SCOPE},
				{"timefrequency.values", "java.util.Collection", REQUEST_SCOPE},
				{"preEvent", "java.lang.String", REQUEST_SCOPE},
				{"orgList", "java.util.List", REQUEST_SCOPE},
				{"sourceSystemList", "java.util.List", SERVICE_SCOPE},
				{"sourceSystemListID", "java.util.Collection", SERVICE_SCOPE},
				{"sourceSystemListValue", "java.util.Collection",
						SERVICE_SCOPE},
				{"InitialLimitProfile", "java.lang.Object", FORM_SCOPE},
				{"creditAprrovalList", "java.util.List", SERVICE_SCOPE},
				{"riskGradeList", "java.util.List", SERVICE_SCOPE},
				{"relationShipMgrName", "java.lang.String", SERVICE_SCOPE},
				{"regionName", "java.lang.String", SERVICE_SCOPE},
				{"branchName", "java.lang.String", SERVICE_SCOPE},
				{"customerID", "java.lang.String", SERVICE_SCOPE},

		});
	}

	public HashMap doExecute(HashMap map)
			throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event;
		String covenantType = null;
		String covenant_Condition = null;
		String targetDate = null;
		String Compiled = null;
		String Advised = null;
		String CovenantCategory = null;
		String monitoringResponsibility = null;
		String covenantremarks = null;
		String covenantDescription = null;
		String finalmonitoringResponsibility = null;
		String finalFaciltyName = null;
		String displayId;
		List otherCovenantDetailsList;
		List otherCovenantDetailsDisplayList;
		ILimitDAO dao = LimitDAOFactory.getDAO();
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
		IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO)BeanHouse.get("otherCoveantDeatilsDAO");
		String errorCode = null;
		
		displayId = (String) map.get("displayId");
		event = (String) map.get("event");
		
		String mainEventIdentifier = (String) map.get("mainEventIdentifier");
		resultMap.put("mainEventIdentifier", mainEventIdentifier);

		// System.out.println("--------------------------------------------->event:----"+event);
		resultMap.put("event", event);
		resultMap.put("displayId", displayId);
		if (event.equals("save_other_covenant") || event.equals("save_other_covenant_edit")) {
			targetDate = (String) map.get("targetDate");
			covenantremarks = (String) map.get("covenantremarks");
			covenantDescription = (String) map.get("covenantDescription");
			OBOtherCovenant obothercovenant = new OBOtherCovenant();
			HashMap exceptionMap = new HashMap();
			if(targetDate == null || targetDate.equals(""))
			{
				exceptionMap.put("targetDate",new ActionMessage("error.string.mandatory"));
			}
			if(covenantremarks!=null && !covenantremarks.equalsIgnoreCase(""))
			{
				if (covenantremarks.length() > 200) {
					exceptionMap.put("covenantremarks",new ActionMessage("error.string.length200"));
				}
			}
			
			if(covenantDescription!=null && !covenantDescription.equalsIgnoreCase(""))
			{
				if (covenantDescription.length() > 200) {
					exceptionMap.put("covenantDescription",new ActionMessage("error.string.length200"));
				}
			}
			CovenantCategory = (String) map.get("CovenantCategory");
			if(CovenantCategory.equalsIgnoreCase("Facility"))
			{
			finalFaciltyName = (String) map.get("finalFaciltyName");
			if((finalFaciltyName == null || ("").equals(finalFaciltyName)))
			{
				exceptionMap.put("FacilityCCerror",new ActionMessage("error.string.mandatory"));
			}
			}
			if(exceptionMap.size()>0)
			{				
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
			return returnMap;
			}
			otherCovenantDetailsList = ((List) map
					.get("otherCovenantDetailsList") != null)
							? (List) map.get("otherCovenantDetailsList")
							: new ArrayList();
			event = (String) map.get("event");
		
			covenantType = (String) map.get("covenantType");
			covenant_Condition = (String) map.get("covenant_Condition");
			

			Compiled = (String) map.get("Compiled");
			Advised = (String) map.get("Advised");
			
			monitoringResponsibility = (String) map
					.get("monitoringResponsibility");
			covenantremarks = (String) map.get("covenantremarks");
			covenantDescription = (String) map.get("covenantDescription");
			finalmonitoringResponsibility = (String) map.get("finalmonitoringResponsibility");
			if (finalmonitoringResponsibility != null || finalmonitoringResponsibility != "") 
			{
				obothercovenant.setMonitoringResponsibilityList1(finalmonitoringResponsibility);
			}
			
			if(CovenantCategory.equalsIgnoreCase("Facility"))
			{
			finalFaciltyName = (String) map.get("finalFaciltyName");
			if (finalFaciltyName != null || finalFaciltyName != "") 
			{
				obothercovenant.setFinalfaciltyName(finalFaciltyName);
			}
			}
			/*String[] MRArr = finalmonitoringResponsibility.split(",");
			ArrayList monRespList = new ArrayList();
			for(int i=0; i<MRArr.length;i++) {
				String[] MRArr1 =MRArr[i].split("-");
				monRespList.add(MRArr1[0]);
			}
			for(int i=0;i<monRespList.size();i++) {
				OBBankingMethod obj = new OBBankingMethod();
				obj.setBankType((String)monRespList.get(i));
				obj.setLEID(Long.parseLong(trx.getStagingReferenceID()));
				obj.setCustomerIDForBankingMethod(obCustomer.getCifId());
				obj.setStatus("ACTIVE");
				//bankingMethodDAOImpl.insertBankingMethodCustStage(obj);				
				
			}
			*/
			System.out.println("finalmonitoringResponsibility------------------"+finalmonitoringResponsibility);
			/*obothercovenant.setOtherCovenantId(Long.parseLong(othercovenantID));*/
			obothercovenant.setOtherCovenantId(Long.parseLong(othercovenantdetailsdaoimpl.getOtherCovenantDetailsStagingIdFromSeq()));
			if (CovenantCategory != null || CovenantCategory != "") {
				obothercovenant.setCovenantCategory(CovenantCategory);
			}
			if (Advised != null || Advised != "") {
				obothercovenant.setAdvised(Advised);
			}
			if (Compiled != null || Compiled != "") {
				obothercovenant.setCompiled(Compiled);
			}
			if (covenantremarks != null || covenantremarks != "") {
				obothercovenant.setRemarks(covenantremarks);
			}
			if (covenantDescription != null || covenantDescription != "") {
				obothercovenant.setCovenantDescription(covenantDescription);
			}
			if (covenantType != null || covenantType != "") {
				obothercovenant.setCovenantType(covenantType);
			}
		
			if (covenant_Condition != null || covenant_Condition != "") {
				obothercovenant.setCovenantCondition(covenant_Condition);
			}

			if (targetDate != null || targetDate != "") {
				obothercovenant.setTargetDate(targetDate);
			}
			String seqNo = null;
			dao = LimitDAOFactory.getDAO();
			try {
				seqNo = dao.getSeqNoForOtherCovenant();
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			obothercovenant.setPreviousStagingId(seqNo);
			obothercovenant.setStatus("ACTIVE");
			
			if (targetDate != null || covenant_Condition != null
					|| covenantType != null) {
				otherCovenantDetailsList.add(obothercovenant);
			}
			
			resultMap.put("otherCovenantDetailsList", otherCovenantDetailsList);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			// returnMap.put(key, value);

		} 
		// FOR EDITED OTHER COVENANT RECORDS
		else if (event.equals("save_edited_other_covenant")) {
			otherCovenantDetailsList = ((List) map
					.get("otherCovenantDetailsList") != null)
							? (List) map.get("otherCovenantDetailsList")
							: new ArrayList();
			OBOtherCovenant obothercovenant = new OBOtherCovenant();
			for (int i = 0; i < otherCovenantDetailsList.size(); i++) {
				
				
				
				obothercovenant = (OBOtherCovenant) otherCovenantDetailsList
						.get(i);
				obothercovenant.setOtherCovenantId(Long.parseLong(othercovenantdetailsdaoimpl.getOtherCovenantDetailsStagingIdFromSeq()));
				if(obothercovenant.getPreviousStagingId().equals(displayId))
				{
				covenantType = (String) map.get("covenantType");
				covenant_Condition = (String) map.get("covenant_Condition");
				targetDate = (String) map.get("targetDate");

				Compiled = (String) map.get("Compiled");
				Advised = (String) map.get("Advised");
				CovenantCategory = (String) map.get("CovenantCategory");
				monitoringResponsibility = (String) map
						.get("monitoringResponsibility");
				
				covenantremarks = (String) map.get("covenantremarks");
				covenantDescription = (String) map.get("covenantDescription");
				finalmonitoringResponsibility = (String) map.get("finalmonitoringResponsibility");
				if (finalmonitoringResponsibility != null) 
				{
					if(!finalmonitoringResponsibility.isEmpty())
					obothercovenant.setMonitoringResponsibilityList1(finalmonitoringResponsibility);
				}
				
				if(CovenantCategory.equalsIgnoreCase("Facility"))
				{
				finalFaciltyName = (String) map.get("finalFaciltyName");
				if (finalFaciltyName != null || finalFaciltyName != "") 
				{
					obothercovenant.setFinalfaciltyName(finalFaciltyName);
				}
				}
				if (CovenantCategory != null || CovenantCategory != "") {
					obothercovenant.setCovenantCategory(CovenantCategory);
				}
				if (Advised != null || Advised != "") {
					obothercovenant.setAdvised(Advised);
				}
				if (Compiled != null || Compiled != "") {
					obothercovenant.setCompiled(Compiled);
				}
				if (covenantremarks != null || covenantremarks != "") {
					obothercovenant.setRemarks(covenantremarks);
				}
				if (covenantDescription != null || covenantDescription != "") {
					obothercovenant.setCovenantDescription(covenantDescription);
				}
				if (covenantType != null || covenantType != "") {
					obothercovenant.setCovenantType(covenantType);
				}

				if (covenant_Condition != null || covenant_Condition != "") {
					obothercovenant.setCovenantCondition(covenant_Condition);
				}
				obothercovenant.setStatus("ACTIVE");
				if (targetDate != null || targetDate != "") {
					obothercovenant.setTargetDate(targetDate);
				}
			}
		}

	}/* else if (event.equals("save_other_covenant_edit")) {
			otherCovenantDetailsList = ((List) map
					.get("otherCovenantDetailsList") != null)
							? (List) map.get("otherCovenantDetailsList")
							: new ArrayList();

			OBOtherCovenant obothercovenant = new OBOtherCovenant();
			covenantType = (String) map.get("covenantType");
			covenant_Condition = (String) map.get("covenant_Condition");
			targetDate = (String) map.get("targetDate");

			Compiled = (String) map.get("Compiled");
			Advised = (String) map.get("Advised");
			CovenantCategory = (String) map.get("CovenantCategory");
			monitoringResponsibility = (String) map
					.get("monitoringResponsibility");
			covenantremarks = (String) map.get("covenantremarks");
			covenantDescription = (String) map.get("covenantDescription");
			if (CovenantCategory != null || CovenantCategory != "") {
				obothercovenant.setCovenantCategory(CovenantCategory);
			}
			if (Advised != null || Advised != "") {
				obothercovenant.setAdvised(Advised);
			}
			if (Compiled != null || Compiled != "") {
				obothercovenant.setCompiled(Compiled);
			}
			if (covenantremarks != null || covenantremarks != "") {
				obothercovenant.setRemarks(covenantremarks);
			}
			if (covenantDescription != null || covenantDescription != "") {
				obothercovenant.setCovenantDescription(covenantDescription);
			}
			if (covenantType != null || covenantType != "") {
				obothercovenant.setCovenantType(covenantType);
			}

			if (covenant_Condition != null || covenant_Condition != "") {
				obothercovenant.setCovenantCondition(covenant_Condition);
			}

			if (targetDate != null || targetDate != "") {
				obothercovenant.setTargetDate(targetDate);
			}
			if (targetDate != null || covenant_Condition != null
					|| covenantType != null) {
				otherCovenantDetailsList.add(obothercovenant);
			}
			resultMap.put("otherCovenantDetailsList", otherCovenantDetailsList);

			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			// returnMap.put(key, value);

		}*/else if (event.equals("other_covenant_deleted")) 
		{
			otherCovenantDetailsList = ((List) map
					.get("otherCovenantDetailsList") != null)
							? (List) map.get("otherCovenantDetailsList")
							: new ArrayList();
			otherCovenantDetailsDisplayList	= otherCovenantDetailsList;
			
			OBOtherCovenant obothercovenant = new OBOtherCovenant();
			
			for (int i = 0; i < otherCovenantDetailsList.size(); i++) {

				obothercovenant = (OBOtherCovenant) otherCovenantDetailsList
						.get(i);
				
				/*otherCovenantDetailsDisplayList.removeIf(n -> (obothercovenant.getStagingRefid() == null));*/
				if(obothercovenant.getPreviousStagingId().equals(displayId))
				{
					obothercovenant.setStatus("INACTIVE");
					
					/*otherCovenantDetailsList.remove(obothercovenant);*/
				}
				/*if(obothercovenant.getStagingRefid() != null && !obothercovenant.getStagingRefid().isEmpty())
				{
				List OtherCovenantValues = othercovenantdetailsdaoimpl.getOtherCovenantDetailsValuesStaging(obothercovenant.getOtherCovenantId()+"");
				if (OtherCovenantValues != null) {
					for (int m = 0; m < OtherCovenantValues.size(); m++) {
						OBOtherCovenant ob1 = (OBOtherCovenant) OtherCovenantValues.get(m);
						ob1.setStatus("INACTIVE");
					}
				}
				}*/
				
				
			}
			resultMap.put("otherCovenantDetailsList", otherCovenantDetailsList);
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		}
		else {
			otherCovenantDetailsList = (List) map
					.get("otherCovenantDetailsList");
			resultMap.put("otherCovenantDetailsList", otherCovenantDetailsList);
		}
		String preEvent = (String) map.get("preEvent");
		resultMap.put("preEvent", preEvent);
		// System.out.println("--------------------------------------------->preEvent:----"+preEvent);

		String relationShipMgrName = (String) map.get("relationShipMgrName");
		// System.out.println("--------------------------------------------->relationShipMgrName:----"+relationShipMgrName);
		resultMap.put("relationShipMgrName", relationShipMgrName);

		String regionName = (String) map.get("regionName");
		// System.out.println("--------------------------------------------->regionName:----"+regionName);
		resultMap.put("regionName", regionName);

		String branchName = (String) map.get("branchName");
		// System.out.println("--------------------------------------------->branchName:----"+branchName);
		resultMap.put("branchName", branchName);

		String customerID = (String) map.get("customerID");
		// System.out.println("--------------------------------------------->customerID:----"+customerID);
		resultMap.put("customerID", customerID);

		List creditAprrovalList;
		creditAprrovalList = (List) map.get("creditAprrovalList");
		// System.out.println("--------------------------------------------->creditAprrovalList:----"+creditAprrovalList);
		resultMap.put("creditAprrovalList", creditAprrovalList);

		List riskGradeList;
		riskGradeList = (List) map.get("riskGradeList");
		// System.out.println("--------------------------------------------->riskGradeList:----"+riskGradeList);
		resultMap.put("riskGradeList", riskGradeList);

		ILimitProfileTrxValue limitProfileTrxValNew = (ILimitProfileTrxValue) map
				.get("limitProfileTrxVal");
		// System.out.println("--------------------------------------------->limitProfileTrxValNew:----"+limitProfileTrxValNew);
		ICMSCustomer customerOB = (ICMSCustomer) map
				.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		Collection timefrequencylabels = (Collection) map
				.get("timefrequency.labels");
		Collection timefrequencyvalues = (Collection) map
				.get("timefrequency.values");
		ILimitProfile obLimitProfile = (ILimitProfile) map
				.get("InitialLimitProfile");
		OBTrxContext trxContext = (OBTrxContext) map.get("theOBTrxContext");
		// System.out.println("--------------------------------------------->obLimitProfile:----"+obLimitProfile);

		resultMap.put("InitialLimitProfile", obLimitProfile);
		resultMap.put("timefrequency.labels", timefrequencylabels);
		resultMap.put("timefrequency.values", timefrequencyvalues);
		resultMap.put("IGlobalConstant.GLOBAL_CUSTOMER_OBJ", customerOB);
		resultMap.put("limitProfileTrxVal", limitProfileTrxValNew);

		resultMap.put("theOBTrxContext", trxContext);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
		
	}

}
