package com.integrosys.cms.batch.fcubsLimitFile.schedular;

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
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.ftp.CMSFtpClient;
import com.integrosys.cms.app.json.command.PrepareSendReceivePartyCommand;
import com.integrosys.cms.app.json.dao.IJsInterfaceLogDao;
import com.integrosys.cms.app.json.dao.ScmLineDao;
import com.integrosys.cms.app.json.dto.IJsInterfaceLog;
import com.integrosys.cms.app.json.dto.OBJsInterfaceLog;
import com.integrosys.cms.app.json.line.dto.LineRootResponse;
import com.integrosys.cms.app.json.line.dto.ResponseString;
import com.integrosys.cms.app.json.line.dto.Status;
import com.integrosys.cms.app.json.ws.ILineWebserviceClient;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;


public class FCUBSLimitFileDownloadJob implements FCUBSFileConstants {

	ResourceBundle resbundle = ResourceBundle.getBundle("ofa");
	boolean flagFcubsdownload = false;
	public static final String ACK_ACTION_MODIFY = "M";
	public static void main(String[] args) {

		new FCUBSLimitFileDownloadJob().execute();
	}

	public FCUBSLimitFileDownloadJob() {


	}

	/**
	 * This job is run and executed by quartz schedular.
	 * For more details refer to schedular configuration in 
	 * config\spring\batch\fcubsLimitFile\AppContext_Master.xml
	 * 
	 * Schedular has been designed to carry out the following activities
	 * 1. Download ACK/Fail file from sftp server
	 * 2. Read the file and update the status against each line detail
	 * 3. Update the log table.
	 * 4. move the file.
	 *
	 */

	public void execute() {	

		try {
			
			ResourceBundle bundle1 = ResourceBundle.getBundle("ofa");
			String fcubsServerName = bundle1.getString("fcubs.server.name");
			
			if(null!= fcubsServerName && fcubsServerName.equalsIgnoreCase("app1")){
				System.out.println("Starting FCUBSLimitFileDownloadJob......"+Calendar.getInstance().getTime()+"**flagFcubsdownload=>"+flagFcubsdownload);
				if(flagFcubsdownload == false) {
				
					flagFcubsdownload = true;
					
					System.out.println("After flagFcubsdownload flag check FCUBSLimitFileDownloadJob.**flagFcubsdownload=>"+flagFcubsdownload);
					
			DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob......"+Calendar.getInstance().getTime());
			ILimitDAO limitDao = LimitDAOFactory.getDAO();
			ArrayList<String[]> fileList = limitDao.getFileNames();
			if(null != fileList && fileList.size()>0){
			boolean fileDownload = downloadFileFromSFTP(fileList);
			DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob after downloadFileFromSFTP......"+fileDownload);
			System.out.println("FCUBSLimitFileDownloadJob after downloadFileFromSFTP......)"+fileDownload);
			if(fileDownload) {
				String localDataDir = resbundle.getString(FTP_FCUBS_DOWNLOAD_LOCAL_DIR);
				File ackFiles = new File(localDataDir.trim());
				File[] files = ackFiles.listFiles();
				DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob list files......"+files.length);

				if(null != files){
					for (File file : files) {

						try{
							DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob file getName......"+file.getName());
							System.out.println("Starting FCUBSLimitFileDownloadJob file getName......)"+file.getName());
							
							if(null != file && file.getName().endsWith(FCUBS_FILESUCCESSNAME))
								readingFileSuccess(localDataDir+file.getName());
							else if(null != file && file.getName().endsWith(FCUBS_FILEERRORNAME))
								readingFileError(localDataDir+file.getName());
						}
						catch(Exception e){
							flagFcubsdownload = false;
							System.out.println("Exception in FCUBSLimitFileDownloadJob Line no 105.**flagFcubsdownload=>"+flagFcubsdownload+"**e=>"+e);
							DefaultLogger.debug(this,"FCUBSLimitFileDownloadJob in catch Exception file name "+file.getName()+"......"+e.getMessage());
							e.printStackTrace();
						}

						finally {

							try {
								DefaultLogger.debug(this,"FCUBSLimitFileDownloadJob in move file name "+file.getName());
								moveFile(file,file.getName());
								flagFcubsdownload = false;
								System.out.println(" FCUBSLimitFileDownloadJob Line no 118.**flagFcubsdownload=>"+flagFcubsdownload);
							} catch (IOException e) {
								flagFcubsdownload = false;
								System.out.println("Exception in FCUBSLimitFileDownloadJob Line no 118.**flagFcubsdownload=>"+flagFcubsdownload+"**e=>"+e);
								e.printStackTrace();
							}
						}
					}

					}

				}

			}
			
			}
		}

		}
		
		catch (Exception e) {
			flagFcubsdownload = false;
			System.out.println("Exception in FCUBSLimitFileDownloadJob Line no 137.**flagFcubsdownload=>"+flagFcubsdownload+"**e=>"+e);
			DefaultLogger.debug(this,"FCUBSLimitFileDownloadJob in catch Exception......"+e.getMessage());
			e.printStackTrace();
		}
		flagFcubsdownload = false;
		System.out.println("flagFcubsdownload : "+flagFcubsdownload);

	}

	private void readingFileSuccess(String filePath) throws DBConnectionException, SQLException, Exception {
		BufferedReader bufferReader = null;
		FileReader fileReader = null;
		String[] spoolArray;
		
		try 
		{
			DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob reading success file......"+Calendar.getInstance().getTime());
			fileReader = new FileReader(filePath);
			bufferReader = new BufferedReader(fileReader);
			String sCurrentLine;
			String spoolDate="";
			String sourceRef="";
			String action ="";
			Map<String, String> map = new HashMap<String, String>();
			Map<String, String> mapNew = new HashMap<String, String>();
			int counter =0;
			while ((sCurrentLine = bufferReader.readLine()) != null) {
				counter++;
				action = "";
				if(sCurrentLine.contains(FCUBS_FILESPOOLDATE))
				{
					spoolArray = sCurrentLine.split(FCUBS_FILESPOOLDATEHEADING);
					spoolDate = spoolArray[1].trim();
					DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob spool Date......"+spoolDate);
				}
				if(counter >= 7)
				{
					if(sCurrentLine.length() ==0)
						break;
					if(sourceRef.equals(""))
						sourceRef = "'"+sCurrentLine.substring(35, 50).trim()+"'";
					else
						sourceRef = sourceRef+ ", '"+sCurrentLine.substring(35, 50).trim()+"'";
					action = sCurrentLine.substring(50, 60).trim();
					DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob sourceRef......"+sourceRef);
					DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob action......"+action);
					if(action.equalsIgnoreCase(ACK_ACTION)){

						String serialNo = sCurrentLine.substring(100, 119).trim();
						String sourceRefNo = sCurrentLine.substring(35, 50).trim();
						DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob serialNo......"+serialNo+" .... sourceRefNo...."+sourceRefNo+"  ..... Action...."+action);
						map.put(sourceRefNo, serialNo);
					}
					if(action.equalsIgnoreCase(ACK_ACTION) || action.equalsIgnoreCase(ACK_ACTION_MODIFY)){

						String serialNo = sCurrentLine.substring(100, 119).trim();
						String sourceRefNo = sCurrentLine.substring(35, 50).trim();
						DefaultLogger.debug(this,"mapNew if condition Starting FCUBSLimitFileDownloadJob serialNo......"+serialNo+" .... sourceRefNo...."+sourceRefNo+"  ..... Action...."+action);
						mapNew.put(sourceRefNo, serialNo);
					}

				}


			}
			if(null != sourceRef && !"".equalsIgnoreCase(sourceRef)){
				ILimitDAO limitDao = LimitDAOFactory.getDAO();
				limitDao.updateLineDetails(sourceRef,map,ICMSConstant.FCUBS_STATUS_SUCCESS);
				limitDao.clearUDFFields(sourceRef);
				//Date responseDate = new Date(spoolDate);
				Date responseDate = new Date();
				limitDao.updateFCUBSDataLog(sourceRef,map,ICMSConstant.FCUBS_STATUS_SUCCESS,responseDate);
//				processSuccessFileToScm(map);
				DefaultLogger.debug(this,"Going For FCUBSLimitFileDownloadJob processSuccessFileToScm(mapNew) Method......");
				processSuccessFileToScm(mapNew);
				DefaultLogger.debug(this, "Completed the processing of the lines that are marked as success");
			}

		} catch (IOException e) {
			System.out.println("Exception in readingFileSuccess fcubsfiledownloadjob.. e=>"+e);
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

	

	

	private void readingFileError(String filePath) throws DBConnectionException, SQLException, Exception {
		BufferedReader bufferReader = null;
		FileReader fileReader = null;
		String[] spoolArray;
		try 
		{
			DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob reading error file......"+Calendar.getInstance().getTime());
			fileReader = new FileReader(filePath);
			bufferReader = new BufferedReader(fileReader);
			String sCurrentLine;
			String errorDescription="";
			String spoolDate="";
			Map<String, String> map = new HashMap<String, String>();
			int counter =0;
			while ((sCurrentLine = bufferReader.readLine()) != null) {
				counter++;
				if(sCurrentLine.contains("Spool Date"))
				{
					spoolArray = sCurrentLine.split("Spool Date :");
					spoolDate = spoolArray[1].trim();
					DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob reading error file spoolDate......"+spoolDate);
				}
				if(counter >= 7)
				{
					if(sCurrentLine.length() ==0)
						break;
					errorDescription = sCurrentLine.substring(137, sCurrentLine.length()).trim();
					DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob reading error file errorDescription......"+errorDescription);
					String sourceRefNo = sCurrentLine.substring(37, 66).trim();
					DefaultLogger.debug(this,"Starting FCUBSLimitFileDownloadJob reading error file sourceRefNo......"+sourceRefNo);
					map.put(sourceRefNo, errorDescription);

				}
			}

			if(map.size()>0){
				ILimitDAO limitDao = LimitDAOFactory.getDAO();
				limitDao.updateLineDetails("",map,ICMSConstant.FCUBS_STATUS_REJECTED);
				//Date responseDate = new Date(spoolDate);
				Date responseDate = new Date();
				limitDao.updateFCUBSDataLog("",map,ICMSConstant.FCUBS_STATUS_REJECTED,responseDate);
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

	public boolean downloadFileFromSFTP(ArrayList<String[]> fileList) throws Exception{
		DefaultLogger.debug(this,"FCUBSLimitFileDownloadJob downloadFileFromSFTP() ");
		CMSFtpClient sftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.FCUBSLIMIT_FILE_UPLOAD);
		String remoteDataDir = resbundle.getString(FTP_FCUBS_DOWNLOAD_REMOTE_DIR);

		String localDataDir = resbundle.getString(FTP_FCUBS_DOWNLOAD_LOCAL_DIR);
		File dirFile = new File(localDataDir);
		if(!dirFile.exists()){

			dirFile.mkdirs();
		}

		DefaultLogger.debug(this,"FCUBSLimitFileDownloadJob downloadFileFromSFTP() open Connection");
		sftpClient.openConnection();
		DefaultLogger.debug(this, "FCUBSLimitFileDownloadJob uploadFileToSFTP():SFTP connection opened() ");
		boolean fileDownload = sftpClient.downloadFCUBSFile(remoteDataDir,localDataDir,fileList);
		sftpClient.closeConnection();
		DefaultLogger.debug(this, "FCUBSLimitFileDownloadJob uploadFileToSFTP():SFTP connection closed() ");
		return fileDownload;


	}

	public void moveFile(File sourceFile,
			String fileName) throws IOException {


		FileInputStream inputStream = null;
		FileOutputStream outstream = null;
		try {
			inputStream = new FileInputStream(sourceFile);
			String destPath = resbundle.getString(FTP_FCUBS_BACKUP_LOCAL_DIR);
			DefaultLogger.debug(this,"FCUBSLimitFileDownloadJob in move file destPath "+destPath);
			File destFolderPath = new File(destPath);
			if (!destFolderPath.exists())
			{
				destFolderPath.mkdirs();
			}

			outstream = new FileOutputStream(destPath+fileName);
			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inputStream.read(buffer)) > 0) {

				outstream.write(buffer, 0, length);

			}


		} catch (Exception e) {
			DefaultLogger.debug(this,"FCUBSLimitFileDownloadJob in move file exception "+e.getMessage());
			e.printStackTrace();

		} finally {
			try {
				if (inputStream != null)
					inputStream.close();

				if (outstream != null)
					outstream.close();

				boolean delete = sourceFile.delete();
				if(delete==false) {
					System.out.println("file  deletion failed for file:"+sourceFile.getPath());	
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

    public void processSuccessFileToScm(Map<String, String> map) {
    	DefaultLogger.debug(this,"Inside processSuccessFileToScm(mapNew) Method FCUBSLimitFileDownloadJob .....map=>"+map);
		ILimitDAO limitDao = LimitDAOFactory.getDAO();
    	if(map != null && map.size() > 0) {
    		ILineWebserviceClient clientLineImpl = (ILineWebserviceClient) BeanHouse.get("lineWebServiceClient") ;
    		OBJsInterfaceLog log = new OBJsInterfaceLog();
			LineRootResponse response;
			DefaultLogger.debug(this, "Before Going Inside for Loop... ");
			
    		for(String srcRefId : map.keySet()) {		
    			try {
    			String stgScmFlag = limitDao.getScmFlagStgforStp(srcRefId);	
    			 String mainScmFlag = limitDao.getScmFlagMainforStp(srcRefId);
    				log.setModuleName("Release Line STP");
    				DefaultLogger.debug(this, "Inside for loop as the values are mainScmFlag =>"+mainScmFlag+"... stgScmFlag=>"+stgScmFlag);
    				if((mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("Yes"))||
    						(mainScmFlag.equalsIgnoreCase("No")&&stgScmFlag.equalsIgnoreCase("Yes"))||
    						(mainScmFlag.equalsIgnoreCase("Yes")&&stgScmFlag.equalsIgnoreCase("No"))) {
    					DefaultLogger.debug(this, "Inside if as the values are "+mainScmFlag+" "+stgScmFlag);
    					DefaultLogger.debug(this, "Going for  clientLineImpl.sendRetrieveRequestforStp(srcRefId, log)...");
    					response = clientLineImpl.sendRetrieveRequestforStp(srcRefId, log);
    					DefaultLogger.debug(this, "SCM webservice response "+response);
    					ResponseString responseString  = response.getResponseString(); 
    					Status statusString = response.getStatus();
    					if(responseString!=null) {
    					log.setResponseDateTime(DateUtil.now().getTime());
    					log.setErrorCode(String.valueOf(responseString.getStatusCode()));
    					List errorMsgList = responseString.getResponseMessage();
    					StringBuilder errorMsg = new StringBuilder();
    					for(int i=0;i<errorMsgList.size();i++) {
    						errorMsg.append(errorMsgList.get(i)); 
    					}
    					log.setErrorMessage(errorMsg.toString());
    					log.setStatus(responseString.getStatusCode()==0 ?"Success":"Fail");
    					DefaultLogger.debug(this, "inserted in log for id "+log.getId());
    					}else {
    						DefaultLogger.debug(this, "inside else as  responseString is null... responseString=>"+responseString);
    						log.setResponseDateTime(DateUtil.now().getTime());
    						log.setErrorCode(statusString.getErrorCode());
    						log.setErrorMessage(statusString.getReplyText());
    						log.setStatus("Error");
    					}
    					DefaultLogger.debug(this, "Going for insertLogForSTP(log)...");
    					limitDao.insertLogForSTP(log);
    				}else {
    					DefaultLogger.debug(this, "Inside else as both are no. Need not call the service "+mainScmFlag+" "+stgScmFlag);
    				}
    	}catch(Exception e) {
    		DefaultLogger.debug(this, "Exception Caught in FCUBSLimitDownloadJob processSuccessFileToScm(mapNew) ...e=>"+e);
    		System.out.println("Exception Caught in FCUBSLimitDownloadJob processSuccessFileToScm(mapNew) ...e=>"+e);
    		e.printStackTrace();
			
    	}
    }
    	}else {
			DefaultLogger.debug(this, "The map is empty"+map.size());
			System.out.println("The map is empty"+map.size());
    	}
    }
}
