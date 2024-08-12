/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/commodityglobal/sublimittype/SLTUIConstants.java,v 1.1 2005/10/06 06:19:19 hmbao Exp $
 */
package com.integrosys.cms.ui.commodityglobal.sublimittype;

/**
 * Describe this class. Purpose: Description:
 * 
 * @author BaoHongMan
 * @version R1.4
 * @since 2005-9-16
 * @Tag com.integrosys.cms.ui.commodityglobal.sublimittype.SLTUIConstants.java
 */
public interface SLTUIConstants {
	// A (attribute) : Objects stored as attributes in
	// HttpRequest||HttpSession||Map.
	public static final String AN_SLT_TRX_VALUE = "sltTraValue";

	public static final String AN_OB_TRX_CONTEXT = "theOBTrxContext";

	public static final String AN_OB_SL = "theSubLimitObject";

	public static final String AN_OB_SLT = "theOBSubLimitType";

	public static final String AN_OB_MAP = "theMapObject";

	public static final String AN_OB_LIST = "theCollectionObject";

	public static final String AN_LIMIT_TYPE_VALUE_COLL = "limitTypeValueColl";

	public static final String AN_LIMIT_TYPE_LABEL_COLL = "limitTypeLabelColl";

	public static final String AN_REQ_TRX_VALUE = "request.ITrxValue";

	public static final String AN_TRX_ID = "trxID";

	// CN (class name) : Full qualified class name.
	public static final String CN_TRX_CONTEXT_MAPPER = "com.integrosys.cms.ui.common.TrxContextMapper";

	public static final String CN_OB_TRX_CONTEXT = "com.integrosys.cms.app.transaction.OBTrxContext";

	public static final String CN_I_SLT_TRX_VALUE = "com.integrosys.cms.app.commodity.main.trx.sublimittype.ISubLimitTypeTrxValue";

	public static final String CN_I_SLT = "com.integrosys.cms.app.commodity.main.bus.sublimittype.ISubLimitType";

	public static final String CN_LIST_SLT_ACTION = "com.integrosys.cms.ui.commodityglobal.sublimittype.list.SubLimitTypeListAction";

	public static final String CN_SLT_ACTION = "com.integrosys.cms.ui.commodityglobal.sublimittype.SubLimitTypeAction";

	public static final String CN_SLT_LIST_MAPPER = "com.integrosys.cms.ui.commodityglobal.sublimittype.list.SubLimitTypeListMapper";

	public static final String CN_SLT_ITEM_MAPPER = "com.integrosys.cms.ui.commodityglobal.sublimittype.item.SubLimitTypeItemMapper";

	public static final String CN_STRING = "java.lang.String";

	public static final String CN_HASHMAP = "java.util.HashMap";

	public static final String CN_COLLECTION = "java.util.Collection";

	public static final String CN_LIST = "java.util.List";

	// FN (field name) : Mainly used in JSP page.
	public static final String FN_LIMIT_TYPE = "limitType";

	public static final String FN_SUB_LIMIT_TYPE = "subLimitType";

	public static final String FN_IDX_ID = "indexID";

	// PN (page name) : Mainly JSP page name.
	public static final String PN_WORK_IN_PROGRESS = "work_in_progress";

	public static final String PN_SLT_LIST_UPDATE = "update_slt_list";

	public static final String PN_SLT_LIST_CK_PROCESS = "ck_process_slt_list";

	public static final String PN_SLT_ACK_SAVE = "ack_save";

	public static final String PN_SLT_ACK_SUBMIT = "ack_submit";

	public static final String PN_SLT_ACK_APPROVE = "ack_approve";

	public static final String PN_SLT_ACK_REJECT = "ack_reject";

	public static final String PN_SLT_LIST_VIEW = "view_slt_list";

	public static final String PN_PRE_CLOSE = "prepare_close";

	public static final String PN_SLT_ACK_CLOSE = "ack_close";

	public static final String PN_SLT_ITEM_UPDATE = "update_slt_item";

	public static final String PN_SLT_ITEM_UPDATE_RETURN = "update_slt_item_return";

	public static final String PN_EDIT_ITEM = "edit_item";

	// EN (event name) : The name of event.
	public static final String EN_PREPARE_ADD = "prepare_add";

	public static final String EN_PREPARE_UPDATE = "prepare_update";

	public static final String EN_UPDATE_RETURN = "update_return";

	public static final String EN_TO_TRACK = "to_track";

	public static final String EN_MK_PROCESS = "mk_process";

	public static final String EN_PRE_CLOSE = "prepare_close";

	public final static String EN_CLOSE = "close";

	public static final String EN_CK_PROCESS = "ck_process";

	public static final String EN_UPDATE_SLT_ITEM = "update_slt_item";

	// SN (status name) : The name of trasaction status.
	public static final String SN_WORK_IN_PROGRESS = "work_in_progress";

	// Constant : Some constants or hardcodes value data.
	public static final String CONSTANT_COMMODITY_CATEGORY_CODE = "27";

	// ERR (Error) : The name of Error
	public static final String ERR_DUPLICATE_SLT = "duplicateSLT";

	public static final String ERR_DUPLICATE_SLT_INFO = "error.sublimittype.duplicate";

	public static final String ERR_EMPTY_SLT = "emptySLT";

	public static final String ERR_EMPTY_SLT_INFO = "error.sublimittype.empty";
}
