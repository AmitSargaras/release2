/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:$
 */
package com.integrosys.cms.ui.manualinput.aa;

import java.util.ArrayList;
import java.util.Collection;
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
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;

/**
 * Describe this class. Purpose: for maker to add new AA Description: command
 * that let the maker to add new AA Value to the database
 * 
 * @author $Author: Jerlin$<br>
 * @version $Revision:$
 * @since $Date:$ Tag: $Name$
 */

public class OtherCovenantDetailsCommand extends AbstractCommand
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
				{IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"aaID", "java.lang.String", REQUEST_SCOPE},
				{"camChecklistArray", "java.util.ArrayList", SERVICE_SCOPE},
				{"otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE},
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
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
				{"customerID", "java.lang.String", SERVICE_SCOPE},

				{"session.TrxId", "java.lang.String", SERVICE_SCOPE},
				{"TrxId", "java.lang.String", REQUEST_SCOPE},
				{"indexChange", "java.lang.String", REQUEST_SCOPE},});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */

	public String[][] getResultDescriptor() {

		return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE},
				{"covenantCondition", "java.lang.String", SERVICE_SCOPE},
				{"covenantType", "java.lang.String", REQUEST_SCOPE},
				{"covenantCondition", "java.lang.String", REQUEST_SCOPE},
				{"covenantType", "java.lang.String", SERVICE_SCOPE},
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
				{"otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE},
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
				{"customerID", "java.lang.String", SERVICE_SCOPE},
				{"aaID", "java.lang.String", REQUEST_SCOPE},
				{"camChecklistArray", "java.util.ArrayList", SERVICE_SCOPE},
				{"session.TrxId", "java.lang.String", SERVICE_SCOPE},
				{"TrxId", "java.lang.String", REQUEST_SCOPE},
				{"indexChange", "java.lang.String", REQUEST_SCOPE},});
	}

	public HashMap doExecute(HashMap map)
			throws CommandProcessingException, CommandValidationException {
		//// System.out.println("--------------------------------------------->In
		//// doExecute of OtherCovenantDetailsCommand");
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event;
		OBOtherCovenant obothercovenant;
		event = (String) map.get("event");
		resultMap.put("event", event);
		String mainEventIdentifier = (String) map.get("mainEventIdentifier");
		resultMap.put("mainEventIdentifier", mainEventIdentifier);
		String indexChange;
		String trxId;
		String trxId2;

		if ("".equals(event)) {
			indexChange = (String) map.get("indexChange");
			resultMap.put("indexChange", indexChange);
			System.out.println("indexChange-----------" + indexChange);

			trxId = (String) map.get("TrxId");
			resultMap.put("trxId", trxId);
			System.out.println("trxId-----------" + trxId);

			trxId2 = (String) map.get("session.TrxId");
			resultMap.put("session.TrxId", trxId2);
			System.out.println("session.TrxId-----------" + trxId2);

		}
		// System.out.println("--------------------------------------------->event:----"+event);
		String preEvent = (String) map.get("preEvent");
		// System.out.println("--------------------------------------------->preEvent:----"+preEvent);
		resultMap.put("preEvent", preEvent);

		String relationShipMgrName = (String) map.get("relationShipMgrName");
		// System.out.println("--------------------------------------------->relationShipMgrName:----"+relationShipMgrName);
		resultMap.put("relationShipMgrName", relationShipMgrName);

		String regionName = (String) map.get("regionName");
		// System.out.println("--------------------------------------------->regionName:----"+regionName);
		resultMap.put("regionName", regionName);

		String aaID = (String) map.get("aaID");
		// System.out.println("--------------------------------------------->aaID:----"+aaID);
		resultMap.put("aaID", aaID);
		String branchName = (String) map.get("branchName");
		// System.out.println("--------------------------------------------->branchName:----"+branchName);
		resultMap.put("branchName", branchName);

		List creditAprrovalList;
		creditAprrovalList = (List) map.get("creditAprrovalList");
		// System.out.println("--------------------------------------------->creditAprrovalList:----"+creditAprrovalList);
		resultMap.put("creditAprrovalList", creditAprrovalList);

		List riskGradeList;
		riskGradeList = (List) map.get("riskGradeList");
		//// System.out.println("--------------------------------------------->riskGradeList:----"+riskGradeList);
		resultMap.put("riskGradeList", riskGradeList);

		ILimitProfileTrxValue limitProfileTrxValNew = (ILimitProfileTrxValue) map
				.get("limitProfileTrxVal");
		//// System.out.println("--------------------------------------------->limitProfileTrxValNew:----"+limitProfileTrxValNew);
		ICMSCustomer customerOB = (ICMSCustomer) map
				.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
		String customerID = Long.toString((customerOB.getCustomerID()));
		//// System.out.println("--------------------------------------------->customerID:----"+customerID);
		resultMap.put("customerID", customerID);
		Collection timefrequencylabels = (Collection) map
				.get("timefrequency.labels");
		Collection timefrequencyvalues = (Collection) map
				.get("timefrequency.values");
		ILimitProfile obLimitProfile = (ILimitProfile) map
				.get("InitialLimitProfile");
		//// System.out.println("--------------------------------------------->obLimitProfile:----"+obLimitProfile);

		resultMap.put("InitialLimitProfile", obLimitProfile);
		resultMap.put("timefrequency.labels", timefrequencylabels);
		resultMap.put("timefrequency.values", timefrequencyvalues);
		resultMap.put("IGlobalConstant.GLOBAL_CUSTOMER_OBJ", customerOB);
		resultMap.put("limitProfileTrxVal", limitProfileTrxValNew);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		if ("view_list_other_covenants".equals(event)
				|| "list_other_covenants_edit".equals(event) || "check_list_other_covenants_view".equals(event))  {
			IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO) BeanHouse
					.get("otherCoveantDeatilsDAO");
			List otherCovenantDetailsList = (List) map
					.get("otherCovenantDetailsList");
			if (limitProfileTrxValNew != null) {
				String refid = limitProfileTrxValNew.getReferenceID();
				if (refid != null) {
					try {
						otherCovenantDetailsList = ((List) map
								.get("otherCovenantDetailsList") != null)
										? (List) map
												.get("otherCovenantDetailsList")
										: othercovenantdetailsdaoimpl
												.getOtherCovenantDetailsActual(
														refid);
						System.out.println(
								"otherCovenantDetailsList----------------------->>>>>>>"
										+ otherCovenantDetailsList);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			if (otherCovenantDetailsList != null) {
				if (!otherCovenantDetailsList.isEmpty()) {
					for (int i = 0; i < otherCovenantDetailsList.size(); i++) {
						obothercovenant = (OBOtherCovenant) otherCovenantDetailsList
								.get(i);
						String othercovenantid = obothercovenant
								.getOtherCovenantId() + "";
						if (obothercovenant.getStatus() != null) {
							if (obothercovenant.getStatus().equalsIgnoreCase("ACTIVE") && obothercovenant.getMonitoringResponsibilityList1() == null) {
								String finalmonitoringresp = othercovenantdetailsdaoimpl
										.getOtherCovenantDetailsValuesActual(
												othercovenantid);
								obothercovenant
										.setMonitoringResponsibilityList1(
												finalmonitoringresp);
							}
						}
						if (obothercovenant.getStatus() != null) {
							if (obothercovenant.getStatus().equalsIgnoreCase("ACTIVE") && obothercovenant.getFinalfaciltyName() == null) {
								String finalFacilityName = othercovenantdetailsdaoimpl
										.getOtherCovenantDetailsFacilityValuesActual(
												othercovenantid);
								obothercovenant
										.setFinalfaciltyName(finalFacilityName);
							}
						}
					}
				}
			}
			if("maker_close_aadetail".equals(mainEventIdentifier))
			{
				if (otherCovenantDetailsList != null) {
					if (!otherCovenantDetailsList.isEmpty()) {
						for (int i = 0; i < otherCovenantDetailsList.size(); i++) {
							obothercovenant = (OBOtherCovenant) otherCovenantDetailsList
									.get(i);
							String othercovenantid = obothercovenant
									.getOtherCovenantId() + "";
							if (obothercovenant.getStatus() != null) {
								if (obothercovenant.getStatus().equalsIgnoreCase("ACTIVE") && (obothercovenant.getMonitoringResponsibilityList1() == null || obothercovenant.getMonitoringResponsibilityList1().isEmpty())) {
									String finalmonitoringresp = othercovenantdetailsdaoimpl
											.getOtherCovenantDetailsValuesStagingInString(
													othercovenantid);
									obothercovenant
											.setMonitoringResponsibilityList1(
													finalmonitoringresp);
								}
							}
							if (obothercovenant.getStatus() != null) {
								if (obothercovenant.getStatus().equalsIgnoreCase("ACTIVE") && obothercovenant.getFinalfaciltyName() == null) {
									String finalFacilityName = othercovenantdetailsdaoimpl
											.getOtherCovenantDetailsFacilityValuesStaging(
													othercovenantid);
									obothercovenant
											.setFinalfaciltyName(finalFacilityName);
								}
							}
						}
					}
				}
			}
			resultMap.put("otherCovenantDetailsList", otherCovenantDetailsList);
		}
		// returnMap.put(key, value);
		return returnMap;
	}

}
