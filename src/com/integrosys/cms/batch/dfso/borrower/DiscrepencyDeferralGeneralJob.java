package com.integrosys.cms.batch.dfso.borrower;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

import au.com.bytecode.opencsv.CSVWriter;

public class DiscrepencyDeferralGeneralJob implements IBorrower {

	private final static Logger logger = LoggerFactory.getLogger(DiscrepencyDeferralGeneralJob.class);

	public static void main(String[] args) {

		new DiscrepencyDeferralGeneralJob().execute();
	}

	public DiscrepencyDeferralGeneralJob() {
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String dfsoServerName = bundle.getString("dfso.borrower.server.name");
		String dfsoChanges= bundle.getString("dfso.changes");
		System.out.println("<<<<In execute() DiscrepencyDeferralGeneralJob Strating....>>>>" + dfsoServerName+" "+dfsoChanges);

		if (null != dfsoChanges && "Y".equalsIgnoreCase(dfsoChanges) 
				&& null != dfsoServerName && "app1".equalsIgnoreCase(dfsoServerName)) {
			try {
				System.out.println("Starting DiscrepencyDeferralGeneralJob");
		    	ILimitDAO dao = LimitDAOFactory.getDAO();
			//	IGeneralParamDao dao1 = (IGeneralParamDao) BeanHouse.get("generalParamDao");
		    	
		    	File dfsoFile = null;
				CSVWriter out = null;
				FileWriter fw = null;
				
				String serverFilePath = bundle.getString(FTP_DFSO_UPLOAD_LOCAL_DIR);
				String fileName = DFSO_DiscrepencyDeferralGeneral_FILENAME;
				Date date = new Date();
				Date dateBefore = new Date(date.getTime() - 1 * 24 * 3600 * 1000  ); //Subtract n days
				System.out.println("dateBefore:: "+dateBefore);
				String fileDate = toDateFormat(dateBefore, DFSO_DATEFORMAT);

				fileName = fileName + DFSO_FILEEXTENSION;
				System.out.println("fileName:: "+fileName);

				dfsoFile = new File(serverFilePath + fileName);
				System.out.println("dfso DiscrepencyDeferralGeneral" + serverFilePath + fileName);

				if(dfsoFile.exists()) {
					dfsoFile.delete();
				}
				File dirFile = new File(serverFilePath);
				if (!dirFile.exists())
					dirFile.mkdirs();

				boolean createNewFile = dfsoFile.createNewFile();
				if (createNewFile == false)
					System.out.println("Error while creating new file:" + dfsoFile.getPath());

				fw = new FileWriter(dfsoFile.getAbsoluteFile(), true);
				out = new CSVWriter(fw,'|',CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER,CSVWriter.DEFAULT_LINE_END);
				

				String header [] = new String[14];
		    	header[0]="DISCREPANCYID";
				header[1]="SEGMENTNAME";
				header[2]="RMNAME";
				header[3]="DISCREPENCY_TYPE";
				header[4]="REGION";
				header[5]="RBI_ASSET";
				
				header[6]="PARTYNAME";
				header[7]="PARTYID";
				header[8]="DISCREPANCYDESC";
				header[9]="STATUS";
				header[10]="ORIGINALDUEDATE";
				header[11]="CREATIONDATE";
				
				header[12]="APPROVAL_NAME";
				header[13]="CREATIONREMARK";
				
			
				out.writeNext(header);
			
				List discrepencyGeneralList = dao.getDiscrepencyDeferralGeneral();
				if (discrepencyGeneralList != null) {
					for (int i = 0; i < discrepencyGeneralList.size(); i++) {
						String[] arr = (String[]) (discrepencyGeneralList.get(i));
				//	System.out.println( " PARTI ID ::"+  arr[0]+ "  PARTY NAME :: "+arr[1]);
					out.writeNext(arr);
					}
				
					out.close();
					fw.close();
					System.out.println("File Created successfully..............!!!!");
				
						// upload file to sftp
						uploadFileToSFTP(fileName, fileDate,serverFilePath);
						System.out.println("SFTP File Uploaded successfully....!!!!");
						moveFile(dfsoFile,fileName);
						deleteOldFiles();

				
			}

			} catch (IOException e) {
				DefaultLogger.debug(this, "DiscrepencyDeferralGeneral in catch IOException......" + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				DefaultLogger.debug(this, "DiscrepencyDeferralGeneral in catch Exception......" + e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public void uploadFileToSFTP(String dfsoFileName, String fileDate ,String serverFilePath) throws Exception {

		System.out.println("Uploading generated file to SFTP location");
		logger.info("Uploading generated file to SFTP location");
		String connectionFor = "";
			connectionFor = ICMSConstant.DFSO_UPLOAD;
			ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
			String remoteDataDir = resbundle.getString(FTP_DFSO_UPLOAD_REMOTE_DIR);

			System.out.println("remoteDataDir:: "+remoteDataDir);
			CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa", connectionFor);
			System.out.println("After CMSFtpClient.getInstance...");
	
			sftpClient.openConnection();
			System.out.println("Uploading generated file to SFTP location and paths :localFilePath=>"+serverFilePath+dfsoFileName+"  remoteDataDir=>"+remoteDataDir);
			logger.info("SFTP connection was opened for file transfer");

			sftpClient.uploadFileNew(serverFilePath+dfsoFileName, remoteDataDir);
	
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
			DefaultLogger.debug("", "DiscrepencyDeferralGeneral toDateFormat" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	private void deleteOldFiles() {
		System.out.println("<<<<In execute() of DiscrepencyDeferralGeneral deleteOldFiles()");
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String serverFilePath = bundle.getString("ftp.dfso.borrower.backup.local.dir");
			String days = bundle.getString("ftp.dfso.borrower.noofDays");
			long noofDays = 1;
			if (null != days && !"".equalsIgnoreCase(days)) {

				noofDays = Long.parseLong(days);
				System.out.println("noofDays:: "+noofDays);
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
		System.out.println("<<<<In execute() of DiscrepencyDeferralGeneral moveFile()");
		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			ResourceBundle bundle = ResourceBundle.getBundle("ofa");
			String destPath = bundle.getString("ftp.dfso.borrower.backup.local.dir");
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
					System.out.println("DiscrepencyDeferralGeneral file  deletion failed for file:" + sourceFile.getPath());
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}


}
