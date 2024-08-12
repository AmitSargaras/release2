/**
 * ServiceCallContextType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.enumeration;

public class ServiceCallContextType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected ServiceCallContextType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _NORMAL = "NORMAL";
    public static final java.lang.String _VALIDATE = "VALIDATE";
    public static final java.lang.String _WORKFLOW = "WORKFLOW";
    public static final java.lang.String _EXIM = "EXIM";
    public static final ServiceCallContextType NORMAL = new ServiceCallContextType(_NORMAL);
    public static final ServiceCallContextType VALIDATE = new ServiceCallContextType(_VALIDATE);
    public static final ServiceCallContextType WORKFLOW = new ServiceCallContextType(_WORKFLOW);
    public static final ServiceCallContextType EXIM = new ServiceCallContextType(_EXIM);
    public java.lang.String getValue() { return _value_;}
    public static ServiceCallContextType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        ServiceCallContextType enumeration = (ServiceCallContextType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static ServiceCallContextType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(ServiceCallContextType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://enumeration.fc.ofss.com", "serviceCallContextType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
