/*
Copyright Integro Technologies Pte Ltd
 */
package com.integrosys.cms.ui.whatifana;

/**
 * <p>
 * Contains the constants for What If Analysis Report.
 * <p>
 * Constants that start with prefix <b>PARAM_NAME_</b> will be used as a
 * parameter name passed into the report template. If required to change the
 * value, please make sure report template get updated as well.
 * <p>
 * Constants that start with prefix <b>PARAM_VALUE_</b>, it's value will be used
 * to passed to report template.
 * @author Siew Kheat
 * @author Chong Jun Yong
 */
public class WhatIfCondReportConstants {

	public static final String REPORT_TYPE_STATE = "ANA001";

	public static final String REPORT_TYPE_INDEX_TYPE = "ANA002";

	public static final String REPORT_TYPE_MAKE = "ANA003";

	public static final String REPORT_TYPE_GOLD_GRADE = "ANA004";

	public static final String REPORT_TYPE_ALL_MAKE = "ANA005";

	public static final String PARAM_NAME_STATE = "state_code";

	public static final String PARAM_NAME_DISTRICT = "district_code";

	public static final String PARAM_NAME_MUKIM = "mukim_code";

	public static final String PARAM_NAME_INDEX_TYPE = "index_type";

	public static final String PARAM_NAME_STOCK_CODE = "stock_code";

	public static final String PARAM_NAME_MAKE = "make_code";

	public static final String PARAM_NAME_MODEL = "model_no";

	public static final String PARAM_NAME_MANUFACTURE_YEAR = "manufacture_year";

	public static final String PARAM_NAME_GOLD_GRADE = "gold_grade";

	public static final String PARAM_NAME_DIRECTION = "direction";

	public static final String PARAM_NAME_PERCENTAGE = "percentage";

	public static final String PARAM_NAME_REPORT_TITLE = "report_title";

	public static final String PARAM_NAME_PARAM_DATE = "param_date";

	public static final String PARAM_VALUE_INDEX_TYPE_MAIN = "MS600";

	public static final String PARAM_VALUE_INDEX_TYPE_OTHER = "MS605";

	public static final String PARAM_VALUE_DIRECTION_INCREASE = "I";

	public static final String PARAM_VALUE_DIRECTION_DECREASE = "R";

	public static final String PARAM_VALUE_PARAM_DATE_FORMAT = "dd/MM/yyyy";

	public static final String ERROR_NO_SELECTION = "error.no.selection";

	public static final String ERROR_MANDATORY = "error.mandatory";

	public static final String STATE_CATEGORY = "STATE";

	public static final String MAKE_CATEGORY = "VEHICLE_BRAND";

	public static final String GOLD_GRADE_CATEGORY = "GOLD_GRADE";

	public static final String OPTION_ALL = "ALL";

	public static final String REPORT_FORMAT_SUMMARY = "summary";

	public static final String REPORT_FORMAT_PER_BORROWER = "per_borrower";

}
