/*
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/app/commodity/deal/bus/CommodityDealDAO.java,v 1.45 2006/10/19 09:58:45 nkumar Exp $
 */
package com.integrosys.cms.app.commodity.deal.bus;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.integrosys.base.businfra.currency.Amount;
import com.integrosys.base.businfra.currency.CurrencyCode;
import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.exception.ChainedException;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.collateral.bus.ICollateralDAOConstants;
import com.integrosys.cms.app.commodity.common.AmountConversion;
import com.integrosys.cms.app.commodity.common.AmountConversionException;
import com.integrosys.cms.app.commodity.common.ConversionKey;
import com.integrosys.cms.app.commodity.common.PriceDifferential;
import com.integrosys.cms.app.commodity.common.Quantity;
import com.integrosys.cms.app.commodity.common.QuantityConversionRate;
import com.integrosys.cms.app.commodity.common.UOMWrapper;
import com.integrosys.cms.app.commodity.common.UOMWrapperFactory;
import com.integrosys.cms.app.commodity.deal.bus.doc.ICommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.doc.OBCommodityTitleDocument;
import com.integrosys.cms.app.commodity.deal.bus.finance.IReceiptRelease;
import com.integrosys.cms.app.commodity.deal.bus.finance.OBReceiptRelease;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.customer.bus.CustomerException;
import com.integrosys.cms.app.limit.bus.ILimitDAOConstant;
import com.integrosys.cms.app.transaction.ICMSTrxTableConstants;
import com.integrosys.cms.app.transaction.ITrxContext;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * DAO for commodity deal.
 * 
 * @author $Author: nkumar $<br>
 * @version $Revision: 1.45 $
 * @since $Date: 2006/10/19 09:58:45 $ Tag: $Name: $
 */
public class CommodityDealDAO implements ICommodityDealDAOConstants {
	private static int MAX_IN_CLAUSE = 1000;

	public static String SELECT_DEAL_BY_COLID = "select DEAL_ID, DEAL_NO, DEAL_CMV_CCY, DEAL_CMV, DEAL_FSV_CCY, DEAL_FSV from CMS_CMDT_DEAL where CMS_COLLATERAL_ID = ? and status <> '"
			+ ICMSConstant.STATE_CLOSED + "'";

	private static String SELECT_COMMODITY_DEAL = "select DEAL_NO, CMS_CMDT_DEAL.DEAL_ID, DEAL_TYPE, PRODUCT_TYPE, "
			+ "PRODUCT_SUB_TYPE, CATEGORY, TRX_REF_NO, ORIG_FACE_AMT_CCY, "
			+ "ORIG_FACE_AMT, FINANCE_PCT, ACT_PRICE_CCY, ACT_PRICE, "
			+ "CMS_CMDT_DEAL.CASH_REQ_PCT DEAL_CASH_REQ_PCT, DEAL_CMV, "
			+ "DEAL_CMV_CCY, DEAL_FSV, DEAL_FSV_CCY, ACT_QTY, CNTR_QTY_UOM_ID, "
			+ "CNTR_PRICE_TYPE, ACT_COMMON_DIFF_SIGN, ACT_COMMON_DIFF, "
			+ "ACT_COMMON_DIFF_CCY, ACT_EOD_CUST_DIFF_SIGN, ACT_EOD_CUST_DIFF, "
			+ "ACT_EOD_CUST_DIFF_CCY, CNTR_QTY_UOM_ID, CNTR_MKT_UOM_ID, "
			+ "CNTR_MKT_UOM_CONVERT_QTY, CMS_CMDT_DEAL.STATUS DEAL_STATUS, "
			+ "SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID, "
			+ "SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID from CMS_CMDT_DEAL, "
			+ "CMS_CMDT_PROFILE, SCI_LSP_APPR_LMTS, SCI_LSP_LMT_PROFILE, SCI_LE_SUB_PROFILE "
			+ "where CMS_CMDT_DEAL.CNTR_PROFILE_ID = CMS_CMDT_PROFILE.PROFILE_ID and "
			+ "CMS_CMDT_DEAL.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
			+ "and SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
			+ "and SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID";

	private static String SELECT_COMMODITY_CO_DEAL = "select DEAL_NO, CMS_CMDT_DEAL.DEAL_ID, DEAL_TYPE, PRODUCT_TYPE, "
			+ "PRODUCT_SUB_TYPE, CATEGORY, TRX_REF_NO, ORIG_FACE_AMT_CCY, "
			+ "ORIG_FACE_AMT, FINANCE_PCT, ACT_PRICE_CCY, ACT_PRICE, "
			+ "CMS_CMDT_DEAL.CASH_REQ_PCT DEAL_CASH_REQ_PCT, DEAL_CMV, "
			+ "DEAL_CMV_CCY, DEAL_FSV, DEAL_FSV_CCY, ACT_QTY, CNTR_QTY_UOM_ID, "
			+ "CNTR_PRICE_TYPE, ACT_COMMON_DIFF_SIGN, ACT_COMMON_DIFF, "
			+ "ACT_COMMON_DIFF_CCY, ACT_EOD_CUST_DIFF_SIGN, ACT_EOD_CUST_DIFF, "
			+ "ACT_EOD_CUST_DIFF_CCY, CNTR_QTY_UOM_ID, CNTR_MKT_UOM_ID, "
			+ "CNTR_MKT_UOM_CONVERT_QTY, CMS_CMDT_DEAL.STATUS DEAL_STATUS, "
			+ "SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID, "
			+ "SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID from CMS_CMDT_DEAL, SCI_LSP_CO_BORROW_LMT, "
			+ "CMS_CMDT_PROFILE, SCI_LSP_APPR_LMTS, SCI_LSP_LMT_PROFILE, SCI_LE_SUB_PROFILE "
			+ "where CMS_CMDT_DEAL.CNTR_PROFILE_ID = CMS_CMDT_PROFILE.PROFILE_ID and "
			+ "SCI_LSP_CO_BORROW_LMT.CMS_LSP_CO_BORROW_LMT_ID = CMS_CMDT_DEAL.CMS_LSP_CO_BORROW_LMT_ID "
			+ "and SCI_LSP_CO_BORROW_LMT.CMS_LIMIT_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
			+ "and SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
			+ "and SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID";

	private static String SELECT_CUSTOMER_DEALS = "SELECT DEAL_NO, " + "       CMS_CMDT_DEAL.DEAL_ID, "
			+ "       DEAL_TYPE, " + "       PRODUCT_TYPE, " + "       PRODUCT_SUB_TYPE, " + "       CATEGORY, "
			+ "       TRX_REF_NO, " + "       ORIG_FACE_AMT_CCY, " + "       ORIG_FACE_AMT, " + "       FINANCE_PCT, "
			+ "       ACT_PRICE_CCY, " + "       ACT_PRICE, "
			+ "       CMS_CMDT_DEAL.CASH_REQ_PCT AS DEAL_CASH_REQ_PCT, " + "       DEAL_CMV, "
			+ "       DEAL_CMV_CCY, " + "       DEAL_FSV, " + "       DEAL_FSV_CCY, " + "       ACT_QTY, "
			+ "       CNTR_QTY_UOM_ID, " + "       CNTR_PRICE_TYPE, " + "       ACT_COMMON_DIFF_SIGN, "
			+ "       ACT_COMMON_DIFF, " + "       ACT_COMMON_DIFF_CCY, " + "       ACT_EOD_CUST_DIFF_SIGN, "
			+ "       ACT_EOD_CUST_DIFF, " + "       ACT_EOD_CUST_DIFF_CCY, " + "       CNTR_QTY_UOM_ID, "
			+ "       CNTR_MKT_UOM_ID, " + "       CNTR_MKT_UOM_CONVERT_QTY, "
			+ "       CMS_CMDT_DEAL.STATUS AS DEAL_STATUS, "
			+ "       CMS_LIMIT_SECURITY_MAP.CASH_REQ_PCT AS CUST_CASH_REQ_PCT, " + "       DEPOSIT_CCY, "
			+ "       DEPOSIT_AMT, " + "       DEPOSIT_TYPE, " + "       CMS_DEAL_CASH.STATUS AS CASH_STATUS "
			+ "FROM CMS_CMDT_PROFILE, " + "     SCI_LSP_APPR_LMTS, " + "     SCI_LSP_LMT_PROFILE, "
			+ "     CMS_LIMIT_SECURITY_MAP, " + "     CMS_CMDT_DEAL LEFT OUTER JOIN  "
			+ "     CMS_DEAL_CASH ON CMS_DEAL_CASH.DEAL_ID = CMS_CMDT_DEAL.DEAL_ID "
			+ "WHERE CMS_CMDT_DEAL.CNTR_PROFILE_ID = CMS_CMDT_PROFILE.PROFILE_ID "
			+ "      AND CMS_CMDT_DEAL.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
			+ "      AND SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
			+ "      AND CMS_CMDT_DEAL.CMS_LSP_APPR_LMTS_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID "
			+ "      AND CMS_CMDT_DEAL.CMS_COLLATERAL_ID = CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID ";

	private static String SELECT_CUSTOMER_CB_DEALS = "SELECT DEAL_NO, " + "       CMS_CMDT_DEAL.DEAL_ID, "
			+ "       DEAL_TYPE, " + "       PRODUCT_TYPE, " + "       PRODUCT_SUB_TYPE, " + "       CATEGORY, "
			+ "       TRX_REF_NO, " + "       ORIG_FACE_AMT_CCY, " + "       ORIG_FACE_AMT, " + "       FINANCE_PCT, "
			+ "       ACT_PRICE_CCY, " + "       ACT_PRICE, "
			+ "       CMS_CMDT_DEAL.CASH_REQ_PCT AS DEAL_CASH_REQ_PCT, " + "       DEAL_CMV, "
			+ "       DEAL_CMV_CCY, " + "       DEAL_FSV, " + "       DEAL_FSV_CCY, " + "       ACT_QTY, "
			+ "       CNTR_QTY_UOM_ID, " + "       CNTR_PRICE_TYPE, " + "       ACT_COMMON_DIFF_SIGN, "
			+ "       ACT_COMMON_DIFF, " + "       ACT_COMMON_DIFF_CCY, " + "       ACT_EOD_CUST_DIFF_SIGN, "
			+ "       ACT_EOD_CUST_DIFF, " + "       ACT_EOD_CUST_DIFF_CCY, " + "       CNTR_QTY_UOM_ID, "
			+ "       CNTR_MKT_UOM_ID, " + "       CNTR_MKT_UOM_CONVERT_QTY, "
			+ "       CMS_CMDT_DEAL.STATUS AS DEAL_STATUS, "
			+ "       CMS_LIMIT_SECURITY_MAP.CASH_REQ_PCT AS CUST_CASH_REQ_PCT, " + "       DEPOSIT_CCY, "
			+ "       DEPOSIT_AMT, " + "       DEPOSIT_TYPE, " + "       CMS_DEAL_CASH.STATUS AS CASH_STATUS "
			+ "FROM CMS_CMDT_PROFILE, " + "     SCI_LSP_APPR_LMTS, " + "     SCI_LSP_CO_BORROW_LMT, "
			+ "     SCI_LSP_LMT_PROFILE, " + "     CMS_LIMIT_SECURITY_MAP, " + "     CMS_CMDT_DEAL LEFT OUTER JOIN  "
			+ "       CMS_DEAL_CASH ON CMS_DEAL_CASH.DEAL_ID = CMS_CMDT_DEAL.DEAL_ID "
			+ "WHERE CMS_CMDT_DEAL.CNTR_PROFILE_ID = CMS_CMDT_PROFILE.PROFILE_ID "
			+ "      AND CMS_CMDT_DEAL.CMS_LSP_CO_BORROW_LMT_ID = SCI_LSP_CO_BORROW_LMT.CMS_LSP_CO_BORROW_LMT_ID "
			+ "      AND SCI_LSP_CO_BORROW_LMT.CMS_LIMIT_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
			+ "      AND SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
			+ "      AND CMS_CMDT_DEAL.CMS_LSP_CO_BORROW_LMT_ID = CMS_LIMIT_SECURITY_MAP.CMS_LSP_CO_BORROW_LMT_ID "
			+ "      AND CMS_CMDT_DEAL.CMS_COLLATERAL_ID = CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID ";

	private static String SELECT_DEAL_TITLE_DOC = "select TITLE_DOC_ID, IS_SECURED, TRX_DATE, ELIGIBILITY_ADV_PCT, "
			+ "CMS_REF_ID from CMS_TITLE_DOC where DEAL_ID = ? and "
			+ "TRX_DATE = (select max(TRX_DATE) from CMS_TITLE_DOC where DEAL_ID = ?)";

	private static String SELECT_RELEASES = " select RELEASE_QTY, RELEASE_QTY_UOM_ID, DEAL_ID, STATUS from CMS_RCPT_RELEASE";

	private static String SELECT_DEAL = "select DEAL_NO, CMS_CMDT_DEAL.DEAL_ID, DEAL_TYPE, "
			+ "PRODUCT_TYPE, PRODUCT_SUB_TYPE, CATEGORY, TRX_REF_NO, "
			+ "ORIG_FACE_AMT_CCY, ORIG_FACE_AMT, FINANCE_PCT, ACT_PRICE_CCY, "
			+ "ACT_PRICE, CMS_CMDT_DEAL.CASH_REQ_PCT DEAL_CASH_REQ_PCT, "
			+ "DEAL_CMV, DEAL_CMV_CCY, DEAL_FSV, DEAL_FSV_CCY, ACT_QTY, "
			+ "CNTR_QTY_UOM_ID, CNTR_PRICE_TYPE, ACT_COMMON_DIFF_SIGN, "
			+ "ACT_COMMON_DIFF, ACT_COMMON_DIFF_CCY, ACT_EOD_CUST_DIFF_SIGN, "
			+ "ACT_EOD_CUST_DIFF, ACT_EOD_CUST_DIFF_CCY, CNTR_QTY_UOM_ID, "
			+ "CNTR_MKT_UOM_ID, CNTR_MKT_UOM_CONVERT_QTY, CMS_CMDT_DEAL.STATUS DEAL_STATUS";

	private static String FROM_DEAL = " from CMS_CMDT_DEAL, CMS_CMDT_PROFILE, SCI_LSP_APPR_LMTS, SCI_LSP_LMT_PROFILE ";

	private static String WHERE_DEAL = " where CMS_CMDT_DEAL.CNTR_PROFILE_ID = CMS_CMDT_PROFILE.PROFILE_ID "
			+ "and CMS_CMDT_DEAL.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
			+ "and SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID";

	private static String SELECT_SCI_LEID_BY_CUSTOMER_ID = "SELECT LSP_LE_ID FROM SCI_LE_SUB_PROFILE WHERE CMS_LE_SUB_PROFILE_ID = ?";

	/*
	 * original private static String SELECT_DEAL_TRX =
	 * "select TRANSACTION_ID, CATEGORY, PRODUCT_TYPE, PRODUCT_SUB_TYPE " +
	 * "from TRANSACTION, CMS_STAGE_CMDT_DEAL, CMS_CMDT_PROFILE, SCI_LSP_APPR_LMTS, "
	 * + "SCI_LSP_LMT_PROFILE, SCI_LE_SUB_PROFILE " +
	 * "where TRANSACTION_TYPE = 'COMMODITY_DEAL' and " +
	 * "(TRANSACTION.STATUS = 'PENDING_CREATE_VERIFY' or " +
	 * "TRANSACTION.STATUS = 'PENDING_UPDATE_VERIFY' or " +
	 * "TRANSACTION.STATUS = 'PENDING_CLOSE_VERIFY') and " +
	 * "TO_GROUP_TYPE_ID <> 13 and STAGING_REFERENCE_ID = DEAL_ID and " +
	 * "CNTR_PROFILE_ID = PROFILE_ID and " +
	 * "CMS_STAGE_CMDT_DEAL.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
	 * +
	 * "and SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
	 * +
	 * "and SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID"
	 * ;
	 */

	// added to retrieve "CMS_CMDT_DEAL.STATUS DEAL_STATUS" so that
	// "processDealResultSet" method will not fail
	// when it is retriving the status field to populate the object.
	// This is the result of CR119. All other sqls in "constructDealSQL" method
	// already retrieving the status field
	// except "SELECT_DEAL_TRX" sql therefore the status has been added; status
	// is currently not used for any purpose
	// even though it is populated.
	private static String SELECT_DEAL_TRX = "select TRANSACTION_ID, CATEGORY, PRODUCT_TYPE, PRODUCT_SUB_TYPE, CMS_CMDT_DEAL.STATUS DEAL_STATUS "
			+ "from TRANSACTION, CMS_STAGE_CMDT_DEAL, CMS_CMDT_PROFILE, SCI_LSP_APPR_LMTS, "
			+ "SCI_LSP_LMT_PROFILE, SCI_LE_SUB_PROFILE "
			+ "where TRANSACTION_TYPE = 'COMMODITY_DEAL' and "
			+ "(TRANSACTION.STATUS = 'PENDING_CREATE_VERIFY' or "
			+ "TRANSACTION.STATUS = 'PENDING_UPDATE_VERIFY' or "
			+ "TRANSACTION.STATUS = 'PENDING_CLOSE_VERIFY') and "
			+ "TO_GROUP_TYPE_ID <> 13 and STAGING_REFERENCE_ID = DEAL_ID and "
			+ "CNTR_PROFILE_ID = PROFILE_ID and "
			+ "CMS_STAGE_CMDT_DEAL.CMS_LSP_APPR_LMTS_ID = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
			+ "and SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
			+ "and SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID = SCI_LE_SUB_PROFILE.CMS_LE_SUB_PROFILE_ID";

	private static String SELECT_CLOSED_DEAL_TRX = "SELECT "
			+ "			 CMS_CMDT_DEAL.DEAL_NO, "
			+ "			 CMS_CMDT_DEAL.DEAL_ID, "
			+ "			 DEAL_TYPE, "
			+ "			 PRODUCT_TYPE, "
			+ "			 PRODUCT_SUB_TYPE, "
			+ "			 CATEGORY, "
			+ "			 TRX_REF_NO, "
			+ "			 ORIG_FACE_AMT_CCY, "
			+ "			 ORIG_FACE_AMT, "
			+ "			 FINANCE_PCT, "
			+ "			 ACT_PRICE_CCY, "
			+ "			 ACT_PRICE, "
			+ "			 CMS_CMDT_DEAL.CASH_REQ_PCT DEAL_CASH_REQ_PCT, "
			+ "			 DEAL_CMV, "
			+ "			 DEAL_CMV_CCY, "
			+ "			 DEAL_FSV, "
			+ "			 DEAL_FSV_CCY, "
			+ "			 ACT_QTY, "
			+ "			 CNTR_QTY_UOM_ID, "
			+ "			 CNTR_PRICE_TYPE, "
			+ "			 ACT_COMMON_DIFF_SIGN, "
			+ "			 ACT_COMMON_DIFF, "
			+ "			 ACT_COMMON_DIFF_CCY, "
			+ "			 ACT_EOD_CUST_DIFF_SIGN, "
			+ "			 ACT_EOD_CUST_DIFF, "
			+ "			 ACT_EOD_CUST_DIFF_CCY, "
			+ "			 CNTR_QTY_UOM_ID, "
			+ "			 CNTR_MKT_UOM_ID, "
			+ "			 CNTR_MKT_UOM_CONVERT_QTY, "
			+ "			 CMS_CMDT_DEAL.STATUS DEAL_STATUS, "
			+ "			 CMS_LIMIT_SECURITY_MAP.CASH_REQ_PCT CUST_CASH_REQ_PCT, "
			+ "			 DEPOSIT_CCY, "
			+ "			 DEPOSIT_AMT, "
			+ "			 DEPOSIT_TYPE, "
			+ "			 CMS_DEAL_CASH.STATUS CASH_STATUS "
			+ "	FROM  CMS_CMDT_DEAL  LEFT OUTER JOIN  CMS_DEAL_CASH  ON  CMS_CMDT_DEAL.DEAL_ID  = CMS_DEAL_CASH.DEAL_ID  , "
			+ "		 CMS_CMDT_PROFILE, " + "		 SCI_LSP_APPR_LMTS, " + "		 SCI_LSP_LMT_PROFILE, "
			+ "		 CMS_LIMIT_SECURITY_MAP, " + "		 TRANSACTION  "
			+ "	WHERE	 CMS_CMDT_DEAL.CNTR_PROFILE_ID  = CMS_CMDT_PROFILE.PROFILE_ID "
			+ "	 AND	CMS_CMDT_DEAL.CMS_LSP_APPR_LMTS_ID  = SCI_LSP_APPR_LMTS.CMS_LSP_APPR_LMTS_ID "
			+ "	 AND	SCI_LSP_APPR_LMTS.CMS_LIMIT_PROFILE_ID  = SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID "
			+ "	 AND	CMS_CMDT_DEAL.CMS_LSP_APPR_LMTS_ID  = CMS_LIMIT_SECURITY_MAP.CMS_LSP_APPR_LMTS_ID "
			+ "	 AND	CMS_CMDT_DEAL.CMS_COLLATERAL_ID  = CMS_LIMIT_SECURITY_MAP.CMS_COLLATERAL_ID "
			+ "	 AND	SCI_LSP_LMT_PROFILE.CMS_CUSTOMER_ID  = ? "
			+ "	 AND	SCI_LSP_LMT_PROFILE.CMS_LSP_LMT_PROFILE_ID  = ? "
			+ "	 AND	TRANSACTION.REFERENCE_ID  = CMS_CMDT_DEAL.DEAL_ID "
			+ "	 AND	TRANSACTION.TRANSACTION_TYPE  = 'COMMODITY_DEAL' " + "	 AND	TRANSACTION.STATUS  = 'CLOSED' "
			+ "	 AND	CMS_CMDT_DEAL.STATUS  = 'CLOSED' "
			// For DB2
//			+ "	 AND	(DAYS(CURRENT TIMESTAMP) - DAYS(TRANSACTION_DATE))  <= 30 ";
			// For Oracle
			+ "	 AND	(TRUNC(CURRENT_TIMESTAMP) - TRUNC(TRANSACTION_DATE))  <= 30 ";

	private static final String DEAL_CASH_REQ_PCT_ALIAS = "DEAL_CASH_REQ_PCT";

	private static final String CUST_CASH_REQ_PCT_ALIAS = "CUST_CASH_REQ_PCT";

	private static final String DEAL_STATUS_ALIAS = "DEAL_STATUS";

	private static final String CASH_STATUS_ALIAS = "CASH_STATUS";

	private static final String LIMIT_SECURITY_MAP_TABLE = ICollateralDAOConstants.LIMIT_SECURITY_MAP_TABLE;

	private static final String MAP_COLLATERAL_ID = ICollateralDAOConstants.MAP_COLLATERAL_ID;

	private static final String MAP_CASH_REQ_PCT = ICollateralDAOConstants.MAP_CASH_REQ_PCT;

	private static final String MAP_LIMIT_ID = ICollateralDAOConstants.MAP_LIMIT_ID;

	// table name and column names for approved limits
	private static final String LIMIT_TABLE = ILimitDAOConstant.LIMIT_TABLE;

	private static final String LIMIT_ID = ILimitDAOConstant.LIMIT_ID;

	private static final String LIMIT_PROFILE_FK = ILimitDAOConstant.LIMIT_PROFILE_FK;

	// table name and column names for limit profile
	private static final String LIMIT_PROFILE_TABLE = ILimitDAOConstant.LIMIT_PROFILE_TABLE;

	private static final String LIMIT_PROFILE_ID = ILimitDAOConstant.LIMIT_PROFILE_ID;

	private static final String LIMIT_PROFILE_CUST_FK = "CMS_CUSTOMER_ID";

	// table name and column names for customer.
	private static final String CUSTOMER_TABLE = "SCI_LE_SUB_PROFILE";

	private static final String CUSTOMER_ID = "CMS_LE_SUB_PROFILE_ID";

	protected static final String CUSTOMER_LE_REF = "LSP_LE_ID";

	private static final String CUST_LOCATION_TABLE = "CUSTOMER_LOCATION_VIEW";

	private static final String CUST_LOCATION_CUST_ID = "CUSTOMERID";

	private static final String CUST_LOCATION_ORIG_CTRY = "CMS_ORIG_COUNTRY";

	private static final String CUST_LOCATION_PROF_ORG = "PROF_ORG";

	private static final String CUST_LOCATION_LIMIT_CTRY = "LIMIT_COUNTRY";

	private static final String CUST_LOCATION_SECURITY_LOCATION = "SECURITY_LOCATION";

	/**
	 * Default Constructor
	 */
	public CommodityDealDAO() {
	}

	/**
	 * Get all deals that belong to a commodity.
	 * 
	 * @param collateralID collateral id
	 * @return a list of commodity deals
	 * @throws SearchDAOException on error searching the commodity deal
	 */
	private ArrayList getCommodityDeals(long collateralID) throws SearchDAOException {
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			dbUtil.setSQL(SELECT_DEAL_BY_COLID);
			dbUtil.setLong(1, collateralID);
			ResultSet rs = dbUtil.executeQuery();
			ArrayList list = new ArrayList();
			while (rs.next()) {
				ICommodityDeal deal = new OBCommodityDeal();
				deal.setCollateralID(collateralID);
				deal.setDealNo(rs.getString(DEAL_NO));
				deal.setCommodityDealID(rs.getLong(DEAL_ID));
				deal.setCMV(getAmount(rs.getBigDecimal(DEAL_CMV), rs.getString(DEAL_CMV_CCY)));
				deal.setFSV(getAmount(rs.getBigDecimal(DEAL_FSV), rs.getString(DEAL_FSV_CCY)));
				list.add(deal);
			}
			rs.close();
			return list;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting collateral subtypes by type code: ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Search commodity deal based on the given criteria.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result that contains a list of OBCommodityDealSearchResult
	 * @throws SearchDAOException on error searching the deal
	 */
	public SearchResult searchDeal(CommodityDealSearchCriteria criteria) throws SearchDAOException {
		if (criteria == null) {
			throw new SearchDAOException("CommodityDealSearchCriteria is null.");
		}

		if (criteria.isSearchByColID()) {
			return new SearchResult(0, 0, 0, this.getCommodityDeals(criteria.getCollateralID()));
		}

		DBUtil dbUtil = null;
		try {
			String selectSQL = constructDealSQL(criteria);

			// DefaultLogger.debug(this, "<<<< selectSQL: "+selectSQL);

			dbUtil = new DBUtil();
			dbUtil.setSQL(selectSQL);
			ResultSet rs = dbUtil.executeQuery();
			Vector dealList = processDealResultSet(criteria, rs);
			rs.close();
			Vector filteredDeal = filteredSearchedResult(criteria, dealList);

			if (!criteria.isPendingOfficerApproval()) {
				setDealTitleDocInfo(dbUtil, filteredDeal);
				getReceiptReleases(filteredDeal);
			}
			return new SearchResult(criteria.getStartIndex(), filteredDeal.size(), dealList.size(), filteredDeal);
		}
		catch (DAPFilterException e) {
			DefaultLogger
					.warn(this,
							"The Team in context does not have country and/or organisation defined. No results will be returned.");
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Exception from searchDeal method ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Search closed commodity deal based on the given criteria.
	 * 
	 * @param criteria of type CommodityDealSearchCriteria
	 * @return search result that contains a list of OBCommodityDealSearchResult
	 * @throws SearchDAOException on error searching the deal
	 */
	public SearchResult searchClosedDeal(CommodityDealSearchCriteria criteria) throws SearchDAOException {
		if (criteria == null) {
			throw new SearchDAOException("CommodityDealSearchCriteria is null.");
		}

		if (criteria.isSearchByColID()) {
			return new SearchResult(0, 0, 0, this.getCommodityDeals(criteria.getCollateralID()));
		}

		DBUtil dbUtil = null;
		try {
			// String selectSQL = constructDealSQL (criteria);
			String selectSQL = SELECT_CLOSED_DEAL_TRX;
			dbUtil = new DBUtil();
			dbUtil.setSQL(selectSQL);
			dbUtil.setLong(1, criteria.getCustomerID());
			dbUtil.setLong(2, criteria.getLimitProfileID());

			ResultSet rs = dbUtil.executeQuery();
			Vector dealList = processClosedDealResultSet(criteria, rs);
			rs.close();
			Vector filteredDeal = filteredSearchedResult(criteria, dealList);

			if (!criteria.isPendingOfficerApproval()) {
				setDealTitleDocInfo(dbUtil, filteredDeal);
				getReceiptReleases(filteredDeal);
			}
			return new SearchResult(criteria.getStartIndex(), filteredDeal.size(), dealList.size(), filteredDeal);
		}
		catch (DAPFilterException e) {
			DefaultLogger
					.warn(this,
							"The Team in context does not have country and/or organisation defined. No results will be returned.");
			return null;
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Exception from searchClosedDeal method ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Retrieve the SCI LE ID given the customer ID.
	 * 
	 * @return long
	 * @throws SearchDAOException if no records found
	 * @throws com.integrosys.cms.app.customer.bus.CustomerException on errors
	 */
	public long searchSCILEID(long customerID) throws CustomerException, SearchDAOException {
		DBUtil dbUtil = null;
		ResultSet rs = null;

		try {
			dbUtil = new DBUtil();

			String sql = SELECT_SCI_LEID_BY_CUSTOMER_ID;
			dbUtil.setSQL(sql);
			dbUtil.setLong(1, customerID);

			rs = dbUtil.executeQuery();

			if (rs.next()) {
				return rs.getLong(1);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "Exception from searchSCILEID method!", e);
			throw new SearchDAOException("Exception from searchSCILEID method", e);
		}
		finally {
			finalize(dbUtil);
		}
		return ICMSConstant.LONG_INVALID_VALUE;
	}

	/**
	 * Set the deal title doc secure status.
	 * 
	 * @param dealVec a list of ICommodityDealSearchResult objects
	 * @throws SearchDAOException on error setting the secure status
	 */
	private void setDealTitleDocInfo(DBUtil dbUtil, Vector dealVec) throws SearchDAOException {
		try {
			if ((dealVec == null) || (dealVec.size() == 0)) {
				return;
			}

			String selectSQL = SELECT_DEAL_TITLE_DOC;
			dbUtil.setSQL(selectSQL);
			int size = dealVec.size();

			for (int i = 0; i < size; i++) {
				ICommodityDealSearchResult deal = (ICommodityDealSearchResult) dealVec.elementAt(i);
				DefaultLogger.debug(this, " Deal ID:" + deal.getDealID());
				dbUtil.setLong(1, deal.getDealID());
				dbUtil.setLong(2, deal.getDealID());
				ResultSet rs = dbUtil.executeQuery();
				setTitleDocInfo(rs, deal);
				rs.close();
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Exception from setDealSecureStatus method ", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Get all deal warehouse receipt's releases.
	 * 
	 * @param dealCol a Collection of ICommodityDealSearchResult
	 * @throws SearchDAOException on error getting the releases
	 */
	private void getReceiptReleases(Collection dealCol) throws SearchDAOException {
		String[] sqls = constructReceiptReleasesSQL(dealCol);
		DBUtil dbUtil = null;
		try {
			dbUtil = new DBUtil();
			for (int i = 0; i < sqls.length; i++) {
				DefaultLogger.debug(this, sqls[i]);
				dbUtil.setSQL(sqls[i]);
				ResultSet rs = dbUtil.executeQuery();
				processReceiptReleases(rs, dealCol);
			}
		}
		catch (Exception e) {
			DefaultLogger.error(this, "", e);
			throw new SearchDAOException("Error in getting receipt releases!", e);
		}
		finally {
			finalize(dbUtil);
		}
	}

	/**
	 * Helper method to construct deal sql given the criteria.
	 * 
	 * @param criteria commodity deal search criteria
	 * @return sql
	 */
	private String constructDealSQL(CommodityDealSearchCriteria criteria) throws DAPFilterException {
		String dealNo = criteria.getDealNo();
		ITrxContext ctx = criteria.getTrxContext();

		StringBuffer buf = new StringBuffer();

		if (criteria.isPendingOfficerApproval()) {
			if ((dealNo != null) && (dealNo.length() != 0)) {
				buf.append(SELECT_DEAL_TRX).append(" and ");
				buf.append(DEAL_NO).append(" = '");
				buf.append(dealNo.toUpperCase()).append("' ");
			}
			else if ((criteria.getTrxID() != null) && (criteria.getTrxID().length() != 0)) {
				buf.append(SELECT_DEAL_TRX).append(" and ");
				buf.append(ICMSTrxTableConstants.TRXTBL_CUR_TRX_HISTORY_ID).append(" = '");
				buf.append(criteria.getTrxID()).append("' ");
			}
		}
		else {
			if ((dealNo != null) && (dealNo.length() != 0)) {
				buf.append(SELECT_COMMODITY_DEAL);
				buf.append(" and ");
				buf.append(DEAL_NO);
				buf.append(" = '");
				buf.append(dealNo.toUpperCase());
				buf.append("' ");
				buf.append(" UNION ");
				buf.append(SELECT_COMMODITY_CO_DEAL);
				buf.append(" and ");
				buf.append(DEAL_NO);
				buf.append(" = '");
				buf.append(dealNo.toUpperCase());
				buf.append("' ");
			}

			if ((criteria.getCustomerID() != ICMSConstant.LONG_INVALID_VALUE)
					&& (criteria.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE)) {
				buf.append(SELECT_CUSTOMER_DEALS);
				buf.append(" and ");
				buf.append(LIMIT_PROFILE_TABLE);
				buf.append(".");
				buf.append(LIMIT_PROFILE_CUST_FK);
				buf.append(" = ");
				buf.append(criteria.getCustomerID());
				buf.append(" and ");
				buf.append(LIMIT_PROFILE_TABLE).append(".").append(LIMIT_PROFILE_ID);
				buf.append(" = ");
				buf.append(criteria.getLimitProfileID());

				buf.append(" union ");

				buf.append(SELECT_CUSTOMER_CB_DEALS);
				buf.append(" and ");
				buf.append(LIMIT_PROFILE_TABLE);
				buf.append(".");
				buf.append(LIMIT_PROFILE_CUST_FK);
				buf.append(" = ");
				buf.append(criteria.getCustomerID());
				buf.append(" and ");
				buf.append(LIMIT_PROFILE_TABLE).append(".").append(LIMIT_PROFILE_ID);
				buf.append(" = ");
				buf.append(criteria.getLimitProfileID());

				ctx = null;
			}
		}

		if (ctx != null) {
			ITeam team = ctx.getTeam();
			String[] country = team.getCountryCodes();
			String[] org = team.getOrganisationCodes();

			String countryList = getSQLList(country);
			String orgList = getSQLList(org);

			if (!isEmpty(countryList) && !isEmpty(orgList)) {
				buf.append(getDAPFilterSQL(countryList, orgList));
			}
			else {
				throw new DAPFilterException("Country or Organisation List in Team is empty.");
			}
		}

		appendOrderBy(criteria, buf);

		return buf.toString();
	}

	private String getDAPFilterSQL(String countryList, String orgList) {
		StringBuffer buf = new StringBuffer();
		buf.append(" and ");
		buf.append(" exists ( select ");
		buf.append(CUST_LOCATION_CUST_ID);
		buf.append(" from ");
		buf.append(CUST_LOCATION_TABLE);
		buf.append(" where ");
		buf.append(CUST_LOCATION_CUST_ID);
		buf.append(" = ");
		buf.append(CUSTOMER_TABLE);
		buf.append(".");
		buf.append(CUSTOMER_ID);
		buf.append(" and ((");
		buf.append(CUST_LOCATION_ORIG_CTRY);
		buf.append(" in ");
		buf.append(countryList);
		buf.append(" and ");
		buf.append(CUST_LOCATION_PROF_ORG);
		buf.append(" in ");
		buf.append(orgList);
		buf.append(") or (");
		buf.append(CUST_LOCATION_LIMIT_CTRY);
		buf.append(" in ");
		buf.append(countryList);
		buf.append(" and ");
		buf.append(CUST_LOCATION_PROF_ORG);
		buf.append(" in ");
		buf.append(orgList);
		buf.append(") or ");
		buf.append(CUST_LOCATION_SECURITY_LOCATION);
		buf.append(" in ");
		buf.append(countryList);
		buf.append(" )) ");

		return buf.toString();
	}

	/**
	 * Helper method to construct sql for WR releases.
	 * 
	 * @param dealCol a Collection of ICommodityDealSearchResult
	 * @return sqls
	 */
	private String[] constructReceiptReleasesSQL(Collection dealCol) {
		StringBuffer buf = new StringBuffer();
		buf.append(SELECT_RELEASES);
		buf.append(" where ");
		buf.append(RELEASE_DEAL_ID);
		buf.append(" in ");

		String[] sqls = getSQLStatements(buf.toString(), (ICommodityDealSearchResult[]) dealCol
				.toArray(new ICommodityDealSearchResult[0]));
		return sqls;
	}

	/**
	 * Helper method to process the result set of WR's releases.
	 * 
	 * @param rs result set
	 * @param dealCol a Collection of ICommodityDealSearchResult objects
	 * @throws SQLException on error processing the result set
	 */
	private void processReceiptReleases(ResultSet rs, Collection dealCol) throws SQLException {
		ArrayList releaseList = new ArrayList();
		HashMap releaseMap = new HashMap();

		while (rs.next()) {
			String status = rs.getString(RELEASE_STATUS);
			if ((status != null) && status.equals(ICMSConstant.STATE_DELETED)) {
				continue;
			}

			Long dealID = new Long(rs.getLong(RELEASE_DEAL_ID));

			OBReceiptRelease release = new OBReceiptRelease();
			release.setReleasedQty(getQuantity(rs.getBigDecimal(RELEASE_QTY), rs.getString(RELEASE_QTY_UOM)));
			release.setStatus(status);
			releaseList = (ArrayList) releaseMap.get(dealID);
			if (releaseList == null) {
				releaseList = new ArrayList();
			}
			releaseList.add(release);
			releaseMap.put(dealID, releaseList);
		}
		rs.close();

		Iterator i = dealCol.iterator();

		OBCommodityDeal mainDeal = new OBCommodityDeal();

		while (i.hasNext()) {
			ICommodityDealSearchResult deal = (ICommodityDealSearchResult) i.next();
			Long dealID = new Long(deal.getDealID());
			releaseList = (ArrayList) releaseMap.get(dealID);
			IReceiptRelease[] releases = null;
			if (releaseList != null) {
				releases = (IReceiptRelease[]) releaseList.toArray(new IReceiptRelease[0]);
				mainDeal.setReceiptReleases(releases);
				Quantity totalReleased = mainDeal.getTotalQuantityReleased();
				if ((deal.getQuantity() != null) && (totalReleased != null)) {
					deal.setQuantity(new Quantity(deal.getQuantity().getQuantity()
							.subtract(totalReleased.getQuantity()), deal.getQuantity().getUnitofMeasure()));
				}
			}
		}
	}

	/**
	 * Helper method to process the result set.
	 * 
	 * @param rs result set
	 * @return a vector of OBCommodityDealSearchResult objects
	 * @throws SQLException on error processing the result set
	 */
	private Vector processDealResultSet(CommodityDealSearchCriteria criteria, ResultSet rs) throws SQLException,
			Exception {
		Vector list = new Vector();
		HashMap hmap = new HashMap();
		OBCommodityDeal mainDeal = new OBCommodityDeal();

		// search by FAM thru Task Re-Assignment link
		if (criteria.isPendingOfficerApproval()) {
			if (rs.next()) {
				OBCommodityDealSearchResult sr = new OBCommodityDealSearchResult();
				sr.setTrxID(rs.getString(ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID));
				sr.setCategoryCode(rs.getString(PROFILE_CATEGORY));
				sr.setProdTypeCode(rs.getString(PROFILE_PRODUCT_TYPE));
				sr.setProdSubtypeCode(rs.getString(PROFILE_PRODUCT_SUBTYPE));
				list.add(sr);
			}
			return list;
		}

		// this part is for normal search
		while (rs.next()) {
			Long dealID = new Long(rs.getLong(DEAL_ID));
			ICommodityDealSearchResult deal = (ICommodityDealSearchResult) hmap.get(dealID);

			if (deal == null) {
				deal = new OBCommodityDealSearchResult();
				deal.setCustomerID(criteria.getCustomerID());
				String status = rs.getString(DEAL_STATUS_ALIAS);
				if ((status != null) && status.equals(ICMSConstant.STATE_CLOSED)) {
					if (criteria.getCustomerID() != ICMSConstant.LONG_INVALID_VALUE) {
						continue; // ignore the closed deals
					}
					deal.setIsDealClosed(true);
				}

				deal.setDealID(rs.getLong(DEAL_ID));
				deal.setDealNo(rs.getString(DEAL_NO));
				deal.setDealTypeCode(rs.getString(DEAL_TYPE));
				deal.setCategoryCode(rs.getString(PROFILE_CATEGORY));
				deal.setProdTypeCode(rs.getString(PROFILE_PRODUCT_TYPE));
				deal.setProdSubtypeCode(rs.getString(PROFILE_PRODUCT_SUBTYPE));
				deal.setDealReferenceNo(rs.getString(DEAL_REF_NO));
				deal.setDealCashReqPct(getDoubleValue(rs.getBigDecimal(DEAL_CASH_REQ_PCT_ALIAS)));
				deal.setDealStatus(rs.getString(DEAL_STATUS_ALIAS)); // added
																		// for
																		// CR119

				String priceType = rs.getString(DEAL_CNTR_PRICE_TYPE);
				deal.setPriceType(priceType == null ? null : priceType.trim());
				String ccy = rs.getString(DEAL_FACE_VALUE_CCY);

				mainDeal.setOrigFaceValue(getAmount(rs.getBigDecimal(DEAL_FACE_VALUE), rs
						.getString(DEAL_FACE_VALUE_CCY)));
				mainDeal.setFinancingPct(getDoubleValue(rs.getBigDecimal(DEAL_FINANCING_PCT)));

				// just use the main deal formula for simplicity.
				deal.setDealAmt(mainDeal.getDealAmt());

				deal.setCashReqAmt(getPctAmount(deal.getDealAmt().getAmountAsBigDecimal(), deal.getDealCashReqPct(),
						ccy));
				deal
						.setMarketPrice(getAmount(rs.getBigDecimal(DEAL_MARKET_PRICE), rs
								.getString(DEAL_MARKET_PRICE_CCY)));
				deal.setDealCMV(getAmount(rs.getBigDecimal(DEAL_CMV), rs.getString(DEAL_CMV_CCY)));
				deal.setDealFSV(getAmount(rs.getBigDecimal(DEAL_FSV), rs.getString(DEAL_FSV_CCY)));
				deal.setQuantity(getQuantity(rs.getBigDecimal(DEAL_ACT_QTY), rs.getString(DEAL_CNTR_QTY_UOM)));

				// CMSSP-459 : rate to convert from contract qty uom to contract
				// mkt uom where applicable was not populated
				// that's why face value was always 0
				mainDeal.setDealNo(deal.getDealNo());
				String cntr_qty_uom = rs.getString("CNTR_QTY_UOM_ID");
				String cntr_mkt_uom = rs.getString("CNTR_MKT_UOM_ID");
				BigDecimal cntr_mkt_uom_conversion_rate = rs.getBigDecimal("CNTR_MKT_UOM_CONVERT_QTY");

				QuantityConversionRate rate = null;
				if (cntr_qty_uom == null) {
					rate = null;
				}
				else {
					if ((cntr_mkt_uom == null) && (cntr_mkt_uom_conversion_rate == null)) {
						rate = new QuantityConversionRate(new ConversionKey(cntr_qty_uom, cntr_qty_uom), 1);
					}
					else {
						rate = new QuantityConversionRate(new ConversionKey(cntr_qty_uom, cntr_mkt_uom),
								cntr_mkt_uom_conversion_rate.doubleValue());
					}
				}

				mainDeal.setContractMarketUOMConversionRate(rate);

				mainDeal.setActualQuantity(deal.getQuantity());
				mainDeal.setActualPrice(deal.getMarketPrice());
				mainDeal.setActualCommonDifferential(getPriceDifferential(rs.getString(DEAL_ACT_DIFF_SIGN), rs
						.getBigDecimal(DEAL_ACT_DIFF), rs.getString(DEAL_ACT_DIFF_CCY)));
				mainDeal.setActualEODCustomerDifferential(getPriceDifferential(rs.getString(DEAL_CUST_DIFF_SIGN), rs
						.getBigDecimal(DEAL_CUST_DIFF), rs.getString(DEAL_CUST_DIFF_CCY)));

				// just use the main deal formula to get face value amount
				try {
					deal.setFaceValueAmt(mainDeal.getFaceValueAmt());
				}
				catch (Exception e) {
					deal.setFaceValueAmt(new Amount(ICMSConstant.DOUBLE_INVALID_VALUE, ""));
				}
			}

			if (criteria.getDealNo() != null) {
				deal.setLimitProfileID(rs.getLong(LIMIT_PROFILE_ID));
				deal.setCustomerID(rs.getLong(CUSTOMER_ID));
			}

			if ((criteria.getCustomerID() != ICMSConstant.LONG_INVALID_VALUE)
					&& (criteria.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE)) {
				deal.setCustCashReqPct(getDoubleValue(rs.getBigDecimal(CUST_CASH_REQ_PCT_ALIAS)));

				String cashType = rs.getString(CASH_DEPOSIT_TYPE);
				if (cashType != null) {
					String cashStatus = rs.getString(CASH_STATUS_ALIAS);

					if ((cashStatus == null) || cashStatus.equals(ICMSConstant.STATE_ACTIVE)) {

						Amount newAmt = getAmount(rs.getBigDecimal(CASH_DEPOSIT_AMT), rs.getString(CASH_DEPOSIT_CCY));

						if (cashType.equals(ICMSConstant.CASH_TYPE_REQUIREMENT)) {
							Amount totalAmt = deal.getActualCashReqRecdAmt();
							deal.setActualCashReqRecdAmt(sumAmount(totalAmt, newAmt, deal.getDealAmt()));
						}
						else if (cashType.equals(ICMSConstant.CASH_TYPE_SETOFF)) {
							Amount totalAmt = deal.getActualCashSetOffRecdAmt();
							deal.setActualCashSetOffRecdAmt(sumAmount(totalAmt, newAmt, deal.getDealAmt()));
						}
						else if (cashType.equals(ICMSConstant.CASH_TYPE_COMFORT)) {
							Amount totalAmt = deal.getActualCashComfortRecdAmt();
							deal.setActualCashComfortRecdAmt(sumAmount(totalAmt, newAmt, deal.getDealAmt()));
						}
					}
				}
			}
			hmap.put(dealID, deal);
		}

		ICommodityDealSearchResult[] results = (ICommodityDealSearchResult[]) hmap.values().toArray(
				new OBCommodityDealSearchResult[0]);
		DealSearchComparator comp = new DealSearchComparator();
		Arrays.sort(results, comp);

		for (int i = 0; i < results.length; i++) {
			list.add(results[i]);
		}
		return list;
	}

	/**
	 * Helper method to process the result set.
	 * 
	 * @param rs result set
	 * @return a vector of OBCommodityDealSearchResult objects
	 * @throws SQLException on error processing the result set
	 */
	private Vector processClosedDealResultSet(CommodityDealSearchCriteria criteria, ResultSet rs) throws SQLException,
			Exception {
		Vector list = new Vector();
		HashMap hmap = new HashMap();
		OBCommodityDeal mainDeal = new OBCommodityDeal();

		/*
		 * //only cmt-maker can access the closed deals // search by FAM thru
		 * Task Re-Assignment link if (criteria.isPendingOfficerApproval()) { if
		 * (rs.next()) { OBCommodityDealSearchResult sr = new
		 * OBCommodityDealSearchResult(); sr.setTrxID (rs.getString
		 * (ICMSTrxTableConstants.TRXTBL_TRANSACTION_ID)); sr.setCategoryCode
		 * (rs.getString (PROFILE_CATEGORY)); sr.setProdTypeCode (rs.getString
		 * (PROFILE_PRODUCT_TYPE)); sr.setProdSubtypeCode (rs.getString
		 * (PROFILE_PRODUCT_SUBTYPE)); list.add (sr); } return list; }
		 */

		int z = 0;
		// this part is for normal search
		while (rs.next()) {
			Long dealID = new Long(rs.getLong(DEAL_ID));
			ICommodityDealSearchResult deal = (ICommodityDealSearchResult) hmap.get(dealID);

			if (deal == null) {
				deal = new OBCommodityDealSearchResult();
				deal.setCustomerID(criteria.getCustomerID());
				String status = rs.getString(DEAL_STATUS_ALIAS);

				/*
				 * //only closed deals are selected if (status != null &&
				 * !(status.equals (ICMSConstant.STATE_CLOSED))) { if
				 * (criteria.getCustomerID() != ICMSConstant.LONG_INVALID_VALUE)
				 * { continue; // ignore the closed deals } continue; }
				 */

				deal.setIsDealClosed(true); // only closed deals should be
											// processed
				deal.setDealID(rs.getLong(DEAL_ID));
				deal.setDealNo(rs.getString(DEAL_NO));
				deal.setDealTypeCode(rs.getString(DEAL_TYPE));
				deal.setCategoryCode(rs.getString(PROFILE_CATEGORY));
				deal.setProdTypeCode(rs.getString(PROFILE_PRODUCT_TYPE));
				deal.setProdSubtypeCode(rs.getString(PROFILE_PRODUCT_SUBTYPE));
				deal.setDealReferenceNo(rs.getString(DEAL_REF_NO));
				deal.setDealCashReqPct(getDoubleValue(rs.getBigDecimal(DEAL_CASH_REQ_PCT_ALIAS)));
				deal.setDealStatus(rs.getString(DEAL_STATUS_ALIAS)); // added
																		// for
																		// CR119

				String priceType = rs.getString(DEAL_CNTR_PRICE_TYPE);
				deal.setPriceType(priceType == null ? null : priceType.trim());
				String ccy = rs.getString(DEAL_FACE_VALUE_CCY);

				mainDeal.setOrigFaceValue(getAmount(rs.getBigDecimal(DEAL_FACE_VALUE), rs
						.getString(DEAL_FACE_VALUE_CCY)));
				mainDeal.setFinancingPct(getDoubleValue(rs.getBigDecimal(DEAL_FINANCING_PCT)));

				// just use the main deal formula for simplicity.
				deal.setDealAmt(mainDeal.getDealAmt());

				deal.setCashReqAmt(getPctAmount(deal.getDealAmt().getAmountAsBigDecimal(), deal.getDealCashReqPct(),
						ccy));
				deal
						.setMarketPrice(getAmount(rs.getBigDecimal(DEAL_MARKET_PRICE), rs
								.getString(DEAL_MARKET_PRICE_CCY)));
				deal.setDealCMV(getAmount(rs.getBigDecimal(DEAL_CMV), rs.getString(DEAL_CMV_CCY)));
				deal.setDealFSV(getAmount(rs.getBigDecimal(DEAL_FSV), rs.getString(DEAL_FSV_CCY)));
				deal.setQuantity(getQuantity(rs.getBigDecimal(DEAL_ACT_QTY), rs.getString(DEAL_CNTR_QTY_UOM)));

				// CMSSP-459 : rate to convert from contract qty uom to contract
				// mkt uom where applicable was not populated
				// that's why face value was always 0
				mainDeal.setDealNo(deal.getDealNo());
				String cntr_qty_uom = rs.getString("CNTR_QTY_UOM_ID");
				String cntr_mkt_uom = rs.getString("CNTR_MKT_UOM_ID");
				BigDecimal cntr_mkt_uom_conversion_rate = rs.getBigDecimal("CNTR_MKT_UOM_CONVERT_QTY");

				QuantityConversionRate rate = null;
				if (cntr_qty_uom == null) {
					rate = null;
				}
				else {
					if ((cntr_mkt_uom == null) && (cntr_mkt_uom_conversion_rate == null)) {
						rate = new QuantityConversionRate(new ConversionKey(cntr_qty_uom, cntr_qty_uom), 1);
					}
					else {
						rate = new QuantityConversionRate(new ConversionKey(cntr_qty_uom, cntr_mkt_uom),
								cntr_mkt_uom_conversion_rate.doubleValue());
					}
				}

				mainDeal.setContractMarketUOMConversionRate(rate);

				mainDeal.setActualQuantity(deal.getQuantity());
				mainDeal.setActualPrice(deal.getMarketPrice());
				mainDeal.setActualCommonDifferential(getPriceDifferential(rs.getString(DEAL_ACT_DIFF_SIGN), rs
						.getBigDecimal(DEAL_ACT_DIFF), rs.getString(DEAL_ACT_DIFF_CCY)));
				mainDeal.setActualEODCustomerDifferential(getPriceDifferential(rs.getString(DEAL_CUST_DIFF_SIGN), rs
						.getBigDecimal(DEAL_CUST_DIFF), rs.getString(DEAL_CUST_DIFF_CCY)));

				// just use the main deal formula to get face value amount
				try {
					deal.setFaceValueAmt(mainDeal.getFaceValueAmt());
				}
				catch (Exception e) {
					deal.setFaceValueAmt(new Amount(ICMSConstant.DOUBLE_INVALID_VALUE, ""));
				}
			}

			if (criteria.getDealNo() != null) {
				deal.setLimitProfileID(rs.getLong(LIMIT_PROFILE_ID));
				deal.setCustomerID(rs.getLong(CUSTOMER_ID));
			}

			if ((criteria.getCustomerID() != ICMSConstant.LONG_INVALID_VALUE)
					&& (criteria.getLimitProfileID() != ICMSConstant.LONG_INVALID_VALUE)) {
				deal.setCustCashReqPct(getDoubleValue(rs.getBigDecimal(CUST_CASH_REQ_PCT_ALIAS)));

				String cashType = rs.getString(CASH_DEPOSIT_TYPE);
				if (cashType != null) {
					String cashStatus = rs.getString(CASH_STATUS_ALIAS);

					if ((cashStatus == null) || cashStatus.equals(ICMSConstant.STATE_ACTIVE)) {

						Amount newAmt = getAmount(rs.getBigDecimal(CASH_DEPOSIT_AMT), rs.getString(CASH_DEPOSIT_CCY));

						if (cashType.equals(ICMSConstant.CASH_TYPE_REQUIREMENT)) {
							Amount totalAmt = deal.getActualCashReqRecdAmt();
							deal.setActualCashReqRecdAmt(sumAmount(totalAmt, newAmt, deal.getDealAmt()));
						}
						else if (cashType.equals(ICMSConstant.CASH_TYPE_SETOFF)) {
							Amount totalAmt = deal.getActualCashSetOffRecdAmt();
							deal.setActualCashSetOffRecdAmt(sumAmount(totalAmt, newAmt, deal.getDealAmt()));
						}
						else if (cashType.equals(ICMSConstant.CASH_TYPE_COMFORT)) {
							Amount totalAmt = deal.getActualCashComfortRecdAmt();
							deal.setActualCashComfortRecdAmt(sumAmount(totalAmt, newAmt, deal.getDealAmt()));
						}
					}
				}
			}
			hmap.put(dealID, deal);

		}

		ICommodityDealSearchResult[] results = (ICommodityDealSearchResult[]) hmap.values().toArray(
				new OBCommodityDealSearchResult[0]);
		DealSearchComparator comp = new DealSearchComparator();
		Arrays.sort(results, comp);

		for (int i = 0; i < results.length; i++) {
			list.add(results[i]);
		}
		return list;
	}

	/**
	 * Helper method to sum newAmt to totalAmt and convert it to new ccyCode.
	 * TotalAmt will be Amount with value ICMSConstant.DOUBLE_INVALID_VALUE and
	 * empty currency code if there's an amount conversion exception.
	 * 
	 * @param totalAmt total amount
	 * @param newAmt amount to be added to total amount
	 * @param dealAmt deal amount
	 */
	private Amount sumAmount(Amount totalAmt, Amount newAmt, Amount dealAmt) throws ChainedException {
		if (dealAmt == null) {
			return null;
		}

		if (newAmt != null) {
			try {
				newAmt = AmountConversion.getConversionAmount(newAmt, dealAmt.getCurrencyCode());
			}
			catch (AmountConversionException e) {
				DefaultLogger.warn(this, "Amount Conversion during search. Returns Forex error");
				newAmt = new Amount(ICMSConstant.DOUBLE_INVALID_VALUE, "");
			}
		}

		if (totalAmt == null) {
			return newAmt;
		}
		else if ((newAmt != null) && (newAmt.getAmountAsDouble() != ICMSConstant.DOUBLE_INVALID_VALUE)
				&& (newAmt.getCurrencyCode().length() != 0)) {
			return totalAmt.add(newAmt);
		}

		if ((newAmt != null) && (newAmt.getAmountAsDouble() == ICMSConstant.DOUBLE_INVALID_VALUE)
				&& (newAmt.getCurrencyCode().length() == 0)) {
			return newAmt;
		}
		else {
			return totalAmt;
		}
	}

	/**
	 * Helper method to set title document information.
	 * 
	 * @param rs result set
	 * @throws SQLException on error processing the result set
	 */
	private void setTitleDocInfo(ResultSet rs, ICommodityDealSearchResult deal) throws SQLException, Exception {
		ArrayList arrayList = new ArrayList();
		while (rs.next()) {
			OBCommodityTitleDocument doc = new OBCommodityTitleDocument();
			doc.setTitleDocID(rs.getLong(TITLE_DOC_ID));
			doc.setIsSecured(rs.getString(TITLE_DOC_IS_SECURED));
			doc.setTransactionDate(rs.getDate(TITLE_DOC_TRX_DATE));
			doc.setRefID(rs.getLong(TITLE_DOC_REF_ID));
			doc.setAdvEligibilityPct(getDoubleValue(rs.getBigDecimal(TITLE_DOC_ADV_PCT)));
			arrayList.add(doc);
		}
		OBCommodityTitleDocument[] docs = (OBCommodityTitleDocument[]) arrayList
				.toArray(new OBCommodityTitleDocument[0]);
		ICommodityTitleDocument td = OBCommodityTitleDocument.getTitleDocsLatestByTitleDocID(docs);
		if (td != null) {
			if ((td.getIsSecured() != null) && td.getIsSecured().equals(ICMSConstant.TRUE_VALUE)) {
				deal.setIsDealSecured(true);
			}
			deal.setEligibilityAdvPct(td.getAdvEligibilityPct());
		}
	}

	/**
	 * Helper method to construct ORDER BY sql for sorting purposes.
	 * 
	 * @param criteria commodity deal search criteria
	 * @param buf buffer to which the sql is appended
	 */
	private void appendOrderBy(CommodityDealSearchCriteria criteria, StringBuffer buf) {
		String firstSort = criteria.getFirstSort() == null ? "" : criteria.getFirstSort().trim();
		String secondSort = criteria.getSecondSort() == null ? "" : criteria.getSecondSort().trim();

		if (!firstSort.equals("")) {
			buf.append(" order by ");
			buf.append(firstSort);
			if (!secondSort.equals("") && !secondSort.equalsIgnoreCase(firstSort)) {
				buf.append(", ");
				buf.append(secondSort);
			}
		}
	}

	/**
	 * Filter the searched result based on the criteria given.
	 * 
	 * @param criteria deal search criteria
	 * @param dealVec a list of OBCommodityDealSearchResult objects
	 * @return a vector of OBCommodityDealSearchResult objects
	 */
	private Vector filteredSearchedResult(CommodityDealSearchCriteria criteria, Vector dealVec) {
		int startIndex = criteria.getStartIndex() - 1;
		int nItems = criteria.getNItems();

		Vector list = new Vector();
		for (int i = 0; i < dealVec.size(); i++) {
			if (i < startIndex) {
				continue;
			}
			list.add(dealVec.elementAt(i));
			if (nItems != 0) {
				if (nItems == list.size()) {
					break;
				}
			}
		}
		return list;
	}

	/**
	 * Helper method to convert the array to a sql friendly string ( in Clause).
	 * 
	 * @param aArray String array like Segment, or Country list
	 * @return String in clause sql
	 */
	private String getSQLList(String[] aArray) {
		if (aArray == null) {
			return null;
		}

		StringBuffer buf = new StringBuffer();
		buf.append("(");

		for (int i = 0; i < aArray.length; i++) {
			buf.append("'");
			buf.append(aArray[i]);
			buf.append("'");
			if (i != aArray.length - 1) {
				buf.append(",");
			}
			else {
				buf.append(")");
			}
		}
		return buf.toString();
	}

	/**
	 * Utility method to check if a string value is null or empty.
	 * 
	 * @param aValue string to be checked
	 * @return boolean true if empty and false otherwise
	 */
	private boolean isEmpty(String aValue) {
		if ((aValue != null) && (aValue.trim().length() > 0)) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Helper method to get price differential givent the sign and its price.
	 * 
	 * @param sign differential sign
	 * @param amt differential price
	 * @param ccy price currency
	 * @return PriceDifferential object
	 */
	private PriceDifferential getPriceDifferential(String sign, BigDecimal amt, String ccy) {
		if ((sign != null) && (amt != null)) {
			return new PriceDifferential(new Amount(amt, new CurrencyCode(ccy)), sign);
		}
		return null;
	}

	/**
	 * Helper method to get the percentage amount.
	 * 
	 * @param amt amount value
	 * @param pct a percent value
	 * @param ccy currency code
	 * @return percentage value
	 */
	private Amount getPctAmount(BigDecimal amt, double pct, String ccy) {
		if ((amt != null) && (pct != ICMSConstant.DOUBLE_INVALID_VALUE) && (ccy != null)) {
			BigDecimal bd = CommonUtil.calcAfterPercent(amt, pct);
			return new Amount(bd, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * Helper method to get the double value of the BigDecimal.
	 * 
	 * @param value of type BigDecimal
	 * @return ICMSConstant.DOUBLE_INVALID_VALUE if the BigDecimal is null
	 */
	private double getDoubleValue(BigDecimal value) {
		if (value == null) {
			return ICMSConstant.DOUBLE_INVALID_VALUE;
		}
		else {
			return value.doubleValue();
		}
	}

	/**
	 * Helper method to get amount object given the amount value and its
	 * currency.
	 * 
	 * @param amt amount value
	 * @param ccy currency code
	 * @return Amount object
	 */
	private Amount getAmount(BigDecimal amt, String ccy) {
		if ((amt != null) && (ccy != null)) {
			return new Amount(amt, new CurrencyCode(ccy));
		}
		return null;
	}

	/**
	 * Helper method to get quantity object given the qty and its measurement
	 * unit.
	 * 
	 * @param qty quantity value
	 * @param uom unit of measurement
	 * @return Quantity object
	 */
	private Quantity getQuantity(BigDecimal qty, String uom) {
		if ((qty != null) && (uom != null)) {
			UOMWrapper unit = UOMWrapperFactory.getInstance().valueOf(uom);
			return new Quantity(qty, unit);
		}
		return null;
	}

	/**
	 * Helper method to clean up database resources.
	 * 
	 * @param dbUtil database utility object
	 * @throws SearchDAOException error in cleaning up DB resources
	 */
	private void finalize(DBUtil dbUtil) throws SearchDAOException {
		try {
			if (dbUtil != null) {
				dbUtil.close();
			}
		}
		catch (Exception e) {
			throw new SearchDAOException("Error in cleaning up DB resources.");
		}
	}

	/**
	 * Helper method to get a list of sql statements with IN clause appended.
	 * 
	 * @param mainSQL the main sql statement
	 * @param deals a list of deals
	 * @return properr constructed SQL with IN clauses
	 */
	public static String[] getSQLStatements(String mainSQL, ICommodityDealSearchResult[] deals) {
		double maxcount = (double) MAX_IN_CLAUSE;
		double len = deals.length;
		int size = (int) Math.ceil(len / maxcount);

		StringBuffer buf;
		String[] sql = new String[size];
		int j = 0;

		for (int i = 0; i < size; i++) {
			buf = new StringBuffer();
			for (; j < len; j++) {
				if (deals[j] != null) {
					buf.append("'");
					buf.append(deals[j].getDealID());
					buf.append("'");
				}
				if ((j != maxcount * (i + 1) - 1) && (j != len - 1)) {
					if (deals[j] != null) {
						buf.append(",");
					}
				}
				else {
					j = (int) maxcount * (i + 1);
					break;
				}
			}
			String str = buf.toString();
			if (str.length() == 0) {
				sql[i] = mainSQL + "('')";
			}
			else {
				int strlen = str.length();
				if (str.substring(strlen - 1).equals(",")) {
					str = str.substring(0, strlen - 1);
				}
				sql[i] = mainSQL + "(" + str + ")";
			}
		}
		return sql;
	}

	// inner class
	private class DAPFilterException extends Exception {
		public DAPFilterException(String msg) {
			super(msg);
		}
	}
}
