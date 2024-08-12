package com.integrosys.cms.batch.reports;

import com.integrosys.base.techinfra.util.PropertyUtil;

import java.util.Calendar;

/*
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */

/**
 * Description: List of constants used for batch reports
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.25 $
 * @since $Date: 2006/10/20 06:29:47 $ Tag: $Name: $
 */
public class ReportConstants {

    /**
     * Constants for batch parameter input
     */
    public static final String KEY_SCOPE = "scope";
    public static final String KEY_VALUE = "value";
    public static final String KEY_DATE = "date";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_ID = "id";
    public static final String KEY_CENTRE = "centre";
 

    /**
	 * Constants for report types
	 */
	public static final String PDF_REPORT = "PDF";

	public static final String CSV_REPORT = "CSV";

	public static final String EXCEL_REPORT = "XLS";

	public static final String WORD_REPORT = "DOC";

	/**
	 * Constants for report file extension
	 */
	public static final String FILE_PDF = "pdf";

	public static final String FILE_EXCEL = "xls";

	public static final String FILE_WORD = "doc";

	public static final String FILE_CSV = "csv";

	public static final String FILE_TEXT = "txt";

	public static final String FILE_RTF = "rtf";

	public static final String FILE_RPT = "rpt";

	/**
	 * keys for looking up database information
	 */
	public static final String USER_ID = "dbconfig.batch.userId";

	public static final String PASSWORD = "dbconfig.batch.password";

	public static final String DATABASE_NAME = "dbconfig.batch.database.name";

	public static final String SYSTEM_DSN = "dbconfig.batch.database.dsn";

	/**
	 * IP Address of Crystal XI Enterprise Server
	 */
	public static final String ENTERPRISE_SERVER = "enterpriseserver.cmsname";

	public static final String ENTERPRISE_USER = "enterpriseserver.username";

	public static final String ENTERPRISE_PASSWORD = "enterpriseserver.password";

	public static final String ENTERPRISE_AUTHTYPE = "enterpriseserver.authtype";

	public static final String REPORT_FOLDER_PATH = "crystal.report.folder.path";

	public static final String SERVER_REPORT_FOLDER_NAME = "enterpriseserver.foldername";

	/**
	 * IP Address of Crystal Report Server
	 */
	public static final String RAS_SERVER = "reportapplicationserver.name";

	public static final String DLL_DRIVER = "dll.driver";

	public static final String SERVER_TYPE = "db.server.type";

	/**
	 * Crystal report class for creating client document
	 */
	public static final String CLIENT_REPORT_CLASS = "com.crystaldecisions.sdk.occa.report.application.ReportClientDocument";

	/**
	 * Scope levels for concentration reports
	 */
	public static final String GLOBAL_SCOPE = "Global";

	public static final String COUNTRY_SCOPE = "Country";

	public static final String EXCHANGE_SCOPE = "Exchange";

	public static final String COMMODITY_SCOPE = "Com";

	public static final String REGION_SCOPE = "Region";

	public static final String GLOBAL_COUNTRY_CODE = "GG";

	public static final String ALL_REGIONS = "All";

	public static final String ALL_REGION_DESC = "All Regions";

	/**
	 * Report generation frequency
	 */
	public static final String DAILY = "Daily";

	public static final String WEEKLY = "Weekly";

	public static final String MONTHLY = "Monthly";

	public static final String YEARLY = "Yearly";

	public static final String BI_MONTHLY = "BiMonthly";

	public static final String QUARTERLY = "Quarterly";

	/**
	 * lookup key to property file for Crystal report templates
	 */
	public static final String REPORT_LOCATION = "report_location";

	/**
	 * Types of stream converters used for exporting
	 */
	public static final String BYTE_STREAM_CONVERTER = "bytearrayconverter";

	public static final String CHAR_STREAM_CONVERTER = "chararrayconverter";

	/**
	 * Replacement symbol for substitution - generating of report titles Report
	 * Master contains the report titles with "$" symbols for substitution
	 */
	public static final String REPLACE_SYMBOL = "$";

	/**
	 * lookup key to property file for valid search report duration
	 */
	public static final String REPORT_PAST_DURATION = "report.search.days";

	/**
	 * different status for the report request
	 */
	public static final String REPORT_STATUS_REQUESTED = "REQUESTED";

	public static final String REPORT_STATUS_IN_PROGRESS = "IN-PROGRESS";

	public static final String REPORT_STATUS_GENERATED = "GENERATED";

	public static final String REPORT_STATUS_GENERATION_ERROR = "ERROR";

	/**
	 * constrants for looking up risk profile report parameters
	 */
	public static final String DENOMINATOR_1 = "den1";

	public static final String DENOMINATOR_2 = "den2";

	public static final String NUMERATOR_1 = "num1";

	public static final String NUMERATOR_2 = "num2";

	/**
	 * constants to differentiate Concentration and MIS reports in database
	 */
	public static final String MIS_CATEGORY = "MIS";

	public static final String CONCENTRATION_CATEGORY = "CON";

    public static final String LIMIT_CONCENTRATION_CATEGORY = "LCN";

	public static final String SYSTEM_CATEGORY = "SYS";

	public static final String DOC_CATEGORY = "DOC";
	
	public static final String ANALYSIS_CATEGORY = "ANA";

	public static final String DIARY_REPORT = "DIARY";

	public static final String COUNTRY_REPORT = "Country";

	public static final String BL_DISCLAIMER_REPORT = "BL_DISCLAIMER";

    public static final String GROUP_CATEGORY = "GRP";
    
    public static final String CCI_CATEGORY = "CCI";
    
	// New report category added in gcms
	public static final String BRIDGING_LOAN_CATEGORY = "BL";

	public static final String PREDEAL_CATEGORY = "PDR";

	public static final String CONTRACT_FINANCING_CATEGORY = "CF";

	public static final String TRADINGBOOK_CATEGORY = "TBR";

	/**
	 * constants to allow search using different columns
	 */
	public static final String REPORT_CONFIG_BY_ID = "ID";

	public static final String REPORT_CONFIG_BY_NAME = "NAME";

	/**
	 * constant to label groups not found in the top N listing
	 */
	public static final String OTHERS_GROUP = "Others";

	/**
	 * mis report id for doc reminder letter
	 * 
	 */
	public static final String REMINDER_DOC_ID = "DOC001";

	/**
	 * key for looking up deficiency report export type
	 */
	public static final String DEFICIENY_REPORT_TYPE = "deficiency_rpt_export.format";

	public static final String DIARY_REPORT_TYPE = "diary_rpt_export.format";

	public static final String MIS_REPORT_TYPE = "mis_rpt_export.format";

	public static final String BL_DISCLAIMER_REPORT_TYPE = "mis_bl_disclaimer_export.format";

	/**
	 * event constant for online report generation
	 */
	public static final String EVENT_GEN_DIARY = "generateDiaryReport";

	public static final String EVENT_GEN_DEFICIANCY = "gen_def_report";

	public static final String EVENT_GEN_MIS_RPT = "generateMISReport";

	public static final String EVENT_GEN_BL_DISCLAIMER = "generateBLDisclaimerLetter";

	/**
	 * Database Server Type constant
	 */
	public static final String ODBC = "ODBC";

	public static final String ORACLE = "ORACLE";

	/**
	 * default value for no organisation code
	 */
	public static final String NO_ORG_CODE = "NO_ORG_CODE";

	public static final String NO_COUNTRY = "NO_COUNTRY";

	/**
	 * named parameters report template fields
	 */
	public static final String RPT_PARAM_DATE = "param_date";

	public static final String RPT_PARAM_COUNTRY = "orig_country";

	public static final String RPT_PARAM_ORG = "orig_organisation";

	public static final String RPT_LMT_PROFILE = "lmt_profile";

    public static final String RPT_PARAM_BRANCH_LIST = "branch_list";

	public static final String RPT_START_EXP_DATE = "start_exp_date";

	public static final String RPT_END_EXP_DATE = "end_exp_date";

	public static final String RPT_COUNTRY_LIST = "country_list";

	public static final String RPT_TEAM_TYPE_MEMBERSHIP_ID = "team_type_membership_id";

	public static final String RPT_LE_ID = "le_id";

	public static final String RPT_CUSTOMER_INDEX = "customer_name";

	public static final String RPT_PARAM_BL_BUILDUP_ID = "buildupID";

    public static final String RPT_PARAM_CENTER_CODE = "centre";
    public static final String PARAM_CENTRE_CODE = "CENTRE";


    /**
     * named parameters - report header related - report template fields
     */
    public static final String RPT_HEADER_PARAM_REPORT_NUMBER = "report_number";
    public static final String RPT_HEADER_PARAM_FREQ = "freq";
    public static final String RPT_HEADER_PARAM_RECIPIENT = "recipient";
    public static final String RPT_HEADER_PARAM_BANK_NAME = "bank_name";
    public static final String RPT_HEADER_PARAM_SYS_NAME = "sys_name";
    public static final String RPT_HEADER_PARAM_REPORT_TITLE = "report_title";


    /**
     * setup related
     */
    public static final int RPT_FREQ_MONTHLY = 1;

	public static final int RPT_FREQ_WEEKLY = Calendar.MONDAY;

	public static final int RPT_FREQ_BIMONTHLY_1 = 1;

	public static final int RPT_FREQ_BIMONTHLY_2 = 15;

	public static final String RPT_SLEEP_TIME = "rpt.sleep.time";

	public static final int RPT_TRX_TIME = 30000;

	public static final int SLEEP_TIME = 500;

	public static final int USR_TRX_SLEEP_TIME = 100;

	public static final int KILL_PENDING_TOO_LONG = -99;

	public static final String WAIT_PENDING = "rpt.wait.pending";

	public static final String RESCHEDULE_REQUIRED = "RESCHEDULE_REQUIRED";
	public static final String RPT_STATUS_COMPLETED = "COMPLETED";

	public static final String MAX_RETRY_COUNT = "rpt.max.retry.count";

	public static final String RPT_THREAD_COUNT = "rpt.thread.count";

    public static final String MIS_RPT_CENTRE_CODE = "is.report.using.centre.code";



     public static boolean isMISReportByCentreCode() {
        boolean check = false;
        try {
            String  str = PropertyUtil.getInstance("/ofa_env.properties").getProperty(ReportConstants.MIS_RPT_CENTRE_CODE);
            if (str != null && !str.equals(""))  {
                check = (Boolean.valueOf(str)).booleanValue();
            }
        }
		catch (Exception e) {
			e.printStackTrace();
        }
        return check;
    }
}
