package com.integrosys.cms.batch.eod;

public class EODConstants {
	public static final String STATUS_PENDING = "PENDING";
	public static final String STATUS_PASS = "PASS";
	public static final String STATUS_FAIL = "FAIL";
	public static final String STATUS_DONE = "DONE";
	public static final String STATUS_SKIPPED = "N/A (Monthly Activity)";
	public static final String STATUS_NA = "N/A";
	
	public static final String FORCELOGOUT = "Force Logout Of Users";
	public static final String LADINFO = "Update LAD Information";
	public static final String GENBASEL = "Generate Daily Basel Reports";
	public static final String GENMONTHLYBASEL = "Generate Monthly Basel Reports";
	public static final String GENEMAIL = "Generate Email Notifications";
	public static final String DORMANTUSERS = "Manage Dormant Users";
	public static final String EXECRECEIVEDSTMTPROC = "Execute Recevied Statement Procedure";
	public static final String EXECFDDELBKUPPROC = "Execute FD Deleted Backup Procedure";
	public static final String EXECFACCHARGEIDPROC = "Execute Facility ChargeID Procedure";
	public static final String EXECINACTIVEFDPROC = "Execute Inactive FD Procedure";
	public static final String EXECFILEUPDCLEANUP = "Execute File Upload Cleanup Procedure";
	public static final String EXEFCUBSACTIVITY = "EXECUTE FCUBS ACTIVITY";
	public static final String EXEINTRALMTAVAILABLEACT = "UPDATE INTRADAY LIMIT AVAILABLE ACTIVITY";
	public static final String EXEAPPLICATIONDATECHANGEACT = "APPLICATION DATE CHANGE ACTIVITY";
	
	public static final String SP_CMS_Recp_Col_Limit_Link  = "Recp Collateral Limit Link" ; 
	public static final String SP_CMS_RECP_COL_PRODUCT_LINK= "RECP Collateral Product Link";
	public static final String SP_cms_Recp_Collateral      = "Recp Collateral"  ;   
	public static final String SP_CMS_RECP_CUST_Legal      = "RECP Customer Legal";     
	public static final String SP_cms_Recp_Customer        = "Recp Customer"; 
	public static final String SP_cms_Recp_PARTY           = "Recp Party";   
	   
	
	
	public static final String Proc_SP_CMS_Recp_Col_Limit_Link	= "SP_CMS_Recp_Col_Limit_Link";  
	public static final String Proc_SP_CMS_RECP_COL_PRODUCT_LINK	= "SP_CMS_RECP_COL_PRODUCT_LINK";
	public static final String Proc_SP_cms_Recp_Collateral		= "SP_cms_Recp_Collateral";      
	public static final String Proc_SP_CMS_RECP_CUST_Legal		= "SP_CMS_RECP_CUST_Legal";      
	public static final String Proc_SP_cms_Recp_Customer		= "SP_cms_Recp_Customer";        
	public static final String Proc_SP_cms_Recp_PARTY		= "SP_cms_Recp_PARTY";  
	public static final String PROC_SP_CMS_RECP_ALL_PROCEDURE           = "SP_cms_Recp_ALL";
	
	public static final String Fincon_Recp_Party = "Fincon Party"; 
	public static final String Fincon_Recp_Customer = "Fincon Customer"; 
	public static final String Fincon_Recp_Cust_Legal = "Fincon Customer Legal"; 
	public static final String Fincon_Recp_Col_Prod_Link = "Fincon Collateral Product Link"; 
	public static final String Fincon_Recp_Col_Limit_link = "Fincon Collateral Limit Link"; 
	public static final String Fincon_Recp_Collateral = "Fincon Collateral"; 
	
	public static final String Proc_SP_Fincon_Recp_Party	= "SP_FINCON_RECP_PARTY";  
	public static final String Proc_SP_Fincon_Recp_Customer	= "SP_FINCON_RECP_CUSTOMER";
	public static final String Proc_SP_Fincon_Recp_Cust_Legal	= "SP_FINCON_RECP_CUST_LEGAL";      
	public static final String Proc_SP_Fincon_Recp_Col_Prod_Link	= "SP_FINCON_RECP_COL_PROD_LINK";      
	public static final String Proc_SP_Fincon_Recp_Col_Limit_link	= "SP_FINCON_RECP_COL_LIMIT_LINK";        
	public static final String Proc_SP_Fincon_Recp_Collateral		= "SP_FINCON_RECP_COLLATERAL";  
	// Added By Dayananda Laishram on FINCON_CR2 10/06/2015 || Starts
	public static final String Fincon_Cust_Wise_Sec = "Fincon Customer Wise Security";
	public static final String Fincon_Master_Industry = "Fincon Master Data For Lookup With Industry";
	public static final String Proc_SP_Fincon_Cust_Wise_Sec		= "SP_FINCON_CUST_WISE_SEC";        
	public static final String Proc_SP_Fincon_Master_Industry	= "SP_FINCON_MASTER_INDUSTRY"; 
	public static final String Purge_LAD_details	= "Purge LAD Details"; 
	// Added By Dayananda Laishram on FINCON_CR2 10/06/2015 || Ends
}
