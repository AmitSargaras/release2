/**
 * MaintenanceType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.enumeration;

public class MaintenanceType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected MaintenanceType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ADDITION = "ADDITION";
    public static final java.lang.String _INQUIRE = "INQUIRE";
    public static final java.lang.String _REOPEN = "REOPEN";
    public static final java.lang.String _DELETION = "DELETION";
    public static final java.lang.String _AUTHORIZE = "AUTHORIZE";
    public static final java.lang.String _CANCEL = "CANCEL";
    public static final java.lang.String _AMEND = "AMEND";
    public static final java.lang.String _CLOSE = "CLOSE";
    public static final java.lang.String _NONE = "NONE";
    public static final java.lang.String _MODIFICATION = "MODIFICATION";
    public static final MaintenanceType ADDITION = new MaintenanceType(_ADDITION);
    public static final MaintenanceType INQUIRE = new MaintenanceType(_INQUIRE);
    public static final MaintenanceType REOPEN = new MaintenanceType(_REOPEN);
    public static final MaintenanceType DELETION = new MaintenanceType(_DELETION);
    public static final MaintenanceType AUTHORIZE = new MaintenanceType(_AUTHORIZE);
    public static final MaintenanceType CANCEL = new MaintenanceType(_CANCEL);
    public static final MaintenanceType AMEND = new MaintenanceType(_AMEND);
    public static final MaintenanceType CLOSE = new MaintenanceType(_CLOSE);
    public static final MaintenanceType NONE = new MaintenanceType(_NONE);
    public static final MaintenanceType MODIFICATION = new MaintenanceType(_MODIFICATION);
    public java.lang.String getValue() { return _value_;}
    public static MaintenanceType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        MaintenanceType enumeration = (MaintenanceType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static MaintenanceType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(MaintenanceType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://enumeration.fc.ofss.com", "maintenanceType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
