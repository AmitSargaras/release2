package com.integrosys.cms.batch.eod;

import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;

public interface IEndOfDaySyncMastersService {
	//Constant for Uploading cps records to CLIMS
	public static final String SYSTEM_BANK_EOD_UPLOAD	=	"SYSTEM_BANK_EOD_UPLOAD";        
	public static final String PARTY_GROUP_EOD_UPLOAD	=	"PARTY_GROUP_EOD_UPLOAD";        
	public static final String FACILITY_MASTER_EOD_UPLOAD	=	"FACILITY_MASTER_EOD_UPLOAD";    
	public static final String COMMODITY_MASTER_EOD_UPLOAD	=	"COMMODITY_MASTER_EOD_UPLOAD";   
	public static final String INDUSTRY_MASTER_EOD_UPLOAD	=	"INDUSTRY_MASTER_EOD_UPLOAD";    
	public static final String RBI_INDUSTRY_MASTER_EOD_UPLOAD=	"RBI_INDUSTRY_MASTER_EOD_UPLOAD";
	public static final String SECURITY_MASTER_EOD_UPLOAD	=	"SECURITY_MASTER_EOD_UPLOAD";    
	public static final String SECTOR_MASTER_EOD_UPLOAD	=	"SECTOR_MASTER_EOD_UPLOAD"; 
	public static final String USER_MASTER_EOD_UPLOAD	=	"USER_MASTER_EOD_UPLOAD";
	
	public static final String COUNTRY_MASTER_EOD_UPLOAD	=	"COUNTRY_MASTER_EOD_UPLOAD";
	public static final String REGION_MASTER_EOD_UPLOAD	=	"REGION_MASTER_EOD_UPLOAD";
	public static final String STATE_MASTER_EOD_UPLOAD	=	"STATE_MASTER_EOD_UPLOAD";
	public static final String CITY_MASTER_EOD_UPLOAD	=	"CITY_MASTER_EOD_UPLOAD";
	public static final String CURRENCY_MASTER_EOD_UPLOAD	=	"CURRENCY_MASTER_EOD_UPLOAD";
	public static final String BANKING_METHOD_MASTER_EOD_UPLOAD	=	"BANKING_METHOD_MASTER_EOD_UPLOAD";
	public static final String MINORITY_COMMUNITY_MASTER_EOD_UPLOAD	=	"MINORITY_COMMUNITY_MASTER_EOD_UPLOAD";
	public static final String NBFC_A_MASTER_EOD_UPLOAD	=	"NBFC_A_MASTER_EOD_UPLOAD";
	public static final String NBFC_B_MASTER_EOD_UPLOAD	=	"NBFC_B_MASTER_EOD_UPLOAD";
	
	public static final String RAM_RATING_MASTER_EOD_UPLOAD	=	"RAM_RATING_MASTER_EOD_UPLOAD";	
	public static final String RBI_ASSET_CLASSIFICATION_MASTER_EOD_UPLOAD	=	"RBI_ASSET_CLASSIFICATION_MASTER_EOD_UPLOAD";
	public static final String RELATED_TYPE_MASTER_EOD_UPLOAD	=	"RELATED_TYPE_MASTER_EOD_UPLOAD";
	public static final String RATING_TYPE_MASTER_EOD_UPLOAD	=	"RATING_TYPE_MASTER_EOD_UPLOAD";	
	public static final String RELATIONSHIP_TYPE_MASTER_EOD_UPLOAD	=	"RELATIONSHIP_TYPE_MASTER_EOD_UPLOAD";
	
	public static final String WEAKER_SECTION_MASTER_EOD_UPLOAD	=	"WEAKER_SECTION_MASTER_EOD_UPLOAD";
	public static final String MSME_CLASSIFICATION_MASTER_EOD_UPLOAD	=	"MSME_CLASSIFICATION_MASTER_EOD_UPLOAD";	
	public static final String PSL_MIS_CODE_MASTER_EOD_UPLOAD	=	"PSL_MIS_CODE_MASTER_EOD_UPLOAD";	
	public static final String INFRASTRUCTURE_FINANCE_MASTER_EOD_UPLOAD	=	"INFRASTRUCTURE_FINANCE_MASTER_EOD_UPLOAD";	
	public static final String MARKET_SEGMENT_MASTER_EOD_UPLOAD	=	"MARKET_SEGMENT_MASTER_EOD_UPLOAD";
	
	//Constant for migrate records from CLIMS to CPS
	public static final String OTHER_BANK_MASTER	=	"OTHERBANK";
	
	public static final String SYNC_OPERATION_INSERT	=	"I";
	public static final String SYNC_OPERATION_UPDATE	=	"U";
	public static final String SYNC_OPERATION_DELETE	=	"D";
	public static final String CPS_CLIMS_CONTROL_FILE = "CPS_CLIMS_Control";
	public static final String CLIMS_CPS_CONTROL_FILE = "CLIMS_CPS_Control";
	
	
//	public StringBuffer performEODSync();
	public StringBuffer performEODSyncClimsToCps();
	public StringBuffer performEODSyncCpsToClims();
	
	public IGeneralParamDao getGeneralParam();
	public void setGeneralParam(IGeneralParamDao generalParam);
	public void processRecord(String masterName, Object obToStore) throws Exception;
	
}
