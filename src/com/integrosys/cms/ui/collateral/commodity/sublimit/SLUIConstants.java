/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/collateral/commodity/sublimit/SLUIConstants.java,v 1.6 2006/09/27 02:19:26 hmbao Exp $
 */
package com.integrosys.cms.ui.collateral.commodity.sublimit;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-26
 * @Tag com.integrosys.cms.ui.collateral.commodity.sublimit.SLUIConstants.java
 */
public interface SLUIConstants {
	// Attribute Name
	String AN_SLT_LIST = "theSubLimitTypeList";

	String AN_COMM_MAIN_TRX_VALUE = "commodityMainTrxValue";

	String AN_CURRENCY_COLLECTION = "theCurrencyCollection";

	String AN_STAGE_LMT = "stageLimit";

	String AN_OB_SL = "theSubLimitObject";

	String AN_CMDT_LIMIT_MAP = "theCmdtLimitMap";

	String AN_SL_AMOUNT_CHK_MSG_LIST = "amountCheckErrorMsg";

	String AN_DEL_SLT_MAP = "theDelSLTMap";

	String AN_SL_DETAIL_MAP = "theSubLimitDetailsMap";

	// Class Name
	String CN_COLLECTION = "java.util.Collection";

	String CN_I_SL = "com.integrosys.cms.app.collateral.bus.type.commodity.sublimit.ISubLimit";

	String CN_LIST = "java.util.List";

	String CN_COMMODITY_MAIN_ACTION = "com.integrosys.cms.ui.collateral.commodity.CommodityMainAction";

	String CN_SL_DEL_MAPPER = "com.integrosys.cms.ui.collateral.commodity.sublimit.list.SubLimitDeleteMapper";

	String CN_SL_LIST_MAPPER = "com.integrosys.cms.ui.collateral.commodity.sublimit.list.SubLimitListMapper";

	String CN_SLI_MAP = "com.integrosys.cms.ui.collateral.commodity.sublimit.item.SubLimitItemMapper";

	String CN_STRING = "java.lang.String";

	String CN_HASHMAP = "java.util.HashMap";

	String CN_LOCALE = "java.util.Locale";

	// Event Name
	String EN_PREPARE_UPDATE_ITEM = "prepare_update_item";

	String EN_RE_PREPARE_UPDATE_ITEM = "re_prepare_update_item";

	String EN_OK_UPDATE_ITEM = "ok_update_item";

	String EN_CANCEL_UPDATE_ITEM = "cancel_update_item";

	String EN_CK_PROCESS_VIEW = "checker_process_view";

	String EN_RETURN_UPDATE_ITEM = "return_update_item";

	String EN_RESTORE = "data_restore";

	String EN_VIEW_RETURN = "view_return";

	// Field Name
	String FN_FROM_EVENT = "fromEvent";

	String FN_IDX_ID = "indexID";

	String FN_LIMIT_ID = "limitID";

	String FN_SLT = "subLimitType";

	String FN_SL_CCY = "subLimitCCY";

	String FN_SL_AMOUNT = "subLimitAmount";

	String FN_ACTIVE_AMOUNT = "activeAmount";

	String FN_INNERFLAG = "innerFlag";

	// Page Name
	String PN_UPDATE_SL_LIST = "update_sl_list";

	String PN_PREPARE_UPDATE_ITEM = "prepare_update_item";

	String PN_RETURN_UPDATE_ITEM = "return_update_item";

	String PN_UPDATE_RETURN = "update_return";

	String PN_VIEW_SL = "sl_view";

	String PN_MK_CLOSE_CMDT = "mk_close_cmdt";

	String PN_MK_VIEW_CMDT = "mk_view_cmdt";

	String PN_CK_PROCESS_CMDT = "ck_process_cmdt";

	String PN_CK_PROCESS_VIEW_SL = "ck_process_view_sl";

	String PN_TRACK_CMDT = "track_cmdt";

	// Error Name
	String ERR_FOREX = "forex error";

	String ERR_DUPLICATE_SL = "emptySL";

	String ERR_DEPLICATE_SL_INFO = "error.collateral.commodity.sublimit.duplicate";

	String ERR_DELETE_SL = "deleteSL";

	String ERR_DELETE_SL_INFO = "error.collateral.commodity.sublimit.delete";

	String ERR_ACTIVE_AMOUNT = "error.collateral.commodity.sublimit.amount.active";

	String MSG_SLAMOUNT_HIGHER_APP_AMOUNT = "Sub Limit Amount is higher than Approved Limit Amount !";

	String MSG_TSLAMOUNT_HIGHER_TAPP_AMOUNT = "Total of Sub-Limits amount is higher than Total Approved Limit Amount !";
}
