package com.integrosys.cms.batch.npaDailyStamping.schedular;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.struts.util.LabelValueBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

public class NpaDailyStagingFileUploadJob implements INpaDailyStampingConstant {

	private final static Logger logger = LoggerFactory.getLogger(NpaDailyStagingFileUploadJob.class);

	public static void main(String[] args) {

		new NpaDailyStagingFileUploadJob().execute();
	}

	public NpaDailyStagingFileUploadJob() {
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String npaServerName = bundle.getString("npa.dailyStamping.server.name");
		System.out.println("<<<<In execute() NpaDailyStagingFileUploadJob Strating....>>>>" + npaServerName);

		if (null != npaServerName && "app1".equalsIgnoreCase(npaServerName)) {
			try {
				System.out.println("Starting NpaDailyStagingFileUploadJob");
				ILimitDAO dao = LimitDAOFactory.getDAO();
			
				
				
				List tempList = dao.getNpaDailyStampingCount();
				if (tempList != null) {
					for (int i = 0; i < tempList.size(); i++) {
						String[] arr = (String[]) (tempList.get(i));
					System.out.println( " SYSTEM ::"+  arr[0]+ "  COUNT :: "+arr[1]);
	
					File npaFile = null;
					BufferedWriter out = null;
					FileWriter fw = null;
					String serverFilePath = bundle.getString(FTP_NPA_UPLOAD_LOCAL_DIR);
					String fileName = bundle.getString(NPA_FILENAME);
					Date date = new Date();
					Date dateBefore = new Date(date.getTime() - 1 * 24 * 3600 * 1000  ); //Subtract n days
					System.out.println("dateBefore:: "+dateBefore);
					
					String fileDate = toDateFormat(dateBefore, NPA_FILEDATEFORMAT);
					fileName = fileName + fileDate + NPA_FILEEXTENSION;
					npaFile = new File(serverFilePath + fileName);
					System.out.println("NPA Daily Stamping FileName......" + serverFilePath + fileName);

					if(npaFile.exists()) {
						npaFile.delete();
					}
					File dirFile = new File(serverFilePath);
					if (!dirFile.exists())
						dirFile.mkdirs();

					boolean createNewFile = npaFile.createNewFile();
					if (createNewFile == false)
						System.out.println("Error while creating new file:" + npaFile.getPath());

					fw = new FileWriter(npaFile.getAbsoluteFile(), true);
					out = new BufferedWriter(fw);
					
						Date date1 = new Date();
						String reportingDate = toDateFormat(date1, NPA_DATEFORMAT);
						String fileHeader = bundle.getString(NPA_FILE_HEADER);

						out.write(fileHeader);
						out.newLine();
						out.write(reportingDate+ "~");
						out.write(arr[0] + "~");
						out.write(arr[1]);
						out.close();
					
						fw.close();
						System.out.println("Npa File Created successfully..............!!!!");
				
						// upload file to sftp
							uploadFileToSFTP(fileName, fileDate,serverFilePath);
						System.out.println("SFTP NPA File Uploaded successfully....!!!!");

						moveFile(npaFile,fileName);
						deleteOldFiles();

				}
			}

			} catch (IOException e) {
				DefaultLogger.debug(this, "NpaDailyStamping in catch IOException......" + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				DefaultLogger.debug(this, "NpaDailyStamping in catch Exception......" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void uploadFileToSFTP(String npaFileName, String fileDate ,String serverFilePath) throws Exception {

		System.out.println("Uploading generated file to SFTP location");
		logger.info("Uploading generated file to SFTP location");
		String connectionFor = "";
			connectionFor = ICMSConstant.NPA_DAILY_STAMPING_FILE_UPLOAD;
			ResourceBundle resbundle = ResourceBundle.getBundle("ofa");

			String remoteDataDir = resbundle.getString(FTP_NPA_UPLOAD_REMOTE_DIR);

			System.out.println("remoteDataDir:: "+remoteDataDir);
			CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa", connectionFor);
			System.out.println("After CMSFtpClient.getInstance...");
	
			sftpClient.openConnection();
			System.out.println("Uploading generated file to SFTP location and paths :localFilePath=>"+serverFilePath+npaFileName+"  remoteDataDir=>"+remoteDataDir);
			logger.info("SFTP connection was opened for file transfer");


			sftpClient.uploadFileNew(serverFilePath+npaFileName, remoteDataDir);

			sftpClient.closeConnection();
			
			System.out.println("Uploading generated file to SFTP location");
			logger.info("SFTP connection successfully closed after file transfer");
	}

	public static String toDateFormat(Date date, String formatter) throws Exception {
		try {
			if (date == null)
				return "";
			SimpleDateFormat dateFormat = new SimpleDateFormat(formatter);
			return dateFormat.format(date);
		} catch (Exception e) {
			DefaultLogger.debug("", "NpaDailyStamping toDateFormat" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	private void deleteOldFiles() {
		System.out.println("<<<<In execute() of NpaDailyStamping deleteOldFiles()");
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String serverFilePath = bundle.getString("ftp.npa.dailyStamping.backup.local.dir");
			String days = bundle.getString("ftp.npa.dailyStamping.noofDays");
			long noofDays = 1;
			if (null != days && !"".equalsIgnoreCase(days)) {

				noofDays = Long.parseLong(days);
			}
			File folder = new File(serverFilePath);

			if (folder.exists()) {

				File[] listFiles = folder.listFiles();

				long eligibleForDeletion = System.currentTimeMillis() - (noofDays * 24 * 60 * 60 * 1000);

				for (File listFile : listFiles) {

					if (listFile.lastModified() < eligibleForDeletion) {

						boolean delete = listFile.delete();
						if (delete == false) {
							System.out.println("file  deletion failed for file:" + listFile.getPath());
						}
					}
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	public void moveFile(File sourceFile, String fileName) {
		System.out.println("<<<<In execute() of NpaDailyStamping moveFile()");
		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String destPath = bundle.getString("ftp.npa.dailyStamping.backup.local.dir");
			File destFolderPath = new File(destPath);
			if (!destFolderPath.exists()) {
				destFolderPath.mkdirs();
			}

			outstream = new FileOutputStream(destPath + fileName);
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inputStream.read(buffer)) > 0) {

				outstream.write(buffer, 0, length);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				boolean delete = sourceFile.delete();
				if (delete == false) {
					System.out.println("file  deletion failed for file:" + sourceFile.getPath());
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}

}
