package com.integrosys.cms.app.ftp;

import com.integrosys.cms.app.poi.report.writer.FileFormat;

/**
 * This class contains the constants for Eod Sync.
 * 
 * @author anil.pandey
 * 
 */
public interface IFileUploadConstants {

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

	public static final String FTP_MASTER_UPLOAD_LOCAL_DIR = "ftp.master.upload.local.dir";
	public static final String FTP_MASTER_DOWNLOAD_REMOTE_CLIMS_DIR = "ftp.master.download.remote.clims.dir";

	public static final String FTP_FILEUPLOAD_DOWNLOAD_UBS_LOCAL_DIR = "ftp.master.download.ubs.local.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_FINWARE_LOCAL_DIR = "ftp.master.download.finware.local.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_HONGKONG_LOCAL_DIR = "ftp.master.download.hongkong.local.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_BAHRAIN_LOCAL_DIR = "ftp.master.download.bahrain.local.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_RAM_RATING_LOCAL_DIR = "ftp.download.ramrating.local.dir";
	
	public static final String FTP_FILEUPLOAD_DOWNLOAD_UBS_REMOTE_DIR = "ftp.master.download.ubs.remote.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_FINWARE_REMOTE_DIR = "ftp.master.download.finware.remote.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_HONGKONG_REMOTE_DIR = "ftp.master.download.hongkong.remote.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_BAHRAIN_REMOTE_DIR = "ftp.master.download.bahrain.remote.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_RAM_RATING_REMOTE_DIR = "ftp.download.ramrating.remote.dir";
	
	public static final String FTP_FILEUPLOAD_DOWNLOAD_GENERATEDXLS_LOCAL_DIR ="ftp.fileupload.download.generatedxls.local.dir";
	
	public static final String FTP_UBS_FILE_NAME = "UBS-LIMIT_";
	public static final String FTP_FINWARE_FILE_NAME = "FC-LIMITS_";
	public static final String FTP_HONGKONG_FILE_NAME = "HONGKONG-LIMITS_";
	public static final String FTP_BAHRAIN_FILE_NAME = "BAHRAIN-LIMITS_";
	// Added By Dayananda Laishram for Basel Report Generation on 12-May-2015 BASEL_REPORT_ON_EOD
	public static final String BASEL_UPLOAD_FILE_NAME = "BASEL-UPLOAD_";
	public static final String FILEUPLOAD_UBS_TRANS_SUBTYPE = "UBS_UPLOAD";
	public static final String FILEUPLOAD_FINWARE_TRANS_SUBTYPE = "FINWARE_UPLOAD";
	public static final String FILEUPLOAD_HONGKONG_TRANS_SUBTYPE = "HONGKONG_UPLOAD";
	public static final String FILEUPLOAD_BAHRAIN_TRANS_SUBTYPE = "BAHRAIN_UPLOAD";
	public static final String FILEUPLOAD_ACKNOWLEDGMENT_TRANS_SUBTYPE = "ACK_UPLOAD";
	public static final String FILEUPLOAD_LEIDETAILS_TRANS_SUBTYPE = "LEI_DETAILS_UPLOAD";
	public static final String FILEUPLOAD_RELEASELINEDETAILS_TRANS_SUBTYPE = "RLD_UPLOAD";
	public static final String FILEUPLOAD_FACILITYDETAILS_TRANS_SUBTYPE = "Facility Update Upload";
	public static final String FILEUPLOAD_AUTOUPDATIONLMTS_TRANS_SUBTYPE = "AUTO_UPLOAD";
	

	
	//Added for FD Upload
	public static final String FTP_FILEUPLOAD_DOWNLOAD_FD_LOCAL_DIR = "ftp.master.download.fd.local.dir";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_EXCH_LOCAL_DIR = "ftp.master.download.exchangeRate.local.dir";
	public static final String FILEUPLOAD_FD_TRANS_SUBTYPE = "FD_UPLOAD";
	//public static final String FTP_FD_FILE_NAME = "FIXEDDEPOSIT-LIMITS_";
	public static final String FTP_FD_FILE_NAME = "ftp.fd.upload.filename";
	public static final String FTP_RAM_RATING_FILE_NAME = "ftp.ramrating.upload.filename";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_FD_REMOTE_DIR = "ftp.master.download.fd.remote.dir";
	public static final String FTP_EXCH_FILE_NAME = "EXCHANGERATE_TRRACS_";
	public static final String FTP_FILEUPLOAD_DOWNLOAD_EXCH_REMOTE_DIR = "ftp.master.download.exchangeRate.remote.dir";
	
	//Uma:For UBS NCB Migration CR
	public static final String FCCNCB_FILE_NAME = "FCCNCB-LIMIT_";
	
	public static final String FILEUPLOAD_PARTYCAM_TRANS_SUBTYPE = "PARTYCAM_UPLOAD";
	public static final String FILEUPLOAD_BULKUDF_TRANS_SUBTYPE ="BULK_UDF_UPLOAD";
	
	public static final String FILEUPLOAD_UBS = "UBS";
	public static final String FILEUPLOAD_FINWARE = "FINWARE";
	public static final String FILEUPLOAD_HONGKONG = "HONGKONG";
	public static final String FILEUPLOAD_BAHRAIN = "BAHRAIN";
	public static final String FILEUPLOAD_FD = "FD";
	
	public static final String FILEUPLOAD_UBS_LIMITS = "UBS-LIMITS";

	public static final String SYSTEM_UPLOAD = "SYSTEM_UPLOAD";
	//RAM RATING CR
	public static final String FTP_FILEUPLOAD_DOWNLOAD_DEFERRAL_EXTENSION_REMOTE_DIR = "ftp.master.download.deferral.extension.remote.dir";
	public static final String FTP_FILEUPLOAD_DEFERRAL_EXTENSION_LOCAL_DIR = "ftp.master.deferral.extension.local.dir";
	public static final String FTP_DEFERRAL_EXTENSION_FILE_NAME = "DEFERRAL_EXTENSION_";
	public static final String FTP_FILEUPLOAD_DEFERRAL_EXTENSION_BACKUP_REMOTE_DIR = "ftp.master.deferral.extension.backup.remote.dir";
	public static final String FTP_DEFERRAL_EXTENSION_NOOFDAYS = "ftp.master.deferral.extension.noofDays";
	
	public static final String FILEDOWNLOAD_HRMS = "FILEDOWNLOAD_HRMS";
	public static final String FTP_FILEDOWNLOAD_HRMS_LOCAL_DIR = "ftp.master.download.hrms.local.dir";
	public static final String FTP_HRMS_FILE_NAME = "ftp.hrms.upload.filename";
	public static final String FILEDOWNLOAD_HRMS_TRANS_SUBTYPE = "FILEDOWNLOAD_HRMS";
	public static final String FILENAME_HRMS_FORMAT = "CLIMS_RMMASTER_";

}