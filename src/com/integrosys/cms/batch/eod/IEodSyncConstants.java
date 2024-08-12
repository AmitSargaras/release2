package com.integrosys.cms.batch.eod;

import com.integrosys.cms.app.poi.report.writer.FileFormat;

/**
 * This class contains the constants for Eod Sync.
 * 
 * @author anil.pandey
 * 
 */
public interface IEodSyncConstants {

	public static String EOD_SYNC_CLIMSTOCPS_TEMPLATE_BASE_PATH = "integrosys.eod.sync.clims.to.cps.template.basePath";
	public static String EOD_SYNC_CPSTOCLIMS_TEMPLATE_BASE_PATH = "integrosys.eod.sync.cps.to.clims.template.basePath";
	public static String EOD_SYNC_CLIMSTOCPS_ACK_TEMPLATE_BASE_PATH = "integrosys.eod.sync.clims.to.cps.ack.template.basePath";

	public static String KEY_COL_LABEL = "columnsMap";
	public static String KEY_COL_FORMAT = "columnType";
	public static String KEY_COL_WIDTH = "columnWidths";
	public static String KEY_REPORT_NAME = "reportname";
	public static String KEY_DELIMETER = "delimiter";
	public static String KEY_SEC_DELIMETER = "secDelimiter";
	public static String KEY_PRINTHEADER_COLUMNNAME = "printheadercolumnname";
	public static String KEY_LOGO = "imagePath";
	public static String KEY_IS_EOD_REPORT = "isEodReport";
	public static String HDFC_LOGO = "integrosys.reports.bank.logo";
	

	public static final int textFormat = FileFormat.COL_TYPE_STRING;
	public static final int amountFormat = FileFormat.COL_TYPE_AMOUNT_FLOAT;

	public static final String SYNC_DIRECTION_CLIMSTOCPS = "CLIMSTOCPS";
	public static final String SYNC_DIRECTION_CPSTOCLIMS = "CPSTOCLIMS";

	public static final String FTP_MASTER_UPLOAD_LOCAL_DIR = "ftp.master.upload.local.dir";
	public static final String FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_DIR = "ftp.master.download.remote.clims.dir";

	public static final String FTP_MASTER_DOWNLOAD_LOCAL_DIR = "ftp.master.download.local.dir";
	public static final String FTP_MASTER_DOWNLOAD_LOCAL_ACK_DIR = "ftp.master.download.local.ack.dir";
	public static final String FTP_MASTER_DOWNLOAD_LOCAL_ARCHIVE_DIR = "ftp.master.download.local.archive.dir";

	public static final String FTP_MASTER_DOWNLOAD_REMOTE_DIR = "ftp.master.download.remote.dir";
	public static final String FTP_MASTER_DOWNLOAD_REMOTE_ACK_DIR = "ftp.master.download.remote.ack.dir";
	public static final String FTP_MASTER_DOWNLOAD_REMOTE_ARCHIVE_DIR = "ftp.master.download.remote.archive.dir";

	public static final String FTP_MASTER_DOWNLOAD_LOCAL_CLIMS_ACK_DIR = "ftp.master.download.local.clims.ack.dir";
	public static final String FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_ACK_DIR = "ftp.master.download.remote.clims.ack.dir";
	public static final String FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_ARCHIVE_DIR = "ftp.master.download.remote.clims.archive.dir";	
	
}
