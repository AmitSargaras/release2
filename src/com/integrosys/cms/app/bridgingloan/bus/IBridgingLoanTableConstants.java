package com.integrosys.cms.app.bridgingloan.bus;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author Cynthia<br>
 * @version R1.1
 * @since Mar 23, 2007 Tag: $Name$
 */
public interface IBridgingLoanTableConstants {

	public static final String BRIDGING_LOAN_TABLE = "CMS_BRIDGING_LOAN";

	public static final String STAGE_BRIDGING_LOAN_TABLE = "CMS_STAGE_BRIDGING_LOAN";

	public static final String BLTBL_PROJECT_ID = "PROJECT_ID";

	public static final String BLTBL_LIMIT_PROFILE_ID = "CMS_LSP_LMT_PROFILE_ID";

	public static final String BLTBL_LIMIT_ID = "CMS_LSP_APPR_LMTS_ID"; // Not
																		// from
																		// bridging
																		// loan
																		// tables
																		// ->
																		// from
																		// sci_lsp_appr_lmts

	public static final String BLTBL_SOURCE_LIMIT = "LMT_ID"; // Not from
																// bridging loan
																// tables ->
																// from
																// sci_lsp_appr_lmts

	public static final String BLTBL_PRODUCT_DESCRIPTION = "PRODUCT_DESCRIPTION"; // Not
																					// from
																					// bridging
																					// loan
																					// tables
																					// -
																					// >
																					// from
																					// common_code_category_entry
																					// -
																					// entry
																					// name

	public static final String BLTBL_PROJECT_NUMBER = "PROJECT_NUMBER";

	// public static final String BLTBL_ORIG_COUNTRY = "CMS_ORIG_COUNTRY";
	public static final String BLTBL_CONTRACT_DATE = "CONTRACT_DATE";

	public static final String BLTBL_ACT_START_DATE = "ACTUAL_START_DATE";

	public static final String BLTBL_ACT_COMPLETION_DATE = "ACTUAL_COMPLETION_DATE";

	public static final String BLTBL_CONTRACT_CURRENCY = "CONTRACTED_CURRENCY";

	public static final String BLTBL_CONTRACT_AMOUNT = "CONTRACTED_AMOUNT";

	public static final String BLTBL_FINANCE_PERCENT = "FINANCE_PERCENT";

	public static final String BLTBL_PROJECT_ID_PREF = BRIDGING_LOAN_TABLE + "." + BLTBL_PROJECT_ID;

	public static final String BLTBL_LIMIT_PROFILE_ID_PREF = BRIDGING_LOAN_TABLE + "." + BLTBL_LIMIT_PROFILE_ID;

	public static final String BLTBL_CONTRACT_NUMBER_PREF = BRIDGING_LOAN_TABLE + "." + BLTBL_PROJECT_NUMBER;

	public static final String BLTBL_CONTRACT_DATE_PREF = BRIDGING_LOAN_TABLE + "." + BLTBL_CONTRACT_DATE;

	public static final String BLTBL_ACT_COMPLETION_DATE_PREF = BRIDGING_LOAN_TABLE + "." + BLTBL_ACT_COMPLETION_DATE;

	public static final String BLTBL_CONTRACT_CURRENCY_PREF = BRIDGING_LOAN_TABLE + "." + BLTBL_CONTRACT_CURRENCY;

	public static final String BLTBL_CONTRACT_AMOUNT_PREF = BRIDGING_LOAN_TABLE + "." + BLTBL_CONTRACT_AMOUNT;

	public static final String BLTBL_FINANCE_PERCENT_PREF = BRIDGING_LOAN_TABLE + "." + BLTBL_FINANCE_PERCENT;

	public static final String STAGE_BLTBL_PROJECT_ID_PREF = STAGE_BRIDGING_LOAN_TABLE + "." + BLTBL_PROJECT_ID;

	public static final String STAGE_BLTBL_LIMIT_PROFILE_ID_PREF = STAGE_BRIDGING_LOAN_TABLE + "."
			+ BLTBL_LIMIT_PROFILE_ID;

	public static final String STAGE_BLTBL_PROJECT_NUMBER_PREF = STAGE_BRIDGING_LOAN_TABLE + "." + BLTBL_PROJECT_NUMBER;

	public static final String STAGE_BLTBL_CONTRACT_DATE_PREF = STAGE_BRIDGING_LOAN_TABLE + "." + BLTBL_CONTRACT_DATE;

	public static final String STAGE_BLTBL_ACT_COMPLETION_DATE_PREF = STAGE_BRIDGING_LOAN_TABLE + "."
			+ BLTBL_ACT_COMPLETION_DATE;

	public static final String STAGE_BLTBL_CONTRACT_CURRENCY_PREF = STAGE_BRIDGING_LOAN_TABLE + "."
			+ BLTBL_CONTRACT_CURRENCY;

	public static final String STAGE_BLTBL_CONTRACT_AMOUNT_PREF = STAGE_BRIDGING_LOAN_TABLE + "."
			+ BLTBL_CONTRACT_AMOUNT;

	public static final String STAGE_BLTBL_FINANCE_PERCENT_PREF = STAGE_BRIDGING_LOAN_TABLE + "."
			+ BLTBL_FINANCE_PERCENT;

}
