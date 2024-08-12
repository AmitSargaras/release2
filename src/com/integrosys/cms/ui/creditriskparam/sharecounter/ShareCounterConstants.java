/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * ShareCounterConstants
 *
 * Created on 9:33:59 AM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.creditriskparam.sharecounter;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Feb 16, 2007 Time: 9:33:59 AM
 */
public final class ShareCounterConstants {
	private ShareCounterConstants() {

	}

	/** keys used to bind objects to session or reqeust */
	public static final String SHARE_COUNTER_FORM = "ShareCounterForm"; // the
																		// form
																		// name
																		// itself
																		// is
																		// used
																		// to
																		// the
																		// bind
																		// the
																		// form
																		// object

	public static final String CURRENT_OFFSET_NUMBER = "current_offset_number"; // the
																				// key
																				// to
																				// bind
																				// the
																				// current
																				// page
																				// number
																				// -
																				// not
																				// used

	public static final String GROUP_SUBTYPE = "stockExchange"; // the key that
																// holds the
																// group
																// suubtype
																// value

	public static final String GROUP_STOCK_TYPE = "stockType"; // the key
																		// that
																		// holds
																		// the
																		// group
																		// stock
																		// type
																		// value

	public static final String STOCK_EXCHANGE_TYPE_LIST = "stock_exchange_type_list"; // the
																						// key
																						// that
																						// bind
																						// the
																						// collection
																						// of
																						// ICreditRiskParamGroup
																						// objects
																						// containing

	public static final String STOCK_EXCHANGE_LIST = "stock_exchange_list"; // the
																			// key
																			// that
																			// bind
																			// the
																			// collection
																			// of
																			// ICreditRiskParamGroup
																			// objects
																			// containing

	public static final String CREDIT_RISK_PARAM_TRX_VALUE = "CREDIT_RISK_PARAM_TRX_VALUE"; // key
																							// to
																							// bind
																							// the
																							// trxValue
																							// to
																							// the
																							// session

	public static final String OFFSET = "offset"; // the key to bind the current
													// page number

	public static final String LENGTH = "length"; // the key to bind the item
													// length page

	public static final String SHARE_TYPE = "SHARE_TYPE";

	public static final String STOCK_EXCHANGE = "STOCK_EXCHANGE";

	public static final String BOARD_TYPE = "BOARD_TYPE";

	public static final String SHARE_STATUS = "SHARE_STATUS";

	public static final String BOARD_TYPE_LABELS = "BOARD_TYPE_LABELS";

	public static final String BOARD_TYPE_VALUES = "BOARD_TYPE_VALUES";

	public static final String SHARE_STATUS_LABELS = "SHARE_STATUS_LABELS";

	public static final String SHARE_STATUS_VALUES = "SHARE_STATUS_VALUES";

	public static final Integer INITIAL_OFFSET = new Integer(0);

	public static final Integer FIXED_LENGTH = new Integer(10);

	/** event used */
	// events for maker
	public static final String SHARE_COUNTER_MAKER_START = "maker_share_counter_start"; // the
																						// event
																						// the
																						// maker
																						// starts
																						// by
																						// click
																						// the
																						// link
																						// to
																						// see
																						// the
																						// list
																						// of
																						// stock
																						// exchanges
																						// type

	public static final String SHARE_COUNTER_MAKER_STOCK_EXCHANGE_SELECTED = "maker_share_counter_select_stock_exchange"; // the
																															// event
																															// the
																															// maker
																															// has
																															// selected
																															// the
																															// stock
																															// exchange
																															// type

	public static final String SHARE_COUNTER_MAKER_TYPE_SELECTED = "maker_share_counter_select_type"; // the
																										// event
																										// the
																										// maker
																										// has
																										// selected
																										// the
																										// type

	public static final String SHARE_COUNTER_MAKER_UPDATE = "maker_share_counter_update"; // the
																							// event
																							// the
																							// maker
																							// submit
																							// the
																							// changes
																							// to
																							// the
																							// system

	public static final String SHARE_COUNTER_MAKER_UPDATE_REJECTED = "maker_share_counter_update_rejected"; // the
																											// event
																											// the
																											// maker
																											// submit
																											// the
																											// changes
																											// to
																											// the
																											// system

	public static final String SHARE_COUNTER_MAKER_PREPARE_UPDATE_REJECTED = "maker_share_counter_prepare_update_rejected"; // the
																															// event
																															// the
																															// maker
																															// submit
																															// the
																															// changes
																															// of
																															// the
																															// rejected
																															// item
																															// to
																															// the
																															// system

	public static final String SHARE_COUNTER_MAKER_PREPARE_CLOSE = "maker_share_counter_prepare_close"; // the
																										// event
																										// the
																										// maker
																										// submit
																										// the
																										// changes
																										// of
																										// the
																										// rejected
																										// item
																										// to
																										// the
																										// system

	public static final String SHARE_COUNTER_MAKER_CLOSE = "maker_share_counter_close"; // the
																						// event
																						// the
																						// maker
																						// submit
																						// the
																						// changes
																						// of
																						// the
																						// rejected
																						// item
																						// to
																						// the
																						// system

	public static final String SHARE_COUNTER_MAKER_UPDATE_ERORR = "maker_share_counter_update_error"; // the
																										// event
																										// the
																										// maker
																										// submit
																										// the
																										// changes
																										// to
																										// the
																										// system

	public static final String SHARE_COUNTER_MAKER_UPDATE_REJECTED_ERROR = "maker_share_counter_update_rejected_error"; // the
																														// event
																														// the
																														// maker
																														// submit
																														// the
																														// changes
																														// to
																														// the
																														// system

	public static final String SHARE_COUNTER_MAKER_PAGINATE = "maker_share_counter_paginate";

	public static final String SHARE_COUNTER_MAKER_PAGINATE_REJECTED = "maker_share_counter_paginate_rejected";

	public static final String SHARE_COUNTER_VIEW = "view";
	
	public static final String SHARE_COUNTER_VIEW_PAGINATE = "view_paginate";

	public static final String SHARE_COUNTER_WIP = "wip";

	public static final String SHARE_COUNTER_POLICY_NOT_SET = "policy_not_set";

	public static final String SHARE_COUNTER_MAKER_REFRESH = "maker_share_counter_refresh";

	public static final String SHARE_COUNTER_MAKER_REFRESH_REJECTED = "maker_share_counter_refresh_rejected";

	public static final String SHARE_COUNTER_TOTRACK = "to_track"; // the event
																	// that
																	// maker
																	// tracking
																	// changes

	// events for checker
	public static final String SHARE_COUNTER_CHECKER_VIEW = "checker_process"; // the
																				// event
																				// the
																				// checker
																				// starts
																				// processing
																				// the
																				// item
																				// is
																				// to
																				// first
																				// view
																				// it

	public static final String SHARE_COUNTER_CHECKER_APPROVE = "checker_share_counter_approve"; // the
																								// event
																								// the
																								// checker
																								// approves

	public static final String SHARE_COUNTER_CHECKER_REJECT = "checker_share_counter_reject"; // the
																								// event
																								// the
																								// checker
																								// rejects

	public static final String GROUP_STOCK_TYPE_NORMAL = "001";

	public static final String GROUP_STOCK_TYPE_WARRANTS = "002";

	public static final String GROUP_STOCK_TYPE_LOAN_STOCKS = "003";
	
	public static final String COMMON_CODE_STOCK_EXCHANGE = "STOCK_EXCHANGE";
	public static final String COMMON_CODE_SHARE_TYPE = "SHARE_TYPE";
	public static final String COMMON_CODE_REF_STOCK_TYPE = "STOCK_TYPE";
}
