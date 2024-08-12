/**
 * FacilityCurrencyRestriction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityCurrencyRestriction  implements java.io.Serializable {
    private java.lang.String ccy;

    private java.math.BigDecimal facilityid;

    public FacilityCurrencyRestriction() {
    }

    public FacilityCurrencyRestriction(
           java.lang.String ccy,
           java.math.BigDecimal facilityid) {
           this.ccy = ccy;
           this.facilityid = facilityid;
    }


    /**
     * Gets the ccy value for this FacilityCurrencyRestriction.
     * 
     * @return ccy
     */
    public java.lang.String getCcy() {
        return ccy;
    }


    /**
     * Sets the ccy value for this FacilityCurrencyRestriction.
     * 
     * @param ccy
     */
    public void setCcy(java.lang.String ccy) {
        this.ccy = ccy;
    }


    /**
     * Gets the facilityid value for this FacilityCurrencyRestriction.
     * 
     * @return facilityid
     */
    public java.math.BigDecimal getFacilityid() {
        return facilityid;
    }


    /**
     * Sets the facilityid value for this FacilityCurrencyRestriction.
     * 
     * @param facilityid
     */
    public void setFacilityid(java.math.BigDecimal facilityid) {
        this.facilityid = facilityid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityCurrencyRestriction)) return false;
        FacilityCurrencyRestriction other = (FacilityCurrencyRestriction) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.ccy==null && other.getCcy()==null) || 
             (this.ccy!=null &&
              this.ccy.equals(other.getCcy()))) &&
            ((this.facilityid==null && other.getFacilityid()==null) || 
             (this.facilityid!=null &&
              this.facilityid.equals(other.getFacilityid())));
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
        if (getCcy() != null) {
            _hashCode += getCcy().hashCode();
        }
        if (getFacilityid() != null) {
            _hashCode += getFacilityid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityCurrencyRestriction.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityCurrencyRestriction"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ccy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
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
