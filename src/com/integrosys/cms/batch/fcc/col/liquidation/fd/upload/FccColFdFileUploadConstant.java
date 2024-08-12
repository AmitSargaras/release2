package com.integrosys.cms.batch.fcc.col.liquidation.fd.upload;

public interface FccColFdFileUploadConstant {

	String FILE_NAME_PREFIX_BH = "CLIMS_FD_BH";
	
	String FILE_NAME_PREFIX_GC = "CLIMS_FD_GC";
	
	String FILE_NAME_PREFIX_HK = "CLIMS_FD_HK";
	
//	String FILE_NAME_PREFIX = "CLIMS_SBBG_SBLC_WMS";
	
	String FILE_NAME_POSTFIX = "txt";
	
	String FTP_UPLOAD_LOCAL_DIR = "ftp.fcc.col.liquidation.upload.local.dir";
	
	String FTP_UPLOAD_REMOTE_DIR = "ftp.fcc.col.liquidation.upload.remote.dir";
	
	String FTP_DOWNLOAD_LOCAL_DIR = "ftp.fcc.col.liquidation.upload.local.dir";
	
	String FTP_DOWNLOAD_REMOTE_DIR = "ftp.fcc.col.liquidation.download.remote.dir";
	
	String FTP_ANONYMOUS = "ftp.fcc.col.liquidation.upload.anonymous";
	
	String FTP_USERNAME = "ftp.fcc.col.liquidation.upload.username";
	
	String FTP_PASSWORD = "ftp.fcc.col.liquidation.upload.password";
	
	String FTP_SERVER_URL = "ftp.fcc.col.liquidation.upload.server";
	
	String FTP_SERVER_PORT = "ftp.fcc.col.liquidation.upload.port";
	
	String CONNECTION_TYPE = "ftp.fcc.col.liquidation.upload.connection.type";
	
	String SERVER_CONFIGURED = "ftp.fcc.col.liquidation.upload.server.name";
	
	String SEPARATOR = "|";
	
	String FTP_BACKUP_LOCAL_DIR = "ftp.fcc.col.liquidation.backup.local.dir";
	
	public static final String FCC_COL_FILENAME_SUFFIX = "_REJECT.txt";
	
//	String DATA_FORMAT = "ddMMyyyy";
	
	String DATA_FORMAT = "yyyyMMdd";
	
	String SERVER_NAME = "app1";
	
	String FTP_FCC_COL_LIQ_SYSTEM_NAMES ="ftp.fcc.col.liquidation.system.names";
	
	String FILE_STATUS = "File updated successfully.";
	String FILE_STATUS_FAILED = "File SFTP failed.";
	
	public static final String FTP_PSR_UPLOAD_LOCAL_DIR = "ftp.psr.upload.local.dir";
	public static final String FTP_PSR_UPLOAD_REMOTE_DIR = "ftp.psr.upload.remote.dir";
	public static final String FTP_PSR_BACKUP_LOCAL_DIR = "ftp.psr.backup.local.dir";
	public static final String FTP_PSR_DOWNLOAD_REMOTE_DIR = "ftp.psr.download.remote.dir";
	public static final String FTP_PSR_DOWNLOAD_LOCAL_DIR = "ftp.psr.download.local.dir";
	
	public static final String FTP_PSR_UPLOAD_NOOFDAYS = "ftp.psr.upload.noofDays";
	public static final String PSR_FILENAME = "CLIMSPSR";
	public static final String PSR_FILEDATEFORMAT = "yyyyMMdd";
	public static final String PSR_FILEEXTENSION = ".csv";
	public static final String PSR_DATEFORMAT = "dd-MM-yyyy";
	public static final String PSR_FILESUCCESSNAME = "_Success.txt";
	public static final String PSR_FILEERRORNAME = "_Fail.txt";
	
	public static final String PSR_FILESPOOLDATE = "Spool Date";
	public static final String PSR_FILESPOOLDATEHEADING = "Spool Date :";
	public static final String ACK_ACTION = "N";
}
