/**
 * ReplyMessageType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.infra.enumeration;

public class ReplyMessageType implements java.io.Serializable {
	
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ReplyMessageType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _MAX_LIMIT = "MAX_LIMIT";
    public static final java.lang.String _AUTH_THIRD_PARTY_LIMIT = "AUTH_THIRD_PARTY_LIMIT";
    public static final java.lang.String _AUTH_MATRIX_LIMIT = "AUTH_MATRIX_LIMIT";
    public static final java.lang.String _INSTR_UNCLAIMED = "INSTR_UNCLAIMED";
    public static final java.lang.String _INSUFF_FUNDS_OVL = "INSUFF_FUNDS_OVL";
    public static final java.lang.String _DUP_BC_PRINT = "DUP_BC_PRINT";
    public static final java.lang.String _AUTH_LN_WAIVER = "AUTH_LN_WAIVER";
    public static final java.lang.String _DUP_BC_INSTR_LOST = "DUP_BC_INSTR_LOST";
    public static final java.lang.String _MINOR_NOT_SOW = "MINOR_NOT_SOW";
    public static final java.lang.String _MULT_CUST_FOUND = "MULT_CUST_FOUND";
    public static final java.lang.String _INSTR_NOT_PAID = "INSTR_NOT_PAID";
    public static final java.lang.String _INV_FLG_REV_CR = "INV_FLG_REV_CR";
    public static final java.lang.String _LN_BEYOND_VALUE_DATE = "LN_BEYOND_VALUE_DATE";
    public static final java.lang.String _FC_LINE_CODE_INV = "FC_LINE_CODE_INV";
    public static final java.lang.String _RECORD_PRESENT = "RECORD_PRESENT";
    public static final java.lang.String _INSTR_CAUTION = "INSTR_CAUTION";
    public static final java.lang.String _REORDER_LEVEL = "REORDER_LEVEL";
    public static final java.lang.String _GRP_MIN_BAL = "GRP_MIN_BAL";
    public static final java.lang.String _REPRESENT_CHQ = "REPRESENT_CHQ";
    public static final java.lang.String _SUCCESS = "SUCCESS";
    public static final java.lang.String _INSUFF_FUNDS_OVL_HOLD_EXCEED = "INSUFF_FUNDS_OVL_HOLD_EXCEED";
    public static final java.lang.String _INSUFF_PDC_PURCH = "INSUFF_PDC_PURCH";
    public static final java.lang.String _JOINT_ACCT_HOLDER = "JOINT_ACCT_HOLDER";
    public static final java.lang.String _INV_TO_ACCT_STAT = "INV_TO_ACCT_STAT";
    public static final java.lang.String _INV_PROC_DATE = "INV_PROC_DATE";
    public static final java.lang.String _AUTH_SC_CHANGE = "AUTH_SC_CHANGE";
    public static final java.lang.String _NOCR_ONACCT = "NOCR_ONACCT";
    public static final java.lang.String _INVALID_DATE = "INVALID_DATE";
    public static final java.lang.String _DELAY_TXN = "DELAY_TXN";
    public static final java.lang.String _MULCCY_CHECK = "MULCCY_CHECK";
    public static final java.lang.String _PROD_CHECK = "PROD_CHECK";
    public static final java.lang.String _NSF_RECV_TODAY = "NSF_RECV_TODAY";
    public static final java.lang.String _MAX_LIMIT_ACCTS = "MAX_LIMIT_ACCTS";
    public static final java.lang.String _NODR_ONACCT = "NODR_ONACCT";
    public static final java.lang.String _ACC_EXISTS = "ACC_EXISTS";
    public static final java.lang.String _INSTR_STALE = "INSTR_STALE";
    public static final java.lang.String _HOLD_FUND_ON_ACCT = "HOLD_FUND_ON_ACCT";
    public static final java.lang.String _FC_LINE_EXCEEDED = "FC_LINE_EXCEEDED";
    public static final java.lang.String _AUTH_INTER_BRANCH = "AUTH_INTER_BRANCH";
    public static final java.lang.String _INSUFF_FUNDS_TOD = "INSUFF_FUNDS_TOD";
    public static final java.lang.String _LIMIT_BREACH = "LIMIT_BREACH";
    public static final java.lang.String _RD_INST_PAID = "RD_INST_PAID";
    public static final java.lang.String _INV_INSTR_LQD_BANK = "INV_INSTR_LQD_BANK";
    public static final java.lang.String _INSTR_REFUNDED = "INSTR_REFUNDED";
    public static final java.lang.String _INSTR_PAID = "INSTR_PAID";
    public static final java.lang.String _INSTR_LOST = "INSTR_LOST";
    public static final java.lang.String _ACCT_INFO_TO_CHNG = "ACCT_INFO_TO_CHNG";
    public static final java.lang.String _ACCT_NT_CLS = "ACCT_NT_CLS";
    public static final java.lang.String _INV_ORIG_TXN = "INV_ORIG_TXN";
    public static final java.lang.String _AUTH_TXN_RATE_CHANGE = "AUTH_TXN_RATE_CHANGE";
    public static final java.lang.String _INSUFF_FUNDS_OVL_EXCEED = "INSUFF_FUNDS_OVL_EXCEED";
    public static final java.lang.String _ACCT_CLOSED_ERR = "ACCT_CLOSED_ERR";
    public static final java.lang.String _ACC_INOPERATIVE = "ACC_INOPERATIVE";
    public static final java.lang.String _ACCT_NT_FLG_CLS = "ACCT_NT_FLG_CLS";
    public static final java.lang.String _AUTH_PAYABLE_LIMIT = "AUTH_PAYABLE_LIMIT";
    public static final java.lang.String _INV_TO_ACCT = "INV_TO_ACCT";
    public static final java.lang.String _INSTR_STOPPED = "INSTR_STOPPED";
    public static final java.lang.String _INSUFF_FUNDS_OVL_HOLD = "INSUFF_FUNDS_OVL_HOLD";
    public static final java.lang.String _INST_PART_AMT_SUFF = "INST_PART_AMT_SUFF";
    public static final java.lang.String _ACCT_SUSPENDED = "ACCT_SUSPENDED";
    public static final java.lang.String _MEMO_ON_LN_ACCT = "MEMO_ON_LN_ACCT";
    public static final java.lang.String _AUTO_REV_DONE = "AUTO_REV_DONE";
    public static final java.lang.String _INSUFF_FUNDS_TOD_HOLD = "INSUFF_FUNDS_TOD_HOLD";
    public static final java.lang.String _DB_ERROR = "DB_ERROR";
    public static final java.lang.String _TXN_ALREADY_SERVICED = "TXN_ALREADY_SERVICED";
    public static final java.lang.String _AUTH_INTERBRANCH_LIMIT = "AUTH_INTERBRANCH_LIMIT";
    public static final java.lang.String _RED_MODE_DIFFERENT = "RED_MODE_DIFFERENT";
    public static final java.lang.String _PAY_MODE_NT_SAME = "PAY_MODE_NT_SAME";
    public static final java.lang.String _NLS_DETAILS_NOT_FOUND = "NLS_DETAILS_NOT_FOUND";
    public static final java.lang.String _RECORD_NOT_FOUND = "RECORD_NOT_FOUND";
    public static final java.lang.String _INV_INSTR_ACCT_ST = "INV_INSTR_ACCT_ST";
    public static final java.lang.String _ORG_TXN_SUSP = "ORG_TXN_SUSP";
    public static final java.lang.String _VAL_DT_CLG_NOT_RUN = "VAL_DT_CLG_NOT_RUN";
    public static final java.lang.String _ACCT_WITHOUTCARD = "ACCT_WITHOUTCARD";
    public static final java.lang.String _INV_ACCT_STAT = "INV_ACCT_STAT";
    public static final java.lang.String _INV_USER_ACCESS = "INV_USER_ACCESS";
    public static final java.lang.String _INV_INSTR_NO = "INV_INSTR_NO";
    public static final java.lang.String _INV_PAYEE_ACCT = "INV_PAYEE_ACCT";
    public static final java.lang.String _INV_INSTR_STAT = "INV_INSTR_STAT";
    public static final java.lang.String _PRIM_CUST_NOT_MIN = "PRIM_CUST_NOT_MIN";
    public static final java.lang.String _INV_INSTR_DETAILS = "INV_INSTR_DETAILS";
    public static final java.lang.String _DORMANT_CUSTOMER = "DORMANT_CUSTOMER";
    public static final java.lang.String _ACCT_BAL_NT_ZERO = "ACCT_BAL_NT_ZERO";
    public static final java.lang.String _INV_INSTR_AMT = "INV_INSTR_AMT";
    public static final java.lang.String _ERR_INSUFF_FUNDS = "ERR_INSUFF_FUNDS";
    public static final java.lang.String _FC_DDA_ST_INFO_001 = "FC_DDA_ST_INFO_001";
    public static final java.lang.String _INV_DEST_BRANCH = "INV_DEST_BRANCH";
    public static final java.lang.String _OLTP_INT_ERROR = "OLTP_INT_ERROR";
    public static final java.lang.String _TOO_MANY_WITHDRAWALS = "TOO_MANY_WITHDRAWALS";
    public static final java.lang.String _ACCT_INFO_CHANGED = "ACCT_INFO_CHANGED";
    public static final java.lang.String _ACCT_WITHCARD = "ACCT_WITHCARD";
    public static final java.lang.String _CREDIT_OVRIDE = "CREDIT_OVRIDE";
    public static final java.lang.String _DEBIT_OVRIDE = "DEBIT_OVRIDE";
    public static final java.lang.String _INV_TXN_CCY = "INV_TXN_CCY";
    public static final java.lang.String _PDC_CREDITED = "PDC_CREDITED";
    public static final java.lang.String _AUTH_SAMEBRANCH_LIMIT = "AUTH_SAMEBRANCH_LIMIT";
    public static final java.lang.String _INV_INSTR_ACCT = "INV_INSTR_ACCT";
    public static final java.lang.String _INSTR_STAT_CANCELLED = "INSTR_STAT_CANCELLED";
    public static final java.lang.String _PRE_MATURE_REDEM = "PRE_MATURE_REDEM";
    public static final java.lang.String _NO_OUTSTANDING_PO = "NO_OUTSTANDING_PO";
    public static final java.lang.String _MEMO_ON_CR_ACCT = "MEMO_ON_CR_ACCT";
    public static final java.lang.String _OVER_PER_TXN_LIM = "OVER_PER_TXN_LIM";
    public static final java.lang.String _INV_INSTR_BANK_CODE = "INV_INSTR_BANK_CODE";
    public static final java.lang.String _INSUFF_SWPIN_BAL = "INSUFF_SWPIN_BAL";
    public static final java.lang.String _AUTH_LN_NO_PAYMENT_EXCEEDED = "AUTH_LN_NO_PAYMENT_EXCEEDED";
    public static final java.lang.String _ALT_ACCT_PRESENT = "ALT_ACCT_PRESENT";
    public static final java.lang.String _ACCT_BLOCKED_ERR = "ACCT_BLOCKED_ERR";
    public static final java.lang.String _MEMO_ON_TD_ACCT = "MEMO_ON_TD_ACCT";
    public static final java.lang.String _FC_SYNC_ERROR = "FC_SYNC_ERROR";
    public static final java.lang.String _AUTH_DUAL_CONTROL = "AUTH_DUAL_CONTROL";
    public static final java.lang.String _MEMO_ON_ACCT = "MEMO_ON_ACCT";
    public static final java.lang.String _INV_INSTR_CCY = "INV_INSTR_CCY";
    public static final java.lang.String _CUST_NT_SAME = "CUST_NT_SAME";
    public static final java.lang.String _ACCT_DORMANT = "ACCT_DORMANT";
    public static final java.lang.String _INSUFF_FUNDS_AMB = "INSUFF_FUNDS_AMB";
    public static final java.lang.String _INV_ACCT_CODE = "INV_ACCT_CODE";
    public static final java.lang.String _MEMO_ON_DR_ACCT = "MEMO_ON_DR_ACCT";
    public static final java.lang.String _MEMO_INSTR_AC = "MEMO_INSTR_AC";
    public static final java.lang.String _AUTH_CUSTOMER_LIMIT = "AUTH_CUSTOMER_LIMIT";
    public static final java.lang.String _ACCT_CLOSED_TODAY = "ACCT_CLOSED_TODAY";
    public static final java.lang.String _LOCKIN_TERM_VIOLATE = "LOCKIN_TERM_VIOLATE";
    public static final java.lang.String _AUTH_FX_LIMIT = "AUTH_FX_LIMIT";
    public static final java.lang.String _OVER_IB_WDR_LIM = "OVER_IB_WDR_LIM";
    public static final java.lang.String _FC_RISK_EXCEEDED = "FC_RISK_EXCEEDED";
    public static final java.lang.String _FC_LINK_FAILED = "FC_LINK_FAILED";
    public static final java.lang.String _AUTH_ACCT_RATE_CHANGE = "AUTH_ACCT_RATE_CHANGE";
    public static final java.lang.String _STOP_ON_ACCOUNT = "STOP_ON_ACCOUNT";
    public static final java.lang.String _AUTO_APPROVED = "AUTO_APPROVED";
    public static final ReplyMessageType MAX_LIMIT = new ReplyMessageType(_MAX_LIMIT);
    public static final ReplyMessageType AUTH_THIRD_PARTY_LIMIT = new ReplyMessageType(_AUTH_THIRD_PARTY_LIMIT);
    public static final ReplyMessageType AUTH_MATRIX_LIMIT = new ReplyMessageType(_AUTH_MATRIX_LIMIT);
    public static final ReplyMessageType INSTR_UNCLAIMED = new ReplyMessageType(_INSTR_UNCLAIMED);
    public static final ReplyMessageType INSUFF_FUNDS_OVL = new ReplyMessageType(_INSUFF_FUNDS_OVL);
    public static final ReplyMessageType DUP_BC_PRINT = new ReplyMessageType(_DUP_BC_PRINT);
    public static final ReplyMessageType AUTH_LN_WAIVER = new ReplyMessageType(_AUTH_LN_WAIVER);
    public static final ReplyMessageType DUP_BC_INSTR_LOST = new ReplyMessageType(_DUP_BC_INSTR_LOST);
    public static final ReplyMessageType MINOR_NOT_SOW = new ReplyMessageType(_MINOR_NOT_SOW);
    public static final ReplyMessageType MULT_CUST_FOUND = new ReplyMessageType(_MULT_CUST_FOUND);
    public static final ReplyMessageType INSTR_NOT_PAID = new ReplyMessageType(_INSTR_NOT_PAID);
    public static final ReplyMessageType INV_FLG_REV_CR = new ReplyMessageType(_INV_FLG_REV_CR);
    public static final ReplyMessageType LN_BEYOND_VALUE_DATE = new ReplyMessageType(_LN_BEYOND_VALUE_DATE);
    public static final ReplyMessageType FC_LINE_CODE_INV = new ReplyMessageType(_FC_LINE_CODE_INV);
    public static final ReplyMessageType RECORD_PRESENT = new ReplyMessageType(_RECORD_PRESENT);
    public static final ReplyMessageType INSTR_CAUTION = new ReplyMessageType(_INSTR_CAUTION);
    public static final ReplyMessageType REORDER_LEVEL = new ReplyMessageType(_REORDER_LEVEL);
    public static final ReplyMessageType GRP_MIN_BAL = new ReplyMessageType(_GRP_MIN_BAL);
    public static final ReplyMessageType REPRESENT_CHQ = new ReplyMessageType(_REPRESENT_CHQ);
    public static final ReplyMessageType SUCCESS = new ReplyMessageType(_SUCCESS);
    public static final ReplyMessageType INSUFF_FUNDS_OVL_HOLD_EXCEED = new ReplyMessageType(_INSUFF_FUNDS_OVL_HOLD_EXCEED);
    public static final ReplyMessageType INSUFF_PDC_PURCH = new ReplyMessageType(_INSUFF_PDC_PURCH);
    public static final ReplyMessageType JOINT_ACCT_HOLDER = new ReplyMessageType(_JOINT_ACCT_HOLDER);
    public static final ReplyMessageType INV_TO_ACCT_STAT = new ReplyMessageType(_INV_TO_ACCT_STAT);
    public static final ReplyMessageType INV_PROC_DATE = new ReplyMessageType(_INV_PROC_DATE);
    public static final ReplyMessageType AUTH_SC_CHANGE = new ReplyMessageType(_AUTH_SC_CHANGE);
    public static final ReplyMessageType NOCR_ONACCT = new ReplyMessageType(_NOCR_ONACCT);
    public static final ReplyMessageType INVALID_DATE = new ReplyMessageType(_INVALID_DATE);
    public static final ReplyMessageType DELAY_TXN = new ReplyMessageType(_DELAY_TXN);
    public static final ReplyMessageType MULCCY_CHECK = new ReplyMessageType(_MULCCY_CHECK);
    public static final ReplyMessageType PROD_CHECK = new ReplyMessageType(_PROD_CHECK);
    public static final ReplyMessageType NSF_RECV_TODAY = new ReplyMessageType(_NSF_RECV_TODAY);
    public static final ReplyMessageType MAX_LIMIT_ACCTS = new ReplyMessageType(_MAX_LIMIT_ACCTS);
    public static final ReplyMessageType NODR_ONACCT = new ReplyMessageType(_NODR_ONACCT);
    public static final ReplyMessageType ACC_EXISTS = new ReplyMessageType(_ACC_EXISTS);
    public static final ReplyMessageType INSTR_STALE = new ReplyMessageType(_INSTR_STALE);
    public static final ReplyMessageType HOLD_FUND_ON_ACCT = new ReplyMessageType(_HOLD_FUND_ON_ACCT);
    public static final ReplyMessageType FC_LINE_EXCEEDED = new ReplyMessageType(_FC_LINE_EXCEEDED);
    public static final ReplyMessageType AUTH_INTER_BRANCH = new ReplyMessageType(_AUTH_INTER_BRANCH);
    public static final ReplyMessageType INSUFF_FUNDS_TOD = new ReplyMessageType(_INSUFF_FUNDS_TOD);
    public static final ReplyMessageType LIMIT_BREACH = new ReplyMessageType(_LIMIT_BREACH);
    public static final ReplyMessageType RD_INST_PAID = new ReplyMessageType(_RD_INST_PAID);
    public static final ReplyMessageType INV_INSTR_LQD_BANK = new ReplyMessageType(_INV_INSTR_LQD_BANK);
    public static final ReplyMessageType INSTR_REFUNDED = new ReplyMessageType(_INSTR_REFUNDED);
    public static final ReplyMessageType INSTR_PAID = new ReplyMessageType(_INSTR_PAID);
    public static final ReplyMessageType INSTR_LOST = new ReplyMessageType(_INSTR_LOST);
    public static final ReplyMessageType ACCT_INFO_TO_CHNG = new ReplyMessageType(_ACCT_INFO_TO_CHNG);
    public static final ReplyMessageType ACCT_NT_CLS = new ReplyMessageType(_ACCT_NT_CLS);
    public static final ReplyMessageType INV_ORIG_TXN = new ReplyMessageType(_INV_ORIG_TXN);
    public static final ReplyMessageType AUTH_TXN_RATE_CHANGE = new ReplyMessageType(_AUTH_TXN_RATE_CHANGE);
    public static final ReplyMessageType INSUFF_FUNDS_OVL_EXCEED = new ReplyMessageType(_INSUFF_FUNDS_OVL_EXCEED);
    public static final ReplyMessageType ACCT_CLOSED_ERR = new ReplyMessageType(_ACCT_CLOSED_ERR);
    public static final ReplyMessageType ACC_INOPERATIVE = new ReplyMessageType(_ACC_INOPERATIVE);
    public static final ReplyMessageType ACCT_NT_FLG_CLS = new ReplyMessageType(_ACCT_NT_FLG_CLS);
    public static final ReplyMessageType AUTH_PAYABLE_LIMIT = new ReplyMessageType(_AUTH_PAYABLE_LIMIT);
    public static final ReplyMessageType INV_TO_ACCT = new ReplyMessageType(_INV_TO_ACCT);
    public static final ReplyMessageType INSTR_STOPPED = new ReplyMessageType(_INSTR_STOPPED);
    public static final ReplyMessageType INSUFF_FUNDS_OVL_HOLD = new ReplyMessageType(_INSUFF_FUNDS_OVL_HOLD);
    public static final ReplyMessageType INST_PART_AMT_SUFF = new ReplyMessageType(_INST_PART_AMT_SUFF);
    public static final ReplyMessageType ACCT_SUSPENDED = new ReplyMessageType(_ACCT_SUSPENDED);
    public static final ReplyMessageType MEMO_ON_LN_ACCT = new ReplyMessageType(_MEMO_ON_LN_ACCT);
    public static final ReplyMessageType AUTO_REV_DONE = new ReplyMessageType(_AUTO_REV_DONE);
    public static final ReplyMessageType INSUFF_FUNDS_TOD_HOLD = new ReplyMessageType(_INSUFF_FUNDS_TOD_HOLD);
    public static final ReplyMessageType DB_ERROR = new ReplyMessageType(_DB_ERROR);
    public static final ReplyMessageType TXN_ALREADY_SERVICED = new ReplyMessageType(_TXN_ALREADY_SERVICED);
    public static final ReplyMessageType AUTH_INTERBRANCH_LIMIT = new ReplyMessageType(_AUTH_INTERBRANCH_LIMIT);
    public static final ReplyMessageType RED_MODE_DIFFERENT = new ReplyMessageType(_RED_MODE_DIFFERENT);
    public static final ReplyMessageType PAY_MODE_NT_SAME = new ReplyMessageType(_PAY_MODE_NT_SAME);
    public static final ReplyMessageType NLS_DETAILS_NOT_FOUND = new ReplyMessageType(_NLS_DETAILS_NOT_FOUND);
    public static final ReplyMessageType RECORD_NOT_FOUND = new ReplyMessageType(_RECORD_NOT_FOUND);
    public static final ReplyMessageType INV_INSTR_ACCT_ST = new ReplyMessageType(_INV_INSTR_ACCT_ST);
    public static final ReplyMessageType ORG_TXN_SUSP = new ReplyMessageType(_ORG_TXN_SUSP);
    public static final ReplyMessageType VAL_DT_CLG_NOT_RUN = new ReplyMessageType(_VAL_DT_CLG_NOT_RUN);
    public static final ReplyMessageType ACCT_WITHOUTCARD = new ReplyMessageType(_ACCT_WITHOUTCARD);
    public static final ReplyMessageType INV_ACCT_STAT = new ReplyMessageType(_INV_ACCT_STAT);
    public static final ReplyMessageType INV_USER_ACCESS = new ReplyMessageType(_INV_USER_ACCESS);
    public static final ReplyMessageType INV_INSTR_NO = new ReplyMessageType(_INV_INSTR_NO);
    public static final ReplyMessageType INV_PAYEE_ACCT = new ReplyMessageType(_INV_PAYEE_ACCT);
    public static final ReplyMessageType INV_INSTR_STAT = new ReplyMessageType(_INV_INSTR_STAT);
    public static final ReplyMessageType PRIM_CUST_NOT_MIN = new ReplyMessageType(_PRIM_CUST_NOT_MIN);
    public static final ReplyMessageType INV_INSTR_DETAILS = new ReplyMessageType(_INV_INSTR_DETAILS);
    public static final ReplyMessageType DORMANT_CUSTOMER = new ReplyMessageType(_DORMANT_CUSTOMER);
    public static final ReplyMessageType ACCT_BAL_NT_ZERO = new ReplyMessageType(_ACCT_BAL_NT_ZERO);
    public static final ReplyMessageType INV_INSTR_AMT = new ReplyMessageType(_INV_INSTR_AMT);
    public static final ReplyMessageType ERR_INSUFF_FUNDS = new ReplyMessageType(_ERR_INSUFF_FUNDS);
    public static final ReplyMessageType FC_DDA_ST_INFO_001 = new ReplyMessageType(_FC_DDA_ST_INFO_001);
    public static final ReplyMessageType INV_DEST_BRANCH = new ReplyMessageType(_INV_DEST_BRANCH);
    public static final ReplyMessageType OLTP_INT_ERROR = new ReplyMessageType(_OLTP_INT_ERROR);
    public static final ReplyMessageType TOO_MANY_WITHDRAWALS = new ReplyMessageType(_TOO_MANY_WITHDRAWALS);
    public static final ReplyMessageType ACCT_INFO_CHANGED = new ReplyMessageType(_ACCT_INFO_CHANGED);
    public static final ReplyMessageType ACCT_WITHCARD = new ReplyMessageType(_ACCT_WITHCARD);
    public static final ReplyMessageType CREDIT_OVRIDE = new ReplyMessageType(_CREDIT_OVRIDE);
    public static final ReplyMessageType DEBIT_OVRIDE = new ReplyMessageType(_DEBIT_OVRIDE);
    public static final ReplyMessageType INV_TXN_CCY = new ReplyMessageType(_INV_TXN_CCY);
    public static final ReplyMessageType PDC_CREDITED = new ReplyMessageType(_PDC_CREDITED);
    public static final ReplyMessageType AUTH_SAMEBRANCH_LIMIT = new ReplyMessageType(_AUTH_SAMEBRANCH_LIMIT);
    public static final ReplyMessageType INV_INSTR_ACCT = new ReplyMessageType(_INV_INSTR_ACCT);
    public static final ReplyMessageType INSTR_STAT_CANCELLED = new ReplyMessageType(_INSTR_STAT_CANCELLED);
    public static final ReplyMessageType PRE_MATURE_REDEM = new ReplyMessageType(_PRE_MATURE_REDEM);
    public static final ReplyMessageType NO_OUTSTANDING_PO = new ReplyMessageType(_NO_OUTSTANDING_PO);
    public static final ReplyMessageType MEMO_ON_CR_ACCT = new ReplyMessageType(_MEMO_ON_CR_ACCT);
    public static final ReplyMessageType OVER_PER_TXN_LIM = new ReplyMessageType(_OVER_PER_TXN_LIM);
    public static final ReplyMessageType INV_INSTR_BANK_CODE = new ReplyMessageType(_INV_INSTR_BANK_CODE);
    public static final ReplyMessageType INSUFF_SWPIN_BAL = new ReplyMessageType(_INSUFF_SWPIN_BAL);
    public static final ReplyMessageType AUTH_LN_NO_PAYMENT_EXCEEDED = new ReplyMessageType(_AUTH_LN_NO_PAYMENT_EXCEEDED);
    public static final ReplyMessageType ALT_ACCT_PRESENT = new ReplyMessageType(_ALT_ACCT_PRESENT);
    public static final ReplyMessageType ACCT_BLOCKED_ERR = new ReplyMessageType(_ACCT_BLOCKED_ERR);
    public static final ReplyMessageType MEMO_ON_TD_ACCT = new ReplyMessageType(_MEMO_ON_TD_ACCT);
    public static final ReplyMessageType FC_SYNC_ERROR = new ReplyMessageType(_FC_SYNC_ERROR);
    public static final ReplyMessageType AUTH_DUAL_CONTROL = new ReplyMessageType(_AUTH_DUAL_CONTROL);
    public static final ReplyMessageType MEMO_ON_ACCT = new ReplyMessageType(_MEMO_ON_ACCT);
    public static final ReplyMessageType INV_INSTR_CCY = new ReplyMessageType(_INV_INSTR_CCY);
    public static final ReplyMessageType CUST_NT_SAME = new ReplyMessageType(_CUST_NT_SAME);
    public static final ReplyMessageType ACCT_DORMANT = new ReplyMessageType(_ACCT_DORMANT);
    public static final ReplyMessageType INSUFF_FUNDS_AMB = new ReplyMessageType(_INSUFF_FUNDS_AMB);
    public static final ReplyMessageType INV_ACCT_CODE = new ReplyMessageType(_INV_ACCT_CODE);
    public static final ReplyMessageType MEMO_ON_DR_ACCT = new ReplyMessageType(_MEMO_ON_DR_ACCT);
    public static final ReplyMessageType MEMO_INSTR_AC = new ReplyMessageType(_MEMO_INSTR_AC);
    public static final ReplyMessageType AUTH_CUSTOMER_LIMIT = new ReplyMessageType(_AUTH_CUSTOMER_LIMIT);
    public static final ReplyMessageType ACCT_CLOSED_TODAY = new ReplyMessageType(_ACCT_CLOSED_TODAY);
    public static final ReplyMessageType LOCKIN_TERM_VIOLATE = new ReplyMessageType(_LOCKIN_TERM_VIOLATE);
    public static final ReplyMessageType AUTH_FX_LIMIT = new ReplyMessageType(_AUTH_FX_LIMIT);
    public static final ReplyMessageType OVER_IB_WDR_LIM = new ReplyMessageType(_OVER_IB_WDR_LIM);
    public static final ReplyMessageType FC_RISK_EXCEEDED = new ReplyMessageType(_FC_RISK_EXCEEDED);
    public static final ReplyMessageType FC_LINK_FAILED = new ReplyMessageType(_FC_LINK_FAILED);
    public static final ReplyMessageType AUTH_ACCT_RATE_CHANGE = new ReplyMessageType(_AUTH_ACCT_RATE_CHANGE);
    public static final ReplyMessageType STOP_ON_ACCOUNT = new ReplyMessageType(_STOP_ON_ACCOUNT);
    public static final ReplyMessageType AUTO_APPROVED = new ReplyMessageType(_AUTO_APPROVED);
    public java.lang.String getValue() { return _value_;}
    public static ReplyMessageType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ReplyMessageType enumeration = (ReplyMessageType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ReplyMessageType fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ReplyMessageType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://enumeration.infra.fc.ofss.com", "replyMessageType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
