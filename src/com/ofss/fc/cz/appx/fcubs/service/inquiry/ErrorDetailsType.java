/**
 * ErrorDetailsType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class ErrorDetailsType  implements java.io.Serializable {
    private java.lang.String ecode;

    private java.lang.String edesc;

    public ErrorDetailsType() {
    }

    public ErrorDetailsType(
           java.lang.String ecode,
           java.lang.String edesc) {
           this.ecode = ecode;
           this.edesc = edesc;
    }


    /**
     * Gets the ecode value for this ErrorDetailsType.
     * 
     * @return ecode
     */
    public java.lang.String getEcode() {
        return ecode;
    }


    /**
     * Sets the ecode value for this ErrorDetailsType.
     * 
     * @param ecode
     */
    public void setEcode(java.lang.String ecode) {
        this.ecode = ecode;
    }


    /**
     * Gets the edesc value for this ErrorDetailsType.
     * 
     * @return edesc
     */
    public java.lang.String getEdesc() {
        return edesc;
    }


    /**
     * Sets the edesc value for this ErrorDetailsType.
     * 
     * @param edesc
     */
    public void setEdesc(java.lang.String edesc) {
        this.edesc = edesc;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ErrorDetailsType)) return false;
        ErrorDetailsType other = (ErrorDetailsType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ecode==null && other.getEcode()==null) || 
             (this.ecode!=null &&
              this.ecode.equals(other.getEcode()))) &&
            ((this.edesc==null && other.getEdesc()==null) || 
             (this.edesc!=null &&
              this.edesc.equals(other.getEdesc())));
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
        if (getEcode() != null) {
            _hashCode += getEcode().hashCode();
        }
        if (getEdesc() != null) {
            _hashCode += getEdesc().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ErrorDetailsType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "errorDetailsType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ecode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ecode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("edesc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "edesc"));
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
