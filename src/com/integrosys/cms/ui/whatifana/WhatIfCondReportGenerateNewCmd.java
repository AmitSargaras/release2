/*
 Copyright Integro Technologies Pte Ltd
 */

package com.integrosys.cms.ui.whatifana;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.uiinfra.common.ICommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.batch.reports.OBReport;
import com.integrosys.cms.batch.reports.OBReportRequest;
import com.integrosys.cms.batch.reports.ReportConstants;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.report.ReportCommandAccessor;
import com.integrosys.component.commondata.app.CommonDataSingleton;
import com.integrosys.component.user.app.bus.ICommonUser;

/**
 * <p>
 * Generates a new what-if-analysis report.
 * <p>
 * For <b>'ALL'</b> option selected, please take note what should be the value
 * passed into the report template. If change here, please make any necessary
 * changes to the report template as well.
 * @author siew kheat
 * @author Chong Jun Yong
 */
public class WhatIfCondReportGenerateNewCmd extends ReportCommandAccessor implements ICommand, ICommonEventConstant {

	private Map reportFormatMisReportIdMap;

	public void setReportFormatMisReportIdMap(Map reportFormatMisReportIdMap) {
		this.reportFormatMisReportIdMap = reportFormatMisReportIdMap;
	}

	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return new String[][] {
				{ WhatIfCondReportForm.MAPPER, "com.integrosys.cms.ui.whatifana.WhatIfCondReportForm", FORM_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE } };
	}

	public String[][] getResultDescriptor() {
		return new String[][] {};
	}

	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandValidationException, CommandProcessingException,
			AccessDeniedException {

		HashMap resultMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		HashMap returnMap = new HashMap();

		try {

			WhatIfCondReportForm form = (WhatIfCondReportForm) map.get(WhatIfCondReportForm.MAPPER);

			DefaultLogger.debug(this, "Mapper Return :" + form);
			ICommonUser user = (ICommonUser) map.get(IGlobalConstant.USER);

			// to generate specific parameter for each type
			String reportFormat = null;
			StringBuffer sb = new StringBuffer();
			if (form.getReportType().equals(WhatIfCondReportConstants.REPORT_TYPE_STATE)) {
				sb.append(WhatIfCondReportConstants.PARAM_NAME_STATE + "="
						+ generateParameters(WhatIfCondReportConstants.REPORT_TYPE_STATE, form.getState()));
				sb.append("|" + WhatIfCondReportConstants.PARAM_NAME_DIRECTION + "=" + form.getDirectionState() + "|"
						+ WhatIfCondReportConstants.PARAM_NAME_PERCENTAGE + "=" + form.getPercentageState());

				if (WhatIfCondReportConstants.OPTION_ALL.equals(form.getMukim())) {
					// faster hack way. to let the sql query , column is equal
					// to it.
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_MUKIM).append("=").append("mukim");
				}
				else {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_MUKIM).append("=");
					sb.append("'").append(form.getMukim()).append("'");
				}

				if (WhatIfCondReportConstants.OPTION_ALL.equals(form.getDistrict())) {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_DISTRICT).append("=").append("district");
				}
				else {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_DISTRICT).append("=");
					sb.append("'").append(form.getDistrict()).append("'");
				}

				reportFormat = form.getReportFormatProperty();
			}
			else if (form.getReportType().equals(WhatIfCondReportConstants.REPORT_TYPE_INDEX_TYPE)) {
				sb.append(WhatIfCondReportConstants.PARAM_NAME_INDEX_TYPE + "='" + form.getIndexType() + "'");
				sb.append("|" + WhatIfCondReportConstants.PARAM_NAME_DIRECTION + "=" + form.getDirectionIndexType()
						+ "|" + WhatIfCondReportConstants.PARAM_NAME_PERCENTAGE + "=" + form.getPercentageIndexType());

				if (WhatIfCondReportConstants.OPTION_ALL.equals(form.getStockCode())) {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_STOCK_CODE).append("=").append(
							"item.stock_code");
				}
				else {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_STOCK_CODE).append("=");
					sb.append("'").append(form.getStockCode()).append("'");
				}

				reportFormat = form.getReportFormatStockIndex();
			}
			else if (form.getReportType().equals(WhatIfCondReportConstants.REPORT_TYPE_MAKE)) {
				sb.append(WhatIfCondReportConstants.PARAM_NAME_MAKE + "='" + form.getMake() + "'");
				sb.append("|" + WhatIfCondReportConstants.PARAM_NAME_DIRECTION + "=" + form.getDirectionMake() + "|"
						+ WhatIfCondReportConstants.PARAM_NAME_PERCENTAGE + "=" + form.getPercentageMake());

				if (WhatIfCondReportConstants.OPTION_ALL.equals(form.getModel())) {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_MODEL).append("=").append("model_no");
				}
				else {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_MODEL).append("=");
					sb.append("'").append(form.getModel()).append("'");
				}

				if (WhatIfCondReportConstants.OPTION_ALL.equals(form.getYearOfManufacture())) {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_MANUFACTURE_YEAR).append("=").append(
							"manufacture_year");
				}
				else {
					sb.append("|").append(WhatIfCondReportConstants.PARAM_NAME_MANUFACTURE_YEAR).append("=");
					sb.append(form.getYearOfManufacture());
				}

				reportFormat = form.getReportFormatVehicle();
			}
			else if (form.getReportType().equals(WhatIfCondReportConstants.REPORT_TYPE_GOLD_GRADE)) {
				sb.append(WhatIfCondReportConstants.PARAM_NAME_GOLD_GRADE + "="
						+ generateParameters(WhatIfCondReportConstants.REPORT_TYPE_GOLD_GRADE, form.getGoldGrade()));
				sb.append("|" + WhatIfCondReportConstants.PARAM_NAME_DIRECTION + "=" + form.getDirectionGoldGrade()
						+ "|" + WhatIfCondReportConstants.PARAM_NAME_PERCENTAGE + "=" + form.getPercentageGoldGrade());

				reportFormat = form.getReportFormatGold();
			}

			// to generate general parameters for all report type
			String country = PropertyManager.getValue("defaultCountryCode");
			String params = ReportConstants.KEY_COUNTRY + "=" + country + "|" + ReportConstants.KEY_SCOPE + "="
					+ country + "|" + sb.toString() + "|" + WhatIfCondReportConstants.PARAM_NAME_REPORT_TITLE + "="
					+ form.getReportName() + "|" + WhatIfCondReportConstants.PARAM_NAME_PARAM_DATE + "="
					+ new SimpleDateFormat(WhatIfCondReportConstants.PARAM_VALUE_PARAM_DATE_FORMAT).format(new Date());

			if (form.getMake().equals(WhatIfCondReportConstants.OPTION_ALL)) {
				// When All Maker is selected, different report is used
				// Instead of the typical ANA003, ANA005 is used
				form.setReportType(WhatIfCondReportConstants.REPORT_TYPE_ALL_MAKE);
			}

			String misReportId = getMisReportIdByReportTypeAndFormat(form.getReportType(), reportFormat);
			OBReport obReport = getReportRequestManager().getReportDetailsByReportID(misReportId);

			DefaultLogger.debug(this, "MISReportID:" + form.getReportType() + ", Report ID :" + obReport.getReportId());

			OBReportRequest req = new OBReportRequest();
			req.setStatus(ReportConstants.REPORT_STATUS_REQUESTED);
			req.setReportName(form.getReportName());
			req.setRequestTime(new Date());
			req.setUserID(user.getUserID());
			req.setReportID(obReport.getReportId());
			req.setParameters(params);

			DefaultLogger.debug(this, params);
			getReportRequestManager().createReportRequest(req);
		}
		catch (Throwable e) {
			DefaultLogger.error(this, "Exception caught in doExecute()", e);
			exceptionMap.put("application.exception", e);
		}

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);

		return returnMap;
	}

	public String getMisReportIdByReportTypeAndFormat(String reportType, String reportFormat) {
		if (StringUtils.isBlank(reportFormat) || WhatIfCondReportConstants.REPORT_FORMAT_SUMMARY.equals(reportFormat)) {
			return reportType;
		}

		return (String) this.reportFormatMisReportIdMap.get(reportType + "_" + reportFormat);
	}

	/**
	 * Generate a string that contains a list of report type items
	 * @param title
	 * @param country
	 * @return
	 */

	private String generateParameters(String reportType, String value) {

		if (value == null)
			return "";

		if (value.equals(WhatIfCondReportConstants.OPTION_ALL)) {
			StringBuffer sb = new StringBuffer();
			List values = new ArrayList();

			if (reportType.equals(WhatIfCondReportConstants.REPORT_TYPE_STATE)) {
				values = new ArrayList(CommonDataSingleton
						.getCodeCategoryValues(WhatIfCondReportConstants.STATE_CATEGORY));
			}
			else if (reportType.equals(WhatIfCondReportConstants.REPORT_TYPE_MAKE)) {
				values = new ArrayList(CommonDataSingleton
						.getCodeCategoryValues(WhatIfCondReportConstants.MAKE_CATEGORY));
			}
			else if (reportType.equals(WhatIfCondReportConstants.REPORT_TYPE_GOLD_GRADE)) {
				values = new ArrayList(CommonDataSingleton
						.getCodeCategoryValues(WhatIfCondReportConstants.GOLD_GRADE_CATEGORY));
			}

			for (int i = 0; values.size() > i; i++) {

				sb.append("'" + values.get(i) + "'");

				if (i != values.size() - 1)
					sb.append(",");

			}

			return sb.toString();
		}
		else {
			return "'" + value + "'";
		}
	}

}
