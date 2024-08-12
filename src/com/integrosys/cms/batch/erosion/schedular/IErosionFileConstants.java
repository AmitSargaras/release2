package com.integrosys.cms.batch.erosion.schedular;

public interface IErosionFileConstants {
	
	public static final String FTP_EROSION_UPLOAD_LOCAL_DIR = "ftp.erosion.upload.local.dir";
	public static final String FTP_EROSION_BACKUP_LOCAL_DIR = "ftp.erosion.backup.local.dir";
	
	public static final String EROSION_FACILITY_FILENAME = "CLIMS TRAQ Provision Report (Customer- Facility)_";
	public static final String EROSION_SECURITY_FILENAME = "CLIMS TRAQ Provision Report (Customer- Security)_";
	public static final String EROSION_FACILITY_WISE_FILENAME = "CLIMS TRAQ Provision Report (Facility wise erosion report)_";
	public static final String EROSION_PARTY_WISE_FILENAME = "CLIMS TRAQ Provision Report (Party wise erosion report)_";
	
	
	public static final String EROSION_FILEDATEFORMAT = "ddMMyyyy";
	public static final String EROSION_DATEFORMAT = "dd-MM-yyyy";
	public static final String EROSION_FILEEXTENSION = ".csv";
	

	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_PASS = "PASS";
	public static final String STATUS_FAIL = "FAIL";
	public static final String STATUS_DONE = "DONE";
	public static final String STATUS_SKIPPED = "N/A (Monthly Activity)";
	public static final String STATUS_NA = "N/A";
	
	public static final String EXECREATEDATAFACILITYRPT="Create Data - Facility Report";
	public static final String EXECREATEDATASECURITYRPT="Create Data - Security Report";
	public static final String EXECREATEDATAFACWISEEROSIONRPT="Create Data - Facility wise Erosion % Report";
	public static final String EXECREATEDATAPARTYWISEEROSIONRPT="Create Data - Party wise Erosion % Report";
	
	public static final String EXEGENERATEFILEFACILITYRPT="Generate File - Facility Report";
	public static final String EXEGENERATEFILESECURITYRPT="Generate File - Security Report";
	public static final String EXEGENERATEFILEFACWISEEROSIONRPT="Generate File - Facility wise Erosion % Report";
	public static final String EXEGENERATEFILEPARTYWISEEROSIONRPT="Generate File - Party wise Erosion % Report";
	
	public static final String EXEUPDATEEROSIONFORNPATRAQFILE="Update Erosion % for NPA TRAQ file";
	
	public static final String TEMPLATEFACILITY="EROSION01";
	public static final String TEMPLATESECURITY="EROSION02";
	public static final String TEMPLATEFACILITYWISEEROSION="EROSION03";
	public static final String TEMPLATEPARTYWISEEROSION="EROSION04";
	



}
