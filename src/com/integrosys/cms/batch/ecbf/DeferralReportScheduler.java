package com.integrosys.cms.batch.ecbf;

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

public class DeferralReportScheduler {

	public static final String SERVER_NAME = "app1";
	public static final String SCHEDULER_NAME = "ECBF_PENDING_DEFERRAL_REPORT";
	
	private ISchedulerDao schedulerDao = (ISchedulerDao) BeanHouse.get("schedulerDao");
	private IECommodityBasedFinancingBo ecbfBo = (IECommodityBasedFinancingBo) BeanHouse.get("ecbfBo");
	
	public static void main(String[] args) {
		new DeferralReportScheduler().execute();
	}
	 
	public void execute() {
		System.out.println("Inside DeferralReportScheduler.java line 33.");
		OBSchedulerLog log = logSchedulerStart();
		String outputDirectory = PropertyManager.getValue("ecbf.dpcftp.directory.path");
		
		DefaultLogger.info(this, "ECBF DPCFTP directory path : " + outputDirectory + ", Server Name: "
				+ PropertyManager.getValue("ecbf.deferral.report.server.name"));
		System.out.println("ECBF DPCFTP directory path : " + outputDirectory + ", Server Name: "
				+ PropertyManager.getValue("ecbf.deferral.report.server.name"));
		if (SERVER_NAME.equals(PropertyManager.getValue("ecbf.deferral.report.server.name"))) {
			boolean uploadSuccess = false;
			ecbfBo = (IECommodityBasedFinancingBo) BeanHouse.get("ecbfBo");
			List<OBEcbfDeferralReport> reportData = ecbfBo.getEcbfDeferralReportData();
			
			try {
				File reportFile = generateReport(reportData);
				uploadSuccess = uploadReport(reportFile.getName());
				System.out.println("DeferralReportScheduler.java=>reportFile.getName()=>"+reportFile.getName());
				log.setEndDate(new Date());
				log.setStatus(uploadSuccess ? BatchConstant.STATUS_SUCCESS : BatchConstant.STATUS_FAILED);
				log.setMessage("Report generation " + log.getStatus() + " for " + reportData.size() + " record(s)");
			} catch (DeferralReportBatchJobException e) {
				System.out.println("Exception DeferralReportScheduler.java=>Line no. 52=>e=>"+e);
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
		String filePath = PropertyManager.getValue("ecbf.dpcftp.directory.path");
		String fileName = "PendingDeferralReport_" + CommonUtil.getCurrentDateTime("ddMMyyyy") + ".csv";
		File file = new File(filePath + fileName);

		return file;
	}

	private static File generateReport(List<OBEcbfDeferralReport> reportDataList)
			throws DeferralReportBatchJobException {

		File reportCsv = createReportFile();
		PrintWriter pw = null;

		try {
			pw = new PrintWriter(reportCsv);
			pw.println(PropertyManager.getValue("ecbf.deferral.report.headers"));
			for (OBEcbfDeferralReport reportData : reportDataList) {
				pw.println(reportData);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Exception DeferralReportScheduler.java=>Line no. 86 FileNotFound=>e=>"+e);
			
			throw new DeferralReportBatchJobException(
					"Failed to find report file while writing report data at " + reportCsv.getPath());
		} finally {
			pw.close();
		}
		
		DefaultLogger.info(DeferralReportScheduler.class,
				"File " + reportCsv.getName() + " generated at local directory.");
		
		return reportCsv;
	}
	
	private static boolean uploadReport(String fileName) throws DeferralReportBatchJobException {

		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa", ICMSConstant.ECBF_DEFERRAL_REPORT_UPLOAD);
		String remoteAckDir = PropertyManager.getValue("ecbf.dpcftp.remote.directory.path");
		String localAckDir = PropertyManager.getValue("ecbf.dpcftp.directory.path");
		System.out.println("DeferralReportScheduler.java=>Open FTP connection for report file [" + fileName
				+ "] transfer from: " + localAckDir + " to " + remoteAckDir);
		DefaultLogger.info(DeferralReportScheduler.class, "Open FTP connection for report file [" + fileName
				+ "] transfer from: " + localAckDir + " to " + remoteAckDir);
		try {
			ftpClient.openConnection();
			ftpClient.uploadFile(localAckDir + fileName, remoteAckDir + fileName);
			System.out.println("DeferralReportScheduler.java=>After ftpClient.uploadFile(localAckDir + fileName, remoteAckDir + fileName)");
			return true;
		} catch (Exception e) {
			System.out.println("Exception at DeferralReportScheduler.java=>Line 109=>Failed to upload generated report file " + fileName + " from "
					+ localAckDir + " to " + remoteAckDir);
			DefaultLogger.error(DeferralReportScheduler.class, "Error uploading file: " + fileName, e);
			throw new DeferralReportBatchJobException("Failed to upload generated report file " + fileName + " from "
					+ localAckDir + " to " + remoteAckDir);
		} finally {
			ftpClient.closeConnection();
		}
	}
	
	private OBSchedulerLog logSchedulerStart(){
		OBSchedulerLog log = new OBSchedulerLog();
		log.setSchedulerCode(SCHEDULER_NAME);
		log.setSchedulerName("ECBF Pending Deferral Report");
		log.setStartDate(new Date());
		log.setStatus(BatchConstant.STATUS_RUNNING);
		return schedulerDao.createSchedulerLog(log);
	}

}
