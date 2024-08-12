package com.integrosys.cms.app.contractfinancing.bus;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 22, 2007 Tag: $Name$
 */
public interface IContractFinancingTableConstants {

	public static final String CONTRACT_FINANCING_TABLE = "CMS_CONTRACT_FINANCING";

	public static final String STAGE_CONTRACT_FINANCING_TABLE = "CMS_STAGE_CONTRACT_FINANCING";

	public static final String CFTBL_CONTRACT_ID = "CONTRACT_ID";

	public static final String CFTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String CFTBL_LIMIT_ID = "CMS_LSP_APPR_LMTS_ID"; // Not
																		// from
																		// contract
																		// financing
																		// tables
																		// ->
																		// from
																		// sci_lsp_appr_lmts

	public static final String CFTBL_SOURCE_LIMIT = "LMT_ID"; // Not from
																// contract
																// financing
																// tables ->
																// from
																// sci_lsp_appr_lmts

	public static final String CFTBL_PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION"; // Not
																					// from
																					// contract
																					// financing
																					// tables
																					// -
																					// >
																					// from
																					// common_code_category_entry

	public static final String CFTBL_CONTRACT_NUMBER = "CONTRACT_NUMBER";

	public static final String CFTBL_CONTRACT_DATE = "CONTRACT_DATE";

	public static final String CFTBL_EXPIRY_DATE = "EXPIRY_DATE";

	public static final String CFTBL_EXTENDED_DATE = "EXTENDED_DATE";

	public static final String CFTBL_CONTRACT_CURRENCY = "CONTRACTED_CURRENCY";

	public static final String CFTBL_CONTRACT_AMOUNT = "CONTRACTED_AMOUNT";

	public static final String CFTBL_FINANCE_PERCENT = "FINANCE_PERCENT";

	public static final String CFTBL_CONTRACT_ID_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_CONTRACT_ID;

	public static final String CFTBL_LIMIT_PROFILE_ID_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_LIMIT_PROFILE_ID;

	public static final String CFTBL_CONTRACT_NUMBER_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_CONTRACT_NUMBER;

	public static final String CFTBL_CONTRACT_DATE_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_CONTRACT_DATE;

	public static final String CFTBL_EXPIRY_DATE_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_EXPIRY_DATE;

	public static final String CFTBL_EXTENDED_DATE_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_EXTENDED_DATE;

	public static final String CFTBL_CONTRACT_CURRENCY_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_CONTRACT_CURRENCY;

	public static final String CFTBL_CONTRACT_AMOUNT_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_CONTRACT_AMOUNT;

	public static final String CFTBL_FINANCE_PERCENT_PREF = CONTRACT_FINANCING_TABLE + "." + CFTBL_FINANCE_PERCENT;

	public static final String STAGE_CFTBL_CONTRACT_ID_PREF = STAGE_CONTRACT_FINANCING_TABLE + "." + CFTBL_CONTRACT_ID;

	public static final String STAGE_CFTBL_LIMIT_PROFILE_ID_PREF = STAGE_CONTRACT_FINANCING_TABLE + "."
			+ CFTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_CFTBL_CONTRACT_NUMBER_PREF = STAGE_CONTRACT_FINANCING_TABLE + "."
			+ CFTBL_CONTRACT_NUMBER;

	public static final String STAGE_CFTBL_CONTRACT_DATE_PREF = STAGE_CONTRACT_FINANCING_TABLE + "."
			+ CFTBL_CONTRACT_DATE;

	public static final String STAGE_CFTBL_EXPIRY_DATE_PREF = STAGE_CONTRACT_FINANCING_TABLE + "." + CFTBL_EXPIRY_DATE;

	public static final String STAGE_CFTBL_EXTENDED_DATE_PREF = STAGE_CONTRACT_FINANCING_TABLE + "."
			+ CFTBL_EXTENDED_DATE;

	public static final String STAGE_CFTBL_CONTRACT_CURRENCY_PREF = STAGE_CONTRACT_FINANCING_TABLE + "."
			+ CFTBL_CONTRACT_CURRENCY;

	public static final String STAGE_CFTBL_CONTRACT_AMOUNT_PREF = STAGE_CONTRACT_FINANCING_TABLE + "."
			+ CFTBL_CONTRACT_AMOUNT;

	public static final String STAGE_CFTBL_FINANCE_PERCENT_PREF = STAGE_CONTRACT_FINANCING_TABLE + "."
			+ CFTBL_FINANCE_PERCENT;

}
