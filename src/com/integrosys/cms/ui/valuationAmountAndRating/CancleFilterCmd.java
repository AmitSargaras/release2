package com.integrosys.cms.ui.valuationAmountAndRating;

import java.util.HashMap;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

/**
 * @author cyliew
 * @author Chong Jun Yong
 * @since 2006/10/27
 */
public class CancleFilterCmd extends AbstractCommand implements
		ICommonEventConstant {

//	private IReportService reportService;
//
//	public IReportService getReportService() {
//		return reportService;
//	}
//
//	public void setReportService(IReportService reportService) {
//		this.reportService = reportService;
//	}

	/**
	 * Default Constructor
	 */
	public CancleFilterCmd() {

	}

	public String[][] getParameterDescriptor() {

		return (new String[][] {
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "valuationAmountAndRatingObj","com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", SERVICE_SCOPE}, 
				{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
				{ "valuationAmountAndRatingObj","com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", FORM_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam",
					GLOBAL_SCOPE },
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
			    { "valuationAmountAndRatingObj","com.integrosys.cms.app.valuationAmountAndRating.bus.OBValuationAmountAndRating", FORM_SCOPE},
				{ "customerObject", "java.util.List", SERVICE_SCOPE },
				{ "customerObject", "com.integrosys.cms.app.customer.bus.OBCustomerSearchResult",SERVICE_SCOPE },
				{ "diaryItemObj","com.integrosys.cms.app.diary.bus.OBDiaryItem", FORM_SCOPE },
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

		OBValuationAmountAndRating filter = (OBValuationAmountAndRating) map.get("valuationAmountAndRatingObj");

		resultMap.put("valuationAmountAndRatingObj", filter);
	//	resultMap.put("diaryNumber", filter.getDiaryNumber());
		
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}
	
}
