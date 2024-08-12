/**
 * LiabilityCreditRating.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class LiabilityCreditRating  implements java.io.Serializable {
    private java.math.BigDecimal agency;

    private java.math.BigDecimal creditrating;

    private java.math.BigDecimal liability;

    private java.lang.String primary;

    public LiabilityCreditRating() {
    }

    public LiabilityCreditRating(
           java.math.BigDecimal agency,
           java.math.BigDecimal creditrating,
           java.math.BigDecimal liability,
           java.lang.String primary) {
           this.agency = agency;
           this.creditrating = creditrating;
           this.liability = liability;
           this.primary = primary;
    }


    /**
     * Gets the agency value for this LiabilityCreditRating.
     * 
     * @return agency
     */
    public java.math.BigDecimal getAgency() {
        return agency;
    }


    /**
     * Sets the agency value for this LiabilityCreditRating.
     * 
     * @param agency
     */
    public void setAgency(java.math.BigDecimal agency) {
        this.agency = agency;
    }


    /**
     * Gets the creditrating value for this LiabilityCreditRating.
     * 
     * @return creditrating
     */
    public java.math.BigDecimal getCreditrating() {
        return creditrating;
    }


    /**
     * Sets the creditrating value for this LiabilityCreditRating.
     * 
     * @param creditrating
     */
    public void setCreditrating(java.math.BigDecimal creditrating) {
        this.creditrating = creditrating;
    }


    /**
     * Gets the liability value for this LiabilityCreditRating.
     * 
     * @return liability
     */
    public java.math.BigDecimal getLiability() {
        return liability;
    }


    /**
     * Sets the liability value for this LiabilityCreditRating.
     * 
     * @param liability
     */
    public void setLiability(java.math.BigDecimal liability) {
        this.liability = liability;
    }


    /**
     * Gets the primary value for this LiabilityCreditRating.
     * 
     * @return primary
     */
    public java.lang.String getPrimary() {
        return primary;
    }


    /**
     * Sets the primary value for this LiabilityCreditRating.
     * 
     * @param primary
     */
    public void setPrimary(java.lang.String primary) {
        this.primary = primary;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LiabilityCreditRating)) return false;
        LiabilityCreditRating other = (LiabilityCreditRating) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.agency==null && other.getAgency()==null) || 
             (this.agency!=null &&
              this.agency.equals(other.getAgency()))) &&
            ((this.creditrating==null && other.getCreditrating()==null) || 
             (this.creditrating!=null &&
              this.creditrating.equals(other.getCreditrating()))) &&
            ((this.liability==null && other.getLiability()==null) || 
             (this.liability!=null &&
              this.liability.equals(other.getLiability()))) &&
            ((this.primary==null && other.getPrimary()==null) || 
             (this.primary!=null &&
              this.primary.equals(other.getPrimary())));
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
        if (getAgency() != null) {
            _hashCode += getAgency().hashCode();
        }
        if (getCreditrating() != null) {
            _hashCode += getCreditrating().hashCode();
        }
        if (getLiability() != null) {
            _hashCode += getLiability().hashCode();
        }
        if (getPrimary() != null) {
            _hashCode += getPrimary().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LiabilityCreditRating.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "liabilityCreditRating"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("agency");
        elemField.setXmlName(new javax.xml.namespace.QName("", "agency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditrating");
        elemField.setXmlName(new javax.xml.namespace.QName("", "creditrating"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liability");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liability"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("primary");
        elemField.setXmlName(new javax.xml.namespace.QName("", "primary"));
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
