package com.integrosys.cms.batch.reportLeadNodalUpload.schedular;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileUpload.bus.OBAutoupdationLmtsFile;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.ftp.IFileUploadConstants;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.poi.report.ReportService;
import com.integrosys.cms.app.poi.report.writer.ExcelWriter;
import com.integrosys.cms.app.poi.report.writer.FileFormat;
import com.integrosys.cms.batch.ewsStockDeferral.schedular.ewsStockDeferralFileUploadJob;


public class ReportLeadNodalUploadJob implements IReportLeadNodalUploadConstant {
	
	private final static Logger logger = LoggerFactory.getLogger(ReportLeadNodalUploadJob.class);
	
	public static void main(String[] args) {

		new ReportLeadNodalUploadJob().execute();
	}

	public ReportLeadNodalUploadJob() {
	}
	
	
	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String reportLNServer = bundle.getString("report.leadnodal.server.name");
		System.out.println("<<<<In execute() ReportLeadNodalUploadJob  Strating....>>>>" + reportLNServer);

		if (null != reportLNServer && "app1".equalsIgnoreCase(reportLNServer)) {
			try {
				System.out.println("Starting ReportLeadNodalUploadJob");
				
		    	File reportLNFile = null;				
				String serverFilePath = bundle.getString(REPORT_LN_UPLOAD_LOCAL_DIR);
				String fileName = REPORT_LN_FILENAME;
				Date date = new Date();
				Date dateBefore = new Date(date.getTime() - 1 * 24 * 3600 * 1000  ); //Subtract n days
				System.out.println("dateBefore:: "+dateBefore);
				String fileDate = toDateFormat(dateBefore, REPORT_LN_FILEDATEFORMAT);

				fileName = fileName + fileDate + REPORT_LN_FILEEXTENSION;
				System.out.println("fileName:: "+fileName);

				reportLNFile = new File(serverFilePath + fileName);
				System.out.println("Report Server and file name" + serverFilePath + fileName);

				if(reportLNFile.exists()) {
					reportLNFile.delete();
				}
				
				File dirFile = new File(serverFilePath);
				if (!dirFile.exists())
					dirFile.mkdirs();
				
				ReportService rn = new ReportService();
				
				rn.generateReport("RPT0094", fileName, null);
					
			    System.out.println("After Generate Report for RPT0094 ");
				
					// upload file to sftp
					uploadFileToSFTP(fileName, fileDate,serverFilePath);
					System.out.println("SFTP File Uploaded successfully....!!!!");
					//moveFile(reportLNFile,fileName);
					//deleteOldFiles();

				}
		
			 catch (IOException e) {
				DefaultLogger.debug(this, "Report Lead/Nodal in catch IOException......" + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				DefaultLogger.debug(this, "Report Lead/Nodal in catch Exception......" + e.getMessage());
				e.printStackTrace();
			}
		}
	}
		


public static String toDateFormat(Date date, String formatter) throws Exception {
	try {
		if (date == null)
			return "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
		return dateFormat.format(date);
	} catch (Exception e) {
		DefaultLogger.debug("", "Report Lead/Nodal toDateFormat" + e.getMessage());
		e.printStackTrace();
		throw e;
	}
}

public void uploadFileToSFTP(String reportLNFilename, String fileDate ,String serverFilePath) throws Exception {

	System.out.println("Uploading generated file to SFTP location");
	logger.info("Uploading generated file to SFTP location");
	String connectionFor = "";
		connectionFor = ICMSConstant.REPORT_LN_FILE_UPLOAD;
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		String remoteDataDir = resbundle.getString(REPORT_LN_UPLOAD_REMOTE_DIR);

		System.out.println("remoteDataDir:: "+remoteDataDir);
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa", connectionFor);
		System.out.println("After CMSFtpClient.getInstance...");

		sftpClient.openConnection();
		System.out.println("Uploading generated file to SFTP location and paths :localFilePath=>"+serverFilePath+reportLNFilename+"  remoteDataDir=>"+remoteDataDir);
		logger.info("SFTP connection was opened for file transfer");

		sftpClient.uploadFileNew(serverFilePath+reportLNFilename, remoteDataDir);

		sftpClient.closeConnection();
		
		System.out.println("Uploading generated file to SFTP location");
		logger.info("SFTP connection successfully closed after file transfer");
}
	
	

}
