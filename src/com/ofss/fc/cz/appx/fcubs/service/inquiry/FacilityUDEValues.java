/**
 * FacilityUDEValues.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityUDEValues  implements java.io.Serializable {
    private java.lang.String codeusage;

    private java.math.BigDecimal facility;

    private java.lang.String ratecode;

    private java.lang.String udeid;

    private java.math.BigDecimal udevalue;

    public FacilityUDEValues() {
    }

    public FacilityUDEValues(
           java.lang.String codeusage,
           java.math.BigDecimal facility,
           java.lang.String ratecode,
           java.lang.String udeid,
           java.math.BigDecimal udevalue) {
           this.codeusage = codeusage;
           this.facility = facility;
           this.ratecode = ratecode;
           this.udeid = udeid;
           this.udevalue = udevalue;
    }


    /**
     * Gets the codeusage value for this FacilityUDEValues.
     * 
     * @return codeusage
     */
    public java.lang.String getCodeusage() {
        return codeusage;
    }


    /**
     * Sets the codeusage value for this FacilityUDEValues.
     * 
     * @param codeusage
     */
    public void setCodeusage(java.lang.String codeusage) {
        this.codeusage = codeusage;
    }


    /**
     * Gets the facility value for this FacilityUDEValues.
     * 
     * @return facility
     */
    public java.math.BigDecimal getFacility() {
        return facility;
    }


    /**
     * Sets the facility value for this FacilityUDEValues.
     * 
     * @param facility
     */
    public void setFacility(java.math.BigDecimal facility) {
        this.facility = facility;
    }


    /**
     * Gets the ratecode value for this FacilityUDEValues.
     * 
     * @return ratecode
     */
    public java.lang.String getRatecode() {
        return ratecode;
    }


    /**
     * Sets the ratecode value for this FacilityUDEValues.
     * 
     * @param ratecode
     */
    public void setRatecode(java.lang.String ratecode) {
        this.ratecode = ratecode;
    }


    /**
     * Gets the udeid value for this FacilityUDEValues.
     * 
     * @return udeid
     */
    public java.lang.String getUdeid() {
        return udeid;
    }


    /**
     * Sets the udeid value for this FacilityUDEValues.
     * 
     * @param udeid
     */
    public void setUdeid(java.lang.String udeid) {
        this.udeid = udeid;
    }


    /**
     * Gets the udevalue value for this FacilityUDEValues.
     * 
     * @return udevalue
     */
    public java.math.BigDecimal getUdevalue() {
        return udevalue;
    }


    /**
     * Sets the udevalue value for this FacilityUDEValues.
     * 
     * @param udevalue
     */
    public void setUdevalue(java.math.BigDecimal udevalue) {
        this.udevalue = udevalue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityUDEValues)) return false;
        FacilityUDEValues other = (FacilityUDEValues) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.codeusage==null && other.getCodeusage()==null) || 
             (this.codeusage!=null &&
              this.codeusage.equals(other.getCodeusage()))) &&
            ((this.facility==null && other.getFacility()==null) || 
             (this.facility!=null &&
              this.facility.equals(other.getFacility()))) &&
            ((this.ratecode==null && other.getRatecode()==null) || 
             (this.ratecode!=null &&
              this.ratecode.equals(other.getRatecode()))) &&
            ((this.udeid==null && other.getUdeid()==null) || 
             (this.udeid!=null &&
              this.udeid.equals(other.getUdeid()))) &&
            ((this.udevalue==null && other.getUdevalue()==null) || 
             (this.udevalue!=null &&
              this.udevalue.equals(other.getUdevalue())));
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
        if (getCodeusage() != null) {
            _hashCode += getCodeusage().hashCode();
        }
        if (getFacility() != null) {
            _hashCode += getFacility().hashCode();
        }
        if (getRatecode() != null) {
            _hashCode += getRatecode().hashCode();
        }
        if (getUdeid() != null) {
            _hashCode += getUdeid().hashCode();
        }
        if (getUdevalue() != null) {
            _hashCode += getUdevalue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityUDEValues.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityUDEValues"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("codeusage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "codeusage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ratecode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ratecode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("udeid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "udeid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("udevalue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "udevalue"));
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
