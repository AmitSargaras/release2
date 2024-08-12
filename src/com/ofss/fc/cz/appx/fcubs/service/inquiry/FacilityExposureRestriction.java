/**
 * FacilityExposureRestriction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityExposureRestriction  implements java.io.Serializable {
    private java.math.BigDecimal exposure;

    private java.math.BigDecimal facility;

    public FacilityExposureRestriction() {
    }

    public FacilityExposureRestriction(
           java.math.BigDecimal exposure,
           java.math.BigDecimal facility) {
           this.exposure = exposure;
           this.facility = facility;
    }


    /**
     * Gets the exposure value for this FacilityExposureRestriction.
     * 
     * @return exposure
     */
    public java.math.BigDecimal getExposure() {
        return exposure;
    }


    /**
     * Sets the exposure value for this FacilityExposureRestriction.
     * 
     * @param exposure
     */
    public void setExposure(java.math.BigDecimal exposure) {
        this.exposure = exposure;
    }


    /**
     * Gets the facility value for this FacilityExposureRestriction.
     * 
     * @return facility
     */
    public java.math.BigDecimal getFacility() {
        return facility;
    }


    /**
     * Sets the facility value for this FacilityExposureRestriction.
     * 
     * @param facility
     */
    public void setFacility(java.math.BigDecimal facility) {
        this.facility = facility;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityExposureRestriction)) return false;
        FacilityExposureRestriction other = (FacilityExposureRestriction) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.exposure==null && other.getExposure()==null) || 
             (this.exposure!=null &&
              this.exposure.equals(other.getExposure()))) &&
            ((this.facility==null && other.getFacility()==null) || 
             (this.facility!=null &&
              this.facility.equals(other.getFacility())));
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
        if (getExposure() != null) {
            _hashCode += getExposure().hashCode();
        }
        if (getFacility() != null) {
            _hashCode += getFacility().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityExposureRestriction.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityExposureRestriction"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exposure");
        elemField.setXmlName(new javax.xml.namespace.QName("", "exposure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facility");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facility"));
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
