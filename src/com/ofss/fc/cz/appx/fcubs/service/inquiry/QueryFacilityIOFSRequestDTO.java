/**
 * QueryFacilityIOFSRequestDTO.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class QueryFacilityIOFSRequestDTO  extends com.ofss.fc.framework.domain.common.dto.DataTransferObject  implements java.io.Serializable {
    private com.ofss.fc.cz.appx.fcubs.service.inquiry.Fcubsbodyrequest fcubsbody;

    private com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType fcubsheader;

    public QueryFacilityIOFSRequestDTO() {
    }

    public QueryFacilityIOFSRequestDTO(
           com.ofss.fc.framework.domain.common.dto.Dictionary[] dictionaryArray,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.Fcubsbodyrequest fcubsbody,
           com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType fcubsheader) {
        super(
            dictionaryArray);
        this.fcubsbody = fcubsbody;
        this.fcubsheader = fcubsheader;
    }


    /**
     * Gets the fcubsbody value for this QueryFacilityIOFSRequestDTO.
     * 
     * @return fcubsbody
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.Fcubsbodyrequest getFcubsbody() {
        return fcubsbody;
    }


    /**
     * Sets the fcubsbody value for this QueryFacilityIOFSRequestDTO.
     * 
     * @param fcubsbody
     */
    public void setFcubsbody(com.ofss.fc.cz.appx.fcubs.service.inquiry.Fcubsbodyrequest fcubsbody) {
        this.fcubsbody = fcubsbody;
    }


    /**
     * Gets the fcubsheader value for this QueryFacilityIOFSRequestDTO.
     * 
     * @return fcubsheader
     */
    public com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType getFcubsheader() {
        return fcubsheader;
    }


    /**
     * Sets the fcubsheader value for this QueryFacilityIOFSRequestDTO.
     * 
     * @param fcubsheader
     */
    public void setFcubsheader(com.ofss.fc.cz.appx.fcubs.service.inquiry.FcubsHeaderType fcubsheader) {
        this.fcubsheader = fcubsheader;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryFacilityIOFSRequestDTO)) return false;
        QueryFacilityIOFSRequestDTO other = (QueryFacilityIOFSRequestDTO) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = super.equals(obj) && 
            ((this.fcubsbody==null && other.getFcubsbody()==null) || 
             (this.fcubsbody!=null &&
              this.fcubsbody.equals(other.getFcubsbody()))) &&
            ((this.fcubsheader==null && other.getFcubsheader()==null) || 
             (this.fcubsheader!=null &&
              this.fcubsheader.equals(other.getFcubsheader())));
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
        if (getFcubsbody() != null) {
            _hashCode += getFcubsbody().hashCode();
        }
        if (getFcubsheader() != null) {
            _hashCode += getFcubsheader().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryFacilityIOFSRequestDTO.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "queryFacilityIOFSRequestDTO"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fcubsbody");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fcubsbody"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsbodyrequest"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fcubsheader");
        elemField.setXmlName(new javax.xml.namespace.QName("", "fcubsheader"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "fcubsHeaderType"));
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
