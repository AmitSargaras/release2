/**
 * CommonCodeConstant.java
 *
 * Created on January 29, 2007, 3:10 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */

package com.integrosys.cms.app.commoncodeentry;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;

/**
 * 
 * @author Eric
 */
public final class CommonCodeEntryConstant {

	/** List of event for the common code module */

	public static final String LIST_PARAMS = "maker_edit_common_code_params_read"; // the
																					// event
																					// display
																					// the
																					// list
																					// of
																					// editable
																					// common
																					// code
																					// parameters

	public static final String EDIT_SELECTED_PARAM = "maker_edit_common_code_params_edit"; // the
																							// event
																							// where
																							// a
																							// user
																							// select
																							// a
																							// parameter
																							// to
																							// edit

	public static final String UPDATE_SELECTED_PARAM = "maker_edit_common_code_params_update"; // the
																								// event
																								// where
																								// the
																								// user
																								// forwards
																								// the
																								// changes
																								// to
																								// the
																								// checker

	public static final String EDIT_REJECTED_PARAM = "maker_edit_common_code_params_edit_rejected"; // the
																									// event
																									// where
																									// the
																									// user
																									// edits
																									// the
																									// rejected
																									// changes

	public static final String ADD_REJECTED_PARAM = "maker_edit_common_code_params_add_rejected"; // the
																									// event
																									// where
																									// the
																									// user
																									// saves
																									// the
																									// edited
																									// param
																									// into
																									// the
																									// system

	public static final String UPDATE_REJECTED_PARAM = "maker_edit_common_code_params_update_rejected"; // the
																										// event
																										// where
																										// the
																										// user
																										// forwards
																										// the
																										// edited
																										// changes
																										// to
																										// the
																										// checker

	public static final String SAVE_PARAM = "maker_edit_common_code_params_save"; // the
																					// event
																					// where
																					// the
																					// user
																					// saves
																					// the
																					// edited
																					// param
																					// into
																					// the
																					// system

	public static final String ADD_PARAM = "maker_edit_common_code_params_add"; // the
																				// event
																				// where
																				// the
																				// user
																				// saves
																				// the
																				// edited
																				// param
																				// into
																				// the
																				// system

	public static final String ADD_PARAM_RETURN = "add_return";
	
	public static final String SEARCH_CC_ENTRY = "search_cc_entry";

	public static final String CLOSE_PARAM = "maker_edit_common_code_params_close"; // the
																					// event
																					// where
																					// the
																					// user
																					// close
																					// the
																					// rejected

	public static final String PREPARE_CLOSE_PARAM = "maker_edit_common_code_params_prepare_close"; // the
																									// event
																									// where
																									// the
																									// user
																									// close
																									// the
																									// rejected

	public static final String CHECKER_PROCESS = "checker_process"; // the event
																	// where the
																	// checker
																	// starts to
																	// process
																	// the item

	public static final String CHECKER_APPROVE = "checker_approve"; // the event
																	// where the
																	// checker
																	// approves
																	// item

	public static final String CHECKER_REJECT = "checker_reject"; // the event
																	// where the
																	// checker
																	// rejects
																	// the item

	public static final String CHECKER_TO_TRACK = "to_track"; // the event where
																// the checker
																// rejects the
																// item

	public static final String WORK_IN_PROGRESS = "wip"; // key , event and
															// value to indicate
															// work is progress

	public static final String EDIT_SELECTED_PARAM_PAGEINATE = "maker_edit_common_code_params_edit_paginate"; // the
																												// event
																												// where
																												// a
																												// user
																												// select
																												// a
																												// parameter
																												// to
																												// edit

	public static final String EDIT_SELECTED_PARAM_PAGEINATE_ERROR = "maker_edit_common_code_params_edit_paginate_error"; // the
																															// event
																															// where
																															// a
																															// user
																															// select
																															// a
																															// parameter
																															// to
																															// edit

	public static final String UPDATE_SELECTED_PARAM_ERROR = "maker_edit_common_code_params_update_error";

	public static final String CHECKER_PROCESS_PAGEINATE = "checker_process_paginate"; // the
																						// event
																						// where
																						// the
																						// checker
																						// starts
																						// to
																						// process
																						// the
																						// item

	public static final String EDIT_REJECTED_PARAM_PAGEINATE = "maker_edit_common_code_params_rejected_paginate"; // the
																													// event
																													// where
																													// the
																													// user
																													// edits
																													// the
																													// rejected
																													// changes

	public static final String EDIT_REJECTED_PARAM_PAGEINATE_ERROR = "maker_edit_common_code_params_rejected_paginate_error"; // the
																																// event
																																// where
																																// the
																																// user
																																// edits
																																// the
																																// rejected
																																// changes

	public static final String PREPARE_CLOSE_PARAM_PAGEINATE = "maker_edit_common_code_params_close_paginate"; // the
																												// event
																												// where
																												// the
																												// user
																												// close
																												// the
																												// rejected

	public static final String CHECKER_TO_TRACK_PAGEINATE = "to_track_paginate"; // the
																					// event
																					// where
																					// the
																					// checker
																					// rejects
																					// the
																					// item

	/** List of keys used to bind object to the session, request or form scope */

	public static final String EDITABLE_PARAM_LIST = "param_list"; // the key
																	// that
																	// binds the
																	// collection
																	// of
																	// editable
																	// parameters

	public static final String SELECTED_CATEGORY_CODE_ID = "selected_category_code_id"; // the
																						// key
																						// that
																						// binds
																						// the
																						// selected
																						// parameter
																						// which
																						// the
																						// user
																						// has
																						// selected

	public static final String ENTRY_LIST = "common_code_entries"; // the key
																	// that
																	// binds the
																	// list of
																	// entries

	public static final String COMMON_CODE_OB = "OBCommonCode"; // the key that
																// binds the
																// OBCommonCode
																// object

	public static final String COMMON_CODE_OB_ENTRY = "OBCommonCodeEntry"; // the
																			// key
																			// that
																			// binds
																			// the
																			// OBCommonCodeEntry
																			// object

	public static final String ITRXVALUE_KEY = "request.ITrxValue"; // key use
																	// to bind
																	// the ITrx
																	// obejct to
																	// the
																	// request
																	// for
																	// processing

	public static final String COMMON_CODE_ENTRIES = "tokenizedString"; // key
																		// use
																		// to
																		// pass
																		// the
																		// common
																		// code
																		// category
																		// entries
																		// data
																		// in
																		// one
																		// string

	public static final String COMMON_CODE_ENTRIES_FORM = "MaintainCommonCodeEntriesForm"; // key
																							// use
																							// to
																							// pass
																							// the
																							// common
																							// code
																							// entries
																							// form

	public static final String DELIMITER = "|"; // the delimiter used to
												// separate the entry data

	public static final String COMMON_CODE_OB_ENTRIES_TRX = "OBCommonCodeEntriesTrxValue"; // the
																							// key
																							// that
																							// binds
																							// the
																							// OBCommonCodeEntry
																							// object

	public static final String TRANCASTION_ID = "TrxId"; // the key that binds
															// the
															// OBCommonCodeEntry
															// object

	public static final String COUNTRY_LABELS = "countryLabels";

	public static final String COUNTRY_VALUES = "countryValues";

	public static final String OFFSET = "offset"; // the key to bind the current
													// page number

	public static final String LENGTH = "length"; // the key to bind the item
													// length page

	public static final String CURRENT_OFFSET_NUMBER = "current_offset"; // the
																			// key
																			// to
																			// bind
																			// the
																			// item
																			// length
																			// page

	public static final Integer INITIAL_OFFSET = new Integer(0);

	public static final Integer FIXED_LENGTH = new Integer(10);

	/** using to saerch of location */
	public static final String COUNTRY_CATEGORY_CODE;

	public static final String STATE_CATEGORY_CODE;

	public static final String DISTRICT_CATEGORY_CODE;

	public static final String MUKIM_CATEGORY_CODE;

	static {
		COUNTRY_CATEGORY_CODE = PropertyManager.getValue("common.code.category.country.code", "36");
		STATE_CATEGORY_CODE = PropertyManager.getValue("common.code.category.state.code", "StateList");
		DISTRICT_CATEGORY_CODE = PropertyManager.getValue("common.code.category.district.code", "DistrictList");
		MUKIM_CATEGORY_CODE = PropertyManager.getValue("common.code.category.mukim.code", "MukimList");
	}
	public static final String EDIT_SELECTED_PARAM_ERROR = "maker_edit_common_code_params_edit_error";
	
	public interface SecurityOwnershipCodes{
		public static final String THIRD_PARTY = "THIRD_PARTY";
		public static final String BORROWER = "BORROWER";
	}
	
	public interface CollateralCategoryCodes{
		public static final String IMMOVABLE = "IMMOVABLE";
		public static final String MOVABLE = "MOVABLE";
	}
}
