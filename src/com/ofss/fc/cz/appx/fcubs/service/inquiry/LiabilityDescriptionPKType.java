/**
 * LiabilityDescriptionPKType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ofss.fc.cz.appx.fcubs.service.inquiry;

public class LiabilityDescriptionPKType  implements java.io.Serializable {
    private java.lang.String liabilityno;

    private java.math.BigDecimal modno;

    public LiabilityDescriptionPKType() {
    }

    public LiabilityDescriptionPKType(
           java.lang.String liabilityno,
           java.math.BigDecimal modno) {
           this.liabilityno = liabilityno;
           this.modno = modno;
    }


    /**
     * Gets the liabilityno value for this LiabilityDescriptionPKType.
     * 
     * @return liabilityno
     */
    public java.lang.String getLiabilityno() {
        return liabilityno;
    }


    /**
     * Sets the liabilityno value for this LiabilityDescriptionPKType.
     * 
     * @param liabilityno
     */
    public void setLiabilityno(java.lang.String liabilityno) {
        this.liabilityno = liabilityno;
    }


    /**
     * Gets the modno value for this LiabilityDescriptionPKType.
     * 
     * @return modno
     */
    public java.math.BigDecimal getModno() {
        return modno;
    }


    /**
     * Sets the modno value for this LiabilityDescriptionPKType.
     * 
     * @param modno
     */
    public void setModno(java.math.BigDecimal modno) {
        this.modno = modno;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof LiabilityDescriptionPKType)) return false;
        LiabilityDescriptionPKType other = (LiabilityDescriptionPKType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.liabilityno==null && other.getLiabilityno()==null) || 
             (this.liabilityno!=null &&
              this.liabilityno.equals(other.getLiabilityno()))) &&
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
        if (getLiabilityno() != null) {
            _hashCode += getLiabilityno().hashCode();
        }
        if (getModno() != null) {
            _hashCode += getModno().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(LiabilityDescriptionPKType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://inquiry.service.fcubs.appx.cz.fc.ofss.com/", "LiabilityDescription-PK-Type"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("liabilityno");
        elemField.setXmlName(new javax.xml.namespace.QName("", "liabilityno"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
