/**
 * QUERYLIABILITY_IOFS_RES.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class QUERYLIABILITY_IOFS_RES  extends com.ofss.fc.service.response.BaseResponse  implements java.io.Serializable {
    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType FCUBS_HEADER;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.QUERYLIABILITY_IOFS_RESFCUBS_BODY FCUBS_BODY;

    public QUERYLIABILITY_IOFS_RES() {
    }

    public QUERYLIABILITY_IOFS_RES(
           java.lang.String configVersionId,
           com.ofss.fc.enumeration.MaintenanceType maintenanceType,
           com.ofss.fc.service.response.TransactionStatus status,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType FCUBS_HEADER,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.QUERYLIABILITY_IOFS_RESFCUBS_BODY FCUBS_BODY) {
        super(
            configVersionId,
            maintenanceType,
            status);
        this.FCUBS_HEADER = FCUBS_HEADER;
        this.FCUBS_BODY = FCUBS_BODY;
    }


    /**
     * Gets the FCUBS_HEADER value for this QUERYLIABILITY_IOFS_RES.
     * 
     * @return FCUBS_HEADER
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType getFCUBS_HEADER() {
        return FCUBS_HEADER;
    }


    /**
     * Sets the FCUBS_HEADER value for this QUERYLIABILITY_IOFS_RES.
     * 
     * @param FCUBS_HEADER
     */
    public void setFCUBS_HEADER(com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType FCUBS_HEADER) {
        this.FCUBS_HEADER = FCUBS_HEADER;
    }


    /**
     * Gets the FCUBS_BODY value for this QUERYLIABILITY_IOFS_RES.
     * 
     * @return FCUBS_BODY
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.QUERYLIABILITY_IOFS_RESFCUBS_BODY getFCUBS_BODY() {
        return FCUBS_BODY;
    }


    /**
     * Sets the FCUBS_BODY value for this QUERYLIABILITY_IOFS_RES.
     * 
     * @param FCUBS_BODY
     */
    public void setFCUBS_BODY(com.ofss.fc.cz.appx.fcubs.service.inquiry.QUERYLIABILITY_IOFS_RESFCUBS_BODY FCUBS_BODY) {
        this.FCUBS_BODY = FCUBS_BODY;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QUERYLIABILITY_IOFS_RES)) return false;
        QUERYLIABILITY_IOFS_RES other = (QUERYLIABILITY_IOFS_RES) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.FCUBS_HEADER==null && other.getFCUBS_HEADER()==null) || 
             (this.FCUBS_HEADER!=null &&
              this.FCUBS_HEADER.equals(other.getFCUBS_HEADER()))) &&
            ((this.FCUBS_BODY==null && other.getFCUBS_BODY()==null) || 
             (this.FCUBS_BODY!=null &&
              this.FCUBS_BODY.equals(other.getFCUBS_BODY())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = super.hashCode();
        if (getFCUBS_HEADER() != null) {
            _hashCode += getFCUBS_HEADER().hashCode();
        }
        if (getFCUBS_BODY() != null) {
            _hashCode += getFCUBS_BODY().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QUERYLIABILITY_IOFS_RES.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", ">QUERYLIABILITY_IOFS_RES"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FCUBS_HEADER");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FCUBS_HEADER"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsHeaderType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FCUBS_BODY");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FCUBS_BODY"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", ">>QUERYLIABILITY_IOFS_RES>FCUBS_BODY"));
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
