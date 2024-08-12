package com.integrosys.cms.ui.bridgingloan;

/**
 * Constants used in the bridging loan ui package.
 * 
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$ Tag: $Name$
 */
public interface IBridgingLoanUIConstant {
	// constants for Asset based - General Charge Tabs
	public static final String TAB_MAIN = "projectinfo";

	public static final String TAB_ADVSPAYMENT = "advspayment";

	public static final String TAB_ADVSPAYMENT_SG = "advspayment_sg";

	public static final String TAB_BUILDUP = "buildup";

	public static final String TAB_FDR = "fdr";

	// Main set of events used in action class. Use in conjunction with
	// ICommonEventConstant
	public static final String EVENT_MAKER_NAVIGATE_TAB = "maker_navigate_tab";

	public static final String EVENT_MAKER_PREPARE_CLOSE = "maker_prepare_close";

	public static final String EVENT_MAKER_PROCESS = "maker_process";

	public static final String EVENT_MAKER_CLOSE = "maker_close";

	public static final String EVENT_SAVE = "save";

	public static final String EVENT_TO_TRACK = "to_track";

	public static final String FORWARD_PREFIX = "after_";

	// events for subclassing actions
	public static final String EVENT_LIST_SUMMARY = "list_summary";

	public static final String EVENT_CHECKER_LIST_SUMMARY = "checker_list_summary";

	public static final String EVENT_PREPARE_FORM = "prepare_form";

	public static final String EVENT_MAKER_PREPARE_CREATE = "maker_prepare_create";

	public static final String EVENT_MAKER_PREPARE_UPDATE = "maker_prepare_update";

	public static final String EVENT_MAKER_PREPARE_DELETE = "maker_prepare_delete";

	public static final String EVENT_MAKER_REFRESH = "maker_refresh";

	public static final String EVENT_CHECKER_VIEW = "checker_view";

	public static final String EVENT_CHECKER_VIEW_ITEM = "checker_view_item";

	// events for sub class sub items actions
	public static final String EVENT_PREPARE_ITEM_FORM = "prepare_item_form";

	public static final String EVENT_MAKER_PREPARE_CREATE_ITEM = "maker_prepare_create_item";

	public static final String EVENT_MAKER_PREPARE_UPDATE_ITEM = "maker_prepare_update_item";

	public static final String EVENT_MAKER_PREPARE_DELETE_ITEM = "maker_prepare_delete_item";

	public static final String EVENT_CREATE_ITEM = "create_item";

	public static final String EVENT_UPDATE_ITEM = "update_item";

	public static final String EVENT_DELETE_ITEM = "delete_item";

	public static final String EVENT_VIEW_ITEM = "view_item";

	public static final String EVENT_PROCESS_RETURN = "process_return";
}