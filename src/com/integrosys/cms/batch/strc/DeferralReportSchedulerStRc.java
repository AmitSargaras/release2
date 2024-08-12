package com.integrosys.cms.batch.strc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.batch.DeferralReportBatchJobException;
import com.integrosys.cms.batch.ISchedulerDao;
import com.integrosys.cms.batch.OBSchedulerLog;
import com.integrosys.cms.batch.common.BatchConstant;

public class DeferralReportSchedulerStRc {

	public static final String SERVER_NAME = PropertyManager.getValue("strc.deferral.report.server.name");
	public static final String SCHEDULER_NAME = PropertyManager.getValue("strc.deferral.report.schedular");
	
	private ISchedulerDao schedulerDao = (ISchedulerDao) BeanHouse.get("schedulerDao");
	private IECommodityBasedFinancingBo strcBo = (IECommodityBasedFinancingBo) BeanHouse.get("strcBo");
	
	public static void main(String[] args) {
		new DeferralReportSchedulerStRc().execute();
	}
	 
	public void execute() {
		OBSchedulerLog log = logSchedulerStart();
		String outputDirectory = PropertyManager.getValue("strc.dpcftp.directory.path");
		
		DefaultLogger.info(this, "STRC DPCFTP directory path : " + outputDirectory + ", Server Name: "
				+ PropertyManager.getValue("strc.deferral.report.server.name"));

		if (SERVER_NAME.equals(PropertyManager.getValue("strc.deferral.report.server.name"))) {
			boolean uploadSuccess = false;
			strcBo = (IECommodityBasedFinancingBo) BeanHouse.get("strcBo");
			List<OBStrcDeferralReport> reportData = strcBo.getEcbfDeferralReportData();
			
			try {
				File reportFile = generateReport(reportData);
				uploadSuccess = uploadReport(reportFile.getName());
				log.setEndDate(new Date());
				log.setStatus(uploadSuccess ? BatchConstant.STATUS_SUCCESS : BatchConstant.STATUS_FAILED);
				log.setMessage("Report generation " + log.getStatus() + " for " + reportData.size() + " record(s)");
			} catch (DeferralReportBatchJobException e) {
				DefaultLogger.error(this, e.getMessage(), e);
				log.setEndDate(new Date());
				log.setStatus(BatchConstant.STATUS_FAILED);
				log.setMessage(e.getMessage());
			} finally {
				schedulerDao.updateSchedulerLog(log);
				DefaultLogger.info(this,
						"Report generation " + log.getStatus() + " for " + reportData.size() + " record(s) ");
			}
		}	
	}
	
	private static File createReportFile() {
		String filePath = PropertyManager.getValue("strc.dpcftp.directory.path");
		String fileName = "StockReceivablesAudiTrailReport_" + CommonUtil.getCurrentDateTime("ddMMyyyy") + ".csv";
		File file = new File(filePath + fileName);

		return file;
	}

	private static File generateReport(List<OBStrcDeferralReport> reportDataList)
			throws DeferralReportBatchJobException {

		File reportCsv = createReportFile();
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(reportCsv);
			pw.println(PropertyManager.getValue("strc.deferral.report.headers"));
			for (OBStrcDeferralReport reportData : reportDataList) {
				pw.println(reportData);
			}
		} catch (FileNotFoundException e) {
			throw new DeferralReportBatchJobException(
					"Failed to find report file while writing report data at " + reportCsv.getPath());
		} finally {
			pw.close();
		}
		
		DefaultLogger.info(DeferralReportSchedulerStRc.class,
				"File " + reportCsv.getName() + " generated at local directory.");
		
		return reportCsv;
	}
	
	private static boolean uploadReport(String fileName) throws DeferralReportBatchJobException {

		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa", ICMSConstant.ECBF_DEFERRAL_REPORT_UPLOAD);
		String remoteAckDir = PropertyManager.getValue("strc.dpcftp.remote.directory.path");
		String localAckDir = PropertyManager.getValue("strc.dpcftp.directory.path");
		
		DefaultLogger.info(DeferralReportSchedulerStRc.class, "Open FTP connection for report file [" + fileName
				+ "] transfer from: " + localAckDir + " to " + remoteAckDir);
		try {
			ftpClient.openConnection();
			ftpClient.uploadFile(localAckDir + fileName, remoteAckDir + fileName);
			return true;
		} catch (Exception e) {
			DefaultLogger.error(DeferralReportSchedulerStRc.class, "Error uploading file: " + fileName, e);
			throw new DeferralReportBatchJobException("Failed to upload generated report file " + fileName + " from "
					+ localAckDir + " to " + remoteAckDir);
		} finally {
			ftpClient.closeConnection();
		}
	}
	
	private OBSchedulerLog logSchedulerStart(){
		OBSchedulerLog log = new OBSchedulerLog();
		log.setSchedulerCode(SCHEDULER_NAME);
		log.setSchedulerName("strc Pending Deferral Report");
		log.setStartDate(new Date());
		log.setStatus(BatchConstant.STATUS_RUNNING);
		return schedulerDao.createSchedulerLog(log);
	}

}
