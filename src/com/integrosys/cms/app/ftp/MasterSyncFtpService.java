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
import java.util.ResourceBundle;
import java.util.Vector;

import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.eod.IEodSyncConstants;

/**
 * @author bhushan.malankar
 *
 */
public class MasterSyncFtpService implements IEodSyncConstants{

	
	/**
	 * 
	 */
	public MasterSyncFtpService() {
		
	}
	
	public void downlodMasterFilesToSync() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.MASTER_SYNC_UP);
		String remoteDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_DIR);
		String localDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_DIR);
		//String remoteArchiveDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_ARCHIVE_DIR);
		//String localArchiveDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_ARCHIVE_DIR);
		
		//archiveLocalFiles(localDir,localArchiveDir);
		
		ftpClient.openConnection();
		Vector<String> fileList = ftpClient.listFileInDir(remoteDir);
		if(fileList!=null&& fileList.size()>0){
			for(String fileName : fileList){
				ftpClient.downloadFile(localDir+ fileName, remoteDir+ fileName);
				//ftpClient.downloadFileAndMove(localDir+ fileName, remoteDir+ fileName, remoteArchiveDir+fileName);
			}
		}
		ftpClient.closeConnection();
		
	}
	public void downlodClimsAckFilesToSync() throws Exception{
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.MASTER_SYNC_UP);
		String remoteDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_ACK_DIR);
		String localDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_CLIMS_ACK_DIR);

		String remoteArchiveDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_ARCHIVE_DIR);
		DefaultLogger.debug(this, "remoteArchiveDir:"+remoteArchiveDir+":localDir:"+localDir+":remoteDir:"+remoteDir);
		
		ftpClient.openConnection();
		DefaultLogger.debug(this, "downlodClimsAckFilesToSync():::FTP connection opened..");
		System.out.println("downlodClimsAckFilesToSync():::FTP connection opened..");
		
		Vector<String> fileList = ftpClient.listFileInDir(remoteDir);
		if(fileList!=null&& fileList.size()>0){
		DefaultLogger.debug(this, "File List Size::"+fileList.size());
			for(String fileName : fileList){
				DefaultLogger.debug(this, "File name :: "+fileName);
				ftpClient.downloadFileAndMove(localDir+ fileName, remoteDir+ fileName, remoteArchiveDir+fileName);
System.out.println("!!!!!!!!!!!!!!!!!!!!!!!! after downloadFileAndMove()");
			}
		}
		ftpClient.closeConnection();
		DefaultLogger.debug(this, "downlodClimsAckFilesToSync():::FTP connection closed..");
		
	}
	
	public void archiveAckFilesToSync() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.MASTER_SYNC_UP);
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
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.MASTER_SYNC_UP);
		String remoteUploadDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_DIR);
		String localUploadDir = bundle.getString(FTP_MASTER_UPLOAD_LOCAL_DIR);
		String remoteArchiveDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_ARCHIVE_DIR);
		ftpClient.openConnection();
		DefaultLogger.debug(this, "archiveClimsUploadFile(): FTP connection opened..");
		System.out.println("@@@@@@@ archiveClimsUploadFile(): FTP connection opened..");
		Vector<String> fileList = ftpClient.listFileInDir(remoteUploadDir);
		if(fileList!=null&& fileList.size()>0){
		DefaultLogger.debug(this, "File List Size::"+fileList.size());
			for (String fileName : fileList) {
				DefaultLogger.debug(this, "fileName::"+fileName);
				System.out.println("fileName::"+fileName);
				ftpClient.downloadFileAndMove(localUploadDir + fileName, remoteUploadDir + fileName, remoteArchiveDir + fileName);
				System.out.println("after downloadFileAndMove()");
				deleteLocalFile(localUploadDir + fileName);// clean local dir
System.out.println("sssssssssssssssssss after deleteLocalFile()");
			}
		}

		ftpClient.closeConnection();
		DefaultLogger.debug(this, "archiveClimsUploadFile():::FTP connection closed..");
	}
	
	
	public void cleanLocalUploadDir() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String localUploadDir = bundle.getString(FTP_MASTER_UPLOAD_LOCAL_DIR);
		cleanDirectory(localUploadDir);
	}
	public void cleanLocalDir(String syncDirection) throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		DefaultLogger.debug(this, "syncDirection:::"+syncDirection);
		if(SYNC_DIRECTION_CLIMSTOCPS.equals(syncDirection)){
			String localUploadDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_CLIMS_ACK_DIR);
			String localAckDir = bundle.getString(FTP_MASTER_UPLOAD_LOCAL_DIR);
			DefaultLogger.debug(this, "SYNC_DIRECTION_CLIMSTOCPS>>>localAckDir:::"+localAckDir+"::localUploadDir:"+localUploadDir);
		System.out.println("SYNC_DIRECTION_CLIMSTOCPS>>>localAckDir:::"+localAckDir+"::localUploadDir:"+localUploadDir);
			cleanDirectory(localUploadDir);
			cleanDirectory(localAckDir);
		}
		if(SYNC_DIRECTION_CPSTOCLIMS.equals(syncDirection)){
			String localUploadDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_DIR);
			String localAckDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_ACK_DIR);
			DefaultLogger.debug(this, "SYNC_DIRECTION_CPSTOCLIMS>>>localAckDir:::"+localAckDir+"::localUploadDir:"+localUploadDir);
System.out.println("SYNC_DIRECTION_CPSTOCLIMS>>>localAckDir:::"+localAckDir+"::localUploadDir:"+localUploadDir);
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
	public void uploadAckFiles() throws Exception  {

		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.MASTER_SYNC_UP);
		String remoteAckDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_ACK_DIR);
		String localAckDir = bundle.getString(FTP_MASTER_DOWNLOAD_LOCAL_ACK_DIR);

		try {
			ftpClient.openConnection();
		} catch (Exception e) {
			DefaultLogger.error(this, "Error opening ftp connection ", e);
			throw new Exception(e);
		}
		File localDirFile = new File(localAckDir);
		String[] ackFileList = localDirFile.list();
		if(ackFileList!=null&& ackFileList.length>0){
			for (String fileName : ackFileList) {
				try {
					ftpClient.uploadFile(localAckDir + fileName, remoteAckDir + fileName);
				} catch (Exception e) {
					DefaultLogger.error(this, "Error uploading file in Dir: "+localAckDir + fileName+", AckDir: "+ remoteAckDir, e);
					throw new Exception(e);
				}
			}
		}
		ftpClient.closeConnection();

	}
	
	public void uploadClimsMasterFiles() throws Exception{
		DefaultLogger.debug(this,"inside method uploadClimsMasterFiles() ");
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.MASTER_SYNC_UP);
		String remoteDataDir = bundle.getString(FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_DIR);
		String localDataDir = bundle.getString(FTP_MASTER_UPLOAD_LOCAL_DIR);
		
		
		ftpClient.openConnection();
		DefaultLogger.debug(this, "uploadClimsMasterFiles():FTP connection opened() ");
		File localDirFile = new File(localDataDir);
		String[] ackFileList = localDirFile.list();
		if(ackFileList!=null&& ackFileList.length>0){
			for(String fileName : ackFileList){
				ftpClient.uploadFile(localDataDir+ fileName, remoteDataDir+ fileName);
			}
		}
		ftpClient.closeConnection();
		DefaultLogger.debug(this, "uploadClimsMasterFiles():FTP connection closed() ");
		
		
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

	public static void main(String[] args) throws Exception {
		MasterSyncFtpService cmsFtpClient = new MasterSyncFtpService();
		//cmsFtpClient.downlodMasterFilesToSync();
		cmsFtpClient.archiveAckFilesToSync();//For archive
		//cmsFtpClient.uploadAckFiles();//upload the files
	}
	
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


