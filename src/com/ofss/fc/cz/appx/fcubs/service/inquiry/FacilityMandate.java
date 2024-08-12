/**
 * FacilityMandate.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class FacilityMandate  implements java.io.Serializable {
    private java.lang.String mandatetype;

    private java.math.BigDecimal minperaval;

    public FacilityMandate() {
    }

    public FacilityMandate(
           java.lang.String mandatetype,
           java.math.BigDecimal minperaval) {
           this.mandatetype = mandatetype;
           this.minperaval = minperaval;
    }


    /**
     * Gets the mandatetype value for this FacilityMandate.
     * 
     * @return mandatetype
     */
    public java.lang.String getMandatetype() {
        return mandatetype;
    }


    /**
     * Sets the mandatetype value for this FacilityMandate.
     * 
     * @param mandatetype
     */
    public void setMandatetype(java.lang.String mandatetype) {
        this.mandatetype = mandatetype;
    }


    /**
     * Gets the minperaval value for this FacilityMandate.
     * 
     * @return minperaval
     */
    public java.math.BigDecimal getMinperaval() {
        return minperaval;
    }


    /**
     * Sets the minperaval value for this FacilityMandate.
     * 
     * @param minperaval
     */
    public void setMinperaval(java.math.BigDecimal minperaval) {
        this.minperaval = minperaval;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof FacilityMandate)) return false;
        FacilityMandate other = (FacilityMandate) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.mandatetype==null && other.getMandatetype()==null) || 
             (this.mandatetype!=null &&
              this.mandatetype.equals(other.getMandatetype()))) &&
            ((this.minperaval==null && other.getMinperaval()==null) || 
             (this.minperaval!=null &&
              this.minperaval.equals(other.getMinperaval())));
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
        if (getMandatetype() != null) {
            _hashCode += getMandatetype().hashCode();
        }
        if (getMinperaval() != null) {
            _hashCode += getMinperaval().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(FacilityMandate.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "facilityMandate"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mandatetype");
        elemField.setXmlName(new javax.xml.namespace.QName("", "mandatetype"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("minperaval");
        elemField.setXmlName(new javax.xml.namespace.QName("", "minperaval"));
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
