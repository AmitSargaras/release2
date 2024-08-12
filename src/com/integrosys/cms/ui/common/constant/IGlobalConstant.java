/**
 * Copyright Integro Technologies Pte Ltd
 * $Header:
 */
package com.integrosys.cms.ui.common.constant;

import java.math.BigDecimal;

import com.integrosys.cms.app.common.util.CommonUtil;

/**
 * This is a Interface to store global scope constants to be used in UI
 * 
 * @author $Author: hmbao $<br>
 * @version $Revision: 1.20 $
 * @since $Date: 2006/10/27 03:04:01 $ Tag: $Name: $
 */

public interface IGlobalConstant {
	// **********************************************************************
	// *** NOTE:
	// *** THE CONSTANTS DEFINED IN THIS CLASS MUST BE UNIQUE STRINGS.
	// *** Please provide unique strings with prefix of
	// "_CMS_UI_GLOBAL_CONSTANT_"
	// *** to your constants before setting into GLOBAL_SCOPE
	// **********************************************************************

	public static final String PREFIX = "_CMS_UI_GLOBAL_CONSTANT_";

	public static final String PAGE_TITLE_NAME = "global.PAGE_TITLE_NAME";
	
	// added by goutam
	/**
	 * These constants are reserved and meant only to be used for login. Do not
	 * use this anywhere else.
	 */
	public static final String USER_TEAM = "TEAM";

	public static final String USER = "ILosUser";// make it equal to
	
	public static final String EVENT = "event";
	
	public static final String SUB_EVENT = "subEvent";
	
	public static final String REFERRER_EVENT = "referrerEvent";
	
	// GLOBAL_LOS_USER

	public static final String GLOBAL_LOS_USER = "ILosUser";

	public static final String TEAM_TYPE_MEMBERSHIP_TYPE_NAME = "TEAM_TYPE_MEMBERSHIP_TYPE_NAME";

	public static final String TEAM_TYPE_MEMBERSHIP_ID = "TEAM_TYPE_MEMBERSHIP_ID";

	public static final String CHANGE_PASSWORD_IND = "CHANGE_PASSWORD";

	public static final String CHANGE_PASSWORD = "change_pwd";

	/**
	 * These constants are reserved and meant only to be used for login. Do not
	 * use this anywhere else.
	 */
	public static final String GLOBAL_USER_LOGIN_ID = "USER_LOGIN_ID";

	/**
	 * These constants are reserved and meant only to be used for login. Do not
	 * use this anywhere else.
	 */
	public static final String GLOBAL_LASTLOGINTIME = "LASTLOGINTIME";

	/**
	 * These constants are reserved and meant only to be used for login. Do not
	 * use this anywhere else.
	 */
	public static final String GLOBAL_LASTLOGOUTTIME = "LASTLOGOUTTIME";

	/**
	 * These constants are reserved and meant only to be used for login. Do not
	 * use this anywhere else.
	 */
	public static final String GLOBAL_AUTHENTICATION_ROLE = "AUTHENTICATION_ROLE";

	/**
	 * These constants are reserved and meant only to be used for login. Do not
	 * use this anywhere else.
	 */
	public static final String GLOBAL_AUTHENTICATION_REALM = "AUTHENTICATION_REALM";

	static final String CMS_CUSTOMER = PREFIX + "cms_customer";// will not be

	// set during
	// login

	public static final String MAXIMUM_ALLOWED_AMOUNT_STR = "9999999999999";

	public static final int MAXIMUM_ALLOWED_SMALL_INTEGER = 9999;

	public static final int MAXIMUM_ALLOWED_INTEGER = 999999;
	
	public static final int MAXIMUM_ALLOWED_INTEGER_9= 999999999;

	public static final String MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR = "999.00";
	
	
	public static final double MAXIMUM_ALLOWED_PERCENTAGE = Double.parseDouble("9999.99");

	public static final String MAXIMUM_ALLOWED_VALUE_2_9_STR = "99.9999999";
	
	public static final String MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR = "99.999999999";

	public static final String MAXIMUM_ALLOWED_VALUE_3_2_STR = "999.99";
	
	public static final String MAXIMUM_ALLOWED_VALUE_2_2_STR = "100.00";
	
	public static final String MAXIMUM_ALLOWED_VALUE_6_2_STR = "999999.99";

	public static final String MAXIMUM_ALLOWED_VALUE_7_2_STR = "9999999.99";
	
	public static final String MAXIMUM_ALLOWED_VALUE_14_2_STR = "99999999999999.99";
	
	public static final String MAXIMUM_ALLOWED_AMOUNT_11_2_STR = "99999999999.99";

	public static final String MAXIMUM_ALLOWED_AMOUNT_18_2_STR = "999999999999999999.99";
	
	public static final String MAXIMUM_ALLOWED_AMOUNT_17_2_STR = "99999999999999999.99";
	
	public static final String MAXIMUM_ALLOWED_AMOUNT_10_2_STR = "9999999999.99";
	
	

	public static final double MAXIMUM_ALLOWED_VALUE_PERCENTAGE = Double
			.parseDouble(MAXIMUM_ALLOWED_VALUE_PERCENTAGE_STR);
	
	public static final double MAXIMUM_ALLOWED_VALUE_SEC_2_9 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_SEC_2_9_STR);
	
	public static final double MAXIMUM_ALLOWED_AMOUNT_11_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_11_2_STR);
	
	public static final double MAXIMUM_ALLOWED_VALUE_10_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_10_2_STR);

	public static final double MAXIMUM_ALLOWED_AMOUNT_18_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_18_2_STR);
	
	public static final double MAXIMUM_ALLOWED_AMOUNT_2_2 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_2_2_STR);
	
	public static final double MAXIMUM_ALLOWED_AMOUNT_17_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_17_2_STR);

	public static final double MAXIMUM_ALLOWED_VALUE_2_9 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_2_9_STR);

	public static final double MAXIMUM_ALLOWED_VALUE_3_2 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_3_2_STR);

	public static final double MAXIMUM_ALLOWED_VALUE_7_2 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_7_2_STR);
	
	public static final double MAXIMUM_ALLOWED_VALUE_6_2 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_6_2_STR);
	
	public static final double MAXIMUM_ALLOWED_VALUE_14_2 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_14_2_STR);

    public static final String MAXIMUM_ALLOWED_AMOUNT_7_STR = "9999999";
    public static final String MAXIMUM_ALLOWED_AMOUNT_15_STR = "999999999999999";
    public static final String MAXIMUM_ALLOWED_AMOUNT_17_STR = "99999999999999999";
    public static final String MAXIMUM_ALLOWED_AMOUNT_13_2_STR = "9999999999999.99";
    public static final String MAXIMUM_ALLOWED_AMOUNT_15_2_STR = "999999999999999.99";
    public static final String MAXIMUM_ALLOWED_AMOUNT_15_3_STR = "999999999999999.999";
    public static final String MAXIMUM_ALLOWED_AMOUNT_15_4_STR = "999999999999999.9999";
    public static final String MINIMUM_ALLOWED_AMOUNT_15_4_STR = "-999999999999999.9999";
    public static final String MINIMUM_ALLOWED_AMOUNT_15_2_STR = "-999999999999999.99";
    public static final String MAXIMUM_ALLOWED_AMOUNT_18_STR = "999999999999999999";
    public static final double MAXIMIUM_ALLOWED_AMOUNT = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_STR);
    public static final double MAXIMUM_ALLOWED_AMOUNT_13_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_13_2_STR);
    public static final double MAXIMIUM_ALLOWED_AMOUNT_15 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_15_STR);
    public static final double MAXIMIUM_ALLOWED_AMOUNT_15_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_15_STR);
    public static final double MAXIMIUM_ALLOWED_AMOUNT_17 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_17_STR);
    public static final double MAXIMUM_ALLOWED_AMOUNT_15_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_15_2_STR);
    public static final double MAXIMUM_ALLOWED_AMOUNT_15_3 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_15_3_STR);
    public static final double MAXIMUM_ALLOWED_AMOUNT_15_4 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_15_4_STR);
    public static final double MINIMUM_ALLOWED_AMOUNT_15_2 = Double.parseDouble(MINIMUM_ALLOWED_AMOUNT_15_2_STR);
    public static final double MINIMUM_ALLOWED_AMOUNT_15_4 = Double.parseDouble(MINIMUM_ALLOWED_AMOUNT_15_4_STR);
    public static final double MAXIMUM_ALLOWED_AMOUNT_18 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_18_STR);
    
    public static final String MAXIMUM_ALLOWED_AMOUNT_25_2_STRNew = "99999999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_25_2new = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_25_2_STRNew);

    public static final String MAXIMUM_ALLOWED_AMOUNT_4_2_STR = "99.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_4_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_4_2_STR);

    public static final String MAXIMUM_ALLOWED_AMOUNT_20_2_STR = "99999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_20_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_20_2_STR);
    
    public static final String MAXIMUM_ALLOWED_AMOUNT_20_STR = "99999999999999999999";
    public static final double MAXIMUM_ALLOWED_AMOUNT_20 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_20_STR);
    
    public static final String MAXIMUM_ALLOWED_AMOUNT_19_2_STR = "9999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_19_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_19_2_STR);
    
    public static final String MAXIMUM_ALLOWED_AMOUNT_19_STR = "9999999999999999999";
    public static final double MAXIMUM_ALLOWED_AMOUNT_19 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_19_STR);
	public static final String MAXIMUM_ALLOWED_INTEREST_RATE = "99.999999999";
	
	public static final String MAXIMUM_ALLOWED_AMOUNT_38_2_STR = "99999999999999999999999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_38_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_38_2_STR);
    
    public static final String MAXIMUM_ALLOWED_AMOUNT_22_2_STR = "9999999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_22_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_22_2_STR);
    
    public static final String MAXIMUM_ALLOWED_AMOUNT_30_2_STR = "999999999999999999999999999999.99";
    public static final BigDecimal MAXIMUM_ALLOWED_AMOUNT_30_2 = new BigDecimal(MAXIMUM_ALLOWED_AMOUNT_30_2_STR);
    
    public static final String MAXIMUM_ALLOWED_VALUE_3_STR = "999";
    public static final double MAXIMUM_ALLOWED_VALUE_3 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_3_STR);

    public static final String MAXIMUM_ALLOWED_AMOUNT_25_2_STR = "9999999999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_25_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_25_2_STR);
    public static final BigDecimal MAXIMUM_ALLOWED_AMOUNT_25_2_BIGDECIMAL =  new BigDecimal(MAXIMUM_ALLOWED_AMOUNT_25_2_STR);

	public static final String DEFAULT_CURRENCY = CommonUtil.getBaseCurrency();

	public static final String REQUEST_CUSTOMER_ID = "customerID"; // does not

	// need
	// prefix
	// becuase
	// does not
	// store
	// into
	// session

	public static final String REQUEST_LIMITPROFILE_ID = "limitProfileID"; // does

	// not
	// need
	// prefix
	// becuase
	// does
	// not
	// store
	// into
	// session

	public static final int MAXIMUM_PERCENTAGE_VALUE = 100;

	public static final String GLOBAL_CUSTOMER_OBJ = PREFIX + "CUSTOMER_OBJ";

	public static final String GLOBAL_LIMITPROFILE_OBJ = PREFIX + "LIMITPROFILE_OBJ";

	public static final String GLOBAL_CUSTOMERSEARCHCRITERIA_OBJ = PREFIX + "CUSTOMERSEARCHCRITERIA_OBJ";

	public static final String GLOBAL_CMSTRXSEARCHCRITERIA_OBJ = PREFIX + "CMSTRXSEARCHCRITERIA_OBJ";

	public static final String GLOBAL_CMSTRXSEARCH_START_INDEX = PREFIX + "CMSTRXSEARCH_START_INDEX";

	public static final String GLOBAL_COLLATERALSEARCH_OBJ = PREFIX + "COLLATERALSEARCH_OBJ";

	// use to keep track the return button flow
	public static final String GLOBAL_TRX_ID = PREFIX + "transactionID";

	public static final String REQUEST_ERROR_MSG = "request.errorMsgDetails";

    public static final String GLOBAL_COUNTERPARTY_SEARCH_CRITERIA_OBJ = PREFIX  + "COUNTERPARTYSEARCHCRITERIA_OBJ";
    public static final String GLOBAL_CUSTGRPIDENTIFIER_SEARCH_CRITERIA_OBJ = PREFIX  + "CUSTGRPIDENTIFIER_SEARCHCRITERIA_OBJ";
    public static final String GLOBAL_GRP_MEMEBER_SEARCH_CRITERIA_OBJ = PREFIX  + "GRP_MEMBER_SEARCH_CRITERIA_OBJ";

    public static final String GLOBAL_CUSTEXPOSURE_SEARCH_CRITERIA_OBJ = PREFIX  + "CUSTEXPOSURE_SEARCHCRITERIA_OBJ";
    public static final String GLOBAL_CUSTEXPOSURE_SUB_PROFILE_ID = PREFIX  + "CUSTEXPOSURE_SUB_PROFILE_ID";    
    
    //Code added for Numeric Serial Number
	public static final String MAXIMUM_ALLOWED_VALUE_10_STR = "9999999999";
    public static final double MAXIMUM_ALLOWED_VALUE_10 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_10_STR);
    
    public static final String MAXIMUM_ALLOWED_VALUE_2_STR = "99";
    public static final double MAXIMUM_ALLOWED_VALUE_2 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_2_STR);//ss
    
    //Start:Uma Khot: added to allow upto decimal 4 in Security OMV for Markatable Sec-Mutual Fund Security
    public static final String MAXIMUM_ALLOWED_AMOUNT_20_4_STR = "99999999999999999999.9999";
    public static final double MAXIMUM_ALLOWED_AMOUNT_20_4 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_20_4_STR);
    
    //End:Uma Khot: added to allow upto decimal 4 in Security OMV for Markatable Sec-Mutual Fund Security
    
    //Start Santosh
    public static final String MAXIMUM_ALLOWED_VALUE_18_2_STR = "999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_VALUE_18_2 = Double.parseDouble(MAXIMUM_ALLOWED_VALUE_18_2_STR);
    //End Santosh
    
    public static final String MAXIMUM_ALLOWED_AMOUNT_24_2_STR = "9999999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_24_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_24_2_STR);
    
    public static final String PAGINATION_CURRENT_INDEX = "currentIndex";
    
    public static final String INDEX = "index";
    public static final String SELECTED_INDEX = "selected_index";
    
    public static final String MAXIMUM_ALLOWED_AMOUNT_40_2_STR = "9999999999999999999999999999999999999999.99";
    public static final double MAXIMUM_ALLOWED_AMOUNT_40_2 = Double.parseDouble(MAXIMUM_ALLOWED_AMOUNT_40_2_STR);
}
