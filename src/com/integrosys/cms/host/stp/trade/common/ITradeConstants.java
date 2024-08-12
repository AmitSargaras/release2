package com.integrosys.cms.host.stp.trade.common;


/**
 * Created by IntelliJ IDEA. User: Andy Wong Date: Sep 19, 2008 Time: 4:54:25 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITradeConstants {
	/* transaction type for Collateral */
	public static final String TRANS_TYPE_COL = "COL";

	/* transaction type for Limit */
	public static final String TRANS_TYPE_LIMIT = "FACILITY";

	// entity name for OBStpMasterTrans
	public static final String TRADE_MASTER_TRANS_ENTITY_NAME = "tradeMasterTrx";

	// entity name for OBStpTrans
	public static final String TRADE_TRANS_ENTITY_NAME = "tradeTrx";

	// entity name for OBStpTransHistory
	public static final String TRADE_TRANS_HISTORY_ENTITY_NAME = "tradeTrxHistory";

	// entity name for OBStpTransError
	public static final String TRADE_TRANS_ERROR_ENTITY_NAME = "tradeTrxError";

	// sequence for TRADE_TRANS TRX_UID
	public static final String SEQUENCE_TRADE_TRANS_UID = "STP_TRANS_UID_SEQ";

	// sequence for STP MBASE HDRNUM
	public static final String SEQUENCE_TRADE_TRANS_REF = "STP_TRANS_REF_SEQ";

	// STP operations
	public static final String OPS_DESC_CREATE = "CREATE";

	public static final String OPS_DESC_UPDATE = "UPDATE";

	public static final String OPS_DESC_DELETE = "DELETE";

	// STP Master Trx Status
	public static final String MASTER_TRX_QUEUE = "QUEUE";

	public static final String MASTER_TRX_LOADING = "LOADING";

	public static final String MASTER_TRX_COMPLETE = "COMPLETE";

	public static final String MASTER_TRX_PENDING_MAKER = "PENDING_MAKER";

	public static final String MASTER_TRX_PENDING_CHECKER = "PENDING_CHECKER";

	public static final String MASTER_TRX_RESET = "RESET";

	// STP Trx Status
	public static final String TRX_SUCCESS = "SUCCESS";

	public static final String TRX_FAILED = "FAILED";

	public static final String TRX_SENDING = "SENDING";

	public static final String TRX_OBSOLETE = "OBSOLETE";

	// Added by KLYong: Stp DSP header
	public static final String HDR_DSP_MORE_SEARCH_IND = "I13MORE";

	public static final String HDR_DSP_USER_ID = "I13USER";

	// Andy Wong: Stp MBASE header input field
	public static final String HDR_MBASE_RESEND_FIELD = "MBSOPT";

	public static final String HDR_MBASE_UID_FIELD = "MBUKEY";

	public static final String HDR_MBASE_TRX_DATE_FIELD = "HDDTIN";

	public static final String HDR_MBASE_TRX_TIME_FIELD = "HDTMIN";

	public static final String HDR_MBASE_REF_NUM_FIELD = "HDRNUM";

	public static final String HDR_MBASE_MORE_SEARCH_IND = "HDMREC";

	public static final String HDR_MBASE_MORE_SEARCH_METHOD = "HDSMTD";

	public static final String HDR_MBASE_USER_ID = "HDUSID";

	// STP Response code field
	public static final String RES_FIELD_ID = "HDRIND";

	public static final String HLEN_FIELD_ID = "SKTMLEN";

	public static final String RES_ERR_CODE1 = "HDRCD1";

	public static final String RES_ERR_REASON1 = "HDRRE1";

	public static final String RES_ERR_CODE2 = "HDRCD2";

	public static final String RES_ERR_REASON2 = "HDRRE2";

	public static final String RES_ERR_CODE3 = "HDRCD3";

	public static final String RES_ERR_REASON3 = "HDRRE3";

	public static final String RES_ERR_CODE4 = "HDRCD4";

	public static final String RES_ERR_REASON4 = "HDRRE4";

	public static final String RES_ERR_CODE5 = "HDRCD5";

	public static final String RES_ERR_REASON5 = "HDRRE5";

	public static final String RES_RECORD_RETURN = "HDNREC";

	public static final String RES_MSG_STAT = "I13MSTA";

	// STP Response Code
	public static final String RES_REPLY_SUCCESS = "AA";

	public static final String RES_REPLY_FAILED = "AB";

	// STP OB name in context
	public static final String COL_TRX_VALUE = "obCollateralTrxValue";

	public static final String FAC_TRX_VALUE = "obFacilityTrxValue";

	public static final String TRADE_TRX_VALUE = "obStpMasterTrans";

	public static final String LIMIT_TRX_VALUE = "obLimitTrxValue";

	public static final String ACTUAL_BIZ_OB = "actualBizOB";

	public static final String STAGE_BIZ_OB = "stageBizOB";

	// STP OB name in context
	public static final String OLD_UPD_TYPE = "OLD_UPDATE";

	public static final String NEW_UPD_TYPE = "NEW_UPDATE";

	public static final String NEW_CRT_TYPE = "NEW_CREATE";

	// STP CMS Error Code
	public static final String ERR_CODE_INVALID_HOST = "TechErr001";

	public static final String ERR_DESC_INVALID_HOST = "SIBS host not reachable.";

	public static final String ERR_CODE_TIMEOUT = "TechErr002";

	public static final String ERR_DESC_TIMEOUT = "Connection timeout to SIBS host. Please retry.";

	public static final String ERR_CODE_BAD_RESPONSE = "TechErr004";

	public static final String ERR_DESC_BAD_RESPONSE = "Bad response from SIBS host. Please retry.";

	public static final String ERR_CODE_GENERIC = "TechErr003";

	public static final String ERR_CODE_BIZ_GENERIC = "BizErr001";

	public static final String ERR_CODE_INVALID_PACK = "BizErr002";

	public static final String ERR_DESC_INVALID_PACK = " need to be numeric.";

	public static final String ERR_DESC_INVALID_TRX_STATUS = "Transaction is not in LOADING status due Stp configuration error.";

	// Stp transaction UID prefix
	public static final String TRADE_TRX_UID_PREFIX = "CMS";

	// Stp send/resend indicator
	public static final String TRADE_TRX_SEND_IND = "S";

	public static final String TRADE_TRX_RESEND_IND = "R";

	// Stp date and time format
	public static final String TRADE_DATE_PATTERN = "trade.date.pattern";

	public static final String TRADE_SHORT_DATE_PATTERN = "trade.short.date.pattern";

	public static final String TRADE_TIME_PATTERN = "trade.time.pattern";

	// Stp asynchronous poller properties config
	public static final String TRADE_ASYNC_POLLER_ENABLED = "trade.async.poller.enabled";

	public static final String TRADE_ASYNC_POLLER_INTERVAL = "trade.async.poller.interval";

	public static final String TRADE_ASYNC_TASK_INTERVAL = "trade.async.task.interval";

	// Additional property in Chain context
	public static final String FIELD_VAL_MAP = "FIELD_VAL_MAP";

	public static final String TRADE_TRANS_MAP = "TRADE_TRANS_MAP";

	public static final String IS_DELETE = "IS_DELETE";

	public static final String REF_NUM = "REF_NUM";

	// Stp Collateral Master Body Input Field
	public static final String MSG_NO_UNIT_FIELD = "CCDSHR";

	public static final String MSG_INS_FLAG_FIELD = "CCINSF";

	public static final String MSG_EXPIRY_DATE_FIELD = "WKTEX8";

	public static final String MSG_PROPERTY_USAGE_FIELD = "CCBDIC";

	public static final String MSG_INT_RATE_FIELD = "CCRATE";

	public static final String MSG_SEC_ISSUER_FIELD = "CCROUT";

	public static final String MSG_PN_CHEQUIE_FIELD = "CCPORC";

	public static final String MSG_PORT_REG_FIELD = "CCPORT";

	public static final String MSG_MODEL_NUMBER_FIELD = "CCMODN";

	public static final String MSG_MAKE_GOOD_FIELD = "CCMAKE";

	public static final String MSG_OWN_BANK_FIELD = "CCBOTH";

	public static final String MSG_EX_RATE_FIELD = "CCFXRT";

	public static final String MSG_CON_RATE_FIELD = "CCCRAT";

	public static final String MSG_EXC_RATE_FIELD = "CCEXRT";

	public static final String MSG_AMT_SOLD_FIELD = "CCSOLD";

	public static final String MSG_INS_POLICY_SEQ_FIELD = "CCDSEQ";

	public static final String MSG_APP_NO_FIELD = "CCAPNO";

	public static final String MSG_FAC_CODE_FIELD = "CCFCDE";

	public static final String MSG_ACC_SEQ_FIELD = "ACTSEQ";

	public static final String MSG_CHA_NUM_FIELD = "CCDCHN";

	public static final String MSG_REG_NUM_FIELD = "CCBRGN";

	public static final String MSG_PUR_PRICE_FIELD = "CCPAMT";

	public static final String MSG_TRADE_DEP_FIELD = "LNVTRP";

	public static final String MSG_TRADE_IN_FIELD = "LNVTRI";

	public static final String MSG_TITLE_TYPE_FIELD = "CCPREX";

	public static final String MSG_PROP_LOT_NO = "CCDLOT";

	public static final String MSG_CHARGE_SEC_RANK = "CCHGTP";

	public static final String MSG_COL_DESC = "CCDDSC";

	public static final String MSG_FAC_CURRENCY_FIELD = "AFCUR";

	public static final String MSG_DEP_EXPIRY_DATE = "D8CCEXP6";

	public static final String MSG_LAND_AREA = "CCLAND";

	public static final String MSG_BUILT_UP_AREA = "CCBUAR";

	public static final String MSG_TENURE_PERIOD = "CCTENT";

	public static final String MSG_ISSUE_DATE = "D8CCISS6";

	public static final String MSG_BOND_NO = "CCBOND";

	public static final String MSG_BENEFIT_NAME = "CCBNME";

	public static final String MSG_BOND_MATURE_DATE = "D8CCBND6";

	public static final String MSG_GUARANTEE_DATE = "D8CCCFD6";

	public static final String MSG_REIMBURSE_BANK = "CCREBN";

	public static final String MSG_CONTRACT_DATE = "D8CCCDT6";

	public static final String MSG_ISSUE_INSTITUTE = "CCINST";

	public static final String MSG_APPRAISED_VALUE = "CCDAPV";

	public static final String MSG_CUR_MARKET_VALUE = "CCDCMV";

	public static final String SIBS_VEH_COL_CODE = "sibs.veh.col.code";

	// KLYong: Socket header static value
	public static final String TRADE_SKT_HDR_NUMBER_RECORD = "trade.number.record.return";

	public static final String TRADE_DSP_TIMEOUT_CODE = ".DSP0003";

	public static final String TRADE_DFT_CURR = "trade.default.currency";

	public static final String TRADE_SWITCH_MODE = "trade.inquiry.enabled";

	// KLYong: Application resources properties
	public static final String ERR_TRADE_INQUIRY = "error.trade.inquiry";

	public static final String ERR_TRADE_PACKVALUE = "error.trade.packvalue";

	public static final String ERR_TRADE_REQUIRED_FIELD = "error.trade.required.field";

	public static final String APPLICATION_TYPE = "CC";

	// Collateral master default valuer code
	public static final String DEFAULT_VALUER = "trade.default.valuer.code";

	// Islamic STP Loan Type
	public static final String TRADE_ISLAMIC_LOAN_TYPE_MASTER = "1";

	public static final String TRADE_ISLAMIC_LOAN_TYPE_BBA = "2";

	public static final String TRADE_ISLAMIC_LOAN_TYPE_CORPORATE = "3";

    public static final String RESPONSE_CODE_TAG_NAME = "ResponseCode";

    public static final String GOOD_RESPONSE_CODE = "0";
}