package com.integrosys.cms.batch.reports;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.TimeZone;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.techinfra.util.StringUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.IncompleteBatchJobException;
import com.integrosys.cms.batch.InvalidParameterBatchJobException;
import com.integrosys.cms.batch.factory.BatchJob;
import com.integrosys.cms.batch.factory.BatchParameterValidator;
import com.integrosys.cms.ui.common.CountryList;

/**
 * Report Scheduler for interface with Crystal Report Server.
 */
public class ReportScheduler extends CommonReportScheduler implements BatchJob {

	private BatchParameterValidator reportParameterValidator;

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public static String FOLDER_PATH;

	//private static int reportThreadCount = 0;

	private static int MAX_RPT_THREAD_COUNT;

	private static String[] allValidScopeTypes = new String[] { ReportConstants.REPORT_CONFIG_BY_ID,
			ReportConstants.GLOBAL_SCOPE, ReportConstants.MIS_CATEGORY, ReportConstants.SYSTEM_CATEGORY,
			ReportConstants.COUNTRY_SCOPE, ReportConstants.REGION_SCOPE, ReportConstants.EXCHANGE_SCOPE,
			ReportConstants.COMMODITY_SCOPE, ReportConstants.DOC_CATEGORY };

	public static String[] getAllValidScopeTypes() {
		return allValidScopeTypes;
	}

	

	protected static String[] scopeTypes = new String[] { ReportConstants.COUNTRY_SCOPE, ReportConstants.GLOBAL_SCOPE,
			ReportConstants.EXCHANGE_SCOPE, ReportConstants.REGION_SCOPE, ReportConstants.COMMODITY_SCOPE };

	private IReportSchedulerDAO reportSchedulerDao;

	
	protected ThreadLocal reportThread = new ThreadLocal();
	
	public void setReportParameterValidator(BatchParameterValidator reportParameterValidator) {
		this.reportParameterValidator = reportParameterValidator;
	}

	/**
	 * @return the batchParameterValidator
	 */
	public BatchParameterValidator getReportParameterValidator() {
		return reportParameterValidator;
	}

	/**
	 * @param reportSchedulerDao the reportSchedulerDao to set
	 */
	public void setReportSchedulerDao(IReportSchedulerDAO reportSchedulerDao) {
		this.reportSchedulerDao = reportSchedulerDao;
	}

	/**
	 * @return the reportSchedulerDao
	 */
	public IReportSchedulerDAO getReportSchedulerDao() {
		return reportSchedulerDao;
	}

	public void execute(Map context) throws BatchJobException {

		getReportParameterValidator().validate(context);

		try {
			startReportGeneration(context);
		}
		catch (InvalidReportParameterException ex) {
			throw new InvalidParameterBatchJobException("missing parameters required for report generation", ex);
		}
		catch (ReportServerFailureException ex) {
			throw new IncompleteBatchJobException("failed to connect to crystal report server", ex);
		}
	}

	/**
	 * <p>
	 * Starts the report generation process The reports to be generated depends
	 * on the input scope
	 * 
	 * <p>
	 * Global scope runs jobs which are non-country specific Country scope runs
	 * jobs which are specific to country Region scope runs jobs for countries
	 * located in the input region Exchange scope runs jobs by exchange name
	 * basis Commodity scope runs jobs by commodity type basis MIS scope runs
	 * jobs by country, organisation (depends) and date basis
	 * 
	 * <p>
	 * The associated report config with regards to the scope will be retrieved
	 * for report generation. Each report is then scheduled according to their
	 * frequency Reports differ in generation frequency
	 * 
	 * <p>
	 * The report settings, associated templates, scope coverage and the
	 * generation frequency are maintained in the database
	 * 
	 * @param paramMap
	 * @throws Exception
	 */
	public void startReportGeneration(Map paramMap) throws ReportException {
		try {
			logger.debug("======= Entering startReportGeneration ========");
	
			String scope = (String) paramMap.get(ReportConstants.KEY_SCOPE);
			String reportMasterID = (String) paramMap.get(ReportConstants.KEY_ID);
			String country = (String) paramMap.get(ReportConstants.KEY_COUNTRY);
			String strReportDate = (String) paramMap.get(ReportConstants.KEY_DATE);
			String centre = (String) paramMap.get(ReportConstants.KEY_CENTRE);
	
			setup();
			reportThread.set(new ArrayList());
	
			ParamData data = getParamData(country, centre, strReportDate);
			String reportScope = getScope(scope, reportMasterID);
			logger.debug("Job Scope: " + scope + "\tReport Scope: " + reportScope);
	
			// start for MIS Centre reports By Jitu
			if (isMISReportByCentreCode() && scope != null && scope.equals(ReportConstants.MIS_CATEGORY)) {
				logger.debug(" Generating MIS Centre Report Scope: " + reportScope);
	
				String[] centreArray = getReportSchedulerDao().getCenterCodes(country);
	
				if (centreArray != null && centreArray.length > 0) {
					logger.debug(" No of Centre code for MIS Centre Reports >>>>>>>>>>>> " + centreArray.length);
	
					OBReport[] reportJobs = getReportJobs(reportScope, country, reportMasterID);
					if (reportJobs != null && reportJobs.length > 0) {
						logger.debug(" No of MIS Centre Reports to be Generated  : " + reportJobs.length);
	
						// start here to generate mis centre for each centre code
						for (int k = 0; k < centreArray.length; k++) {
							String centreCode = centreArray[k];
							data.setCentre(centreCode);
	
							logger.debug(" Generating MIS Centre Reports with centreCode : " + centreCode);
	
							// Set generated report folder path
							setReportFolderPath(data.getReportDate());
							triggerReportsGeneration(reportJobs, data, reportScope);
	//						for (int i = 0; i < reportJobs.length; i++) {
	//							logger.debug("<<<  reportMasterID " + reportJobs[i].getReportMasterId());
	//							if (requiredRunReport(reportJobs[i], data)) {
	//								logger.debug("<<< required run report.. ReportId " + reportJobs[i].getReportId());
	//								runReport(reportJobs[i], reportScope, data);
	//							}
	//						}
						}
						// end here to generate mis centre for each centre code
	
					}
					else {
						logger.debug(" No of MIS Centre Reports to be Generated ");
					}
	
				}
				else {
					logger.debug(" No of Centre code in DB, Please   check DB.. ");
				}
			}
			else {
				// ORIGINAL CODE
				// Set generated report folder path
				setReportFolderPath(data.getReportDate());
				OBReport[] reportJobs = getReportJobs(reportScope, country, reportMasterID);
				triggerReportsGeneration(reportJobs, data, reportScope);
			}
	
			setEnterpriseServerLogout();
			logger.debug("======= Finished report generation ==========");
		} catch (ReportException e) {
			setEnterpriseServerLogout();
			throw e;
		}
	}

	private void triggerReportsGeneration(OBReport[] reportJobs, ParamData data, String reportScope) {
		for (int i = 0; i < reportJobs.length; i++) {
			logger.debug("<<<  reportMasterID " + reportJobs[i].getReportMasterId());
			if (requiredRunReport(reportJobs[i], data)) {
				logger.debug("<<< required run report.. ReportId " + reportJobs[i].getReportMasterId());
				OBReportConfig reportConfig = getReportConfig(reportJobs[i].getReportMasterId(), data.getCountry(), reportJobs[i]
						.getFrequency());		
				reportConfig.setReportDate(data.getReportDate());
				boolean hasOrgCode = (reportConfig.getOrganisations() != null) ? (reportConfig.getOrganisations().length > 0)
						: false;				
				ReportParameter parameter = ReportParameterFactory.createReportParameter(reportScope, hasOrgCode, data,
						reportConfig);			

				ReportContext ctx = new ReportContext();
				ctx.setParam(parameter);
				if (hasOrgCode) {
					String[] organisations = reportConfig.getOrganisations();
					for (int j = 0; j < organisations.length; j++) {
						if (triggerAndVerifyReport(true)) {
							((MISOrgParameter) parameter).setOrganisation(organisations[j]);
							reportConfig.setOrganisation(organisations[j]);
							ctx.setReportConfig(reportConfig);
							generateReport(ctx);
						}
					}
				}
				else {
					if (triggerAndVerifyReport(true)) {
						if (parameter instanceof MISReportParameter) {
							if (data.getCentre() != null) {
								reportConfig.setOrganisation(data.getCentre()); // added by
								// Jitu
							}
						}
						ctx.setReportConfig(reportConfig);						
						generateReport(ctx);
					}
				}								
			}
		}
		// check the report status 
		triggerAndVerifyReport(false);
	}	

	private boolean triggerAndVerifyReport(boolean isTriggerRpt) {	
		ArrayList scheduleReportList = (ArrayList)reportThread.get();
		logger.debug("~~~~ method triggerAndVerifyReport: isTriggerRpt: "+isTriggerRpt+"\tscheduleReportList : "+scheduleReportList.size() );
		if (isTriggerRpt && scheduleReportList.size() < MAX_RPT_THREAD_COUNT) 
			return true;
		
		int index = 0;
		if ((!isTriggerRpt && scheduleReportList.size() > 0) ||
				isTriggerRpt && scheduleReportList.size() >= MAX_RPT_THREAD_COUNT) {
			while (true) {
				index = 0;
				while (index < scheduleReportList.size()) {
					ReportContext ctx = (ReportContext)scheduleReportList.get(index);
					String status = checkReportStatus(ctx.getOInfoObjects(),ctx.getReportStartTime());
					if (ReportConstants.RPT_STATUS_COMPLETED.equals(status)) {						
						index = scheduleNextReportFormat(ctx, index);
					} else if (ReportConstants.RESCHEDULE_REQUIRED.equals(status)) {
						// report that is required to rescheduled
						if (ctx.getRetryCount() < MAX_RETRY_COUNT) {							
							((ArrayList)reportThread.get()).remove(index);
							generateReport(ctx);
							index++;
						} else { // kill this report scheduling for report that reach max retry count
							logger.debug("Failed to generate pending report after retry " + MAX_RETRY_COUNT + " times... ");
							ctx.setErrLog("Exceed maximum retry count (" + MAX_RETRY_COUNT + ") for pending report generation"); 
							
							ctx.setRetryCount(0);
							index = scheduleNextReportFormat(ctx, index);			
						}
					} else if (String.valueOf(ISchedulingInfo.ScheduleStatus.FAILURE).equals(status)) {
						// report that is failed to generate
						ctx.setErrLog(ERR_LOG);
						index = scheduleNextReportFormat(ctx, index);						
					} else {
						logger.debug("~~~~~ Report : "+ ctx.getReportFormat() + "\tgeneratedReportName: " + ctx.getGenReportName()+" not yet finish generating with status: "+ status);
						index++;
					}		
					scheduleReportList = (ArrayList)reportThread.get();
				}
				try {
					if (scheduleReportList.size() >= MAX_RPT_THREAD_COUNT ||
							(!isTriggerRpt && scheduleReportList.size() > 0)) {
						Thread.sleep(SLEEP_TIME);
					} else {
						break;
					}
				}	catch (InterruptedException e) {
					logger.error("failed to interrupt current thread [" + Thread.currentThread().getName() + "]", e);
				}
			}
		}
					
		return true;		
	}
	
	
	protected int scheduleNextReportFormat(ReportContext ctx, int index) {
		if (index != ICMSConstant.INT_INVALID_VALUE)
			((ArrayList)reportThread.get()).remove(index);
		
		setReportGenerateInfoByFormat(ctx.getGenReportName(), ctx.getReportFormat(), ctx.getReportConfig());
		logger.debug("~~~~~~~~~~ Format: " + ctx.getReportFormat() + "\tgeneratedReportName: " + ctx.getGenReportName());
		
		ctx.increaseExportFormatIndex();
		if (ctx.getReportFormat().equals("")) { // finish generate report
			ctx.getReportConfig().setEndTime(DateUtil.getDate());
			if (ctx.getParam() instanceof RiskProfileReportParameter) {
				ctx.getReportConfig().setTitleMask(((RiskProfileReportParameter) ctx.getParam()).getReportDisplayName());
			}
			
			insertGeneratedReportInfoToDB(ctx);	
			return index;
		} else { // still has more report format to generate
			generateReport(ctx);
		}		
		return index++;
	}
	
	/**
	 * Generate report
	 */
	protected void generateReport(ReportContext ctx)
			throws ReportException {

		logger.debug(" at generateReport: "+ctx.getReportConfig().getReportMasterId());

		ctx.getReportConfig().setStartTime(DateUtil.getDate());
		ERR_LOG = null;
		ctx.setErrLog(null);
		ctx.setReportStatus(null);
		ctx.setGenReportName(null);
		
		ctx = makeReportScheduling(ctx);
		if (ctx.getErrLog() != null && ctx.getErrLog().length() > 0) {
			scheduleNextReportFormat(ctx, ICMSConstant.INT_INVALID_VALUE);
		} else {
			((ArrayList)reportThread.get()).add(ctx);
		}
	}

	/**
	 * Helper method to set report generated information into Database
	 */
	private void insertGeneratedReportInfoToDB(ReportContext ctx) {		
		OBReportConfig reportConfig = ctx.getReportConfig();
		logger.debug("~~~~~~~~ at method insertGeneratedReportInfoToDB : "+reportConfig.getReportMasterId());
		if (reportConfig.hasReportGenerated()) {
			getReportSchedulerDao().insertGeneratedReportInfoToDB(reportConfig);
		}

		if (reportConfig.hasFailedReport()) {
			reportConfig.setRemarks(ctx.getErrLog());
			getReportSchedulerDao().insertFailedReportInfoToDB(reportConfig);
		}
	}

	/**
	 * Helper method to set report generated information by report format type
	 */
	public void setReportGenerateInfoByFormat(String rptName, String rptFormat, OBReportConfig reportConfig) {
		int status = ICMSConstant.INT_INVALID_VALUE;
		if (rptName != null) {
			status = ISchedulingInfo.ScheduleStatus.COMPLETE;
		}
		else {
			status = ISchedulingInfo.ScheduleStatus.FAILURE;
		}

		setReportStatusAndFileName(status, rptName, rptFormat, reportConfig);
	}

	/**
	 * Helper method to set report generated status by report format type
	 */
	private void setReportStatusAndFileName(int status, String rptName, String rptFormat, OBReportConfig reportConfig) {
		if (rptFormat.equals(ReportConstants.PDF_REPORT)) {
			reportConfig.setPdfStatus(status);
			reportConfig.setPdfFileName(rptName);
		}
		else if (rptFormat.equals(ReportConstants.EXCEL_REPORT)) {
			reportConfig.setXlsStatus(status);
			reportConfig.setXlsFileName(rptName);
		}
		else if (rptFormat.equals(ReportConstants.WORD_REPORT)) {
			reportConfig.setWordStatus(status);
			reportConfig.setWordFileName(rptName);
		}
	}

	/**
	 * Helper method to set report path
	 */
	protected void setReportFolderPath(Date reportDate) {
		SimpleDateFormat folderFormat = new SimpleDateFormat("yyyy-MM-dd");
		FOLDER_PATH = folderFormat.format(reportDate);
	}

	protected OBReportConfig getReportConfig(String reportMasterId, String country) {
		return getReportConfig(reportMasterId, country, null);
	}

	/**
	 * Helper method to get report configuration
	 * @param reportMasterId
	 * @param country
	 */
	public OBReportConfig getReportConfig(String reportMasterId, String country, String frequency) {
		OBReportConfig config = getReportSchedulerDao().getReportConfig(reportMasterId, country);
		config.setFolderPath(FOLDER_PATH);
		config.setCountry(country);
		config.setTitleMask(generateReportTitle(config));

		setGenerateScopeCode(config);
		// config.setReportName(getReportFileNameWithoutSuffix(config.getReportName()));
		config.setReportName(config.getReportName());
		if (frequency != null) {
			config.setFrequency(frequency);
		}
		return config;
	}

	/**
	 * Helper method to set report title
	 */
	private String generateReportTitle(OBReportConfig reportConfig) {
		logger.debug("<<<<<<<< scope: " + reportConfig.getScope());

		if (reportConfig.getScope().equalsIgnoreCase(ReportConstants.COUNTRY_SCOPE)) {
			return generateCountryReportTitle(reportConfig);
		}

		if (reportConfig.getScope().equalsIgnoreCase(ReportConstants.SYSTEM_CATEGORY)
				|| reportConfig.getScope().equalsIgnoreCase(ReportConstants.GLOBAL_SCOPE)) {
			return reportConfig.getTitleMask();
		}

		if (reportConfig.getScope().equalsIgnoreCase(ReportConstants.REGION_SCOPE)) {
			return generateRegionReportTitle(reportConfig);
		}

		if (reportConfig.getScope().equalsIgnoreCase(ReportConstants.COMMODITY_SCOPE)) {
			return generateCommodityReportTitle(reportConfig);
		}

		if (reportConfig.getScope().equalsIgnoreCase(ReportConstants.EXCHANGE_SCOPE)) {
			return generateExchangeReportTitle(reportConfig);
		}

		// todo other remaining scope...

		return generateMISReportTitle(reportConfig);
	}

	/**
	 * Helper method to generate Exchange report title
	 */
	private String generateExchangeReportTitle(OBReportConfig reportConfig) {
		logger.debug("Entering method generateExchangeReportTitle");

		String title = reportConfig.getTitleMask();
		logger.debug("Title=" + title);
		String stockExchange = reportConfig.getCountry();
		logger.debug("StockExchange=" + stockExchange);

		if (stockExchange != null) {
			logger.debug("stockExchange= " + stockExchange);
			String exchange = getReportSchedulerDao().getStockExchangeDescription(stockExchange);
			String repl = exchange + " - " + stockExchange;
			title = StringUtil.replaceStringOnce(title, ReportConstants.REPLACE_SYMBOL, repl);
		}

		logger.debug("title= " + title);
		return title;
	}

	/**
	 * Helper method to generate Commmodity report title
	 */
	private String generateCommodityReportTitle(OBReportConfig reportConfig) {
		logger.debug("Entering method generateCommodityReportTitle");

		String title = reportConfig.getTitleMask();
		String commodityType = reportConfig.getCountry();

		if (commodityType != null) {
			logger.debug("commodityType= " + commodityType);
			String subtype = getReportSchedulerDao().getCommoditySubType(commodityType);
			logger.debug("subtype= " + subtype);
			title = title + subtype;
		}

		logger.debug("title= " + title);
		return title;
	}

	/**
	 * Helper method to generate Region report title
	 */
	private String generateRegionReportTitle(OBReportConfig reportConfig) {
		logger.debug("Entering method generateRegionReportTitle");

		String title = reportConfig.getTitleMask();
		String regionCode = reportConfig.getCountry();

		if (regionCode != null) {
			logger.debug("regionCode= " + regionCode);
			String repl = getReportSchedulerDao().getRegionDescription(regionCode);
			title = StringUtil.replaceStringOnce(title, ReportConstants.REPLACE_SYMBOL, repl);
		}

		logger.debug("title= " + title);
		return title;
	}

	/**
	 * Helper method to set Country report title
	 */
	private String generateCountryReportTitle(OBReportConfig reportConfig) {
		logger.debug("Entering generateCountryReportTitle... ");
		String title = reportConfig.getTitleMask();
		if (reportConfig.getCountry() != null) {
			CountryList countries = CountryList.getInstance();
			String repl = countries.getCountryName(reportConfig.getCountry());
			title = StringUtil.replaceStringOnce(title, ReportConstants.REPLACE_SYMBOL, repl);
		}
		return title;
	}

	/**
	 * Helper method to set MIS report title
	 */
	private String generateMISReportTitle(OBReportConfig reportConfig) {

		logger.debug("Entering method generateMISReportTitle");
		String title = reportConfig.getTitleMask();
		logger.debug("Title=" + title);
		String frequency = reportConfig.getFrequency();
		logger.debug("frequency=" + frequency);

		if (frequency != null) {
			logger.debug("frequency= " + frequency);
			String repl = frequency;
			title = StringUtil.replaceStringOnce(title, ReportConstants.REPLACE_SYMBOL, repl);
		}
		logger.debug("title= " + title);
		if (title == null) {
			return "";
		}
		return title;
	}

	/**
	 * Helper method to set generated report scope code
	 */
	private void setGenerateScopeCode(OBReportConfig reportConfig) {
		String scope = reportConfig.getScope();
		if (scope.equals(ReportConstants.GLOBAL_SCOPE) || scope.equals(ReportConstants.COMMODITY_SCOPE)) {
			reportConfig.setScope(ReportConstants.GLOBAL_COUNTRY_CODE);
		}
		else if (scope.equalsIgnoreCase(ReportConstants.MIS_CATEGORY)
				|| scope.equalsIgnoreCase(ReportConstants.DOC_CATEGORY)) {
			reportConfig.setScope(reportConfig.getCountry());
		}
		else {
			reportConfig.setScope(reportConfig.getCountry() == null ? ReportConstants.NO_COUNTRY : reportConfig
					.getCountry());
		}
	}

	/**
	 * Check whether the report is needed to run for the day
	 * @param report
	 * @param data
	 * @return boolean
	 */
	private boolean requiredRunReport(OBReport report, ParamData data) {
		if (!isRunJobDay(report, data)) {
			return false;
		}

		if (!isReportGenForCountry(report.getReportMasterId(), data.getCountry())) {
			return false;
		}

		return true;
	}

	/**
	 * Check if Report is Generated for country
	 * @param reportMasterId
	 * @param country
	 * @return boolean
	 */
	private boolean isReportGenForCountry(String reportMasterId, String country) {
		boolean isGenerate = getReportSchedulerDao().getIsReportShouldGeneratedByCountry(reportMasterId, country);
		return isGenerate;
	}

	/**
	 * determine whether job are scheduled to run
	 * @param report
	 * @param data
	 * @return true if job is to be run
	 */
	private boolean isRunJobDay(OBReport report, ParamData data) {

		Date reportDate = data.getReportDate();
		Calendar cal = getCalendarForDefaultTimeZone();

		logger.debug("Entering method runJob");
		String jobFrequency = getJobFrequency(report);
		logger.debug("jobFrequency = " + jobFrequency);

		cal.setTime(reportDate);

		if (ReportConstants.QUARTERLY.equalsIgnoreCase(jobFrequency)
				&& ((cal.get(Calendar.MONTH) == Calendar.APRIL) || (cal.get(Calendar.MONTH) == Calendar.JULY)
						|| (cal.get(Calendar.MONTH) == Calendar.OCTOBER) || (cal.get(Calendar.MONTH) == Calendar.JANUARY))
				&& (cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMinimum(Calendar.DAY_OF_MONTH))) {
			return true;
		}
		
		if (ReportConstants.YEARLY.equalsIgnoreCase(jobFrequency)&& cal.get(Calendar.MONTH) == Calendar.JANUARY
				&& cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMinimum(Calendar.DAY_OF_MONTH)) {
			return true;
		}

		int[] frequencyDays = getFrequencyDays(report.getReportMasterId(), jobFrequency);
		return isJobDay(frequencyDays, jobFrequency, cal);
	}

	/**
	 * Get Calendar for default time zone
	 */
	private static Calendar getCalendarForDefaultTimeZone() {
		return new GregorianCalendar(TimeZone.getDefault());
	}

	/**
	 * checks whether there is a need to generate reports for today
	 * 
	 * @param frequencyDays
	 * @param cal
	 * @return boolean
	 */
	public static boolean isJobDay(int[] frequencyDays, String jobFrequency, Calendar cal) {

		if (isNull(jobFrequency)) {
			return false;
		}

		if (jobFrequency.equalsIgnoreCase(ReportConstants.DAILY)) {
			return true;
		}

		return (jobFrequency.equalsIgnoreCase(ReportConstants.MONTHLY) && (CalendarHelper.getDayOfMonth(cal) == frequencyDays[0]))
				|| (jobFrequency.equalsIgnoreCase(ReportConstants.WEEKLY) && (CalendarHelper.getDayOfWeek(cal) == frequencyDays[0]))
				|| (jobFrequency.equalsIgnoreCase(ReportConstants.BI_MONTHLY) && ((CalendarHelper.getDayOfMonth(cal) == frequencyDays[0]) || (CalendarHelper
						.getDayOfMonth(cal) == frequencyDays[1])));
	}

	/**
	 * Helper method to get day(s) to generate report by report master id and
	 * frequency given
	 * @param reportMasterID
	 * @param jobFrequency - Daily report need to generate everyday, hence do
	 *        not need to query DB
	 * @return int[]
	 */
	private int[] getFrequencyDays(String reportMasterID, String jobFrequency) {
		int[] result = null;
		if (!jobFrequency.equalsIgnoreCase(ReportConstants.DAILY)) {
			logger.info("<<<<<<<<<< Call DAO to get frequency day for non-daily report");
			result = getReportSchedulerDao().getFrequencyDaysByID(reportMasterID);
		}
		return getFrequencyDays(result, jobFrequency);
	}

	/*
	 * Helper method to get default frequency days for non-daily reports
	 * 
	 * @param frequencyDays
	 * 
	 * @param jobFrequency
	 * 
	 * @return int[] the default day(s) to generate reports according to
	 * frequency given
	 */
	public static int[] getFrequencyDays(int[] frequencyDays, String jobFrequency) {
		if (frequencyDays == null) {
			if (jobFrequency.equalsIgnoreCase(ReportConstants.MONTHLY)) {
				frequencyDays = new int[] { ReportConstants.RPT_FREQ_MONTHLY };
			}
			else if (jobFrequency.equalsIgnoreCase(ReportConstants.WEEKLY)) {
				frequencyDays = new int[] { ReportConstants.RPT_FREQ_WEEKLY };
			}
			else if (jobFrequency.equalsIgnoreCase(ReportConstants.BI_MONTHLY)) {
				frequencyDays = new int[] { ReportConstants.RPT_FREQ_BIMONTHLY_1, ReportConstants.RPT_FREQ_BIMONTHLY_2 };
			}
		}
		return frequencyDays;
	}

	/**
	 * gets the report generation frequency
	 * @param report
	 * @return String
	 */
	private String getJobFrequency(OBReport report) {
		if (report.getFrequency() != null) {
			return report.getFrequency();
		}

		String result = getUserDefinedReportGenerationFrequency(report.getReportMasterId());
		logger.debug("UserDefinedFrequency = " + result);

		return (isNull(result)) ? report.getFrequency() : result;
	}

	/**
	 * retrieves the report generation frequency
	 * @param reportMasterId
	 * @return String
	 */
	private String getUserDefinedReportGenerationFrequency(String reportMasterId) {
		logger.debug("Entering method getUserDefinedReportGenerationFrequency");

		String paramCode = BizReportParameterMap.getReportFreqParamCode(reportMasterId);
		return getBizParamValue(paramCode);
	}

	/**
	 * retrieves the parameter value stored in bus param table
	 * @param paramCode
	 * @return string - parameter value
	 */
	private String getBizParamValue(String paramCode) {
		logger.debug("Entering method getBizParamValue");

		String returnStr = getReportSchedulerDao().getBizParamValue(paramCode);
		return returnStr;
	}

	/**
	 * Get report jobs by scope and/or country, and/or reportMasterID (Adhoc
	 * report)
	 * @param scope
	 * @param country
	 * @param reportMasterID
	 */
	private OBReport[] getReportJobs(String scope, String country, String reportMasterID) {
		if (reportMasterID != null) {
			logger.debug("<<<<<< Adhoc Report - Report master id: " + reportMasterID);

			OBReport rpt = new OBReport();
			rpt.setReportMasterId(reportMasterID);
			rpt.setFrequency(ReportConstants.DAILY);
			return new OBReport[] { rpt };
		}
		else {
			return getReportJobs(scope, country);
		}
	}

	/**
	 * Get the report jobs by scope and/or country
	 * @param scope
	 * @param country
	 */
	private OBReport[] getReportJobs(String scope, String country) {
		if (scope.equalsIgnoreCase(ReportConstants.GLOBAL_SCOPE)
				|| scope.equalsIgnoreCase(ReportConstants.REGION_SCOPE)
				|| scope.equalsIgnoreCase(ReportConstants.EXCHANGE_SCOPE)
				|| scope.equalsIgnoreCase(ReportConstants.COMMODITY_SCOPE)) {
			return getReportJobsByScope(scope);
		}
		else if (isValidForMIS(scope)) {
			logger.debug("Getting jobs by scope--mis reports");
			return getReportJobsByScope(scope);

			// getting Country reports scheduled to run
		}
		else if (!isNull(country) && isValidForCountry(scope)) {
			logger.debug("Getting jobs by scope - Country reports");
			return getReportJobsByScope(scope);
		}
		else {
			throw new InvalidReportParameterException("Parameter is not set correctly, report generation aborting",
					new String[] { "scope", "country" });
		}
	}

	/**
	 * get the report jobs to be run at country level
	 * @param scope
	 */
	private OBReport[] getReportJobsByScope(String scope) {
		OBReport[] aReportArray = getReportSchedulerDao().getReportJobsByScope(scope);

		return aReportArray;
	}

	/**
	 * get the report jobs to be run at scope level
	 * @param country
	 */
	private OBReport[] getReportJobsByCountry(String country, String scope) throws ReportException {
		OBReport[] aReportArray = getReportSchedulerDao().getReportJobsByCountry(country, scope);

		return aReportArray;
	}

	/**
	 * Helper method to get report scope
	 */
	private String getScope(String scope, String reportMasterID) throws ReportException {
		String strScope = scope;
		if ((scope == null) || scope.equals(ReportConstants.REPORT_CONFIG_BY_ID)) {
			strScope = getReportSchedulerDao().getScopeByID(reportMasterID);
		}
		return strScope;
	}

	/**
	 * Helper method to get parameter data
	 */
	private ParamData getParamData(String country, String centre, String strDate) {
		ParamData data = new ParamData();
		data.setReportDate(convertDateFromStr(strDate));
		data.setCountry(country);
		if (centre != null) {
			data.setCentre(getReportSchedulerDao().getFormattedBranchList(centre));
		}
		return data;
	}

	private Date convertDateFromStr(String strDate) {
		if (strDate == null) {
			return new Date();
		}
		return CalendarHelper.convertDate(strDate);
	}

	/**
	 * Setup report properties
	 */
	protected void setup() throws ReportException {
		logger.debug("---------------- Setting report generation properties -------------- ");

		commonSetup();

		setMaxReportThreadCount();

		logger.debug("--------------- Finish Setting report properties ---------- ");
	}

	/**
	 * Set max report thread count
	 */
	private void setMaxReportThreadCount() {
		String rptThreadCnt = PropertyManager.getValue(ReportConstants.RPT_THREAD_COUNT);
		try {
			MAX_RPT_THREAD_COUNT = Integer.parseInt(rptThreadCnt);
		}
		catch (NumberFormatException e) {
			MAX_RPT_THREAD_COUNT = 1;
			logger.error("Report thread count default to 1");
		}
	}

	/**
	 * Helper method to generate report name
	 */
	private String genReportName(String reportName, int formatType, String country, String frequency) {
		String fileExt = getFileExtensionByType(formatType);

		String str = frequency.charAt(0) + (country == null ? "" : country)
				+ (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + reportName + fileExt;
		return str;
	}

	/**
	 * Starts the report Scheduling process The reports to be generated depends
	 * on the input scope
	 * 
	 */
	private ReportContext makeReportScheduling(ReportContext ctx) throws ReportException {

		IInfoObjects oInfoObjects;

		int formatType = getReportFormatByType(ctx.getReportFormat());
		String reportName = getReportFileNameWithoutSuffix(ctx.getReportFileName());
		
		String genReportName = genReportName(reportName, formatType, 
				ctx.getParam().getCountry(), ctx.getReportConfig().getFrequency());

		ctx.setGenReportName(genReportName);
		
		//String reportInfo = null;
		try {
			if (ctx.getRetryCount() == 0) {
				if (oEnterpriseSession == null) {
					throw new ReportServerFailureException(
							"Crystal Report Enterprise Session Server has not been connected");
				}
	
				logger.debug("Connected to infoStore");
				// Query for the report object in the CMS. See the Developer
				// Reference guide for more information on the query language.
				oInfoObjects = (IInfoObjects) oInfoStore.query("SELECT TOP 1 * " + "FROM CI_INFOOBJECTS "
						+ "WHERE SI_PROGID = 'CrystalEnterprise.Report' AND SI_INSTANCE=0 AND SI_NAME='" + reportName
						+ "' AND SI_PARENTID = " + CMS_REPORT_FOLDER_ID);
	
				ctx.setOInfoObjects(oInfoObjects);
			} else {
				oInfoObjects = ctx.getOInfoObjects();
			}
			
			logger.debug("oInfoObjects = " + oInfoObjects);

			if (oInfoObjects.size() > 0) {
				String reportFileName = FOLDER_PATH + "\\" + genReportName;

				logger.debug("reportFileName = " + reportFileName);

				// Call local utility function to carry out the scheduling work.
				/*
				int retryCount = 0;
				while (((reportInfo == null) && (retryCount == 0))
						|| ((reportInfo != null) && reportInfo.equals(ReportConstants.RESCHEDULE_REQUIRED) 
								&& (retryCount <= MAX_RETRY_COUNT))) {
				*/
				if (ctx.getRetryCount() < MAX_RETRY_COUNT) {
					if (ctx.getRetryCount() > 0) {
						logger.debug("<<<< reschedule the report: " + ctx.getRetryCount());
					}

					ctx.setReportStartTime(DateUtil.getDate());
					scheduleReports(oInfoObjects, oInfoStore, reportFileName, formatType, ctx.getParam());

					ctx.increaseRetryCount();
					
//					reportInfo = checkReportStatus(oInfoObjects, genReportName);
//					retryCount++;
				}
				/*
				if ((reportInfo != null) && reportInfo.equals(ReportConstants.RESCHEDULE_REQUIRED)) {
					logger.debug("Failed to generate pending report after retry " + MAX_RETRY_COUNT + " times... ");
					ERR_LOG = "Exceed maximum retry count (" + MAX_RETRY_COUNT + ") for pending report generation";
					reportInfo = null;
				}
				*/
			}
			else {
				ctx.setErrLog(reportName + " is not found in Crystal Enterprise Server");
				logger.debug("<<<<<<<<<< this report " + reportName + " is not found at Crystal Enterprise Server... ");
			}

			//logger.debug("=============== End ========================");
		}
		catch (SDKException ex) {
			logger.warn("failed to query report object in crystal report server for report name [" + reportName
					+ "], continue to next report", ex);
		}
		catch (ReportServerFailureException ex) {
			logger.warn("failed to schedule report generation, for report name [" + reportName
					+ "], continue to next report", ex);
		}

		return ctx;
		//return reportInfo;
	}

	/**
	 * checks that the following are MIS scope
	 * @param scope
	 * @return boolean - true if valid
	 */
	public static boolean isValidForMIS(String scope) {
		return scope.equalsIgnoreCase(ReportConstants.MIS_CATEGORY)
				|| scope.equalsIgnoreCase(ReportConstants.SYSTEM_CATEGORY)
				|| scope.equalsIgnoreCase(ReportConstants.DOC_CATEGORY);
	}

	public static boolean isValidForCountry(String scope) {
		return scope.equalsIgnoreCase(ReportConstants.COUNTRY_REPORT);
	}

	private boolean isMISReportByCentreCode() {
		return ReportConstants.isMISReportByCentreCode();
	}

}