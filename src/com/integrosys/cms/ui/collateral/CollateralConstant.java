/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.collateral;

import static com.integrosys.cms.app.common.constant.ICMSConstant.COLTYPE_ASSET_SPEC_CHARGE_VEH;
import static com.integrosys.cms.app.common.constant.ICMSConstant.COLTYPE_OTHERS_OTHERSA;

import java.util.Arrays;
import java.util.List;

/**
 * This is an Interface to store common constants to be used accross the various
 * ui packages in cms project
 * 
 * @author $Author: jzhan $<br>
 * @version $Revision: 1.6 $
 * @since $Date: 2006/07/27 04:41:58 $ Tag: $Name: $
 */
public interface CollateralConstant {
	// constants for Asset based - General Charge Tabs
	static final String TAB_GENERAL = "GENERAL";

	static final String TAB_STOCK = "STOCK";

	static final String TAB_DEBTOR = "DEBTOR";

	static final String TAB_FAO = "FAO";

	static final String TAB_DRAWING_POWER = "DRAWING_POWER";

	// constants for Asset based - General Charge items
	static final String STOCK = "STOCK";

	static final String DEBTOR = "DEBTOR";

	static final String INSURANCE_POLICY = "INS_POLICY";
	
	public static final String RECEIVED = "RECEIVED";

	static final String FAO = "FAO";

	public static final int MAX_PERIOD_MONTH = 999;

	// constants for delete 1 to many relationship obj in collateral
	public static final String LIMIT_CHARGE = "LIMIT_CHARGE";

	public static final String FEE_DETAILS = "FEE_DETAILS";

	public static final String INS_INFO = "INSURANCE_INFO";
	
	public static final String ADD_DOC_FAC_DET_INFO = "ADDITIONAL_DOC_FAC_DET_INFO";

	public static final String APPORTIONMENT = "APPORTIONMENT";

	// constants for asset and property to use in 1 to many relationship
	public static final String COL_ASSET = "Asset";

	public static final String COL_PROPERTY = "Prop";

	public static final String FN_CTY_LAB = "countryLabels";

	public static final String FN_CTY_VAL = "countryValues";

	public static final String FN_ORG_LAB = "organizationLabels";

	public static final String FN_ORG_VAL = "organizationValues";
	
	public static final String HOST_COL_STATUS_ACTIVE = "1";
	
	public static final String SERVICE_COLLATERAL_OBJ = "serviceColObj";
	
	public static final String FORM_COLLATERAL_OBJ = "form.collateralObject";
	
	public static final String IS_SECURITY_VIEW_MODE = "isSecurityViewMode";
	
	public static final String DP_SHARE = "dpShare";
	
	public static final String COL_ASSET_ID = "assetId";
	
	public static final String SESSION_COLLATERAL_ID = "collateralID";
	
	public static final String DUE_DATE_AND_STOCK_DETAILS_KEY = "dueDateAndStockDetailsKey";
	
	public static final String ACTUAL_DUE_DATE_AND_STOCK_DETAILS_KEY= "actualDueDateAndStockDetailsKey";
	
	public static final String SESSION_DUE_DATE_AND_STOCK_DETAILS = "sessionDueDateAndStockDetails";
	
	public static final String SESSION_LEAD_BANK_STOCK_LIST = "sessionLeadBankStockList";
	
	public static final String SELECTED_LNB_STOCK_INDEX = "selectedLNBStockIndex";
	
	public static final String LEAD_BANK_STOCK_KEY = "leadBankStockKey";
	
	public static final String INSURANCE_HISTORY_KEY = "insuranceHistoryKey";
	
	public static final String SESSION_INSURANCE_HISTORY_KEY = "session.insuranceHistoryKey";
	
	public static final String INSURANCE_HISTORY_REPORT_CRITERIA = "insuranceHistoryReportCriteria";
	
	public static final String SESSION_INSURANCE_HISTORY = "session.insuranceHistory";
	
	public static final String INSURANCE_HISTORY_REPORT_FILE_NAME = "insuranceHistoryReportFileName";
	
	public static final String LOCATION_LIST = "locationList";
	
	public static final String SESSION_LOCATION_LIST = "sessionLocationList";
	
	public static final String SESSION_DUE_DATE_ACTION = "sessionDueDateAction";
	
	public static final String SELECTED_STOCK_LOCATIONS = "selectedStockLocations";
	
	public static final String DUE_DATE_LIST = "dueDateList";
	
	public static final String DUE_DATE_MAP = "dueDateMap";
	
	public static final String STOCK_DOC_MONTH_LIST = "stockDocMonthList";
	
	public static final String STOCK_DOC_YEAR_LIST = "stockDocYearList";
	
	public static final String DUE_DATE_VALUE = "dueDateValue";
	
	public static final String GC_DETAIL_ID = "gcDetailId";
	
	public static final String COLLATERAL_ID = "collateralID";
	
	public static final String TRX_ID = "trxID";
	
	public static final String PARENT_PAGE = "parentPageFrom";
	
	public static final String SESSION_PARENT_PAGE = "sessionParentPageFrom";
	
	public static final String OLD_POLICY_NO_LIST = "oldPolicyNoList";
	
	public static final String SESSION_DUE_DATE_AND_STOCK_SUB_ACTION = "sessionDueDateAndStockSubAction";
	
	public static final String SESSION_LEAD_BANK_REFERRER_EVENT = "sessionLeadBankReferrerEvent";
	
	public static final String SESSION_LEAD_BANK_COL_ASSET_ID = "sessionLeadBankColAssetId";
	
	public static final String SESSION_SELECTED_LNB_STOCK_INDEX = "sessionSelectedLNBStockIndex";
	
	public static final String LEAD_BANK_STOCK_ACTION = "leadBankStockAction";
	
	public static final String SESSION_LEAD_BANK_SUB_EVENT = "sessionLeadBankSubEvent";
	
	public static final String IS_LEAD_BANK_STOCK_BANKING = "isLeadBankStockBanking";
	
	public static final String SESSION_DUE_DATE_AND_STOCK_FAC_LINE_PENDING_MSG = "sessionDueDateAndStockFacLinePending";
	
	public static final String SESSION_DUE_DATE_AND_STOCK_TOTAL_RELEASED_AMT = "sessionDueDateAndStockTotalReleasedAmt";
	public static final String SESSION_ASSET_GC_INSURED_AMT_WARNING = "sessionInsuredAmtLessThanDpWarning";
	public static final String SESSION_ASSET_GC_DRAWING_POWER_MSG= "sessionDrawingPowerLessThanReleasedAmount";
	
	public static final String SESSION_VIEW_STOCK_STATEMENT_IMAGES = "sessionViewStockStatementImages";
	
	List<String> COLLATERALS_WITHOUT_INSURANCE_STATUS = Arrays.asList(COLTYPE_ASSET_SPEC_CHARGE_VEH, COLTYPE_OTHERS_OTHERSA);
	String UPDATE_RECEIVED = "UPDATE_RECEIVED";
	
	String SESSION_TOTAL_INSURANCE_POLICY_AMT = "sessionTotalInsurancePolicyAmt";
	
	String REQUEST_DUE_DATE_AND_STOCK_FORM_LIST = "requestDueDateAndStockFormList";
	
	String SESSION_HIGHLIGHTED_DUE_DATE_SET = "sessionHighlightedDueDateSet";
}