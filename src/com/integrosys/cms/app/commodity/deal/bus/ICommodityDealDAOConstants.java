/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/ICommodityDealDAOConstants.java,v 1.24 2004/09/08 06:36:20 lyng Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

/**
 * Interface that defines constants for table and column names used in Commodity
 * Deal.
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.24 $
 * @since $Date: 2004/09/08 06:36:20 $ Tag: $Name: $
 */
public interface ICommodityDealDAOConstants {
	// table name and column names for commodity deal.
	public static final String DEAL_TABLE = "CMS_CMDT_DEAL";

	public static final String DEAL_STAGE_TABLE = "CMS_STAGE_CMDT_DEAL";

	public static final String DEAL_ID = "DEAL_ID";

	public static final String DEAL_COL_ID = "CMS_COLLATERAL_ID";

	public static final String DEAL_LIMIT_ID = "CMS_LSP_APPR_LMTS_ID";

	public static final String DEAL_NO = "DEAL_NO";

	public static final String DEAL_TYPE = "DEAL_TYPE";

	public static final String DEAL_REF_NO = "TRX_REF_NO";

	public static final String DEAL_FACE_VALUE = "ORIG_FACE_AMT";

	public static final String DEAL_FACE_VALUE_CCY = "ORIG_FACE_AMT_CCY";

	public static final String DEAL_FINANCING_PCT = "FINANCE_PCT";

	public static final String DEAL_MARKET_PRICE = "ACT_PRICE";

	public static final String DEAL_MARKET_PRICE_CCY = "ACT_PRICE_CCY";

	public static final String DEAL_MARKET_PRICE_DATE = "ACT_MKT_PRICE_DATE";

	public static final String DEAL_HEDGE_PRICE = "HEDGE_PRICE";

	public static final String DEAL_HEDGE_PRICE_CCY = "HEDGE_PRICE_CCY";

	public static final String DEAL_HEDGE_QTY = "HEDGE_QTY";

	public static final String DEAL_HEDGE_CNTR_ID = "HEDGE_CNTR_ID";

	public static final String DEAL_CASH_REQ_PCT = "CASH_REQ_PCT";

	public static final String DEAL_CMV = "DEAL_CMV";

	public static final String DEAL_CMV_CCY = "DEAL_CMV_CCY";

	public static final String DEAL_FSV = "DEAL_FSV";

	public static final String DEAL_FSV_CCY = "DEAL_FSV_CCY";

	public static final String DEAL_ACT_QTY = "ACT_QTY";

	public static final String DEAL_CNTR_QTY_UOM = "CNTR_QTY_UOM_ID";

	public static final String DEAL_CNTR_PRICE_TYPE = "CNTR_PRICE_TYPE";

	public static final String DEAL_PROFILE_ID = "CNTR_PROFILE_ID";

	public static final String DEAL_ENFORCIBILITY = "IS_ATTAIN_ENFORCE";

	public static final String DEAL_ACT_DIFF = "ACT_COMMON_DIFF";

	public static final String DEAL_ACT_DIFF_SIGN = "ACT_COMMON_DIFF_SIGN";

	public static final String DEAL_ACT_DIFF_CCY = "ACT_COMMON_DIFF_CCY";

	public static final String DEAL_CUST_DIFF = "ACT_EOD_CUST_DIFF";

	public static final String DEAL_CUST_DIFF_SIGN = "ACT_EOD_CUST_DIFF_SIGN";

	public static final String DEAL_CUST_DIFF_CCY = "ACT_EOD_CUST_DIFF_CCY";

	public static final String DEAL_CNTR_RIC = "CNTR_RIC";

	public static final String DEAL_CNTR_MKT_UOM_RATE = "CNTR_MKT_UOM_CONVERT_QTY";

	public static final String DEAL_CNTR_MKT_UOM_ID = "CNTR_MKT_UOM_ID";

	public static final String DEAL_CASH_MARGIN = "CASH_MARGIN_PCT";

	public static final String DEAL_STATUS = "STATUS";

	public static final String DEAL_VERSION_TIME = "VERSION_TIME";

	// table namd and column names for commodity deah cash.
	public static final String CASH_TABLE = "CMS_DEAL_CASH";

	public static final String CASH_STAGE_TABLE = "CMS_STAGE_DEAL_CASH";

	public static final String CASH_ID = "CASH_DEPOSIT_ID";

	public static final String CASH_DEAL_ID = "DEAL_ID";

	public static final String CASH_DEPOSIT_AMT = "DEPOSIT_AMT";

	public static final String CASH_DEPOSIT_CCY = "DEPOSIT_CCY";

	public static final String CASH_DEPOSIT_TYPE = "DEPOSIT_TYPE";

	public static final String CASH_STATUS = "STATUS";

	// table name and column names for commodity deal title doc.
	public static final String TITLE_DOC_TABLE = "CMS_TITLE_DOC";

	public static final String TITLE_DOC_IS_SECURED = "IS_SECURED";

	public static final String TITLE_DOC_TRX_DATE = "TRX_DATE";

	public static final String TITLE_DOC_ADV_PCT = "ELIGIBILITY_ADV_PCT";

	public static final String TITLE_DOC_DEAL_ID = "DEAL_ID";

	public static final String TITLE_DOC_ID = "TITLE_DOC_ID";

	public static final String TITLE_DOC_REF_ID = "CMS_REF_ID";

	// table name and column names for deal settlement.
	public static final String SETTLE_TABLE = "CMS_SETTLEMENT";

	public static final String SETTLE_STAGE_TABLE = "CMS_STAGE_SETTLEMENT";

	public static final String SETTLE_SETTLEMENT_ID = "SETTLEMENT_ID";

	public static final String SETTLE_PAYMENT = "PAYMENT_AMT";

	public static final String SETTLE_PAYMENT_CCY = "PAYMENT_CCY";

	public static final String SETTLE_DEAL_ID = "DEAL_ID";

	public static final String SETTLE_RELEASE_QTY = "RELEASE_QTY";

	public static final String SETTLE_RELEASE_QTY_UOM = "RELEASE_QTY_UOM_ID";

	public static final String SETTLE_STATUS = "STATUS";

	// table name and column names for deal WR's releases.
	public static final String RELEASE_TABLE = "CMS_RCPT_RELEASE";

	public static final String RELEASE_STAGE_TABLE = "CMS_STAGE_RCPT_RELEASE";

	public static final String RELEASE_ID = "RELEASE_ID";

	public static final String RELEASE_DEAL_ID = "DEAL_ID";

	public static final String RELEASE_QTY = "RELEASE_QTY";

	public static final String RELEASE_QTY_UOM = "RELEASE_QTY_UOM_ID";

	public static final String RELEASE_STATUS = "STATUS";

	// table name and column name for commodity profile.
	public static final String PROFILE_TABLE = "CMS_CMDT_PROFILE";

	public static final String PROFILE_PROFILE_ID = "PROFILE_ID";

	public static final String PROFILE_PRODUCT_TYPE = "PRODUCT_TYPE";

	public static final String PROFILE_PRODUCT_SUBTYPE = "PRODUCT_SUB_TYPE";

	public static final String PROFILE_CATEGORY = "CATEGORY";

	// table name and column name for hedge extension
	public static final String HEDGE_EXT_TABLE = "CMS_HEDGE_EXT";

	public static final String HEDGE_EXT_STAGE_TABLE = "CMS_STAGE_HEDGE_EXT";

	public static final String HEDGE_EXT_ID = "HEDGE_EXT_ID";

	public static final String HEDGE_EXT_END_DATE = "END_DATE";

	public static final String HEDGE_EXT_STATUS = "STATUS";

	public static final String HEDGE_EXT_DEAL_ID = "DEAL_ID";

	// table name and column name for hedged contract
	public static final String HEDGE_CNTR_TABLE = "CMS_HEDGE_CNTR";

	public static final String HEDGE_CNTR_MARGIN = "MARGIN_PCT";

	public static final String HEDGE_CNTR_ID = "HEDGECONTRACT_ID";
}
