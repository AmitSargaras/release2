package com.integrosys.cms.batch.fcc.col.liquidation.fd.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fcc.col.liquidation.fd.upload.bus.IFccColFdValProcessDownload;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

public class FccColFdFileDownloadJob implements FccColFdFileUploadConstant {

	
	private final static Logger logger = LoggerFactory.getLogger(FccColFdFileDownloadJob.class);

	public FccColFdFileDownloadJob() {

	}

	public static void main(String[] args) {
		new FccColFdFileDownloadJob().execute();
	}

	/**
	 * This job is run and executed by quartz schedular. For more details refer to
	 * schedular configuration in
	 * config\spring\batch\fccColFdValProcessUpload\AppContext_Master.xml Schedular has been
	 * designed to carry out the following activities 1. Download ACK/Fail file from
	 * sftp server 2. Read the file and update the status against each line detail
	 * 3. Update the log table. 4. move the file.
	 */
	
    private String runProcedureNameFcc;

	public String getRunProcedureNameFcc() {
		return runProcedureNameFcc;
	}

	public void setRunProcedureNameFcc(String runProcedureNameFcc) {
		this.runProcedureNameFcc = runProcedureNameFcc;
	}
	
	
	public void execute() {
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		
		System.out.println("Inside FccColFdFileDownloadJob.java");
		String fdSystemNames = resbundle.getString(FTP_FCC_COL_LIQ_SYSTEM_NAMES);
		String systemNameArr[] = fdSystemNames.split(",");
		List<String> arr = new ArrayList<String>();
		arr = Arrays.asList(systemNameArr);
		
		String systemName = "";
		try {
		IFccColFdValProcessDownload processDownload = (IFccColFdValProcessDownload) BeanHouse
				.get("fccColFdValProcessDownloadJdbc");
		logger.info("Before Procedure run FccColFdFileDownloadJob.java");
		System.out.println("Before Procedure run FccColFdFileDownloadJob.java");
		processDownload.dataTransferTempToActualFcc(getRunProcedureNameFcc());
		logger.info("Before Procedure run FccColFdFileDownloadJob.java");
		logger.info("Starting FccColFdFileDownloadJob......" + Calendar.getInstance().getTime());
		System.out.println("Starting FccColFdFileDownloadJob......" + Calendar.getInstance().getTime());
		
			ICollateralDAO dao = CollateralDAOFactory.getDAO();
			for (int j = 0; j < arr.size(); j++) {
				systemName = arr.get(j);
			//BAHRAIN,GIFTCITY,HONGKONG
				String systemNameSuffix = "";
				if("BAHRAIN".equals(systemName)) {
					systemNameSuffix = ".bh";
				}
				else if("GIFTCITY".equals(systemName)) {
					systemNameSuffix = ".gc";
				}
				else if("HONGKONG".equals(systemName)) {
					systemNameSuffix = ".hk";
				}
			
			ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
			String serverName = resbundle.getString(SERVER_CONFIGURED + systemNameSuffix);

			SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
			Date today = DateUtil.clearTime(new Date());
			String todaysDate = dateFormat.format(today);
			logger.info("File upload for FCC Collateral Liquidation process  has been started at "
					+ Calendar.getInstance().getTime());
			
			System.out.println("File upload for FCC Collateral Liquidation process  has been started at "
					+ Calendar.getInstance().getTime());
			
			if (null != serverName && serverName.equalsIgnoreCase(SERVER_NAME)) {
				ILimitDAO limitDao = LimitDAOFactory.getDAO();
				ArrayList<String> fileList = new ArrayList<String>();
				if("BAHRAIN".equals(systemName)) {
					String fileList1 = FILE_NAME_PREFIX_BH + "_"+todaysDate+ FCC_COL_FILENAME_SUFFIX;
					fileList.add(fileList1);
					System.out.println("fileList1=>" + fileList1 );
				}
				else if("GIFTCITY".equals(systemName)) {
					String fileList2 = FILE_NAME_PREFIX_GC + "_"+todaysDate+ FCC_COL_FILENAME_SUFFIX;
					fileList.add(fileList2);
					System.out.println( " fileList2=>" + fileList2);
				}
				else if("HONGKONG".equals(systemName)) {
					String fileList3 = FILE_NAME_PREFIX_HK + "_"+todaysDate+ FCC_COL_FILENAME_SUFFIX;
					fileList.add(fileList3);
					System.out.println(" fileList3=>" + fileList3);
				}
				
				
				//a)CLIMS_FD_BH_YYYYMMDD_REJECT.txt
				//b)CLIMS_FD_GC_YYYYMMDD_REJECT.txt
				//c)CLIMS_FD_HK_YYYYMMDD_REJECT.txt
				

				if (null != fileList && fileList.size() > 0) {
					boolean fileDownload = downloadFileFromSFTP(fileList,systemName);
					logger.info("Starting FccColFdFileDownloadJob after downloadFileFromSFTP......" + fileDownload);
					System.out.println("Starting FccColFdFileDownloadJob after downloadFileFromSFTP......" + fileDownload);
					if (fileDownload) {
						String localDataDir = resbundle.getString(FTP_DOWNLOAD_LOCAL_DIR + systemNameSuffix);
						File ackFiles = new File(localDataDir.trim());
						File[] files = ackFiles.listFiles();
						logger.info("Starting FccColFdFileDownloadJob list files......" + files.length);
						System.out.println("Starting FccColFdFileDownloadJob list files......" + files.length);
						if (null != files) {
							for (File file : files) {
								try {
									
									logger.info("Starting FccColFdFileDownloadJob file getName......" + file.getName());
									System.out.println("Starting FccColFdFileDownloadJob file getName......" + file.getName());
									if (null != file && file.getName().endsWith(FCC_COL_FILENAME_SUFFIX))
										processDownload.readingFileSuccess(localDataDir + file.getName(),file.getName());
//									else if (null != file && file.getName().endsWith(FCC_COL_FILENAME_SUFFIX))
//										readingFileFail(localDataDir + file.getName());
								} catch (Exception e) {
									logger.info("FccColFdFileDownloadJob in catch Exception file name "+ file.getName() + "..." + e.getMessage());
									System.out.println("FccColFdFileDownloadJob in catch Exception file name "+ file.getName() + "..." + e.getMessage());
									e.printStackTrace();
								}
								finally {
									try {
										System.out.println("FccColFdFileDownloadJob outside if condition =>  move file name " + file.getName());
										if (null != file && file.getName().endsWith(FCC_COL_FILENAME_SUFFIX)) {
										logger.info("FccColFdFileDownloadJob in move file name " + file.getName());
										System.out.println("FccColFdFileDownloadJob in move file name " + file.getName());
										moveFile(file, file.getName(),systemName);
										}
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				}
			}
			}
			logger.info("Ending FccColFdFileDownloadJob......" + Calendar.getInstance().getTime());
			System.out.println("Ending FccColFdFileDownloadJob......" + Calendar.getInstance().getTime());
		} catch (Exception e) {
			DefaultLogger.debug(this, "FccColFdFileDownloadJob in catch Exception......" + e.getMessage());
			e.printStackTrace();
		}
			
	}
	
	public boolean downloadFileFromSFTP(ArrayList<String> fileList,String systemName) throws Exception {
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		DefaultLogger.debug(this,"FccColFdFileDownloadJob downloadFileFromSFTP() ");
		System.out.println("FccColFdFileDownloadJob downloadFileFromSFTP() ");
		String connectionFor = "";
		if ("BAHRAIN".equals(systemName)) {
			connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_BH;
		} else if ("GIFTCITY".equals(systemName)) {
			connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_GC;
		} else if ("HONGKONG".equals(systemName)) {
			connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_HK;
		}
		
		String systemNameSuffix = "";
		if("BAHRAIN".equals(systemName)) {
			systemNameSuffix = ".bh";
		}
		else if("GIFTCITY".equals(systemName)) {
			systemNameSuffix = ".gc";
		}
		else if("HONGKONG".equals(systemName)) {
			systemNameSuffix = ".hk";
		}
		
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa",connectionFor);
		String remoteDataDir = resbundle.getString(FTP_DOWNLOAD_REMOTE_DIR + systemNameSuffix);
		String localDataDir = resbundle.getString(FTP_DOWNLOAD_LOCAL_DIR + systemNameSuffix);
		File dirFile = new File(localDataDir);
		if(!dirFile.exists())
			dirFile.mkdirs();
		DefaultLogger.debug(this,"FccColFdFileDownloadJob downloadFileFromSFTP() open Connection");
		logger.info("FccColFdFileDownloadJob downloadFileFromSFTP() open Connection");
		System.out.println("FccColFdFileDownloadJob downloadFileFromSFTP() open Connection");
		sftpClient.openConnection();
		DefaultLogger.debug(this, "FccColFdFileDownloadJob downloadFileFromSFTP():SFTP connection opened() "); 
		logger.info("FccColFdFileDownloadJob downloadFileFromSFTP():SFTP connection opened() ");
		System.out.println("FccColFdFileDownloadJob downloadFileFromSFTP():SFTP connection opened() ");
		boolean fileDownload = sftpClient.downloadFccColFile(remoteDataDir,localDataDir,fileList);
		sftpClient.closeConnection();
		DefaultLogger.debug(this, "FccColFdFileDownloadJob downloadFileFromSFTP():SFTP connection closed() ");
		logger.info("FccColFdFileDownloadJob downloadFileFromSFTP():SFTP connection closed() ");
		System.out.println("FccColFdFileDownloadJob downloadFileFromSFTP():SFTP connection closed() ");
		return fileDownload;

	}
	
	public void moveFile(File sourceFile, String fileName,String systemName) throws IOException {

		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		
		String systemNameSuffix = "";
		if("BAHRAIN".equals(systemName)) {
			systemNameSuffix = ".bh";
		}
		else if("GIFTCITY".equals(systemName)) {
			systemNameSuffix = ".gc";
		}
		else if("HONGKONG".equals(systemName)) {
			systemNameSuffix = ".hk";
		}
		
		try {
			inputStream = new FileInputStream(sourceFile);
			String destPath = resbundle.getString(FTP_BACKUP_LOCAL_DIR + systemNameSuffix);
			DefaultLogger.debug(this, "FccColFdFileDownloadJob in move file destPath " + destPath);
			logger.info("FccColFdFileDownloadJob in move file destPath " + destPath);
			System.out.println("FccColFdFileDownloadJob in move file destPath " + destPath);
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
			DefaultLogger.debug(this, "FccColFdFileDownloadJob in move file exception " + e.getMessage());
			System.out.println("FccColFdFileDownloadJob in move file exception " + e.getMessage());
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
}
