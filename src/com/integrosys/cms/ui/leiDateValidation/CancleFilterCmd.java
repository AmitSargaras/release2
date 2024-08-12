package com.integrosys.cms.ui.leiDateValidation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.leiDateValidation.bus.OBLeiDateValidation;
import com.integrosys.cms.app.leiDateValidation.proxy.ILeiDateValidationProxyManager;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.manualinput.CommonUtil;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class CancleFilterCmd extends AbstractCommand implements
		ICommonEventConstant {

	private ILeiDateValidationProxyManager leiDateValidationProxy;

	public ILeiDateValidationProxyManager getLeiDateValidationProxy() {
		return leiDateValidationProxy;
	}

	public void setLeiDateValidationProxy(ILeiDateValidationProxyManager leiDateValidationProxy) {
		this.leiDateValidationProxy = leiDateValidationProxy;
	}

	/**
	 * Default Constructor
	 */
	public CancleFilterCmd() {

	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "reportId", "java.lang.String", REQUEST_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",SERVICE_SCOPE }, 
				{ "filterPartyMode", "java.lang.String", REQUEST_SCOPE },
				{ "filterUserMode", "java.lang.String", REQUEST_SCOPE },
				{ "industry", "java.lang.String", REQUEST_SCOPE },
				{ "segment", "java.lang.String", REQUEST_SCOPE },
				{ "relationManager", "java.lang.String", REQUEST_SCOPE },
				{ "rbiAsset", "java.lang.String", REQUEST_SCOPE },
				{ "fromDate", "java.lang.String", REQUEST_SCOPE },
				{ "toDate", "java.lang.String", REQUEST_SCOPE },
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
				{ "reportsFilterType", "java.lang.String",SERVICE_SCOPE },
				{ "fileName", "java.lang.String",REQUEST_SCOPE },
				{ "departmentList", "java.util.List", SERVICE_SCOPE },
				{ "reportFormObj","com.integrosys.cms.app.poi.report.OBFilter",FORM_SCOPE },
				{ "customerObject", "java.util.List", SERVICE_SCOPE },
				{ "selectedUserObject", "java.util.List", SERVICE_SCOPE },
				{ "filterPartyMode", "java.lang.String", REQUEST_SCOPE },
				{ "filterUserMode", "java.lang.String", REQUEST_SCOPE },
				{ "industry", "java.lang.String", REQUEST_SCOPE },
				{ "segment", "java.lang.String", REQUEST_SCOPE },
				{ "fromDate", "java.lang.String", REQUEST_SCOPE },
				{ "toDate", "java.lang.String", REQUEST_SCOPE },
				});
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

		OBLeiDateValidation filter = (OBLeiDateValidation) map.get("reportFormObj");
//		HashMap reportFilterMap = ReportFilterTypeList.getInstance().getReportFilterList();
		String filterPartyMode=(String) map.get("filterPartyMode");
		if("ALL".equals(filterPartyMode)){
			resultMap.put("customerObject", null);
		}
		
		String filterUserMode=(String) map.get("filterUserMode");
		if("ALL".equals(filterUserMode)){
			resultMap.put("selectedUserObject", null);
		}
//		filter.setFilterPartyMode(filterPartyMode);
//		filter.setFilterUserMode(filterUserMode);
//		filter.setSegment((String)map.get("segment"));
//		filter.setRelationManager((String)map.get("relationManager"));
//		filter.setRbiAsset((String)map.get("rbiAsset"));
//		filter.setIndustry((String)map.get("industry"));
//		filter.setFromDate((String)map.get("fromDate"));
//		filter.setToDate((String)map.get("toDate"));
		resultMap.put("reportFormObj", filter);
		resultMap.put("departmentList", getDepartmentList());
		resultMap.put("filterPartyMode", filterPartyMode);
		resultMap.put("filterUserMode", filterUserMode);
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	 private List getDepartmentList() {
		List departmentList= new ArrayList();
		CommonCodeList  commonCode = CommonCodeList.getInstance(CategoryCodeConstant.HDFC_DEPARTMENT);
		Map labelValueMap = commonCode.getLabelValueMap();			
		Iterator iterator = labelValueMap.entrySet().iterator();
		String label;
		String value;
		while (iterator.hasNext()) {
	        Map.Entry pairs = (Map.Entry)iterator.next();
	        value=pairs.getKey().toString();
	        label=pairs.getKey()+" ("+pairs.getValue()+")";
			LabelValueBean lvBean = new LabelValueBean(label,value);
			departmentList.add(lvBean);
	    }
		return CommonUtil.sortDropdown(departmentList);
	}
}
