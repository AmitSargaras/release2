/**
 * 
 */
package com.integrosys.cms.app.ftp;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.util.DateUtil;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.fileUpload.bus.IFileUploadJdbc;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamGroup;


/**
 * @author bhushan.malankar
 *
 */
public class FileUploadFtpService implements IFileUploadConstants{

	
	/**
	 * 
	 */
	public FileUploadFtpService() {
		
	}
	
	private static IFileUploadJdbc fileUploadJdbc = null;
	
	public static IFileUploadJdbc getFileUploadJdbc() {
		if (fileUploadJdbc == null) {
			fileUploadJdbc = (IFileUploadJdbc) BeanHouse.get("fileUploadJdbc");
        }
		return fileUploadJdbc;
	}
	
	public void downlodUploadedFiles(String system) throws Exception{
		Date date=new Date();
		/*		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
		IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
		Date applicationDate=new Date();
		for(int i=0;i<generalParamEntries.length;i++){
			if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
				applicationDate=new Date(generalParamEntries[i].getParamValue());
			}
		}*/	
		Date applicationDate=new Date();
		Boolean isHBConnectionException = Boolean.FALSE;
		IGeneralParamDao generalParamDao =(IGeneralParamDao)BeanHouse.get("generalParamDao");
		try {
			System.out.println("::: In FileUploadFtpService ::  line:-68  start  generalParamDao try block");
			IGeneralParamGroup generalParamGroup  =generalParamDao.getGeneralParamGroupByGroupType("actualGeneralParamGroup", "GENERAL_PARAM");
			IGeneralParamEntry[]generalParamEntries= generalParamGroup.getFeedEntries();
			
			for(int i=0;i<generalParamEntries.length;i++){
				if(generalParamEntries[i].getParamCode().equals("APPLICATION_DATE")){
					applicationDate=new Date(generalParamEntries[i].getParamValue());
					System.out.println(" applicationDate:: "+applicationDate);

				}
			}
			System.out.println("::: In FileUploadFtpService ::  line:-79  end  generalParamDao try block ");
		}catch(Exception e) {
			isHBConnectionException = Boolean.TRUE;
			System.out.println("::: In FileUploadFtpService ::  line:-82  inside catch isHBConnectionException : "+isHBConnectionException);
			e.printStackTrace();
		}
		
		if(isHBConnectionException) {
			try {
				System.out.println("::: In FileUploadFtpService ::  line:-88  inside try isHBConnectionException : "+isHBConnectionException);
				IGeneralParamEntry generalParamEntries;
				generalParamEntries = getFileUploadJdbc().getAppDate();
				applicationDate=new Date(generalParamEntries.getParamValue());
				System.out.println("::: In FileUploadFtpService ::  line:-92  applicationDate : "+applicationDate);
				
			} catch (Exception e) {
				System.out.println("::: In FileUploadFtpService ::  line:-95  inside catch() : ");
				e.printStackTrace();
			}
		}
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String checkDate=df.format(applicationDate);
		
		DateFormat Ramdf = new SimpleDateFormat("ddMMyyyy");
		String ramcheckDate=Ramdf.format(applicationDate);
		
		DateFormat dfde = new SimpleDateFormat("yyyyMMdd");
		String checkDateDE = dfde.format(applicationDate);
		
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient;
		DateFormat df1 = new SimpleDateFormat("yyyyMMdd");
		String fileDate=df1.format(applicationDate);
		//Start:Added by Uma Khot:for FD Flexcube CR
		System.out.println("downlodUploadedFiles system=>"+system);
	  if("FD".equals(system)){
		
		ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.FD_FILE_UPLOAD);
	   } //End:Added by Uma Khot:for FD Flexcube CR
	  else if("DEFERRAL_EXTENSION".equals(system)) {
		  ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.DEFERRAL_EXTENSION); 
	  }
	  else if("FILEDOWNLOAD_HRMS".equals(system)) {
		  System.out.println("System is FILEDOWNLOAD_HRMS inside if.");
		  
		  ftpClient = CMSFtpClient.getInstance("ofa",IFileUploadConstants.FILEDOWNLOAD_HRMS);
		  
		  System.out.println("System is FILEDOWNLOAD_HRMS inside if after connection gets.");
		  
	  }
	 else{
		 ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.SYSTEM_FILE_UPLOAD);
		}
		String remoteDir = "";
		String localDir = "";
		String remoteArchiveDir="";
		if(system.equals("UBS")){
		 remoteDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_UBS_REMOTE_DIR);
		 localDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_UBS_LOCAL_DIR);
		}else if(system.equals("FINWARE")){
			 remoteDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_FINWARE_REMOTE_DIR);
			 localDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_FINWARE_LOCAL_DIR);
		}else if(system.equals("HONGKONG")){
			 remoteDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_HONGKONG_REMOTE_DIR);
			 localDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_HONGKONG_LOCAL_DIR);
		}else if(system.equals("BAHRAIN")){
			 remoteDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_BAHRAIN_REMOTE_DIR);
			 localDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_BAHRAIN_LOCAL_DIR);
		}else if("FD".equals(system)){
		//	 remoteDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_FD_REMOTE_DIR);
			 String fixedRemoteFdFilePath = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_FD_REMOTE_DIR);
			 String folderName="";
			// Date date=new Date();
			 Calendar c=Calendar.getInstance();
			 c.setTime(date);
			 c.add(Calendar.DATE, -1);
			 int day=c.get(Calendar.DAY_OF_WEEK);
			 if(day==Calendar.SUNDAY){
				 folderName="sun";
			 }else  if(day==Calendar.MONDAY){
				 folderName="mon";
			 }else  if(day==Calendar.TUESDAY){
				 folderName="tue";
			 }else  if(day==Calendar.WEDNESDAY){
				 folderName="wed";
			 }else  if(day==Calendar.THURSDAY){
				 folderName="thu";
			 }else  if(day==Calendar.FRIDAY){
				 folderName="fri";
			 }else  if(day==Calendar.SATURDAY){
				 folderName="sat";
			 }
				 
			 remoteDir=fixedRemoteFdFilePath+folderName+"/reports/";
			 localDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_FD_LOCAL_DIR);
		}else if("EXCH".equals(system)){
			remoteDir= bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_EXCH_REMOTE_DIR);
			localDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_EXCH_LOCAL_DIR);
		}else if(system.equals("RAM_RATING")){
			 remoteDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_RAM_RATING_REMOTE_DIR);
			 localDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_RAM_RATING_LOCAL_DIR);
		}else if(system.equals("DEFERRAL_EXTENSION")){
			 remoteDir = bundle.getString(FTP_FILEUPLOAD_DOWNLOAD_DEFERRAL_EXTENSION_REMOTE_DIR);
			 localDir = bundle.getString(FTP_FILEUPLOAD_DEFERRAL_EXTENSION_LOCAL_DIR);
			 remoteArchiveDir=bundle.getString(FTP_FILEUPLOAD_DEFERRAL_EXTENSION_BACKUP_REMOTE_DIR);
		}else if(system.equals("FILEDOWNLOAD_HRMS")){
			 remoteDir = bundle.getString("ftp.hrms.download.remote.dir");
			 localDir = bundle.getString("ftp.hrms.download.local.dir");
			 remoteArchiveDir=bundle.getString("ftp.hrms.backup.local.dir");
		}
		
		DefaultLogger.debug(this, "remoteDir:"+remoteDir);
		DefaultLogger.debug(this, "localDir:"+localDir);
		
		DefaultLogger.debug(this, "executing openConnection()");
		ftpClient.openConnection();
		DefaultLogger.debug(this, "completed openConnection()");
		
		String DATA_FORMAT = "ddMMyyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATA_FORMAT);
		Date today = DateUtil.clearTime(new Date());
		String todaysDate = dateFormat.format(today);
		System.out.println("HRMS FILE name with todaysDate=>"+todaysDate);
		Vector<String> fileList = ftpClient.listFileInDir(remoteDir);
		if(fileList!=null&& fileList.size()>0){
			for(String fileName : fileList){
				
				DefaultLogger.debug(this, "fileName:"+fileName);
				if("UBS".equals(system) && ( //fileName.contains(FTP_UBS_FILE_NAME+checkDate) || 
						/*Uma:For UBS NCB Migration CR */	fileName.contains(FCCNCB_FILE_NAME+checkDate))
						&& (fileName.endsWith(".csv") ||  fileName.endsWith(".CSV")))
				{
					ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				}
				else if("FINWARE".equals(system) && fileName.contains(FTP_FINWARE_FILE_NAME+checkDate) 
						&& (fileName.endsWith(".csv") ||  fileName.endsWith(".CSV")))
				{
					ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				}
				else if("HONGKONG".equals(system) && fileName.contains(FTP_HONGKONG_FILE_NAME+checkDate) 
						&& (fileName.endsWith(".xls") ||  fileName.endsWith(".XLS") || fileName.endsWith(".xlsx") ||  fileName.endsWith(".XLSX") ))
				{
					ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				}
				else if("BAHRAIN".equals(system) && fileName.contains(FTP_BAHRAIN_FILE_NAME+checkDate) 
						&& (fileName.endsWith(".xls") ||  fileName.endsWith(".XLS") || fileName.endsWith(".xlsx") ||  fileName.endsWith(".XLSX") ))
				{
					ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				}
		/*		else if("FD".equals(system) && fileName.contains(FTP_FD_FILE_NAME+checkDate) 
						&& (fileName.endsWith(".csv") ||  fileName.endsWith(".CSV"))) */
				else if("FD".equals(system) && fileName.contains(bundle.getString(FTP_FD_FILE_NAME)))
				{
					ftpClient.downloadFile(localDir+ fileName+".csv", remoteDir+ fileName);
				}else if("RAM_RATING".equals(system) && fileName.equals("RAM_Rating_"+ramcheckDate+".xlsx") 
						&& (fileName.endsWith(".xls") ||  fileName.endsWith(".XLS") || fileName.endsWith(".xlsx") ||  fileName.endsWith(".XLSX") ))
				{
					ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				}
				else if("DEFERRAL_EXTENSION".equals(system) && fileName.contains(FTP_DEFERRAL_EXTENSION_FILE_NAME+checkDateDE))
				{
					ftpClient.downloadFileAndMove(localDir+fileName, remoteDir+fileName,remoteArchiveDir+fileName);
				}
				else if("EXCH".equals(system) && (fileName.contains(FTP_EXCH_FILE_NAME+fileDate))
						&& (fileName.endsWith(".csv") ||  fileName.endsWith(".CSV")))
				{
						ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				}
				
//				else if("FILEDOWNLOAD_HRMS".equals(system) && (fileName.contains("HRMS"))
				/*else if("FILEDOWNLOAD_HRMS".equals(system) && (fileName.contains("RMMASTER"))
						&& (fileName.endsWith(".csv") ||  fileName.endsWith(".CSV")))
				{
						ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				}*/
				else if("FILEDOWNLOAD_HRMS".equals(system) &&( (fileName.equals(FILENAME_HRMS_FORMAT+todaysDate+".csv")
						|| (fileName.equals(FILENAME_HRMS_FORMAT+todaysDate+".CSV")))))
						
				{
						ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				}
				//ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				//ftpClient.downloadFileAndMove(localDir+ fileName, remoteDir+ fileName, remoteArchiveDir+fileName);
			}
		}
		ftpClient.closeConnection();
		
	}
	public void deleteRemoteFiles() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.SYSTEM_FILE_UPLOAD);
		String remoteDir = bundle.getString(FTP_FILEUPLOAD_DEFERRAL_EXTENSION_BACKUP_REMOTE_DIR);
		String days = bundle.getString(FTP_DEFERRAL_EXTENSION_NOOFDAYS);
		
		long noofDays = 1;
		if(null!= days && !"".equalsIgnoreCase(days)){
			noofDays = Long.parseLong(days);
		}
		long eligibleForDeletion = System.currentTimeMillis()-(noofDays * 24 * 60 * 60 * 1000);
		
		ftpClient.openConnection();
		Vector<String> fileList = ftpClient.listFileInDir(remoteDir);
		if(fileList!=null&& fileList.size()>0){
			for(String fileName : fileList){
				
				/*if (fileName.lastModified() < eligibleForDeletion) {
					ftpClient.deleteFile(fileName);
				}*/
			}
		}
		ftpClient.closeConnection();
	}
	
	
	/*public void downlodClimsAckFilesToSync() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa");
		String remoteDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_ACK_DIR);
		String localDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_CLIMS_ACK_DIR);

		String remoteArchiveDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_ARCHIVE_DIR);
		
		ftpClient.openConnection();
		
		Vector<String> fileList = ftpClient.listFileInDir(remoteDir);
		if(fileList!=null&& fileList.size()>0){
			for(String fileName : fileList){
				ftpClient.downloadFileAndMove(localDir+ fileName, remoteDir+ fileName, remoteArchiveDir+fileName);
			}
		}
		ftpClient.closeConnection();
		
	}
	
	public void archiveAckFilesToSync() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa");
		String remoteAckDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_ACK_DIR);
		String localAckDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_ACK_DIR);

		String remoteArchiveDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_ARCHIVE_DIR);
		String localArchiveDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_ARCHIVE_DIR);
		// archive local ack file
		archiveLocalFiles(localAckDir, localArchiveDir);

		ftpClient.openConnection();
		// archive remote ack file
		Vector<String> fileList = ftpClient.listFileInDir(remoteAckDir);
		if(fileList!=null&& fileList.size()>0){
			for (String fileName : fileList) {
				ftpClient.downloadFileAndMove(localAckDir + fileName, remoteAckDir + fileName, remoteArchiveDir + fileName);
				deleteLocalFile(localAckDir + fileName);// clean local ack dir
			}
		}

		ftpClient.closeConnection();

	}
	
	
	public void archiveClimsUploadFile() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa");
		String remoteUploadDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_DIR);
		String localUploadDir = bundle.getString(FTP_MASTER_UPLOAD_LOCAL_DIR);
		String remoteArchiveDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_ARCHIVE_DIR);
		ftpClient.openConnection();
		Vector<String> fileList = ftpClient.listFileInDir(remoteUploadDir);
		if(fileList!=null&& fileList.size()>0){
			for (String fileName : fileList) {
				ftpClient.downloadFileAndMove(localUploadDir + fileName, remoteUploadDir + fileName, remoteArchiveDir + fileName);
				deleteLocalFile(localUploadDir + fileName);// clean local dir
			}
		}

		ftpClient.closeConnection();

	}
	
	
	public void cleanLocalUploadDir() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String localUploadDir = bundle.getString(FTP_MASTER_UPLOAD_LOCAL_DIR);
		cleanDirectory(localUploadDir);
	}
	public void cleanLocalDir(String syncDirection) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		if(SYNC_DIRECTION_CLIMSTOCPS.equals(syncDirection)){
			String localUploadDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_CLIMS_ACK_DIR);
			String localAckDir = bundle.getString(FTP_MASTER_UPLOAD_LOCAL_DIR);
			cleanDirectory(localUploadDir);
			cleanDirectory(localAckDir);
		}
		if(SYNC_DIRECTION_CPSTOCLIMS.equals(syncDirection)){
			String localUploadDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_DIR);
			String localAckDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_ACK_DIR);
			cleanDirectory(localUploadDir);
			cleanDirectory(localAckDir);
		}
	}

	private void cleanDirectory(String localUploadDir) throws IOException {
		File localDirFile = new File(localUploadDir);
		if(localDirFile.exists()){
			String[] localUploadDirFileList = localDirFile.list();
			if(localUploadDirFileList!=null&&localUploadDirFileList.length>0){
				for (String fileName : localUploadDirFileList) {
					deleteLocalFile(localUploadDir + fileName);// clean local Upload dir
				}
			}
		}
	}
	public void uploadAckFiles() throws Exception {

		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa");
		String remoteAckDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_ACK_DIR);
		String localAckDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_ACK_DIR);

		ftpClient.openConnection();
		File localDirFile = new File(localAckDir);
		String[] ackFileList = localDirFile.list();
		if(ackFileList!=null&& ackFileList.length>0){
			for (String fileName : ackFileList) {
				ftpClient.uploadFile(localAckDir + fileName, remoteAckDir + fileName);
			}
		}
		ftpClient.closeConnection();

	}*/
	
	public void uploadClimsMasterFiles() throws Exception{
		
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.SYSTEM_FILE_UPLOAD);
		String remoteDataDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_DIR);
		String localDataDir = bundle.getString(FTP_MASTER_UPLOAD_LOCAL_DIR);
		
		
		ftpClient.openConnection();
		File localDirFile = new File(localDataDir);
		String[] ackFileList = localDirFile.list();
		if(ackFileList!=null&& ackFileList.length>0){
			for(String fileName : ackFileList){
				ftpClient.uploadFile(localDataDir+ fileName, remoteDataDir+ fileName);
			}
		}
		ftpClient.closeConnection();
		
		
	}
	private void archiveLocalFiles(String localDir, String localArchiveDir) throws IOException {
		File localDirFile = new File(localDir);
		String[] fileList = localDirFile.list();
		if(fileList!=null&& fileList.length>0){
			for (int i = 0; i < fileList.length; i++) {
				moveFile(new File(localDir+fileList[i]), new File(localArchiveDir+fileList[i]));
			}
		}
	}
	private void deleteLocalFile(String localFilePath) throws IOException {
		File localFile = new File(localFilePath);
		if(localFile.exists()) {
			boolean delete = localFile.delete();
			if(delete==false) {
				System.out.println("file  deletion failed for file:"+localFile.getPath());	
			}
		}
	}

/*	public static void main(String[] args) throws Exception {
		FileUploadFtpService cmsFtpClient = new FileUploadFtpService();
		//cmsFtpClient.downlodMasterFilesToSync();
		cmsFtpClient.archiveAckFilesToSync();//For archive
		//cmsFtpClient.uploadAckFiles();//upload the files
	}*/
	
	/**
     * Moves a file.
     * <p>
     * When the destination file is on another file system, do a "copy and delete".
     *
     * @param srcFile the file to be moved
     * @param destFile the destination file
     * @throws NullPointerException if source or destination is {@code null}
     * @throws FileExistsException if the destination file exists
     * @throws IOException if source or destination is invalid
     * @throws IOException if an IO error occurs moving the file
     * @since 1.4
     */
    public static void moveFile(final File srcFile, final File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        }
        if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        }
        if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        }
        if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        }
       
        if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        }
        final boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
        	doCopyFile( srcFile, destFile,true );
            if (!srcFile.delete()) {
               
                throw new IOException("Failed to delete original file '" + srcFile +
                        "' after copy to '" + destFile + "'");
            }
        }
    }
    
    private static void doCopyFile(final File srcFile, final File destFile, final boolean preserveFileDate) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel input = null;
        FileChannel output = null;
        try {
            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(destFile);
            input  = fis.getChannel();
            output = fos.getChannel();
            final long size = input.size(); 
            long pos = 0;
            long count = 0;
            while (pos < size) {
                final long remain = size - pos;
                count = remain > 1048576 ? 1048576 : remain;
                final long bytesCopied = output.transferFrom(input, pos, count);
                if (bytesCopied == 0) { 
                    break; // ensure we don't loop forever
                }
                pos += bytesCopied;
            }
        } finally {
            closeQuietly(output, fos, input, fis);
        }

        final long srcLen = srcFile.length(); 
        final long dstLen = destFile.length(); 
        if (srcLen != dstLen) {
            throw new IOException("Failed to copy full contents from '" +
                    srcFile + "' to '" + destFile + "' Expected length: " + srcLen +" Actual: " + dstLen);
        }
        if (preserveFileDate) {
            boolean setLastModified = destFile.setLastModified(srcFile.lastModified());
            if(setLastModified==false) {
				System.out.println("Error while setting Last Modified time for file:"+destFile.getPath());	
			}
        }
    }
    
	public static void closeQuietly(final Closeable... closeables) {
		for (final Closeable closeable : closeables) {
			try {
				if (closeable != null) {
					closeable.close();
				}
			} catch (final IOException ioe) {
				// ignore
			}
		}
	}
}


