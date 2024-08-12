package com.integrosys.cms.ui.contractfinancing;

/**
 * Constants used in the bridging loan ui package.
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IContractFinancingUIConstant {
	// constants for Asset based - General Charge Tabs
	public static final String TAB_CONTRACTDETAIL = "contractdetail";

	public static final String TAB_ADVSPAYMENT = "advspayment";

	public static final String TAB_ADVSPAYMENT_SG = "advspayment_sg";

	public static final String TAB_TNC = "tnc";

	public static final String TAB_FDR = "fdr";

	// Main set of events used in action class. Use in conjunction with
	// ICommonEventConstant
	public static String EVENT_MAKER_NAVIGATE_TAB = "maker_navigate_tab";

	public static String EVENT_MAKER_PREPARE_CLOSE = "maker_prepare_close";

	public static String EVENT_MAKER_CLOSE = "maker_close";

	public static String EVENT_MAKER_PROCESS = "maker_process";

	public static String EVENT_SAVE = "save";

	public static String EVENT_TO_TRACK = "to_track";

	public static String FORWARD_PREFIX = "after_";

	// events for subclassing actions
	public static String EVENT_LIST_SUMMARY = "list_summary";

	public static String EVENT_CHECKER_LIST_SUMMARY = "checker_list_summary";

	public static String EVENT_MAKER_PREPARE_CREATE = "maker_prepare_create";

	public static String EVENT_MAKER_PREPARE_UPDATE = "maker_prepare_update";

	public static String EVENT_MAKER_PREPARE_DELETE = "maker_prepare_delete";

	public static final String EVENT_PREPARE_FORM = "prepare_form";

	public static final String EVENT_REFRESH = "refresh";

	public static final String EVENT_MAKER_EDIT_ERROR = "maker_edit_error";

	public static final String EVENT_CHECKER_VIEW = "checker_view";

	// events for sub class sub items actions
	public static final String EVENT_PREPARE_ITEM_FORM = "prepare_item_form";

	public static final String EVENT_MAKER_PREPARE_CREATE_ITEM = "maker_prepare_add_item";

	public static final String EVENT_MAKER_PREPARE_UPDATE_ITEM = "maker_prepare_edit_item";

	public static final String EVENT_MAKER_PREPARE_DELETE_ITEM = "maker_prepare_delete_item";

	public static final String EVENT_CREATE_ITEM = "create_item";

	public static final String EVENT_UPDATE_ITEM = "update_item";

	public static final String EVENT_DELETE_ITEM = "delete_item";

	public static final String EVENT_VIEW_ITEM = "view_item";

	public static final String EVENT_PROCESS_RETURN = "process_return";

}
