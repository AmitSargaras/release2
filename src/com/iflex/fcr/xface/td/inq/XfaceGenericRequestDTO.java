/**
 * XfaceGenericRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.iflex.fcr.xface.td.inq;

public class XfaceGenericRequestDTO  implements java.io.Serializable {
    private java.lang.String validationFlag;

    public XfaceGenericRequestDTO() {
    }

    public XfaceGenericRequestDTO(
           java.lang.String validationFlag) {
           this.validationFlag = validationFlag;
    }


    /**
     * Gets the validationFlag value for this XfaceGenericRequestDTO.
     * 
     * @return validationFlag
     */
    public java.lang.String getValidationFlag() {
        return validationFlag;
    }


    /**
     * Sets the validationFlag value for this XfaceGenericRequestDTO.
     * 
     * @param validationFlag
     */
    public void setValidationFlag(java.lang.String validationFlag) {
        this.validationFlag = validationFlag;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof XfaceGenericRequestDTO)) return false;
        XfaceGenericRequestDTO other = (XfaceGenericRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.validationFlag==null && other.getValidationFlag()==null) || 
             (this.validationFlag!=null &&
              this.validationFlag.equals(other.getValidationFlag())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getValidationFlag() != null) {
            _hashCode += getValidationFlag().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(XfaceGenericRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inq.td.xface.fcr.iflex.com/", "xfaceGenericRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("validationFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "validationFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
