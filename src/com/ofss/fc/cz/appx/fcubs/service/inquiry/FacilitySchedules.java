/**
 * FacilitySchedules.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilitySchedules  implements java.io.Serializable {
    private java.math.BigDecimal facilityid;

    private java.math.BigDecimal limitamount;

    private java.lang.String limitdate;

    public FacilitySchedules() {
    }

    public FacilitySchedules(
           java.math.BigDecimal facilityid,
           java.math.BigDecimal limitamount,
           java.lang.String limitdate) {
           this.facilityid = facilityid;
           this.limitamount = limitamount;
           this.limitdate = limitdate;
    }


    /**
     * Gets the facilityid value for this FacilitySchedules.
     * 
     * @return facilityid
     */
    public java.math.BigDecimal getFacilityid() {
        return facilityid;
    }


    /**
     * Sets the facilityid value for this FacilitySchedules.
     * 
     * @param facilityid
     */
    public void setFacilityid(java.math.BigDecimal facilityid) {
        this.facilityid = facilityid;
    }


    /**
     * Gets the limitamount value for this FacilitySchedules.
     * 
     * @return limitamount
     */
    public java.math.BigDecimal getLimitamount() {
        return limitamount;
    }


    /**
     * Sets the limitamount value for this FacilitySchedules.
     * 
     * @param limitamount
     */
    public void setLimitamount(java.math.BigDecimal limitamount) {
        this.limitamount = limitamount;
    }


    /**
     * Gets the limitdate value for this FacilitySchedules.
     * 
     * @return limitdate
     */
    public java.lang.String getLimitdate() {
        return limitdate;
    }


    /**
     * Sets the limitdate value for this FacilitySchedules.
     * 
     * @param limitdate
     */
    public void setLimitdate(java.lang.String limitdate) {
        this.limitdate = limitdate;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilitySchedules)) return false;
        FacilitySchedules other = (FacilitySchedules) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.facilityid==null && other.getFacilityid()==null) || 
             (this.facilityid!=null &&
              this.facilityid.equals(other.getFacilityid()))) &&
            ((this.limitamount==null && other.getLimitamount()==null) || 
             (this.limitamount!=null &&
              this.limitamount.equals(other.getLimitamount()))) &&
            ((this.limitdate==null && other.getLimitdate()==null) || 
             (this.limitdate!=null &&
              this.limitdate.equals(other.getLimitdate())));
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
        if (getFacilityid() != null) {
            _hashCode += getFacilityid().hashCode();
        }
        if (getLimitamount() != null) {
            _hashCode += getLimitamount().hashCode();
        }
        if (getLimitdate() != null) {
            _hashCode += getLimitdate().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilitySchedules.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilitySchedules"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityid");
        elemField.setXmlName(new javax.xml.namespace.QName("", "facilityid"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitamount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limitamount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitdate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "limitdate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "anySimpleType"));
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
