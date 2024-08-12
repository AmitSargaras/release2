package com.integrosys.cms.batch.fcc.col.liquidation.fd.upload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.db2.jcc.uw.a.InvalidationException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fcc.col.liquidation.fd.upload.bus.IFccColFdValProcessUpload;
import com.integrosys.cms.app.ftp.CMSFtpClient;

public class FccColFdFileUploadJob implements FccColFdFileUploadConstant {

	private final static Logger logger = LoggerFactory.getLogger(FccColFdFileUploadJob.class);

	public static void main(String[] args) {
		new FccColFdFileUploadJob().execute();
	}

	public void execute() {

		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		System.out.println("Inside FccColFdFileUploadJob.java");
		// ftp.fd.system.names
		String fdSystemNames = resbundle.getString(FTP_FCC_COL_LIQ_SYSTEM_NAMES);
		String systemNameArr[] = fdSystemNames.split(",");
		List<String> arr = new ArrayList<String>();
		arr = Arrays.asList(systemNameArr);
		
		String systemName = "";
		
		try {
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

		String serverName = resbundle.getString(SERVER_CONFIGURED + systemNameSuffix);
		
		if (!AbstractCommonMapper.isEmptyOrNull(serverName) && serverName.equalsIgnoreCase(SERVER_NAME)) {
			Date today = DateUtil.clearTime(new Date());
			System.out.println("File upload for FCC Collateral Liquidation process  has been started at "
					+ Calendar.getInstance().getTime());
			logger.info("File upload for FCC Collateral Liquidation process  has been started at "
					+ Calendar.getInstance().getTime());

			

					List<HashMap<String, String>> uploadList = dao.getDetailsForFccColFdileUploadJob(systemName);

					/*
					 * if(uploadList == null) { System.out.println("uploadList is null"); }else {
					 * if(uploadList.isEmpty()) { System.out.println("uploadList is empty"); }else {
					 * System.out.println("uploadList is not empty"); } }
					 */

					if (uploadList == null || uploadList.size() == 0) {
//						System.out.println("File upload for FCC Collateral Liquidation process  has no data"
//								+ " to upload at SFTP location");
						logger.warn("File upload for FCC Collateral Liquidation process  has no data"
								+ " to upload at SFTP location");
					} else {
						FccColFdFileUploadHeader[] headerList = FccColFdFileUploadHeader.values();
						String headers = join(FccColFdFileUploadHeader.getHeaderNames(), SEPARATOR, true);

						StringBuffer dataFile = new StringBuffer();
						dataFile.append(headers);
						dataFile.append(System.getProperty("line.separator"));

						int size = uploadList.size();
						for (int i = 0; i < size; i++) {
							HashMap<String, String> item = uploadList.get(i);

							List<String> line = new ArrayList<String>();
							for (FccColFdFileUploadHeader header : headerList) {
								String itemValue = item.containsKey(header.name()) ? item.get(header.name()) : "";
								itemValue = AbstractCommonMapper.isEmptyOrNull(itemValue) ? "" : itemValue.trim();
								line.add(itemValue);
							}

							dataFile.append(join(line, SEPARATOR, true));

							if (i < (size - 1)) {
								dataFile.append(System.getProperty("line.separator"));
							}
						}

						File dirFile = new File(resbundle.getString(FTP_UPLOAD_LOCAL_DIR + systemNameSuffix));
						if (!dirFile.exists()) {
							dirFile.mkdirs();
						}

						SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
						String todaysDate = dateFormat.format(today);

						// CLIMS_FD_BH_YYYYMMDD.txt
						// CLIMS_FD_GC_YYYYMMDD.txt
						// CLIMS_FD_HK_YYYYMMDD.txt

						String localFilePath = "";
						if ("BAHRAIN".equals(systemName)) {
							localFilePath = String.format("%s%s%s_%s.%s", resbundle.getString(FTP_UPLOAD_LOCAL_DIR + systemNameSuffix),
									File.separator, FILE_NAME_PREFIX_BH, todaysDate, FILE_NAME_POSTFIX);
						} else if ("GIFTCITY".equals(systemName)) {
							localFilePath = String.format("%s%s%s_%s.%s", resbundle.getString(FTP_UPLOAD_LOCAL_DIR + systemNameSuffix),
									File.separator, FILE_NAME_PREFIX_GC, todaysDate, FILE_NAME_POSTFIX);
						} else if ("HONGKONG".equals(systemName)) {
							localFilePath = String.format("%s%s%s_%s.%s", resbundle.getString(FTP_UPLOAD_LOCAL_DIR + systemNameSuffix),
									File.separator, FILE_NAME_PREFIX_HK, todaysDate, FILE_NAME_POSTFIX);
						}

						File file = new File(localFilePath);
						if (file.exists()) {
							file.delete();
						}
						file.createNewFile();

						/*file.setReadable(true);
						file.setWritable(true);*/
						file.setReadable(true, false);
						file.setWritable(true, false);
//						file.setExecutable(true);
						
						
						FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
						BufferedWriter out = new BufferedWriter(fw);
						out.write(dataFile.toString());

						out.close();
						fw.close();
//						System.out.println("Data captured was written successfully into local file: " + localFilePath);
						logger.info("Data captured was written successfully into local file: " + localFilePath);

						String remoteDataDir = "";
						String fName = "";
						String connectionFor = "";
						if ("BAHRAIN".equals(systemName)) {
							remoteDataDir = String.format("%s%s%s_%s.%s", resbundle.getString(FTP_UPLOAD_REMOTE_DIR + systemNameSuffix),
									File.separator, FILE_NAME_PREFIX_BH, todaysDate, FILE_NAME_POSTFIX);
							fName = FccColFdFileUploadConstant.FILE_NAME_PREFIX_BH;
							connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_BH;
						} else if ("GIFTCITY".equals(systemName)) {
							remoteDataDir = String.format("%s%s%s_%s.%s", resbundle.getString(FTP_UPLOAD_REMOTE_DIR + systemNameSuffix),
									File.separator, FILE_NAME_PREFIX_GC, todaysDate, FILE_NAME_POSTFIX);
							fName = FccColFdFileUploadConstant.FILE_NAME_PREFIX_GC;
							connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_GC;
						} else if ("HONGKONG".equals(systemName)) {
							remoteDataDir = String.format("%s%s%s_%s.%s", resbundle.getString(FTP_UPLOAD_REMOTE_DIR + systemNameSuffix),
									File.separator, FILE_NAME_PREFIX_HK, todaysDate, FILE_NAME_POSTFIX);
							fName = FccColFdFileUploadConstant.FILE_NAME_PREFIX_HK;
							connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_HK;
						}
						
						boolean flag = true;
						
						try {
							uploadFileToSFTP(localFilePath, remoteDataDir,systemName);
						}catch(Exception e) {
							flag = false;
							System.out.println("Exception caught for uploadFileToSFTP.");
							e.printStackTrace();
						}
//						System.out
//								.println("File name: " + String.format("%s_%s.%s", fName, todaysDate, FILE_NAME_POSTFIX)
//										+ " successfully transfer");
						logger.info("File name: " + String.format("%s_%s.%s", fName, todaysDate, FILE_NAME_POSTFIX)
								+ " successfully transfer");
//						System.out.println("Date and Time of transfer : " + DateUtil.now());
						logger.info("Date and Time of transfer : " + DateUtil.now());
//						System.out.println(
//								"File was transferred to SFTP Location: " + resbundle.getString(FTP_UPLOAD_REMOTE_DIR));
						logger.info(
								"File was transferred to SFTP Location: " + resbundle.getString(FTP_UPLOAD_REMOTE_DIR + systemNameSuffix));
						System.out.println("Storing file information into database");
						logger.info("Storing file information into database");

						IFccColFdValProcessUpload processUpload = (IFccColFdValProcessUpload) BeanHouse
								.get("fccColFdValProcessUploadJdbc");
						if(flag == true) {
						processUpload.logData(connectionFor,
								String.format("%s", resbundle.getString(FTP_UPLOAD_REMOTE_DIR + systemNameSuffix)),
								String.format("%s_%s.%s", fName, todaysDate, FILE_NAME_POSTFIX), uploadList.size(),
								FILE_STATUS);
						}else {
							processUpload.logData(connectionFor,
									String.format("%s", resbundle.getString(FTP_UPLOAD_REMOTE_DIR + systemNameSuffix)),
									String.format("%s_%s.%s", fName, todaysDate, FILE_NAME_POSTFIX), uploadList.size(),
									FILE_STATUS_FAILED);
						}
						System.out.println("File upload for FCC Collateral Liquidation process  is completed at "
								+ Calendar.getInstance().getTime());
						logger.info("File upload for FCC Collateral Liquidation process  is completed at "
								+ Calendar.getInstance().getTime());

						file = new File(localFilePath);
						if(flag == true) {
						if (file.exists()) {
							file.delete();
							System.out.println("Local file: " + localFilePath + " was deleted successfully");
							logger.info("Local file: " + localFilePath + " was deleted successfully");
						}
					}
					}
				}}
			} catch (IOException e) {
				System.out.println("File upload for FCC Collateral Liquidation process  stopped abrutly at "
						+ Calendar.getInstance().getTime() + " because of " + e.getMessage());
				logger.error("File upload for FCC Collateral Liquidation process  stopped abrutly at "
						+ Calendar.getInstance().getTime() + " because of " + e.getMessage());
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("File upload for FCC Collateral Liquidation process  stopped abrutly at "
						+ Calendar.getInstance().getTime() + " because of " + e.getMessage());
				logger.error("File upload for FCC Collateral Liquidation process  stopped abrutly at "
						+ Calendar.getInstance().getTime() + " because of " + e.getMessage());
				e.printStackTrace();
			}
		}
	

	private void uploadFileToSFTP(String localFilePath, String remoteDataDir, String systemName) throws Exception {
		System.out.println("Uploading generated file to SFTP location");
		logger.info("Uploading generated file to SFTP location");
		String connectionFor = "";
		if ("BAHRAIN".equals(systemName)) {
			connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_BH;
		} else if ("GIFTCITY".equals(systemName)) {
			connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_GC;
		} else if ("HONGKONG".equals(systemName)) {
			connectionFor = ICMSConstant.FD_STP_FCC_COL_FILE_UPLOAD_HK;
		}
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa", connectionFor);

		sftpClient.openConnection();
		System.out.println("Uploading generated file to SFTP location and paths :localFilePath=>"+localFilePath+"  remoteDataDir=>"+remoteDataDir);
		logger.info("SFTP connection was opened for file transfer");
			sftpClient.uploadFile(localFilePath, remoteDataDir);
		sftpClient.closeConnection();
		System.out.println("Uploading generated file to SFTP location");
		logger.info("SFTP connection successfully closed after file transfer");
	}

	private String join(List<String> dataList, String separator, boolean skipCondition) {
		if (dataList == null || dataList.size() == 0)
			return null;

		StringBuffer result = new StringBuffer();
		int size = dataList.size();

		for (int i = 0; i < size; i++) {
			result.append(dataList.get(i));
			if (i < (size - 1) || skipCondition) {
				result.append(separator);
			}
		}

		return result.toString();
	}

}
