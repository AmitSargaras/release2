/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealConstants
 *
 * Created on 3:05:47 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.ui.predeal;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 21, 2007 Time: 3:05:47 PM
 */
public final class PreDealConstants {

	private PreDealConstants() {

	}

	// maker's events
	public static final String EVENT_MAKER_PREPARE_SEARCH = "maker_prepare_search";

	public static final String EVENT_MAKER_SEARCH = "maker_search";

	public static final String EVENT_MAKER_VIEW_EAR_MARK = "maker_view_ear_mark";

	public static final String EVENT_MAKER_PREPARE_NEW_EAR_MARK = "maker_prepare_new_mark";

	public static final String EVENT_MAKER_PRESUBMIT_NEW_EAR_MARK = "maker_presubmit_new_ear_mark";

	public static final String EVENT_MAKER_SUBMIT_NEW_EAR_MARK = "maker_submit_new_ear_mark";

	public static final String EVENT_MAKER_CALCULATE = "maker_calculate";

	public static final String EVENT_MAKER_PREPARE_RELEASE = "maker_prepare_release";

	public static final String EVENT_MAKER_PREPARE_DELETE = "maker_prepare_delete";

	public static final String EVENT_MAKER_PREPARE_TRANSFER = "maker_prepare_transfer";

	public static final String EVENT_MAKER_SUBMIT_RELEASE = "maker_submit_release";

	public static final String EVENT_MAKER_SUBMIT_DELETE = "maker_submit_delete";

	public static final String EVENT_MAKER_SUBMIT_TRANSFER = "maker_submit_transfer";

	public static final String EVENT_MAKER_WIP = "wip";

	public static final String EVENT_MAKER_PREPARE_CLOSE_DELETE = "maker_prepare_close_delete";

	public static final String EVENT_MAKER_PREPARE_CLOSE_TRANSFER = "maker_prepare_close_transfer";

	public static final String EVENT_MAKER_PREPARE_CLOSE_RELEASE = "maker_prepare_close_release";

	public static final String EVENT_MAKER_PREPARE_CLOSE_UPDATE = "maker_prepare_close_update";

	public static final String EVENT_MAKER_PREPARE_CLOSE_CREATE = "maker_prepare_close_create";

	public static final String EVENT_MAKER_SUBMIT_CLOSE = "maker_submit_close";

	public static final String EVENT_MAKER_PREPARE_UPDATE_REJECT = "maker_prepare_update_rejected_create";

	public static final String EVENT_MAKER_PRESUBMIT_UPDATE_REJECT = "maker_preupdate_rejected_create";

	public static final String EVENT_MAKER_SUBMIT_UPDATE_REJECT = "maker_update_rejected_create";

	public static final String EVENT_MAKER_CALCULATE_REJECT = "maker_calculate_rejected";

	public static final String EVENT_ERROR_PAGE = "error_page";

	public static final String EVENT_MAKER_SOURCE_SYSTEM = "maker_prepare_sourcesystem";

	public static final String EVENT_MAKER_SOURCE_SYSTEM_REJECT = "maker_prepare_sourcesystem_rejected";

	public static final String EVENT_MAKER_VIEW_CONCENTRATION = "maker_prepare_view_concentration";

	// checker's events
	public static final String EVENT_CHECKER_PROCESS = "checker_process";

	public static final String EVENT_CHECKER_PROCESS_NEW = "checker_process_new";

	public static final String EVENT_CHECKER_APPROVE = "checker_approve";

	public static final String EVENT_CHECKER_APPROVE_NEW = "checker_approve_new";

	public static final String EVENT_CHECKER_REJECT = "checker_reject";

	public static final String EVENT_CHECKER_REJECT_NEW = "checker_reject_new";

	public static final String EVENT_CHECKER_PROCESS_DELETE = "checker_process_delete";

	public static final String EVENT_CHECKER_PROCESS_TRANSFER = "checker_process_transfer";

	public static final String EVENT_CHECKER_PROCESS_RELEASE = "checker_process_release";

	public static final String EVENT_TO_TRACK = "to_track";

	public static final String EVENT_TO_TRACK_NEW = "to_track_new";

	public static final String EVENT_TO_TRACK_DELETE = "to_track_delete";

	public static final String EVENT_TO_TRACK_TRANSFER = "to_track_transfer";

	public static final String EVENT_TO_TRACK_RELEASE = "to_track_release";

	// keys used to bind object
	public static final String PRE_DEAL_FROM = "PreDealForm";

	public static final String PRE_DEAL_OB = "OBPreDeal";

	public static final String OB_PRE_DEAL_TRX_VALUE = "OBPreDealTrxValue";

	public static final String PRE_DEAL_RECORD = "record";

	public static final String EAR_MARK_GROUP = "earMarkGroupResult";

	public static final String ISIN_CODE = "isinCode";

	public static final String COUNTER_NAME = "counterName";

	public static final String RIC = "ric";

	public static final String SEARCH_RESULT = "searchResult";

	public static final String FEED_ID = "feedId";

	public static final String TRX_ID = "TrxId";

	public static final String EARMARK_ID = "earMarkId";

	public static final String EARMARK_ID_SESSION = "earMarkIdSession";

	public static final String EVENT = "event";

	public static final String NEXT_EVENT = "next_event";

	public static final String UPDATE_TYPE = "updateType";

	public static final String LIMIT_LEVEL_BREACHED = "limitLevelBreached";

	public static final String STOCK_EXCHANGE_NAME = "stockExchangeName";

	public static final String CONCENTRATION = "concentrationResult";

	public static final String MAX_CAP_BREACH = "max_cap_breach";

	public static final String QUOTA_CAP_BREACH = "quota_cap_breach";

	public static final String EARMARK_STATUS_EARMARKED = com.integrosys.cms.app.predeal.PreDealConstants.EARMARK_STATUS_EARMARKED;

	public static final String EARMARK_STATUS_HOLDING = com.integrosys.cms.app.predeal.PreDealConstants.EARMARK_STATUS_HOLDING;

	public static final String EARMARK_STATUS_RELEASED = com.integrosys.cms.app.predeal.PreDealConstants.EARMARK_STATUS_RELEASED;

	public static final String EARMARK_STATUS_DELETED = com.integrosys.cms.app.predeal.PreDealConstants.EARMARK_STATUS_DELETED;

	public static final String PRE_DEAL_SOURCE = "PREDEAL_SOURCE";

	public static final String PRE_DEAL_SOURCE_LABELS = "PREDEAL_SOURCE_LABELS";

	public static final String PRE_DEAL_SOURCE_VALUES = "PREDEAL_SOURCE_VALUES";

}
