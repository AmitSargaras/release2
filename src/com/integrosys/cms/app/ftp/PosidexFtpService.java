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
import com.integrosys.cms.batch.eod.IPosidexFileGenConstants;

/**
 * @author ankit.limbadia
 *
 */
public class PosidexFtpService implements IPosidexFileGenConstants{
	/**
	 * 
	 */
	public PosidexFtpService() {
		
	}
	
	public void archiveClimsUploadFile() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.POSIDEX_FILE_UPLOAD);
		String remoteUploadDir = bundle.getString(FTP_POSIDEX_UPLOAD_REMOTE_DIR);
		String localUploadDir = bundle.getString(FTP_POSIDEX_UPLOAD_LOCAL_DIR);
//		String remoteArchiveDir = bundle.getString(FTP_POSIDEX_UPLOAD_REMOTE_ARCHIVE_DIR);
		String localArchiveDir = bundle.getString(FTP_POSIDEX_UPLOAD_LOCAL_ARCHIVE_DIR);
		ftpClient.openConnection();
		DefaultLogger.debug(this, "archive Posidex UploadFile(): FTP connection opened..");
		Vector<String> fileList = ftpClient.listFileInDir(remoteUploadDir);
		if(fileList!=null&& fileList.size()>0){
		DefaultLogger.debug(this, "File List Size::"+fileList.size());
			for (String fileName : fileList) {
				DefaultLogger.debug(this, "fileName::"+fileName);
				ftpClient.downloadFileToLocalAndDeleteRemoteFile(localUploadDir + fileName, remoteUploadDir + fileName);
				DefaultLogger.debug(this, "copyLocalFile to Local Archival Directory:::"+localUploadDir + fileName);
				doCopyFile(new File(localUploadDir + fileName),new File(localArchiveDir+fileName), true);
				DefaultLogger.debug(this, "Post copying file to local Archieval Directory:::"+localArchiveDir+fileName);
				deleteLocalFile(localUploadDir + fileName);// clean local dir
			}
		}

		ftpClient.closeConnection();
		DefaultLogger.debug(this, "archive Posidex UploadFile():::FTP connection closed..");
	}
	
	
	/*public void cleanLocalUploadDir() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String localUploadDir = bundle.getString(FTP_POSIDEX_UPLOAD_LOCAL_DIR);
		cleanDirectory(localUploadDir);
	}*/
	public void cleanLocalDir() throws Exception {
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		String localUploadDir = bundle.getString(FTP_POSIDEX_UPLOAD_LOCAL_DIR);
		String localAckDir = bundle.getString(FTP_POSIDEX_UPLOAD_LOCAL_ARCHIVE_DIR);
		DefaultLogger.debug(this, "POSIDEX >>> localAckDir:::"+localAckDir+"::localUploadDir:"+localUploadDir);
		cleanDirectory(localUploadDir);
		cleanDirectory(localAckDir);
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
	
	public void uploadPosidexFileToFTP() throws Exception{
		DefaultLogger.debug(this,"inside method uploadPosidexFileToFTP() ");
		ResourceBundle bundle = ResourceBundle.getBundle("ofa");
		CMSFtpClient ftpClient = CMSFtpClient.getInstance("ofa",ICMSConstant.POSIDEX_FILE_UPLOAD);
		String remoteDataDir = bundle.getString(FTP_POSIDEX_UPLOAD_REMOTE_DIR);
		String localDataDir = bundle.getString(FTP_POSIDEX_UPLOAD_LOCAL_DIR);
		
		ftpClient.openConnection();
		DefaultLogger.debug(this, "uploadPosidexFileToFTP():FTP connection opened() ");
		File localDirFile = new File(localDataDir);
		String[] ackFileList = localDirFile.list();
		if(ackFileList!=null&& ackFileList.length>0){
			for(String fileName : ackFileList){
				ftpClient.uploadFile(localDataDir+ fileName, remoteDataDir+ fileName);
			}
		}
		ftpClient.closeConnection();
		DefaultLogger.debug(this, "uploadPosidexFileToFTP():FTP connection closed() ");
		
		
	}
	/*private void archiveLocalFiles(String localDir, String localArchiveDir) throws IOException {
		File localDirFile = new File(localDir);
		String[] fileList = localDirFile.list();
		if(fileList!=null&& fileList.length>0){
			for (int i = 0; i < fileList.length; i++) {
				moveFile(new File(localDir+fileList[i]), new File(localArchiveDir+fileList[i]));
			}
		}
	}*/
	private void deleteLocalFile(String localFilePath) throws IOException {
		File localFile = new File(localFilePath);
		if(localFile.exists()) {
			boolean delete = localFile.delete();
			if(delete==false) {
				System.out.println("file  deletion failed for file:"+localFile.getPath());	
			}
		}
	}

	/*public static void main(String[] args) throws Exception {
		PosidexFtpService cmsFtpClient = new PosidexFtpService();
		//cmsFtpClient.downlodMasterFilesToSync();
//		cmsFtpClient.archiveAckFilesToSync();//For archive
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


