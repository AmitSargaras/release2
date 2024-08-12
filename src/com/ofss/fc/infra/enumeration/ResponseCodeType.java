/**
 * ResponseCodeType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.infra.enumeration;

public class ResponseCodeType implements java.io.Serializable {
	
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ResponseCodeType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NOT_TEMP_CLOSED = "NOT_TEMP_CLOSED";
    public static final java.lang.String _NO_SERVICE_AVL = "NO_SERVICE_AVL";
    public static final java.lang.String _MAX_LIMIT = "MAX_LIMIT";
    public static final java.lang.String _NOT_CONFIRMED = "NOT_CONFIRMED";
    public static final java.lang.String _ACTION_BLOCK = "ACTION_BLOCK";
    public static final java.lang.String _SUCCESS_SWPIN = "SUCCESS_SWPIN";
    public static final java.lang.String _LN_VOID = "LN_VOID";
    public static final java.lang.String _INV_BATCH_STATUS = "INV_BATCH_STATUS";
    public static final java.lang.String _SUCCESS = "SUCCESS";
    public static final java.lang.String _SUCCESS_TNKTXN = "SUCCESS_TNKTXN";
    public static final java.lang.String _INV_PROC_CODE = "INV_PROC_CODE";
    public static final java.lang.String _OVR_EXCEP_PRES_01 = "OVR_EXCEP_PRES_01";
    public static final java.lang.String _NOGLACCT = "NOGLACCT";
    public static final java.lang.String _SUCCESS_CRD = "SUCCESS_CRD";
    public static final java.lang.String _TWO_BATCH = "TWO_BATCH";
    public static final java.lang.String _ACTION_DELAY = "ACTION_DELAY";
    public static final java.lang.String _ACTION_CHALLENGE_1FA_DELAY = "ACTION_CHALLENGE_1FA_DELAY";
    public static final java.lang.String _ACTION_CHALLENGE_1FA_DELAY_INTERNAL = "ACTION_CHALLENGE_1FA_DELAY_INTERNAL";
    public static final java.lang.String _ACTION_CHALLENGE_2FA_DELAY_INTERNAL = "ACTION_CHALLENGE_2FA_DELAY_INTERNAL";
    public static final java.lang.String _SUCCESS_AUNCLR = "SUCCESS_AUNCLR";
    public static final java.lang.String _DATES_NOT_IN_SYNC = "DATES_NOT_IN_SYNC";
    public static final java.lang.String _SUCCESS_OVR = "SUCCESS_OVR";
    public static final java.lang.String _INV_SERVER = "INV_SERVER";
    public static final java.lang.String _OVR_EXCEP_PRES_99 = "OVR_EXCEP_PRES_99";
    public static final java.lang.String _TIMED_OUT = "TIMED_OUT";
    public static final java.lang.String _CH_VOID = "CH_VOID";
    public static final java.lang.String _TD_VOID = "TD_VOID";
    public static final java.lang.String _INT_ERROR = "INT_ERROR";
    public static final java.lang.String _INV_USER = "INV_USER";
    public static final java.lang.String _BATCH_NOT_OPEN = "BATCH_NOT_OPEN";
    public static final java.lang.String _ACTION_CHALLENGE_2FA_DELAY = "ACTION_CHALLENGE_2FA_DELAY";
    public static final java.lang.String _INV_MSG_FORMAT = "INV_MSG_FORMAT";
    public static final java.lang.String _BATCH_CLOSED = "BATCH_CLOSED";
    public static final java.lang.String _OVR_EXCEP_PRES_45 = "OVR_EXCEP_PRES_45";
    public static final java.lang.String _CUTOFF_IN_PROG = "CUTOFF_IN_PROG";
    public static final java.lang.String _CIFS_VOID = "CIFS_VOID";
    public static final java.lang.String _OL_VOID = "OL_VOID";
    public static final java.lang.String _ACTION_CHALLENGE_2FA = "ACTION_CHALLENGE_2FA";
    public static final java.lang.String _ACTION_CHALLENGE_1FA = "ACTION_CHALLENGE_1FA";
    public static final java.lang.String _INV_BR_CODE = "INV_BR_CODE";
    public static final java.lang.String _REM_LNK_FAILED = "REM_LNK_FAILED";
    public static final java.lang.String _FATAL_INT_ERROR = "FATAL_INT_ERROR";
    public static final java.lang.String _BATCH_ALREADY_OPEN = "BATCH_ALREADY_OPEN";
    public static final ResponseCodeType NOT_TEMP_CLOSED = new ResponseCodeType(_NOT_TEMP_CLOSED);
    public static final ResponseCodeType NO_SERVICE_AVL = new ResponseCodeType(_NO_SERVICE_AVL);
    public static final ResponseCodeType MAX_LIMIT = new ResponseCodeType(_MAX_LIMIT);
    public static final ResponseCodeType NOT_CONFIRMED = new ResponseCodeType(_NOT_CONFIRMED);
    public static final ResponseCodeType ACTION_BLOCK = new ResponseCodeType(_ACTION_BLOCK);
    public static final ResponseCodeType SUCCESS_SWPIN = new ResponseCodeType(_SUCCESS_SWPIN);
    public static final ResponseCodeType LN_VOID = new ResponseCodeType(_LN_VOID);
    public static final ResponseCodeType INV_BATCH_STATUS = new ResponseCodeType(_INV_BATCH_STATUS);
    public static final ResponseCodeType SUCCESS = new ResponseCodeType(_SUCCESS);
    public static final ResponseCodeType SUCCESS_TNKTXN = new ResponseCodeType(_SUCCESS_TNKTXN);
    public static final ResponseCodeType INV_PROC_CODE = new ResponseCodeType(_INV_PROC_CODE);
    public static final ResponseCodeType OVR_EXCEP_PRES_01 = new ResponseCodeType(_OVR_EXCEP_PRES_01);
    public static final ResponseCodeType NOGLACCT = new ResponseCodeType(_NOGLACCT);
    public static final ResponseCodeType SUCCESS_CRD = new ResponseCodeType(_SUCCESS_CRD);
    public static final ResponseCodeType TWO_BATCH = new ResponseCodeType(_TWO_BATCH);
    public static final ResponseCodeType ACTION_DELAY = new ResponseCodeType(_ACTION_DELAY);
    public static final ResponseCodeType ACTION_CHALLENGE_1FA_DELAY = new ResponseCodeType(_ACTION_CHALLENGE_1FA_DELAY);
    public static final ResponseCodeType ACTION_CHALLENGE_1FA_DELAY_INTERNAL = new ResponseCodeType(_ACTION_CHALLENGE_1FA_DELAY_INTERNAL);
    public static final ResponseCodeType ACTION_CHALLENGE_2FA_DELAY_INTERNAL = new ResponseCodeType(_ACTION_CHALLENGE_2FA_DELAY_INTERNAL);
    public static final ResponseCodeType SUCCESS_AUNCLR = new ResponseCodeType(_SUCCESS_AUNCLR);
    public static final ResponseCodeType DATES_NOT_IN_SYNC = new ResponseCodeType(_DATES_NOT_IN_SYNC);
    public static final ResponseCodeType SUCCESS_OVR = new ResponseCodeType(_SUCCESS_OVR);
    public static final ResponseCodeType INV_SERVER = new ResponseCodeType(_INV_SERVER);
    public static final ResponseCodeType OVR_EXCEP_PRES_99 = new ResponseCodeType(_OVR_EXCEP_PRES_99);
    public static final ResponseCodeType TIMED_OUT = new ResponseCodeType(_TIMED_OUT);
    public static final ResponseCodeType CH_VOID = new ResponseCodeType(_CH_VOID);
    public static final ResponseCodeType TD_VOID = new ResponseCodeType(_TD_VOID);
    public static final ResponseCodeType INT_ERROR = new ResponseCodeType(_INT_ERROR);
    public static final ResponseCodeType INV_USER = new ResponseCodeType(_INV_USER);
    public static final ResponseCodeType BATCH_NOT_OPEN = new ResponseCodeType(_BATCH_NOT_OPEN);
    public static final ResponseCodeType ACTION_CHALLENGE_2FA_DELAY = new ResponseCodeType(_ACTION_CHALLENGE_2FA_DELAY);
    public static final ResponseCodeType INV_MSG_FORMAT = new ResponseCodeType(_INV_MSG_FORMAT);
    public static final ResponseCodeType BATCH_CLOSED = new ResponseCodeType(_BATCH_CLOSED);
    public static final ResponseCodeType OVR_EXCEP_PRES_45 = new ResponseCodeType(_OVR_EXCEP_PRES_45);
    public static final ResponseCodeType CUTOFF_IN_PROG = new ResponseCodeType(_CUTOFF_IN_PROG);
    public static final ResponseCodeType CIFS_VOID = new ResponseCodeType(_CIFS_VOID);
    public static final ResponseCodeType OL_VOID = new ResponseCodeType(_OL_VOID);
    public static final ResponseCodeType ACTION_CHALLENGE_2FA = new ResponseCodeType(_ACTION_CHALLENGE_2FA);
    public static final ResponseCodeType ACTION_CHALLENGE_1FA = new ResponseCodeType(_ACTION_CHALLENGE_1FA);
    public static final ResponseCodeType INV_BR_CODE = new ResponseCodeType(_INV_BR_CODE);
    public static final ResponseCodeType REM_LNK_FAILED = new ResponseCodeType(_REM_LNK_FAILED);
    public static final ResponseCodeType FATAL_INT_ERROR = new ResponseCodeType(_FATAL_INT_ERROR);
    public static final ResponseCodeType BATCH_ALREADY_OPEN = new ResponseCodeType(_BATCH_ALREADY_OPEN);
    public java.lang.String getValue() { return _value_;}
    public static ResponseCodeType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ResponseCodeType enumeration = (ResponseCodeType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ResponseCodeType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ResponseCodeType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://enumeration.infra.fc.ofss.com", "responseCodeType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
