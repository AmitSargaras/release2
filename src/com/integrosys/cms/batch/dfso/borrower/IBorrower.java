package com.integrosys.cms.batch.dfso.borrower;

public interface IBorrower {

	public static final String FTP_DFSO_UPLOAD_LOCAL_DIR = "ftp.dfso.borrower.local.dir";
	public static final String FTP_DFSO_UPLOAD_REMOTE_DIR ="ftp.dfso.borrower.remote.dir";
	public static final String FTP_DFSO_UPLOAD_NOOFDAYS = "ftp.dfso.borrower.noofDays";
	public static final String FTP_DFSO_BACKUP_LOCAL_DIR = "ftp.borrower.backup.local.dir";
	
	public static final String DFSO_StockStatementDPYes_FILENAME = "Stock Statement DP_Yes";
	public static final String DFSO_StockStatementDPNo_FILENAME = "Stock Statement DP_No";
	public static final String DFSO_DiscrepencyDeferralFacility_FILENAME = "DiscrepencyDeferralFacility";
	public static final String DFSO_DiscrepencyDeferralGeneral_FILENAME = "DiscrepencyDeferralGeneral";
	
	public static final String DFSO_FILEDATEFORMAT = "yyyy-MM-dd";
	public static final String DFSO_FILEEXTENSION = ".csv";
	public static final String DFSO_DATEFORMAT = "dd-MM-yyyy";
	
	/*DFSO JOB*/
	public static final String FTP_DFSO_JOB_UPLOAD_LOCAL_DIR = "ftp.dfso.local.dir";
	public static final String FTP_DFSO_JOB_UPLOAD_REMOTE_DIR ="ftp.dfso.remote.dir";
	public static final String FTP_DFSO_JOB_UPLOAD_NOOFDAYS = "ftp.dfso.noofDays";
	public static final String FTP_DFSO_JOB_BACKUP_LOCAL_DIR = "ftp.dfso.backup.local.dir";
	public static final String DFSO_JOB_FILENAME = "CLIMS_DFSO_Report_";
	public static final String DFSO_JOB_FILEDATEFORMAT = "ddMMyyyy";
	public static final String DFSO_JOB_FILEEXTENSION = ".csv";
	
}
