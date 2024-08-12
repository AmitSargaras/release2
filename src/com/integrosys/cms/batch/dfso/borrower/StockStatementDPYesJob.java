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

public class StockStatementDPYesJob implements IBorrower {

	private final static Logger logger = LoggerFactory.getLogger(StockStatementDPYesJob.class);

	public static void main(String[] args) {

		new StockStatementDPYesJob().execute();
	}

	public StockStatementDPYesJob() {
	}

	public void execute() {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String dfsoServerName = bundle.getString("dfso.borrower.server.name");
		String dfsoChanges= bundle.getString("dfso.changes");
		System.out.println("<<<<In execute() DiscrepencyDeferralGeneralJob Strating....>>>>" + dfsoServerName+" "+dfsoChanges);

		if (null != dfsoChanges && "Y".equalsIgnoreCase(dfsoChanges) 
				&& null != dfsoServerName && "app1".equalsIgnoreCase(dfsoServerName)) {
			try {
				System.out.println("Starting StockStatementDPYesJob");
		    	ILimitDAO dao = LimitDAOFactory.getDAO();
			//	IGeneralParamDao dao1 = (IGeneralParamDao) BeanHouse.get("generalParamDao");
		    	
		    	File dfsoFile = null;
				CSVWriter out = null;
				FileWriter fw = null;
				
				String serverFilePath = bundle.getString(FTP_DFSO_UPLOAD_LOCAL_DIR);
				String fileName = DFSO_StockStatementDPYes_FILENAME;
				Date date = new Date();
				Date dateBefore = new Date(date.getTime() - 1 * 24 * 3600 * 1000  ); //Subtract n days
				System.out.println("dateBefore:: "+dateBefore);
				String fileDate = toDateFormat(dateBefore, DFSO_DATEFORMAT);

				fileName = fileName + DFSO_FILEEXTENSION;
				System.out.println("fileName:: "+fileName);

				dfsoFile = new File(serverFilePath + fileName);
				System.out.println("dfso StockStatementDPYes......" + serverFilePath + fileName);

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
				

				String header [] = new String[22];
		    	header[0]="PARTYID";
				header[1]="PARTYNAME";
				header[2]="SEGMENTNAME";
				header[3]="RMREGION";
				header[4]="RMNAME";
				header[5]="DUEDATE";
				
				header[6]="RECEIVEDATE";
				header[7]="PERIODDESC";
				header[8]="COMPONENT";
				header[9]="HEADER";
				header[10]="FLAG";
				header[11]="COLLATERALSHARE";
				
				header[12]="GROSSVALUE";
				header[13]="MARGIN";
				header[14]="NETAMOUNT";
				header[15]="DP";
				header[16]="DP_CALCULATE_MANUALLY";
				header[17]="COMPONENT_CATEGORY";
				
				header[18]="MAKER";
				header[19]="MAKERDATETIME";
				header[20]="APPROVED_BY";
				header[21]="CHECKERDATETIME";
			
				out.writeNext(header);
			
				List StockStatementDPYes = dao.getStockStatementDPYes();
				if (StockStatementDPYes != null) {
					for (int i = 0; i < StockStatementDPYes.size(); i++) {
						String[] arr = (String[]) (StockStatementDPYes.get(i));
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
				DefaultLogger.debug(this, "StockStatementDPYes in catch IOException......" + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				DefaultLogger.debug(this, "StockStatementDPYes in catch Exception......" + e.getMessage());
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
			DefaultLogger.debug("", "StockStatementDPYes toDateFormat" + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	private void deleteOldFiles() {
		System.out.println("<<<<In execute() of StockStatementDPYes deleteOldFiles()");
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
		System.out.println("<<<<In execute() of StockStatementDPYes moveFile()");
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
					System.out.println("StockStatementDPYes file  deletion failed for file:" + sourceFile.getPath());
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

	}


}
