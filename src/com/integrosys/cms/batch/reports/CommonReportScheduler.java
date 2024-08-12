package com.integrosys.cms.batch.reports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.framework.CrystalEnterprise;
import com.crystaldecisions.sdk.framework.IEnterpriseSession;
import com.crystaldecisions.sdk.framework.ISessionMgr;
import com.crystaldecisions.sdk.occa.infostore.CeScheduleType;
import com.crystaldecisions.sdk.occa.infostore.IDestination;
import com.crystaldecisions.sdk.occa.infostore.IDestinationPlugin;
import com.crystaldecisions.sdk.occa.infostore.IInfoObject;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.IInfoStore;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportFormatOptions;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportLogon;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameter;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterRangeValue;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterSingleValue;
import com.crystaldecisions.sdk.plugin.desktop.common.IReportParameterValues;
import com.crystaldecisions.sdk.plugin.desktop.report.IReport;
import com.crystaldecisions.sdk.plugin.destination.diskunmanaged.IDiskUnmanagedOptions;
import com.crystaldecisions.sdk.properties.IProperty;
import com.crystaldecisions.sdk.properties.ISDKList;
import com.integrosys.base.techinfra.crypto.CryptoManager;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.util.PropertyUtil;
import com.integrosys.cms.ui.common.constant.ICMSUIConstant;

/**
 * Description: This class is for common scheduler setting
 * 
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.5 $
 * @since $Date: 2006/10/27 06:40:05 $ Tag: $Name: $
 */

public abstract class CommonReportScheduler {
	private static final Logger logger = LoggerFactory.getLogger(CommonReportScheduler.class);

	protected static String DB_USERNAME;

	protected static String DB_PASSWORD;

	protected static String DB_NAME;

	protected static int DB_SERVER_TYPE = -1;

	protected static String DSN_DATASOURCE;

	protected static String ENTERPRISESERVER_CMSNAME;

	protected static String ENTERPRISESERVER_USERNAME;

	protected static String ENTERPRISESERVER_PASSWORD;

	protected static String ENTERPRISESERVER_AUTHTYPE;

	protected static String ERR_LOG;

	protected static int CMS_REPORT_FOLDER_ID;

	public static int SLEEP_TIME;

	public static int MAX_WAITING_PENDING = -1;

	public static int MAX_RETRY_COUNT = -1;

	protected static ISessionMgr oSessionManager;

	public static IEnterpriseSession oEnterpriseSession;

	public static IInfoStore oInfoStore;

	private static final String ENCRYPTION_PROPERTY = "db.encryption.needed";

	private static final String ENCRYPTION_PROPERTY_VALUE = "true";
	
	public static long MAX_PENDING_TIME = 50000;
	
	public static int reportBatchCount = 0;

	/**
	 * Set Enterprise session
	 */
	private void setSessionManager() throws ReportException {
		reportBatchCount++;
		logger.debug("Entering setSessionManager... reportBatchCount: "+reportBatchCount);

		try {			
			if (oEnterpriseSession == null) {
				oSessionManager = CrystalEnterprise.getSessionMgr();
				oEnterpriseSession = oSessionManager.logon(ENTERPRISESERVER_USERNAME, ENTERPRISESERVER_PASSWORD,
						ENTERPRISESERVER_CMSNAME, ENTERPRISESERVER_AUTHTYPE);
			}
		}
		catch (SDKException ex) {
			throw new ReportServerFailureException("failed to logon to crystal report server ["
					+ ENTERPRISESERVER_CMSNAME + "]", ex);
		}
		
		
		logger.debug("At setSessionManager: " + oEnterpriseSession);
	}

	/**
	 * Set InfoStore service
	 */
	private void setInfoStore() throws ReportException {
		try {
			if (oInfoStore == null)
				oInfoStore = (IInfoStore) oEnterpriseSession.getService("", "InfoStore");
		}
		catch (SDKException ex) {
			throw new ReportServerFailureException("failed to get crystal report server infostore", ex);
		}

		logger.debug("oInfoStore = " + oInfoStore);
	}

	/**
	 * Set DB User Name from property file
	 */
	private static void setDBUserName() {
		logger.debug("Entering method setDBUserName");
		if (DB_USERNAME == null) {

			if ((lookup(ENCRYPTION_PROPERTY) != null) && ENCRYPTION_PROPERTY_VALUE.equals(lookup(ENCRYPTION_PROPERTY))) {
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				String encryptedUserId = lookup(ReportConstants.USER_ID);
				DB_USERNAME = sec.decrypt(encryptedUserId);
			}
			else {
				DB_USERNAME = lookup(ReportConstants.USER_ID);
			}
		}
	}

	/**
	 * Set DB Password from property file decrypts the encrypted password read
	 * from property file
	 */
	private static void setDBPassword() {
		logger.debug("Entering method setPassword");
		if (DB_PASSWORD == null) {

			if ((lookup(ENCRYPTION_PROPERTY) != null) && ENCRYPTION_PROPERTY_VALUE.equals(lookup(ENCRYPTION_PROPERTY))) {
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				String encryptedPassword = lookup(ReportConstants.PASSWORD);
				DB_PASSWORD = sec.decrypt(encryptedPassword);
			}
			else {
				DB_PASSWORD = lookup(ReportConstants.PASSWORD);
			}
		}
	}

	private static void setDBName() {
		if (DB_NAME == null) {
			DB_NAME = lookup(ReportConstants.DATABASE_NAME);
		}
		logger.debug("DB Name is : " + DB_NAME);
	}

	private static void setDBServerType() {
		if (DB_SERVER_TYPE < 0) {
			DB_SERVER_TYPE = setDBServerType(lookup(ReportConstants.SERVER_TYPE));
		}

		logger.debug("DB Server Type is: " + DB_SERVER_TYPE);
	}

	private static int setDBServerType(String serverType) {
		if (serverType == null) {
			logger.debug("DB server type is null... ");
			return -1;
		}

		if (serverType.equals(ReportConstants.ODBC)) {
			return IReportLogon.CeReportServerType.ODBC;
		}

		if (serverType.equals(ReportConstants.ORACLE)) {
			return IReportLogon.CeReportServerType.ORACLE;
		}

		return -1;
	}

	/**
	 * set DNS Data Source from property file
	 */
	private static void setDNSDataSource() {
		if (DSN_DATASOURCE == null) {
			DSN_DATASOURCE = lookup(ReportConstants.SYSTEM_DSN);
		}

		logger.debug("DNS Datasource is : " + DSN_DATASOURCE);
	}

	/**
	 * Set Enterprise server CMS Name
	 */
	private static void setEnterpriseServerCMSName() {
		if (ENTERPRISESERVER_CMSNAME == null) {
			ENTERPRISESERVER_CMSNAME = lookup(ReportConstants.ENTERPRISE_SERVER);
		}

		logger.debug("Enterprise server CMS Name is : " + ENTERPRISESERVER_CMSNAME);
	}

	/**
	 * Set Enterprise server User Name
	 */
	private static void setEnterpriseServerUserName() {
		logger.debug("Entering method setEnterpriseServerUserName");
		if (ENTERPRISESERVER_USERNAME == null) {
			if ((lookup(ENCRYPTION_PROPERTY) != null) && ENCRYPTION_PROPERTY_VALUE.equals(lookup(ENCRYPTION_PROPERTY))) {
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				String encryptedUserId = lookup(ReportConstants.ENTERPRISE_USER);
				ENTERPRISESERVER_USERNAME = sec.decrypt(encryptedUserId);
			}
			else {
				ENTERPRISESERVER_USERNAME = lookup(ReportConstants.ENTERPRISE_USER);
			}
		}
	}

	/**
	 * Set Enterprise server password
	 */
	private static void setEnterpriseServerPassword() {
		logger.debug("Entering method setEnterpriseServerPassword");
		if (ENTERPRISESERVER_PASSWORD == null) {

			if ((lookup(ENCRYPTION_PROPERTY) != null) && ENCRYPTION_PROPERTY_VALUE.equals(lookup(ENCRYPTION_PROPERTY))) {
				CryptoManager sec = new CryptoManager("Hex", PropertyManager.getValue("encryption.provider"));
				String encryptedPassword = lookup(ReportConstants.ENTERPRISE_PASSWORD);
				ENTERPRISESERVER_PASSWORD = sec.decrypt(encryptedPassword);
			}
			else {
				ENTERPRISESERVER_PASSWORD = lookup(ReportConstants.ENTERPRISE_PASSWORD);
			}
		}
	}

	/**
	 * Set Enterprise server Auth Type
	 */
	private static void setEnterpriseServerAuthType() {
		if (ENTERPRISESERVER_AUTHTYPE == null) {
			ENTERPRISESERVER_AUTHTYPE = lookup(ReportConstants.ENTERPRISE_AUTHTYPE);
		}
		logger.debug("Enterprise server Auth Type is : " + ENTERPRISESERVER_AUTHTYPE);
	}

	/**
	 * Set Maximum Waiting Pending status count
	 */
	private static void setMaxWaitPending() {
		try {
			if (MAX_WAITING_PENDING == -1) {
				MAX_WAITING_PENDING = Integer.parseInt(lookup(ReportConstants.WAIT_PENDING));
			}
		}
		catch (Exception e) {
			MAX_WAITING_PENDING = 1;
		}
		logger.debug("Wait pending count is : " + String.valueOf(MAX_WAITING_PENDING));
	}

	/**
	 * Set Maximum retry count
	 */
	private static void setMaxRetryCount() {
		try {
			if (MAX_RETRY_COUNT == -1) {
				MAX_RETRY_COUNT = Integer.parseInt(lookup(ReportConstants.MAX_RETRY_COUNT));
			}
		}
		catch (Exception e) {
			MAX_RETRY_COUNT = 1;
		}
		logger.debug("Maximum Retry count is : " + String.valueOf(MAX_RETRY_COUNT));
	}

	/**
	 * Helper method to lookup from property file
	 */
	protected static String lookup(String key) {
		if (key == null) {
			return null;
		}
		logger.debug("============= Looking up using key: " + key);
		String retVal = PropertyManager.getValue(key);
		return retVal;
	}

	/**
	 * Common setup to enterprise server
	 */
	protected void commonSetup() throws ReportException {
		setDBUserName();
		setDBPassword();
		setDBName();
		setDNSDataSource();
		setDBServerType();
		setEnterpriseServerCMSName();
		setEnterpriseServerUserName();
		setEnterpriseServerPassword();
		setEnterpriseServerAuthType();

		setSleepTime();
		setMaxWaitPending();
		MAX_PENDING_TIME = SLEEP_TIME * MAX_WAITING_PENDING;
		
		setMaxRetryCount();

		/* Log on using the Enterprise SDK */
		logger.debug("Connected to Enterprise Session Server ");
		setSessionManager();
		setInfoStore();
		setCMSReportFolderID();
	}

	/**
	 * log out from the Enterprise server
	 */
	protected void setEnterpriseServerLogout() {
		reportBatchCount--;
		logger.debug("~~~~~ at setEnterpriseServerLogout - reportBatchCount: "+reportBatchCount);
		if (reportBatchCount == 0 && oEnterpriseSession != null) {
			oEnterpriseSession.logoff();
			oEnterpriseSession = null;
			oInfoStore = null;

			logger.debug("Log out from Enterprise Server ");
		} else {
			logger.debug("Report Enterprise Server is not logout, there are pending report batch job... ");
		}
	}

	public int getSleepTime() {
		return SLEEP_TIME;
	}

	public static int getMaxWaitPending() {
		return MAX_WAITING_PENDING;
	}

	public int getMaxRetryCount() {
		return MAX_RETRY_COUNT;
	}

	private static void setSleepTime() {
		String strSleepTime = PropertyManager.getValue(ReportConstants.RPT_SLEEP_TIME);
		try {
			SLEEP_TIME = Integer.parseInt(strSleepTime);
		}
		catch (NumberFormatException e) {
			SLEEP_TIME = ReportConstants.SLEEP_TIME;
			logger.error("monitoring job Transaction Timeout not set, defaulting to 2500 seconds ");
		}
	}

	private void setCMSReportFolderID() {
		IInfoObjects oInfoObjects = null;
		try {
			oInfoObjects = (IInfoObjects) oInfoStore.query("SELECT TOP 1 * " + "FROM CI_INFOOBJECTS "
					+ "WHERE SI_PROGID = 'CrystalEnterprise.Folder' " + "AND SI_INSTANCE=0 AND SI_NAME='"
					+ lookup(ReportConstants.SERVER_REPORT_FOLDER_NAME) + "'");
		}
		catch (SDKException e) {
			throw new ReportServerFailureException(
					"failed to query crystal report server info store for the folder name.", e);
		}
		CMS_REPORT_FOLDER_ID = ((IInfoObject) oInfoObjects.get(0)).getID();

		logger.debug("CMS_REPORT_FOLDER_ID: " + CMS_REPORT_FOLDER_ID);
	}

	/**
	 * Helper method to get report file name without suffix (.rpt)
	 */
	public static String getReportFileNameWithoutSuffix(String reportFileName) {
		int endIndex = reportFileName.indexOf(".rpt");
		String temp = reportFileName.substring(0, endIndex);

		logger.debug("Report File Name generated : " + temp);
		return temp;
	}

	/**
	 * Utility function to set the database logon credentials that are used when
	 * the schedule to run.
	 * 
	 * @param report
	 */
	protected static void setReportDBLogon(IReport report) throws ReportException {
		try {
			// Set the custom username and password
			ISDKList dbLogons = report.getReportLogons();

			// Set the db logon credentials to be the same for each db
			// connection.
			logger.debug("<<< dbLogons size: " + dbLogons.size());

			for (int i = 0; i < dbLogons.size(); i++) {
				IReportLogon dbLogon = (IReportLogon) dbLogons.get(i);
				dbLogon.setOriginalDataSource(false);
				dbLogon.setCustomUserName(DB_USERNAME);
				dbLogon.setCustomPassword(DB_PASSWORD);
				dbLogon.setCustomDatabaseName(DB_NAME);
				dbLogon.setCustomServerName(DSN_DATASOURCE);
				logger.debug("dbLogon.getConnectionInfo().getAttributes()  = "
						+ dbLogon.getConnectionInfo().getAttributes());
			}
		}
		catch (SDKException e) {
			throw new ReportServerFailureException("failed to logon crystal report [" + report + "]", e);
		}
	}

	protected static void printReportDBLogonInfo(IReport report) throws ReportException {
		try {
			// Set the custom username and password
			ISDKList dbLogons = report.getReportLogons();

			// Set the db logon credentials to be the same for each db
			// connection.
			logger.debug("<<< dbLogons size: " + dbLogons.size());
			for (int i = 0; i < dbLogons.size(); i++) {
				IReportLogon dbLogon = (IReportLogon) dbLogons.get(i);

				logger.debug("set Original Data Source: " + dbLogon.isOriginalDataSource());

				logger.debug("<<<<< custom dll: " + dbLogon.getCustomDatabaseDLLName());

				logger.debug("<<<<<< custom server type: " + dbLogon.getCustomServerType());
				logger.debug("<<<< custom Database name: " + dbLogon.getCustomDatabaseName());

				logger.debug("<<<< custom user name: " + dbLogon.getCustomUserName());

				logger.debug("<<<<< custom server name: " + dbLogon.getCustomServerName());
			}
		}
		catch (SDKException ex) {
			throw new ReportServerFailureException("failed to retrieve crystal report logon info [" + report + "]", ex);
		}
	}

	/**
	 * Setting parameters for the report.
	 * 
	 * @param oReport
	 */
	protected static void setReportParameters(IReport oReport, ReportParameter parameter) {

		// Retrieve the lsit of parameters on the report
		List paramList = new ArrayList();
		try {
			paramList = oReport.getReportParameters();
		}
		catch (SDKException ex) {
			throw new ReportServerFailureException("failed to retrieve crystal report [" + oReport + "]parameter list",
					ex);
		}

		// Create an IReportParameter interface
		IReportParameter oReportParameter;
		IReportParameterValues iReportParameterValues = null;

		logger.debug("-------------------\nReport Parameters Set For Scheduling");

		Map paramMap;
		if (parameter instanceof RiskProfileReportParameter) {
			paramMap = ((RiskProfileReportParameter) parameter).getParamMap();
		}
		else {
			paramMap = parameter.getTemplateParameters();
		}

		// For each parameter in the report,
		// set a parameter value appropriate for the parameter type
		for (int i = 0; i < paramList.size(); i++) {

			oReportParameter = (IReportParameter) paramList.get(i);
			logger.debug(oReportParameter.getParameterName() + "["
					+ getReportColumnType(oReportParameter.getValueType()) + "] ");
			String param = "";
			if ((paramList.size() == 1) && (paramMap.size() == 1)) {
				param = castObject(paramMap.values().iterator().next());
			}
			else {
				param = castObject(paramMap.get(oReportParameter.getParameterName()));
			}

			// Get the name of the report the parameter is on (blank if main
			// report, otherwise populated with subreport name).
			String reportTemplateName = oReportParameter.getReportName();
			logger.debug("<<<<<<<<<<< report template name: " + reportTemplateName);

			int paramType = oReportParameter.getValueType();
			iReportParameterValues = oReportParameter.getCurrentValues();

			switch (paramType) {
			case IReportParameter.ReportVariableValueType.STRING:
                setReportParamSingleValue(iReportParameterValues, (String) param);
				break;
			case IReportParameter.ReportVariableValueType.NUMBER:
				setReportParamSingleValue(iReportParameterValues, param);
				break;
			case IReportParameter.ReportVariableValueType.DATE:
				if (!oReportParameter.isRangeValueSupported()) {
					setReportParamSingleValue(iReportParameterValues, param);
				}
				else {
					setReportParamRangeValues(iReportParameterValues, param);
				}
				break;
			case IReportParameter.ReportVariableValueType.DATE_TIME:
				setReportParamSingleValue(iReportParameterValues, param);
				break;
			case IReportParameter.ReportVariableValueType.TIME:
				setReportParamSingleValue(iReportParameterValues, param);
				break;
			case IReportParameter.ReportVariableValueType.BOOLEAN:
				setReportParamSingleValue(iReportParameterValues, param);
				break;
			case IReportParameter.ReportVariableValueType.CURRENCY:
				setReportParamSingleValue(iReportParameterValues, param);
				break;
			}
		}
	}



    /**
	 * Helper method to get param from ClassCast object from HashMap
	 */
	private static String castObject(Object objParam) {
		String param = "";
		String msg = " ";

		if (objParam instanceof Date) {
			msg = " Date ";
			Calendar cal = new GregorianCalendar();
			cal.setTime((Date) objParam);
			param = "Date(" + cal.get(Calendar.YEAR) + "," + (cal.get(Calendar.MONTH) + 1) + ","
					+ cal.get(Calendar.DAY_OF_MONTH) + ")";
		}
		else if (objParam instanceof Long) {
			msg = " Long ";
			param = ((Long) objParam).toString();
			msg = " Long ";
		}
		else {
			param = (String) objParam;
			msg = " String ";
		}

		logger.debug("ReportParemeter [" + msg + "] = " + param);

		return param;
	}

	/**
	 * Helper metohd to set single report parameter value
	 * @param rptParameterVals - type IReportParameterValues
	 * @param param - type String
	 */
	private static void setReportParamSingleValue(IReportParameterValues rptParameterVals, String param) {
		rptParameterVals.clear();

		IReportParameterSingleValue currentValue = rptParameterVals.addSingleValue();
		logger.debug("<<<<< Cur Value   " + currentValue.getValue());
		if ((param == null) || param.equals("null")) {
			currentValue.setNull(true);
		}
		else {
			currentValue.setValue(param);
		}
		logger.debug("<<<<< Set Value   " + currentValue.getValue());
	}

	/**
	 * Helper metohd to set range report parameter value
	 * @param rptParameterVals - type IReportParameterValues
	 * @param param - type String
	 */
	private static void setReportParamRangeValues(IReportParameterValues rptParameterVals, String param) {
		rptParameterVals.clear();

		IReportParameterRangeValue currentRangeValue = rptParameterVals.addRangeValue();
		logger.debug("<<<<<< Curr From Value " + currentRangeValue.getFromValue().getValue());
		logger.debug("<<<<<< Curr To Value " + currentRangeValue.getToValue().getValue());
		currentRangeValue.getFromValue().setValue(param);
		currentRangeValue.getToValue().setValue(param);
		logger.debug("<<<<<< Set To Value " + currentRangeValue.getFromValue().getValue());
		logger.debug("<<<<<< Set To Value " + currentRangeValue.getToValue().getValue());
	}

	private static String getReportColumnType(int Type) {
		String columnType = "";
		switch (Type) {
		case IReportParameter.ReportVariableValueType.BOOLEAN:
			columnType = "Boolean";
			break;
		case IReportParameter.ReportVariableValueType.DATE:
			columnType = "Date";
			break;
		case IReportParameter.ReportVariableValueType.CURRENCY:
			columnType = "Currency";
			break;
		case IReportParameter.ReportVariableValueType.DATE_TIME:
			columnType = "Date Time";
			break;
		case IReportParameter.ReportVariableValueType.STRING:
			columnType = "String";
			break;
		case IReportParameter.ReportVariableValueType.NUMBER:
			columnType = "Number";
			break;
		case IReportParameter.ReportVariableValueType.TIME:
			columnType = "Time";
			break;
		default:
			columnType = "String";
			break;
		}

		return columnType;
	}

	/**
	 * Starts the report Scheduling process The reports to be generated depends
	 * on the input scope
	 */
	protected static void scheduleReports(IInfoObjects oInfoObjects, IInfoStore oInfoStore, String reportName,
			int exportFormat, ReportParameter parameter) throws ReportException {

		try {

			logger.debug("info object size: " + oInfoObjects.size());
			// Grab the first object in the collection, this will be the object
			// that
			// will be scheduled.
			IReport oReport = (IReport) oInfoObjects.get(0);

			// set DB log on
			setReportDBLogon(oReport);

			// print DB Log on info
			printReportDBLogonInfo(oReport);

			setReportParameters(oReport, parameter);

			IReportFormatOptions reportFormat = oReport.getReportFormatOptions();

			// Set report format.
			reportFormat.setFormat(exportFormat);

			IDestinationPlugin destinationPlugin = getDestinationPlugin(oInfoStore, reportName);

			// Retrieve the ISchedulingInfo Interface for the Report object and
			// set
			// the schedule
			// time (right Now) and type (run once)
			ISchedulingInfo schedInfo = oReport.getSchedulingInfo();

			Date futureDate = getReportServerSystemTime();
			// Date futureDate = new Date( System.currentTimeMillis());

			schedInfo.setBeginDate(futureDate);
			schedInfo.setType(CeScheduleType.ONCE);

			// Retrieve the IDestination interface from the SchedulingInfo
			// object
			// use the setFromPlugin()
			// to apply the changes we made to the IDestinationPlugin object
			// returned from the IStore
			IDestination destination = schedInfo.getDestination();
			destination.setFromPlugin(destinationPlugin);
			destination.setCleanup(true);

			// Schedule the InfoObjects.
			oInfoStore.schedule(oInfoObjects);
			
			logger.debug(reportName + 
					" [" + oReport.properties().getProperty("SI_NEW_JOB_ID").getValue().toString() + "] " +
							"has been scheduled successfully ");
		}
		catch (SDKException e) {
			throw new ReportServerFailureException(
					"failed to schedule report in crystal report server, probably due to server error.", e);
		}
	}

	/**
	 * Utility function for obtain the appropriate destination plugin and set
	 * options for that plugin.
	 * 
	 * @param oInfoStore
	 * @param reportName
	 * @return
	 */
	private static IDestinationPlugin getDestinationPlugin(IInfoStore oInfoStore, String reportName)
			throws ReportException {

		// Retrieve the UnmanagedDisk Destination plugin from the InfoStore
		// this should be cast as an IDestinationPlugin *DON'T FORGET THE get(0)
		// AT THE END**
		IDestinationPlugin destinationPlugin = null;
		try {
			destinationPlugin = (IDestinationPlugin) oInfoStore.query(
					"SELECT TOP 1 * " + "FROM CI_SYSTEMOBJECTS " + "WHERE SI_NAME='CrystalEnterprise.DiskUnmanaged'")
					.get(0);
		}
		catch (SDKException e) {
			throw new ReportServerFailureException(
					"failed to query crystal report server info store for destination plugin.", e);
		}

		// Retrieve the Scheduling Options and cast it as IDiskUnmanagedOptions.
		// This interface is the one which allows us to add the file location
		// for the scheduling.
		IDiskUnmanagedOptions diskUnmanagedOptions = (IDiskUnmanagedOptions) destinationPlugin.getScheduleOptions();
		List listDestination = diskUnmanagedOptions.getDestinationFiles();
		String location = lookup(ReportConstants.REPORT_FOLDER_PATH) + reportName;
		logger.debug("Report location = " + location);
		listDestination.add(location);

		return destinationPlugin;
	}

	/**
	 * Helper method to get Crystal report constant for report format
	 */
	public static int getReportFormatByType(String type) {
		if (type.equals(ReportConstants.PDF_REPORT)) {
			return IReportFormatOptions.CeReportFormat.PDF;
		}
		if (type.equals(ReportConstants.EXCEL_REPORT)) {
			return IReportFormatOptions.CeReportFormat.EXCEL;
		}
		if (type.equals(ReportConstants.WORD_REPORT)) {
			return IReportFormatOptions.CeReportFormat.WORD;
		}
		return IReportFormatOptions.CeReportFormat.PDF;
	}

	public static String getFileExtensionByType(int reportType) {

		String fileExt = "";
		String fileSeperator = ".";

		switch (reportType) {
		case IReportFormatOptions.CeReportFormat.CRYSTAL_REPORT:
			fileExt = fileSeperator + ReportConstants.FILE_RPT;
			break;
		case IReportFormatOptions.CeReportFormat.EXCEL:
			fileExt = fileSeperator + ReportConstants.FILE_EXCEL;
			break;
		case IReportFormatOptions.CeReportFormat.EXCEL_DATA_ONLY:
			fileExt = fileSeperator + ReportConstants.FILE_EXCEL;
			break;
		case IReportFormatOptions.CeReportFormat.PDF:
			fileExt = fileSeperator + ReportConstants.FILE_PDF;
			break;
		case IReportFormatOptions.CeReportFormat.RTF:
			fileExt = fileSeperator + ReportConstants.FILE_RTF;
			break;
		case IReportFormatOptions.CeReportFormat.RTF_EDITABLE:
			fileExt = fileSeperator + ReportConstants.FILE_RTF;
			break;
		case IReportFormatOptions.CeReportFormat.TEXT_CHARACTER_SEPARATED:
			fileExt = fileSeperator + ReportConstants.FILE_TEXT;
			break;
		case IReportFormatOptions.CeReportFormat.TEXT_PAGINATED:
			fileExt = fileSeperator + ReportConstants.FILE_TEXT;
			break;
		case IReportFormatOptions.CeReportFormat.TEXT_PLAIN:
			fileExt = fileSeperator + ReportConstants.FILE_TEXT;
			break;
		case IReportFormatOptions.CeReportFormat.TEXT_TAB_SEPARATED:
			fileExt = fileSeperator + ReportConstants.FILE_TEXT;
			break;
		case IReportFormatOptions.CeReportFormat.TEXT_TAB_SEPARATED_TEXT:
			fileExt = fileSeperator + ReportConstants.FILE_TEXT;
			break;
		case IReportFormatOptions.CeReportFormat.USER_DEFINED:
			fileExt = "";
			break;
		case IReportFormatOptions.CeReportFormat.WORD:
			fileExt = fileSeperator + ReportConstants.FILE_WORD;
			break;
		default:
			fileExt = fileSeperator + ReportConstants.FILE_RPT;
			break;
		}

		return fileExt;
	}

	protected String checkReportStatus(IInfoObjects oInfoObjects, Date startTime) { //, String genReportName) {
		// Obtain the new job id for the scheduled report.
		IInfoObject infoObject = (IInfoObject) oInfoObjects.get(0);
		IProperty newJobIdProp = infoObject.properties().getProperty("SI_NEW_JOB_ID");
		String newJobId = newJobIdProp.getValue().toString();

		//long startTime = System.currentTimeMillis();

		int jobStatus = getReportStatus(oInfoStore, newJobId, startTime);

		//long endTime = System.currentTimeMillis();

		//logger.debug("Report Generation Time Taken in Milliseconds: " + (endTime - startTime));
		//logger.debug("Report Generate Status " + jobStatus);
		if (jobStatus == ISchedulingInfo.ScheduleStatus.COMPLETE) {
			//return genReportName;
			return ReportConstants.RPT_STATUS_COMPLETED;
		}
		else if (jobStatus == ReportConstants.KILL_PENDING_TOO_LONG) {
			return ReportConstants.RESCHEDULE_REQUIRED;
		}
		/*
		else {
			if ((ERR_LOG == null) || (ERR_LOG.length() == 0)) {
				ERR_LOG = "Failed during report generation";
			}
			logger.debug("ERR_LOG: " + ERR_LOG);
		}
		return null;
		*/
		return String.valueOf(jobStatus);
	}

	/**
	 * Gives the report status for the schedule report
	 * 
	 * @param infoStore
	 * @param jobId
	 * @return job status 0 = Running 1 = Complete 3 = failed 8 = paused 9 =
	 *         pending
	 */
	private int getReportStatus(IInfoStore infoStore, String jobId, Date startTime) {
		int jobStatus = 0;
		// Query for the report object in the CMS with job id.
		try {
			IInfoObjects oInfoObjects = (IInfoObjects) infoStore.query("SELECT * FROM CI_INFOOBJECTS WHERE SI_ID="
					+ jobId);

			if (oInfoObjects.size() > 0) {
				IInfoObject scheduledObj = (IInfoObject) oInfoObjects.get(0);
				
				// Check the status of this job.
				jobStatus = scheduledObj.getSchedulingInfo().getStatus();
				//int maxTime = 0;
				Date currentTime = DateUtil.getDate();

				long maxTime = DateUtil.getDate().getTime() - startTime.getTime();
				
				// If the scheduling status is in a non-finished state, then
				// sleep for a while to wait for final status
				/*
				while ((jobStatus != ISchedulingInfo.ScheduleStatus.COMPLETE)
						&& (jobStatus != ISchedulingInfo.ScheduleStatus.FAILURE)
						&& (jobStatus != ReportConstants.KILL_PENDING_TOO_LONG)) {
					maxTime++;
					Thread.sleep(SLEEP_TIME);
					*/
				//oInfoObjects = (IInfoObjects) infoStore.query("SELECT * FROM CI_INFOOBJECTS WHERE SI_ID=" + jobId);
				//if (oInfoObjects.size() > 0) {
				//	scheduledObj = (IInfoObject) oInfoObjects.get(0);
				//	jobStatus = scheduledObj.getSchedulingInfo().getStatus();
				switch (jobStatus) {
					case ISchedulingInfo.ScheduleStatus.RUNNING: // Job is
						// Running
						logger.debug(maxTime + "\tJob [" + jobId + "] is currently running. ");
						break;
					case ISchedulingInfo.ScheduleStatus.COMPLETE: // Job has
						// Complete
						logger.debug(maxTime + "\tJob [" + jobId + "] has completed successfully.");
						break;
					case ISchedulingInfo.ScheduleStatus.FAILURE: // Job has
						// failed
						logger.debug(maxTime + "\tJob [" + jobId + "] failed to run.  Error Message: ");
						ERR_LOG = scheduledObj.getSchedulingInfo().getErrorMessage();
						break;
					case ISchedulingInfo.ScheduleStatus.PAUSED: // Job is
						// paused
						logger.debug(maxTime + "\tJob [" + jobId + "] is currently paused.");
						break;
					case ISchedulingInfo.ScheduleStatus.PENDING: // Job is
						// pending
						logger.debug(maxTime + "\tJob [" + jobId + "] is currently pending. ");
						break;
					default:
						logger.debug(maxTime+ "\tJob [" + jobId + "] with status unknown (set to completed) - "+ jobStatus);
						// Consider the report generation is completed
						// Assuming the status of the record is deleted from report server 
						// but the info object not yet deleted 
						jobStatus = ISchedulingInfo.ScheduleStatus.COMPLETE;
						
				}

				// kill the report scheduling job after pending for long
				// time
				//if ((maxTime >= MAX_WAITING_PENDING) &&
				if (maxTime >= MAX_PENDING_TIME &&
						ISchedulingInfo.ScheduleStatus.PENDING == jobStatus) {
					oInfoObjects.delete(scheduledObj);
					infoStore.commit(oInfoObjects);
					jobStatus = ReportConstants.KILL_PENDING_TOO_LONG;
				}
			}
			else {
				jobStatus = ISchedulingInfo.ScheduleStatus.COMPLETE;
				logger.debug("Job " + jobId + " has completed successfully");
			}
			//}
			//}
		}
		catch (SDKException ex) {
			logger.error(" failed to retrieve report status, for report job id [" + jobId + "]", ex);
		}
		/*
		catch (InterruptedException e) {
			logger.error("failed to interrupt current thread [" + Thread.currentThread().getName() + "]", e);
		}
		*/

		return jobStatus;
	}

	/**
	 * Helper method to check null or empty strings
	 * @param str
	 */
	protected static boolean isNull(String str) {
		return (str == null) || str.trim().equals("");
	}

	private static Date getReportServerSystemTime() throws ReportException {
		try {
			logger.debug("<<< at method getReportServerSystemTime... ");
			URLConnection con = getURLConnection();
			ObjectOutputStream objStream = new ObjectOutputStream(con.getOutputStream());
			objStream.writeObject(null);
			objStream.flush();
			objStream.close();
			logger.debug("Request send !");
			BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			logger.debug("Waiting for response...");
			String line;
			Date reportTime = new Date(System.currentTimeMillis());
			while ((line = reader.readLine()) != null) {
				logger.debug("line: " + line);
				reportTime = new Date(Long.parseLong(line));
				logger.debug("ReportTime: " + reportTime);
			}
			reader.close();
			return reportTime;
		}
		catch (Throwable t) {
			throw new ReportServerFailureException("failed to retrieve server system time, probably due to connection",
					t);
		}
	}

	private static URLConnection getURLConnection() throws KeyManagementException, NoSuchAlgorithmException,
			IOException {
		String urlStr = getAppURL();
		logger.debug("URL : " + urlStr);
		if (urlStr == null) {
			throw new InvalidReportParameterException("Invalid app url configuration", new String[] { "urlStr" });
		}
		URL urlObj = null;
		if (urlStr.startsWith("https:")) {
			urlObj = getHttpsURL(urlStr + "/GetReport.image?param=time");
		}
		else {
			urlObj = new URL(urlStr + "/GetReport.image?param=time");
		}

		logger.debug("create a new URL obj");
		URLConnection con = urlObj.openConnection();
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setRequestProperty("Content-type", "application/octest-stream");

		return con;
	}

	private static URL getHttpsURL(String urlStr) throws KeyManagementException, NoSuchAlgorithmException,
			MalformedURLException {
		trustAllHttpsCertificates();
		return new URL(urlStr);
	}

	private static String getAppURL() {

		return PropertyManager.getValue(ICMSUIConstant.CMS_REPORT_URL);

	}

	private static void trustAllHttpsCertificates() throws NoSuchAlgorithmException, KeyManagementException {
		String protocol = PropertyUtil.getInstance("/ofa_env.properties").getProperty("sslortls.protocol");
		System.out.println("protocol:"+protocol);
		SSLContext sc = SSLContext.getInstance(protocol);
		//SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, getTrustAllCerts(), new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(getHostNameVreifier());
	}

	private static TrustManager[] getTrustAllCerts() {
		final X509TrustManager finalTm = getX509TrustManager();
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {
				finalTm.checkClientTrusted(certs, authType);
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {
				finalTm.checkServerTrusted(certs, authType);
			}
		} };
		return trustAllCerts;
	}

	private static HostnameVerifier getHostNameVreifier() {
		HostnameVerifier verifier = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				DefaultLogger.debug(this,"URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;
			}
		};
		return verifier;
	}
	
	private static X509TrustManager getX509TrustManager() {
		TrustManagerFactory tmf = null;
		try {
			tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
		
		// Using null here initialises the TMF with the default trust store.
		tmf.init((KeyStore) null);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Get hold of the default trust manager
		X509TrustManager x509Tm = null;
		for (TrustManager tm : tmf.getTrustManagers()) {
		    if (tm instanceof X509TrustManager) {
		        x509Tm = (X509TrustManager) tm;
		        break;
		    }
		}

		return x509Tm;
		
	}
}