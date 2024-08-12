package com.integrosys.cms.batch.eod;

import com.integrosys.cms.app.poi.report.writer.FileFormat;

/**
 * This class contains the constants for Eod Sync.
 * 
 * @author ankit.limbadia
 * 
 */
public interface IPosidexFileGenConstants {

	public static String INTEGROSYS_POSIDEX_TEMPLATE_BASEPATH = "integrosys.posidex.template.basePath";

	public static String KEY_COL_LABEL = "columnsMap";
	public static String KEY_COL_FORMAT = "columnType";
	public static String KEY_COL_WIDTH = "columnWidths";
	public static String KEY_REPORT_NAME = "reportname";
	public static String KEY_DELIMETER = "delimiter";
	public static String KEY_SEC_DELIMETER = "secDelimiter";
	public static String KEY_PRINTHEADER_COLUMNNAME = "printheadercolumnname";
	public static String KEY_LOGO = "imagePath";
	public static String KEY_IS_POSIDEX_FILE = "isPosidexFile";
	public static String HDFC_LOGO = "integrosys.reports.bank.logo";
	public static String KEY_ADDITIONAL_HEADER = "additionalHeader";
	public static String KEY_ADDITIONAL_TRAILER = "additionalTrailer";
	

	public static final int textFormat = FileFormat.COL_TYPE_STRING;
	public static final int amountFormat = FileFormat.COL_TYPE_AMOUNT_FLOAT;

	public static final String FTP_POSIDEX_UPLOAD_LOCAL_DIR = "ftp.posidex.upload.local.dir";
	public static final String FTP_POSIDEX_UPLOAD_LOCAL_ARCHIVE_DIR = "ftp.posidex.upload.local.archive.dir";
	
	public static final String FTP_POSIDEX_UPLOAD_REMOTE_DIR = "ftp.posidex.upload.remote.dir";
//	public static final String FTP_POSIDEX_UPLOAD_REMOTE_ARCHIVE_DIR = "ftp.posidex.upload.remote.archive.dir";

}
