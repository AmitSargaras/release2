/**
 * FacilitySystemRestriction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilitySystemRestriction  implements java.io.Serializable {
    private java.lang.String extsystem;

    private java.math.BigDecimal facilityid;

    private java.math.BigDecimal modno;

    public FacilitySystemRestriction() {
    }

    public FacilitySystemRestriction(
           java.lang.String extsystem,
           java.math.BigDecimal facilityid,
           java.math.BigDecimal modno) {
           this.extsystem = extsystem;
           this.facilityid = facilityid;
           this.modno = modno;
    }


    /**
     * Gets the extsystem value for this FacilitySystemRestriction.
     * 
     * @return extsystem
     */
    public java.lang.String getExtsystem() {
        return extsystem;
    }


    /**
     * Sets the extsystem value for this FacilitySystemRestriction.
     * 
     * @param extsystem
     */
    public void setExtsystem(java.lang.String extsystem) {
        this.extsystem = extsystem;
    }


    /**
     * Gets the facilityid value for this FacilitySystemRestriction.
     * 
     * @return facilityid
     */
    public java.math.BigDecimal getFacilityid() {
        return facilityid;
    }


    /**
     * Sets the facilityid value for this FacilitySystemRestriction.
     * 
     * @param facilityid
     */
    public void setFacilityid(java.math.BigDecimal facilityid) {
        this.facilityid = facilityid;
    }


    /**
     * Gets the modno value for this FacilitySystemRestriction.
     * 
     * @return modno
     */
    public java.math.BigDecimal getModno() {
        return modno;
    }


    /**
     * Sets the modno value for this FacilitySystemRestriction.
     * 
     * @param modno
     */
    public void setModno(java.math.BigDecimal modno) {
        this.modno = modno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilitySystemRestriction)) return false;
        FacilitySystemRestriction other = (FacilitySystemRestriction) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.extsystem==null && other.getExtsystem()==null) || 
             (this.extsystem!=null &&
              this.extsystem.equals(other.getExtsystem()))) &&
            ((this.facilityid==null && other.getFacilityid()==null) || 
             (this.facilityid!=null &&
              this.facilityid.equals(other.getFacilityid()))) &&
            ((this.modno==null && other.getModno()==null) || 
             (this.modno!=null &&
              this.modno.equals(other.getModno())));
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
        if (getExtsystem() != null) {
            _hashCode += getExtsystem().hashCode();
        }
        if (getFacilityid() != null) {
            _hashCode += getFacilityid().hashCode();
        }
        if (getModno() != null) {
            _hashCode += getModno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilitySystemRestriction.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilitySystemRestriction"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extsystem");
        elemField.setXmlName(new javax.xml.namespace.QName("", "extsystem"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "modno"));
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
