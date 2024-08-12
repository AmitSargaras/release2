package com.integrosys.cms.batch.fcubsLimitFile.schedular;



/**
 * This class contains the constants for FCUBS
 * 
 * @author ankit.limbadia
 * 
 */
public interface FCUBSFileConstants {

	
	public static final String FTP_FCUBS_UPLOAD_LOCAL_DIR = "ftp.fcubslimit.upload.local.dir";
	public static final String FTP_FCUBS_UPLOAD_REMOTE_DIR = "ftp.fcubslimit.upload.remote.dir";
	public static final String FTP_FCUBS_UPLOAD_NOOFDAYS = "ftp.fcubs.upload.noofDays";
	public static final String FTP_FCUBS_BACKUP_LOCAL_DIR = "ftp.fcubslimit.backup.local.dir";
	public static final String FTP_FCUBS_DOWNLOAD_LOCAL_DIR = "ftp.fcubslimit.download.local.dir";
	public static final String FTP_FCUBS_DOWNLOAD_REMOTE_DIR = "ftp.fcubslimit.download.remote.dir";
	public static final String FCUBS_FILENAME = "LIMITSTP";
	public static final String FCUBS_DATEFORMAT = "MM/dd/yyyy";
	public static final String FCUBS_FILEDATEFORMAT = "yyyyMMdd";
	public static final String FCUBS_FILESTATICNAME = "_CAD";
	public static final String FCUBS_FILEEXTENSION = ".csv";
	public static final String FCUBS_FILEDESCKEY = "FACILITY_MASTER";
	public static final String FCUBS_FILEBRANCHKEY = "FACILITY_BRANCH";
	public static final String FCUBS_FILECUSTOMERKEY = "FACILITY_CUSTOMER";
	public static final String FCUBS_FILECURRENCYKEY = "FACILITY_CCY";
	public static final String FCUBS_FILEPRODUCTKEY = "FACILITY_PRODUCT";
	public static final String FCUBS_FILEUDFKEY = "FACILITY_UDF";
	public static final String FCUBS_FILECOBORROWERKEY = "FACILITY_COBORROWER";

	public static final String FCUBS_SEGMENTUDF = "SEGMENT1";
	public static final String FCUBS_UNCONDUDF = "UNCONDI CANCL COMMITMENT";
	public static final String FCUBS_REALESTATEUDF = "REAL ESTATE";
	public static final String FCUBS_PSLFLAGUDF = "PSL FLAG";
	public static final String FCUBS_CAPITALMARKETUDF = "CAPITAL MARKET EXPOSURE";
	public static final String FCUBS_COMMERCIAL = "COMMERCIAL";
	public static final String FCUBS_COMREALESTATE = "Commercial Real estate";
	public static final String FCUBS_INDIRECTFINANCE = "INDIRECT FINANCE";
	public static final String FCUBS_INDIRECT = "Indirect real estate";
	public static final String FCUBS_RESDREALESTATE = "Residential real Estate";
	public static final String FCUBS_RESIDENTIAL = "RESIDENTIAL";
	public static final String FCUBS_CBOP = "CLIMS";
	public static final String FCUBS_FILESUCCESSNAME = "_SUCCESS.txt";
	public static final String FCUBS_FILEERRORNAME = "_ERROR.txt";
	public static final String FCUBS_FILESPOOLDATE = "Spool Date";
	public static final String FCUBS_FILESPOOLDATEHEADING = "Spool Date :";
	public static final String ACK_ACTION = "N";
	public static final String FCUBS_CREUDF = "CRE";
	public static final String FCUBS_STOCK = "STOCK";
	public static final String FCUBS_FILEUDFCOV         = "FACILITY_COVENANT";
	public static final String FCUBS_COUNTRYREST        = "COUNTRY RESTRICTION";
	public static final String FCUBS_DRAWERNAME         = "DRAWERNAME";
	public static final String FCUBS_DRAWEENAME         = "DRAWEENAME";
	public static final String FCUBS_BENENAME           = "BENENAME";
	public static final String FCUBS_MAXCOMBINEDTENOR   = "MAX COMBINED TENOR";
	public static final String FCUBS_RUNNINGACCOUNT     = "RUNNING ACCOUNT";
	public static final String FCUBS_SELLDOWN           = "SELL DOWN";
	public static final String FCUBS_LASTAVAILABLEDATE  = "LAST AVAILABLE DATE";
	public static final String FCUBS_MORATORIUMPERIOD   = "MORATORIUM PERIOD";
	public static final String FCUBS_RESTRICTEDGOODSCODE= "GOODS RESTRICTION";
	public static final String FCUBS_RESTRICTEDCURRENCY = "CURRENCY RESTRICTION";
	public static final String FCUBS_RESTRICTEDBANK     = "BANK RESTRICTION";
	public static final String FCUBS_BUYERSRATING       = "BUYERS RATING";
	public static final String FCUBS_ECGCCOVER          = "ECGC COVER"; 
	public static final String FCUBS_RESTRICTEDAMOUNT   = "RESTRICTED AMOUNT";
	public static final String FCUBS_DRAWERAMOUNT       = "DRAWER AMOUNT";
	public static final String FCUBS_DRAWEEAMOUNT       = "DRAWEE AMOUNT"; 
	public static final String FCUBS_BENEAMOUNT         = "BENEAMOUNT";
	public static final String FCUBS_PRESHIPMENTLINKAGE = "PRE SHIPMENT LINKAGE";
	public static final String FCUBS_ORDERBACKEDBYLC    = "ORDER BACKED BY LC";
	public static final String FCUBS_EMIFREQUENCY       = "EMI FREQUENCY";
	public static final String FCUBS_AGENCYMASTER       = "AGENCY MASTER";
	public static final String FCUBS_DRAWERCUSTID       = "DRAWER CUST ID";
	public static final String FCUBS_DRAWEECUSTID       = "DRAWEE CUST ID";
	public static final String FCUBS_BENECUSTID         = "BENE CUST ID";
	public static final String FCUBS_POSTSHIPMENTLINKAGE= "POST SHIPMENT LINKAGE";
	public static final String FCUBS_INCOTERM           = "INCO TERM";
	public static final String FCUBS_NOOFINSTALLMENTS   = "NO OF INSTALLMENTS";
	public static final String FCUBS_RATINGMASTER       = "RATING MASTER";
	public static final String FCUBS_DRAWERCUSTNAME     = "DRAWER CUST NAME";
	public static final String FCUBS_DRAWEECUSTNAME     = "DRAWEE CUST NAME";
	public static final String FCUBS_BENECUSTNAME       = "BENE CUST NAME";
	public static final String FCUBS_MODULECODE         = "MODULE CODE";
	
	String FCUBS_FILE_COVENANT_SINGLE = "FACILITY_COVENANT";
	String FCUBS_FILE_COVENANT_DRAWER = "Drawer";
	String FCUBS_FILE_COVENANT_DRAWEE = "Drawee";
	String FCUBS_FILE_COVENANT_BENEFICIARY = "Beneficiary";
	String FCUBS_FILE_COVENANT_GOODS_RESTRICTION = "HSN_RESTRICTION";
	String FCUBS_FILE_COVENANT_CURRENCY_RESTRICTION = "FACILITY_CCY";
	String FCUBS_FILE_COVENANT_COUNTRY_RESTRICTION = "COUNTRY_RESTRICTION";
	String FCUBS_FILE_COVENANT_BANK_RESTRICTION = "FACILITY_BANK_CUSTOMER";
	String FCUBS_FILE_COVENANT_FACILITY_OTHER_PARTY = "FACILITY_OTHER_PARTY";
	String FCUBS_FILE_COVENANT_FACILITY_INCOTERM = "FACILITY_INCOTERM";
}
