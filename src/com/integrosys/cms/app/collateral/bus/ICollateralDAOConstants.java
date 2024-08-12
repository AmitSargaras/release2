/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/collateral/bus/ICollateralDAOConstants.java,v 1.36 2006/06/15 08:53:37 hmbao Exp $
 */
package com.integrosys.cms.app.collateral.bus;

/**
 * Interface that defines constants for table and column names used in
 * Collateral.
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.36 $
 * @since $Date: 2006/06/15 08:53:37 $ Tag: $Name: $
 */
public interface ICollateralDAOConstants {
	// table name and column names for security.
	public static final String SECURITY_TABLE = "CMS_SECURITY";

	public static final String SECURITY_STAGE_TABLE = "CMS_STAGE_SECURITY";

	public static final String SECURITY_LOCATION = "SECURITY_LOCATION";

	public static final String SECURITY_SUBTYPE_CODE = "SECURITY_SUB_TYPE_ID";

	public static final String SECURITY_MARGIN = "MARGIN";

	public static final String COLLATERAL_ID = "CMS_COLLATERAL_ID";

	public static final String SECURITY_VERSION_TIME = "VERSION_TIME";

	public static final String SECURITY_CCY_CODE = "SCI_SECURITY_CURRENCY";

	public static final String SECURITY_CMV = "CMV";

	public static final String SECURITY_CMV_CCY = "CMV_CURRENCY";

	public static final String SECURITY_FSV = "FSV";

	public static final String SECURITY_FSV_CCY = "FSV_CURRENCY";

	public static final String FSV_BALANCE = "FSV_BALANCE";

	public static final String SECURITY_STATUS = "STATUS";

	public static final String SECURITY_IS_PERFECTED = "IS_SECURITY_PERFECTED";

	public static final String SECURITY_PERFECTION_DATE = "SECURITY_PERFECTION_DATE";

	// table name and column names for marketable security.
	public static final String MARKETABLE_TABLE = "CMS_MARKETABLE_SEC";

	public static final String MARKETABLE_STAGE_TABLE = "CMS_STAGE_MARKETABLE_SEC";

	public static final String MARKETABLE_COL_ID = "CMS_COLLATERAL_ID";

	// table name and column names for marketable porfolio items.
	public static final String PORTFOLIO_ITEM_TABLE = "CMS_PORTFOLIO_ITEM";

	public static final String PORTFOLIO_ITEM_STAGE_TABLE = "CMS_STAGE_PORTFOLIO_ITEM";

	public static final String ITEM_ID = "ITEM_ID";

	public static final String ITEM_TYPE = "TYPE";

	public static final String ITEM_RIC = "RIC";

	public static final String ITEM_COL_ID = "CMS_COLLATERAL_ID";

	public static final String ITEM_NO_OF_UNITS = "NO_OF_UNITS";

	public static final String ITEM_STOCK_EXCHANGE = "STOCK_EXCHANGE";

	public static final String ITEM_STOCK_EXCHANGE_CTRY = "STOCK_EXCHANGE_COUNTRY";

	public static final String ITEM_FSV = "FSV";

	public static final String ITEM_FSV_CCY = "FSV_CURRENCY";

	public static final String ITEM_CMV = "CMV";

	public static final String ITEM_CMV_CCY = "CMV_CURRENCY";

	public static final String ITEM_IS_BLACKLISTED = "IS_BLACKLISTED";

	public static final String ITEM_UNIT_PRICE = "UNIT_PRICE";

	public static final String ITEM_UNIT_PRICE_CCY = "UNIT_PRICE_CURRENCY";

	public static final String ITEM_STATUS = "STATUS";

	public static final String ITEM_BOND_RATING = "BOND_RATING";

	// table name and column names for security parameter.
	public static final String COLPARAM_TABLE = "CMS_SECURITY_PARAMETER";

	public static final String COLPARAM_COUNTRY = "COUNTRY_ISO_CODE";

	public static final String COLPARAM_SUBTYPE = "SECURITY_SUB_TYPE_ID";

	public static final String COLPARAM_THRESHOLD = "THRESHOLD_PERCENT";

	public static final String COLPARAM_VAL_FREQ_UNIT = "VALUATION_FREQUENCY_UNIT";

	public static final String COLPARAM_VAL_FREQ = "VALUATION_FREQUENCY";

	// table name and column names for cash.
	public static final String CASH_TABLE = "CMS_CASH";

	public static final String CASH_STAGE_TABLE = "CMS_STAGE_CASH";

	public static final String CASH_COL_ID = "CMS_COLLATERAL_ID";

	// table name and column names for cash deposit
	public static final String CASH_DEPOSIT_TABLE = "CMS_CASH_DEPOSIT";

	public static final String CASH_DEPOSIT_STAGE_TABLE = "CMS_STAGE_CASH_DEPOSIT";

	public static final String DEPOSIT_COL_ID = "CMS_COLLATERAL_ID";

	public static final String DEPOSIT_AMOUNT = "DEPOSIT_AMOUNT";

	public static final String DEPOSIT_AMOUNT_CCY = "DEPOSIT_AMOUNT_CURRENCY";

	public static final String DEPOSIT_STATUS = "STATUS";

	// table name and column names for Asset collateral
	public static final String ASSET_TABLE = "CMS_ASSET";

	public static final String ASSET_STAGE_TABLE = "CMS_STAGE_ASSET";

	public static final String ASSET_COL_ID = "CMS_COLLATERAL_ID";

	// table name and column names for Asset Post Dated Cheque
	public static final String ASSET_PDC_TABLE = "CMS_ASSET_PDC";

	public static final String ASSET_PDC_STAGE_TABLE = "CMS_STAGE_ASSET_PDC";

	public static final String PDC_COL_ID = "CMS_COLLATERAL_ID";

	public static final String PDC_AMOUNT = "CHEQUE_AMOUNT";

	public static final String PDC_AMOUNT_CCY = "CHEQUE_AMOUNT_CURRENCY";

	public static final String PDC_VALUE_BEFORE_MARGIN = "VALUE_BEFORE_MARGIN";

	public static final String PDC_VALUATION_CCY = "VALUATION_CURRENCY";

	public static final String PDC_MARGIN = "MARGIN";

	public static final String PDC_VALUATION_DATE = "VALUATION_DATE";

	public static final String PDC_STATUS = "STATUS";

	// table name and column names for guarantee
	public static final String GUARANTEE_TABLE = "CMS_GUARANTEE";

	public static final String GUARANTEE_STAGE_TABLE = "CMS_STAGE_GUARANTEE";

	public static final String GUARANTEE_AMOUNT = "GUARANTEE_AMT";

	public static final String GUARANTEE_CCY = "CURRENCY_CODE";

	public static final String GUARANTEE_COL_ID = "CMS_COLLATERAL_ID";

	// table name and column names for insurance
	public static final String INSURANCE_TABLE = "CMS_INSURANCE";

	public static final String INSURANCE_STAGE_TABLE = "CMS_STAGE_INSURANCE";

	public static final String INSURANCE_COL_ID = "CMS_COLLATERAL_ID";

	public static final String INSURANCE_AMT = "INSURED_AMOUNT";

	public static final String INSURANCE_AMT_CCY = "INSURED_AMT_CURR";

	// table name and column names for property
	public static final String PROPERTY_TABLE = "CMS_PROPERTY";

	public static final String PROPERTY_STAGE_TABLE = "CMS_STAGE_PROPERTY";

	public static final String PROPERTY_COL_ID = "CMS_COLLATERAL_ID";

	// table name and column names for asset based collaterals
	public static final String ASSET_BASED_TABLE = "CMS_ASSET";

	public static final String ASSET_BASED_STAGE_TABLE = "CMS_STAGE_ASSET";

	public static final String ASSET_BASED_COL_ID = "CMS_COLLATERAL_ID";

	public static final String ASSET_BASED_CMV_DEBTOR = "CMV_DEBTOR";

	public static final String ASSET_BASED_FSV_DEBTOR = "FSV_DEBTOR";

	public static final String ASSET_BASED_MARGIN_DEBTOR = "MARGIN_DEBTOR";

	public static final String ASSET_BASED_CMV_STOCK = "CMV_STOCK";

	public static final String ASSET_BASED_FSV_STOCK = "FSV_STOCK";

	public static final String ASSET_BASED_MARGIN_STOCK = "MARGIN_STOCK";

	// table name and column names for security pledgor map
	public static final String PLEDGOR_MAP_TABLE = "SCI_SEC_PLDGR_MAP";

	public static final String PLEDGOR_MAP_COL_ID = "CMS_COLLATERAL_ID";

	public static final String PLEDGOR_MAP_PLEDGOR_ID = "CMS_PLEDGOR_DTL_ID";

	// table name and column name for valuation
	public static final String VALUATION_TABLE = "CMS_VALUATION";

	public static final String VALUATION_STAGE_TABLE = "CMS_STAGE_VALUATION";

	public static final String VALUATION_CCY = "VALUATION_CURRENCY";

	public static final String VALUATION_COL_ID = "CMS_COLLATERAL_ID";

	public static final String VALUATION_BEFORE_MARGIN = "VALUE_BEFORE_MARGIN";

	public static final String VALUATION_CMV = "CMV";

	public static final String VALUATION_FSV = "FSV";

	public static final String VALUATION_ID = "VALUATION_ID";

	public static final String VALUATION_DATE = "VALUATION_DATE";

	public static final String VALUATION_VALUER = "VALUER";

	public static final String VALUATION_REVAL_FREQ = "REVAL_FREQ";

	public static final String VALUATION_REVAL_FREQ_UNIT = "REVAL_FREQ_UNIT";

	public static final String VALUATION_NON_REVAL_FREQ = "NON_REVAL_FREQ";

	public static final String VALUATION_NON_REVAL_FREQ_UNIT = "NON_REVAL_FREQ_UNIT";

	public static final String VALUATION_COMMENTS = "COMMENTS";

	// table name and column names for feed group.
	public static final String FEED_GROUP_TABLE = "CMS_FEED_GROUP";

	public static final String FEED_GROUP_ID = "FEED_GROUP_ID";

	public static final String FEED_GROUP_TYPE = "GROUP_TYPE";

	public static final String FEED_GROUP_SUBTYPE = "GROUP_SUBTYPE";

	// table name and column names for price feed.
	public static final String PRICE_FEED_TABLE = "CMS_PRICE_FEED";

	public static final String PRICE_FEED_GROUP_ID = "FEED_GROUP_ID";

	public static final String PRICE_FEED_RIC = "RIC";

	public static final String PRICE_FEED_CURRENCY = "CURRENCY";

	public static final String PRICE_FEED_COUNTRY = "COUNTRY";

	public static final String PRICE_FEED_UNITPRICE = "UNIT_PRICE";

	public static final String PRICE_FEED_IS_SUSPENDED = "IS_SUSPENDED";

	public static final String PRICE_FEED_IS_BLACKLISTED = "IS_BLACKLISTED";

	public static final String PRICE_FEED_RATING = "RATING";

	// constants for CMS_CHARGE_DETAIL and CMS_STAGE_CHARGE_DETAIL table
	public static final String CHARGE_DETAIL_TABLE = "CMS_CHARGE_DETAIL";

	public static final String STAGE_CHARGE_DETAIL_TABLE = "CMS_STAGE_CHARGE_DETAIL";

	public static final String CHARGE_DETAIL_ID = "CHARGE_DETAIL_ID";

	public static final String CHARGE_AMOUNT = "CHARGE_AMOUNT";

	public static final String CHARGE_CURRENCY_CODE = "CHARGE_CURRENCY_CODE";

	public static final String PRIOR_CHARGE_AMOUNT = "PRIOR_CHARGE_AMOUNT";

	public static final String PRIOR_CHARGE_CURRENCY = "PRIOR_CHARGE_CURRENCY";

	public static final String SECURITY_RANK = "SECURITY_RANK";

	public static final String CHARGE_TYPE = "CHARGE_TYPE";

	public static final String CHARGE_COLLATERAL_ID = "CMS_COLLATERAL_ID";

	public static final String ACTUAL_CHARGE_AMOUNT = "CMS_ACTUAL_CHARGE_AMOUNT";

	public static final String ACTUAL_CHARGE_CCY = "CMS_ACTUAL_CHARGE_CCY";

	// LIMIT_SECURITY_MAP CONSTANTS
	public static final String LIMIT_SECURITY_MAP_TABLE = "CMS_LIMIT_SECURITY_MAP";

	public static final String MAP_ID = "CHARGE_ID";

	public static final String MAP_CHARGE_DETAIL_ID = "CHARGE_DETAIL_ID";

	public static final String MAP_LIMIT_ID = "CMS_LSP_APPR_LMTS_ID";

	public static final String MAP_COLLATERAL_ID = "CMS_COLLATERAL_ID";

	public static final String MAP_SCI_LIMIT_ID = "SCI_LAS_LMT_ID";

	public static final String MAP_SCI_STATUS = "UPDATE_STATUS_IND";

	public static final String MAP_CASH_REQ_PCT = "CASH_REQ_PCT";

	public static final String MAP_IS_ALLOW_POOL = "IS_ALLOW_POOL";

	public static final String MAP_IS_ALLOW_SPECIFIC = "IS_ALLOW_SPECIFIC";

	// LIMIT_CHARGE_MAP CONSTANTS
	public static final String LIMIT_CHARGE_MAP_TABLE = "CMS_LIMIT_CHARGE_MAP";

	public static final String CHARGE_MAP_ID = "LIMIT_CHARGE_MAP_ID";

	public static final String CHARGE_MAP_CHARGE_ID = "CHARGE_ID";

	public static final String CHARGE_MAP_CHARGE_DETAIL_ID = "CHARGE_DETAIL_ID";

	public static final String CHARGE_MAP_STATUS = "STATUS";

	// table name and column names for common code category
	public static final String COMMON_CODE_CATEGORY_TABLE = "COMMON_CODE_CATEGORY";

	public static final String CATEGORY_CODE = "CATEGORY_CODE";

	// table name and column names for common code category entry
	public static final String COMMON_CODE_CATEGORY_ENTRY_TABLE = "COMMON_CODE_CATEGORY_ENTRY";

	public static final String ENTRY_CODE = "ENTRY_CODE";

	public static final String ENTRY_CATEGORY_CODE = "CATEGORY_CODE";

	public static final String ENTRY_NAME = "ENTRY_NAME";

	// table name and column names for others
	public static final String OTHERS_TABLE = "CMS_OTHERS";

	public static final String OTHERS_STAGE_TABLE = "CMS_STAGE_OTHERS";

	public static final String OTHERS_COL_ID = "CMS_COLLATERAL_ID";

	// table name and column names for commodity
	public static final String CMDT_TABLE = "CMS_COMMODITY";

	public static final String CMDT_STAGE_TABLE = "CMS_STAGE_COMMODITY";

	public static final String CMDT_COL_ID = "CMS_COLLATERAL_ID";

	public static int MAX_IN_CLAUSE = 1000;
}
