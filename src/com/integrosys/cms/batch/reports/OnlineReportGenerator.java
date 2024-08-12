/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/scb/cms/app/batch/reports/OnlineReportGenerator.java,v 1.9 2006/11/11 13:43:47 hshii Exp $
 **/

package com.integrosys.cms.batch.reports;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crystaldecisions.sdk.exception.SDKException;
import com.crystaldecisions.sdk.occa.infostore.IInfoObjects;
import com.crystaldecisions.sdk.occa.infostore.ISchedulingInfo;
import com.integrosys.base.techinfra.util.DateUtil;

/**
 * Description: This class receives online report generation request from the
 * user and starts the RAS generation process. Synchronous in nature.
 * 
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.9 $
 * @since $Date: 2006/11/11 13:43:47 $ Tag: $Name: DEV_20060126_B286V1 $
 */
public class OnlineReportGenerator extends CommonReportScheduler {
	private static final Logger logger = LoggerFactory.getLogger(OnlineReportGenerator.class);

	public String generateReport(String reportMasterID, Date reportDate, long limitProfileID,
			String exportFormat, String[] allowedCountries, String loginID) throws ReportException {

		if (limitProfileID <= 0) {
			throw new InvalidReportParameterException("LimitProfileID is invalid");
		}
		if ((reportMasterID == null) || (reportMasterID.trim().length() == 0)) {
			throw new InvalidReportParameterException(" ReportMasterID is invalid");
		}

		logger.info("@@@ reportMasterID: " + reportMasterID);

		OBReportConfig config = getReportConfig(reportMasterID);

		ParamData data = new ParamData(limitProfileID, reportDate, allowedCountries);
		ReportParameter param = ReportParameterFactory.createReportParameter(ReportConstants.MIS_CATEGORY, false, data,
				config);

		return scheduleOnlineReport(config, param, exportFormat, loginID);
	}

	public String generateReport(String reportMasterID, Date startExpDate, Date endExpDate,
			long teamTypeMembershipId, String[] allowedCountries, String exportFormat, long leID, String customerIndex,
			String loginID) throws ReportException {
		if (teamTypeMembershipId <= 0) {
			throw new InvalidReportParameterException("team_type_membership_id is invalid");
		}
		if ((reportMasterID == null) || (reportMasterID.trim().length() == 0)) {
			throw new InvalidReportParameterException(" ReportMasterID is invalid");
		}
		if ((allowedCountries == null) || (allowedCountries.length == 0)) {
			throw new InvalidReportParameterException(" allowedCountries is invalid");
		}
		if (exportFormat == null) {
			throw new InvalidReportParameterException(" Export Format is invalid");
		}

		logger.info("@@@ reportMasterID: " + reportMasterID);
		logger.info("@@@ team_type_membership_id: " + teamTypeMembershipId);
		logger.info("@@@ startExpDate: " + startExpDate);
		logger.info("@@@ endExpDate: " + endExpDate);
		logger.info("@@@ leId: " + Long.toString(leID));
		logger.info("@@@ customerIndex: " + customerIndex);

		OBReportConfig config = getReportConfig(reportMasterID);

		ParamData data = new ParamData(teamTypeMembershipId, startExpDate, endExpDate, allowedCountries, leID,
				customerIndex);

		ReportParameter param = ReportParameterFactory.createReportParameter(ReportConstants.DIARY_REPORT, false, data,
				config);

		return scheduleOnlineReport(config, param, exportFormat, loginID);
	}

	public String generateReport(String reportMasterID, Date reportDate, String country, String exportFormat,
			String loginID) throws ReportException {
		if ((reportMasterID == null) || (reportMasterID.trim().length() == 0)) {
			throw new InvalidReportParameterException(" ReportMasterID is invalid ");
		}
		if (reportDate == null) {
			throw new InvalidReportParameterException(" Report date is invalid");
		}
		if ((country == null) || (country.trim().length() == 0)) {
			throw new InvalidReportParameterException(" Country is invalid");
		}
		if (exportFormat == null) {
			throw new InvalidReportParameterException(" Export Format is invalid");
		}

		logger.info("@@@ reportMasterID: " + reportMasterID);
		logger.info("@@@ reportDate: " + reportDate);
		logger.info("@@@ country: " + country);

		OBReportConfig config = getReportConfig(reportMasterID);
		ParamData data = new ParamData(country, reportDate, null);
		ReportParameter param = ReportParameterFactory.createReportParameter(ReportConstants.MIS_CATEGORY, false, data,
				config);

		return scheduleOnlineReport(config, param, exportFormat, loginID);
	}

	public String generateReport(String reportNumber, Date reportDate, String country, long buildupID,
			String exportFormat, String loginID) throws ReportException {
		if ((reportNumber == null) || (reportNumber.trim().length() == 0)) {
			throw new InvalidReportParameterException(" Report Number is invalid ");
		}
		if (reportDate == null) {
			throw new InvalidReportParameterException(" Report date is invalid");
		}
		if ((country == null) || (country.trim().length() == 0)) {
			throw new InvalidReportParameterException(" Country is invalid");
		}
		if (exportFormat == null) {
			throw new InvalidReportParameterException(" Export Format is invalid");
		}
		logger.info("@@@ reportNumber: " + reportNumber);
		logger.info("@@@ reportDate: " + reportDate);
		logger.info("@@@ country: " + country);

		OBReportConfig config = getReportConfigByReportNumber(reportNumber);
		ParamData data = new ParamData(country, reportDate, buildupID);
		ReportParameter param = ReportParameterFactory.createReportParameter(ReportConstants.BL_DISCLAIMER_REPORT,
				false, data, config);

		return scheduleOnlineReport(config, param, exportFormat, loginID);
	}

	/**
	 * Helper method to generate online view report
	 */
	private String scheduleOnlineReport(OBReportConfig config, ReportParameter param, String exportFormat,
			String loginID) throws ReportException {
		try {
			commonSetup();

			String reportName = getReportFileNameWithoutSuffix(config.getReportFileName());

			logger.debug("reportName... " + reportName);

			// Query for the report object in the CMS. See the Developer
			// Reference guide for more information the query language.
			IInfoObjects oInfoObjects = (IInfoObjects) oInfoStore.query("SELECT TOP 1 * " + "FROM CI_INFOOBJECTS "
					+ "WHERE SI_PROGID = 'CrystalEnterprise.Report' " + "AND SI_INSTANCE=0 " + "AND SI_PARENTID = "
					+ CMS_REPORT_FOLDER_ID + " " + "AND SI_NAME='" + reportName + "'");

			int formatType = getReportFormatByType(exportFormat);
			reportName = genReportName(reportName, formatType, loginID);
			String generatedFileName = "TEMP_REPORT\\" + reportName;

			logger.debug("generatedFileName... " + generatedFileName);
			String reportInfo = null;

			if (oInfoObjects.size() > 0) {

				int retryCount = 0;
				while (((reportInfo == null) && (retryCount == 0))
						|| ((reportInfo != null) && reportInfo.equals(ReportConstants.RESCHEDULE_REQUIRED) 
								&& (retryCount <= MAX_RETRY_COUNT))) {

					if (retryCount > 0) {
						logger.debug("<<<< reschedule the report: " + retryCount);
					}

					Date startTime = DateUtil.getDate();
					scheduleReports(oInfoObjects, oInfoStore, generatedFileName, formatType, param);
					
					while (true) {
						reportInfo = checkReportStatus(oInfoObjects, startTime); //, reportName);
						if (ReportConstants.RPT_STATUS_COMPLETED.equals(reportInfo) ||
								ReportConstants.RESCHEDULE_REQUIRED.equals(reportInfo) ||
								String.valueOf(ISchedulingInfo.ScheduleStatus.FAILURE).equals(reportInfo)) {
							break;							
						} 
						try {
						Thread.sleep(SLEEP_TIME);
						}	catch (InterruptedException e) {
							logger.error("failed to interrupt current thread [" + Thread.currentThread().getName() + "]", e);
						}
					}
					//reportInfo = reportName;
					retryCount++;
				}

				if ((reportInfo != null) && reportInfo.equals(ReportConstants.RESCHEDULE_REQUIRED)) {
					logger.debug("Failed to generate pending report after retry " + MAX_RETRY_COUNT + " times... ");
					ERR_LOG = "Exceed maximum retry count (" + MAX_RETRY_COUNT + ") for pending report generation";
					reportInfo = null;
				}

			}
			else {
				logger.warn("<<<< report name: " + config.getReportFileName() + " is not found in the info store.");
			}
			// log out from the Enterprise server
			setEnterpriseServerLogout();

			return reportName;
		}
		catch (SDKException ex) {
			throw new ReportServerFailureException("failed to query crystal report server info store", ex);
		}
	}

	/**
	 * Helper method to generate report name
	 */
	private static String genReportName(String reportName, int formatType, String loginID) {
		String fileExt = getFileExtensionByType(formatType);

		String str = (loginID == null ? "" : loginID) + (new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date())
				+ reportName + fileExt;
		return str;
	}

	private static OBReportConfig getReportConfig(String reportMasterID) {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getReportConfig(reportMasterID, null);
	}

	private static OBReportConfig getReportConfigByReportNumber(String reportNumber) {
		ReportSchedulerDAO dao = new ReportSchedulerDAO();
		return dao.getReportConfigByReportNumber(reportNumber, null);
	}
}
