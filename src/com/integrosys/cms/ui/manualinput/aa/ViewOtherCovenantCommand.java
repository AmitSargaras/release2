package com.integrosys.cms.ui.manualinput.aa;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.OBOtherCovenant;
import com.integrosys.cms.app.limit.trx.ILimitProfileTrxValue;
import com.integrosys.cms.app.manualinput.limit.proxy.SBMILmtProxy;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.manualinput.limit.MILimitUIHelper;
import com.integrosys.cms.ui.manualinput.limit.othercovenantsdetails.IOtherCovenantDetailsDAO;

public class ViewOtherCovenantCommand extends AbstractCommand
		implements
			ICommonEventConstant {

	@Override
	public HashMap doExecute(HashMap map) throws CommandValidationException,
			CommandProcessingException, AccessDeniedException {

		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		String event;
		event = (String) map.get("event");
		resultMap.put("event", event);
		String indexChange;
		String trxId;
		String trxId2;
		List facilityNamesList = new ArrayList();
		MILimitUIHelper helper = new MILimitUIHelper();
		SBMILmtProxy proxy = helper.getSBMILmtProxy();
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
		String displayId = (String) map.get("displayId");
		System.out.println(
				"--------------------------------------------->displayId:----"
						+ displayId);
		resultMap.put("displayId", displayId);

		String preEvent = (String) map.get("preEvent");
		// System.out.println("--------------------------------------------->preEvent:----"+preEvent);
		resultMap.put("preEvent", preEvent);
		
		
		String mainEventIdentifier = (String) map.get("mainEventIdentifier");
		resultMap.put("mainEventIdentifier", mainEventIdentifier);

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
		
		if (limitProfileTrxValNew != null) {
			String cmsLmtProID = limitProfileTrxValNew.getReferenceID();
			try {
				facilityNamesList = proxy.getFacilityNameByAAId(cmsLmtProID);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (facilityNamesList != null || !facilityNamesList.isEmpty()) {
				List facilityNamesListTemp = new ArrayList();
				try {

					for (int i = 0; i < facilityNamesList.size(); i++) {
						List tempList = (List) facilityNamesList.get(i);

						String id1 = (String) tempList.get(1);
						String val1 = (String) tempList.get(0);

						LabelValueBean lvBean1 = new LabelValueBean(val1, id1);
						facilityNamesListTemp.add(lvBean1);
					}
				} catch (Exception ex) {
				}
				facilityNamesList = CommonUtil
						.sortDropdown(facilityNamesListTemp);

			}
		}
		resultMap.put("monitoringResponsibilityList1",
				getmonitoringResponsibilityList());
		resultMap.put("monitoringResponsibilityList2", new ArrayList());
		resultMap.put("facilityNamesList", facilityNamesList);
		resultMap.put("InitialLimitProfile", obLimitProfile);
		resultMap.put("timefrequency.labels", timefrequencylabels);
		resultMap.put("timefrequency.values", timefrequencyvalues);
		resultMap.put("IGlobalConstant.GLOBAL_CUSTOMER_OBJ", customerOB);
		resultMap.put("limitProfileTrxVal", limitProfileTrxValNew);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);

		IOtherCovenantDetailsDAO othercovenantdetailsdaoimpl = (IOtherCovenantDetailsDAO) BeanHouse
				.get("otherCoveantDeatilsDAO");
		List otherCovenantDetailsList = null;
		OBOtherCovenant obothercovenant = new OBOtherCovenant();
		if (otherCovenantDetailsList != null) {
			for (int i = 0; i < otherCovenantDetailsList.size(); i++) {

				obothercovenant = (OBOtherCovenant) otherCovenantDetailsList
						.get(i);
				if (displayId.equals(obothercovenant.getPreviousStagingId())) {
					String valueid = obothercovenant.getOtherCovenantId() + "";
					List otherCovenantDetailsValuesList=othercovenantdetailsdaoimpl
							.getOtherCovenantDetailsValuesStaging(valueid);
					
					if(otherCovenantDetailsValuesList != null)
					{
					resultMap.put("otherCovenantDetailsValuesList", otherCovenantDetailsValuesList);
					}
					
				}
			}
		}
		otherCovenantDetailsList = (List) map.get("otherCovenantDetailsList");

		resultMap.put("otherCovenantDetailsList", otherCovenantDetailsList);

		// returnMap.put(key, value);
		return returnMap;

	}

	@Override
	public String[][] getParameterDescriptor() {
		return (new String[][]{
				{IGlobalConstant.USER_TEAM,
						"com.integrosys.component.bizstructure.app.bus.ITeam",
						GLOBAL_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"aaID", "java.lang.String", REQUEST_SCOPE},
				{"camChecklistArray", "java.util.ArrayList", SERVICE_SCOPE},
				{"otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE},
				
				{"displayId", "java.lang.String", REQUEST_SCOPE},
				{"limitProfileTrxVal",
						"com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue",
						SERVICE_SCOPE},
				{IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE},
				{"timefrequency.labels", "java.util.Collection", REQUEST_SCOPE},
				{"timefrequency.values", "java.util.Collection", REQUEST_SCOPE},
				{"preEvent", "java.lang.String", REQUEST_SCOPE},
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
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

	@Override
	public String[][] getResultDescriptor() {

		return (new String[][]{{"event", "java.lang.String", REQUEST_SCOPE},
				{"covenantCondition", "java.lang.String", SERVICE_SCOPE},
				{"covenantType", "java.lang.String", REQUEST_SCOPE},
				{"covenantCondition", "java.lang.String", REQUEST_SCOPE},
				{"covenantType", "java.lang.String", SERVICE_SCOPE},
				{"monitoringResponsibilityList1", "java.util.List",
					SERVICE_SCOPE},
			{"monitoringResponsibilityList2", "java.util.List",
					SERVICE_SCOPE},
			
			{"facilityNamesList", "java.util.List", SERVICE_SCOPE},
				{"otherCovenantDetailsList", "java.util.List", SERVICE_SCOPE},
				{"otherCovenantDetailsValuesList", "java.util.List", SERVICE_SCOPE},
				{"limitProfileTrxVal",
						"com.integrosys.cms.app.limit.trx.OBLimitProfileTrxValue",
						SERVICE_SCOPE},
				{IGlobalConstant.GLOBAL_CUSTOMER_OBJ,
						"com.integrosys.cms.app.customer.bus.ICMSCustomer",
						GLOBAL_SCOPE},
				{"timefrequency.labels", "java.util.Collection", REQUEST_SCOPE},
				{"timefrequency.values", "java.util.Collection", REQUEST_SCOPE},
				{"preEvent", "java.lang.String", REQUEST_SCOPE},
				{ "mainEventIdentifier", "java.lang.String", REQUEST_SCOPE },
				{ "mainEventIdentifier", "java.lang.String", SERVICE_SCOPE },
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
	private List getmonitoringResponsibilityList() {
		List monitoringResponsibilityList = new ArrayList();
		CommonCodeList commonCode = CommonCodeList.getInstance(CategoryCodeConstant.MONITORING_RESPONSIBILITY);
		Map labelValueMap = commonCode.getLabelValueMap();
		Iterator iterator = labelValueMap.entrySet().iterator();
		String label;
		String value;
		String valueOnly;
		while (iterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) iterator.next();
			value = pairs.getKey().toString();
			label = pairs.getKey() + "-" + pairs.getValue();
			valueOnly = (String) pairs.getValue();
			// LabelValueBean lvBean = new LabelValueBean(label,value);
			LabelValueBean lvBean = new LabelValueBean(label, valueOnly);
			monitoringResponsibilityList.add(lvBean);
			// System.out.println("monitoringResponsibilityList----------->>>>>>>>>>>>>>>>...."+monitoringResponsibilityList.size());
		}
		return CommonUtil.sortDropdown(monitoringResponsibilityList);
	}
}
