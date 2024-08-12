package com.integrosys.cms.ui.diaryitem;

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
import com.integrosys.cms.app.diary.bus.OBDiaryItem;
import com.integrosys.cms.app.poi.report.IReportService;
import com.integrosys.cms.app.poi.report.OBFilter;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;
import com.integrosys.cms.ui.collateral.CategoryCodeConstant;
import com.integrosys.cms.ui.common.CommonCodeList;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.CommonUtil;

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

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", SERVICE_SCOPE}, 
				{ "diaryNumber", "java.lang.String", REQUEST_SCOPE },
				{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
				{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam",
					GLOBAL_SCOPE },
				{ "facilityLineNoList", "java.util.List", SERVICE_SCOPE },
				{ "facilitySerialNoList", "java.util.List", SERVICE_SCOPE },
				{ "facilityLineNo", "java.lang.String", REQUEST_SCOPE },
				{ "activityList", "java.util.List", SERVICE_SCOPE },
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
			    { "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE},
				{ "customerObject", "java.util.List", SERVICE_SCOPE },
				{ "diaryNumber", "java.lang.String", REQUEST_SCOPE },
				{ "facilityLineNoList", "java.util.List", REQUEST_SCOPE },
				{ "facilitySerialNoList", "java.util.List", REQUEST_SCOPE },
				{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
				{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
				{ "facilityBoardCategorylist", "java.util.List", SERVICE_SCOPE },
				{ "facilityLineNoList", "java.util.List", SERVICE_SCOPE },
				{ "facilitySerialNoList", "java.util.List", SERVICE_SCOPE },
				{ "activityList", "java.util.List", SERVICE_SCOPE },
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

		OBDiaryItem filter = (OBDiaryItem) map.get("diaryItemObj");

		resultMap.put("diaryItemObj", filter);
	//	resultMap.put("diaryNumber", filter.getDiaryNumber());
		
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	
}
