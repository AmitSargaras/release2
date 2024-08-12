package com.integrosys.cms.app.transaction;

/**
 * This is an Interface to store Transaction related table and column name
 * constants..
 * 
 * @author $Author: lyng $<br>
 * @version $Revision: 1.8 $
 * @since $Date: 2004/09/21 09:00:06 $ Tag: $Name: $
 */
public interface ICMSTrxTableConstants {
	// ********* constants for Transaction table and corresponding coulmn
	// names.. ****************
	public static final String TRX_TBL_NAME = "TRANSACTION";

	public static final String TRXTBL_TRANSACTION_ID = "TRANSACTION_ID";

	public static final String TRXTBL_FROM_STATE = "FROM_STATE";

	public static final String TRXTBL_USER_ID = "USER_ID";

	public static final String TRXTBL_TRANSACTION_TYPE = "TRANSACTION_TYPE";

	public static final String TRXTBL_CREATION_DATE = "CREATION_DATE";

	public static final String TRXTBL_TRANSACTION_DATE = "TRANSACTION_DATE";

	public static final String TRXTBL_REFERENCE_ID = "REFERENCE_ID";

	public static final String TRXTBL_STATUS = "STATUS";

	public static final String TRXTBL_STAGING_REFERENCE_ID = "STAGING_REFERENCE_ID";

	public static final String TRXTBL_TEAM_ID = "TEAM_ID";

	public static final String TRXTBL_VERSION = "VERSION";

	public static final String TRXTBL_REMARKS = "REMARKS";

	public static final String TRXTBL_TRX_REFERENCE_ID = "TRX_REFERENCE_ID";

	public static final String TRXTBL_CUR_TRX_HISTORY_ID = "CUR_TRX_HISTORY_ID";

	public static final String TRXTBLE_TO_GROUP_TYPE_ID = "TO_GROUP_TYPE_ID";

	// for routing
	// public static final String TRXTBL_NEXT_TEAM_ID="NEXT_TEAM_ID";
	public static final String TRXTBL_ROUTE_TEAM_TYPE_ID = "ROUTE_TEAM_TYPE_ID";

	public static final String TRXTBL_ROUTE_MEMBERSHIP_TYPE_ID = "ROUTE_MEMBERSHIP_TYPE_ID";

	public static final String TRXTBL_ROUTE_COUNTRY_CODE = "ROUTE_COUNTRY_CODE";

	public static final String TRXTBL_ROUTE_SEGMENT_CODE = "ROUTE_SEGMENT_CODE";

	// Constants for state transition table
	public static final String TRX_STATE_TABLE = "TR_STATE_MATRIX";

	public static final String TSTTBL_STATE_ID = "STATEID";

	public static final String TSTTBL_STATE_INS = "STATEINS";

	public static final String TSTTBL_OPERATION = "OPERATION";

	public static final String TSTTBL_FROM_STATE = "FROMSTATE";

	public static final String TSTTBL_TO_STATE = "TOSTATE";

	public static final String TSTTBL_USER_STATE = "USERSTATE";

	public static final String TSTTBL_ENABLED_IND = "ENABLEIND";

	public static final String TSTTBL_FRONTEND_OP = "FRONTENDOP";

	// Constants for the transaction history table
	public static final String TRX_HIST_TABLE = "TRANS_HISTORY";

	public static final String HISTBL_TRANSACTION_ID = "TRANSACTION_ID";

	public static final String HISTBL_TRANSACTION_TYPE = "TRANSACTION_TYPE";

	public static final String HISTBL_TRANSACTION_DATE = "TRANSACTION_DATE";

	public static final String HISTBL_STAGING_REFERENCE_ID = "STAGING_REFERENCE_ID";

	public static final String HISTBL_FROM_STATE = "FROM_STATE";

	public static final String HISTBL_STATUS = "STATUS";
}