/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/CommodityMainConstant.java,v 1.11 2004/11/27 04:58:19 hshii Exp $
 */
package com.integrosys.cms.ui.collateral.commodity;

/**
 * Description
 * 
 * @author $Author: hshii $<br>
 * @version $Revision: 1.11 $
 * @since $Date: 2004/11/27 04:58:19 $ Tag: $Name: $
 */

public class CommodityMainConstant {
	public static final String APPROVED_COMMODITY = "approved_commodity";

	public static final String COMMODITY_CONTRACT = "contract";

	public static final String HEDGED_CONTRACT = "hedged_contract";

	public static final String SECURITY_DETAILS = "security";

	public static final String LOAN_AGENCY = "loan_agency";

	// common code category
	public static final String FACILITY_TYPE = "FACILITY_TYPE";

	public static final String PAYMENT_FREQ = "PAYMENT_FREQ";

	public static final String CALCULATION_BASE = "CAL_BASE";

	public static final String TRX_TYPE = "TRX_TYPE";

	// security environmentally risky
	public static final String ENV_RISK_NO_RISK = "NR";

	public static final String ENV_RISK_NO_APPLICALBE = "NA";

	// amount constant
	public static final String MAX_AMOUNT_STR = "999999999999999";

	public static final double MAX_AMOUNT = Double.parseDouble(MAX_AMOUNT_STR);

	public static final String MAX_AMOUNT_15_2_STR = "999999999999999.99";

	public static final double MAX_AMOUNT_15_2 = Double.parseDouble(MAX_AMOUNT_15_2_STR);

	// Quantity constant
	public static final String MAX_QTY_STR = "999999999999999.9999";

	public static final double MAX_QTY = Double.parseDouble(MAX_QTY_STR);

	// price Differential constant
	public static final String MAX_PRICE_DIFF_STR = "9999999999999.999999";

	public static final double MAX_PRICE_DIFF = Double.parseDouble(MAX_PRICE_DIFF_STR);

	// Fee constant
	public static final String MAX_FEE_STR = "999999.99";

	public static final double MAX_FEE = Double.parseDouble(MAX_FEE_STR);

	// payment constant
	public static final String PAYMENT_FREQ_MONTHLY = "m";

	public static final String PAYMENT_FREQ_BIMONTHLY = "b";

	public static final String PAYMENT_FREQ_QUARTERLY = "q";

	public static final String PAYMENT_FREQ_SEMI_ANNUALLY = "h";

	public static final String PAYMENT_FREQ_ANNUALLY = "a";
}
