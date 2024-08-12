package com.integrosys.cms.batch.psrLimitFile.schedular;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

public class PSRLimitFileDownloadJob implements PSRFileConstants {

	ResourceBundle resbundle = ResourceBundle.getBundle("ofa");

	public PSRLimitFileDownloadJob() {

	}

	public static void main(String[] args) {
		new PSRLimitFileDownloadJob().execute();
	}

	/**
	 * This job is run and executed by quartz schedular. For more details refer to
	 * schedular configuration in
	 * config\spring\batch\psrLimitFile\AppContext_Master.xml Schedular has been
	 * designed to carry out the following activities 1. Download ACK/Fail file from
	 * sftp server 2. Read the file and update the status against each line detail
	 * 3. Update the log table. 4. move the file.
	 */

	public void execute() {

		try {
			System.out.println("Starting PSRLimitFileDownloadJob......" + Calendar.getInstance().getTime());
			ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
			String psrServerName = bundle1.getString("psr.server.name");

			if (null != psrServerName && psrServerName.equalsIgnoreCase("app1")) {
				ILimitDAO limitDao = LimitDAOFactory.getDAO();
				ArrayList<String[]> fileList = limitDao.getPSRFileNames();

				if (null != fileList && fileList.size() > 0) {
					boolean fileDownload = downloadFileFromSFTP(fileList);
					System.out.println("Starting PSRLimitFileDownloadJob after downloadFileFromSFTP......" + fileDownload);
					if (fileDownload) {
						String localDataDir = resbundle.getString(FTP_PSR_DOWNLOAD_LOCAL_DIR);
						File ackFiles = new File(localDataDir.trim());
						File[] files = ackFiles.listFiles();
						System.out.println("Starting PSRLimitFileDownloadJob list files......" + files.length);
						if (null != files) {
							for (File file : files) {
								try {
									System.out.println("Starting PSRLimitFileDownloadJob file getName......" + file.getName());
									if (null != file && file.getName().endsWith(PSR_FILESUCCESSNAME))
										readingFileSuccess(localDataDir + file.getName());
									else if (null != file && file.getName().endsWith(PSR_FILEERRORNAME))
										readingFileFail(localDataDir + file.getName());
								} catch (Exception e) {
									System.out.println("PSRLimitFileDownloadJob in catch Exception file name "+ file.getName() + "..." + e.getMessage());
									e.printStackTrace();
								}
								finally {
									try {
										System.out.println("PSRLimitFileDownloadJob in move file name " + file.getName());
										moveFile(file, file.getName());
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			System.out.println("Ending PSRLimitFileDownloadJob......" + Calendar.getInstance().getTime());
		} catch (Exception e) {
			DefaultLogger.debug(this, "PSRLimitFileDownloadJob in catch Exception......" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean downloadFileFromSFTP(ArrayList<String[]> fileList) throws Exception {
		
		DefaultLogger.debug(this,"PSRLimitFileDownloadJob downloadFileFromSFTP() ");
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.PSRLIMIT_FILE_UPLOAD);
		String remoteDataDir = resbundle.getString(FTP_PSR_DOWNLOAD_REMOTE_DIR);
		String localDataDir = resbundle.getString(FTP_PSR_DOWNLOAD_LOCAL_DIR);
		File dirFile = new File(localDataDir);
		if(!dirFile.exists())
			dirFile.mkdirs();
		DefaultLogger.debug(this,"PSRLimitFileDownloadJob downloadFileFromSFTP() open Connection");
		sftpClient.openConnection();
		DefaultLogger.debug(this, "PSRLimitFileDownloadJob downloadFileFromSFTP():SFTP connection opened() ");
		boolean fileDownload = sftpClient.downloadPSRFile(remoteDataDir,localDataDir,fileList);
		sftpClient.closeConnection();
		DefaultLogger.debug(this, "PSRLimitFileDownloadJob downloadFileFromSFTP():SFTP connection closed() ");
		return fileDownload;

	}
	
	public void moveFile(File sourceFile, String fileName) throws IOException {

		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			String destPath = resbundle.getString(FTP_PSR_BACKUP_LOCAL_DIR);
			DefaultLogger.debug(this, "PSRLimitFileDownloadJob in move file destPath " + destPath);
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
			DefaultLogger.debug(this, "PSRLimitFileDownloadJob in move file exception " + e.getMessage());
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				sourceFile.delete();
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}
	
	private void readingFileSuccess(String filePath) throws DBConnectionException, SQLException, Exception {
		BufferedReader bufferReader = null;
		FileReader fileReader = null;
		String[] spoolArray;
		try {
			System.out.println("In PSRLimitFileDownloadJob reading success file......"+Calendar.getInstance().getTime());
			fileReader = new FileReader(filePath);
			bufferReader = new BufferedReader(fileReader);
			String sCurrentLine;
			String spoolDate="";
			String sourceRef="";
			String action ="";
			Map<String, String> map = new HashMap<String, String>();
			int counter =0;
			while ((sCurrentLine = bufferReader.readLine()) != null) {
				counter++;
				action = "";
				if(sCurrentLine.contains(PSR_FILESPOOLDATE)) {
					System.out.println("In PSRLimitFileDownloadJob reading success file...1");
					spoolArray = sCurrentLine.split(PSR_FILESPOOLDATEHEADING);
					System.out.println("In PSRLimitFileDownloadJob reading success file...2");
					spoolDate = spoolArray[1].trim();
					System.out.println("Starting PSRLimitFileDownloadJob spool Date......"+spoolDate);
				}
				if(counter >= 7) {
					if(sCurrentLine.length() ==0)
						break;
					if(sourceRef.equals(""))
						sourceRef = "'"+sCurrentLine.substring(35, 50).trim()+"'";
					else
						sourceRef = sourceRef+ ", '"+sCurrentLine.substring(35, 50).trim()+"'";
					System.out.println("In PSRLimitFileDownloadJob reading success file...3");
					action = sCurrentLine.substring(50, 60).trim();
					System.out.println("Starting PSRLimitFileDownloadJob sourceRef......"+sourceRef);
					System.out.println("Starting PSRLimitFileDownloadJob action......"+action);
					
					if(action.equalsIgnoreCase(ACK_ACTION)) {
						System.out.println("In PSRLimitFileDownloadJob reading success file...4");
						String serialNo = sCurrentLine.substring(100, 120).trim();
						System.out.println("Starting PSRLimitFileDownloadJob serialNo......"+serialNo);
						String sourceRefNo = sCurrentLine.substring(35, 50).trim();
						System.out.println("In PSRLimitFileDownloadJob reading success file...5");
						map.put(sourceRefNo, serialNo);
					}
				}
			}
			if(null != sourceRef && !"".equalsIgnoreCase(sourceRef)) {
				ILimitDAO limitDao = LimitDAOFactory.getDAO();
				System.out.println("In PSRLimitFileDownloadJob reading success file...6");
				limitDao.updatePSRLineDetails(sourceRef,map,ICMSConstant.PSR_STATUS_SUCCESS);
				System.out.println("In PSRLimitFileDownloadJob reading success file...7");
				Date responseDate = new Date();
				limitDao.updatePSRDataLog(sourceRef,map,ICMSConstant.PSR_STATUS_SUCCESS,responseDate);
				System.out.println("In PSRLimitFileDownloadJob reading success file...8");
			}
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	private void readingFileFail(String filePath) throws DBConnectionException, SQLException, Exception {
		BufferedReader bufferReader = null;
		FileReader fileReader = null;
		String[] spoolArray;
		try {
			System.out.println("Starting PSRLimitFileDownloadJob reading error file......"+Calendar.getInstance().getTime());
			fileReader = new FileReader(filePath);
			bufferReader = new BufferedReader(fileReader);
			String sCurrentLine;
			String errorDescription="";
			String spoolDate="";
			Map<String, String> map = new HashMap<String, String>();
			int counter =0;
			
			while ((sCurrentLine = bufferReader.readLine()) != null) {
				counter++;
				if(sCurrentLine.contains("Spool Date")) {
					spoolArray = sCurrentLine.split("Spool Date :");
					System.out.println("Starting PSRLimitFileDownloadJob reading error file...9");
					spoolDate = spoolArray[1].trim();
					System.out.println("Starting PSRLimitFileDownloadJob reading error file spoolDate......"+spoolDate);
				}
				if(counter >= 7) {
					if(sCurrentLine.length() ==0)
						break;
					System.out.println("Starting PSRLimitFileDownloadJob reading error file...10");
					errorDescription = sCurrentLine.substring(137, sCurrentLine.length()).trim();
					System.out.println("Starting PSRLimitFileDownloadJob reading error file errorDescription......"+errorDescription);
					String sourceRefNo = sCurrentLine.substring(37, 66).trim();
					System.out.println("Starting PSRLimitFileDownloadJob reading error file sourceRefNo......"+sourceRefNo);
					map.put(sourceRefNo, errorDescription);
					System.out.println("Starting PSRLimitFileDownloadJob reading error file...11");
				}
			}

			if(map.size()>0) {
				ILimitDAO limitDao = LimitDAOFactory.getDAO();
				System.out.println("Starting PSRLimitFileDownloadJob reading error file...12");
				limitDao.updatePSRLineDetails("",map,ICMSConstant.PSR_STATUS_REJECTED);
				System.out.println("Starting PSRLimitFileDownloadJob reading error file...13");
				Date responseDate = new Date();
				limitDao.updatePSRDataLog("",map,ICMSConstant.PSR_STATUS_REJECTED,responseDate);
				System.out.println("Starting PSRLimitFileDownloadJob reading error file...14");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferReader != null)
					bufferReader.close();
				if (fileReader != null)
					fileReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
