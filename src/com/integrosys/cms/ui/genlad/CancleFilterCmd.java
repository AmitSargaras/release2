package com.integrosys.cms.ui.genlad;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.poi.report.IReportService;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.manualinput.CommonUtil;
import com.integrosys.cms.ui.poi.report.ReportFilterTypeList;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class CancleFilterCmd extends AbstractCommand implements
		ICommonEventConstant {

	private IReportService reportService;

	public IReportService getReportService() {
		return reportService;
	}

	public void setReportService(IReportService reportService) {
		this.reportService = reportService;
	}

	/**
	 * Default Constructor
	 */
	public CancleFilterCmd() {

	}

	public String[][] getParameterDescriptor() {

		/*
		 * private String segment; private String dueMonth; private String
		 * dueYear;
		 */

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reportId", "java.lang.String", REQUEST_SCOPE },
				{ "generateLadFormObj",
						"com.integrosys.cms.ui.genlad.OBFilter", FORM_SCOPE },
				{ "filterPartyMode", "java.lang.String", REQUEST_SCOPE },
				{ "searchCustomerName", "java.lang.String", FORM_SCOPE },
				{ "segment", "java.lang.String", FORM_SCOPE },
				{ "relationshipMgr", "java.lang.String", FORM_SCOPE },
				{ "dueMonth", "java.lang.String", FORM_SCOPE },
				{ "dueYear", "java.lang.String", FORM_SCOPE },
				{ "filterUserMode", "java.lang.String", REQUEST_SCOPE },
				{ "industry", "java.lang.String", REQUEST_SCOPE },
				{ "rbiAsset", "java.lang.String", REQUEST_SCOPE },
				{ "fromDate", "java.lang.String", REQUEST_SCOPE },
				{ "toDate", "java.lang.String", REQUEST_SCOPE } });
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
				{ "reportsFilterType", "java.lang.String", SERVICE_SCOPE },
				{ "fileName", "java.lang.String", REQUEST_SCOPE },
				{ "departmentList", "java.util.List", SERVICE_SCOPE },
				{ "generateLadFormObj",
						"com.integrosys.cms.ui.genlad.OBFilter", FORM_SCOPE },
				{ "segment", "java.lang.String", SERVICE_SCOPE },
				{ "searchCustomerName", "java.lang.String", SERVICE_SCOPE },
				{ "relationshipMgr", "java.lang.String", SERVICE_SCOPE },
				{ "segment", "java.lang.String", SERVICE_SCOPE },
				{ "dueMonth", "java.lang.String", SERVICE_SCOPE },
				{ "dueYear", "java.lang.String", SERVICE_SCOPE },
				{ "customerObject", "java.util.List", SERVICE_SCOPE },
				{ "selectedUserObject", "java.util.List", SERVICE_SCOPE },
				{ "filterPartyMode", "java.lang.String", REQUEST_SCOPE },
				{ "filterUserMode", "java.lang.String", REQUEST_SCOPE },
				{ "industry", "java.lang.String", REQUEST_SCOPE },
				{ "fromDate", "java.lang.String", REQUEST_SCOPE },
				{ "toDate", "java.lang.String", REQUEST_SCOPE } });
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map
	 *            is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException,
			CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();

		DefaultLogger.debug(this, "Inside doExecute()");

		OBFilter filter = (OBFilter) map.get("generateLadFormObj");

		resultMap.put("searchCustomerName", filter.getSearchCustomerName());

		resultMap.put("relationshipMgr", filter.getRelationshipMgr());

		resultMap.put("segment", filter.getSegment());

		resultMap.put("dueMonth", filter.getDueMonth());

		resultMap.put("dueYear", filter.getDueYear());

		DefaultLogger.debug(this, "Going out of doExecute()");
		
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}

	private List getDepartmentList() {
		List departmentList = new ArrayList();
		CommonCodeList commonCode = CommonCodeList
				.getInstance(CategoryCodeConstant.HDFC_DEPARTMENT);
		Map labelValueMap = commonCode.getLabelValueMap();
		Iterator iterator = labelValueMap.entrySet().iterator();
		String label;
		String value;
		while (iterator.hasNext()) {
			Map.Entry pairs = (Map.Entry) iterator.next();
			value = pairs.getKey().toString();
			label = pairs.getKey() + " (" + pairs.getValue() + ")";
			LabelValueBean lvBean = new LabelValueBean(label, value);
			departmentList.add(lvBean);
		}
		return CommonUtil.sortDropdown(departmentList);
	}
}
