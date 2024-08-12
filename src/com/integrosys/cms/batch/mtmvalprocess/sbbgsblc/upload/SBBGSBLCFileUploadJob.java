package com.integrosys.cms.batch.mtmvalprocess.sbbgsblc.upload;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.base.uiinfra.common.AbstractCommonMapper;
import com.integrosys.cms.app.collateral.bus.CollateralDAOFactory;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.mtmvaluationprocess.upload.bus.IMTMValProcessUpload;
import com.integrosys.cms.batch.mtmvalprocess.mfequity.upload.MfEquityFileUploadHeader;
import com.integrosys.cms.batch.mtmvalprocess.mfequity.upload.MfEquityFileUploadNewHeader;

public class SBBGSBLCFileUploadJob implements SBBGSBLCFileUploadConstant{

	private final static Logger logger = LoggerFactory.getLogger(SBBGSBLCFileUploadJob.class);
	
	public static void main(String[] args) {
		new SBBGSBLCFileUploadJob().execute();
	}
	
	public void execute() {
		
		ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
		String serverName = resbundle.getString(SERVER_CONFIGURED);
		String invrstmentAccNoFlag = resbundle.getString("wms.investment.acc.flag");
		String headers=null;
		
		if(!AbstractCommonMapper.isEmptyOrNull(serverName) && serverName.equalsIgnoreCase(SERVER_NAME)) {
			Date today = DateUtil.clearTime(new Date());
			System.out.println("File upload for MTM valuation process for SBBG and SBLC has been started at " 
					  + Calendar.getInstance().getTime());
			logger.info("File upload for MTM valuation process for SBBG and SBLC has been started at " 
					  + Calendar.getInstance().getTime());
			
			try {
				ICollateralDAO dao = CollateralDAOFactory.getDAO();
				List<HashMap<String,String>> uploadList = dao.getDetailsForSBBGSBLCFileUploadJob();
				
				if(uploadList == null || uploadList.size() == 0) { 
					System.out.println("File upload for MTM valuation process for SBBG and SBLC has no data"
							+ " to upload at SFTP location");
					logger.warn("File upload for MTM valuation process for SBBG and SBLC has no data"
							+ " to upload at SFTP location");
				} else {
					//
					if(invrstmentAccNoFlag.equalsIgnoreCase("true"))
					{
						headers = join(SBBGSBLCFileUploadNewHeader.getHeaderNames(), SEPARATOR, true);
					}
					else {	
						 headers = join(SBBGSBLCFileUploadHeader.getHeaderNames(), SEPARATOR, true);
					}
					
					StringBuffer dataFile = new StringBuffer();
					dataFile.append(headers);
					dataFile.append(System.getProperty("line.separator"));
					
					int size = uploadList.size();
					for(int i = 0; i < size; i++) {
						HashMap<String, String> item = uploadList.get(i);
						
						dataFile.append(join(enumCheck(invrstmentAccNoFlag,item), SEPARATOR, true));
						
						if(i < (size - 1)) {
							dataFile.append(System.getProperty("line.separator"));
						}
					}
					
					File dirFile = new File(resbundle.getString(FTP_UPLOAD_LOCAL_DIR));
					if(!dirFile.exists()){
						dirFile.mkdirs();
					}
					
					SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
					String todaysDate = dateFormat.format(today);
					
					String localFilePath = String.format("%s%s%s_%s.%s", resbundle.getString(FTP_UPLOAD_LOCAL_DIR),
							File.separator, FILE_NAME_PREFIX, todaysDate, FILE_NAME_POSTFIX);
					File file = new File(localFilePath);
					if(file.exists()) {
						file.delete();
					}
					file.createNewFile();
					
					FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
					BufferedWriter out = new BufferedWriter(fw);
					out.write(dataFile.toString());
					
					out.close();
					fw.close();
					System.out.println("Data captured was written successfully into local file: " + localFilePath);
					logger.info("Data captured was written successfully into local file: " + localFilePath);
					
					String remoteDataDir = String.format("%s%s%s_%s.%s", resbundle.getString(FTP_UPLOAD_REMOTE_DIR),
							File.separator, FILE_NAME_PREFIX, todaysDate, FILE_NAME_POSTFIX);
					
					uploadFileToSFTP(localFilePath, remoteDataDir);
					System.out.println("File name: " + String.format("%s_%s.%s",FILE_NAME_PREFIX, todaysDate, FILE_NAME_POSTFIX) 
					+ " successfully transfer");
					logger.info("File name: " + String.format("%s_%s.%s",FILE_NAME_PREFIX, todaysDate, FILE_NAME_POSTFIX) 
								+ " successfully transfer");
					System.out.println("Date and Time of transfer : " + DateUtil.now());
					logger.info("Date and Time of transfer : " + DateUtil.now());
					System.out.println("File was transferred to SFTP Location: " + resbundle.getString(FTP_UPLOAD_REMOTE_DIR));
					logger.info("File was transferred to SFTP Location: " + resbundle.getString(FTP_UPLOAD_REMOTE_DIR));
					System.out.println("Storing file information into database");
					logger.info("Storing file information into database");
					
					IMTMValProcessUpload processUpload = (IMTMValProcessUpload) BeanHouse.get("mtmValProcessUploadJdbc");
					processUpload.logData(ICMSConstant.SBBG_SBLC_FILE_UPLOAD, String.format("%s", resbundle.getString(FTP_UPLOAD_REMOTE_DIR)),
							String.format("%s_%s.%s",FILE_NAME_PREFIX, todaysDate, FILE_NAME_POSTFIX), uploadList.size());
					System.out.println("File upload for MTM valuation process for SBBG and SBLC is completed at " 
							 + Calendar.getInstance().getTime());
					logger.info("File upload for MTM valuation process for SBBG and SBLC is completed at " 
								 + Calendar.getInstance().getTime());
					
					file = new File(localFilePath);
					if(file.exists()) {
						file.delete();
						System.out.println("Local file: " + localFilePath + " was deleted successfully");
						logger.info("Local file: " + localFilePath + " was deleted successfully");
					}
				}
			} catch (IOException e) {
				System.out.println("File upload for MTM valuation process for SBBG and SBLC stopped abrutly at " 
						+ Calendar.getInstance().getTime() + " because of " + e.getMessage());
				logger.error("File upload for MTM valuation process for SBBG and SBLC stopped abrutly at " 
										+ Calendar.getInstance().getTime() + " because of " + e.getMessage());
				e.printStackTrace();
			}
			catch (Exception e) {
				System.out.println("File upload for MTM valuation process for SBBG and SBLC stopped abrutly at " 
						+ Calendar.getInstance().getTime() + " because of " + e.getMessage());
				logger.error("File upload for MTM valuation process for SBBG and SBLC stopped abrutly at " 
						+ Calendar.getInstance().getTime() + " because of " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
	
	private void uploadFileToSFTP(String localFilePath, String remoteDataDir) throws Exception {
		System.out.println("Uploading generated file to SFTP location");
		logger.info("Uploading generated file to SFTP location");
		
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.SBBG_SBLC_FILE_UPLOAD);
		
		sftpClient.openConnection();
		System.out.println("Uploading generated file to SFTP location");
		logger.info("SFTP connection was opened for file transfer");
		
		sftpClient.uploadFile(localFilePath, remoteDataDir);
		
		sftpClient.closeConnection();
		System.out.println("Uploading generated file to SFTP location");
		logger.info("SFTP connection successfully closed after file transfer");
	}
	
	private String join(List<String> dataList, String separator, boolean skipCondition) {
		if(dataList == null || dataList.size() == 0)
			return null;
		
		StringBuffer result = new StringBuffer();
		int size = dataList.size();
		
		for(int i = 0; i < size; i++) {
			result.append(dataList.get(i));
			if(i < (size - 1) || skipCondition) {
				result.append(separator);
			}
		}
		
		return result.toString();
	}
	
	
	private List<String> enumCheck(String invrstmentAccNoFlag, HashMap<String, String> item)
	{
		
		List<String> line = new ArrayList<String>();
		if(invrstmentAccNoFlag.equalsIgnoreCase("false"))
		{
			SBBGSBLCFileUploadHeader[] headerList = SBBGSBLCFileUploadHeader.values();
		for(SBBGSBLCFileUploadHeader header: headerList) {
			String itemValue = item.containsKey(header.name()) ? item.get(header.name()) : "";
			itemValue = AbstractCommonMapper.isEmptyOrNull(itemValue) ? "" : itemValue.trim();
			line.add(itemValue);
		
		}
		}
		else
		{  SBBGSBLCFileUploadNewHeader[] headerList = SBBGSBLCFileUploadNewHeader.values();
			for(SBBGSBLCFileUploadNewHeader header: headerList) {
				String itemValue = item.containsKey(header.name()) ? item.get(header.name()) : "";
				itemValue = AbstractCommonMapper.isEmptyOrNull(itemValue) ? "" : itemValue.trim();
				line.add(itemValue);	
		}
		}
		return line;
	}
	
	
}
