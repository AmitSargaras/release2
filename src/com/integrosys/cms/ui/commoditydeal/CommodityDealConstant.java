/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commoditydeal/CommodityDealConstant.java,v 1.15 2006/01/16 07:13:16 pratheepa Exp $
 */
package com.integrosys.cms.ui.commoditydeal;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Description
 * 
 * @author $Author: pratheepa $<br>
 * @version $Revision: 1.15 $
 * @since $Date: 2006/01/16 07:13:16 $ Tag: $Name: $
 */

public class CommodityDealConstant {
	public static final String TITLE_DOCUMENT = "title_document";

	public static final String FINANCING_DOCUMENT = "financing_document";

	public static final String CASH_DEPOSIT = "cash_deposit";

	public static final String PURCHASE_SALE = "purchase_sale";

	public static final String SETTLEMENT = "settlement";

	public static final String RELEASE = "release";

	public static final String HEDGING = "hedging";

	// common code category constant

	public static final String SALES_DOC_DESCRIPTION = "SALE_DOC_DESC";

	public static final String CASH_TYPE = "CASH_TYPE";

	public static final String PROCESS_STAGE = "PROCESS_STAGE";

	// public static final String DEAL_PRICE = "DEAL_PRICE";

	// constant for entry code
	public static final String SALES_DOCTYPE_OTHER = "O";

	public static final String SALES_DOCTYPE_BACK_TO_BACK_LC = "B";

	/*
	 * public static final String PRICE_TYPE_EOD_CASH = "C"; public static final
	 * String PRICE_TYPE_FLOATING_FUTURE = "F"; public static final String
	 * PRICE_TYPE_FIXED_FUTURE = "X"; public static final String
	 * PRICE_TYPE_MANUAL_VALUATION_FUTURE = "M"; public static final String
	 * PRICE_TYPE_MANUAL_VALUATION_EOD = "E";
	 */

	// option constant
	public static final String OPTION_OTHER = "O";

	public static final String OPTION_YES = "true";

	public static final String OPTION_NO = "false";

	/*
	 * // deal type public static final String COLLATERAL_POOL =
	 * "collateral_pool"; public static final String SPECIFIC_TRANSACTION =
	 * "specific_transaction";
	 */
	// cash type
	public static final String CASH_TYPE_REQUIREMENT = ICMSConstant.CASH_TYPE_REQUIREMENT;

	// constant for amount
	/*
	 * public static final String MAX_AMOUNT20 = "99999999999999999999.999999";
	 * public static final String MAX_AMOUNT15 = "999999999999999.999999";
	 * public static final String MAX_AMOUNT6 = "999999.999999"; public static
	 * final double MAX_AMT20 = Double.parseDouble(MAX_AMOUNT20); public static
	 * final double MAX_AMT15 = Double.parseDouble(MAX_AMOUNT15); public static
	 * final double MAX_AMT6 = Double.parseDouble(MAX_AMOUNT6);
	 */

	public static final String MAX_AMOUNT_STR = "999999999999999";

	public static final String MAX_AMOUNT_STR_15_2 = "999999999999999.99";

	public static final String MAX_PRICE_STR = "9999999999999.999999";

	public static final String MAX_QTY_STR = "999999999999999.9999";

	public static final String MAX_QTY_DIFF_STR = "9999999999999.999999";

	public static final String MAX_PRICE_DIFF_STR = "9999999999999.999999";

	public static final double MAX_AMOUNT = Double.parseDouble(MAX_AMOUNT_STR);

	public static final double MAX_AMOUNT_15_2 = Double.parseDouble(MAX_AMOUNT_STR_15_2);

	public static final double MAX_PRICE = Double.parseDouble(MAX_PRICE_STR);

	public static final double MAX_QTY = Double.parseDouble(MAX_QTY_STR);

	public static final double MAX_QTY_DIFF = Double.parseDouble(MAX_QTY_DIFF_STR);

	public static final double MAX_PRICE_DIFF = Double.parseDouble(MAX_PRICE_DIFF_STR);

	// constant for commodity deal tab
	public static final String GENERAL_INFO = "general_info";

	public static final String DOC = "doc";

	public static final String DEAL_INFO = "deal_info";

	public static final String FINANCE = "finance";

	// Commodity Title Document type - fixed title document
	public static final String DOC_TYPE_WAREHOUSE_RECEIPT = "Warehouse Receipt";

	public static final String DOC_TYPE_BILL_LADING = "Bill of Lading";

	public static final String DOC_TYPE_TRUST_RECEIPT = "Trust Receipt";

	// Pratheepa on 10/01/2006 added these three constants while fixing R1.5
	// CR129.
	public static final String DOC_TYPE_WAREHOUSE_RECEIPT_N = "Warehouse Receipt (Negotiable)";

	public static final String DOC_TYPE_BILL_LADING_N = "Bill of Lading (Negotiable)";

	public static final String DOC_TYPE_TRUST_RECEIPT_NN = "Trust Receipt (Non-Negotiable)";

	// Tab constant
	public static final String TAB_GENERAL_INFO = "tab_general_info";

	public static final String TAB_DOCUMENT = "tab_document";

	public static final String TAB_DEAL_INFO = "tab_deal_info";

	public static final String TAB_PS_HEDGING = "tab_ps_hedging";

	// Constant for validating Deal Amount Against CMV Amount -- CR144
	public static final String CHECK_AMT_AGAINST_CMV_REQUIRED = "CHECK_AMT_AGAINST_CMV_REQUIRED";

	public static final String CHECK_AMT_AGAINST_CMV_NOT_REQUIRED = "CHECK_AMT_AGAINST_CMV_NOT_REQUIRED";

	public static final String CHECK_AMT_GREATER_THAN_CMV = "CHECK_AMT_GREATER_THAN_CMV";

	public static final String CHECK_AMT_AMOUNT_CONVERSION_ERROR = "CHECK_AMT_AMOUNT_CONVERSION_ERROR";

	public static final String DEAL_AMT_GREATER_THAN_CMV = "Care! Deal Amount is higher than the CMV of collateral. Click OK button if you wish to continue?";

	public static final String AMOUNT_CONVERSION_ERROR = "Unable to convert Deal Amount and Collateral CMV to common currency for Comparision! Click OK button if you wish to continue?";

}
