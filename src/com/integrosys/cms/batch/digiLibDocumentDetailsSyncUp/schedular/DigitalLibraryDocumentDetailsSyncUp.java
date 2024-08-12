package com.integrosys.cms.batch.digiLibDocumentDetailsSyncUp.schedular;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.FileUploadLog.OBFileUploadLog;
import com.integrosys.cms.app.checklist.bus.CheckListDAOFactory;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.CustomerDAOFactory;
import com.integrosys.cms.app.customer.bus.ICustomerDAO;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadDao;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.poi.report.ReportService;

public class DigitalLibraryDocumentDetailsSyncUp
		implements
			IDigitalLibraryDocumentDetailsSyncUp {

	private final static Logger logger = LoggerFactory
			.getLogger(DigitalLibraryDocumentDetailsSyncUp.class);

	ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
	public static final String ACK_ACTION_MODIFY = "M";

	public static void main(String[] args) {
		new DigitalLibraryDocumentDetailsSyncUp().execute();
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String digiLibOnOffFlag = bundle.getString("digi.lib.onoff.flag");
		System.out.println("<<<<In execute() DigitalLibraryDocumentDetailsSyncUp  Starting....>>>>digiLibOnOffFlag=>"+ digiLibOnOffFlag+"****");
		if (digiLibOnOffFlag.equalsIgnoreCase(digiLibOnOffFlag)) {
			String digiLibServerName = bundle
					.getString("digi.lib.doc.server.name");
			System.out.println(
					"<<<<In execute() DigitalLibraryDocumentDetailsSyncUp  Starting....>>>>"
							+ digiLibServerName);
			if (null != digiLibServerName
					&& "app1".equalsIgnoreCase(digiLibServerName)) {
				try {
					System.out.println(
							"Starting DigitalLibraryDocumentDetailsSyncUp");

					File digLibDocDetailsFile = null;
					String serverFilePath = bundle
							.getString(DIGI_LIB_DOC_UPLOAD_LOCAL_DIR);
					String fileName = DIGI_LIB_DOC_FILENAME;

					
					 SimpleDateFormat dateFormat = new
					 SimpleDateFormat("yyyy/MM/dd"); 
					 Date date = new Date();
					// String sysdate = dateFormat.format(date);
					 
//					Date date = new Date();
					//Date sameday = new Date(date.getTime() * 24 * 3600 * 1000); 
																
					
					//System.out.println("date:: " + sysdate);
					System.out.println("date:: " + date);

					String fileDate = toDateFormat(date,
							DIGI_LIB_DOC_FILEDATEFORMAT);
					fileName = fileName + fileDate + DIGI_LIB_DOC_FILEEXTENSION;
					System.out.println("fileName:: " + fileName);

					digLibDocDetailsFile = new File(serverFilePath + fileName);
					System.out.println("Report Server and file name"
							+ serverFilePath + fileName);

					if (digLibDocDetailsFile.exists()) {
						digLibDocDetailsFile.delete();
					}

					File dirFile = new File(serverFilePath);
					if (!dirFile.exists())
						dirFile.mkdirs();

					ReportService rn = new ReportService();

					rn.generateReport("RPT0102", fileName, null);
					
					System.out.println("After Generate Report for RPT0102 =>serverFilePath=>"+serverFilePath+"***fileDate=>"+fileDate+"***fileName=>"+fileName);
					boolean stpCheck = true;
					// upload file to sftp
					try {
					uploadFileToSFTP(fileName, fileDate, serverFilePath);
					System.out
							.println("SFTP File Uploaded successfully....DigitalLibraryDocumentDetailsSyncUp...!!!!");
					}catch(Exception e) {
						stpCheck = false;
						System.out.println("Exception SFTP File Uploaded Failed....DigitalLibraryDocumentDetailsSyncUp...=>e=>"+e);
						e.printStackTrace();
					}
					// moveFile(digLibDocDetailsFile,fileName);
					// deleteOldFiles();
					ICustomerDAO custDAO = CustomerDAOFactory.getDAO();
					System.out.println("SFTP File Uploaded STATUS ....DigitalLibraryDocumentDetailsSyncUp...=>stpCheck=>"+stpCheck);
					IFileUploadDao fileUploadDao = (IFileUploadDao)BeanHouse.get("fileUploadDao");
					System.out.println("DigitalLibraryDocumentDetailsSyncUp=>Going to custDAO.getDocumentCount()");
//					String documentCode = CheckListDAOFactory.getCheckListDAO().getDocumentCount();
//					String documentCode = fileUploadDao.getDocumentCount();
					String documentCode = custDAO.getDocumentCount();
					//FileUploadDaoImpl fileUploadDao = new FileUploadDaoImpl();
					System.out.println("DigitalLibraryDocumentDetailsSyncUp=>After custDAO.getDocumentCount()=>documentCode=>"+documentCode);
//					String fileuploadid = fileUploadDao.getfileuploadidFromSeq();
					String fileuploadid = custDAO.getfileuploadidFromSeq();
					System.out.println("DigitalLibraryDocumentDetailsSyncUp=>fileuploadid=>"+fileuploadid);
					if(null == fileuploadid || "".equals(fileuploadid)) {
						fileuploadid = "0";
						System.out.println("DigitalLibraryDocumentDetailsSyncUp=>Inside if where fileuploadid is null=>fileuploadid=>"+fileuploadid);
					}
					long fileId = Long.parseLong(fileuploadid);  
					OBFileUploadLog obfileuploadlog = new OBFileUploadLog();
					obfileuploadlog.setFileUploadId(fileId);
					obfileuploadlog.setUploadedFileName(fileName);
					obfileuploadlog.setUploadDate(date);
					if(stpCheck == true) {
						obfileuploadlog.setFileUploadMessage("FILE UPLOADED SUCCESSFULLY");
						obfileuploadlog.setFileUploadStatus("SUCCESS");
					}else {
						obfileuploadlog.setFileUploadMessage("SFTP FILE UPLOAD FAILED");
						obfileuploadlog.setFileUploadStatus("FAILED");
					}
					obfileuploadlog.setNoOfRecords(documentCode);
					System.out.println("Going to Insert in CMS LOG TABLE =>CMS_FILE_UPLOAD_LOG table.. DigitalLibraryDocumentDetailsSyncUp..");
					fileUploadDao.insertFileUploadMessage(obfileuploadlog);
					System.out.println("After Insert in CMS LOG TABLE =>CMS_FILE_UPLOAD_LOG table.. DigitalLibraryDocumentDetailsSyncUp..");
				
				}

				catch (IOException e) {
					System.out.println("IOException in DigitalLibraryDocumentDetailsSyncUp DIGI_LIB_DOC in catch 119......e=>"+e);
					DefaultLogger.debug(this,"DigitalLibraryDocumentDetailsSyncUp in catch IOException......" + e.getMessage());
					e.printStackTrace();
				} catch (Exception e) {
					System.out.println("Exception in DigitalLibraryDocumentDetailsSyncUp DIGI_LIB_DOC in catch 123......e=>"+e);
					DefaultLogger.debug(this,"DigitalLibraryDocumentDetailsSyncUp DIGI_LIB_DOC in catch Exception......"+ e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	public static String toDateFormat(Date date, String formatter)
			throws Exception {
		try {
			if (date == null)
				return "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
			return dateFormat.format(date);
		} catch (Exception e) {
			DefaultLogger.debug("",
					"DIGI_LIB_DOC toDateFormat" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public void uploadFileToSFTP(String digLibDocDetailsFilename,
			String fileDate, String serverFilePath) throws Exception {

		System.out.println("Uploading generated file to SFTP location");
		logger.info("Uploading generated file to SFTP location");
		String connectionFor = "";
		connectionFor = ICMSConstant.DIGI_LIB_DOC_FILE_UPLOAD;
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		String remoteDataDir = resbundle
				.getString(DIGI_LIB_DOC_UPLOAD_REMOTE_DIR);

		System.out.println("remoteDataDir:: " + remoteDataDir);
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa",
				connectionFor);
		System.out.println("After CMSFtpClient.getInstance...");

		sftpClient.openConnection();
		System.out.println(
				"Uploading generated file to SFTP location and paths :localFilePath=>"
						+ serverFilePath + digLibDocDetailsFilename
						+ "  remoteDataDir=>" + remoteDataDir);
		logger.info("SFTP connection was opened for file transfer");

		sftpClient.uploadFileNew(serverFilePath + digLibDocDetailsFilename,
				remoteDataDir);

		sftpClient.closeConnection();

		System.out.println("Uploading generated file to SFTP location");
		logger.info("SFTP connection successfully closed after file transfer");
	}
}
