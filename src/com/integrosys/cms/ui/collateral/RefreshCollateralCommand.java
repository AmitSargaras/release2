package com.integrosys.cms.ui.collateral;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.proxy.CheckListProxyManagerFactory;
import com.integrosys.cms.app.checklist.proxy.ICheckListProxyManager;
import com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.ui.common.CommonCodeList;

public class RefreshCollateralCommand extends AbstractCommand {
	/**
	 * Default Constructor
	 */
	public RefreshCollateralCommand() {
	}

	/**
	 * Defines a two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] {
				{ "searchCriteriaObj", "com.integrosys.cms.app.collateral.bus.CollateralSearchCriteria", FORM_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE }, { "collateralId", "java.lang.String", REQUEST_SCOPE },
				{ "securityType", "java.lang.String", REQUEST_SCOPE },
				{ "listType", "java.lang.String", REQUEST_SCOPE },
				{ "securityLoc", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "stateCD", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "districtCD", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE }

		});
	}

	/**
	 * Defines a two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { { "event", "java.lang.String", REQUEST_SCOPE },
				{ "collateralId", "java.lang.String", REQUEST_SCOPE },
				{ "subTypeCode", "java.util.Collection", REQUEST_SCOPE },
				{ "subTypeLabel", "java.util.Collection", REQUEST_SCOPE },

				/*
				 * {"stateValue", "java.util.Collection",
				 * ICommonEventConstant.REQUEST_SCOPE}, {"stateLabel",
				 * "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
				 * {"districtValue", "java.util.Collection",
				 * ICommonEventConstant.REQUEST_SCOPE}, {"districtLabel",
				 * "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
				 * {"mukimValue", "java.util.Collection",
				 * ICommonEventConstant.REQUEST_SCOPE}, {"mukimLabel",
				 * "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE},
				 */
				{ "locationStateLabel", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationStateValue", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationDistrictColl", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE },
				{ "locationMukimColl", "java.util.Collection", ICommonEventConstant.REQUEST_SCOPE }, });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 * @throws com.integrosys.base.uiinfra.exception.CommandProcessingException
	 *         on errors
	 * @throws com.integrosys.base.uiinfra.exception.CommandValidationException
	 *         on errors
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "Inside doExecute()");

		CollateralSearchCriteria objSearch = (CollateralSearchCriteria) map.get("searchCriteriaObj");

		String event = (String) map.get("event");
		String listType = (String) map.get("listType");
		String collateralId = (String) map.get("collateralId");
		String securityType = (String) map.get("securityType");

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap result = new HashMap();

		List subTypeCode = new ArrayList();
		List subTypeLabel = new ArrayList();

		try {
			if ((securityType != null) && !"".equals(securityType)) {
				ICheckListProxyManager proxy = CheckListProxyManagerFactory.getCheckListProxyManager();
				HashMap map1 = proxy.getSecuritySubTypes(securityType);
				if ((map1 != null) && !map1.isEmpty()) {
					subTypeLabel = (List) map1.get("subTypeLabel");
					subTypeCode = (List) map1.get("subTypeCode");
				}

				if (ICMSConstant.SECURITY_TYPE_PROPERTY.equals(securityType)) {
					this.getStateDistrictMukim(map, result);
				}

			}
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			throw (new CommandProcessingException(e.getMessage()));
		}

		result.put("event", event);
		result.put("listType", listType);
		result.put("collateralId", collateralId);

		result.put("subTypeCode", subTypeCode);
		result.put("subTypeLabel", subTypeLabel);

		DefaultLogger.debug(this, "Going out of doExecute()");

		resultMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, result);
		resultMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return resultMap;

	}

	private void getStateDistrictMukim(HashMap map, HashMap result) throws Exception {
		result.put("locationDistrictColl", new ArrayList());
		result.put("locationMukimColl", new ArrayList());

		String collateralLoc = (String) map.get("securityLoc");
		CommonCodeList commonCode = CommonCodeList.getInstance(collateralLoc, CategoryCodeConstant.STATE_CATEGORY_CODE);
		result.put("locationStateLabel", commonCode.getCommonCodeLabels());
		result.put("locationStateValue", commonCode.getCommonCodeValues());

		String state = (String) map.get("stateCD");
		String district = (String) map.get("districtCD");

		if ((state != null) && !"".equals(state)) {
			result.put("locationDistrictColl", getDistrictList(state));
		//}
		//if ((district != null) && !"".equals(district)) {
			result.put("locationMukimColl", getMukimList(state));
		}

		String listType = (String) map.get("listType");
		if ("COUNTRY_CODE".equals(listType)) {
			result.put("locationDistrictColl", new ArrayList());
			result.put("locationMukimColl", new ArrayList());
		}
		//else if ("STATE_CODE".equals(listType)) {
		//	result.put("locationMukimColl", new ArrayList());
		//}

	}

	private static ArrayList getDistrictList(String locationState) {
		CommonCodeList commonCode = CommonCodeList.getInstance(null, null, CategoryCodeConstant.DISTRICT_CATEGORY_CODE,
				false, locationState);
		Collection labelList = commonCode.getCommonCodeLabels();
		Collection valueList = commonCode.getCommonCodeValues();
		return CollateralUiUtil.getLVBeanList(labelList, valueList);
	}

	private static ArrayList getMukimList(String locationState) {
		CommonCodeList commonCode = CommonCodeList.getInstance(null, null, CategoryCodeConstant.MUKIM_CATEGORY_CODE,
				false, locationState);
		Collection labelList = commonCode.getCommonCodeLabels();
		Collection valueList = commonCode.getCommonCodeValues();
		return CollateralUiUtil.getLVBeanList(labelList, valueList);
	}

}
